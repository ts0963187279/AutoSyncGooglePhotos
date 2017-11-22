package com.walton.java.autosyncgooglephotos.processor;

import com.walton.java.GooglePhotosForJava.model.AlbumInfo;
import com.walton.java.GooglePhotosForJava.model.PhotoInfo;
import poisondog.core.Mission;

import java.util.List;

public class GetNumberOfPhotos implements Mission<List<AlbumInfo>>{
    public Integer execute(List<AlbumInfo> albumInfos){
        int numberOfPhotos = 0;
        for(AlbumInfo albumInfo : albumInfos)
            for(PhotoInfo photoInfo : albumInfo.getPhotoInfos())
                numberOfPhotos ++;
        return numberOfPhotos;
    }
}
