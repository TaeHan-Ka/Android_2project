package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Main extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting:
                intent = new Intent(getApplicationContext(),MainActivity.class);   // 완료 후 다음 위치
                startActivity(intent);
                break;
            case R.id.call:
                // 체크 박스 이용해서 체크된 리스트 값 가져와서 parse 안에다가 변수형태로 넣어서 설정
                Intent tellIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010)0000-0000"));
                startActivity(tellIntent);
                break;
            case R.id.message:
                intent = new Intent(getApplicationContext(),sms.class);   // 완료 후 다음 위치
                startActivity(intent);
                break;
        }
//                Intent intent = new Intent(getApplicationContext(),DB.class);   // 완료 후 다음 위치
//                startActivity(intent);
    }
}
