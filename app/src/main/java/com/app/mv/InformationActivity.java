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

    EditText editTextName, editTextSubName;
    RadioGroup radioGroupStudentAndTeacher;
    RadioButton radioButtonStudent, radioButtonTeacher;
    Button buttonOk;

    String sNYou = HelpData.KEY_DONT_BUG, sYou = HelpData.KEY_DONT_BUG;
    boolean student = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        editTextName    = findViewById(R.id.editText_InformationName);
        editTextSubName = findViewById(R.id.editText_InformationAmiChatWithYou);
        radioGroupStudentAndTeacher = findViewById(R.id.radioGroup_studentAndTeacher);
        radioButtonStudent          = findViewById(R.id.radioButton_Student);
        radioButtonTeacher          = findViewById(R.id.radioButton_Teacher);
        buttonOk = findViewById(R.id.button_Sign_up);

        SharedPreferences sharedPreferences   = getSharedPreferences(HelpData.KEY_INFORMATION, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        sNYou   = sharedPreferences.getString(HelpData.KEY_NAME, HelpData.KEY_DONT_BUG);
        sYou    = sharedPreferences.getString(HelpData.KEY_SUB_NAME, HelpData.KEY_DONT_BUG);
        student = sharedPreferences.getBoolean(HelpData.KEY_STUDENT, true);
        if (sNYou.equals(HelpData.KEY_DONT_BUG) || sYou.equals(HelpData.KEY_DONT_BUG)){
            buttonOk.setText(getResources().getString(R.string.ok));
        }
        else {
            editTextName.setText(sNYou);
            editTextSubName.setText(sYou);
            radioButtonTeacher.setChecked(!student);
            radioButtonStudent.setChecked(student);
            buttonOk.setText(getResources().getString(R.string.restartOk));
        }

        radioGroupStudentAndTeacher.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButton_Student:
                        student = true;
                    break;
                    case R.id.radioButton_Teacher:
                        student = false;
                    break;
                }
            }
        });
        sNYou = editTextName.getText().toString();
        sYou  = editTextSubName.getText().toString();
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sNYou.equals(HelpData.KEY_DONT_BUG) || sYou.equals(HelpData.KEY_DONT_BUG) || (!radioButtonStudent.isChecked() && !radioButtonTeacher.isChecked())){
                    Toast.makeText(InformationActivity.this, getResources().getString(R.string.noteInformationError), Toast.LENGTH_LONG).show();
                }
                else {
                    editor.putString(HelpData.KEY_NAME, editTextName.getText().toString());
                    editor.putString(HelpData.KEY_SUB_NAME, editTextSubName.getText().toString());
                    editor.putBoolean(HelpData.KEY_STUDENT,student);
                    editor.commit();
                    Log.d(HelpData.KEY_LOG,"InformationActivity <- ok.onclick: name,sname,student"+sNYou+sYou+student);
                    Toast.makeText(InformationActivity.this, getResources().getString(R.string.noteInformationComplete), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
