package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ironsource.mediationsdk.IronSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hugo.weaving.DebugLog;
import utils.C;
import utils.Logger;
import utils.Parameters;
import utils.PreferenceManager;
import utils.ResponseCodes;
import utils.SQLiteManager;

public class Coin_per extends Activity implements OnClickListener {

    public static Handler handler;
    TextView title;
    C c = C.getInstance();
    String[] _id, Price;
    int[] coins;
    int Screen_Hight, Screen_Width;
    Animation buttonClick;
    Dialog dialog1, dialog2;
    SQLiteManager sqLiteManager;
    ImageView glow1, glow2, glow3, glow4;
    //=================================== new Changes ===============================================
    FrameLayout MainFrame, frm_6;
    TextView Title, txt_btn_buy6;
    ImageView Close, imgbg6, imgchips6;
    int[] btnID = {R.id.txt_btn_buy1, R.id.txt_btn_buy2, R.id.txt_btn_buy3, R.id.txt_btn_buy4, R.id.txt_btn_buy5};
    FrameLayout[] frm = new FrameLayout[5];
    ImageView imgbg1, imgbg2, imgbg3, imgbg4, imgbg5;
    TextView txtTitle1, txtTitle2, txtTitle3, txtTitle4, txtTitle5, txtTitle10, txtTitle20, txtTitle30, txtTitle40, txtTitle50;
    ImageView imgchips1, imgchips2, imgchips3, imgchips4, imgchips5;
    TextView[] btnBuy = new TextView[5];
    ImageView imgRemovead1, imgRemovead2, imgRemovead3, imgOfferWall;
    int[] ChipsValue = {5000000, 1500000, 500000, 100000, 0};
    String[] PriceValue = {"$11.99", "$5.99", "$3.49", "$1.49", "$3.49"};
    //=================================================================================================
    Animation HeartBeatAnimation;
    String path = "";
    String datastr = "";
    int indexfinal = -1;
    private InAppSku[] inAppSkus;
    private Dialog confirmationdialogAlert;
    private Dialog ConfirmationDialogInfo;

    private String clickedSku = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.buy_chips);
        sqLiteManager = new SQLiteManager(getApplicationContext());
        c.height = Screen_Hight = PreferenceManager.getheight();
        c.width = Screen_Width = PreferenceManager.getwidth();
        c.typeface = Typeface.createFromAsset(getAssets(), "font/PoetsenOne-Regular.ttf");
        Screen_Hight = c.height;
        Screen_Width = c.width;
        buttonClick = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buttonpressed);

        DefineIds();
        Getdata();

        initHandler();


    }

    private void GlowAnimation() {
        glow1 = findViewById(R.id.glow1);
        glow2 = findViewById(R.id.glow2);
        glow3 = findViewById(R.id.glow3);
        glow4 = findViewById(R.id.glow4);

        glow1.setVisibility(View.INVISIBLE);
        glow2.setVisibility(View.INVISIBLE);
        glow3.setVisibility(View.INVISIBLE);
        glow4.setVisibility(View.INVISIBLE);

    }

    private void DefineIds() {

        MainFrame = findViewById(R.id.main_frame);
        Title = findViewById(R.id.title);
        Close = findViewById(R.id.close);

        imgOfferWall = findViewById(R.id.offerWall);

        Title.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(45));

        Title.setTypeface(c.typeface);

        frm[0] = findViewById(R.id.frm_1);
        imgbg1 = findViewById(R.id.imgbg);
        txtTitle1 = findViewById(R.id.txt_title1);
        txtTitle10 = findViewById(R.id.txt_title10);
        imgchips1 = findViewById(R.id.imgchips1);
        imgRemovead1 = findViewById(R.id.imgRemovead1);

        frm[1] = findViewById(R.id.frm_2);
        imgbg2 = findViewById(R.id.imgbg2);
        txtTitle2 = findViewById(R.id.txt_title2);
        txtTitle20 = findViewById(R.id.txt_title20);
        imgchips2 = findViewById(R.id.imgchips2);
        imgRemovead2 = findViewById(R.id.imgRemovead2);

        frm[2] = findViewById(R.id.frm_3);
        imgbg3 = findViewById(R.id.imgbg3);
        txtTitle3 = findViewById(R.id.txt_title3);
        txtTitle30 = findViewById(R.id.txt_title30);
        imgchips3 = findViewById(R.id.imgchips3);

        frm[3] = findViewById(R.id.frm_4);
        imgbg4 = findViewById(R.id.imgbg4);
        txtTitle4 = findViewById(R.id.txt_title4);
        txtTitle40 = findViewById(R.id.txt_title40);
        imgchips4 = findViewById(R.id.imgchips4);

        frm[4] = findViewById(R.id.frm_5);
        imgbg5 = findViewById(R.id.imgbg5);
        txtTitle5 = findViewById(R.id.txt_title5);
        txtTitle50 = findViewById(R.id.txt_title50);
        imgchips5 = findViewById(R.id.imgchips5);
        imgRemovead3 = findViewById(R.id.imgRemovead3);

        for (int i = 0; i < 5; i++) {
            frm[i].setOnClickListener(this);
        }

        GlowAnimation();
        DrawScreen();

        Close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                finish();
                overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
            }
        });
    }

    private void DrawScreen() {

        final int screenwidth = this.getResources().getDisplayMetrics().widthPixels;
        final int screenheight = this.getResources().getDisplayMetrics().heightPixels;

        int width = screenwidth * 80 / 1280;
        int height = width * 70 / 80;
        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(width, height, Gravity.TOP | Gravity.RIGHT);
        fp.rightMargin = screenwidth * 18 / 1280;
        fp.topMargin = screenheight * 20 / 720;
        Close.setLayoutParams(fp);


        LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) findViewById(R.id.listfrm).getLayoutParams();
        ll.topMargin = screenheight * -15 / 720;

        width = screenwidth * 900 / 1280;
        height = width * 70 / 900;
        fp = new FrameLayout.LayoutParams(width, height, Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        fp.topMargin = screenheight * 20 / 720;
        Title.setLayoutParams(fp);


        ///////////////-------------------------------------------   NEW SCREEN DESIGN FOR VERSION 2 ----------------------------

        FrameLayout.LayoutParams flp;
        LinearLayout.LayoutParams llp;
        int tw = 0, th = 0;

        for (int i = 0; i < 5; i++) {
            btnBuy[i] = findViewById(btnID[i]);
            llp = (LinearLayout.LayoutParams) btnBuy[i].getLayoutParams();
            tw = (screenwidth * 180) / 1280;
            th = (tw * 70) / 180;
            llp.width = tw;
            llp.height = th;
            llp.topMargin = getheight(-15);
            llp.gravity = Gravity.CENTER_HORIZONTAL;
            btnBuy[i].setText("" + PriceValue[i]);
            btnBuy[i].setOnClickListener(this);
            btnBuy[i].setTypeface(c.typeface, Typeface.BOLD);
            btnBuy[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(25));

        }

        frm_6 = findViewById(R.id.frm_6);
        frm_6.setOnClickListener(this);

        imgbg6 = findViewById(R.id.imgbg6);
        imgbg6.setOnClickListener(this);

        imgchips6 = findViewById(R.id.imgchips6);
        imgchips6.setOnClickListener(this);

        txt_btn_buy6 = findViewById(R.id.txt_btn_buy6);
        txt_btn_buy6.setTypeface(c.typeface, Typeface.BOLD);
        txt_btn_buy6.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(25));
        txt_btn_buy6.setOnClickListener(this);

        llp = (LinearLayout.LayoutParams) findViewById(R.id.txt_btn_buy6).getLayoutParams();
        llp.width = (screenwidth * 180) / 1280;
        llp.height = (tw * 70) / 180;
        llp.topMargin = getheight(17);

        flp = (FrameLayout.LayoutParams) imgOfferWall.getLayoutParams();
        tw = (screenwidth * 171) / 1280;
        th = (tw * 129) / 171;
        flp.width = tw;
        flp.height = th;
        flp.gravity = Gravity.LEFT | Gravity.TOP;
        flp.topMargin = (screenheight * 30) / 720;
        flp.leftMargin = (screenwidth * 30) / 1280;
        imgOfferWall.setOnClickListener(this);


        llp = (LinearLayout.LayoutParams) frm[0].getLayoutParams();
        tw = (screenwidth * 275) / 1280;////275
        llp.width = tw;

        flp = (FrameLayout.LayoutParams) imgbg1.getLayoutParams();
        tw = (screenwidth * 221) / 1280;
        th = (tw * 240) / 221;
        flp.width = tw;
        flp.height = th;
        flp.gravity = Gravity.CENTER;

        llp = (LinearLayout.LayoutParams) txtTitle1.getLayoutParams();
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.topMargin = (screenheight * 85) / 720;

        llp = (LinearLayout.LayoutParams) txtTitle10.getLayoutParams();
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.topMargin = (screenheight * -40) / 720;

        FrameLayout.LayoutParams flps = (FrameLayout.LayoutParams) imgchips1.getLayoutParams();
        tw = (screenwidth * 150) / 1280;
        th = (tw * 140) / 150;
        flps.width = tw;
        flps.height = th;
        flps.gravity = Gravity.CENTER_HORIZONTAL;
        flps.topMargin = (screenheight * -10) / 720;
        glow1.setLayoutParams(flps);

        flp = (FrameLayout.LayoutParams) imgRemovead1.getLayoutParams();
        tw = (screenwidth * 198) / 1280;
        th = (tw * 62) / 198;
        flp.width = tw;
        flp.height = th;
        flp.gravity = Gravity.LEFT | Gravity.TOP;
        flp.topMargin = (screenheight * 45) / 720;

        /////////-----------------------

        llp = (LinearLayout.LayoutParams) frm[1].getLayoutParams();
        tw = (screenwidth * 275) / 1280;////275
        llp.width = tw;
        llp.leftMargin = (screenwidth * 20) / 1280;
        llp.rightMargin = (screenwidth * 20) / 1280;

        flp = (FrameLayout.LayoutParams) imgbg2.getLayoutParams();
        tw = (screenwidth * 221) / 1280;
        th = (tw * 240) / 221;
        flp.width = tw;
        flp.height = th;
        flp.gravity = Gravity.CENTER;

        llp = (LinearLayout.LayoutParams) txtTitle2.getLayoutParams();
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.topMargin = (screenheight * 85) / 720;

        llp = (LinearLayout.LayoutParams) txtTitle20.getLayoutParams();
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.topMargin = (screenheight * -40) / 720;

        flps = (FrameLayout.LayoutParams) imgchips2.getLayoutParams();
        tw = (screenwidth * 150) / 1280;
        th = (tw * 140) / 150;
        flps.width = tw;
        flps.height = th;
        flps.gravity = Gravity.CENTER_HORIZONTAL;
        flps.topMargin = (screenheight * -10) / 720;
        glow2.setLayoutParams(flps);

        flp = (FrameLayout.LayoutParams) imgRemovead2.getLayoutParams();
        tw = (screenwidth * 198) / 1280;
        th = (tw * 62) / 198;
        flp.width = tw;
        flp.height = th;
        flp.gravity = Gravity.LEFT | Gravity.TOP;
        flp.topMargin = (screenheight * 45) / 720;

        /////////-----------------------

        llp = (LinearLayout.LayoutParams) frm[2].getLayoutParams();
        tw = (screenwidth * 275) / 1280;////275
        llp.width = tw;

        flp = (FrameLayout.LayoutParams) imgbg3.getLayoutParams();
        tw = (screenwidth * 221) / 1280;
        th = (tw * 240) / 221;
        flp.width = tw;
        flp.height = th;
        flp.gravity = Gravity.CENTER;

        flp = (FrameLayout.LayoutParams) findViewById(R.id.imgbg6).getLayoutParams();
        tw = (screenwidth * 221) / 1280;
        th = (tw * 240) / 221;
        flp.width = tw;
        flp.height = th;
        flp.gravity = Gravity.CENTER;

        llp = (LinearLayout.LayoutParams) txtTitle3.getLayoutParams();
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.topMargin = (screenheight * 85) / 720;

        llp = (LinearLayout.LayoutParams) txtTitle30.getLayoutParams();
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.topMargin = (screenheight * -40) / 720;

        flps = (FrameLayout.LayoutParams) imgchips3.getLayoutParams();
        tw = (screenwidth * 150) / 1280;
        th = (tw * 140) / 150;
        flps.width = tw;
        flps.height = th;
        flps.gravity = Gravity.CENTER_HORIZONTAL;
        flps.topMargin = (screenheight * -10) / 720;
        glow3.setLayoutParams(flps);

        /////////-----------------------

        llp = (LinearLayout.LayoutParams) frm[3].getLayoutParams();
        tw = (screenwidth * 275) / 1280;////275
        llp.width = tw;

        llp = (LinearLayout.LayoutParams) findViewById(R.id.frm_6).getLayoutParams();
        tw = (screenwidth * 275) / 1280;////275
        llp.width = tw;

        flp = (FrameLayout.LayoutParams) imgbg4.getLayoutParams();
        tw = (screenwidth * 221) / 1280;
        th = (tw * 240) / 221;
        flp.width = tw;
        flp.height = th;
        flp.gravity = Gravity.CENTER;

        llp = (LinearLayout.LayoutParams) txtTitle4.getLayoutParams();
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.topMargin = (screenheight * 85) / 720;

        llp = (LinearLayout.LayoutParams) txtTitle40.getLayoutParams();
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.topMargin = (screenheight * -40) / 720;

        flps = (FrameLayout.LayoutParams) imgchips4.getLayoutParams();
        tw = (screenwidth * 150) / 1280;
        th = (tw * 140) / 150;
        flps.width = tw;
        flps.height = th;
        flps.gravity = Gravity.CENTER_HORIZONTAL;
        flps.topMargin = (screenheight * -10) / 720;
        glow4.setLayoutParams(flps);


        /////////-----------------------

        llp = (LinearLayout.LayoutParams) frm[4].getLayoutParams();
        tw = (screenwidth * 275) / 1280;////275
        llp.width = tw;
        llp.leftMargin = (screenwidth * 20) / 1280;

        flp = (FrameLayout.LayoutParams) imgbg5.getLayoutParams();
        tw = (screenwidth * 221) / 1280;
        th = (tw * 240) / 221;
        flp.width = tw;
        flp.height = th;
        flp.gravity = Gravity.CENTER;

        llp = (LinearLayout.LayoutParams) txtTitle5.getLayoutParams();
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.topMargin = (screenheight * 60) / 720;

        llp = (LinearLayout.LayoutParams) txtTitle50.getLayoutParams();
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.topMargin = (screenheight * 7) / 720;

        flps = (FrameLayout.LayoutParams) imgchips5.getLayoutParams();
        tw = (screenwidth * 140) / 1280;
        th = (tw * 120) / 140;
        flps.width = tw;
        flps.height = th;
        flps.gravity = Gravity.CENTER_HORIZONTAL;
        flps.topMargin = (screenheight * 95) / 720;

        llp = (LinearLayout.LayoutParams) findViewById(R.id.imgchips6).getLayoutParams();
        tw = (screenwidth * 140) / 1280;
        th = (tw * 120) / 140;
        llp.width = tw;
        llp.height = th;
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.topMargin = (screenheight * 85) / 720;


        flp = (FrameLayout.LayoutParams) imgRemovead3.getLayoutParams();
        tw = (screenwidth * 198) / 1280;
        th = (tw * 62) / 198;
        flp.width = tw;
        flp.height = th;
        flp.gravity = Gravity.LEFT | Gravity.TOP;
        flp.topMargin = (screenheight * 45) / 720;


        flp = (FrameLayout.LayoutParams) findViewById(R.id.imgRestore).getLayoutParams();
        tw = (screenwidth * 198) / 1280;
        th = (tw * 62) / 198;
        flp.width = tw;
        flp.height = th;
        flp.gravity = Gravity.LEFT | Gravity.TOP;
        flp.topMargin = (screenheight * 40) / 720;

        llp = (LinearLayout.LayoutParams) btnBuy[4].getLayoutParams();
        tw = (screenwidth * 180) / 1280;
        th = (tw * 70) / 180;
        llp.width = tw;
        llp.height = th;
        llp.topMargin = (screenheight * 20) / 720;
        llp.gravity = Gravity.CENTER_HORIZONTAL;

        txtTitle10.setText("" + c.formatter.format(ChipsValue[0]));
        txtTitle20.setText("" + c.formatter.format(ChipsValue[1]));
        txtTitle30.setText("" + c.formatter.format(ChipsValue[2]));
        txtTitle40.setText("" + c.formatter.format(ChipsValue[3]));
        txtTitle50.setText("");

        txtTitle1.setText("" + c.formatter.format(ChipsValue[0]));
        txtTitle2.setText("" + c.formatter.format(ChipsValue[1]));
        txtTitle3.setText("" + c.formatter.format(ChipsValue[2]));
        txtTitle4.setText("" + c.formatter.format(ChipsValue[3]));
        txtTitle5.setText("");

        txtTitle10.setVisibility(View.GONE);
        txtTitle20.setVisibility(View.GONE);
        txtTitle30.setVisibility(View.GONE);
        txtTitle40.setVisibility(View.GONE);
        txtTitle50.setVisibility(View.GONE);
        txtTitle5.setVisibility(View.GONE);

        txtTitle1.setTypeface(c.typeface, Typeface.BOLD);
        txtTitle2.setTypeface(c.typeface, Typeface.BOLD);
        txtTitle3.setTypeface(c.typeface, Typeface.BOLD);
        txtTitle4.setTypeface(c.typeface, Typeface.BOLD);
        txtTitle5.setTypeface(c.typeface, Typeface.BOLD);

        txtTitle10.setTypeface(c.typeface, Typeface.BOLD);
        txtTitle20.setTypeface(c.typeface, Typeface.BOLD);
        txtTitle30.setTypeface(c.typeface, Typeface.BOLD);
        txtTitle40.setTypeface(c.typeface, Typeface.BOLD);
        txtTitle50.setTypeface(c.typeface, Typeface.BOLD);

        txtTitle10.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(30));
        txtTitle20.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(30));
        txtTitle30.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(30));
        txtTitle40.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(30));
        txtTitle50.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(30));

        txtTitle1.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(30));
        txtTitle2.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(30));
        txtTitle3.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(30));
        txtTitle4.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(30));
        txtTitle5.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(30));


        HeartBeatAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.heartbeat);
        imgOfferWall.startAnimation(HeartBeatAnimation);
    }

    @DebugLog
    private void checkRestore() {

        if (PreferenceManager.getremoveAdPurchased()) {
            showInfoDialog("Restore", "Remove Ads Successfully");
            PreferenceManager.setisshowAd(false);
            PreferenceManager.SetQuestRemoveAdsCount(0);
            PreferenceManager.SetQuestRemoveAdsClaimCountLife(1);
        } else {
            showInfoDialog("Restore", "You have not purchased any Pack");
            PreferenceManager.setisshowAd(true);
        }
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
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO Auto-generated method stub

        super.onDestroy();


        if (dialog1 != null && dialog1.isShowing()) {
            dialog1.dismiss();
        }

        if (dialog2 != null && dialog2.isShowing()) {
            dialog2.dismiss();
        }

        if (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing()) {
            ConfirmationDialogInfo.dismiss();
        }
        recursiveLoopChildren(MainFrame);

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

    void alert(String messagestr) {

        LinearLayout.LayoutParams lin;
        if (confirmationdialogAlert != null || (confirmationdialogAlert != null && confirmationdialogAlert.isShowing())) {
            if (confirmationdialogAlert.isShowing()) {
                confirmationdialogAlert.dismiss();
            }
            confirmationdialogAlert = null;
        }

        confirmationdialogAlert = new Dialog(Coin_per.this,
                R.style.Theme_Transparent);
        confirmationdialogAlert
                .requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmationdialogAlert.setContentView(R.layout.dialog);
        confirmationdialogAlert.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView title = confirmationdialogAlert
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(c.typeface);
        title.setText("Purchase Failed!");

        TextView message1 = confirmationdialogAlert
                .findViewById(R.id.tvMessage);
        message1.setTypeface(c.typeface);
        message1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getwidth(32));

        Button btnOne = confirmationdialogAlert
                .findViewById(R.id.button1);
        btnOne.setTypeface(c.typeface);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("Watch Video");

        Button btnTwo = confirmationdialogAlert
                .findViewById(R.id.button2);
        btnTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnTwo.setText("Cancel");
        btnTwo.setVisibility(View.VISIBLE);

        lin = (LinearLayout.LayoutParams) confirmationdialogAlert
                .findViewById(R.id.button1).getLayoutParams();
        lin.height = getheight(70);
        lin.width = getwidth(180);

        lin = (LinearLayout.LayoutParams) confirmationdialogAlert
                .findViewById(R.id.tvMessage).getLayoutParams();
        lin.topMargin = getheight(30);

        lin = (LinearLayout.LayoutParams) confirmationdialogAlert
                .findViewById(R.id.llButtons).getLayoutParams();
        lin.topMargin = getheight(30);

        lin = (LinearLayout.LayoutParams) confirmationdialogAlert
                .findViewById(R.id.button2).getLayoutParams();
        lin.height = getheight(70);
        lin.width = getwidth(180);

        message1.setText("" + messagestr);

        btnTwo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Music_Manager.buttonclick();
                c.adDisplay = false;
                confirmationdialogAlert.dismiss();
            }

        });

        btnOne.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Music_Manager.buttonclick();
                confirmationdialogAlert.dismiss();
                if (PreferenceManager.isInternetConnected()) {
                    Message msg = new Message();
                    msg.what = ResponseCodes.VIDEOADS;
                    if (Dashboard.handler != null) {
                        Dashboard.handler.sendMessage(msg);
                    }
                    finish();
                } else {
                    showInfoDialog("No Internet", "Please check your Internet Connection.");
                }
            }

        });

        if (!isFinishing()) {
            confirmationdialogAlert.show();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub

        super.onResume();
        c.payment_Handler = handler;

        c.con = this;
        c.activity = Coin_per.this;

        c.contex = this;
        if (imgOfferWall.getAnimation() != null) {
            imgOfferWall.clearAnimation();
            HeartBeatAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.heartbeat);
            imgOfferWall.startAnimation(HeartBeatAnimation);
        }

        if (c.mMediationAgent != null) {
            IronSource.onResume(this);
        }
        Handler mainHandler = new Handler(this.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                //  Logger.print(">>>>>>>>>>>><>><><><><><><><><><>>11111resume1111<<><><<><><><><><><><><><<>");
                if (c.mMediationAgent != null) {
                    IronSource.getOfferwallCredits();

                }
            } // This is your code
        };
        mainHandler.post(myRunnable);


    }

    @Override
    public void onClick(View v) {

        if (v == txt_btn_buy6 || v == imgchips6 || v == imgbg6 || v == frm_6) {
            checkRestore();
        } else {

            for (int i = 0; i < btnBuy.length; i++) {

                if (v == btnBuy[i] || v == frm[i]) {
                    Music_Manager.buttonclick();

                    if (PreferenceManager.isInternetConnected()) {
                        int index = i;
                        try {
                            if (PreferenceManager.getremoveAdPurchased() && index == 4) {
                                alert("You have allready purchase this package.");
//                            showInfoDialog("Purchase Failed!", "You have allready purchase this package.");
                            } else {

                                try {

                                    JSONObject developerPayload = new JSONObject();
                                    developerPayload.put("_id", _id[index]);
                                    developerPayload.put("price", Price[index]);
                                    developerPayload.put(Parameters.isOfferPurchase, false);
                                    clickedSku = inAppSkus[index].getSkuId();
//                                    purchasePack(inAppSkus[index].getSkuId(), inAppSkus[index].isConsumable(), developerPayload.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        showInfoDialog("No Internet", "Please check your Internet Connection.");
                    }
                }
            }
        }
    }

    private void showInfoDialog(String DialogTitle, String Message) {
        // TODO Auto-generated method stub

        LinearLayout.LayoutParams lin;

        if (ConfirmationDialogInfo != null || (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing())) {
            if (ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.dismiss();
            }
            ConfirmationDialogInfo = null;
        }

        ConfirmationDialogInfo = new Dialog(Coin_per.this,
                R.style.Theme_Transparent);
        ConfirmationDialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialogInfo.setContentView(R.layout.dialog);
        ConfirmationDialogInfo.setCancelable(false);
        ConfirmationDialogInfo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView title = ConfirmationDialogInfo
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(c.typeface);
        title.setText(DialogTitle);

        TextView message = ConfirmationDialogInfo
                .findViewById(R.id.tvMessage);
        message.setTypeface(c.typeface);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

        Button btnOne = ConfirmationDialogInfo.findViewById(R.id.button1);
        btnOne.setTypeface(c.typeface);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("Ok");

        Button btnTwo = ConfirmationDialogInfo.findViewById(R.id.button2);
        btnTwo.setVisibility(View.GONE);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.button1).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(120);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.tvMessage).getLayoutParams();
        lin.topMargin = getheight(30);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.llButtons).getLayoutParams();
        lin.topMargin = getheight(30);

        message.setText(Message);

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                Music_Manager.buttonclick();
                // free_chip_logo.startAnimation(freechipanim);
                ConfirmationDialogInfo.dismiss();
            }
        });
        // ConfirmationDialogInfo.show();
        if (!isFinishing())
            ConfirmationDialogInfo.show();

    }

    @Override
    public void onBackPressed() {
//        finish();
//        overridePendingTransition(0, R.anim.activity_gone);
    }

    //todo >>>>>>>>>>>>> InApp Purchase <<<<<<<<<<<<<<<<<<

    private void initHandler() {
        // TODO Auto-generated method stub

        handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                // TODO Auto-generated method stub


                if (msg.what == ResponseCodes.ClickOncoinper) {

                    int index = msg.arg1;

                    try {
                        JSONObject developerPayload = new JSONObject();
                        developerPayload.put("_id", _id[index]);
                        developerPayload.put("price", Price[index]);
//                        purchasePack(inAppSkus[index].getSkuId(), inAppSkus[index].isConsumable(), developerPayload.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }

    private void Getdata() {

        // TODO Auto-generated method stub

        _id = new String[5];
        Price = new String[5];
        inAppSkus = new InAppSku[5];
        coins = new int[5];

        _id[0] = "0";
        _id[1] = "1";
        _id[2] = "2";
        _id[3] = "3";
        _id[4] = "4";

        Price[0] = "11.99";
        Price[1] = "5.99";
        Price[2] = "3.49";
        Price[3] = "1.49";
        Price[4] = "3.49";

        inAppSkus[0] = new InAppSku("ginrummyofflinechipspack4", true);
        inAppSkus[1] = new InAppSku("ginrummyofflinechipspack3", true);
        inAppSkus[2] = new InAppSku("ginrummyofflinechipspack2", true);
        inAppSkus[3] = new InAppSku("ginrummyofflinechipspack1", true);
        inAppSkus[4] = new InAppSku("ginrummyofflineremoveads", false);

        coins[0] = 5000000;
        coins[1] = 1500000;
        coins[2] = 500000;
        coins[3] = 100000;
        coins[4] = 0;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void enableUI() {
        // mane change karva vala par bharoso nathi etle condition mari
        if (frm != null) {
//            for (int i = 0; i < frm.length; i++) {
            for (FrameLayout aFrm : frm) {
                // upar kidhu em , nathi bharoso
                if (aFrm != null) {
                    aFrm.setEnabled(true);
                }
            }
        }
    }

    public void disableUI() {
        // mane change karva vala par bharoso nathi etle condition mari
        if (frm != null) {
            for (FrameLayout aFrm : frm) {
                // upar kidhu em , nathi bharoso
                if (aFrm != null) {
                    aFrm.setEnabled(false);
                }
            }
        }
    }

    class InAppSku {
        String skuId;
        boolean consumable;

        InAppSku(String skuId, boolean consumable) {
            this.skuId = skuId;
            this.consumable = consumable;
        }

        String getSkuId() {
            return skuId;
        }

        boolean isConsumable() {
            return consumable;
        }
    }

    //todo >>>>>>>>>>>>> InApp Purchase <<<<<<<<<<<<<<<<<<


}