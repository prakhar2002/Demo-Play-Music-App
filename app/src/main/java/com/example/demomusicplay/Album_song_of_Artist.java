package com.example.demomusicplay;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Album_song_of_Artist extends AppCompatActivity {

    ListView artist__view;
    String Artist;
    ArrayList<Artist_Album_Song> artist_albumList;
    int x=0;
    TextView artist__name;
    TextView title__name;
    ImageButton play;
    ImageView songimg;
    ImageView imageView;
    int z=0;
    DataBase dbHelper;
    SQLiteDatabase Db;
    Intent intent;
    int imgcount;
    int check;
    String title ;
    String artist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_song_of__artist);
        artist__view = (ListView)findViewById(R.id.artists__Album_songs);
        title__name =(TextView)findViewById(R.id.song_title);
        artist__name =(TextView)findViewById(R.id.song_artist);
        play =(ImageButton)findViewById(R.id.playid);
        songimg =(ImageView)findViewById(R.id.imageView);
        imageView =(ImageView)findViewById(R.id.artist__Album_img);
        //imageView.setImageResource(R.drawable.rand);

        dbHelper = new DataBase(this);
        RelativeLayout rl =(RelativeLayout)findViewById(R.id.playmusiclayout);
        Bundle bundle = getIntent().getExtras();
        Artist= bundle.getString("Album");
        TextView artistname = (TextView) findViewById(R.id.artist__Album_name);
        artistname.setText(Artist);
        artist_albumList = new ArrayList<Artist_Album_Song>();
        getsong();
        Collections.sort(artist_albumList, new Comparator<Artist_Album_Song>() {
            @Override
            public int compare(Artist_Album_Song lhs, Artist_Album_Song rhs) {
                String s1 = lhs.getArtist_album_song();
                String s2 = rhs.getArtist_album_song();
                return s1.compareToIgnoreCase(s2);
            }
        });
        Artist_Album_Song_Adapter adapter = new Artist_Album_Song_Adapter(this,artist_albumList);
        artist__view.setAdapter(adapter);
        Artist_Album_Song curr = artist_albumList.get(0);

        Cursor c = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?", new String[]{curr.getAlbum_artistid()}, null);
        if(c.moveToFirst()){
            String path = c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            if(path!=null){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds=false;
                options.inPreferredConfig= Bitmap.Config.RGB_565;
                options.inDither=true;
                Bitmap art = BitmapFactory.decodeFile(path,options);
                imageView.setImageBitmap(art);

            }
        }c.close();

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
                x=DatabaseCheck();
                layoutcall(x);
                Artist_Album_Song curr = artist_albumList.get(position);

               /* Artist_Album_Song curr = artist_albumList.get(position);
               imgcount=position;
//                String location = curr.getArtist_album_location();
                title__name.setText(title);
                artist__name.setText(artist);
                Cursor c = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID + "=?", new String[]{curr.getAlbum_artistid()}, null);
                if (c.moveToFirst()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                    if (path != null) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = false;
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        options.inDither = true;
                        Bitmap art = BitmapFactory.decodeFile(path, options);
                        songimg.setImageBitmap(art);

                    } else {
                        songimg.setImageResource(R.drawable.prakhar);
                    }
                }
                c.close();
*/


            }
        });
        check = DatabaseCheck();
        x=check;
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
                intent = new Intent(Album_song_of_Artist.this, Playsong.class).putExtra("title", title).putExtra("artist", artist);
                startActivity(intent);
            }
        });
    }


    public void DataBaseCall(SQLiteDatabase Db,int position){
        ContentValues values = new ContentValues();
        Artist_Album_Song currrr = artist_albumList.get(position);
        String title = currrr.getArtist_album_song();
        String artist = currrr.getAlbum_artist();
        String location = currrr.getArtist_album_location();
        String id = currrr.getAlbum_artistid();
        Toast.makeText(Album_song_of_Artist.this, id , Toast.LENGTH_SHORT).show();

        values.put(Record.RecordTask.COL2, title);
        values.put(Record.RecordTask.COL3, artist);
        values.put(Record.RecordTask.COL4, location);
        values.put(Record.RecordTask.COL0,id);
        Db.insertWithOnConflict(Record.RecordTask.Table, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        for (int i = 0; i < artist_albumList.size(); i++) {
            if(i!=position){
                Artist_Album_Song currr = artist_albumList.get(i);
                String titlee = currr.getArtist_album_song();
                String artistt = currr.getAlbum_artist();
                String locationn = currr.getArtist_album_location();
                String idd = currr.getAlbum_artistid();
                Toast.makeText(Album_song_of_Artist.this, idd , Toast.LENGTH_SHORT).show();
                values.put(Record.RecordTask.COL2, titlee);
                values.put(Record.RecordTask.COL3, artistt);
                values.put(Record.RecordTask.COL4, locationn);
                values.put(Record.RecordTask.COL0,idd);
                Db.insertWithOnConflict(Record.RecordTask.Table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            }
        }
        Db.close();

    /*    SQLiteDatabase Datab = dbHelper.getReadableDatabase();
        Cursor cursor = Datab.query(Record.RecordTask.Table,
                new String[]{Record.RecordTask.COL0}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(Record.RecordTask.COL0);
            String task = cursor.getString(index);
            Toast.makeText(Album_song_of_Artist.this, task , Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        Datab.close();**/

    }

    public int DatabaseCheck(){
        SQLiteDatabase Datab = dbHelper.getReadableDatabase();
        Cursor cursorr = Datab.query(Record.RecordTask.Table,new String[]{Record.RecordTask.COL2, Record.RecordTask.COL3, Record.RecordTask.COL4,Record.RecordTask.COL0}, null, null, null, null, null);
        if(cursorr.getCount()>0){
            SQLiteDatabase Data = dbHelper.getReadableDatabase();
            Cursor cursor = Data.query(Record.RecordTask.Table,
                    new String[]{Record.RecordTask.COL2, Record.RecordTask.COL3, Record.RecordTask.COL4,Record.RecordTask.COL0}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(Record.RecordTask.COL2);
                String task = cursor.getString(index);
                int ind = cursor.getColumnIndex(Record.RecordTask.COL3);
                String tas = cursor.getString(ind);
                int in = cursor.getColumnIndex(Record.RecordTask.COL4);
                String ta = cursor.getString(in);
                int ply = cursor.getColumnIndex(Record.RecordTask.COL0);
                String ylp = cursor.getString(ply);
                Artist_Album_Song curr = artist_albumList.get(imgcount);
                title__name.setText(task);
                artist__name.setText(tas);
                title = task;
                artist = tas;
                Cursor c = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID + "=?", new String[]{ylp}, null);
                if (c.moveToFirst()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                    if (path != null) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = false;
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        options.inDither = true;
                        Bitmap art = BitmapFactory.decodeFile(path, options);
                        songimg.setImageBitmap(art);

                    } else {
                        songimg.setImageResource(R.drawable.prakhar);
                    }
                }
                c.close();
                break;
            }
            cursor.close();
            Datab.close();

            cursorr.close();
            return 5;
        }

        else{
            cursorr.close();
            return 0;}
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
        String selection = MediaStore.Audio.Media.ALBUM+"=?";
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
            int albumartColum = cursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ID);
            //    int durationColumn = cursor.getColumnIndex
            //          (MediaStore.Audio.Media.DURATION);
            //add songs to list
            do {
                String thisLocation = cursor.getString(locationColumn);
                String thisTitle = cursor.getString(titleColumn);
                String thisArtist = cursor.getString(artistColumn);
                String thisalbumart = cursor.getString(albumartColum);
                // String thisDuration = cursor.getString(durationColumn);

                artist_albumList.add(new Artist_Album_Song(thisTitle,thisLocation,thisArtist,thisalbumart));

            }
            while (cursor.moveToNext());
        }

    }

}
