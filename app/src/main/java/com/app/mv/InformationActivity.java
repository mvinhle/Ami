package com.app.mv;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class InformationActivity extends AppCompatActivity {

    EditText editTextName;
    RadioGroup radioGroupBoyAndGirl;
    RadioButton radioButtonBoy, radioButtonGirl;
    Button buttonOk;

    String sNYou = "", sYou = "";
    boolean boy = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // ko sài được [T_T]!
        setContentView(R.layout.activity_information);
        this.setFinishOnTouchOutside(false);
        setTitle("Ami đang cần một ít thông tin về bạn");

        editTextName         = findViewById(R.id.editText_InformationName);
        radioGroupBoyAndGirl = findViewById(R.id.radioGroup_BoyAndGirl);
        radioButtonBoy       = findViewById(R.id.radioButton_Boy);
        radioButtonGirl      = findViewById(R.id.radioButton_Girl);
        buttonOk = findViewById(R.id.button_Sign_up);

        SharedPreferences sharedPreferences   = getSharedPreferences(HelpData.KEY_INFORMATION, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        sNYou   = sharedPreferences.getString(HelpData.KEY_NAME, "");
        sYou    = sharedPreferences.getString(HelpData.KEY_SUB_NAME, "");
        boy = sharedPreferences.getBoolean(HelpData.KEY_STUDENT, true);
        if ((sNYou.length() < 1) || (sYou.length() < 1)){
            buttonOk.setText(getResources().getString(R.string.ok));
        }
        else {
            editTextName.setText(sNYou);
            radioButtonGirl.setChecked(!boy);
            radioButtonBoy.setChecked(boy);
            buttonOk.setText(getResources().getString(R.string.restartOk));
        }

        radioGroupBoyAndGirl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButton_Boy:
                        boy = true;
                        sYou = "anh";
                    break;
                    case R.id.radioButton_Girl:
                        boy = false;
                        sYou = "chị";
                    break;
                }
            }
        });
        sNYou = editTextName.getText().toString();
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sNYou = editTextName.getText().toString().trim();
                if ((sNYou.length() < 1) || (!radioButtonBoy.isChecked() && !radioButtonGirl.isChecked())){
                    Toast.makeText(InformationActivity.this, getResources().getString(R.string.noteInformationError), Toast.LENGTH_LONG).show();
                }
                else {
                    editor.putString(HelpData.KEY_NAME, editTextName.getText().toString());
                    editor.putString(HelpData.KEY_SUB_NAME, sYou);
                    editor.putBoolean(HelpData.KEY_STUDENT, boy);
                    editor.commit();
                    Log.d(HelpData.KEY_LOG,"InformationActivity <- ok.onclick: name,sname,boy: "+sYou+" "+sNYou+", boy = "+boy);
                    Toast.makeText(InformationActivity.this, getResources().getString(R.string.noteInformationComplete), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
