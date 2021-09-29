package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import adapters.Adapter_quest_list;
import utils.C;
import utils.Logger;
import utils.Parameters;
import utils.QuestDaily;
import utils.QuestLifeTime;
import utils.ResponseCodes;

/**
 * Created by milin.patel on 4/11/2017.
 */

public class ActivityQuestAchivement extends Activity implements View.OnClickListener {
    public static Handler handler;
    QuestDaily qd;
    QuestLifeTime ql;
    GridView questlistDaily;
    TextView title;
    int Screen_Width, Screen_Hight;
    Button close;
    C c;
    boolean isQuest;
    Adapter_quest_list questAdapterlist;
    private Dialog ConfirmationDialogInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questdiadlog);

        c = C.getInstance();
        qd = new QuestDaily(this);
        ql = new QuestLifeTime(this);
        Screen_Hight = c.height;
        Screen_Width = c.width;
        FindviewById();
        SetScreen();
        initHandler();

        isQuest = getIntent().getBooleanExtra(Parameters.isQuest, false);

        if (isQuest) {
            SetQuestDaily();
            title.setText("Quest");
            questlistDaily.setVerticalScrollBarEnabled(false);
        } else {
            SetQuestLifeTime();
            title.setText("Achievement");

        }


    }

    private void initHandler() {

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if (msg.what == ResponseCodes.REFRESHQUESTDATA) {
                    String chip = msg.obj.toString();
                    showInfoDialog("Claimed", "" + c.getNumberFormatedValue(Long.parseLong(chip)) + " Chips Added");

                }


                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        c.payment_Handler = handler;

        c.con = this;
        c.activity = ActivityQuestAchivement.this;

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

        ConfirmationDialogInfo = new Dialog(this,
                R.style.Theme_Transparent);
        ConfirmationDialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialogInfo.setContentView(R.layout.dialog);
        ConfirmationDialogInfo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView title = ConfirmationDialogInfo
                .findViewById(R.id.tvTitle);
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

    private void SetScreen() {

        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) findViewById(R.id.close).getLayoutParams();
        frm.width = getwidth(80);
        frm.height = getheight(70);
        frm.topMargin = getheight(15);
        frm.rightMargin = getwidth(15);

//        frm = (FrameLayout.LayoutParams)findViewById(R.id.lnmain).getLayoutParams();
//        frm.topMargin = getheight(10);

        LinearLayout.LayoutParams lin = (LinearLayout.LayoutParams) findViewById(R.id.frmlist).getLayoutParams();
        lin.width = c.getwidth1(1000);
//        lin.height = getheight(520);
//        lin.topMargin = getheight(30);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.tvTitle).getLayoutParams();
        lin.width = c.getwidth1(900);
        lin.height = getheight(70);
        lin.topMargin = getheight(15);

//         lin=(LinearLayout.LayoutParams)findViewById(R.id.ivline).getLayoutParams();
////        lin.height=c.getheight1(700);
//        lin.topMargin=c.getheight1(20);


    }

    private void FindviewById() {

        questlistDaily = findViewById(R.id.questdata);

        title = findViewById(R.id.tvTitle);

        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));

        title.setTypeface(c.typeface, Typeface.BOLD);


//        title.setText(">>>>>><<<<<<<");

        close = findViewById(R.id.close);
        close.setOnClickListener(this);


    }

    private int getheight(int val) {
        // TODO Auto-generated method stub
        return Screen_Hight * val / 720;
    }

    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return Screen_Width * val / 1280;
    }

    private void SetQuestDaily() {

        long[] chips = new long[qd.Detail.length()];
        String[] taskname = new String[qd.Detail.length()];
        String[] tasknamesort = new String[qd.Detail.length()];
        String[] taskcompleted = new String[qd.Detail.length()];
        int[] taskid = new int[qd.Detail.length()];
        int[] complete = new int[qd.Detail.length()];
        int[] outOfcomplete = new int[qd.Detail.length()];
        boolean[] isClaimEnable = new boolean[qd.Detail.length()];
        boolean[] isFrameDisable = new boolean[qd.Detail.length()];

        for (int i = 0; i < qd.Detail.length(); i++) {

            chips[i] = qd.getRewardValue(i);
            taskname[i] = qd.getTaskName(i);

            tasknamesort[i] = qd.getTaskSortName(i);
            taskcompleted[i] = qd.getTaskCompletedText(i);
            taskid[i] = i;
            complete[i] = qd.getAchivementOnClaimCollectToday(i);
            Logger.print("XXXXXXXXXXXXX>>> complete " + i + " " + complete[i]);
            outOfcomplete[i] = qd.getCurrentGoalValue(i);
            isClaimEnable[i] = qd.getisCurrentGoalAchive(i);
            isFrameDisable[i] = qd.getisAllTaskAchived(i);

        }

        Logger.print("XXXXXXXXXXXXX>>> complete" + complete.toString());
        questAdapterlist = new Adapter_quest_list(chips, taskname, tasknamesort, taskid, complete, outOfcomplete, isClaimEnable, this, true, isFrameDisable, taskcompleted);

        questAdapterlist.notifyDataSetChanged();
        questlistDaily.setNumColumns(1);
        questlistDaily.setAdapter(questAdapterlist);

    }

    private void SetQuestLifeTime() {

        long[] chips = new long[ql.Detail.length()];
        String[] taskname = new String[ql.Detail.length()];
        String[] tasknamesort = new String[ql.Detail.length()];
        String[] taskcompleted = new String[ql.Detail.length()];
        int[] taskid = new int[ql.Detail.length()];
        int[] complete = new int[ql.Detail.length()];
        int[] outOfcomplete = new int[ql.Detail.length()];
        boolean[] isClaimEnable = new boolean[ql.Detail.length()];
        boolean[] isFrameDisable = new boolean[ql.Detail.length()];

        for (int i = 0; i < ql.Detail.length(); i++) {

            chips[i] = ql.getRewardValue(i);
            taskname[i] = ql.getTaskName(i);
            tasknamesort[i] = ql.getTaskSortName(i);
            taskcompleted[i] = ql.getTaskCompletedText(i);
            taskid[i] = i;
            complete[i] = ql.getAchivementOnClaimCollectLife(i);
            outOfcomplete[i] = ql.getCurrentGoalValue(i);
            isClaimEnable[i] = ql.getisCurrentGoalAchive(i);
            isFrameDisable[i] = ql.getisAllTaskAchived(i);
        }


        questAdapterlist = new Adapter_quest_list(chips, taskname, tasknamesort, taskid, complete, outOfcomplete, isClaimEnable, this, false, isFrameDisable, taskcompleted);

        questAdapterlist.notifyDataSetChanged();
        questlistDaily.setNumColumns(1);
        questlistDaily.setAdapter(questAdapterlist);


    }

    @Override
    public void onClick(View v) {

        if (v == close) {
            Music_Manager.buttonclick();
            overridePendingTransition(R.anim.none, R.anim.to_lefttoright);
            finish();
        }

    }
}
