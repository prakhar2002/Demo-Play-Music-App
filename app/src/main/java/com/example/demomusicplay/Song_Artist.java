package com.example.demomusicplay;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Song_Artist extends Activity {

    GridView artist__view;
    String Artist;
    String Artistid;
    ArrayList<Artist_Album> artist_albumList;
    ImageView artistimg;
    int check=0;
    DataBase dataBase;
    SQLiteDatabase Db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_artist);
        artist__view = (GridView)findViewById(R.id.artists__songs);
        Bundle bundle = getIntent().getExtras();
        Artist= bundle.getString("Artist");
        Artistid =bundle.getString("id");
        TextView artistname = (TextView) findViewById(R.id.artist__name);
        artistname.setText(Artist);
        dataBase = new DataBase(this);
        artistimg=(ImageView)findViewById(R.id.artist__Album);
        artist_albumList = new ArrayList<Artist_Album>();
        getImage(Artistid);

        //  check = DatabaseCheck();
        layoutcall(check);
        getsong();
        Collections.sort(artist_albumList, new Comparator<Artist_Album>() {
            @Override
            public int compare(Artist_Album lhs, Artist_Album rhs) {
                String s1 = lhs.getArtist_album();
                String s2 = rhs.getArtist_album();
                if (s1.equals("All Songs") || s2.equals("All Songs")) {

                    return 0;
                } else
                    return s1.compareToIgnoreCase(s2);
            }
        });
        setArtist_albumList(artist_albumList);
        setArtist_albumList(artist_albumList);
        setArtist_albumList(artist_albumList);

        Artist_AlbumAdapter Adt = new Artist_AlbumAdapter(this, artist_albumList);
        artist__view.setAdapter(Adt);
        artist__view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent i = new Intent(Song_Artist.this,All_songs_of_Artist.class).putExtra("Artist",Artist);
                    startActivity(i);
                }else{
                    Artist_Album artist_album = artist_albumList.get(position);
                    String artistalbum=artist_album.getArtist_album();
                    Intent intent = new Intent(Song_Artist.this,Album_song_of_Artist.class).putExtra("Album",artistalbum);
                    startActivity(intent);
                }
            }
        });
    }
    /*public int DatabaseCheck(){
        Cursor c = Db.query(Record.RecordTask.Table, null, null, null, null, null, null);
        if(c.getCount()>0){
            c.close();
           return 5;
        }
        else{
            c.close();
            return 0;}
    }*/

    public  void layoutcall(int check){
        if(check==0){
            View root = findViewById(R.id.playmusiclayout);
            root.setVisibility(View.GONE);
        }else{
            View root = findViewById(R.id.playmusiclayout);
            root.setVisibility(View.VISIBLE);
        }
    }
    public void setArtist_albumList(ArrayList<Artist_Album> artist_albumlist ){

        if(artist_albumlist.size()>2){
            for(int i=0; i<artist_albumlist.size();i++){
                Artist_Album currArtist = artist_albumlist.get(i);
                String nowArtist = currArtist.getArtist_album();
                for(int j=i; j<artist_albumlist.size();j++){
                    Artist_Album nextArtist = artist_albumlist.get(j);
                    String nextnowArtist = nextArtist.getArtist_album();
                    if((nowArtist.equals(nextnowArtist))&&j!=i){
                        // Toast.makeText(this,nextnowArtist+nowArtist,Toast.LENGTH_SHORT).show();
                        artist_albumlist.remove(i);
                        //  j++;
                    }
                }
            }}

    }

    public void getsong(){
        String selection = MediaStore.Audio.Media.ARTIST+"=?";
        String[] selectedArtist = {Artist};
        String allsongs = "All Songs";
        artist_albumList.add(0,new Artist_Album(allsongs,null));
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,selection,selectedArtist,null);
        if(cursor!=null && cursor.moveToFirst()){
            //get columns
            //int titleColumn = cursor.getColumnIndex
            //      (android.provider.MediaStore.Audio.Media.TITLE);
            //int locationColumn = cursor.getColumnIndex
            //      (MediaStore.Audio.Media.DATA);
            int artistColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM);
            int idColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ID);
            //add songs to list
            do {
                // String thisLocation = cursor.getString(locationColumn);
                // String thisTitle = cursor.getString(titleColumn);
                String thisArtist = cursor.getString(artistColumn);
                String thisid = cursor.getString(idColumn);

                artist_albumList.add(new Artist_Album(thisArtist,thisid));

            }
            while (cursor.moveToNext());
        }

    }

    public  void getImage(String artistid){
        Cursor c = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?", new String[]{artistid}, null);
        if(c.moveToFirst()){
            String path = c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            if(path!=null){
                Bitmap art = BitmapFactory.decodeFile(path);
                artistimg.setImageBitmap(art);
            }else{
                artistimg.setImageResource(R.drawable.prakhar);
            }c.close();
        }
    }

}

