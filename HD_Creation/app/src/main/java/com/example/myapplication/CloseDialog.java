package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CloseDialog extends Dialog {

    private ImageView dial_img;
    private Button dial_btn1, dial_btn2;

    public CloseDialog(final Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_close_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dial_img = (ImageView) findViewById(R.id.dial_img);
        dial_btn1 = (Button) findViewById(R.id.dial_btn1);
        dial_btn2 = (Button) findViewById(R.id.dial_btn2);

        dial_btn1.setOnClickListener(new View.OnClickListener() {                   // 다이얼로그 종료
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        dial_btn2.setOnClickListener(new View.OnClickListener() {                   // 앱 종료
            @Override
            public void onClick(View view) {
                dismiss();
//                ((MainActivity)context).finish();               // 다이얼로그 제한 -> 메인 액티비티 직접 참조
            }
        });
    }
}