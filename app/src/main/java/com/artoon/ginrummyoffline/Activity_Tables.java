package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import DataStore.Item_Table;
import adapters.Adapter_GridView;
import utils.C;
import utils.PreferenceManager;
import utils.ResponseCodes;

public class Activity_Tables extends Activity implements OnClickListener, OnItemClickListener {

    GridView TableList;
    ArrayList<Item_Table> TableData = new ArrayList<>();
    Adapter_GridView tableAdapter2;
    ImageView BackButton, profilepic;
    Dialog buddyDialog;
    Handler handler;
    String TableId;
    TextView Userchips, nullData, title, title_betval, title_chips, table_buy_chips;
    Button buyBtn;
    C c = C.getInstance();

    Animation buttonClick;

    LinearLayout checkLinear;
    RadioGroup radioGroup;
    RadioGroup radioGroup2;
    RadioButton normalRadio, noKnockRadio;
    TextView normalText, noKnockText;
    int numberOfPlayers = 2;
    Dialog dialog1;

    Random rano;
    int[] point = {10, 10, 50, 50, 150, 150, 250, 250};
    TextView[] tv_mode = new TextView[4];
    RadioButton[] rb_mode = new RadioButton[4];
    private TextView tv_player_text, tv_mode_text;
    private int multiplier = 3;
    private Dialog ConfirmationDialogInfo;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        c.height = PreferenceManager.getheight();
        c.width = PreferenceManager.getwidth();
        buttonClick = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buttonpressed);
        c.typeface = Typeface.createFromAsset(getAssets(), "font/PoetsenOne-Regular.ttf");
        rano = new Random();

        TableList = findViewById(R.id.activity_table_list);
        TableList.setOnItemClickListener(this);
        BackButton = findViewById(R.id.activity_table_backbtn);
        BackButton.setOnClickListener(this);

        findViewByIds();
        LoadTableList();
        LayoutScreen();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dialog1 != null && dialog1.isShowing()) {

            dialog1.dismiss();

        }

    }

    private void LayoutScreen() {

        FrameLayout.LayoutParams frm;
        LinearLayout.LayoutParams lin;
        RelativeLayout.LayoutParams rel;

        int tw, th;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.activity_table_buy_chip).getLayoutParams();

        frm.height = (getHight(81, c.height));
        frm.width = (getwidth(244, c.width));
        frm.topMargin = (getHight(20, c.height));
        frm.leftMargin = (getwidth(20, c.width));

        rel = (RelativeLayout.LayoutParams) findViewById(R.id.table_firstbar).getLayoutParams();
        th = (getHight(100, c.height));
        rel.height = th;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.activity_table_backbtn).getLayoutParams();
        frm.rightMargin = (getwidth(15, c.width));
        frm.topMargin = (getHight(10, c.height));
        frm.width = getwidth(80);
        frm.height = getheight(70);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.private_table_pp).getLayoutParams();
        th = tw = (getwidth(80, c.width));
        lin.leftMargin = (getwidth(20, c.width));
        lin.width = tw;
        lin.height = th;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.layout_Username).getLayoutParams();
        lin.leftMargin = (getwidth(20, c.width));

        lin = (LinearLayout.LayoutParams) findViewById(R.id.activity_table_username).getLayoutParams();
        tw = (getwidth(300, c.width));
        lin.width = tw;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.activity_table_buybtn).getLayoutParams();
        tw = (getwidth(102, c.width));
        th = (getHight(38, c.height));
        lin.width = tw;
        lin.height = th;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.chip_image).getLayoutParams();
        tw = (getwidth(31, c.width));
        th = (getHight(31, c.height));
        lin.topMargin = (getHight(4, c.height));
        lin.width = tw;
        lin.height = th;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.activity_table_title).getLayoutParams();
        frm.topMargin = getHight(10, c.height);
        frm.width = getwidth(900);
        frm.height = getheight(70);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.lineee).getLayoutParams();
        lin.width = getwidth(400, c.width);
        lin.height = getHight(4, c.height);
        lin.topMargin = getHight(10, c.height);

        rel = (RelativeLayout.LayoutParams) checkLinear.getLayoutParams();
        rel.topMargin = c.height * 90 / 720;
        rel.bottomMargin = c.height * 10 / 720;

        int w = c.width * 40 / 1280;
        int h = w;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, h);
        lp.rightMargin = c.width * 5 / 1280;
        normalRadio.setLayoutParams(lp);

        lp = new LinearLayout.LayoutParams(w, h);
        lp.rightMargin = c.width * 5 / 1280;
        lp.leftMargin = c.width * 30 / 1280;
        noKnockRadio.setLayoutParams(lp);

        for (int i = 0; i < rb_mode.length; i++) {
            w = c.width * 40 / 1280;
            h = w;
            lp = new LinearLayout.LayoutParams(w, h);
            lp.rightMargin = c.width * 5 / 1280;
            if (i != 0) {
                lp.leftMargin = c.width * 30 / 1280;
            }
            rb_mode[i].setLayoutParams(lp);


        }

        rel = (RelativeLayout.LayoutParams) findViewById(R.id.table_thirdbar).getLayoutParams();
        rel.topMargin = c.height * 150 / 720;
        rel.width = getwidth(1100);

        TableList.setHorizontalSpacing(getwidth(15));
        TableList.setVerticalSpacing(getheight(15));

        lin = (LinearLayout.LayoutParams) radioGroup2.getLayoutParams();
        lin.leftMargin = getwidth(30);

    }

    public int getwidth(int val, int widthT) {
        return (widthT * val) / 1280;
    }

    public int getHight(int val, int screen_hight) {
        return (screen_hight * val) / 720;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void findViewByIds() {

        table_buy_chips = findViewById(R.id.activity_table_buy_chip);
        table_buy_chips.setOnClickListener(this);
        title_betval = findViewById(R.id.activity_table_bootpricetitle);
        title_chips = findViewById(R.id.activity_table_maxbettitle);

        profilepic = findViewById(R.id.private_table_pp);
        title = findViewById(R.id.activity_table_title);
        Userchips = findViewById(R.id.activity_table_userchips);
        buyBtn = findViewById(R.id.activity_table_buybtn);
        nullData = findViewById(R.id.nullData);

        if (c.Chips < 1000000) {
            table_buy_chips.setText("" + c.formatter.format(c.Chips));
        } else {
            Userchips.setText("" + c.numDifferentiation(c.Chips));
        }

        checkLinear = findViewById(R.id.check_linear);
        radioGroup = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        normalRadio = findViewById(R.id.radio_normal);
        normalText = findViewById(R.id.normal_text);
        noKnockRadio = findViewById(R.id.radio_noknock);
        noKnockText = findViewById(R.id.noknock_text);

        buyBtn.setOnClickListener(this);
        normalText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                normalRadio.setChecked(true);
            }
        });
        noKnockText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                noKnockRadio.setChecked(true);
            }
        });

        table_buy_chips.setTypeface(c.typeface);
        title_betval.setTypeface(c.typeface);
        title_chips.setTypeface(c.typeface);

        title.setTypeface(c.typeface);
        Userchips.setTypeface(c.typeface);
        buyBtn.setTypeface(c.typeface);
        normalText.setTypeface(c.typeface);
        noKnockText.setTypeface(c.typeface);

        table_buy_chips.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(26, c.width));
        TextView activity_table_username = findViewById(R.id.activity_table_username);
        activity_table_username.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28, c.width));
        buyBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(22, c.width));
        buyBtn.setPadding(getwidth(40, c.width), 0, 0, getHight(6, c.height));
        Userchips.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(22, c.width));
        Userchips.setPadding(getwidth(10, c.width), getHight(10, c.height), getwidth(10, c.width),
                getHight(10, c.height));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(50, c.width));
        TextView activity_table_nametitle = findViewById(R.id.activity_table_nametitle);
        activity_table_nametitle.setPadding(getwidth(60, c.width), 0, 0, 0);
        activity_table_nametitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28, c.width));
        activity_table_nametitle.setTypeface(c.typeface);
        title_betval.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28, c.width));
        title_chips.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28, c.width));

        normalText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(35, c.width));
        noKnockText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(35, c.width));

        tv_player_text = findViewById(R.id.tv_player_text);
        tv_mode_text = findViewById(R.id.tv_mode_text);

        tv_player_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(35, c.width));
        tv_mode_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(35, c.width));

        tv_player_text.setTypeface(c.typeface);
        tv_mode_text.setTypeface(c.typeface);

        for (int i = 0; i < tv_mode.length; i++) {

            tv_mode[i] = findViewById(getResources().getIdentifier("tv_mode_" + i, "id", getPackageName()));
            rb_mode[i] = findViewById(getResources().getIdentifier("rb_mode_" + i, "id", getPackageName()));
            tv_mode[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(35, c.width));
            tv_mode[i].setTypeface(c.typeface);
            final int finalI = i;
            tv_mode[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    rb_mode[finalI].setChecked(true);
                }
            });


        }

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                if (checkedId == normalRadio.getId()) {
                    Music_Manager.buttonclick();
                    numberOfPlayers = 2;


                } else if (checkedId == noKnockRadio.getId()) {
                    Music_Manager.buttonclick();
                    numberOfPlayers = 3;

                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                if (checkedId == rb_mode[0].getId()) {
                    Music_Manager.buttonclick();
                    c.gameType = 0;

                } else if (checkedId == rb_mode[1].getId()) {
                    Music_Manager.buttonclick();
                    c.gameType = 1;

                } else if (checkedId == rb_mode[2].getId()) {
                    Music_Manager.buttonclick();
                    c.gameType = 3;

                } else if (checkedId == rb_mode[3].getId()) {
                    Music_Manager.buttonclick();
                    c.gameType = 2;

                }
            }
        });

        rb_mode[0].setChecked(true);
        c.gameType = 0;
        rb_mode[1].setChecked(false);
        rb_mode[2].setChecked(false);
        rb_mode[3].setChecked(false);


        // if (Locale.getDefault().getLanguage().contains("fr")) {
        // buyBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP,
        // pixelsToSp(buyBtn.getTextSize()) * 0.85f);
        // }
    }

    @Override
    protected void onResume() {


        c.payment_Handler = this.handler;
        c.con = this;
        c.activity = Activity_Tables.this;


        if (c.Chips < 1000000) {
            table_buy_chips.setText("" + c.formatter.format(c.Chips));
        } else {
            table_buy_chips.setText("" + c.numDifferentiation(c.Chips));
        }

        super.onResume();


    }

    private void showInfoDialog2(String DialogTitle, String Message) {
        // TODO Auto-generated method stub

        LinearLayout.LayoutParams lin;

        if (ConfirmationDialogInfo != null || (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing())) {
            if (ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.dismiss();
            }
            ConfirmationDialogInfo = null;
        }

        ConfirmationDialogInfo = new Dialog(this,
                R.style.Theme_Transparent);
        ConfirmationDialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialogInfo.setContentView(R.layout.dialog);
        ConfirmationDialogInfo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView title = ConfirmationDialogInfo
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(c.typeface);
        title.setText(DialogTitle);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);

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

        btnOne.setOnClickListener(new OnClickListener() {
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

    private void showInfoDialog(String DialogTitle, final String message) {
        // TODO Auto-generated method stub

        LinearLayout.LayoutParams lin;

        if (ConfirmationDialogInfo != null || (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing())) {
            if (ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.dismiss();
            }
            ConfirmationDialogInfo = null;
        }

        ConfirmationDialogInfo = new Dialog(Activity_Tables.this,
                R.style.Theme_Transparent);
        ConfirmationDialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialogInfo.setContentView(R.layout.dialog);
        ConfirmationDialogInfo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView title = ConfirmationDialogInfo
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(c.typeface);
        title.setText(DialogTitle);

        TextView chip = ConfirmationDialogInfo.findViewById(R.id.txt_Chip_11);
        chip.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(26));
        chip.setTypeface(c.typeface);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.iv_chips_11).getLayoutParams();
        lin.height = getwidth(50);
        lin.width = getwidth(50);

        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.mainpopup).getLayoutParams();
        frm.height = getheight(450);
        frm.width = getwidth(900);

        LinearLayout ll_chip_outof = ConfirmationDialogInfo.findViewById(R.id.ll_chip_outof);
        ll_chip_outof.setVisibility(View.VISIBLE);

        TextView message1 = ConfirmationDialogInfo
                .findViewById(R.id.tvMessage);
        message1.setTypeface(c.typeface);
        message1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

        Button btnOne = ConfirmationDialogInfo.findViewById(R.id.button1);
        btnOne.setTypeface(c.typeface);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("Buy Chips");

        Button btnTwo = ConfirmationDialogInfo.findViewById(R.id.button2);
        btnTwo.setTypeface(c.typeface);
        btnTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnTwo.setText("Watch Video");

        Button btnThree = ConfirmationDialogInfo.findViewById(R.id.button3);
        btnThree.setTypeface(c.typeface);
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
                R.id.ll_chip_outof).getLayoutParams();
        lin.topMargin = getheight(30);
        lin.leftMargin = getwidth(90);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.tvMessage).getLayoutParams();
        lin.topMargin = getheight(50);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.llButtons).getLayoutParams();
        lin.topMargin = getheight(5);

        message1.setText(message);

        btnOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                Music_Manager.buttonclick();
                Intent inprofile = new Intent(getApplication(), Coin_per.class);
                startActivity(inprofile);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
                ConfirmationDialogInfo.dismiss();
                finish();
            }
        });

        btnTwo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                try {
                    Music_Manager.buttonclick();
                    ConfirmationDialogInfo.dismiss();
                    if (PreferenceManager.isInternetConnected()) {

                    } else {
                        showInfoDialog2("No Internet", "Please check your Internet Connection.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showInfoDialog2("Alert", "Reward video is Loading...");
                }
            }
        });

        btnClose.setOnClickListener(new OnClickListener() {
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

    private int getheight(int val) {
        // TODO Auto-generated method stub
        return c.height * val / 720;
    }

    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return c.width * val / 1280;
    }

    protected void LoadTableList() {

        TableData = new ArrayList<>();

        try {
            String stadata = loadJSONFromAsset();
            JSONObject obj = new JSONObject(stadata);
            JSONArray array = obj.getJSONArray("data");

            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    Item_Table item = new Item_Table(array.getJSONObject(i));
                    TableData.add(item);
                }
                if (TableData.size() <= 0) {
                    nullData.setVisibility(View.VISIBLE);
                } else {
                    nullData.setVisibility(View.GONE);
                }
                tableAdapter2 = new Adapter_GridView(this, R.layout.adapter_table_grid, TableData);
                TableList.setAdapter(tableAdapter2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String loadJSONFromAsset() {

        String json = null;

        try {
            InputStream is = this.getAssets().open("tutorial.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(0, R.anim.activity_gone);
    }

    @Override
    public void onClick(View v) {

        if (v == BackButton) {
            Music_Manager.buttonclick();
            BackButton.startAnimation(buttonClick);
            finish();
            overridePendingTransition(R.anim.none, R.anim.to_lefttoright);

        }

        if (v == table_buy_chips) {
            Music_Manager.buttonclick();
            buyBtn.setClickable(false);
            table_buy_chips.startAnimation(buttonClick);

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();
        Music_Manager.buttonclick();

        if (c.Chips > 500) {

            if ((position == 0 && c.Chips >= 500) || (c.Chips >= (TableData.get(position).getMaxBootValue() * multiplier))) {

                c.initialBootValue = getbootvalOftable(position);
                PreferenceManager.setBootvalue(c.initialBootValue);
                c.tableindex = position;
                PreferenceManager.setgameType(1);

                PreferenceManager.setNumberOfPlayers(numberOfPlayers);
//                if(PreferenceManager.getNumberOfPlayers() == 2){
                PreferenceManager.setPlayerPoints(PreferenceManager.getpoints(), c.gameType, PreferenceManager.getNumberOfPlayers());
//                }else{
//                    PreferenceManager.set3PlayerPoints(PreferenceManager.getpoints(), c.gameType);
//                }


                Intent inn44 = new Intent(getApplicationContext(), PlayScreen2.class);
                inn44.putExtra("totalPlayer", numberOfPlayers);
                startActivity(inn44);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
                finish();

            } else {

                showInfoDialog("Out Of Chips", getResources().getString(R.string.You_dont_have_sufficient_chips_to_play_on_this_table));

            }

        } else {

            showInfoDialog("Out Of Chips", getResources().getString(R.string.You_dont_have_sufficient_chips_to_play_on_this_table));

        }


//        if (TableData.get(position).getMaxBootValue()*3 < c.Chips) {
//            finish();
//            overridePendingTransition(0, R.anim.activity_gone);
//        }

    }

    private int getbootvalOftable(int position) {

        c.Chips = PreferenceManager.getChips();

        if (position == 0) {
            c.initialRoundPoint = point[position];
            PreferenceManager.setpoints(c.initialRoundPoint);
            if (c.Chips > 500 * multiplier) {
                int[] boot = {500, 1000, 1500, 2000};
                return getBootValue(boot);
            } else {
                return 500;
            }


        } else if (position == 1) {
            c.initialRoundPoint = point[position];
            PreferenceManager.setpoints(c.initialRoundPoint);
            if (c.Chips > 3000 * multiplier) {
                int[] boot = {3000, 4000, 5000, 6000};
                return getBootValue(boot);

            } else {
                return 3000;
            }

        } else if (position == 2) {

            c.initialRoundPoint = point[position];
            PreferenceManager.setpoints(c.initialRoundPoint);
            if (c.Chips > 7000 * multiplier) {
                int[] boot = {7000, 9500, 12000, 14500};
                return getBootValue(boot);

            } else {
                return 7000;
            }

        } else if (position == multiplier) {

            c.initialRoundPoint = point[position];
            PreferenceManager.setpoints(c.initialRoundPoint);
            if (c.Chips > 15000 * 3) {
                int[] boot = {15000, 20000, 25000, 30000};
                return getBootValue(boot);

            } else {
                return 15000;
            }

        } else if (position == 4) {

            c.initialRoundPoint = point[position];
            PreferenceManager.setpoints(c.initialRoundPoint);
            if (c.Chips > 40000 * multiplier) {
                int[] boot = {40000, 50000, 60000, 70000};
                return getBootValue(boot);


            } else {
                return 40000;
            }

        } else if (position == 5) {

            c.initialRoundPoint = point[position];
            PreferenceManager.setpoints(c.initialRoundPoint);
            if (c.Chips > 75000 * multiplier) {
                int[] boot = {75000, 100000, 125000, 150000};
                return getBootValue(boot);

            } else {
                return 75000;
            }

        } else if (position == 6) {

            c.initialRoundPoint = point[position];
            PreferenceManager.setpoints(c.initialRoundPoint);
            if (c.Chips > 200000 * multiplier) {
                int[] boot = {200000, 250000, 300000, 350000};
                return getBootValue(boot);

            } else {
                return 200000;
            }

        } else if (position == 7) {
            c.initialRoundPoint = point[position];
            PreferenceManager.setpoints(c.initialRoundPoint);
            if (c.Chips > 400000 * multiplier) {
                int[] boot = {400000, 500000, 600000, 700000};

                return getBootValue(boot);
            } else {
                return 400000;
            }

        }
        return 0;
    }

    private int getBootValue(int[] boot) {
        int bootvalue = boot[0];
        for (int i = boot.length - 1; i >= 0; i--) {
            if (PreferenceManager.getChips() > boot[i] * 3) {
                return boot[i];
            }
        }
        return bootvalue;
    }

    private int getrandom(int i) {

        return rano.nextInt(i);

    }


}