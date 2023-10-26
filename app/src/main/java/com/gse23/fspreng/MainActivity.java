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

        try {

            String[] inAssets = getAssets().list("albums");

            for (int i = 0; i < inAssets.length; i++) {

                if (inAssets[i] != null) {

                    Log.i("/albums...", "Beinhält: " + inAssets[i]);

                    String[] a = getAssets().list("albums/" + inAssets[i]);

                    int j = 0;

                    for (j = 0; j < a.length; j++) {

                        boolean x = false;

                        if (a[j] != null) {

                            Log.i("/albums/" + inAssets[i], "Beinhält: " + a[j]);

                        } else {

                            Log.i("albums/" + inAssets[i], "ist Leer");

                        }

                    }

                    for (j = 0; j < a.length; j++) {

                        String imagePath = "albums/" + inAssets[i] + "/" + a[j];

                        String jpg = ".*\\.jpg$";

                        if (a[j].matches(jpg)) {

                            Log.i(a[j], "Die Datei ist eine .jpg-Datei.");

                            new ExifReader().readExif(imagePath, getApplicationContext());

                        } else {

                            Log.i(a[j], "Die Datei ist keine .jpg-Datei.");

                        }

                    }

                }

            }

        } catch (IOException e) {

            Log.e("ERROR", "Fehler beim Auflisten von "
                    + "Dateien/Verzeichnissen in /albums: " + e.getMessage());

        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }

}
