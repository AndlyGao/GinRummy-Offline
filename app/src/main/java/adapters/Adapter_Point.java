package adapters;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.artoon.ginrummyoffline.R;

import utils.C;

/**
 * Created by milin.patel on 7/4/2017.
 */

public class Adapter_Point extends BaseAdapter {

    C c = C.getInstance();
    Context context;
    int[] points;

    public Adapter_Point(int[] points, Context context) {

        this.points = points.clone();
        this.context = context;

    }

    @Override
    public int getCount() {
        return points.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        // if (row == null) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(R.layout.adapter_point, parent, false);

        int scree_Hieght = context.getResources().getDisplayMetrics().heightPixels;
        int Screen_Width = context.getResources().getDisplayMetrics().widthPixels;

        holder = new RecordHolder();

        holder.main = row.findViewById(R.id.lnmainbg);
        holder.table_bootvalue = row.findViewById(R.id.tvbootvalue);
        holder.table_betvalue = row.findViewById(R.id.txt_init);
        holder.btnSelect = row.findViewById(R.id.btn_select);
        holder.iv_bg_animation = row.findViewById(R.id.iv_bg_animation);
        holder.main.setPadding(c.getwidth1(5), c.getheight1(5), c.getwidth1(5), c.getheight1(5));


        holder.table_bootvalue.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(60));
        holder.table_betvalue.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(30));
        holder.btnSelect.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(24));


        holder.table_bootvalue.setTypeface(c.typeface);
        holder.table_betvalue.setTypeface(c.typeface);
        holder.btnSelect.setTypeface(c.typeface);

        row.setTag(holder);

        holder.table_bootvalue.setText("" + points[position]);
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.scalebounce);
        anim.setInterpolator(new LinearInterpolator());
        holder.iv_bg_animation.startAnimation(anim);


        FrameLayout.LayoutParams frm1 = (FrameLayout.LayoutParams) row.findViewById(R.id.btn_select).getLayoutParams();
        frm1.height = c.getheight1(60);
        frm1.width = c.getwidth1(180);
        frm1.bottomMargin = c.getheight1(10);

        frm1 = (FrameLayout.LayoutParams) row.findViewById(R.id.txt_init).getLayoutParams();
        frm1.topMargin = c.getheight1(30);

        frm1 = (FrameLayout.LayoutParams) row.findViewById(R.id.tvbootvalue).getLayoutParams();

        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) row.findViewById(R.id.lnmainbg).getLayoutParams();
        frm.height = c.getheight1(260);
        frm.width = c.getwidth1(230);

        frm = (FrameLayout.LayoutParams) row.findViewById(R.id.iv_bg_animation).getLayoutParams();
        frm.height = c.getheight1(189);
        frm.width = c.getwidth1(180);


        return row;

    }

    static class RecordHolder {

        TextView table_bootvalue, table_betvalue;
        FrameLayout main;
        TextView btnSelect;
        ImageView iv_bg_animation;

    }
}
