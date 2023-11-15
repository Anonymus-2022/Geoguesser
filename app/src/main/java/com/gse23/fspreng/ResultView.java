package com.gse23.fspreng;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultView extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view);
        Button go_back = findViewById(R.id.go_back);
        TextView show_Link = findViewById(R.id.showLink);

        Bundle get = getIntent().getExtras();
        String showLink = (String) get.get("posLink");
        show_Link.setText(showLink);
        Log.i("positionlink", showLink);

        show_Link.setOnClickListener(v->{

        });
        go_back.setOnClickListener(v-> finish());
    }

}
