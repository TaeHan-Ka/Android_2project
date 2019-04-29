package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sms  extends AppCompatActivity implements View.OnClickListener {
    Button buttonSend;
    EditText textPhoneNo;
    EditText textSMS;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms);

        buttonSend = (Button) findViewById(R.id.send);
        textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        textSMS = (EditText) findViewById(R.id.editTextSMS);

    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send:
                String phoneNo = textPhoneNo.getText().toString();
                String sms = textSMS.getText().toString();
                try {
                    //전송
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(),Main.class);   // 완료 후 다음 위치
                startActivity(intent);
        }
//                Intent intent = new Intent(getApplicationContext(),DB.class);   // 완료 후 다음 위치
//                startActivity(intent);
    }
}