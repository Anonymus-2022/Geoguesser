package com.gse23.fspreng;

import android.content.Context;
import android.util.Log;
import java.io.IOException;

public class GetAssetContents {

    public static Images get(Context context) {
        Images pictures = new Images();
        try {
            String alb = "albums";
            String[] inAssets = context.getAssets().list(alb);
            assert inAssets != null;

            for (String unterordner : inAssets) {
                if (unterordner != null) {
                    String msg = "Enthält: ";
                    Log.i("/" + alb + "/", msg + unterordner);
                    String[] ordnerMitBildern = context.getAssets().list(alb + "/" + unterordner);
                    assert ordnerMitBildern != null;

                    for (String bilder : ordnerMitBildern) {
                        if (bilder != null) {
                            Log.i(alb + "/" + unterordner, msg + bilder);
                        } else {
                            Log.i(alb + "/" + unterordner, "ist Leer");
                        }
                        String imagePath = alb + "/" + unterordner + "/" + bilder;
                        String jpg = ".*\\.jpg$";

                        if (bilder.matches(jpg)) {
                            Log.i(bilder, "Die Datei ist eine .jpg-Datei.");
                            Images.addImage(ExifReader.readExif(unterordner, bilder, imagePath, context));
                            Log.i(bilder, "added to Images");
                        } else {
                            Log.i(bilder, "Die Datei ist keine .jpg-Datei.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("ERROR", "Fehler beim Auflisten von "
                    + "Dateien/Verzeichnissen in /albums: " + e.getMessage());
        }
        return pictures;
    }
}
