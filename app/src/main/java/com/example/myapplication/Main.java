package com.example.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Main extends AppCompatActivity {
    Intent intent;
    BackService bs;
    boolean backService = false;
    private BroadcastReceiver screenOffReceiver;
    private IntentFilter screenFilter;


    ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name,IBinder service) {

// 서비스와 연결되었을 때 호출되는 메서드
// 서비스 객체를 전역변수로 저장
            BackService.MyBinder mb = (BackService.MyBinder) service;
            bs = mb.getService(); // 서비스가 제공하는 메소드 호출하여
// 서비스쪽 객체를 전달받을수 있슴
            backService = true;
        }
        public void onServiceDisconnected(ComponentName name) {
// 서비스와 연결이 끊겼을 때 호출되는 메서드
            backService = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    public void onBackPressed(){  // back 키 앱 종료
        super.onBackPressed();
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
            case R.id.sound:
                //SoundPool = MediaPlayer.create(this, R.raw.sound);
                //((MediaPlayer) SoundPool).start();
                break;
            case R.id.backon:
                intent = new Intent(Main.this,BackService.class);
                bindService(intent,conn, Context.BIND_AUTO_CREATE);
                // 서비스 실행
                break;
            case R.id.backoff:
                unbindService(conn);
                // 서비스 종료
                break;
        }
//                Intent intent = new Intent(getApplicationContext(),DB.class);   // 완료 후 다음 위치
//                startActivity(intent);
    }
    IntentFilter intentFilter = new IntentFilter();
    //intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
    //intentFilter.addAction(Intent.ACTION_SCREEN_ON);

    BroadcastReceiver screenOnOff = new BroadcastReceiver()
    {
        public static final String ScreenOff = "android.intent.action.SCREEN_OFF";
        public static final String ScreenOn = "android.intent.action.SCREEN_ON";
        public void onReceive(Context contex, Intent intent)
        {
            if (intent.getAction().equals(ScreenOff))   // 화면 off
            {
                Log.e("MainActivity", "Screen Off");
            }
            else if (intent.getAction().equals(ScreenOn)) // 화면 on
            {
                Log.e("MainActivity", "Screen On");
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //   뒤로가기 키 이벤트
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {  // 볼륨키 up 이벤트

        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) { // 볼륨키 down 이벤트

        }
        return true;
        // return super.onKeyDown(keyCode, event);
    }
}