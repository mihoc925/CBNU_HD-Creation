package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class CloseDialog extends Dialog {

    private Button dial_btn1, dial_btn2;


    public static final String TAG = "CloseDialog";
    GetterSetter getterSetter = new GetterSetter();      // Getter / Setter

    // DB 관련 변수
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    private boolean openDatabase() {
        dbHelper = new DatabaseHelper(MainActivity.mainContext);    // mainContext = this
        db = dbHelper.getWritableDatabase();
        return true;
    }

    public CloseDialog(final Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_close_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dial_btn1 = (Button) findViewById(R.id.dial_btn1);
        dial_btn2 = (Button) findViewById(R.id.dial_btn2);

        openDatabase(); // DB 오픈


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

//    private void aa(){
//        Cursor cursor = db.rawQuery("select * from Manage where dateKind='"+sYear+sMonth+sDay+"' and timeKind='"+timeKind+"'",null); // 날짜,시간 조회
//        Cursor img_cursor = db.rawQuery("select img, foodType from Food where name='"+wcAll_List.get(wcList)+"'",null); // 이름에 맞는 img
//        while (img_cursor.moveToNext()) {
//            imgValue = img_cursor.getString(0);  // 이미지 값
//            foodType = img_cursor.getString(1);  // 재료 값
//        }
//
//        Cursor typeCursor = db.rawQuery("select img, name, min(note1) from Food where foodType='"+foodType+"' and foodKind='"+foodKind+"'",null);
//        typeCursor.moveToFirst();
//        aa= typeCursor.getString(0);
//
//        int tmpCount = cursor.getCount() + 1; // tmp = select of manage count number
//
//        if(tmpCount < 5) {
//            db.execSQL("insert into Manage (dateKind, timeKind, foodKind, img, name) values (?, ?, ?, ?, ?)",
//                    new String[]{sYear + sMonth + sDay, timeKind, ""+tmpCount, imgValue, nameValue});
//
//        }else{
////                    int tmpDiv = tmpCount / 5;
////                    db.execSQL("update Manage set img=?, name=? where dateKind=? and timeKind=? and foodKind=?",
////                            new String[] {imgValue, nameValue, sYear + sMonth + sDay, timeKind, ""+tmpDiv});
//            Toast.makeText(this,"음식 리스트가 가득 찼습니다.", Toast.LENGTH_SHORT).show();
//        }
//    }
}