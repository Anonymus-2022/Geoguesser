package com.gse23.fspreng;

//import android.content.Intent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

/**
 * Hier bekommt der Nutzer die Möglichkeit, ein Album von Bildern auszuwählen und danach das Spiel
 * zu starten. Ist kein Album ausgewählt kann das Spiel nicht gestartet werden.
 */
public class StartBild extends AppCompatActivity {
    /**
     * Hier wird gespeichert, ob ein Album ausgewählt wurde.
     */
    boolean albumChoosen = false;
    private String selectedAlbum = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_bild);

        Button startGame = findViewById(R.id.StartGame);
        Button spielAnleitung = findViewById(R.id.game_rules);
        Spinner albumChoice = findViewById(R.id.album_choice);

        String[] inAssets = null;
        try {
            inAssets = getAssets().list("albums");
        } catch (IOException e) {
            Log.i("error", "ijdoösh");
        }

        assert inAssets != null;
        String[] transfer = new String[inAssets.length+1];
        transfer[0] = "Choose an Album!";
        int index = 0;
        for (String x: inAssets) {
            index++;
            transfer[index] = x;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                transfer
        );
        albumChoice.setAdapter(adapter);

        albumChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                albumChoosen = true;
                selectedAlbum = albumChoice.getSelectedItem().toString();
                Log.i("Ausgewähltes Album", selectedAlbum);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("Kein Element gewählt", "Kein Element wurde ausgewählt");
            }
        });

        spielAnleitung.setOnClickListener(v -> {
            Intent intent = new Intent(this, SpielAnleitung.class);
            startActivity(intent);
        });

        startGame.setOnClickListener(v -> {
            if (albumChoosen && !selectedAlbum.equals(transfer[0])) {
                Intent intent = new Intent(this, GameView.class);
                intent.putExtra("AlbumChoice", selectedAlbum);
                startActivity(intent);
            }
        } );


    }
}
