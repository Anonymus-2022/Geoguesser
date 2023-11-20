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
     * Ein konstruktor,der nur der vollständigkeit halber existiert.
     */
    protected CalcStuff(){}

    /**
     * Berechnet den Punktestand basierend auf der Entfernung zum Ziel.
     *
     * @param distance Die Entfernung zum Ziel in Metern.
     * @return Der berechnete Punktestand.
     */
    public static int getScore(double distance) {
        int result;

        // Überprüfen, ob die Entfernung im gültigen Bereich liegt
        if (distance < 10000 && distance > 10) {
            final int maxPoints = 5000;
            final double maxDistance = 10000;
            final double minDistance = 10;

            // Berechnung der Teile für die Punkteformel
            double partOne = log(maxDistance / minDistance);
            double partTwo = log(maxDistance / distance);
            double partThree = maxPoints / partOne;

            // Berechnung des Ergebnisses unter Berücksichtigung des Aufrundens
            result = (int) ceil(partTwo * partThree);
        } else {
            // Punktzahl ist 0, wenn die Entfernung außerhalb des gültigen Bereichs liegt
            if (distance > 10000) {
                result = 0;
            } else {
                // Maximalpunktzahl, wenn die Entfernung kleiner als 10 Meter ist
                result = 5000;
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
        if (parts.length >= 3) {
            double degrees = Double.parseDouble(parts[0].split("/")[0]);
            double minutes = Double.parseDouble(parts[1].split("/")[0]);
            double seconds = Double.parseDouble(parts[2].split("/")[0]);

            // Berechnung der Koordinate im Dezimalformat
            return degrees + (minutes / 60) + (seconds / 3600);
        } else {
            // Fehler, wenn die Koordinatenzeichenfolge nicht den erwarteten Aufbau hat
            throw new CorruptedDataException();
        }
    }
}
