package com.walton.java.autosyncgooglephotos.processor;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeAccessGoogleDataTest {
    @Test
    public void testExecute(){
        AnalyzeAccessGoogleData analyzeAccessGoogleData = new AnalyzeAccessGoogleData();
        String stubAccessGoogleData = "name\nAuthCode\nregisterToken\n";
        List<String> actual = analyzeAccessGoogleData.execute(stubAccessGoogleData);
        List<String> expected = new ArrayList<String>();
        expected.add("name");
        expected.add("AuthCode");
        expected.add("registerToken");
        Assert.assertEquals(expected,actual);
    }
}
