package com.walton.java.autosyncgooglephotos.processor;

import com.walton.java.GooglePhotosForJava.model.AlbumInfo;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetNumberOfPhotosTest {
    @Test
    public void testExecute() throws MalformedURLException {
        GetNumberOfPhotos getNumberOfPhotos = new GetNumberOfPhotos();
        List<AlbumInfo> stubAlbumInfos = new ArrayList<AlbumInfo>();
        AlbumInfo stubAlbumInfo = new AlbumInfo();
        URL stubUrl = new URL("http://test");
        stubAlbumInfo.addPhotoNameAndPhotoURL("test",stubUrl);
        stubAlbumInfos.add(stubAlbumInfo);
        Integer expected = 1;
        Assert.assertEquals(expected,getNumberOfPhotos.execute(stubAlbumInfos));
    }
}
