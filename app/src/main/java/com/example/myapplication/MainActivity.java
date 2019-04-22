package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity  extends AppCompatActivity implements View.OnClickListener{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numlist);

        Button insert = (Button)findViewById(R.id.insert);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.insert:
                Intent intent = new Intent(getApplicationContext(),DB.class);   // 완료 후 다음 위치
                startActivity(intent);
        }
//                Intent intent = new Intent(getApplicationContext(),DB.class);   // 완료 후 다음 위치
//                startActivity(intent);
    }
}
