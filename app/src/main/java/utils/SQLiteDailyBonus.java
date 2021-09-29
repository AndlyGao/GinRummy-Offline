package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteDailyBonus extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dailyBonus.db";
    public static final String USER_TABLE = "UserdailyBonus";
    public static final String USER_SHARE = "UsershareData";
    public static final String APPLICATION = "application";
    public static final String Collect_Date = "collect_date";
    public static final String Collect_Month = "collect_month";
    public static final String Collect_Year = "collect_year";
    public static final String Collect_Day = "collect_day";

    public static String Lock = "dblock";

    C myData = C.getInstance();

    public SQLiteDailyBonus(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//		db.execSQL("create table Song(id integer primary key autoincrement, song_name text, 
//		song_album text, song_path text, song_played text, song_favourite text)");

        String sql = "create table " + USER_TABLE + "(id integer primary key autoincrement, "
                + Collect_Date + " text, "
                + Collect_Month + " text, "
                + Collect_Year + " text, "
                + Collect_Day + " text)";

        db.execSQL(sql);

//        String sql2 = "create table " + USER_SHARE + "(id integer primary key autoincrement, "
//                + APPLICATION +  " text)";
//        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(HashMap<String, String> data) {
        synchronized (Lock) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();

            cv.put(Collect_Date, data.get(myData.DATE));
            cv.put(Collect_Month, data.get(myData.MONTH));
            cv.put(Collect_Year, data.get(myData.YEAR));
            cv.put(Collect_Day, data.get(myData.DAY));


            db.insert(USER_TABLE, null, cv);
            db.close();
        }
    }

//    public void insertApplicationData(HashMap<String, String> data) {
//        synchronized(Lock) {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            ContentValues cv = new ContentValues();
//
//
//            cv.put(APPLICATION, data.get(myData.APPLICATION));
//
//
//            db.insert(USER_SHARE, null, cv);
//            db.close();
//        }
//    }

    public ArrayList<HashMap<String, String>> getBonusCollectInfo() {

        ArrayList<HashMap<String, String>> returnArray = new ArrayList<HashMap<String, String>>();
        synchronized (Lock) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                String quesry = "SELECT * FROM " + USER_TABLE;
                Cursor c = db.rawQuery(quesry, null);

                if (c.moveToFirst()) {

                    do {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(myData.DATE, c.getString(1));
                        map.put(myData.MONTH, c.getString(2));
                        map.put(myData.YEAR, c.getString(3));
                        map.put(myData.DAY, c.getString(4));


                        returnArray.add(map);

                    } while (c.moveToNext());

                }

                c.close();
            } catch (RuntimeException r) {
                Log.e("RuntimeException", "" + r);
            }
        }
        return returnArray;
    }

//    public ArrayList<HashMap<String, String>> getShareAppInfo() {
//
//        ArrayList<HashMap<String, String>> returnArray = new ArrayList<HashMap<String, String>>();
//        synchronized(Lock) {
//            SQLiteDatabase db = this.getReadableDatabase();
//
//            String quesry = "SELECT * FROM " + USER_SHARE;
//            Cursor c = db.rawQuery(quesry, null);
//
//            if (c.moveToFirst()) {
//
//                do {
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put(myData.APPLICATION, c.getString(1));
//                    returnArray.add(map);
//
//                } while (c.moveToNext());
//
//            }
//
//            c.close();
//        }
//        return returnArray;
//    }


//    public ArrayList<HashMap<String, String>> getLeaderBoard() {
//
//        ArrayList<HashMap<String, String>> returnArray = new ArrayList<HashMap<String, String>>();
//        synchronized(Lock) {
//            SQLiteDatabase db = this.getReadableDatabase();
//            String quesry = "SELECT * FROM " + USER_TABLE;
//
//            Cursor c = db.rawQuery(quesry, null);
//
//            if (c.moveToFirst()) {
//
//                do {
//
//                    boolean has = false;
//
//                    for (int i = 0; i < returnArray.size(); i++) {
//
//                        if (returnArray.get(i).get(myData.ID).equals(c.getString(1))) {
//                            has = true;
//                        }
//                    }
//
//                    if (!has) {
//
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(myData.ID, c.getString(1));
//                        map.put(myData.NAME, c.getString(2));
//                        map.put(myData.IMAGE, c.getString(3));
//                        map.put(myData.CHIPS, c.getString(4));
//                        map.put(myData.PLAYED, c.getString(5));
//                        map.put(myData.WIN, c.getString(6));
//                        map.put(myData.BIG_WON, c.getString(7));
//
//                        returnArray.add(map);
//                    }
//
//                } while (c.moveToNext());
//
//            }
//
//            c.close();
//
//            Collections.sort(returnArray, comparator);
//        }
//        return returnArray;
//
//    }
//
//    Comparator<HashMap<String, String>> comparator = new Comparator<HashMap<String, String>>() {
//
//        @Override
//        public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
//
//            long v2 = Long.parseLong(lhs.get(myData.CHIPS));
//            long v1 = Long.parseLong(rhs.get(myData.CHIPS));
//
//            return v1 < v2 ? -1 : v1 > v2 ? 1 : 0;
//
//        }
//    };
//
//    public void incrementGamePlayed(String id) {
//        synchronized(Lock) {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            String q = "SELECT * FROM " + USER_TABLE;
//            Cursor c = db.rawQuery(q, null);
//
//            int count = 0;
//
//            if (c.moveToFirst()) {
//
//                do {
//
//                    if (c.getString(1).equals(id)) {
//
//
//                        //Logger.Print("WWWW incrementGamePlayed:: INSIDE:: " + c.getString(5));
//
//                        count = Integer.valueOf(c.getString(5));
//
//                    }
//
//                } while (c.moveToNext());
//            }
//
//            count = count + 1;
//
//            //Logger.Print("WWWW incrementGamePlayed:: OUTSIDE:: " + count);
//
//            String cc = String.valueOf(count);
//
//            ContentValues cv = new ContentValues();
//            cv.put(USER_PLAYED, cc);
//            db.update(USER_TABLE, cv, USER_ID + "=?", new String[]{id});
//            db.close();
//            this.close();
//        }
//    }
//
//    public void incrementGameWin(String id) {
//        synchronized(Lock) {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            String q = "SELECT * FROM " + USER_TABLE;
//            Cursor c = db.rawQuery(q, null);
//
//            int count = 0;
//
//            if (c.moveToFirst()) {
//
//                do {
//
//                    if (c.getString(1).equals(id)) {
//
//                        count = Integer.valueOf(c.getString(6));
//
//                    }
//
//                } while (c.moveToNext());
//            }
//
//            count = count + 1;
//
//            String cc = String.valueOf(count);
//
//            ContentValues cv = new ContentValues();
//            cv.put(USER_WIN, cc);
//            db.update(USER_TABLE, cv, USER_ID + "=?", new String[]{id});
//            db.close();
//        }
//    }
//
//    public void updateChip(String id, int chip, boolean isIncrease) {
//        synchronized(Lock) {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            String q = "SELECT * FROM " + USER_TABLE;
//            Cursor c = db.rawQuery(q, null);
//
//            long count = 0;
//
//            if (c.moveToFirst()) {
//
//                do {
//
//                    if (c.getString(1).equals(id)) {
//
//                        count = Long.parseLong(c.getString(4));
//
//                    }
//
//                } while (c.moveToNext());
//            }
//
//            if (isIncrease) {
//                count = count + chip;
//            } else {
//                count = count - chip;
//            }
//
//            ContentValues cv = new ContentValues();
//            cv.put(USER_CHIP, count);
//            db.update(USER_TABLE, cv, USER_ID + "=?", new String[]{id});
//            db.close();
//            close();
//        }
//    }
//
//    public void updateName(String name) {
//        synchronized(Lock) {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            String q = "SELECT * FROM " + USER_TABLE;
//            Cursor c = db.rawQuery(q, null);
//
//            try {
//
//                if (c.moveToFirst()) {
//
//                    do {
//
//                        if (c.getString(1).equals(PreferenceManager.get_id())) {
//
//                            ContentValues cv = new ContentValues();
//                            cv.put(USER_NAME, name);
//
//                            //	db.update(USER_TABLE, cv, USER_ID + "=?", new String[] {PreferenceManager.GetId()});
//
//                            db.close();
//                            close();
//
//                        }
//
//                    } while (c.moveToNext());
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void updateBiggestWin(String id, int newWin) {
//        synchronized(Lock) {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            String q = "SELECT * FROM " + USER_TABLE;
//            Cursor c = db.rawQuery(q, null);
//
//            long oldWin = 0;
//
//            if (c.moveToFirst()) {
//
//                do {
//
//                    if (c.getString(1).equals(id)) {
//
//                        oldWin = Long.parseLong(c.getString(7));
//                    }
//
//                } while (c.moveToNext());
//
//            }
//
//            if (newWin > oldWin) {
//
//                ContentValues cv = new ContentValues();
//                cv.put(USER_BIG_WON, newWin);
//                db.update(USER_TABLE, cv, USER_ID + "=?", new String[]{id});
//                db.close();
//            }
//        }
//    }
//
//    public HashMap<String, String> getUserData(String id) {
//
//        HashMap<String, String> data = new HashMap<String, String>();
//        synchronized(Lock) {
//            SQLiteDatabase db = this.getReadableDatabase();
//
//            String q = "SELECT * FROM " + USER_TABLE;
//            Cursor c = db.rawQuery(q, null);
//
//            if (c.moveToFirst()) {
//
//                do {
//
//                    if (c.getString(1).equals(id)) {
//
//                        data.put(myData.ID, c.getString(1));
//                        data.put(myData.NAME, c.getString(2));
//                        data.put(myData.IMAGE, c.getString(3));
//                        data.put(myData.CHIPS, c.getString(4));
//                        data.put(myData.PLAYED, c.getString(5));
//                        data.put(myData.WIN, c.getString(6));
//                        data.put(myData.BIG_WON, c.getString(7));
//
//                    }
//
//                } while (c.moveToNext());
//            }
//
//        }
//        return data;
//    }
//
//    public ArrayList<HashMap<String, String>> getPlayersList(int start, int end) {
//
//        ArrayList<HashMap<String, String>> returnArray = new ArrayList<HashMap<String, String>>();
//        synchronized(Lock) {
//            SQLiteDatabase db = this.getReadableDatabase();
//
//            String quesry = "SELECT * FROM " + USER_TABLE;
//            Cursor c = db.rawQuery(quesry, null);
//
//            if (c.moveToFirst()) {
//
//                do {
//
//                    long chip = Long.parseLong(c.getString(4));
//
//                    if (!c.getString(1).equals(PreferenceManager.get_id())) {
//
//                      //  if (chip >= start && chip < end) {
//
//                            boolean has = false;
//
//                            for (int i = 0; i < returnArray.size(); i++) {
//
//                                if (returnArray.get(i).get(myData.ID).equals(c.getString(1))) {
//                                    has = true;
//                                }
//                            }
//
//                            if (!has) {
//
//                                HashMap<String, String> map = new HashMap<String, String>();
//                                map.put(myData.ID, c.getString(1));
//                                map.put(myData.NAME, c.getString(2));
//                                map.put(myData.IMAGE, c.getString(3));
//                                map.put(myData.CHIPS, c.getString(4));
//                                map.put(myData.PLAYED, c.getString(5));
//                                map.put(myData.WIN, c.getString(6));
//                                map.put(myData.BIG_WON, c.getString(7));
//
//                                returnArray.add(map);
//
//                            }
//                        }
//                   // }
//
//                } while (c.moveToNext());
//
//            }
//
//            c.close();
//        }
//        return returnArray;
//    }
}