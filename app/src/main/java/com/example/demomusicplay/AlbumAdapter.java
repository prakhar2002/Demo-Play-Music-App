package com.example.demomusicplay;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
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
 * Created by SAM on 09-Jul-18.
 */
public class AlbumAdapter extends BaseAdapter{
    //song list and layout
    private ArrayList<Album> songs;
    private LayoutInflater songInf;

    Bitmap artimg;
    BitmapFactory.Options bfo = new BitmapFactory.Options();
    Context context;
    MediaMetadataRetriever mmr ;
    private byte[] rawArt;
    //constructor
    public AlbumAdapter(Context c, ArrayList<Album> theSongs){
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
        TextView songView = (TextView)songLay.findViewById(R.id.artisttext);
        ImageView songimg = (ImageView)songLay.findViewById(R.id.artistimg);

        //get song using position
        Album currSong = songs.get(position);
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
        //get title and artist strings
        songView.setText(currSong.getAlbum());

        //set position as tag
        songLay.setTag(position);
        return songLay;
    }
}

