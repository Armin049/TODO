package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void NewActivity(View view) {
        Button button = findViewById(R.id.button);
        setContentView(R.layout.activity_detail);
    }

    public void cancel(View view) {
        Button button = findViewById(R.id.cancel);
        setContentView(R.layout.activity_main);
    }
}