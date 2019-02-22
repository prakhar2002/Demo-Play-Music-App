package com.example.demomusicplay;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.ArrayList;

public class Music_Service {
    ArrayList<Song> songList = new ArrayList<Song>();
    MediaPlayer mp;


    public  void songplay(int position, Context context, ArrayList<Song> arrayList){
        Song player = arrayList.get(position);
        String songTitle = player.getlocation();
        String songduration = player.getDuration();
        //           Toast.makeText(MainActivity.this, songTitle, Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse(songTitle);
        mp = MediaPlayer.create(context, uri);
        mp.start();

    }

}
