package com.gse23.fspreng;

import static com.gse23.fspreng.GameView.GAME_VIEW;
import static com.gse23.fspreng.GameView.PRINT_LATITUDE;
import static com.gse23.fspreng.GameView.PRINT_LONGITUDE;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class GetRandonPic {

    public static ImageInfo get(Images pics) {
        Log.d("GetRandonPic.get()", "started");

        // Check if there are images available
        if (pics.length() == 0) {
            return null; // or handle the case where there are no images
        }

        // Generate a random index
        Random random = new Random();
        int index = random.nextInt(pics.length());

        // Retrieve the image at the random index
        ImageInfo pic = pics.pos(index);

        // Delete the chosen image
        pics.deleteImage(pic.picname);

        return pic;
    }

}
