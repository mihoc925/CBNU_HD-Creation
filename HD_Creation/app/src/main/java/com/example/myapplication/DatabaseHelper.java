package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "WorldCupGame.db";         // DB 이름
    private static int DATABASE_VERSION = 8;             // 버전
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
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '1', '비빔밥', '637', '84.8', '30.9', '19.4');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '2', '칼국수', '540', '104.3', '23.1', '3.5');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '3', '시리얼', '354', '85', '5', '3.7');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '4', '햄버거', '619', '48', '29', '34');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '5', '치킨', '2880', '137.2', '164.3', '180');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '6', '순대국밥', '673', '93.5', '28.6', '20.6');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '7', '뼈해장국', '203', '8.5', '25.6', '7.8');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '8', '김밥', '322', '46', '11.1', '10.5');" );

            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '6', '임시1', '322', '46', '11.1', '10.5');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '5', '임시2', '322', '46', '11.1', '10.5');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '4', '임시3', '322', '46', '11.1', '10.5');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '3', '임시4', '322', '46', '11.1', '10.5');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '2', '임시5', '322', '46', '11.1', '10.5');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('1', '1', '임시6', '322', '46', '11.1', '10.5');" );

            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('2', '0', '임시1', '637', '84.8', '30.9', '19.4');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('2', '0', '임시2', '637', '84.8', '30.9', '19.4');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('2', '0', '임시3', '637', '84.8', '30.9', '19.4');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('2', '0', '임시4', '637', '84.8', '30.9', '19.4');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('2', '0', '임시5', '637', '84.8', '30.9', '19.4');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('2', '0', '임시6', '637', '84.8', '30.9', '19.4');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('2', '0', '임시7', '637', '84.8', '30.9', '19.4');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('2', '0', '임시8', '637', '84.8', '30.9', '19.4');" );

            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('3', '1', '비빔밥', '637', '84.8', '30.9', '19.4');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('3', '2', '칼국수', '540', '104.3', '23.1', '3.5');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('3', '3', '시리얼', '354', '85', '5', '3.7');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('3', '4', '햄버거', '619', '48', '29', '34');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('3', '5', '치킨', '2880', '137.2', '164.3', '180');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('3', '6', '순대국밥', '673', '93.5', '28.6', '20.6');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('3', '7', '뼈해장국', '203', '8.5', '25.6', '7.8');" );
            db.execSQL( "insert into Food (foodKind, img, name, note1, note2, note3, note4) values ('3', '8', '김밥', '322', '46', '11.1', '10.5');" );
        } catch(Exception ex) {
            Log.e(TAG, "INSERT SUPERVISOR ERROR\n", ex);
        }
    }
}