package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

import adapters.Adapter_ScoreBoard;
import de.hdodenhof.circleimageview.CircleImageView;
import utils.C;
import utils.Logger;
import utils.Parameters;
import utils.PreferenceManager;
import utils.ResponseCodes;

public class Scorboard extends Activity implements OnClickListener {

    public Handler handler;
    String[] ProfilePic;
    String[] UserName;
    String[] total;
    String[] _ids;
    ListView score_listview;

    Adapter_ScoreBoard adapter_scorBoard;
    CountDownTimer CDTimer;

    C c = C.getInstance();
    CircleImageView image1, image2, image3;
    TextView tot1, tot2, tot3, txtname1, txtname2, txtname3;
    int Screen_Hieght;
    int Screen_Width;
    boolean isShowTimer = false;
    TextView tvRoundNumber, tvSecondsText, nextroundtext, tvPointDescription;
    Typeface typeface;
    TextView tvTitle, tvRoundText, tvScoreText;

    //    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//
//        Logger.print("MMMMMMMMMMMMMMM>>> onRestoreInstanceState ::::Called");
//
//        Logger.print("MMMMMMMMMMMMMMM>>> Scorboard>>> Background issue Genrated 2222...");
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
    LinearLayout llThirdUserInfo;
    Button btnClose;
    boolean isRoundStarted = false;
    int userIndex;
    private ImageView ivUserThreeYouTag, ivUserTwoYouTag, ivUserOneYouTag, ivVerticleLine3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);
        c.height = Screen_Hieght = PreferenceManager.getheight();
        c.width = Screen_Width = PreferenceManager.getwidth();
        c.typeface = Typeface.createFromAsset(getAssets(), "font/PoetsenOne-Regular.ttf");
        Screen_Hieght = c.height;
        Screen_Width = c.width;
        typeface = Typeface.createFromAsset(getAssets(),
                "font/PoetsenOne-Regular.ttf");

        isShowTimer = getIntent().getBooleanExtra("showTimer", false);

        findViewByIds();
        setScreen();

        try {

            JSONArray jArray = new JSONArray(getIntent().getStringExtra("A1"));
            SETDATA(jArray);
            Logger.print("JJJJJJJJJJJJJJJJJJJJJJJJJJ:" + jArray);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        initHandler();

    }

    private void scrollMyListViewToBottom() {
        score_listview.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                score_listview.setSelection(adapter_scorBoard.getCount() - 1);
            }
        });
    }

    private void findViewByIds() {

        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));
        tvTitle.setTypeface(typeface);

        tvRoundText = findViewById(R.id.tvRoundText);
        tvRoundText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvRoundText.setTypeface(typeface);

        image1 = findViewById(R.id.ivUserOne);
        image2 = findViewById(R.id.ivUserTwo);
        image3 = findViewById(R.id.ivUserThree);

        ivUserOneYouTag = findViewById(R.id.ivUserOneYouTag);
        ivUserTwoYouTag = findViewById(R.id.ivUserTwoYouTag);
        ivUserThreeYouTag = findViewById(R.id.ivUserThreeYouTag);

        txtname1 = findViewById(R.id.ivUserNameOne);
        txtname1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        txtname1.setTypeface(typeface);

        txtname2 = findViewById(R.id.ivUserNameTwo);
        txtname2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        txtname2.setTypeface(typeface);

        txtname3 = findViewById(R.id.ivUserNameThree);
        txtname3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        txtname3.setTypeface(typeface);

        tvScoreText = findViewById(R.id.tvScoreText);
        tvScoreText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvScoreText.setTypeface(typeface);

        tot1 = findViewById(R.id.tvUserOneScore);
        tot1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(44));
        tot1.setTypeface(typeface);

        tot2 = findViewById(R.id.tvUserTwoScore);
        tot2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(44));
        tot2.setTypeface(typeface);

        tot3 = findViewById(R.id.tvUserThreeScore);
        tot3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(44));
        tot3.setTypeface(typeface);


        tvPointDescription = findViewById(R.id.tvPointDescription);
        tvPointDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvPointDescription.setTypeface(typeface);

        // int p = getHight(20);
        score_listview = findViewById(R.id.Sco_listview);
        //score_listview.setPadding(p, p, p, p);

        nextroundtext = findViewById(R.id.nexrroundtext);
        nextroundtext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        nextroundtext.setTypeface(typeface);

        tvRoundNumber = findViewById(R.id.tvRoundNumber);
        tvRoundNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvRoundNumber.setTypeface(typeface);

        tvSecondsText = findViewById(R.id.tvSecondsText);
        tvSecondsText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvSecondsText.setTypeface(typeface);

        ivVerticleLine3 = findViewById(R.id.ivVerticleLine3);

        llThirdUserInfo = findViewById(R.id.llThirdUserInfo);

        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);


        /*if (isShowTimer) {
            nextroundtext.setVisibility(View.VISIBLE);
            tvRoundNumber.setVisibility(View.VISIBLE);
            tvSecondsText.setVisibility(View.VISIBLE);
            showTimer();
        } else {*/
        nextroundtext.setVisibility(View.INVISIBLE);
        tvRoundNumber.setVisibility(View.INVISIBLE);
        tvSecondsText.setVisibility(View.INVISIBLE);
        //}

    }

    private void setScreen() {
        // TODO Auto-generated method stub
        FrameLayout.LayoutParams frm;
        LinearLayout.LayoutParams lin;
        int w, h;

//        frm = (FrameLayout.LayoutParams) findViewById(R.id.frmMainLayout).getLayoutParams();
//        frm.width = getwidth(1280);
//        frm.height = getHight(720);


        lin = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
        lin.topMargin = getHight(10);
        lin.height = getHight(70);
        lin.width = getwidth(900);


        lin = (LinearLayout.LayoutParams) findViewById(R.id.llUserInfoRowContainer).getLayoutParams();
        lin.topMargin = getHight(20);

        w = getwidth(130);
        frm = (FrameLayout.LayoutParams) image1.getLayoutParams();
        frm.width = frm.height = w;

        frm = (FrameLayout.LayoutParams) image2.getLayoutParams();
        frm.width = frm.height = w;

        frm = (FrameLayout.LayoutParams) image3.getLayoutParams();
        frm.width = frm.height = w;

        w = getwidth(80);
        h = w * 70 / 80;
        frm = (FrameLayout.LayoutParams) ivUserOneYouTag.getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.leftMargin = w * 2 / 80;
//        frm.topMargin = h * 5 / 70;

        frm = (FrameLayout.LayoutParams) ivUserTwoYouTag.getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.leftMargin = w * 2 / 80;
//        frm.topMargin = h * 5 / 70;

        frm = (FrameLayout.LayoutParams) ivUserThreeYouTag.getLayoutParams();
        frm.width = w;
        frm.height = h;
        frm.leftMargin = w * 2 / 80;
//        frm.topMargin = h * 5 / 70;


        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivLineSep1).getLayoutParams();
        lin.bottomMargin = getHight(16);
        lin.topMargin = getHight(6);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivLineSep2).getLayoutParams();
        lin.topMargin = getHight(6);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.Sco_listview).getLayoutParams();
        lin.height = getHight(320);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvRoundNumber)
                .getLayoutParams();
        lin.width = getwidth(40);
        lin.height = getwidth(40);

        frm = (FrameLayout.LayoutParams) btnClose.getLayoutParams();
        frm.width = getwidth(80);
        frm.height = getwidth(70);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivVerticleLine1).getLayoutParams();
        frm.height = getHight(680);
        frm.leftMargin = getwidth(124);
        frm.topMargin = getHight(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivVerticleLine2).getLayoutParams();
        frm.height = getHight(680);
        frm.rightMargin = getwidth(124);
        frm.topMargin = getHight(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivVerticleLine3).getLayoutParams();
        frm.height = getHight(680);
        frm.leftMargin = getwidth(256);
        frm.topMargin = getHight(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.llRoundTimerText).getLayoutParams();
        frm.height = getHight(60);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvPointDescription).getLayoutParams();
        frm.bottomMargin = getHight(10);

    }

    public int getwidth(int val) {
        return (Screen_Width * val) / 1280;
    }

    public int getHight(int val) {
        return (Screen_Hieght * val) / 720;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub

        super.onDestroy();

        if (CDTimer != null) {

            CDTimer.cancel();

        }

    }

    private void initHandler() {
        // TODO Auto-generated method stub

        handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                // TODO Auto-generated method stub


                if (msg.what == ResponseCodes.StartDealingResp
                        || msg.what == ResponseCodes.CollectBootValueResp) {

                    finish();
                    overridePendingTransition(0, android.R.anim.slide_out_right);
                    isRoundStarted = true;

                }

                if (msg.what == ResponseCodes.RoundTimerStartResp) {

                    isRoundStarted = false;

                }

                return false;

            }

        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (handler == null) {
            initHandler();
        }
        c.payment_Handler = this.handler;

        c.con = this;
        c.activity = Scorboard.this;

    }

    public String loadJSONFromAsset() {

        String json = null;

        try {

            InputStream is = getAssets().open("temp.json");

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

    public String getProUserName(String name) {
        try {
            String names[] = name.split(" ");

            if (names.length <= 1)
                return name;
            else
                return names[0];

        } catch (Exception e) {

            e.printStackTrace();

        }

        return name;
    }

    public void SETDATA(JSONArray str) {
        // TODO Auto-generated method stub
        FrameLayout.LayoutParams frm;
        JSONArray jarraArray = new JSONArray();
        jarraArray = str;
        ProfilePic = new String[jarraArray.length()];
        UserName = new String[jarraArray.length()];
        total = new String[jarraArray.length()];
        _ids = new String[jarraArray.length()];
        JSONArray arra1 = new JSONArray();
        JSONArray newScoreArray = new JSONArray();
        JSONArray no = new JSONArray();
        int[] seatIndexes = new int[jarraArray.length()];

        if (jarraArray.length() == 3) {
            ivVerticleLine3.setVisibility(View.VISIBLE);
            llThirdUserInfo.setVisibility(View.VISIBLE);
            tot3.setVisibility(View.VISIBLE);

            frm = (FrameLayout.LayoutParams) findViewById(R.id.ivVerticleLine1).getLayoutParams();
            frm.height = getHight(680);
            frm.leftMargin = getwidth(124);
            frm.topMargin = getHight(10);

            frm = (FrameLayout.LayoutParams) findViewById(R.id.ivVerticleLine2).getLayoutParams();
            frm.height = getHight(680);
            frm.rightMargin = getwidth(124);
            frm.topMargin = getHight(10);
        } else {
            ivVerticleLine3.setVisibility(View.INVISIBLE);
            llThirdUserInfo.setVisibility(View.GONE);
            tot3.setVisibility(View.GONE);

            frm = (FrameLayout.LayoutParams) findViewById(R.id.ivVerticleLine1).getLayoutParams();
            frm.height = getHight(680);
            frm.leftMargin = getwidth(320);
            frm.topMargin = getHight(10);

            frm = (FrameLayout.LayoutParams) findViewById(R.id.ivVerticleLine2).getLayoutParams();
            frm.height = getHight(680);
            frm.leftMargin = getwidth(190);
            frm.topMargin = getHight(10);
        }

        int no1 = 1;

        try {

            for (int j = 0; j < jarraArray.getJSONObject(0)
                    .getJSONArray(Parameters.points).length(); j++) {

                no.put(no1);
                no1++;

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            if (jarraArray.getJSONObject(0).has("st")) {
                newScoreArray.put(no);
            } else {
                arra1.put(no);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        for (int k = 0; k < jarraArray.length(); k++) {

            try {

                if (jarraArray.getJSONObject(k).length() > 0) {


                    if (jarraArray.getJSONObject(k).has("st")) {
                        newScoreArray.put(jarraArray.getJSONObject(k).getJSONArray("st"));
                    } else {
                        arra1.put(jarraArray.getJSONObject(k).getJSONArray(Parameters.points));
                    }

                    if (jarraArray.getJSONObject(k).getString(Parameters.User_Id).contentEquals(PreferenceManager.get_id())) {
                        if (k == 0) {
                            userIndex = 0;
                            if (PreferenceManager.get_picPath().length() > 0) {
                                Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                image1.setImageDrawable(d);
                            } else {
                                image1.setImageResource(PreferenceManager.getprofilepic());
                            }
                            ivUserOneYouTag.setVisibility(View.VISIBLE);
                        } else if (k == 1) {
                            userIndex = 1;
                            if (PreferenceManager.get_picPath().length() > 0) {
                                Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                image2.setImageDrawable(d);
                            } else {
                                image2.setImageResource(PreferenceManager.getprofilepic());
                            }
                            ivUserTwoYouTag.setVisibility(View.VISIBLE);
                        } else if (k == 2) {
                            userIndex = 2;
                            if (PreferenceManager.get_picPath().length() > 0) {
                                Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                image3.setImageDrawable(d);
                            } else {
                                image3.setImageResource(PreferenceManager.getprofilepic());
                            }
                            ivUserThreeYouTag.setVisibility(View.VISIBLE);
                        }
                    }

                    if (jarraArray.getJSONObject(k).has(Parameters.User_Info)) {

                        seatIndexes[k] = jarraArray.getJSONObject(k)
                                .getJSONObject(Parameters.User_Info)
                                .getInt(Parameters.SeatIndex);

                        UserName[k] = getProUserName(jarraArray.getJSONObject(k)
                                .getJSONObject(Parameters.User_Info)
                                .getString(Parameters.User_Name));

                        ProfilePic[k] = jarraArray.getJSONObject(k)
                                .getJSONObject(Parameters.User_Info)
                                .getString(Parameters.ProfilePicture);

                    } else {

                        seatIndexes[k] = jarraArray.getJSONObject(k).getInt(
                                Parameters.SeatIndex);

                        UserName[k] = getProUserName(jarraArray.getJSONObject(k).getString(
                                Parameters.User_Name));

                        ProfilePic[k] = jarraArray.getJSONObject(k).getString(
                                Parameters.ProfilePicture);

                    }

                    total[k] = jarraArray.getJSONObject(k).getString(
                            Parameters.totalsum);

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

//        Logger.print("BBBBBBBBBB:" + newScoreArray);
//        String data = "[\n" +
//                "    [\n" +
//                "        1,\n" +
//                "        2,\n" +
//                "        3,\n" +
//                "        4,\n" +
//                "        5 ,\n" +
//                "        6,\n" +
//                "\t7 \n" +
//                "  ],\n" +
//                "    [\n" +
//                "        {\n" +
//                "            \"DP\":49,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":25    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":58,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":25    \n" +
//                "    },\n" +
//                "    {\n" +
//                "            \"DP\":49,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":25    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":58,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":25    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    }  \n" +
//                "  ],\n" +
//                "    [\n" +
//                "        {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    },\n" +
//                "    {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    }  \n" +
//                "  ],\n" +
//                "    [\n" +
//                "        {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":52,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":25    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    },\n" +
//                "     {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":52,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":25    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":0,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":0    \n" +
//                "    },\n" +
//                "        {\n" +
//                "            \"DP\":52,\n" +
//                "            \"WP\":0,\n" +
//                "            \"GP\":25    \n" +
//                "    }  \n" +
//                "  ]\n" +
//                "]";
//        try {
//            JSONArray test = new JSONArray(data.toString());
        adapter_scorBoard = new Adapter_ScoreBoard(arra1, newScoreArray, seatIndexes, this);
        score_listview.setAdapter(adapter_scorBoard);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        scrollMyListViewToBottom();

        if (ProfilePic.length > 0 && total.length > 0 && UserName.length > 0) {

            if (ProfilePic[0] != null && total[0] != null && UserName[0] != null) {

                tot1.setText(total[0]);
                txtname1.setText(UserName[0]);
                if (userIndex != 0) {
                    image1.setImageResource(Integer.parseInt(ProfilePic[0]));
                }

            }

            if (jarraArray.length() >= 2) {

                if (ProfilePic[1] != null && total[1] != null
                        && UserName[1] != null) {

                    tot2.setText(total[1]);
                    txtname2.setText(UserName[1]);
                    if (userIndex != 1)
                        image2.setImageResource(Integer.parseInt(ProfilePic[1]));

                }
            }

            if (jarraArray.length() >= 3) {

                if (ProfilePic[2] != null) {
                    tot3.setText(total[2]);
                    txtname3.setText(UserName[2]);
                    if (userIndex != 2)
                        image3.setImageResource(Integer.parseInt(ProfilePic[2]));
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnClose) {
            Music_Manager.buttonclick();
            this.finish();
            overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
        }
    }

}
