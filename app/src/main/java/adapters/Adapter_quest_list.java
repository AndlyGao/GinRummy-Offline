package adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.artoon.ginrummyoffline.ActivityQuestAchivement;
import com.artoon.ginrummyoffline.Dashboard;
import com.artoon.ginrummyoffline.Music_Manager;
import com.artoon.ginrummyoffline.R;

import utils.C;
import utils.Logger;
import utils.PreferenceManager;
import utils.QuestDaily;
import utils.QuestLifeTime;
import utils.ResponseCodes;

/**
 * Created by Artoon on 09-Jan-17.
 */
public class Adapter_quest_list extends BaseAdapter {

    int Screen_Hight, Screen_Width;

    C c = C.getInstance();
    Holder holder;
    Activity activity;
    LayoutInflater inflater = null;
    String[] taskname, taskcompleted, tasknamesort;
    int[] complete, taskid, outofcomplet;
    long[] Chips;
    boolean[] isClaimEnable, isFrameDisable;
    QuestDaily qd;
    QuestLifeTime ql;
    boolean isDaily;


    public Adapter_quest_list(long[] chips,
                              String[] taskname,
                              String[] tasknamesort, int[] taskid,
                              int[] complete,
                              int[] outofcomplet,
                              boolean[] isClaimEnable,
                              Context c, boolean isDaily, boolean[] isFrameDisable, String[] taskcompleted) {


        this.Chips = new long[chips.length];
        this.taskname = new String[taskname.length];
        this.tasknamesort = new String[tasknamesort.length];
        this.taskcompleted = new String[taskcompleted.length];
        this.taskid = new int[taskid.length];
        this.complete = new int[complete.length];
        this.outofcomplet = new int[outofcomplet.length];
        this.isClaimEnable = new boolean[isClaimEnable.length];
        this.isFrameDisable = new boolean[isFrameDisable.length];

        this.Chips = chips.clone();
        this.taskname = taskname.clone();
        this.tasknamesort = tasknamesort.clone();
        this.taskcompleted = taskcompleted.clone();
        this.taskid = taskid.clone();
        this.complete = complete.clone();
        this.outofcomplet = outofcomplet.clone();
        this.isClaimEnable = isClaimEnable.clone();
        this.isFrameDisable = isFrameDisable.clone();
        this.isDaily = isDaily;

        ql = new QuestLifeTime(c);
        qd = new QuestDaily(c);

        activity = (Activity) c;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return taskid.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View arg1, ViewGroup parent) {


        holder = new Holder();
        View vi = arg1;

        if (vi == null) {

            vi = inflater.inflate(R.layout.adapter_questdata, null);


            holder.taskname = vi.findViewById(R.id.taskname);
            holder.pic = vi.findViewById(R.id.imgsample);
            holder.tvcomplete = vi.findViewById(R.id.tvcomplete);
            holder.frM = vi.findViewById(R.id.quest_frm);
            holder.chips = vi.findViewById(R.id.chips);
            holder.lntask = vi.findViewById(R.id.lntask);
            holder.progress_id = vi.findViewById(R.id.progress_id);
            holder.seekBar1 = vi.findViewById(R.id.seekBar1);
            holder.seekBar1.setVisibility(View.VISIBLE);
            holder.btnclaim = vi.findViewById(R.id.btnclaim);


            vi.setTag(holder);

        } else {
            holder = (Holder) vi.getTag();
        }

        Screen_Hight = vi.getResources().getDisplayMetrics().heightPixels;
        Screen_Width = vi.getResources().getDisplayMetrics().widthPixels;

        holder.btnclaim.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(22));
        holder.btnclaim.setTypeface(c.typeface, Typeface.BOLD);

        holder.taskname.setTypeface(c.typeface, Typeface.BOLD);
        //holder.pic2.setTypeface(c.typeface, Typeface.BOLD);
        holder.tvcomplete.setTypeface(c.typeface, Typeface.BOLD);
        holder.chips.setTypeface(c.typeface, Typeface.BOLD);
        holder.chips.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(22));

        holder.taskname.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(22));
        //holder.pic2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(18));
        holder.tvcomplete.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(22));

        holder.frM.setPadding(getwidth(5), getheight(5), getwidth(5), getheight(5));


        setscreen(vi);
        holder.taskname.setText("" + taskname[position]);
        // holder.pic2.setText(""/*+tasknamesort[position]*/);
        holder.tvcomplete.setText(taskcompleted[position] + " " + c.getNumberFormatedValue(complete[position]) + " Of " + c.getNumberFormatedValue(outofcomplet[position]));
        holder.seekBar1.setMax(outofcomplet[position]);
        holder.seekBar1.setProgress(complete[position]);
        holder.seekBar1.setClickable(false);
        // holder.seekBar1.setEnabled(false);
        holder.seekBar1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        holder.chips.setText("" + c.numDifferentiation(Chips[position]));

        if (isFrameDisable[position]) {
            holder.frM.setAlpha(.5f);
            holder.seekBar1.setVisibility(View.GONE);
            holder.tvcomplete.setText("All Are Claimed");
            //holder.frM.setEnabled(false);

        } else {
            holder.frM.setAlpha(1f);
            holder.seekBar1.setVisibility(View.VISIBLE);
            // holder.frM.setEnabled(true);
        }

        if (isClaimEnable[position]) {
            enableClaim();

        } else {

            disableClaim();
        }

        holder.btnclaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                //disableClaim();
                holder.btnclaim.clearAnimation();
                c.Chips = PreferenceManager.getChips() + Chips[position];
                PreferenceManager.setChips(c.Chips);


                if (isDaily) {
                    qd.setClaimCount(taskid[position], outofcomplet[position]);


                    Message msg = new Message();
                    msg.arg1 = 1;
                    msg.obj = String.valueOf(Chips[position]);
                    msg.what = ResponseCodes.REFRESHQUESTDATA;


                    Message msg2 = new Message();
                    msg2.copyFrom(msg);

                    if (Dashboard.handler != null) {
                        Dashboard.handler.sendMessage(msg);
                    }

                    if (ActivityQuestAchivement.handler != null) {
                        ActivityQuestAchivement.handler.sendMessage(msg2);
                    }

                } else {
                    ql.setClaimCount(taskid[position]);
                    Message msg = new Message();
                    msg.arg1 = 0;
                    msg.obj = String.valueOf(Chips[position]);
                    msg.what = ResponseCodes.REFRESHQUESTDATA;

                    Message msg2 = new Message();
                    msg2.copyFrom(msg);


                    if (Dashboard.handler != null) {
                        Dashboard.handler.sendMessage(msg);
                    }
                    if (ActivityQuestAchivement.handler != null) {
                        ActivityQuestAchivement.handler.sendMessage(msg2);
                    }

                }
                refreshData(taskid[position], isDaily, position);


            }
        });

        return vi;
    }

    private void refreshData(int taskid, boolean isDaily, int position) {


        Logger.print("KKKKKKKKKKKK>>> " + taskid + " " + position);
        int chips, Taskid, complete, outOfcomplete;
        String taskname, taskcompleted, tasknamesort;
        boolean isClaimEnable, isFrameDisable;

        if (isDaily) {

            chips = qd.getRewardValue(taskid);
            taskname = qd.getTaskName(taskid);
            tasknamesort = qd.getTaskSortName(taskid);
            taskcompleted = qd.getTaskCompletedText(taskid);
            Taskid = taskid;
            complete = qd.getAchivementOnClaimCollectToday(taskid);
            outOfcomplete = qd.getCurrentGoalValue(taskid);
            isClaimEnable = qd.getisCurrentGoalAchive(taskid);
            isFrameDisable = qd.getisAllTaskAchived(taskid);
        } else {

            chips = ql.getRewardValue(taskid);
            taskname = ql.getTaskName(taskid);
            tasknamesort = ql.getTaskSortName(taskid);
            taskcompleted = ql.getTaskCompletedText(taskid);
            complete = ql.getAchivementOnClaimCollectLife(taskid);
            outOfcomplete = ql.getCurrentGoalValue(taskid);
            isClaimEnable = ql.getisCurrentGoalAchive(taskid);
            isFrameDisable = ql.getisAllTaskAchived(taskid);
        }

        Logger.print("KKKKKKKKKKKK>>> chips " + chips);
        Logger.print("KKKKKKKKKKKK>>> taskname " + taskname);
        Logger.print("KKKKKKKKKKKK>>> tasknamesort " + tasknamesort);
        Logger.print("KKKKKKKKKKKK>>> taskcompleted " + taskcompleted);
        Logger.print("KKKKKKKKKKKK>>> complete " + complete);
        Logger.print("KKKKKKKKKKKK>>> outOfcomplete " + outOfcomplete);
        Logger.print("KKKKKKKKKKKK>>> isClaimEnable " + isClaimEnable);
        Logger.print("KKKKKKKKKKKK>>> isFrameDisable " + isFrameDisable);


        holder.taskname.setText("" + taskname);
        holder.tvcomplete.setText(taskcompleted + " " + complete + " Of " + outOfcomplete);
        holder.seekBar1.setMax(outOfcomplete);
        holder.seekBar1.setProgress(complete);


        holder.chips.setText("" + c.numDifferentiation(chips));

        if (isFrameDisable) {
            holder.frM.setAlpha(.5f);
        } else {
            holder.frM.setAlpha(1f);
        }

        if (isClaimEnable) {
            enableClaim();

        } else {

            disableClaim();
        }

        this.taskname[position] = taskname;
        this.taskcompleted[position] = taskcompleted;
        this.outofcomplet[position] = outOfcomplete;
        this.Chips[position] = chips;
        this.complete[position] = complete;
        this.isClaimEnable[position] = isClaimEnable;
        this.isFrameDisable[position] = isFrameDisable;

        notifyDataSetChanged();
    }


    private void enableClaim() {
        Animation anim = AnimationUtils.loadAnimation(c.con, R.anim.freechipanim);
        holder.btnclaim.setAlpha(1f);
        holder.btnclaim.setEnabled(true);
        holder.btnclaim.setBackgroundResource(R.drawable.profile_bg);
        holder.btnclaim.setAnimation(anim);


    }

    private void disableClaim() {

        holder.btnclaim.setAlpha(.6f);
        holder.btnclaim.setEnabled(false);
        holder.btnclaim.setBackgroundResource(R.drawable.btndisable);
        holder.btnclaim.clearAnimation();

    }

    private void setscreen(View vi) {

        // TODO Auto-generated method stub

        FrameLayout.LayoutParams frm = null;
        LinearLayout.LayoutParams lin = null;
        RelativeLayout.LayoutParams rel = null;

        int height = 0;
        int width = 0;
        width = c.width * 100 / 1280;
        height = getheight(100);

        frm = new FrameLayout.LayoutParams(width, height, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        frm.leftMargin = c.width * 15 / 1280;
        holder.pic.setLayoutParams(frm);

        frm = (FrameLayout.LayoutParams) vi.findViewById(R.id.lntask).getLayoutParams();
        //frm.gravity=Gravity.CENTER;
        frm.width = getwidth(560);
        frm.leftMargin = getwidth(110);

        frm = (FrameLayout.LayoutParams) vi.findViewById(R.id.lnchips).getLayoutParams();
        frm.rightMargin = c.width * 145 / 1280;
        frm.width = getwidth(100);


        lin = (LinearLayout.LayoutParams) vi.findViewById(R.id.ivchip).getLayoutParams();
        lin.height = getheight(35);
        lin.width = getwidth(35);

        lin = (LinearLayout.LayoutParams) vi.findViewById(R.id.chips).getLayoutParams();
        lin.topMargin = getheight(7);

        lin = (LinearLayout.LayoutParams) vi.findViewById(R.id.taskname).getLayoutParams();
        // lin.gravity=Gravity.CENTER;

        lin = (LinearLayout.LayoutParams) vi.findViewById(R.id.tvcomplete).getLayoutParams();
        lin.topMargin = getheight(7);
        //lin.gravity=Gravity.CENTER;

        lin = (LinearLayout.LayoutParams) vi.findViewById(R.id.progress_id).getLayoutParams();
        lin.height = getheight(12);
        lin.width = getwidth(530);
        lin.topMargin = getheight(8);
        // lin.gravity=Gravity.CENTER;

        width = c.width * 130 / 1280;
        height = getheight(50);
        frm = new FrameLayout.LayoutParams(width, height, Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        frm.rightMargin = c.width * 10 / 1280;
        holder.btnclaim.setLayoutParams(frm);

        frm = (FrameLayout.LayoutParams) vi.findViewById(R.id.quest_frm).getLayoutParams();
        frm.height = getheight(120);
        holder.frM.setLayoutParams(frm);

    }

    private int getheight(int val) {
        // TODO Auto-generated method stub
        return Screen_Hight * val / 720;

    }

    private int getwidth(int val) {
        return Screen_Width * val / 1280;
    }

    class Holder {

        ImageView pic, ivchip;
        TextView taskname, tvcomplete, chips/*,pic2*/;
        FrameLayout frM;
        Button btnclaim;
        LinearLayout lntask, lnchips;
        RelativeLayout progress_id;
        SeekBar seekBar1;

    }

}
