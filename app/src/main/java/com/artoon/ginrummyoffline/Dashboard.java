package com.artoon.ginrummyoffline;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.Snapshots;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import adapters.Adapter_quest_list;
import adapters.ShareIntentListAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import hugo.weaving.DebugLog;
import utils.C;
import utils.Logger;
import utils.NotifyUser;
import utils.Parameters;
import utils.PreferenceManager;
import utils.ProgressDialog2;
import utils.QuestDaily;
import utils.QuestLifeTime;
import utils.ResponseCodes;
import utils.SQLiteDailyBonus;
import utils.SQLiteManager;
import utils.TraslateAnimationManager;
import utils.animation_playnow;

public class Dashboard extends Activity implements OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "KKKKKK";
    private static final int APP_STATE_KEY = 0;
    private static final int RC_SAVED_GAMES = 9009;
    private static final int MAX_SNAPSHOT_RESOLVE_RETRIES = 3;
    public static Handler handler;
    @SuppressLint("StaticFieldLeak")
    //>>>>>>>>>>> Ads Statis\c
    public static boolean isFromSpin = false;
    public static boolean isFromDailyBonus = false;
    public static boolean isFromHorlyBonus = false;
    public static GoogleApiClient mGoogleApiClient;
    //>>>>>>>>>>> Ads Statis\c
    static SQLiteManager SQLiteManager;
    static HashMap<String, String> myMap = new HashMap<>();
    static Bitmap coverImage;
    private static int RC_SIGN_IN = 6005;
    private static int REQUEST_LEADERBOARD = 6018;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public FrameLayout playnowframe, playnowframe3;
    public FrameLayout playontableframe;
    public FrameLayout privateframe;
    public InterstitialAd mInterstitial;
    LinearLayout special_offer;
    FrameLayout setting_btn;
    ImageView INVITE_GET_BTN, INVITE_GET_BTN_middle;
    ImageView PLAY_NOW, PLAY_NOW_Middle, PLAY_NOW3, PLAY_NOW_Middle3, iv_offer1;
    ImageView PLAY_ON_TABLE, PLAY_ON_TABLE_middle;
    CircleImageView pic;
    TextView level_txt;
    TextView Chips_text;
    FrameLayout leaderboard_btn;
    FrameLayout invite_btn;
    TextView Version;
    TextView play_now_text;
    TextView play_now_text3;
    TextView play_on_table_text;
    TextView da_minigame;
    TextView rewarded_noti;
    boolean VideoTimer = false;
    LinearLayout profile_container;
    boolean first = false;
    FrameLayout main_layout;
    C myData = C.getInstance();
    int Screen_Hight, Screen_Width;
    Typeface typeface;
    Animation anim1, fromLeft, ToLeft, BonusPotAnim;
    FrameLayout frmPlainBg;
    Animation buttonPressed, handAnimation;
    TextView ShareText;
    TextView tvEntryText, tvEntryPoint, tvEntryPointPlayNow, tvEntryChipPlayNow, tvWatchVideo;
    FrameLayout frmPlayNowEntryPoint, frmPlayNowEntryPoint_2players, frmPlayNowEntryPoint_3players;
    SQLiteDailyBonus sqLiteDailyBonus;
    int respective_chips[] = {100, 200, 400, 1000, 1000};
    ImageView[] CoinAnimation = new ImageView[10];
    animation_playnow onclickply;
    TextView tvEntryChipPlayNow_3Players, tvEntryChipPlayNow_2Players;
    ArrayList<HashMap<String, String>> dailybonusCollectinfo = new ArrayList<>();
    QuestDaily qd;
    QuestLifeTime ql;
    DisplayImageOptions defaultOptions;
    ProgressBar offer_pbar;
    LinearLayout html5_lin;
    ImageView html5_bk, html5_middle;
    AlarmManager am;
    //    int countern = 0;
    RadioButton Gin, BigGin, Knock, OfferWall, Video, Share, Straight, Oklahoma;
    boolean isGin, isKnock, isOfferWall, isVideo, isShare, isStraiht, isOklahoma, isBigGin;
    String counter = "";
    Dialog QuestDialog;
    GridView questlistDaily;
    RadioButton daily, Lifetime;
    Adapter_quest_list questAdapterlist;
    //=====================Html5 offer====================
    ObjectAnimator imageViewObjectAnimator;
    FrameLayout videoAdframe;
    LinearLayout bottom_right_linear;
    //====================================================
    FrameLayout rate_frame, share_frame, removead_frame, offerwall_frame;
    ImageView rate_bg, share_bg, removead_bg, offerwall_bg;
    ImageView rate_icn, share_icn, removead_icn, offerwall_img;
    // ============================= HTML Offer ==========================
    TextView removead_text, share_text, rate_text1, offerwall_text;
    //==============================  Hourly Bonus ========================
    int whichBottom = 0;
    //========================================================================
    String Label;
    int SMX, SMY, DMX, DMY;
    TraslateAnimationManager traslateAnimationManager;
    Dialog ExitConfirmationDialog;
    Dialog playerChooserDialog;
    String path = "";
    String datastr = "";
    String indexfinal = "";
    String[] InAppSku = new String[5];
    int[] coins = new int[5];
    FrameLayout btn_achivement_icn, btn_quest_icn;
    ImageView iv_spin, iv_spintext, iv_spin_ring;
    TextView tv_spinglow;
    // TODO: 11/7/2017 savan.nasit >>>>>>>>>>>>>>>>>>> AdMob Rewarded Ads <<<<<<<<<<<<<<<<<<<
    private boolean isplayingstart;
    private Animation ShakeAnim;
    private boolean istestingmode = false; // Must Be false;
    private boolean isDbCollected;
    private boolean isShareViaApp = false;
    private ProgressDialog2 mProgressDialog;
    private boolean isStartGame = false;
    private Dialog OutOfChipsDialog;
    private AdRequest mRequest;
    private boolean isbackPress = false;
    private Dialog ConfirmationDialogInfo2;
    private Dialog HourlyDialog;
    private Dialog rateNowDialog;
    private Dialog ConfirmationDialogInfo;
    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;

    private boolean mSignInClicked = false;

    private ImageView videoIcon, videoIconchip1, videoIconchip2, videoIcontyre;
    private TextView videoText;

    private TextView notiquest, notiachive;
    private Timer mTimer1;
    private boolean isOfferPurchase = false;
    //=======================================Google Play Service ===================================
    private Dialog SpecialOfferDialog;

    /**
     * Get the data from the EditText.
     *
     * @return the String in the EditText, or "" if empty.
     */
    @DebugLog
    private static String getData() {


        JSONObject userObject = new JSONObject();
        myMap = new HashMap<>();

        try {
            myMap = SQLiteManager.getUserData(PreferenceManager.get_id());
            userObject.put(Parameters.User_Name, PreferenceManager.getUserName());
            userObject.put(Parameters.Chips, PreferenceManager.getChips());
            userObject.put(Parameters.PLAYED, myMap.get(Parameters.PLAYED));
            userObject.put(Parameters.WIN, myMap.get(Parameters.WIN));
            userObject.put(Parameters.BIG_WON, myMap.get(Parameters.BIG_WON));
            userObject.put(Parameters.BIG_GIN, PreferenceManager.GetQuestBigGinCount());
            userObject.put(Parameters.GIN, PreferenceManager.GetQuestGinCount());
            userObject.put(Parameters.KNOCK, PreferenceManager.GetQuestKnockCount());
            userObject.put(Parameters.VIDEO, PreferenceManager.GetQuestWatchVideoCount());
            userObject.put(Parameters.OFFERWALL, PreferenceManager.GetQuestOfferwallCount());
            userObject.put(Parameters.SHARE, PreferenceManager.GetQuestShareCount());
            userObject.put(Parameters.STRAIGHT, PreferenceManager.GetQuestStraightCount());
            userObject.put(Parameters.OKLAHOMA, PreferenceManager.GetQuestOklahomaCount());
            userObject.put(Parameters.GIN_PLAYED, PreferenceManager.GetGinPlayed());
            userObject.put(Parameters.GIN_WON, PreferenceManager.GetGinWon());
            userObject.put(Parameters.OKLAHOMA_PLAYED, PreferenceManager.GetOklahomaPlayed());
            userObject.put(Parameters.OKLAHOMA_WON, PreferenceManager.GetOklahomaWon());
            userObject.put(Parameters.STRAIGHT_PLAYED, PreferenceManager.GetStraightPlayed());
            userObject.put(Parameters.STRAIGHT_WON, PreferenceManager.GetStraightWon());

            // add this in get data
            userObject.put(Parameters.SHARECount, PreferenceManager.GetQuestShareClaimCountLife());
            userObject.put(Parameters.GINCount, PreferenceManager.GetQuestGinClaimCountLife());
            userObject.put(Parameters.BIG_GINCount, PreferenceManager.GetQuestBigGinClaimCountLife());
            userObject.put(Parameters.KNOCKCount, PreferenceManager.GetQuestKnockClaimCountLife());
            userObject.put(Parameters.VIDEOCount, PreferenceManager.GetQuestVideoClaimCountLife());
            userObject.put(Parameters.OFFERWALLCount, PreferenceManager.GetQuestOfferClaimCountLife());
            userObject.put(Parameters.STRAIGHTCount, PreferenceManager.GetQuestStraightClaimCountLife());
            userObject.put(Parameters.OKLAHOMACount, PreferenceManager.GetQuestOklahomaClaimCountLife());
            userObject.put(Parameters.RemoveadsClaimCount, PreferenceManager.GetQuestRemoveAdsClaimCountLife());


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String returnsrting = String.valueOf(userObject);
        return returnsrting;
    }

    /**
     * Update the Snapshot in the Saved Games service with new data.  Metadata is not affected,
     * however for your own application you will likely want to update metadata such as cover image,
     * played time, and description with each Snapshot update.  After update, the UI will
     * be cleared.
     */


    @DebugLog
    public static void savedGamesUpdate() {

        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
            return;
        }

//        final String snapshotName = makeSnapshotName(APP_STATE_KEY);
//        final boolean createIfMissing = true;
//
//        // Use the data from the EditText as the new Snapshot data.
//        final byte[] data = getData().getBytes();

        /*AsyncTask<Void, Void, Boolean> updateTask = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                //showProgressDialog("Updating User Data...");
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    if (mGoogleApiClient.isConnected()) {
                        Snapshots.OpenSnapshotResult open = Games.Snapshots.open(
                                mGoogleApiClient, snapshotName, createIfMissing).await();

                        //                if (!open.getStatus().isSuccess()) {
                        //                    Log.w(TAG, "KKKKKKKK>>> Could not open Snapshot for update.");
                        //                    return false;
                        //                }
                        Log.w(TAG, "KKKKKKKK>>> DATA UPLOAD >>> " + data);
                        Log.w(TAG, "KKKKKKKK>>> SNAP SHOT RESULT  >>> " + open.getStatus().getStatusMessage() + " : " + open.getStatus().getStatusCode());
                        // Change data but leave existing metadata
                        // Snapshot snapshot = processSnapshotOpenResult(open, 0);
                        Snapshot snapshot = null;
                        try {
                            if (open.getStatus().getStatusCode() == GamesStatusCodes.STATUS_SNAPSHOT_CONFLICT) {
                                snapshot = processSnapshotOpenResult(open, 0);
                                //                    //delete the snapshot
                                //                    Snapshots.DeleteSnapshotResult delete = (Snapshots.DeleteSnapshotResult)Games.Snapshots.delete
                                //                            (mGoogleApiClient, open.getSnapshot().getMetadata()).await();
                                //
                                //                    if (!delete.getStatus().isSuccess()) {
                                //                        Log.w(TAG, "Failed to delete Snapshot.");
                                //                        snapshot = open.getSnapshot();
                                //                    }else{
                                //                        Log.w(TAG, "KKKKKKKK>>> SnapShot Delete sucssefully >>> ");
                                //                        Snapshots.OpenSnapshotResult open2 = Games.Snapshots.open(
                                //                                mGoogleApiClient, snapshotName, createIfMissing).await();
                                //                        Log.w(TAG, "KKKKKKKK>>> NEW CREATED SNAP SHOT RESULT  >>> "+open2.getStatus().getStatusMessage() +" : "+open2.getStatus().getStatusCode() );
                                //                        snapshot = open2.getSnapshot();
                                //                    }


                            } else {
                                snapshot = open.getSnapshot();
                            }
                            if (snapshot == null) {
                                snapshot = open.getSnapshot();
                            } else {
                            }
                            if (snapshot != null) {
                                snapshot.getSnapshotContents().writeBytes(data);
                                //                Bitmap coverImage ;
                                //
                                //                if (PreferenceManager.get_picPath().length() > 0) {
                                //                    Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                                //                    if(d!=null) {
                                //                        coverImage = ((BitmapDrawable) d).getBitmap();
                                //                    }else{
                                //                        coverImage =  BitmapFactory.decodeResource(Dashboard.this.getResources(),
                                //                                R.drawable.me);
                                //                    }
                                //                } else {
                                //
                                //                    coverImage =  BitmapFactory.decodeResource(Dashboard.this.getResources(),
                                //                            R.drawable.me);
                                //                }

                                if (coverImage == null) {
                                    coverImage = BitmapFactory.decodeResource(context.getResources(),
                                            R.drawable.me);
                                }


                                //  Create the change operation
                                SnapshotMetadataChange metadataChange = new SnapshotMetadataChange.Builder()
                                        .setCoverImage(coverImage)
                                        .setDescription(PreferenceManager.getUserName() + " : " + PreferenceManager.get_id())
                                        .build();

                                Snapshots.CommitSnapshotResult commit = Games.Snapshots.commitAndClose(
                                        mGoogleApiClient, snapshot, *//*SnapshotMetadataChange.EMPTY_CHANGE*//*metadataChange).await();
                                if (!commit.getStatus().isSuccess()) {
                                    Log.w(TAG, "KKKKKKKK>>> Failed to commit Snapshot.");
                                    return false;
                                }
                                Log.w(TAG, "KKKKKKKK>>> Success commit Snapshot.");
                                // No failures
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    // displayMessage(getString(R.string.saved_games_update_success), false);
                } else {
                    // displayMessage(getString(R.string.saved_games_update_failure), true);
                }              //  dismissProgressDialog();
            }
        };
        updateTask.execute();*/
        new DownloadFilesTask().execute();
    }

    /**
     * Conflict resolution for when Snapshots are opened.  Must be run in an AsyncTask or in a
     * background thread,
     */
    @DebugLog
    static Snapshot processSnapshotOpenResult(Snapshots.OpenSnapshotResult result, int retryCount) {
        Snapshot mResolvedSnapshot;
        retryCount++;
        Log.i(TAG, "processSnapshotOpenResult >>> retryCount " + retryCount);
        int status = result.getStatus().getStatusCode();
        Log.i(TAG, "Save Result status: " + status);


        if (status == GamesStatusCodes.STATUS_OK) {
            return result.getSnapshot();
        } else if (status == GamesStatusCodes.STATUS_SNAPSHOT_CONTENTS_UNAVAILABLE) {
            return result.getSnapshot();
        } else if (status == GamesStatusCodes.STATUS_SNAPSHOT_CONFLICT) {
            try {
                Snapshot snapshot = result.getSnapshot();
                Snapshot conflictSnapshot = result.getConflictingSnapshot();
                byte[] SnapShotData = snapshot.getSnapshotContents().readFully();
                String data1 = new String(SnapShotData);
                byte[] conflictSnapshotData = conflictSnapshot.getSnapshotContents().readFully();
                String data2 = new String(conflictSnapshotData);
                Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> processSnapshotOpenResult >>> SnapShotData >>> " + data1);
                Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> processSnapshotOpenResult >>> conflictSnapshotData>>> " + data2);

                JSONObject userobj1;
                JSONObject userobj2;

                userobj1 = new JSONObject(data1);
                userobj2 = new JSONObject(data2);
                Logger.print("KKKKKKKKK>>> Google Play Service >>> processSnapshotOpenResult >>>  userobj: " + userobj1.getInt(Parameters.PLAYED));
                Logger.print("KKKKKKKKK>>> Google Play Service >>> processSnapshotOpenResult >>>  userobjconflic: " + userobj2.getInt(Parameters.PLAYED));
                int objcont = userobj1.getInt(Parameters.PLAYED);
                int objcontconflic = userobj2.getInt(Parameters.PLAYED);
                if (objcont > objcontconflic) {
                    mResolvedSnapshot = snapshot;
                    if (retryCount >= MAX_SNAPSHOT_RESOLVE_RETRIES) {
                        return result.getSnapshot();
                    }
                    //return snapshot;
                } else {
                    mResolvedSnapshot = conflictSnapshot;
                    if (retryCount >= MAX_SNAPSHOT_RESOLVE_RETRIES) {
                        return result.getConflictingSnapshot();

                    }
                    //return conflictSnapshot;
                }


                // Resolve between conflicts by selecting the newest of the conflicting snapshots.
                //  mResolvedSnapshot = snapshot;

//            if (snapshot.getMetadata().getLastModifiedTimestamp() <
//                    conflictSnapshot.getMetadata().getLastModifiedTimestamp()) {
//                mResolvedSnapshot = conflictSnapshot;
//            }
//
                Snapshots.OpenSnapshotResult resolveResult = Games.Snapshots.resolveConflict(
                        mGoogleApiClient, result.getConflictId(), mResolvedSnapshot).await();
//
                if (retryCount < MAX_SNAPSHOT_RESOLVE_RETRIES) {
//                // Recursively attempt again
                    return processSnapshotOpenResult(resolveResult, retryCount);
                } else {
                    if (objcont > objcontconflic) {


                        return snapshot;
                        //return snapshot;
                    } else {
                        return conflictSnapshot;
                        //return conflictSnapshot;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Fail, return null.
        return null;
    }

    /**
     * Generate a unique Snapshot name from an AppState stateKey.
     *
     * @param appStateKey the stateKey for the Cloud Save data.
     * @return a unique Snapshot name that maps to the stateKey.
     */
    @DebugLog
    private static String makeSnapshotName(int appStateKey) {
        return "GinRummyOffline-" + String.valueOf(appStateKey);
    }

    @DebugLog
    public static void DeleteFromSdCard() {

        String stored = null;
        Logger.print("KKKKKKKK >>> DeleteFromSdCard >>> ");
        PreferenceManager.set_Picpath("");
        File sdcard = Environment.getExternalStorageDirectory();

        File folder = new File(sdcard.getAbsoluteFile(), "");//the dot makes this directory hidden to the user
        folder.mkdir();
        File file = new File(folder.getAbsoluteFile(), "temp_photo" + ".jpg");
        if (file.exists()) {
            Logger.print("KKKKKKKK >>> DeleteFromSdCard >>> " + file.exists());
            file.delete();
        } else {
            Logger.print("KKKKKKKK >>> DeleteFromSdCard >>> " + file.exists());
        }
    }

    @DebugLog
    public static String saveToSdCard(Bitmap bitmap) {
        DeleteFromSdCard();
        String stored = null;
        Logger.print("KKKKKKKK >>> saveToSdCard >>> ");
        PreferenceManager.set_Picpath("");
        File sdcard = Environment.getExternalStorageDirectory();

        File folder = new File(sdcard.getAbsoluteFile(), "");//the dot makes this directory hidden to the user
        folder.mkdir();
        File file = new File(folder.getAbsoluteFile(), "temp_photo" + ".jpg");
        Logger.print("PPPPPPPPPPPPP >>>>> path >>> " + file.getPath());
        PreferenceManager.set_Picpath(file.getPath());
//        if (file.exists())
//            return stored ;

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            stored = "success";
        } catch (Exception e) {
            e.printStackTrace();
            DeleteFromSdCard();
            PreferenceManager.set_Picpath("");
            Logger.print("KKKKKKKK >>> saveToSdCard >>> Exception >>> ");
        }
        return stored;
    }

    @DebugLog
    public static InputStream getHttpConnection(String urlString) throws IOException {

        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("KKKKKKKK >>> downloadImage" + ex.toString());
        }
        return stream;
    }

    @DebugLog
    public static void DisconnectGoogleClient() {
        if (mGoogleApiClient.isConnected()) {
            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> DisConnect >>>> >>> ");
            mGoogleApiClient.disconnect();
        }

    }

    @DebugLog
    public void showAds() {
        Logger.print("GETTTTTT ADDDDDDDDDDDDDDDDD Winner class C class");
        try {

            mInterstitial = new InterstitialAd(this);
            mInterstitial.setAdUnitId("ca-app-pub-4109577825364690/7427397966");


            AdRequest adRequest = new AdRequest.Builder()
                    .build();


            mInterstitial.loadAd(adRequest);

            mInterstitial.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    if (isbackPress) {
                        setQuestAlarm();
                        setAchivementAlarm();
                        setHourlyBonusNotification();
                        finish();
                    } else {
                        requestNewInterstitial();
                    }
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                }
            });


        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @DebugLog
    public void requestNewInterstitial() {
        try {
            if (mInterstitial != null) {
                AdRequest adRequest = new AdRequest.Builder()
                        .build();

                mInterstitial.loadAd(adRequest);
            }
        } catch (OutOfMemoryError o) {
            o.printStackTrace();
        }
    }

    @Override
    @DebugLog

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        isStartGame = true;
        setContentView(R.layout.dashboard);
        try {
            myData.con = Dashboard.this;
        } catch (Exception r) {
            Log.e("RuntimeException ", "" + r);
        }

        SQLiteManager = new SQLiteManager(getApplicationContext());
        sqLiteDailyBonus = new SQLiteDailyBonus(getApplicationContext());

        Logger.print(">>> VVVVVVVV >>> NEW VERSION >>> " + PreferenceManager.GetIsNewVersion());

        myData.height = Screen_Hight = PreferenceManager.getheight();
        myData.width = Screen_Width = PreferenceManager.getwidth();
        myData.typeface = Typeface.createFromAsset(getAssets(), "font/PoetsenOne-Regular.ttf");
        myData.Chips = PreferenceManager.getChips();
        typeface = Typeface.createFromAsset(getAssets(),
                "font/PoetsenOne-Regular.ttf");
        Music_Manager m = new Music_Manager(getApplicationContext());
        anim1 = AnimationUtils.loadAnimation(this, R.anim.scalebounce);
        fromLeft = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in_right1);
        ToLeft = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in_left1);

        context = this;

        // Create the Google Api Client with access to the Play Games services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .setGravityForPopups(Gravity.TOP)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .addApi(Drive.API).addScope(Drive.SCOPE_APPFOLDER)
                //.setViewForPopups(findViewById(android.R.id.content))
                // add other APIs and scopes here as needed
                .build();

        if (!PreferenceManager.get_FirstTime() && PreferenceManager.get_Google_Sing_in() && PreferenceManager.isInternetConnected()) {
            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> CONNECTION START 6666 >>> ");
            mGoogleApiClient.connect();
        }

        PreferenceManager.setexitfromplaying(false);
        BonusPotAnim = AnimationUtils.loadAnimation(this, R.anim.scalebounce_infinite);
        new AnimationUtils();
        Animation alarmclockAnimation = AnimationUtils.loadAnimation(this, R.anim.alarm_infinite);
        alarmclockAnimation.setInterpolator(new DecelerateInterpolator());
        alarmclockAnimation.setRepeatCount(Animation.INFINITE);
        alarmclockAnimation.setRepeatMode(Animation.RESTART);


        myData.exitfromplaying = false;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (PreferenceManager.get_FirstTime()) {
            PreferenceManager.set_id(myData.unique_id);

            new insert().execute();
            DeleteFromSdCard();

            ShowSingInPopup();

        }

        if (PreferenceManager.GetIsNewVersion()) {

            PreferenceManager.SetIsNewVersion(false);
            HashMap<String, String> myMap = new HashMap<>();
            myMap = SQLiteManager.getUserData(PreferenceManager.get_id());
            Logger.print(">>> VVVVVVVV >>> USER DATA >>> " + myMap.toString());
            if (myMap.get("win") != null) {
                Logger.print(">>> VVVVVVVV >>> WIN COUNT UPDATE >>> ");
                String win = myMap.get("win");
                PreferenceManager.SetGinWon(Integer.parseInt(win));
            }
            if (myMap.get("played") != null) {
                Logger.print(">>> VVVVVVVV >>> PLAYED COUNT UPDATE >>> ");
                String played = myMap.get("played");
                PreferenceManager.SetGinPlayed(Integer.parseInt(played));
            }

        }


        String userId = PreferenceManager.get_id();
        Logger.print("User Ids => " + userId);
        // this must be user id which is unique for every user
        String appKey = myData.SUPER_SONIC_APPLICATION_KEY;

        IntegrationHelper.validateIntegration(this);
        showAds();

        Music_Manager music_manager = new Music_Manager(getApplicationContext());
        initHandler();
        FindViewByIds();
        setScreen();


        dailybonusCollectinfo = sqLiteDailyBonus.getBonusCollectInfo();

        Logger.print("MMMMMMMMMMMMMM>>>> dailybonusCollectinfo " + dailybonusCollectinfo);


        myMap = SQLiteManager.getUserData(PreferenceManager.get_id());


        Handler mainHandler = new Handler(this.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                myData.supersonicoffer(Dashboard.this, Dashboard.this);


            } // This is your code
        };
        mainHandler.post(myRunnable);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isStartGame) {
                    Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onCreated Check Load ");
                    if (mGoogleApiClient.isConnected()) {

                        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onCreated Post ");
                        updateScoreOnLeaderBoard();
                        if (isStartGame) {
                            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onCreated Load Data  ");
                            loadFromSnapshot(false);
                        }

                    }

                }

            }
        }, 10000);


        onclickply = new animation_playnow();

        qd = new QuestDaily(Dashboard.this);
        ql = new QuestLifeTime(Dashboard.this);

        //==================================Daily Bonus new Change========================================

        myData.DbcollectCount = PreferenceManager.GetDBCollectCount();
        Calendar c = Calendar.getInstance();

        myData.currentDate = c.get(Calendar.DATE);
        myData.currentMonth = c.get(Calendar.MONTH) + 1;
        myData.currentYear = c.get(Calendar.YEAR);
        myData.currentDay = c.get(Calendar.DAY_OF_YEAR);

        if (PreferenceManager.GetInstallday() == 0) {
            PreferenceManager.SetInstallday(myData.currentDay);
            PreferenceManager.SetInstallyear(myData.currentYear);
        }

        Logger.print("MMMMMMMMMMMMMM>>>> Day:   " + myData.currentDay);
        Logger.print("MMMMMMMMMMMMMM>>>> Date:  " + myData.currentDate);
        Logger.print("MMMMMMMMMMMMMM>>>> Month: " + myData.currentMonth);
        Logger.print("MMMMMMMMMMMMMM>>>> Year:  " + myData.currentYear);
        Logger.print("MMMMMMMMMMMMMM>>>> InstallDay:   " + PreferenceManager.GetInstallday());
        Logger.print("MMMMMMMMMMMMMM>>>> InstallYear:  " + PreferenceManager.GetInstallyear());


        isDbCollected = CheckisDatabaseCollected();

        //==========================================================================================

        if (PreferenceManager.GetDBDate() == 0) {
            PreferenceManager.SetDBDate(myData.currentDate);
            PreferenceManager.SetDBcollected(true);

            PreferenceManager.setShareBonushCollected_Facebook(false);
            PreferenceManager.setShareBonushCollected_WhatsApp(false);
        }
        Logger.print(">>>>>  db date =" + PreferenceManager.GetDBDate() + " ;; db collected or not =" + PreferenceManager.GetDBcollected());
        Logger.print(">>>>>>>>>>>> current date : " + myData.currentDate + " , Cal date : " + c.get(Calendar.DAY_OF_MONTH));

        if (myData.currentDate != PreferenceManager.GetDate()) {

            PreferenceManager.SetDate(myData.currentDate);


            PreferenceManager.SetQuestGinTodayCount(PreferenceManager.GetQuestGinCount());
            PreferenceManager.SetQuestKnockTodayCount(PreferenceManager.GetQuestKnockCount());
            PreferenceManager.SetQuestOfferWallTodayCount(PreferenceManager.GetQuestOfferwallCount());
            PreferenceManager.SetQuestWatchVideoTodayCount(PreferenceManager.GetQuestWatchVideoCount());
            PreferenceManager.SetQuestShareTodayCount(PreferenceManager.GetQuestShareCount());
            PreferenceManager.SetQuestStraightTodayCount(PreferenceManager.GetQuestStraightCount());
            PreferenceManager.SetQuestOklahomaTodayCount(PreferenceManager.GetQuestOklahomaCount());

            PreferenceManager.SetQuestGinClaimCountToday(0);
            PreferenceManager.SetQuestKnockClaimCountToday(0);
            PreferenceManager.SetQuestOfferClaimCountToday(0);
            PreferenceManager.SetQuestVideoClaimCountToday(0);
            PreferenceManager.SetQuestShareClaimCountToday(0);
            PreferenceManager.SetQuestStraightClaimCountToday(0);
            PreferenceManager.SetQuestOklahomaClaimCountToday(0);

            PreferenceManager.setBonusTimerCount(0);

            setRepeatingAlarm();
        }
        if (myData.currentDate != PreferenceManager.GetDBDate() && !isDbCollected) {
            PreferenceManager.setShareBonushCollected_WhatsApp(false);
            PreferenceManager.setShareBonushCollected_Facebook(false);
            startDailyBonus2();
        } else {
            if (!PreferenceManager.GetDBcollected()) {
                startDailyBonus2();
            } else if (myData.isShowHourlyBonus) {
                ShowHourlyBonusDialog();
            }
        }

        Logger.print("<<<<GET NOTIFICATION :" + PreferenceManager.getNotification());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //isStartGame =false;
            }
        }, 15000);


        //===================================== HTML 5 Offer =======================================
        defaultOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.bkimg)
                .showImageForEmptyUri(R.drawable.bkimg)
                .showImageOnFail(R.drawable.bkimg).cacheInMemory(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(
                defaultOptions).memoryCache(new WeakMemoryCache()).denyCacheImageMultipleSizesInMemory().build();
        ImageLoader.getInstance().init(config);

        setOfferImages();


        //========================= First Two Day ads not Show =====================================


        //todo>>>>>>>>>>>>>>>> AdMob Rewarded Ads <<<<<<<<<<<<<<<<<
        //todo>>>>>>>>>>>>>>>>^^^^^^^ AdMob Rewarded Ads <<<<<<<<<<<<<<<<<^^^^^^^
    }


    private void setOfferImages() {


        if (!myData.httpresp.equals("")) {

            try {

                offer_pbar.setVisibility(View.VISIBLE);
                html5_lin.setVisibility(View.VISIBLE);

                JSONObject obj = new JSONObject(myData.httpresp);

                final String bkImagepath = obj.getString("cpath");
                final String upImagepath = obj.getString("ipath");

                Logger.print("<<<<<< BK PATH:" + bkImagepath);
                Logger.print("<<<<<< BK PATH 11:" + upImagepath);

                if (!PreferenceManager.getBKImagePath().equals(bkImagepath)) {
                    MemoryCacheUtils.removeFromCache(PreferenceManager.getBKImagePath(), ImageLoader.getInstance().getMemoryCache());
                    DiskCacheUtils.removeFromCache(PreferenceManager.getBKImagePath(), ImageLoader.getInstance().getDiscCache());
                }
                if (!PreferenceManager.getUPImagePath().equals(upImagepath)) {
                    MemoryCacheUtils.removeFromCache(PreferenceManager.getUPImagePath(), ImageLoader.getInstance().getMemoryCache());
                    DiskCacheUtils.removeFromCache(PreferenceManager.getUPImagePath(), ImageLoader.getInstance().getDiscCache());
                }

                ImageLoader.getInstance().displayImage(bkImagepath, html5_bk, defaultOptions, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        super.onLoadingCancelled(imageUri, view);

                        offer_pbar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);

                        offer_pbar.setVisibility(View.GONE);
                        PreferenceManager.setBKImagePath(bkImagepath);

                    }

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        super.onLoadingStarted(imageUri, view);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        super.onLoadingFailed(imageUri, view, failReason);
                        offer_pbar.setVisibility(View.GONE);
                        PreferenceManager.setUPImagePath(upImagepath);
                    }
                });

                ImageLoader.getInstance().displayImage(upImagepath, html5_middle, defaultOptions);

            } catch (Exception e) {

                e.printStackTrace();
                html5_lin.setVisibility(View.GONE);

            }


        } else {

            html5_lin.setVisibility(View.GONE);

        }


    }

    private void ShowHourlyBonusDialog() {
        HourlyDialog = new Dialog(Dashboard.this, R.style.Theme_Transparent);
        HourlyDialog.requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        HourlyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        HourlyDialog.setContentView(R.layout.hourly_bonus_dialog);
        HourlyDialog.setCancelable(false);
        HourlyDialog.getWindow().getAttributes().windowAnimations = R.style.ActivityStyleDialogAnimation;

        ImageView hb_logo_img = HourlyDialog.findViewById(R.id.hb_logo_img);
        LinearLayout congrats_button_linear = HourlyDialog.findViewById(R.id.congrats_button_linear);
        TextView descriptions_txt = HourlyDialog.findViewById(R.id.descriptions_txt);
        TextView tx_hourly_2 = HourlyDialog.findViewById(R.id.tx_hourly_2);
        TextView tx_hourly_1 = HourlyDialog.findViewById(R.id.tx_hourly_1);
        final Button collect_hourly = HourlyDialog.findViewById(R.id.collect_hourly);
        final Button collect2x = HourlyDialog.findViewById(R.id.collect2x);
        Button close_hourly = HourlyDialog.findViewById(R.id.close_hourly);

        FrameLayout.LayoutParams frmPrm = (FrameLayout.LayoutParams) hb_logo_img.getLayoutParams();
        frmPrm.height = myData.getwidth1(430);
        frmPrm.width = myData.getwidth1(600);


        frmPrm = (FrameLayout.LayoutParams) close_hourly.getLayoutParams();
        frmPrm.height = myData.getwidth1(70);
        frmPrm.width = myData.getwidth1(70);
        frmPrm.topMargin = frmPrm.rightMargin = myData.getwidth1(10);

        frmPrm = (FrameLayout.LayoutParams) HourlyDialog.findViewById(R.id.ll_hourly_2).getLayoutParams();
        frmPrm.bottomMargin = myData.getwidth1(115);
        frmPrm.leftMargin = myData.getwidth1(210);
        frmPrm.height = myData.getwidth1(50);

        frmPrm = (FrameLayout.LayoutParams) HourlyDialog.findViewById(R.id.ll_hourly_1).getLayoutParams();
        frmPrm.bottomMargin = myData.getwidth1(115);
        frmPrm.rightMargin = myData.getwidth1(250);
        frmPrm.height = myData.getwidth1(50);


        LinearLayout.LayoutParams linPrm = (LinearLayout.LayoutParams) HourlyDialog.findViewById(R.id.iv_hourly_1).getLayoutParams();
        linPrm.height = myData.getwidth1(50);
        linPrm.width = myData.getwidth1(50);


        linPrm = (LinearLayout.LayoutParams) HourlyDialog.findViewById(R.id.iv_hourly_2).getLayoutParams();
        linPrm.height = myData.getwidth1(50);
        linPrm.width = myData.getwidth1(50);

        linPrm = (LinearLayout.LayoutParams) descriptions_txt.getLayoutParams();
        linPrm.topMargin = myData.getwidth1(30);
        linPrm.bottomMargin = myData.getwidth1(30);

        linPrm = (LinearLayout.LayoutParams) collect_hourly.getLayoutParams();
        linPrm.width = myData.getwidth1(200);
        linPrm.height = myData.getwidth1(80);
        linPrm.bottomMargin = myData.getwidth1(10);
        linPrm.rightMargin = myData.getwidth1(90);

        linPrm = (LinearLayout.LayoutParams) collect2x.getLayoutParams();
        linPrm.width = myData.getwidth1(320);
        linPrm.height = myData.getwidth1(100);
        linPrm.bottomMargin = myData.getwidth1(20);
        linPrm.leftMargin = myData.getwidth1(90);

        collect2x.setLayoutParams(linPrm);
        collect2x.setPadding(getwidth(65), getwidth(15), 0, 0);


        linPrm = (LinearLayout.LayoutParams) congrats_button_linear.getLayoutParams();
        linPrm.width = myData.getwidth1(950);

        descriptions_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(40));
        descriptions_txt.setTypeface(myData.typeface, Typeface.BOLD);

        tx_hourly_1.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(29));
        tx_hourly_1.setTypeface(myData.typeface);

        tx_hourly_2.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(29));
        tx_hourly_2.setTypeface(myData.typeface);

        collect_hourly.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(30));
        collect_hourly.setTypeface(myData.typeface, Typeface.BOLD);
        startHeartBeat(collect_hourly);

        collect2x.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(30));
        collect2x.setTypeface(myData.typeface, Typeface.BOLD);
        startHeartBeat(collect2x);

        myData.isShowHourlyBonus = false;


        close_hourly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C.dismissDialog(HourlyDialog);
            }
        });

        collect_hourly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                C.dismissDialog(HourlyDialog);

//                GiveVideoChips();
                Music_Manager.buttonclick();
                myData.Chips = PreferenceManager.getChips() + 250;
                PreferenceManager.setChips(myData.Chips);
                if (myData.Chips < 1000000) {

                    Chips_text.setText("" + myData.formatter.format(myData.Chips));

                } else {
                    Chips_text.setText("" + myData.getNumberFormatedValue(myData.Chips));
                }

//                myData.fabricHourlyBonus();
                showInfoDialog("Reward", "250 Chips Added");
                savedGamesUpdate();
                updateScoreOnLeaderBoard();

            }
        });

        collect2x.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceManager.isInternetConnected()) {

                } else {
                    showInfoDialog("Alert", "No Internet Connection.");
                }

            }
        });

        C.showDialog(HourlyDialog);

    }

    private void startHeartBeat(View imageView) {

        final com.nineoldandroids.animation.ObjectAnimator scaleDown = com.nineoldandroids.animation.ObjectAnimator.ofPropertyValuesHolder(imageView,
                PropertyValuesHolder.ofFloat("scaleX", 1.1f),
                PropertyValuesHolder.ofFloat("scaleY", 1.1f));
        scaleDown.setDuration(150);
        scaleDown.setStartDelay(1000);
        scaleDown.setRepeatCount(1);
        scaleDown.setRepeatMode(com.nineoldandroids.animation.ObjectAnimator.REVERSE);


        final com.nineoldandroids.animation.ObjectAnimator scaleUp = com.nineoldandroids.animation.ObjectAnimator.ofPropertyValuesHolder(imageView,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleUp.setDuration(150);
        scaleUp.setStartDelay(10);
        scaleUp.setRepeatCount(1);
        scaleUp.setRepeatMode(com.nineoldandroids.animation.ObjectAnimator.REVERSE);

        final AnimatorSet set = new AnimatorSet();
        set.play(scaleDown).before(scaleUp);
        set.start();

        scaleUp.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                set.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @DebugLog
    private boolean CheckisDatabaseCollected() {

        if (myData.currentYear < PreferenceManager.GetInstallyear()) {
            Logger.print("MMMMMMMMMMMMMM>>>> Date before Install111:>> ");
            return true;
        }

        if ((myData.currentYear == PreferenceManager.GetInstallyear())
                && (myData.currentDay < PreferenceManager.GetInstallday())) {
            Logger.print("MMMMMMMMMMMMMM>>>> Date before Install222:>> ");
            return true;
        }


        if (dailybonusCollectinfo.size() > 0) {


            for (int i = 0; i < dailybonusCollectinfo.size(); i++) {

                int date, month, year, day;

                date = Integer.valueOf(dailybonusCollectinfo.get(i).get(myData.DATE));
                month = Integer.valueOf(dailybonusCollectinfo.get(i).get(myData.MONTH));
                year = Integer.valueOf(dailybonusCollectinfo.get(i).get(myData.YEAR));
                day = Integer.valueOf(dailybonusCollectinfo.get(i).get(myData.DAY));

                Logger.print("MMMMMMMMMMMMMM>>>> Date:    " + date + "-" + month + "-" + year);

                if (myData.currentDate == date && myData.currentMonth == month && myData.currentYear == year && myData.currentDay == day) {
                    Logger.print("MMMMMMMMMMMMMM>>>> Already Collected on Date:>> " + date + "-" + month + "-" + year);
                    return true;

                }


            }


        }


        return false;
    }

    @DebugLog
    private void GiveVideoChips(final int Chip) {
        //Toast.makeText(getApplicationContext(),"250 added + "+c.Chips,Toast.LENGTH_LONG).show();
        myData.Chips = PreferenceManager.getChips() + Chip;

        PreferenceManager.setChips(myData.Chips);
        //Toast.makeText(getApplicationContext(),"250 added"+c.Chips,Toast.LENGTH_LONG).show();
        SQLiteManager.updateChip(PreferenceManager.get_id(), Chip, true);

        //c.Chips = PreferenceManager.getChips();
        updateScoreOnLeaderBoard();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (myData.Chips < 1000000) {

                    Chips_text.setText("" + myData.formatter.format(myData.Chips));

                } else {
                    Chips_text.setText("" + myData.numDifferentiation(myData.Chips));
                }

                //====================== Quest Counter ====================================
                PreferenceManager.SetQuestWatchVideoCount(PreferenceManager.GetQuestWatchVideoCount() + 1);
                Logger.print("MMMMMMMMM QUEST Counter>>> Watch Video increment >>> " + PreferenceManager.GetQuestWatchVideoCount());
                //=========================================================================

                setQuestNotifiaction();
                showInfoDialog("Reward Bonus", "" + Chip + " Chips Rewarded! Watch Again & get more!!");
                savedGamesUpdate();
            }
        });

    }

    @Override
    @DebugLog
    protected void onPause() {
        super.onPause();
        Logger.print(">>>>>>>>>>>><>><><><><><><><><><>>Play online disable called<<><><<><><><><><><><><><<>");
        INVITE_GET_BTN.setEnabled(false);

    }

    @DebugLog
    private void setRepeatingAlarm() {
        Logger.print("MMMMMMMMMMMMMMM>>> setRepeatingAlarm <<<");
        CancelAlarm();
        setFirstAlarm();
        setSecondAlarm();
    }

    @DebugLog
    private void setQuestAlarm() {
        if (PreferenceManager.GetIsQuestNotiEnable()) {
            Logger.print(">>>>>>>>>>>> MMMM Alarm Quest Dashboard >>>>===========.");
            AlarmManager am1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTimeInMillis(System.currentTimeMillis());
            if (calendar1.get(Calendar.HOUR_OF_DAY) > 23) {
                calendar1.set(Calendar.HOUR_OF_DAY, calendar1.get(Calendar.HOUR_OF_DAY) + 1 - 24);
            } else {
                calendar1.set(Calendar.HOUR_OF_DAY, calendar1.get(Calendar.HOUR_OF_DAY) + 1);
            }
            Intent intent1 = new Intent(this, NotifyUser.class);
            intent1.putExtra("for", Parameters.quest);
            PendingIntent pendingIntent11 = PendingIntent.getBroadcast(this, 15, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            boolean alarmUp1 = (PendingIntent.getBroadcast(this, 15, intent1, PendingIntent.FLAG_NO_CREATE) != null);
//        Logger.Print(">>>>>>>>>>>> first alrarm up " + alarmUp);

            if (alarmUp1) {
                am1.cancel(pendingIntent11);
//            Logger.Print(">>>>>>>>>>>> first alrarm canceled");
            }

            PendingIntent pendingIntent2 = PendingIntent.getService(this, 15, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            am1.setInexactRepeating(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar1.getTimeInMillis(), 0, pendingIntent2);

        }
    }

    @DebugLog
    private void setAchivementAlarm() {
        if (PreferenceManager.GetIsAchivementNotiEnable()) {
            Logger.print(">>>>>>>>>>>> MMMM Alarm Quest Dashboard  >>>>===========.");
            AlarmManager am1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTimeInMillis(System.currentTimeMillis());
            if (calendar1.get(Calendar.HOUR_OF_DAY) > 23) {
                calendar1.set(Calendar.HOUR_OF_DAY, calendar1.get(Calendar.HOUR_OF_DAY) + 1 - 24);
            } else {
                calendar1.set(Calendar.HOUR_OF_DAY, calendar1.get(Calendar.HOUR_OF_DAY) + 1);
            }
            Intent intent1 = new Intent(this, NotifyUser.class);
            intent1.putExtra("for", Parameters.achivement);
            PendingIntent pendingIntent11 = PendingIntent.getBroadcast(this, 14, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            boolean alarmUp1 = (PendingIntent.getBroadcast(this, 14, intent1, PendingIntent.FLAG_NO_CREATE) != null);
//        Logger.Print(">>>>>>>>>>>> first alrarm up " + alarmUp);

            if (alarmUp1) {
                am1.cancel(pendingIntent11);
//            Logger.Print(">>>>>>>>>>>> first alrarm canceled");
            }

            PendingIntent pendingIntent2 = PendingIntent.getService(this, 14, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            am1.setInexactRepeating(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar1.getTimeInMillis(), 0, pendingIntent2);

        }
    }

    public void setHourlyBonusNotification() {
        Logger.print(">>>> Hourly Bonus >>>> DashBoard >> ");
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        if (calendar.get(Calendar.HOUR_OF_DAY) > 22) {
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 2 - 24);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 2);
        }
        Intent intent = new Intent(this, NotifyUser.class);
        intent.putExtra("for", "DailyBonus2hour");
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 16, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp = (PendingIntent.getBroadcast(this, 16, intent, PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmUp) {
            am.cancel(pendingIntent1);
        }

        PendingIntent pendingIntent = PendingIntent.getService(this, 16, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), 0, pendingIntent);

    }

    @DebugLog
    private void setFirstAlarm() {
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR ) + 1);
        Intent intent = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 13, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp = (PendingIntent.getBroadcast(this, 13, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp) {
            am.cancel(pendingIntent1);
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 13, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent);

        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);

        Intent intent1 = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent11 = PendingIntent.getBroadcast(this, 14, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp1 = (PendingIntent.getBroadcast(this, 14, intent1, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp1) {
            am.cancel(pendingIntent11);
        }

        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 14, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent2);


        // ======== new day 5 and Day 10 logic .....
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 3);

        Intent intent2 = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent12 = PendingIntent.getBroadcast(this, 30, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp2 = (PendingIntent.getBroadcast(this, 30, intent2, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp2) {
            am.cancel(pendingIntent12);
        }

        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(this, 30, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent3);

        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 5);

        Intent intent3 = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent13 = PendingIntent.getBroadcast(this, 31, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp3 = (PendingIntent.getBroadcast(this, 31, intent3, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp3) {
            am.cancel(pendingIntent13);
        }

        PendingIntent pendingIntent4 = PendingIntent.getBroadcast(this, 31, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent4);
        // ======== new day 5 and Day 10 logic .....


    }

    @DebugLog
    private void setSecondAlarm() {
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR ) + 1);
        Intent intent = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 21, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp = (PendingIntent.getBroadcast(this, 21, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp) {
            am.cancel(pendingIntent1);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 21, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);

        Intent intent1 = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent11 = PendingIntent.getBroadcast(this, 22, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp1 = (PendingIntent.getBroadcast(this, 22, intent1, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp1) {
            am.cancel(pendingIntent11);
        }

        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 22, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent2);


        // ======== new day 5 and Day 10 logic .....
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 3);

        Intent intent2 = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent12 = PendingIntent.getBroadcast(this, 32, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp2 = (PendingIntent.getBroadcast(this, 32, intent2, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp2) {
            am.cancel(pendingIntent12);
        }

        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(this, 32, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent3);

        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 5);

        Intent intent3 = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent13 = PendingIntent.getBroadcast(this, 33, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp3 = (PendingIntent.getBroadcast(this, 33, intent3, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmUp3) {
            am.cancel(pendingIntent13);
        }

        PendingIntent pendingIntent4 = PendingIntent.getBroadcast(this, 33, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), pendingIntent4);
        // ======== new day 5 and Day 10 logic .....

    }

    @DebugLog
    private void startDailyBonus2() {
        Intent intent = new Intent(Dashboard.this, DailyBonus3.class);
        startActivity(intent);
        overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
    }

    @DebugLog
    private void showDefaultScreens() {
        // TODO Auto-generated method stub
        myData.showrateDialog = true;
        Intent share = new Intent(this, ActivityRateUs.class);
        startActivity(share);
        overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
    }

    @DebugLog
    private void initHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if (msg.what == ResponseCodes.UPDATE_CHIP) {

                    refreshData();

                } else if (msg.what == ResponseCodes.SEND_PURCHASE_DATA_TO_SERVER) {
                    try {
                        if (msg.obj != null) {
                            Logger.print(">>>> DashBoard >>> Payment >>> Data :" + msg.obj.toString());
                            JSONObject obj = new JSONObject(msg.obj.toString());
                            datastr = obj.getString(Parameters.data);
                            path = obj.getString(Parameters.path);
                            indexfinal = obj.getString(Parameters.IndesX1);

                            new MyAsyncTask().execute(path);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else if (msg.what == ResponseCodes.Finish_Playscreen) {

                    Logger.print(" WWWWW >>>>>>>>>> Win Lost popup Created >>>>> " + myData.lostChip);
                    Logger.print(">>>> LOST CHIP 5555 >>> " + myData.lostChip);

                    try {
                        PreferenceManager.setOpenCounter1(PreferenceManager.getOpenCounter() + 1);

                        if (myData.lostChip < 0) {
                            // ===Win ====
                            if (myData.exitFromPlayScreen) {
                                ShowOutOfChipsPopup(false, true, myData.lostChip);
                                myData.exitFromPlayScreen = false;
                            }

                        } else if (myData.lostChip > 0) {
                            // ==== lost ======
                            if (myData.exitFromPlayScreen) {
                                ShowOutOfChipsPopup(false, true, myData.lostChip);
                                myData.exitFromPlayScreen = false;
                            }
                            //+++++Draw+++++++
                        } else {
                            if (myData.gameCount != 0) {
                                ShowOutOfChipsPopup(false, true, myData.lostChip);
                                myData.exitFromPlayScreen = false;
                            }
                        }


                    } catch (Exception e) {

                        e.printStackTrace();
                    }


                    try {
                        PreferenceManager.setexitfromplaying(false);
                        myData.exitfromplaying = PreferenceManager.getexitfromplaying();

                        if (PreferenceManager.getisshowAd() /* && myData.is_Ads_Show_After_Two_Day*/) {
                            Logger.print("GETTTTTT ADDDDDDDDDDDDDDDDD Dashe board class");

                            if (mInterstitial != null && mInterstitial.isLoaded()) {
                                Logger.print("GETTTTTT ADDDDDDDDDDDDDDDDD Dashe board Load ");
                                if (!isFinishing()) {
                                    mInterstitial.show();
                                }
                            } else {
                                Logger.print("GETTTTTT ADDDDDDDDDDDDDDDDD Dashe board  nOT Load ");
                                if (mInterstitial != null) {
                                    requestNewInterstitial();
                                } else {
                                    showAds();
                                }
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else if (msg.what == ResponseCodes.VIDEOADS) {
                    if (PreferenceManager.isInternetConnected()) {

                    } else {
                        showInfoDialog("Alert", "No Internet Connection.");
                    }
                } else if (msg.what == ResponseCodes.PlayFromHoeToPlay) {
                    myData.gameType = 0;
                    PreferenceManager.setNumberOfPlayers(2);
                    SETBOOTVALUE();
                    PreferenceManager.setgameType(2);
                    PlayNow();
                } else if (msg.what == ResponseCodes.Start_Playing_Mode) {
                    SETBOOTVALUE();
                    PreferenceManager.setgameType(0);
                    PlayNow();
                } else if (msg.what == 7777) {
                    String chip = String.valueOf(msg.obj);
                    showInfoDialog("Daily Reward", "You Have Collect " + myData.getNumberFormatedValue(Long.parseLong(chip)) + " Chips...");
                    new insertDailybonus().execute();
                } else if (msg.what == ResponseCodes.REFRESHQUESTDATA) {

                    Chips_text.setText(myData.getNumberFormatedValue(PreferenceManager.getChips()));
                    updateScoreOnLeaderBoard();
                    savedGamesUpdate();
                    setQuestNotifiaction();


                } else if (msg.what == ResponseCodes.FINISH) {
                    showInfoDialogBuy("Out Of Chip", "You don't have enough chips to play on table!");
                } else if (msg.what == ResponseCodes.ShowPlayButton) {
                    // Logger.print("MMMMMMMMMMMMMMM1221212121212212121212 Handler");

                    playnowframe.clearAnimation();
                    playnowframe3.clearAnimation();
                    playontableframe.clearAnimation();
                    privateframe.clearAnimation();

                    playnowframe.setVisibility(View.VISIBLE);
                    playnowframe3.setVisibility(View.VISIBLE);
                    playontableframe.setVisibility(View.VISIBLE);
                    privateframe.setVisibility(View.VISIBLE);
                } else if (msg.what == 41417171) {
                    Chips_text.setText(myData.getNumberFormatedValue(PreferenceManager.getChips()));
                }

                return false;
            }
        });
    }

    @DebugLog
    private void AnimateWeeklyContestButton() {

        new AnimationUtils();
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scalebounce);
        anim.setInterpolator(new LinearInterpolator());
        Animation animr = AnimationUtils.loadAnimation(this, R.anim.scalebouncerevers);
        animr.setInterpolator(new LinearInterpolator());

        PLAY_NOW.startAnimation(anim);
        PLAY_NOW3.startAnimation(anim);
        iv_offer1.startAnimation(anim);
        PLAY_ON_TABLE.startAnimation(anim);
        INVITE_GET_BTN.startAnimation(anim);
        html5_bk.startAnimation(anim);


        PLAY_NOW_Middle.startAnimation(animr);
        PLAY_NOW_Middle3.startAnimation(animr);
        INVITE_GET_BTN_middle.startAnimation(animr);
        PLAY_ON_TABLE_middle.startAnimation(animr);

    }

    @Override
    @DebugLog

    protected void onResume() {
        super.onResume();
        myData.payment_Handler = handler;


        myData.isPlayingScreenOpen = false;

        myData.contex = this;

        myData.con = this;
        myData.activity = Dashboard.this;

        if (myData.mMediationAgent != null) {
            IronSource.onResume(this);
        }

        myData.contex = this;

        Handler mainHandler = new Handler(this.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Logger.print(">>>>>>>>>>>><>><><><><><><><><><>>22222resume22222<<><><<><><><><><><><><><<>");

                playnowframe.clearAnimation();
                playontableframe.clearAnimation();
                privateframe.clearAnimation();

                playnowframe.setVisibility(View.VISIBLE);
                playontableframe.setVisibility(View.VISIBLE);
                privateframe.setVisibility(View.VISIBLE);
                IronSource.getOfferwallCredits();


            } // This is your code
        };
        mainHandler.post(myRunnable);

        //==========================================================================================

        if (isShareViaApp) {
            isShareViaApp = false;


            stopTimer();
            Logger.print("MMMMMMMMMMM>>> Shareclickintime " + myData.Shareclickintime);

            if (myData.Shareclickintime >= 7) {

                try {


                    //====================== Quest Counter ====================================
                    PreferenceManager.SetQuestShareCount(PreferenceManager.GetQuestShareCount() + 1);
                    Logger.print("MMMMMMMMM QUEST Counter>>> Knock increment >>> " + PreferenceManager.GetQuestShareCount());
                    //=========================================================================


                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }


        //==================================================================================================
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Logger.print(">>>>>>>>>>>><>><><><><><><><><><>>Play online enable called<<><><<><><><><><><><><><<>");

                INVITE_GET_BTN.setEnabled(true);
            }
        }, 1000);


        playnowframe.setVisibility(View.VISIBLE);
        playontableframe.setVisibility(View.VISIBLE);
        privateframe.setVisibility(View.VISIBLE);

        PLAY_NOW.clearAnimation();
        PLAY_NOW3.clearAnimation();
        iv_offer1.clearAnimation();
        PLAY_NOW_Middle.clearAnimation();
        PLAY_NOW_Middle3.clearAnimation();
        PLAY_ON_TABLE.clearAnimation();
        INVITE_GET_BTN.clearAnimation();


        PLAY_NOW.setEnabled(true);
        PLAY_NOW3.setEnabled(true);
        AnimateWeeklyContestButton();
        try {

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancelAll();
        } catch (Exception e) {
            Logger.print("ON Resume::::Notification");
        }


        Logger.print("dashboard onResume => " + myData.showMessageOnDashboard + " " + myData.ERROR_CODE + " " + C.NO_CHIPS);
        if (myData.showMessageOnDashboard) {
            myData.showMessageOnDashboard = false;
            if (myData.errorCode == C.NO_CHIPS) {
                showInfoDialogBuy("Out Of Chip", "You don't have enough chips to play on table!");
            }
        }


        refreshData();
    }

    @DebugLog
    private void setQuestNotifiaction() {

        int questnotifiacationcounter = qd.getNoachivmentCompleted();
        int achivmrntnotifiacationcounter = ql.getNoachivmentCompleted();

        if (questnotifiacationcounter > 0) {
            notiquest.setVisibility(View.VISIBLE);
            notiquest.setText("" + questnotifiacationcounter);
            notiquest.setBackgroundResource(R.drawable.bg_count_red);
            PreferenceManager.SetIsQuestNotiEnable(true);
        } else {
//            notiquest.setVisibility(View.VISIBLE);
            int count = qd.getNoachivmentRemain();
            if (count > 0) {
                notiquest.setVisibility(View.INVISIBLE);
//                notiquest.setText("" + count);
//                notiquest.setBackgroundResource(R.drawable.bg_count_red);
            } else {
                notiquest.setVisibility(View.INVISIBLE);
            }


            PreferenceManager.SetIsQuestNotiEnable(false);
        }

        if (achivmrntnotifiacationcounter > 0) {
            notiachive.setVisibility(View.VISIBLE);
            notiachive.setText("" + achivmrntnotifiacationcounter);
            notiachive.setBackgroundResource(R.drawable.bg_count_red);
            PreferenceManager.SetIsAchivementNotiEnable(true);
        } else {
//            notiachive.setVisibility(View.VISIBLE);

            int count = ql.getNoachivmentRemain();

            if (count > 0) {
                notiachive.setVisibility(View.INVISIBLE);
                notiachive.setText("" + count);
                notiachive.setBackgroundResource(R.drawable.bg_count_red);
            } else {
                notiachive.setVisibility(View.INVISIBLE);
            }
            PreferenceManager.SetIsAchivementNotiEnable(false);
        }


    }

    @DebugLog
    private void showQuestTestDialog() {


        if (QuestDialog != null || (QuestDialog != null && QuestDialog.isShowing())) {
            if (QuestDialog.isShowing()) {
                QuestDialog.dismiss();
            }
            QuestDialog = null;
        }

        QuestDialog = new Dialog(Dashboard.this, R.style.Theme_Transparent);
        QuestDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        QuestDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        QuestDialog.setContentView(R.layout.dialog333);
        QuestDialog.setCancelable(false);


        TextView title = QuestDialog
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(convertedint(20, 1.2)), getheight(convertedint(20, 1.2)), getwidth(convertedint(20, 1.2)),
                getheight(convertedint(20, 1.2)));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(38));
        title.setTypeface(typeface, Typeface.BOLD);


        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) QuestDialog.findViewById(
                R.id.lnmain).getLayoutParams();
        frm.height = getheight(convertedint(528, 1.2));
        frm.width = getwidth(convertedint(843, 1.2));


        RadioGroup questRadiioGrp = QuestDialog.findViewById(R.id.radiogrpquest);


        BigGin = QuestDialog.findViewById(R.id.rdbtnBigGin);
        Gin = QuestDialog.findViewById(R.id.rdbtnGin);
        Knock = QuestDialog.findViewById(R.id.rdbtnKnock);
        OfferWall = QuestDialog.findViewById(R.id.rdbtnOfferwall);
        Video = QuestDialog.findViewById(R.id.rdbtnvideo);
        Share = QuestDialog.findViewById(R.id.rdbtnShare);
        Straight = QuestDialog.findViewById(R.id.rdbtnStraight);
        Oklahoma = QuestDialog.findViewById(R.id.rdbtnOklahoma);

        Button close = QuestDialog.findViewById(R.id.close);
        final Button Add = QuestDialog.findViewById(R.id.btnadd);
        final Button Set = QuestDialog.findViewById(R.id.btnSet);
        final EditText editText = QuestDialog.findViewById(R.id.tvMessage);

        questlistDaily = QuestDialog.findViewById(R.id.questdata);

        BigGin.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        Gin.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        Knock.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        OfferWall.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        Video.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        Share.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        Straight.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        Oklahoma.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        Add.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        Set.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));

        BigGin.setTypeface(myData.typeface, Typeface.BOLD);
        Gin.setTypeface(myData.typeface, Typeface.BOLD);
        Knock.setTypeface(myData.typeface, Typeface.BOLD);
        OfferWall.setTypeface(myData.typeface, Typeface.BOLD);
        Video.setTypeface(myData.typeface, Typeface.BOLD);
        Share.setTypeface(myData.typeface, Typeface.BOLD);
        Straight.setTypeface(myData.typeface, Typeface.BOLD);
        Oklahoma.setTypeface(myData.typeface, Typeface.BOLD);
        Add.setTypeface(myData.typeface, Typeface.BOLD);
        Set.setTypeface(myData.typeface, Typeface.BOLD);

        LinearLayout.LayoutParams lin;
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                setQuestNotifiaction();
                QuestDialog.dismiss();

            }
        });


        Add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();


                Add.startAnimation(buttonPressed);
                counter = String.valueOf(editText.getText());
                if (counter.equalsIgnoreCase("")) {
                    editText.setHint("Please Enter Digits");
                    return;
                }
                if (isBigGin) {
                    PreferenceManager.SetQuestBigGinCount(PreferenceManager.GetQuestBigGinCount() + Integer.parseInt(counter));
                } else if (isGin) {
                    PreferenceManager.SetQuestGinCount(PreferenceManager.GetQuestGinCount() + Integer.parseInt(counter));
                } else if (isKnock) {
                    PreferenceManager.SetQuestKnockCount(PreferenceManager.GetQuestKnockCount() + Integer.parseInt(counter));
                } else if (isOfferWall) {
                    PreferenceManager.SetQuestOfferWallCount(PreferenceManager.GetQuestOfferwallCount() + Integer.parseInt(counter));
                } else if (isVideo) {
                    PreferenceManager.SetQuestWatchVideoCount(PreferenceManager.GetQuestWatchVideoCount() + Integer.parseInt(counter));
                } else if (isShare) {
                    PreferenceManager.SetQuestShareCount(PreferenceManager.GetQuestShareCount() + Integer.parseInt(counter));
                } else if (isStraiht) {
                    PreferenceManager.SetQuestStraightCount(PreferenceManager.GetQuestStraightCount() + Integer.parseInt(counter));
                } else if (isOklahoma) {
                    PreferenceManager.SetQuestOklahomaCount(PreferenceManager.GetQuestOklahomaCount() + Integer.parseInt(counter));
                }


                editText.setText("");
            }
        });

        Set.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();

                Set.startAnimation(buttonPressed);
                counter = String.valueOf(editText.getText());
                if (counter.equalsIgnoreCase("")) {
                    editText.setHint("Please Enter Digits");
                    return;
                }
                if (isBigGin) {
                    PreferenceManager.SetQuestBigGinCount(Integer.parseInt(counter));
                } else if (isGin) {
                    PreferenceManager.SetQuestGinCount(Integer.parseInt(counter));
                } else if (isKnock) {
                    PreferenceManager.SetQuestKnockCount(Integer.parseInt(counter));
                } else if (isOfferWall) {
                    PreferenceManager.SetQuestOfferWallCount(Integer.parseInt(counter));
                } else if (isVideo) {
                    PreferenceManager.SetQuestWatchVideoCount(Integer.parseInt(counter));
                } else if (isShare) {
                    PreferenceManager.SetQuestShareCount(Integer.parseInt(counter));
                } else if (isStraiht) {
                    PreferenceManager.SetQuestStraightCount(Integer.parseInt(counter));
                } else if (isOklahoma) {
//                    PreferenceManager.SetQuestOklahomaCount(Integer.parseInt(counter));
                    PreferenceManager.setChips(Integer.parseInt(counter));
                }

                editText.setText("");
            }
        });


        questRadiioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rdbtnBigGin) {

                    BigGin.setAlpha(1.0f);
                    BigGin.setEnabled(false);
                    Knock.setAlpha(.6f);
                    Knock.setEnabled(true);
                    Gin.setAlpha(.6f);
                    Gin.setEnabled(true);
                    OfferWall.setAlpha(.6f);
                    OfferWall.setEnabled(true);
                    Share.setAlpha(.6f);
                    Share.setEnabled(true);
                    Video.setAlpha(.6f);
                    Video.setEnabled(true);
                    Straight.setAlpha(.6f);
                    Straight.setEnabled(true);
                    Oklahoma.setAlpha(.6f);
                    Oklahoma.setEnabled(true);

                    isGin = false;
                    isBigGin = true;
                    isKnock = false;
                    isOfferWall = false;
                    isVideo = false;
                    isShare = false;
                    isStraiht = false;
                    isOklahoma = false;


                } else if (checkedId == R.id.rdbtnGin) {

                    Gin.setAlpha(1.0f);
                    Gin.setEnabled(false);
                    BigGin.setAlpha(.6f);
                    BigGin.setEnabled(true);
                    Knock.setAlpha(.6f);
                    Knock.setEnabled(true);
                    OfferWall.setAlpha(.6f);
                    OfferWall.setEnabled(true);
                    Share.setAlpha(.6f);
                    Share.setEnabled(true);
                    Video.setAlpha(.6f);
                    Video.setEnabled(true);
                    Straight.setAlpha(.6f);
                    Straight.setEnabled(true);
                    Oklahoma.setAlpha(.6f);
                    Oklahoma.setEnabled(true);

                    isGin = true;
                    isBigGin = false;
                    isKnock = false;
                    isOfferWall = false;
                    isVideo = false;
                    isShare = false;
                    isStraiht = false;
                    isOklahoma = false;


                } else if (checkedId == R.id.rdbtnKnock) {

                    Knock.setAlpha(1.0f);
                    Knock.setEnabled(false);
                    Gin.setAlpha(.6f);
                    Gin.setEnabled(true);
                    BigGin.setAlpha(.6f);
                    BigGin.setEnabled(true);
                    OfferWall.setAlpha(.6f);
                    OfferWall.setEnabled(true);
                    Share.setAlpha(.6f);
                    Share.setEnabled(true);
                    Video.setAlpha(.6f);
                    Video.setEnabled(true);
                    Straight.setAlpha(.6f);
                    Straight.setEnabled(true);
                    Oklahoma.setAlpha(.6f);
                    Oklahoma.setEnabled(true);

                    isGin = false;
                    isBigGin = false;
                    isKnock = true;
                    isOfferWall = false;
                    isVideo = false;
                    isShare = false;
                    isStraiht = false;
                    isOklahoma = false;

                } else if (checkedId == R.id.rdbtnOfferwall) {

                    Gin.setAlpha(.6f);
                    Gin.setEnabled(true);
                    BigGin.setAlpha(.6f);
                    BigGin.setEnabled(true);
                    Knock.setAlpha(.6f);
                    Knock.setEnabled(true);
                    OfferWall.setAlpha(1f);
                    OfferWall.setEnabled(false);
                    Share.setAlpha(.6f);
                    Share.setEnabled(true);
                    Video.setAlpha(.6f);
                    Video.setEnabled(true);
                    Straight.setAlpha(.6f);
                    Straight.setEnabled(true);
                    Oklahoma.setAlpha(.6f);
                    Oklahoma.setEnabled(true);

                    isGin = false;
                    isBigGin = false;
                    isKnock = false;
                    isOfferWall = true;
                    isVideo = false;
                    isShare = false;
                    isStraiht = false;
                    isOklahoma = false;

                } else if (checkedId == R.id.rdbtnvideo) {

                    Gin.setAlpha(.6f);
                    Gin.setEnabled(true);
                    BigGin.setAlpha(.6f);
                    BigGin.setEnabled(true);
                    Knock.setAlpha(.6f);
                    Knock.setEnabled(true);
                    OfferWall.setAlpha(.6f);
                    OfferWall.setEnabled(true);
                    Share.setAlpha(.6f);
                    Share.setEnabled(true);
                    Video.setAlpha(1f);
                    Video.setEnabled(false);
                    Straight.setAlpha(.6f);
                    Straight.setEnabled(true);
                    Oklahoma.setAlpha(.6f);
                    Oklahoma.setEnabled(true);

                    isGin = false;
                    isBigGin = false;
                    isKnock = false;
                    isOfferWall = false;
                    isVideo = true;
                    isShare = false;
                    isStraiht = false;
                    isOklahoma = false;

                } else if (checkedId == R.id.rdbtnShare) {
                    Gin.setAlpha(.6f);
                    Gin.setEnabled(true);
                    BigGin.setAlpha(.6f);
                    BigGin.setEnabled(true);
                    Knock.setAlpha(.6f);
                    Knock.setEnabled(true);
                    OfferWall.setAlpha(.6f);
                    OfferWall.setEnabled(true);
                    Share.setAlpha(1f);
                    Share.setEnabled(false);
                    Video.setAlpha(.6f);
                    Video.setEnabled(true);
                    Straight.setAlpha(.6f);
                    Straight.setEnabled(true);
                    Oklahoma.setAlpha(.6f);
                    Oklahoma.setEnabled(true);

                    isGin = false;
                    isBigGin = false;
                    isKnock = false;
                    isOfferWall = false;
                    isVideo = false;
                    isShare = true;
                    isStraiht = false;
                    isOklahoma = false;

                } else if (checkedId == R.id.rdbtnStraight) {
                    Gin.setAlpha(.6f);
                    Gin.setEnabled(true);
                    BigGin.setAlpha(.6f);
                    BigGin.setEnabled(true);
                    Knock.setAlpha(.6f);
                    Knock.setEnabled(true);
                    OfferWall.setAlpha(.6f);
                    OfferWall.setEnabled(true);
                    Share.setAlpha(.6f);
                    Share.setEnabled(true);
                    Video.setAlpha(.6f);
                    Video.setEnabled(true);
                    Straight.setAlpha(1f);
                    Straight.setEnabled(false);
                    Oklahoma.setAlpha(.6f);
                    Oklahoma.setEnabled(true);

                    isGin = false;
                    isBigGin = false;
                    isKnock = false;
                    isOfferWall = false;
                    isVideo = false;
                    isShare = false;
                    isStraiht = true;
                    isOklahoma = false;

                } else if (checkedId == R.id.rdbtnOklahoma) {
                    Gin.setAlpha(.6f);
                    Gin.setEnabled(true);
                    BigGin.setAlpha(.6f);
                    BigGin.setEnabled(true);
                    Knock.setAlpha(.6f);
                    Knock.setEnabled(true);
                    OfferWall.setAlpha(.6f);
                    OfferWall.setEnabled(true);
                    Share.setAlpha(.6f);
                    Share.setEnabled(true);
                    Video.setAlpha(.6f);
                    Video.setEnabled(true);
                    Straight.setAlpha(.6f);
                    Straight.setEnabled(true);
                    Oklahoma.setAlpha(1f);
                    Oklahoma.setEnabled(false);

                    isGin = false;
                    isBigGin = false;
                    isKnock = false;
                    isOfferWall = false;
                    isVideo = false;
                    isShare = false;
                    isStraiht = false;
                    isOklahoma = true;

                }

            }
        });


        Gin.setChecked(true);
        BigGin.setChecked(false);
        Knock.setChecked(false);
        OfferWall.setChecked(false);
        Video.setChecked(false);
        Share.setChecked(false);


        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.close).getLayoutParams();
        lin.width = myData.getwidth1(convertedint(50, 1.2));
        lin.height = myData.getheight1(convertedint(50, 1.2));
        lin.rightMargin = myData.getwidth1(convertedint(30, 1.2));
        lin.topMargin = myData.getheight1(convertedint(20, 1.2));

        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.tvMessage).getLayoutParams();
        lin.topMargin = myData.getwidth1(convertedint(40, 1.2));
        lin.width = myData.getwidth1(300);

        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.tvTitle).getLayoutParams();
        lin.topMargin = myData.getwidth1(convertedint(5, 1.2));

        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.radiogrpquest).getLayoutParams();
        lin.topMargin = myData.getwidth1(convertedint(20, 1.2));


        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.rdbtnGin).getLayoutParams();
        lin.width = myData.getwidth1((int) Math.round(248 * .7));
        lin.height = myData.getheight1((int) Math.round(80 * .7));

        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.rdbtnKnock).getLayoutParams();
        lin.width = myData.getwidth1((int) Math.round(248 * .7));
        lin.height = myData.getheight1((int) Math.round(80 * .7));
        lin.leftMargin = myData.getwidth1(10);

        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.rdbtnBigGin).getLayoutParams();
        lin.width = myData.getwidth1((int) Math.round(248 * .7));
        lin.height = myData.getheight1((int) Math.round(80 * .7));
        lin.leftMargin = myData.getwidth1(10);

        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.rdbtnOfferwall).getLayoutParams();
        lin.width = myData.getwidth1((int) Math.round(248 * .7));
        lin.height = myData.getheight1((int) Math.round(80 * .7));
        lin.leftMargin = myData.getwidth1(10);

        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.rdbtnvideo).getLayoutParams();
        lin.width = myData.getwidth1((int) Math.round(248 * .7));
        lin.height = myData.getheight1((int) Math.round(80 * .7));
        lin.leftMargin = myData.getwidth1(10);

        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.rdbtnShare).getLayoutParams();
        lin.width = myData.getwidth1((int) Math.round(248 * .7));
        lin.height = myData.getheight1((int) Math.round(80 * .7));
        lin.leftMargin = myData.getwidth1(10);

        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.btnadd).getLayoutParams();
        lin.width = myData.getwidth1((int) Math.round(248 * .7));
        lin.height = myData.getheight1((int) Math.round(80 * .7));
        lin.topMargin = myData.getheight1(20);

        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.btnSet).getLayoutParams();
        lin.width = myData.getwidth1((int) Math.round(248 * .7));
        lin.height = myData.getheight1((int) Math.round(80 * .7));
        lin.topMargin = myData.getheight1(20);


        lin = (LinearLayout.LayoutParams) QuestDialog.findViewById(R.id.line).getLayoutParams();
        lin.width = myData.getwidth1(convertedint(600, 1.2));
        lin.height = myData.getheight1(convertedint(4, 1.2));
        lin.topMargin = myData.getheight1(20);


        //questlist.setHorizontalSpacing(getwidth(5));
        //questlist.setVerticalSpacing(getheight(5));


        if (!isFinishing()) {
            QuestDialog.show();
        }

    }

    @DebugLog
    int convertedint(double no, double multy) {


        return (int) Math.round(no * multy);

    }

    @DebugLog
    public void ShowOutOfChipsPopup(boolean isEarnChips, boolean showChips, final long Chips) {


        OutOfChipsDialog = new Dialog(Dashboard.this, R.style.Theme_Transparent);
        OutOfChipsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        OutOfChipsDialog.setContentView(R.layout.out_of_chips_full);

        LinearLayout title_linear, chip_linear, middle_main_linear;
        ImageView watch_now_2, offerwall_2, store_2, title_line, chip_icon, chips_underline, close_img;
        Button offerwall_btn, store_btn;
        TextView watch_now_1, offerwall_1, title_txt, chip_txt2, store_1;

        title_linear = OutOfChipsDialog.findViewById(R.id.title_linear);
        chip_linear = OutOfChipsDialog.findViewById(R.id.chip_linear);
        middle_main_linear = OutOfChipsDialog.findViewById(R.id.middle_main_linear);

        watch_now_1 = OutOfChipsDialog.findViewById(R.id.watch_now_1);
        watch_now_2 = OutOfChipsDialog.findViewById(R.id.watch_now_2);
        offerwall_1 = OutOfChipsDialog.findViewById(R.id.offerwall_1);
        offerwall_2 = OutOfChipsDialog.findViewById(R.id.offerwall_2);
        store_1 = OutOfChipsDialog.findViewById(R.id.store_1);
        store_2 = OutOfChipsDialog.findViewById(R.id.store_2);
        title_line = OutOfChipsDialog.findViewById(R.id.title_line);
        chip_icon = OutOfChipsDialog.findViewById(R.id.chip_icon);
        chips_underline = OutOfChipsDialog.findViewById(R.id.chips_underline);
        close_img = OutOfChipsDialog.findViewById(R.id.close_img);

        Button watch_now_btn = OutOfChipsDialog.findViewById(R.id.watch_now_btn);
        offerwall_btn = OutOfChipsDialog.findViewById(R.id.offerwall_btn);
        store_btn = OutOfChipsDialog.findViewById(R.id.store_btn);

        title_txt = OutOfChipsDialog.findViewById(R.id.title_txt);
        chip_txt2 = OutOfChipsDialog.findViewById(R.id.chip_txt2);

        System.out.println("_WWWWWWWWWWWWW::::::::::" + myData.width + ":::::::" + myData.height);


        String isWin = "Win";
        if (!isEarnChips) {
            title_txt.setText("Out Of Chips");


            if (showChips) {

                if (Chips < 0) {
                    chip_txt2.setText("" + myData.getNumberFormatedValue(Math.abs(-Chips)));
                    title_txt.setText("You Win Chips");
                    isWin = "Win";
                } else {
                    chip_txt2.setText("" + myData.getNumberFormatedValue(Math.abs(Chips)));
                    title_txt.setText("You Lost Chips");
                    isWin = "Lost";
                }
                System.out.println("_TABLE CHIPS:::" + Chips);
            }

        }


        FrameLayout.LayoutParams fprm = (FrameLayout.LayoutParams) close_img.getLayoutParams();

        fprm.height = getheight(70);
        fprm.width = myData.getwidth1(80);
        fprm.topMargin = fprm.rightMargin = myData.getwidth1(25);


        LinearLayout.LayoutParams lp;

        lp = (LinearLayout.LayoutParams) title_linear.getLayoutParams();
        lp.topMargin = myData.getwidth1(25);


        lp = (LinearLayout.LayoutParams) title_txt.getLayoutParams();
        lp.height = myData.getheight1(70);
        lp.width = myData.getwidth1(900);

        lp = (LinearLayout.LayoutParams) title_line.getLayoutParams();
        lp.width = myData.getwidth1(920);
        lp.topMargin = myData.getwidth1(30);

        lp = (LinearLayout.LayoutParams) chip_linear.getLayoutParams();
        lp.topMargin = myData.getwidth1(20);

        lp = (LinearLayout.LayoutParams) middle_main_linear.getLayoutParams();
        lp.width = myData.getwidth1(920);
        lp.topMargin = myData.getwidth1(30);

        lp = (LinearLayout.LayoutParams) chips_underline.getLayoutParams();
        lp.width = myData.getwidth1(920);
        lp.topMargin = myData.getwidth1(15);

        lp = (LinearLayout.LayoutParams) chip_icon.getLayoutParams();
        lp.rightMargin = myData.getwidth1(5);
        lp.height = lp.width = myData.getwidth1(35);


        lp = (LinearLayout.LayoutParams) watch_now_1.getLayoutParams();
        lp.bottomMargin = myData.getwidth1(20);
        lp.height = myData.getwidth1(70);
        lp.width = myData.getwidth1(230);

        store_1.setLayoutParams(lp);


        lp = (LinearLayout.LayoutParams) offerwall_1.getLayoutParams();
        lp.bottomMargin = myData.getwidth1(20);
        lp.height = myData.getwidth1(70);
        lp.width = myData.getwidth1(230);


        lp = (LinearLayout.LayoutParams) watch_now_2.getLayoutParams();
        lp.bottomMargin = myData.getwidth1(50);
        lp.height = lp.width = myData.getwidth1(150);

        offerwall_2.setLayoutParams(lp);
        store_2.setLayoutParams(lp);


        lp = (LinearLayout.LayoutParams) watch_now_btn.getLayoutParams();
        lp.bottomMargin = myData.getwidth1(20);
        lp.height = myData.getwidth1(80);
        lp.width = myData.getwidth1(190);
        store_btn.setLayoutParams(lp);
        offerwall_btn.setLayoutParams(lp);


        title_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(45));
        watch_now_btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(25));
        offerwall_btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(25));
        store_btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(25));
        chip_txt2.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(31));
        offerwall_1.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(32));
        store_1.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(32));
        watch_now_1.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(32));

        title_txt.setTypeface(myData.typeface);
        offerwall_btn.setTypeface(myData.typeface);
        store_btn.setTypeface(myData.typeface);
        watch_now_btn.setTypeface(myData.typeface);
        chip_txt2.setTypeface(myData.typeface);
        offerwall_1.setTypeface(myData.typeface);
        store_1.setTypeface(myData.typeface);
        watch_now_1.setTypeface(myData.typeface);


        final String finalIsWin = isWin;
        watch_now_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    Music_Manager.buttonclick();

                    if (PreferenceManager.isInternetConnected()) {

                    } else {
                        showInfoDialog("Alert", "No Internet Connection.");
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });

        offerwall_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                try {
                    try {
                        Music_Manager.buttonclick();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    myData.fabricWinLost(Math.abs(Chips), finalIsWin, "OfferWall");
                    offerwall_frame.performClick();
                    OutOfChipsDialog.dismiss();

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });

        store_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    Music_Manager.buttonclick();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                myData.fabricWinLost(Math.abs(Chips), finalIsWin, "Buy Now");
                Intent intent = new Intent(Dashboard.this,
                        Coin_per.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_righttoleft,
                        R.anim.none);
                OutOfChipsDialog.dismiss();

            }
        });

        close_img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    try {
//                        myData.fabricWinLost(Math.abs(Chips), finalIsWin, "Close");
                        Music_Manager.buttonclick();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {

                        if (!PreferenceManager.GetIsRateGive()) {
                            if (PreferenceManager.getOpenCounter() >= 0 && PreferenceManager.getOpenCounter() <= 5) {
                                if (PreferenceManager.getOpenCounter() == 5) {
                                    showDefaultScreens();
                                    PreferenceManager.setOpenCounter1(0);
                                }
                            } else {
                                showDefaultScreens();
                                PreferenceManager.setOpenCounter1(0);
                            }

                        }

                        OutOfChipsDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });

        if (!isFinishing()) {
            OutOfChipsDialog.show();
        }
    }

    @DebugLog
    public void SETBOOTVALUE() {
        Logger.print("LLLLLLLLLLL SetbootValue() Method Called >>> ");
        myData.Chips = PreferenceManager.getChips();
        if (myData.Chips >= 500 && myData.Chips <= 15000) {

            int[] boot = {500, 1000, 1500, 2000};

            myData.initialBootValue = getBootValue(boot);
            myData.initialRoundPoint = 10;
            myData.tableindex = 0;

        } else if (myData.Chips > 15000 && myData.Chips < 35000) {
            int[] boot = {3000, 4000, 5000, 6000};
            myData.initialBootValue = getBootValue(boot);
            myData.initialRoundPoint = 10;
            myData.tableindex = 1;
        } else if (myData.Chips >= 35000 && myData.Chips < 75000) {
            int[] boot = {7000, 9500, 12000, 14500};
            myData.initialBootValue = getBootValue(boot);
            myData.initialRoundPoint = 50;
            myData.tableindex = 2;
        } else if (myData.Chips >= 75000 && myData.Chips < 200000) {
            int[] boot = {15000, 20000, 25000, 30000};
            myData.initialBootValue = getBootValue(boot);
            myData.initialRoundPoint = 50;
            myData.tableindex = 3;

        } else if (myData.Chips >= 200000 && myData.Chips < 375000) {
            int[] boot = {40000, 50000, 60000, 70000};
            myData.initialBootValue = getBootValue(boot);
            myData.initialRoundPoint = 150;
            myData.tableindex = 4;

        } else if (myData.Chips >= 375000 && myData.Chips < 1000000) {
            int[] boot = {75000, 100000, 125000, 150000};
            myData.initialBootValue = getBootValue(boot);
            myData.initialRoundPoint = 150;
            myData.tableindex = 5;

        } else if (myData.Chips >= 1000000 && myData.Chips < 2000000) {
            int[] boot = {200000, 250000, 300000, 350000};
            myData.initialBootValue = getBootValue(boot);
            myData.initialRoundPoint = 250;
            myData.tableindex = 6;

        } else if (myData.Chips >= 2000000) {
            int[] boot = {400000, 500000, 600000, 700000};
            myData.initialBootValue = getBootValue(boot);
            myData.initialRoundPoint = 250;
            myData.tableindex = 7;
        }

        PreferenceManager.setBootvalue(myData.initialBootValue);
        Logger.print("LLLLLLLLLLL SetbootValue() Method Called Return BooTValue >>> " + PreferenceManager.getBootvalue());
    }

    private int getBootValue(int[] boot) {
        int bootvalue = boot[0];
        for (int i = boot.length - 1; i >= 0; i--) {
            if (PreferenceManager.getChips() > boot[i] * 5) {
                return boot[i];
            }
        }
        return bootvalue;
    }

    @DebugLog
    public void PlayNow() {

        if (myData.Chips >= 500) {

            Intent inn44 = new Intent(getApplicationContext(), PlayScreen2.class);
            inn44.putExtra("totalPlayer", PreferenceManager.getNumberOfPlayers());
            startActivity(inn44);


        } else {
            showInfoDialogBuy("Out Of Chip", "You don't have enough chips to play on table!");
        }
    }

    private void ShowSpecialOfferPopup() {
        String PurchaseJson = null;
        JSONObject jsonObject = null;
        int index = 0;
        String SkuId = "";

        if (SpecialOfferDialog != null || (SpecialOfferDialog != null && SpecialOfferDialog.isShowing())) {
            if (SpecialOfferDialog.isShowing()) {
                SpecialOfferDialog.dismiss();
            }
            SpecialOfferDialog = null;
        }
        SpecialOfferDialog = new Dialog(Dashboard.this,
                R.style.Theme_Transparent);
        SpecialOfferDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SpecialOfferDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        SpecialOfferDialog.setContentView(R.layout.special_offer);

        //>>>>>>>>>>>>>>> Set Design <<<<<<<<<<<<<<<<<<<<<

        FrameLayout.LayoutParams frm;
        LinearLayout.LayoutParams ll;

        frm = (FrameLayout.LayoutParams) SpecialOfferDialog.findViewById(R.id.offer_popup).getLayoutParams();
        frm.width = getwidth(850);
        frm.height = getheight(600);

        frm = (FrameLayout.LayoutParams) SpecialOfferDialog.findViewById(R.id.header_image).getLayoutParams();
        frm.width = getwidth(820);
        frm.height = getheight(120);

        frm = (FrameLayout.LayoutParams) SpecialOfferDialog.findViewById(R.id.offer_Main_chip).getLayoutParams();
        frm.width = getwidth(770);
        frm.height = getheight(550);
        frm.topMargin = getheight(25);

        frm = (FrameLayout.LayoutParams) SpecialOfferDialog.findViewById(R.id.chip_sticker).getLayoutParams();
        frm.width = getwidth(160);
        frm.height = getwidth(160);
        frm.rightMargin = getwidth(235);

        frm = (FrameLayout.LayoutParams) SpecialOfferDialog.findViewById(R.id.bottom_header).getLayoutParams();
        frm.width = getwidth(815);
        frm.height = getheight(120);
        frm.bottomMargin = getheight(30);

        frm = (FrameLayout.LayoutParams) SpecialOfferDialog.findViewById(R.id.ll_bottomheader).getLayoutParams();
        frm.height = getheight(120);
        frm.bottomMargin = getheight(30);

        SpecialOfferDialog.findViewById(R.id.ll_bottomheader).setPadding(0, getwidth(10), 0, 0);
        frm = (FrameLayout.LayoutParams) SpecialOfferDialog.findViewById(R.id.ll_middle).getLayoutParams();
        frm.height = getheight(120);
        frm.topMargin = getheight(85);

        ll = (LinearLayout.LayoutParams) SpecialOfferDialog.findViewById(R.id.fl_dash).getLayoutParams();
        ll.height = getheight(50);

        ll = (LinearLayout.LayoutParams) SpecialOfferDialog.findViewById(R.id.ll_main_offer).getLayoutParams();
        ll.height = getheight(70);

        ll = (LinearLayout.LayoutParams) SpecialOfferDialog.findViewById(R.id.iv_minichip).getLayoutParams();
        ll.width = getwidth(45);
        ll.height = getwidth(45);

        ll = (LinearLayout.LayoutParams) SpecialOfferDialog.findViewById(R.id.iv_big_chip).getLayoutParams();
        ll.width = getwidth(60);
        ll.height = getwidth(60);

        //>>>>>>>>>>>>>>> Set Design <<<<<<<<<<<<<<<<<<<<<

        TextView old_prize, new_prize, tx_starter, price_pack;
        old_prize = SpecialOfferDialog.findViewById(R.id.old_prize);
        new_prize = SpecialOfferDialog.findViewById(R.id.new_prize);
        tx_starter = SpecialOfferDialog.findViewById(R.id.tx_starter);
        price_pack = SpecialOfferDialog.findViewById(R.id.price_pack);

        //>>>>>>>>>>>>>>> Set Text and Size <<<<<<<<<<<<<<<<<<<<<
        old_prize.setTypeface(typeface);
        new_prize.setTypeface(typeface);
        tx_starter.setTypeface(typeface);
        price_pack.setTypeface(typeface);

        old_prize.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));
        new_prize.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(48));
        tx_starter.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(30));
        price_pack.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(49));
        //>>>>>>>>>>>>>>> Set Text and Size <<<<<<<<<<<<<<<<<<<<<

        final String[] PriceValue = {"$11.99", "$5.99", "$3.49", "$1.49", "$3.49"};
        String[] chipold = {"5,000,000", "1,500,000", "500,000", "100,000", "0"};
        String[] chipnew = {"10,000,000", "3,000,000", "1,000,000", "200,000", "0"};

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        if (!(PreferenceManager.GetPurchaseJsonObject() == null)) {
            PurchaseJson = PreferenceManager.GetPurchaseJsonObject();
            try {
                jsonObject = new JSONObject(PreferenceManager.GetPurchaseJsonObject());
                index = jsonObject.getInt(Parameters.PurchaseIndex);
                SkuId = (String) jsonObject.get(Parameters.PurchaseSkuid);
//                Toast.makeText(getApplicationContext(), "" + index, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        price_pack.setText("" + PriceValue[index] + " ");
        new_prize.setText("" + chipnew[index]);
        old_prize.setText("" + chipold[index]);

        Log.e("Json Data >>>>>> ", "" + jsonObject);

        ImageView buy = SpecialOfferDialog.findViewById(R.id.btn_Buy);
        final int finalIndex = index;
        final String finalSkuId = SkuId;
        buy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject developerPayload = new JSONObject();
                    developerPayload.put("_id", "" + finalIndex);
                    developerPayload.put("price", PriceValue[finalIndex]);
                    developerPayload.put(Parameters.isOfferPurchase, true);
                    Log.e("Json Data >>>>>> ", "" + finalSkuId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        if (!isFinishing())
            SpecialOfferDialog.show();

    }

    @DebugLog
    private void showInfoDialog(String DialogTitle, String Message) {
        // TODO Auto-generated method stub

        LinearLayout.LayoutParams lin;

        if (ConfirmationDialogInfo != null || (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing())) {
            if (ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.dismiss();
            }
            ConfirmationDialogInfo = null;
        }

        ConfirmationDialogInfo = new Dialog(Dashboard.this,
                R.style.Theme_Transparent);
        ConfirmationDialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialogInfo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ConfirmationDialogInfo.setContentView(R.layout.dialog);

        TextView title = ConfirmationDialogInfo
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(typeface);
        title.setText(DialogTitle);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);

        TextView message = ConfirmationDialogInfo
                .findViewById(R.id.tvMessage);
        message.setTypeface(typeface);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

        Button btnOne = ConfirmationDialogInfo.findViewById(R.id.button1);
        btnOne.setTypeface(typeface);
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

        btnOne.setOnClickListener(new OnClickListener() {
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

    @DebugLog
    private void showInfoDialogBuy(String DialogTitle, String Message) {
        // TODO Auto-generated method stub

        LinearLayout.LayoutParams lin;

        if (ConfirmationDialogInfo != null || (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing())) {
            if (ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.dismiss();
            }
            ConfirmationDialogInfo = null;
        }

        ConfirmationDialogInfo = new Dialog(Dashboard.this,
                R.style.Theme_Transparent);
        ConfirmationDialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialogInfo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ConfirmationDialogInfo.setContentView(R.layout.dialog);

        TextView title = ConfirmationDialogInfo
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(typeface);
        title.setText(DialogTitle);

        TextView chip = ConfirmationDialogInfo.findViewById(R.id.txt_Chip_11);
        chip.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(26));
        chip.setTypeface(typeface);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.iv_chips_11).getLayoutParams();
        lin.height = getwidth(50);
        lin.width = getwidth(50);

        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.mainpopup).getLayoutParams();
        frm.height = getheight(450);
        frm.width = getwidth(900);

        LinearLayout ll_chip_outof = ConfirmationDialogInfo.findViewById(R.id.ll_chip_outof);
        ll_chip_outof.setVisibility(View.VISIBLE);

        TextView message = ConfirmationDialogInfo
                .findViewById(R.id.tvMessage);
        message.setTypeface(typeface);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

        Button btnOne = ConfirmationDialogInfo.findViewById(R.id.button1);
        btnOne.setTypeface(typeface);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("Buy Chips");

        Button btnTwo = ConfirmationDialogInfo.findViewById(R.id.button2);
        btnTwo.setTypeface(typeface);
        btnTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnTwo.setText("Free Chips");

        Button btnThree = ConfirmationDialogInfo.findViewById(R.id.button3);
        btnThree.setTypeface(typeface);
        btnThree.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnThree.setText("Cancel");
        //btnThree.setVisibility(View.VISIBLE);

        Button btnClose = ConfirmationDialogInfo.findViewById(R.id.closebtn);
        btnClose.setVisibility(View.VISIBLE);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.button1).getLayoutParams();
        lin.height = getheight(65);
        lin.width = getwidth(180);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.ll_chip_outof).getLayoutParams();
        lin.topMargin = getheight(30);
        lin.leftMargin = getwidth(90);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.button2).getLayoutParams();
        lin.height = getheight(65);
        lin.width = getwidth(180);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.closebtn).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(60);
        lin.topMargin = getheight(-100);
        lin.rightMargin = getwidth(5);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.button3).getLayoutParams();
        lin.height = getheight(65);
        lin.width = getwidth(180);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.tvMessage).getLayoutParams();
        lin.topMargin = getheight(50);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.llButtons).getLayoutParams();
        lin.topMargin = getheight(5);

        message.setText(Message);

        btnOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                Music_Manager.buttonclick();
                Intent inprofile = new Intent(getApplication(), Coin_per.class);
                startActivity(inprofile);
                overridePendingTransition(android.R.anim.slide_in_left, 0);
                ConfirmationDialogInfo.dismiss();
                // finish();
            }
        });

        btnTwo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                Music_Manager.buttonclick();
                ConfirmationDialogInfo.dismiss();
                Music_Manager.buttonclick();
                if (PreferenceManager.isInternetConnected()) {
                    Message msg = new Message();
                    msg.what = ResponseCodes.VIDEOADS;
                    if (Dashboard.handler != null) {
                        Dashboard.handler.sendMessage(msg);
                    }
                } else {
                    showInfoDialog("No Internet", "Please check your Internet Connection.");
                }

            }
        });

        btnClose.setOnClickListener(new OnClickListener() {
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

    @DebugLog
    private void refreshData() {
        //=====================Quest Notifiacation  Must be at last in On_Resume ===================
        setQuestNotifiaction();
        //==========================================================================================

        level_txt.setText(PreferenceManager.getUserName());
        tvEntryPointPlayNow.setText("Players : " + PreferenceManager.getNumberOfPlayers() + "");

        try {
            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> REFRECE DATA >>> onresume >>> get_picPath" + PreferenceManager.get_picPath());
            if (PreferenceManager.get_picPath().length() > 0) {
                Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                if (d != null) {
//                Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> REFRECE DATA >>> onresume >>> coverImage11" + d);
                    coverImage = ((BitmapDrawable) d).getBitmap();
                } else {
                    Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> REFRECE DATA >>> onresume >>> coverImage22" + d);
                    coverImage = BitmapFactory.decodeResource(Dashboard.this.getResources(),
                            R.drawable.me);
                }
            } else {

                coverImage = BitmapFactory.decodeResource(Dashboard.this.getResources(),
                        R.drawable.me);
            }

            if (PreferenceManager.get_picPath().length() > 0) {
                Drawable d = Drawable.createFromPath(PreferenceManager.get_picPath());
                if (d != null) {
                    pic.setImageDrawable(d);
                } else {
                    pic.setImageResource(PreferenceManager.getprofilepic());
                }
            } else {
                pic.setImageResource(PreferenceManager.getprofilepic());
            }
        } catch (Exception e) {
            e.printStackTrace();
            coverImage = BitmapFactory.decodeResource(Dashboard.this.getResources(),
                    R.drawable.me);
            pic.setImageResource(PreferenceManager.getprofilepic());
        }
        myData.Chips = PreferenceManager.getChips();
        Chips_text.setText(myData.getNumberFormatedValue(myData.Chips));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> LeaderBoard Score Update Here >>> onresume");
                        Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.LEADERBOARD_ID), PreferenceManager.getChips());
                    }
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 100);
        tvEntryChipPlayNow_2Players.setText("Point : " + PreferenceManager.getPlayerPoints(myData.gameType, 2));
        tvEntryChipPlayNow_3Players.setText("Point : " + PreferenceManager.getPlayerPoints(myData.gameType, 3));
    }

    private void ShowSingInPopup() {

        {
            // TODO Auto-generated method stub

            LinearLayout.LayoutParams lin;

            if (ConfirmationDialogInfo2 != null || (ConfirmationDialogInfo2 != null && ConfirmationDialogInfo2.isShowing())) {
                if (ConfirmationDialogInfo2.isShowing()) {
                    ConfirmationDialogInfo2.dismiss();
                }
                ConfirmationDialogInfo2 = null;
            }

            ConfirmationDialogInfo2 = new Dialog(Dashboard.this,
                    R.style.Theme_Transparent);
            ConfirmationDialogInfo2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            ConfirmationDialogInfo2.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            ConfirmationDialogInfo2.setContentView(R.layout.dialog);

            TextView title = ConfirmationDialogInfo2
                    .findViewById(R.id.tvTitle);
            TextView tv_do_it = ConfirmationDialogInfo2.findViewById(R.id.tv_do_it);
            tv_do_it.setVisibility(View.VISIBLE);
            title.setPadding(getwidth(20), getheight(20), getwidth(20),
                    getheight(20));
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
            tv_do_it.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(34));
            title.setTypeface(typeface);
            tv_do_it.setTypeface(typeface);
            title.setText("Google Play Sign In");

            lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo2.findViewById(
                    R.id.line).getLayoutParams();
            lin.height = getheight(4);
            lin.width = getwidth(600);

            TextView message = ConfirmationDialogInfo2
                    .findViewById(R.id.tvMessage);
            message.setTypeface(typeface);
            message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

            Button btnOne = ConfirmationDialogInfo2.findViewById(R.id.button1);
            btnOne.setTypeface(typeface);
            btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
            btnOne.setText("Cancel");
            btnOne.setBackgroundResource(R.drawable.rate_chips_btn);

            Button btnTwo = ConfirmationDialogInfo2.findViewById(R.id.button2);
            btnTwo.setTypeface(typeface);
            btnTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
            btnTwo.setText("Sign In");
            btnTwo.setBackgroundResource(R.drawable.get_free_card);

            lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo2.findViewById(
                    R.id.button1).getLayoutParams();
            lin.height = getheight(60);
            lin.width = getwidth(120);

            lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo2.findViewById(
                    R.id.button2).getLayoutParams();
            lin.height = getheight(60);
            lin.width = getwidth(120);

            lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo2.findViewById(
                    R.id.tvMessage).getLayoutParams();
            lin.topMargin = getheight(30);

            lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo2.findViewById(
                    R.id.llButtons).getLayoutParams();
            lin.topMargin = getheight(30);

            message.setText("Sign in using your Google Play account to safeguard\nyour progress and play with multiple devices.");

            btnTwo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method
                    Music_Manager.buttonclick();

                    if (PreferenceManager.isInternetConnected()) {
                        PreferenceManager.set_Google_Sing_in(true);
                        ConfirmationDialogInfo2.dismiss();
                        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> CONNECTION START 7777 >>> ");
                        mGoogleApiClient.connect();
                    } else {
                        showInfoDialog("Alert!", "Please Check Your Internet Connection!");
                    }

                }
            });

            btnOne.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method
                    Music_Manager.buttonclick();
                    ConfirmationDialogInfo2.dismiss();
                    PreferenceManager.set_Google_Sing_in(false);
                    PreferenceManager.set_FirstTime(false);
                    Intent inprofile = new Intent(getApplication(), HowToPlay.class);
                    startActivity(inprofile);
                    overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
                }
            });
            if (!isFinishing())
                ConfirmationDialogInfo2.show();

        }


    }

    @DebugLog
    private void showProgressDialog(final String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog2(this, Dashboard.this);
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!isFinishing()) {
                    mProgressDialog.show(msg);
                }
            }
        }, 100);

    }

    /**
     * Hide the progress dialog, if it was showing.
     */
    @DebugLog
    private void dismissProgressDialog() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            }
        }, 1000);

    }

    @DebugLog
    void loadFromSnapshot(final boolean isConfirmdailog) {
        if (PreferenceManager.get_FirstTime()) {
            // Display a progress dialog
            // ...
            if (mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
                return;
            }
            isStartGame = false;
            showProgressDialog("Load User Data...");
            @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Integer> task = new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... params) {
                    // Open the saved game using its name.

                    if (!mGoogleApiClient.isConnected()) {
                        savedGamesUpdate();
                        dismissProgressDialog();
                        return 0;
                    }
                    Snapshots.OpenSnapshotResult result = Games.Snapshots.open(mGoogleApiClient,
                            makeSnapshotName(APP_STATE_KEY), true).await();
                    Log.w(TAG, "KKKKKKKK>>> loadFromSnapshot >>> RESULT  >>> " + result.getStatus().getStatusMessage() + " : " + result.getStatus().getStatusCode());

                    // Check the result of the open operation
                    Snapshot snapshot = null;
                    if (result.getStatus().getStatusCode() == GamesStatusCodes.STATUS_SNAPSHOT_NOT_FOUND) {

                        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> loadFromSnapshot >>> SNAPSHOT_NOT_FOUND");
                        savedGamesUpdate();
                        dismissProgressDialog();

                    } else if (result.getStatus().isSuccess()) {
                        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> loadFromSnapshot >>> saved_games_load_success");
                        snapshot = result.getSnapshot();

                    } else {
                        Log.e(TAG, "Error while loading: " + result.getStatus().getStatusCode());
                        Log.e(TAG, "Error while loading: " + result.getStatus().getStatusMessage());
                        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> loadFromSnapshot >>> STATUS_SNAPSHOT_CONFLICT");
                        //snapshot = processSnapshotOpenResult(result, 3);
                        //snapshot = result.getSnapshot();

                    /*String uuu = result.getConflictingSnapshot().getMetadata().getCoverImageUrl();
                    Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> loadFromSnapshot >>> conflic url >>> "+uuu);*/


                        snapshot = processSnapshotOpenResult(result, 0);

//                    try {
//                        byte[] b = snapshot.getSnapshotContents().readFully();
//                        final String data = new String(b);
//                        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> loadFromSnapshot >>> Data >>> " + data);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    }

                    if (snapshot != null) {
                        Bitmap CoverImage;
                        String uri;
                        // Read the byte content of the saved game.
                        try {
                            byte[] b = snapshot.getSnapshotContents().readFully();
                            final String data = new String(b);
                            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> loadFromSnapshot >>> Data >>> " + data);
                            SnapshotMetadata matadata = snapshot.getMetadata();
                            // matadata.getSnapshotId();

                            uri = matadata.getCoverImageUrl();
                            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> loadFromSnapshot >>>  URI : " + uri);
                            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> loadFromSnapshot >>>  Name : " + matadata.getUniqueName());

                            CoverImage = getBitmapFromURL(uri);

                            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> loadFromSnapshot >>> CoverImage >>> " + CoverImage);

                            final Bitmap finalCoverImage = CoverImage;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if (data.length() > 0) {
                                        if (isConfirmdailog) {
                                            updatedataFromGooglrPlay(data, finalCoverImage);
                                            dismissProgressDialog();
                                        } else {
                                            PreferenceManager.set_FirstTime(false);
                                            LoadDataFromGooglePlayGames(data, null, finalCoverImage);
                                        }
                                    } else {
                                        PreferenceManager.set_FirstTime(false);
                                        StartTutorialScreen();
                                        savedGamesUpdate();
                                        dismissProgressDialog();
                                    }
                                }
                            }, 100);

                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(TAG, "Error while reading Snapshot.", e);

                        } catch (Exception e) {
                            e.printStackTrace();
                            dismissProgressDialog();
                        }
                    } else {

                        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> loadFromSnapshot >>>  SnapShot Null : " + snapshot);
                        savedGamesUpdate();
                        dismissProgressDialog();
                    }


                    return result.getStatus().getStatusCode();

                }

                @Override
                protected void onPostExecute(Integer status) {
                    // Dismiss progress dialog and reflect the changes in the UI.
                    // ...
                }
            };

            task.execute();
        } else {
            savedGamesUpdate();
        }
    }

    private void StartTutorialScreen() {


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent inprofile = new Intent(getApplication(), HowToPlay.class);
                startActivity(inprofile);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
            }
        }, 1500);


    }

    @DebugLog
    private void LoadDataFromGooglePlayGames(final String data, final String uri, final Bitmap coverImage) {

        try {

            JSONObject userobj = new JSONObject(data);
            Logger.print("KKKKKKKKK>>> Google Play Service >>> LoadDataFromGooglePlayGames >>>  userobj: " + userobj);

            if (userobj.has(Parameters.PLAYED)) {

                myMap = new HashMap<>();
                myMap = SQLiteManager.getUserData(PreferenceManager.get_id());
                int localdatbaseplaygame;
                if (myMap.get(myData.PLAYED) == null) {
                    localdatbaseplaygame = 0;
                } else {
                    localdatbaseplaygame = Integer.valueOf(myMap.get(myData.PLAYED));
                }
                if (userobj.getLong(Parameters.PLAYED) > localdatbaseplaygame) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!isFinishing()) {
                                PreferenceManager.set_FirstTime(false);
                                updatedataFromGooglrPlay(data, coverImage);
                                dismissProgressDialog();
                            }
                        }
                    });

                } else {
                    savedGamesUpdate();
                    dismissProgressDialog();
                }
            } else {

                savedGamesUpdate();
                dismissProgressDialog();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @DebugLog
    private void updatedataFromGooglrPlay(String data, Bitmap coverImage) {

        JSONObject userobj;
        try {
            userobj = new JSONObject(data);
            Logger.print("KKKKKKKKK>>> Google Play Service >>> updatedataFromGooglrPlay >>>  userobj: " + userobj);
            if (userobj.has(Parameters.User_Name)) {
                PreferenceManager.setUserName(userobj.getString(Parameters.User_Name));
            }

            if (userobj.has(Parameters.GIN)) {
                PreferenceManager.SetQuestGinCount(userobj.getInt(Parameters.GIN));
                if (PreferenceManager.GetQuestGinTodayCount() == 0) {
                    PreferenceManager.SetQuestGinTodayCount(PreferenceManager.GetQuestGinCount());
                    PreferenceManager.SetQuestGinClaimCountToday(0);
                }

            }

            if (userobj.has(Parameters.GINCount)) {
                PreferenceManager.SetQuestGinClaimCountLife(userobj.getInt(Parameters.GINCount));
            }

            if (userobj.has(Parameters.BIG_GIN)) {
                PreferenceManager.SetQuestBigGinCount(userobj.getInt(Parameters.BIG_GIN));
                if (PreferenceManager.GetQuestBigGinTodayCount() == 0) {
                    PreferenceManager.SetQuestBigGinTodayCount(PreferenceManager.GetQuestBigGinCount());
                    PreferenceManager.SetQuestBigGinClaimCountToday(0);
                }

            }

            if (userobj.has(Parameters.BIG_GINCount)) {
                PreferenceManager.SetQuestBigGinClaimCountLife(userobj.getInt(Parameters.BIG_GINCount));
            }

            if (userobj.has(Parameters.KNOCK)) {
                PreferenceManager.SetQuestKnockCount(userobj.getInt(Parameters.KNOCK));
                if (PreferenceManager.GetQuestKnockTodayCount() == 0) {
                    PreferenceManager.SetQuestKnockTodayCount(PreferenceManager.GetQuestKnockCount());
                    PreferenceManager.SetQuestKnockClaimCountToday(0);
                }

            }

            if (userobj.has(Parameters.KNOCKCount)) {
                PreferenceManager.SetQuestKnockClaimCountLife(userobj.getInt(Parameters.KNOCKCount));
            }

            if (userobj.has(Parameters.OFFERWALL)) {
                PreferenceManager.SetQuestOfferWallCount(userobj.getInt(Parameters.OFFERWALL));
                if (PreferenceManager.GetQuestOfferwallTodayCount() == 0) {
                    PreferenceManager.SetQuestOfferWallTodayCount(PreferenceManager.GetQuestOfferwallCount());
                    PreferenceManager.SetQuestOfferClaimCountToday(0);
                }

            }

            if (userobj.has(Parameters.OFFERWALLCount)) {
                PreferenceManager.SetQuestOfferClaimCountLife(userobj.getInt(Parameters.OFFERWALLCount));
            }

            if (userobj.has(Parameters.SHARE)) {
                PreferenceManager.SetQuestShareCount(userobj.getInt(Parameters.SHARE));
                if (PreferenceManager.GetQuestShareTodayCount() == 0) {
                    PreferenceManager.SetQuestShareTodayCount(PreferenceManager.GetQuestShareCount());
                    PreferenceManager.SetQuestShareClaimCountToday(0);
                }

            }

            if (userobj.has(Parameters.SHARECount)) {
                PreferenceManager.SetQuestShareClaimCountLife(userobj.getInt(Parameters.SHARECount));
            }

            if (userobj.has(Parameters.STRAIGHT)) {
                PreferenceManager.SetQuestStraightCount(userobj.getInt(Parameters.STRAIGHT));
                if (PreferenceManager.GetQuestStraightTodayCount() == 0) {
                    PreferenceManager.SetQuestStraightTodayCount(PreferenceManager.GetQuestStraightCount());
                    PreferenceManager.SetQuestStraightClaimCountToday(0);
                }

            }

            if (userobj.has(Parameters.STRAIGHTCount)) {
                PreferenceManager.SetQuestStraightClaimCountLife(userobj.getInt(Parameters.STRAIGHTCount));
            }
            if (userobj.has(Parameters.OKLAHOMA)) {
                PreferenceManager.SetQuestOklahomaCount(userobj.getInt(Parameters.OKLAHOMA));
                if (PreferenceManager.GetQuestOklahomaTodayCount() == 0) {
                    PreferenceManager.SetQuestOklahomaTodayCount(PreferenceManager.GetQuestOklahomaCount());
                    PreferenceManager.SetQuestOklahomaClaimCountToday(0);
                }

            }

            if (userobj.has(Parameters.OKLAHOMACount)) {
                PreferenceManager.SetQuestShareClaimCountLife(userobj.getInt(Parameters.OKLAHOMACount));
            }

            if (userobj.has(Parameters.RemoveadsClaimCount)) {
                PreferenceManager.SetQuestRemoveAdsClaimCountLife(userobj.getInt(Parameters.RemoveadsClaimCount));
            }


            if (userobj.has(Parameters.VIDEO)) {
                PreferenceManager.SetQuestWatchVideoCount(userobj.getInt(Parameters.VIDEO));
                if (PreferenceManager.GetQuestWatchVideoTodayCount() == 0) {
                    PreferenceManager.SetQuestWatchVideoTodayCount(PreferenceManager.GetQuestWatchVideoCount());
                    PreferenceManager.SetQuestVideoClaimCountToday(0);
                }

            }

            if (userobj.has(Parameters.VIDEOCount)) {
                PreferenceManager.SetQuestVideoClaimCountLife(userobj.getInt(Parameters.VIDEOCount));
            }

            if (userobj.has(Parameters.Chips)) {
                PreferenceManager.setChips(userobj.getLong(Parameters.Chips));
            }

            if (userobj.has(Parameters.BIG_WON)) {
                SQLiteManager.updateBiggestWin(PreferenceManager.get_id(), userobj.getLong(Parameters.BIG_WON));
            }

            if (userobj.has(Parameters.WIN)) {
                SQLiteManager.updateGameWin(PreferenceManager.get_id(), userobj.getLong(Parameters.WIN));
            }

            if (userobj.has(Parameters.PLAYED)) {
                SQLiteManager.updateGamePlayed(PreferenceManager.get_id(), userobj.getLong(Parameters.PLAYED));
            }

            if (userobj.has(Parameters.GIN_PLAYED)) {
                PreferenceManager.SetGinPlayed(userobj.getInt(Parameters.GIN_PLAYED));
            } else {
                if (userobj.has(Parameters.PLAYED)) {
                    PreferenceManager.SetGinPlayed(userobj.getInt(Parameters.PLAYED));
                }
            }

            if (userobj.has(Parameters.GIN_WON)) {
                PreferenceManager.SetGinWon(userobj.getInt(Parameters.GIN_WON));
            } else {
                if (userobj.has(Parameters.WIN)) {
                    PreferenceManager.SetGinWon(userobj.getInt(Parameters.WIN));
                }
            }

            if (userobj.has(Parameters.STRAIGHT_PLAYED)) {
                PreferenceManager.SetStraightPlayed(userobj.getInt(Parameters.STRAIGHT_PLAYED));
            } else {
                PreferenceManager.SetStraightPlayed(0);
            }

            if (userobj.has(Parameters.STRAIGHT_WON)) {
                PreferenceManager.SetStraightWon(userobj.getInt(Parameters.STRAIGHT_WON));
            } else {
                PreferenceManager.SetStraightWon(0);
            }

            if (userobj.has(Parameters.OKLAHOMA_PLAYED)) {
                PreferenceManager.SetOklahomaPlayed(userobj.getInt(Parameters.OKLAHOMA_PLAYED));
            } else {
                PreferenceManager.SetOklahomaPlayed(0);
            }


            if (userobj.has(Parameters.OKLAHOMA_WON)) {
                PreferenceManager.SetOklahomaWon(userobj.getInt(Parameters.OKLAHOMA_WON));
            } else {
                PreferenceManager.SetOklahomaWon(0);
            }

            //Bitmap coverImage =
            //showPlayerImage(R.id.pic,uri);

            //if(coverImage != null) {
            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> LoadDataFromGooglePlayGames >>>  coverImage null: "
                    + saveToSdCard(coverImage));
            //}
            refreshData();
            savedGamesUpdate();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @DebugLog
    public Bitmap getBitmapFromURL(String uri) {


        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    @DebugLog
    public void onConnected(@Nullable Bundle bundle) {
        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onConnected");
        updateScoreOnLeaderBoard();
        if (isStartGame) {
            loadFromSnapshot(false);
        }
    }

    @DebugLog
    private void updateScoreOnLeaderBoard() {

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> LeaderBoard Score Update Here >>> onConnected");
            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.LEADERBOARD_ID), PreferenceManager.getChips());
        }

    }

    @Override
    @DebugLog
    public void onConnectionSuspended(int i) {

        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onConnectionSuspended");
        // Attempt to reconnect
        if (PreferenceManager.isInternetConnected()/*&& PreferenceManager.get_Google_Sing_in() && !PreferenceManager.get_FirstTime()*/) {
            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> CONNECTION START 9999 >>> ");
            mGoogleApiClient.connect();
        }
    }

    @Override
    @DebugLog
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onConnectionFailed >>> connectionResult >>> " + connectionResult.getErrorMessage() + " : " + connectionResult.getErrorCode());
        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onConnectionFailed");
        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onConnectionFailed >>> mGoogleApiClient.isConnected() >>> " + mGoogleApiClient.isConnected());

        if (mGoogleApiClient.isConnected()) {
            if (isStartGame) {
                //savedGamesLoad(makeSnapshotName(APP_STATE_KEY),false);
//                if(PreferenceManager.get_FirstTime()) {
                loadFromSnapshot(false);
//                }

            }
        }

        if (mResolvingConnectionFailure) {
            // already resolving
            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onConnectionFailed >>> 1111");
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInflow) {
            mAutoStartSignInflow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onConnectionFailed >>> 2222");

            if (PreferenceManager.isInternetConnected()/*&& PreferenceManager.get_Google_Sing_in() && !PreferenceManager.get_FirstTime()*/) {
                Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onConnectionFailed >>> retry connect11");
                Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> CONNECTION START 101010 >>> ");
                mGoogleApiClient.connect();
            }

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."

        }

        //  if(!mGoogleApiClient.isConnected()){
        if (PreferenceManager.isInternetConnected()/*&& PreferenceManager.get_Google_Sing_in() && !PreferenceManager.get_FirstTime()*/) {
            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onConnectionFailed >>> retry connect22");
            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> CONNECTION START 11_11_11 >>> ");
            mGoogleApiClient.connect();

            if (PreferenceManager.get_FirstTime()) {
                checkIsConnectAndLoaddata();
            }
        }
        // }
        // Put code here to display the sign-in button

    }

    @DebugLog
    private void checkIsConnectAndLoaddata() {
        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> checkIsConnectAndLoaddata >>>  Check Load ");
        showProgressDialog("Please Wait...");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
                if (isStartGame) {
                    Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> checkIsConnectAndLoaddata >>> Check Load ");
                    if (mGoogleApiClient.isConnected()) {

                        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> checkIsConnectAndLoaddata >>> Post ");
                        updateScoreOnLeaderBoard();
                        //savedGamesUpdate();
                        if (isStartGame) {
                            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> checkIsConnectAndLoaddata >>> Load Data  ");
                            //savedGamesLoad(makeSnapshotName(APP_STATE_KEY), false);
//                            if(PreferenceManager.get_FirstTime()) {
                            loadFromSnapshot(false);
//                            }
                        }

                    }
                }

            }
        }, 5000);

    }

    @Override
    @DebugLog
    protected void onStart() {
        super.onStart();
        if (GameisOn()) {
            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onStart >>> ");

            if (!isStartGame) {
                if (!mGoogleApiClient.isConnected()) {
                    Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onStart try to Connect >>> ");
                    if (PreferenceManager.isInternetConnected()) {

                        if (PreferenceManager.get_Google_Sing_in() && !PreferenceManager.get_FirstTime())
                            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> CONNECTION START 1111 >>> ");
                        mGoogleApiClient.connect();
                    }
                }
            }
        }
    }

    @Override
    @DebugLog
    protected void onStop() {
        super.onStop();
        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onStop >>> ");
    }

    @DebugLog
    private void insertDailyBonusData() {

        try {

            Logger.print("MMMMMMMMMMMMMM>>>> Inserted Date:  " + myData.currentDate);
            Logger.print("MMMMMMMMMMMMMM>>>> Inserted Month: " + myData.currentMonth);
            Logger.print("MMMMMMMMMMMMMM>>>> Inserted Year:  " + myData.currentYear);
            Logger.print("MMMMMMMMMMMMMM>>>> Inserted Day:  " + myData.currentDay);
            HashMap<String, String> map = new HashMap<>();


            map.put(myData.DATE, "" + myData.currentDate);
            map.put(myData.MONTH, "" + myData.currentMonth);
            map.put(myData.YEAR, "" + myData.currentYear);
            map.put(myData.DAY, "" + myData.currentDay);


            sqLiteDailyBonus.insertData(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DebugLog
    private String loadJSONFromAsset() {

        String json;
        try {

            InputStream is = this.getAssets().open("leader.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {

            ex.printStackTrace();
            return null;
        }

        return json;
    }

    @Override
    @DebugLog
    protected void onDestroy() {
        super.onDestroy();

        try {
            if (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.cancel();
                ConfirmationDialogInfo = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (ConfirmationDialogInfo2 != null && ConfirmationDialogInfo2.isShowing()) {
                ConfirmationDialogInfo2.cancel();
                ConfirmationDialogInfo2 = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (rateNowDialog != null && rateNowDialog.isShowing()) {
                rateNowDialog.cancel();
                rateNowDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (playerChooserDialog != null && playerChooserDialog.isShowing()) {
                playerChooserDialog.cancel();
                playerChooserDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (ExitConfirmationDialog != null && ExitConfirmationDialog.isShowing()) {
                ExitConfirmationDialog.cancel();
                ExitConfirmationDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (OutOfChipsDialog != null && OutOfChipsDialog.isShowing()) {
                OutOfChipsDialog.cancel();
                OutOfChipsDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (QuestDialog != null && QuestDialog.isShowing()) {
                QuestDialog.cancel();
                QuestDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        stopTimer();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @DebugLog
    private void insertAllUserData() {

        try {

            JSONArray data = new JSONArray(loadJSONFromAsset());

            Logger.print("<<<JSON DATA FOR ARRAY:" + data.toString());

            for (int i = 0; i < data.length(); i++) {

                HashMap<String, String> map = new HashMap<>();
                map.put(myData.ID, data.getJSONObject(i).getString("_id"));
                map.put(myData.NAME, data.getJSONObject(i).getString("un"));
                map.put(myData.IMAGE, data.getJSONObject(i).getString("pp"));
                map.put(myData.CHIPS, data.getJSONObject(i).getString("chip"));
                map.put(myData.PLAYED, data.getJSONObject(i).getString("HP"));
                map.put(myData.WIN, data.getJSONObject(i).getString("HW"));
                map.put(myData.BIG_WON, data.getJSONObject(i).getString("BHW"));
                SQLiteManager.insertData(map);

            }

            HashMap<String, String> map = new HashMap<>();

            String imagepath = String.valueOf(PreferenceManager.getprofilepic());

            map.put(myData.ID, PreferenceManager.get_id());
            map.put(myData.NAME, PreferenceManager.getUserName());
            map.put(myData.IMAGE, imagepath);
            map.put(myData.CHIPS, "" + PreferenceManager.getChips());
            map.put(myData.PLAYED, "0");
            map.put(myData.WIN, "0");
            map.put(myData.BIG_WON, "0");

            SQLiteManager.insertData(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DebugLog
    private void setScreen() {
        // TODO Auto-generated method stub
        FrameLayout.LayoutParams frm;
        LinearLayout.LayoutParams lin;
        RelativeLayout.LayoutParams rel;
        int w, h;

        rel = (RelativeLayout.LayoutParams) findViewById(R.id.top_bar)
                .getLayoutParams();
        rel.height = getheight(100);
        rel.topMargin = getheight(6);

        w = getwidth(220);
        rel = (RelativeLayout.LayoutParams) findViewById(R.id.profile_container)
                .getLayoutParams();
        rel.width = w;
        rel.height = w * 90 / 220;
        rel.leftMargin = getwidth(10);
        rel.topMargin = getheight(10);

        w = getwidth(70);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.pic)
                .getLayoutParams();
        lin.width = w;
        lin.height = w;
        w = getwidth(10);
        lin.leftMargin = w;
        lin.topMargin = w * 6 / 10;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.level_txt)
                .getLayoutParams();
        lin.width = getwidth(120);
        lin.leftMargin = getwidth(10);

        w = getwidth(239);
        rel = (RelativeLayout.LayoutParams) findViewById(R.id.chips_text)
                .getLayoutParams();
        rel.width = w;
        rel.height = w * 88 / 239;
        rel.leftMargin = getwidth(20);
        rel.topMargin = getheight(10);

        w = getwidth(50);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.notiquest)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;

        w = getwidth(50);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.notiachive)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;

        w = getwidth(90);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.invite_btn)
                .getLayoutParams();
        lin.width = w;
        lin.height = w;
        lin.topMargin = getheight(10);
        lin.rightMargin = getwidth(10);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.leaderboard_btn)
                .getLayoutParams();
        lin.width = w;
        lin.height = w;
        lin.topMargin = getheight(10);
        lin.rightMargin = getwidth(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.btn_achivement_icn)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = getheight(10);
        frm.rightMargin = getwidth(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.btn_quest_icn)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = getheight(10);
        frm.rightMargin = getwidth(10);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.imgQuesttest)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = getheight(110);
        frm.leftMargin = getwidth(20);


        lin = (LinearLayout.LayoutParams) findViewById(R.id.setting_btn)
                .getLayoutParams();
        lin.width = w;
        lin.height = w;
        lin.topMargin = getheight(10);
        lin.rightMargin = getwidth(6);

        w = getwidth(267);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.play_now)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.play_now3)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.fl_offer)
                .getLayoutParams();
        lin.width = w;
        lin.height = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.play_middle)
                .getLayoutParams();
        frm.width = getwidth(267);
        frm.height = getwidth(267);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.play_middle3)
                .getLayoutParams();
        frm.width = getwidth(267);
        frm.height = getwidth(267);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_offer2)
                .getLayoutParams();
        frm.width = getwidth(267);
        frm.height = getwidth(267);


        frm = (FrameLayout.LayoutParams) findViewById(R.id.play_now_text)
                .getLayoutParams();
        frm.width = getwidth(180);
        frm.height = getwidth(240);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.play_now_text3)
                .getLayoutParams();
        frm.width = getwidth(180);
        frm.height = getwidth(240);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_offer3)
                .getLayoutParams();
        frm.width = getwidth(180);
        frm.height = getwidth(240);


        w = getwidth(267);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.html5_bk).getLayoutParams();
        frm.width = w;
        frm.height = w;


        frm = (FrameLayout.LayoutParams) findViewById(R.id.html5_middle).getLayoutParams();
        frm.width = w;
        frm.height = w;

        w = getwidth(267);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.play_on_table)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;

        w = getwidth(267);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.play_on_table_middle)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.play_on_table_text)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;

        //=======================================================================================


        //======================================================================

        w = getwidth(267);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.ivMiniGame)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;

        w = getwidth(267);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.da_minigame)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;

        w = getwidth(267);
        frm = (FrameLayout.LayoutParams) findViewById(R.id.invite_get_btn)
                .getLayoutParams();
        frm.width = w;
        frm.height = w;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.frmPlayNowEntryPoint).getLayoutParams();
        lin.width = getwidth(270);
        lin.height = getheight(60);

//====================================================================================================================================
        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivEntryPlayNow).getLayoutParams();
        lin.leftMargin = getwidth(10);
        lin.width = getwidth(77);
        lin.height = getwidth(77);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.frmPlayOnTableEntryPoint).getLayoutParams();
        lin.width = getwidth(270);
        lin.leftMargin = getwidth(20);

        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivEntryPlayOnTAble).getLayoutParams();
        lin.leftMargin = getwidth(10);
        lin.width = getwidth(77);
        lin.height = getwidth(77);

        int wmb = myData.width * 160 / 1280;
        int hmb = wmb * 46 / 160;
        FrameLayout.LayoutParams fmb = new FrameLayout.LayoutParams(wmb, hmb);
        fmb.gravity = Gravity.BOTTOM | Gravity.CENTER;
        videoIcon.setLayoutParams(fmb);

        wmb = myData.width * 160 / 1280;
        hmb = wmb * 132 / 160;
        fmb = new FrameLayout.LayoutParams(wmb, hmb);
        videoIcontyre.setLayoutParams(fmb);

        wmb = myData.width * 45 / 1280;
        fmb = new FrameLayout.LayoutParams(wmb, wmb);
        fmb.rightMargin = wmb / 4;
        fmb.bottomMargin = wmb / 4;
        fmb.gravity = Gravity.CENTER;
        videoIconchip1.setLayoutParams(fmb);

        fmb = new FrameLayout.LayoutParams(wmb, wmb);
        fmb.leftMargin = wmb / 4;
        fmb.topMargin = wmb / 4;
        fmb.gravity = Gravity.CENTER;
        videoIconchip2.setLayoutParams(fmb);


        fmb = (FrameLayout.LayoutParams) findViewById(R.id.videoAdframe).getLayoutParams();
        fmb.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        fmb.rightMargin = getwidth(30);

//==================================================================================================


        // ==========================================================================================


        /////////////////////////////////// Change Point Design /////////////////////////////////

        w = getwidth(290);
        lin = (LinearLayout.LayoutParams) frmPlayNowEntryPoint_2players.getLayoutParams();
        lin.width = w;

        lin = (LinearLayout.LayoutParams) frmPlayNowEntryPoint_3players.getLayoutParams();
        lin.width = w;

        w = getwidth(77);
        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivEntryPlayNow_2Players).getLayoutParams();
        lin.width = lin.height = w;
        lin.leftMargin = w * 10 / 77;

        lin = (LinearLayout.LayoutParams) findViewById(R.id.ivEntryPlayNow_3Players).getLayoutParams();
        lin.width = lin.height = w;
        lin.leftMargin = w * 10 / 77;

        frm = (FrameLayout.LayoutParams) findViewById(R.id.fl_spin).getLayoutParams();
        frm.width = getwidth(400);
        frm.height = getwidth(400);
        frm.bottomMargin = getwidth(-210);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.tv_spinglow).getLayoutParams();
        frm.width = getwidth(410);
        frm.height = getwidth(220);
        frm.bottomMargin = getwidth(200);
        frm.rightMargin = getwidth(6);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_spin).getLayoutParams();
        frm.width = getwidth(400);
        frm.height = getwidth(400);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_spin_ring).getLayoutParams();
        frm.width = getwidth(340);
        frm.height = getwidth(340);

        frm = (FrameLayout.LayoutParams) findViewById(R.id.iv_spintext).getLayoutParams();
        frm.width = getwidth(150);
        frm.height = getwidth(80);
        frm.topMargin = getwidth(70);

        frm = (FrameLayout.LayoutParams) rewarded_noti.getLayoutParams();
        frm.height = getwidth(45);
        frm.width = getwidth(45);
        frm.rightMargin = getwidth(15);
        frm.topMargin = getwidth(5);


    }

    @DebugLog
    private void startAnimation(ImageView imageView) {

        float a = 0f, b = 360f;
        if (imageView == videoIconchip2) {
            a = 360f;
            b = 0f;
        }
        imageViewObjectAnimator = ObjectAnimator.ofFloat(imageView,
                "rotation", a, b);
        imageViewObjectAnimator.setInterpolator(new LinearInterpolator());
        imageViewObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        imageViewObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        imageViewObjectAnimator.setDuration(7000); // miliseconds
        imageViewObjectAnimator.start();
    }

    @DebugLog
    private int getheight(int val) {
        // TODO Auto-generated method stub
        return Screen_Hight * val / 720;
    }

    @DebugLog
    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return Screen_Width * val / 1280;
    }

    @DebugLog
    private void FindViewByIds() {
        // TODO Auto-generated method stub
        SpecialOffers();
        //===================== HTML 5 offer =======================================
        html5_bk = findViewById(R.id.html5_bk);
        html5_bk.setOnClickListener(this);

        html5_lin = findViewById(R.id.html5_lin);
        html5_lin.setVisibility(View.GONE);
        html5_lin.setOnClickListener(this);

        offer_pbar = findViewById(R.id.offer_pbar);
        html5_middle = findViewById(R.id.html5_middle);
        html5_middle.setOnClickListener(this);

//==================================================================================================

        bottom_right_linearSetup();

        btn_achivement_icn = findViewById(R.id.btn_achivement_icn);
        btn_quest_icn = findViewById(R.id.btn_quest_icn);

        btn_achivement_icn.setOnClickListener(this);
        btn_quest_icn.setOnClickListener(this);


        notiachive = findViewById(R.id.notiachive);
        notiachive.setTypeface(typeface, Typeface.BOLD);
        notiachive.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        rewarded_noti = findViewById(R.id.rewarded_noti);
        rewarded_noti.setVisibility(View.GONE);
        rewarded_noti.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        rewarded_noti.setTypeface(typeface, Typeface.BOLD);

        notiquest = findViewById(R.id.notiquest);
        notiquest.setTypeface(typeface, Typeface.BOLD);
        notiquest.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));

        //==================================================================================
        handAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.handpointer);
        buttonPressed = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.buttonpressed);

        videoAdframe = findViewById(R.id.videoAdframe);
        videoIcon = findViewById(R.id.video_icon);
        videoText = findViewById(R.id.video_text);
        videoIconchip1 = findViewById(R.id.video_chip1);
        videoIconchip2 = findViewById(R.id.video_chip2);
        videoIcontyre = findViewById(R.id.video_iconcircle);


        videoIcon.setOnClickListener(this);
        videoIconchip1.setOnClickListener(this);
        videoIconchip2.setOnClickListener(this);
        videoIcontyre.setOnClickListener(this);
        videoAdframe.setOnClickListener(this);
        videoText.setOnClickListener(this);

        videoText.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getwidth1(28));
        videoText.setTypeface(myData.typeface, Typeface.BOLD);

        FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) videoText.getLayoutParams();
        FrameLayout.LayoutParams lp3 = (FrameLayout.LayoutParams) videoText.getLayoutParams();
        if (myData.height == 1536 && myData.width == 2048) {
            lp2.bottomMargin = myData.getheight1(6);
            lp3.bottomMargin = myData.getheight1(6);
        } else {
            lp2.bottomMargin = myData.getheight1(8);
            lp3.bottomMargin = myData.getheight1(8);
        }


        for (int i = 0; i < CoinAnimation.length; i++) {

            CoinAnimation[i] = new ImageView(getApplicationContext());
            CoinAnimation[i].setImageResource(R.drawable.small_chips);
            FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(getwidth(31), getwidth(31));
            CoinAnimation[i].setLayoutParams(fp);
            CoinAnimation[i].setVisibility(View.GONE);
        }
        ShareText = findViewById(R.id.share_text);

        ShareText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(18));

        ShareText.setTypeface(typeface);

        PLAY_ON_TABLE = findViewById(R.id.play_on_table);
        PLAY_ON_TABLE_middle = findViewById(R.id.play_on_table_middle);
        PLAY_ON_TABLE.setOnClickListener(this);
        PLAY_ON_TABLE_middle.setOnClickListener(this);
        main_layout = findViewById(R.id.main_layout);


        Version = findViewById(R.id.version);
        Version.setTypeface(typeface, Typeface.BOLD);
        Version.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        setVersion();


        pic = findViewById(R.id.pic);

        level_txt = findViewById(R.id.level_txt);
        level_txt.setTypeface(typeface, Typeface.BOLD);
        level_txt.setText("Level: ");
        level_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));

        Chips_text = findViewById(R.id.chips_text);
        Chips_text.setOnClickListener(this);
        Chips_text.setTypeface(typeface, Typeface.BOLD);
        Chips_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));

        playnowframe = findViewById(R.id.play_now_frame);
        playnowframe3 = findViewById(R.id.play_now_frame3);
        privateframe = findViewById(R.id.layout_da_minigame);
        playontableframe = findViewById(R.id.layout_Playon_Table);
        frmPlayNowEntryPoint_2players = findViewById(R.id.frmPlayNowEntryPoint_2players);
        frmPlayNowEntryPoint_2players.setOnClickListener(this);
        frmPlayNowEntryPoint_3players = findViewById(R.id.frmPlayNowEntryPoint_3Players);
        frmPlayNowEntryPoint_3players.setOnClickListener(this);


        play_now_text = findViewById(R.id.play_now_text);

        play_now_text3 = findViewById(R.id.play_now_text3);


        PLAY_NOW = findViewById(R.id.play_now);
        PLAY_NOW_Middle = findViewById(R.id.play_middle);
        PLAY_NOW.setOnClickListener(this);
        PLAY_NOW_Middle.setOnClickListener(this);
        play_now_text.setOnClickListener(this);

        iv_offer1 = findViewById(R.id.iv_offer1);
        PLAY_NOW3 = findViewById(R.id.play_now3);
        PLAY_NOW_Middle3 = findViewById(R.id.play_middle3);
        PLAY_NOW3.setOnClickListener(this);
        PLAY_NOW_Middle3.setOnClickListener(this);
        play_now_text3.setOnClickListener(this);


        play_on_table_text = findViewById(R.id.play_on_table_text);
        play_on_table_text.setOnClickListener(this);

        da_minigame = findViewById(R.id.da_minigame);

        setting_btn = findViewById(R.id.setting_btn);
        setting_btn.setOnClickListener(this);
        da_minigame.setOnClickListener(this);


        ImageView imgQuesttest = findViewById(R.id.imgQuesttest);
        imgQuesttest.setVisibility(View.GONE);
        imgQuesttest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                showQuestTestDialog();

            }
        });
        if (istestingmode) {
            imgQuesttest.setVisibility(View.VISIBLE);
        } else {
            imgQuesttest.setVisibility(View.GONE);
        }

        leaderboard_btn = findViewById(R.id.leaderboard_btn);
        leaderboard_btn.setOnClickListener(this);

        invite_btn = findViewById(R.id.invite_btn);
        invite_btn.setOnClickListener(this);


        INVITE_GET_BTN = findViewById(R.id.invite_get_btn);
        INVITE_GET_BTN_middle = findViewById(R.id.ivMiniGame);
        INVITE_GET_BTN.setOnClickListener(this);
        INVITE_GET_BTN_middle.setOnClickListener(this);

        tvEntryChipPlayNow_2Players = findViewById(R.id.tvEntryChipPlayNow_2Players);
        tvEntryChipPlayNow_2Players.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvEntryChipPlayNow_2Players.setTypeface(typeface);

        tvEntryChipPlayNow_3Players = findViewById(R.id.tvEntryChipPlayNow_3Players);
        tvEntryChipPlayNow_3Players.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvEntryChipPlayNow_3Players.setTypeface(typeface);

        frmPlayNowEntryPoint = findViewById(R.id.frmPlayNowEntryPoint);

        frmPlayNowEntryPoint.setOnClickListener(this);
        profile_container = findViewById(R.id.profile_container);
        profile_container.setOnClickListener(this);

        Drawable img = getResources().getDrawable(R.drawable.small_chips);
        img.setBounds(0, 0, getwidth(31), getwidth(31));

        tvEntryText = findViewById(R.id.tvEntryText);
        tvEntryText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvEntryText.setTypeface(typeface);

        tvEntryPoint = findViewById(R.id.tvEntryPoint);
        tvEntryPoint.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvEntryPoint.setTypeface(typeface);
        tvEntryPoint.setCompoundDrawables(img, null, null, null);

        tvEntryPointPlayNow = findViewById(R.id.tvEntryPointPlayNow);
        tvEntryPointPlayNow.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvEntryPointPlayNow.setTypeface(typeface);

        tvEntryChipPlayNow = findViewById(R.id.tvEntryChipPlayNow);
        tvEntryChipPlayNow.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(28));
        tvEntryChipPlayNow.setTypeface(typeface);
        tvEntryChipPlayNow.setCompoundDrawables(img, null, null, null);


        //Spinner>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        iv_spin = findViewById(R.id.iv_spin);
        iv_spin.setOnClickListener(this);

        iv_spintext = findViewById(R.id.iv_spintext);
        iv_spintext.setOnClickListener(this);

        iv_spin_ring = findViewById(R.id.iv_spin_ring);
        iv_spin_ring.setOnClickListener(this);

        iv_spin_ring.setBackgroundResource(R.drawable.spin_ring_animation);
        AnimationDrawable gyroAnimation = (AnimationDrawable) iv_spin_ring.getBackground();
        gyroAnimation.start();

        tv_spinglow = findViewById(R.id.tv_spinglow);
        tv_spinglow.setOnClickListener(this);
        startAnimation(iv_spin);
        //Spinner>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    }

    private void SpecialOffers() {
        //======== >>>> savan.nasit <<<< ==================Special Offer==========================
        special_offer = findViewById(R.id.special_offer);
        String PurchaseJson;
        int DayOfPurchase = 0;
        boolean isSpecialOfferPurChase = false;
        Calendar c = Calendar.getInstance();
        int Today_Day = c.get(Calendar.DAY_OF_YEAR);

        if (!(PreferenceManager.GetPurchaseJsonObject() == null)) {
            PurchaseJson = PreferenceManager.GetPurchaseJsonObject();
            try {
                JSONObject jsonObject = new JSONObject(PreferenceManager.GetPurchaseJsonObject());
                DayOfPurchase = jsonObject.getInt(Parameters.PurchaseDaY);
                isSpecialOfferPurChase = jsonObject.getBoolean(Parameters.isOfferPurchase);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        int dif = Today_Day - DayOfPurchase;
        if (PreferenceManager.GetPurchaseAnyPack()) {
            if (PreferenceManager.isInternetConnected()) {
                if (isSpecialOfferPurChase) {
                    if (DayOfPurchase != 0 && dif >= 4) {
                        if (dif % 2 == 0) {
                            special_offer.setVisibility(View.VISIBLE);
                        } else {
                            special_offer.setVisibility(View.GONE);
                        }
                    } else {
                        special_offer.setVisibility(View.GONE);
                    }
                } else {
                    if (DayOfPurchase != 0 && dif >= 2) {
                        if (dif % 2 == 0) {
                            special_offer.setVisibility(View.VISIBLE);
                        } else {
                            special_offer.setVisibility(View.GONE);
                        }
                    } else {
                        special_offer.setVisibility(View.GONE);
                    }
                }
            } else {
                special_offer.setVisibility(View.GONE);
            }
        } else {
            special_offer.setVisibility(View.GONE);
        }

        // >>>>>>>>> Click <<<<<<<<<
        special_offer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceManager.isInternetConnected()) {
                    ShowSpecialOfferPopup();
                } else {
                    showInfoDialog("No Internet", "Please check your Internet Connection.");
                }
            }
        });
        //======== >>>> savan.nasit <<<< ==================Special Offer==========================
    }

    @DebugLog
    private void setVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);

            myData.version = info.versionName;
            Version.setText(info.versionName + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DebugLog
    public void CancelAlarm() {

        Intent intent = new Intent(this, NotifyUser.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }

    @DebugLog
    private void bottom_right_linearSetup() {

        bottom_right_linear = findViewById(R.id.bottom_right_linear);

        rate_frame = findViewById(R.id.rate_frame);
        share_frame = findViewById(R.id.share_frame);
        removead_frame = findViewById(R.id.removead_frame);
        offerwall_frame = findViewById(R.id.offerwall_frame);

        rate_bg = findViewById(R.id.rate_bg);
        share_bg = findViewById(R.id.share_bg);
        removead_bg = findViewById(R.id.removead_bg);
        offerwall_bg = findViewById(R.id.offerwall_bg);

        rate_icn = findViewById(R.id.rate_icn);
        share_icn = findViewById(R.id.share_icn);
        removead_icn = findViewById(R.id.removead_icn);
        offerwall_img = findViewById(R.id.offerwall_img);
        removead_text = findViewById(R.id.removead_text);
        share_text = findViewById(R.id.share_text);
        rate_text1 = findViewById(R.id.rate_text1);
        offerwall_text = findViewById(R.id.offerwall_text);

        DrawBottomRightLinear();
    }

    @DebugLog
    private void DrawBottomRightLinear() {

        FrameLayout.LayoutParams fram_prm = (FrameLayout.LayoutParams) bottom_right_linear.getLayoutParams();
        fram_prm.rightMargin = myData.getwidth1(190);

        int MainFrameHeight = (130);
        int MainFrameWidth = (115);

        LinearLayout.LayoutParams lin_prm = (LinearLayout.LayoutParams) rate_frame.getLayoutParams();
        lin_prm.height = myData.getwidth1(MainFrameHeight);
        lin_prm.width = myData.getwidth1(MainFrameWidth);
        lin_prm.rightMargin = myData.getwidth1(10);
        share_frame.setLayoutParams(lin_prm);
        removead_frame.setLayoutParams(lin_prm);

        fram_prm = (FrameLayout.LayoutParams) offerwall_frame.getLayoutParams();
        fram_prm.height = myData.getwidth1(MainFrameHeight + 20);
        fram_prm.width = myData.getwidth1(145);
        fram_prm.rightMargin = getwidth(220);

        fram_prm = (FrameLayout.LayoutParams) rate_bg.getLayoutParams();
        fram_prm.height = fram_prm.width = myData.getwidth1(MainFrameWidth);
        share_bg.setLayoutParams(fram_prm);
        removead_bg.setLayoutParams(fram_prm);
        rate_icn.setLayoutParams(fram_prm);
        share_icn.setLayoutParams(fram_prm);
        removead_icn.setLayoutParams(fram_prm);

        fram_prm = (FrameLayout.LayoutParams) offerwall_img.getLayoutParams();
        fram_prm.height = fram_prm.width = myData.getwidth1(MainFrameWidth + 20);


        fram_prm = (FrameLayout.LayoutParams) offerwall_bg.getLayoutParams();
        fram_prm.height = fram_prm.width = myData.getwidth1(135);

        removead_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(18));
        share_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(18));
        rate_text1.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(18));
        offerwall_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, myData.getSize(18));


        removead_text.setTypeface(myData.typeface, Typeface.BOLD);
        share_text.setTypeface(myData.typeface, Typeface.BOLD);
        rate_text1.setTypeface(myData.typeface, Typeface.BOLD);
        offerwall_text.setTypeface(myData.typeface, Typeface.BOLD);

        offerwall_frame.setOnClickListener(this);
        removead_frame.setOnClickListener(this);
        share_frame.setOnClickListener(this);
        rate_frame.setOnClickListener(this);
    }

    @DebugLog
    private void StartPlayNowAnimation() {
        frmPlayNowEntryPoint.setVisibility(View.GONE);
        isplayingstart = true;
        Animation anim2;

        int x;
        int[] location = new int[2];


        PLAY_NOW.clearAnimation();
        PLAY_NOW_Middle.clearAnimation();
        PLAY_NOW_Middle3.clearAnimation();
        PLAY_NOW3.clearAnimation();
        iv_offer1.clearAnimation();
        PLAY_ON_TABLE.clearAnimation();
        PLAY_ON_TABLE_middle.clearAnimation();
        INVITE_GET_BTN.clearAnimation();
        INVITE_GET_BTN_middle.clearAnimation();

        playontableframe.clearAnimation();
        playnowframe.clearAnimation();
        playnowframe3.clearAnimation();
        privateframe.clearAnimation();

        PLAY_ON_TABLE.getLocationInWindow(location);
        x = location[0];
        Logger.print("SX>>>>>PLAY_ON_TABLE now X::" + x);
        anim2 = onclickply.stratanimation(x, ((myData.width / 2) - x) - (PLAY_NOW.getWidth() / 2), 0, 0);
        anim2.setFillAfter(true);
        anim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        playontableframe.startAnimation(anim2);


        INVITE_GET_BTN.getLocationInWindow(location);
        x = location[0];
        Logger.print("SX>>>>>CREATE_PRIVATE_TABLE_BTN now X::" + x);
        anim2 = onclickply.stratanimation(x, ((myData.width / 2) - x) - (PLAY_NOW.getWidth() / 2), 0, 0);
        anim2.setFillAfter(true);
        privateframe.startAnimation(anim2);


        PLAY_NOW.getLocationInWindow(location);
        x = location[0];
        Logger.print("SX>>>>>Play now X::" + x);
        anim2 = onclickply.stratanimation(x, ((myData.width / 2) - x) - (PLAY_NOW.getWidth() / 2), 0, 0);
        anim2.setFillAfter(true);
        playnowframe.startAnimation(anim2);

        PLAY_NOW3.getLocationInWindow(location);
        x = location[0];
        Logger.print("SX>>>>>Play now X::" + x);
        anim2 = onclickply.stratanimation(x, ((myData.width / 2) - x) - (PLAY_NOW.getWidth() / 2), 0, 0);
        anim2.setFillAfter(true);
        playnowframe3.startAnimation(anim2);


        //final int plyr=i;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                PlayNow();
                isplayingstart = false;
            }
        }, 700);

    }

    @Override
    @DebugLog
    public void onClick(View v) {
        if (isplayingstart) {
            return;
        }

        if (v == html5_bk || v == html5_middle || v == html5_lin) {

            Logger.print(">>>> HTML >>>>> RESPONSE >>> " + myData.httpresp);

            if (!myData.httpresp.equals("")) {

                try {
                    JSONObject object = new JSONObject(myData.httpresp);
                    if (object.getString("action").equals("VIEW")) {

                        Logger.print(">>>> HTML >>>>> RESPONSE >>> VIEW");
                        Intent innWeview = new Intent(getApplicationContext(), WebActivity.class);

                        innWeview.putExtra("path", object.getString("url"));
                        innWeview.putExtra("cid", object.getString("cid"));
//                        myData.fabricHtml5("VIEW");

                        startActivity(innWeview);


                    } else if (object.getString("action").equals("REDIRECT")) {
                        Logger.print(">>>> HTML >>>>> RESPONSE >>> REDIRECT");
                        String url = object.getString("url");

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                        myData.fabricHtml5("REDIRECT");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else if (v == offerwall_frame) {
            if (PreferenceManager.isInternetConnected()) {
                try {
                    Music_Manager.buttonclick();
                    String placementName = "DefaultOfferWall";
                    Logger.print("OFFERWALL >>>> ONCLICK " + IronSource.isOfferwallAvailable());
                    Logger.print("OFFERWALL >>>> ONCLICK " + myData.mMediationAgent);

                    if (IronSource.isOfferwallAvailable()) {

//                        myData.fabricOfferWall("Offer Wall Load");
                        IronSource.showOfferwall(placementName);
//                        myData.fabricButtonClicked("DashBoard", "OfferWall");

                    } else {
//                        myData.fabricOfferWall("Offer Wall Unavailable");
                        showInfoDialog("Alert", "Currently no Offer Wall, check back later. ");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showInfoDialog("No Internet", "Please check your Internet Connection.");
            }
        } else if (v == removead_frame) {


            try {
                Music_Manager.buttonclick();
//                myData.fabricButtonClicked("DashBoard", "RemoveAds");
                Intent inprofile = new Intent(getApplication(), Coin_per.class);
                startActivity(inprofile);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v == share_frame) {

            Music_Manager.buttonclick();
            final Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, "Join me on World's First Classic Gin Rummy Offline https://play.google.com/store/apps/details?id=com.artoon.ginrummyoffline");

            final List<ResolveInfo> activities = getPackageManager().queryIntentActivities(i, 0);

            final List<ResolveInfo> appNames = new ArrayList<>();

            for (ResolveInfo info : activities) {
                String appnname = info.loadLabel(getPackageManager()).toString();
                for (int m = 0; m < myData.shareViaApps.length; m++) {
                    if (myData.shareViaApps[m].equalsIgnoreCase(appnname)) {
                        appNames.add(info);
                        break;
                    }
                }
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Share Via...");

            final ShareIntentListAdapter adapter = new ShareIntentListAdapter(this, R.layout.adaptershareintent, appNames.toArray());
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ResolveInfo info = (ResolveInfo) adapter.getItem(which);

                    Label = info.activityInfo.loadLabel(getPackageManager()).toString();

                    try {
                        isShareViaApp = true;
                        startTimer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    i.setPackage(info.activityInfo.packageName);
                    startActivityForResult(i, 4171);

                }
            });
            if (!isFinishing()) {
                builder.create().show();
            }


        } else if (v == rate_frame) {

            Music_Manager.buttonclick();
            Intent share = new Intent(this, ActivityRateUs.class);
            startActivity(share);
            overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);

        } else if (v == invite_btn) {
//            myData.fabricButtonClicked("DashBoard", "How To play");
            Music_Manager.buttonclick();
            v.startAnimation(buttonPressed);
            Intent inprofile = new Intent(getApplication(), HowToPlay.class);
            startActivity(inprofile);
            overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
        } else if (v == PLAY_NOW || v == PLAY_NOW_Middle || v == play_now_text) {
            Music_Manager.buttonclick();
            SETBOOTVALUE();
            PreferenceManager.setNumberOfPlayers(2);
            PreferenceManager.setgameType(0);
            myData.gameType = 0;
            if (myData.Chips >= 500) {
                Intent selectmode = new Intent(this, Select_Mode.class);
                startActivity(selectmode);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
//                myData.fabricButtonClicked("DashBoard", "PlayNow 2 Palyer");
            } else {
                showInfoDialogBuy("Out Of Chip", "You don't have enough chips to play on table!");
            }
        } else if (v == PLAY_NOW3 || v == PLAY_NOW_Middle3 || v == play_now_text3) {
            Music_Manager.buttonclick();
            SETBOOTVALUE();
            PreferenceManager.setNumberOfPlayers(3);
            PreferenceManager.setgameType(0);
            myData.gameType = 0;
            if (myData.Chips >= 500) {
//                myData.fabricButtonClicked("DashBoard", "PlayNow 3 Palyer");
                Intent selectmode = new Intent(this, Select_Mode.class);
                startActivity(selectmode);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
            } else {
                showInfoDialogBuy("Out Of Chip", "You don't have enough chips to play on table!");
            }
        } else if (v == btn_achivement_icn) {
            Music_Manager.buttonclick();
            Intent achivement = new Intent(getApplication(), ActivityQuestAchivement.class);
//            myData.fabricButtonClicked("DashBoard", "AchiveMent");
            achivement.putExtra(Parameters.isQuest, false);
            startActivity(achivement);
            overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
        } else if (v == btn_quest_icn) {

            Music_Manager.buttonclick();
            Intent quest = new Intent(getApplication(), ActivityQuestAchivement.class);
            quest.putExtra(Parameters.isQuest, true);
//            myData.fabricButtonClicked("DashBoard", "Quest");
            startActivity(quest);
            overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);


        } else if (v == leaderboard_btn) {

            Music_Manager.buttonclick();
            if (PreferenceManager.get_Google_Sing_in()) {
                if (PreferenceManager.isInternetConnected()) {
                    //showSavedGamesUI();
//                    myData.fabricScreenView("LeaderBoard");
//                    myData.fabricButtonClicked("DashBoard", "LeaderBoard");
                    showGooglePlayLeaderBoard();
                } else {
                    showInfoDialog("No Internet", "Please check your Internet Connection.");
                }
            } else {
                showInfoDialog("Alert!", "Please Sign In To Google Play Games First.");
            }

        } else if (v == frmPlayNowEntryPoint) {
            Music_Manager.buttonclick();
            openPlayerChooserDialog();
        } else if (v == INVITE_GET_BTN || v == INVITE_GET_BTN_middle || v == da_minigame) {
            Music_Manager.buttonclick();
            boolean installed = appInstalledOrNot("com.ginrummymultiplayer");
            if (installed) {
                Intent LaunchIntent = getPackageManager()
                        .getLaunchIntentForPackage("com.ginrummymultiplayer");
                startActivity(LaunchIntent);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
            } else {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.ginrummymultiplayer")));
                    overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ginrummymultiplayer")));
                    overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
                }
            }
        } else if (v == setting_btn) {
            Music_Manager.buttonclick();
//            myData.fabricButtonClicked("DashBoard", "Setting");
//            setting_btn.startAnimation(buttonPressed);
//            if (setting_popUp.getVisibility() == View.VISIBLE) {
//                setting_popUp.setVisibility(View.GONE);
//            } else {
//                Animation animation = AnimationUtils.loadAnimation(
//                        getApplicationContext(), R.anim.slide_in_right);
//                setting_popUp.setVisibility(View.VISIBLE);
//                findViewById(R.id.da_scrollsetting).scrollTo(0, 0);
//                if (PreferenceManager.getMute()) {
//                    sound.setChecked(false);
//                    Music_Manager.Mute = true;
//                } else {
//                    sound.setChecked(true);
//                    Music_Manager.Mute = false;
//                }
//
//                Vibrate.setChecked(PreferenceManager.getVibrate());
//                setting_popUp.startAnimation(animation);
//            }
            Intent intent = new Intent(Dashboard.this, Setting.class);
            startActivity(intent);
            overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);

        } else if (v == profile_container) {

            Music_Manager.buttonclick();
//            myData.fabricButtonClicked("DashBoard", "Profile");
            profile_container.startAnimation(buttonPressed);
            Intent inprofile = new Intent(getApplication(), Activity_Profile.class);
            inprofile.putExtra("uid", PreferenceManager.get_id());
            startActivity(inprofile);
            overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
        } else if (v == PLAY_ON_TABLE || v == PLAY_ON_TABLE_middle || v == play_on_table_text) {
            Music_Manager.buttonclick();
//            myData.fabricButtonClicked("DashBoard", "Play On Table");
            Intent inprofile = new Intent(getApplication(), Activity_Tables.class);
            startActivity(inprofile);
            overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
        } else if (v == Chips_text) {
            Music_Manager.buttonclick();
//            myData.fabricButtonClicked("DashBoard", "Store");
            Intent inprofile = new Intent(getApplication(), Coin_per.class);
            startActivity(inprofile);
            overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
        } else if (v == videoAdframe || v == videoIcon || v == videoText || v == videoIconchip2 || v == videoIconchip1 || v == videoIcontyre) {
            if (PreferenceManager.isInternetConnected()) {
                if (!VideoTimer) {
//                    myData.fabricButtonClicked("DashBoard", "Free Chips");
                    Music_Manager.buttonclick();
                }
            } else {
                Music_Manager.buttonclick();
                showInfoDialog("No Internet", "Please check your Internet Connection.");
            }
        } else if (v == frmPlayNowEntryPoint_2players) {

            Music_Manager.buttonclick();
            SETBOOTVALUE();
            PreferenceManager.setNumberOfPlayers(2);
            PreferenceManager.setgameType(0);

            if (myData.Chips >= 500) {
//                myData.fabricButtonClicked("DashBoard", "PlayNow 2 Palyer");
                Intent twoPlayer = new Intent(Dashboard.this, Activity_Point_Select.class);
                startActivity(twoPlayer);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
            } else {
                showInfoDialogBuy("Out Of Chip", "You don't have enough chips to play on table!");
            }


        } else if (v == frmPlayNowEntryPoint_3players) {
            Music_Manager.buttonclick();
            SETBOOTVALUE();
            PreferenceManager.setNumberOfPlayers(3);
            PreferenceManager.setgameType(0);
            if (myData.Chips >= 500) {
//                myData.fabricButtonClicked("DashBoard", "PlayNow 3 Palyer");
                Intent twoPlayer = new Intent(Dashboard.this, Activity_Point_Select.class);
                startActivity(twoPlayer);
                overridePendingTransition(R.anim.from_righttoleft2, R.anim.none);
            } else {
                showInfoDialogBuy("Out Of Chip", "You don't have enough chips to play on table!");
            }
        } else if (v == iv_spintext || v == iv_spin_ring || v == iv_spin || v == tv_spinglow) {
            Intent intent = new Intent(Dashboard.this, SpinEarn.class);
            startActivity(intent);
        }

    }

    @DebugLog
    private void showGooglePlayLeaderBoard() {


        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient, getString(R.string.LEADERBOARD_ID), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC), REQUEST_LEADERBOARD);
        } else {
            Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> mGoogleApiClient is Not Connected ");

            if (PreferenceManager.isInternetConnected() && !PreferenceManager.get_FirstTime() && PreferenceManager.get_Google_Sing_in()) {
                Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> CONNECTION START 3333 >>> ");
                mGoogleApiClient.connect();
            }
        }

    }

    @Override
    @DebugLog
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                Log.e("Ok DUDE Result >>>>> ", "True");
            } else if (resultCode == RESULT_CANCELED) {
                Log.e("Ok DUDE Result >>>>> ", "False");
            }
        }

        if (requestCode == 4171) {
            PreferenceManager.SetQuestShareCount(PreferenceManager.GetQuestShareCount() + 1);
        }

        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onActivityResult >>> ");
        if (requestCode == RC_SIGN_IN) {

            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onActivityResult >>> 1111");
                Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onActivityResult >>> 1111 >>> mGoogleApiClient.isConnected() >>> " + mGoogleApiClient.isConnected());

                if (!mGoogleApiClient.isConnected()) {
                    if (PreferenceManager.isInternetConnected()/* && PreferenceManager.get_Google_Sing_in() && !PreferenceManager.get_FirstTime()*/) {
                        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onActivityResult >>> 1111 >>> reTry Connect 11");
                        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> CONNECTION START 4444 >>> ");
                        mGoogleApiClient.connect();
                    }
                }
            } else {
                Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onActivityResult >>> 2222");

                if (!mGoogleApiClient.isConnected()) {
                    if (PreferenceManager.isInternetConnected()/*&& PreferenceManager.get_Google_Sing_in() && !PreferenceManager.get_FirstTime()*/) {
                        Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> CONNECTION START 5555 >>> ");
                        mGoogleApiClient.connect();
                    }
                }

            }
        }

        if (requestCode == REQUEST_LEADERBOARD) {
            if (resultCode == RESULT_OK) {
                Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onActivityResult >>> LeaderBoards >>> Sucsses");
            } else if (resultCode == RESULT_CANCELED) {
                Logger.print("KKKKKKKKKKKKKKK>>> Google Play Service >>> onActivityResult >>> LeaderBoards >>> Fail");
            }
        } else if (requestCode == RC_SAVED_GAMES) {
            Log.d(TAG, "KKKKKKKKKK >>>onActivityResult: RC_SELECT_SNAPSHOT, resultCode = " + resultCode);
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    SnapshotMetadata selected = Games.Snapshots.getSnapshotFromBundle(bundle);
                    if (selected == null) {

                        savedGamesUpdate();
                    } else {
                        String snapshotName = selected.getUniqueName();
                        loadFromSnapshot(false);
                    }
                } else {
                    savedGamesUpdate();

                }
            }
        }


    }

    @DebugLog
    private void stopTimer() {
        if (mTimer1 != null) {
            mTimer1.cancel();
            mTimer1.purge();
        }
    }

    @DebugLog
    private void startTimer() {
        mTimer1 = new Timer();
        myData.Shareclickintime = 0;
        mTimer1.schedule(new TimerTask() {
            public void run() {
                myData.Shareclickintime++;
                Logger.print("MMMMMMMMMMM >>> timer " + myData.Shareclickintime);
            }
        }, 0, 1000);
    }

    @DebugLog
    private void IncrementCounterChip(int start, int end) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Logger.print("WWWWWWWWWWWWWW INCREMENT COUNTER");
                        Chips_text.setText("" + myData.numDifferentiation(PreferenceManager.getChips()));

                    }
                });
            }
        }, 600);
    }

    @DebugLog
    private void EnableVideoChip() {
        videoIcon.setEnabled(true);
        videoIconchip1.setEnabled(true);
        videoIconchip2.setEnabled(true);
        videoIcontyre.setEnabled(true);
        videoText.setEnabled(true);
        videoAdframe.setEnabled(true);
        ViewHelper.setAlpha(videoAdframe, 1.0f);
        rewarded_noti.setVisibility(View.VISIBLE);
    }

    @DebugLog
    private void disableVideoChip() {
        videoIcon.setEnabled(false);
        videoIconchip1.setEnabled(false);
        videoIconchip2.setEnabled(false);
        videoIcontyre.setEnabled(false);
        videoText.setEnabled(false);
        ViewHelper.setAlpha(videoAdframe, 0.50f);
        videoAdframe.setEnabled(false);
        rewarded_noti.setVisibility(View.GONE);
    }

    @DebugLog
    private void checkRestore() {

        if (PreferenceManager.getremoveAdPurchased()) {
            showInfoDialog("Restore", "Remove Ads Successfully");
            PreferenceManager.setisshowAd(false);
            PreferenceManager.SetQuestRemoveAdsCount(0);
            PreferenceManager.SetQuestRemoveAdsClaimCountLife(1);
        } else {
            showInfoDialog("Restore", "You have not purchased any Pack");
            PreferenceManager.setisshowAd(true);
        }
    }

    @DebugLog
    private boolean GameisOn() {
        // TODO Auto-generated method stub

        ActivityManager activityManager = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager
                .getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;

        if (services.get(0).topActivity.getPackageName()
                .equalsIgnoreCase(this.getPackageName())) {
            isActivityFound = true;
        }

        return isActivityFound;
    }

    @DebugLog
    private int getRelativeLeft(View myView) {

        if (myView.getParent() == myView.getRootView())
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }

    @DebugLog
    private int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }
    //todo >>>>>>>>>>>>> InApp Purchase <<<<<<<<<<<<<<<<<<

    @DebugLog
    private int getX(ImageView image) {

        int Right[] = new int[2];

        image.getLocationOnScreen(Right);
        return Right[0];

    }

    @DebugLog
    private int getY(ImageView image) {

        int Right[] = new int[2];
        image.getLocationOnScreen(Right);
        return Right[1];
    }

    @Override
    @DebugLog
    public void onBackPressed() {

        try {

            if (PreferenceManager.getisshowAd()) {
                Logger.print("GETTTTTT ADDDDDDDDDDDDDDDDD Winner class");

                if (mInterstitial != null && mInterstitial.isLoaded()) {
                    Logger.print("GETTTTTT ADDDDDDDDDDDDDDDDD Winner class111111 ");
                    if (!isFinishing()) {
                        mInterstitial.show();
                        isbackPress = true;
                    }

                } else {
                    isbackPress = false;
                    showConfirmationDialog();
                    if (mInterstitial != null) {
                        requestNewInterstitial();
                    } else {
                        showAds();
                    }
                }
            } else {
                showConfirmationDialog();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @DebugLog
    private void showConfirmationDialog() {
        // TODO Auto-generated method stub

        LinearLayout.LayoutParams lin;

        if (ExitConfirmationDialog != null || (ExitConfirmationDialog != null && ExitConfirmationDialog.isShowing())) {
            if (ExitConfirmationDialog.isShowing()) {
                ExitConfirmationDialog.dismiss();
            }
            ExitConfirmationDialog = null;
        }

        ExitConfirmationDialog = new Dialog(Dashboard.this,
                R.style.Theme_Transparent);
        ExitConfirmationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ExitConfirmationDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ExitConfirmationDialog.setContentView(R.layout.dialog);
        ExitConfirmationDialog.setCancelable(false);

        TextView title = ExitConfirmationDialog
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(typeface);
        title.setText("Exit Game");

        lin = (LinearLayout.LayoutParams) ExitConfirmationDialog.findViewById(
                R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);

        TextView message = ExitConfirmationDialog
                .findViewById(R.id.tvMessage);
        message.setTypeface(typeface);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

        Button btnOne = ExitConfirmationDialog.findViewById(R.id.button1);
        btnOne.setTypeface(typeface);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("Yes");

        Button btnTwo = ExitConfirmationDialog.findViewById(R.id.button2);
        btnTwo.setTypeface(typeface);
        btnTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnTwo.setText("No");

        lin = (LinearLayout.LayoutParams) ExitConfirmationDialog.findViewById(
                R.id.button1).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(120);

        lin = (LinearLayout.LayoutParams) ExitConfirmationDialog.findViewById(
                R.id.tvMessage).getLayoutParams();
        lin.topMargin = getheight(30);

        lin = (LinearLayout.LayoutParams) ExitConfirmationDialog.findViewById(
                R.id.llButtons).getLayoutParams();
        lin.topMargin = getheight(30);

        lin = (LinearLayout.LayoutParams) ExitConfirmationDialog.findViewById(
                R.id.button2).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(120);

        message.setText("Are you sure, want to quit game?");

        btnOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Music_Manager.buttonclick();
                try {
                    ExitConfirmationDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setQuestAlarm();
                setAchivementAlarm();
                setHourlyBonusNotification();
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });

        btnTwo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Music_Manager.buttonclick();
                ExitConfirmationDialog.dismiss();
            }
        });
        if (!isFinishing()) {
            ExitConfirmationDialog.show();
        }
    }

    @DebugLog
    private void openPlayerChooserDialog() {

        LinearLayout.LayoutParams lin;

        if (playerChooserDialog != null) {
            if (playerChooserDialog.isShowing()) {
                playerChooserDialog.dismiss();
            }
            playerChooserDialog = null;
        }

        playerChooserDialog = new Dialog(Dashboard.this,
                R.style.Theme_Transparent);
        playerChooserDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        playerChooserDialog.setContentView(R.layout.dialog);

        TextView title = playerChooserDialog
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(typeface);
        title.setText("Select Players");

        lin = (LinearLayout.LayoutParams) playerChooserDialog.findViewById(
                R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);

        TextView message = playerChooserDialog
                .findViewById(R.id.tvMessage);
        message.setTypeface(typeface);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

        Button btnOne = playerChooserDialog.findViewById(R.id.button1);
        btnOne.setTypeface(typeface, Typeface.BOLD);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("2 Players");

        Button btnTwo = playerChooserDialog.findViewById(R.id.button2);
        btnTwo.setTypeface(typeface, Typeface.BOLD);
        btnTwo.setBackgroundResource(R.drawable.get_free_card);
        btnTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnTwo.setText("3 Players");

        lin = (LinearLayout.LayoutParams) playerChooserDialog.findViewById(
                R.id.button1).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(120);

        lin = (LinearLayout.LayoutParams) playerChooserDialog.findViewById(
                R.id.button2).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(120);

        lin = (LinearLayout.LayoutParams) playerChooserDialog.findViewById(
                R.id.tvMessage).getLayoutParams();
        lin.topMargin = getheight(30);

        lin = (LinearLayout.LayoutParams) playerChooserDialog.findViewById(
                R.id.llButtons).getLayoutParams();
        lin.topMargin = getheight(30);

        message.setVisibility(View.GONE);

        btnOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                Music_Manager.buttonclick();
                playerChooserDialog.dismiss();
                PreferenceManager.setNumberOfPlayers(2);
                tvEntryPointPlayNow.setText("Players : 2");
                // showSavedGamesUI();
            }
        });

        btnTwo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Music_Manager.buttonclick();
                playerChooserDialog.dismiss();
                PreferenceManager.setNumberOfPlayers(3);
                tvEntryPointPlayNow.setText("Players : 3");
            }
        });
        if (!isFinishing()) {
            playerChooserDialog.show();
        }
    }

    @DebugLog
    public void StartConnection(String link) {

        try {
            Uri uriUrl2 = Uri.parse(link);
            Intent launchBrowser2 = new Intent(Intent.ACTION_VIEW, uriUrl2);
            startActivity(launchBrowser2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DebugLog
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void ParseResponce(InputStream stream) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String line;
        StringBuilder builder = new StringBuilder();

        try {

            while ((line = reader.readLine()) != null) {

                builder.append(line);
            }

            reader.close();

            String strResp = builder.toString();

            try {
                Logger.print(">>>> DashBoard >>> Payment >>> <<<<<< response :" + strResp);

                if (strResp.equals("completed") && !indexfinal.equals("")) {

                    Logger.print(">>>> DashBoard >>> Payment >>> <<<<<< response :" + strResp);
                    Log.e("InApp Purchase", "Dashboard http response : " + strResp);



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chipAddedDialog(Integer.parseInt(indexfinal));
                        }
                    });


                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chipAddedDialog(final int index) {
        myData.Chips = PreferenceManager.getChips();
        InAppSku[0] = "ginrummyofflinechipspack4";
        InAppSku[1] = "ginrummyofflinechipspack3";
        InAppSku[2] = "ginrummyofflinechipspack2";
        InAppSku[3] = "ginrummyofflinechipspack1";
        InAppSku[4] = "ginrummyofflineremoveads";

        coins[0] = 5000000;
        coins[1] = 1500000;
        coins[2] = 500000;
        coins[3] = 100000;
        coins[4] = 0;

        Calendar c = Calendar.getInstance();
        int PurchaseDay = c.get(Calendar.DAY_OF_YEAR);

        String massg = "" + myData.formatter.format(coins[index]) + " Coins added in your account !";
        String tittle = "Store";
        boolean isSpecialOfferPurChase = false;
        JSONObject PurchaseObj = new JSONObject();
        if (index == 0) {

            long ChipsToAdded = 5000000;

            if (isOfferPurchase) {
                ChipsToAdded *= 2;
                isSpecialOfferPurChase = true;
            }
            massg = "" + myData.formatter.format(ChipsToAdded) + " Coins added in your account !";
            myData.Chips = myData.Chips + ChipsToAdded;
            PreferenceManager.setChips(myData.Chips);
            SQLiteManager.updateChip(PreferenceManager.get_id(), ChipsToAdded, true);
            PreferenceManager.setisshowAd(false);

            PreferenceManager.SetQuestPackTenDollerCollected(false);
            PreferenceManager.SetQuestPackTenDollerCount(1);
            Dashboard.savedGamesUpdate();
            Logger.print(">>>>>>>   Sucess >>>>>>>");
//            myData.fabricPurchase(11.99f, InAppSku[index], "50,00,000 + RemoveAds", "Chips + RemoveAds");
            PreferenceManager.SetPurchaseAnyPack(true);

            //>>>>>>>>> Special Offer <<<<<<<<<<
            try {
                PurchaseObj.put(Parameters.PurchaseDaY, PurchaseDay);
                PurchaseObj.put(Parameters.PurchasePrice, "11.99");
                PurchaseObj.put(Parameters.PurchaseSkuid, InAppSku[index]);
                PurchaseObj.put(Parameters.PurchaseAmount, "" + coins[index]);
                PurchaseObj.put(Parameters.PurchaseIndex, index);
                PurchaseObj.put(Parameters.isOfferPurchase, isSpecialOfferPurChase);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PreferenceManager.SetPurchaseJsonObject(PurchaseObj.toString());
            //>>>>>>>>> Special Offer <<<<<<<<<<
            Log.e(">>>>> Data Json >>>>>> ", "" + PreferenceManager.GetPurchaseJsonObject());
        } else if (index == 1) {

            long ChipsToAdded = 1500000;
            if (isOfferPurchase) {
                ChipsToAdded *= 2;
                isSpecialOfferPurChase = true;
            }
            massg = "" + myData.formatter.format(ChipsToAdded) + " Coins added in your account !";
            myData.Chips = myData.Chips + ChipsToAdded;
            PreferenceManager.setChips(myData.Chips);
            SQLiteManager.updateChip(PreferenceManager.get_id(), ChipsToAdded, true);
            PreferenceManager.setisshowAd(false);

            PreferenceManager.SetQuestPackFiveDollerCollected(false);
            PreferenceManager.SetQuestPackFiveDollerCount(1);
            Dashboard.savedGamesUpdate();
//            myData.fabricPurchase(5.99f, InAppSku[index], "15,00,000 + RemoveAds", "Chips + RemoveAds");
            PreferenceManager.SetPurchaseAnyPack(true);

            //>>>>>>>>> Special Offer <<<<<<<<<<
            try {
                PurchaseObj.put(Parameters.PurchaseDaY, PurchaseDay);
                PurchaseObj.put(Parameters.PurchasePrice, "5.99");
                PurchaseObj.put(Parameters.PurchaseSkuid, InAppSku[index]);
                PurchaseObj.put(Parameters.PurchaseAmount, "" + coins[index]);
                PurchaseObj.put(Parameters.PurchaseIndex, index);
                PurchaseObj.put(Parameters.isOfferPurchase, isSpecialOfferPurChase);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PreferenceManager.SetPurchaseJsonObject(PurchaseObj.toString());
            //>>>>>>>>> Special Offer <<<<<<<<<<

        } else if (index == 2) {


            long ChipsToAdded = 500000;
            if (isOfferPurchase) {
                ChipsToAdded *= 2;
                isSpecialOfferPurChase = true;
            }
            massg = "" + myData.formatter.format(ChipsToAdded) + " Coins added in your account !";
            myData.Chips = myData.Chips + ChipsToAdded;
            PreferenceManager.setChips(myData.Chips);
            SQLiteManager.updateChip(PreferenceManager.get_id(), ChipsToAdded, true);

            PreferenceManager.SetQuestPackThreeDollerCollected(false);
            PreferenceManager.SetQuestPackThreeDollerCount(1);
            Dashboard.savedGamesUpdate();
//            myData.fabricPurchase(3.49f, InAppSku[index], "5,00,000", "Chips");
            PreferenceManager.SetPurchaseAnyPack(true);

            //>>>>>>>>> Special Offer <<<<<<<<<<
            try {
                PurchaseObj.put(Parameters.PurchaseDaY, PurchaseDay);
                PurchaseObj.put(Parameters.PurchasePrice, "3.49");
                PurchaseObj.put(Parameters.PurchaseSkuid, InAppSku[index]);
                PurchaseObj.put(Parameters.PurchaseAmount, "" + coins[index]);
                PurchaseObj.put(Parameters.PurchaseIndex, index);
                PurchaseObj.put(Parameters.isOfferPurchase, isSpecialOfferPurChase);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PreferenceManager.SetPurchaseJsonObject(PurchaseObj.toString());
            //>>>>>>>>> Special Offer <<<<<<<<<<

        } else if (index == 3) {

            long ChipsToAdded = 100000;
            if (isOfferPurchase) {
                ChipsToAdded *= 2;
                isSpecialOfferPurChase = true;
            }
            massg = "" + myData.formatter.format(ChipsToAdded) + " Coins added in your account !";
            myData.Chips = myData.Chips + ChipsToAdded;
            PreferenceManager.setChips(myData.Chips);
            SQLiteManager.updateChip(PreferenceManager.get_id(), ChipsToAdded, true);

            PreferenceManager.SetQuestPackOneDollerCollected(false);
            PreferenceManager.SetQuestPackOneDollerCount(1);
            Dashboard.savedGamesUpdate();
//            myData.fabricPurchase(1.49f, InAppSku[index], "1,00,000", "Chips");
            PreferenceManager.SetPurchaseAnyPack(true);

            //>>>>>>>>> Special Offer <<<<<<<<<<
            try {
                PurchaseObj.put(Parameters.PurchaseDaY, PurchaseDay);
                PurchaseObj.put(Parameters.PurchasePrice, "1.49");
                PurchaseObj.put(Parameters.PurchaseSkuid, InAppSku[index]);
                PurchaseObj.put(Parameters.PurchaseAmount, "" + coins[index]);
                PurchaseObj.put(Parameters.PurchaseIndex, index);
                PurchaseObj.put(Parameters.isOfferPurchase, isSpecialOfferPurChase);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PreferenceManager.SetPurchaseJsonObject(PurchaseObj.toString());
            //>>>>>>>>> Special Offer <<<<<<<<<<

        } else if (index == 4) {
            PreferenceManager.setisshowAd(false);
            PreferenceManager.SetQuestRemoveAdsCount(1);
            Dashboard.savedGamesUpdate();

//            myData.fabricPurchase(3.49f, InAppSku[index], "RemoveAds", "RemoveAds");
            massg = "Remove Ads Successfully";
            tittle = "Purchase Successfull";
        }

        if (isOfferPurchase) {
            special_offer.setVisibility(View.GONE);
            if (SpecialOfferDialog.isShowing()) {
                SpecialOfferDialog.dismiss();
            }
        }

        if (handler != null) {
            Message msg = new Message();
            msg.what = ResponseCodes.UPDATE_CHIP;
            handler.sendMessage(msg);
        }
        myData.ShowInfoDialog(massg, tittle);


    }

    private static class DownloadFilesTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            final String snapshotName = makeSnapshotName(APP_STATE_KEY);
            final boolean createIfMissing = true;

            final byte[] data = getData().getBytes();
            try {
                if (mGoogleApiClient.isConnected()) {
                    Snapshots.OpenSnapshotResult open = Games.Snapshots.open(
                            mGoogleApiClient, snapshotName, createIfMissing).await();


                    Log.w(TAG, "KKKKKKKK>>> DATA UPLOAD >>> " + Arrays.toString(data));
                    Log.w(TAG, "KKKKKKKK>>> SNAP SHOT RESULT  >>> " + open.getStatus().getStatusMessage() + " : " + open.getStatus().getStatusCode());

                    Snapshot snapshot;
                    try {
                        if (open.getStatus().getStatusCode() == GamesStatusCodes.STATUS_SNAPSHOT_CONFLICT) {
                            snapshot = processSnapshotOpenResult(open, 0);


                        } else {
                            snapshot = open.getSnapshot();
                        }
                        if (snapshot == null) {
                            snapshot = open.getSnapshot();
                        }
                        if (snapshot != null) {
                            snapshot.getSnapshotContents().writeBytes(data);


                            if (coverImage == null) {
                                coverImage = BitmapFactory.decodeResource(context.getResources(),
                                        R.drawable.me);
                            }


                            SnapshotMetadataChange metadataChange = new SnapshotMetadataChange.Builder()
                                    .setCoverImage(coverImage)
                                    .setDescription(PreferenceManager.getUserName() + " : " + PreferenceManager.get_id())
                                    .build();

                            Snapshots.CommitSnapshotResult commit = Games.Snapshots.commitAndClose(
                                    mGoogleApiClient, snapshot, metadataChange).await();
                            if (!commit.getStatus().isSuccess()) {
                                Log.w(TAG, "KKKKKKKK>>> Failed to commit Snapshot.");
                                return false;
                            }
                            Log.w(TAG, "KKKKKKKK>>> Success commit Snapshot.");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    //==============================================================================================
    @SuppressLint("StaticFieldLeak")
    class insert extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            insertAllUserData();
            return null;
        }

    }

    @SuppressLint("StaticFieldLeak")
    class insertDailybonus extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {


            insertDailyBonusData();
            return null;
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData();
            return null;
        }

        protected void onPostExecute(Double result) {
            //  pb.setVisibility(View.GONE);
            // Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();

        }

        protected void onProgressUpdate(Integer... progress) {
            //pb.setProgress(progress[0]);
        }

        void postData() {

            // Create a new HttpClient and Post Header

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(path);

            try {

                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("data", datastr));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity httpEntity = response.getEntity();
                InputStream is = httpEntity.getContent();

                ParseResponce(is);

            } catch (ClientProtocolException e) {
                Logger.print("");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}
