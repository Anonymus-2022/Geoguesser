package com.gse23.fspreng;


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
        Button spielAnleitung = findViewById(R.id.game_rules);
        TextView albChoice = findViewById(R.id.albChoice);
        Button goBack = findViewById(R.id.go_back);
        TextView albContent = findViewById(R.id.albContent);

        String choosenAlbum = "AlbumChoice";
        String enter = "\n";
        String albumChoice = getIntent().getStringExtra(choosenAlbum);
        albChoice.setText(albumChoice);

        /*
        Da bilder, die über keine koordinaten verfügen werden gar nicht erst eingespeichert.
        daher ist die geforderte fehlerbehandlung redundant
         */
        assert albumChoice != null;
        Log.i(choosenAlbum, albumChoice);
        StringBuilder pictures = new StringBuilder();
        Images pics = GetAssetContents.get(getApplicationContext());
        // Hier wird darauf verzichtet, zu prüfen ob das Album leer ist, da Solche Alben gar nicht
        // erst erfasst werden
        for (int index = 0; index < pics.length(); index++) {
            Images.ImageInfo pic = pics.pos(index);
            String alb = pic.album;
            if (alb.equals(albumChoice)){
                pictures.append(pic.picname).append(enter).append(pic.imageDescription).
                        append(enter).append(pic.latitude).append(enter).append(pic.longitude).
                        append(enter).append(enter);
            }
        }

        albContent.setText(pictures.toString());

        spielAnleitung.setOnClickListener(v -> {
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
