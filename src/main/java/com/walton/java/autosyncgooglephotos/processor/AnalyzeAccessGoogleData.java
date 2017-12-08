package com.walton.java.autosyncgooglephotos.processor;

import com.walton.java.autosyncgooglephotos.model.AccessGoogleData;
import poisondog.core.Mission;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeAccessGoogleData implements Mission<String> {
    public AccessGoogleData execute(String accessGoogleDataString){
        AccessGoogleData accessGoogleData = new AccessGoogleData();
        List<String> accessGoogleInfoList = new ArrayList<String>();
        for(int i=0;i<accessGoogleDataString.length();i++){
            String accessGoogleInfo = "";
            for(;accessGoogleDataString.charAt(i)!='\n';i++)
                accessGoogleInfo += accessGoogleDataString.charAt(i);
            accessGoogleInfoList.add(accessGoogleInfo);
        }
        accessGoogleData.setClientID(accessGoogleInfoList.get(0));
        accessGoogleData.setClientSecret(accessGoogleInfoList.get(1));
        accessGoogleData.setUserName(accessGoogleInfoList.get(2));
        accessGoogleData.setAuthCode(accessGoogleInfoList.get(3));
        accessGoogleData.setScope(accessGoogleInfoList.get(4));
        return accessGoogleData;
    }
}
