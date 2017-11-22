package com.walton.java.autosyncgooglephotos.processor;

import poisondog.core.Mission;

public class HaveItemUpdate implements Mission<Integer> {
    private int numberOfItem;
    public HaveItemUpdate(){
        numberOfItem = -1;
    }
    public Boolean execute(Integer newNumberOfItem){
        if(numberOfItem != newNumberOfItem) {
            setNumberOfItem(newNumberOfItem);
            return true;
        }
        return false;
    }
    private void setNumberOfItem(int numberOfItem){
        this.numberOfItem = numberOfItem;
    }
}
