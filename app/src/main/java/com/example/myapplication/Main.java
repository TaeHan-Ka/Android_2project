package com.example.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Main extends AppCompatActivity {
    Intent intent;
    BackService ms; // 서비스 객체
    boolean isService = false; // 서비스 중인 확인용
        ServiceConnection conn = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder service) {
                // 서비스와 연결되었을 때 호출되는 메서드
                // 서비스 객체를 전역변수로 저장
                BackService.MyBinder mb = (BackService.MyBinder) service;
                ms = mb.getService(); // 서비스가 제공하는 메소드 호출하여
                // 서비스쪽 객체를 전달받을수 있슴
                isService = true;
            }
            public void onServiceDisconnected(ComponentName name) {
                // 서비스와 연결이 끊겼을 때 호출되는 메서드
                isService = false;
            }
        };
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.setting:
                    intent = new Intent(getApplicationContext(), MainActivity.class);   // 완료 후 다음 위치
                    startActivity(intent);
                    break;
                case R.id.call:
                    // 체크 박스 이용해서 체크된 리스트 값 가져와서 parse 안에다가 변수형태로 넣어서 설정
                    Intent tellIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010)0000-0000"));
                    startActivity(tellIntent);
                    break;
                case R.id.message:
                    intent = new Intent(getApplicationContext(), sms.class);   // 완료 후 다음 위치
                    startActivity(intent);
                    break;
                case R.id.sound:
                    //SoundPool = MediaPlayer.create(this, R.raw.sound);
                    //((MediaPlayer) SoundPool).start();
                    break;
                case R.id.backoff:
                    Intent intent = new Intent(Main.this, BackService.class); // 다음넘어갈 컴퍼넌트
                    bindService(intent, // intent 객체
                            conn, // 서비스와 연결에 대한 정의
                            Context.BIND_AUTO_CREATE);
                    break;
                case R.id.backon:
                    unbindService(conn);
                    break;
            }
//                Intent intent = new Intent(getApplicationContext(),DB.class);   // 완료 후 다음 위치
//                startActivity(intent);
        }
}

