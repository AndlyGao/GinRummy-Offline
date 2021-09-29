package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.AlphaModifier;
import com.plattysoft.leonids.modifiers.ScaleModifier;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import hugo.weaving.DebugLog;
import utils.C;
import utils.Logger;
import utils.MagicTextView;
import utils.PreferenceManager;
import utils.ResponseCodes;
import utils.SQLiteManager;

/**
 * Created by Artoon on 30-Dec-16.
 */
public class DailyBonus3 extends Activity implements View.OnClickListener {
    final static String ApiKey = "x8E3sV";
    public static Handler handler;
    TextView tx_or;
    C c = C.getInstance();
    LinearLayout lnMaincenter, lnYouWin;
    LinearLayout[] lnfrmreward = new LinearLayout[7];
    //  FrameLayout frmInner1,frmInner2;
    FrameLayout[] frmInner = new FrameLayout[7];
    TextView tvTittle, tvyouwintittle, tvyouwinchip, tvchiptext, txt_2xChip, txt_Chip_11;
    TextView[] tvCongras = new TextView[7];
    //  ImageView ivmiddlemain1,ivmiddlemain2;
    ImageView[] ivmiddlemain = new ImageView[7];
    // TextView  tvinnerchip1,tvinnerchip2;
    TextView[] tvinnerchip = new TextView[7];
    // ImageView ivmiddlechip1,ivmiddlechip2;
    ImageView[] ivmiddlechip = new ImageView[7];
    ImageView ivlinetittle;
    ImageView[] ivtoday = new ImageView[7];
    //  TextView tvday1,tvday2;
    TextView[] tvday = new TextView[7];
    TextView[] tomorrow = new TextView[7];
    String[] rewardChips = {"100", "250", "500", "1000", "1500", "2000", "2500"};
    Integer[] rewardChipsint = {100, 250, 500, 1000, 1500, 2000, 2500};
    Button btnchip, btncollect;
    MagicTextView Bonus2X;
    int day, index;
    // 250, 500, 1000, 2000, 3000, 4000, 5000
    Timer timer = new Timer();
    C myData = C.getInstance();
    boolean isShowFull = false;
    String rewardAmount = "";
    int chipIndex = 0;
    private ProgressBar spinner;
    private FrameLayout fl_loader;
    private boolean isLoading = false;
    private Animation freechipanim, shakeAnimation;
    private long WinChip;
    private SQLiteManager sqLiteManager;
    private long second;
    private Dialog ConfirmationDialogInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailybonus3);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        c.height = PreferenceManager.getheight();
        c.width = PreferenceManager.getwidth();
        c.typeface = Typeface.createFromAsset(getAssets(), "font/PoetsenOne-Regular.ttf");
        sqLiteManager = new SQLiteManager(getApplicationContext());

        freechipanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.freechipanim22);
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim);
        Music_Manager m = new Music_Manager(getApplicationContext());


        FindViewById();
        LayoutScreen();
        InintHandler();

        int LastcollectDay = PreferenceManager.GetDBCollectDay();
        Logger.print("MMMMMMMMMMMMMM>>>> Last Collected Day:  " + LastcollectDay);

        if ((LastcollectDay != 0) && (c.currentDay - LastcollectDay != 1) && (c.currentDay != 1 || LastcollectDay != 365) && (c.currentDay != 1 || LastcollectDay != 366)) {
            c.DbcollectCount = 0;
            PreferenceManager.SetDBCollectCount(c.DbcollectCount);

        }


        day = PreferenceManager.GetDBCollectCount();
//        day=4;
        if (day == 7) {
            day = 6;
            c.DbcollectCount = day;
        }
        index = day;
        WinChip = Long.valueOf(rewardChips[index]);
        if (day == 6) {
            PreferenceManager.SetIsDaySeven(true);
        } else {
            PreferenceManager.SetIsDaySeven(false);
        }

        setdata();
        txt_2xChip.setText("" + (rewardChipsint[chipIndex] * 2));
        txt_Chip_11.setText("" + (rewardChipsint[chipIndex]));
    }

    private void InintHandler() {

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if (msg.what == ResponseCodes.TryCatch) {
                    Logger.prints("TryCatch is running");
                    DoExplode();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AdsClosedChocolate();
                        }
                    }, 2000);
                }

                return false;

            }
        });

    }

    private void AdsClosedChocolate() {
        c.DbcollectCount++;
        if (c.DbcollectCount > 6) {
            c.DbcollectCount = 0;
        }
        btncollect.setEnabled(false);
        PreferenceManager.SetDBCollectCount(c.DbcollectCount);

        final long beforchip = PreferenceManager.getChips();
        final long increment = WinChip / 4;

        final long bfrchp = beforchip;
        final long incr = increment;

        c.Chips = PreferenceManager.getChips() + (WinChip * 2);
        PreferenceManager.setChips(c.Chips);
        PreferenceManager.SetDBCollectCount(c.DbcollectCount);
        PreferenceManager.SetDBCollectDay(c.currentDay);
        sqLiteManager.updateChip(PreferenceManager.get_id(), (int) WinChip * 2, true);

        PreferenceManager.SetDBcollected(true);
        btnchip.setText("" + c.numDifferentiation(PreferenceManager.getChips()));

        Calendar c = Calendar.getInstance();
        int currentDate = c.get(Calendar.DATE);

        PreferenceManager.SetDBDate(currentDate);
        PreferenceManager.SetPendingChips(0);

        if (Dashboard.handler != null) {
            Message me = new Message();
            me.what = 7777;
            me.obj = WinChip * 2;
            Dashboard.handler.sendMessage(me);
        }

        overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
        finish();


    }

    private void LayoutScreen() {


        FrameLayout.LayoutParams frm;
        LinearLayout.LayoutParams ln;
        int w, h;


        frm = (FrameLayout.LayoutParams) findViewById(R.id.linearMAin).getLayoutParams();
        frm.topMargin = c.getheight1(90);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnMaincenter).getLayoutParams();
        ln.topMargin = c.getheight1(10);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnMaincenter2).getLayoutParams();
        ln.topMargin = c.getheight1(10);

        //==============================================================================================
        ln = (LinearLayout.LayoutParams) findViewById(R.id.frmInner1).getLayoutParams();
        ln.topMargin = c.getheight1(10);
        ln.gravity = Gravity.CENTER;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.frmInner2).getLayoutParams();
        ln.topMargin = c.getheight1(10);
        ln.gravity = Gravity.CENTER;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.frmInner3).getLayoutParams();
        ln.topMargin = c.getheight1(10);
        ln.gravity = Gravity.CENTER;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.frmInner4).getLayoutParams();
        ln.topMargin = c.getheight1(10);
        ln.gravity = Gravity.CENTER;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.frmInner5).getLayoutParams();
        ln.topMargin = c.getheight1(10);
        ln.gravity = Gravity.CENTER;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.frmInner6).getLayoutParams();
        ln.topMargin = c.getheight1(10);
        ln.gravity = Gravity.CENTER;

        ln = (LinearLayout.LayoutParams) findViewById(R.id.frmInner7).getLayoutParams();
        ln.topMargin = c.getheight1(10);
        ln.gravity = Gravity.CENTER;
        //==============================================================================

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnfrmreward1).getLayoutParams();
        ln.leftMargin = c.getwidth1(30);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnfrmreward2).getLayoutParams();
        ln.leftMargin = c.getwidth1(30);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnfrmreward3).getLayoutParams();
        ln.leftMargin = c.getwidth1(30);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnfrmreward4).getLayoutParams();
        ln.leftMargin = c.getwidth1(30);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnfrmreward5).getLayoutParams();
        ln.leftMargin = c.getwidth1(30);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnfrmreward6).getLayoutParams();
        ln.leftMargin = c.getwidth1(30);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.lnfrmreward7).getLayoutParams();
        ln.leftMargin = c.getwidth1(30);

        //==============================================================================================
        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlemain1).getLayoutParams();
        frm.width = c.getwidth1(200);
        frm.height = c.getheight1(215);
        frm.topMargin = c.getheight1(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlemain2).getLayoutParams();
        frm.width = c.getwidth1(200);
        frm.height = c.getheight1(215);
        frm.topMargin = c.getheight1(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlemain3).getLayoutParams();
        frm.width = c.getwidth1(200);
        frm.height = c.getheight1(215);
        frm.topMargin = c.getheight1(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlemain4).getLayoutParams();
        frm.width = c.getwidth1(200);
        frm.height = c.getheight1(215);
        frm.topMargin = c.getheight1(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlemain5).getLayoutParams();
        frm.width = c.getwidth1(200);
        frm.height = c.getheight1(215);
        frm.topMargin = c.getheight1(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlemain6).getLayoutParams();
        frm.width = c.getwidth1(200);
        frm.height = c.getheight1(215);
        frm.topMargin = c.getheight1(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlemain7).getLayoutParams();
        frm.width = c.getwidth1(200);
        frm.height = c.getheight1(215);
        frm.topMargin = c.getheight1(10);

        //==============================================================================================
        ln = (LinearLayout.LayoutParams) findViewById(R.id.tvday1).getLayoutParams();
        ln.width = c.getwidth1(142);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.tvday2).getLayoutParams();
        ln.width = c.getwidth1(142);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.tvday3).getLayoutParams();
        ln.width = c.getwidth1(142);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.tvday4).getLayoutParams();
        ln.width = c.getwidth1(142);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.tvday5).getLayoutParams();
        ln.width = c.getwidth1(142);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.tvday6).getLayoutParams();
        ln.width = c.getwidth1(142);

        ln = (LinearLayout.LayoutParams) findViewById(R.id.tvday7).getLayoutParams();
        ln.width = c.getwidth1(142);

        //==========================================================================================

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvtodtom1).getLayoutParams();
        frm.topMargin = c.getheight1(20);
        frm.rightMargin = c.getwidth1(17);
        frm.width = c.getwidth1(145);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvtodtom2).getLayoutParams();
        frm.topMargin = c.getheight1(20);
        frm.rightMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvtodtom3).getLayoutParams();
        frm.topMargin = c.getheight1(20);
        frm.rightMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvtodtom4).getLayoutParams();
        frm.topMargin = c.getheight1(20);
        frm.rightMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvtodtom5).getLayoutParams();
        frm.topMargin = c.getheight1(20);
        frm.rightMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvtodtom6).getLayoutParams();
        frm.topMargin = c.getheight1(20);
        frm.rightMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvtodtom7).getLayoutParams();
        frm.topMargin = c.getheight1(20);
        frm.rightMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);


        //==============================================================================================
        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvCongras1).getLayoutParams();
        frm.topMargin = c.getheight1(150);
        frm.leftMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvCongras2).getLayoutParams();
        frm.topMargin = c.getheight1(150);
        frm.leftMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvCongras3).getLayoutParams();
        frm.topMargin = c.getheight1(150);
        frm.leftMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvCongras4).getLayoutParams();
        frm.topMargin = c.getheight1(150);
        frm.leftMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvCongras5).getLayoutParams();
        frm.topMargin = c.getheight1(150);
        frm.leftMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvCongras6).getLayoutParams();
        frm.topMargin = c.getheight1(150);
        frm.leftMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvCongras7).getLayoutParams();
        frm.topMargin = c.getheight1(150);
        frm.leftMargin = c.getwidth1(12);
        frm.width = c.getwidth1(145);


        //==============================================================================================
        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlechip1).getLayoutParams();
        frm.width = c.getwidth1(130);
        frm.height = c.getheight1(120);
        frm.leftMargin = c.getwidth1(25);
        frm.topMargin = c.getheight1(50);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlechip2).getLayoutParams();
        frm.width = c.getwidth1(130);
        frm.height = c.getheight1(120);
        frm.leftMargin = c.getwidth1(25);
        frm.topMargin = c.getheight1(50);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlechip3).getLayoutParams();
        frm.width = c.getwidth1(130);
        frm.height = c.getheight1(120);
        frm.leftMargin = c.getwidth1(25);
        frm.topMargin = c.getheight1(50);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlechip4).getLayoutParams();
        frm.width = c.getwidth1(130);
        frm.height = c.getheight1(120);
        frm.leftMargin = c.getwidth1(25);
        frm.topMargin = c.getheight1(50);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlechip5).getLayoutParams();
        frm.width = c.getwidth1(130);
        frm.height = c.getheight1(120);
        frm.leftMargin = c.getwidth1(25);
        frm.topMargin = c.getheight1(50);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlechip6).getLayoutParams();
        frm.width = c.getwidth1(130);
        frm.height = c.getheight1(120);
        frm.leftMargin = c.getwidth1(25);
        frm.topMargin = c.getheight1(50);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivmiddlechip7).getLayoutParams();
        frm.width = c.getwidth1(130);
        frm.height = c.getheight1(120);
        frm.leftMargin = c.getwidth1(25);
        frm.topMargin = c.getheight1(50);

        //=============================================================================================

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvinnerchip1).getLayoutParams();
        frm.width = c.getwidth1(135);
        frm.height = c.getheight1(29);
        frm.rightMargin = c.getwidth1(9);
        frm.bottomMargin = c.getheight1(10);
        frm.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvinnerchip2).getLayoutParams();
        frm.width = c.getwidth1(135);
        frm.height = c.getheight1(29);
        frm.rightMargin = c.getwidth1(9);
        frm.bottomMargin = c.getheight1(10);
        frm.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvinnerchip3).getLayoutParams();
        frm.width = c.getwidth1(135);
        frm.height = c.getheight1(29);
        frm.rightMargin = c.getwidth1(9);

        frm.bottomMargin = c.getheight1(10);
        frm.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvinnerchip4).getLayoutParams();
        frm.width = c.getwidth1(135);
        frm.height = c.getheight1(29);
        frm.rightMargin = c.getwidth1(9);
        frm.bottomMargin = c.getheight1(10);
        frm.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvinnerchip5).getLayoutParams();
        frm.width = c.getwidth1(135);
        frm.height = c.getheight1(29);
        frm.rightMargin = c.getwidth1(9);
        frm.bottomMargin = c.getheight1(10);
        frm.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvinnerchip6).getLayoutParams();
        frm.width = c.getwidth1(135);
        frm.height = c.getheight1(29);
        frm.rightMargin = c.getwidth1(9);
        frm.bottomMargin = c.getheight1(10);
        frm.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvinnerchip7).getLayoutParams();
        frm.width = c.getwidth1(135);
        frm.height = c.getheight1(29);
        frm.rightMargin = c.getwidth1(9);
        frm.bottomMargin = c.getheight1(10);
        frm.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        //==============================================================================================

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivtoday1).getLayoutParams();
        frm.width = c.getwidth1(57);
        frm.height = c.getwidth1(57);
        frm.topMargin = c.getheight1(11);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivtoday2).getLayoutParams();
        frm.width = c.getwidth1(57);
        frm.height = c.getwidth1(57);
        frm.topMargin = c.getheight1(11);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivtoday3).getLayoutParams();
        frm.width = c.getwidth1(57);
        frm.height = c.getwidth1(57);
        frm.topMargin = c.getheight1(11);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivtoday4).getLayoutParams();
        frm.width = c.getwidth1(57);
        frm.height = c.getwidth1(57);
        frm.topMargin = c.getheight1(11);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivtoday5).getLayoutParams();
        frm.width = c.getwidth1(57);
        frm.height = c.getwidth1(57);
        frm.topMargin = c.getheight1(11);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivtoday6).getLayoutParams();
        frm.width = c.getwidth1(57);
        frm.height = c.getwidth1(57);
        frm.topMargin = c.getheight1(11);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivtoday7).getLayoutParams();
        frm.width = c.getwidth1(57);
        frm.height = c.getwidth1(57);
        frm.topMargin = c.getheight1(11);

        //==============================================================================================

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvTittle).getLayoutParams();
        frm.topMargin = c.getheight1(20);
        frm.width = c.getwidth1(900);
        frm.height = c.getheight1(70);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivlinetittle).getLayoutParams();
        frm.topMargin = c.getheight1(75);
        frm.width = c.getwidth1(850);
        frm.height = c.getheight1(8);


        //==============================================================================================

        frm = (FrameLayout.LayoutParams) findViewById(R.id.btnchip).getLayoutParams();
        frm.width = c.getwidth1(244);
        frm.height = c.getheight1(85);
        frm.topMargin = c.getheight1(20);
        frm.leftMargin = c.getwidth1(20);

        //==============================================================================================

        frm = (FrameLayout.LayoutParams) findViewById(R.id.lnYouWin).getLayoutParams();
        frm.bottomMargin = c.getheight1(100);


        frm = (FrameLayout.LayoutParams) findViewById(R.id.btncollect).getLayoutParams();
        frm.height = c.getheight1(60);
        frm.width = c.getwidth1(186);
        frm.rightMargin = c.getwidth1(160);
        frm.bottomMargin = c.getheight1(30);


        frm = (FrameLayout.LayoutParams) findViewById(R.id.Bonus2X).getLayoutParams();
        frm.height = c.getheight1(110);
        frm.width = c.getwidth1(320);
        frm.leftMargin = c.getwidth1(220);
        frm.bottomMargin = c.getheight1(20);

        LinearLayout.LayoutParams lin;
        lin = (LinearLayout.LayoutParams) findViewById(R.id.iv_chips).getLayoutParams();
        lin.height = c.getwidth1(50);
        lin.width = c.getwidth1(50);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.iv_chips_11).getLayoutParams();
        lin.height = c.getwidth1(50);
        lin.width = c.getwidth1(50);


        frm = (FrameLayout.LayoutParams) findViewById(R.id.ll_chip_2X_text).getLayoutParams();
        frm.height = c.getheight1(50);
        frm.bottomMargin = c.getheight1(90);
        frm.leftMargin = c.getwidth1(220);


        frm = (FrameLayout.LayoutParams) findViewById(R.id.ll_chip_text_11).getLayoutParams();
        frm.height = c.getheight1(50);
        frm.bottomMargin = c.getheight1(90);
        frm.rightMargin = c.getwidth1(160);


        frm = (FrameLayout.LayoutParams) findViewById(R.id.tx_or).getLayoutParams();
        frm.bottomMargin = c.getheight1(50);


        //==============================================================================================

    }

    private void FindViewById() {

        lnMaincenter = findViewById(R.id.lnMaincenter);
        //==============================================================================================
        lnfrmreward[0] = findViewById(R.id.lnfrmreward1);
        lnfrmreward[1] = findViewById(R.id.lnfrmreward2);
        lnfrmreward[2] = findViewById(R.id.lnfrmreward3);
        lnfrmreward[3] = findViewById(R.id.lnfrmreward4);
        lnfrmreward[4] = findViewById(R.id.lnfrmreward5);
        lnfrmreward[5] = findViewById(R.id.lnfrmreward6);
        lnfrmreward[6] = findViewById(R.id.lnfrmreward7);
        //  lnfrmreward2=(LinearLayout)findViewById(R.id.lnfrmreward2);


        //==============================================================================================
        frmInner[0] = findViewById(R.id.frmInner1);
        frmInner[1] = findViewById(R.id.frmInner2);
        frmInner[2] = findViewById(R.id.frmInner3);
        frmInner[3] = findViewById(R.id.frmInner4);
        frmInner[4] = findViewById(R.id.frmInner5);
        frmInner[5] = findViewById(R.id.frmInner6);
        frmInner[6] = findViewById(R.id.frmInner7);
        // frmInner2=(FrameLayout)findViewById(R.id.frmInner2);

        //==============================================================================================
        tvCongras[0] = findViewById(R.id.tvCongras1);
        tvCongras[1] = findViewById(R.id.tvCongras2);
        tvCongras[2] = findViewById(R.id.tvCongras3);
        tvCongras[3] = findViewById(R.id.tvCongras4);
        tvCongras[4] = findViewById(R.id.tvCongras5);
        tvCongras[5] = findViewById(R.id.tvCongras6);
        tvCongras[6] = findViewById(R.id.tvCongras7);
        // tvCongras2=(TextView)findViewById(R.id.tvCongras2);

        //==============================================================================================
        tvinnerchip[0] = findViewById(R.id.tvinnerchip1);
        tvinnerchip[1] = findViewById(R.id.tvinnerchip2);
        tvinnerchip[2] = findViewById(R.id.tvinnerchip3);
        tvinnerchip[3] = findViewById(R.id.tvinnerchip4);
        tvinnerchip[4] = findViewById(R.id.tvinnerchip5);
        tvinnerchip[5] = findViewById(R.id.tvinnerchip6);
        tvinnerchip[6] = findViewById(R.id.tvinnerchip7);
        // tvinnerchip2=(TextView)findViewById(R.id.tvinnerchip2);

        //==============================================================================================
        ivmiddlechip[0] = findViewById(R.id.ivmiddlechip1);
        ivmiddlechip[1] = findViewById(R.id.ivmiddlechip2);
        ivmiddlechip[2] = findViewById(R.id.ivmiddlechip3);
        ivmiddlechip[3] = findViewById(R.id.ivmiddlechip4);
        ivmiddlechip[4] = findViewById(R.id.ivmiddlechip5);
        ivmiddlechip[5] = findViewById(R.id.ivmiddlechip6);
        ivmiddlechip[6] = findViewById(R.id.ivmiddlechip7);
        //  ivmiddlechip2=(ImageView)findViewById(R.id.ivmiddlechip2);

        //==============================================================================================
        ivmiddlemain[0] = findViewById(R.id.ivmiddlemain1);
        ivmiddlemain[1] = findViewById(R.id.ivmiddlemain2);
        ivmiddlemain[2] = findViewById(R.id.ivmiddlemain3);
        ivmiddlemain[3] = findViewById(R.id.ivmiddlemain4);
        ivmiddlemain[4] = findViewById(R.id.ivmiddlemain5);
        ivmiddlemain[5] = findViewById(R.id.ivmiddlemain6);
        ivmiddlemain[6] = findViewById(R.id.ivmiddlemain7);
        //  ivmiddlemain2=(ImageView)findViewById(R.id.ivmiddlemain2);

        //==============================================================================================
        ivtoday[0] = findViewById(R.id.ivtoday1);
        ivtoday[1] = findViewById(R.id.ivtoday2);
        ivtoday[2] = findViewById(R.id.ivtoday3);
        ivtoday[3] = findViewById(R.id.ivtoday4);
        ivtoday[4] = findViewById(R.id.ivtoday5);
        ivtoday[5] = findViewById(R.id.ivtoday6);
        ivtoday[6] = findViewById(R.id.ivtoday7);
        // ivtoday2=(ImageView)findViewById(R.id.ivtoday2);

        //==============================================================================================
        tvday[0] = findViewById(R.id.tvday1);
        tvday[1] = findViewById(R.id.tvday2);
        tvday[2] = findViewById(R.id.tvday3);
        tvday[3] = findViewById(R.id.tvday4);
        tvday[4] = findViewById(R.id.tvday5);
        tvday[5] = findViewById(R.id.tvday6);
        tvday[6] = findViewById(R.id.tvday7);
        // tvday2=(TextView)findViewById(R.id.tvday2);
//===============================================================================================
        tomorrow[0] = findViewById(R.id.tvtodtom1);
        tomorrow[1] = findViewById(R.id.tvtodtom2);
        tomorrow[2] = findViewById(R.id.tvtodtom3);
        tomorrow[3] = findViewById(R.id.tvtodtom4);
        tomorrow[4] = findViewById(R.id.tvtodtom5);
        tomorrow[5] = findViewById(R.id.tvtodtom6);
        tomorrow[6] = findViewById(R.id.tvtodtom7);


//===================================================================================

        tvTittle = findViewById(R.id.tvTittle);

        tvTittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(38));
        tvTittle.setTypeface(c.typeface);

        txt_2xChip = findViewById(R.id.txt_2xChip);

        txt_2xChip.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(29));
        txt_2xChip.setTypeface(c.typeface);

        txt_Chip_11 = findViewById(R.id.txt_Chip_11);
        txt_Chip_11.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(29));
        txt_Chip_11.setTypeface(c.typeface);

        tx_or = findViewById(R.id.tx_or);

        tx_or.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(29));
        tx_or.setTypeface(c.typeface);

        ivlinetittle = findViewById(R.id.ivlinetittle);


        for (int i = 0; i < rewardChips.length; i++) {
            tvday[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(28));
            tvday[i].setTypeface(c.typeface, Typeface.BOLD_ITALIC);
            tvday[i].setVisibility(View.GONE);

            tvCongras[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(18));
            tvCongras[i].setTypeface(c.typeface, Typeface.BOLD_ITALIC);
            tvCongras[i].setVisibility(View.INVISIBLE);

            tomorrow[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(24));
            tomorrow[i].setTypeface(c.typeface, Typeface.BOLD_ITALIC);
//            tomorrow[i].setVisibility(View.INVISIBLE);

            tvinnerchip[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(20));
            tvinnerchip[i].setTypeface(c.typeface);
            tvinnerchip[i].setText("" + rewardChips[i] + " Chips");

            ivtoday[i].setVisibility(View.INVISIBLE);

        }


        //==============================================================================================

        btnchip = findViewById(R.id.btnchip);
        btnchip.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(25));
        btnchip.setTypeface(c.typeface);

        //==============================================================================================

        lnYouWin = findViewById(R.id.lnYouWin);

        tvyouwintittle = findViewById(R.id.tvyouwintittle);
        tvyouwintittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(28));
        tvyouwintittle.setTypeface(c.typeface);


        tvyouwinchip = findViewById(R.id.tvyouwinchip);
        tvyouwinchip.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(28));
        tvyouwinchip.setTypeface(c.typeface, Typeface.BOLD);

        tvchiptext = findViewById(R.id.tvchiptext);
        tvchiptext.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(28));
        tvchiptext.setTypeface(c.typeface);


        btncollect = findViewById(R.id.btncollect);
        btncollect.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(25));
        btncollect.setTypeface(c.typeface);
        btncollect.setOnClickListener(this);


        Bonus2X = findViewById(R.id.Bonus2X);
        Bonus2X.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(33));
        Bonus2X.setTypeface(c.typeface);
        Bonus2X.setOnClickListener(this);
//        Bonus2X.startAnimation(freechipanim);
        Bonus2X.setPadding(0, getwidth(18), getwidth(74), 0);

        //==============================================================================================
        spinner = findViewById(R.id.progressBar1);
        fl_loader = findViewById(R.id.fl_loader);
        fl_loader.setVisibility(View.VISIBLE);

        Handler handlers = new Handler();
        handlers.postDelayed(new Runnable() {
            @Override
            public void run() {
                fl_loader.setVisibility(View.GONE);
            }
        }, 3000);


    }

    private void DoExplode() {

        if (ivmiddlechip[index].getAnimation() != null) {
            ivmiddlechip[index].clearAnimation();
            ivmiddlechip[index].setVisibility(View.INVISIBLE);
        }
        final int timeToLive = 1000;
        final int NoofParticles = 10;
        final int setFadeOutTime = 250;
        new ParticleSystem(this, 4, R.drawable.dust, timeToLive)
                .setSpeedByComponentsRange(-0.025f, 0.025f, -0.06f, -0.08f)
                .setAcceleration(0.00001f, 30)
                .setInitialRotationRange(0, 360)
                .addModifier(new AlphaModifier(255, 0, 750, 1250))
                .addModifier(new ScaleModifier(0.5f, 1.5f, 0, 1250))
                .oneShot(ivmiddlechip[index], 4);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        btnchip.startAnimation(shakeAnimation);
                        for (int i = 0; i <= day; i++) {
                            new ParticleSystem(DailyBonus3.this, NoofParticles, R.drawable.coin_icon, timeToLive)
                                    .setSpeedByComponentsRange(-0.2f, 0.2f, -0.2f, 0.04f)
                                    .setAcceleration(0.00006f, 90)
                                    .setInitialRotationRange(0, 360)
                                    .setRotationSpeed(150)
                                    .setFadeOut(setFadeOutTime)
                                    .addModifier(new ScaleModifier(0f, 0.75f, 0, 750))
                                    .oneShot(ivmiddlechip[index], NoofParticles);
                        }

                    }
                });
            }
        }, 150);


    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
        } catch (Exception e) {
            e.printStackTrace();
        }


        c.payment_Handler = handler;
        c.con = this;
        c.activity = DailyBonus3.this;

        c.Chips = PreferenceManager.getChips();
        btnchip.setText("" + c.numDifferentiation(PreferenceManager.getChips()));
    }

    private void setdata() {

        //==========================================================================================
        tvyouwinchip.setText(" " + rewardChips[index]);
        for (int i = 0; i <= day; i++) {

            if (i == index) {

                tomorrow[i].setVisibility(View.VISIBLE);
                tomorrow[i].setText("Today");
                if (i == 6) {
                    tomorrow[0].setVisibility(View.VISIBLE);
                    tomorrow[0].setText("Tomorrow");
                } else {
                    tomorrow[i + 1].setVisibility(View.VISIBLE);
                    tomorrow[i + 1].setText("Tomorrow");
                }

                ivmiddlechip[i].startAnimation(freechipanim);
                tvCongras[i].setVisibility(View.VISIBLE);
                if (i == 0) {
                    ivmiddlemain[i].setBackgroundResource(R.drawable.dailyrewardbk2);
                } else if (i == 6) {
                    ivmiddlemain[i].setBackgroundResource(R.drawable.dailyrewardbk2_5);
                } else {
                    ivmiddlemain[i].setBackgroundResource(R.drawable.dailyrewardbk2234);
                }
                tvinnerchip[i].setBackgroundResource(R.drawable.daily_btn);
                chipIndex = i;
                tvinnerchip[i].setOnClickListener(this);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) tvinnerchip[i].getLayoutParams();
                params.width = c.getwidth1(160);
                params.height = c.getheight1(50);
                params.rightMargin = c.getwidth1(9);
                params.bottomMargin = c.getheight1(5);
                params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

            } else {
                tomorrow[i].setVisibility(View.VISIBLE);
                tomorrow[i].setText("Collected");

//                if(i==0){
//                    ivmiddlemain[i].setBackgroundResource(R.drawable.dailyrewardbk2);
//                }else{
//
//                    ivmiddlemain[i].setBackgroundResource(R.drawable.dailyrewardbk2234);
//                }
                lnfrmreward[i].setAlpha(.6f);
//                tvinnerchip[i].setBackgroundResource(R.drawable.d_bkgchips_select);


            }

        }


        //==========================================================================================
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        clearmemory();

    }

    private void clearmemory() {


        for (int i = 0; i < 7; i++) {

            ivmiddlechip[i].setBackgroundResource(0);
            ivtoday[i].setBackgroundResource(0);
            ivmiddlemain[i].setBackgroundResource(0);
            tvinnerchip[i].setBackgroundResource(0);

        }
        ivlinetittle.setBackgroundResource(0);
        btnchip.setBackgroundResource(0);
        btncollect.setBackgroundResource(0);


        if (ivmiddlechip[index].getAnimation() != null) {
            ivmiddlechip[index].clearAnimation();
        }

        if (btnchip.getAnimation() != null) {
            btnchip.clearAnimation();
        }

        if (timer != null) {
            timer.cancel();
        }

    }

    private void ChocolateListener() {

    }

    @Override
    public void onClick(View v) {

        if (v == Bonus2X) {
            if (PreferenceManager.isInternetConnected()) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                    showInfoDialog("Alert", "Reward video is Not Loading...");
                }
            } else {
                showInfoDialog("Alert", "No Internet Connection.");
            }

        } else {


            DoExplode();

            Music_Manager.play_MagicCollect();

            c.DbcollectCount++;
            if (c.DbcollectCount > 6) {
                c.DbcollectCount = 0;
            }
            btncollect.setEnabled(false);

            PreferenceManager.SetDBCollectCount(c.DbcollectCount);

            final long beforchip = PreferenceManager.getChips();
            final long increment = WinChip / 4;

            final long bfrchp = beforchip;
            final long incr = increment;

            c.Chips = PreferenceManager.getChips() + WinChip;
            PreferenceManager.setChips(c.Chips);
            PreferenceManager.SetDBCollectCount(c.DbcollectCount);
            PreferenceManager.SetDBCollectDay(c.currentDay);
            sqLiteManager.updateChip(PreferenceManager.get_id(), (int) WinChip, true);


            // btncollect.setEnabled(false);
            // btncollect.setAlpha(.5f);
            PreferenceManager.SetDBcollected(true);
            btnchip.setText("" + c.numDifferentiation(PreferenceManager.getChips()));

            Calendar c = Calendar.getInstance();
            int currentDate = c.get(Calendar.DATE);

            PreferenceManager.SetDBDate(currentDate);
            PreferenceManager.SetPendingChips(0);


//            startTimer(beforchip,increment);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (btnchip.getAnimation() != null) {
                        btnchip.clearAnimation();
                    }

                }
            }, 1500);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
                    finish();


                }
            }, 1800);


            if (Dashboard.handler != null) {
                Message me = new Message();
                me.what = 7777;
                me.obj = WinChip;
                Dashboard.handler.sendMessage(me);
            }

        }

    }


    private void showInfoDialog(String DialogTitle, String Message) {
        // TODO Auto-generated method stub
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "font/PoetsenOne-Regular.ttf");
        LinearLayout.LayoutParams lin;

        if (ConfirmationDialogInfo != null || (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing())) {
            if (ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.dismiss();
            }
            ConfirmationDialogInfo = null;
        }

        ConfirmationDialogInfo = new Dialog(DailyBonus3.this,
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

    private void startTimer(final long remainingSeconds, final long increment) {


        second = remainingSeconds;
        Logger.print("MSG Show Message Timer => 2 " + second);
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();


        try {
            final Timer finalTimer = timer;
            timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    DailyBonus3.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            if (second <= c.Chips) {
                                btnchip.setText("" + c.numDifferentiation(second));
                            } else {
                                btnchip.setText("" + c.numDifferentiation(PreferenceManager.getChips()));
                                return;
                            }

                        }
                    });
                    second += increment;

                }
            }, 300, 100);
        } catch (Exception e) {
            e.printStackTrace();
            btnchip.setVisibility(View.GONE);
            btnchip.setText("");
            if (timer != null) {
                timer.cancel();
            }
        }


    }

    @DebugLog
    private int getheight(int val) {
        // TODO Auto-generated method stub
        return myData.height * val / 720;
    }

    @DebugLog
    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return myData.width * val / 1280;
    }

    @Override
    public void onBackPressed() {
        if (isLoading) {
            isLoading = false;
        }
    }

}
