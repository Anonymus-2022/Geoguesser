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
public class StartBild extends AppCompatActivity{
    boolean albumChoosen = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_bild);
        Button start_game = findViewById(R.id.StartGame);
        Spinner album_choice = findViewById(R.id.album_choice);

        String[] inAssets = new String[0];
        try {
            inAssets = getAssets().list("albums");
        } catch (IOException e) {
            Log.i("error", "ijdoösh");
        }

        View albumUsage = findViewById(R.id.albumUsage);

        ArrayAdapter<String> adapter = new ArrayAdapter(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                inAssets
        );
        album_choice.setAdapter(adapter);


        album_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                albumChoosen = true;
                //CharSequence x = getText(position);
                //albChStr = view.toString();
                Log.i("Element gewählt", "dkkdsssssssssssnybsbikkkkkkkkkkkkkkkkkkkkkkkkk");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("Kein Element gewählt", "Kein Element wurde ausgewählt");
            }
        });


        start_game.setOnClickListener(v ->{
            if (albumChoosen) {
                Intent intent = new Intent(this, GameView.class);
                startActivity(intent);
            }
        });


    }
}
