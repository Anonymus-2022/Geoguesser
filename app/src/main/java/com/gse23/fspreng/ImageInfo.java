package com.gse23.fspreng;

import android.util.Log;


/**
 * ImageInfo ist ein Objekt, welches in einem Images-Objekt gespeichert weden kann. Es enthält
 * Informationen zu einer Bilddatei. u.a. Name, Speicherort und Metadaten.
 */
public class ImageInfo {
    /**
     * Speichert den NAmen eines Bildes.
     */
    String picname;
    /**
     * Speichert, in welchem Album das Bild sich befindet.
     */
    String album;
    /**
     * Speichert den längengrad des Aufnameortes.
     */
    double longitude;
    /**
     * Speichert den längengrad des Aufnameortes.
     */
    double latitude;
    /**
     * Speichert die Bildunterschrift.
     */
    String imageDescription;
    /**
     * Speichert den Filepath des Bildes.
     */
    String filepath;


    ImageInfo(String album, String picname, double lon, double lat, String imd, String filpa) {
        this.album = album;
        this.picname = picname;
        this.longitude = lon;
        this.latitude = lat;
        this.imageDescription = imd;
        this.filepath = filpa;
    }


    /**
     * Diese Methode gibt die in der Datenstruktur ImageInfo enthaltenen Daten über das
     * Logsystem aus.
     *
     * @param pic Übergeben wird der Methode ein ImageInfo-Objekt
     */
    public static void logChoosenPic(ImageInfo pic) {
        Log.i("Filename", pic.picname);
        Log.i("Is in Album", pic.album);
        Log.i("Image Description", pic.imageDescription);
        Log.i("Filepath", pic.filepath);
        Log.i("Breitengrad", String.valueOf(pic.latitude));
        Log.i("Längengrad", String.valueOf(pic.longitude));
    }
}