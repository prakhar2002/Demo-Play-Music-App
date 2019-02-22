package com.example.demomusicplay;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SAM on 14-Jul-18.
 */
public class One_song_Adapter  extends BaseAdapter{

    private ArrayList<One_song> songs;
    private LayoutInflater songInf;

    //constructor
    public One_song_Adapter(Context c, ArrayList<One_song> theSongs){
        songs=theSongs;
        songInf=LayoutInflater.from(c);
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
                (R.layout.musicplay, parent, false);
        //get title and artist views
        TextView songView = (TextView)songLay.findViewById(R.id.song_title);
        TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
        //  TextView durationView = (TextView)songLay.findViewById(R.id.song_duration);

        //get song using position
        One_song currSong = songs.get(position);
        //get title and artist strings
        songView.setText(currSong.gettitle());
        artistView.setText(currSong.getartist());

//        durationView.setText(currSong.getDuration());
        //set position as tag
        songLay.setTag(position);
        return songLay;
    }
}

