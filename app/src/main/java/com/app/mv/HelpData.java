package com.app.mv;

import android.util.Log;

public class HelpData {
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
