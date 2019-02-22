package com.example.demomusicplay;

import android.graphics.Bitmap;

public class Song {

    private String location;
    private String title;
    private String artist;
    private String duration;
    private String albumart;


    public Song(String songlocation, String songTitle, String songArtist, String songduration,String albumid){
        location=songlocation;
        title=songTitle;
        artist=songArtist;
        duration = songduration;
        albumart = albumid;

    }

    public String getlocation(){return location;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getDuration(){return duration;}
    public String getAlbumart(){return albumart;}

}