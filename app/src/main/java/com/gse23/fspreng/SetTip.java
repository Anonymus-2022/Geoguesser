package com.gse23.fspreng;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

/**
 * Hier wird dem Spieler ermöglicht, die Position des gesehenen Bildes zu schätzen.
 */
public class SetTip extends AppCompatActivity {
    private static double convertToDecimal(String coordinate) {
        String[] parts = coordinate.split(",");

        if (parts.length >= 3) {
            double degrees = Double.parseDouble(parts[0].split("/")[0]);
            double minutes = Double.parseDouble(parts[1].split("/")[0]);
            double seconds = Double.parseDouble(parts[2].split("/")[0]);

            return degrees + (minutes / 60) + (seconds / 3600);
        } else {
            return 0.0;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_tip);
        Bundle get = getIntent().getExtras();
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
                assert get != null;
                String posLink = "https://www.openstreetmap.org/directions?engine=fossgis_valhalla_"
                        + "foot&route=" + longitudeStr + "," + latitudeStr + ";"
                        + convertToDecimal((String) Objects.requireNonNull(
                                get.get("choosenPicLon"))) + ","
                        + convertToDecimal((String) Objects.requireNonNull(
                                get.get("choosenPicLat")));
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
