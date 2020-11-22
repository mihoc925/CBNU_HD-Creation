package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    // 기본 변수
    public static final String TAG = "MainActivity";
    public static Context mainContext;
    GetterSetter getterSetter = new GetterSetter();      // Getter / Setter
    // 게임 관련 변수
    ArrayList<String> wcAll_List = new ArrayList<String>();   // 월드컵 리스트
    int wcList, wcRound, wcAllRound = 0;                 // 리스트 / 현 라운드 / 총 라운드
    private TextView wc_tv1;                             // 라운드, 문제수
    private InitialDialog init_Dialog;                   // 커스텀 다이얼로그 - (모드 설정) -> 그냥 선언 가능 new InitialDialog(this) 자신이기 때문
    private CloseDialog close_Dialog;                    // 커스텀 다이얼로그 - (게임 진행)\

    // 음식 관련 변수
    private TextView wc_food1_tv1, wc_food1_tv2, wc_food1_tv3, wc_food1_tv4, wc_food1_tv5; // 1번 음식 설명
    private TextView wc_food2_tv1, wc_food2_tv2, wc_food2_tv3, wc_food2_tv4, wc_food2_tv5; // 2번 음식 설명
    ArrayList<String> wc_food1_arr1 = new ArrayList<String>(); // 2차원 어레이는 너무 복잡
    ArrayList<String> wc_food1_arr2 = new ArrayList<String>();
    ArrayList<String> wc_food1_arr3 = new ArrayList<String>();
    ArrayList<String> wc_food1_arr4 = new ArrayList<String>();
    private ImageButton wc_food1_img; // 1번 음식 이미지
    private ImageButton wc_food2_img; // 2번 음식 이미지

    // [상단메뉴] 날짜/시간/종류 구분 - 관련 변수
    Spinner spTime, spKind;
    String nYear, nMonth, nDay = "";    // 현재 날짜
    String sYear,sMonth,sDay = "";      // 선택 날짜
    String timeKind = "morning";
    int foodKind = 1;

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
        setContentView(R.layout.activity_main);
        // 퍼미션 체크
        permissionSet();
        // DB 오픈
        boolean isOpen = openDatabase();
        if (isOpen) {

        }else{
            Toast.makeText(this,"DB 연결 실패", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"---------------------------------------------DB 연결 실패");
        }

        wc_tv1 = (TextView) findViewById(R.id.wc_tv1);
        // 1번 음식
        wc_food1_img = (ImageButton)findViewById(R.id.wc_food1_img);
        wc_food1_tv1 = (TextView) findViewById(R.id.wc_food1_tv1);
        wc_food1_tv2 = (TextView) findViewById(R.id.wc_food1_tv2);
        wc_food1_tv3 = (TextView) findViewById(R.id.wc_food1_tv3);
        wc_food1_tv4 = (TextView) findViewById(R.id.wc_food1_tv4);
        wc_food1_tv5 = (TextView) findViewById(R.id.wc_food1_tv5);
        // 2번 음식
        wc_food2_img = (ImageButton)findViewById(R.id.wc_food2_img);
        wc_food2_tv1 = (TextView) findViewById(R.id.wc_food2_tv1);
        wc_food2_tv2 = (TextView) findViewById(R.id.wc_food2_tv2);
        wc_food2_tv3 = (TextView) findViewById(R.id.wc_food2_tv3);
        wc_food2_tv4 = (TextView) findViewById(R.id.wc_food2_tv4);
        wc_food2_tv5 = (TextView) findViewById(R.id.wc_food2_tv5);
        mainContext = this;

        customDialog("init");   // 초기 시작 - InitialDialog.java 에서 시작
        initSet(); // 초기설정
    }

    public void initSet(){
        setFoodKind();
        setTimeKind();
        setDate();
    }

    public void runGame(){
        setList(foodKind);
        question();
    }

    private void setFoodKind(){ //음식구분 스피너
        spKind = (Spinner)findViewById(R.id.spKind);
        spKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tmpStr = ""+adapterView.getItemAtPosition(i);
                tmpStr = tmpStr.replaceAll(" ","");
                if(tmpStr.equals("음식1"))
                    foodKind = 1;
                else if(tmpStr.equals("음식2"))
                    foodKind = 2;
                else if(tmpStr.equals("음식3"))
                    foodKind = 3;
                else if(tmpStr.equals("음식4"))
                    foodKind = 4;
                else
                    foodKind = 5;
                runGame();      // 게임 리셋
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void setTimeKind(){ // 시간구분 스피너
        spTime = (Spinner)findViewById(R.id.spTime);
        spTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tmpStr = ""+adapterView.getItemAtPosition(i);
                tmpStr = tmpStr.replaceAll(" ","");
                if(tmpStr.equals("아침"))
                    timeKind = "morning";
                else if(tmpStr.equals("점심"))
                    timeKind = "lunch";
                else
                    timeKind = "dinner";
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
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
        Button btnCalendar = (Button)findViewById(R.id.btnCalendar);
        btnCalendar.setText("날짜선택\n"+sYear+"-"+sMonth+"-"+sDay);
    }

    private void setList(int foodKind){ // 게임 초기 설정
        wcAllRound = init_Dialog.wcAllRound; // 모드 수
        wcRound = wcAllRound;
        Button btnMode = (Button)findViewById(R.id.btnMode);
        btnMode.setText("모드선택\n"+wcAllRound+"강");

        wcAll_List.clear();
        wc_food1_arr1.clear();
        wc_food1_arr2.clear();
        wc_food1_arr3.clear();
        wc_food1_arr4.clear();

//        Cursor cursor = db.rawQuery("select * from Food",null);
        Cursor cursor = db.rawQuery("select * from Food where foodKind='"+foodKind+"'",null);

        int j = 0;
        if(wcAllRound > cursor.getCount()){
            j = cursor.getCount();
            if(cursor.getCount() % 2 != 0)
                j -=1;
            Toast.makeText(this,"데이터가 부족하여 " + cursor.getCount() + "강을 진행합니다.", Toast.LENGTH_SHORT).show();
            btnMode.setText("모드선택\n"+cursor.getCount()+"강");
//            wc_tv1.setText("라운드: " + cursor.getCount() + "/" + cursor.getCount() + "강\n문제: "+ wcList +"번");
//            Log.e(TAG, "수정 필요 -> 1.진행x / 2.한 단계 낮춰서 진행 (8, 16..) 정해진 라운드로");
        }else
            j = wcAllRound;

        // 랜덤 정렬 임시변수
        ArrayList tmpArr0 = new ArrayList();
        ArrayList tmpArr1 = new ArrayList();
        ArrayList tmpArr2 = new ArrayList();
        ArrayList tmpArr3 = new ArrayList();
        ArrayList tmpArr4 = new ArrayList();

        for(int i=1; i<=j; i++) {
            cursor.moveToNext();
            tmpArr0.add("" + cursor.getString(3));
            tmpArr1.add("" + cursor.getString(4));
            tmpArr2.add("" + cursor.getString(5));
            tmpArr3.add("" + cursor.getString(6));
            tmpArr4.add("" + cursor.getString(7));
        }

        // 해쉬셋 - 중복제거 랜덤 (문자)
        HashSet<String> hashNum = new HashSet<>();
        while(hashNum.size() < j){
            int ran = (int)(Math.random() * cursor.getCount()) + 1;
            hashNum.add(""+ran);
        }
        // 배열로 저장
        String[] tmpNum = new String[j+1];
        int n = 0;
        // 해쉬셋에서 데이터 뽑기
        Iterator<String> iterator = hashNum.iterator();
        while (iterator.hasNext()){
            n++;
            tmpNum[n] = iterator.next();
//            Log.e(TAG,"tmpNum:"+tmpNum[n]);
        }
//        Log.e(TAG,"hashNum="+hashNum);

        // 해쉬셋 랜덤으로 데이터 지정
        for(int i=1; i<=j ;i++){
            cursor.moveToPosition( Integer.parseInt(tmpNum[i])-1);
            wcAll_List.add("" + cursor.getString(3));
            wc_food1_arr1.add("" + cursor.getString(4));
            wc_food1_arr2.add("" + cursor.getString(5));
            wc_food1_arr3.add("" + cursor.getString(6));
            wc_food1_arr4.add("" + cursor.getString(7));
        }

        // 초기화
        wcList = 0;
        if(wcList != wcAll_List.size()) {   // 데이터가 없을 경우 예외
            dataSet(); // 텍스트뷰 설정
        }
    }

    private void dataSet(){ // 텍스트뷰 설정
        wc_food1_tv1.setText(wcAll_List.get(wcList));
        wc_food1_tv2.setText(wc_food1_arr1.get(wcList));
        wc_food1_tv3.setText(wc_food1_arr2.get(wcList));
        wc_food1_tv4.setText(wc_food1_arr3.get(wcList));
        wc_food1_tv5.setText(wc_food1_arr4.get(wcList));

        wc_food2_tv1.setText(wcAll_List.get(wcList + 1));
        wc_food2_tv2.setText(wc_food1_arr1.get(wcList + 1));
        wc_food2_tv3.setText(wc_food1_arr2.get(wcList + 1));
        wc_food2_tv4.setText(wc_food1_arr3.get(wcList + 1));
        wc_food2_tv5.setText(wc_food1_arr4.get(wcList + 1));
    }

    // 문제 출제
    private void question() {
        wc_tv1.setText("라운드: " + wcRound + "/" + wcAllRound + "강\n문제: "+ wcList +"번");

        // 데이터가 없을 경우 예외
        if(wcList != wcAll_List.size()) {
            // 텍스트뷰 설정
            dataSet();

            // 이미지 설정
            Cursor c = db.rawQuery("select img from Food where name='"+wcAll_List.get(wcList).toString()+"' and foodKind='"+foodKind+"'", null);
            Cursor c2 = db.rawQuery("select img from Food where name='"+wcAll_List.get(wcList+1).toString()+"' and foodKind='"+foodKind+"'", null);

            String photo = null;
            while (c.moveToNext()){
                photo = c.getString(0);
                if(c.getString(0)==null || c.getString(0).equals("")){
                    Log.e(TAG,"데이터는 있지만, 이미지가 없는 행: " + c.getPosition());
                    photo = "0"; // 대체 이미지
                }
            }
            String photo2 = null;
            while (c2.moveToNext()){
                photo2 = c2.getString(0);
                if(c2.getString(0)==null || c2.getString(0).equals("")){
                    Log.e(TAG,"데이터는 있지만, 이미지가 없는 행: " + c2.getPosition());
                    photo2 = "0"; // 대체 이미지
                }
            }

            Resources res = mainContext.getResources();
            Bitmap bm = BitmapFactory.decodeResource(res, R.drawable.food + Integer.parseInt(photo));
            Bitmap bm2 = BitmapFactory.decodeResource(res, R.drawable.food + Integer.parseInt(photo2));
            wc_food1_img.setImageBitmap(bm);
            wc_food2_img.setImageBitmap(bm2);
        }
    }

    // 진행 상황
    private void progress() {
        // 라운드 버튼 안눌렀을 경우
        if(wcAllRound == 0) {
            customDialog("init");
            return;
        }
        // 수정 [wcList == wcAll_List.size() -> 즉시변경으로]
        if((wcAll_List.size() - wcList) <= 2){
            // 최종 결과
            if(wcAll_List.size() == 1){
//================================================================================================== 작업중_결과 //==================================================================================================
                wcAllRound = 0; // 게임 종료로 설정
                String imgValue = "";
                String nameValue = wcAll_List.get(wcList);


                // 커스텀 다이얼로그 띄우기 추가??
//                megBox("결과는 " + wcAll_List.get(wcList) + "입니다.");
                customDialog("endgame");


                // 검색 -> 없으면 insert // 있으면 update
                Cursor cursor = db.rawQuery("select * from Manage where dateKind='"+sYear+sMonth+sDay+"' and timeKind='"+timeKind+"' and foodKind='"+foodKind+"'",null); // 날짜,시간 조회
                Cursor img_cursor = db.rawQuery("select img from Food where name='"+wcAll_List.get(wcList)+"'",null); // 이름에 맞는 img
                while (img_cursor.moveToNext()) {
                    imgValue = img_cursor.getString(0);  // 이미지 값
                }

                if(cursor.getCount() == 0) {
                    db.execSQL("insert into Manage (dateKind, timeKind, foodKind, img, name) values (?, ?, ?, ?, ?)",
                            new String[]{sYear + sMonth + sDay, timeKind, ""+foodKind, imgValue, nameValue});
                }else{
                    db.execSQL("update Manage set img=?, name=? where dateKind=? and timeKind=? and foodKind=?",
                            new String[] {imgValue, nameValue, sYear + sMonth + sDay, timeKind, ""+foodKind});
                }
//==================================================================================================        //==================================================================================================
            // 중간 라운드
            }else{
                wcRound /= 2;
                wcList = 0;
                megBox("월드컵 " + wcRound + "강을 시작합니다.");
                question();
            }
            // 진행중
        }else {
            wcList += 1;
            question();
        }
    }
    // 메시지박스
    private void megBox(String msgString){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
        alert_confirm.setMessage("" + msgString).setCancelable(false)
                .setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'YES'
                    }
                });
//                취소 필요할때
//                .setNegativeButton("취소",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 'No'
//                        return;
//                    }
//                })
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }
    // 커스텀 다이얼로그
    private void customDialog(String indexString){
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        int width = dm.widthPixels;                                                     //디바이스 화면 너비
        int height = dm.heightPixels;                                                   //디바이스 화면 높이
        // 라운드선택 커스텀
        if(indexString == "init"){
            init_Dialog = new InitialDialog(this);
            WindowManager.LayoutParams wm = init_Dialog.getWindow().getAttributes();    //다이얼로그의 높이 너비 설정하기위해
            wm.copyFrom(init_Dialog.getWindow().getAttributes());                       //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
            wm.width = width / 2;                                                           //화면 너비의 절반
            wm.height = height;                                                         //화면 높이의 절반

            // @@@@@@@@@@@@@@@@@@@@

            wm.width = WindowManager.LayoutParams.MATCH_PARENT;
            wm.height = WindowManager.LayoutParams.MATCH_PARENT;
            init_Dialog.show();
        // 종료 커스텀
        }else if(indexString == "endgame"){
            close_Dialog = new CloseDialog(this);
            WindowManager.LayoutParams wm = close_Dialog.getWindow().getAttributes();
            wm.copyFrom(close_Dialog.getWindow().getAttributes());
            wm.width = WindowManager.LayoutParams.MATCH_PARENT;
            wm.height = WindowManager.LayoutParams.MATCH_PARENT;
            close_Dialog.show();
        }
    }


    private void calendar(){
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

                Button btnCalendar = (Button)findViewById(R.id.btnCalendar);
                btnCalendar.setText("날짜선택\n"+sYear+"-"+sMonth+"-"+sDay);
            }
        },tmpYear, tmpMonth-1, tmpDay);

        datePickerDialog.setMessage("날짜 선택");
        datePickerDialog.show();
    }

    // 클릭 이벤트 그룹
    public void onClick(View v) {
        if (v.getId() == R.id.wc_food1_img) {
            if(wcList != wcAll_List.size() && wcAll_List.size() != 1) {
                wcAll_List.remove(wcList + 1);
                wc_food1_arr1.remove(wcList + 1);
                wc_food1_arr2.remove(wcList + 1);
                wc_food1_arr3.remove(wcList + 1);
                wc_food1_arr4.remove(wcList + 1);
            }
            progress();
        }else if (v.getId() == R.id.wc_food2_img) {
            if(wcList != wcAll_List.size() && wcAll_List.size() != 1) {
                wcAll_List.remove(wcList);
                wc_food1_arr1.remove(wcList);
                wc_food1_arr2.remove(wcList);
                wc_food1_arr3.remove(wcList);
                wc_food1_arr4.remove(wcList);
            }
            progress();
        }else if(v.getId() == R.id.btnMode) {
            customDialog("init");
        }else if(v.getId() == R.id.btnManage) {
            startActivity(new Intent(this, Management.class));
        }else if(v.getId() == R.id.btnCalendar) {
            calendar();
        }
    }

/* 할거 리스트
* 게임 수정!!
* 1. 데이터 매칭 2. 이미지 삽입 / 3. 랜덤으로
*
* xml 꾸미기 >> 이미지 버튼 / 정보 표시
* 식단표 꾸미기
*
* 데이터는 네트워크 -> 파이어베이스로 받기?
* 안되면 찾아보거나 db 직접 연동시키기
* */


// == 사용 안함 == SQLite로 이미지 받기 (갤러리 경로)
//    private void db_test1(){
//        String SQL = "select img from Food where name='"+wcAll_List.get(wcList).toString()+"'";
//        Cursor c = db.rawQuery(SQL,null);
//        String SQL2 = "select img from Food where name='"+wcAll_List.get(wcList+1).toString()+"'";
//        Cursor c2 = db.rawQuery(SQL2,null);
//
//        String photo = null;
//        while (c.moveToNext()){
//            photo = c.getString(0);
//            Log.e(TAG,"get(num): "+ wcAll_List.get(wcList).toString() +" // 커서 포지션: "+ c.getPosition() + " // 커서 이미지 파일: " + c.getString(0));
//            if(c.getString(0)==null || c.getString(0).equals("")){
//                Log.e(TAG,"이미지가 없는 행: " + c.getPosition());
//                // 대체 이미지
//            }
//        }
//        String photo2 = null;
//        while (c2.moveToNext()){
//            photo2 = c2.getString(0);
//            Log.e(TAG,"get(num): "+ wcAll_List.get(wcList+1).toString() +" // 커서 포지션: "+ c2.getPosition() + " // 커서 이미지 파일: " + c2.getString(0));
//            if(c2.getString(0)==null || c2.getString(0).equals("")){
//                Log.e(TAG,"이미지가 없는 행: " + c2.getPosition());
//                // 대체 이미지
//            }
//        }
//        Bitmap bitmap = BitmapFactory.decodeFile(photo);
//        Bitmap bitmap2 = BitmapFactory.decodeFile(photo2);
//        test_imgbtn1 = (ImageButton)findViewById(R.id.test_imgbtn1);
//        test_imgbtn1.setImageBitmap(bitmap);
//        test_imgbtn2 = (ImageButton)findViewById(R.id.test_imgbtn2);
//        test_imgbtn2.setImageBitmap(bitmap2);
//    }

//--- 퍼미션(나중에 수정)
    @Override // 권한에 대한 응답이 있을때 작동하는 함수
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //권한을 허용 했을 경우
        if(requestCode == 1){
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 동의
                    Log.e("MainActivity","권한 허용 : " + permissions[i]);
                }
            }
        }
    }

    public void permissionSet() {
        String temp = "";
        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }
        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }

        if (TextUtils.isEmpty(temp) == false) {
            // 권한 요청
            ActivityCompat.requestPermissions(this, temp.trim().split(" "),1);
        }else {
            // 모두 허용 상태
            Log.e("MainActivity","권한을 모두 허용");
        }
    }
}