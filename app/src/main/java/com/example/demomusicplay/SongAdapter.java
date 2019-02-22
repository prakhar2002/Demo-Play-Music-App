package com.example.demomusicplay;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This is demo code to accompany the Mobiletuts+ series:
 * Android SDK: Creating a Music Player
 *
 * Sue Smith - February 2014
 */

public class SongAdapter extends BaseAdapter {

    //song list and layout
    private ArrayList<Song> songs;
    ArrayList<Artistimg> img = new ArrayList<Artistimg>();
    private LayoutInflater songInf;
    MediaMetadataRetriever mmr ;
    private byte[] rawArt;
    Bitmap art;
    BitmapFactory.Options bfo = new BitmapFactory.Options();
    Context context;
    //constructor
    public SongAdapter(Context c, ArrayList<Song> theSongs){
        songs=theSongs;
        songInf=LayoutInflater.from(c);
        context =c;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //map to song layout
        LinearLayout songLay = (LinearLayout)songInf.inflate
                (R.layout.song, parent, false);
        //get title and artist views
        TextView songView = (TextView)songLay.findViewById(R.id.song_title);
        TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
        TextView durationView = (TextView)songLay.findViewById(R.id.song_duration);
        ImageView songimg = (ImageView)songLay.findViewById(R.id.imageView);
        //get song using position
        Song currSong = songs.get(position);
        Cursor c = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?", new String[]{currSong.getAlbumart()}, null);
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
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getArtist());
        durationView.setText(currSong.getDuration());
        //set position as tag
        songLay.setTag(position);
        return songLay;
    }


}

