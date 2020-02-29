package com.elgindy.expandablelistviewlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ItemClick extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_click);
        TextView textView = findViewById(R.id.topic_title);
        Intent intent = getIntent();
        String title = intent.getStringExtra("subItem");
        Log.d("TAG", "onCreate: " + title);
        textView.setText(title);
    }
}
