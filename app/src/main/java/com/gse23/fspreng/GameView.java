package com.gse23.fspreng;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gse23.fspreng.exception.CorruptedDataException;
import com.gse23.fspreng.exception.EmpyAlbumException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


/**
 * Hier wird dem Spieler ein zufälliges Bild aus dem gewählten Album präsentiert. Falls ihm das Bild
 * nicht gefällt, hat er die Möglichkeit, es zu skippen. Hat er alle Bilder geskipped, so bekommt er
 * das angezeigt und wird zur Albenwahlzurückgeführt.
 */
public class GameView extends AppCompatActivity {

    /**
     * Message für Logs. Gibt an, aus welcher Classe die Message kommt
     */
    public static final String GAME_VIEW = "GameView";
    /**
     * Zeigt an, was hier ausgegeben wird.
     */
    public static final String PRINT_LONGITUDE = "Print Longitude: ";
    /**
     * Zeigt an, was hier ausgegeben wird.
     */
    public static final String PRINT_LATITUDE = "Print Latitude: ";
    private final ArrayList<String> alreadyShown = new ArrayList<>();
    private Images pics;
    private String albumChoice;

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
        setContentView(R.layout.game_view);

        Bundle get;
        EditText latitudeIn;
        EditText longitudeIn;
        Button spielAnleitung = findViewById(R.id.game_rules);
        TextView albChoice = findViewById(R.id.albChoice);
        Button goBack = findViewById(R.id.go_back);
        ImageView image = findViewById(R.id.image);
        Button newPic = findViewById(R.id.newPic);
        Button confirm = findViewById(R.id.confirm);
        Button cancel =  findViewById(R.id.cancel);

        String choosenAlbum = "AlbumChoice";
        albumChoice = getIntent().getStringExtra(choosenAlbum);
        albChoice.setText(albumChoice);

        try {
            pics = GetAssetContents.get(getApplicationContext(), albumChoice);
        } catch (EmpyAlbumException ignored) {
            finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Drawable bild;
        int index = 0;

        final Images.ImageInfo[] pic = new Images.ImageInfo[1];
        while (true) {
            try {
                pic[0] = pics.pos(index);

                if (!pics.emptyAlbum(albumChoice)) {
                    Random random = new Random();
                    int randomNum = random.nextInt(pics.length());

                    if (randomNum == index) {
                        alreadyShown.add(pic[0].picname);
                        pics.deleteImage(pic[0].picname);
                        Log.i("already shown", String.valueOf(alreadyShown));

                        // Bild laden
                        try (InputStream bildstream = getAssets().open(pic[0].filepath)) {
                            bild = Drawable.createFromStream(bildstream, pic[0].picname);
                            Log.i("PrintedAlbum", pic[0].picname);
                            Log.i("PrintFilepath", pic[0].filepath);
                            Log.i(GAME_VIEW, PRINT_LONGITUDE + pic[0].longitude);
                            Log.i(GAME_VIEW, PRINT_LATITUDE + pic[0].latitude);
                        }

                        break;
                    }
                }
                if (index == pics.length() - 1) {
                    index = 0;
                } else {
                    index++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        image.setImageDrawable(bild);
        image.setContentDescription(pic[0].imageDescription);

        spielAnleitung.setOnClickListener(v -> {
            Intent intent = new Intent(this, SpielAnleitung.class);
            startActivity(intent);
        });

        newPic.setOnClickListener(v -> {
            Drawable bildOne = null;
            int indexOne = 0;
            int randomNumOne;


            while (true) {
                String alb = null;

                if (pics.length() != 0) {
                    pic[0] = pics.pos(indexOne);
                    alb = pic[0].album;
                }

                if (pics.length() != 0) {
                    assert alb != null;
                    if (alb.equals(albumChoice)) {
                        Random random = new Random();
                        randomNumOne = random.nextInt(pics.length());
                        if (randomNumOne == indexOne) {
                            try {
                                try (InputStream bildstream = getAssets().open(pic[0].filepath)) {
                                    bildOne = Drawable.createFromStream(bildstream, pic[0].picname);
                                    Log.i(GAME_VIEW, "Printed Album: " + pic[0].picname);
                                    Log.i(GAME_VIEW, "Print Filepath: " + pic[0].filepath);
                                    Log.i(GAME_VIEW, PRINT_LONGITUDE + pic[0].longitude);
                                    Log.i(GAME_VIEW, PRINT_LATITUDE + pic[0].latitude);
                                }

                                pics.deleteImage(pic[0].picname);
                                Log.i(GAME_VIEW, "Already shown images count: " + pics.length());
                                break;
                            } catch (IOException e) {
                                Log.e(GAME_VIEW, "Error loading image: " + e.getMessage());
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    if (indexOne == pics.length() - 1) {
                        indexOne = 0;
                    } else {
                        indexOne++;
                    }
                } else {
                    Log.i(GAME_VIEW, "No images left to show.");
                    AlertDialog.Builder allImagesSeen = new AlertDialog.Builder(this);
                    allImagesSeen.setTitle("There are no images left to show!");
                    allImagesSeen.setPositiveButton("OK", (dialog, id) -> {
                        dialog.dismiss();
                        finish();
                    });
                    allImagesSeen.show();
                    break;
                }
            }

            image.setImageDrawable(bildOne);
            image.setContentDescription(pic[0].imageDescription);
        });

        goBack.setOnClickListener(v -> {
            Log.i("Status", "going to: change to AlbumChoice");
            finish();
        });

        ///////////////////////////////////////////////////////////////////////////////////////////

        get = getIntent().getExtras();
        latitudeIn = findViewById(R.id.latitude);
        longitudeIn = findViewById(R.id.longitude);


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


        EditText finalLatitudeIn = latitudeIn;
        EditText finalLongitudeIn = longitudeIn;
        Bundle finalGet = get;
        confirm.setOnClickListener(v -> {
            String latitudeStr = finalLatitudeIn.getText().toString();
            String longitudeStr = finalLongitudeIn.getText().toString();
            if (Double.parseDouble(latitudeStr) < 90
                    && Double.parseDouble(latitudeStr) > -90
                    && Double.parseDouble(longitudeStr) < 180
                    && Double.parseDouble(longitudeStr) > -180) {
                Log.d("Entered Coordinates", "Latitude: " + latitudeStr + ", Longitude: "
                        + longitudeStr);
                assert finalGet != null;
                String posLink = null;
                try {
                    posLink = "https://www.openstreetmap.org/directions?engine=fossgis_valhalla_"
                            + "foot&route=" + longitudeStr + "," + latitudeStr + ";"
                            + convertToDecimal(pic[0].longitude) + ","
                            + convertToDecimal(pic[0].latitude);
                } catch (CorruptedDataException e) {
                    throw new RuntimeException(e);
                }
                Intent intent = new Intent(this, ResultView.class);
                double distance = 0;
                try {
                    distance = Haversine.distance(Double.parseDouble(latitudeStr),
                            Double.parseDouble(longitudeStr),(convertToDecimal(pic[0].longitude)),
                            convertToDecimal((String) Objects.requireNonNull(
                                    pic[0].latitude)));
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

        cancel.setOnClickListener(v->{
            AlertDialog.Builder shutdown = new AlertDialog.Builder(this);
            shutdown.setTitle("Do you really want to shutdown the game?");
            shutdown.setNegativeButton("YES",(dialog,id) -> {
                dialog.dismiss();
                finish();
            });
            shutdown.setPositiveButton("NO", (dialog,id) -> dialog.dismiss());
            shutdown.show();
        });
    }
}
