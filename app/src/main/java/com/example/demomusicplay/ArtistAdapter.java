package com.example.demomusicplay;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by SAM on 03-Jul-18.
 */
public class ArtistAdapter extends BaseAdapter {
    //song list and layout
    private ArrayList<Artists> songs;
    private LayoutInflater songInf;
    BitmapFactory.Options bfo = new BitmapFactory.Options();
    ArrayList<Artistimg> img;
    MediaMetadataRetriever mmr ;
    private byte[] rawArt;
    Context context;
    //constructor
    public ArtistAdapter(Context c, ArrayList<Artists> theSongs){
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
        ImageView songimg = (ImageView)songLay.findViewById(R.id.artistimg);
        img = new ArrayList<Artistimg>();

        //get song using position
        Artists currSong = songs.get(position);
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
        String art = currSong.getArtist();

        artistView.setText(art);
//        Artistimg curr = img.get(position);

        //set position as tag
        songLay.setTag(position);
        return songLay;
    }


}
