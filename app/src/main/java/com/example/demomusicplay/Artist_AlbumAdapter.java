package com.example.demomusicplay;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SAM on 06-Jul-18.
 */
public class Artist_AlbumAdapter extends BaseAdapter {
    private ArrayList<Artist_Album> songs;
    private LayoutInflater songInf;
    ImageView songimg;
    //constructor
    Context context;
    public Artist_AlbumAdapter(Context c, ArrayList<Artist_Album> theSongs){
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
                (R.layout.artist, parent, false);
        //get title and artist views
        TextView artistView = (TextView)songLay.findViewById(R.id.artisttext);
        songimg = (ImageView)songLay.findViewById(R.id.artistimg);

        //get song using position


        Artist_Album currSong = songs.get(position);
        if(position!=0){
            String path = currSong.getId();
            getImage(path);
        }
        //get title and artist strings
        String art = currSong.getArtist_album();

        artistView.setText(art);

        //set position as tag
        songLay.setTag(position);
        return songLay;
    }
    public void getImage(String artistid){

        Cursor c = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?", new String[]{artistid}, null);
        if(c.moveToFirst()){
            String path = c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            if(path!=null){
                Bitmap art = BitmapFactory.decodeFile(path);
                songimg.setImageBitmap(art);
            }else{
                songimg.setImageResource(R.drawable.prakhar);
            }
        }c.close();
    }
}
