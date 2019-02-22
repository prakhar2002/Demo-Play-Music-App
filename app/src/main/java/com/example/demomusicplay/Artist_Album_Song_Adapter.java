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
 * Created by SAM on 07-Jul-18.
 */
public class Artist_Album_Song_Adapter extends BaseAdapter{
    private ArrayList<Artist_Album_Song> songs;
    private LayoutInflater songInf;
    Context context;

    //constructor
    public Artist_Album_Song_Adapter(Context c, ArrayList<Artist_Album_Song> theSongs){
        songs=theSongs;
        songInf=LayoutInflater.from(c);
        context=c;
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
        TextView artistView =(TextView)songLay.findViewById(R.id.song_artist);
        ImageView songimg =(ImageView)songLay.findViewById(R.id.imageView);
        //get song using position
        Artist_Album_Song currSong = songs.get(position);
        //get title and artist strings
        String sng = currSong.getArtist_album_song();
        String art = currSong.getAlbum_artist();

        songView.setText(sng);
        artistView.setText(art);
        Cursor c = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?", new String[]{currSong.getAlbum_artistid()}, null);
        if(c.moveToFirst()){
            String path = c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            if(path!=null){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds=false;
                options.inPreferredConfig= Bitmap.Config.RGB_565;
                options.inDither=true;
                Bitmap img = BitmapFactory.decodeFile(path,options);
                songimg.setImageBitmap(img);
            }else{
                songimg.setImageResource(R.drawable.prakhar);
            }
        }c.close();
        //set position as tag
        songLay.setTag(position);
        return songLay;
    }
}
