package com.walton.java.autosyncgooglephotos.processor;

import poisondog.core.Mission;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeAccessGoogleData implements Mission<String> {
    public List<String> execute(String accessGoogleData){
        List<String> accessGoogleInfos = new ArrayList<String>(3);
        for(int i=0;i<accessGoogleData.length();i++){
            String accessGoogleInfo = "";
            for(;accessGoogleData.charAt(i)!='\n';i++)
                accessGoogleInfo += accessGoogleData.charAt(i);
            accessGoogleInfos.add(accessGoogleInfo);
        }
        return accessGoogleInfos;
    }
}
