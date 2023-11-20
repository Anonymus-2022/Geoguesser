package com.gse23.fspreng;

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

import com.gse23.fspreng.exception.EmpyAlbumException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hier bekommt der Nutzer die Möglichkeit, ein Album von Bildern auszuwählen und danach das Spiel
 * zu starten. Ist kein Album ausgewählt kann das Spiel nicht gestartet werden.
 */
public class StartBild extends AppCompatActivity {
    public static final String ERROR = "Error";
    /**
     * Hier wird gespeichert, ob ein Album ausgewählt wurde.
     */
    boolean albumChosen = false;
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
            Log.e(ERROR, "Error listing albums: " + e.getMessage());
        }

        assert inAssets != null;
        // Filtere Alben ohne Bilder heraus
        String[] albumsWithImages = filterAlbumsWithImages(inAssets);

        String[] transfer = new String[albumsWithImages.length + 1];
        transfer[0] = "Choose an Album!";
        System.arraycopy(albumsWithImages, 0, transfer, 1, albumsWithImages.length);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, transfer);
        albumChoice.setAdapter(adapter);

        albumChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                albumChosen = true;
                selectedAlbum = albumChoice.getSelectedItem().toString();
                Log.i("Ausgewähltes Album", selectedAlbum);
                // Aktiviere die Schaltfläche "Start Game" wenn ein Album ausgewählt wurde
                startGame.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("Kein Element gewählt", "Kein Element wurde ausgewählt");
                // Deaktiviere die Schaltfläche "Start Game", wenn kein Album ausgewählt wurde
                startGame.setEnabled(false);
            }
        });

        spielAnleitung.setOnClickListener(v -> {
            Intent intent = new Intent(this, SpielAnleitung.class);
            startActivity(intent);
        });

        startGame.setOnClickListener(v -> {
            if (albumChosen && !selectedAlbum.equals(transfer[0])) {
                Intent intent = new Intent(this, GameView.class);
                intent.putExtra("AlbumChoice", selectedAlbum);
                startActivity(intent);
            }
        });
    }

    /**
     * Filtert Alben ohne Bilder heraus.
     *
     * @param albums Die Liste der Alben.
     * @return Eine Liste der Alben, die Bilder enthalten.
     */
    private String[] filterAlbumsWithImages(String[] albums) {
        List<String> albumsWithImages = new ArrayList<>();

        for (String album : albums) {
            if (containsImages(album)) {
                albumsWithImages.add(album);
            }
        }

        return albumsWithImages.toArray(new String[0]);
    }

    /**
     * Überprüft, ob ein Album Bilder enthält.
     *
     * @param albumName Der Name des Albums.
     * @return True, wenn das Album Bilder enthält, sonst False.
     */
    private boolean containsImages(String albumName) {
        try {
            Images images = GetAssetContents.get(getApplicationContext(), albumName);
            return !images.emptyAlbum(albumName);
        } catch (EmpyAlbumException | IOException e) {
            Log.e(ERROR, "Error checking album: " + albumName);
            // Im Fehlerfall nehmen wir an, dass das Album leer ist
            return false;
        }
    }
}
