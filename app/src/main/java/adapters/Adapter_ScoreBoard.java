package adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artoon.ginrummyoffline.R;

import org.json.JSONArray;

import utils.C;
import utils.Logger;
import utils.Parameters;

public class Adapter_ScoreBoard extends BaseAdapter implements OnTouchListener {

    Holder holder;
    C c = C.getInstance();
    Activity activity;
    LayoutInflater inflater = null;

    JSONArray jarraArray = new JSONArray();
    JSONArray newjarraArray = new JSONArray();
    int[] seatIndexes;
    int count = 0;
    int scree_Hieght;
    int Screen_Width;


    public Adapter_ScoreBoard(JSONArray arra3, JSONArray newScores, int[] seatIndexes, Context c1) {
        // TODO Auto-generated constructor stub

        activity = (Activity) c1;
        this.seatIndexes = seatIndexes;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        scree_Hieght = c.height;
        Screen_Width = c.width;
        jarraArray = arra3;
        newjarraArray = newScores;

        try {

            if (newjarraArray.length() > 0) {

                for (int i = 0; i < newjarraArray.length(); i++) {

                    if (newjarraArray.getJSONArray(i).length() > 0) {

                        count = newjarraArray.getJSONArray(i).length();

                    }
                }

            } else {
                for (int i = 0; i < jarraArray.length(); i++) {

                    if (jarraArray.getJSONArray(i).length() > 0) {

                        count = jarraArray.getJSONArray(i).length();

                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return count;

    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;

    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub

        holder = new Holder();
        View vi = arg1;
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(),
                "font/PoetsenOne-Regular.ttf");

        if (vi == null) {

            vi = inflater.inflate(R.layout.adapter_scoreboard, null);

            holder.tvRoundTxt = vi.findViewById(R.id.tvRoundNo);
            holder.tvRoundTxt.setTypeface(typeface);
            holder.tvRoundTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(30));

            holder.tvGinKnockPointsTitle[0] = vi.findViewById(R.id.tvGinKnockPointsTitleOne);
            holder.tvGinKnockPointsTitle[0].setTypeface(typeface);
            holder.tvGinKnockPointsTitle[0].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvGinKnockPointsTitle[1] = vi.findViewById(R.id.tvGinKnockPointsTitleTwo);
            holder.tvGinKnockPointsTitle[1].setTypeface(typeface);
            holder.tvGinKnockPointsTitle[1].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvGinKnockPointsTitle[2] = vi.findViewById(R.id.tvGinKnockPointsTitleThree);
            holder.tvGinKnockPointsTitle[2].setTypeface(typeface);
            holder.tvGinKnockPointsTitle[2].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvGinKnockPoint[0] = vi.findViewById(R.id.tvGinKnockPointOne);
            holder.tvGinKnockPoint[0].setTypeface(typeface);
            holder.tvGinKnockPoint[0].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvGinKnockPoint[1] = vi.findViewById(R.id.tvGinKnockPointTwo);
            holder.tvGinKnockPoint[1].setTypeface(typeface);
            holder.tvGinKnockPoint[1].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvGinKnockPoint[2] = vi.findViewById(R.id.tvGinKnockPointThree);
            holder.tvGinKnockPoint[2].setTypeface(typeface);
            holder.tvGinKnockPoint[2].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvDeadwoodPointsTitle[0] = vi.findViewById(R.id.tvDeadwoodPointsTitleIOne);
            holder.tvDeadwoodPointsTitle[0].setTypeface(typeface);
            holder.tvDeadwoodPointsTitle[0].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvDeadwoodPointsTitle[1] = vi.findViewById(R.id.tvDeadwoodPointsTitleTwo);
            holder.tvDeadwoodPointsTitle[1].setTypeface(typeface);
            holder.tvDeadwoodPointsTitle[1].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvDeadwoodPointsTitle[2] = vi.findViewById(R.id.tvDeadwoodPointsTitleThree);
            holder.tvDeadwoodPointsTitle[2].setTypeface(typeface);
            holder.tvDeadwoodPointsTitle[2].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));


            holder.tvDeadwoodPoint[0] = vi.findViewById(R.id.tvDeadwoodPointOne);
            holder.tvDeadwoodPoint[0].setTypeface(typeface);
            holder.tvDeadwoodPoint[0].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvDeadwoodPoint[1] = vi.findViewById(R.id.tvDeadwoodPointTwo);
            holder.tvDeadwoodPoint[1].setTypeface(typeface);
            holder.tvDeadwoodPoint[1].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvDeadwoodPoint[2] = vi.findViewById(R.id.tvDeadwoodPointThree);
            holder.tvDeadwoodPoint[2].setTypeface(typeface);
            holder.tvDeadwoodPoint[2].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvWinPointsTitle[0] = vi.findViewById(R.id.tvWinPointsTitleIOne);
            holder.tvWinPointsTitle[0].setTypeface(typeface);
            holder.tvWinPointsTitle[0].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvWinPointsTitle[1] = vi.findViewById(R.id.tvWinPointsTitleITwo);
            holder.tvWinPointsTitle[1].setTypeface(typeface);
            holder.tvWinPointsTitle[1].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvWinPointsTitle[2] = vi.findViewById(R.id.tvWinPointsTitleIThree);
            holder.tvWinPointsTitle[2].setTypeface(typeface);
            holder.tvWinPointsTitle[2].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvWinPoints[0] = vi.findViewById(R.id.tvWinPointOne);
            holder.tvWinPoints[0].setTypeface(typeface);
            holder.tvWinPoints[0].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvWinPoints[1] = vi.findViewById(R.id.tvWinPointTwo);
            holder.tvWinPoints[1].setTypeface(typeface);
            holder.tvWinPoints[1].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.tvWinPoints[2] = vi.findViewById(R.id.tvWinPointThree);
            holder.tvWinPoints[2].setTypeface(typeface);
            holder.tvWinPoints[2].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getwidth(28));

            holder.llThirdUserData = vi.findViewById(R.id.llThirdUserData);

            if (newjarraArray.length() == 4) {
                holder.llThirdUserData.setVisibility(View.VISIBLE);
            } else {
                holder.llThirdUserData.setVisibility(View.GONE);
            }

            vi.setTag(holder);
            setScreen(vi);

        } else {

            holder = (Holder) vi.getTag();
        }

        Logger.print("Jarray  =>>>>>>>>>>>>>>>>>>>> " + jarraArray.toString()
                + " length => " + jarraArray.length());
        Logger.print("New Jarray  =>>>>>>>>>>>>>>>>>>>> " + newjarraArray.toString()
                + " length => " + newjarraArray.length());
        try {
            //int NO = 0;
            String score = "";
            int knockPenalty, deadwoodPoint, winningPoint, ginPoint, total;
            for (int i = 0; i < newjarraArray.length(); i++) {
                knockPenalty = 0;
                deadwoodPoint = 0;
                winningPoint = 0;
                ginPoint = 0;

                if (i == 0) {
                    holder.tvRoundTxt.setText(""
                            + newjarraArray.getJSONArray(i).get(position).toString());
                } else {

                    Logger.print("SCOREVOARD ARRAY DATA +> " + " i " + i + " " + newjarraArray.getJSONArray(i).get(position).toString());
                    if (newjarraArray.getJSONArray(i).getJSONObject(position).has(Parameters.KnockPenalty))
                        knockPenalty = newjarraArray.getJSONArray(i).getJSONObject(position).getInt(Parameters.KnockPenalty);

                    if (newjarraArray.getJSONArray(i).getJSONObject(position).has(Parameters.DeadwoodPoint))
                        deadwoodPoint = newjarraArray.getJSONArray(i).getJSONObject(position).getInt(Parameters.DeadwoodPoint);

                    if (newjarraArray.getJSONArray(i).getJSONObject(position).has(Parameters.WinningPoint))
                        winningPoint = newjarraArray.getJSONArray(i).getJSONObject(position).getInt(Parameters.WinningPoint);

                    if (newjarraArray.getJSONArray(i).getJSONObject(position).has(Parameters.GinPoint))
                        ginPoint = newjarraArray.getJSONArray(i).getJSONObject(position).getInt(Parameters.GinPoint);

                    Logger.print("->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + winningPoint + " " + i + " " + position);
                    /*if (deadwoodPoint > 0) {
                        score = deadwoodPoint + " (DP)";
                    }*/

                    holder.tvDeadwoodPoint[i - 1].setText("" + deadwoodPoint);

                    /*if (winningPoint > 0) {
                        if (deadwoodPoint > 0)
                            score = score.concat(" +\n");
                        score = score.concat(winningPoint + " (WP)");
                    }*/

                    if (winningPoint > 0) {
                        holder.tvWinPoints[i - 1].setText("" + winningPoint);
                    } else {
                        holder.tvWinPoints[i - 1].setText("0");
                    }

                    if (ginPoint > 0) {
                        /*if (deadwoodPoint > 0 || winningPoint > 0)
                            score = score.concat(" +\n");*/
                        holder.tvGinKnockPointsTitle[i - 1].setText("GP");
                        holder.tvGinKnockPoint[i - 1].setText("" + ginPoint);
                        //score = score.concat(ginPoint + " (GP)");
                    } else {
                        if (knockPenalty > 0) {
                        /*if (deadwoodPoint > 0 || winningPoint > 0 || ginPoint > 0)
                            score = score.concat(" +\n");
                        score = score.concat(knockPenalty + " (KP)");*/

                            holder.tvGinKnockPointsTitle[i - 1].setText("UC");
                            holder.tvGinKnockPoint[i - 1].setText("" + knockPenalty);
                        } else {
                            holder.tvGinKnockPoint[i - 1].setText("0");
                        }
                    }



                    /*total = deadwoodPoint + knockPenalty + winningPoint + ginPoint;
                    if (winningPoint > 0 || knockPenalty > 0 || ginPoint > 0) {
                        score = score.concat(" = " + total);
                    }

                    if (total <= 0) {
                        score = "0";
                    }*/
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return vi;

    }

    private void setScreen(View vi) {
        // TODO Auto-generated method stub

        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) vi.findViewById(R.id.llMainBg).getLayoutParams();
        frm.height = getHight(120);

        LinearLayout.LayoutParams lin = (LinearLayout.LayoutParams) vi.findViewById(R.id.llScore1Container).getLayoutParams();
        lin.leftMargin = getwidth(20);

        lin = (LinearLayout.LayoutParams) vi.findViewById(R.id.llScore2Container).getLayoutParams();
        lin.leftMargin = getwidth(20);

        lin = (LinearLayout.LayoutParams) vi.findViewById(R.id.llScore3Container).getLayoutParams();
        lin.leftMargin = getwidth(20);

        lin = (LinearLayout.LayoutParams) vi.findViewById(R.id.llScore11Container).getLayoutParams();
        lin.leftMargin = getwidth(20);

        lin = (LinearLayout.LayoutParams) vi.findViewById(R.id.llScore22Container).getLayoutParams();
        lin.leftMargin = getwidth(20);

        lin = (LinearLayout.LayoutParams) vi.findViewById(R.id.llScore33Container).getLayoutParams();
        lin.leftMargin = getwidth(20);

        /*linLay.setPadding(0, getHight(30), 0, getHight(30));*/

        /*lin = (LinearLayout.LayoutParams) vi.findViewById(R.id.llLastPlayer)
                .getLayoutParams();
        lin.leftMargin = getwidth(30);*/

    }

    public int getwidth(int val) {
        return (Screen_Width * val) / 1280;
    }

    public int getHight(int val) {
        return (scree_Hieght * val) / 720;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        return false;
    }

    class Holder {
        TextView tvRoundTxt;
        TextView[] tvGinKnockPointsTitle = new TextView[3];
        TextView[] tvGinKnockPoint = new TextView[3];

        TextView[] tvDeadwoodPointsTitle = new TextView[3];
        TextView[] tvDeadwoodPoint = new TextView[3];

        TextView[] tvWinPointsTitle = new TextView[3];
        TextView[] tvWinPoints = new TextView[3];

        LinearLayout llThirdUserData, llScore3Container, llScore2Container, llScore1Container;
    }

}
