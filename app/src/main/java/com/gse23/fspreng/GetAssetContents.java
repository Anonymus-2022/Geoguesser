package com.gse23.fspreng;

import android.content.Context;
import android.util.Log;

import com.gse23.fspreng.exception.CorruptedDataException;
import com.gse23.fspreng.exception.EmpyAlbumException;

import java.io.IOException;

/**
 * Diese Klasse liefert ein Images-Objekt, in dem sämtliche verfügbaren Bilder gespeichert
 * werden können. Dazu wird die Klasse imagesInfo verwendet, in dessen Objekten jeweils die
 * Informationen zu einem Bild gespeichert werden.
 */
public class GetAssetContents {

    /**
     * Log msg.
     */
    public static final String ALBUMS = "albums";
    /**
     * Log msg.
     */
    public static final String ENTHAELT = "Enthält: ";
    /**
     * Log msg.
     */
    public static final String IST_LEER = "ist Leer";
    /**
     * Regex für die Beurteilung der Validität der Dateien.
     */
    public static final String JPG = ".*\\.jpg$";
    /**
     * Regex für die Beurteilung der Validität der Dateien.
     */
    public static final String JPEG = ".*\\.jpeg$";
    /**
     * Regex für die Beurteilung der Validität der Dateien.
     */
    public static final String PNG = ".*\\.png$";
    /**
     * Bestätigung, dass die Datei valide ist.
     */
    public static final String DIE_DATEI_IST_EINE_JPG_PNG_JPEG_DATEI = "Die Datei ist eine .jpg-"
            + ", .png-, .jpeg-Datei.";
    /**
     * Feststellung, dass die Datei nicht valide ist.
     */
    public static final String DIE_DATEI_IST_KEINE_JPG_DATEI = "Die Datei ist keine .jpg-Datei.";
    /**
     * Log msg.
     */
    public static final String ADDED_TO_IMAGES = "added to Images";

    /**
     * Existiert nur der Vollständigkeit halber. Da nie ein GetAssetContent-Objekt erzeugt wird, ist
     * auch kein Konstruktor nötig.
     */
    protected GetAssetContents() {
    }

    /**
     * Die Funktion erzeugt ein Images-Objekt, indem es iterativ die existierenden Bilder findet
     * und mit dem ExifReader ein ImagesInfo-Objekt erzeugt, welches dann zu Images geaddet werden
     * kann.
     *
     * @param context es handelt sich hier um den Context der aufrufenden Activity, meist übergeben
     *                in der Form getApplicationContext()
     * @return Zurück gibt die Methode ein Images-Objekt, welches sämtliche Bilder
     * (sofern vorhanden) unterhalb des Asset-Ordners speichert
     * @throws EmpyAlbumException Das bedeutet wohl etwas.
     */
    public static Images get(Context context) throws EmpyAlbumException {
        Images pictures = new Images();
        try {
            String alb = ALBUMS;
            String[] inAssets = context.getAssets().list(alb);

            assert inAssets != null;
            for (String unterordner : inAssets) {
                if (unterordner != null) {
                    String msg = ENTHAELT;
                    Log.i("/" + alb + "/", msg + unterordner);
                    String[] ordnerMitBildern = context.getAssets().list(alb + "/"
                            + unterordner);

                    assert ordnerMitBildern != null;
                    for (String bilder : ordnerMitBildern) {
                        if (bilder != null) {
                            Log.i(alb + "/" + unterordner, msg + bilder);
                        } else {
                            Log.i(alb + "/" + unterordner, IST_LEER);
                        }
                        String imagePath = alb + "/" + unterordner + "/" + bilder;

                        assert bilder != null;
                        if (bilder.matches(JPG)
                                || bilder.matches(JPEG)
                                || bilder.matches(PNG)) {
                            Log.i(bilder, DIE_DATEI_IST_EINE_JPG_PNG_JPEG_DATEI);
                            Images.addImage(ExifReader.readExif(unterordner, bilder, imagePath,
                                    context));
                            Log.i(bilder, ADDED_TO_IMAGES);
                        } else {
                            Log.i(bilder, DIE_DATEI_IST_KEINE_JPG_DATEI);
                        }
                    }
                } else {
                    throw new EmpyAlbumException();
                }
            }
        } catch (IOException | EmpyAlbumException | CorruptedDataException e) {
            Log.e("ERROR", "Fehler beim Auflisten von "
                    + "Dateien/Verzeichnissen in /albums: " + e.getMessage());
        }
        return pictures;
    }

    /**
     * Die Funktion erzeugt ein Images-Objekt, indem es iterativ die existierenden Bilder findet
     * und mit dem ExifReader ein ImagesInfo-Objekt erzeugt, welches dann zu Images geaddet werden
     * kann.
     *
     * @param context   es handelt sich hier um den Context der aufrufenden Activity, meist übergeben
     *                  in der Form getApplicationContext()
     * @param albumWish gibt an, aus welchem Ordner die Bilder ausgelesen werden sollen
     * @return Zurück gibt die Methode ein Images-Objekt, welches sämtliche Bilder
     * (sofern vorhanden) des angegebenen Ordners speichert
     * @throws IOException        das sollte wohl etwas bedeuten
     * @throws EmpyAlbumException das sollte wohl was bedeuten
     */
    public static Images get(Context context, String albumWish) throws EmpyAlbumException,
            IOException {
        Images pictures = new Images();
        String alb = ALBUMS;
        String[] inAssets = context.getAssets().list(alb);

        assert inAssets != null;
        for (String unterordner : inAssets) {
            if (unterordner != null && unterordner.equals(albumWish)) {
                String msg = ENTHAELT;
                Log.i("/" + alb + "/", msg + unterordner);
                String[] ordnerMitBildern = context.getAssets().list(alb + "/"
                        + unterordner);

                assert ordnerMitBildern != null;
                for (String bilder : ordnerMitBildern) {
                    if (bilder != null) {
                        Log.i(alb + "/" + unterordner, msg + bilder);
                    } else {
                        Log.i(alb + "/" + unterordner, IST_LEER);
                    }
                    String imagePath = alb + "/" + unterordner + "/" + bilder;

                    assert bilder != null;
                    if (bilder.matches(JPG)
                            || bilder.matches(JPEG)
                            || bilder.matches(PNG)) {
                        Log.i(bilder, DIE_DATEI_IST_EINE_JPG_PNG_JPEG_DATEI);
                        try {
                            Images.addImage(ExifReader.readExif(unterordner, bilder, imagePath,
                                    context));
                            Log.i(bilder, ADDED_TO_IMAGES);
                        } catch (CorruptedDataException e) {
                            Log.e("CorruptedDataException", "GetAssetContent, line 106: " + e.getMessage());
                        }
                    } else {
                        Log.i(bilder, DIE_DATEI_IST_KEINE_JPG_DATEI);
                    }
                }
            } else {
                if (unterordner == null && unterordner.equals(albumWish)) {
                    Log.e("EmpyAlbumException", "Unterordner ist null und entspricht dem angegebenen Album");
                    throw new EmpyAlbumException();
                }
            }
        }

        return pictures;
    }

}

