package com.example.demomusicplay;

public class Album {
    private String album;
    private String location;

    public Album(String musicalbum,String locationimg){
        album=musicalbum;
        location=locationimg;
    }

    public String getAlbum(){return album;}
    public String getAlbumart(){return location;}
}

