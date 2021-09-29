package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utils.C;
import utils.Logger;
import utils.PreferenceManager;
import utils.ResponseCodes;

/**
 * Created by Artoon on 19-Jul-16.
 */
public class HowToPlay extends Activity {


    TextView title;
    FrameLayout close;
    ViewPager pager;
    ImageView[] dot = new ImageView[5];
    int Screen_Hight, Screen_Width;
    Typeface typeface;
    C c = C.getInstance();
    boolean isFirstTimeCome;
    ImageView prevarrow, nextarrow;
    Animation arrowAnimation;
    ImageView[] cards = new ImageView[11];
    ImageView ivHand;
    Handler animationHandler = new Handler();
    int[] cardDrawables = {R.drawable.f1, R.drawable.f2, R.drawable.c4, R.drawable.c5, R.drawable.l8, R.drawable.l9, R.drawable.l7, R.drawable.f3, R.drawable.c6, R.drawable.l1, R.drawable.l10};

    //Button btnLetsPlay;

    //    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//
//        Logger.print("MMMMMMMMMMMMMMM>>> onRestoreInstanceState ::::Called");
//
//        Logger.print("MMMMMMMMMMMMMMM>>> HowToPlay>>> Background issue Genrated 2222...");
//        finish();
//
//        Intent i = getBaseContext().getPackageManager().
//                getLaunchIntentForPackage(getBaseContext().getPackageName());
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);
//
//
//        super.onRestoreInstanceState(savedInstanceState);
//    }
    int[] cardDrawables1 = {R.drawable.f_b_1, R.drawable.f_b_2, R.drawable.f_b_3, R.drawable.c4, R.drawable.c5, R.drawable.l8, R.drawable.l9, R.drawable.l1, R.drawable.c6, R.drawable.l7, R.drawable.l10};
    int[] cardDrawables2 = {R.drawable.f_b_1, R.drawable.f_b_2, R.drawable.f_b_3, R.drawable.c_g_4, R.drawable.c_g_5, R.drawable.c_g_6, R.drawable.l8, R.drawable.l9, R.drawable.l1, R.drawable.l7, R.drawable.l10};
    int[] cardDrawables3 = {R.drawable.f_b_1, R.drawable.f_b_2, R.drawable.f_b_3, R.drawable.c_g_4, R.drawable.c_g_5, R.drawable.c_g_6, R.drawable.l8_1, R.drawable.l9_1, R.drawable.l10_1, R.drawable.l1, R.drawable.l7};
    String[] userCards = {"f-1", "f-2", "c-4", "c-5", "l-8", "l-9", "l-10", "f-3", "c-6", "l-1", "l-7"};
    TranslateAnimation translateAnimation, translateAnimation1, translateAnimation2;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howtoplaynew);


        initHandler();

        c.height = Screen_Hight = PreferenceManager.getheight();
        c.width = Screen_Width = PreferenceManager.getwidth();
        c.typeface = Typeface.createFromAsset(getAssets(), "font/PoetsenOne-Regular.ttf");

        Screen_Hight = c.height;
        Screen_Width = c.width;

        arrowAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.heartbeat);
        typeface = Typeface.createFromAsset(getAssets(),
                "font/PoetsenOne-Regular.ttf");
        setScreen();
        title = findViewById(R.id.activity_help_ctitle);
        title.setTypeface(typeface);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));

        close = findViewById(R.id.activity_help_closebtn);
        pager = findViewById(R.id.activity_help_pager);

        isFirstTimeCome = getIntent().getBooleanExtra("isFirstTime", false);
        //isFirstTimeCome = true;
        c.isShowTutorial = false;

        if (PreferenceManager.get_FirstTime()) {
            PreferenceManager.set_FirstTime(false);
            close.setVisibility(View.GONE);
        }

        prevarrow = findViewById(R.id.prevarrow);
        prevarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                pager.setCurrentItem(getItem(-1), true);

            }
        });
        nextarrow = findViewById(R.id.nextarrow);
        nextarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                pager.setCurrentItem(getItem(+1), true);
            }
        });

        dot[0] = findViewById(R.id.help_dot_1);
        dot[1] = findViewById(R.id.help_dot_2);
        dot[2] = findViewById(R.id.help_dot_3);
        dot[3] = findViewById(R.id.help_dot_4);
        dot[4] = findViewById(R.id.help_dot_5);


        if (isFirstTimeCome) {
            close.setVisibility(View.INVISIBLE);
            nextarrow.setVisibility(View.VISIBLE);
            prevarrow.setVisibility(View.GONE);
            nextarrow.startAnimation(arrowAnimation);
        } else {
            //nextarrow.setVisibility(View.GONE);
            // prevarrow.setVisibility(View.GONE);
        }
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Music_Manager.buttonclick();
                finish();
                overridePendingTransition(0, android.R.anim.slide_out_right);

            }
        });
        ChangeDotBackground(0);

        pager.setAdapter(new HelpAdapter());

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                if (isFirstTimeCome) {
                    close.setVisibility(View.INVISIBLE);
                    if (position == 4) {
                        if (nextarrow.getAnimation() != null) {
                            nextarrow.clearAnimation();
                        }
                        nextarrow.setVisibility(View.GONE);

                        if (prevarrow.getAnimation() != null) {
                            prevarrow.clearAnimation();
                        }
                       /* btnLetsPlay.setVisibility(View.VISIBLE);
                        btnLetsPlay.startAnimation(arrowAnimation);*/
                    } else {
                        nextarrow.setVisibility(View.VISIBLE);
                        nextarrow.startAnimation(arrowAnimation);
                    }

                    if (position == 0) {
                        if (prevarrow.getAnimation() != null) {
                            prevarrow.clearAnimation();
                        }
                        prevarrow.setVisibility(View.GONE);
                    } else {
                        prevarrow.setVisibility(View.VISIBLE);
                        prevarrow.startAnimation(arrowAnimation);
                        if (position == 4) {
                            if (prevarrow.getAnimation() != null) {
                                prevarrow.clearAnimation();
                            }
                        }
                    }
                }

                if (position == 1) {

                    if (animationHandler != null) {
                        animationHandler.removeCallbacksAndMessages(null);
                        animationHandler = null;
                    }
                    translateAnimation = null;
                    translateAnimation1 = null;
                    translateAnimation2 = null;
                    animationHandler = new Handler();
                    try {
                        for (int i = 0; i < cards.length; i++) {
                            Logger.print("cards set " + i);
                            cards[i].setBackgroundResource(cardDrawables[i]);
                            if (cards[i].getAnimation() != null)
                                cards[i].clearAnimation();
                            /*cards[i].setVisibility(View.INVISIBLE);
                            cards[i].setVisibility(View.VISIBLE);*/
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (ivHand != null && ivHand.getAnimation() != null) {
                        ivHand.clearAnimation();
                        ivHand.setVisibility(View.INVISIBLE);

                    }
                    animationHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            animateCards();
                        }
                    }, 2000);
                }
                ChangeDotBackground(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void initHandler() {

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        c.payment_Handler = this.handler;

        c.con = this;
        c.activity = HowToPlay.this;

    }

    private int getItem(int i) {
        return pager.getCurrentItem() + i;
    }

    private int getheight(int val) {
        // TODO Auto-generated method stub
        return Screen_Hight * val / 720;
    }

    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return Screen_Width * val / 1280;
    }

    private void setScreen() {
        // TODO Auto-generated method stub
        FrameLayout.LayoutParams frm = null;
        LinearLayout.LayoutParams lin = null;
        RelativeLayout.LayoutParams rel = null;

//        frm = (FrameLayout.LayoutParams) findViewById(R.id.help_bk)
//                .getLayoutParams();
//        frm.width = getwidth(1280);
//        frm.height = getheight(720);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.prevarrow)
                .getLayoutParams();
        frm.width = getwidth((int) (40 * 1.2));
        frm.height = getheight((int) (100 * 1.2));
        frm.leftMargin = getwidth(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.nextarrow)
                .getLayoutParams();
        frm.width = getwidth((int) (40 * 1.2));
        frm.height = getheight((int) (100 * 1.2));
        frm.rightMargin = getwidth(10);

        lin = (LinearLayout.LayoutParams) findViewById(
                R.id.activity_help_ctitle).getLayoutParams();
        lin.topMargin = getheight(20);

        frm = (FrameLayout.LayoutParams) findViewById(
                R.id.activity_help_closebtn).getLayoutParams();
        frm.width = getwidth(81);
        frm.height = getwidth(81);
        frm.topMargin = getheight(20);
        frm.rightMargin = getwidth(20);

        /*frm = (FrameLayout.LayoutParams) findViewById(R.id.help_frm1)
                .getLayoutParams();
        frm.topMargin = getheight(20);*/

       /* frm = (FrameLayout.LayoutParams) findViewById(R.id.help_lin1)
                .getLayoutParams();
        frm.rightMargin = getwidth(10);
        frm.topMargin = getheight(40);
*/
        FrameLayout frm1 = findViewById(R.id.help_frm2);
        frm1.setPadding(getwidth(30), 0, getwidth(30), getheight(10));

        frm = (FrameLayout.LayoutParams) findViewById(R.id.activity_help_pager)
                .getLayoutParams();
        frm.height = getheight(630);
        frm.width = getwidth(1280);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.help_lin2)
                .getLayoutParams();
        lin.topMargin = getheight(20);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.help_dot_1)
                .getLayoutParams();
        lin.height = getwidth(30);
        lin.width = getwidth(30);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.help_dot_2)
                .getLayoutParams();
        lin.height = getwidth(30);
        lin.width = getwidth(30);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.help_dot_3)
                .getLayoutParams();
        lin.height = getwidth(30);
        lin.width = getwidth(30);


        lin = (LinearLayout.LayoutParams) findViewById(R.id.help_dot_4)
                .getLayoutParams();
        lin.height = getwidth(30);
        lin.width = getwidth(30);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.help_dot_5)
                .getLayoutParams();
        lin.height = getwidth(30);
        lin.width = getwidth(30);

    }

    private void setScreenForViewPager(int resId, View view) {
        // TODO Auto-generated method stub

        LinearLayout.LayoutParams lin = null;
        FrameLayout.LayoutParams frm;
        int w, h;

        if (resId == R.layout.howtoplay1) {

            TextView tvTitle = view.findViewById(R.id.tvTitle);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
            tvTitle.setPadding(0, 0, 0, getheight(14));
            tvTitle.setTypeface(typeface, Typeface.BOLD);

            lin = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
            lin.width = getwidth(400);

            TextView basicText = view.findViewById(R.id.tvBasicText);
            basicText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
            basicText.setTypeface(typeface, Typeface.BOLD);
            basicText.setText((Html.fromHtml("<span><font color='yellow'>Goal :</font> Arrange all your cards in sets and sequences of 3 or more cards to win.</span>")));

            lin = (LinearLayout.LayoutParams) basicText.getLayoutParams();
            lin.topMargin = getheight(20);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llRule1Container).getLayoutParams();
            lin.topMargin = getheight(10);

            w = getwidth(30);
            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivStar1).getLayoutParams();
            lin.width = lin.height = w;
            lin.rightMargin = w * 10 / 30;

            TextView tvRule1 = view.findViewById(R.id.tvRule1);
            tvRule1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
            tvRule1.setTypeface(typeface, Typeface.BOLD);


            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llRule2Container).getLayoutParams();
            lin.topMargin = getheight(10);
            lin.bottomMargin = getheight(10);

            w = getwidth(30);
            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivStar2).getLayoutParams();
            lin.width = lin.height = w;
            lin.rightMargin = w * 10 / 30;

            TextView tvRule2 = view.findViewById(R.id.tvRule2);
            tvRule2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
            tvRule2.setTypeface(typeface, Typeface.BOLD);

            TextView tvSequenceText = view.findViewById(R.id.tvSequenceText);
            tvSequenceText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
            tvSequenceText.setTypeface(typeface, Typeface.BOLD);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llSequenceCardContainer).getLayoutParams();
            lin.topMargin = getheight(20);
            lin.bottomMargin = getheight(20);

            w = getwidth(84);
            h = w * 114 / 84;
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard1).getLayoutParams();
            frm.width = w;
            frm.height = h;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard2).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 46 / 84;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard3).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 92 / 84;

            w = getwidth(60);
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivValidSign1).getLayoutParams();
            frm.width = w;
            frm.height = w;
            frm.leftMargin = w * -20 / 60;
            frm.bottomMargin = w * -20 / 60;

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.frmSecondSeqContainer).getLayoutParams();
            lin.leftMargin = getheight(40);

            w = getwidth(84);
            h = w * 114 / 84;
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard4).getLayoutParams();
            frm.width = w;
            frm.height = h;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard5).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 46 / 84;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard6).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 92 / 84;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard7).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 138 / 84;

            w = getwidth(60);
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivValidSign2).getLayoutParams();
            frm.width = w;
            frm.height = w;
            frm.leftMargin = w * -20 / 60;
            frm.bottomMargin = w * -20 / 60;

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.frmThirdSeqContainer).getLayoutParams();
            lin.leftMargin = getheight(40);

            w = getwidth(84);
            h = w * 114 / 84;
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard8).getLayoutParams();
            frm.width = w;
            frm.height = h;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard9).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 46 / 84;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard10).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 92 / 84;

            w = getwidth(60);
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivValidSign3).getLayoutParams();
            frm.width = w;
            frm.height = w;
            frm.leftMargin = w * -20 / 60;
            frm.bottomMargin = w * -20 / 60;

            TextView tvSetsText = view.findViewById(R.id.tvSetsText);
            tvSetsText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
            tvSetsText.setTypeface(typeface, Typeface.BOLD);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.tvSetsText).getLayoutParams();
            lin.topMargin = getheight(10);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llSetCardContainer).getLayoutParams();
            lin.topMargin = getheight(20);
            lin.bottomMargin = getheight(20);

            w = getwidth(84);
            h = w * 114 / 84;
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard11).getLayoutParams();
            frm.width = w;
            frm.height = h;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard12).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 46 / 84;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard13).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 92 / 84;

            w = getwidth(60);
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivValidSign4).getLayoutParams();
            frm.width = w;
            frm.height = w;
            frm.leftMargin = w * -20 / 60;
            frm.bottomMargin = w * -20 / 60;

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.frmSetOneContainer).getLayoutParams();
            lin.leftMargin = getheight(40);

            w = getwidth(84);
            h = w * 114 / 84;
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard14).getLayoutParams();
            frm.width = w;
            frm.height = h;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard15).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 46 / 84;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard16).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 92 / 84;

            w = getwidth(60);
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivValidSign5).getLayoutParams();
            frm.width = w;
            frm.height = w;
            frm.leftMargin = w * -20 / 60;
            frm.bottomMargin = w * -20 / 60;

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.frmSetTwoContainer).getLayoutParams();
            lin.leftMargin = getheight(40);

            w = getwidth(84);
            h = w * 114 / 84;
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard18).getLayoutParams();
            frm.width = w;
            frm.height = h;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard19).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 46 / 84;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivCard20).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 92 / 84;

            w = getwidth(60);
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivValidSign6).getLayoutParams();
            frm.width = w;
            frm.height = w;
            frm.leftMargin = w * -20 / 60;
            frm.bottomMargin = w * -20 / 60;

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivSubHeadSep2).getLayoutParams();
            lin.width = getwidth(320);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivSubHeadSep1).getLayoutParams();
            lin.width = getwidth(320);


        } else if (resId == R.layout.howtoplay2) {

            TextView tvTitle = view.findViewById(R.id.tvTitle);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
            tvTitle.setPadding(0, 0, 0, getheight(14));
            tvTitle.setTypeface(typeface, Typeface.BOLD);

            lin = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
            lin.width = getwidth(500);

            TextView tvHowToGroupDesc = view.findViewById(R.id.tvHowToGroupDesc);
            tvHowToGroupDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
            tvHowToGroupDesc.setTypeface(typeface, Typeface.BOLD);
            tvHowToGroupDesc.setText((Html.fromHtml("<span><font color='yellow'>How to Group :</font> Tap and hold the selected card you want to group,<br/>drag and drop besides the respective cards.</span>")));

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.frmCards).getLayoutParams();
            lin.topMargin = getheight(140);

            w = getwidth(120);
            h = w * 160 / 120;
            int leftMargin = 0;
            for (int i = 1; i <= 11; i++) {
                cards[i - 1] = view.findViewById(getResources().getIdentifier("ivCard" + i, "id", getPackageName()));
                cards[i - 1].setBackgroundResource(cardDrawables[i - 1]);
                cards[i - 1].setTag(cardDrawables[i - 1]);
                frm = (FrameLayout.LayoutParams) cards[i - 1].getLayoutParams();
                frm.width = w;
                frm.height = h;
                frm.leftMargin = getwidth(leftMargin);
                leftMargin += 60;
            }

            w = getwidth(90);
            h = w * 80 / 90;
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.ivHand).getLayoutParams();
            frm.width = w;
            frm.height = h;

            ivHand = view.findViewById(R.id.ivHand);
            ivHand.setVisibility(View.INVISIBLE);

            TextView tvAutoSortDesc = view.findViewById(R.id.tvAutoSortDesc);
            tvAutoSortDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
            tvAutoSortDesc.setTypeface(typeface, Typeface.BOLD);

            lin = (LinearLayout.LayoutParams) tvAutoSortDesc.getLayoutParams();
            lin.topMargin = getheight(40);

        } else if (resId == R.layout.howtoplay3) {

            TextView tvTitle = view.findViewById(R.id.tvTitle);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
            tvTitle.setPadding(0, 0, 0, getheight(14));
            tvTitle.setTypeface(typeface, Typeface.BOLD);

            lin = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
            lin.width = getwidth(500);

            TextView tvKnockDesc = view.findViewById(R.id.tvKnockDesc);
            tvKnockDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
            tvKnockDesc.setTypeface(typeface, Typeface.BOLD);
            tvKnockDesc.setText((Html.fromHtml("<span><font color='yellow'>Knock :</font> Players have KNOCK button only when sum of cards (Deadwood) are less than 11</span>")));

            Button btnknock = view.findViewById(R.id.btnKnock);
            btnknock.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
            btnknock.setTypeface(typeface, Typeface.BOLD);

            w = getwidth(240);
            h = w * 80 / 240;
            lin = (LinearLayout.LayoutParams) btnknock.getLayoutParams();
            lin.width = w;
            lin.height = h;
            lin.topMargin = h * 20 / 80;

            w = getwidth(340);
            h = w * 200 / 340;
            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llDeadWoodContainer).getLayoutParams();
            lin.width = w;
            lin.height = h;
            lin.topMargin = h * 20 / 200;

            TextView tvDeadwoodtext = view.findViewById(R.id.tvDeadwoodtext);
            tvDeadwoodtext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(26));
            tvDeadwoodtext.setTypeface(typeface, Typeface.BOLD);

            TextView tvDeadwoodPoints = view.findViewById(R.id.tvDeadwoodPoints);
            tvDeadwoodPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));
            tvDeadwoodPoints.setTypeface(typeface, Typeface.BOLD);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.frmCards).getLayoutParams();
            lin.topMargin = getheight(20);

            w = getwidth(120);
            h = w * 160 / 120;
            int leftMargin = 0;
            for (int i = 1; i <= 11; i++) {
                frm = (FrameLayout.LayoutParams) view.findViewById(getResources().getIdentifier("ivCard" + i, "id", getPackageName())).getLayoutParams();
                frm.width = w;
                frm.height = h;
                frm.leftMargin = getwidth(leftMargin);
                leftMargin += 60;
            }


        } else if (resId == R.layout.howtoplay4) {

            TextView tvTitle = view.findViewById(R.id.tvTitle);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
            tvTitle.setPadding(0, 0, 0, getheight(14));
            tvTitle.setTypeface(typeface, Typeface.BOLD);

            lin = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
            lin.width = getwidth(500);

            TextView tvKnockDesc = view.findViewById(R.id.tvKnockDesc);
            tvKnockDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
            tvKnockDesc.setTypeface(typeface, Typeface.BOLD);
            tvKnockDesc.setText((Html.fromHtml("<span><font color='yellow'>Gin :</font> Players have GIN button only when sum of cards is zero.</span>")));

            Button btnGin = view.findViewById(R.id.btnGin);
            btnGin.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));
            btnGin.setTypeface(typeface, Typeface.BOLD);

            w = getwidth(220);
            h = w * 80 / 220;
            lin = (LinearLayout.LayoutParams) btnGin.getLayoutParams();
            lin.width = w;
            lin.height = h;
            lin.topMargin = h * 20 / 80;

            w = getwidth(260);
            h = w * 150 / 260;
            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llDeadWoodContainer).getLayoutParams();
            lin.width = w;
            lin.height = h;
            lin.topMargin = h * 20 / 150;

            TextView tvDeadwoodtext = view.findViewById(R.id.tvDeadwoodtext);
            tvDeadwoodtext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
            tvDeadwoodtext.setTypeface(typeface, Typeface.BOLD);

            TextView tvDeadwoodPoints = view.findViewById(R.id.tvDeadwoodPoints);
            tvDeadwoodPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
            tvDeadwoodPoints.setTypeface(typeface, Typeface.BOLD);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.frmCards).getLayoutParams();
            lin.topMargin = getheight(20);

            w = getwidth(120);
            h = w * 160 / 120;
            int leftMargin = 0;
            for (int i = 1; i <= 11; i++) {
                frm = (FrameLayout.LayoutParams) view.findViewById(getResources().getIdentifier("ivCard" + i, "id", getPackageName())).getLayoutParams();
                frm.width = w;
                frm.height = h;
                frm.leftMargin = getwidth(leftMargin);
                leftMargin += 60;
            }

            /*btnLetsPlay = (Button) view.findViewById(R.id.btnLetsPlay);
            btnLetsPlay.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
            btnLetsPlay.setTypeface(typeface, Typeface.BOLD);
            btnLetsPlay.setVisibility(View.GONE);
            btnLetsPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    *//*if (Dashboard.handler != null) {
                        Message msg = new Message();
                        msg.what = ResponseCodes.ShowPreloader;
                        Dashboard.handler.sendMessage(msg);
                    }*//*
                    finish();
                    overridePendingTransition(0, android.R.anim.slide_out_right);
                }
            });

            w = getwidth(240);
            h = w * 80 / 240;
            lin = (LinearLayout.LayoutParams) btnLetsPlay.getLayoutParams();
            lin.width = w;
            lin.height = h;
            lin.topMargin = h * 10 / 80;*/

        } else if (resId == R.layout.howtoplay5) {

            TextView activity_title, game_text1, game_text2, game_text3, game_text11, game_text22, game_text111;
            final Button play_btn;
            ImageView hand;


            Animation handAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.handpointer2);

            activity_title = view.findViewById(R.id.activity_help_ctitle);
            game_text1 = view.findViewById(R.id.game_text1);
            game_text2 = view.findViewById(R.id.game_text2);
            game_text3 = view.findViewById(R.id.game_text3);
            game_text11 = view.findViewById(R.id.game_text11);
            game_text22 = view.findViewById(R.id.game_text22);
            game_text111 = view.findViewById(R.id.game_text111);
            play_btn = view.findViewById(R.id.play_btn);
            hand = view.findViewById(R.id.ivHandOpenCard2);


            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.activity_help_ctitle).getLayoutParams();
            frm.topMargin = getheight(20);
            frm.width = getwidth(440);
            frm.height = getheight(64);

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.line31).getLayoutParams();
            frm.topMargin = getheight(100);

            activity_title.setText("Let's Play");
            activity_title.setTypeface(typeface);
            activity_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));
            activity_title.setPadding(0, 0, 0, getheight(10));
            game_text1.setTypeface(typeface);
            game_text1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));
            game_text2.setTypeface(typeface);
            game_text2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));
            game_text3.setTypeface(typeface);
            game_text3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));
            game_text11.setTypeface(typeface);
            game_text11.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));
            game_text22.setTypeface(typeface);
            game_text22.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));

            game_text111.setTypeface(typeface);
            game_text111.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(22));

            play_btn.setTypeface(typeface);
            play_btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));

            lin = (LinearLayout.LayoutParams) game_text3.getLayoutParams();
            lin.topMargin = getheight(10);

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.help_bk).getLayoutParams();
            frm.height = getheight(720);
            frm.width = getwidth(1280);

            int w4 = getwidth(440);
            int h4 = w4 * 425 / 440;
            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.help_bk1).getLayoutParams();
            frm.height = w4;
            frm.width = h4;
            frm.leftMargin = w4 * 200 / 440;

            w = c.width * 400 / 1280;
            frm = new FrameLayout.LayoutParams(w, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            frm.topMargin = getheight(10);
            activity_title.setLayoutParams(frm);

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.line32).getLayoutParams();
            frm.rightMargin = getwidth(240);
            frm.bottomMargin = getheight(160);

            w4 = getwidth(250);
            h4 = w4 * 75 / 250;
            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.play_btn).getLayoutParams();
            lin.topMargin = getheight(20);
            lin.height = h4;
            lin.width = w4;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.game_text111).getLayoutParams();
            frm.leftMargin = getwidth(680);
            frm.bottomMargin = getheight(100);

            // frm = (FrameLayout.LayoutParams) findViewById(R.id.ivHandOpenCard)
            //       .getLayoutParams();
            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivHandOpenCard2).getLayoutParams();
            lin.width = getwidth(80);
            lin.height = getwidth(80);
            lin.rightMargin = getwidth(10);
            lin.topMargin = getheight(-30);


//            if (play_btn.getAnimation() == null)
//                play_btn.startAnimation(arrowAnimation);

            if (hand.getAnimation() == null)
                hand.startAnimation(handAnimation);

            play_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    play_btn.clearAnimation();
                    Music_Manager.buttonclick();
                    if (Dashboard.handler != null) {
                        Message msg = new Message();
                        msg.what = ResponseCodes.PlayFromHoeToPlay;
                        Dashboard.handler.sendMessage(msg);
                    }
                    overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
                    finish();
                }
            });

        }
    }

    private void animateCards() {

        final int[] cardLocation = new int[2];
        cards[7].getLocationOnScreen(cardLocation);

        int x = cardLocation[0];
        int y = cardLocation[1];

        Logger.print("Coordinates of HAND => " + x + " " + y);
        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) ivHand.getLayoutParams();
        frm.leftMargin = x + getwidth(30);
        frm.topMargin = y - getheight(40);
        ivHand.setLayoutParams(frm);


        translateAnimation = new TranslateAnimation(0, getwidth(-330), 0, 0);
        translateAnimation.setDuration(2000);
        translateAnimation.setFillAfter(false);
        if (translateAnimation != null) {
            cards[7].startAnimation(translateAnimation);
            ivHand.setVisibility(View.VISIBLE);
            ivHand.startAnimation(translateAnimation);
        }

        translateAnimation1 = new TranslateAnimation(0, getwidth(-210), 0, 0);
        translateAnimation1.setDuration(2000);
        translateAnimation1.setFillAfter(false);

        translateAnimation2 = new TranslateAnimation(0, getwidth(-140), 0, 0);
        translateAnimation2.setDuration(2000);
        translateAnimation2.setFillAfter(false);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (cards[7].getAnimation() != null) {
                    cards[7].clearAnimation();
                }

                if (ivHand.getAnimation() != null) {
                    ivHand.clearAnimation();
                }
                ivHand.setVisibility(View.INVISIBLE);

                if (translateAnimation1 != null) {
                    for (int i = 0; i < cards.length; i++) {
                        cards[i].setBackgroundResource(cardDrawables1[i]);
                    }
                    ivHand.setVisibility(View.VISIBLE);
                    cards[8].getLocationOnScreen(cardLocation);
                    int xx = cardLocation[0];
                    int yy = cardLocation[1];

                    FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) ivHand.getLayoutParams();
                    frm.leftMargin = xx + getwidth(30);
                    frm.topMargin = yy - getheight(40);
                    ivHand.setLayoutParams(frm);
                    cards[8].startAnimation(translateAnimation1);
                    ivHand.startAnimation(translateAnimation1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        translateAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (cards[8].getAnimation() != null) {
                    cards[8].clearAnimation();
                }

                if (ivHand.getAnimation() != null) {
                    ivHand.clearAnimation();
                }
                ivHand.setVisibility(View.INVISIBLE);

                if (translateAnimation2 != null) {
                    for (int i = 0; i < cards.length; i++) {
                        cards[i].setBackgroundResource(cardDrawables2[i]);
                    }
                    ivHand.setVisibility(View.VISIBLE);
                    cards[10].getLocationOnScreen(cardLocation);
                    int xx = cardLocation[0];
                    int yy = cardLocation[1];

                    FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) ivHand.getLayoutParams();
                    frm.leftMargin = xx + getwidth(30);
                    frm.topMargin = yy - getheight(40);
                    ivHand.setLayoutParams(frm);

                    cards[10].startAnimation(translateAnimation2);
                    ivHand.startAnimation(translateAnimation2);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        translateAnimation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (cards[10].getAnimation() != null) {
                    cards[10].clearAnimation();
                }

                if (ivHand.getAnimation() != null) {
                    ivHand.clearAnimation();
                }
                ivHand.setVisibility(View.INVISIBLE);

                if (animationHandler != null) {
                    for (int i = 0; i < cards.length; i++) {
                        cards[i].setBackgroundResource(cardDrawables3[i]);
                    }
                    animationHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i < cards.length; i++) {
                                    cards[i].setBackgroundResource(cardDrawables[i]);
                                }
                                cards[7].getLocationOnScreen(cardLocation);
                                int xx = cardLocation[0];
                                int yy = cardLocation[1];
                                FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) ivHand.getLayoutParams();
                                frm.leftMargin = xx + getwidth(30);
                                frm.topMargin = yy - getheight(40);
                                ivHand.setLayoutParams(frm);
                                cards[7].startAnimation(translateAnimation);
                                ivHand.startAnimation(translateAnimation);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 2000);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void ChangeDotBackground(int index) {
        for (int i = 0; i < dot.length; i++) {
            if (i == index) {
                dot[index].setBackgroundResource(R.drawable.dot);
                prevarrow.setVisibility(View.VISIBLE);
                nextarrow.setVisibility(View.VISIBLE);
                if (index == 0) {
                    prevarrow.setVisibility(View.GONE);
                    nextarrow.setVisibility(View.VISIBLE);

                } else if (index == (dot.length - 1)) {
                    prevarrow.setVisibility(View.VISIBLE);
                    nextarrow.setVisibility(View.GONE);

                }
            } else {
                dot[i].setBackgroundResource(R.drawable.dotdis);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private class HelpAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            LayoutInflater inflater = (LayoutInflater) container.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            int resId = 0;

            switch (position) {
                case 0:
                    resId = R.layout.howtoplay1;
                    break;

                case 1:
                    resId = R.layout.howtoplay2;
                    break;

                case 2:
                    resId = R.layout.howtoplay3;
                    break;

                case 3:
                    resId = R.layout.howtoplay4;
                    break;

                case 4:
                    resId = R.layout.howtoplay5;
                    break;

                default:
                    resId = R.layout.howtoplay1;
                    break;
            }

            View view = inflater.inflate(resId, null);

            setScreenForViewPager(resId, view);

            if (container != null)
                container.addView(view, 0);

            return view;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);

        }
    }


}
