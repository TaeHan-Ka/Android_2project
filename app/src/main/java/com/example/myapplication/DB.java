package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DB extends Activity {
    EditText id, passwd, title, no;
    long mNow;
    Date mDate;
    SQLiteDatabase db;
    dbHelper helper;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new dbHelper(this);
        try {
            db = helper.getWritableDatabase();
            //데이터베이스 객체를 얻기 위하여 getWritableDatabse()를 호출
        } catch (SQLiteException e) {
            db = helper.getReadableDatabase();
        }

        Button in = (Button)findViewById(R.id.in2);
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                no = (EditText)findViewById(R.id.note2);

                String NO = no.getText().toString();
;

                ContentValues value = new ContentValues();
                value.put("note", NO);

                db.insert("note",null,value);
                //db.execSQL("INSERT INTO Tnote VALUES(null,'"+ID+"','"+PWD+"','"+TITLE+"','"+NO+"','"+DATE+"');");
//                try {
//
//                    if (id.length() == 0) {
//                        Toast.makeText(DB.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
//                        id.requestFocus();
//                        return;
//                    }
//
//                    if (passwd.length() == 0) {
//                        Toast.makeText(DB.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
//                        passwd.requestFocus();
//                        return;
//                    }
//                    if (title.length() == 0) {
//                        Toast.makeText(DB.this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                        title.requestFocus();
//                        return;
//                    }
//                } catch (Exception e){
//                    e.printStackTrace();
//                }

                //테이블의 모든 레코드를 커서객체로 가져온다.
                // Cursor cursor = db.rawQuery("select * from Tnote",null);
                //startManagingCursor(cursor) 문장은 액티비티가 커서 객체를 관리하도록 한다.
                //즉 액티비티의 생액주기와 커서의 생애주기를 일치시키는것이다.
                //void android.app.Activity.startManagingCursor(Cursor c)
                //startManagingCursor(cursor);

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);   // 완료 후 다음 위치
                startActivity(intent);
            }
        });
    }
}
class dbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "note";
    private static final int DATABASE_VERSION = 1;

    /*
     *먼저 SQLiteOpenHelper클래스를 상속받은 dbHelper클래스가 정의 되어 있다. 데이터베이스 파일 이름은 "mycontacts.db"가되고,
     *데이터베이스 버전은 1로 되어있다. 만약 데이터베이스가 요청되었는데 데이터베이스가 없으면 onCreate()를 호출하여 데이터베이스
     *파일을 생성해준다.
     */
    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE note (_id INTEGER PRIMARY KEY AUTOINCREMENT, note TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS note");
        onCreate(db);
    }
}
