package com.gse23.fspreng;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class ResultView extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view);
        Bundle get = getIntent().getExtras();

        TextView showDistacne = findViewById(R.id.distanz);
        assert get != null;
        double distance = (double) get.get("distance");
        showDistacne.setText("Distance between your guess an reallity:\n\n"
        + distance + " km");

        TextView show_Link = findViewById(R.id.showLink);
        String showLink = (String) get.get("posLink");
        show_Link.setText(showLink);
        Log.i("positionlink", showLink);
        show_Link.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(showLink));
            startActivity(intent);
        });

        Button go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(v-> finish());
    }

}
