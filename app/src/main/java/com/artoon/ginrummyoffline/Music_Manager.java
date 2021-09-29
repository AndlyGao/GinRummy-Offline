package com.artoon.ginrummyoffline;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import utils.Logger;
import utils.PreferenceManager;

public class Music_Manager {

    static Boolean Mute = false;
    private static SoundPool spool;
    private static int Chaal;
    private static int Card_Dealing;
    private static int UserTurn;
    private static int Winner;
    private static int Gin;
    private static int Timeout;
    private static int Skip;
    private static int Knock;
    private static int MagicCollect;
    private static int MagicFull;
    private static int WinPoints;
    private static int buttonclk;
    private static int throwcard;

    public Music_Manager(Context mcontext) {
        try {
            Logger.print("SSSSSSSSSSSSSSSS>>> Music_Manager 1111");
           /* Mute = PreferenceManager.getMute();*/
            AudioManager am = (AudioManager) mcontext.getSystemService(Context.AUDIO_SERVICE);
            if (am.getRingerMode() == AudioManager.RINGER_MODE_SILENT || am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE || PreferenceManager.getMute()) {

                Logger.print("SSSSSSSSSSSSSSSS>>> Music_Manager true");
                PreferenceManager.setMute(true);
                Mute = true;

            } else {


                Logger.print("SSSSSSSSSSSSSSSS>>> Music_Manager false");
                Mute = false;
                PreferenceManager.setMute(false);
            }

            spool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

            Chaal = spool.load(mcontext, R.raw.chaal, 1);
            buttonclk = spool.load(mcontext, R.raw.button_click, 1);
            WinPoints = spool.load(mcontext, R.raw.winpoints, 1);
            Card_Dealing = spool.load(mcontext, R.raw.carddealing, 1);
            UserTurn = spool.load(mcontext, R.raw.userturn, 1);
            Winner = spool.load(mcontext, R.raw.winner, 1);
            throwcard = spool.load(mcontext, R.raw.hit, 1);

            Gin = spool.load(mcontext, R.raw.gin, 1);
            Timeout = spool.load(mcontext, R.raw.timeout, 1);
            Skip = spool.load(mcontext, R.raw.skip, 1);
            Knock = spool.load(mcontext, R.raw.knock, 1);

            MagicCollect = spool.load(mcontext, R.raw.magic_collect, 1);
            MagicFull = spool.load(mcontext, R.raw.magic_full, 1);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    static void play_Chaal() {
        if (!Mute) {
            Logger.print("SSSSSSSSSSSSSSSS>>> play_Chaal");
            spool.play(Chaal, 0.99f, 0.99f, 1, 0, 1);
        }
    }

    static void throwcard() {
        if (!Mute) {
            Logger.print("SSSSSSSSSSSSSSSS>>> throwcard");
            spool.play(throwcard, 0.99f, 0.99f, 1, 0, 1);
        }
    }

    public static void buttonclick() {

        if (!Mute) {
            try {
                Logger.print("SSSSSSSSSSSSSSSS>>> buttonclick");
                spool.play(buttonclk, 0.99f, 0.99f, 1, 0, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void Play_WinPoints() {
        if (!Mute) {
            Logger.print("SSSSSSSSSSSSSSSS>>> Play_WinPoints");
            spool.play(WinPoints, 0.99f, 0.99f, 1, 0, 1);
        }
    }

    static void play_MagicFull() {
        if (!Mute) {
            Logger.print("SSSSSSSSSSSSSSSS>>> play_MagicFull");
            spool.play(MagicFull, 0.99f, 0.99f, 1, 0, 1);
        }
    }

    static void play_MagicCollect() {
        if (!Mute) {
            try {
                Logger.print("SSSSSSSSSSSSSSSS>>> play_MagicCollect");
                spool.play(MagicCollect, 0.99f, 0.99f, 1, 0, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void play_CardDealing() {
        if (!Mute) {
            Logger.print("SSSSSSSSSSSSSSSS>>> play_CardDealing");
            spool.play(Card_Dealing, 0.99f, 0.99f, 1, 0, 1);
        }
    }

    static void play_Notification() {
        if (!Mute) {
            spool.play(UserTurn, 0.99f, 0.99f, 1, 0, 1);
        }
    }

    static void play_UserTurn() {
        if (!Mute) {
            spool.play(UserTurn, 0.99f, 0.99f, 1, 0, 1);
        }
    }

    public static void play_Winner() {
        if (!Mute) {
            spool.play(Winner, 0.99f, 0.99f, 1, 0, 1);
        }
    }

    static void play_Gin() {
        if (!Mute) {
            spool.play(Gin, 0.99f, 0.99f, 1, 0, 1);
        }
    }

    static void play_Timeout() {
        if (!Mute) {
            spool.play(Timeout, 0.99f, 0.99f, 1, 0, 1);
        }
    }

    public static void play_Skip() {
        if (!Mute) {
            spool.play(Skip, 0.99f, 0.99f, 1, 0, 1);
        }
    }

    static void play_Knock() {
        if (!Mute) {
            spool.play(Knock, 0.99f, 0.99f, 1, 0, 1);
        }
    }

}
