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
    public final static String KEY_DONT_BUG    = "sanPhamDuocPhatTrienBoiLeMinhVinhSinhNhatCN12082018";

    public final static String S_NAME_B        = "anh";
    public final static String S_NAME_G        = "chị";

    public int randomRange(int start, int end){
        int random = (int) (start + Math.random() * (end - start));
        Log.d(HelpData.KEY_LOG, "random lớn từ: "+start+" đến "+end+" kết quả là "+random);
        return random;
    }
    public int randomRange(int range){
        int random = (int) (Math.random() * range);
        Log.d(HelpData.KEY_LOG, "random nhỏ từ: 0 đến "+range+" kết quả là "+random);
        return random;
    }
    public String removeSubString(String stringDefault, String stringDelete) {
        String s = stringDefault.replace(stringDelete, "");
        Log.d(HelpData.KEY_LOG, "xóa một đoạn: từ = " + stringDefault + " xóa bỏ = " + stringDelete + " kết quả là = " + s);
        return s;
    }
}
