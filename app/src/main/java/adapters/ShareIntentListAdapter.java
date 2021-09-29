package adapters;

/**
 * Created by Artoon on 03-Jan-17.
 */

import android.app.Activity;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.artoon.ginrummyoffline.R;

import utils.C;

public class ShareIntentListAdapter extends ArrayAdapter {
    Activity context;
    Object[] items;
    boolean[] arrows;
    int layoutId;
    C c = C.getInstance();

    public ShareIntentListAdapter(Activity context, int layoutId, Object[] items) {
        super(context, layoutId, items);

        this.context = context;
        this.items = items;
        this.layoutId = layoutId;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.adaptershareintent, null);
        TextView label = row.findViewById(R.id.textadaptermm);
        label.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(28));
        label.setTypeface(c.typeface, Typeface.BOLD);
        String appname = ((ResolveInfo) items[pos]).activityInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
        label.setText(((ResolveInfo) items[pos]).activityInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
        ImageView image = row.findViewById(R.id.logomm);

        ImageView collect = row.findViewById(R.id.Colledcted);
        collect.setVisibility(View.INVISIBLE);
        image.setImageDrawable(((ResolveInfo) items[pos]).activityInfo.applicationInfo.loadIcon(context.getPackageManager()));


        FrameLayout.LayoutParams ln = (FrameLayout.LayoutParams) row.findViewById(R.id.textadaptermm).getLayoutParams();
        ln.width = c.getwidth1(400);
        ln.leftMargin = c.getwidth1(150);

        ln = (FrameLayout.LayoutParams) row.findViewById(R.id.Colledcted).getLayoutParams();
        ln.width = c.getwidth1(30);
        ln.height = c.getheight1(35);
        ln.rightMargin = c.getwidth1(20);

        ln = (FrameLayout.LayoutParams) row.findViewById(R.id.logomm).getLayoutParams();
        ln.leftMargin = c.getwidth1(10);


        return (row);
    }
}
