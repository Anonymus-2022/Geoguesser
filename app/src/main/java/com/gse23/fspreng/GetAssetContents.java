package com.gse23.fspreng;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
/**
 * Diese Klasse liefert ein Images-Objekt, in dem sämtliche verfügbaren Bilder gespeichert
 * werden können. Dazu wird die Klasse imagesInfo verwendet, in dessen Objekten jeweils die
 * informationen zu  einem Bild gespeichert werden.
 */
public class GetAssetContents {

    GetAssetContents() { }

    /**
     * Die Funktion erzeugt ein Images-Objekt, indem es iterativ die existierenden bilder findet
     * und mit dem ExifReader ein ImagesInfo-Objekt erzeugt, welches dann zu Images geaddet werden
     * kann.
     * @param context es handelt sich hier unden context der aufrufenden Activity, meist übergeben
     *                in der Form get(getApplicationContext())
     * @return Zurück gibt die Methode ein Images-Objekt, welches sämtliche Bilder
     *         (sofern vorhanden) unterhalb des Asset-Ordners Speichert
     */
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
                    String[] ordnerMitBildern = context.getAssets().list(alb + "/"
                            + unterordner);

                    assert ordnerMitBildern != null;
                    for (String bilder : ordnerMitBildern) {
                        if (bilder != null) {
                            Log.i(alb + "/" + unterordner, msg + bilder);
                        } else {
                            Log.i(alb + "/" + unterordner, "ist Leer");
                        }
                        String imagePath = alb + "/" + unterordner + "/" + bilder;

                        assert bilder != null;
                        if (bilder.matches(".*\\.jpg$")
                                || bilder.matches(".*\\.jpeg$")
                                || bilder.matches(".*\\.png$")) {
                            Log.i(bilder, "Die Datei ist eine .jpg-, .png-, .jpeg-Datei.");
                            Images.addImage(ExifReader.readExif(unterordner, bilder, imagePath,
                                    context));
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
