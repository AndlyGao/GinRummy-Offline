package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utils.C;
import utils.PreferenceManager;

public class Activity_Help extends Activity {

    TextView title;
    ImageView close, arrowleft, arrowright;
    ViewPager pager;
    ImageView[] dot = new ImageView[3];
    int Screen_Hight, Screen_Width;
    Typeface typeface;
    C c = C.getInstance();

    private Handler handler;


//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//
//        Logger.print("MMMMMMMMMMMMMMM>>> onRestoreInstanceState ::::Called");
//

    @Override
    protected void onResume() {
        super.onResume();

        c.payment_Handler = this.handler;
        c.con = this;
        c.activity = Activity_Help.this;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        typeface = Typeface.createFromAsset(getAssets(),
                "font/PoetsenOne-Regular.ttf");
        c.height = Screen_Hight = PreferenceManager.getheight();
        c.width = Screen_Width = PreferenceManager.getwidth();
        setScreen();
        title = findViewById(R.id.activity_help_ctitle);
        title.setTypeface(typeface);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));

        title.setText("Game Rules");
        close = findViewById(R.id.activity_help_closebtn);
        pager = findViewById(R.id.activity_help_pager);
        arrowleft = findViewById(R.id.prevarrow);
        // arrowleft.setVisibility(View.INVISIBLE);
        arrowright = findViewById(R.id.nextarrow);
        //arrowright.setVisibility(View.INVISIBLE);

        arrowleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                pager.setCurrentItem(getItem(-1), true);

            }
        });
        arrowright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                pager.setCurrentItem(getItem(+1), true);
            }
        });


        dot[0] = findViewById(R.id.help_dot_1);
        dot[1] = findViewById(R.id.help_dot_2);
        dot[2] = findViewById(R.id.help_dot_3);

        close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Music_Manager.buttonclick();
                finish();
                overridePendingTransition(R.anim.none, R.anim.to_lefttoright);

            }
        });

        ChangeDotBackground(0);

        pager.setAdapter(new HelpAdapter());

        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
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

    private int getItem(int i) {
        return pager.getCurrentItem() + i;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        Screen_Hight = c.height;
        Screen_Width = c.width;
        FrameLayout.LayoutParams frm = null;
        LinearLayout.LayoutParams lin = null;
        RelativeLayout.LayoutParams rel = null;

//        frm = (FrameLayout.LayoutParams) findViewById(R.id.help_bk)
//                .getLayoutParams();
//        frm.width = getwidth(1280);
//        frm.height = getheight(720);

        frm = (FrameLayout.LayoutParams) findViewById(
                R.id.activity_help_ctitle).getLayoutParams();
        frm.topMargin = getheight(20);
        frm.height = getheight(70);
        frm.width = getwidth(900);

        frm = (FrameLayout.LayoutParams) findViewById(
                R.id.activity_help_closebtn).getLayoutParams();
        frm.width = getwidth(80);
        frm.height = getwidth(70);
        frm.topMargin = getheight(20);
        frm.rightMargin = getwidth(20);

        frm = (FrameLayout.LayoutParams) findViewById(
                R.id.prevarrow).getLayoutParams();
        frm.width = getwidth((int) (40 * 1.2));
        frm.height = getheight((int) (100 * 1.2));
        frm.leftMargin = getwidth(10);
        frm.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
//        frm.leftMargin=getwidth(10);

        frm = (FrameLayout.LayoutParams) findViewById(
                R.id.nextarrow).getLayoutParams();
        frm.width = getwidth((int) (40 * 1.2));
        frm.height = getheight((int) (100 * 1.2));
        frm.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        frm.rightMargin = getwidth(10);


        frm = (FrameLayout.LayoutParams) findViewById(R.id.help_frm1)
                .getLayoutParams();
        frm.topMargin = getheight(20);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.help_lin1)
                .getLayoutParams();
        frm.rightMargin = getwidth(10);
        frm.topMargin = getheight(70);

        FrameLayout frm1 = findViewById(R.id.help_frm2);
        frm1.setPadding(getwidth(25), 0, getwidth(10), getheight(10));

        frm = (FrameLayout.LayoutParams) findViewById(R.id.activity_help_pager)
                .getLayoutParams();
        frm.height = getheight(570);
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
        lin.leftMargin = getwidth(10);
        lin.height = getwidth(30);
        lin.width = getwidth(30);

    }

    private void setScreenForViewPager(int resId, View view) {
        // TODO Auto-generated method stub


        LinearLayout.LayoutParams lin = null;
        FrameLayout.LayoutParams frm = null;
        int w, h;
        if (resId == R.layout.help0) {

            //arrowleft.setVisibility(View.INVISIBLE);
            // arrowright.setVisibility(View.VISIBLE);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivStar1).getLayoutParams();
            lin.width = lin.height = getwidth(30);
            lin.rightMargin = getwidth(10);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivStar2).getLayoutParams();
            lin.width = lin.height = getwidth(30);
            lin.rightMargin = getwidth(10);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivStar3).getLayoutParams();
            lin.width = lin.height = getwidth(30);
            lin.rightMargin = getwidth(10);

            TextView tvTheDeck = view.findViewById(R.id.tvTheDeck);
            tvTheDeck.setTypeface(typeface, Typeface.BOLD);
            tvTheDeck.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivLineSep).getLayoutParams();
            lin.leftMargin = getwidth(-60);
            lin.width = getwidth(300);

            TextView tvText1 = view.findViewById(R.id.tvText1);
            tvText1.setTypeface(typeface);
            tvText1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llContainer1).getLayoutParams();
            lin.topMargin = getheight(20);

           /* TextView tvText2 = (TextView) view.findViewById(R.id.tvText2);
            tvText2.setTypeface(typeface);
            tvText2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));

            lin = (LinearLayout.LayoutParams) tvText2.getLayoutParams();
            lin.topMargin = getheight(10);*/

            TextView tvText3 = view.findViewById(R.id.tvText3);
            tvText3.setTypeface(typeface);
            tvText3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));


            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llContainer2).getLayoutParams();
            lin.topMargin = getheight(20);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llCardValuesContainer).getLayoutParams();
            lin.width = getwidth(700);
            lin.topMargin = getheight(10);

            TextView tvText4 = view.findViewById(R.id.tvText4);
            tvText4.setTypeface(typeface);
            tvText4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

            TextView tvText5 = view.findViewById(R.id.tvText5);
            tvText5.setTypeface(typeface);
            tvText5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

            TextView tvText6 = view.findViewById(R.id.tvText6);
            tvText6.setTypeface(typeface);
            tvText6.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

            TextView tvText7 = view.findViewById(R.id.tvText7);
            tvText7.setTypeface(typeface);
            tvText7.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

            TextView tvText8 = view.findViewById(R.id.tvText8);
            tvText8.setTypeface(typeface);
            tvText8.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llContainer3).getLayoutParams();
            lin.topMargin = getheight(20);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.frmCardContainer).getLayoutParams();
            lin.topMargin = getheight(10);

            ImageView ivCards[] = new ImageView[13];
            ivCards[0] = view.findViewById(R.id.ivCard1);
            ivCards[1] = view.findViewById(R.id.ivCard2);
            ivCards[2] = view.findViewById(R.id.ivCard3);
            ivCards[3] = view.findViewById(R.id.ivCard4);
            ivCards[4] = view.findViewById(R.id.ivCard5);
            ivCards[5] = view.findViewById(R.id.ivCard6);
            ivCards[6] = view.findViewById(R.id.ivCard7);
            ivCards[7] = view.findViewById(R.id.ivCard8);
            ivCards[8] = view.findViewById(R.id.ivCard9);
            ivCards[9] = view.findViewById(R.id.ivCard10);
            ivCards[10] = view.findViewById(R.id.ivCard11);
            ivCards[11] = view.findViewById(R.id.ivCard12);
            ivCards[12] = view.findViewById(R.id.ivCard13);

            w = getwidth(90);
            h = w * 120 / 90;
            int leftMargin = 0;
            for (int i = 0; i < ivCards.length; i++) {
                frm = (FrameLayout.LayoutParams) ivCards[i].getLayoutParams();
                frm.width = w;
                frm.height = h;
                frm.leftMargin = getwidth(leftMargin);
                leftMargin += 44;
            }

        } else if (resId == R.layout.help1) {

            //arrowleft.setVisibility(View.VISIBLE);
            // arrowright.setVisibility(View.VISIBLE);


            TextView tvTitleGameRules = view.findViewById(R.id.tvTitleGameRules);
            tvTitleGameRules.setTypeface(typeface, Typeface.BOLD);
            tvTitleGameRules.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));

            w = getwidth(400);
            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivSeprator1)
                    .getLayoutParams();
            lin.width = w;
            lin.bottomMargin = w * 10 / 400;
            lin.leftMargin = w * -60 / 400;

            w = getwidth(30);
            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivStar1)
                    .getLayoutParams();
            lin.width = lin.height = w;
            lin.rightMargin = w * 10 / 30;

            TextView tvRule1 = view.findViewById(R.id.tvRule1);
            tvRule1.setTypeface(typeface);
            tvRule1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llContainer)
                    .getLayoutParams();
            lin.topMargin = getheight(10);

            TextView tvSequenceEx = view.findViewById(R.id.tvSequenceEx);
            tvSequenceEx.setTypeface(typeface);
            tvSequenceEx.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.frmSeqCards)
                    .getLayoutParams();
            lin.topMargin = getheight(10);

            w = getwidth(90);
            h = w * 120 / 90;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.card1).getLayoutParams();
            frm.width = w;
            frm.height = h;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.card2).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 45 / 120;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.card3).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 90 / 120;

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llSet)
                    .getLayoutParams();
            lin.leftMargin = getwidth(100);

            TextView tvSetEx = view.findViewById(R.id.tvSetEx);
            tvSetEx.setTypeface(typeface);
            tvSetEx.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.frmSetCards)
                    .getLayoutParams();
            lin.topMargin = getheight(10);

            w = getwidth(90);
            h = w * 120 / 90;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.card4).getLayoutParams();
            frm.width = w;
            frm.height = h;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.card5).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 45 / 90;

            frm = (FrameLayout.LayoutParams) view.findViewById(R.id.card6).getLayoutParams();
            frm.width = w;
            frm.height = h;
            frm.leftMargin = w * 90 / 90;

            w = getwidth(30);
            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivStar2)
                    .getLayoutParams();
            lin.width = lin.height = w;
            lin.rightMargin = w * 10 / 30;

            TextView tvHead2 = view.findViewById(R.id.tvHead2);
            tvHead2.setTypeface(typeface, Typeface.BOLD);
            tvHead2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));

            TextView tvRule2 = view.findViewById(R.id.tvRule2);
            tvRule2.setTypeface(typeface);
            tvRule2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));

            lin = (LinearLayout.LayoutParams) tvRule2.getLayoutParams();
            lin.topMargin = getheight(10);

            TextView tvRule3 = view.findViewById(R.id.tvRule3);
            tvRule3.setTypeface(typeface);
            tvRule3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));

            lin = (LinearLayout.LayoutParams) tvRule3.getLayoutParams();
            lin.topMargin = getheight(10);

        } else if (resId == R.layout.help2) {

            //arrowleft.setVisibility(View.VISIBLE);
            // arrowright.setVisibility(View.INVISIBLE);


            TextView tvTitleGameRules = view.findViewById(R.id.tvTitleGameRules);
            tvTitleGameRules.setTypeface(typeface, Typeface.BOLD);
            tvTitleGameRules.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));

            w = getwidth(200);
            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivSeprator1)
                    .getLayoutParams();
            lin.width = w;
            lin.topMargin = w * 10 / 200;
            lin.bottomMargin = w * 10 / 200;
            lin.leftMargin = w * -30 / 200;

            TextView tvRule1 = view.findViewById(R.id.tvRule1);
            tvRule1.setTypeface(typeface);
            tvRule1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));

            TextView tvRule2 = view.findViewById(R.id.tvRule2);
            tvRule2.setTypeface(typeface);
            tvRule2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));

            TextView tvRule3 = view.findViewById(R.id.tvRule3);
            tvRule3.setTypeface(typeface);
            tvRule3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));

            TextView tvRule4 = view.findViewById(R.id.tvRule4);
            tvRule4.setTypeface(typeface);
            tvRule4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));

            w = getwidth(30);
            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivStar1)
                    .getLayoutParams();
            lin.width = lin.height = w;
            lin.rightMargin = w * 10 / 30;

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivStar2)
                    .getLayoutParams();
            lin.width = lin.height = w;
            lin.rightMargin = w * 10 / 30;

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivStar3)
                    .getLayoutParams();
            lin.width = lin.height = w;
            lin.rightMargin = w * 10 / 30;

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.ivStar4)
                    .getLayoutParams();
            lin.width = lin.height = w;
            lin.rightMargin = w * 10 / 30;

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llRuleContainer2).getLayoutParams();
            lin.topMargin = getheight(20);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llRuleContainer3).getLayoutParams();
            lin.topMargin = getheight(20);

            lin = (LinearLayout.LayoutParams) view.findViewById(R.id.llRuleContainer4).getLayoutParams();
            lin.topMargin = getheight(20);


        }
    }

    private void ChangeDotBackground(int index) {

        for (int i = 0; i < dot.length; i++) {

            if (i == index) {

                dot[index].setBackgroundResource(R.drawable.help_dot_black);
                if (index == 0) {
                    arrowleft.setVisibility(View.INVISIBLE);
                    arrowright.setVisibility(View.VISIBLE);

                } else if (index == 1) {
                    arrowleft.setVisibility(View.VISIBLE);
                    arrowright.setVisibility(View.VISIBLE);

                } else if (index == 2) {
                    arrowleft.setVisibility(View.VISIBLE);
                    arrowright.setVisibility(View.INVISIBLE);

                }

            } else {

                dot[i].setBackgroundResource(R.drawable.helpdot77);

            }
        }
    }

    private class HelpAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 3;
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
                    resId = R.layout.help0;
                    // arrowleft.setVisibility(View.INVISIBLE);
                    //arrowright.setVisibility(View.VISIBLE);
                    break;

                case 1:
                    resId = R.layout.help1;
                    //arrowleft.setVisibility(View.VISIBLE);
                    //    arrowright.setVisibility(View.VISIBLE);
                    break;

                case 2:
                    resId = R.layout.help2;
                    //    arrowleft.setVisibility(View.VISIBLE);
                    //   arrowright.setVisibility(View.INVISIBLE);
                    break;

                default:
                    resId = R.layout.help0;
                    //  arrowleft.setVisibility(View.INVISIBLE);
                    //arrowright.setVisibility(View.VISIBLE);

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