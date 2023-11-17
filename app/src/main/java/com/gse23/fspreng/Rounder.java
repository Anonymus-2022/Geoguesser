package com.gse23.fspreng;

public class Rounder {

    public static double round(double number, double placeValue)
    {
        String temporaryNumber = "00" + number + "00";
        if (placeValue == 1)
        {
            int truncated = (int) number;
            double decimalValue = number - truncated;
            if (decimalValue < 0.5)
            {
                return truncated;
            }
            else
            {
                return truncated + 1;
            }
        }
        if (placeValue > 1)
        {
            int power = 0;
            while (placeValue > 1)
            {
                placeValue = placeValue / 10;
                power++;
            }
            temporaryNumber = temporaryNumber.substring(0, (temporaryNumber.indexOf(".") - power + 1));
            int decidingNumber = Integer.parseInt(temporaryNumber.substring(temporaryNumber.length() - 1));
            temporaryNumber = temporaryNumber.substring(0, temporaryNumber.length() - 1);
            int significantFigures = Integer.parseInt(temporaryNumber);
            if (decidingNumber > 4)
            {
                significantFigures = Integer.parseInt(temporaryNumber) + 1;
            }
            for (int i = 0; i < power; i++)
            {
                significantFigures = significantFigures * 10;
            }
            return significantFigures;
        }

        if (placeValue < 1)
        {
            int power = 1;
            while (placeValue < 1)
            {
                placeValue = placeValue * 10;
                power++;
            }
            temporaryNumber = temporaryNumber.substring(0, (temporaryNumber.indexOf(".") + power + 1));
            int decidingNumber = Integer.parseInt(temporaryNumber.substring(temporaryNumber.length() - 1));
            temporaryNumber = temporaryNumber.substring(0, temporaryNumber.length() - 1);
            double significantFigures = Double.parseDouble(temporaryNumber);
            if (decidingNumber > 4)
            {
                double roundingAddition = 1;
                for (int i = 1; i < power; i++)
                {
                    roundingAddition = roundingAddition / 10;
                }
                significantFigures += roundingAddition;
            }
            return significantFigures;
        }
        return 0;
    }
}
