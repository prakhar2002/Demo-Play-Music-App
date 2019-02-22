package com.example.demomusicplay;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class Player extends Activity {

    MediaPlayer mp;
    ArrayList<File> mySongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        TextView text = (TextView)findViewById(R.id.text);
        Intent i = getIntent();
        Bundle b =i.getExtras();
        // mySongs =(ArrayList) b.getParcelableArrayList("song");

        int postion = b.getInt("pos",0);
     /*   mp = MediaPlayer.create(getApplicationContext(), uri);
        mp.start();*/
    }


}

