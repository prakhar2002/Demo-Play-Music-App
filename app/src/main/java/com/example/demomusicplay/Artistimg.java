package com.example.demomusicplay;

import android.graphics.Bitmap;

/**
 * Created by SAM on 17-Jul-18.
 */
public class Artistimg {
    Bitmap img ;
    public Artistimg(Bitmap image){
        img = image;
    }

    public Bitmap getImg(){return img;}
}