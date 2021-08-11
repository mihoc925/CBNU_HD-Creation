package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Ex extends AppCompatActivity {

    Context context;
    LinearLayout linearContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex);

        init();
    }

    private void init(){
        context = getApplicationContext();
        linearContainer = findViewById(R.id.linearContainer);

        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,0);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);

        for (int i = 0; i < 3; i++) {
            TextView textView = new TextView(context);
            textView.setLayoutParams(params);
            textView.setText(i+"");

            linearLayout.addView(textView);
        }
        linearContainer.addView(linearLayout);

        TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setText("test");
        linearContainer.addView(textView);

        for (int i = 0; i < 3; i++) {
            LinearLayout linearLayout1 = new LinearLayout(context);
            linearLayout1.setLayoutParams(params);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            linearLayout1.setPadding(30, 30, 30, 30);
            linearLayout1.setGravity(Gravity.CENTER);

            ImageButton imageButton = new ImageButton(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams( 150, 150 );
            imageButton.setLayoutParams(layoutParams);
            imageButton.setImageResource(R.drawable.background02);
            imageButton.setBackground(null);
            imageButton.setScaleType(ImageView.ScaleType.FIT_XY);

            TextView textView1 = new TextView(context);
            textView1.setLayoutParams(params);
            textView1.setText("txt"+i);

            linearLayout1.addView(imageButton);
            linearLayout1.addView(textView1);
            linearContainer.addView(linearLayout1);
        }
    }
}
