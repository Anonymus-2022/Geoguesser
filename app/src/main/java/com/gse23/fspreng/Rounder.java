package com.gse23.fspreng;

/**
 * Diese Klasse enthält eine aus GtHub entnommene Methode zum Runden.
 */
public class Rounder {

    /**
     * Konstante, welche zur Berechnung gebraucht wird.
     */
    static final int TEN = 10;
    /**
     * Konstante, welche zur Berechnung gebraucht wird.
     */
    static final int FOUR = 4;
    /**
     * Konstante, welche zur Berechnung gebraucht wird.
     */
    static final double ZERO_POINT_FIFE = 0.5;
    /**
     * Konstante, welche zur Berechnung gebraucht wird.
     */
    static final String NULL_NULL = "00";

    /**
     * der konstruktor existiert nur der vollständigkeit halber, da nie ein rounder Objekt erzeugt
     * wird.
     */
    protected Rounder() {
    }

    /**
     * Übernommen aus GitHub:
     * <a href="https://github.com/MichaelFarid/Rounding/blob/master/Rounding.java">...</a>
     *
     * @param number     Die Zahl, welche gerundet werden soll
     * @param placeValue exemplarische zahl, die als referenzwert für die menge an Nachkommastellen
     *                   zu stehen scheint
     * @return zurückgegeben wird die gerundete Zahl
     */
    public static double round(double number, double placeValue) {
        String temporaryNumber = NULL_NULL + number + NULL_NULL;
        if (placeValue == 1) {
            int truncated = (int) number;
            double decimalValue = number - truncated;
            if (decimalValue < ZERO_POINT_FIFE) {
                return truncated;
            } else {
                return truncated + 1;
            }
        }
        if (placeValue > 1) {
            int power = 0;
            while (placeValue > 1) {
                placeValue = placeValue / TEN;
                power++;
            }
            temporaryNumber = temporaryNumber.substring(0, (temporaryNumber.indexOf(".") - power
                    + 1));
            int decidingNumber = Integer.parseInt(temporaryNumber.substring(
                    temporaryNumber.length() - 1));
            temporaryNumber = temporaryNumber.substring(0, temporaryNumber.length() - 1);
            int significantFigures = Integer.parseInt(temporaryNumber);
            if (decidingNumber > FOUR) {
                significantFigures = Integer.parseInt(temporaryNumber) + 1;
            }
            for (int i = 0; i < power; i++) {
                significantFigures = significantFigures * TEN;
            }
            return significantFigures;
        }

        if (placeValue < 1) {
            int power = 1;
            while (placeValue < 1) {
                placeValue = placeValue * TEN;
                power++;
            }
            temporaryNumber = temporaryNumber.substring(0, (temporaryNumber.indexOf(".")
                    + power + 1));
            int decidingNumber = Integer.parseInt(temporaryNumber.substring(
                    temporaryNumber.length() - 1));
            temporaryNumber = temporaryNumber.substring(0, temporaryNumber.length() - 1);
            double significantFigures = Double.parseDouble(temporaryNumber);
            if (decidingNumber > FOUR) {
                double roundingAddition = 1;
                for (int i = 1; i < power; i++) {
                    roundingAddition = roundingAddition / TEN;
                }
                significantFigures += roundingAddition;
            }
            return significantFigures;
        }
        return 0;
    }
}
