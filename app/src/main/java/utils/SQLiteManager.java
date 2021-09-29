package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class SQLiteManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Tonk.db";
    public static final String USER_TABLE = "User";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_IMAGE = "user_image";
    public static final String USER_CHIP = "user_chip";
    public static final String USER_PLAYED = "user_played";
    public static final String USER_WIN = "user_win";
    public static final String USER_BIG_WON = "user_big_won";
    public static String Lock = "dblock";

    C myData = C.getInstance();
    Comparator<HashMap<String, String>> comparator = new Comparator<HashMap<String, String>>() {

        @Override
        public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {

            long v2 = Long.parseLong(lhs.get(myData.CHIPS));
            long v1 = Long.parseLong(rhs.get(myData.CHIPS));

            return v1 < v2 ? -1 : v1 > v2 ? 1 : 0;

        }
    };


    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//		db.execSQL("create table Song(id integer primary key autoincrement, song_name text,
//		song_album text, song_path text, song_played text, song_favourite text)");

        String sql = "create table " + USER_TABLE + "(id integer primary key autoincrement, "
                + USER_ID + " text, "
                + USER_NAME + " text, "
                + USER_IMAGE + " text, "
                + USER_CHIP + " text, "
                + USER_PLAYED + " text, "
                + USER_WIN + " text, "
                + USER_BIG_WON + " text)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(HashMap<String, String> data) {
        synchronized (Lock) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();

            cv.put(USER_ID, data.get(myData.ID));
            cv.put(USER_NAME, data.get(myData.NAME));
            cv.put(USER_IMAGE, data.get(myData.IMAGE));
            cv.put(USER_CHIP, data.get(myData.CHIPS));
            cv.put(USER_PLAYED, data.get(myData.PLAYED));
            cv.put(USER_WIN, data.get(myData.WIN));
            cv.put(USER_BIG_WON, data.get(myData.BIG_WON));

            db.insert(USER_TABLE, null, cv);
            db.close();
        }
    }

    public ArrayList<HashMap<String, String>> getLeaderBoard() {

        ArrayList<HashMap<String, String>> returnArray = new ArrayList<HashMap<String, String>>();
        synchronized (Lock) {
            SQLiteDatabase db = this.getReadableDatabase();
            String quesry = "SELECT * FROM " + USER_TABLE;

            Cursor c = db.rawQuery(quesry, null);

            if (c.moveToFirst()) {

                do {

                    boolean has = false;

                    for (int i = 0; i < returnArray.size(); i++) {

                        if (returnArray.get(i).get(myData.ID).equals(c.getString(1))) {
                            has = true;
                        }
                    }

                    if (!has) {

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(myData.ID, c.getString(1));
                        map.put(myData.NAME, c.getString(2));
                        map.put(myData.IMAGE, c.getString(3));
                        map.put(myData.CHIPS, c.getString(4));
                        map.put(myData.PLAYED, c.getString(5));
                        map.put(myData.WIN, c.getString(6));
                        map.put(myData.BIG_WON, c.getString(7));

                        returnArray.add(map);
                    }

                } while (c.moveToNext());

            }

            c.close();

            Collections.sort(returnArray, comparator);
        }
        return returnArray;

    }

    public void incrementGamePlayed(String id) {
        synchronized (Lock) {
            SQLiteDatabase db = this.getWritableDatabase();

            String q = "SELECT * FROM " + USER_TABLE;
            Cursor c = db.rawQuery(q, null);

            int count = 0;

            try {
                if (c.moveToFirst()) {

                    do {

                        if (c.getString(1).equals(id)) {


                            //Logger.Print("WWWW incrementGamePlayed:: INSIDE:: " + c.getString(5));

                            count = Integer.valueOf(c.getString(5));

                        }

                    } while (c.moveToNext());
                }

                count = count + 1;

                //Logger.Print("WWWW incrementGamePlayed:: OUTSIDE:: " + count);

                String cc = String.valueOf(count);

                ContentValues cv = new ContentValues();
                cv.put(USER_PLAYED, cc);
                db.update(USER_TABLE, cv, USER_ID + "=?", new String[]{id});
                db.close();
                this.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateGamePlayed(String id, long gameplayed) {
        synchronized (Lock) {
            SQLiteDatabase db = this.getWritableDatabase();

            String q = "SELECT * FROM " + USER_TABLE;
            Cursor c = db.rawQuery(q, null);

            long count = 0;

            try {
                if (c.moveToFirst()) {

                    do {

                        if (c.getString(1).equals(id)) {


                            //Logger.Print("WWWW incrementGamePlayed:: INSIDE:: " + c.getString(5));

                            count = Integer.valueOf(c.getString(5));

                        }

                    } while (c.moveToNext());
                }

                count = gameplayed;

                //Logger.Print("WWWW incrementGamePlayed:: OUTSIDE:: " + count);

                String cc = String.valueOf(count);

                ContentValues cv = new ContentValues();
                cv.put(USER_PLAYED, cc);
                db.update(USER_TABLE, cv, USER_ID + "=?", new String[]{id});
                db.close();
                this.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void incrementGameWin(String id) {
        synchronized (Lock) {
            SQLiteDatabase db = this.getWritableDatabase();

            String q = "SELECT * FROM " + USER_TABLE;
            Cursor c = db.rawQuery(q, null);

            int count = 0;

            if (c.moveToFirst()) {

                do {

                    if (c.getString(1).equals(id)) {

                        count = Integer.valueOf(c.getString(6));

                    }

                } while (c.moveToNext());
            }

            count = count + 1;

            String cc = String.valueOf(count);

            ContentValues cv = new ContentValues();
            cv.put(USER_WIN, cc);
            db.update(USER_TABLE, cv, USER_ID + "=?", new String[]{id});
            db.close();
        }
    }

    public void updateGameWin(String id, long wincount) {
        synchronized (Lock) {
            SQLiteDatabase db = this.getWritableDatabase();

            String q = "SELECT * FROM " + USER_TABLE;
            Cursor c = db.rawQuery(q, null);

            long count = 0;

            if (c.moveToFirst()) {

                do {

                    if (c.getString(1).equals(id)) {

                        count = Integer.valueOf(c.getString(6));

                    }

                } while (c.moveToNext());
            }

            count = wincount;

            String cc = String.valueOf(count);

            ContentValues cv = new ContentValues();
            cv.put(USER_WIN, cc);
            db.update(USER_TABLE, cv, USER_ID + "=?", new String[]{id});
            db.close();
        }
    }

    public void updateChip(String id, long chip, boolean isIncrease) {
        synchronized (Lock) {
            SQLiteDatabase db = this.getWritableDatabase();

            String q = "SELECT * FROM " + USER_TABLE;
            Cursor c = db.rawQuery(q, null);

            long count = 0;

            if (c.moveToFirst()) {

                do {

                    if (c.getString(1).equals(id)) {

                        count = Long.parseLong(c.getString(4));

                    }

                } while (c.moveToNext());
            }

            if (isIncrease) {
                count = count + chip;
            } else {
                count = count - chip;
            }

            ContentValues cv = new ContentValues();
            cv.put(USER_CHIP, count);
            db.update(USER_TABLE, cv, USER_ID + "=?", new String[]{id});
            db.close();
            close();
        }
    }

    public void updateName(String name) {
        synchronized (Lock) {
            SQLiteDatabase db = this.getWritableDatabase();

            String q = "SELECT * FROM " + USER_TABLE;
            Cursor c = db.rawQuery(q, null);

            try {

                if (c.moveToFirst()) {

                    do {

                        if (c.getString(1).equals(PreferenceManager.get_id())) {

                            ContentValues cv = new ContentValues();
                            cv.put(USER_NAME, name);

                            //	db.update(USER_TABLE, cv, USER_ID + "=?", new String[] {PreferenceManager.GetId()});

                            db.close();
                            close();

                        }

                    } while (c.moveToNext());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateBiggestWin(String id, long newWin) {
        synchronized (Lock) {
            SQLiteDatabase db = this.getWritableDatabase();

            String q = "SELECT * FROM " + USER_TABLE;
            Cursor c = db.rawQuery(q, null);

            long oldWin = 0;

            if (c.moveToFirst()) {

                do {

                    if (c.getString(1).equals(id)) {

                        oldWin = Long.parseLong(c.getString(7));
                    }

                } while (c.moveToNext());

            }

            if (newWin > oldWin) {

                ContentValues cv = new ContentValues();
                cv.put(USER_BIG_WON, newWin);
                db.update(USER_TABLE, cv, USER_ID + "=?", new String[]{id});
                db.close();
            }
        }
    }

    public HashMap<String, String> getUserData(String id) {

        HashMap<String, String> data = new HashMap<String, String>();
        synchronized (Lock) {
            SQLiteDatabase db = this.getReadableDatabase();

            String q = "SELECT * FROM " + USER_TABLE;
            Cursor c = db.rawQuery(q, null);

            try {
                if (c.moveToFirst()) {

                    do {

                        if (c.getString(1).equals(id)) {

                            data.put(myData.ID, c.getString(1));
                            data.put(myData.NAME, c.getString(2));
                            data.put(myData.IMAGE, c.getString(3));
                            data.put(myData.CHIPS, c.getString(4));
                            data.put(myData.PLAYED, c.getString(5));
                            data.put(myData.WIN, c.getString(6));
                            data.put(myData.BIG_WON, c.getString(7));

                        }

                    } while (c.moveToNext());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public ArrayList<HashMap<String, String>> getPlayersList(int start, int end) {

        ArrayList<HashMap<String, String>> returnArray = new ArrayList<HashMap<String, String>>();
        synchronized (Lock) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                String quesry = "SELECT * FROM " + USER_TABLE;
                Cursor c = db.rawQuery(quesry, null);

                if (c.moveToFirst()) {

                    do {

                        long chip = Long.parseLong(c.getString(4));

                        if (!c.getString(1).equals(PreferenceManager.get_id())) {

                            //  if (chip >= start && chip < end) {

                            boolean has = false;

                            for (int i = 0; i < returnArray.size(); i++) {

                                if (returnArray.get(i).get(myData.ID).equals(c.getString(1))) {
                                    has = true;
                                }
                            }

                            if (!has) {

                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put(myData.ID, c.getString(1));
                                map.put(myData.NAME, c.getString(2));
                                map.put(myData.IMAGE, c.getString(3));
                                map.put(myData.CHIPS, c.getString(4));
                                map.put(myData.PLAYED, c.getString(5));
                                map.put(myData.WIN, c.getString(6));
                                map.put(myData.BIG_WON, c.getString(7));

                                returnArray.add(map);

                            }
                        }
                        // }

                    } while (c.moveToNext());

                }

                c.close();
            } catch (RuntimeException r) {
                Log.e("RuntimeEx", "" + r);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnArray;
    }
}