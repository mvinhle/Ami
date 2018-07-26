package com.app.mv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AmiActivity extends AppCompatActivity {

    int trust = 0;
    int index_clothes  = 2;
    int chat = 0;
    String sYou = "ông", sI = "tui", sNI = "Ami";

    HelpData helpData;

    ImageView imageBody, imageHair, imageClothes, imageEye, imageGlass, imageEyebrow, imageMouth, imageFeature;
    TextView textAmiChat;
    Button buttonChat;

    int bodyDefault, hairDefault, eyeDefault, eyebrowDeafault, clothesDefault, glassDefault, featureDefault, mouthDefault;
    int bodyChange,  hairchange,  eyeChange,  eyebrowChange,   clothesChange,  glassChange,  featureChange,   mouthChange;

    final int BODY_DEFAULT    = 1; // body.length;		// HAIR DEFAULT = BODY DEFAULT = 1
    int CLOTHES_DEFAULT = index_clothes; 		// [0, <= 63] = 64?
    final int EYE_TINY        = 2;
    final int EYE_BIG         = EYE_TINY + 4; 		// [indexTinyDefault,5] = 6 (4 is length of eyeBig)
    final int EYE_FUN         = EYE_BIG + 3; 		// [indexTinyBig, 9] = 10
    final int EYEBROW_DEFAULT = 1;
    final int EYEBROW_ANGRY   = EYEBROW_DEFAULT + 1; 	// [EYEBORW_DEFAULT, 1] = 2
    final int EYEBROW_SAD     = EYEBROW_ANGRY + 1;  	// [EYEBROW_ANGRY, 2] = 3
    final int GLASS_DEFAULT   = 4; // glass.length; 	    	// [0, 3] = 4
    final int FEATURE_FUN     = 9;
    final int FEATURE_ANGRY   = FEATURE_FUN + 9; 	    // [FEATURE_FUN, 17] = 18
    final int FEATURE_SAD     = FEATURE_ANGRY + 2; 	    // [FEATURE_FUN, 19] = 20
    final int MOUTH_DEFAULT   = 8;
    final int MOUTH_FUN       = MOUTH_DEFAULT + 31; 	// [MOUTH_DEFAULT, 38] = 39
    final int MOUTH_ANGRY     = MOUTH_FUN + 6; 		    // [MOUTH_FUN, 44] = 45
    final int MOUTH_SAD       = MOUTH_ANGRY + 6; 	    // [MOUTH_ANGRY, 50] = 51
    final int MOUTH_SUDDENT   = MOUTH_SAD + 6; 		    // [MOUTH_SAD, 56] = 57
    final int TEST_WIN        = 100;
    final int TEST_LOST       = -20;
    final int CHAT            = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ami);

        helpData      = new HelpData();
        imageBody     = findViewById(R.id.image_body);
        imageHair     = findViewById(R.id.image_hair);
        imageClothes  = findViewById(R.id.image_clothes);
        imageEye      = findViewById(R.id.image_eye);
        imageEyebrow  = findViewById(R.id.image_eyebrow);
        imageFeature  = findViewById(R.id.image_feature);
        imageGlass    = findViewById(R.id.image_glass);
        imageMouth    = findViewById(R.id.image_mouth);
        textAmiChat   = findViewById(R.id.text_AmiChat);
        setIndexDefault(false);

        buttonChat = findViewById(R.id.button_chat);
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImageAmi("Fun");
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
        //String feature[]    = getResources().getStringArray(R.array.feature); // no feature default
        String mouth[]      = getResources().getStringArray(R.array.mouth);

        int indexBody    = helpData.randomRange(BODY_DEFAULT);
        bodyDefault      = idImageFromName(body[indexBody]);
        hairDefault      = idImageFromName(hair[indexBody]); // index body = index hair
        clothesDefault   = idImageFromName(clothes[helpData.randomRange(CLOTHES_DEFAULT)]);
        eyebrowDeafault  = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_DEFAULT)]);
        mouthDefault     = idImageFromName(mouth[helpData.randomRange(MOUTH_DEFAULT)]);
        featureDefault   = R.drawable.imagenull; //(NO FEATURE DEFAULT)
        if (sglass){
            eyeDefault   = idImageFromName(eye[helpData.randomRange(EYE_TINY)]);
            glassDefault = idImageFromName(glass[helpData.randomRange(GLASS_DEFAULT)]);
        }
        else{
            eyeDefault   = idImageFromName(eye[helpData.randomRange(EYE_TINY, EYE_BIG)]);
            glassDefault = R.drawable.imagenull;
        }
        setImageAmi("Default");
    }

    // set image ami (Fun, Angry, Suddent, Sad, Default)
    private void setImageAmi(String type){
        String eye[]        = getResources().getStringArray(R.array.eye);
        String eyebrow[]    = getResources().getStringArray(R.array.eyebrow);
        String feature[]    = getResources().getStringArray(R.array.feature);
        String mouth[]      = getResources().getStringArray(R.array.mouth);
        switch (type){
            case "Fun":
//                String eye[]        = getResources().getStringArray(R.array.eye);
//                String feature[]    = getResources().getStringArray(R.array.feature);
//                String mouth[]      = getResources().getStringArray(R.array.mouth);
                eyeChange = idImageFromName(eye[helpData.randomRange(EYE_BIG,EYE_FUN)]);
                mouthChange = idImageFromName(mouth[helpData.randomRange(MOUTH_DEFAULT, MOUTH_FUN)]);
                featureChange = idImageFromName(feature[helpData.randomRange(FEATURE_FUN)]);
                imageEye.setImageResource(eyeChange);
                imageMouth.setImageResource(mouthChange);
                imageFeature.setImageResource(featureChange);
                imageEyebrow.setImageResource(eyebrowDeafault);
                break;
            case "Angry":
//                String eyebrow[]    = getResources().getStringArray(R.array.eyebrow);
//                String feature[]    = getResources().getStringArray(R.array.feature);
//                String mouth[]      = getResources().getStringArray(R.array.mouth);
                featureChange = idImageFromName(feature[helpData.randomRange(FEATURE_FUN,FEATURE_ANGRY)]);
                mouthChange   = idImageFromName(mouth[helpData.randomRange(MOUTH_FUN, MOUTH_ANGRY)]);
                eyebrowChange = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_DEFAULT, EYEBROW_ANGRY)]);
                imageFeature.setImageResource(featureChange);
                imageMouth.setImageResource(mouthChange);
                imageEyebrow.setImageResource(eyebrowChange);
                imageEye.setImageResource(eyeDefault);
                break;
            case "Sad":
//                String eyebrow[]    = getResources().getStringArray(R.array.eyebrow);
//                String feature[]    = getResources().getStringArray(R.array.feature);
//                String mouth[]      = getResources().getStringArray(R.array.mouth);
                mouthChange   = idImageFromName(mouth[helpData.randomRange(MOUTH_ANGRY, MOUTH_SAD)]);
                eyebrowChange = idImageFromName(eyebrow[helpData.randomRange(EYEBROW_ANGRY, EYEBROW_SAD)]);
                featureChange = idImageFromName(feature[helpData.randomRange(FEATURE_ANGRY, FEATURE_SAD)]);
                imageMouth.setImageResource(mouthChange);
                imageEyebrow.setImageResource(eyebrowChange);
                imageFeature.setImageResource(featureChange);
                imageEye.setImageResource(eyeDefault);
                break;
            case "Suddent":
//                String mouth[]      = getResources().getStringArray(R.array.mouth);
                mouthChange = idImageFromName(mouth[helpData.randomRange(MOUTH_SAD, MOUTH_SUDDENT)]);
                imageMouth.setImageResource(mouthChange);
                imageEye.setImageResource(eyeDefault);
                break;
            case "Default":
                imageBody.setImageResource(bodyDefault);
                imageHair.setImageResource(hairDefault);
                imageClothes.setImageResource(clothesDefault);
                imageEyebrow.setImageResource(eyebrowDeafault);
                imageEye.setImageResource(eyeDefault);
                imageGlass.setImageResource(glassDefault);
                imageFeature.setImageResource(featureDefault);
                imageMouth.setImageResource(mouthDefault);
                break;
            default:
                Log.d("MVLog", "AmiActivity <- setImageAmi không nhận được trạng thái");
                break;
        }
    }

    public int idImageFromName(String nameImage){
        int idResource = getResources().getIdentifier(nameImage, "drawable", getPackageName());
        Log.d("MVLog", "AmiActivity <- idImageFromName getResources là: "+nameImage+" id là: "+idResource);
        return idResource;
    }

    public void chatWithAmi(String text){
        if(text.contains("[fun]")){setImageAmi("Fun"); text = helpData.removeSubString(text, "[fun]");}
        if(text.contains("[ang]")){setImageAmi("Angry"); text = helpData.removeSubString(text, "[ang]");}
        if(text.contains("[sad]")){setImageAmi("Sad"); text = helpData.removeSubString(text, "[sad]");}
        if(text.contains("[sud]")){setImageAmi("Suddent"); text = helpData.removeSubString(text, "[sud]");}
        text = text.replaceAll("[you]", sYou).replaceAll("[i]", sI).replaceAll("[ni]", sNI);
        textAmiChat.setText(text);
    }
}
