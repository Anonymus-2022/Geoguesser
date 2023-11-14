package com.gse23.fspreng;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Hier wird dem Spieler ermöglicht, die Position des gesehenen Bildes zu schätzen.
 */
public class SetTip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_tip);

        Button back = findViewById(R.id.button);
        EditText latitudeIn = findViewById(R.id.latitude);
        EditText longitudeIn = findViewById(R.id.longitude);
        Button confirm = findViewById(R.id.confirm);


        // Event Handling für die Breiten-EditText
        latitudeIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String latitudeStr = editable.toString();
                Log.i("Input Coordinate", "Latitude: " + latitudeStr);
            }
        });

        longitudeIn.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String latitudeStr = editable.toString();
                Log.i("Input Coordinate", "Latitude: " + latitudeStr);
            }
        });

        confirm.setOnClickListener(v -> {
            // Hier kannst du den eingegebenen Breiten- und Längengrad verarbeiten
            String latitudeStr = latitudeIn.getText().toString();
            String longitudeStr = longitudeIn.getText().toString();
            Log.i("Entered Coordinates", "Latitude: " + latitudeStr + ", Longitude: " + longitudeStr);
        });

        back.setOnClickListener(v -> finish());

    }
}
