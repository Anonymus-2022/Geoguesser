package com.gse23.fspreng;

import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Ist nur ein Ansatz einer Activity die der Nutzer betreten kann, um sich die Spielanleitung
 * anzuschauen.
 */
public class SpielAnleitung extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spiel_anleitung);
        Button goBack = findViewById(R.id.go_back);
        goBack.setOnClickListener( v-> finish() );
    }
}
