package utils;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Artoon on 18-Nov-16.
 */
public class animation_playnow extends Activity {

    int c;

    public animation_playnow() {
        c = 0;
    }

    public Animation stratanimation(float sx, float dx, float sy, float dy) {
        c++;
        Logger.print("SX>>>" + sx + " dX>>> " + dx + " Count>>>>" + c);
        Animation onclickoplaynow = new TranslateAnimation(0, dx, 0, 0);
        // onclickoplaynow.setFillEnabled(true);
        onclickoplaynow.setFillAfter(true);
        onclickoplaynow.setDuration(700);
//        onclickoplaynow.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//              //  animation.reset();
//
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
        return onclickoplaynow;
    }


}
