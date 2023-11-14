package com.gse23.fspreng;

import android.content.Context;

import androidx.exifinterface.media.ExifInterface;

import android.util.Log;
import com.gse23.fspreng.exception.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Der ExifReader enthält nur eine methode, die Dazu dient, Informationen aus einer Bilddatei zu
 * gewinnen. Um welche Informationen es sich handelt ist festgelegt und an entsprechender Stelle
 * näher erklärt.
 */
class ExifReader {

    /**
     * Der Konstruktor existiert nur Formal. Da nie ein ExifReader-Objekt erzeugt, sondern nur auf
     * eine Methode zugegriffen wird hat er keine wirkliche Funktion.
     */
    protected ExifReader() {
    }

    /**
     * readExif() gibt ausgewählte Metadaten aus Exif-Datei aus. Dazu bekommt er einen relativen
     * Pfad bezüglich dem assets-Ordner übergeben. Es werden die Koordinaten und die
     * Bildunterscgrift ausgegeben.
     */
    public static Images.ImageInfo readExif(String albname, String picname, String imagePath,
                                            Context context) throws CorruptedDataException {


        Images.ImageInfo pic = null;
        try (InputStream inputStream = context.getAssets().open(imagePath)) {

            ExifInterface exifInterface = new ExifInterface(inputStream);
            if (exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE) != null
                    || exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE) != null) {

                String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);

                String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);

                String imageDescr = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION);

                if (imageDescr == null) {
                    imageDescr = "No description available";
                }

                Log.i("Image Path", imagePath);

                String lat = "Latitude";

                String lon = "Longitude";

                if (latitude != null && longitude != null) {

                    Log.i(lat, latitude);

                    Log.i(lon, longitude);

                } else {
                    throw new CorruptedDataException();
                }

                String imD = "Image Description";
                Log.i(imD, imageDescr);

                Log.i("Album", albname);
                pic = new Images.ImageInfo(albname, picname, longitude, latitude, imageDescr,
                        imagePath);

            }

        } catch (IOException e) {

                Log.e(imagePath + "/" + "ExifReader", "Fehler beim Lesen "
                        + "der EXIF-Daten: " + e.getMessage());

            }

            return pic;

        }

}
