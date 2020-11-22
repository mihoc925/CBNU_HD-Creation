package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;
import static com.example.myapplication.MainActivity.mainContext;

public class InitialDialog extends Dialog implements View.OnClickListener{          // 클릭리스너 -> 오버라이드 onClick

    private ImageButton init_btn1, init_btn2, init_btn3, init_btn4, init_btn5, init_btn6;
    private MainActivity mainActivity;

    int wcAllRound = 0;                // 총 라운드 // 메인소속, private 아니면 됨

    // 생성자
    public InitialDialog(Context context){
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);                              // 타이틀바 제거
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));    // 다이얼로그 투명 배경
        setContentView(R.layout.activity_initial_dialog);                           // 메인 설정이 생성자에 적용됨
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 변수
        init_btn1 = (ImageButton) findViewById(R.id.init_btn1);
        init_btn2 = (ImageButton) findViewById(R.id.init_btn2);
        init_btn3 = (ImageButton) findViewById(R.id.init_btn3);
        init_btn4 = (ImageButton) findViewById(R.id.init_btn4);
        init_btn5 = (ImageButton) findViewById(R.id.init_btn5);
        init_btn6 = (ImageButton) findViewById(R.id.init_btn6);

        // implements 이벤트일 경우 필요! (xml_onClick = 필요없음)
        init_btn1.setOnClickListener(this);
        init_btn2.setOnClickListener(this);
        init_btn3.setOnClickListener(this);
        init_btn4.setOnClickListener(this);
        init_btn5.setOnClickListener(this);
        init_btn6.setOnClickListener(this);

//        // 임시 이벤트 코드 (다이얼로그 -> 변수변경 final 제한)
//        init_btn6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });
    }

    public void clickSetting(int n){
        wcAllRound = n;
        Toast.makeText(mainContext,wcAllRound + "강을 시작합니다!", Toast.LENGTH_SHORT).show();
        ((MainActivity)mainContext).runGame();
        dismiss();
    }

    // 클릭 이벤트 그룹
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.init_btn1) {
            clickSetting(8);
        }else if (v.getId() == R.id.init_btn2) {
            clickSetting(16);
        }else if (v.getId() == R.id.init_btn3) {
            clickSetting(32);
        }else if (v.getId() == R.id.init_btn4) {
            clickSetting(64);
        }else if (v.getId() == R.id.init_btn5) {
            clickSetting(128);
        }else if (v.getId() == R.id.init_btn6) {
            clickSetting(256);
        }
    }
}