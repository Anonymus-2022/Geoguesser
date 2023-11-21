package com.gse23.fspreng;

import static java.lang.Math.ceil;
import static java.lang.Math.log;

import com.gse23.fspreng.exception.CorruptedDataException;

/**
 * Die Klasse CalcStuff enthält statische Methoden für Berechnungen,
 * die im Spiel verwendet werden.
 */
public class CalcStuff {

    /**
     * Menge der Punkte, die man maximal erreichen kann.
     */
    static final int MAX_POINTS = 5000;
    /**
     * maximale Distanz, die bewertet wird, Ist sie größer, werden null Punkte gegeben.
     */
    static final double MAX_DISTANC = 10000;
    /**
     * Mindestdistanz; wenn man einen noch besseren Guess abgibt erhöht das die erhaltene Punktzahl
     * nicht.
     */
    static final double MIN_DISTANZ = 10;
    /**
     * größe der Koordinaten. Indentifizierung des Formats als DMS.
     */
     static final int COORDINATE_SIZE = 3;
    /**
     * Konstante zum bBerechnen der DD aus DMS.
     */
     static final int MIN = 60;
    /**
     * Konstante zum Berechnen der DD aus DMS.
     */
     static final int SECS = 3600;

    /**
     * Ein konstruktor,der nur der vollständigkeit halber existiert.
     */
    protected CalcStuff() {
    }

    /**
     * Berechnet den Punktestand basierend auf der Entfernung zum Ziel.
     *
     * @param distance Die Entfernung zum Ziel in Metern.
     * @return Der berechnete Punktestand.
     */
    public static int getScore(double distance) {
        int result;

        // Überprüfen, ob die Entfernung im gültigen Bereich liegt
        if (distance < MAX_DISTANC && distance > MIN_DISTANZ) {

            // Berechnung der Teile für die Punkteformel
            double partOne = log(MAX_DISTANC / MIN_DISTANZ);
            double partTwo = log(MAX_DISTANC / distance);
            double partThree = MAX_POINTS / partOne;

            // Berechnung des Ergebnisses unter Berücksichtigung des Aufrundens
            result = (int) ceil(partTwo * partThree);
        } else {
            // Punktzahl ist 0, wenn die Entfernung außerhalb des gültigen Bereichs liegt
            if (distance > MAX_DISTANC) {
                result = 0;
            } else {
                // Maximalpunktzahl, wenn die Entfernung kleiner als 10 Meter ist
                result = MAX_POINTS;
            }
        }

        return result;
    }

    /**
     * Konvertiert eine Koordinatenzeichenfolge in das Dezimalformat.
     *
     * @param coordinate Die Koordinatenzeichenfolge im Format "Grad/Minute/Sekunde".
     * @return Die Koordinate im Dezimalformat.
     * @throws CorruptedDataException Wenn die Koordinatenzeichenfolge korrupt oder ungültig ist.
     */
    public static double convertToDecimal(String coordinate) throws CorruptedDataException {
        String[] parts = coordinate.split(",");

        // Überprüfen, ob die Koordinatenzeichenfolge ausreichend Teile hat
        if (parts.length >= COORDINATE_SIZE) {
            double degrees = Double.parseDouble(parts[0].split("/")[0]);
            double minutes = Double.parseDouble(parts[1].split("/")[0]);
            double seconds = Double.parseDouble(parts[2].split("/")[0]);

            // Berechnung der Koordinate im Dezimalformat
            return degrees + (minutes / MIN) + (seconds / SECS);
        } else {
            // Fehler, wenn die Koordinatenzeichenfolge nicht den erwarteten Aufbau hat
            throw new CorruptedDataException();
        }
    }
}
