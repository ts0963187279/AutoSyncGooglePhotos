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
import com.walton.java.socket.processor.SendStringToServer;

import java.io.IOException;
import java.net.Socket;

public class SyncGoogleTask extends Thread{
    private Socket socket;
    public SyncGoogleTask(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        OAuth2Data oAuth2Data = new OAuth2Data();
        String refreshToken = null;
        String accessToken;
        System.out.println("connected");
        try {
            GetStringFromClient getStringFromClient = new GetStringFromClient(socket);
            String accessGoogleDataString = getStringFromClient.execute(null);
            AccessGoogleData accessGoogleData = new AnalyzeAccessGoogleData().execute(accessGoogleDataString);
            oAuth2Data.setClientId(accessGoogleData.getClientID());
            oAuth2Data.setClientSecrets(accessGoogleData.getClientSecret());
            oAuth2Data.setUserName(accessGoogleData.getUserName());
            String authCode = accessGoogleData.getAuthCode();
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
        SendStringToServer sendStringToServer = new SendStringToServer(socket);
        if(oAuth2Data.getScope().get(0).equals("https://picasaweb.google.com/data/")) {
            PicasawebService picasawebService = new PicasawebService("SyncGooglePhotos");
            picasawebService.setOAuth2Credentials(credential);
            sendStringToServer.execute("Sync Start , Sync Google Photos Start");
            new SyncGooglePhotos(oAuth2Data.getUserName()).execute(picasawebService);
            sendStringToServer.execute("Sync Done , Sync Google Photos Done");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(oAuth2Data.getScope().get(0).equals("https://www.googleapis.com/auth/drive")) {
            GetDriveService getDriveService = new GetDriveService(oAuth2Data);
            sendStringToServer.execute("Sync Start , Sync Google Drive Start");
            new SyncGoogleDrive().execute(getDriveService.execute(credential));
            sendStringToServer.execute("Sync Done , Sync Google Drive Done");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
