package com.walton.java.autosyncgooglephotos.processor;

import com.walton.java.GoogleDriveForJava.model.SearchFileInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class GetNumberOfFilesTest {
    @Test
    public void testExecute(){
        GetNumberOfFiles getNumberOfFiles = new GetNumberOfFiles();
        Map<String,SearchFileInfo> stubFiles = new HashMap<String, SearchFileInfo>();
        stubFiles.put("1",new SearchFileInfo());
        stubFiles.put("2",new SearchFileInfo());
        int expected = 2;
        int actual = getNumberOfFiles.execute(stubFiles);
        Assert.assertEquals(expected,actual);
    }
}