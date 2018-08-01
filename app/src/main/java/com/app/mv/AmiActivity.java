package com.app.mv;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AmiActivity extends AppCompatActivity {

    int trust    = 1500;
    int eyeChange,  eyebrowChange, featureChange,   mouthChange;
    String sYou = "anh", sI = "em", sNI = "Ami", sNYou = "Vinh";
    int result = 0;
    String textResult = "";

    private int bodyDefault;
    private int hairDefault;
    private int eyeDefault;
    private int eyebrowDeafault;
    private int clothesDefault;
    private int glassDefault;
    private int featureDefault;
    private int mouthDefault;

    SharedPreferences sharedPreferencesInformation;
    SharedPreferences sharedPreferencesTrust;
    SharedPreferences.Editor editor;
    HelpData helpData;

    ImageView imageBody, imageHair, imageClothes, imageEye, imageGlass, imageEyebrow, imageMouth, imageFeature;
    TextView textViewAmiChat;
    Button buttonChat, buttonTest, button0, button1, button2, button3;
    LinearLayout linearLayoutHome, linearLayoutClass;

    final int BODY_DEFAULT    = 1; // body.length;		// HAIR DEFAULT = BODY DEFAULT = 1
    final int CLOTHES_DEFAULT = 64;              		// [0, 63]
    final int EYE_TINY        = 2;
    final int EYE_BIG         = EYE_TINY + 4; 	    	// [indexTinyDefault,5] = 6 (4 is number of eyeBig)
    final int EYE_FUN         = EYE_BIG + 3; 	    	// [indexTinyBig, 9] = 10
    final int EYEBROW_DEFAULT = 1;
    final int EYEBROW_ANGRY   = EYEBROW_DEFAULT + 1; 	// [EYEBORW_DEFAULT, 1] = 2
    final int EYEBROW_SAD     = EYEBROW_ANGRY + 1;  	// [EYEBROW_ANGRY, 2] = 3
    final int GLASS_DEFAULT   = 4; // glass.length; 	// [0, 3] = 4
    final int FEATURE_FUN     = 9;
    final int FEATURE_ANGRY   = FEATURE_FUN + 9; 	    // [FEATURE_FUN, 17] = 18
    final int FEATURE_SAD     = FEATURE_ANGRY + 2; 	    // [FEATURE_FUN, 19] = 20
    final int MOUTH_DEFAULT   = 8;
    final int MOUTH_FUN       = MOUTH_DEFAULT + 31; 	// [MOUTH_DEFAULT, 38] = 39
    final int MOUTH_ANGRY     = MOUTH_FUN + 6; 		    // [MOUTH_FUN, 44] = 45
    final int MOUTH_SAD       = MOUTH_ANGRY + 6; 	    // [MOUTH_ANGRY, 50] = 51
    final int MOUTH_SUDDENT   = MOUTH_SAD + 6; 		    // [MOUTH_SAD, 56] = 57
    final int TEST_WIN        = 100;  //x10 up 1 chat
    final int TEST_LOST       = -50;  //x40 down 1 chat
    final int CHAT_ABOUT      = 10;   //chat random [chat, chat + 10) // about 10 chat
    final int CHAT_WIN        = 10;   //x100 up 1 chat
    final int TL_CHAT         = 320;  // tỉ lệ 1 chat = 320 trust
    final int TL_CLOTHES      = 468;  // tỉ lệ 1 clothe = 468 trust
    final int TL_TEST         = 200;  // tỉ lệ 200 trust = 1 câu hỏi.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ami);

        helpData        = new HelpData();
        imageBody       = findViewById(R.id.image_body);
        imageHair       = findViewById(R.id.image_hair);
        imageClothes    = findViewById(R.id.image_clothes);
        imageEye        = findViewById(R.id.image_eye);
        imageEyebrow    = findViewById(R.id.image_eyebrow);
        imageFeature    = findViewById(R.id.image_feature);
        imageGlass      = findViewById(R.id.image_glass);
        imageMouth      = findViewById(R.id.image_mouth);
        textViewAmiChat = findViewById(R.id.text_AmiChat);
        setIndexDefault(false);

        linearLayoutHome  = findViewById(R.id.linearLayout_home);
        linearLayoutClass = findViewById(R.id.linearLayout_class);
        changeHome(true);

        buttonChat = findViewById(R.id.button_chat);
        buttonTest = findViewById(R.id.button_test);
        button0    = findViewById(R.id.button_testA);
        button1    = findViewById(R.id.button_testB);
        button2    = findViewById(R.id.button_testC);
        button3    = findViewById(R.id.button_testD);

        sharedPreferencesInformation = getSharedPreferences(HelpData.KEY_INFORMATION, MODE_PRIVATE);
        sharedPreferencesTrust = getSharedPreferences(HelpData.KEY_SHARED_TRUST, MODE_PRIVATE);
        editor                 = sharedPreferencesTrust.edit();
        sNYou = sharedPreferencesInformation.getString(HelpData.KEY_NAME, ".. quên tên anh mất rồi ^^..");
        sYou  = sharedPreferencesInformation.getString(HelpData.KEY_SUB_NAME, "anh");
        trust = sharedPreferencesTrust.getInt(HelpData.KEY_TRUST, trust);

        final String textAmiChat[] = getResources().getStringArray(R.array.ami_chat);

        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int chat = 0;
                if (trust > TL_CHAT){
                    chat = (int) trust / TL_CHAT;
                }
                setSharedPreferencesTrust(CHAT_WIN);
                if (chat < 1){
                    textViewAmiChat.setText(chatWithAmi(textAmiChat[0]));
                }
                else if (chat < (textAmiChat.length - CHAT_ABOUT)){
                    textViewAmiChat.setText(chatWithAmi(textAmiChat[helpData.randomRange(chat, chat + CHAT_ABOUT)]));
                }
                else {
                    textViewAmiChat.setText(chatWithAmi(textAmiChat[helpData.randomRange((textAmiChat.length - CHAT_ABOUT), textAmiChat.length)]));
                }
                Log.d(HelpData.KEY_LOG, "AmiActivity <- buttonChat.onClick chat: index chat: "+chat+" (length: "+textAmiChat.length+")");
            }
        });
        final String textAmiTest[] = getResources().getStringArray(R.array.ami_test);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeHome(false);
                int test = (int)  trust / TL_TEST + 30; // +30 để trust <= 0 vẫn ra 6 câu hỏi
                if (test > textAmiTest.length) {test = textAmiTest.length;}
                else if (test < 0){test = 30;}
                int ran = helpData.randomRange(test);
                int rand = ran - (ran % 5);
                String textTest = chatWithAmi(textAmiTest[rand]) + "\n";
                Integer nT[] = {2,3,4};
                ArrayList<Integer> NT = new ArrayList<>(Arrays.asList(nT));
                Collections.shuffle(NT);
                String N0 = textAmiTest[rand+1];
                String N1 = textAmiTest[rand+NT.get(0)];
                String N2 = textAmiTest[rand+NT.get(1)];
                String N3 = textAmiTest[rand+NT.get(2)];
                String testY[][]  = {
                        {getResources().getString(R.string.A).concat(": ").concat(N0),
                                getResources().getString(R.string.B).concat(": ").concat(N1),
                                getResources().getString(R.string.C).concat(": ").concat(N2),
                                getResources().getString(R.string.D).concat(": ").concat(N3)},
                        {getResources().getString(R.string.A).concat(": ").concat(N1),
                                getResources().getString(R.string.B).concat(": ").concat(N0),
                                getResources().getString(R.string.C).concat(": ").concat(N2),
                                getResources().getString(R.string.D).concat(": ").concat(N3)},
                        {getResources().getString(R.string.A).concat(": ").concat(N1),
                                getResources().getString(R.string.B).concat(": ").concat(N2),
                                getResources().getString(R.string.C).concat(": ").concat(N0),
                                getResources().getString(R.string.D).concat(": ").concat(N3)},
                        {getResources().getString(R.string.A).concat(": ").concat(N1),
                                getResources().getString(R.string.B).concat(": ").concat(N2),
                                getResources().getString(R.string.C).concat(": ").concat(N3),
                                getResources().getString(R.string.D).concat(": ").concat(N0)}
                };
                result = helpData.randomRange(testY.length);
                for (String s : testY[result]){
                    textTest = textTest.concat(s).concat("\n");
                }
                textResult = testY[result][result];
                Log.d(HelpData.KEY_LOG, "AmiActivity <- buttonTest.onClick question: "+textTest+" result: "+N0);
                textViewAmiChat.setText(textTest);
            }
        });
        final String textAmiWin[]  = getResources().getStringArray(R.array.ami_win);
        final String textAmiLost[] = getResources().getStringArray(R.array.ami_lost);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTest(textAmiWin, textAmiLost, 0, textResult);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTest(textAmiWin, textAmiLost, 1, textResult);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTest(textAmiWin, textAmiLost, 2, textResult);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTest(textAmiWin, textAmiLost, 3, textResult);
            }
        });
    }

    // set index and image ami default
    private void setIndexDefault(boolean sglass){
        String body[]       = getResources().getStringArray(R.array.body);
        String hair[]       = getResources().getStringArray(R.array.hair); 	// intdex array hair = body
        String eye[]        = getResources().getStringArray(R.array.eye);
        String eyebrow[]    = getResources().getStringArray(R.array.eyebrow);
        String clothes[]    = getResources().getStringArray(R.array.clothes);
        String glass[]      = getResources().getStringArray(R.array.glass);
        //String feature[]    = getResources().getStringArray(R.array.feature); // no feature default and feature don't change.
        String mouth[]      = getResources().getStringArray(R.array.mouth);

        int indexBodyAndHair    = helpData.randomRange(BODY_DEFAULT);
        bodyDefault     = idImageFromName(body[indexBodyAndHair]);
        hairDefault     = idImageFromName(hair[indexBodyAndHair]); // index body = index hair
        eyeDefault      = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_DEFAULT)]);
        eyebrowDeafault = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_DEFAULT)]);
        mouthDefault    = idImageFromName(mouth[helpData.randomRange(MOUTH_DEFAULT)]);
        featureDefault  = R.drawable.imagenull; //(NO FEATURE DEFAULT)
        if (sglass){
            eyeDefault   = idImageFromName(eye[helpData.randomRange(EYE_TINY)]);
            glassDefault = idImageFromName(glass[helpData.randomRange(GLASS_DEFAULT)]);
        }
        else{
            eyeDefault   = idImageFromName(eye[helpData.randomRange(EYE_TINY, EYE_BIG)]);
            glassDefault = R.drawable.imagenull;
        }
        int CLOTHES = 3;
        if (trust > (TL_CLOTHES * CLOTHES) ){
            CLOTHES = (int) trust / TL_CLOTHES;
            if (CLOTHES > CLOTHES_DEFAULT){CLOTHES = CLOTHES_DEFAULT;}
        }
        clothesDefault = idImageFromName(clothes[helpData.randomRange(CLOTHES)]);
        setImageAmi("Default");
    }

    // set image ami (Fun, Angry, Suddent, Sad, Default)
    private void setImageAmi(String type){
        String eye[]     = getResources().getStringArray(R.array.eye);
        String eyebrow[] = getResources().getStringArray(R.array.eyebrow);
        String feature[] = getResources().getStringArray(R.array.feature);
        String mouth[]   = getResources().getStringArray(R.array.mouth);
        switch (type){
            case "Fun":
                eyeChange       = idImageFromName(eye[helpData.randomRange(EYE_BIG,EYE_FUN)]);
                mouthChange     = idImageFromName(mouth[helpData.randomRange(MOUTH_DEFAULT, MOUTH_FUN)]);
                featureChange   = idImageFromName(feature[helpData.randomRange(FEATURE_FUN)]);
                imageEye    .setImageResource(eyeChange);
                imageMouth  .setImageResource(mouthChange);
                imageFeature.setImageResource(featureChange);
                imageEyebrow.setImageResource(eyebrowDeafault);
                Log.d(HelpData.KEY_LOG, "AmiActivity <- setImageAmi ami: fun feeling");
                break;
            case "Angry":
                featureChange = idImageFromName(feature[helpData.randomRange(FEATURE_FUN,FEATURE_ANGRY)]);
                mouthChange   = idImageFromName(mouth[helpData.randomRange(MOUTH_FUN, MOUTH_ANGRY)]);
                eyebrowChange = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_DEFAULT, EYEBROW_ANGRY)]);
                imageFeature.setImageResource(featureChange);
                imageMouth  .setImageResource(mouthChange);
                imageEyebrow.setImageResource(eyebrowChange);
                imageEye    .setImageResource(eyeDefault);
                Log.d(HelpData.KEY_LOG, "AmiActivity <- setImageAmi ami: angry feeling");
                break;
            case "Sad":
                mouthChange   = idImageFromName(mouth[helpData.randomRange(MOUTH_ANGRY, MOUTH_SAD)]);
                eyebrowChange = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_ANGRY, EYEBROW_SAD)]);
                featureChange = idImageFromName(feature[helpData.randomRange(FEATURE_ANGRY, FEATURE_SAD)]);
                imageMouth  .setImageResource(mouthChange);
                imageEyebrow.setImageResource(eyebrowChange);
                imageFeature.setImageResource(featureChange);
                imageEye    .setImageResource(eyeDefault);
                Log.d(HelpData.KEY_LOG, "AmiActivity <- setImageAmi ami: sad feeling");
                break;
            case "Suddent":
                mouthChange = idImageFromName(mouth[helpData.randomRange(MOUTH_SAD, MOUTH_SUDDENT)]);
                imageMouth.setImageResource(mouthChange);
                imageEye  .setImageResource(eyeDefault);
                Log.d(HelpData.KEY_LOG, "AmiActivity <- setImageAmi ami: suddent feeling");
                break;
            case "Default":
                imageBody   .setImageResource(bodyDefault);
                imageHair   .setImageResource(hairDefault);
                imageClothes.setImageResource(clothesDefault);
                imageEye    .setImageResource(eyeDefault);
                imageEyebrow.setImageResource(eyebrowDeafault);
                imageGlass  .setImageResource(glassDefault);
                imageFeature.setImageResource(featureDefault);
                imageMouth  .setImageResource(mouthDefault);
                Log.d(HelpData.KEY_LOG, "AmiActivity <- setImageAmi ami: Default feeling");
                break;
            default:
                Log.d(HelpData.KEY_LOG, "AmiActivity <- setImageAmi ami: nothing feeling, don't have type!!!");
                break;
        }
    }

    private int idImageFromName(String nameImage){
        int idResource = getResources().getIdentifier(nameImage, "drawable", getPackageName());
        Log.d(HelpData.KEY_LOG, "AmiActivity <- idImageFromName getResources: "+nameImage+", id: "+idResource);
        return idResource;
    }

    private String chatWithAmi(String text){
        if(text.contains("[fun]")){setImageAmi("Fun");     text = helpData.removeSubString(text, "[fun]");}
        if(text.contains("[ang]")){setImageAmi("Angry");   text = helpData.removeSubString(text, "[ang]");}
        if(text.contains("[sad]")){setImageAmi("Sad");     text = helpData.removeSubString(text, "[sad]");}
        if(text.contains("[sud]")){setImageAmi("Suddent"); text = helpData.removeSubString(text, "[sud]");}
        if(text.contains("[def]")){setImageAmi("Default"); text = helpData.removeSubString(text, "[def]");}
        String s = text .replace("[you]",sYou)
                        .replace("[nyou]", sNYou)
                        .replace("[i]", sI)
                        .replace("[ni]", sNI);
        Log.d(HelpData.KEY_LOG, "AmiActivity <- chatWithAmi: chat: "+text+" <- "+s);
        return s;
    }

    private void changeHome(boolean visibleHome){
        if (visibleHome){
            linearLayoutHome.setVisibility(View.VISIBLE);
            linearLayoutClass.setVisibility(View.INVISIBLE);
        }
        else {
            linearLayoutClass.setVisibility(View.VISIBLE);
            linearLayoutHome.setVisibility(View.INVISIBLE);
        }
        Log.d(HelpData.KEY_LOG, "AmiActivity <- changeHome: home: "+visibleHome+", glass: "+!visibleHome);
    }

    private void clickTest(String[] win,String[] lost, int indexResult, String result){
        changeHome(true);
        if (this.result == indexResult){
            textViewAmiChat.setText(chatWithAmi(win[helpData.randomRange(win.length)]));
            setSharedPreferencesTrust(TEST_WIN);
        }
        else {
            textViewAmiChat.setText(chatWithAmi(lost[helpData.randomRange(lost.length)].replace("[result]", result)));
            setSharedPreferencesTrust(TEST_LOST);
        }
    }

    private void setSharedPreferencesTrust(int i){
        trust += i;
        editor.putInt(HelpData.KEY_TRUST, trust);
        editor.commit();
        Log.d(HelpData.KEY_LOG,"AmiActivity <- setSharedPreferences: "+trust);
    }
}
