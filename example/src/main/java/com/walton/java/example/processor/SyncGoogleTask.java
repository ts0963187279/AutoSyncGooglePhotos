package com.walton.java.example.processor;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gdata.client.photos.PicasawebService;
import com.walton.java.GoogleDriveForJava.processor.*;
import com.walton.java.accessgoogleservice.module.OAuth2Data;
import com.walton.java.accessgoogleservice.processor.GetAccessToken;
import com.walton.java.accessgoogleservice.processor.GetGoogleCredential;
import com.walton.java.accessgoogleservice.processor.GetRefreshToken;
import com.walton.java.autosyncgooglephotos.processor.AnalyzeAccessGoogleData;
import com.walton.java.socket.processor.GetStringFromClient;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.ResourceBundle;

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
        ResourceBundle res = ResourceBundle.getBundle("local");
        String clientId = res.getString("client_Id");
        String clientSecrets = res.getString("client_Secrets");
        oAuth2Data.setClientId(clientId);
        oAuth2Data.setClientSecrets(clientSecrets);
        GetStringFromClient getStringFromClient = null;
        try {
            getStringFromClient = new GetStringFromClient(socket);
            String accessGoogleData = getStringFromClient.execute(null);
            List<String> accessGoogleInfoList = new AnalyzeAccessGoogleData().execute(accessGoogleData);
            oAuth2Data.setUserName(accessGoogleInfoList.get(0));
            String authCode = accessGoogleInfoList.get(1);
            oAuth2Data.addScope(accessGoogleInfoList.get(2));
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
            new SyncGooglePhotos(oAuth2Data.getUserName()).execute(picasawebService);
        }else if(oAuth2Data.getScope().get(0).equals("https://www.googleapis.com/auth/drive")) {
            GetDriveService getDriveService = new GetDriveService(oAuth2Data);
            new SyncGoogleDrive().execute(getDriveService.execute(credential));
        }
    }
}
