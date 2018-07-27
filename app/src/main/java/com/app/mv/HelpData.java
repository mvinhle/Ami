package com.app.mv;

import android.util.Log;

public class HelpData {
    private int bodyDefault;
    private int hairDefault;
    private int eyeDefault;
    private int eyebrowDeafault;
    private int clothesDefault;
    private int glassDefault;
    private int featureDefault;
    private int mouthDefault;

    public void setBodyDefault(int bodyDefault) {
        this.bodyDefault = bodyDefault;
    }
    public void setHairDefault(int hairDefault) {
        this.hairDefault = hairDefault;
    }
    public void setEyeDefault(int eyeDefault) {
        this.eyeDefault = eyeDefault;
    }
    public void setEyebrowDeafault(int eyebrowDeafault) {
        this.eyebrowDeafault = eyebrowDeafault;
    }
    public void setClothesDefault(int clothesDefault) {
        this.clothesDefault = clothesDefault;
    }
    public void setGlassDefault(int glassDefault) {
        this.glassDefault = glassDefault;
    }
    public void setFeatureDefault(int featureDefault) {
        this.featureDefault = featureDefault;
    }
    public void setMouthDefault(int mouthDefault) {
        this.mouthDefault = mouthDefault;
    }

    public int getBodyDefault() {
        return bodyDefault;
    }
    public int getHairDefault() {
        return hairDefault;
    }
    public int getEyeDefault() {
        return eyeDefault;
    }
    public int getEyebrowDeafault() {
        return eyebrowDeafault;
    }
    public int getClothesDefault() {
        return clothesDefault;
    }
    public int getGlassDefault() {
        return glassDefault;
    }
    public int getFeatureDefault() {
        return featureDefault;
    }
    public int getMouthDefault() {
        return mouthDefault;
    }

    public int randomRange(int start, int end){
        int random = (int) (start + Math.random() * (end - start));
        Log.d("MVLog", "HelpData <- randomRange random từ: "+start+" -> "+end+" = "+random);
        return random;
    }
    public int randomRange(int range){
        int random = (int) (Math.random() * range);
        Log.d("MVLog", "HelpData <- randomRange random từ: 0 -> "+range+" = "+random);
        return random;
    }
    public String removeSubString(String stringDefault, String stringDelete) {
        String s = stringDefault.replaceAll(stringDelete, "");
        Log.d("MVLog", "HelpData <- removeSubString delete: " + stringDefault + " <- " + stringDelete + " = " + s);
        return s;
    }
}
