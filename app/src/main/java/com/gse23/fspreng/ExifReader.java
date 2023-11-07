package com.gse23.fspreng;

import android.content.Context;
import androidx.exifinterface.media.ExifInterface;

import android.graphics.ImageDecoder;
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
    public static Images.ImagesInfo readExif(String albname, String picname,String imagePath, Context context) {


        Images.ImagesInfo pic = null;
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

                Log.i(lat, latitude);

                Log.i(lon, longitude);

            } else {

                Log.i(lat, noData);

                Log.i(lon, noData);

            }

            String imD = "Image Description";
            if (imageDescr != null) {

                Log.i(imD, imageDescr);

            } else {

                Log.i(imD, noData);

            }
            Log.i("Album", albname);
            pic = new Images.ImagesInfo(albname, picname, lon, lat, imageDescr, imagePath);


        } catch (IOException e) {

            Log.e(imagePath + "/" + "ExifReader", "Fehler beim Lesen "
                    + "der EXIF-Daten: " + e.getMessage());

        }

        return pic;

    }


}
