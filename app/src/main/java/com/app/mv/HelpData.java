package com.app.mv;

import android.util.Log;

public class HelpData {

    public final static String KEY_INFORMATION  = "information";
    public final static String KEY_SHARED_TRUST = "trust";
    public final static String KEY_TRUST       = "istrust";
    public final static String KEY_NAME        = "you";
    public final static String KEY_SUB_NAME    = "nyou";
    public final static String KEY_STUDENT     = "student";
    public final static String KEY_LOG         = "MVLog";
    public final static String KEY_DONT_BUG    = "sanPhamDuocPhatTrienBoiLeMinhVinh";

    public int randomRange(int start, int end){
        int random = (int) (start + Math.random() * (end - start));
        Log.d(HelpData.KEY_LOG, "HelpData <- randomRange random từ: "+start+" -> "+end+" = "+random);
        return random;
    }
    public int randomRange(int range){
        int random = (int) (Math.random() * range);
        Log.d(HelpData.KEY_LOG, "HelpData <- randomRange random từ: 0 -> "+range+" = "+random);
        return random;
    }
    public String removeSubString(String stringDefault, String stringDelete) {
        String s = stringDefault.replace(stringDelete, "");
        Log.d(HelpData.KEY_LOG, "HelpData <- removeSubString delete: " + stringDefault + " <- " + stringDelete + " = " + s);
        return s;
    }
}
