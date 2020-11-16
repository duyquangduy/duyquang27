package com.example.testkeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edttest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edttest = findViewById(R.id.edttest);
//        edttest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AndroidBug5497Workaround.assistActivity(MainActivity.this);
//            }
//        });
        AndroidBug5497Workaround.assistActivity(MainActivity.this);
    }
}