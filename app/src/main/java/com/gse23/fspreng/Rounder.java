package com.gse23.fspreng;

/**
 * Diese Klasse enthält eine aus GtHub entnommene Methode zum Runden.
 */
public class Rounder {

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
        String temporaryNumber = "00" + number + "00";
        if (placeValue == 1) {
            int truncated = (int) number;
            double decimalValue = number - truncated;
            if (decimalValue < 0.5) {
                return truncated;
            } else {
                return truncated + 1;
            }
        }
        if (placeValue > 1) {
            int power = 0;
            while (placeValue > 1) {
                placeValue = placeValue / 10;
                power++;
            }
            temporaryNumber = temporaryNumber.substring(0, (temporaryNumber.indexOf(".") - power
                    + 1));
            int decidingNumber = Integer.parseInt(temporaryNumber.substring(
                    temporaryNumber.length() - 1));
            temporaryNumber = temporaryNumber.substring(0, temporaryNumber.length() - 1);
            int significantFigures = Integer.parseInt(temporaryNumber);
            if (decidingNumber > 4) {
                significantFigures = Integer.parseInt(temporaryNumber) + 1;
            }
            for (int i = 0; i < power; i++) {
                significantFigures = significantFigures * 10;
            }
            return significantFigures;
        }

        if (placeValue < 1) {
            int power = 1;
            while (placeValue < 1) {
                placeValue = placeValue * 10;
                power++;
            }
            temporaryNumber = temporaryNumber.substring(0, (temporaryNumber.indexOf(".")
                    + power + 1));
            int decidingNumber = Integer.parseInt(temporaryNumber.substring(
                    temporaryNumber.length() - 1));
            temporaryNumber = temporaryNumber.substring(0, temporaryNumber.length() - 1);
            double significantFigures = Double.parseDouble(temporaryNumber);
            if (decidingNumber > 4) {
                double roundingAddition = 1;
                for (int i = 1; i < power; i++) {
                    roundingAddition = roundingAddition / 10;
                }
                significantFigures += roundingAddition;
            }
            return significantFigures;
        }
        return 0;
    }
}
