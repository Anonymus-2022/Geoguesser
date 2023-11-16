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

import com.gse23.fspreng.exception.CorruptedDataException;

import java.util.Objects;

/**
 * Hier wird dem Spieler ermöglicht, die Position des gesehenen Bildes zu schätzen.
 */
public class SetTip extends AppCompatActivity {
    private static double convertToDecimal(String coordinate) throws CorruptedDataException {
        String[] parts = coordinate.split(",");

        if (parts.length >= 3) {
            double degrees = Double.parseDouble(parts[0].split("/")[0]);
            double minutes = Double.parseDouble(parts[1].split("/")[0]);
            double seconds = Double.parseDouble(parts[2].split("/")[0]);

            return degrees + (minutes / 60) + (seconds / 3600);
        } else {
            throw new CorruptedDataException();
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
                String posLink = null;
                try {
                    posLink = "https://www.openstreetmap.org/directions?engine=fossgis_valhalla_"
                            + "foot&route=" + longitudeStr + "," + latitudeStr + ";"
                            + convertToDecimal((String) Objects.requireNonNull(
                                    get.get("choosenPicLon"))) + ","
                            + convertToDecimal((String) Objects.requireNonNull(
                                    get.get("choosenPicLat")));
                } catch (CorruptedDataException e) {
                    throw new RuntimeException(e);
                }
                Intent intent = new Intent(this, ResultView.class);
                double distance = 0;
                try {
                    distance = Haversine.distance(Double.parseDouble(latitudeStr),
                            Double.parseDouble(longitudeStr),(convertToDecimal((String) Objects.requireNonNull(
                                    get.get("choosenPicLon")))),
                            convertToDecimal((String) Objects.requireNonNull(
                                    get.get("choosenPicLat"))));
                } catch (CorruptedDataException e) {
                    throw new RuntimeException(e);
                }
                intent.putExtra("posLink", posLink);
                intent.putExtra("distance", distance);
                startActivity(intent);
            } else {
                Log.d("SetTip", "Invalid input");
                AlertDialog.Builder invalidInput = new AlertDialog.Builder(this);
                invalidInput.setTitle("The input has the wrong format!");
                invalidInput.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                invalidInput.show();
            }

        });
    }
}
