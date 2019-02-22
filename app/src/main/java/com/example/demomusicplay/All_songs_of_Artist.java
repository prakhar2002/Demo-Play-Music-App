package com.example.demomusicplay;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class All_songs_of_Artist extends Activity {

    ListView artist__view;
    String Artist;
    ArrayList<Artist_Allsongs> artist_albumList;
    int x=0;
    TextView artist__name;
    TextView title__name;
    ImageButton play;
    int z=0;
    DataBase dbHelper;
    SQLiteDatabase Db;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_songs_of__artist);
        artist__view =(ListView)findViewById(R.id.artists__songs);
        title__name =(TextView)findViewById(R.id.song_title);
        artist__name =(TextView)findViewById(R.id.song_artist);
        play =(ImageButton)findViewById(R.id.playid);
        RelativeLayout rl =(RelativeLayout)findViewById(R.id.playmusiclayout);
        dbHelper = new DataBase(this);
        Bundle bundle = getIntent().getExtras();
        Artist= bundle.getString("Artist");
        TextView artistname = (TextView) findViewById(R.id.artist__name);
        artistname.setText(Artist);
        artist_albumList = new ArrayList<Artist_Allsongs>();
        getsong();
        Collections.sort(artist_albumList, new Comparator<Artist_Allsongs>() {
            @Override
            public int compare(Artist_Allsongs lhs, Artist_Allsongs rhs) {
                String s1 = lhs.getArtist_album_song();
                String s2 = rhs.getArtist_album_song();
                return s1.compareToIgnoreCase(s2);
            }
        });
        Artist_Allsongs_Adapter adapter = new Artist_Allsongs_Adapter(this,artist_albumList);
        artist__view.setAdapter(adapter);

        artist__view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Db = dbHelper.getWritableDatabase();
                Cursor cursorr = Db.query(Record.RecordTask.Table,
                        null, null, null, null, null, null);
                if (cursorr.moveToFirst()) {
                    do {
                        String query = String.format("DELETE FROM %s ",
                                Record.RecordTask.Table);
                        Log.d(Record.TAG, "deleteAllNames: query" + query);
                        Db.execSQL(query);
                    } while (cursorr.moveToNext());
                    DataBaseCall(Db, position);
                } else {
                    DataBaseCall(Db, position);
                }
                x++;
                layoutcall(x);
                Artist_Allsongs curr = artist_albumList.get(position);
                String title = curr.getArtist_album_song();
                String artist = curr.getAlbum_artist();
                title__name.setText(title);
                artist__name.setText(artist);
                intent = new Intent(All_songs_of_Artist.this, Playsong.class).putExtra("title",title).putExtra("artist",artist);

            }
        });
        layoutcall(x);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (z == 0) {
                    play.setImageResource(R.drawable.playmusic);
                    z++;
                } else if (z != 0) {
                    play.setImageResource(R.drawable.pausemusic);
                    z = 0;
                }
            }
        });
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }

    public void DataBaseCall(SQLiteDatabase Db,int position){
        ContentValues values = new ContentValues();
        Artist_Allsongs currrr = artist_albumList.get(position);
        String title = currrr.getArtist_album_song();
        String artist = currrr.getAlbum_artist();
        String location = currrr.getArtist_album_location();
        values.put(Record.RecordTask.COL2, title);
        values.put(Record.RecordTask.COL3, artist);
        values.put(Record.RecordTask.COL4, location);
        Db.insertWithOnConflict(Record.RecordTask.Table, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        for (int i = 0; i < artist_albumList.size(); i++) {
            if(i!=position){
                Artist_Allsongs currr = artist_albumList.get(i);
                String titlee = currr.getArtist_album_song();
                String artistt = currr.getAlbum_artist();
                String locationn = currr.getArtist_album_location();
                values.put(Record.RecordTask.COL2, titlee);
                values.put(Record.RecordTask.COL3, artistt);
                values.put(Record.RecordTask.COL4, locationn);
                Db.insertWithOnConflict(Record.RecordTask.Table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            }
        }
        Db.close();

        SQLiteDatabase Datab = dbHelper.getReadableDatabase();
        Cursor cursor = Datab.query(Record.RecordTask.Table,
                new String[]{Record.RecordTask.COL2, Record.RecordTask.COL3, Record.RecordTask.COL4}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(Record.RecordTask.COL2);
            String task = cursor.getString(index);
            int ind = cursor.getColumnIndex(Record.RecordTask.COL3);
            String tas = cursor.getString(ind);
            int in = cursor.getColumnIndex(Record.RecordTask.COL4);
            String ta = cursor.getString(in);

            Toast.makeText(All_songs_of_Artist.this, task + "\n" + tas + "\n" + ta, Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        Datab.close();

    }

    public  void layoutcall(int check){
        if(check==0){
            View root = findViewById(R.id.playmusiclayout);
            root.setVisibility(View.GONE);
        }else{
            View root = findViewById(R.id.playmusiclayout);
            root.setVisibility(View.VISIBLE);
        }
    }
    public void getsong(){
        String selection = MediaStore.Audio.Media.ARTIST+"=?";
        String[] selectedArtist = {Artist};
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,selection,selectedArtist,null);
        if(cursor!=null && cursor.moveToFirst()){
            //get columns
            int titleColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int locationColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int artistColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Albums.ARTIST);
            //    int durationColumn = cursor.getColumnIndex
            //          (MediaStore.Audio.Media.DURATION);
            //add songs to list
            do {
                String thisLocation = cursor.getString(locationColumn);
                String thisTitle = cursor.getString(titleColumn);
                String thisArtist = cursor.getString(artistColumn);
                // String thisDuration = cursor.getString(durationColumn);

                artist_albumList.add(new Artist_Allsongs(thisTitle,thisLocation,thisArtist));

            }
            while (cursor.moveToNext());
        }

    }

}
