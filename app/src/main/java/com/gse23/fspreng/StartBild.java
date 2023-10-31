package com.gse23.fspreng;

//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartBild extends AppCompatActivity{
    public View albCh = null;
    public String albChStr = null;
    boolean albumChoosen = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_bild);
        Button albChoice1 = findViewById(R.id.albumOp1);
        Button albChoice2 = findViewById(R.id.albumOp2);
        Button albChoice3 = findViewById(R.id.albumOp3);
        Button startgame = findViewById(R.id.StartGame);
        albChoice1.setOnClickListener(v ->{
            albCh = v;
            albumChoosen = true;
            albChStr = "Italien";
        });
        albChoice2.setOnClickListener(v ->{
            albCh = v;
            albumChoosen = true;
            albChStr = "Ostwestfalen";
        });
        albChoice3.setOnClickListener(v ->{
            albCh = v;
            albumChoosen = true;
            albChStr = "Urlaub Österreich";
        });
        startgame.setOnClickListener(v -> {
            if (albumChoosen) {
                switch (albChStr) {
                    case "Italien":
                        Log.i("Choosen Album", "albums/Italien");
                        break;
                    case "Ostwestfalen":
                        Log.i("Choosen Album", "albums/Ostwestfalen");
                        break;
                    case "Urlaub Österreich":
                        Log.i("Choosen Album", "albums/Urlaub Österreich");
                        break;
                    default:
                        // Eigentlich redundant da if-bedingung nur erfüllt, wenn album
                        // ausgewählt wurde
                        throw new IllegalStateException("Unexpected value: " + albChStr);
                }
                Log.i("Status", "going to: change to GameView.class");
                Intent intent = new Intent(StartBild.this, GameView.class);
                intent.putExtra("ChoosenAlbum", albChStr);
                Log.i("Choosen Album", "albums/"+albChStr);
                startActivity(intent);
            }
        });
    }
}
