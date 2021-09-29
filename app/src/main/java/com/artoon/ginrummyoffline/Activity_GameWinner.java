package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.C;
import utils.CircularImageView;
import utils.Logger;
import utils.Parameters;
import utils.PreferenceManager;
import utils.ResponseCodes;

public class Activity_GameWinner extends Activity implements OnClickListener,
        OnTouchListener {

    private final C c = C.getInstance();
    private final Handler timerHandler = new Handler();
    private TextView activity_wt_ctitle;
    private TextView c_username_first;
    private TextView c_username_second;
    private TextView c_username_third;
    private TextView text;
    private TextView exit_game;
    private TextView nexrroundtext;
    private ImageView c_rank_first;
    private ImageView c_pic_first;
    private ImageView c_rank_second;
    private ImageView c_pic_second;
    private ImageView c_rank_third;
    private ImageView c_pic_third;
    private TextView tvRoundTwo = (TextView) findViewById(R.id.tvRoundTwo), tvRoundOne, tvRoundThree;
    private TextView tvPointTwo, tvPointOne, tvPointThree;
    private LinearLayout lin2;
    private LinearLayout lin3;
    private int Screen_Hight, Screen_Width;
    private Button ContinueBtn;
    private CountDownTimer CDTimer;
    private ImageView lineOne, lineTwo;
    private boolean firstTime = true;
    private int roundPlayed = 0;
    private TextView winnerSecondChips, winnerOneChips, winnerThirdChips;
    private boolean isButtonClick;

    private Handler handler;
    private Handler adHandler = new Handler();
    private TextView tvRoundNumber;
    private TextView tvSecondsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_winnernew);
        c.height = Screen_Hight = PreferenceManager.getheight();
        c.width = Screen_Width = PreferenceManager.getwidth();
        c.typeface = Typeface.createFromAsset(getAssets(), "font/PoetsenOne-Regular.ttf");
        FindViewById();
        initHandler();
        isButtonClick = false;

        try {

            String str = getIntent().getStringExtra(Parameters.data);
            // roundPlayed = getIntent().getIntExtra("roundno", 0);
            Logger.print("ROUNDEX => " + roundPlayed);
            JSONArray winnerData = new JSONArray(str);
            // winnerData = setWinnerArray();
            setWinnerData(winnerData);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            finish();
            overridePendingTransition(0, android.R.anim.slide_out_right);
        }

        setscreen();

        if (RoundWinner.handler != null) {
            Message msg = new Message();
            msg.what = ResponseCodes.FINISH;
            RoundWinner.handler.sendMessage(msg);
        }

        //closescreen();
//        startTimer();

    }

    private void initHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if (msg.what == ResponseCodes.Show_Payment_Successfull_Popup) {

                }

                return false;
            }
        });

    }

    private void closescreen() {
        // TODO Auto-generated method stub

        try {

            timerHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    finish();
                    overridePendingTransition(0, android.R.anim.slide_out_right);
                }
            }, 8000);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void setscreen() {

        // TODO Auto-generated method stub

        FrameLayout.LayoutParams frm;
        LinearLayout.LayoutParams lin;

        int w, h, th;

        w = getwidth(81);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.winner_close)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = w * 20 / 81;
        frm.rightMargin = w * 20 / 81;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.winner_linttitle)
                .getLayoutParams();
        frm.topMargin = getheight(2);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.activity_wt_ctitle)
                .getLayoutParams();
        lin.topMargin = getheight(20);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.pw_title_bk)
                .getLayoutParams();
        lin.width = getwidth(850);
        lin.height = getheight(8);


        // / name/////
        w = getwidth(150);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.c_username_first)
                .getLayoutParams();
        lin.width = w;
        lin.topMargin = w * 30 / 150;


        lin = (LinearLayout.LayoutParams) findViewById(R.id.c_username_second)
                .getLayoutParams();
        lin.width = w;
        lin.topMargin = w * 30 / 150;


        lin = (LinearLayout.LayoutParams) findViewById(R.id.c_username_third)
                .getLayoutParams();
        lin.width = w;
        lin.topMargin = w * 30 / 150;


        // / frm
        w = getwidth(220);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.win_frm1)
                .getLayoutParams();
        lin.height = w;
        lin.width = w;
        lin.topMargin = w * 10 / 220;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.win_frm2)
                .getLayoutParams();
        lin.height = w;
        lin.width = w;
        lin.topMargin = w * 10 / 220;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.win_frm3)
                .getLayoutParams();
        lin.height = w;
        lin.width = w;
        lin.topMargin = w * 10 / 220;

        //
        w = getwidth(220);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.c_pic_first)
                .getLayoutParams();
        frm.height = w;
        frm.width = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.c_pic_second)
                .getLayoutParams();
        frm.height = w;
        frm.width = w;


        frm = (FrameLayout.LayoutParams) findViewById(R.id.c_pic_third)
                .getLayoutParams();
        frm.height = w;
        frm.width = w;

        //
        w = getwidth(240);
        h = w * 126 / 240;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.c_rank_first)
                .getLayoutParams();
        frm.height = h;
        frm.width = w;
        frm.bottomMargin = h * -20 / 126;
        frm.leftMargin = w * 26 / 240;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.c_rank_second)
                .getLayoutParams();
        frm.height = h;
        frm.width = w;
        frm.bottomMargin = h * -20 / 126;
        frm.leftMargin = w * 26 / 240;


        frm = (FrameLayout.LayoutParams) findViewById(R.id.c_rank_third)
                .getLayoutParams();
        frm.height = h;
        frm.width = w;
        frm.bottomMargin = h * -20 / 126;
        frm.leftMargin = w * 26 / 240;

        w = getwidth(1100);
        th = w * 4 / 1100;
        frm = (FrameLayout.LayoutParams) findViewById(
                R.id.ivHorSeperator1).getLayoutParams();
        frm.width = w;
        frm.height = th;
        frm.topMargin = h * 420 / 126;

        frm = (FrameLayout.LayoutParams) findViewById(
                R.id.ivHorSeperator2).getLayoutParams();
        frm.width = w;
        frm.height = th;
        frm.topMargin = h * 510 / 126;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.win_lin1)
                .getLayoutParams();
        frm.topMargin = h * 100 / 126;

        h = getheight(500);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.win_line1)
                .getLayoutParams();
        lin.height = h;
        lin.leftMargin = h * 40 / 500;
        lin.rightMargin = h * 40 / 500;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.win_line2)
                .getLayoutParams();
        lin.height = h;
        lin.leftMargin = h * 40 / 500;
        lin.rightMargin = h * 40 / 500;

        w = getwidth(160);
        h = w * 70 / 160;
        frm = (FrameLayout.LayoutParams) findViewById(
                R.id.activity_winner_cont).getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.leftMargin = w * 340 / 160;
        frm.bottomMargin = h * 12 / 70;

        frm = (FrameLayout.LayoutParams) findViewById(
                R.id.activity_winner_exit_game).getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.rightMargin = w * 340 / 160;
        frm.bottomMargin = h * 12 / 70;


        h = getheight(70);
        frm = (FrameLayout.LayoutParams) findViewById(
                R.id.llRoundTimerText).getLayoutParams();
        frm.height = h;
        frm.bottomMargin = h * 12 / 70;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.win_linepart2)
                .getLayoutParams();
        lin.rightMargin = getwidth(20);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.win_linepart2)
                .getLayoutParams();
        lin.leftMargin = getwidth(20);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.llWinnerTwoScore)
                .getLayoutParams();
        lin.topMargin = getheight(50);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvRoundTwo)
                .getLayoutParams();
        lin.topMargin = getheight(6);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvPointTwo)
                .getLayoutParams();
        lin.topMargin = getheight(6);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.win_linepart1)
                .getLayoutParams();
        lin.leftMargin = getwidth(20);
        lin.rightMargin = getwidth(20);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.llWinnerOneScore)
                .getLayoutParams();
        lin.topMargin = getheight(50);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvRoundOne)
                .getLayoutParams();
        lin.topMargin = getheight(6);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvPointOne)
                .getLayoutParams();
        lin.topMargin = getheight(6);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.win_linepart2)
                .getLayoutParams();
        lin.leftMargin = getwidth(20);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.llWinnerThreeScore)
                .getLayoutParams();
        lin.topMargin = getheight(50);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvRoundThree)
                .getLayoutParams();
        lin.topMargin = getheight(6);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvPointThree)
                .getLayoutParams();
        lin.topMargin = getheight(6);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.winnerOneChips)
                .getLayoutParams();
        lin.topMargin = getheight(55);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.winnerSecondChips)
                .getLayoutParams();
        lin.topMargin = getheight(55);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.winnerThirdChips)
                .getLayoutParams();
        lin.topMargin = getheight(55);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvRoundNumber).getLayoutParams();
        lin.width = getwidth(40);
        lin.height = getwidth(40);
    }

    private int getheight(int val) {
        // TODO Auto-generated method stub
        return Screen_Hight * val / 720;
    }

    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return Screen_Width * val / 1280;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        if (CDTimer != null) {
            CDTimer.cancel();
        }
        if (timerHandler != null) {
            timerHandler.removeCallbacksAndMessages(null);
        }

        if (adHandler != null) {
            adHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onBackPressed() {
        return;

    }

    private void startTimer() {


        tvSecondsText.setVisibility(View.VISIBLE);
        tvRoundNumber.setVisibility(View.VISIBLE);
        nexrroundtext.setText("New Game begins in ");

        CDTimer = new CountDownTimer(5 * 1000,
                1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

                Logger.print("Round Timer : "
                        + (int) (millisUntilFinished / 1000));

                tvRoundNumber.setText("" + (int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub

                finish();
                overridePendingTransition(0, android.R.anim.slide_out_right);

            }
        }.start();
    }

    private void setWinnerData(JSONArray jsonArray) throws JSONException {
        // TODO Auto-generated method stub

        Logger.print("Set Winner DATA " + jsonArray.toString());
        int winner = -1;
        text.setVisibility(View.GONE);
        int ps1 = -1, ps2 = -1, ps3 = -1;
        int sc;
        String name;
        String[] name1;

        if (jsonArray.length() == 0) {

            text.setVisibility(View.VISIBLE);

        } else {

            text.setVisibility(View.GONE);
            JSONObject jObject1;

            boolean isSelfUserWin = false, isSelfUserPlaying = false;

            boolean isWinner;
            for (int i = 0; i < jsonArray.length(); i++) {

                jObject1 = jsonArray.getJSONObject(i);
                isWinner = false;
                Logger.print("<<<<>>>><<<<<>>>>>> " + jObject1.toString());

                if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                        .getString(Parameters._id))) {
                    isSelfUserPlaying = true;
                }

                if (jObject1.has(Parameters.winner))
                    isWinner = jObject1.getBoolean(Parameters.winner);

                if (isWinner) {

                    // first_rank_container.setBackgroundResource(R.drawable.weeklywinner_userbg);
                    if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                            .getString(Parameters._id))) {
                        isSelfUserWin = true;
                    }
                    c_rank_first.setVisibility(View.VISIBLE);
                    c_rank_first.setBackgroundResource(R.drawable.winnertag);

                    name = jObject1.getJSONObject(Parameters.User_Info)
                            .getString(Parameters.User_Name);

                    name1 = name.split(" ");
                    c_username_first.setText("" + name1[0]);

                    try {
                        if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                .getString(Parameters._id))) {
                            if (PreferenceManager.get_picPath().length() > 0) {
                                Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                c_pic_first.setImageDrawable(d);
                            } else {
                                c_pic_first.setImageResource(PreferenceManager.getprofilepic());
                            }
                        } else {
                            c_pic_first.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                    .getInt(Parameters.ProfilePicture));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    sc = jsonArray.getJSONObject(i).getInt(Parameters.totalsum);
                    tvPointOne.setText("" + sc);

                    roundPlayed = jsonArray.getJSONObject(i)
                            .getJSONArray(Parameters.points).length();

                    tvRoundOne.setText("" + roundPlayed);
                    winner = i;

                    if (jsonArray.getJSONObject(i)
                            .has(Parameters.winningChips)) {
                        winnerOneChips.setVisibility(View.VISIBLE);
                        long winChips = jsonArray.getJSONObject(i)
                                .getLong(Parameters.winningChips);
                        if (winChips > 100000) {
                            winnerOneChips.setText(""
                                    + c.numDifferentiation(winChips));
                        } else {
                            if (winChips < 0) {
                                winnerOneChips.setText("-"
                                        + c.formatter
                                        .format(winChips * -1));
                            } else {
                                winnerOneChips.setText(""
                                        + c.formatter.format(winChips));
                            }
                        }

                    }
                    break;

                }

            }

            if (isSelfUserWin) {
                activity_wt_ctitle.setText("You are the Winner!!!");
            } else if (isSelfUserPlaying) {
                activity_wt_ctitle.setText("You Lost");
            } else {
                activity_wt_ctitle.setText("Winner");
            }


            if (jsonArray.length() > 1) {

                if (jsonArray.length() > 0)
                    ps1 = jsonArray.getJSONObject(0)
                            .getInt(Parameters.totalsum);
                if (jsonArray.length() > 1)
                    ps2 = jsonArray.getJSONObject(1)
                            .getInt(Parameters.totalsum);
                if (jsonArray.length() > 2)
                    ps3 = jsonArray.getJSONObject(2)
                            .getInt(Parameters.totalsum);

                Logger.print(" " + ps1 + " " + ps2 + " " + ps3);

                if (winner == 0) {

                    if (ps2 >= ps3) {

                        jObject1 = jsonArray.getJSONObject(1);
                        if (jObject1.has(Parameters.winner)) {
                            isWinner = jObject1.getBoolean(Parameters.winner);
                            if (isWinner)
                                c_rank_second.setVisibility(View.VISIBLE);
                        }
                        // c_.setImageResource(R.drawable.one);
                        name = jObject1.getJSONObject(Parameters.User_Info)
                                .getString(Parameters.User_Name);
                        name1 = name.split(" ");
                        c_username_second.setText("" + name1[0]);

                        try {
                            if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                    .getString(Parameters._id))) {
                                if (PreferenceManager.get_picPath().length() > 0) {
                                    Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                    c_pic_second.setImageDrawable(d);
                                } else {
                                    c_pic_second.setImageResource(PreferenceManager.getprofilepic());
                                }
                            } else {
                                c_pic_second.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                        .getInt(Parameters.ProfilePicture));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        sc = jsonArray.getJSONObject(1).getInt(
                                Parameters.totalsum);
                        tvPointTwo.setText("" + sc);
                        roundPlayed = jsonArray.getJSONObject(1)
                                .getJSONArray(Parameters.points).length();
                        tvRoundTwo.setText("" + roundPlayed);

                        if (jsonArray.getJSONObject(1)
                                .has(Parameters.winningChips)) {
                            winnerSecondChips.setVisibility(View.VISIBLE);
                            long winChips = jsonArray.getJSONObject(1)
                                    .getLong(Parameters.winningChips);
                            if (winChips > 100000) {

                                winnerSecondChips.setText(""
                                        + c.numDifferentiation(winChips));

                            } else {

                                if (winChips < 0) {

                                    winnerSecondChips.setText("-"
                                            + c.formatter
                                            .format(winChips * -1));
                                } else {
                                    winnerSecondChips.setText(""
                                            + c.formatter.format(winChips));
                                }

                            }

                        }

                        if (jsonArray.length() > 2 && ps3 < ps2) {

                            jObject1 = jsonArray.getJSONObject(2);
                            if (jObject1.has(Parameters.winner)) {
                                isWinner = jObject1
                                        .getBoolean(Parameters.winner);
                                if (isWinner)
                                    c_rank_third.setVisibility(View.VISIBLE);
                            }
                            // c_.setImageResource(R.drawable.one);
                            name = jObject1.getJSONObject(Parameters.User_Info)
                                    .getString(Parameters.User_Name);
                            name1 = name.split(" ");
                            c_username_third.setText("" + name1[0]);

                            try {
                                if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                        .getString(Parameters._id))) {
                                    if (PreferenceManager.get_picPath().length() > 0) {
                                        Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                        c_pic_third.setImageDrawable(d);
                                    } else {
                                        c_pic_third.setImageResource(PreferenceManager.getprofilepic());
                                    }
                                } else {
                                    c_pic_third.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                            .getInt(Parameters.ProfilePicture));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            sc = jsonArray.getJSONObject(2).getInt(
                                    Parameters.totalsum);
                            tvPointThree.setText("" + sc);
                            roundPlayed = jsonArray.getJSONObject(2)
                                    .getJSONArray(Parameters.points).length();
                            tvRoundThree.setText("" + roundPlayed);

                            if (jsonArray.getJSONObject(2)
                                    .has(Parameters.winningChips)) {
                                long winChips = jsonArray.getJSONObject(2)
                                        .getLong(Parameters.winningChips);
                                winnerThirdChips.setVisibility(View.VISIBLE);
                                if (winChips > 100000) {

                                    winnerThirdChips.setText(""
                                            + c.numDifferentiation(winChips));

                                } else {

                                    if (winChips < 0) {

                                        winnerThirdChips.setText("-"
                                                + c.formatter
                                                .format(winChips * -1));
                                    } else {
                                        winnerThirdChips.setText(""
                                                + c.formatter.format(winChips));
                                    }

                                }

                            }

                            Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 2 "
                                    + sc + " " + roundPlayed);
                        }

                    }

                    if (jsonArray.length() > 2 && ps3 >= ps2) {

                        Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 2");
                        jObject1 = jsonArray.getJSONObject(2);
                        if (jObject1.has(Parameters.winner)) {
                            isWinner = jObject1.getBoolean(Parameters.winner);
                            if (isWinner)
                                c_rank_third.setVisibility(View.VISIBLE);
                        }
                        // c_.setImageResource(R.drawable.one);
                        name = jObject1.getJSONObject(Parameters.User_Info)
                                .getString(Parameters.User_Name);
                        name1 = name.split(" ");
                        c_username_third.setText("" + name1[0]);

                        try {
                            if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                    .getString(Parameters._id))) {
                                if (PreferenceManager.get_picPath().length() > 0) {
                                    Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                    c_pic_third.setImageDrawable(d);
                                } else {
                                    c_pic_third.setImageResource(PreferenceManager.getprofilepic());
                                }
                            } else {
                                c_pic_third.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                        .getInt(Parameters.ProfilePicture));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        sc = jsonArray.getJSONObject(2).getInt(
                                Parameters.totalsum);
                        tvPointThree.setText("" + sc);
                        roundPlayed = jsonArray.getJSONObject(2)
                                .getJSONArray(Parameters.points).length();
                        tvRoundThree.setText("" + roundPlayed);

                        if (jsonArray.getJSONObject(2)
                                .has(Parameters.winningChips)) {
                            long winChips = jsonArray.getJSONObject(2)
                                    .getLong(Parameters.winningChips);
                            winnerThirdChips.setVisibility(View.VISIBLE);
                            if (winChips > 100000) {

                                winnerThirdChips.setText(""
                                        + c.numDifferentiation(winChips));

                            } else {

                                if (winChips < 0) {

                                    winnerThirdChips.setText("-"
                                            + c.formatter
                                            .format(winChips * -1));
                                } else {
                                    winnerThirdChips.setText(""
                                            + c.formatter.format(winChips));
                                }

                            }

                        }

                        Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 2 "
                                + sc + " " + roundPlayed);

                        if (ps2 < ps3) {

                            Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 11");

                            jObject1 = jsonArray.getJSONObject(1);
                            if (jObject1.has(Parameters.winner)) {
                                isWinner = jObject1
                                        .getBoolean(Parameters.winner);
                                if (isWinner)
                                    c_rank_second.setVisibility(View.VISIBLE);
                            }
                            // c_.setImageResource(R.drawable.one);
                            name = jObject1.getJSONObject(Parameters.User_Info)
                                    .getString(Parameters.User_Name);
                            name1 = name.split(" ");
                            c_username_second.setText("" + name1[0]);

                            try {
                                if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                        .getString(Parameters._id))) {
                                    if (PreferenceManager.get_picPath().length() > 0) {
                                        Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                        c_pic_second.setImageDrawable(d);
                                    } else {
                                        c_pic_second.setImageResource(PreferenceManager.getprofilepic());
                                    }
                                } else {
                                    c_pic_second.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                            .getInt(Parameters.ProfilePicture));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            sc = jsonArray.getJSONObject(1).getInt(
                                    Parameters.totalsum);
                            tvPointTwo.setText("" + sc);
                            roundPlayed = jsonArray.getJSONObject(1)
                                    .getJSONArray(Parameters.points).length();

                            if (jsonArray.getJSONObject(1)
                                    .has(Parameters.winningChips)) {
                                long winChips = jsonArray.getJSONObject(1)
                                        .getLong(Parameters.winningChips);
                                winnerSecondChips.setVisibility(View.VISIBLE);
                                if (winChips > 100000) {

                                    winnerSecondChips.setText(""
                                            + c.numDifferentiation(winChips));

                                } else {

                                    if (winChips < 0) {

                                        winnerSecondChips.setText("-"
                                                + c.formatter
                                                .format(winChips * -1));
                                    } else {
                                        winnerSecondChips.setText(""
                                                + c.formatter.format(winChips));
                                    }

                                }

                            }
                            tvRoundTwo.setText("" + roundPlayed);
                        }
                    }

                } else if (winner == 1) {

                    if (ps1 >= ps3) {

                        Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 3");
                        jObject1 = jsonArray.getJSONObject(0);
                        if (jObject1.has(Parameters.winner)) {
                            isWinner = jObject1.getBoolean(Parameters.winner);
                            if (isWinner)
                                c_rank_second.setVisibility(View.VISIBLE);
                        }
                        // c_.setImageResource(R.drawable.one);
                        name = jObject1.getJSONObject(Parameters.User_Info)
                                .getString(Parameters.User_Name);
                        name1 = name.split(" ");
                        c_username_second.setText("" + name1[0]);

                        try {
                            if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                    .getString(Parameters._id))) {
                                if (PreferenceManager.get_picPath().length() > 0) {
                                    Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                    c_pic_second.setImageDrawable(d);
                                } else {
                                    c_pic_second.setImageResource(PreferenceManager.getprofilepic());
                                }
                            } else {
                                c_pic_second.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                        .getInt(Parameters.ProfilePicture));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        sc = jsonArray.getJSONObject(0).getInt(
                                Parameters.totalsum);
                        tvPointTwo.setText("" + sc);
                        roundPlayed = jsonArray.getJSONObject(0)
                                .getJSONArray(Parameters.points).length();
                        tvRoundTwo.setText("" + roundPlayed);
                        if (jsonArray.getJSONObject(0)
                                .has(Parameters.winningChips)) {
                            winnerSecondChips.setVisibility(View.VISIBLE);
                            long winChips = jsonArray.getJSONObject(0)
                                    .getLong(Parameters.winningChips);
                            if (winChips > 100000) {

                                winnerSecondChips.setText(""
                                        + c.numDifferentiation(winChips));

                            } else {

                                if (winChips < 0) {

                                    winnerSecondChips.setText("-"
                                            + c.formatter
                                            .format(winChips * -1));
                                } else {
                                    winnerSecondChips.setText(""
                                            + c.formatter.format(winChips));
                                }

                            }

                        }
                        Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 3 "
                                + sc + " " + roundPlayed);

                        if (jsonArray.length() > 2 && ps3 < ps1) {

                            Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 4");

                            jObject1 = jsonArray.getJSONObject(2);
                            if (jObject1.has(Parameters.winner)) {
                                isWinner = jObject1
                                        .getBoolean(Parameters.winner);
                                if (isWinner)
                                    c_rank_third.setVisibility(View.VISIBLE);
                            }
                            // c_.setImageResource(R.drawable.one);
                            name = jObject1.getJSONObject(Parameters.User_Info)
                                    .getString(Parameters.User_Name);
                            name1 = name.split(" ");
                            c_username_third.setText("" + name1[0]);

                            try {
                                if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                        .getString(Parameters._id))) {
                                    if (PreferenceManager.get_picPath().length() > 0) {
                                        Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                        c_pic_third.setImageDrawable(d);
                                    } else {
                                        c_pic_third.setImageResource(PreferenceManager.getprofilepic());
                                    }
                                } else {
                                    c_pic_third.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                            .getInt(Parameters.ProfilePicture));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            sc = jsonArray.getJSONObject(2).getInt(
                                    Parameters.totalsum);
                            tvPointThree.setText("" + sc);
                            roundPlayed = jsonArray.getJSONObject(2)
                                    .getJSONArray(Parameters.points).length();
                            tvRoundThree.setText("" + roundPlayed);

                            if (jsonArray.getJSONObject(2)
                                    .has(Parameters.winningChips)) {
                                winnerThirdChips.setVisibility(View.VISIBLE);
                                long winChips = jsonArray.getJSONObject(2)
                                        .getLong(Parameters.winningChips);
                                if (winChips > 100000) {

                                    winnerThirdChips.setText(""
                                            + c.numDifferentiation(winChips));

                                } else {

                                    if (winChips < 0) {

                                        winnerThirdChips.setText("-"
                                                + c.formatter
                                                .format(winChips * -1));
                                    } else {
                                        winnerThirdChips.setText(""
                                                + c.formatter.format(winChips));
                                    }

                                }

                            }

                        }
                    }
                    if (jsonArray.length() > 2 && ps3 >= ps1) {

                        Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 4");

                        jObject1 = jsonArray.getJSONObject(2);
                        if (jObject1.has(Parameters.winner)) {
                            isWinner = jObject1.getBoolean(Parameters.winner);
                            if (isWinner)
                                c_rank_third.setVisibility(View.VISIBLE);
                        }
                        // c_.setImageResource(R.drawable.one);
                        name = jObject1.getJSONObject(Parameters.User_Info)
                                .getString(Parameters.User_Name);
                        name1 = name.split(" ");
                        c_username_third.setText("" + name1[0]);

                        try {
                            if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                    .getString(Parameters._id))) {
                                if (PreferenceManager.get_picPath().length() > 0) {
                                    Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                    c_pic_third.setImageDrawable(d);
                                } else {
                                    c_pic_third.setImageResource(PreferenceManager.getprofilepic());
                                }
                            } else {
                                c_pic_third.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                        .getInt(Parameters.ProfilePicture));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        sc = jsonArray.getJSONObject(2).getInt(
                                Parameters.totalsum);
                        tvPointThree.setText("" + sc);
                        roundPlayed = jsonArray.getJSONObject(2)
                                .getJSONArray(Parameters.points).length();
                        tvRoundThree.setText("" + roundPlayed);

                        if (jsonArray.getJSONObject(2)
                                .has(Parameters.winningChips)) {
                            winnerThirdChips.setVisibility(View.VISIBLE);
                            long winChips = jsonArray.getJSONObject(2)
                                    .getLong(Parameters.winningChips);
                            if (winChips > 100000) {

                                winnerThirdChips.setText(""
                                        + c.numDifferentiation(winChips));

                            } else {

                                if (winChips < 0) {

                                    winnerThirdChips.setText("-"
                                            + c.formatter
                                            .format(winChips * -1));
                                } else {
                                    winnerThirdChips.setText(""
                                            + c.formatter.format(winChips));
                                }

                            }

                        }

                        Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 4 "
                                + sc + " " + roundPlayed);

                        if (jsonArray.length() > 2 && ps1 < ps3) {

                            Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 3");
                            jObject1 = jsonArray.getJSONObject(0);
                            if (jObject1.has(Parameters.winner)) {
                                isWinner = jObject1
                                        .getBoolean(Parameters.winner);
                                if (isWinner)
                                    c_rank_second.setVisibility(View.VISIBLE);
                            }
                            // c_.setImageResource(R.drawable.one);
                            name = jObject1.getJSONObject(Parameters.User_Info)
                                    .getString(Parameters.User_Name);
                            name1 = name.split(" ");
                            c_username_second.setText("" + name1[0]);

                            try {
                                if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                        .getString(Parameters._id))) {
                                    if (PreferenceManager.get_picPath().length() > 0) {
                                        Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                        c_pic_second.setImageDrawable(d);
                                    } else {
                                        c_pic_second.setImageResource(PreferenceManager.getprofilepic());
                                    }
                                } else {
                                    c_pic_second.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                            .getInt(Parameters.ProfilePicture));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            sc = jsonArray.getJSONObject(0).getInt(
                                    Parameters.totalsum);
                            tvPointTwo.setText("" + sc);
                            roundPlayed = jsonArray.getJSONObject(0)
                                    .getJSONArray(Parameters.points).length();
                            tvRoundTwo.setText("" + roundPlayed);

                            if (jsonArray.getJSONObject(0)
                                    .has(Parameters.winningChips)) {
                                winnerSecondChips.setVisibility(View.VISIBLE);
                                long winChips = jsonArray.getJSONObject(0)
                                        .getLong(Parameters.winningChips);
                                if (winChips > 100000) {

                                    winnerSecondChips.setText(""
                                            + c.numDifferentiation(winChips));

                                } else {

                                    if (winChips < 0) {

                                        winnerSecondChips.setText("-"
                                                + c.formatter
                                                .format(winChips * -1));
                                    } else {
                                        winnerSecondChips.setText(""
                                                + c.formatter.format(winChips));
                                    }

                                }

                            }
                            Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 3 "
                                    + sc + " " + roundPlayed);

                        }

                    }

                } else if (winner == 2) {

                    if (ps1 >= ps2) {

                        Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 5");

                        jObject1 = jsonArray.getJSONObject(0);
                        if (jObject1.has(Parameters.winner)) {
                            isWinner = jObject1.getBoolean(Parameters.winner);
                            if (isWinner)
                                c_rank_second.setVisibility(View.VISIBLE);
                        }
                        // c_.setImageResource(R.drawable.one);
                        name = jObject1.getJSONObject(Parameters.User_Info)
                                .getString(Parameters.User_Name);
                        name1 = name.split(" ");
                        c_username_second.setText("" + name1[0]);

                        try {
                            if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                    .getString(Parameters._id))) {
                                if (PreferenceManager.get_picPath().length() > 0) {
                                    Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                    c_pic_second.setImageDrawable(d);
                                } else {
                                    c_pic_second.setImageResource(PreferenceManager.getprofilepic());
                                }
                            } else {
                                c_pic_second.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                        .getInt(Parameters.ProfilePicture));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        sc = jsonArray.getJSONObject(0).getInt(
                                Parameters.totalsum);
                        tvPointTwo.setText("" + sc);
                        roundPlayed = jsonArray.getJSONObject(0)
                                .getJSONArray(Parameters.points).length();
                        tvRoundTwo.setText("" + roundPlayed);

                        if (jsonArray.getJSONObject(0)
                                .has(Parameters.winningChips)) {
                            winnerSecondChips.setVisibility(View.VISIBLE);
                            long winChips = jsonArray.getJSONObject(0)
                                    .getLong(Parameters.winningChips);
                            if (winChips > 100000) {

                                winnerSecondChips.setText(""
                                        + c.numDifferentiation(winChips));

                            } else {

                                if (winChips < 0) {

                                    winnerSecondChips.setText("-"
                                            + c.formatter
                                            .format(winChips * -1));
                                } else {
                                    winnerSecondChips.setText(""
                                            + c.formatter.format(winChips));
                                }

                            }

                        }

                        Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 5 "
                                + sc + " " + roundPlayed);

                        if (jsonArray.length() > 2 && ps2 < ps1) {

                            Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 6");

                            jObject1 = jsonArray.getJSONObject(1);
                            if (jObject1.has(Parameters.winner)) {
                                isWinner = jObject1
                                        .getBoolean(Parameters.winner);
                                if (isWinner)
                                    c_rank_third.setVisibility(View.VISIBLE);
                            }
                            // c_.setImageResource(R.drawable.one);
                            name = jObject1.getJSONObject(Parameters.User_Info)
                                    .getString(Parameters.User_Name);
                            name1 = name.split(" ");
                            c_username_third.setText("" + name1[0]);

                            try {
                                if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                        .getString(Parameters._id))) {
                                    if (PreferenceManager.get_picPath().length() > 0) {
                                        Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                        c_pic_third.setImageDrawable(d);
                                    } else {
                                        c_pic_third.setImageResource(PreferenceManager.getprofilepic());
                                    }
                                } else {
                                    c_pic_third.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                            .getInt(Parameters.ProfilePicture));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            sc = jsonArray.getJSONObject(1).getInt(
                                    Parameters.totalsum);
                            tvPointThree.setText("" + sc);
                            roundPlayed = jsonArray.getJSONObject(1)
                                    .getJSONArray(Parameters.points).length();
                            tvRoundThree.setText("" + roundPlayed);

                            if (jsonArray.getJSONObject(1)
                                    .has(Parameters.winningChips)) {
                                winnerThirdChips.setVisibility(View.VISIBLE);
                                long winChips = jsonArray.getJSONObject(1)
                                        .getLong(Parameters.winningChips);
                                if (winChips > 100000) {

                                    winnerThirdChips.setText(""
                                            + c.numDifferentiation(winChips));

                                } else {

                                    if (winChips < 0) {

                                        winnerThirdChips.setText("-"
                                                + c.formatter
                                                .format(winChips * -1));
                                    } else {
                                        winnerThirdChips.setText(""
                                                + c.formatter.format(winChips));
                                    }

                                }

                            }

                        }
                    }

                    if (jsonArray.length() > 2 && ps2 >= ps1) {

                        Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 6");

                        jObject1 = jsonArray.getJSONObject(1);
                        if (jObject1.has(Parameters.winner)) {
                            isWinner = jObject1.getBoolean(Parameters.winner);
                            if (isWinner)
                                c_rank_third.setVisibility(View.VISIBLE);
                        }
                        // c_.setImageResource(R.drawable.one);
                        name = jObject1.getJSONObject(Parameters.User_Info)
                                .getString(Parameters.User_Name);
                        name1 = name.split(" ");
                        c_username_third.setText("" + name1[0]);

                        try {
                            if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                    .getString(Parameters._id))) {
                                if (PreferenceManager.get_picPath().length() > 0) {
                                    Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                    c_pic_third.setImageDrawable(d);
                                } else {
                                    c_pic_third.setImageResource(PreferenceManager.getprofilepic());
                                }
                            } else {
                                c_pic_third.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                        .getInt(Parameters.ProfilePicture));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        sc = jsonArray.getJSONObject(1).getInt(
                                Parameters.totalsum);
                        tvPointThree.setText("" + sc);
                        roundPlayed = jsonArray.getJSONObject(1)
                                .getJSONArray(Parameters.points).length();
                        tvRoundThree.setText("" + roundPlayed);

                        if (jsonArray.getJSONObject(1)
                                .has(Parameters.winningChips)) {
                            winnerThirdChips.setVisibility(View.VISIBLE);
                            long winChips = jsonArray.getJSONObject(1)
                                    .getLong(Parameters.winningChips);
                            if (winChips > 100000) {

                                winnerThirdChips.setText(""
                                        + c.numDifferentiation(winChips));

                            } else {

                                if (winChips < 0) {

                                    winnerThirdChips.setText("-"
                                            + c.formatter
                                            .format(winChips * -1));
                                } else {
                                    winnerThirdChips.setText(""
                                            + c.formatter.format(winChips));
                                }

                            }

                        }

                        Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 6 "
                                + sc + " " + roundPlayed);

                        if (jsonArray.length() > 2 && ps1 < ps2) {

                            Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 5");

                            jObject1 = jsonArray.getJSONObject(0);
                            if (jObject1.has(Parameters.winner)) {
                                isWinner = jObject1
                                        .getBoolean(Parameters.winner);
                                if (isWinner)
                                    c_rank_second.setVisibility(View.VISIBLE);
                            }
                            // c_.setImageResource(R.drawable.one);
                            name = jObject1.getJSONObject(Parameters.User_Info)
                                    .getString(Parameters.User_Name);
                            name1 = name.split(" ");
                            c_username_second.setText("" + name1[0]);

                            try {
                                if (PreferenceManager.get_id().contentEquals(jObject1.getJSONObject(Parameters.User_Info)
                                        .getString(Parameters._id))) {
                                    if (PreferenceManager.get_picPath().length() > 0) {
                                        Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                        c_pic_second.setImageDrawable(d);
                                    } else {
                                        c_pic_second.setImageResource(PreferenceManager.getprofilepic());
                                    }
                                } else {
                                    c_pic_second.setImageResource(jObject1.getJSONObject(Parameters.User_Info)
                                            .getInt(Parameters.ProfilePicture));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            sc = jsonArray.getJSONObject(0).getInt(
                                    Parameters.totalsum);
                            tvPointTwo.setText("" + sc);
                            roundPlayed = jsonArray.getJSONObject(0)
                                    .getJSONArray(Parameters.points).length();
                            tvRoundTwo.setText("" + roundPlayed);

                            if (jsonArray.getJSONObject(0)
                                    .has(Parameters.winningChips)) {
                                winnerSecondChips.setVisibility(View.VISIBLE);
                                long winChips = jsonArray.getJSONObject(0)
                                        .getLong(Parameters.winningChips);
                                if (winChips > 100000) {

                                    winnerSecondChips.setText(""
                                            + c.numDifferentiation(winChips));

                                } else {

                                    if (winChips < 0) {

                                        winnerSecondChips.setText("-"
                                                + c.formatter
                                                .format(winChips * -1));
                                    } else {
                                        winnerSecondChips.setText(""
                                                + c.formatter.format(winChips));
                                    }

                                }

                            }

                            Logger.print("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW = > 5 "
                                    + sc + " " + roundPlayed);

                        }

                    }

                }

            } else {

                lin3.setVisibility(View.GONE);
                lin2.setVisibility(View.GONE);
                lineOne.setVisibility(View.GONE);
                lineTwo.setVisibility(View.GONE);
            }

            if (jsonArray.length() == 2) {
                lin3.setVisibility(View.GONE);
                lineTwo.setVisibility(View.GONE);
            }

        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        c.payment_Handler = this.handler;

        c.con = this;
        c.activity = Activity_GameWinner.this;

        super.onResume();
        try {
            adHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //if (c.adFlag == 1) {
                        /*java.util.Random rand = new java.util.Random();
                        int ranNo;
                        if (c.adProb <= 0)
                            ranNo = 0;
                        else
                            ranNo = rand.nextInt(c.adProb);
                        Logger.print("RANDOM NUMBER => " + ranNo);*/
                    if (firstTime && PreferenceManager.getisshowAd() /*&& c.is_Ads_Show_After_Two_Day*/) {
                        Logger.print("GETTTTTT ADDDDDDDDDDDDDDDDD aCTIVITY gaMEwinner class");
                        firstTime = false;
                        if (PlayScreen2.mInterstitial != null && PlayScreen2.mInterstitial.isLoaded()) {
                            Logger.print("GETTTTTT ADDDDDDDDDDDDDDDDD aCTIVITY gaMEwinner class Load ");
                            if (!isFinishing()) {
                                PlayScreen2.mInterstitial.show();
                            }
                        } else {
                            Logger.print("GETTTTTT ADDDDDDDDDDDDDDDDD aCTIVITY gaMEwinner class not Load ");
                            //c.mInterstitial2.show();
                            if (PlayScreen2.mInterstitial != null) {
                                PlayScreen2.requestNewInterstitial();
                            } else {
                                PlayScreen2.showAds();
                            }
                        }
                    }
                    // }
                }
            }, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void FindViewById() {
        // TODO Auto-generated method stub
        Screen_Hight = c.height;
        Screen_Width = c.width;
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "font/PoetsenOne-Regular.ttf");

        ImageView win_close = findViewById(R.id.winner_close);
        win_close.setOnClickListener(this);

        lin2 = findViewById(R.id.win_linepart2);
        lin3 = findViewById(R.id.win_linepart3);

        text = findViewById(R.id.text);
        text.setText("Oops No data found.");
        text.setVisibility(View.GONE);

        tvRoundNumber = findViewById(R.id.tvRoundNumber);
        tvRoundNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));
        tvRoundNumber.setTypeface(typeface, Typeface.BOLD);
        tvRoundNumber.setVisibility(View.GONE);

        nexrroundtext = findViewById(R.id.winner_prepaired_text);
        nexrroundtext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));
        nexrroundtext.setTypeface(typeface, Typeface.BOLD);
        nexrroundtext.setVisibility(View.GONE);

        tvSecondsText = findViewById(R.id.tvSecondsText);
        tvSecondsText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));
        tvSecondsText.setTypeface(typeface, Typeface.BOLD);
        tvSecondsText.setVisibility(View.GONE);

        TextView tvRoundStrOne = findViewById(R.id.tvRoundStrOne);
        tvRoundStrOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(20));
        tvRoundStrOne.setTypeface(typeface, Typeface.BOLD);

        tvRoundOne = findViewById(R.id.tvRoundOne);
        tvRoundOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(20));
        tvRoundOne.setTypeface(typeface);

        TextView tvRoundStrTwo = findViewById(R.id.tvRoundStrTwo);
        tvRoundStrTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(20));
        tvRoundStrTwo.setTypeface(typeface, Typeface.BOLD);

        tvRoundTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(20));
        tvRoundTwo.setTypeface(typeface, Typeface.BOLD);

        TextView tvRoundStrThree = findViewById(R.id.tvRoundStrThree);
        tvRoundStrThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(20));
        tvRoundStrThree.setTypeface(typeface, Typeface.BOLD);

        tvRoundThree = findViewById(R.id.tvRoundThree);
        tvRoundThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(20));
        tvRoundThree.setTypeface(typeface, Typeface.BOLD);

        TextView tvPointStrOne = findViewById(R.id.tvPointStrOne);
        tvPointStrOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvPointStrOne.setTypeface(typeface, Typeface.BOLD);

        tvPointOne = findViewById(R.id.tvPointOne);
        tvPointOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvPointOne.setTypeface(typeface, Typeface.BOLD);

        TextView tvPointStrTwo = findViewById(R.id.tvPointStrTwo);
        tvPointStrTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvPointStrTwo.setTypeface(typeface, Typeface.BOLD);

        tvPointTwo = findViewById(R.id.tvPointTwo);
        tvPointTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvPointTwo.setTypeface(typeface, Typeface.BOLD);

        TextView tvPointStrThree = findViewById(R.id.tvPointStrThree);
        tvPointStrThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvPointStrThree.setTypeface(typeface, Typeface.BOLD);

        tvPointThree = findViewById(R.id.tvPointThree);
        tvPointThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvPointThree.setTypeface(typeface, Typeface.BOLD);

        // note = (TextView) findViewById(R.id.note);
        // note.setText("Note : System will not display your name on Weekly
        // Contest if you have won this contest in past 4 weeks.");
        // note.setVisibility(View.INVISIBLE);

        exit_game = findViewById(R.id.activity_winner_exit_game);
        exit_game.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(26));
        exit_game.setPadding(0, 0, 0, getheight(6));
        exit_game.setOnClickListener(this);
        exit_game.setTypeface(typeface, Typeface.BOLD);

        activity_wt_ctitle = findViewById(R.id.activity_wt_ctitle);
        activity_wt_ctitle.setTypeface(typeface, Typeface.BOLD);
        activity_wt_ctitle.setText(R.string.Winner);
        activity_wt_ctitle
                .setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));

        ContinueBtn = findViewById(R.id.activity_winner_cont);

        ContinueBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(26));
        ContinueBtn.setOnClickListener(this);
        ContinueBtn.setTypeface(typeface, Typeface.BOLD);

        c_rank_first = findViewById(R.id.c_rank_first);
        c_rank_second = findViewById(R.id.c_rank_second);
        c_rank_third = findViewById(R.id.c_rank_third);

        c_username_first = findViewById(R.id.c_username_first);
        c_username_first.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        c_username_first.setTypeface(typeface, Typeface.BOLD);

        c_username_second = findViewById(R.id.c_username_second);
        c_username_second.setTypeface(typeface, Typeface.BOLD);
        c_username_second.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));

        c_username_third = findViewById(R.id.c_username_third);
        c_username_third.setTypeface(typeface, Typeface.BOLD);
        c_username_third.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));

        c_pic_first = (CircularImageView) findViewById(R.id.c_pic_first);
        c_pic_second = (CircularImageView) findViewById(R.id.c_pic_second);
        c_pic_third = (CircularImageView) findViewById(R.id.c_pic_third);

        lineOne = findViewById(R.id.win_line1);
        lineTwo = findViewById(R.id.win_line2);

        winnerOneChips = findViewById(R.id.winnerOneChips);
        winnerOneChips.setTypeface(typeface, Typeface.BOLD);
        winnerOneChips.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));


        winnerSecondChips = findViewById(R.id.winnerSecondChips);
        winnerSecondChips.setTypeface(typeface, Typeface.BOLD);
        winnerSecondChips.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));

        winnerThirdChips = findViewById(R.id.winnerThirdChips);
        winnerThirdChips.setTypeface(typeface, Typeface.BOLD);
        winnerThirdChips.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));

        Drawable img = getResources().getDrawable(R.drawable.small_chips);
        img.setBounds(0, 0, getwidth(31), getwidth(31));

        winnerOneChips.setCompoundDrawables(img, null, null, null);
        winnerSecondChips.setCompoundDrawables(img, null, null, null);
        winnerThirdChips.setCompoundDrawables(img, null, null, null);

        int pad = getwidth(6);

        winnerOneChips.setCompoundDrawablePadding(pad);
        winnerSecondChips.setCompoundDrawablePadding(pad);
        winnerThirdChips.setCompoundDrawablePadding(pad);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == ContinueBtn) {
            Music_Manager.buttonclick();

            if (PlayScreen2.handler != null) {
                Message message = new Message();
                message.what = ResponseCodes.Start_RoundTimer_In_Playscreen;
                message.obj = "5";
                PlayScreen2.handler.sendMessage(message);
            }
//            if(CDTimer != null) {
//                CDTimer.cancel();
//                CDTimer = null;
//            }
            isButtonClick = true;
            finish();
            overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
        }

        if (v == exit_game) {
            Music_Manager.buttonclick();
            try {
                isButtonClick = true;
                c.exitFromPlayScreen = true;
                c.isPlayingScreenOpen = false;
                Intent intent = new Intent(getApplicationContext(),
                        Dashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.none, R.anim.to_lefttoright);

                if (Dashboard.handler != null) {
                    Message msg = new Message();
                    msg.what = ResponseCodes.Finish_Playscreen;
                    Dashboard.handler.sendMessage(msg);
                }
//                if(CDTimer != null) {
//                    CDTimer.cancel();
//                    CDTimer = null;
//                }

                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {

                v.getBackground().setColorFilter(Color.parseColor("#30000000"),
                        PorterDuff.Mode.SRC_ATOP);
                v.invalidate();

                break;

            }
            case MotionEvent.ACTION_UP: {

                v.getBackground().clearColorFilter();
                v.invalidate();
                break;

            }
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        // The rest of your onStart() code.

    }

    @Override
    public void onStop() {
        super.onStop();
        // The rest of your onStop() code.

    }


}