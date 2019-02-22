package com.example.demomusicplay;

public class Artists {
    private String artist;
    private String location;

    public Artists(String songArtist,String locationimg){
        artist=songArtist;
        location=locationimg;
    }

    public String getArtist(){return artist;}
    public String getAlbumart(){return location;}
}

