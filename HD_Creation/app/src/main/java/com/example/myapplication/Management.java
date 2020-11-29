package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static com.example.myapplication.MainActivity.mainContext;

public class Management extends AppCompatActivity{

    public static final String TAG = "Management";

    // 모든 이미지버튼, 텍스트뷰
    ImageButton[] l_i = new ImageButton[16];
    TextView[] l_t = new TextView[16];
    TextView morningTxt, lunchTxt, dinnerTxt;
    // 이미지 주소, 텍스트 값
    int[][] imgVal = new int[3][5];
    String[][] txtVal = new String[3][5];

    // 캘린더 설정 -> getter,setter 지정 시, 메인쪽 많이 수정해야 함
    String nYear, nMonth, nDay = "";    // 현재 날짜
    String sYear,sMonth,sDay = "";      // 선택 날짜
    Button btn_mCal;

    // DB 관련 변수
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    private boolean openDatabase() {
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        // DB 오픈
        boolean isOpen = openDatabase();
        if (isOpen) {
        }else{
            Toast.makeText(this,"DB 연결 실패", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"---------------------------------------------DB 연결 실패");
        }
        btn_mCal = (Button)findViewById(R.id.manageCalendar);

        setDate();
        allView();
        setValue();
        //food_list();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.manageCalendar) {
            mCalendar();
        }
    }

    private void setDate(){ // 현재 시간
        long sysDate = System.currentTimeMillis();
        Date dateF = new Date(sysDate);
        SimpleDateFormat dNow = new SimpleDateFormat("yyyy/MM/dd");
        String formatDate = dNow.format(dateF);

        nYear = sYear = formatDate.substring(0,4);
        nMonth = sMonth = formatDate.substring(5,7);
        nDay = sDay = formatDate.substring(8);

        if(nMonth.length() == 1) {
            nMonth = "0" +nMonth;
            sMonth = "0" +sMonth;
        }
        if(nDay.length() == 1) {
            nDay = "0" +nDay;
            sDay = "0" +sDay;
        }

        btn_mCal.setText(""+sYear+"-"+sMonth+"-"+sDay);
    }
    private void mCalendar(){
        int tmpYear = Integer.parseInt(nYear);
        int tmpMonth = Integer.parseInt(nMonth);
        int tmpDay = Integer.parseInt(nDay);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                sYear = ""+year;
                sMonth = ""+(month+1);
                sDay = ""+dayOfMonth;
                if(sMonth.length() == 1)
                    sMonth = "0"+sMonth;
                if(sDay.length() == 1)
                    sDay = "0"+sDay;
                btn_mCal.setText(""+sYear+"-"+sMonth+"-"+sDay);
                resetValue();
                setValue();
            }
        },tmpYear, tmpMonth-1, tmpDay);

        datePickerDialog.setMessage("날짜 선택");
        datePickerDialog.show();
    }

    private void resetValue(){
        int z = 0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<5; j++){
                z++;
                imgVal[i][j] = 0;
                txtVal[i][j] = "";
                l_i[z].setImageBitmap(null);
                l_t[z].setText("");
            }
        }
        morningTxt.setText("아침");
        lunchTxt.setText("점심");
        dinnerTxt.setText("저녁");
    }

    private void setValue(){
        // 데이터 검색
        int tmpI = 0;
        int y = 0;
        for(int x=0; x<3; x++) {
            String tmpS = "morning";
            if(x==1){
                tmpS = "lunch";
            }else if(x==2){
                tmpS = "dinner";
            }
            Cursor cursor = db.rawQuery("select * from Manage where dateKind='"+ sYear+sMonth+sDay +"' and timeKind='"+tmpS+"' order by foodKind asc",null);
            if(cursor.getCount() == 0) {
                tmpI++;
                if(tmpI == 3)
                    Toast.makeText(this, "해당 날짜에 대한 데이터가 없습니다.", Toast.LENGTH_LONG).show();
            }
            cursor.moveToFirst();
            for(y=0; y<cursor.getCount(); y++) {
                imgVal[x][y] = Integer.parseInt(cursor.getString(4));
                txtVal[x][y] = cursor.getString(5);
                cursor.moveToNext();
            }
        }

        // 데이터 지정
        Resources res = mainContext.getResources();
        Bitmap bm;
        int tmpPhoto;
        int[] Kcal = new int[3]; // 칼로리 계산
        int k = 0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<5; j++){
                k++;
                if(imgVal[i][j] != 0) {
                    tmpPhoto = res.getIdentifier("food"+imgVal[i][j], "drawable", "com.example.myapplication");
                    bm = BitmapFactory.decodeResource(res, tmpPhoto); // 이미지 불러오기 메모리 오류 > 스레드+비트맵 설정해봤지만, 메모리 쪽의 다른 문제
                    l_i[k].setImageBitmap(bm);
                }else{ // 이미지가 없는 부분
//                    bm = BitmapFactory.decodeResource(res, R.drawable.food);
//                    l_i[k].setImageBitmap(bm);
                }
                if(txtVal[i][j] != null) {
                    l_t[k].setText("" + txtVal[i][j]);
                    Kcal[i] += totalKcal(txtVal[i][j]);
                }
            }
        }
        morningTxt.setText("("+Kcal[0]+" Kcal)");
        lunchTxt.setText("("+Kcal[1]+" Kcal)");
        dinnerTxt.setText("("+Kcal[2]+" Kcal)");
    }

    // 시간 분류 텍스트뷰 '총 칼로리' 지정
    private int totalKcal(String txtData){
        int kcal = 0;
        try { // 칼로리 계산/ 저장 => 데이터 갯수만큼 select 반복하기 때문에 테스트 해봐야 함
            Cursor cursor = db.rawQuery("select note1 from Food where name='" + txtData + "'", null);
            cursor.moveToFirst();
            if(cursor.getCount() != 0) {
                kcal = Integer.parseInt(cursor.getString(0));
            }
        }catch (NullPointerException e){
            Log.e(TAG, "[error]--"+e);
        }
        return kcal;
    }

    private void allView(){
        int[] r_img = {R.id.l_i1, R.id.l_i2, R.id.l_i3, R.id.l_i4, R.id.l_i5, R.id.l_i6, R.id.l_i7, R.id.l_i8, R.id.l_i9, R.id.l_i10, R.id.l_i11, R.id.l_i12, R.id.l_i13, R.id.l_i14, R.id.l_i15};
        int[] r_txt = {R.id.l_t1, R.id.l_t2, R.id.l_t3, R.id.l_t4, R.id.l_t5, R.id.l_t6, R.id.l_t7, R.id.l_t8, R.id.l_t9, R.id.l_t10, R.id.l_t11, R.id.l_t12, R.id.l_t13, R.id.l_t14, R.id.l_t15};
        for(int i=1; i<=15; i++) {
            l_i[i] = (ImageButton)findViewById(r_img[i-1]);
            l_t[i] = (TextView)findViewById(r_txt[i-1]);
        }
        morningTxt = (TextView)findViewById(R.id.morningTxt);
        lunchTxt = (TextView)findViewById(R.id.lunchTxt);
        dinnerTxt = (TextView)findViewById(R.id.dinnerTxt);
    }

// 어뎁터 사용시
//    private Cursor Cursor_List() {
//
//        typeName = "2020-11-20";
//
//        String SQL = "select * from Manage where m_date like '%"+typeName+"%'";
//        Cursor c = db.rawQuery(SQL, null);
//        return c;
//    }
//
//    private void food_list(){
//
//        Cursor cursor = Cursor_List();
//        startManagingCursor(cursor);
//
//        String[] columns = new String[]{"m_name1", "m_name2", "m_name3", "m_name4", "m_name5"};
//        int[] to = new int[]{R.id.l_t1, R.id.l_t2, R.id.l_t3, R.id.l_t4, R.id.l_t5};
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.activity_manage_list, cursor, columns, to);
//        manage_list = (ListView)findViewById(R.id.manage_list);
//        manage_list.setAdapter(adapter);
//
//        if (typeNumber == 1) {  // 검색어가 있을경우
//        }
//    }
}