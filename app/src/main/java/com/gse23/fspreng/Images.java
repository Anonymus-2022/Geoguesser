package com.gse23.fspreng;

import android.util.Log;

import java.util.ArrayList;

public class Images {


    public static class ImagesInfo {
        String picname;
        String album;
        String longitude;
        String latitude;
        String image_description;
        String filepath;


        ImagesInfo(String album, String picname, String lon, String lat, String imd, String filpa) {
            this.album = album;
            this.picname = picname;
            this.longitude = lon;
            this.latitude = lat;
            this.image_description = imd;
            this.filepath = filpa;
        }
    }
    public static ArrayList<ImagesInfo> infos;

    public static void addImage(ImagesInfo pic){
        infos.add(pic);
    }

    public int length(){
        return infos.size();
    }

    public ImagesInfo pos(int pos){
        return infos.get(pos);
    }


    Images(){
        infos = new ArrayList<>();
        Log.i("ImageStack", "ImageStack has been Created");
    }
}
