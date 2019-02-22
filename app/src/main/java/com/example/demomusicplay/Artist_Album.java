package com.example.demomusicplay;


public class Artist_Album {
    private String artist_album;
    private String id;

    public Artist_Album(String songArtist,String Artistid){
        artist_album=songArtist;
        id = Artistid;
    }

    public String getArtist_album(){return artist_album;}
    public String getId(){return id;}
}
