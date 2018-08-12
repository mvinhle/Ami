package com.app.mv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Tiểu AA - thông báo");
        Button buttonYes = findViewById(R.id.ButtonYes);

        final SharedPreferences sharedPreferences   = getSharedPreferences(HelpData.KEY_INFORMATION, MODE_PRIVATE);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString(HelpData.KEY_SUB_NAME, HelpData.KEY_DONT_BUG).equals(HelpData.KEY_DONT_BUG)
                        || sharedPreferences.getString(HelpData.KEY_NAME,HelpData.KEY_DONT_BUG).equals(HelpData.KEY_DONT_BUG)){
                    Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, AmiActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button buttonNo = findViewById(R.id.ButtonNo);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
