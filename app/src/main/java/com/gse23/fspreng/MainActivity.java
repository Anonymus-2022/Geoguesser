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
                String[] inAssets;
                inAssets = getAssets().list("albums");
                int i = 0;
                while (true){
                    while (i < inAssets.length) {
                        if (inAssets[i] != null) {
                            Log.i("/albums/...", "Beinhält: " + inAssets[i]);
                            i++;
                        }
                    }
                break;
                }
                Log.i("marker", "keine weiteren files / directories");
            }catch(IOException e) {
                Log.e("ERROR", "Ordner /assets ist leer");
            }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
