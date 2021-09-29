package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.games.Games;

import java.util.Calendar;

import hugo.weaving.DebugLog;
import utils.C;
import utils.Logger;
import utils.NotifyUser;
import utils.PreferenceManager;

public class Setting extends Activity {
    int Screen_Width, Screen_Hight;
    LinearLayout ll_setting;
    C c;
    TextView title, tx_sound, tx_vibrate, tx_notofication, tx_feedback, tx_terms, tx_privacy, tx_gamerule, tx_signin;
    LinearLayout ll_sound, ll_vibrate, ll_notofication, ll_feedback, ll_terms, ll_privacy, ll_gamerule;
    ImageView iv_sound, iv_vibrate, iv_notification, btn_signin;
    AlarmManager am;
    private Dialog ConfirmationDialogInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        c = C.getInstance();
        Screen_Hight = c.height;
        Screen_Width = c.width;

        finDViewByIds();

        setScreen();
    }

    private void finDViewByIds() {
        title = findViewById(R.id.tvTitle_setting);
        title.setTypeface(c.typeface);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(37));


        tx_sound = findViewById(R.id.tx_sounds);
        tx_sound.setTypeface(c.typeface);
        tx_sound.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));

        tx_vibrate = findViewById(R.id.tx_vibrate);
        tx_vibrate.setTypeface(c.typeface);
        tx_vibrate.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));

        tx_notofication = findViewById(R.id.tx_notofication);
        tx_notofication.setTypeface(c.typeface);
        tx_notofication.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));

        tx_feedback = findViewById(R.id.tx_feedback);
        tx_feedback.setTypeface(c.typeface);
        tx_feedback.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));

        tx_terms = findViewById(R.id.tx_terms);
        tx_terms.setTypeface(c.typeface);
        tx_terms.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));

        tx_privacy = findViewById(R.id.tx_privacy);
        tx_privacy.setTypeface(c.typeface);
        tx_privacy.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));

        tx_gamerule = findViewById(R.id.tx_gamerule);
        tx_gamerule.setTypeface(c.typeface);
        tx_gamerule.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));

        tx_signin = findViewById(R.id.tx_signin);
        tx_signin.setTypeface(c.typeface);
        tx_signin.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(40));

        btn_signin = findViewById(R.id.btn_signin);

//        if (Dashboard.mGoogleApiClient.isConnected()){
//            PreferenceManager.set_Google_Sing_in(true);
//        }else {
//            PreferenceManager.set_Google_Sing_in(false);
//        }

        if (PreferenceManager.get_Google_Sing_in()) {
            btn_signin.setImageResource(R.drawable.btn_disconnect);
        } else {
            btn_signin.setImageResource(R.drawable.btn_connect);
        }



        iv_sound = findViewById(R.id.iv_sound);
        if (PreferenceManager.getMute()) {
            iv_sound.setImageResource(R.drawable.sound_off);
            tx_sound.setText("Sound Off");
        } else {
            iv_sound.setImageResource(R.drawable.sound_on);
            tx_sound.setText("Sound On");
        }


        iv_vibrate = findViewById(R.id.iv_vibrate);
        if (PreferenceManager.getVibrate()) {
            iv_vibrate.setImageResource(R.drawable.vibrate_ons);
            tx_vibrate.setText("Vibrate On");
        } else {
            iv_vibrate.setImageResource(R.drawable.vibrate_offs);
            tx_vibrate.setText("Vibrate Off");
        }

        iv_notification = findViewById(R.id.iv_notification);
        if (PreferenceManager.getNotification()) {
            iv_notification.setImageResource(R.drawable.notification_on);
            tx_notofication.setText("Notification On");
        } else {
            iv_notification.setImageResource(R.drawable.notification_off);
            tx_notofication.setText("Notification Off");
        }
        clickEvents();

    }

    private void clickEvents() {
        Button close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
                finish();
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                if (PreferenceManager.isInternetConnected()) {
                    if (PreferenceManager.get_Google_Sing_in()) {
                        PreferenceManager.set_Google_Sing_in(false);

                        if (Dashboard.mGoogleApiClient != null && Dashboard.mGoogleApiClient.isConnected()) {
                            Games.signOut(Dashboard.mGoogleApiClient);
                            Dashboard.mGoogleApiClient.disconnect();
                            btn_signin.setImageResource(R.drawable.btn_connect);
                        }

                    } else {
                        PreferenceManager.set_Google_Sing_in(true);
                        if (!PreferenceManager.get_FirstTime() && Dashboard.mGoogleApiClient != null && !Dashboard.mGoogleApiClient.isConnected()) {
                            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> CONNECTION START 2222 >>> ");
                            Dashboard.mGoogleApiClient.connect();
                            btn_signin.setImageResource(R.drawable.btn_disconnect);
                            overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
                            finish();
                        }

                    }
                } else {
                    showInfoDialog("Alert", "Please check your Internet Connection.");
                }
            }
        });

        ll_sound = findViewById(R.id.ll_sound);
        ll_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceManager.getMute()) {
                    PreferenceManager.setMute(false);
                    Music_Manager.Mute = false;
                    iv_sound.setImageResource(R.drawable.sound_on);
                    tx_sound.setText("Sound On");
                } else {
                    PreferenceManager.setMute(true);
                    Music_Manager.Mute = true;
                    iv_sound.setImageResource(R.drawable.sound_off);
                    tx_sound.setText("Sound Off");
                }
                Music_Manager.buttonclick();
            }
        });

        ll_vibrate = findViewById(R.id.ll_vibrate);
        ll_vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                if (PreferenceManager.getVibrate()) {
                    iv_vibrate.setImageResource(R.drawable.vibrate_offs);
                    PreferenceManager.setVibrate(false);
                    tx_vibrate.setText("Vibrate Off");
                } else {
                    iv_vibrate.setImageResource(R.drawable.vibrate_ons);
                    PreferenceManager.setVibrate(true);
                    tx_vibrate.setText("Vibrate On");
                }
            }
        });

        ll_notofication = findViewById(R.id.ll_notofication);
        ll_notofication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                if (PreferenceManager.getNotification()) {
                    iv_notification.setImageResource(R.drawable.notification_off);
                    tx_notofication.setText("Notification Off");
                    PreferenceManager.setNotification(false);
                    CancelAlarm();
                } else {
                    iv_notification.setImageResource(R.drawable.notification_on);
                    tx_notofication.setText("Notification On");
                    PreferenceManager.setNotification(true);
                    setRepeatingAlarm();
                }
            }
        });

        ll_feedback = findViewById(R.id.ll_feedback);
        ll_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                try {


                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@gamewithpals.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT, "GinRummy Offline Feedback(Android V" + c.version + ")");
                    email.putExtra(Intent.EXTRA_TEXT, "");
                    email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
                    overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        ll_terms = findViewById(R.id.ll_terms);
        ll_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                StartConnection("http://www.gamewithpals.com/home/GetConfiguration/2");
            }
        });

        ll_privacy = findViewById(R.id.ll_privacy);
        ll_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                StartConnection("http://www.gamewithpals.com/home/GetConfiguration/3");
            }
        });

        ll_gamerule = findViewById(R.id.ll_gamerule);
        ll_gamerule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                Intent inprofile = new Intent(getApplication(), Activity_Help.class);
                startActivity(inprofile);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
                finish();
            }
        });
    }

    @DebugLog
    private void setRepeatingAlarm() {
        Logger.print("MMMMMMMMMMMMMMM>>> setRepeatingAlarm <<<");
        CancelAlarm();
        setFirstAlarm();
        setSecondAlarm();
    }

    @DebugLog
    public void StartConnection(String link) {

        try {
            Uri uriUrl2 = Uri.parse(link);
            Intent launchBrowser2 = new Intent(Intent.ACTION_VIEW, uriUrl2);
            startActivity(launchBrowser2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DebugLog
    public void CancelAlarm() {

        Intent intent = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }

    @DebugLog
    private void setFirstAlarm() {
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR ) + 1);
        Intent intent = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 13, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp = (PendingIntent.getBroadcast(this, 13, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp) {
            am.cancel(pendingIntent1);
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 13, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent);

        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);

        Intent intent1 = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent11 = PendingIntent.getBroadcast(this, 14, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp1 = (PendingIntent.getBroadcast(this, 14, intent1, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp1) {
            am.cancel(pendingIntent11);
        }

        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 14, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent2);


        // ======== new day 5 and Day 10 logic .....
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 3);

        Intent intent2 = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent12 = PendingIntent.getBroadcast(this, 30, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp2 = (PendingIntent.getBroadcast(this, 30, intent2, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp2) {
            am.cancel(pendingIntent12);
        }

        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(this, 30, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent3);

        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 5);

        Intent intent3 = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent13 = PendingIntent.getBroadcast(this, 31, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp3 = (PendingIntent.getBroadcast(this, 31, intent3, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp3) {
            am.cancel(pendingIntent13);
        }

        PendingIntent pendingIntent4 = PendingIntent.getBroadcast(this, 31, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent4);
        // ======== new day 5 and Day 10 logic .....


    }

    @DebugLog
    private void setSecondAlarm() {
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR ) + 1);
        Intent intent = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 21, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp = (PendingIntent.getBroadcast(this, 21, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp) {
            am.cancel(pendingIntent1);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 21, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);

        Intent intent1 = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent11 = PendingIntent.getBroadcast(this, 22, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp1 = (PendingIntent.getBroadcast(this, 22, intent1, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp1) {
            am.cancel(pendingIntent11);
        }

        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 22, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent2);


        // ======== new day 5 and Day 10 logic .....
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 3);

        Intent intent2 = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent12 = PendingIntent.getBroadcast(this, 32, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp2 = (PendingIntent.getBroadcast(this, 32, intent2, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp2) {
            am.cancel(pendingIntent12);
        }

        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(this, 32, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent3);

        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 5);

        Intent intent3 = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent13 = PendingIntent.getBroadcast(this, 33, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp3 = (PendingIntent.getBroadcast(this, 33, intent3, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp3) {
            am.cancel(pendingIntent13);
        }

        PendingIntent pendingIntent4 = PendingIntent.getBroadcast(this, 33, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent4);
        // ======== new day 5 and Day 10 logic .....

    }

    private void setScreen() {
        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) findViewById(R.id.close).getLayoutParams();
        frm.width = getwidth(80);
        frm.height = getheight(70);
        frm.topMargin = getheight(15);
        frm.rightMargin = getwidth(15);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tvTitle_setting).getLayoutParams();
        frm.width = c.getwidth1(900);
        frm.height = getheight(70);
        frm.topMargin = getheight(15);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.ll_setting).getLayoutParams();
        frm.topMargin = getheight(110);
        frm.leftMargin = getwidth(60);
        frm.rightMargin = getwidth(60);
        frm.bottomMargin = getwidth(30);

        findViewById(R.id.ll_setting).setPadding(getwidth(10), getwidth(10), getwidth(10), getwidth(10));

    }

    private int getheight(int val) {
        // TODO Auto-generated method stub
        return Screen_Hight * val / 720;
    }

    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return Screen_Width * val / 1280;
    }


    @DebugLog
    private void showInfoDialog(String DialogTitle, String Message) {
        // TODO Auto-generated method stub

        LinearLayout.LayoutParams lin;

        if (ConfirmationDialogInfo != null || (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing())) {
            if (ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.dismiss();
            }
            ConfirmationDialogInfo = null;
        }

        ConfirmationDialogInfo = new Dialog(Setting.this,
                R.style.Theme_Transparent);
        ConfirmationDialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialogInfo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ConfirmationDialogInfo.setContentView(R.layout.dialog);

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
}
