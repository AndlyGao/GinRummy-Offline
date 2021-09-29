package com.artoon.ginrummyoffline;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import hugo.weaving.DebugLog;
import utils.C;
import utils.Logger;
import utils.OnClearFromRecentService;


public class Login extends Activity {

    int Screen_Hight, Screen_Width;
    C c = C.getInstance();
    Typeface typeface;
    ImageView artoon_a, artoon_txt, artoon_line;
    FrameLayout artoon_layout;
    Animation artoonAnimation;
    boolean isAnimFinish = false;
    private Handler handler1 = new Handler();

    public static String GET(String url) {

        InputStream inputStream = null;
        String result = "";

        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //================================== HTML5 Offer ==============================================

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    @Override
    @DebugLog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.splash_screen);

        if (isTaskRoot() == false) {
            finish();
            return;
        }

//        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
//                Login.class));


        startService(new Intent(getBaseContext(), OnClearFromRecentService.class));


//        findViewByIds();
        typeface = Typeface.createFromAsset(getAssets(),
                "font/PoetsenOne-Regular.ttf");
        c.typeface = typeface;

        c.unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
//        c.deviceinfo = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.System.NAME);
        Logger.print(">>>>>>>>>>>>Device id<<<<<<<<<<<<::::::: " + c.unique_id + " >>>> " + c.deviceinfo);
//        c.fabricScreenView("Login");

        //==================================== Hourly  bonush ======================================
        if (getIntent().hasExtra("msg")) {

            if (getIntent().getExtras().getString("msg").equals("hourly")) {
                c.isShowHourlyBonus = true;
                Logger.print("WWWWWWWWWWWW NOTIIIIIIIIIIIIIII ::: " + getIntent().getExtras().getString("msg"));
            } else {
                Logger.print("WWWWWWWWWWWW NOTIIIIIIIIIIIIIII11111111 ::: " + getIntent().getExtras().getString("msg"));
            }
        }
//=====================================================================================================

//        c.height = Screen_Hight = this.getResources().getDisplayMetrics().heightPixels;
//        c.width = Screen_Width = this.getResources().getDisplayMetrics().widthPixels;
//
//
//        c.hideSystemUI(getWindow().getDecorView());
//        if (c.height > c.width) {
//            c.width = this.getResources().getDisplayMetrics().heightPixels;
//            c.height = this.getResources().getDisplayMetrics().widthPixels;
//        }
//
//        try {
//            if(c != null) {
//                PreferenceManager.setwidth(c.width);
//                PreferenceManager.setheight(c.height);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        c.contex = this;
        c.CalculateDisplaySize(this);

        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_logo).getLayoutParams();
        frm.height = c.getheight1(270);
        frm.width = c.getwidth1(544);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    this.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                System.out.println("KEYYYYYYYYYY>>>>>>>> "
                        + Base64.encodeToString(md.digest(), Base64.DEFAULT));
                //
            }
//            n0xLmaw2/RPrvXo4DJYvGVImj5M=
            // ========= New aRTOON ANIMNATION =========
            new SplashAnimation().start();

            // =========================================

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


        //================================= Offer ======================================================

        //====================================================================================================
//        artoonAnimation();
    }

    public boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    //====================================================================================================================
    @DebugLog
    private void findViewByIds() {
//        artoon_layout = (FrameLayout) findViewById(R.id.artoon_layout);
//        artoon_a = (ImageView) findViewById(R.id.artoon_a);
//        artoon_line = (ImageView) findViewById(R.id.artoon_patti);
//        artoon_txt = (ImageView) findViewById(R.id.artoon_txt);
//        artoon_txt.setVisibility(View.INVISIBLE);
    }

    @DebugLog
    private void artoonAnimation() {
        artoon_layout.setVisibility(View.VISIBLE);

        FrameLayout.LayoutParams ll;

        int width = Screen_Width * 558 / 1280;
        int height = width * 215 / 558;

        ll = (FrameLayout.LayoutParams) artoon_txt
                .getLayoutParams();
        ll.width = width;
        ll.height = height;
        width = Screen_Width * 128 / 1280;
        height = width * 170 / 128;

        ll = (FrameLayout.LayoutParams) artoon_a
                .getLayoutParams();
        ll.width = width;
        ll.height = height;
        ll.leftMargin = Screen_Width * 280 / 1280;
        // girl1.setVisibility(View.INVISIBLE);

        width = Screen_Width * 4 / 1280;
        height = width * 215 / 4;

        ll = (FrameLayout.LayoutParams) artoon_line
                .getLayoutParams();
        ll.width = width;
        ll.height = height;
        ll.leftMargin = Screen_Width * 445 / 1280;
        artoon_line.setVisibility(View.INVISIBLE);

        Animation a = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotation_a);
        final Animation scale_patti = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.scale_line);
        final Animation trans_artoon = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.translate_artoon);

        artoonAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.artoon_animation);

        artoonAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                isAnimFinish = true;
                artoon_layout.setVisibility(View.GONE);
            }
        });

        scale_patti.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (!isFinishing()) {
                    artoon_txt.startAnimation(trans_artoon);
                    artoon_txt.setVisibility(View.VISIBLE);
                }

            }
        });

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Login.this, Dashboard.class);
                startActivity(i);
                overridePendingTransition(R.anim.from_righttoleft, 0);
                finish();
            }
        }, 3500);

        a.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (!isFinishing()) {
                    artoon_line.startAnimation(scale_patti);
                    artoon_line.setVisibility(View.VISIBLE);
                }
            }
        });

        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                if (!isFinishing()) {
                    try {
                        if (artoon_layout.getAnimation() != null) {
                            artoon_layout.clearAnimation();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    artoon_layout.startAnimation(artoonAnimation);
                }
            }
        }.start();

        artoon_a.startAnimation(a);
    }

    @DebugLog
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler1 != null) {
            handler1.removeCallbacksAndMessages(null);
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {
                c.httpresp = result;
                Logger.print("<<< httpresp:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //======================================= New Artoon Animation =====================================================
    @DebugLog
    private class SplashAnimation {

        @DebugLog
        private void start() {

            final ImageView artoon_a = findViewById(R.id.artoon_logo);
            final ImageView artoon_txt = findViewById(R.id.artoon_txt);
            final FrameLayout mainFrame = findViewById(R.id.artoon_layout);
            mainFrame.setVisibility(View.VISIBLE);

            int width = c.width * 172 / 1280;
            int height = width * 262 / 172;
            LinearLayout.LayoutParams ln = (LinearLayout.LayoutParams) artoon_a.getLayoutParams();
            ln.rightMargin = c.width * 30 / 1280;
            ln.width = width;
            ln.height = height;

            width = c.width * 723 / 1280;
            height = width * 214 / 723;
            ln = (LinearLayout.LayoutParams) artoon_txt.getLayoutParams();
            ln.width = width;
            ln.height = height;


            Login.SplashAnimation.CustomAnimationDrawable cad = new Login.SplashAnimation.CustomAnimationDrawable((AnimationDrawable) getResources().getDrawable(R.drawable.artoon_animation)) {
                @Override
                void onAnimationFinish() {

//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent i = new Intent(Login.this, Dashboard.class);
//                            startActivity(i);
//                            overridePendingTransition(R.anim.from_righttoleft, 0);
//                            finish();
//                        }
//                    },2200)/*.start()*/;
                    AnimationSet set = new AnimationSet(false);

                    TranslateAnimation trans = new TranslateAnimation(0, 0, 0, -((c.height / 2) + (c.height * 262 / 720)));
                    trans.setDuration(1500);
                    trans.setFillAfter(true);

                    AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
                    alpha.setDuration(1500);
                    alpha.setFillAfter(true);

                    set.addAnimation(trans);
                    set.addAnimation(alpha);

                    set.setAnimationListener(new AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            artoon_a.setVisibility(View.INVISIBLE);

                            AnimationSet set = new AnimationSet(true);

                            ScaleAnimation scale = new ScaleAnimation(1f, 3.5f, 1f, 3.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            scale.setFillAfter(true); // Needed to keep the result of the animation
                            scale.setDuration(800);

                            AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
                            alpha.setDuration(800);
                            alpha.setFillAfter(true);

                            set.addAnimation(scale);
                            set.addAnimation(alpha);
                            set.setFillAfter(true);
                            artoon_txt.startAnimation(set);

                            set.setAnimationListener(new AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    artoon_txt.setVisibility(View.GONE);

                                    Logger.print("_SPLASH_ANIM : text visibility set");
                                    AlphaAnimation alpha = new AlphaAnimation(1f, 0f);
                                    alpha.setFillAfter(true);
                                    alpha.setDuration(500);
                                    mainFrame.startAnimation(alpha);


                                    alpha.setAnimationListener(new AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
//                                            logger.e(TAG, "33333333333333");
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            Logger.print("_SPLASH_ANIM : alpha end");
                                            mainFrame.setVisibility(View.GONE);
                                            Intent i = new Intent(Login.this, Dashboard.class);
                                            startActivity(i);
                                            overridePendingTransition(R.anim.from_righttoleft, 0);
                                            finish();
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    artoon_a.startAnimation(set);

                }
            };

            // Set the views drawable to our custom drawable
            artoon_a.setBackgroundDrawable(cad);

            cad.start();
        }

        public abstract class CustomAnimationDrawable extends AnimationDrawable {

            Handler mAnimationHandler;

            @DebugLog
            public CustomAnimationDrawable(AnimationDrawable aniDrawable) {
                for (int i = 0; i < aniDrawable.getNumberOfFrames(); i++) {
                    this.addFrame(aniDrawable.getFrame(i), aniDrawable.getDuration(i));
                }
            }

            @Override
            @DebugLog
            public void start() {
                super.start();
                mAnimationHandler = new Handler();
                mAnimationHandler.postDelayed(new Runnable() {

                    public void run() {
                        onAnimationFinish();
                    }
                }, getTotalDuration());

            }

            @DebugLog
            public int getTotalDuration() {

                int iDuration = 0;

                for (int i = 0; i < this.getNumberOfFrames(); i++) {
                    iDuration += this.getDuration(i);
                }

                return iDuration;
            }

            @DebugLog
            abstract void onAnimationFinish();
        }
    }
}