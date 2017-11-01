package com.walton.java.autosyncgooglephotos.processor;

import poisondog.core.Mission;

public class HavePhotosUpdate implements Mission<Integer> {
    private int numberOfPhotos;
    public HavePhotosUpdate(){
        numberOfPhotos = -1;
    }
    public void setNumberOfPhotos(int numberOfPhotos){
        this.numberOfPhotos = numberOfPhotos;
    }
    public Boolean execute(Integer newNumberOfPhotos){
        if(numberOfPhotos != newNumberOfPhotos)
            return true;
        return false;
    }
}
