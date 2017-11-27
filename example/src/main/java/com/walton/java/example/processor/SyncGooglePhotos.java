package com.walton.java.example.processor;

import com.google.gdata.client.photos.PicasawebService;
import com.walton.java.GooglePhotosForJava.processor.DownloadImage;
import poisondog.core.Mission;

public class SyncGooglePhotos implements Mission<PicasawebService> {
    private String userName;
    public SyncGooglePhotos(String userName){
        this.userName = userName;
    }
    @Override
    public Void execute(PicasawebService picasawebService) {
        DownloadImage downloadImage = new DownloadImage(userName);
        downloadImage.setPath("/var/www/web1/web/myskybox/sata_1/Google/Photos/");
        downloadImage.execute(picasawebService);
        return null;
    }
}
