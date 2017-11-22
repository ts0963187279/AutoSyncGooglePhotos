package com.walton.java.autosyncgooglephotos.processor;

import com.walton.java.GoogleDriveForJava.model.SearchFileInfo;
import poisondog.core.Mission;

import java.util.Map;

public class GetNumberOfFiles implements Mission<Map<String,SearchFileInfo>> {
    public Integer execute(Map<String, SearchFileInfo> searchFileInfoMap){
        return searchFileInfoMap.size();
    }
}
