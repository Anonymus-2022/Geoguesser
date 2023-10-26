package com.gse23.fspreng;

import android.content.Context;
import android.media.ExifInterface;
import android.util.Log;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExifReader {

    public static void readExif(String imagePath, Context context) {
        try (InputStream inputStream = context.getAssets().open(imagePath)) {

            ExifInterface exifInterface = new ExifInterface(inputStream);

            String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);

            String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);

            String imageDescription = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION);

            Log.i("Image Path", imagePath);

            if (latitude != null && longitude != null) {

                Log.i(imagePath + "/" + "Latitude", latitude);

                Log.i(imagePath + "/" + "Longitude", longitude);

            } else {

                Log.i(imagePath + "/" + "Latitude", "Keine Daten verfügbar");

                Log.i(imagePath + "/" + "Longitude", "Keine Daten verfügbar");

            }

            if (imageDescription != null) {

                Log.i(imagePath + "/" + "Image Description", imageDescription);

            } else {

                Log.i(imagePath + "/" + "Image Description", "Keine Daten verfügbar");

            }

        } catch (IOException e) {

            Log.e(imagePath + "/" + "ExifReader", "Fehler beim Lesen der EXIF-Daten: " + e.getMessage());

        }

    }

}
