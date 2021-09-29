package com.artoon.ginrummyoffline;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artoon.sort.Card;
import com.artoon.sort.Hand;
import com.artoon.sort.HandException;
import com.artoon.sort.ICardSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import DataStore.Item_Card;
import de.hdodenhof.circleimageview.CircleImageView;
import utils.C;
import utils.Logger;
import utils.Parameters;
import utils.PreferenceManager;
import utils.ResponseCodes;

/**
 * Created by Artoon on 20-Jul-16.
 */
public class RoundWinner extends Activity implements View.OnClickListener {

    public static Handler handler;
    private final Handler adHandler = new Handler();
    String[] cardString = {"c-1", "c-2", "c-3", "c-4", "c-5", "c-6", "c-7",
            "c-8", "c-9", "c-10", "c-11", "c-12", "c-13", "l-1", "l-2", "l-3",
            "l-4", "l-5", "l-6", "l-7", "l-8", "l-9", "l-10", "l-11", "l-12",
            "l-13", "k-1", "k-2", "k-3", "k-4", "k-5", "k-6", "k-7", "k-8",
            "k-9", "k-10", "k-11", "k-12", "k-13", "f-1", "f-2", "f-3", "f-4",
            "f-5", "f-6", "f-7", "f-8", "f-9", "f-10", "f-11", "f-12", "f-13"};
    TextView tvRoundTitle, tvPointsText, tvGamePoints, error;
    ListView roundList;
    int Screen_Hieght, Screen_Width;
    Typeface typeface;
    C c = C.getInstance();
    JSONObject roundWinnerData;
    JSONArray roundWinnerArray;
    Adapter_RoundWinner adapterRoundWinner;
    ImageView ivClose;
    Animation heartBeat;
    int roundNO, timerSec;
    int roundPoints;
    LinearLayout llTitleScoreContainer;
    boolean isWinner, isDraw;
    ImageView iv_big_gin_plus, iv_gin_plus, iv_undercut_plus;

    boolean istesting;
    int scorePoint1, scorePoint2, scorePoint3;
    CountDownTimer CDTimer;
    int time;
    TextView tvRoundNumber, tvSecondsText, nextroundtext, tvScoresTitle;
    FrameLayout frm_winner;
    TextView tv_win_chip;
    Button btn_win_exit, btn_win_continue;
    CircleImageView ivProfileImage;
    private Button btnContinue, btnExit;
    private boolean isAdShow = true;
    private TextView tv_winner_tittle;
    private boolean firstTime = true;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roundwinner);
        c.height = Screen_Hieght = PreferenceManager.getheight();
        c.width = Screen_Width = PreferenceManager.getwidth();
        c.typeface = Typeface.createFromAsset(getAssets(), "font/PoetsenOne-Regular.ttf");
        Screen_Hieght = c.height;
        Screen_Width = c.width;

        typeface = Typeface.createFromAsset(getAssets(),
                "font/PoetsenOne-Regular.ttf");
        String roundWinnerDataText = getIntent().getStringExtra("roundWinnerData");
        roundNO = getIntent().getIntExtra("round", 0);
        roundPoints = getIntent().getIntExtra("point", 0);
        isWinner = getIntent().getBooleanExtra("winner", false);
        Music_Manager m = new Music_Manager(this);
        isDraw = getIntent().getBooleanExtra("draw", false);
        timerSec = getIntent().getIntExtra("timer", 13);
        istesting = getIntent().getBooleanExtra("test", false);
        try {


            if (istesting) {
                roundWinnerArray = new JSONArray(roundWinnerDataText);
                Logger.print("Round winner Array 2 => " + roundWinnerArray.toString());
            } else {
                roundWinnerData = new JSONObject(roundWinnerDataText);
                roundWinnerArray = roundWinnerData.getJSONObject(Parameters.data).getJSONArray(Parameters.PlayersInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        findViewByIds();
        setScreen();
        initHandler();

        ShowInterstitialAd();

    }

    private void StartAnimationOnResult() {
        adapterRoundWinner = new Adapter_RoundWinner(roundWinnerArray);
        roundList.setAdapter(adapterRoundWinner);

        if (isWinner) {
            SetWinnerScreenData(roundWinnerArray);
        }
    }

    private void SetWinnerScreenData(JSONArray roundWinnerArray) {

        try {
            for (int i = 0; i < roundWinnerArray.length(); i++) {

                JSONObject userObj = roundWinnerArray.getJSONObject(i);

                if (userObj.has("w") && userObj.getBoolean("w") && userObj.getInt("si") == 0) {
                    btnExit.setVisibility(View.GONE);
                    btnContinue.setVisibility(View.GONE);

                    try {
                        if (PreferenceManager.get_picPath().length() > 0) {
                            Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                            if (d != null) {
                                ivProfileImage.setImageDrawable(d);
                            } else {
                                ivProfileImage.setImageResource(PreferenceManager.getprofilepic());
                            }
                        } else {
                            ivProfileImage.setImageResource(PreferenceManager.getprofilepic());
                        }
                    } catch (Exception e) {
                        ivProfileImage.setImageResource(PreferenceManager.getprofilepic());
                        e.printStackTrace();
                    }

                    if (userObj.has("wchips")) {
                        long chip = userObj.getLong("wchips");
                        tv_win_chip.setText("" + c.getNumberFormatedValue(chip));

                    } else {
                        tv_win_chip.setText("" + c.getNumberFormatedValue(1000));
                    }


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            frm_winner.setVisibility(View.VISIBLE);

                            Animation rotate = AnimationUtils.loadAnimation(getApplication(), R.anim.scalebounce);
                            rotate.setInterpolator(new LinearInterpolator());
                            findViewById(R.id.iv_winner_ring).startAnimation(rotate);

                            ShowRateUs();
                        }
                    }, 8500);

                } else {

                    if (isAdShow) {
                        isAdShow = false;
                    }

                    if (userObj.getInt("si") == 0) {
                        frm_winner.setVisibility(View.GONE);
                        btnExit.setVisibility(View.VISIBLE);
                        btnContinue.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void ShowRateUs() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (PreferenceManager.GetGetIsRateGiveWith2()) {
                            if (PreferenceManager.getContinuesWin() >= 2) {
                                Intent share = new Intent(RoundWinner.this, ActivityRateUs.class);
                                share.putExtra("isFromWinner", true);
                                startActivity(share);
                                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
                                PreferenceManager.setContinuesWin(0);
                            } else if (PreferenceManager.GetIsDaySeven()) {
                                Intent share = new Intent(RoundWinner.this, ActivityRateUs.class);
                                share.putExtra("isFromWinner", true);
                                startActivity(share);
                                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
                                PreferenceManager.setContinuesWin(0);
                            }
                        }
                    }
                }, 500);
            }
        });
    }

    private void initHandler() {

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if (msg.what == ResponseCodes.FINISH) {
                    finish();
                } else if (msg.what == ResponseCodes.AdsClolse) {
                    StartAnimationOnResult();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
        } catch (IllegalArgumentException I) {
            I.printStackTrace();
        }
        c.payment_Handler = handler;
        c.con = this;
        c.activity = RoundWinner.this;


    }

    private void ShowInterstitialAd() {
        try {
            boolean isAddShowInWinner = false;

            if (PreferenceManager.getisshowAd()) {
                if (PlayScreen2.mInterstitial != null && PlayScreen2.mInterstitial.isLoaded()) {
                    if (!isFinishing()) {
                        PlayScreen2.mInterstitial.show();
                        isAddShowInWinner = true;
                    }
                } else {
                    if (PlayScreen2.mInterstitial != null) {
                        PlayScreen2.requestNewInterstitial();
                    } else {
                        PlayScreen2.showAds();
                    }
                }
            }

            if (!isAddShowInWinner) {
                if (PreferenceManager.getisshowAd()) {
                    if (PlayScreen2.mInterstitial != null) {
                        PlayScreen2.requestNewInterstitial();
                    } else {
                        PlayScreen2.showAds();
                    }
                }
                StartAnimationOnResult();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTimer() {
        // TODO Auto-generated method stub

        timerSec = 14;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, timerSec * 1000);
    }

    @SuppressLint("WrongConstant")
    private void findViewByIds() {


        iv_gin_plus = findViewById(R.id.iv_gin_plus);
        iv_big_gin_plus = findViewById(R.id.iv_big_gin_plus);
        iv_undercut_plus = findViewById(R.id.iv_undercut_plus);

        iv_gin_plus.setVisibility(View.INVISIBLE);
        iv_big_gin_plus.setVisibility(View.INVISIBLE);
        iv_undercut_plus.setVisibility(View.INVISIBLE);

        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) iv_gin_plus.getLayoutParams();
        frm.width = getwidth(239);
        frm.height = getHight(249);

        frm = (FrameLayout.LayoutParams) iv_big_gin_plus.getLayoutParams();
        frm.width = getwidth(440);
        frm.height = getHight(249);

        frm = (FrameLayout.LayoutParams) iv_undercut_plus.getLayoutParams();
        frm.width = getwidth(580);
        frm.height = getHight(250);


        heartBeat = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.heartbeat);

        tvRoundTitle = findViewById(R.id.tvRoundTitle);
        tvRoundTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        tvRoundTitle.setTypeface(typeface, Typeface.BOLD);

        tv_winner_tittle = findViewById(R.id.tv_winner_tittle);
        tv_winner_tittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        tv_winner_tittle.setTypeface(typeface, Typeface.BOLD);
        if (isDraw) {
            tvRoundTitle.setText("DRAW");
        } else {
            tvRoundTitle.setText("Round - " + roundNO);
        }

        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        btnContinue.setTypeface(typeface, Typeface.BOLD);
        btnContinue.setOnClickListener(this);

        btnExit = findViewById(R.id.btnExit);
        btnExit.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        btnExit.setTypeface(typeface, Typeface.BOLD);
        btnExit.setOnClickListener(this);

        tvScoresTitle = findViewById(R.id.tvScoresTitle);
        tvScoresTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        tvScoresTitle.setTypeface(typeface, Typeface.BOLD);

        tvPointsText = findViewById(R.id.tvPointsText);
        tvPointsText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        tvPointsText.setTypeface(typeface, Typeface.BOLD);

        tvGamePoints = findViewById(R.id.tvGamePoints);
        tvGamePoints.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        tvGamePoints.setTypeface(typeface, Typeface.BOLD);
        tvGamePoints.setText("" + roundPoints);

        roundList = findViewById(R.id.roundList);


        nextroundtext = findViewById(R.id.nexrroundtext);
        nextroundtext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        nextroundtext.setTypeface(typeface, Typeface.BOLD);

        tvRoundNumber = findViewById(R.id.tvRoundNumber);
        tvRoundNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvRoundNumber.setTypeface(typeface, Typeface.BOLD);

        tvSecondsText = findViewById(R.id.tvSecondsText);
        tvSecondsText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvSecondsText.setTypeface(typeface, Typeface.BOLD);

        ivClose = findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, android.R.anim.slide_out_right);
            }
        });

        ImageView ivSeperator = findViewById(R.id.ivSeperator);

        if (isWinner) {
            btnContinue.setVisibility(View.GONE);
            btnExit.setVisibility(View.GONE);
            tvRoundTitle.setVisibility(View.INVISIBLE);
            tv_winner_tittle.setVisibility(View.VISIBLE);
            ivSeperator.setVisibility(View.INVISIBLE);
            nextroundtext.setVisibility(View.INVISIBLE);
            tvRoundNumber.setVisibility(View.INVISIBLE);
            tvSecondsText.setVisibility(View.INVISIBLE);

            Logger.print(">>>>>>>> VISIBITY 1111 >>>> Count " + btnContinue.getVisibility());
            Logger.print(">>>>>>>> VISIBITY 1111 >>>> Exit " + btnExit.getVisibility());
//            showTimer();
        } else {
            btnContinue.setVisibility(View.VISIBLE);
            tv_winner_tittle.setVisibility(View.INVISIBLE);

            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.freechipanim);
            btnContinue.startAnimation(anim);


        }
//
        //======= New Winner Screen ============

        NewWinnerScreenId();
    }

    private void NewWinnerScreenId() {

        //======= New Winner Screen ============

        frm_winner = findViewById(R.id.frm_winner);
        frm_winner.setVisibility(View.GONE);

        tv_win_chip = findViewById(R.id.tv_win_chip);
        tv_win_chip.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(32));
        tv_win_chip.setTypeface(c.typeface, Typeface.BOLD);


        //========== User ProfileScreen =====
        ivProfileImage = findViewById(R.id.ivProfileImage);


        //======== Button Exit And Countuie =====

        btn_win_exit = findViewById(R.id.btn_win_exit);
        btn_win_continue = findViewById(R.id.btn_win_continue);

        btn_win_exit.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(32));
        btn_win_exit.setTypeface(c.typeface, Typeface.BOLD);

        btn_win_continue.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(32));
        btn_win_continue.setTypeface(c.typeface, Typeface.BOLD);

        btn_win_exit.setOnClickListener(this);
        btn_win_continue.setOnClickListener(this);


        NewWinnerScreenLayout();

    }

    private void NewWinnerScreenLayout() {

        FrameLayout.LayoutParams frm;
        LinearLayout.LayoutParams ln;
        int w, h;

        // ======= Blue Bg ======
        w = getwidth(758);
        h = getHight(678);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_win_bg).getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.bottomMargin = getHight(20);

        //======== Profile Frame ======

        frm = (FrameLayout.LayoutParams) findViewById(R.id.frm_win_profile).getLayoutParams();
        frm.bottomMargin = getHight(105);

        //======= Profile Pic =======

        w = getwidth(200);
        h = getHight(200);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivProfileImage).getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = getHight(15);

        w = getwidth(285);
        h = getHight(285);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_winner_ring).getLayoutParams();
        frm.width = w;
        frm.height = h;

        w = getwidth(285);
        h = getHight(316);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_winner_crown).getLayoutParams();
        frm.width = w;
        frm.height = h;

        w = getwidth(366);
        h = getHight(128);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_winner_tag).getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.bottomMargin = getHight(-15);

        w = getwidth(234);
        h = getHight(75);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.lnr_win_chip).getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = getHight(147);

        w = getwidth(41);
        h = getHight(41);
        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_coin).getLayoutParams();
        ln.width = w;
        ln.height = h;
        ln.leftMargin = getwidth(20);


        ln = (LinearLayout.LayoutParams) findViewById(R.id.tv_win_chip).getLayoutParams();
        ln.leftMargin = getwidth(20);


        //======== Button Exit And Countuie =====
        frm = (FrameLayout.LayoutParams) findViewById(R.id.lnr_button).getLayoutParams();
        frm.topMargin = getHight(287);

        w = getwidth(198);
        h = getHight(77);
        ln = (LinearLayout.LayoutParams) findViewById(R.id.btn_win_exit).getLayoutParams();
        ln.width = w;
        ln.height = h;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.btn_win_continue).getLayoutParams();
        ln.width = w;
        ln.height = h;
        ln.leftMargin = getwidth(30);


    }

    private void setScreen() {
        LinearLayout.LayoutParams lin;
        FrameLayout.LayoutParams frm;

//        frm = (FrameLayout.LayoutParams) findViewById(R.id.frmMainLayout).getLayoutParams();
//        frm.width = getwidth(1280);
//        frm.height = getHight(720);

        int w, h;
       /* w = getwidth(240);
        h = w * 60 / 240;*/
        frm = (FrameLayout.LayoutParams) findViewById(R.id.llPointsTextContainer).getLayoutParams();
       /* frm.width = w;
        frm.height = h;*/
        frm.topMargin = getHight(10);
        frm.leftMargin = getwidth(30);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivSeperatorWinPoints).getLayoutParams();
        lin.width = getwidth(380);
        lin.leftMargin = getwidth(-20);


        w = getwidth(40);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivTrophy).getLayoutParams();
        lin.width = lin.height = w;
        lin.rightMargin = getwidth(10);

        lin = (LinearLayout.LayoutParams) tvRoundTitle.getLayoutParams();
        lin.topMargin = getHight(10);

        frm = (FrameLayout.LayoutParams) tv_winner_tittle.getLayoutParams();
        frm.topMargin = getHight(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.llRoundTimerText).getLayoutParams();
        frm.height = getHight(60);

        frm = (FrameLayout.LayoutParams) ivClose.getLayoutParams();
        frm.width = getwidth(60);
        frm.height = getwidth(60);
        frm.rightMargin = getwidth(10);
        frm.topMargin = getHight(10);

        frm = (FrameLayout.LayoutParams) btnContinue.getLayoutParams();
        frm.width = getwidth(160);
        frm.height = getwidth(70);
        frm.bottomMargin = getHight(10);
        frm.rightMargin = getwidth(30);

        frm = (FrameLayout.LayoutParams) btnExit.getLayoutParams();
        frm.width = getwidth(160);
        frm.height = getwidth(70);
        frm.bottomMargin = getHight(10);
        frm.rightMargin = getwidth(10 + 160 + 30);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvRoundNumber).getLayoutParams();
        lin.width = getwidth(40);
        lin.height = getwidth(40);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.llTitleScoreContainer).getLayoutParams();
        frm.rightMargin = getwidth(-20);
        frm.topMargin = getHight(10);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivLineScore).getLayoutParams();
        lin.width = getwidth(160);

        //======= New Winner Screen ============

    }

    public int getwidth(int val) {
        return (Screen_Width * val) / 1280;
    }

    public int getHight(int val) {
        return (Screen_Hieght * val) / 720;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (CDTimer != null) {
            CDTimer.cancel();
        }
    }

    @Override
    public void onClick(View v) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }

//        hidehandler.post(hideList);
        mLastClickTime = SystemClock.elapsedRealtime();

        if (v == btnContinue) {
            // error.setText("");
            Music_Manager.buttonclick();
            if (isWinner) {
                if (PlayScreen2.handler != null) {
                    Message message = new Message();
                    message.what = ResponseCodes.Start_RoundTimer_In_Playscreen;
                    message.obj = "5";
                    PlayScreen2.handler.sendMessage(message);
                }
            } else {
                if (PlayScreen2.handler != null) {
                    Message message = new Message();
                    message.what = ResponseCodes.Start_New_Round;
                    message.arg1 = time;
                    PlayScreen2.handler.sendMessage(message);

                }
            }

            if (CDTimer != null) {
                CDTimer.cancel();
                CDTimer = null;
            }
            finish();
            overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
        } else if (v == btnExit) {
            Music_Manager.buttonclick();
            try {
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
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v == btn_win_continue) {
            Music_Manager.buttonclick();

            if (PlayScreen2.handler != null) {
                Message message = new Message();
                message.what = ResponseCodes.Start_RoundTimer_In_Playscreen;
                message.obj = "5";
                PlayScreen2.handler.sendMessage(message);
            }
            finish();
            overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
        } else if (v == btn_win_exit) {
            Music_Manager.buttonclick();
            try {
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
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public boolean GameisOn() {
        // TODO Auto-generated method stub

        ActivityManager activityManager = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager
                .getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;

        if (services.get(0).topActivity.getPackageName().toString()
                .equalsIgnoreCase(this.getPackageName().toString())) {
            isActivityFound = true;
        }

        return isActivityFound;
    }

    public int getDeadWood(Item_Card card) {
        int value = 0;
        if (card.getCardValue() >= 10) {
            value = 10;
        } else {
            value = card.getCardValue();
        }
        return value;
    }

    public String getCardString(Item_Card object) {
        // TODO Auto-generated method stub

        String card = "";
        if (object.getCardColor() == c.Falli) {
            card = "f";
        } else if (object.getCardColor() == c.Chirkut) {
            card = "c";
        } else if (object.getCardColor() == c.Laal) {
            card = "l";
        } else if (object.getCardColor() == c.Kaali) {
            card = "k";
        }

        card = card + "-" + (object.getCardValue());
        return card;

    }

    public Item_Card getItemCard(String string) {
        // TODO Auto-generated method stub

        Item_Card ic = new Item_Card();
        try {
            String vals[] = new String[2];
            vals = string.split("-");
            int CardColor = c.getCardSuit(vals[0]);
            ic.setCardColor(CardColor);
            int CardValue = Integer.parseInt(vals[1]);
            ic.setCardValue(CardValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return ic;

    }

    public int CalculateTotalCount(JSONArray card) throws JSONException {
        int TotalCount = 0;
        for (int i = 0; i < card.length(); i++) {
            int value = 0;
            if (getItemCard(card.getString(i)).getCardValue() >= 10) {
                value = 10;
            } else {
                value = getItemCard(card.getString(i)).getCardValue();
            }

            TotalCount += value;
        }

        return TotalCount;
    }

    public ArrayList<Item_Card> Sort(ArrayList<Item_Card> mCardsArray,
                                     int seatIndex) {
        ArrayList<Item_Card> FinalSortedArray = new ArrayList<Item_Card>();
        Hand test = new Hand();
        for (int i = 0; i < mCardsArray.size(); i++) {
            try {
                test.add(test.getCard(mCardsArray.get(i)));
            } catch (HandException e) {

            }
        }

        test.autoMatch();

        // JSONArray array = new JSONArray();
        for (ICardSet set : test.getMatchedSets()) {
            if (set.isGroup()) {
                JSONArray jArray = new JSONArray();
                for (Card c : set) {
                    String card = test.getCardColor(c) + "-"
                            + (c.getRank().ordinal() + 1);
                    jArray.put(card);
                }
                test.MatchedCardsArray.put(jArray);
            }
        }

        JSONArray temp = new JSONArray();

        try {
            for (int i = 0; i < test.MatchedCardsArray.length(); i++) {
                for (int j = 0; j < test.MatchedCardsArray.length() - 1; j++) {
                    if (CalculateTotalCount(test.MatchedCardsArray
                            .getJSONArray(j)) > CalculateTotalCount(test.MatchedCardsArray
                            .getJSONArray(j + 1))) /*
                                                     * For descending order use
													 * <
													 */ {
                        temp = test.MatchedCardsArray.getJSONArray(j);
                        test.MatchedCardsArray.put(j,
                                test.MatchedCardsArray.get(j + 1));
                        test.MatchedCardsArray.put(j + 1, temp);// array[d+1] =
                        // swap;
                    } else if (CalculateTotalCount(test.MatchedCardsArray
                            .getJSONArray(j)) == CalculateTotalCount(test.MatchedCardsArray
                            .getJSONArray(j + 1))) {
                        if (test.MatchedCardsArray.getJSONArray(j).length() < test.MatchedCardsArray
                                .getJSONArray(j + 1).length()) {
                            temp = test.MatchedCardsArray.getJSONArray(j);
                            test.MatchedCardsArray.put(j,
                                    test.MatchedCardsArray.get(j + 1));
                            test.MatchedCardsArray.put(j + 1, temp);// array[d+1]
                            // = swap;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        for (ICardSet set : test.getMatchedSets()) {
            if (set.isRun()) {
                JSONArray jArray = new JSONArray();
                for (Card c : set) {
                    String card = test.getCardColor(c) + "-"
                            + (c.getRank().ordinal() + 1);
                    jArray.put(card);
                }
                test.MatchedCardsArray1.put(jArray);
            }
        }

        try {
            for (int i = 0; i < test.MatchedCardsArray1.length(); i++) {
                for (int j = 0; j < test.MatchedCardsArray1.length() - 1; j++) {

                    if (getItemCard(
                            test.MatchedCardsArray1.getJSONArray(j)
                                    .getString(0)).getCardColor() > getItemCard(
                            test.MatchedCardsArray1.getJSONArray(j + 1)
                                    .getString(0)).getCardColor()) {
                        temp = test.MatchedCardsArray1.getJSONArray(j);
                        test.MatchedCardsArray1.put(j,
                                test.MatchedCardsArray1.get(j + 1));
                        test.MatchedCardsArray1.put(j + 1, temp);// array[d+1] =
                        // swap;
                    } else if (getItemCard(
                            test.MatchedCardsArray1.getJSONArray(j)
                                    .getString(0)).getCardColor() == getItemCard(
                            test.MatchedCardsArray1.getJSONArray(j + 1)
                                    .getString(0)).getCardColor()) {
                        if (CalculateTotalCount(test.MatchedCardsArray1
                                .getJSONArray(j)) > CalculateTotalCount(test.MatchedCardsArray1
                                .getJSONArray(j + 1))) /*
                                                         * For descending order
														 * use <
														 */ {
                            temp = test.MatchedCardsArray1.getJSONArray(j);
                            test.MatchedCardsArray1.put(j,
                                    test.MatchedCardsArray1.get(j + 1));
                            test.MatchedCardsArray1.put(j + 1, temp);// array[d+1]
                            // =
                            // swap;
                        } else if (CalculateTotalCount(test.MatchedCardsArray1
                                .getJSONArray(j)) == CalculateTotalCount(test.MatchedCardsArray1
                                .getJSONArray(j + 1))) {
                            if (test.MatchedCardsArray1.getJSONArray(j)
                                    .length() < test.MatchedCardsArray1
                                    .getJSONArray(j + 1).length()) {
                                temp = test.MatchedCardsArray1.getJSONArray(j);
                                test.MatchedCardsArray1.put(j,
                                        test.MatchedCardsArray1.get(j + 1));
                                test.MatchedCardsArray1.put(j + 1, temp);// array[d+1]
                                // =
                                // swap;
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < test.MatchedCardsArray1.length(); i++) {
                test.MatchedCardsArray.put(test.MatchedCardsArray1.get(i));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        for (Card set : test.getUnmatchedCards()) {
            String card = test.getCardColor(set) + "-"
                    + (set.getRank().ordinal() + 1);
            test.UnMatchedCardsArray.put(card);
        }

        // Logger.print("Sorting 5 => " + test.UnMatchedCardsArray.toString());

        try {

            for (int i = 0; i < test.MatchedCardsArray.length(); i++) {
                ArrayList<Item_Card> arr = new ArrayList<Item_Card>();
                for (int j = 0; j < test.MatchedCardsArray.getJSONArray(i)
                        .length(); j++) {
                    Item_Card obj = getItemCard(test.MatchedCardsArray
                            .getJSONArray(i).getString(j));
                    obj.setPairNumber(i + 1);
                    obj.setCardInPair(true);
                    obj.setIsValidGroup(true);

                    /*for (int j2 = 0; j2 < mCardsArray.size(); j2++) {
                        if (obj.getCardColor() == mCardsArray.get(j2)
                                .getCardColor()
                                && obj.getCardValue() == mCardsArray.get(j2)
                                .getCardValue()) {

                        }
                    }*/
                    arr.add(obj);
                }
                try {
                    Collections.sort(arr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FinalSortedArray.addAll(arr);
            }

            // Logger.print("Sorting 6 =>  " + FinalSortedArray.toString());

            ArrayList<Item_Card> arr2 = new ArrayList<Item_Card>();
            for (int i = 0; i < test.UnMatchedCardsArray.length(); i++) {
                Item_Card obj = getItemCard(test.UnMatchedCardsArray
                        .getString(i));
                obj.setPairNumber(0);
                obj.setCardInPair(false);
                obj.setIsValidGroup(false);

                /*for (int j2 = 0; j2 < mCardsArray.size(); j2++) {
                    if (obj.getCardColor() == mCardsArray.get(j2)
                            .getCardColor()
                            && obj.getCardValue() == mCardsArray.get(j2)
                            .getCardValue()) {

                    }
                }*/

                arr2.add(obj);
            }

            Collections.sort(arr2);
            FinalSortedArray.addAll(arr2);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return FinalSortedArray;
    }

    private int getRelativeLeft(View myView) {
        int view = 0;
        try {
            if (myView.getParent() == myView.getRootView())
                view = myView.getLeft();
            else
                view = myView.getLeft() + getRelativeLeft((View) myView.getParent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private int getRelativeTop(View myView) {
        int view = 0;
        try {
            if (myView.getParent() == myView.getRootView())
                view = myView.getTop();
            else
                view = myView.getTop() + getRelativeTop((View) myView.getParent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private class Adapter_RoundWinner extends BaseAdapter {

        JSONArray winnerData;
        int w, h;
        boolean[] posArray = new boolean[3];

        public Adapter_RoundWinner(JSONArray winnerData) {
            this.winnerData = winnerData;
        }

        @Override
        public int getCount() {
            return winnerData.length();
        }

        @Override
        public JSONObject getItem(int position) {
            try {
                return winnerData.getJSONObject(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Typeface typeface = Typeface.createFromAsset(getAssets(), "font/PoetsenOne-Regular.ttf");
            LinearLayout.LayoutParams lin;
            FrameLayout.LayoutParams frm;
            RelativeLayout.LayoutParams rel;
            final RecordHolder holder;
            int fontSize;

            View mView = convertView;

            if (mView == null) {


                Logger.print("called............. " + position);
                Logger.print("MMMMMMMMMMMMMMMMMMM:::::::::::getItem(position)............. " + getItem(position));
                mView = getLayoutInflater().inflate(R.layout.adapter_roundwinnerlist, parent, false);
                holder = new RecordHolder();

                if (!posArray[position]) {

                    Logger.print("called.............222 " + position + " " + posArray[position]);
                    posArray[position] = true;

                    holder.tvUserName = mView.findViewById(R.id.tvUserName);
                    holder.tvUserName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
                    holder.tvUserName.setTypeface(typeface);

                    holder.tvPoints = mView.findViewById(R.id.tvPoints);
                    holder.tvPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
                    holder.tvPoints.setTypeface(typeface);
                    w = getwidth(80);
                    LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) holder.tvPoints.getLayoutParams();
                    ll.width = ll.height = w;

                    holder.tvTotalPoints = mView.findViewById(R.id.tvTotalPoints);
                    holder.tvTotalPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));
                    holder.tvTotalPoints.setTypeface(typeface);
                    holder.tvTotalPoints.setText("/" + roundPoints);

                    holder.ivUserProfile = mView.findViewById(R.id.ivUserProfile);
                    holder.iv_win_crown = mView.findViewById(R.id.iv_win_crown);
                    holder.iv_win_tag = mView.findViewById(R.id.iv_win_tag);
                    w = getwidth(120);
                    frm = (FrameLayout.LayoutParams) holder.ivUserProfile.getLayoutParams();
                    frm.width = frm.height = w;
                    w = getwidth(150);
                    frm = (FrameLayout.LayoutParams) holder.iv_win_crown.getLayoutParams();
                    frm.width = frm.height = w;
                    frm.topMargin = getHight(-10);

                    frm = (FrameLayout.LayoutParams) holder.iv_win_tag.getLayoutParams();
                    frm.width = frm.height = w;

                    holder.ivCards[0] = mView.findViewById(R.id.ivCard1);
                    holder.ivCards[1] = mView.findViewById(R.id.ivCard2);
                    holder.ivCards[2] = mView.findViewById(R.id.ivCard3);
                    holder.ivCards[3] = mView.findViewById(R.id.ivCard4);
                    holder.ivCards[4] = mView.findViewById(R.id.ivCard5);
                    holder.ivCards[5] = mView.findViewById(R.id.ivCard6);
                    holder.ivCards[6] = mView.findViewById(R.id.ivCard7);
                    holder.ivCards[7] = mView.findViewById(R.id.ivCard8);
                    holder.ivCards[8] = mView.findViewById(R.id.ivCard9);
                    holder.ivCards[9] = mView.findViewById(R.id.ivCard10);
                    holder.ivCards[10] = mView.findViewById(R.id.ivCard11);

                    holder.ivpoints[0] = mView.findViewById(R.id.ivPoint1);
                    holder.ivpoints[1] = mView.findViewById(R.id.ivPoint2);
                    holder.ivpoints[2] = mView.findViewById(R.id.ivPoint3);
                    holder.ivpoints[3] = mView.findViewById(R.id.ivPoint4);
                    holder.ivpoints[4] = mView.findViewById(R.id.ivPoint5);
                    holder.ivpoints[5] = mView.findViewById(R.id.ivPoint6);
                    holder.ivpoints[6] = mView.findViewById(R.id.ivPoint7);
                    holder.ivpoints[7] = mView.findViewById(R.id.ivPoint8);
                    holder.ivpoints[8] = mView.findViewById(R.id.ivPoint9);
                    holder.ivpoints[9] = mView.findViewById(R.id.ivPoint10);
                    holder.ivpoints[10] = mView.findViewById(R.id.ivPoint11);

                    holder.ivKnock = mView.findViewById(R.id.ivKnock);
                    w = getwidth(140);
                    h = w * 112 / 140;
                    frm = (FrameLayout.LayoutParams) holder.ivKnock.getLayoutParams();
                    frm.width = w;
                    frm.height = h;
                    frm.bottomMargin = h * -40 / 112;
                    //frm.leftMargin = w * 50 / 140;
                    //frm.topMargin = h * 90 / 112;

                    frm = (FrameLayout.LayoutParams) mView.findViewById(R.id.llMainLayout).getLayoutParams();
                    frm.leftMargin = frm.rightMargin = frm.topMargin = frm.bottomMargin = getwidth(20);


                    w = getwidth(100);
                    h = w * 136 / 100;
                    int w1 = getwidth(60);
                    fontSize = getwidth(36);
                    for (int i = 0; i < holder.ivCards.length; i++) {
                        frm = (FrameLayout.LayoutParams) holder.ivCards[i].getLayoutParams();
                        frm.width = w;
                        frm.height = h;

                        frm = (FrameLayout.LayoutParams) holder.ivpoints[i].getLayoutParams();
                        frm.width = frm.height = w1;
                        // frm.topMargin = w1;

                        holder.ivpoints[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                        holder.ivpoints[i].setTypeface(typeface);
                    }

                    holder.ivPointAnim = mView.findViewById(R.id.tvPointsAnim);
                    holder.ivPointAnim.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                    holder.ivPointAnim.setTypeface(typeface);

                    frm = (FrameLayout.LayoutParams) holder.ivPointAnim.getLayoutParams();
                    frm.width = frm.height = w1;
                    frm.rightMargin = w1 * 170 / 60;
                    frm.bottomMargin = w1 * 10 / 60;

                    holder.tvPointsAnimLast = mView.findViewById(R.id.tvPointsAnimLast);
                    holder.tvPointsAnimLast.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                    holder.tvPointsAnimLast.setTypeface(typeface);

                    frm = (FrameLayout.LayoutParams) holder.tvPointsAnimLast.getLayoutParams();
                    frm.width = frm.height = w1;
                    frm.rightMargin = w1 * 170 / 60;
                    frm.bottomMargin = w1 * -30 / 60;

                    holder.tvPointsAnimLast2 = mView.findViewById(R.id.tvPointsAnimLast2);
                    holder.tvPointsAnimLast2.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                    holder.tvPointsAnimLast2.setTypeface(typeface);

                    frm = (FrameLayout.LayoutParams) holder.tvPointsAnimLast2.getLayoutParams();
                    frm.width = frm.height = w1;
                    frm.rightMargin = w1 * 170 / 60;
                    frm.bottomMargin = w1 * -30 / 60;

                    holder.tvGinPointText = mView.findViewById(R.id.tvGinPointText);
                    holder.tvGinPointText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
                    holder.tvGinPointText.setTypeface(typeface);


                    holder.tvGinPoints = mView.findViewById(R.id.tvGinPoints);
                    holder.tvGinPoints_iv = mView.findViewById(R.id.tvGinPoints_iv);
                    holder.tvGinPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
                    holder.tvGinPoints.setTypeface(typeface);

                    holder.ivGin = mView.findViewById(R.id.ivGin);

                    w = getwidth(60);
                    h = w * 40 / 60;
                    rel = (RelativeLayout.LayoutParams) holder.ivGin.getLayoutParams();
                    rel.width = w;
                    rel.height = h;

                    rel = (RelativeLayout.LayoutParams) holder.tvGinPointText.getLayoutParams();
                    rel.leftMargin = getwidth(10);

                    rel = (RelativeLayout.LayoutParams) holder.tvGinPoints.getLayoutParams();
                    rel.width = rel.height = getwidth(40);
                    rel.leftMargin = getwidth(10);

                    rel = (RelativeLayout.LayoutParams) holder.tvGinPoints_iv.getLayoutParams();
                    rel.width = rel.height = getwidth(40);
                    rel.leftMargin = getwidth(10);

                    holder.ivUndercut = mView.findViewById(R.id.ivUndercut);

                    w = getwidth(40);
                    h = w * 40 / 40;
                    rel = (RelativeLayout.LayoutParams) holder.ivUndercut.getLayoutParams();
                    rel.width = w;
                    rel.height = h;
                    rel.leftMargin = w * 20 / 40;

                    holder.tvKnockPenaltyText = mView.findViewById(R.id.tvKnockPenaltyText);
                    holder.tvKnockPenaltyText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
                    holder.tvKnockPenaltyText.setTypeface(typeface);

                    rel = (RelativeLayout.LayoutParams) holder.tvKnockPenaltyText.getLayoutParams();
                    rel.leftMargin = getwidth(10);

                    holder.tvKnockPenalty = mView.findViewById(R.id.tvKnockPenalty);
                    holder.tvKnockPenalty.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
                    holder.tvKnockPenalty.setTypeface(typeface);

                    rel = (RelativeLayout.LayoutParams) holder.tvKnockPenalty.getLayoutParams();
                    rel.width = rel.height = getwidth(40);
                    rel.leftMargin = getwidth(10);

                    holder.tvWinPointsText = mView.findViewById(R.id.tvWinPointsText);
                    holder.tvWinPointsText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
                    holder.tvWinPointsText.setTypeface(typeface);

                    rel = (RelativeLayout.LayoutParams) holder.tvWinPointsText.getLayoutParams();
                    rel.leftMargin = getwidth(20);

                    holder.tvWinPoints = mView.findViewById(R.id.tvWinPoints);
                    holder.tvWinPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
                    holder.tvWinPoints.setTypeface(typeface);

                    rel = (RelativeLayout.LayoutParams) holder.tvWinPoints.getLayoutParams();
                    rel.width = rel.height = getwidth(40);
                    rel.leftMargin = getwidth(10);

                    lin = (LinearLayout.LayoutParams) mView.findViewById(R.id.llLayOutPoints).getLayoutParams();
                    lin.leftMargin = getwidth(80);

                    holder.pos = position;
                    //ANIMATIONNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN


                    //////////////////////////////////////////////////////////////////////////////////S
                    mView.setTag(holder);

                    holder.ivYouTag = mView.findViewById(R.id.ivYouTag);

                    w = getwidth(70);
                    h = w * 60 / 70;
                    frm = (FrameLayout.LayoutParams) holder.ivYouTag.getLayoutParams();
                    frm.width = w;
                    frm.height = h;
                    frm.leftMargin = w * 12 / 70;
//                    frm.topMargin = h * 5 / 60;
                    try {
                        if (getItem(position).has("ui")) {
                            try {
                                Logger.print("_UIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII 1 => " + position + " " + getItem(position).getJSONObject("ui").toString() + " " + PreferenceManager.get_id());
                                if (getItem(position).getJSONObject("ui").getString(Parameters._id).contentEquals(PreferenceManager.get_id())) {
                                    holder.ivYouTag.setVisibility(View.VISIBLE);
                                    try {
                                        if (PreferenceManager.get_picPath().length() > 0) {
                                            Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                            holder.ivUserProfile.setImageDrawable(d);
                                        } else {
                                            holder.ivUserProfile.setImageResource(PreferenceManager.getprofilepic());
                                        }
                                    } catch (Exception e) {
                                        holder.ivUserProfile.setImageResource(PreferenceManager.getprofilepic());
                                    }
                                } else {
                                    holder.ivYouTag.setVisibility(View.INVISIBLE);
                                    holder.ivUserProfile.setImageResource(getItem(position).getJSONObject("ui").getInt(Parameters.ProfilePicture));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                Logger.print("_UIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII 2 => " + position + " " + getItem(position).getString(Parameters.User_Id) + " " + PreferenceManager.get_id());
                                if (getItem(position).getString(Parameters.User_Id).contentEquals(PreferenceManager.get_id())) {
                                    holder.ivYouTag.setVisibility(View.VISIBLE);
                                    try {
                                        if (PreferenceManager.get_picPath().length() > 0) {
                                            Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                            holder.ivUserProfile.setImageDrawable(d);
                                        } else {
                                            holder.ivUserProfile.setImageResource(PreferenceManager.getprofilepic());
                                        }
                                    } catch (Exception e) {
                                        holder.ivUserProfile.setImageResource(PreferenceManager.getprofilepic());
                                    }
                                } else {
                                    holder.ivYouTag.setVisibility(View.INVISIBLE);
                                    holder.ivUserProfile.setImageResource(getItem(position).getInt(Parameters.ProfilePicture));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                holder.ivUserProfile.setImageResource(R.drawable.me);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                   /* String url = null;
                    try {
                        if (getItem(position).has("ui")) {
                            url = getItem(position).getJSONObject("ui").getString(Parameters.ProfilePicture);
                        } else {
                            url = getItem(position).getString(Parameters.ProfilePicture);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!url.contains("http")) {
                        url = c.REMOTE_ASSET_BASE_URL + url;
                    }
                    Logger.print("URL Bottom => " + url);
                    ImageLoader.getInstance().displayImage(url, holder.ivUserProfile, imageOptions);*/
                    try {
                        String username;
                        if (getItem(position).has("ui")) {
                            username = getItem(position).getJSONObject("ui").getString(Parameters.User_Name);
                        } else {
                            username = getItem(position).getString(Parameters.User_Name);
                        }
                        holder.tvUserName.setText(username);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int points = 0;
                    try {
                        JSONArray jPoints = getItem(position).getJSONArray(Parameters.points);
                        points = jPoints.getInt(jPoints.length() - 1);
                        //holder.tvPoints.setText(points + "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (points > 0) {
                        try {
                            holder.prevScore = getItem(position).getInt("ps") - points;
                            if (holder.prevScore > 0) {
                                holder.tvPoints.setText("" + holder.prevScore);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        holder.winnerSeat = position;
                    } else {
                        try {

                            holder.prevScore = getItem(position).getInt("ps") - points;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (holder.prevScore > 0) {
                            holder.tvPoints.setText("" + holder.prevScore);
                        }
                        holder.winnerSeat = -1;
                    }

                    try {
                        Logger.print("Previous score =>>>>>>> " + holder.prevScore + " " + getItem(position).getInt("ps") + " " + points + " " + position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        if (c.isKnocked && getItem(position).getInt(Parameters.SeatIndex) == c.SeatIndexOfUser) {
                            holder.ivKnock.setVisibility(View.VISIBLE);
                        } else {
                            holder.ivKnock.setVisibility(View.INVISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        if (getItem(position).has("w") && getItem(position).getBoolean("w")) {
                            Animation rotate = AnimationUtils.loadAnimation(getApplication(), R.anim.scalebounce);
                            rotate.setInterpolator(new LinearInterpolator());
                            holder.iv_win_tag.setVisibility(View.VISIBLE);
                            holder.iv_win_crown.setVisibility(View.VISIBLE);
                            holder.iv_win_tag.startAnimation(rotate);

                        } else {
                            holder.iv_win_tag.clearAnimation();
                            holder.iv_win_tag.setVisibility(View.GONE);
                            holder.iv_win_crown.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                /*if (getItem(position).has("st")) {

                    try {
                        JSONObject jObjScore = getItem(position).getJSONArray("st").getJSONObject(getItem(position).getJSONArray("st").length() - 1);
                        if (points > 0) {
                            if (jObjScore.has("GP") && jObjScore.getInt("GP") == 0) {
                                holder.ivKnock.setVisibility(View.VISIBLE);
                            } else {
                                holder.ivKnock.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            holder.ivKnock.setVisibility(View.INVISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    holder.ivKnock.setVisibility(View.INVISIBLE);
                }*/


                    ArrayList<Item_Card> userCards = null;
                    holder.deadwood = 0;
                    if (getItem(position).has(Parameters.NewCards)) {
                        try {
                            JSONObject newCardsObj = getItem(position).getJSONObject(Parameters.NewCards);
                            JSONArray valid = newCardsObj.getJSONArray("valid");
                            JSONArray invalid = newCardsObj.getJSONArray("invalid");
                            userCards = new ArrayList<>();

                            for (int i = 0; i < valid.length(); i++) {
                                JSONArray validSeq = valid.getJSONArray(i);
                                for (int j = 0; j < validSeq.length(); j++) {
                                    Item_Card iCard = getItemCard(validSeq.getString(j));
                                    iCard.setCardInPair(true);
                                    iCard.setIsValidGroup(true);
                                    iCard.setPairNumber(i + 1);
                                    iCard.setGroupNumber(i + 1);
                                    userCards.add(iCard);
                                }
                            }

                            for (int i = 0; i < invalid.length(); i++) {

                                Item_Card iCard = getItemCard(invalid.getString(i));
                                iCard.setCardInPair(false);
                                iCard.setIsValidGroup(false);
                                iCard.setPairNumber(0);
                                iCard.setGroupNumber(0);
                                userCards.add(iCard);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        userCards = new ArrayList<>();
                        try {
                            JSONArray cards = getItem(position).getJSONArray(Parameters.Cards);
                            for (int i = 0; i < cards.length(); i++) {
                                userCards.add(getItemCard(cards.getString(i)));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        userCards = new ArrayList<>(Sort(userCards, 0));
                    }

                    int deadcard = 0;
                    int deadcard2 = 1;

                    for (int i = 0; i < userCards.size() && i < 11; i++) {
                        final Item_Card card = userCards.get(i);
                        if (card.getPairNumber() == 0) {

                            deadcard2++;
                        }

                    }

//                    final int[] dw = new int[deadcard2];
//                    final int duration = 3200 / deadcard2;

                    w = getwidth(110);
                    int leftMargin = 0;
                    boolean firstTime = true;
                    int DW = 0;
                    try {
                        for (int i = 0; i < userCards.size() && i < 11; i++) {
                            final Item_Card card = userCards.get(i);
                            int index = Arrays.asList(cardString).indexOf(getCardString(card));
                            int cardvalue = card.getCardValue();
                            String cardcolor = card.getCardColorString();
                            if (card.getPairNumber() != 0) {
                                frm = (FrameLayout.LayoutParams) holder.ivCards[i].getLayoutParams();
                                frm.leftMargin = w * leftMargin / 110;

                                frm = (FrameLayout.LayoutParams) holder.ivpoints[i].getLayoutParams();
                                frm.leftMargin = w * leftMargin / 110;

                                leftMargin += 60;
                                if (card.getPairNumber() == 1) {

                                    int id = getResources().getIdentifier(cardcolor + cardvalue + "_1", "drawable", getPackageName());
                                    holder.ivCards[i].setBackgroundResource(id);
                                } else if (card.getPairNumber() == 2) {
                                    int id = getResources().getIdentifier(cardcolor + "_g_" + cardvalue, "drawable", getPackageName());
                                    holder.ivCards[i].setBackgroundResource(id);
                                } else if (card.getPairNumber() == 3) {
                                    int id = getResources().getIdentifier(cardcolor + "_b_" + cardvalue, "drawable", getPackageName());
                                    holder.ivCards[i].setBackgroundResource(id);
                                } else if (card.getPairNumber() == 4) {
                                    int id = getResources().getIdentifier(cardcolor + cardvalue + "_1", "drawable", getPackageName());
                                    holder.ivCards[i].setBackgroundResource(id);
                                }
                                holder.ivpoints[i].setVisibility(View.INVISIBLE);
                            } else {
                                if (firstTime) {
                                    firstTime = false;
                                    leftMargin += 90;
                                }
                                holder.ivpoints[i].setVisibility(View.VISIBLE);

                                frm = (FrameLayout.LayoutParams) holder.ivCards[i].getLayoutParams();
                                frm.leftMargin = w * leftMargin / 110;

                                frm = (FrameLayout.LayoutParams) holder.ivpoints[i].getLayoutParams();
                                frm.leftMargin = w * leftMargin / 110;

                                leftMargin += 60;
                                DW = getDeadWood(card);
                                int id = getResources().getIdentifier(cardcolor + cardvalue + "", "drawable", getPackageName());
                                holder.ivCards[i].setBackgroundResource(id);
                                holder.ivpoints[i].setText(DW + "");
                                deadcard++;
                                holder.deadwood += DW;
//                                dw[deadcard] = DW;


//                                final int m = i;
//                                final int coords[] = new int[2];
//                                final int[] xC = new int[1];
//                                final int[] yC = new int[1];
//                                final int finalDeadcard = deadcard;
//                                final int offset = deadcard2--;
//                                final int finalI = i;
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {

//                                        holder.ivPointAnim.getLocationOnScreen(coords);
//                                        xC[0] = coords[0];
//                                        yC[0] = coords[1];
//                                        int t = (10 - m) * 60 + 150;
//
//                                        int[] cards = new int[2];
//                                        holder.ivCards[m].getLocationOnScreen(cards);
//                                        int xx = cards[0];
//                                        int yy = cards[1];
//                                        holder.ivpoints[m].setVisibility(View.INVISIBLE);
//                                        TranslateAnimation tAnim = new TranslateAnimation(0, xC[0] - xx, 0, yC[0] - yy - getHight(40));
//                                        tAnim.setInterpolator(new AccelerateInterpolator());
//                                        tAnim.setFillAfter(false);
//                                        if (duration > 1000) {
//                                            tAnim.setDuration(700);
//                                        } else {
//                                            tAnim.setDuration(duration);
//                                        }
//                                        tAnim.setStartOffset(duration * offset);
//
//                                        tAnim.setAnimationListener(new Animation.AnimationListener() {
//                                            @Override
//                                            public void onAnimationStart(Animation animation) {
//
//                                            }
//
//                                            @Override
//                                            public void onAnimationEnd(Animation animation) {
//                                                holder.deadwood += dw[finalDeadcard];
//                                                holder.ivPointAnim.setVisibility(View.VISIBLE);
//                                                holder.ivPointAnim.setText(holder.deadwood + "");
//                                                Music_Manager.throwcard();
//
//                                            }
//
//                                            @Override
//                                            public void onAnimationRepeat(Animation animation) {
//
//                                            }
//                                        });
//                                       // holder.ivpoints[m].startAnimation(tAnim);

//                                    }
//                                }, 800);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.print("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                    }

                    holder.ivPointAnim.setVisibility(View.VISIBLE);

                    holder.ivPointAnim.setText(holder.deadwood + "");

                    if (!isDraw) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                        /*for (int k = 0; k < holder.ivpoints.length; k++) {
                            if (holder.ivpoints[k].getAnimation() != null) {
                                holder.ivpoints[k].clearAnimation();
                            }
                            holder.ivpoints[k].setVisibility(View.INVISIBLE);
                        }*/

                                if (getCount() == 2) {
                                    if (position == 0) {

                                /*holder.ivPointAnim.setVisibility(View.VISIBLE);
                                holder.ivPointAnim.setText(holder.deadwood + "");
                                com.nineoldandroids.animation.AnimatorSet set = new com.nineoldandroids.animation.AnimatorSet();
                                set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                        holder.ivPointAnim, "translationY", 0, getHight(95)));
                                set.setDuration(700).start();*/


                                        TranslateAnimation scoreMerge = new TranslateAnimation(0, 0, 0, getHight(95));
                                        scoreMerge.setInterpolator(new LinearInterpolator());
                                        scoreMerge.setFillAfter(true);
                                        scoreMerge.setDuration(700);
                                        holder.ivPointAnim.setVisibility(View.VISIBLE);
                                        holder.ivPointAnim.setText(holder.deadwood + "");
                                        holder.ivPointAnim.startAnimation(scoreMerge);
                                    } else if (position == 1) {

                                /*holder.ivPointAnim.setVisibility(View.VISIBLE);
                                holder.ivPointAnim.setText(holder.deadwood + "");
                                com.nineoldandroids.animation.AnimatorSet set = new com.nineoldandroids.animation.AnimatorSet();
                                set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                        holder.ivPointAnim, "translationY", 0, -getHight(95)));
                                set.setDuration(700).start();*/

                                        TranslateAnimation scoreMerge = new TranslateAnimation(0, 0, 0, -getHight(95));
                                        scoreMerge.setInterpolator(new LinearInterpolator());
                                        scoreMerge.setFillAfter(true);
                                        scoreMerge.setDuration(700);
                                        holder.ivPointAnim.setVisibility(View.VISIBLE);
                                        holder.ivPointAnim.setText(holder.deadwood + "");
                                        holder.ivPointAnim.startAnimation(scoreMerge);
                                    }
                                } else if (getCount() == 3) {
                                    if (position == 0) {

                               /* holder.ivPointAnim.setVisibility(View.VISIBLE);
                                holder.ivPointAnim.setText(holder.deadwood + "");
                                com.nineoldandroids.animation.AnimatorSet set = new com.nineoldandroids.animation.AnimatorSet();
                                set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                        holder.ivPointAnim, "translationY", 0, getHight(190)));
                                set.setDuration(700).start();*/

                                        TranslateAnimation scoreMerge = new TranslateAnimation(0, 0, 0, getHight(190));
                                        scoreMerge.setInterpolator(new LinearInterpolator());
                                        scoreMerge.setFillAfter(true);
                                        scoreMerge.setDuration(700);
                                        holder.ivPointAnim.setVisibility(View.VISIBLE);
                                        holder.ivPointAnim.setText(holder.deadwood + "");
                                        holder.ivPointAnim.startAnimation(scoreMerge);
                                    } else if (position == 2) {

                                /*holder.ivPointAnim.setVisibility(View.VISIBLE);
                                holder.ivPointAnim.setText(holder.deadwood + "");
                                com.nineoldandroids.animation.AnimatorSet set = new com.nineoldandroids.animation.AnimatorSet();
                                set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                        holder.ivPointAnim, "translationY", 0, -getHight(190)));
                                set.setDuration(700).start();*/

                                        TranslateAnimation scoreMerge = new TranslateAnimation(0, 0, 0, -getHight(190));
                                        scoreMerge.setInterpolator(new LinearInterpolator());
                                        scoreMerge.setFillAfter(true);
                                        scoreMerge.setDuration(700);
                                        holder.ivPointAnim.setVisibility(View.VISIBLE);
                                        holder.ivPointAnim.setText(holder.deadwood + "");
                                        holder.ivPointAnim.startAnimation(scoreMerge);
                                    } else if (position == 1) {
                                        TranslateAnimation scoreMerge = new TranslateAnimation(0, 0, 0, 0);
                                        scoreMerge.setInterpolator(new AccelerateInterpolator());
                                        scoreMerge.setFillAfter(true);
                                        scoreMerge.setDuration(700);
                                        holder.ivPointAnim.setVisibility(View.VISIBLE);
                                        holder.ivPointAnim.setText(holder.deadwood + "");
                                        holder.ivPointAnim.startAnimation(scoreMerge);
                                    }
                                }
                            }
                        }, 2200);

                        if (getCount() == 2) {

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (holder.ivPointAnim.getAnimation() != null) {
                                        holder.ivPointAnim.clearAnimation();
                                    }
                                    int DeadwoodPoint = 0;

                                    holder.ivPointAnim.setVisibility(View.INVISIBLE);
                                    if (holder.winnerSeat == 1) {

                                        holder.tvPointsAnimLast2.setVisibility(View.VISIBLE);
                                        try {

                                            DeadwoodPoint = getItem(position).getJSONArray("st").getJSONObject(getItem(position).getJSONArray("st").length() - 1).getInt("DP");
                                            if (c.isDouble) {
                                                DeadwoodPoint = DeadwoodPoint / 2;
                                            }

                                            holder.tvPointsAnimLast2.setText(DeadwoodPoint + "");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                /*final int scorePoint = DeadwoodPoint + holder.prevScore;
                                com.nineoldandroids.animation.AnimatorSet set = new com.nineoldandroids.animation.AnimatorSet();
                                set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                        holder.tvPointsAnimLast2, "translationX", 0, getwidth(105)),
                                        com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                                holder.tvPointsAnimLast2, "translationY", 0, getHight(95)));
                                set.setDuration(700).start();
                                set.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        if (holder.tvPointsAnimLast2.getAnimation() != null) {
                                            holder.tvPointsAnimLast2.clearAnimation();
                                        }
                                        holder.tvPointsAnimLast2.setVisibility(View.INVISIBLE);
                                        holder.tvPoints.setText("" + scorePoint);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });*/

                                        TranslateAnimation scoreMerge = new TranslateAnimation(0, getwidth(125), 0, getHight(95));
                                        scoreMerge.setInterpolator(new LinearInterpolator());
                                        scoreMerge.setFillAfter(true);
                                        scoreMerge.setDuration(700);

                                        if (c.isDouble) {
//                                            scoreMerge.setDuration(500);
                                            scoreMerge.setRepeatCount(1);
                                            scoreMerge.setRepeatMode(Animation.RESTART);
                                        }

                                        holder.tvPointsAnimLast2.startAnimation(scoreMerge);
                                        scorePoint1 = holder.prevScore + DeadwoodPoint;
                                        Logger.print(">>>> Animation >>> MILIN >>> 2" + scorePoint1);
                                        final int finalDeadwoodPoint = DeadwoodPoint;
                                        int i = 0;
                                        scoreMerge.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {

                                                Logger.print(">>>> Animation >>> MILIN >>> Start" + scorePoint1);

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                Logger.print(">>>> Animation >>> MILIN >>> End >> " + scorePoint1);
                                                if (holder.tvPointsAnimLast2.getAnimation() != null) {
                                                    holder.tvPointsAnimLast2.clearAnimation();
                                                }
                                                holder.tvPointsAnimLast2.setVisibility(View.INVISIBLE);
                                                holder.tvPoints.setText("" + scorePoint1);

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {
                                                Logger.print(">>>> Animation >>> MILIN >>> Reapeat >> " + scorePoint1);
                                                holder.tvPoints.setText("" + scorePoint1);
                                                scorePoint1 = holder.prevScore + (finalDeadwoodPoint * c.multi);
                                                Logger.print(">>>> Animation >>> MILIN >>> 3" + scorePoint1);
                                            }
                                        });
                                    } else if (holder.winnerSeat == 0) {

                                        holder.tvPointsAnimLast.setVisibility(View.VISIBLE);
                                        try {
                                            DeadwoodPoint = getItem(position).getJSONArray("st").getJSONObject(getItem(position).getJSONArray("st").length() - 1).getInt("DP");

                                            if (c.isDouble) {
                                                DeadwoodPoint = DeadwoodPoint / 2;
                                            }

                                            holder.tvPointsAnimLast.setText(DeadwoodPoint + "");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                /*final int scorePoint = DeadwoodPoint + holder.prevScore;
                                com.nineoldandroids.animation.AnimatorSet set = new com.nineoldandroids.animation.AnimatorSet();
                                set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                        holder.tvPointsAnimLast, "translationX", 0, getwidth(105)),
                                        com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                                holder.tvPointsAnimLast, "translationY", 0, getHight(-95)));
                                set.setDuration(700).start();
                                set.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        if (holder.tvPointsAnimLast.getAnimation() != null) {
                                            holder.tvPointsAnimLast.clearAnimation();
                                        }
                                        holder.tvPointsAnimLast.setVisibility(View.INVISIBLE);
                                        holder.tvPoints.setText("" + scorePoint);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });*/

                                        TranslateAnimation scoreMerge = new TranslateAnimation(0, getwidth(125), 0, getHight(-95));
                                        scoreMerge.setInterpolator(new LinearInterpolator());
                                        scoreMerge.setFillAfter(true);
                                        scoreMerge.setDuration(700);

                                        if (c.isDouble) {
//                                            scoreMerge.setDuration(500);
                                            scoreMerge.setRepeatCount(1);
                                            scoreMerge.setRepeatMode(Animation.RESTART);
                                        }


                                        holder.tvPointsAnimLast.startAnimation(scoreMerge);
                                        scorePoint2 = holder.prevScore + DeadwoodPoint;
                                        Logger.print(">>>> Animation >>> MILIN >>> 4" + scorePoint2);
                                        final int finalDeadwoodPoint1 = DeadwoodPoint;
                                        scoreMerge.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {
                                                Logger.print(">>>> Animation >>> MILIN >>> Start222" + scorePoint2);
                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                if (holder.tvPointsAnimLast.getAnimation() != null) {
                                                    holder.tvPointsAnimLast.clearAnimation();
                                                }
                                                holder.tvPointsAnimLast.setVisibility(View.INVISIBLE);
                                                holder.tvPoints.setText("" + scorePoint2);
                                                Logger.print(">>>> Animation >>> MILIN >>> end 222" + scorePoint2);
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {
                                                Logger.print(">>>> Animation >>> MILIN >>> Repeat 222" + scorePoint2);
                                                holder.tvPoints.setText("" + scorePoint2);
                                                scorePoint2 = holder.prevScore + (finalDeadwoodPoint1 * c.multi);
                                                Logger.print(">>>> Animation >>> MILIN >>> 5" + scorePoint2);
                                            }
                                        });
                                    }
                                }
                            }, 3100);
                        } else if (getCount() == 3) {
                            Logger.print(">>>> Animation >>> MILIN >>> isDouble " + c.isDouble);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (holder.ivPointAnim.getAnimation() != null) {
                                        holder.ivPointAnim.clearAnimation();
                                    }
                                    holder.ivPointAnim.setVisibility(View.INVISIBLE);

                                    Logger.print("SET VSISIBILITY OF POINT BUTTON 1 => " + holder.pos);
                            /*if (holder.pos == 1) {
                                holder.ivPointAnim.setVisibility(View.VISIBLE);
                            } else {
                                holder.ivPointAnim.setVisibility(View.INVISIBLE);
                            }*/
                                    int DeadwoodPoint = 0;
                                    try {

                                        if (holder.winnerSeat == 0) {
                                            DeadwoodPoint = getItem(position).getJSONArray("st").getJSONObject(getItem(position).getJSONArray("st").length() - 1).getInt("DP");
                                        } else if (holder.winnerSeat == 1) {
                                            DeadwoodPoint = getItem(position).getJSONArray("st").getJSONObject(getItem(position).getJSONArray("st").length() - 1).getInt("DP");
                                        } else if (holder.winnerSeat == 2) {
                                            DeadwoodPoint = getItem(position).getJSONArray("st").getJSONObject(getItem(position).getJSONArray("st").length() - 1).getInt("DP");
                                        }
//                                        if (DeadwoodPoint > 0) {
//
//                                            if(c.isDouble){
//                                                DeadwoodPoint =DeadwoodPoint/2;
//                                            }
//
//                                            holder.ivPointAnim.setText(DeadwoodPoint + "");
//                                            Logger.print("SET VSISIBILITY OF POINT BUTTON 2 => " + holder.winnerSeat);
//                                            Logger.print(">>>> Animation >>> MILIN >>> 1"+ scorePoint);
//                                            scorePoint = DeadwoodPoint + holder.prevScore;
//                                            Logger.print(">>>> Animation >>> MILIN >>> 6 "+ scorePoint);
//                                        }
//                                        TranslateAnimation scoreMerge1 = null;

                                        if (holder.winnerSeat == 1) {

                                            if (DeadwoodPoint > 0) {

                                                if (c.isDouble) {
                                                    DeadwoodPoint = DeadwoodPoint / 2;
                                                }

                                                holder.ivPointAnim.setText(DeadwoodPoint + "");
                                                Logger.print("SET VSISIBILITY OF POINT BUTTON 2 => " + holder.winnerSeat);
                                                Logger.print(">>>> Animation >>> MILIN >>> 1" + scorePoint1);
                                                scorePoint1 = DeadwoodPoint + holder.prevScore;
                                                Logger.print(">>>> Animation >>> MILIN >>> 6 " + scorePoint1);
                                            }
                                            TranslateAnimation scoreMerge1 = null;

                                            scoreMerge1 = new TranslateAnimation(0, getwidth(130), 0, 0);
                                            scoreMerge1.setInterpolator(new LinearInterpolator());
                                            scoreMerge1.setFillAfter(true);
                                            scoreMerge1.setDuration(500);

                                            if (c.isDouble) {
//                                            scoreMerge.setDuration(500);
                                                scoreMerge1.setRepeatCount(1);
                                                scoreMerge1.setRepeatMode(Animation.RESTART);
                                            }


                                            final int finalDeadwoodPoint = DeadwoodPoint;
                                            scoreMerge1.setAnimationListener(new Animation.AnimationListener() {
                                                @Override
                                                public void onAnimationStart(Animation animation) {
                                                    Logger.print(">>>> Animation >>> MILIN >>> Start 333" + scorePoint1);
                                                }

                                                @Override
                                                public void onAnimationEnd(Animation animation) {
                                                    if (holder.ivPointAnim.getAnimation() != null) {
                                                        holder.ivPointAnim.clearAnimation();
                                                    }
                                                    holder.ivPointAnim.setVisibility(View.INVISIBLE);
                                                    holder.tvPoints.setText("" + scorePoint1);
                                                    Logger.print(">>>> Animation >>> MILIN >>> end 333" + scorePoint1);
                                                }

                                                @Override
                                                public void onAnimationRepeat(Animation animation) {
                                                    Logger.print(">>>> Animation >>> MILIN >>> reapeat 333" + scorePoint1);
                                                    if (c.isDouble) {
                                                        holder.tvPoints.setText("" + scorePoint1);
                                                        scorePoint1 = (finalDeadwoodPoint * c.multi) + holder.prevScore;
                                                        Logger.print(">>>> Animation >>> MILIN >>> 7" + scorePoint1);
                                                    }
                                                }
                                            });


                                            holder.ivPointAnim.startAnimation(scoreMerge1);
                                        } else if (holder.winnerSeat == 0) {

                                            if (DeadwoodPoint > 0) {

                                                if (c.isDouble) {
                                                    DeadwoodPoint = DeadwoodPoint / 2;
                                                }

                                                holder.ivPointAnim.setText(DeadwoodPoint + "");
                                                Logger.print("SET VSISIBILITY OF POINT BUTTON 2 => " + holder.winnerSeat);
                                                Logger.print(">>>> Animation >>> MILIN >>> 1" + scorePoint2);
                                                scorePoint2 = DeadwoodPoint + holder.prevScore;
                                                Logger.print(">>>> Animation >>> MILIN >>> 6 " + scorePoint2);
                                            }
                                            TranslateAnimation scoreMerge1 = null;

                                            scoreMerge1 = new TranslateAnimation(0, getwidth(130), getHight(190), getHight(0));
                                            scoreMerge1.setInterpolator(new LinearInterpolator());
                                            scoreMerge1.setFillAfter(true);
                                            scoreMerge1.setDuration(500);

                                            if (c.isDouble) {
//                                            scoreMerge.setDuration(500);
                                                scoreMerge1.setRepeatCount(1);
                                                scoreMerge1.setRepeatMode(Animation.RESTART);
                                            }


                                            final int finalDeadwoodPoint1 = DeadwoodPoint;
                                            scoreMerge1.setAnimationListener(new Animation.AnimationListener() {
                                                @Override
                                                public void onAnimationStart(Animation animation) {
                                                    Logger.print(">>>> Animation >>> MILIN >>> Start 444" + scorePoint2);
                                                }

                                                @Override
                                                public void onAnimationEnd(Animation animation) {
                                                    if (holder.ivPointAnim.getAnimation() != null) {
                                                        holder.ivPointAnim.clearAnimation();
                                                    }
                                                    holder.ivPointAnim.setVisibility(View.INVISIBLE);
                                                    holder.tvPoints.setText("" + scorePoint2);

                                                    Logger.print(">>>> Animation >>> MILIN >>> End 444" + scorePoint2);
                                                }

                                                @Override
                                                public void onAnimationRepeat(Animation animation) {
                                                    Logger.print(">>>> Animation >>> MILIN >>> Reapeat 444" + scorePoint2);
                                                    if (c.isDouble) {
                                                        holder.tvPoints.setText("" + scorePoint2);
                                                        scorePoint2 = (finalDeadwoodPoint1 * c.multi) + holder.prevScore;
                                                        Logger.print(">>>> Animation >>> MILIN >>> 8" + scorePoint2);
                                                    }
                                                }
                                            });

                                            holder.ivPointAnim.startAnimation(scoreMerge1);
                                        } else if (holder.winnerSeat == 2) {
                                            if (DeadwoodPoint > 0) {

                                                if (c.isDouble) {
                                                    DeadwoodPoint = DeadwoodPoint / 2;
                                                }

                                                holder.ivPointAnim.setText(DeadwoodPoint + "");
                                                Logger.print("SET VSISIBILITY OF POINT BUTTON 2 => " + holder.winnerSeat);
                                                Logger.print(">>>> Animation >>> MILIN >>> 1" + scorePoint3);
                                                scorePoint3 = DeadwoodPoint + holder.prevScore;
                                                Logger.print(">>>> Animation >>> MILIN >>> 6 " + scorePoint3);
                                            }
                                            TranslateAnimation scoreMerge1 = null;
                                            //  holder.ivPointAnim.setVisibility(View.VISIBLE);
                                    /*com.nineoldandroids.animation.AnimatorSet set = new com.nineoldandroids.animation.AnimatorSet();
                                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                            holder.ivPointAnim, "translationX", 0, getwidth(105)),
                                            com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                                    holder.ivPointAnim, "translationY", getHight(-190), 0));
                                    set.setDuration(500).start();
                                    set.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animator) {
                                            if (holder.tvPointsAnimLast.getAnimation() != null) {
                                                holder.tvPointsAnimLast.clearAnimation();
                                            }
                                            holder.tvPointsAnimLast.setVisibility(View.INVISIBLE);
                                            holder.tvPoints.setText("" + scorePoint);
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animator) {

                                        }
                                    });*/


                                            scoreMerge1 = new TranslateAnimation(0, getwidth(130), getHight(-190), 0);
                                            scoreMerge1.setInterpolator(new LinearInterpolator());
                                            scoreMerge1.setFillAfter(true);
                                            scoreMerge1.setDuration(500);

                                            if (c.isDouble) {
//                                            scoreMerge.setDuration(500);
                                                scoreMerge1.setRepeatCount(1);
                                                scoreMerge1.setRepeatMode(Animation.RESTART);
                                            }

                                            final int finalDeadwoodPoint2 = DeadwoodPoint;
                                            scoreMerge1.setAnimationListener(new Animation.AnimationListener() {
                                                @Override
                                                public void onAnimationStart(Animation animation) {
                                                    Logger.print(">>>> Animation >>> MILIN >>> Start 555" + scorePoint3);
                                                }

                                                @Override
                                                public void onAnimationEnd(Animation animation) {
                                                    if (holder.ivPointAnim.getAnimation() != null) {
                                                        holder.ivPointAnim.clearAnimation();
                                                    }
                                                    holder.ivPointAnim.setVisibility(View.INVISIBLE);
                                                    holder.tvPoints.setText("" + scorePoint3);
                                                    Logger.print(">>>> Animation >>> MILIN >>> End 555" + scorePoint3);
                                                }

                                                @Override
                                                public void onAnimationRepeat(Animation animation) {
                                                    Logger.print(">>>> Animation >>> MILIN >>> Repeat 555" + scorePoint3);
                                                    if (c.isDouble) {
                                                        holder.tvPoints.setText("" + scorePoint3);
                                                        scorePoint3 = (finalDeadwoodPoint2 * c.multi) + holder.prevScore;
                                                        Logger.print(">>>> Animation >>> MILIN >>> 9" + scorePoint3);
                                                    }
                                                }
                                            });

                                            holder.ivPointAnim.startAnimation(scoreMerge1);
                                        }



                                /*if (holder.pos == 1) {
                                    holder.ivPointAnim.startAnimation(scoreMerge1);
                                }*/
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 3100);

                        }
                        if (getItem(position).has("st")) {
                            JSONObject jObjScore = null;
                            try {
                                jObjScore = getItem(position).getJSONArray("st").getJSONObject(getItem(position).getJSONArray("st").length() - 1);
                                if (jObjScore.has("GP") && jObjScore.getInt("GP") > 0) {

                                    final int GP = jObjScore.getInt("GP");
                                    final int start = holder.prevScore + jObjScore.getInt("DP") + jObjScore.getInt("WP");
                                    final int tot = start + GP;

                                    Logger.print(">>>> Animation >>> MILIN >>> GP " + GP);
                                    Logger.print(">>>> Animation >>> MILIN >>> start " + start);
                                    Logger.print(">>>> Animation >>> MILIN >>> tot " + tot);

                                    holder.tvGinPointText.setVisibility(View.VISIBLE);
                                    holder.tvGinPoints.setVisibility(View.VISIBLE);
//                                    holder.tvGinPoints_iv.setVisibility(View.VISIBLE);
                                    holder.ivGin.setVisibility(View.VISIBLE);
                                    holder.tvGinPoints.setText("" + jObjScore.getInt("GP"));

                                    ImageView animaply = iv_gin_plus;

                                    if (GP > 25) {
                                        holder.tvGinPointText.setText("Big Gin Point");
                                        animaply = iv_big_gin_plus;
                                    }

                                    final ImageView finalAnimaply = animaply;
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            int[] pointsXY = new int[2];
                                            holder.tvPoints.getLocationOnScreen(pointsXY);
                                            int px = getRelativeLeft(holder.tvPoints);
                                            int py = getRelativeTop(holder.tvPoints);
                                            px = pointsXY[0];
                                            py = pointsXY[1];

                                            int[] gpXY = new int[2];
                                            holder.tvGinPoints.getLocationOnScreen(gpXY);

                                            int gpx = getRelativeLeft(holder.tvGinPoints);
                                            int gpy = getRelativeTop(holder.tvGinPoints);
                                            gpx = gpXY[0];
                                            gpy = gpXY[1];

                                            int[] ivXY = new int[2];
                                            finalAnimaply.getLocationOnScreen(ivXY);
                                            int ivx = getRelativeLeft(iv_gin_plus);
                                            int ivy = getRelativeTop(iv_gin_plus);
                                            ivx = ivXY[0];
                                            ivy = ivXY[1];

                                            Logger.print(">> ANIMATION >>> X >> " + px);
                                            Logger.print(">> ANIMATION >>> Y >> " + py);

                                            Logger.print(">> ANIMATION >>> X1 >> " + gpx);
                                            Logger.print(">> ANIMATION >>> Y1 >> " + gpy);

                                            Logger.print(">> ANIMATION >>> X2 >> " + ivx);
                                            Logger.print(">> ANIMATION >>> Y2 >> " + ivy);


                                            holder.tvGinPoints.bringToFront();

                                    /*AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.2f);
                                    alphaAnimation.setStartOffset(1500);
                                    alphaAnimation.setDuration(500); */

                                            ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1.0f, 0, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                            scaleAnimation.setDuration(1000);
                                            scaleAnimation.setFillAfter(true);

                                   /* com.nineoldandroids.animation.AnimatorSet set = new com.nineoldandroids.animation.AnimatorSet();
                                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                            holder.tvGinPoints, "translationX", 0, x - x1),
                                            com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                                    holder.tvGinPoints, "translationY", 0, y - y1));
                                    set.setDuration(500).start();
                                    set.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animator) {
                                            if (holder.tvGinPoints.getAnimation() != null) {
                                                holder.tvGinPoints.clearAnimation();
                                            }
                                            //holder.tvGinPoints.setVisibility(View.INVISIBLE);
                                            // holder.tvGinPointText.setVisibility(View.INVISIBLE);
                                            IncrementCounterChip(holder.tvPoints, start, tot);
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animator) {

                                        }
                                    });*/


                                            TranslateAnimation translateAnimation = new TranslateAnimation(gpx - ivx - getwidth(100), 0, gpy - ivy - (getHight(100)), 0);
                                            translateAnimation.setDuration(1000);
                                            translateAnimation.setFillAfter(true);
//                                            translateAnimation.setStartOffset(1500);

                                            TranslateAnimation translateAnimation1 = new TranslateAnimation(0, px - ivx - getwidth(100), 0, py - ivy - (getHight(100)));
                                            translateAnimation1.setDuration(500);
                                            translateAnimation1.setFillAfter(false);
                                            translateAnimation1.setStartOffset(1500);

                                            ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                            scaleAnimation1.setDuration(500);
                                            scaleAnimation1.setFillAfter(false);
                                            scaleAnimation1.setStartOffset(1500);

                                            AnimationSet animationSet = new AnimationSet(true);
                                            // animationSet.addAnimation(alphaAnimation);
                                            animationSet.addAnimation(scaleAnimation);
                                            animationSet.addAnimation(scaleAnimation1);
                                            animationSet.addAnimation(translateAnimation);
                                            animationSet.addAnimation(translateAnimation1);


                                            animationSet.setAnimationListener(new Animation.AnimationListener() {
                                                @Override
                                                public void onAnimationStart(Animation animation) {
                                                    Logger.print(">> ANIMATION >>> STARTED >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");

                                                }

                                                @Override
                                                public void onAnimationEnd(Animation animation) {
                                                    if (holder.tvGinPoints.getAnimation() != null) {
                                                        holder.tvGinPoints.clearAnimation();
                                                    }

                                                    //holder.tvGinPoints.setVisibility(View.INVISIBLE);
                                                    // holder.tvGinPointText.setVisibility(View.INVISIBLE);
                                                    // IncrementCounterChip(holder.tvPoints, start, tot);
                                                    holder.tvPoints.setText(tot + "");
                                                }

                                                @Override
                                                public void onAnimationRepeat(Animation animation) {

                                                }
                                            });
//                                            holder.tvGinPoints_iv.startAnimation(animationSet);
                                            finalAnimaply.startAnimation(animationSet);
                                            if (GameisOn()) {
                                                Music_Manager.Play_WinPoints();
                                            }
                                            //holder.tvGinPointText.startAnimation(animationSet);
                                        }
                                    }, 5800);
                                } else {
                                    holder.tvGinPointText.setVisibility(View.GONE);
                                    holder.tvGinPoints.setVisibility(View.GONE);
                                    holder.tvGinPoints_iv.setVisibility(View.GONE);
                                    holder.ivGin.setVisibility(View.GONE);
                                }

                                if (jObjScore.has("KP") && jObjScore.getInt("KP") > 0) {
                                    final int KP = jObjScore.getInt("KP");
                                    int WP = 0;
                                    if (jObjScore.has("WP")) {
                                        WP = jObjScore.getInt("WP");
                                    }
                                    final int start = holder.prevScore + jObjScore.getInt("DP") + WP;
                                    final int tot = start + KP;

                                    holder.tvKnockPenaltyText.setVisibility(View.VISIBLE);
                                    holder.tvKnockPenalty.setVisibility(View.VISIBLE);
                                    holder.ivUndercut.setVisibility(View.VISIBLE);
                                    holder.tvKnockPenalty.setText("" + KP);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            int[] pointsXY = new int[2];
                                            holder.tvPoints.getLocationOnScreen(pointsXY);
                                            int x = getRelativeLeft(holder.tvPoints);
                                            int y = getRelativeTop(holder.tvPoints);

                                            int[] gpXY = new int[2];
                                            holder.tvKnockPenalty.getLocationOnScreen(gpXY);
                                            int x1 = getRelativeLeft(holder.tvKnockPenalty);
                                            int y1 = getRelativeTop(holder.tvKnockPenalty);

                                            holder.tvKnockPenalty.bringToFront();

                                            int[] ivXY = new int[2];
                                            iv_undercut_plus.getLocationOnScreen(ivXY);
                                            int ivx = getRelativeLeft(iv_gin_plus);
                                            int ivy = getRelativeTop(iv_gin_plus);
                                            ivx = ivXY[0];
                                            ivy = ivXY[1];

                                   /* com.nineoldandroids.animation.AnimatorSet set = new com.nineoldandroids.animation.AnimatorSet();
                                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                            holder.tvKnockPenalty, "translationX", 0, x - x1),
                                            com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                                    holder.tvKnockPenalty, "translationY", 0, y - y1));
                                    set.setDuration(500).start();
                                    set.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animator) {
                                            if (holder.tvKnockPenalty.getAnimation() != null) {
                                                holder.tvKnockPenalty.clearAnimation();
                                            }
                                            // holder.tvKnockPenalty.setVisibility(View.INVISIBLE);

                                            if (holder.tvKnockPenaltyText.getAnimation() != null) {
                                                holder.tvKnockPenaltyText.clearAnimation();
                                            }
                                            //holder.tvKnockPenaltyText.setVisibility(View.INVISIBLE);

                                            // int points = Integer.parseInt(holder.tvPointsAnimLast2.getText().toString()) + KP;
                                            *//*holder.tvPoints.setText(tot + "");*//*
                                            IncrementCounterChip(holder.tvPoints, start, tot);
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animator) {

                                        }
                                    });*/

                                    /*AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.2f);
                                    alphaAnimation.setStartOffset(1500);
                                    alphaAnimation.setDuration(500); */

                                            ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1.0f, 0, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                            scaleAnimation.setDuration(1000);
                                            scaleAnimation.setFillAfter(true);

                                            //holder.tvKnockPenalty.startAnimation(scaleAnimation);
                                            TranslateAnimation translateAnimation1 = new TranslateAnimation(x1 - ivx - getwidth(100), 0, y1 - ivy - (getHight(100)), 0);
                                            translateAnimation1.setDuration(1000);
                                            translateAnimation1.setFillAfter(true);

//                                            TranslateAnimation translateAnimation = new TranslateAnimation(0, x - x1, 0, y - y1);
//                                            translateAnimation.setDuration(500);
//                                            translateAnimation.setFillAfter(false);
//                                            translateAnimation.setStartOffset(1500);

                                            TranslateAnimation translateAnimation = new TranslateAnimation(0, x - ivx - getwidth(100), 0, y - ivy - (getHight(100)));
                                            translateAnimation.setDuration(500);
                                            translateAnimation.setFillAfter(false);
                                            translateAnimation.setStartOffset(1500);

                                            ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                            scaleAnimation1.setDuration(500);
                                            scaleAnimation1.setFillAfter(false);
                                            scaleAnimation1.setStartOffset(1500);

                                            AnimationSet animationSet = new AnimationSet(true);
                                    /*animationSet.addAnimation(alphaAnimation);*/
                                            animationSet.addAnimation(scaleAnimation);
                                            animationSet.addAnimation(scaleAnimation1);
                                            animationSet.addAnimation(translateAnimation1);
                                            animationSet.addAnimation(translateAnimation);


                                            animationSet.setAnimationListener(new Animation.AnimationListener() {
                                                @Override
                                                public void onAnimationStart(Animation animation) {

                                                }

                                                @Override
                                                public void onAnimationEnd(Animation animation) {
                                                    if (holder.tvKnockPenalty.getAnimation() != null) {
                                                        holder.tvKnockPenalty.clearAnimation();
                                                    }
                                                    // holder.tvKnockPenalty.setVisibility(View.INVISIBLE);

                                                    if (holder.tvKnockPenaltyText.getAnimation() != null) {
                                                        holder.tvKnockPenaltyText.clearAnimation();
                                                    }
                                                    //holder.tvKnockPenaltyText.setVisibility(View.INVISIBLE);

                                                    // int points = Integer.parseInt(holder.tvPointsAnimLast2.getText().toString()) + KP;
                                                    holder.tvPoints.setText(tot + "");
                                                    //IncrementCounterChip(holder.tvPoints, start, tot);

                                                }

                                                @Override
                                                public void onAnimationRepeat(Animation animation) {

                                                }
                                            });
                                            iv_undercut_plus.startAnimation(animationSet);
                                            if (GameisOn()) {
                                                Music_Manager.Play_WinPoints();
                                            }
                                            // holder.tvKnockPenaltyText.startAnimation(animationSet);
                                        }
                                    }, 5800);
                                } else {
                                    holder.tvKnockPenaltyText.setVisibility(View.GONE);
                                    holder.tvKnockPenalty.setVisibility(View.GONE);
                                    holder.ivUndercut.setVisibility(View.GONE);
                                }

                                if (jObjScore.has("WP") && jObjScore.getInt("WP") > 0) {
                                    final int WP = jObjScore.getInt("WP");
                                    final int start = holder.prevScore + jObjScore.getInt("DP");
                                    final int tot = start + WP;

                                    holder.tvWinPointsText.setVisibility(View.VISIBLE);
                                    holder.tvWinPoints.setVisibility(View.VISIBLE);
                                    holder.tvWinPoints.setText("" + WP);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {


                                            int[] pointsXY = new int[2];
                                            holder.tvPoints.getLocationOnScreen(pointsXY);
                                            int x = pointsXY[0];
                                            int y = pointsXY[1];

                                            int[] gpXY = new int[2];
                                            holder.tvWinPoints.getLocationOnScreen(gpXY);
                                            int x1 = gpXY[0];
                                            int y1 = gpXY[1];

                                   /* com.nineoldandroids.animation.AnimatorSet set = new com.nineoldandroids.animation.AnimatorSet();
                                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                            holder.tvWinPoints, "scaleX", 0, x - x1),
                                            com.nineoldandroids.animation.ObjectAnimator.ofFloat(
                                                    holder.tvWinPoints, "scaleY", 0, y - y1));
                                    set.setDuration(500).start();
                                    set.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animator) {
                                            if (holder.tvWinPoints.getAnimation() != null) {
                                                holder.tvWinPoints.clearAnimation();
                                            }


                                            holder.tvWinPoints.setLayoutParams(holder.inittianParams);
                                            // holder.tvWinPoints.setVisibility(View.INVISIBLE);
                                            // holder.tvWinPointsText.setVisibility(View.INVISIBLE);
                                            //int points = Integer.parseInt(holder.tvPointsAnimLast2.getText().toString()) + WP;
                                            *//*holder.tvPoints.setText(tot + "");*//*
                                           // IncrementCounterChip(holder.tvPoints, start, tot);
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animator) {

                                        }
                                    });*/

                                    /*AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.2f);
                                    alphaAnimation.setStartOffset(1500);
                                    alphaAnimation.setDuration(500);*/

                                            ScaleAnimation scaleAnimation = new ScaleAnimation(0, 3.0f, 0, 3.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                            scaleAnimation.setDuration(1000);
                                            scaleAnimation.setFillAfter(true);

                                            TranslateAnimation translateAnimation = new TranslateAnimation(0, x - x1, 0, y - y1);
                                            translateAnimation.setDuration(500);
                                            translateAnimation.setFillAfter(false);
                                            translateAnimation.setStartOffset(1500);

                                            ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.0f, 0.3f, 1.0f, 0.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                            scaleAnimation1.setDuration(500);
                                            scaleAnimation1.setFillAfter(false);
                                            scaleAnimation1.setStartOffset(1500);


                                            AnimationSet animationSet = new AnimationSet(true);
                                    /*animationSet.addAnimation(alphaAnimation);*/
                                            animationSet.addAnimation(scaleAnimation);
                                            animationSet.addAnimation(scaleAnimation1);
                                            animationSet.addAnimation(translateAnimation);


                                            animationSet.setAnimationListener(new Animation.AnimationListener() {
                                                @Override
                                                public void onAnimationStart(Animation animation) {

                                                }

                                                @Override
                                                public void onAnimationEnd(Animation animation) {
                                                    if (holder.tvWinPoints.getAnimation() != null) {
                                                        holder.tvWinPoints.clearAnimation();
                                                    }
                                                    // holder.tvWinPoints.setVisibility(View.INVISIBLE);
                                                    // holder.tvWinPointsText.setVisibility(View.INVISIBLE);
                                                    //int points = Integer.parseInt(holder.tvPointsAnimLast2.getText().toString()) + WP;
                                                    holder.tvPoints.setText(tot + "");
                                                    // IncrementCounterChip(holder.tvPoints, start, tot);
                                                }

                                                @Override
                                                public void onAnimationRepeat(Animation animation) {

                                                }
                                            });
                                            holder.tvWinPoints.startAnimation(animationSet);
                                            if (GameisOn()) {
                                                Music_Manager.Play_WinPoints();
                                            }
                                            //holder.tvWinPointsText.startAnimation(animationSet);
                                        }
                                    }, 3800);
                                } else {
                                    holder.tvWinPointsText.setVisibility(View.GONE);
                                    holder.tvWinPoints.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } else {
                holder = (RecordHolder) mView.getTag();
            }
            return mView;
        }

        private void IncrementCounterChip(final TextView view, int start, int end) {

//            ValueAnimator animator = new ValueAnimator();
//            animator.setObjectValues(start, end);
//            animator.setDuration(1500);
//
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    view.setText(" " + animation.getAnimatedValue());
//                }
//            });
//            animator.start();

           /* new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isTutorial)
                                tvTotalBetValue.setText("1,500");
                            else
                                tvTotalBetValue.setText("" + betval);
                        }
                    });
                }
            }, 800);*/
        }

        public class RecordHolder {
            public int deadwoodcounter;
            public View tvGinPoints_iv;
            ImageView ivKnock, ivYouTag, ivGin, ivUndercut;
            TextView tvUserName, tvPoints, ivPointAnim, tvPointsAnimLast, tvPointsAnimLast2, tvGinPointText, tvGinPoints, tvWinPointsText, tvWinPoints, tvKnockPenaltyText, tvKnockPenalty, tvTotalPoints;
            ImageView[] ivCards = new ImageView[11];
            TextView[] ivpoints = new TextView[11];
            int deadwood, pos = -1;
            int winnerSeat = -1;
            int prevScore = 0;
            CircleImageView ivUserProfile;
            ImageView iv_win_tag, iv_win_crown;
        }
    }

}

