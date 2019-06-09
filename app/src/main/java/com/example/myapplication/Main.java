package com.example.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

public class Main extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    MediaPlayer music;
    public boolean keydown;
    public boolean keyup;
    TextView test;

    public String pp = "";
    public static Context context;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        test = (TextView)findViewById(R.id.test);
        context = this;
        music = MediaPlayer.create(this, R.raw.siren);
        music.setLooping(true);

        Intent intent = getIntent();

        TextView phone=(TextView) findViewById(R.id.test);
        pp = intent.getStringExtra("phone");
        if(pp != null ) {
            phone.setText("0" + pp);
        }
        Button btnStart = (Button) findViewById(R.id.backon);
        Button btnEnd = (Button) findViewById(R.id.backoff);

        btnStart.setOnClickListener(new View.OnClickListener() {        // Service 는 onCreate 안에서 실행
            public void onClick(View v) {
                // 서비스 시작하기
                Intent intent = new Intent(
                        getApplicationContext(),// Activiey Context
                        BackService.class); // 이동할 서비스 객체
                startService(intent); // 서비스 시작
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 서비스 종료하기
                Intent intent = new Intent(
                        getApplicationContext(),// Activiey Context
                        BackService.class); // 이동할 서비스 객체
                stopService(intent); // 서비스 종료
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
                // 체크된 리스트 값 가져와서 parse 안에다가 변수형태로 넣어서 설정
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:0"+pp));
                startActivity(intent);
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

    public boolean onKeyDown(int keycode, KeyEvent event) {
        switch (keycode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:       //   업 > 다운 실행
                keydown = true;
                if (keyup) {
                    if (!music.isPlaying()) {
                        music.start();
                    } else {
                        music.stop();
                        Prepare();
                    }
//                    String smsBody = "Message from the API";
//                    // Get the default instance of SmsManager
//                    SmsManager smsManager = SmsManager.getDefault();
//                    // Send a text based SMS
//                    smsManager.sendTextMessage("0"+pp, null, smsBody, null, null);       왜 문자 퍼미션을 풀어도 안될까 ?

                    Toast.makeText(this, "KeyDown", Toast.LENGTH_SHORT).show();
                    keyup = false;
                    keydown = false;
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:        //  다운 > 업 실행
                keyup = true;
                if (keydown) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        /** * 사용자 단말기의 권한 중 "전화걸기" 권한이 허용되어 있는지 확인한다. * Android는 C언어 기반으로 만들어졌기 때문에 Boolean 타입보다 Int 타입을 사용한다. */
                        int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE);
                        /** * 패키지는 안드로이드 어플리케이션의 아이디이다. * 현재 어플리케이션이 CALL_PHONE에 대해 거부되어있는지 확인한다. */
                        if (permissionResult == PackageManager.PERMISSION_DENIED) {
                            /** * 사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다. * 거부한적이 있으면 True를 리턴하고 * 거부한적이 없으면 False를 리턴한다. */
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(Main.this);
                                dialog.setTitle("권한이 필요합니다.")
                                        .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속 하시겠습니까?")
                                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                /** * 새로운 인스턴스(onClickListener)를 생성했기 때문에 * 버전체크를 다시 해준다. */
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    // CALL_PHONE 권한을 Android OS에 요청한다.
                                                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                                }
                                            }
                                        })
                                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(Main.this, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .create()
                                        .show();
                            } // 최초로 권한을 요청할 때
                            else {
                                // CALL_PHONE 권한을 Android OS에 요청한다.
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                            }
                        } // CALL_PHONE의 권한이 있을 때
                        else {
                            // 즉시 실행
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0"+pp));
                            startActivity(intent);
                        }
                    } // 마시멜로우 미만의 버전일 때
                    else {
                        // 즉시 실행
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0"+pp));
                        startActivity(intent);
                    };
                    keyup = false;
                    keydown = false;
                }
                break;
        }
        return true;
    }
}
