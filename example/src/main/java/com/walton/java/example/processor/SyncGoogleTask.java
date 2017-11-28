package com.walton.java.example.processor;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gdata.client.photos.PicasawebService;
import com.walton.java.GoogleDriveForJava.processor.*;
import com.walton.java.accessgoogleservice.module.FirebaseMessage;
import com.walton.java.accessgoogleservice.module.OAuth2Data;
import com.walton.java.accessgoogleservice.processor.GetAccessToken;
import com.walton.java.accessgoogleservice.processor.GetGoogleCredential;
import com.walton.java.accessgoogleservice.processor.GetRefreshToken;
import com.walton.java.accessgoogleservice.processor.SendFirebaseMessage;
import com.walton.java.autosyncgooglephotos.model.AccessGoogleData;
import com.walton.java.autosyncgooglephotos.processor.AnalyzeAccessGoogleData;
import com.walton.java.socket.processor.GetStringFromClient;

import java.io.IOException;
import java.net.Socket;
import java.util.ResourceBundle;

public class SyncGoogleTask extends Thread{
    private Socket socket;
    public SyncGoogleTask(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("local");
        String authorizationKey = resourceBundle.getString("authorizationKey");
        System.out.println(authorizationKey);
        OAuth2Data oAuth2Data = new OAuth2Data();
        String refreshToken = null;
        String accessToken;
        System.out.println("connected");
        GetStringFromClient getStringFromClient = null;
        SendFirebaseMessage sendFirebaseMessage = null;
        try {
            getStringFromClient = new GetStringFromClient(socket);
            String accessGoogleDataString = getStringFromClient.execute(null);
            AccessGoogleData accessGoogleData = new AnalyzeAccessGoogleData().execute(accessGoogleDataString);
            oAuth2Data.setClientId(accessGoogleData.getClientID());
            oAuth2Data.setClientSecrets(accessGoogleData.getClientSecret());
            oAuth2Data.setUserName(accessGoogleData.getUserName());
            String authCode = accessGoogleData.getAuthCode();
            sendFirebaseMessage = new SendFirebaseMessage(authorizationKey,accessGoogleData.getFirebaseToken());
            oAuth2Data.addScope(accessGoogleData.getScope());
            GetRefreshToken getRefreshToken = new GetRefreshToken(oAuth2Data);
            refreshToken = getRefreshToken.execute(authCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GetAccessToken getAccessToken = new GetAccessToken(oAuth2Data);
        accessToken = getAccessToken.execute(refreshToken);
        GetGoogleCredential getGoogleCredential = new GetGoogleCredential(oAuth2Data);
        GoogleCredential credential = getGoogleCredential.execute(accessToken);
        if(oAuth2Data.getScope().get(0).equals("https://picasaweb.google.com/data/")) {
            PicasawebService picasawebService = new PicasawebService("SyncGooglePhotos");
            picasawebService.setOAuth2Credentials(credential);
            FirebaseMessage firebaseMessage = new FirebaseMessage();
            firebaseMessage.setBody("GooglePhotoSyncStart");
            firebaseMessage.setTitle("GooglePhotoSync");
            sendFirebaseMessage.execute(firebaseMessage);
            new SyncGooglePhotos(oAuth2Data.getUserName(),sendFirebaseMessage).execute(picasawebService);
        }else if(oAuth2Data.getScope().get(0).equals("https://www.googleapis.com/auth/drive")) {
            GetDriveService getDriveService = new GetDriveService(oAuth2Data);
            FirebaseMessage firebaseMessage = new FirebaseMessage();
            firebaseMessage.setBody("GoogleDriveSyncStart");
            firebaseMessage.setTitle("GoogleDriveSync");
            sendFirebaseMessage.execute(firebaseMessage);
            new SyncGoogleDrive(sendFirebaseMessage).execute(getDriveService.execute(credential));
        }
    }
}
