package com.gse23.fspreng;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Hier wird dem Spieler ermöglicht, die Position des gesehehenen Bildes zu schätzen.
 */
public class SetTip extends AppCompatActivity {

    protected void onCreate(Bundle savedBundleInstances) {

        super.onCreate(savedBundleInstances);
        setContentView(R.layout.set_tip);
        Button back = findViewById(R.id.button);

        back.setOnClickListener(v -> finish());
    }
}
