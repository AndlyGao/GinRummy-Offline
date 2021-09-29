package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import hugo.weaving.DebugLog;
import utils.C;
import utils.PreferenceManager;

/**
 * Created by milin.patel on 6/8/2017.
 */

public class ActivityRateUs extends Activity implements View.OnClickListener {

    FrameLayout frm_popup;
    TextView tv_tittle, tv_rateus_text;
    Button btn_close;
    TextView tv_text5, tv_text4, tv_text3, tv_text2, tv_text1;
    ImageView iv_star_one_five, iv_star_two_five, iv_star_three_five, iv_star_four_five, iv_star_five_five;
    ImageView iv_star_one_four, iv_star_two_four, iv_star_three_four, iv_star_four_four;
    ImageView iv_star_one_three, iv_star_two_three, iv_star_three_three;
    ImageView iv_star_one_two, iv_star_two_two;
    ImageView iv_star_one_one;
    LinearLayout lnr_five_star, lnr_four_star, lnr_three_star, lnr_two_star, lnr_one_star;
    Button btn_submit, btn_feedback;


    int Screen_Hight, Screen_Width;
    C c = C.getInstance();
    boolean isRateGive = false;
    boolean isFromWinner = false;
    private boolean isPlayStore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateus);
        Screen_Hight = c.height;
        Screen_Width = c.width;

        try {
            isFromWinner = getIntent().getBooleanExtra("isFromWinner", false);
        } catch (Exception e) {
            e.printStackTrace();
            isFromWinner = false;
        }

        FindViewById();
        LayOutScreen();


    }

    private void FindViewById() {

        //==== tittle and close ===

        tv_tittle = findViewById(R.id.tv_tittle);
        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);

        //====== Description text =======
        tv_rateus_text = findViewById(R.id.tv_rateus_text);

        //====  Review Text ===
        tv_text5 = findViewById(R.id.tv_text5);
        tv_text4 = findViewById(R.id.tv_text4);
        tv_text3 = findViewById(R.id.tv_text3);
        tv_text2 = findViewById(R.id.tv_text2);
        tv_text1 = findViewById(R.id.tv_text1);

        //==== Star forGround ===

        //====== Star First item ====
        iv_star_one_five = findViewById(R.id.iv_star_one_five);
        iv_star_two_five = findViewById(R.id.iv_star_two_five);
        iv_star_three_five = findViewById(R.id.iv_star_three_five);
        iv_star_four_five = findViewById(R.id.iv_star_four_five);
        iv_star_five_five = findViewById(R.id.iv_star_five_five);

        //====== Star Second item ====

        iv_star_one_four = findViewById(R.id.iv_star_one_four);
        iv_star_two_four = findViewById(R.id.iv_star_two_four);
        iv_star_three_four = findViewById(R.id.iv_star_three_four);
        iv_star_four_four = findViewById(R.id.iv_star_four_four);

        //====== Star Third item ====

        iv_star_one_three = findViewById(R.id.iv_star_one_three);
        iv_star_two_three = findViewById(R.id.iv_star_two_three);
        iv_star_three_three = findViewById(R.id.iv_star_three_three);

        //====== Star Fourth item ====

        iv_star_one_two = findViewById(R.id.iv_star_one_two);
        iv_star_two_two = findViewById(R.id.iv_star_two_two);

        //====== Star Fifth item ====
        iv_star_one_one = findViewById(R.id.iv_star_one_one);

        //==== Item For Select ===
        lnr_five_star = findViewById(R.id.lnr_five_star);
        lnr_four_star = findViewById(R.id.lnr_four_star);
        lnr_three_star = findViewById(R.id.lnr_three_star);
        lnr_two_star = findViewById(R.id.lnr_two_star);
        lnr_one_star = findViewById(R.id.lnr_one_star);

        lnr_five_star.setOnClickListener(this);
        lnr_four_star.setOnClickListener(this);
        lnr_three_star.setOnClickListener(this);
        lnr_two_star.setOnClickListener(this);
        lnr_one_star.setOnClickListener(this);


        //===== Submit Button ===
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        btn_feedback = findViewById(R.id.btn_feedback);
        btn_feedback.setOnClickListener(this);

//        btn_submit.setEnabled(false);
        SetTextSize();
    }

    private void SetTextSize() {
        //==== tittle and close ===
        tv_tittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(40));
        tv_tittle.setTypeface(c.typeface, Typeface.BOLD);

        //====== Description text =======

        tv_rateus_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(28));
        tv_rateus_text.setTypeface(c.typeface, Typeface.BOLD);

        //====  Review Text ===
        tv_text5.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(32));
        tv_text5.setTypeface(c.typeface, Typeface.BOLD);

        tv_text4.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(32));
        tv_text4.setTypeface(c.typeface, Typeface.BOLD);

        tv_text3.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(32));
        tv_text3.setTypeface(c.typeface, Typeface.BOLD);

        tv_text2.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(32));
        tv_text2.setTypeface(c.typeface, Typeface.BOLD);

        tv_text1.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(32));
        tv_text1.setTypeface(c.typeface, Typeface.BOLD);

        //===== Submit Button ===
        btn_submit.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(32));
        btn_submit.setTypeface(c.typeface, Typeface.BOLD);

        btn_feedback.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getSize(32));
        btn_feedback.setTypeface(c.typeface, Typeface.BOLD);
    }

    private void LayOutScreen() {

        FrameLayout.LayoutParams frm;
        LinearLayout.LayoutParams ln;
        int w, h;

        w = getwidth(900);
        h = getheight(600);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.frm_popup).getLayoutParams();
        frm.width = w;
        frm.height = h;

        //==== tittle and close ===
        h = getheight(78);
        frm = (FrameLayout.LayoutParams) tv_tittle.getLayoutParams();
        frm.height = h;

        w = getwidth(60);
        h = getheight(60);
        frm = (FrameLayout.LayoutParams) btn_close.getLayoutParams();
        frm.height = h;
        frm.width = w;
        frm.topMargin = getheight(110);
        frm.rightMargin = getwidth(30);

        //==== Middle main linear ======

        w = getwidth(805);
        h = getheight(388);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.lnr_middle).getLayoutParams();
//        frm.height = h;
        frm.width = w;
        frm.topMargin = getheight(140);

        //====== Description text =======

        ln = (LinearLayout.LayoutParams) tv_rateus_text.getLayoutParams();
        ln.topMargin = getheight(10);

        //==== Star List =====
        w = getwidth(550);
        h = getheight(300);
        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnr_star_list).getLayoutParams();
        ln.width = w;
        ln.height = h;
        ln.topMargin = getheight(15);


        //==== Item For Select ===
        h = getheight(60);
        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnr_five_star).getLayoutParams();
        ln.height = h;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnr_four_star).getLayoutParams();
        ln.height = h;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnr_three_star).getLayoutParams();
        ln.height = h;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnr_two_star).getLayoutParams();
        ln.height = h;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnr_one_star).getLayoutParams();
        ln.height = h;

        //====  Review Text ===
        w = getwidth(198);
        ln = (LinearLayout.LayoutParams) findViewById(R.id.tv_text5).getLayoutParams();
        ln.width = w;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.tv_text4).getLayoutParams();
        ln.width = w;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.tv_text3).getLayoutParams();
        ln.width = w;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.tv_text2).getLayoutParams();
        ln.width = w;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.tv_text1).getLayoutParams();
        ln.width = w;

        //====== Star backgraound frame ====
        h = getheight(56);
        w = getwidth(352);
        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnr_five_start_bg).getLayoutParams();
        ln.height = h;
        ln.width = w;

        w = getwidth(283);
        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnr_four_start_bg).getLayoutParams();
        ln.height = h;
        ln.width = w;

        w = getwidth(208);
        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnr_three_start_bg).getLayoutParams();
        ln.height = h;
        ln.width = w;

        w = getwidth(146);
        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnr_two_start_bg).getLayoutParams();
        ln.height = h;
        ln.width = w;

        w = getwidth(77);
        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnr_one_start_bg).getLayoutParams();
        ln.height = h;
        ln.width = w;


        //====== Star BG ====

        w = getwidth(38);
        h = getheight(37);

        //====== background Star First item ====
        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_one_five).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(20);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_two_five).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(30);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_three_five).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(30);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_four_five).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(30);


        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_five_five).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(30);


        //====== background Star Second item ====
        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_one_four).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(20);


        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_two_four).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(30);


        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_three_four).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(30);


        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_four_four).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(30);


        //====== background Star Third item ====
        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_one_three).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(20);


        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_two_three).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(30);


        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_three_three).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(30);


        //====== background Star Second item ====
        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_one_two).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(20);


        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_two_two).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(30);


        //====== background Star First item ====
        ln = (LinearLayout.LayoutParams) findViewById(R.id.iv_star_bg_one_one).getLayoutParams();
        ln.height = h;
        ln.width = w;
        ln.leftMargin = getwidth(20);


        //==== Star forGround ===
        w = getwidth(34);
        h = getheight(33);

        //====== Star First item ====
        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_one_five).getLayoutParams();
        frm.height = h;
        frm.width = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_two_five).getLayoutParams();
        frm.height = h;
        frm.width = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_three_five).getLayoutParams();
        frm.height = h;
        frm.width = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_four_five).getLayoutParams();
        frm.height = h;
        frm.width = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_five_five).getLayoutParams();
        frm.height = h;
        frm.width = w;

        //====== Star Second item ====
        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_one_four).getLayoutParams();
        frm.height = h;
        frm.width = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_two_four).getLayoutParams();
        frm.height = h;
        frm.width = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_three_four).getLayoutParams();
        frm.height = h;
        frm.width = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_four_four).getLayoutParams();
        frm.height = h;
        frm.width = w;


        //======  Star Third item ====
        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_one_three).getLayoutParams();
        frm.height = h;
        frm.width = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_two_three).getLayoutParams();
        frm.height = h;
        frm.width = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_three_three).getLayoutParams();
        frm.height = h;
        frm.width = w;

        //======  Star Second item ====
        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_one_two).getLayoutParams();
        frm.height = h;
        frm.width = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_two_two).getLayoutParams();
        frm.height = h;
        frm.width = w;

        //======  Star First item ====
        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_star_one_one).getLayoutParams();
        frm.height = h;
        frm.width = w;

        //===== Submit Button ===
        w = getwidth(180);
        h = getheight(70);
        ln = (LinearLayout.LayoutParams) btn_submit.getLayoutParams();
        ln.width = w;
        ln.height = h;
        ln.topMargin = getheight(5);
        ln.leftMargin = getwidth(50);


        ln = (LinearLayout.LayoutParams) btn_feedback.getLayoutParams();
        ln.width = w;
        ln.height = h;
        ln.topMargin = getheight(5);

    }

    private void itemFirstVisible() {
        iv_star_one_five.setVisibility(View.VISIBLE);
        iv_star_two_five.setVisibility(View.VISIBLE);
        iv_star_three_five.setVisibility(View.VISIBLE);
        iv_star_four_five.setVisibility(View.VISIBLE);
        iv_star_five_five.setVisibility(View.VISIBLE);
    }

    private void itemFirstInvisible() {
        iv_star_one_five.setVisibility(View.GONE);
        iv_star_two_five.setVisibility(View.GONE);
        iv_star_three_five.setVisibility(View.GONE);
        iv_star_four_five.setVisibility(View.GONE);
        iv_star_five_five.setVisibility(View.GONE);
    }

    private void itemSecondVisible() {
        iv_star_one_four.setVisibility(View.VISIBLE);
        iv_star_two_four.setVisibility(View.VISIBLE);
        iv_star_three_four.setVisibility(View.VISIBLE);
        iv_star_four_four.setVisibility(View.VISIBLE);
    }

    private void itemSecondInvisible() {
        iv_star_one_four.setVisibility(View.GONE);
        iv_star_two_four.setVisibility(View.GONE);
        iv_star_three_four.setVisibility(View.GONE);
        iv_star_four_four.setVisibility(View.GONE);
    }

    private void itemThirdVisible() {
        iv_star_one_three.setVisibility(View.VISIBLE);
        iv_star_two_three.setVisibility(View.VISIBLE);
        iv_star_three_three.setVisibility(View.VISIBLE);
    }

    private void itemThirdInvisible() {
        iv_star_one_three.setVisibility(View.GONE);
        iv_star_two_three.setVisibility(View.GONE);
        iv_star_three_three.setVisibility(View.GONE);

    }

    private void itemFourthVisible() {
        iv_star_one_two.setVisibility(View.VISIBLE);
        iv_star_two_two.setVisibility(View.VISIBLE);

    }

    private void itemFourthInvisible() {
        iv_star_one_two.setVisibility(View.GONE);
        iv_star_two_two.setVisibility(View.GONE);

    }

    private void itemFifthVisible() {
        iv_star_one_one.setVisibility(View.VISIBLE);
    }

    private void itemFifthInvisible() {
        iv_star_one_one.setVisibility(View.GONE);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private int getheight(int val) {
        // TODO Auto-generated method stub
        return Screen_Hight * val / 720;
    }

    @DebugLog
    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return Screen_Width * val / 1280;
    }

    @Override
    public void onClick(View v) {

        if (v == btn_close) {
            if (isFromWinner) {
                PreferenceManager.SetShowRateOnWinner(false);
            }
            Music_Manager.buttonclick();
            finish();
            overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
        } else if (v == lnr_five_star) {

            itemFirstVisible();
            itemSecondInvisible();
            itemThirdInvisible();
            itemFourthInvisible();
            itemFifthInvisible();
            isPlayStore = true;
            isRateGive = true;
            Music_Manager.buttonclick();
//            EnableSubmit();

        } else if (v == lnr_four_star) {
            itemFirstInvisible();
            itemSecondVisible();
            itemThirdInvisible();
            itemFourthInvisible();
            itemFifthInvisible();
            isPlayStore = true;
            isRateGive = true;
            Music_Manager.buttonclick();
//            EnableSubmit();

        } else if (v == lnr_three_star) {
            itemFirstInvisible();
            itemSecondInvisible();
            itemThirdVisible();
            itemFourthInvisible();
            itemFifthInvisible();
            isPlayStore = true;
            isRateGive = true;
            Music_Manager.buttonclick();
//            EnableSubmit();

        } else if (v == lnr_two_star) {
            itemFirstInvisible();
            itemSecondInvisible();
            itemThirdInvisible();
            itemFourthVisible();
            itemFifthInvisible();
            isPlayStore = false;
            isRateGive = true;
            Music_Manager.buttonclick();
//            EnableSubmit();

        } else if (v == lnr_one_star) {
            Music_Manager.buttonclick();
            itemFirstInvisible();
            itemSecondInvisible();
            itemThirdInvisible();
            itemFourthInvisible();
            itemFifthVisible();
            isRateGive = true;
            isPlayStore = false;
//            EnableSubmit();

        } else if (v == btn_submit) {

            if (isRateGive) {
                if (isPlayStore) {
                    Music_Manager.buttonclick();
                    PreferenceManager.setOpenCounter1(-1);
                    String packageName = getApplicationContext().getPackageName();
                    String myUrl = "https://play.google.com/store/apps/details?id=" + packageName;
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl)));
                    PreferenceManager.SetIsRateGive(true);
                } else {
                    Music_Manager.buttonclick();
                    try {
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@gamewithpals.com"});
                        email.putExtra(Intent.EXTRA_SUBJECT, "GinRummy Offline Feedback(Android V" + c.version + ")");
                        email.putExtra(Intent.EXTRA_TEXT, "");
                        email.setType("message/rfc822");
                        startActivity(Intent.createChooser(email, "Choose an Email client :"));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "There no app installed to perform this action.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

                finish();
            }

        } else if (v == btn_feedback) {
            Music_Manager.buttonclick();
            try {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@gamewithpals.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "GinRummy Offline Feedback(Android V" + c.version + ")");
                email.putExtra(Intent.EXTRA_TEXT, "");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "There no app installed to perform this action.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }

    void EnableSubmit() {
        btn_submit.setEnabled(true);
        btn_submit.setBackgroundResource(R.drawable.profile_bg);
    }
}