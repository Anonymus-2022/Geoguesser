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

            for (String x : inAssets) {
                if (x != null) {
                    String msg = "Enthält: ";
                    Log.i("/" + alb + "/", msg + x);
                    String[] next = context.getAssets().list(alb + "/" + x);
                    assert next != null;

                    for (String j : next) {
                        if (j != null) {
                            Log.i(alb + "/" + x, msg + j);
                        } else {
                            Log.i(alb + "/" + x, "ist Leer");
                        }
                    }

                    for (String j : next) {
                        String imagePath = alb + "/" + x + "/" + j;
                        String jpg = ".*\\.jpg$";

                        if (j.matches(jpg)) {
                            Log.i(j, "Die Datei ist eine .jpg-Datei.");
                            Images.addImage(ExifReader.readExif(x, j, imagePath, context));
                            Log.i(j, "added to Images");
                        } else {
                            Log.i(j, "Die Datei ist keine .jpg-Datei.");
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
