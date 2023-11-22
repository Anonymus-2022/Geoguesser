package com.gse23.fspreng;

import static com.gse23.fspreng.CalcStuff.getScore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.gse23.fspreng.exception.EmpyAlbumException;

import org.osmdroid.config.Configuration;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Die Klasse GameView repräsentiert die Ansicht des Spiels, in dem dem Spieler
 * zufällige Bilder aus einem ausgewählten Album präsentiert werden. Der Spieler
 * kann die Bilder überspringen oder die Koordinaten eingeben und Punkte sammeln.
 */
public class GameView extends AppCompatActivity {

    /**
     * Der Tag für Log-Nachrichten, der anzeigt, aus welcher Klasse die Nachricht kommt.
     */
    static final String GAME_VIEW = "GameView";
    /**
     * Die Konstante für die Ausgabe der Längen-Koordinate.
     */
    static final String PRINT_LONGITUDE = "Print Longitude: ";
    /**
     * Message zum loggen.
     */
    static final String LATITUDE = "Latitude";

    /**
     * Doppelpunkt.
     */
     static final String DIES_IST_EIN_DOPPELPUNKT = ": ";
    /**
     * Die Konstante für die Ausgabe der Breiten-Koordinate.
     */
    static final String PRINT_LATITUDE = "Print " + LATITUDE + DIES_IST_EIN_DOPPELPUNKT;

    /**
     * Konstante, welche zur Berechnung gebraucht wird.
     */
    static final int TAUSEND = 1000;
    /**
     * Die aufschrift eines Buttens im AlertDialog.
     */
    static final String YES = "YES";
    /**
     * Die aufschrift eines Buttens im AlertDialog.
     */
    static final String NO = "NO";
    /**
     * Zum loggen.
     */
    static final String INPUT_COORDINATE = "Input Coordinate";

    /**
     * Komma.
     */
    static final String AN_DIESER_STELLE_STEHT_EIN_KOMMA = ",";
    /**
     * Buttonaufschrift für einen AlertDialog.
     */
    static final String OK = "OK";
    private Images pics;
    private String albumChoice;

    MapView mapView;

    /**
     * Legt fest was passieren soll, wenn der zurück-Button gedrückt wird.
     */
    public void onBackPressed() {
        AlertDialog.Builder shutdown = new AlertDialog.Builder(this);
        shutdown.setTitle("Do you really want to shutdown the game?");
        shutdown.setNegativeButton(YES, (dialog, id) -> {
            dialog.dismiss();
            finish();
        });
        shutdown.setPositiveButton(NO, (dialog, id) -> dialog.dismiss());
        shutdown.show();
    }

    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);
        mapView = findViewById(R.id.mapView);
        GeoPoint tip = new GeoPoint(0, 0);

        mapView.setMultiTouchControls(true);
        Context ctx = getApplicationContext();
        IConfigurationProvider provider = Configuration.getInstance();
        provider.setUserAgentValue("com.gse23.fspreng");
        provider.load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        List<GeoPoint> geoPoints = new ArrayList<>();
        geoPoints.add(tip);

        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Hier erhältst du die Position des Klicks
                    GeoPoint clickedPoint = (GeoPoint) mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());

                    // Nun kannst du mit den Koordinaten arbeiten
                    double latitude = clickedPoint.getLatitude();
                    double longitude = clickedPoint.getLongitude();

                    // Beispiel: Logge die Koordinaten
                    Log.d("MapClick", "Latitude: " + latitude + ", Longitude: " + longitude);
                }

                // Rückgabewert: false bedeutet, dass das Event weiterverarbeitet werden soll
                return false;
            }
        });

        mapView.post(() -> {
            IMapController mapController = mapView.getController();
            mapView.zoomToBoundingBox(BoundingBox.fromGeoPointsSafe(geoPoints), false);
            mapController.setCenter(geoPoints.get(0));
        });


        Marker marker = new Marker(mapView);
        marker.setPosition(geoPoints.get(0));
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(marker);
        marker.setTitle("Universität Bielefeld");


        Bundle get;
        EditText latitudeIn;
        EditText longitudeIn;
        Button spielAnleitung = findViewById(R.id.game_rules);
        TextView albChoice = findViewById(R.id.albChoice);
        Button goBack = findViewById(R.id.go_back);
        ImageView image = findViewById(R.id.image);
        Button newPic = findViewById(R.id.newPic);
        Button confirm = findViewById(R.id.confirm);
        Button cancel = findViewById(R.id.cancel);

        String choosenAlbum = "AlbumChoice";
        albumChoice = getIntent().getStringExtra(choosenAlbum);
        albChoice.setText(albumChoice);
        Log.i("Choosen Album: ", albumChoice);

        try {
            pics = GetAssetContents.get(getApplicationContext(), albumChoice);
        } catch (EmpyAlbumException ignored) {
            finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Drawable bild;
        int index = 0;

        final ImageInfo[] pic = new ImageInfo[1];
        while (true) {
            try {
                pic[0] = pics.pos(index);

                if (!pics.emptyAlbum(albumChoice)) {
                    Random random = new Random();
                    int randomNum = random.nextInt(pics.length());

                    if (randomNum == index) {
                        pics.deleteImage(pic[0].picname);

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
                // Aktualisiere den Index für das nächste Bild
                if (index == pics.length() - 1) {
                    index = 0;
                } else {
                    index++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Setze das geladene Bild in die ImageView
        image.setImageDrawable(bild);
        image.setContentDescription(pic[0].imageDescription);

        // Klick-Listener für die Schaltfläche, die die Spielanleitung öffnet
        spielAnleitung.setOnClickListener(v -> {
            Intent intent = new Intent(this, SpielAnleitung.class);
            startActivity(intent);
        });

        // Klick-Listener für die Schaltfläche, die zur Albumauswahl zurückführt
        goBack.setOnClickListener(v -> {
            Log.i("Status", "going to: change to AlbumChoice");
            finish();
        });

        // Initialisierung von UI-Elementen und Text Watchern für die Koordinateneingabe
        get = getIntent().getExtras();

        // Klick-Listener für die Schaltfläche, um ein neues Bild anzuzeigen
        newPic.setOnClickListener(v -> {
            Drawable bildOne = null;
            int indexOne = 0;
            int randomNumOne;

            while (true) {
                String alb = null;

                if (pics.length() != 0) {
                    // Hole das Bild an der aktuellen Indexposition
                    pic[0] = pics.pos(indexOne);
                    alb = pic[0].album;
                }

                if (pics.length() != 0) {
                    assert alb != null;
                    // Vergleiche das Album des Bildes mit dem ausgewählten Album
                    if (alb.equals(albumChoice)) {
                        // Generiere eine zufällige Zahl und vergleiche sie
                        // Generiere eine zufällige Zahl und vergleiche sie mit dem Index
                        Random random = new Random();
                        randomNumOne = random.nextInt(pics.length());
                        if (randomNumOne == indexOne) {
                            try {
                                // Lade das neue Bild
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
                    allImagesSeen.setPositiveButton(OK, (dialog, id) -> {
                        dialog.dismiss();
                        finish();
                    });
                    allImagesSeen.show();
                    break;
                }
            }

            // Setze das neue Bild in die ImageView
            image.setImageDrawable(bildOne);
            image.setContentDescription(pic[0].imageDescription);

        });










        /*
        Bundle finalGet = get;

        // Klick-Listener für die Bestätigungs-Schaltfläche
        confirm.setOnClickListener(v -> {
            String latitudeStr = finalLatitudeIn.getText().toString();
            String longitudeStr = finalLongitudeIn.getText().toString();

            // Überprüfen Sie, ob die eingegebenen Koordinaten gültig sind
            if (latitudeStr.matches("^-?(\\d{1,2}(\\.\\d+)?|90)$")
                    && longitudeStr.matches("^-?(\\d{1,2}(\\.\\d+)?|1[0-7]\\d(\\.\\d+)?|1"
                    + "80)$")) {
                Log.d("Entered Coordinates", LATITUDE + DIES_IST_EIN_DOPPELPUNKT + latitudeStr + ", Longitude: "
                        + longitudeStr);

                // Erstellen Sie einen Link zu OpenStreetMap mit den eingegebenen und den
                // Bildkoordinaten
                assert finalGet != null;
                String posLink;
                ImageInfo.logChoosenPic(pic[0]);
                posLink = "https://www.openstreetmap.org/directions?engine=fossgis_valhalla_"
                        + "foot&route=" + latitudeStr + AN_DIESER_STELLE_STEHT_EIN_KOMMA
                        + longitudeStr + ";"
                        + pic[0].latitude + AN_DIESER_STELLE_STEHT_EIN_KOMMA
                        + pic[0].longitude;

                // Berechnen Sie die Entfernung und erstellen Sie einen Intent für das Ergebnis
                Intent intent = new Intent(this, ResultView.class);
                double distance = 0;
                distance = Haversine.distance(Double.parseDouble(latitudeStr),
                        Double.parseDouble(longitudeStr), (pic[0].longitude),
                        (pic[0].latitude));

                // Fügen Sie die Daten dem Intent hinzu und starten Sie die Ergebnis-Ansicht
                intent.putExtra("posLink", posLink);
                int score = getScore(distance * TAUSEND);
                intent.putExtra("score", score);
                intent.putExtra("distance", distance);
                startActivity(intent);
            } else {
                // Ungültige Eingabe, zeige eine Benachrichtigung
                Log.d("SetTip", "Invalid input");
                AlertDialog.Builder invalidInput = new AlertDialog.Builder(this);
                invalidInput.setTitle("The input has the wrong format!");
                invalidInput.setPositiveButton(OK, (dialog, id) -> dialog.dismiss());
                invalidInput.show();
            }
        });

        // Klick-Listener für die Abbruch-Schaltfläche
        //cancel.setOnClickListener(v -> {
        //    // Zeige eine Bestätigungsnachricht und beende die Aktivität, wenn der Benutzer
        //    // bestätigt
        //    AlertDialog.Builder shutdown = new AlertDialog.Builder(this);
        //    shutdown.setTitle("Do you really want to shutdown the game?");
        //    shutdown.setNegativeButton("YES", (dialog, id) -> {
        //        dialog.dismiss();
        //        finish();
        //    });
        //    shutdown.setPositiveButton("NO", (dialog, id) -> dialog.dismiss());
        //    shutdown.show();
        //});

        */

    }


}
