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

            String alb = "albums";
            String[] inAssets = getAssets().list(alb);

            for (int i = 0; i < inAssets.length; i++) {

                if (inAssets[i] != null) {

                    String msg = "Enthält: ";
                    Log.i("/" + alb + "/", msg + inAssets[i]);

                    String[] next = getAssets().list(alb + "/" + inAssets[i]);

                    int jx = 0;

                    for (jx = 0; jx < next.length; jx++) {

                        if (next[jx] != null) {

                            Log.i(alb + "/" + inAssets[i], msg + next[jx]);

                        } else {

                            Log.i(alb + "/" + inAssets[i], "ist Leer");

                        }

                    }

                    for (jx = 0; jx < next.length; jx++) {

                        String imagePath = alb + "/" + inAssets[i] + "/" + next[jx];

                        String jpg = ".*\\.jpg$";

                        if (next[jx].matches(jpg)) {

                            Log.i(next[jx], "Die Datei ist eine .jpg-Datei.");

                            new ExifReader().readExif(imagePath, getApplicationContext());

                        } else {

                            Log.i(next[jx], "Die Datei ist keine .jpg-Datei.");

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
