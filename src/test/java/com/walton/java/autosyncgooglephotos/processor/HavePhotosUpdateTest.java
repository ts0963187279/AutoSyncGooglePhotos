package com.walton.java.autosyncgooglephotos.processor;

import org.junit.Assert;
import org.junit.Test;

public class HavePhotosUpdateTest {
    @Test
    public void testExecute(){
        HavePhotosUpdate havePHotosUpdate = new HavePhotosUpdate();
        havePHotosUpdate.setNumberOfPhotos(0);
        boolean expected = true;
        Assert.assertEquals(expected,havePHotosUpdate.execute(1));
        expected = false;
        Assert.assertEquals(expected,havePHotosUpdate.execute(0));
    }
}
