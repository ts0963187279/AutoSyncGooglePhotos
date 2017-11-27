package com.walton.java.autosyncgooglephotos.processor;

import com.walton.java.GooglePhotosForJava.model.AlbumInfo;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeAlbumDataTest {
    @Test
    public void testExecute() throws MalformedURLException {
        AnalyzeAlbumData analyzePhotosUrl = new AnalyzeAlbumData();
        String albumData = "albumName\n{\nphotoName\nhttps://1\nphotoName2\nhttps://2\n}\n";
        List<AlbumInfo> actual = analyzePhotosUrl.execute(albumData);
        List<AlbumInfo> expected = new ArrayList<AlbumInfo>();
        AlbumInfo albumInfo = new AlbumInfo();
        albumInfo.setAlbumName("albumName");
        albumInfo.addPhotoNameAndPhotoURL("photoName",new URL("https://1"));
        albumInfo.addPhotoNameAndPhotoURL("photoName2",new URL("https://2"));
        expected.add(albumInfo);
        Assert.assertEquals(expected.get(0).getAlbumName(),actual.get(0).getAlbumName());
        Assert.assertEquals(expected.get(0).getPhotoInfos().get(0).getPhotoName(),actual.get(0).getPhotoInfos().get(0).getPhotoName());
        Assert.assertEquals(expected.get(0).getPhotoInfos().get(0).getUrl(),actual.get(0).getPhotoInfos().get(0).getUrl());
        Assert.assertEquals(expected.get(0).getPhotoInfos().get(1).getPhotoName(),actual.get(0).getPhotoInfos().get(1).getPhotoName());
        Assert.assertEquals(expected.get(0).getPhotoInfos().get(1).getUrl(),actual.get(0).getPhotoInfos().get(1).getUrl());
    }
}
