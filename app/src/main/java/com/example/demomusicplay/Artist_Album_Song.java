package com.example.demomusicplay;

public class Artist_Album_Song {
    private String artist_album_song;
    private String artist_album_location;
    private String album_artist;
    private String album_artistid;


    public Artist_Album_Song(String songArtist,String album_location,String artist,String id){
        artist_album_song=songArtist;
        artist_album_location=album_location;
        album_artist=artist;
        album_artistid =id;
    }

    public String getArtist_album_song(){return artist_album_song;}
    public String getArtist_album_location(){return artist_album_location;}
    public String getAlbum_artist(){return album_artist;}
    public String getAlbum_artistid(){return album_artistid;}

}

