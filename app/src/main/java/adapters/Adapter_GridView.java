package adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artoon.ginrummyoffline.R;

import java.util.ArrayList;

import DataStore.Item_Table;
import utils.C;

/**
 * @author manish.s
 */
public class Adapter_GridView extends ArrayAdapter<Item_Table> {

    Context context;
    int layoutResourceId;
    ArrayList<Item_Table> data = new ArrayList<Item_Table>();

    C c = C.getInstance();
    int[] point = {10, 10, 50, 50, 150, 150, 250, 250};

    public Adapter_GridView(Context context, int layoutResourceId, ArrayList<Item_Table> data) {

        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        // if (row == null) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        int scree_Hieght = context.getResources().getDisplayMetrics().heightPixels;
        int Screen_Width = context.getResources().getDisplayMetrics().widthPixels;

        holder = new RecordHolder();

        holder.main = row.findViewById(R.id.lnmainbg);
        holder.table_name = row.findViewById(R.id.tvtablename);
        holder.table_bootvalue = row.findViewById(R.id.tvbootvalue);
        holder.table_betvalue = row.findViewById(R.id.txt_init);
        holder.btnSelect = row.findViewById(R.id.btn_select);
        holder.iv_bg_animation = row.findViewById(R.id.iv_bg_animation);
        holder.main.setPadding(c.getwidth1(5), c.getheight1(5), c.getwidth1(5), c.getheight1(5));


//		LinearLayout lin1 = (LinearLayout) row.findViewById(R.id.table_ad_lin1);

        holder.table_bootvalue.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(20, Screen_Width));
        holder.table_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28, Screen_Width));
        holder.table_betvalue.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24, Screen_Width));
        holder.btnSelect.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24, Screen_Width));

//		lin1.setPadding(0, getHight(20, scree_Hieght), 0, getHight(20, scree_Hieght));

//		holder.table_name.setPadding(getwidth(60, Screen_Width), 0, 0, 0);

        holder.table_name.setTypeface(c.typeface);
        holder.table_bootvalue.setTypeface(c.typeface);
        holder.table_betvalue.setTypeface(c.typeface);
        holder.btnSelect.setTypeface(c.typeface);


//		if(position >= 4 ) {
//			if (position % 2 == 0) {
//				holder.main.setBackgroundResource(R.drawable.bgchip);
//			}
//		}else{
//			if (position % 2 != 0) {
//				holder.main.setBackgroundResource(R.drawable.bgchip);
//			}
//		}


        row.setTag(holder);

        Item_Table item = data.get(position);
        holder.table_name.setText(String.valueOf(item.getTableName()));
        holder.table_bootvalue.setText("" + C.getInstance().formatter.format(item.getBootMinValue()) + " - "
                + C.getInstance().formatter.format(item.getBootMaxValue()));
        holder.table_betvalue.setText(" Points : " + point[position]);

        LinearLayout.LayoutParams lin = (LinearLayout.LayoutParams) row.findViewById(R.id.btn_select).getLayoutParams();
        lin.height = c.getheight1(60);
        lin.width = c.getwidth1(180);
        lin.topMargin = c.getheight1(10);

        lin = (LinearLayout.LayoutParams) row.findViewById(R.id.tvtablename).getLayoutParams();
        lin.topMargin = c.getheight1(10);

        lin = (LinearLayout.LayoutParams) row.findViewById(R.id.txt_init).getLayoutParams();
        lin.topMargin = c.getheight1(10);

        lin = (LinearLayout.LayoutParams) row.findViewById(R.id.imgchips1).getLayoutParams();
        lin.topMargin = c.getheight1(10);
        lin.height = c.getheight1(50);
        lin.width = c.getwidth1(50);

        lin = (LinearLayout.LayoutParams) row.findViewById(R.id.tvbootvalue).getLayoutParams();
        lin.topMargin = c.getheight1(10);

        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) row.findViewById(R.id.frm_main_bg).getLayoutParams();
        frm.height = c.getheight1(260);
        frm.width = c.getwidth1(230);

        frm = (FrameLayout.LayoutParams) row.findViewById(R.id.iv_bg_animation).getLayoutParams();
        frm.height = c.getheight1(189);
        frm.width = c.getwidth1(180);


//		holder.table_betvalue.setText("" + C.getInstance().formatter.format(item.getMaxBootValue()));


        if (item.getIsFeatured() == 1) {

            row.setBackgroundColor(Color.parseColor("#45799C"));

        }

        return row;

    }

    public int getwidth(int val, int screen_width) {

        return (screen_width * val) / 1280;

    }

    public int getHight(int val, int screen_hight) {

        return (screen_hight * val) / 720;

    }

    static class RecordHolder {

        TextView table_name, table_bootvalue, table_betvalue;
        LinearLayout main;
        TextView btnSelect;
        ImageView iv_bg_animation;

    }
}