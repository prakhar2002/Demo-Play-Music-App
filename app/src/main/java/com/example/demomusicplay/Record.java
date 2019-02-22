package com.example.demomusicplay;

import android.provider.BaseColumns;

/**
 * Created by SAM on 14-Jul-18.
 */
public class Record {
    public static final String TAG ="Bulbul";
    public static final int DB_VERSION =2;

    public class RecordTask implements BaseColumns {
        public static final String Table = "Record";
        public static final String COL2 = "Songs";
        public static final String COL3 = "artist";
        public static final String COL4 = "location";
        public static final String COL0 = "imageid";
    }
}
