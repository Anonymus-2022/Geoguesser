package com.gse23.fspreng;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

/**
 * Das ist die MainActivity, die zuerst ausgeführt wird, wenn die App gestartet wird.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String answer = "Enthält: ";
        String album = "albums";
        try {
            String[] inAssets = getAssets().list(album);
            for (int i = 0; i < inAssets.length; i++) {
                if (inAssets[i] != null) {
                    Log.i("/" + album, answer + inAssets[i]);
                    String[] next = getAssets().list("/" + album + "/" + inAssets[i]);
                    for (int j = 0; j < next.length; j++) {
                        if (next[j] != null) {
                            Log.i("/" + album + "/" + inAssets[i], answer + next[j]);
                            String[] subfolders = getAssets().list("/" + album + "/" + inAssets[i] + "/" + next[j]);
                        } else {
                            Log.i("/" + album + "/" + inAssets[i], "ist Leer");
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("ERROR", "Fehler beim Auflisten von Dateien/Verzeichnissen in /albums: " + e.getMessage());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
