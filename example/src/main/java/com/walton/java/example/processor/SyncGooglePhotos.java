package com.walton.java.example.processor;

import com.google.gdata.client.photos.PicasawebService;
import com.walton.java.GooglePhotosForJava.processor.DownloadImage;
import com.walton.java.accessgoogleservice.module.FirebaseMessage;
import com.walton.java.accessgoogleservice.processor.SendFirebaseMessage;
import poisondog.core.Mission;

public class SyncGooglePhotos implements Mission<PicasawebService> {
    private String userName;
    private SendFirebaseMessage sendFirebaseMessage;
    public SyncGooglePhotos(String userName, SendFirebaseMessage sendFirebaseMessage){
        this.userName = userName;
        this.sendFirebaseMessage = sendFirebaseMessage;
    }
    @Override
    public Void execute(PicasawebService picasawebService) {
        DownloadImage downloadImage = new DownloadImage(userName);
        downloadImage.setPath("/var/www/web1/web/myskybox/sata_1/Google/GooglePhotos/");
        downloadImage.execute(picasawebService);
        FirebaseMessage firebaseMessage = new FirebaseMessage();
        firebaseMessage.setBody("GoogleDriveSync done!");
        firebaseMessage.setTitle("GoogleDriveSync");
        sendFirebaseMessage.execute(firebaseMessage);
        return null;
    }
}
