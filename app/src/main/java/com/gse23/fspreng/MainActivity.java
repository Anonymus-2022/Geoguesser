package com.gse23.fspreng;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;



/**
 * Das ist die MainActivity, die zuerst ausgeführt wird, wenn die App gestartet wird.
 */
public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        View intro = findViewById(R.id.toAlbumChoice);
        Button spielAnleitung = findViewById(R.id.game_rules);

        GetAssetContents.get(getApplicationContext());

        intro.setOnClickListener(v -> {
            Intent intent = new Intent(this, StartBild.class);
            Log.i("Status", "going to: change to AlbumChoice");
            startActivity(intent);
        });

        spielAnleitung.setOnClickListener(v -> {
            Intent intent = new Intent(this, SpielAnleitung.class);
            startActivity(intent);
        });
    }
}
