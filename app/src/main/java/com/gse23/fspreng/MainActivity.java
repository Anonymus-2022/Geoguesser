package com.gse23.fspreng;
import static java.nio.file.Files.list;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.nio.file.Paths;


/**
 * Das ist die MainActivity, die zuerst ausgeführt wird, wenn die App gestartet wird.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            String[] inAssets = getAssets().list("albums");
            for (int i = 0; i < inAssets.length; i++) {
                if (inAssets[i] != null) {
                    Log.i("/albums...", "Beinhält: " + inAssets[i]);
                    String[] a = getAssets().list("albums/" + inAssets[i]);
                    for (int j = 0; j < a.length; j++) {
                        if (a[j] != null) {
                            Log.i("/albums/" + inAssets[i], "Beinhält: " + a[j]);
                            String[] subfolders = getAssets().list("albums/" + inAssets[i] + "/" + a[j]);
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
