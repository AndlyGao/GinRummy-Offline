package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import utils.C;
import utils.Logger;
import utils.PreferenceManager;
import utils.ResponseCodes;

/**
 * Created by milin.patel on 5/4/2017.
 */

public class Select_Mode extends Activity implements View.OnClickListener {

    public static Handler handler;
    C c = C.getInstance();
    int ScreenWidth, ScreenHeight;
    Typeface typeface;

    //========================================================================
    //    private AdView mAdView;
//    private AdRequest mRequest;
    FrameLayout frame_banner;
    TextView title;
    Button close;
    ImageView gin_rummy, gin_rummy_middle, gin_rummy_rbn;
    ImageView straight, straight_middle, straight_rbn;
    ImageView hollywood, hollywood_middle, hollywood_rbn;
    ImageView Oklahoma, Oklahoma_middle, Oklahoma_rbn;
    LinearLayout lnr_main_hollywood;
    FrameLayout main;
    TextView gin_rummy_text, straight_text, hollywood_text, Oklahoma_text;
    TextView tv_point_gin_rummy, tv_point_straight, tv_point_Oklahoma, tv_point_hollywood;
    private int countern;
    private FrameLayout frm_point_gin_rummy, frm_point_Oklahoma, frm_point_hollywood, frm_point_straight;
    private Dialog ConfirmationDialogInfo;


    //=========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.daillog_select_mode);
        ScreenHeight = c.height;
        ScreenWidth = c.width;
        typeface = c.typeface;

        FindViewById();
        LayoutScreen();
        initHandler();


    }

    private void initHandler() {

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == ResponseCodes.FINISH) {
                    finish();
                }
                return false;
            }
        });
    }


    private void LayoutScreen() {
        //================= Tittle And Close ========================================

        int w = getwidth(80);
        int h = w * 70 / 80;

        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) close.getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = getheight(15);
        frm.rightMargin = getwidth(15);

        w = getwidth(900);
        h = w * 70 / 900;
        frm = (FrameLayout.LayoutParams) title.getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.topMargin = getheight(15);

        //=================== Middle Buttons ========================================

        w = getwidth(400);
        frm = (FrameLayout.LayoutParams) gin_rummy.getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) straight.getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) hollywood.getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) Oklahoma.getLayoutParams();
        frm.width = w;
        frm.height = w;
        //==================================================================================================
        frm = (FrameLayout.LayoutParams) gin_rummy_middle.getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) straight_middle.getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) hollywood_middle.getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) Oklahoma_middle.getLayoutParams();
        frm.width = w;
        frm.height = w;
        //==================================================================================================
        frm = (FrameLayout.LayoutParams) gin_rummy_rbn.getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) straight_rbn.getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) hollywood_rbn.getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) Oklahoma_rbn.getLayoutParams();
        frm.width = w;
        frm.height = w;
        //==================================================================================================
        frm = (FrameLayout.LayoutParams) gin_rummy_text.getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) straight_text.getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) hollywood_text.getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) Oklahoma_text.getLayoutParams();
        frm.width = w;
        frm.height = w;

        //==================================================================================================

        w = getwidth(304);
        h = getheight(91);
        LinearLayout.LayoutParams lnr = (LinearLayout.LayoutParams) findViewById(R.id.frm_point_gin_rummy).getLayoutParams();
        lnr.width = w;
        lnr.height = h;

        lnr = (LinearLayout.LayoutParams) findViewById(R.id.frm_point_straight).getLayoutParams();
        lnr.width = w;
        lnr.height = h;

        lnr = (LinearLayout.LayoutParams) findViewById(R.id.frm_point_hollywood).getLayoutParams();
        lnr.width = w;
        lnr.height = h;

        lnr = (LinearLayout.LayoutParams) findViewById(R.id.frm_point_Oklahoma).getLayoutParams();
        lnr.width = w;
        lnr.height = h;

        //===================================================================================================
        w = getwidth(77);
        lnr = (LinearLayout.LayoutParams) findViewById(R.id.iv_point_gin_rummy).getLayoutParams();
        lnr.width = lnr.height = w;
        lnr.leftMargin = w * 10 / 77;

        lnr = (LinearLayout.LayoutParams) findViewById(R.id.iv_point_straight).getLayoutParams();
        lnr.width = lnr.height = w;
        lnr.leftMargin = w * 10 / 77;

        lnr = (LinearLayout.LayoutParams) findViewById(R.id.iv_point_hollywood).getLayoutParams();
        lnr.width = lnr.height = w;
        lnr.leftMargin = w * 10 / 77;

        lnr = (LinearLayout.LayoutParams) findViewById(R.id.iv_point_Oklahoma).getLayoutParams();
        lnr.width = lnr.height = w;
        lnr.leftMargin = w * 10 / 77;


    }

    private void FindViewById() {

        main = findViewById(R.id.main_frame_d2);

        //================= Tittle And Close ========================================

        title = findViewById(R.id.tv_tittle_d2);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(38));
        title.setTypeface(typeface, Typeface.BOLD);

        close = findViewById(R.id.btn_close_d2);
        close.setOnClickListener(this);

        //=================== Middle Buttons ========================================

        lnr_main_hollywood = findViewById(R.id.lnr_main_hollywood);

        gin_rummy = findViewById(R.id.gin_rummy);
        straight = findViewById(R.id.straight);
        hollywood = findViewById(R.id.hollywood);
        Oklahoma = findViewById(R.id.Oklahoma);

        gin_rummy_middle = findViewById(R.id.gin_rummy_middle);
        gin_rummy_rbn = findViewById(R.id.gin_rummy_rbn);
        straight_middle = findViewById(R.id.straight_middle);
        straight_rbn = findViewById(R.id.straight_rbn);
        hollywood_middle = findViewById(R.id.hollywood_middle);
        hollywood_rbn = findViewById(R.id.hollywood_rbn);
        Oklahoma_middle = findViewById(R.id.Oklahoma_middle);
        Oklahoma_rbn = findViewById(R.id.Oklahoma_rbn);

        gin_rummy_text = findViewById(R.id.gin_rummy_text);
        straight_text = findViewById(R.id.straight_text);
        hollywood_text = findViewById(R.id.hollywood_text);
        Oklahoma_text = findViewById(R.id.Oklahoma_text);

        gin_rummy.setOnClickListener(this);
        gin_rummy_middle.setOnClickListener(this);
        gin_rummy_rbn.setOnClickListener(this);
        gin_rummy_text.setOnClickListener(this);

        straight.setOnClickListener(this);
        straight_middle.setOnClickListener(this);
        straight_rbn.setOnClickListener(this);
        straight_text.setOnClickListener(this);

        hollywood.setOnClickListener(this);
        hollywood_middle.setOnClickListener(this);
        hollywood_rbn.setOnClickListener(this);
        hollywood_text.setOnClickListener(this);

        Oklahoma.setOnClickListener(this);
        Oklahoma_middle.setOnClickListener(this);
        Oklahoma_rbn.setOnClickListener(this);
        Oklahoma_text.setOnClickListener(this);

        tv_point_gin_rummy = findViewById(R.id.tv_point_gin_rummy);
        tv_point_straight = findViewById(R.id.tv_point_straight);
        tv_point_Oklahoma = findViewById(R.id.tv_point_Oklahoma);
        tv_point_hollywood = findViewById(R.id.tv_point_hollywood);

        tv_point_gin_rummy.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tv_point_straight.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tv_point_Oklahoma.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tv_point_hollywood.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tv_point_gin_rummy.setTypeface(typeface);
        tv_point_straight.setTypeface(typeface);
        tv_point_Oklahoma.setTypeface(typeface);
        tv_point_hollywood.setTypeface(typeface);


        frm_point_gin_rummy = findViewById(R.id.frm_point_gin_rummy);
        frm_point_straight = findViewById(R.id.frm_point_straight);
        frm_point_Oklahoma = findViewById(R.id.frm_point_Oklahoma);
        frm_point_hollywood = findViewById(R.id.frm_point_hollywood);

        frm_point_gin_rummy.setOnClickListener(this);
        frm_point_straight.setOnClickListener(this);
        frm_point_Oklahoma.setOnClickListener(this);
        frm_point_hollywood.setOnClickListener(this);

        long point = 10;

        point = PreferenceManager.getPlayerPoints(0, PreferenceManager.getNumberOfPlayers());
        tv_point_gin_rummy.setText("Point : " + point);

        point = PreferenceManager.getPlayerPoints(1, PreferenceManager.getNumberOfPlayers());
        tv_point_straight.setText("Point : " + point);

        point = PreferenceManager.getPlayerPoints(3, PreferenceManager.getNumberOfPlayers());
        tv_point_Oklahoma.setText("Point : " + point);

        point = PreferenceManager.getPlayerPoints(2, PreferenceManager.getNumberOfPlayers());
        tv_point_hollywood.setText("Point : " + point);


        frame_banner = findViewById(R.id.frame_banner);
//        frame_banner.setVisibility(View.GONE);
//
//        try {
//            frame_banner.removeAllViews();
//            frame_banner.addView(c.mAdView);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if(PreferenceManager.getisshowAd()/* && c.is_Ads_Show_After_Two_Day*/){
//            frame_banner.setVisibility(View.VISIBLE);
//        }

    }


    private int getheight(int val) {
        // TODO Auto-generated method stub
        return ScreenHeight * val / 720;
    }

    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return ScreenWidth * val / 1280;
    }

    @Override
    public void onClick(View v) {

        if (v == close) {
            Music_Manager.buttonclick();
            finish();
            overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
        } else if (v == gin_rummy || v == gin_rummy_middle || v == gin_rummy_rbn || v == gin_rummy_text) {
            Music_Manager.buttonclick();
            c.gameType = 0;
            finish();
            sendMassegeToDashBoard();

        } else if (v == straight || v == straight_middle || v == straight_rbn || v == straight_text) {
            Music_Manager.buttonclick();
            c.gameType = 1;
            finish();
            sendMassegeToDashBoard();


        } else if (v == hollywood || v == hollywood_middle || v == hollywood_rbn || v == hollywood_text) {
            Music_Manager.buttonclick();
            c.gameType = 2;
            finish();
            sendMassegeToDashBoard();

        } else if (v == Oklahoma || v == Oklahoma_middle || v == Oklahoma_rbn || v == Oklahoma_text) {
            Music_Manager.buttonclick();
            c.gameType = 3;
            finish();
            sendMassegeToDashBoard();

        } else if (v == frm_point_gin_rummy) {
            c.gameType = 0;
            Music_Manager.buttonclick();
            PreferenceManager.setgameType(0);
            if (c.Chips >= 500) {
                Intent twoPlayer = new Intent(Select_Mode.this, Activity_Point_Select.class);
                startActivity(twoPlayer);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
            } else {
                showInfoDialogBuy("Out Of Chip", "You don't have enough chips to play on table!");
            }

        } else if (v == frm_point_straight) {
            c.gameType = 1;
            Music_Manager.buttonclick();

            PreferenceManager.setgameType(0);
            if (c.Chips >= 500) {
                Intent twoPlayer = new Intent(Select_Mode.this, Activity_Point_Select.class);
                startActivity(twoPlayer);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
            } else {
                showInfoDialogBuy("Out Of Chip", "You don't have enough chips to play on table!");
            }

        } else if (v == frm_point_Oklahoma) {
            c.gameType = 3;
            Music_Manager.buttonclick();

            PreferenceManager.setgameType(0);
            if (c.Chips >= 500) {
                Intent twoPlayer = new Intent(Select_Mode.this, Activity_Point_Select.class);
                startActivity(twoPlayer);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
            } else {
                showInfoDialogBuy("Out Of Chip", "You don't have enough chips to play on table!");
            }
        } else if (v == frm_point_hollywood) {
            c.gameType = 2;
            Music_Manager.buttonclick();

            PreferenceManager.setgameType(0);
            if (c.Chips >= 500) {
                Intent twoPlayer = new Intent(Select_Mode.this, Activity_Point_Select.class);
                startActivity(twoPlayer);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
            } else {
                showInfoDialogBuy("Out Of Chip", "You don't have enough chips to play on table!");
            }
        }

    }

    private void StartMiddleButtonAnimation() {

        Animation anim = new AnimationUtils().loadAnimation(this, R.anim.scalebounce);
        anim.setInterpolator(new LinearInterpolator());
        Animation animr = new AnimationUtils().loadAnimation(this, R.anim.scalebouncerevers);
        animr.setInterpolator(new LinearInterpolator());

        final AnimationSet animm = (AnimationSet) new AnimationUtils().loadAnimation(this, R.anim.midle_shake);
        animm.setRepeatCount(0);

        gin_rummy.startAnimation(anim);
        straight.startAnimation(anim);
        hollywood.startAnimation(anim);
        Oklahoma.startAnimation(anim);

        gin_rummy_middle.startAnimation(animr);
        straight_middle.startAnimation(animr);
        hollywood_middle.startAnimation(animr);
        Oklahoma_middle.startAnimation(animr);

//        gin_rummy_text.startAnimation(animm);


        countern = 0;
        animm.getAnimations().get(1).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                if (countern == 3) {
                    countern = 0;
                } else {
                    countern++;
                }

                Logger.print("_ANIM_END : counter : " + countern);
                if (countern == 0) {
                    if (Oklahoma_text.getAnimation() != null) {
                        Oklahoma_text.getAnimation().cancel();
                        Oklahoma_text.getAnimation().reset();
                        Oklahoma_text.clearAnimation();
                    }
                    gin_rummy_text.startAnimation(animm);

                } else if (countern == 1) {
                    if (gin_rummy_text.getAnimation() != null) {
                        gin_rummy_text.getAnimation().cancel();
                        gin_rummy_text.getAnimation().reset();
                        gin_rummy_text.clearAnimation();
                    }
                    straight_text.startAnimation(animm);

                } else if (countern == 2) {
                    if (straight_text.getAnimation() != null) {
                        straight_text.getAnimation().cancel();
                        straight_text.getAnimation().reset();
                        straight_text.clearAnimation();
                    }
                    if (lnr_main_hollywood.getVisibility() == View.VISIBLE) {
                        hollywood_text.startAnimation(animm);
                    } else {
                        countern = 3;
                        Oklahoma_text.startAnimation(animm);
                    }

                } else if (countern == 3) {
                    if (hollywood_text.getAnimation() != null) {
                        hollywood_text.getAnimation().cancel();
                        hollywood_text.getAnimation().reset();
                        hollywood_text.clearAnimation();
                    }
                    if (straight_text.getAnimation() != null) {
                        straight_text.getAnimation().cancel();
                        straight_text.getAnimation().reset();
                        straight_text.clearAnimation();
                    }
                    Oklahoma_text.startAnimation(animm);

                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
//        if(c.mAdView!= null){
//            c.mAdView.pause();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(c.mAdView!= null){
//            c.mAdView.resume();
//        }
        c.payment_Handler = this.handler;

        c.con = this;
        c.activity = Select_Mode.this;

        StartMiddleButtonAnimation();

//        if(PreferenceManager.getBonusTimerCount()<5){
//            Logger.print(">>>>>>> BONUS COUNT BRFORE 111 >>> "+PreferenceManager.getBonusTimerCount());
//
//            PreferenceManager.setBonusTimerCount(PreferenceManager.getBonusTimerCount()+1);
//            Logger.print(">>>>>>> BONUS COUNT AFTER 111 >>> "+PreferenceManager.getBonusTimerCount());
//        }else{
//            PreferenceManager.setBonusTimerCount(0);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(c.mAdView!= null){
//            c.mAdView.destroy();
//        }

        try {
            if (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.cancel();
                ConfirmationDialogInfo = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame_banner.removeAllViews();
        recursiveLoopChildren(main);
    }

    private void recursiveLoopChildren(ViewGroup parent) {


        if (parent == null) return;
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                recursiveLoopChildren((ViewGroup) child);
            } else {
                if (child != null) {
                    try {

                        if (child instanceof ImageView) {
                            try {
                                ((ImageView) child).setImageResource(0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        child.setBackgroundResource(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private void sendMassegeToDashBoard() {

        if (Dashboard.handler != null) {
            Message msg = new Message();
            msg.what = ResponseCodes.Start_Playing_Mode;
            Dashboard.handler.sendMessage(msg);
        }

    }

    private void showInfoDialogBuy(String DialogTitle, String Message) {
        // TODO Auto-generated method stub

        LinearLayout.LayoutParams lin;

        if (ConfirmationDialogInfo != null || (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing())) {
            if (ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.dismiss();
            }
            ConfirmationDialogInfo = null;
        }

        ConfirmationDialogInfo = new Dialog(Select_Mode.this,
                R.style.Theme_Transparent);
        ConfirmationDialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialogInfo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ConfirmationDialogInfo.setContentView(R.layout.dialog);

        TextView title = ConfirmationDialogInfo
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(typeface);
        title.setText(DialogTitle);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);

        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.mainpopup).getLayoutParams();
        frm.height = getheight(350);
        frm.width = getwidth(900);

        TextView message = ConfirmationDialogInfo
                .findViewById(R.id.tvMessage);
        message.setTypeface(typeface);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

        Button btnOne = ConfirmationDialogInfo.findViewById(R.id.button1);
        btnOne.setTypeface(typeface);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("Buy Chips");

        Button btnTwo = ConfirmationDialogInfo.findViewById(R.id.button2);
        btnTwo.setTypeface(typeface);
        btnTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnTwo.setText("Watch Video");
        btnTwo.setVisibility(View.GONE);

        Button btnThree = ConfirmationDialogInfo.findViewById(R.id.button3);
        btnThree.setTypeface(typeface);
        btnThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnThree.setText("Cancel");
        //btnThree.setVisibility(View.VISIBLE);

        Button btnClose = ConfirmationDialogInfo.findViewById(R.id.closebtn);
        btnClose.setVisibility(View.VISIBLE);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.button1).getLayoutParams();
        lin.height = getheight(65);
        lin.width = getwidth(180);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.button2).getLayoutParams();
        lin.height = getheight(65);
        lin.width = getwidth(180);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.closebtn).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(60);
        lin.topMargin = getheight(-100);
        lin.rightMargin = getwidth(5);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.button3).getLayoutParams();
        lin.height = getheight(65);
        lin.width = getwidth(180);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.tvMessage).getLayoutParams();
        lin.topMargin = getheight(50);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.llButtons).getLayoutParams();
        lin.topMargin = getheight(50);

        message.setText(Message);

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                Music_Manager.buttonclick();
                Intent inprofile = new Intent(getApplication(), Coin_per.class);
                startActivity(inprofile);
                overridePendingTransition(android.R.anim.slide_in_left, 0);
                ConfirmationDialogInfo.dismiss();
                // finish();
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method


            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                Music_Manager.buttonclick();
                ConfirmationDialogInfo.dismiss();
            }
        });

        if (!isFinishing())
            ConfirmationDialogInfo.show();

    }
}
