package com.example.demomusicplay;

public class One_song {
    private String location;
    private String title;
    private String artist;
    public One_song(String songlocation, String songTitle, String songArtist){
        location=songlocation;
        title=songTitle;
        artist=songArtist;
    }


    public String getlocation(){return location;}
    public String gettitle(){return title;}
    public String getartist(){return artist;}

}
