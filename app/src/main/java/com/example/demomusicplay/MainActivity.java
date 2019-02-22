package com.example.demomusicplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



public class MainActivity extends AppCompatActivity {
    private ListView songView;
    GridView artistView;
    GridView albumView;
    MediaPlayer mp;
    private ArrayList<Song> songList;
    private ArrayList<Artists> artistList;
    private ArrayList<Album> albumList;
    ImageButton play,next,previous;
    int a=0;
    int p=0;
    int i=0;
    int x=0;
    TextView duration;
    GestureDetectorCompat gestureObj;
    DataBase dbHelper;
    // ArrayList<Artistimg> img = new ArrayList<Artistimg>();
    // private LayoutInflater songInf;
    MediaMetadataRetriever mmr ;
    private byte[] rawArt;
    Bitmap art;
    BitmapFactory.Options bfo = new BitmapFactory.Options();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        songView =(ListView)findViewById(R.id.song_list);
        artistView =(GridView)findViewById(R.id.music_artist);
        albumView =(GridView)findViewById(R.id.song_albums);
        play=(ImageButton)findViewById(R.id.play);
        previous=(ImageButton)findViewById(R.id.previous);
        next=(ImageButton)findViewById(R.id.next);
        duration = (TextView)findViewById(R.id.selected_song_duration);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec spec5 = tabHost.newTabSpec("Tab 4");
        spec5.setIndicator("PLAYLISTS");
        spec5.setContent(R.id.Playlists);
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab 2");
        spec2.setIndicator("ARTISTS");
        spec2.setContent(R.id.Artists);
        TabHost.TabSpec spec3 = tabHost.newTabSpec("Tab 3");
        spec3.setIndicator("ALBUMS");
        spec3.setContent(R.id.Albums);
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab 1");
        spec1.setIndicator("SONGS");
        spec1.setContent(R.id.music);
        tabHost.addTab(spec5);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
        tabHost.addTab(spec1);
        dbHelper = new DataBase(this);
        gestureObj = new GestureDetectorCompat(MainActivity.this,new LearnGesture());
        songList = new ArrayList<Song>();
        artistList = new ArrayList<Artists>();
        albumList = new ArrayList<Album>();
        //get songs from device
        getSongList();
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song lhs, Song rhs) {
                String s1 = lhs.getTitle();
                String s2 = rhs.getTitle();
                return s1.compareToIgnoreCase(s2);
            }
        });
        SongAdapter songAdt = new SongAdapter(this, songList);
        songView.setAdapter(songAdt);

        Collections.sort(artistList, new Comparator<Artists>() {
            @Override
            public int compare(Artists lhs, Artists rhs) {
                String s1 = lhs.getArtist();
                String s2 = rhs.getArtist();
                return s1.compareToIgnoreCase(s2);

            }
        });
        for(int i=0; i<artistList.size();i++){
            Artists currArtist = artistList.get(i);
            String nowArtist = currArtist.getArtist();
            for(int j=i; j<artistList.size();j++){
                Artists nextArtist = artistList.get(j);
                String nextnowArtist = nextArtist.getArtist();
                if((nowArtist.equals(nextnowArtist))&&j!=i){
//                    Toast.makeText(this,nextnowArtist+nowArtist,Toast.LENGTH_SHORT).show();
                    artistList.remove(i);
                    //  j++;
                }
            }
        }

        for(int i=0; i<artistList.size();i++){
            Artists currArtist = artistList.get(i);
            String nowArtist = currArtist.getArtist();
            for(int j=i; j<artistList.size();j++){
                Artists nextArtist = artistList.get(j);
                String nextnowArtist = nextArtist.getArtist();
                if((nowArtist.equals(nextnowArtist))&&j!=i){
                    //                  Toast.makeText(this,nextnowArtist+nowArtist,Toast.LENGTH_SHORT).show();
                    artistList.remove(i);
                    //  j++;
                }
            }
        }

        for(int i=0; i<artistList.size();i++){
            Artists currArtist = artistList.get(i);
            String nowArtist = currArtist.getArtist();
            for(int j=i; j<artistList.size();j++){
                Artists nextArtist = artistList.get(j);
                String nextnowArtist = nextArtist.getArtist();
                if((nowArtist.equals(nextnowArtist))&&j!=i){
                    //                Toast.makeText(this,nextnowArtist+nowArtist,Toast.LENGTH_SHORT).show();
                    artistList.remove(i);
                    //  j++;
                }
            }
        }

        ArtistAdapter artistAdt = new ArtistAdapter(this, artistList);
        artistView.setAdapter(artistAdt);


        Collections.sort(albumList, new Comparator<Album>() {
            @Override
            public int compare(Album lhs, Album rhs) {
                String s1 = lhs.getAlbum();
                String s2 = rhs.getAlbum();
                return s1.compareToIgnoreCase(s2);

            }
        });
        for(int i=0; i<albumList.size();i++){
            Album currArtist = albumList.get(i);
            String nowArtist = currArtist.getAlbum();
            for(int j=i; j<albumList.size();j++){
                Album nextArtist = albumList.get(j);
                String nextnowArtist = nextArtist.getAlbum();
                if((nowArtist.equals(nextnowArtist))&&j!=i){
                    //                    Toast.makeText(this,nextnowArtist+nowArtist,Toast.LENGTH_SHORT).show();
                    albumList.remove(i);
                    //  j++;
                }
            }
        }


        for(int i=0; i<albumList.size();i++){
            Album currArtist = albumList.get(i);
            String nowArtist = currArtist.getAlbum();
            for(int j=i; j<albumList.size();j++){
                Album nextArtist = albumList.get(j);
                String nextnowArtist = nextArtist.getAlbum();
                if((nowArtist.equals(nextnowArtist))&&j!=i){
                    //                  Toast.makeText(this,nextnowArtist+nowArtist,Toast.LENGTH_SHORT).show();
                    albumList.remove(i);
                    //  j++;
                }
            }
        }

        for(int i=0; i<albumList.size();i++){
            Album currArtist = albumList.get(i);
            String nowArtist = currArtist.getAlbum();
            for(int j=i; j<albumList.size();j++){
                Album nextArtist = albumList.get(j);
                String nextnowArtist = nextArtist.getAlbum();
                if((nowArtist.equals(nextnowArtist))&&j!=i){
//                    Toast.makeText(this,nextnowArtist+nowArtist,Toast.LENGTH_SHORT).show();
                    albumList.remove(i);
                    //  j++;
                }
            }
        }

        AlbumAdapter artistA = new AlbumAdapter(this,albumList);
        albumView.setAdapter(artistA);


        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                x = position;
                if (p == 0) {
                    if (i == 0) {
                        songplay(position);
                        p = position;
                        i++;
                    } else {
                        mp.stop();
                        mp.reset();
                        songplay(position);
                        p = position;

                    }

                } else if (p != position) {
                    mp.stop();
                    mp.reset();
                    songplay(position);
                    p = position;

                } else if (p == position) {
                    mp.stop();
                    mp.reset();
                    songplay(position);
                    p = position;
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x == 0) {
                    songplay(x);
                    x++;
                } else if (x != 0) {
                    if (a == 0) {
                        mp.pause();
                        play.setImageResource(R.drawable.playmusic);
                        a++;
                    } else if (a != 0) {
                        mp.start();
                        play.setImageResource(R.drawable.pausemusic);
                        a = 0;
                    }
                }
            }
        });

        play.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    play.setBackgroundResource(R.drawable.play_music);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    play.setBackgroundResource(R.drawable.change_green);
                }
                return false;
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p == 0) {
                    p = (songList.size() - 1);
                    mp.stop();
                    mp.reset();
                    songplay(p);

                } else {
                    p--;
                    mp.stop();
                    mp.reset();
                    songplay(p);
                }

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(p==(songList.size()-1)){
                    p=0;
                    mp.stop();
                    mp.reset();
                    songplay(p);
                }else {
                    p++;
                    mp.stop();
                    mp.reset();
                    songplay(p);

                }
            }
        });

        artistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artists songs = artistList.get(position);
                String songArtist = songs.getArtist();
                String songArtistid=songs.getAlbumart();
                Intent i = new Intent(MainActivity.this, Song_Artist.class).putExtra("Artist", songArtist).putExtra("id",songArtistid);
                startActivity(i);
            }
        });
        albumView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Album artist_album = albumList.get(position);
                String artistalbum=artist_album.getAlbum();
                Intent intent = new Intent(MainActivity.this,Album_song_of_Artist.class).putExtra("Album",artistalbum);
                startActivity(intent);

            }
        });}

    public  void songplay(int position){
        Song player = songList.get(position);
        String songTitle = player.getlocation();
        String songduration = player.getDuration();
        //           Toast.makeText(MainActivity.this, songTitle, Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse(songTitle);
        mp = MediaPlayer.create(getApplicationContext(), uri);
        duration.setText(songduration);
        mp.start();
        play.setImageResource(R.drawable.playmusic);
        play.postDelayed(new Runnable() {
            @Override
            public void run() {
                play.setImageResource(R.drawable.pausemusic);
            }
        }, 1000);
    }

    public void getSongList(){
        //query external audio

        Cursor musicCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        //iterate over results if valid
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int locationColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ARTIST);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM);
            int durationColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            int albumartColum = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ID);
            int album_artColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);
            //add songs to list
            do {
                String thisLocation = musicCursor.getString(locationColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisAlbum = musicCursor.getString(albumColumn);
                String thisDuration = musicCursor.getString(durationColumn);
                String thisalbumart = musicCursor.getString(albumartColum);

                /**/
                songList.add(new Song(thisLocation, thisTitle, thisArtist, thisDuration, thisalbumart));
                artistList.add(new Artists(thisArtist,thisalbumart));
                albumList.add(new Album(thisAlbum,thisalbumart));

            }
            while (musicCursor.moveToNext());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureObj.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX,float velocityY){
            if(event2.getX()>event1.getX()){

            }else if(event2.getX()<event1.getX()){

            }

            return true;
        }
    }
}



