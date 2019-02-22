package com.example.demomusicplay;


import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Playsong extends Activity {

    ArrayList<Playmusic> artist_albumList;
    String Artist;
    String titlee;
    ImageView songimg;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playsong);
        Bundle bundle = getIntent().getExtras();
        Artist= bundle.getString("artist");
        titlee=bundle.getString("title");
        songimg = (ImageView)findViewById(R.id.imageView2);
        artist_albumList=new ArrayList<Playmusic>();
        TextView title_song=(TextView)findViewById(R.id.song_title);
        TextView artist_song=(TextView)findViewById(R.id.song_artist);

        title_song.setText(titlee);
        artist_song.setText(Artist);
        getsong();
        /*      Playmusic curr = artist_albumList.get(0);*/
        Cursor c = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?", new String[]{id}, null);
        if(c.moveToFirst()){
            String path = c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            if(path!=null){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds=false;
                options.inPreferredConfig= Bitmap.Config.RGB_565;
                options.inDither=true;
                Bitmap art = BitmapFactory.decodeFile(path,options);
                songimg.setImageBitmap(art);
            }else{
                songimg.setImageResource(R.drawable.prakhar);
            }
        }c.close();


    }



    public void getsong(){
        String selection = MediaStore.Audio.Media.TITLE+"=?";
        String[] selectedArtist = {titlee};
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,selection,selectedArtist,null);
        if(cursor!=null && cursor.moveToFirst()){
            //get columns
            //int titleColumn = cursor.getColumnIndex
            //      (android.provider.MediaStore.Audio.Media.TITLE);
            int locationColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
           /* int artistColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM);*/
            int idColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ID);
            int durationColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            //add songs to list
            do {
                // String thisLocation = cursor.getString(locationColumn);
                // String thisTitle = cursor.getString(titleColumn);
                //String thisArtist = cursor.getString(artistColumn);
                String thislocation = cursor.getString(locationColumn);
                String thisduration = cursor.getString(durationColumn);
                String thisid = cursor.getString(idColumn);
                //Toast.makeText(this,thisid,Toast.LENGTH_SHORT).show();
                id=thisid;

                artist_albumList.add(new Playmusic(thislocation,thisduration));

            }
            while (cursor.moveToNext());
        }

    }

}

