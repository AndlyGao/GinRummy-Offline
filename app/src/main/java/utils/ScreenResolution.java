package utils;

import android.content.Context;

/**
 * Created by milin.patel on 6/15/2017.
 */

public class ScreenResolution {

    private static ScreenResolution instance;
    int height, height_ratio;
    int width, width_ratio;

    public static void initInstance() {

        if (instance == null) {
            instance = new ScreenResolution();
        }
    }

    public static ScreenResolution getInstance() {
        // Return the instance

        if (instance == null) {
            initInstance();
        }
        return instance;

    }

    public void CalculateDisplaySize(Context cc) {
        height = cc.getResources().getDisplayMetrics().heightPixels;
        width = cc.getResources().getDisplayMetrics().widthPixels;
        if (height > width) {
            width = cc.getResources().getDisplayMetrics().heightPixels;
            height = cc.getResources().getDisplayMetrics().widthPixels;
        }
        int r = GCD(width, height);
        width_ratio = width / r;
        height_ratio = height / r;
        double ratio = (double) 9 / (double) 16;

        //==== Set New Height acording ratio of  16:9
        height = (int) (ratio * width);

        r = GCD(width, height);
        width_ratio = width / r;
        height_ratio = height / r;
        PreferenceManager.setwidth(width);
        PreferenceManager.setheight(height);

    }

    private int GCD(int width, int height) {
        return (height == 0) ? width : GCD(height, width % height);
    }

    private int getheight(int val) {
        // TODO Auto-generated method stub
        return height * val / 720;
    }

    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return width * val / 1280;
    }
}
