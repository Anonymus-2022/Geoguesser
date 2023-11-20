package com.gse23.fspreng;

import android.util.Log;

import java.util.ArrayList;

/**
 * Images ist ein Objekt, welches dazu genutzt werden kann Objekte vom typ ImagesInfo zu speichern.
 * implementiert sind die Methoden addImage(), length() und pos().
 */
public class Images {

    /**
     * Initialisierung der ArrayList, in welcher die ImageInfo-Objekte gespeichert werden.
     */
    static ArrayList<ImageInfo> infos;


    /**
     * ImageInfo ist ein Objekt, welches in einem Images-Objekt gespeichert weden kann. Es enthält
     * Informationen zu einer Bilddatei. u.a. Name, Speicherort und Metadaten.
     */
    public static class ImageInfo {
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

        public static void logChoosenPic(ImageInfo pic){
            Log.i("Filename", pic.picname);
            Log.i("Is in Album", pic.album);
            Log.i("Image Description", pic.imageDescription);
            Log.i("Filepath", pic.filepath);
            Log.i("Breitengrad", String.valueOf(pic.latitude));
            Log.i("Längengrad", String.valueOf(pic.longitude));
        }

        ImageInfo(String album, String picname, double lon, double lat, String imd, String filpa) {
            this.album = album;
            this.picname = picname;
            this.longitude = lon;
            this.latitude = lat;
            this.imageDescription = imd;
            this.filepath = filpa;
        }
    }

    Images() {
        infos = new ArrayList<>();
        Log.i("ImageStack", "ImageStack has been Created");
    }


    /**
     * Die methode addImage() fügt ein Bild zu der ArrayList infos hinzu.
     * @param pic übergeben bekommt diese Methode ein ImageInfo-Objekt.
     */
    public static void addImage(ImageInfo pic) {
        infos.add(pic);
    }

    /**
     * Diese Metode gibt die Größe bzgl. der Menge an Einträgen des Images-Objektes zurück.
     * @return die Ausgabe ist ein Integerwert der die Menge an Elementen in der ArryList darstellt.
     */
    public int length() {
        return infos.size();
    }


    /**
     * löscht ein ImageInfo-Objekt element aus dem referenzierten Images-Objekt.
     * @param picname ist der name des Bildes, das gelöscht werden soll. Da durch werden auch
     *               doppelte b.z.w. mehrfach forkommende Bilder entfernt
     */
    public void deleteImage(String picname) {
        int index;
        for (index = 0; index < infos.size(); index++) {
            if (infos.get(index).picname == picname) {
                infos.remove(index);
            }
        }
    }

    /**
     * Die Methode prüft, ob das angegebene Album Bilder enthölt.
     * @param alb ist der Name des Albums, welches auf Leerheit geprüft werden soll
     * @return falls das Album leer ist wird true zurückgegeben, ansonsten false
     */
    public boolean emptyAlbum(String alb) {
        boolean isEmpty = true;
        for (ImageInfo x: infos) {
            if (x.album.equals(alb)) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    /**
     * Diese Methode erlaubt es, sich ein ImageInfo-Objekt an einer ganz bestimmten Stelle in Images
     * ausgeben  zu lassen.
     * @param pos Übergeben wird dazu ein Index zwischen 0 und infos.length()-1
     * @return zurückgegeben wird hier ein ImageInfo-Objekt.
     */
    public ImageInfo pos(int pos) {
        return infos.get(pos);
    }



}
