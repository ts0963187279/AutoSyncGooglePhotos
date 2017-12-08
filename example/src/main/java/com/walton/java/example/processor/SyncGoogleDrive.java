package com.walton.java.example.processor;

import com.google.api.services.drive.Drive;
import com.walton.java.GoogleDriveForJava.model.DownloadFileInfo;
import com.walton.java.GoogleDriveForJava.model.SearchFileInfo;
import com.walton.java.GoogleDriveForJava.processor.DownloadDriveFile;
import com.walton.java.GoogleDriveForJava.processor.GetDownloadFileInfoList;
import com.walton.java.GoogleDriveForJava.processor.GetDriveFilesMap;
import com.walton.java.GoogleDriveForJava.processor.GetFolderList;
import com.walton.java.accessgoogleservice.module.FirebaseMessage;
import com.walton.java.accessgoogleservice.processor.SendFirebaseMessage;
import poisondog.core.Mission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncGoogleDrive implements Mission<Drive>{
    @Override
    public Void execute(Drive driveService){
        GetFolderList getFolderList = new GetFolderList();
        GetDriveFilesMap getDriveFilesMap = new GetDriveFilesMap(driveService);
        List<SearchFileInfo> folderList = getFolderList.execute(driveService);
        Map<String,SearchFileInfo> files = new HashMap<String, SearchFileInfo>();
        for(SearchFileInfo folder : folderList){
            files.putAll(getDriveFilesMap.execute(folder));
        }
        List<DownloadFileInfo> downloadFileInfoList = new GetDownloadFileInfoList().execute(files);
        DownloadDriveFile downloadDriveFile = new DownloadDriveFile(driveService);
        downloadDriveFile.setPath("/var/www/web1/web/myskybox/sata_1/Google/");
        for (DownloadFileInfo downloadFileInfo : downloadFileInfoList) {
            downloadDriveFile.execute(downloadFileInfo);
        }
        return null;
    }
}
