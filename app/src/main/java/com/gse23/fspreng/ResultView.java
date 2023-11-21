package com.gse23.fspreng;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Diese Klasse dient der Darstellung der Ergebnisse des Spiels.
 */
public class ResultView extends AppCompatActivity {

    /**
     * Konstante, welche zur Berechnung gebraucht wird.
     * Dient zur umrechnung von km zu m
     */
    public static final int THOUSAND = 1000;

    /**
     * Konstante, welche zur Berechnung gebraucht wird.
     */
    public static final double PLACE_VALUE = 0.01;
    /**
     * Konstante, welche zum Loggen gebraucht wird.
     */
    public static final String DISTANZ = "Distanz";


    /**
     * Beschreibt, was passiert, wenn die Zurücktaste gedrückt wird.
     */
    public void onBackPressed() {
        AlertDialog.Builder shutdown = new AlertDialog.Builder(this);
        shutdown.setTitle("Do you really want to shutdown the game?");
        shutdown.setNegativeButton("YES", (dialog, id) -> {
            dialog.dismiss();
            finish();
        });
        shutdown.setPositiveButton("NO", (dialog, id) -> dialog.dismiss());
        shutdown.show();
    }


    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view);
        Bundle get = getIntent().getExtras();

        TextView showDistacne = findViewById(R.id.distanz);
        assert get != null;
        double distance = (double) get.get("distance");
        Log.i(DISTANZ, distance + " km");
        Log.i(DISTANZ, distance * THOUSAND + " m");
        distance = Rounder.round(distance, PLACE_VALUE);
        showDistacne.setText("Distance between your guess an reallity:\n\n"
                + distance + " km\n\nYour score:\n" + get.get("score") + "/5000");

        TextView showLINK = findViewById(R.id.showLink);
        String showLink = (String) get.get("posLink");
        showLINK.setText(showLink);
        Log.i("positionlink", showLink);
        showLINK.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(showLink));
            startActivity(intent);
        });
    }

}
