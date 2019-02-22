package com.example.demomusicplay;

public class Artist_Allsongs {
    private String artist_album_song;
    private String artist_album_location;
    private String album_artist;

    public Artist_Allsongs(String songArtist,String album_location,String artist){
        artist_album_song=songArtist;
        artist_album_location=album_location;
        album_artist=artist;
    }

    public String getArtist_album_song(){return artist_album_song;}
    public String getArtist_album_location(){return artist_album_location;}
    public String getAlbum_artist(){return album_artist;}
}
