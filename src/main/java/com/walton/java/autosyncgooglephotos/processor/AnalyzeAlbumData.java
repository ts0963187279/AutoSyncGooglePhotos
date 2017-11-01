package com.walton.java.autosyncgooglephotos.processor;

import com.walton.java.accessgoogleservice.module.AlbumInfo;
import poisondog.core.Mission;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeAlbumData implements Mission<String> {
    public List<AlbumInfo> execute(String albumData) throws MalformedURLException {
        List<AlbumInfo> albumInfos = new ArrayList<AlbumInfo>();
        for(int i=0;i<albumData.length();i++){
            AlbumInfo albumInfo = new AlbumInfo();
            if(i+2 ==  albumData.length())
                break;
            String albumName = "";
            for(;albumData.charAt(i) != '\n';i++)
                albumName += albumData.charAt(i);
            albumInfo.setAlbumName(albumName);
            i++;i++;i++;
            for(;albumData.charAt(i) != '}';i++){
                String photoName = "";
                String url = "";
                for(;albumData.charAt(i) != '\n';i++)
                    photoName += albumData.charAt(i);
                i++;
                for(;albumData.charAt(i) != '\n';i++)
                    url += albumData.charAt(i);
                albumInfo.addPhotoNameAndPhotoURL(photoName,new URL(url));
            }
            albumInfos.add(albumInfo);
            i++;
        }
        return albumInfos;
    }
}
