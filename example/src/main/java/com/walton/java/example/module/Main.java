package com.walton.java.example.module;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gdata.client.photos.PicasawebService;
import com.walton.java.accessgoogleservice.module.OAuth2Data;
import com.walton.java.accessgoogleservice.processor.*;
import com.walton.java.autosyncgooglephotos.processor.AnalyzeAccessGoogleData;
import com.walton.java.autosyncgooglephotos.processor.GetNumberOfPhotos;
import com.walton.java.autosyncgooglephotos.processor.GetTime;
import com.walton.java.autosyncgooglephotos.processor.HavePhotosUpdate;
import com.walton.java.socket.processor.GetStringFromClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.*;


public class Main extends TimerTask{
    private int numberOfPhotos = -1;
    private String registrationToken = "";
    private HavePhotosUpdate havePhotosUpdate = new HavePhotosUpdate();
    public static void main(String[] args){
        Timer timer = new Timer();
        timer.schedule(new Main(),5000,180000);
    }
    @Override
    public void run() {
        ResourceBundle res = ResourceBundle.getBundle("local");
        String authorizationKey = res.getString("authorizationKey");
        String clientId = res.getString("client_Id");
        String clientSecrets = res.getString("client_Secrets");
        GetTime getTime = new GetTime();
        OAuth2Data oAuth2Data = new OAuth2Data();
        oAuth2Data.addScope("https://picasaweb.google.com/data/");
        oAuth2Data.setClientId(clientId);
        oAuth2Data.setClientSecrets(clientSecrets);
        RefreshTokenStorage refreshTokenStorage = new RefreshTokenStorage();
        RefreshTokenIsValid refreshTokenIsValid = new RefreshTokenIsValid(oAuth2Data);
        GetAccessToken getAccessToken = new GetAccessToken(oAuth2Data);
        String refreshToken = refreshTokenStorage.getToken();
        String accessToken;
        if(refreshTokenIsValid.execute(refreshToken)){
            oAuth2Data.setUserName(refreshTokenStorage.getUserName());
        }else{
            SendFirebaseMessage sendFirebaseMessage = new SendFirebaseMessage(authorizationKey,registrationToken);
            sendFirebaseMessage.execute("MySkyBox need getAuthCode");
            try {
                ServerSocket serverSocket = new ServerSocket(5566);
                System.out.println("waiting for connect.....");
                GetStringFromClient getStringFromClient = new GetStringFromClient(serverSocket.accept());
                System.out.println("connected");
                String accessGoogleData = getStringFromClient.execute(null);
                List<String> accessGoogleInfos = new AnalyzeAccessGoogleData().execute(accessGoogleData);
                oAuth2Data.setUserName(accessGoogleInfos.get(0));
                String authCode = accessGoogleInfos.get(1);
                GetRefreshToken getRefreshToken = new GetRefreshToken(oAuth2Data);
                refreshToken = getRefreshToken.execute(authCode);
                refreshTokenStorage.update(oAuth2Data.getUserName(),refreshToken);
                registrationToken = accessGoogleInfos.get(2);
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        accessToken = getAccessToken.execute(refreshToken);

        GetGoogleCredential getGoogleCredential = new GetGoogleCredential(oAuth2Data);
        GoogleCredential credential = getGoogleCredential.execute(accessToken);
        PicasawebService picasawebService = new PicasawebService("SyncGooglePhotos");
        picasawebService.setOAuth2Credentials(credential);
        GetAlbumInfos getAlbumInfos = new GetAlbumInfos(oAuth2Data.getUserName());
        GetNumberOfPhotos getNumberOfPhotos = new GetNumberOfPhotos();
        numberOfPhotos = getNumberOfPhotos.execute(getAlbumInfos.execute(picasawebService));
        if(havePhotosUpdate.execute(numberOfPhotos)) {
            Date date = new Date();
            System.out.println("開始同步時間 ： " + getTime.execute(date));
            havePhotosUpdate.setNumberOfPhotos(numberOfPhotos);
            DownLoadImage downLoadImage = new DownLoadImage(oAuth2Data.getUserName());
            downLoadImage.execute(picasawebService);
            date = new Date();
            System.out.println("同步結束時間 ： " + getTime.execute(date));
        }
    }
}
//public class Main{
//    public static void main(String args[]) throws IOException {
//        while(true){
//            ServerSocket serverSocket = new ServerSocket(5566);
//            System.out.println("waiting for connect....");
//            GetStringFromClient getStringFromClient = new GetStringFromClient(serverSocket.accept());
//            System.out.println("connected");
//            String albumData = getStringFromClient.execute(null);
//            for(int i=0;i<albumData.length();i++){
//                if(i+2 ==  albumData.length())
//                    break;
//                String albumName = "";
//                for(;albumData.charAt(i) != '\n';i++)
//                    albumName += albumData.charAt(i);
//                i++;i++;i++;
//                for(;albumData.charAt(i) != '}';i++){
//                    String photoName = "";
//                    String url = "";
//                    for(;albumData.charAt(i) != '\n';i++)
//                        photoName += albumData.charAt(i);
//                    i++;
//                    for(;albumData.charAt(i) != '\n';i++)
//                        url += albumData.charAt(i);
//                    SocketDownloadData downloadData = new SocketDownloadData(photoName,albumName);
//                    downloadData.execute(new URL(url));
//                }
//                i++;
//            }
//            getStringFromClient.close();
//            serverSocket.close();
//        }
//    }
//}