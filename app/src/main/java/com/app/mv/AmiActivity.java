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

public class AmiActivity extends AppCompatActivity {

    int changeRoom = 6;
    boolean atHome = true;
    int trust      = 2000;
    int eyeChange,  eyebrowChange, featureChange,   mouthChange;
    String sYou = "bạn", sI = "em", sNI = "Ami", sNYou = "Vinh";
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
    TextView textViewStt;
    Button buttonChat, buttonTest, button0, button1, button2, button3;
    LinearLayout linearLayoutHome, linearLayoutClass, linearLayoutBackground;
    Button buttonTouchHair, buttonTouchFace, buttonTouchBodyUp, buttonTouchBody;

    final int BODY_DEFAULT      = 1; // body.length;		// HAIR DEFAULT = BODY DEFAULT = 1
    final int CLOTHES_DEFAULT   = 64;              		// [0, 63]
    final int EYE_TINY          = 2;
    final int EYE_BIG           = EYE_TINY + 4; 	    	// [indexTinyDefault,5] = 6 (4 is number of eyeBig)
    final int EYE_FUN           = EYE_BIG + 3; 	    	// [indexTinyBig, 9] = 10
    final int EYEBROW_DEFAULT   = 1;
    final int EYEBROW_ANGRY     = EYEBROW_DEFAULT + 1; 	// [EYEBORW_DEFAULT, 1] = 2
    final int EYEBROW_SAD       = EYEBROW_ANGRY + 1;  	// [EYEBROW_ANGRY, 2] = 3
    final int GLASS_DEFAULT     = 4; // glass.length; 	// [0, 3] = 4
    final int FEATURE_FUN       = 9;
    final int FEATURE_ANGRY     = FEATURE_FUN + 9; 	    // [FEATURE_FUN, 17] = 18
    final int FEATURE_SAD       = FEATURE_ANGRY + 2; 	    // [FEATURE_FUN, 19] = 20
    final int MOUTH_DEFAULT     = 8;
    final int MOUTH_FUN         = MOUTH_DEFAULT + 31; 	// [MOUTH_DEFAULT, 38] = 39
    final int MOUTH_ANGRY       = MOUTH_FUN + 6; 		    // [MOUTH_FUN, 44] = 45
    final int MOUTH_SAD         = MOUTH_ANGRY + 6; 	    // [MOUTH_ANGRY, 50] = 51
    final int MOUTH_SUDDENT     = MOUTH_SAD + 6; 		    // [MOUTH_SAD, 56] = 57
    final int TEST_WIN          = 100;  //x10 up 1 chat
    final int TEST_LOST         = -70;  //x40 down 1 chat
    final int CHAT_ABOUT        = 10;   //chat random [chat, chat + 10) // about 10 chat
    final int CHAT_WIN          = 10;   //x100 up 1 chat
    final int TL_CHAT           = 600;  // tỉ lệ 1 chat = 320 trust
    final int TL_CLOTHES        = 700;  // tỉ lệ 1 clothe = 468 trust
    final int TL_TEST           = 300;  // tỉ lệ 200 trust = 1 câu hỏi.
    final int TL_HELLO_WORLD    = 3000;
    final int HELLO_WORLD_ABOUT = 3;
    final int CHANGE_ROOM       = 5;
    final int TL_TOUCH          = 1000;
    final int TOUCH_ABOUT       = 5;

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
        textViewStt = findViewById(R.id.textView_HomeAndClass);

        linearLayoutHome  = findViewById(R.id.linearLayout_home);
        linearLayoutClass = findViewById(R.id.linearLayout_class);
        linearLayoutBackground = findViewById(R.id.linearLayout_BackgroundHomeAndClass);
        changeHome(true);

        amiHelloWorldAndGetFile();
        buttonChat();
        buttonTest();
        buttonTouch();
    }

    private void amiHelloWorldAndGetFile(){
        sharedPreferencesInformation = getSharedPreferences(HelpData.KEY_INFORMATION, MODE_PRIVATE);
        sharedPreferencesTrust = getSharedPreferences(HelpData.KEY_SHARED_TRUST, MODE_PRIVATE);
        editor                 = sharedPreferencesTrust.edit();
        sNYou = sharedPreferencesInformation.getString(HelpData.KEY_NAME, ".. quên tên bạn mất rồi ^^..");
        sYou  = sharedPreferencesInformation.getString(HelpData.KEY_SUB_NAME, "bạn");
        trust = sharedPreferencesTrust.getInt(HelpData.KEY_TRUST, trust);

        final String textAmiHelloWorld[] = getResources().getStringArray(R.array.ami_HelloWorld);
        if (trust < 0){
            textViewAmiChat.setText(chatWithAmi(textAmiHelloWorld[0]));
        }
        else {
            int helloWorld = (int) (trust / TL_HELLO_WORLD);
            if (helloWorld > (textAmiHelloWorld.length - HELLO_WORLD_ABOUT))
            {
                textViewAmiChat.setText(chatWithAmi(textAmiHelloWorld[helpData.randomRange(helloWorld - HELLO_WORLD_ABOUT, textAmiHelloWorld.length)]));
            }
            else if (helloWorld > 1){
                textViewAmiChat.setText(chatWithAmi(textAmiHelloWorld[helpData.randomRange(helloWorld, helloWorld + HELLO_WORLD_ABOUT)]));
            }
            else{
                textViewAmiChat.setText(chatWithAmi(textAmiHelloWorld[1]));
            }
            Log.d(HelpData.KEY_LOG, "Đã chào: index about HelloWorld: "+helloWorld);
        }
    }
    private void buttonChat(){
        buttonChat = findViewById(R.id.button_chat);
        final String textAmiChat[] = getResources().getStringArray(R.array.ami_chat);
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!atHome){
                    if (changeRoom > CHANGE_ROOM){
                        linearLayoutBackground.setBackgroundResource(R.drawable.athome);
                        atHome = true;
                        setIndexDefault(false);
                    }
                    else {
                        changeRoom+=1;
                        textViewStt.setText("Độ lười: "+changeRoom);
                    }
                }
                int chat = (int) trust / TL_CHAT;
                if (chat < 0){
                    chat = 0;
                }
                else if (chat > (textAmiChat.length - CHAT_ABOUT)){
                    chat = textAmiChat.length - CHAT_ABOUT;
                }
                textViewAmiChat.setText(chatWithAmi(textAmiChat[helpData.randomRange(chat, chat + CHAT_ABOUT)]));
                Log.d(HelpData.KEY_LOG, "Click vào chat: index about chat: "+chat);
                setSharedPreferencesTrust(CHAT_WIN);
            }
        });
    }
    private void buttonTest(){
        buttonTest = findViewById(R.id.button_test);
        button0    = findViewById(R.id.button_testA);
        button1    = findViewById(R.id.button_testB);
        button2    = findViewById(R.id.button_testC);
        button3    = findViewById(R.id.button_testD);
        final String textAmiTest[] = getResources().getStringArray(R.array.ami_test);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atHome){
                    if (changeRoom < -CHANGE_ROOM){
                        linearLayoutBackground.setBackgroundResource(R.drawable.atclass);
                        atHome = false;
                        setIndexDefault(true);
                    }
                    else {
                        changeRoom -= 1;
                        textViewStt.setText("Độ lười: "+changeRoom);
                    }
                }
                changeHome(false);
                int test = (int)  trust / TL_TEST;
                if (test > textAmiTest.length) {test = textAmiTest.length;}
                else if (test < 0){test = 30;}
                int ran = helpData.randomRange(test);
                int rand = ran - (ran % 5);
                String textTest = chatWithAmi("<^>" + textAmiTest[rand]).concat("\n\n");
                Integer nT[] = {2,3,4};
                ArrayList<Integer> NT = new ArrayList<>(Arrays.asList(nT));
                Collections.shuffle(NT);
                String N0 = textAmiTest[rand+1];
                String N1 = textAmiTest[rand+NT.get(0)];
                String N2 = textAmiTest[rand+NT.get(1)];
                String N3 = textAmiTest[rand+NT.get(2)];
                String testY[][]  = {
                        {"\t"+getResources().getString(R.string.A).concat(": ").concat(N0),
                                "\t"+getResources().getString(R.string.B).concat(": ").concat(N1),
                                "\t"+getResources().getString(R.string.C).concat(": ").concat(N2),
                                "\t"+getResources().getString(R.string.D).concat(": ").concat(N3)},
                        {"\t"+getResources().getString(R.string.A).concat(": ").concat(N1),
                                "\t"+getResources().getString(R.string.B).concat(": ").concat(N0),
                                "\t"+getResources().getString(R.string.C).concat(": ").concat(N2),
                                "\t"+getResources().getString(R.string.D).concat(": ").concat(N3)},
                        {"\t"+getResources().getString(R.string.A).concat(": ").concat(N1),
                                "\t"+getResources().getString(R.string.B).concat(": ").concat(N2),
                                "\t"+getResources().getString(R.string.C).concat(": ").concat(N0),
                                "\t"+getResources().getString(R.string.D).concat(": ").concat(N3)},
                        {"\t"+getResources().getString(R.string.A).concat(": ").concat(N1),
                                "\t"+getResources().getString(R.string.B).concat(": ").concat(N2),
                                "\t"+getResources().getString(R.string.C).concat(": ").concat(N3),
                                "\t"+getResources().getString(R.string.D).concat(": ").concat(N0)}
                };
                result = helpData.randomRange(testY.length);
                for (int i = 0; i < testY[result].length; i++){
                    if (i < testY[result].length - 1){
                        textTest = textTest.concat(testY[result][i]).concat("\n\n");
                    }
                    else {
                        textTest = textTest.concat(testY[result][i]);
                    }
                }
                textResult = testY[result][result];
                Log.d(HelpData.KEY_LOG, "Click vào test: "+textTest+" result: "+N0);
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
    private void buttonTouch(){
        buttonTouchHair = findViewById(R.id.button_Touch_Hair);
        buttonTouchFace = findViewById(R.id.button_Touch_Face);
        buttonTouchBody = findViewById(R.id.button_Touch_Body);
        buttonTouchBodyUp = findViewById(R.id.button_Touch_Body_Up);

        final String[] textTouchHair = getResources().getStringArray(R.array.stringTouchHair);
        final String[] textTouchFace = getResources().getStringArray(R.array.stringTouchFace);
        final String[] textTouchBodyB = getResources().getStringArray(R.array.stringTouchBodyB);
        final String[] textTouchBodyG = getResources().getStringArray(R.array.stringTouchBodyG);
        final String[] textTouchBodyUp = getResources().getStringArray(R.array.stringTouchBodyUp);

        buttonTouchHair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int touch = (int) trust / TL_TOUCH;
                if (touch < 0){
                    touch = 0;
                }
                else if (touch > (textTouchHair.length - TOUCH_ABOUT)){
                    touch = textTouchHair.length - TOUCH_ABOUT;
                }
                textViewAmiChat.setText(chatWithAmi(textTouchHair[helpData.randomRange(touch,touch+TOUCH_ABOUT)]));
            }
        });
        buttonTouchFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int touch = (int) trust / TL_TOUCH;
                if (touch < 0){
                    touch = 0;
                }
                else if (touch > (textTouchFace.length - TOUCH_ABOUT)){
                    touch = textTouchFace.length - TOUCH_ABOUT;
                }
                textViewAmiChat.setText(chatWithAmi(textTouchFace[helpData.randomRange(touch,touch+TOUCH_ABOUT)]));
            }
        });
        buttonTouchBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int touch = (int) trust / TL_TOUCH;
                if (touch < 0){
                    touch = 0;
                }
                if (sYou.equalsIgnoreCase(HelpData.S_NAME_G)) {
                    if (touch > (textTouchBodyG.length - TOUCH_ABOUT)) {
                        touch = textTouchBodyG.length - TOUCH_ABOUT;
                    }
                    textViewAmiChat.setText(chatWithAmi(textTouchBodyG[helpData.randomRange(touch, touch + TOUCH_ABOUT)]));
                }
                else {
                    if (touch > (textTouchBodyB.length - TOUCH_ABOUT )){
                        touch = textTouchBodyB.length - TOUCH_ABOUT;
                    }
                    textViewAmiChat.setText(chatWithAmi(textTouchBodyB[helpData.randomRange(touch,touch+TOUCH_ABOUT)]));
                }
//                if (sYou.equalsIgnoreCase("anh"))
            }
        });
        buttonTouchBodyUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int touch = (int) trust / TL_TOUCH;
                if (touch < 0){
                    touch = 0;
                }
                else if (touch > (textTouchBodyUp.length - TOUCH_ABOUT)){
                    touch = textTouchBodyUp.length - TOUCH_ABOUT;
                }
                textViewAmiChat.setText(chatWithAmi(textTouchBodyUp[helpData.randomRange(touch,touch+TOUCH_ABOUT)]));
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
                Log.d(HelpData.KEY_LOG, "Ami đổi stt: fun feeling");
                break;
            case "Angry":
                featureChange = idImageFromName(feature[helpData.randomRange(FEATURE_FUN,FEATURE_ANGRY)]);
                mouthChange   = idImageFromName(mouth[helpData.randomRange(MOUTH_FUN, MOUTH_ANGRY)]);
                eyebrowChange = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_DEFAULT, EYEBROW_ANGRY)]);
                imageFeature.setImageResource(featureChange);
                imageMouth  .setImageResource(mouthChange);
                imageEyebrow.setImageResource(eyebrowChange);
                imageEye    .setImageResource(eyeDefault);
                Log.d(HelpData.KEY_LOG, "Ami đổi stt: angry feeling");
                break;
            case "Sad":
                mouthChange   = idImageFromName(mouth[helpData.randomRange(MOUTH_ANGRY, MOUTH_SAD)]);
                eyebrowChange = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_ANGRY, EYEBROW_SAD)]);
                featureChange = idImageFromName(feature[helpData.randomRange(FEATURE_ANGRY, FEATURE_SAD)]);
                imageMouth  .setImageResource(mouthChange);
                imageEyebrow.setImageResource(eyebrowChange);
                imageFeature.setImageResource(featureChange);
                imageEye    .setImageResource(eyeDefault);
                Log.d(HelpData.KEY_LOG, "Ami đổi stt: sad feeling");
                break;
            case "Suddent":
                mouthChange = idImageFromName(mouth[helpData.randomRange(MOUTH_SAD, MOUTH_SUDDENT)]);
                imageMouth.setImageResource(mouthChange);
                imageEye  .setImageResource(eyeDefault);
                Log.d(HelpData.KEY_LOG, "Ami đổi stt: suddent feeling");
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
                Log.d(HelpData.KEY_LOG, "Ami đổi stt: Default feeling");
                break;
            default:
                Log.d(HelpData.KEY_LOG, "Ami Không nhận được trạng thái nhưng vẫn gửi vào hàm setAmiImage !!");
                break;
        }
    }

    private int idImageFromName(String nameImage){
        int idResource = getResources().getIdentifier(nameImage, "drawable", getPackageName());
        Log.d(HelpData.KEY_LOG, "Chuyển tấm ảnh: "+nameImage+", thành id: "+idResource);
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
        Log.d(HelpData.KEY_LOG,"AmiActivity <- setSharedPreferencesTrust: "+trust);
    }
}
