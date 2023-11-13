package com.gse23.fspreng;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gse23.fspreng.exception.EmpyAlbumException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * GameView erzeugt eine noch mehr oder weniger nutzlose Aktivity die Momentan überwiegend als
 * Platzhalter für spätere Aktivitäten.
 */
public class GameView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);
        Button spielAnleitung = findViewById(R.id.game_rules);
        TextView albChoice = findViewById(R.id.albChoice);
        Button goBack = findViewById(R.id.go_back);
        ImageView image = findViewById(R.id.image);
        Button newPic = findViewById(R.id.newPic);
        Button guess = findViewById(R.id.guess);

        ArrayList<String> alreadyShown= new ArrayList<>();
        String choosenAlbum = "AlbumChoice";
        String albumChoice = getIntent().getStringExtra(choosenAlbum);
        albChoice.setText(albumChoice);
        Images pics = null;
        try {
            pics = GetAssetContents.get(getApplicationContext(), albumChoice);
        } catch (EmpyAlbumException ignored) {
            finish();
        }

        /*
        Da bilder, die über keine koordinaten verfügen werden gar nicht erst eingespeichert.
        daher ist die geforderte fehlerbehandlung redundant
         */
        assert albumChoice != null;
        Log.i(choosenAlbum, albumChoice);
        //String enter = "\n";
        //StringBuilder pictures = new StringBuilder();
        // Hier wird darauf verzichtet, zu prüfen ob das Album leer ist, da Solche Alben gar nicht
        // erst erfasst werden
        //for (int index = 0; index < pics.length(); index++) {
        //    Images.ImageInfo pic = pics.pos(index);
        //    String alb = pic.album;
        //    if (alb.equals(albumChoice)) {
        //        pictures.append(pic.picname).append(enter).append(pic.imageDescription).
        //                append(enter).append(pic.latitude).append(enter).append(pic.longitude).
        //                append(enter).append(enter);
        //    }
        //}
        //TextView albContent = findViewById(R.id.albContent);
        //albContent.setText(pictures.toString());



        Drawable bild = null;
        InputStream bildstream = null;
        int index = 0;
        int randomNum = 0;
        while (true) {
            Images.ImageInfo choosenPic = null;
            Boolean isChoosen = false;
            Log.i("ImageChoice" , "" + index + "," + randomNum);
            Images.ImageInfo pic = pics.pos(index);
            String alb = pic.album;
            if (!pics.emptyAlbum(albumChoice)) {
                Random random = new Random();
                randomNum = random.nextInt(pics.length());
                if (randomNum == index) {
                    isChoosen = true;
                    alreadyShown.add(pic.picname);
                    pics.deleteImage(pic.picname);
                    Log.i("already shown", String.valueOf(alreadyShown));
                }
            }
            if (index == pics.length()-1) {
                index = 0;
            } else {
                index++;
            }
            if (isChoosen){
                try {
                    bildstream = getAssets().open(pic.filepath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                bild = Drawable.createFromStream(bildstream, pic.picname);
                Log.i("PrintedAlbum", pic.picname);
                Log.i("PrintFilepath", pic.filepath);
                break;
            }
        }
        image.setImageDrawable(bild);


        spielAnleitung.setOnClickListener(v -> {
            Intent intent = new Intent(this, SpielAnleitung.class);
            startActivity(intent);
        });

        Images finalPics = pics;
        newPic.setOnClickListener(v -> {
            Drawable bild1 = null;
            InputStream bildstream1 = null;
            int index1 = 0;
            int randomNum1 = 0;
            while (true) {
                Images.ImageInfo choosenPic = null;
                Boolean isChoosen = false;
                Log.i("ImageChoice" , "" + index1 + "," + randomNum1);
                Images.ImageInfo pic = finalPics.pos(index1);
                String alb = pic.album;
                if (!finalPics.emptyAlbum(albumChoice)){
                    if (alb.equals(albumChoice)) {
                        Random random = new Random();
                        randomNum1= random.nextInt(finalPics.length());
                        if (randomNum1 == index1) {
                            choosenPic = pic;
                            isChoosen = true;
                            finalPics.deleteImage(pic.picname);
                            Log.i("already shown", String.valueOf(finalPics.length()));
                        }
                    }
                }
                if (index1 == finalPics.length()-1) {
                    index1 = 0;
                } else {
                    index1++;
                }
                if (isChoosen){
                    try {
                        bildstream1 = getAssets().open(pic.filepath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    bild1 = Drawable.createFromStream(bildstream1, pic.picname);
                    Log.i("PrintedAlbum", pic.picname);
                    Log.i("PrintFilepath", pic.filepath);
                    break;
                }
            }
            image.setImageDrawable(bild1);
        });



        goBack.setOnClickListener(v -> {
            Log.i("Status", "going to: change to AlbumChoice");
            Intent intent = new Intent(GameView.this, StartBild.class);
            startActivity(intent);
        });

        guess.setOnClickListener(v -> {
            Intent intent = new Intent(this, SetTip.class);
            startActivity(intent);
        });


    }
}
