package com.walton.java.autosyncgooglephotos.processor;

import com.walton.java.autosyncgooglephotos.model.AccessGoogleData;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeAccessGoogleDataTest {
    @Test
    public void testExecute(){
        AnalyzeAccessGoogleData analyzeAccessGoogleData = new AnalyzeAccessGoogleData();
        String stubAccessGoogleData = "clientId\nclientSecret\nname\nAuthCode\nScope\nregisterToken\n";
        AccessGoogleData actual = analyzeAccessGoogleData.execute(stubAccessGoogleData);
        AccessGoogleData expected = new AccessGoogleData();
        expected.setClientID("clientId");
        expected.setClientSecret("clientSecret");
        expected.setUserName("name");
        expected.setAuthCode("AuthCode");
        expected.setScope("Scope");
        expected.setFirebaseToken("registerToken");
        Assert.assertEquals(expected.getAuthCode(),actual.getAuthCode());
        Assert.assertEquals(expected.getClientID(),actual.getClientID());
        Assert.assertEquals(expected.getClientSecret(),actual.getClientSecret());
        Assert.assertEquals(expected.getFirebaseToken(),actual.getFirebaseToken());
        Assert.assertEquals(expected.getScope(),actual.getScope());
        Assert.assertEquals(expected.getUserName(),actual.getUserName());
    }
}
