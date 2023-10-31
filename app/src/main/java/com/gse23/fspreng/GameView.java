package com.gse23.fspreng;

import static com.gse23.fspreng.R.id.go_back;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class GameView extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState );
        setContentView(R.layout.game_view);
        TextView albChoice = findViewById(R.id.albChoice);
        String alb_Choice = getIntent().getStringExtra("ChoosenAlbum");
        albChoice.setText(alb_Choice);
        Button goBack = findViewById(go_back);
        goBack.setOnClickListener(v ->{
            Log.i("Status", "going to: change to AlbumChoice");
            Intent intent = new Intent(GameView.this , StartBild.class);
            startActivity(intent);
        });
    }

}
