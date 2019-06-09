package com.example.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Main extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    MediaPlayer music;
    public boolean keydown;
    public boolean keyup;
    TextView test;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        test = (TextView)findViewById(R.id.test);

       music = MediaPlayer.create(this, R.raw.siren);
       music.setLooping(true);

        Intent intent = getIntent();

        TextView phone=(TextView) findViewById(R.id.test);
        phone.setText(intent.getStringExtra("phone"));


        Button btnStart = (Button) findViewById(R.id.backon);
        Button btnEnd = (Button) findViewById(R.id.backoff);

        btnStart.setOnClickListener(new View.OnClickListener() {        // Service 는 onCreate 안에서 실행
            public void onClick(View v) {
                // 서비스 시작하기
                Intent intent = new Intent(
                        getApplicationContext(),// Activiey Context
                        BackService.class); // 이동할 서비스 객체
                startService(intent); // 서비스 시작
                test.setText("on");
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 서비스 종료하기
                Intent intent = new Intent(
                        getApplicationContext(),// Activiey Context
                        BackService.class); // 이동할 서비스 객체
                stopService(intent); // 서비스 종료
                test.setText("off");
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                intent = new Intent(getApplicationContext(), Numlist.class);   // 완료 후 다음 위치
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
            case R.id.map:
                intent = new Intent(getApplicationContext(), map.class);
                startActivity(intent);
                break;
            case R.id.sound:         // 사이렌
                if (!music.isPlaying()) {
                    music.start();
                } else {
                    music.stop();
                    Prepare();
                }
                break;
        }
    }

    boolean Prepare() {
        try {
            music.prepare();
        } catch (IllegalStateException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean onKeyDown(int keycode, KeyEvent event){
        switch(keycode)
        {
            case KeyEvent.KEYCODE_VOLUME_DOWN:       //  볼륨 다운 !
                if(keyup) {
                    keydown = true;
                    Intent tellIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010)0000-0000"));
                    startActivity(tellIntent);
                    //basic_text.setText("Down Key");
                    Toast.makeText(this, "KeyDown", Toast.LENGTH_SHORT).show();
                    keyup = false;
                }
                    break;

            case KeyEvent.KEYCODE_VOLUME_UP:        //  볼륨 업 !
                keyup = true;
                Toast.makeText(this, "KeyUp", Toast.LENGTH_SHORT).show();
                //basic_text.setText("Up Key");
                break;
        }
        return true;
    }
}
