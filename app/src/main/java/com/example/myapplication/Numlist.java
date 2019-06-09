package com.example.myapplication;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class Numlist  extends AppCompatActivity {

    EditText phone;
    EditText name;
    TextView cphone;
    private ArrayList<HashMap<String,String>> Data = new ArrayList<HashMap<String, String>>();
    private HashMap<String,String> Data1 = new HashMap<>();
    private HashMap<String,String> Data2 = new HashMap<>();

    private ArrayList<Phone> listitem = new ArrayList<Phone>();

    ListView list;
    ListAdapter adapter;
    ArrayList<HashMap<String, String>> personList;

    private final String DBname = "Test";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numlist);
        name = (EditText) findViewById(R.id.note);
        phone = (EditText) findViewById(R.id.note2);

        list = (ListView) findViewById(R.id.list);
        personList = new ArrayList<HashMap<String,String>>();

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), DBname, null, 1);

        listView();
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                //클릭한 아이템의 문자열을 가져옴
//                listitem.set() = (String)adapterView.getItemAtPosition(0);
//              //  String vo = (String)adapterView.getAdapter().getItem(2);
//
//
//                //텍스트뷰에 출력
//                cphone.setText(selected_item);
//            }
//        });
        FriendsAdapter adapter = new FriendsAdapter(this, R.layout.list_item, listitem);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent( getApplicationContext(), Main.class);
                intent.putExtra("phone", listitem.get(position).getPhone());
                startActivity(intent);
            }
        });


        Button insert = (Button) findViewById(R.id.insert);   // 데이터 넣기
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = name.getText().toString();
                String price = phone.getText().toString();

                dbHelper.insert(item, price);
                listView();
                name.setText("");
                phone.setText("");
            }
        });

        Button delete = (Button) findViewById(R.id.delete);   // 삭제
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = name.getText().toString();
                dbHelper.delete(item);
                listView();
            }
        });


        // DB에 있는 데이터 수정
        Button update = (Button) findViewById(R.id.update);     // 고치기
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = name.getText().toString();
                String price = phone.getText().toString();
                dbHelper.update(item, price);
                listView();
            }
        });
    }

    public void listView(){
        try {
            SQLiteDatabase dbHelper1 = this.openOrCreateDatabase(DBname, 0, null);
            Cursor c = ((SQLiteDatabase) dbHelper1).rawQuery("SELECT * FROM MONEYBOOK", null);
            personList.clear();
            if (c != null) {

                if (c.moveToFirst()) {
                    do {
                        String n = c.getString(c.getColumnIndex("name"));
                        String p = c.getString(c.getColumnIndex("phone"));

                        HashMap<String, String> persons = new HashMap<String, String>();

                        persons.put("name", n);
                        persons.put("phone", p);
                        Phone p1 = new Phone();
                        p1.setPhone(p);
                        listitem.add(p1);

                        personList.add(persons);

                    } while (c.moveToNext());
                }
            }
            dbHelper1.close();
            adapter = new SimpleAdapter(
                    this, personList, R.layout.list_item,
                    new String[]{"name","phone"},
                    new int[]{ R.id.name, R.id.phone}
            );
            list.setAdapter(adapter);
        }catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("",  se.getMessage());
        }
    }
}
