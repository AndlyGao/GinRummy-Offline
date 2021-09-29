package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import utils.C;
import utils.PreferenceManager;
import utils.ResponseCodes;

/**
 * Created by savan.nasit on 6/12/2017.
 */
public class SpinEarn extends Activity implements View.OnClickListener {
    final static String SpinApiKey = "93WriO";
    public static Handler handler;
    TextView tvTitle_spin, tv_chip1, tv_chip2;
    Button close_spin;
    C c = C.getInstance();
    int Screen_Width, Screen_Hight;
    ImageView spinners, spinner_outer, spinner_point, tv_btn_spin;
    boolean isSpinning = false;
    int[] Angles = {0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330};
    int[] chips = {100, 350, 1000, 800, 500, 700, 250, 600, 200, 400, 150, 300};
    String rewardAmount = "";
    boolean isShowFull = false;
    private boolean isRotateStart = false;
    private Dialog ConfirmationDialogInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinearn);
        Screen_Hight = c.height;
        Screen_Width = c.width;

        FindViewByIds();
        SetScreenDesign();

        AnimationDrawable gyroAnimation = (AnimationDrawable) spinner_outer.getBackground();
        gyroAnimation.start();

        initHandler();

    }


    @Override
    public void onBackPressed() {
        return;
    }

    private void initHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == ResponseCodes.AdsClosed) {
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!isSpinning) {
                                Random r = new Random();
                                int Random = r.nextInt(12);
                                StartRotation(Random);
                            }
                        }
                    }, 2000);
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        spinner_outer.clearAnimation();
        super.onDestroy();
        tv_btn_spin.clearAnimation();
    }

    private void SetScreenDesign() {
        FrameLayout.LayoutParams frm;
        LinearLayout.LayoutParams lin;

        tvTitle_spin.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        tvTitle_spin.setTypeface(c.typeface);

        tv_chip1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(34));
        tv_chip1.setTypeface(c.typeface);

        tv_chip2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(34));
        tv_chip2.setTypeface(c.typeface);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvTitle_spin).getLayoutParams();
        frm.width = c.getwidth1(900);
        frm.height = getheight(70);
        frm.topMargin = getheight(15);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.close_spin).getLayoutParams();
        frm.width = getwidth(80);
        frm.height = getheight(70);
        frm.topMargin = getheight(15);
        frm.rightMargin = getwidth(15);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.iv_chip1).getLayoutParams();
        lin.width = getwidth(42);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.fl_spinner).getLayoutParams();
//        frm.height = frm.width = getheight(650);
        frm.height = frm.width = Screen_Hight - getheight(140);
        frm.topMargin = getheight(150);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.spinner_point).getLayoutParams();
        frm.width = getwidth(90);
        frm.height = getwidth(130);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tv_btn_spin).getLayoutParams();
        frm.width = getwidth(200);
        frm.height = getwidth(200);
    }

    private void FindViewByIds() {
        tvTitle_spin = findViewById(R.id.tvTitle_spin);
        close_spin = findViewById(R.id.close_spin);
        close_spin.setOnClickListener(this);
        tv_chip1 = findViewById(R.id.tv_chip1);
        tv_chip2 = findViewById(R.id.tv_chip2);
        spinners = findViewById(R.id.spinners);
        spinner_outer = findViewById(R.id.spinner_outer);
        spinner_outer.setBackgroundResource(R.drawable.spin_ring_animation);

        spinner_point = findViewById(R.id.spinner_point);
        tv_btn_spin = findViewById(R.id.tv_btn_spin);
        tv_btn_spin.setOnClickListener(this);

        new AnimationUtils();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.freechipanim);//scalebounce_infinite
        tv_btn_spin.startAnimation(animation);

//        Animation animations = new AnimationUtils().loadAnimation(this, R.anim.scalebounce_infinite);//scalebounce_infinite
//        tv_btn_spin.startAnimation(animations);

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
    public void onClick(View v) {
        Music_Manager.buttonclick();
        if (v == close_spin) {
            overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
            finish();
        } else if (v == tv_btn_spin) {
            if (PreferenceManager.isInternetConnected()) {

            } else {
                showInfoDialog("Alert", "No Internet Connection.");
            }
        }
    }

    private void StartRotation(final int Angle) {
        final RotateAnimation rotateAnimation = new RotateAnimation(0.0F, (360 * 6) + Angles[Angle], Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimation.setRepeatMode(0);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(7000);

        spinners.startAnimation(rotateAnimation);

        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isSpinning = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RotationAnimationEnds(Angle);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    private void showInfoDialog(String DialogTitle, String Message) {
        // TODO Auto-generated method stub
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "font/PoetsenOne-Regular.ttf");
        LinearLayout.LayoutParams lin;

        if (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing()) {
            if (ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.dismiss();
            }
            ConfirmationDialogInfo = null;
        }

        ConfirmationDialogInfo = new Dialog(SpinEarn.this,
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

        TextView message = ConfirmationDialogInfo
                .findViewById(R.id.tvMessage);
        message.setTypeface(typeface);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

        Button btnOne = ConfirmationDialogInfo.findViewById(R.id.button1);
        btnOne.setTypeface(typeface);
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
                ConfirmationDialogInfo.dismiss();
            }
        });
        if (!isFinishing())
            ConfirmationDialogInfo.show();

    }

    private void startSpin() {
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isSpinning) {
                    Random r = new Random();
                    int Random = r.nextInt(12);
                    StartRotation(Random);
                }
            }
        }, 500);
    }


    private void RotationAnimationEnds(final int Angle) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (chips[Angle] == 0) {
                    showInfoDialog("Oops!", "You Didn't Win. Try Again later..!");
                    tv_btn_spin.setImageResource(R.drawable.btn_spin_again);


                } else {
                    showInfoDialog("Claimed", "" + chips[Angle] + " Chips Added");
                    tv_btn_spin.setImageResource(R.drawable.btn_spin_again);

                    c.Chips = PreferenceManager.getChips() + chips[Angle];
                    PreferenceManager.setChips(c.Chips);

                    Message msg = new Message();
                    msg.what = 41417171;

                    if (Dashboard.handler != null) {
                        Dashboard.handler.sendMessage(msg);
                    }

                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        spinners.clearAnimation();
                        isSpinning = false;
                        tv_btn_spin.setEnabled(true);
                        new AnimationUtils();
                        Animation animations = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.freechipanim);//scalebounce_infinite
                        tv_btn_spin.startAnimation(animations);
                    }
                }, 1200);

            }
        }, 1000);


    }

}
