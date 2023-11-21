package com.gse23.fspreng;

import android.content.Context;

import androidx.exifinterface.media.ExifInterface;

import android.util.Log;

import com.gse23.fspreng.exception.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * Der ExifReader enthält nur eine Methode, die dazu dient, Informationen aus einer Bilddatei zu
 * gewinnen. Die Art der Informationen, die extrahiert werden, ist festgelegt und an entsprechender
 * Stelle näher erklärt.
 */
class ExifReader {


    /**
     * Der Konstruktor existiert nur formal. Da nie ein ExifReader-Objekt erzeugt wird, sondern
     * nur auf eine Methode zugegriffen wird, hat er keine wirkliche Funktion.
     */
    protected ExifReader() {
    }


    public static boolean isValidKoordinate(String coordinate) {
        String coordinateRegex = "^(\\d+)/(\\d+),(\\d+)/(\\d+),(\\d+)/(\\d+)$";
        // Die Koordinate entspricht dem erwarteten Format
        // Die Koordinate entspricht nicht dem erwarteten Format
        return coordinate != null && coordinate.matches(coordinateRegex);
    }



    /**
     * Liest ausgewählte Metadaten aus einer Exif-Datei aus. Dazu wird ein relativer Pfad bezüglich
     * des assets-Ordners übergeben. Die extrahierten Informationen umfassen Koordinaten und die
     * Bildunterschrift.
     *
     * @param albname   Der Name des Albums, zu dem das Bild gehört.
     * @param picname   Der Name des Bildes.
     * @param imagePath Der relative Pfad zum Bild im assets-Ordner.
     * @param context   Der Kontext der Anwendung.
     * @return Eine Instanz von Images.ImageInfo, die die extrahierten Informationen enthält.
     * @throws CorruptedDataException Wenn die Daten in der Exif-Datei korrupt oder ungültig sind.
     */
    public static ImageInfo readExif(String albname, String picname, String imagePath,
                                            Context context) throws CorruptedDataException {

        ImageInfo pic = null;
        try (InputStream inputStream = context.getAssets().open(imagePath)) {

            ExifInterface exifInterface = new ExifInterface(inputStream);
            if (isValidKoordinate(exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE))
                    || isValidKoordinate(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE))) {

                String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                String imageDescr = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION);

                // Wenn keine Bildunterschrift vorhanden ist, wird ein Standardtext verwendet
                if (imageDescr == null) {
                    imageDescr = "No description available";
                }

                if (latitude == null || longitude == null) {

                    // Wenn keine Koordinaten vorhanden sind, wird eine Ausnahme ausgelöst
                    throw new CorruptedDataException();
                }

                Log.i("Album", albname);
                pic = new ImageInfo(albname, picname, CalcStuff.convertToDecimal(longitude),
                        CalcStuff.convertToDecimal(latitude), imageDescr,
                        imagePath);
                ImageInfo.logChoosenPic(pic);

            }

        } catch (CorruptedDataException | IOException e) {
            // Fehlerbehandlung bei IOException beim Lesen der EXIF-Daten
            Log.e(imagePath + "/" + "ExifReader", "Fehler beim Lesen der EXIF-Daten: "
                    + e.getMessage());
        }

        return pic;
    }
}
