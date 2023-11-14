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

public class GameView extends AppCompatActivity {

    private final ArrayList<String> alreadyShown = new ArrayList<>();
    private Images pics;
    private String albumChoice;

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

        String choosenAlbum = "AlbumChoice";
        albumChoice = getIntent().getStringExtra(choosenAlbum);
        albChoice.setText(albumChoice);

        try {
            pics = GetAssetContents.get(getApplicationContext(), albumChoice);
        } catch (EmpyAlbumException ignored) {
            finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Drawable bild = null;
        int index = 0;

        final Images.ImageInfo[] pic = new Images.ImageInfo[1];
        while (true) {
            try {
                pic[0] = pics.pos(index);

                if (!pics.emptyAlbum(albumChoice)) {
                    Random random = new Random();
                    int randomNum = random.nextInt(pics.length());

                    if (randomNum == index) {
                        alreadyShown.add(pic[0].picname);
                        pics.deleteImage(pic[0].picname);
                        Log.i("already shown", String.valueOf(alreadyShown));

                        // Bild laden
                        try (InputStream bildstream = getAssets().open(pic[0].filepath)) {
                            bild = Drawable.createFromStream(bildstream, pic[0].picname);
                            Log.i("PrintedAlbum", pic[0].picname);
                            Log.i("PrintFilepath", pic[0].filepath);
                            Log.i("GameView", "Print Longitude: " + pic[0].longitude);
                            Log.i("GameView", "Print Latitude: " + pic[0].latitude);
                        }

                        break; // Schleife beenden, da ein Bild ausgewählt wurde
                    }
                }

                // Schleifenindex aktualisieren
                if (index == pics.length() - 1) {
                    index = 0;
                } else {
                    index++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        image.setImageDrawable(bild);
        image.setContentDescription(pic[0].imageDescription);

        spielAnleitung.setOnClickListener(v -> {
            Intent intent = new Intent(this, SpielAnleitung.class);
            startActivity(intent);
        });

        newPic.setOnClickListener(v -> {
            Drawable bild1 = null;
            int index1 = 0;
            int randomNum1 = 0;

            while (true) {
                String alb = null;

                if (pics.length() != 0) {
                    pic[0] = pics.pos(index1);
                    alb = pic[0].album;
                }

                if (pics.length() != 0) {
                    assert alb != null;
                    if (alb.equals(albumChoice)) {
                        Random random = new Random();
                        randomNum1 = random.nextInt(pics.length());
                        if (randomNum1 == index1) {
                            try {
                                try (InputStream bildstream1 = getAssets().open(pic[0].filepath)) {
                                    bild1 = Drawable.createFromStream(bildstream1, pic[0].picname);
                                    Log.i("GameView", "Printed Album: " + pic[0].picname);
                                    Log.i("GameView", "Print Filepath: " + pic[0].filepath);
                                    Log.i("GameView", "Print Longitude: " + pic[0].longitude);
                                    Log.i("GameView", "Print Latitude: " + pic[0].latitude);
                                }

                                pics.deleteImage(pic[0].picname);
                                Log.i("GameView", "Already shown images count: " + pics.length());
                                break;
                            } catch (IOException e) {
                                Log.e("GameView", "Error loading image: " + e.getMessage());
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    if (index1 == pics.length() - 1) {
                        index1 = 0;
                    } else {
                        index1++;
                    }
                } else {
                    Log.i("GameView", "No images left to show.");
                    AlertDialog.Builder allImagesSeen = new AlertDialog.Builder(this);
                    allImagesSeen.setTitle("There are no images left to show!");
                    allImagesSeen.setPositiveButton("OK", (dialog, id) -> {
                        dialog.dismiss();
                        finish();
                    });
                    allImagesSeen.show();
                    break;
                }
            }

            image.setImageDrawable(bild1);
            image.setContentDescription(pic[0].imageDescription);
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
