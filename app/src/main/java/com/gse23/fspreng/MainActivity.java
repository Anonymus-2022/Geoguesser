package com.gse23.fspreng;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;


/**
 * Das ist die MainActivity, die zuerst ausgeführt wird, wenn die App gestartet wird.
 */
public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        try {

            String alb = "albums";
            String[] inAssets = getAssets().list(alb);
            assert inAssets != null;
            for (String x: inAssets) {

                if (x != null) {

                    String msg = "Enthält: ";
                    Log.i("/" + alb + "/", msg + x);

                    String[] next = getAssets().list(alb + "/" + x);

                    assert next != null;
                    for (String j: next) {

                        if (j != null) {

                            Log.i(alb + "/" + x, msg + j);

                        } else {

                            Log.i(alb + "/" + x, "ist Leer");

                        }

                    }

                    for (String j: next ) {

                        String imagePath = alb + "/" + x + "/" + j;

                        String jpg = ".*\\.jpg$";

                        if (j.matches(jpg)) {

                            Log.i(j, "Die Datei ist eine .jpg-Datei.");

                            ExifReader.readExif(imagePath, getApplicationContext());

                        } else {

                            Log.i(j, "Die Datei ist keine .jpg-Datei.");

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
        View intro = findViewById(R.id.toAlbumChoice);
        intro.setOnClickListener(v -> {
            Intent intent = new Intent(this, StartBild.class);
            Log.i("Status", "going to: change to AlbumChoice");
            startActivity(intent);
        });
    }

}
