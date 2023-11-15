package com.gse23.fspreng;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicReference;

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


        latitudeIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before,
                                      int count) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String latitudeStr = editable.toString();
                Log.i("Input Coordinate", "Latitude: " + latitudeStr);
            }
        });



        longitudeIn.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before,
                                      int count) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String latitudeStr = editable.toString();
                Log.i("Input Coordinate", "Latitude: " + latitudeStr);
            }
        });


        confirm.setOnClickListener(v -> {
            String latitudeStr = latitudeIn.getText().toString();
            String longitudeStr = longitudeIn.getText().toString();
            if (Double.parseDouble(latitudeStr) < 90
                    && Double.parseDouble(latitudeStr) > -90
                    && Double.parseDouble(longitudeStr) < 180
                    && Double.parseDouble(longitudeStr) > -180) {
                Log.d("Entered Coordinates", "Latitude: " + latitudeStr + ", Longitude: "
                        + longitudeStr);
                String posLink = "https://www.openstreetmap.org/directions?engine=fossgis_valhalla_foot"
                        + "&route=" + longitudeStr + "," + latitudeStr;
                Intent intent = new Intent(this, ResultView.class);
                intent.putExtra("posLink", posLink);
                startActivity(intent);
            } else {
                Log.d("SetTip", "Invalid input");
                AlertDialog.Builder invalidInput = new AlertDialog.Builder(this);
                invalidInput.setTitle("The input has the wrong format!");
                invalidInput.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                invalidInput.show();
            }

        });

        back.setOnClickListener(v ->
            finish()
        );

    }
}
