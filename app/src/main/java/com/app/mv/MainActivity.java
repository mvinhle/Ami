package com.app.mv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewClickAlway = findViewById(R.id.TextViewClickAlway);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_flash);
        textViewClickAlway.startAnimation(animation);

        ImageButton button = findViewById(R.id.ButtonClickAlway);

        final SharedPreferences sharedPreferences   = getSharedPreferences(HelpData.KEY_INFORMATION, MODE_PRIVATE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString(HelpData.KEY_SUB_NAME,HelpData.KEY_DONT_BUG).equals(HelpData.KEY_DONT_BUG)
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
