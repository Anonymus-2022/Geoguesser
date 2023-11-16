package com.gse23.fspreng;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class ResultView extends AppCompatActivity {


    /**
     * Übernommen aus GitHub:
     * https://github.com/MichaelFarid/Rounding/blob/master/Rounding.java
     */
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
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view);
        Bundle get = getIntent().getExtras();

        TextView showDistacne = findViewById(R.id.distanz);
        assert get != null;
        double distance = (double) get.get("distance");
        Log.i("Distanz", distance + " km");
        Log.i("Distanz", distance * 1000 + " m");
        distance = round(distance, 0.01);
        showDistacne.setText("Distance between your guess an reallity:\n\n"
        + distance + " km\n\nYour score:\n" + get.get("score") + "/5000");

        TextView show_Link = findViewById(R.id.showLink);
        String showLink = (String) get.get("posLink");
        show_Link.setText(showLink);
        Log.i("positionlink", showLink);
        show_Link.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(showLink));
            startActivity(intent);
        });

        Button go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(v-> finish());
    }

}
