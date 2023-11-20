package com.gse23.fspreng;

/**
 * Übernommen aus GitHub:
 * https://github.com/jasonwinn/haversine/blob/master/Haversine.java
 * <p>
 * Jason Winn
 * http://jasonwinn.org
 * Erstellt am 10. Juli 2013
 * <p>
 * Beschreibung: Kleine Klasse, die die ungefähre Entfernung zwischen
 * zwei Punkten unter Verwendung der Haversine-Formel bereitstellt.
 * <p>
 * Aufruf im statischen Kontext:
 * Haversine.distance(47.6788206, -122.3271205,
 * 47.6788206, -122.5271205)
 * --> 14.973190481586224 [km]
 */
public class Haversine {

    /**
     * der konstruktor existiert nur der vollständigkeit halber, da nie ein haversine Objekt erzeugt
     * wird.
     */
    protected Haversine() {
    }

    // Ungefährer Erdradius in KM
    private static final int EARTH_RADIUS = 6371;

    /**
     * Berechnet die Entfernung zwischen zwei geografischen Punkten unter Verwendung
     * der Haversine-Formel.
     *
     * @param startLat  Die Breitengrad des Startpunkts.
     * @param startLong Die Längengrad des Startpunkts.
     * @param endLat    Die Breitengrad des Endpunkts.
     * @param endLong   Die Längengrad des Endpunkts.
     * @return Die Entfernung zwischen den beiden Punkten in Kilometern.
     */
    public static double distance(double startLat, double startLong,
                                  double endLat, double endLong) {

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // <-- d
        return EARTH_RADIUS * c;
    }

    /**
     * Berechnet den Haversin-Wert eines gegebenen Winkels.
     *
     * @param val Der Winkel, für den der Haversin-Wert berechnet wird.
     * @return Der Haversin-Wert des gegebenen Winkels.
     */
    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
