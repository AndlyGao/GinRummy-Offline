package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import utils.C;
import utils.Logger;
import utils.PreferenceManager;
import utils.SQLiteManager;

//import utils.CircularImageView;
public class Activity_Profile extends Activity implements OnClickListener {
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public static final String TEMP_PHOTO_FILE_NAME1 = "temp_photo1.jpg";
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1001;
    public static final int REQUEST_CODE_GALLERY = 1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 2;
    public static final int REQUEST_CODE_CROP_IMAGE = 3;
    public static final int REQUEST_CODE_FRO_CAMERA = 4;
    ImageView edit_name, editclose;
    CircleImageView avtarImage;
    ImageButton closebtn;
    TextView changeAvtar, text3, text4, text3_total, text4_total, edit_title;
    TextView Username, UserHandsPlayed,
            UserHandsWon, UserHandsPlayed_total,
            UserHandsWon_total, EditOk, title;
    EditText et_UserName;
    FrameLayout edit_namefrm, profil_pic_back;
    EditText editname;
    FrameLayout bk;
    String changeUsername;
    String _id;
    Animation buttonClick;
    TextView tvyouhavetext, tvChips;
    Dialog dialog2, dialog3;
    boolean fronPlaying = false;
    HashMap<String, String> myMap = new HashMap<>();
    RadioButton chk_gin, chk_straight, chk_oklahoma;
    TextView txt_check_oklahoma, txt_check_straight, txt_check_gin;
    SQLiteManager sqLiteManager;
    C myData = C.getInstance();
    int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 1002;
    private File mFileTemp;
    private File mFileTemp1;
    private Handler handler;
    private long mLastClickTime = 0;
    private Dialog ConfirmationDialog;
    private Dialog ConfirmationDialogInfo;
    private String TAG = "Permission";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sqLiteManager = new SQLiteManager(getApplicationContext());
        myData.height = PreferenceManager.getheight();
        myData.width = PreferenceManager.getwidth();
        buttonClick = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buttonpressed);
        myData.typeface = Typeface.createFromAsset(getAssets(), "font/PoetsenOne-Regular.ttf");
        String uid = getIntent().getStringExtra("uid");
        fronPlaying = getIntent().getBooleanExtra("isFromPlaying", false);
        myMap = sqLiteManager.getUserData(uid);
        findViewByIds(uid);
        BindListeners();
        initHandler();
        setData(uid);
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
            mFileTemp1 = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME1);
        } else {
            mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
            mFileTemp1 = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME1);
        }
        setScreen(2);

    }

    private void initHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return false;
            }
        });
    }

    private void setScreen(int no) {
        FrameLayout.LayoutParams frm = null;
        LayoutParams lin;
        int tw = 0, th = 0;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.Ename_frm1).getLayoutParams();
        frm.height = getheight(720);
        frm.width = getwidth(1280);
        lin = (LayoutParams) findViewById(R.id.e_nameEdit).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(300);
        lin.topMargin = getheight(50);
        lin = (LayoutParams) findViewById(R.id.e_nameokbtn).getLayoutParams();
        lin.height = getheight(70);
        lin.width = getwidth(200);
        lin.topMargin = getheight(200);

        lin = (LayoutParams) findViewById(R.id.profile_changename).getLayoutParams();
        lin.height = getwidth(40);
        lin.width = getwidth(40);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.liner_name).getLayoutParams();
        lin.topMargin = (myData.height * 5) / 720;
        lin.width = (myData.width * 120) / 720;
        lin.height = (myData.width * 50) / 720;
        lin.rightMargin = (myData.width * 10) / 720;

        lin = (LayoutParams) findViewById(R.id.profile_line33).getLayoutParams();
        lin.topMargin = getheight(50);
        lin = (LayoutParams) findViewById(R.id.tvyouhavetext).getLayoutParams();
        lin.topMargin = getheight(10);
        lin = (LayoutParams) findViewById(R.id.profile_line333).getLayoutParams();
        lin.topMargin = getheight(10);
        lin.width = getwidth(200);
        lin = (LayoutParams) findViewById(R.id.tvChips).getLayoutParams();
        lin.topMargin = getheight(10);
        lin.leftMargin = getwidth(10);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.activity_profile_closebtn).getLayoutParams();
        tw = (myData.width * 80) / 1280;
        th = (myData.height * 70) / 720;
        frm.topMargin = (myData.height * 20) / 720;
        frm.rightMargin = (myData.height * 20) / 720;
        frm.height = th;
        frm.width = tw;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvTittle).getLayoutParams();
        tw = (myData.width * 900) / 1280;
        th = (myData.height * 70) / 720;
        frm.topMargin = (myData.height * 20) / 720;
        frm.height = th;
        frm.width = tw;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.profile_lin1).getLayoutParams();
        lin.topMargin = (myData.height * 100) / 720;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.profile_View).getLayoutParams();
        lin = (LayoutParams) findViewById(R.id.profile_lin2).getLayoutParams();
        lin.topMargin = (myData.height * 10) / 720;
        lin = (LayoutParams) findViewById(R.id.profil_pic_back).getLayoutParams();
        tw = (myData.width * 300) / 1280;
        th = (myData.width * 300) / 1280;
        lin.width = tw;
        lin.height = th;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.activity_profile_avtarimage).getLayoutParams();
        tw = (myData.width * 300) / 1280;
        th = (myData.width * 300) / 1280;
        frm.width = tw;
        frm.height = th;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.activity_profile_changeavatar).getLayoutParams();
        th = (myData.height * 60) / 720;
        tw = (myData.width * 240) / 1280;
        frm.width = tw;
        frm.height = th;
        frm.bottomMargin = (myData.height * 5) / 720;

        lin = (LayoutParams) findViewById(R.id.second_linear).getLayoutParams();
        lin.leftMargin = myData.width * 25 / 1280;
        lin = (LayoutParams) findViewById(R.id.ivChips).getLayoutParams();
        lin.width = getwidth(50);
        lin.height = getheight(50);
        int w = myData.width * 253 / 1280;
        int h = w * 81 / 253;

        w = myData.width * 245 / 1280;
        h = w * 18 / 245;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.lnrusrinfo).getLayoutParams();
        lin.topMargin = getheight(40);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.lnrinner1).getLayoutParams();
        lin.topMargin = getheight(20);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.lnrinner2).getLayoutParams();
        lin.topMargin = getheight(50);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.innerline1).getLayoutParams();
        lin.width = getwidth(250);
        lin.topMargin = getheight(5);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.innerline1_total).getLayoutParams();
        lin.width = getwidth(250);
        lin.topMargin = getheight(5);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.innerline2).getLayoutParams();
        lin.width = getwidth(250);
        lin.topMargin = getheight(5);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.innerline2_total).getLayoutParams();
        lin.width = getwidth(250);
        lin.topMargin = getheight(5);
        //=============================================================================
        frm = (FrameLayout.LayoutParams) findViewById(R.id.edit_closebtn).getLayoutParams();
        tw = (myData.width * 80) / 1280;
        th = (myData.height * 70) / 720;
        frm.topMargin = (myData.height * 20) / 720;
        frm.rightMargin = (myData.height * 20) / 720;
        frm.height = th;
        frm.width = tw;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.ename_title).getLayoutParams();
        tw = (myData.width * 900) / 1280;
        th = (myData.height * 70) / 720;
        frm.topMargin = (myData.height * 20) / 720;
        frm.height = th;
        frm.width = tw;
        //============= New Mode =====================
        lin = (LinearLayout.LayoutParams) findViewById(R.id.lnr_check1).getLayoutParams();
        lin.topMargin = getheight(10);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.txt_check_gin).getLayoutParams();
        lin.rightMargin = getwidth(5);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.txt_check_straight).getLayoutParams();
        lin.rightMargin = getwidth(5);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.iv_inr_diveder).getLayoutParams();
        lin.topMargin = getwidth(50);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.txt_check_oklahoma).getLayoutParams();
        lin.rightMargin = getwidth(5);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.chk_gin).getLayoutParams();
        lin.width = getwidth(180);
        lin.height = getwidth(60);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.chk_straight).getLayoutParams();
        lin.width = getwidth(180);
        lin.height = getwidth(60);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.chk_oklahoma).getLayoutParams();
        lin.width = getwidth(180);
        lin.height = getwidth(60);
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

    protected void setData(String id) {
        try {
            if (id.equals(PreferenceManager.get_id())) {
                Username.setText("" + PreferenceManager.getUserName());
                tvChips.setText("" + myData.formatter.format(PreferenceManager.getChips()));
                if (PreferenceManager.get_picPath().length() > 0) {
                    Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                    avtarImage.setImageDrawable(d);
                } else {
                    avtarImage.setImageResource(PreferenceManager.getprofilepic());

                }
//                UserHandsWon.setText("");
                UserHandsWon_total.setText("" + myMap.get("win"));
                UserHandsPlayed_total.setText("" + myMap.get("played"));
                UserHandsWon.setText("" + PreferenceManager.GetGinWon());
                UserHandsPlayed.setText("" + PreferenceManager.GetGinPlayed());

            } else {
                int index11 = Integer.valueOf(myMap.get(myData.IMAGE)) - 1;
                avtarImage.setImageResource(myData.leaderImage[index11]);
                Username.setText("" + myMap.get(myData.NAME));
                tvChips.setText("" + myMap.get(myData.CHIPS));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myData.payment_Handler = this.handler;
        myData.con = this;
        myData.activity = Activity_Profile.this;
    }

    private void setTextSize() {
        EditOk.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));
        editname.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        tvChips.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));
        tvyouhavetext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        edit_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.width * 50 / 1280);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.width * 50 / 1280);
        text3.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 24) / 1280);
        text3_total.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 24) / 1280);
        text4.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 24) / 1280);
        text4_total.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 24) / 1280);
        chk_gin.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 24) / 1280);
        chk_straight.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 24) / 1280);
        chk_oklahoma.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 24) / 1280);
        UserHandsPlayed.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 24) / 1280);
        UserHandsPlayed_total.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 24) / 1280);
        UserHandsWon.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 24) / 1280);
        UserHandsWon_total.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 24) / 1280);
        Username.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 28) / 1280);
        et_UserName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 27) / 1280);
        changeAvtar.setTextSize(TypedValue.COMPLEX_UNIT_PX, (myData.width * 20) / 1280);
        EditOk.setTypeface(myData.typeface);
        editname.setTypeface(myData.typeface);
        edit_title.setTypeface(myData.typeface);
        title.setTypeface(myData.typeface);
        tvChips.setTypeface(myData.typeface);
        tvyouhavetext.setTypeface(myData.typeface);
        text3.setTypeface(myData.typeface);
        text3_total.setTypeface(myData.typeface);
        text4.setTypeface(myData.typeface);
        text4_total.setTypeface(myData.typeface);
        chk_gin.setTypeface(myData.typeface);
        chk_straight.setTypeface(myData.typeface);
        chk_oklahoma.setTypeface(myData.typeface);
        changeAvtar.setTypeface(myData.typeface);
        Username.setTypeface(myData.typeface);
        et_UserName.setTypeface(myData.typeface);
        UserHandsPlayed.setTypeface(myData.typeface);
        UserHandsPlayed_total.setTypeface(myData.typeface);
        UserHandsWon.setTypeface(myData.typeface);
        UserHandsWon_total.setTypeface(myData.typeface);
    }

    private void findViewByIds(String uid) {
        profil_pic_back = findViewById(R.id.profil_pic_back);
        bk = findViewById(R.id.profile_bk);
        EditOk = findViewById(R.id.e_nameokbtn);
        EditOk.setOnClickListener(this);
        editname = findViewById(R.id.e_nameEdit);
        editname.setSelection(editname.getText().length());

        editname.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editname.setSelection(editname.getText().length());
            }
        });

        editname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editname.setSelection(editname.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                editname.setSelection(editname.getText().length());
            }
        });

        edit_title = findViewById(R.id.ename_title);
        title = findViewById(R.id.tvTittle);
        tvyouhavetext = findViewById(R.id.tvyouhavetext);
        tvChips = findViewById(R.id.tvChips);
        editclose = findViewById(R.id.edit_closebtn);
        editclose.setOnClickListener(this);
        edit_namefrm = findViewById(R.id.Ename_frm1);
        edit_namefrm.setVisibility(View.GONE);
        edit_name = findViewById(R.id.profile_changename);
        edit_name.setImageResource(R.drawable.avtarchange);
        edit_name.setOnClickListener(this);
        text3 = findViewById(R.id.profile_text3);
        text3_total = findViewById(R.id.profile_text3_total);
        text4 = findViewById(R.id.profile_text4);
        text4_total = findViewById(R.id.profile_text4_total);
//       text5 = (TextView) findViewById(R.id.profile_text5);
        avtarImage = findViewById(R.id.activity_profile_avtarimage);
//        avtarImage.setScaleType(ScaleType.FIT_XY);
        changeAvtar = findViewById(R.id.activity_profile_changeavatar);
        closebtn = findViewById(R.id.activity_profile_closebtn);
        if (!uid.equals(PreferenceManager.get_id())) {
            edit_name.setVisibility(View.GONE);
            changeAvtar.setVisibility(View.GONE);
        } else {
            edit_name.setVisibility(View.VISIBLE);
            changeAvtar.setVisibility(View.VISIBLE);
        }
        et_UserName = findViewById(R.id.et_activity_profile_username);
        et_UserName.setVisibility(View.GONE);

        Username = findViewById(R.id.activity_profile_username);
        Username.setText("");
//       UserChipLevel = (TextView) findViewById(R.id.activity_profile_chiplevel);
//        UserChipLevel.setText("");
        UserHandsPlayed = findViewById(R.id.activity_profile_handsplayed);
        UserHandsPlayed_total = findViewById(R.id.activity_profile_handsplayed_total);
//        UserHandsPlayed.setText("");
        UserHandsWon = findViewById(R.id.activity_profile_handswon);
        UserHandsWon_total = findViewById(R.id.activity_profile_handswon_total);
        txt_check_gin = findViewById(R.id.txt_check_gin);
        txt_check_straight = findViewById(R.id.txt_check_straight);
        txt_check_oklahoma = findViewById(R.id.txt_check_oklahoma);
        chk_gin = findViewById(R.id.chk_gin);
        chk_straight = findViewById(R.id.chk_straight);
        chk_oklahoma = findViewById(R.id.chk_oklahoma);
        chk_gin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
            }
        });
        chk_straight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
            }
        });
        chk_oklahoma.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
            }
        });
        chk_gin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UserHandsWon.setText("" + PreferenceManager.GetGinWon());
                    UserHandsPlayed.setText("" + PreferenceManager.GetGinPlayed());
                    chk_straight.setChecked(false);
                    chk_oklahoma.setChecked(false);
                }
            }
        });
        chk_straight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UserHandsWon.setText("" + PreferenceManager.GetStraightWon());
                    UserHandsPlayed.setText("" + PreferenceManager.GetStraightPlayed());
                    chk_gin.setChecked(false);
                    chk_oklahoma.setChecked(false);
                }
            }
        });
        chk_oklahoma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UserHandsWon.setText("" + PreferenceManager.GetOklahomaWon());
                    UserHandsPlayed.setText("" + PreferenceManager.GetOklahomaPlayed());
                    chk_straight.setChecked(false);
                    chk_gin.setChecked(false);
                }
            }
        });
        chk_gin.setChecked(true);
        chk_oklahoma.setChecked(false);
        chk_straight.setChecked(false);
        setTextSize();
        if (fronPlaying) {
            edit_name.setVisibility(View.INVISIBLE);
            changeAvtar.setVisibility(View.INVISIBLE);
        } else {
            edit_name.setVisibility(View.VISIBLE);
            changeAvtar.setVisibility(View.VISIBLE);
        }
    }

    private void BindListeners() {
        avtarImage.setOnClickListener(this);
        changeAvtar.setOnClickListener(this);
        closebtn.setOnClickListener(this);
    }

    private void startCropImage() {
        try {

            overridePendingTransition(R.anim.from_righttoleft, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog2 != null && dialog2.isShowing()) {
            dialog2.dismiss();
        }
        if (dialog3 != null && dialog3.isShowing()) {
            dialog3.dismiss();
        }
        try {
            if (ConfirmationDialog != null && ConfirmationDialog.isShowing()) {
                ConfirmationDialog.cancel();
                ConfirmationDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            bk.setBackgroundResource(0);
            closebtn.setBackgroundResource(0);
            profil_pic_back.setBackgroundResource(0);
            avtarImage.setBackgroundResource(0);
            changeAvtar.setBackgroundResource(0);
            edit_name.setBackgroundResource(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.activity_gone);
    }

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        if (edit_namefrm.getVisibility() == View.VISIBLE) {
            if (v == editclose) {
                Music_Manager.buttonclick();
                editclose.startAnimation(buttonClick);
                edit_namefrm.setVisibility(View.GONE);
            } else if (v == EditOk) {
                Music_Manager.buttonclick();
                EditOk.startAnimation(buttonClick);
                changeUsername = editname.getText().toString();
                String after = changeUsername.trim().replaceAll(" +", " ");
                editname.setText("");
                if (after.equals(PreferenceManager.getUserName())) {
                    edit_namefrm.setVisibility(View.GONE);
                } else {
                    if (after.trim().length() == 0 || changeUsername.length() <= 0) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(Activity_Profile.this, "Enter username",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        edit_namefrm.setVisibility(View.GONE);
                        editname.setText("");
                        PreferenceManager.setUserName(after);
                        sqLiteManager.updateName(after);
                        Username.setText(PreferenceManager.getUserName());
                        Dashboard.savedGamesUpdate();
                    }
                }
            }
        } else {
            if (v == edit_name) {
                Music_Manager.buttonclick();
//                editname.startAnimation(buttonClick);
//                String name = Username.getText().toString();
//                editname.setText("" + name);
//                edit_namefrm.setVisibility(View.VISIBLE);
                if (et_UserName.getVisibility() == View.GONE) {
                    et_UserName.setVisibility(View.VISIBLE);
                    Username.setVisibility(View.GONE);
                    showSoftKeyboard(et_UserName);
                    edit_name.setImageResource(R.drawable.avtardone);
                } else {
                    String name = et_UserName.getText().toString().trim();
                    if (name.trim().length() == 0){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(Activity_Profile.this, "Enter username",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        et_UserName.setVisibility(View.GONE);
                        et_UserName.setText("");
                        Username.setText(""+name);
                        Username.setVisibility(View.VISIBLE);
                        PreferenceManager.setUserName(name);
                        sqLiteManager.updateName(name);
                        Dashboard.savedGamesUpdate();
                        edit_name.setImageResource(R.drawable.avtarchange);
                    }
                }

            }
            if (v == closebtn) {
                Music_Manager.buttonclick();
                closebtn.startAnimation(buttonClick);
                finish();
                overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
            }
            if (v == changeAvtar) {
                Music_Manager.buttonclick();
                CheckPermission();
            }

        }
    }

    public void showSoftKeyboard(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);

        if (view.requestFocus()) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(
                    view.getApplicationWindowToken(),
                    InputMethodManager.SHOW_FORCED, 0);

        }
    }

    public void hideSoftKeyboard(View view) {

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }

    private void CheckPermission() {
        // Here, thisActivity is the current activity
        if ((ContextCompat.checkSelfPermission(Activity_Profile.this, "android.permission.WRITE_EXTERNAL_STORAGE")
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(Activity_Profile.this, "android.permission.READ_EXTERNAL_STORAGE")
                != PackageManager.PERMISSION_GRANTED)) {
            showInfoDialog("Give Permission", "Please Allow Permission To \n Change Profile Image...");
        } else {
            openAvatarChangeDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    openAvatarChangeDialog();

                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void openAvatarChangeDialog() {

        LinearLayout.LayoutParams lin = null;
        if (ConfirmationDialog != null || (ConfirmationDialog != null && ConfirmationDialog.isShowing())) {
            if (ConfirmationDialog.isShowing()) {
                ConfirmationDialog.dismiss();
            }
            ConfirmationDialog = null;
        }
        ConfirmationDialog = new Dialog(
                Activity_Profile.this, R.style.Theme_Transparent);
        ConfirmationDialog
                .requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialog.setContentView(R.layout.dialog);
        ConfirmationDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        TextView title = ConfirmationDialog
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(myData.typeface);
        title.setText("Profile Picture");
        lin = (LinearLayout.LayoutParams) ConfirmationDialog
                .findViewById(R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);
        TextView message1 = ConfirmationDialog
                .findViewById(R.id.tvMessage);
        message1.setTypeface(myData.typeface);
        message1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
        Button btnOne = ConfirmationDialog
                .findViewById(R.id.button1);
        btnOne.setTypeface(myData.typeface);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("Gallery");
        Button btnTwo = ConfirmationDialog
                .findViewById(R.id.button2);
        btnTwo.setBackgroundResource(R.drawable.get_free_card);
        btnTwo.setTypeface(myData.typeface);
        btnTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnTwo.setText("Camera");
        Button btnThree = ConfirmationDialog
                .findViewById(R.id.button3);
        btnThree.setTypeface(myData.typeface);
        btnThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnThree.setText("Cancel");
        btnThree.setVisibility(View.VISIBLE);
        lin = (LinearLayout.LayoutParams) ConfirmationDialog
                .findViewById(R.id.button1).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(120);
        lin = (LinearLayout.LayoutParams) ConfirmationDialog
                .findViewById(R.id.button2).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(120);
        lin = (LinearLayout.LayoutParams) ConfirmationDialog
                .findViewById(R.id.button3).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(120);
        lin = (LinearLayout.LayoutParams) ConfirmationDialog
                .findViewById(R.id.tvMessage).getLayoutParams();
        lin.topMargin = getheight(30);
        lin = (LinearLayout.LayoutParams) ConfirmationDialog
                .findViewById(R.id.llButtons).getLayoutParams();
        lin.topMargin = getheight(30);
        message1.setText("Select your Avatar!");

        btnOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Music_Manager.buttonclick();
                Logger.print("FFFFFFFFF   2");
                try {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
                    overridePendingTransition(R.anim.from_righttoleft, 0);
                    Logger.print("FFFFFFFFF   3");
                    ConfirmationDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnTwo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                // TODO Auto-generated method stub
                ConfirmationDialog.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    Uri mImageCaptureUri = null;
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        mImageCaptureUri = Uri.fromFile(mFileTemp1);
                    }
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, REQUEST_CODE_FRO_CAMERA);
                } catch (Exception | OutOfMemoryError e) {
                    e.printStackTrace();
                }
            }
        });
        btnThree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                // TODO Auto-generated method stub
                ConfirmationDialog.dismiss();
            }
        });
        if (!isFinishing()) {
            Logger.print("RUNNING");
            ConfirmationDialog.show();
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
        ConfirmationDialogInfo = new Dialog(Activity_Profile.this,
                R.style.Theme_Transparent);
        ConfirmationDialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialogInfo.setContentView(R.layout.dialog);
        ConfirmationDialogInfo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        TextView title = ConfirmationDialogInfo
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(myData.typeface);
        title.setText(DialogTitle);
        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);
        TextView message = ConfirmationDialogInfo
                .findViewById(R.id.tvMessage);
        message.setTypeface(myData.typeface);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));
        Button btnOne = ConfirmationDialogInfo.findViewById(R.id.button1);
        btnOne.setTypeface(myData.typeface);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("Allow");
        Button btnTwo = ConfirmationDialogInfo.findViewById(R.id.button2);
        btnTwo.setVisibility(View.GONE);

        Button closebtn = ConfirmationDialogInfo.findViewById(R.id.closebtn);
        closebtn.setVisibility(View.VISIBLE);

        closebtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationDialogInfo.dismiss();
            }
        });

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
                ActivityCompat.requestPermissions(Activity_Profile.this,
                        new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                Music_Manager.buttonclick();
                ConfirmationDialogInfo.dismiss();
            }
        });
        if (!isFinishing())
            ConfirmationDialogInfo.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.print("MMMMMMMMMMMMMMMMM>>>>>>RrequestCode>" + requestCode);
        Logger.print("MMMMMMMMMMMMMMMMM>>>>>>ResultCode>" + resultCode);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_FRO_CAMERA:
                startCropImage();
                break;
            case REQUEST_CODE_GALLERY:
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp1);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    startCropImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_CODE_TAKE_PICTURE:
                startCropImage();
                break;
            case REQUEST_CODE_CROP_IMAGE:
//
//                if (path == null) {
//                    return;
//                }
//                PreferenceManager.set_Picpath(path);
                Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                avtarImage.setImageDrawable(d);

                try {
                    Dashboard.coverImage = ((BitmapDrawable) d).getBitmap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Dashboard.savedGamesUpdate();
                break;
        }
    }

    private int getheight(int val) {
        return myData.height * val / 720;
    }

    private int getwidth(int val) {
        return myData.width * val / 1280;
    }
}