package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity  extends AppCompatActivity implements View.OnClickListener{
    SQLiteDatabase db;
    dbHelper helper;
    EditText no;
    Cursor cursor;
//    MyCursorAdapter myAdapter;
    final static String dbName = "t3.db";
    final static int dbVersion = 2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numlist);


        helper = new dbHelper(this,dbName,null,dbVersion);
        try {
            db = helper.getWritableDatabase();
            //데이터베이스 객체를 얻기 위하여 getWritableDatabse()를 호출
        } catch (SQLiteException e) {
            db = helper.getReadableDatabase();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.insert:
                String sql;
                no = (EditText)findViewById(R.id.numText);
                String num = no.getText().toString();
                //ContentValues value = new ContentValues();
                db= helper.getWritableDatabase();
                sql = String.format("INSERT INTO t3 VALUES("+num+");");
                db.execSQL(sql);
//                value.put("test", NO);
//                db.insert("test",null,value);

                /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);   // 완료 후 다음 위치
                startActivity(intent);*/
                no.setText(""); // 텍스트 초기화
                break;

            case R.id.delete:
                db= helper.getWritableDatabase();
                sql = "DELETE FROM t3";
                db.execSQL(sql);
                break;

            case R.id.update:
                db = helper.getReadableDatabase();
        }
    }
}

class dbHelper extends SQLiteOpenHelper {

//    private static final String DATABASE_NAME = "test";
//    private static final int DATABASE_VERSION = 1;
    /*
     *먼저 SQLiteOpenHelper클래스를 상속받은 dbHelper클래스가 정의 되어 있다. 데이터베이스 파일 이름은 "mycontacts.db"가되고,
     *데이터베이스 버전은 1로 되어있다. 만약 데이터베이스가 요청되었는데 데이터베이스가 없으면 onCreate()를 호출하여 데이터베이스
     *파일을 생성해준다.
     */
    public dbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE t3 (num TEXT);");
        //result.append("\nt3 테이블 생성 완료.");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS t3");
        onCreate(db);
    }
}
/*
class MyCursorAdapter extends CursorAdapter {
    @SuppressWarnings("deprecation")
    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById( R.id.tv_name );
        TextView tvAge = (TextView) view.findViewById( R.id.tv_age );
        String name = cursor.getString( cursor.getColumnIndex( KEY_NAME ) );
        String age = cursor.getString( cursor.getColumnIndex( KEY_AGE ) );
        Log.d("스트링 확인", name + ", " + age); tvName.setText( name ); tvAge.setText( age );
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from( context );
        View v = inflater.inflate( R.layout.list_item, parent, false ); return v;
    }
}
*/