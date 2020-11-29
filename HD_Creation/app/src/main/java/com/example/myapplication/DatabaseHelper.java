package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "WorldCupGame.db";         // DB 이름
    private static int DATABASE_VERSION = 12;             // 버전
    public static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // db 초기설정
    public void onCreate(SQLiteDatabase db) {

        // DROP TABLE
        try {
            String DROP_Food = "drop table if exists Food";
            String DROP_Manage = "drop table if exists Manage";

            db.execSQL(DROP_Food);
            db.execSQL(DROP_Manage);

        } catch(Exception ex) {
            Log.e(TAG, "\n--- [DB] ---\nDROP TABLE ERROR\n", ex);
        }

        // CREATE TABLE
        // 데이터 타입 의미 없음
        String CREATE_Food = "create table Food ("+
                "_id integer PRIMARY KEY autoincrement, "+
                "foodKind, "+
                "foodType, "+
                "img, "+
                "name, "+
                "note1, "+
                "note2, "+
                "note3, "+
                "note4)";

        String CREATE_Manage = "create table Manage ("+
                "_id integer PRIMARY KEY autoincrement, "+
                "dateKind, "+
                "timeKind, "+
                "foodKind, "+
                "img, "+
                "name)";


        try {
            db.execSQL(CREATE_Food);
            db.execSQL(CREATE_Manage);
        } catch(Exception ex) {
            Log.e(TAG, "\n--- [DB] ---\nCREATE TABLE ERROR\n", ex);
        }
        // 초기 데이터 필요할 경우
        initData(db);
    }

    // 버전 변경
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "\n--- [DB] ----------------------\n\n" +
                "Upgrading database from version " + oldVersion +
                " to " + newVersion +
                "\n\n-------------------------------");
        onCreate(db);
    }

    private void initData(SQLiteDatabase db){   // foodKind 테이블 삭제하고 -> manage에서 관리?
        try {
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('한식', '밥류', '1', '비빔밥(400g)', '586', '89.7', '22', '13.9');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('한식', '면류', '2', '칼국수(350g)', '420', '71.7', '16', '8.7');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('기타', '우유류', '3', '시리얼(40g)', '151', '34.8', '2.4', '0.2');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('양식', '빵류', '4', '햄버거(278g)', '619', '48', '29', '34');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('양식', '고기류', '5', '치킨(100g)', '225', '1', '23', '2.3');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('한식', '국류', '6', '순대국밥(300g)', '360', '42.6', '23', '9.9');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('한식', '국류', '7', '뼈해장국(300g)', '309', '7.9', '38.8', '13.3');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('한식', '밥류', '8', '김밥(300g)', '485', '73.5', '12.2', '15.3');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('기타', '고기류', '9', '닭가슴살(100g)', '109', '0', '22.9', '1.2');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('기타', '면류', '10', '쌀국수(600g)', '320', '55.1', '15.6', '4.2');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('한식', '국류', '11', '떡만둣국(700g)', '624', '110.7', '20', '11.3');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('일식', '밥류', '12', '생선초밥(300g)', '461', '76.4', '25.4', '6');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('한식', '밥류', '13', '오징어덮밥(360g)', '484', '79', '25.9', '7.2');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('한식', '밥류', '14', '볶음밥(350g)', '640', '118.9', '19.5', '9.7');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('한식', '국류', '15', '매운탕(600g)', '211', '15.1', '29.1', '3.9');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('한식', '고기류', '16', '삼겹살(200g)', '933', '0.7', '45.1', '83.4');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('기타', '고기류', '17', '오리고기(200g)', '586', '9.8', '48.1', '39.4');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('중식', '면류', '18', '짜장면(650g)', '824', '134.2', '22.3', '22');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('중식', '면류', '19', '짬뽕(500g)', '764', '133.5', '28.7', '12');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('중식', '고기류', '20', '탕수육(300g)', '591', '64.7', '26.9', '23.9');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('양식', '고기류', '21', '스테이크(100g)', '252', '0', '27.2', '15');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('양식', '빵류', '22', '피자(100g)', '244', '26.3', '10.8', '10.5');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('일식', '고기류', '23', '돈까스(100g)', '284', '12.2', '22.5', '15.3');" );
            db.execSQL( "insert into Food (foodKind, foodType, img, name, note1, note2, note3, note4) values ('일식', '면류', '24', '라면(100g)', '453', '65.5', '9.3', '17.1');" );
        } catch(Exception ex) {
            Log.e(TAG, "INSERT SUPERVISOR ERROR\n", ex);
        }
    }
}