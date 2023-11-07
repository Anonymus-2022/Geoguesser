package com.gse23.fspreng;

import static com.gse23.fspreng.R.id.go_back;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

/**
 * GameView erzeugt eine noch mehr oder weniger nutzlose Aktivity die Momentan überwiegend als
 * Platzhalter für spätere Aktivitäten.
 */
public class GameView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);
        Button spiel_anleitung = findViewById(R.id.game_rules);
        TextView albChoice = findViewById(R.id.albChoice);
        Button goBack = findViewById(R.id.go_back);
        TextView albContent = findViewById(R.id.albContent);

        String alb_Choice = getIntent().getStringExtra("AlbumChoice");
        albChoice.setText(alb_Choice);

        Log.i("AlbumChoice", alb_Choice);
        String pictures = "";
        Images pics = GetAssetContents.get(getApplicationContext());
        // Hier wird darauf verzichtet, zu prüfen ob das Album leer ist, da Solche Alben gar nicht
        // erst erfasst werden
        for (int index = 0; index<pics.length(); index++ ){
            String alb = pics.pos(index).album;
            if (alb.equals(alb_Choice)){
                pictures = pictures + pics.pos(index).picname + "\n";
            }
        }

        albContent.setText(pictures);

        spiel_anleitung.setOnClickListener(v -> {
            Intent intent = new Intent(this, SpielAnleitung.class);
            startActivity(intent);
        });

        goBack.setOnClickListener(v -> {
            Log.i("Status", "going to: change to AlbumChoice");
            Intent intent = new Intent(GameView.this, StartBild.class);
            startActivity(intent);
        });
    }
}
