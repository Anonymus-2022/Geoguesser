package com.gse23.fspreng;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ResultView extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view);

        Button go_back = findViewById(R.id.go_back);


        go_back.setOnClickListener(v-> finish());
    }

}
