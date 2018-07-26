package com.app.mv;

import android.content.Intent;
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AmiActivity.class);
                startActivity(intent);
            }
        });
    }
}
