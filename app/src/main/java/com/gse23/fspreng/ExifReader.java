package com.gse23.fspreng;

import android.content.Context;
import androidx.exifinterface.media.ExifInterface;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;

/**
 * Der ExifReader enthält nur eine methode, dei Dazu dient, Informationen aus einer Bilddatei zu
 * gewinnen. Um welche Informationen es sich handelt ist festgelegt und an entsprechender Stelle
 * näher erklärt.
 */
class ExifReader {

    /**
     * Der Konstruktor existiert nur Formal. Da nie ein ExifReader-Objekt erzeugt, sondern nur auf
     * eine Methode zugegriffen wird hat er keine wirkliche Funktion.
     */
    protected ExifReader() { }

    /**
     * readExif() gibt ausgewählte Metadaten aus Exif-Datei aus. Dazu bekommt er einen relativen
     * Pfad bezüglich dem assets-Ordner übergeben. Es werden die Koordinaten und die
     * Bildunterscgrift ausgegeben.
     */
    public static void readExif(String imagePath, Context context) {

        try (InputStream inputStream = context.getAssets().open(imagePath)) {

            ExifInterface exifInterface = new ExifInterface(inputStream);

            String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);

            String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);

            String imageDescr = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION);

            Log.i("Image Path", imagePath);

            String lat = "Latitude";

            String lon = "Longitude";

            String noData = "Keine Daten verfügbar";
            if (latitude != null && longitude != null) {

                Log.i(imagePath + "/" + lat, latitude);

                Log.i(imagePath + "/" + lon, longitude);

            } else {

                Log.i(imagePath + "/" + lat, noData);

                Log.i(imagePath + "/" + lon, noData);

            }

            String imD = "Image Description";
            if (imageDescr != null) {

                Log.i(imagePath + "/" + imD, imageDescr);

            } else {

                Log.i(imagePath + "/" + imD, noData);

            }

        } catch (IOException e) {

            Log.e(imagePath + "/" + "ExifReader", "Fehler beim Lesen "
                    + "der EXIF-Daten: " + e.getMessage());

        }

    }


}
