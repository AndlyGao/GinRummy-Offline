package utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artoon.ginrummyoffline.R;


/**
 * Created by milin.patel on 2/11/2017.
 */

public class ProgressDialog2 {

    Context context;
    String message;
    Activity activity;
    ImageView progessimg;
    TextView tvLodingText;
    C c;
    private Dialog dialog;

    public ProgressDialog2(Context con, final Activity activity) {
        context = con;
        c = C.getInstance();
        this.activity = activity;
    }

    public void show(String message) {
        this.message = message;

        dialog = new Dialog(context, R.style.Theme_Transparent);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        View v;


        v = activity.getLayoutInflater().inflate(R.layout.progress_layout, null, false);
        progessimg = v.findViewById(R.id.progessimg);
        tvLodingText = v.findViewById(R.id.tvLodingText);
        tvLodingText.setText(message);
        tvLodingText.setTextSize(TypedValue.COMPLEX_UNIT_PX, c.getwidth1(30));
        tvLodingText.setTypeface(c.typeface, Typeface.BOLD);

        LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) v.findViewById(R.id.progessimg).getLayoutParams();
        ll.width = c.getwidth1(100);
        ll.height = c.getheight1(100);


        Animation rotate = AnimationUtils.loadAnimation(context, R.anim.progress);
        rotate.setInterpolator(new LinearInterpolator());
        progessimg.startAnimation(rotate);

        dialog.setContentView(v);
        dialog.setCancelable(false);

        try {
            if (!activity.isFinishing()) {
                dialog.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dismiss() {

        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    if (progessimg.getAnimation() != null) {
                        progessimg.clearAnimation();
                    }
                    dialog.dismiss();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }


    }

}
