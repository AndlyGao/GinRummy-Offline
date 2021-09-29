package utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artoon.ginrummyoffline.DailyBonus3;
import com.artoon.ginrummyoffline.Dashboard;
import com.artoon.ginrummyoffline.Music_Manager;
import com.artoon.ginrummyoffline.R;
import com.google.android.gms.ads.AdRequest;
import com.ironsource.adapters.supersonicads.SupersonicConfig;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.logger.IronSourceLogger;
import com.ironsource.mediationsdk.logger.LogListener;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.OfferwallListener;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;


//import com.supersonic.adapters.supersonicads.SupersonicConfig;
//import com.supersonic.mediationsdk.logger.LogListener;
//import com.supersonic.mediationsdk.logger.SupersonicError;
//import com.supersonic.mediationsdk.logger.SupersonicLogger;
//import com.supersonic.mediationsdk.model.Placement;
//import com.supersonic.mediationsdk.sdk.OfferwallListener;
//import com.supersonic.mediationsdk.sdk.Supersonic;
//import com.supersonic.mediationsdk.sdk.SupersonicFactory;
@SuppressWarnings("all")
public class C {

    //public ArrayList<HashMap<String, String>> AppshareCollectinfo = new ArrayList<HashMap<String, String>>();
    //public ArrayList<String> AppshareCollectApps = new ArrayList<String>();

    public static final int NO_CHIPS = 1000;
    public static final byte[] e = new byte[]{-58, -103, 83, 44, -96, 12, -94, 67, 97, -61, -41, -19, 18,
            -40, -127, -50, -41, 75, -100, 93, -38, -49, 79, 6, -89, -4, -28, 50, 7, -63, 55,
            -109, -36, 60, 54, 98, 124, -63, -41, -59, -74, 44, -11, -57, 99, -20, 80, 75, 18,
            -81, -109, 93, 19, -8, -121, 100, 41, 124, 51, 30, -127, 36, -114, 87, -30, -124,
            37, 22, 37, 30, -90, 21, -4, 57, 8, 114, 93, 100, 123, -112, 84, 105, 24, 53, 50,
            -69, 66, 102, -107, 47, 115, -63, 21, -90, -72, 119, 96, -111, -39, 78, 35, -119,
            43, -23, 121, 119, 70, 56, 80, 0, -97, -87, 5, 12, -64, 94, 34, 97, 56, 17, 44,
            -27, -62, -54, -38, -10, -10, -108, -98, 47, 102, 106, 119, 85, -116, 15, 98, 76,
            103, 115, -106, 115, 6, -87, -84, 65, 112, -85, -15, 55, 7, 99, -17, 81, 51, 126,
            -100, -31, 110, 47, 84, 62, -94, -41, 4, -123, 100, 33, -18, -124, 16, 13, 74,
            93, 46, 93, -104, -127, -28, -47, -91, 12, -97, -9, 52, 25, 46, 21, 78, -61,
            1, -83, -29, 70, -126, -75, 14, -87, 47, 66, 27, 26, -16, -94, 36, 101, 30, -126,
            -126, -117, 105, 87, 18, -65, -109, 91, -88, -109, 28, -57, -29, 108, -67, 68,
            37, -103, -25, 37, -53, -60, -54, 90, 80, 56, -24, -13, 25, -127, 2, -47, 28, 16,
            -54, -51, 80, 91, -16, 31, -90, 100, 55, 42, 52, -87, 95, 4, -104, -92, -5, -32,
            49, 85, 51, 43, -105, 89, 81, 112, -13, 40, 59, -48, -92, -77, -63, -90, 122, -1,
            -94, -114, 65, -23, 63, 108, 2, 85, 77, 72, 107, -35, -32, 99, -43, -120, 125,
            -30, -15, -16, -41, 105, 81, -35, -123, 15, -56, -61, -96, -60, 10, -123, -4,
            36, -67, -66, 32, 52, -69, 116, 11, -25, 17, 20, -103, -83, 57, 42, -126, -92,
            -96, 34, 104, 67, 65, 122, 29, 117, -77, -21, 48, 100, 102, 29, 87, -82, -111,
            55, -36, 39, 79, -2, 60, 120, -3, -16, -106, 74, 12, -110, -36, 58, -80, -23, 19,
            -93, -34, -78, 113, -93, -69, -19, -80, -30, 1, -69, -96, 100, 17, 51, 126, 70, 68,
            -118, -29, 114, 31, -4, 121, -112, 17, -25, -89, 124, 111, 20, 49, 46, -97, -34,
            13, -121, -52, 19, 72, 101, 42, -112, -83, -26, 49, 78, -69, -93, -62, 39, -78, 125,
            -115, 12, -102, -125, -116, -101, 10, -117, -95, 102, -14, 42, -87, -73, 50, 1, -45,
            31, -30, -72, -17, -93, -11, -26, 127, -20, 116, 100, 37, -107, -99, -64, -99, -2,
            -120, 7, 34, 50, -78, 7, -55, -89, -115, 50, 99, -41, -44, -118, -104, 60, -47, 115,
            43, 74, -101, 81, 8, -41, -53, -21, -109, -14, -52, 112, 91, 55, -60, 124, -25, -92,
            4, 119, 68, -71, -75, 24, 120, -77, -93, 52, -109, -85, 10, -97, 68, 52, -128, 64, 103,
            -102, 68, -64, 58, -21, 44, 57, -40, -112, -123, 53, 119, 57, -126, -126, -102, 42, 110,
            23, -125, -21, 80, -37, -90, 95, 113, 66, 122, 123, 14, -47, 50, -104, -118, 67, 41,
            -85, 92, 85, 59, 42, -29, 68, -107, -77, 117, 37, 114, 109, -78, -16, -12, -88, 10,
            -108, -35, -98, 82, -76, -80, -47, -1, 30, -48, 83, 86, -15, 12, 57, -25, -124, 32,
            -59, 94, -70, 117, 93, 74, -96, -92, -24, 8, -59, -23, -24, 63, 85, 122, -45, -39, 73,
            88, 84, 30, -60, 18, 97, 108, -14, 15, 18, -2, 42, -3, -37, -55, 105, -1, 47, -34, 40,
            127, -71, 31, 58, -57, -104, 33, -51, 60, 9, 26, -55, 68, -48, -36, 66, 96, 42, -113,
            5, 51, -58, 4, -128, -21, -13, -82, 17, 102, 124, 85, 9, -104, -1, -81, 37, -45, 91,
            58, 81, -124, -10, -55, 14, -85, 42, 22, 32, -14, 106, 120, 23, -54, 120, -71, 7, -124,
            82, -113, 10, -15, -76, 11, 114, 111, -76, 3, 84, 36, -44, -36, -86, -35, 94, -26, -61,
            38, 20, 21, 62, -67, -77, 123, -22, 70, -31, -19, -73, 99, -15, -32, 86, 43, -50, -69,
            51, -7, 15, -8, -58, -71, -59, 23, -96, 72, 49, -73, 0, 40, -34, -10, -118, -85, -103,
            15, 58, -48, 5, 113, -75, -68, 109, -48, 15, 12, 2, -39, -52, 106, -77, 10, 116, 45, 52,
            114, -48, -103, -106, 74, -113, -87, 1, -10, 46, 126, 13, 27, 115, 12, -49, -110, -76, 98,
            -50, -104, -8, 42, 1, -111, -26, 1, 53, -100, 9, -123, 101, 58, -105, 37, 60, 50, 23, -76,
            -89, 112, 105, -52, 50, 59, -18, -8, 88, -112, 86, 84, 52, -34, 10, -78, 74, 4, 106, 97, 121,
            -118, 9, -46, -96, 91, 66, 96, -33, 53, -120, 63, 1, -126, -47, 100, -117, 100, -82, -69, 41,
            -108, 47, -109, -101, -7, -68, -29, 29, 90, -54, 107, -72, -69, 29, -108, 71, -123, 91, -34, 95,
            85, -105, 106, -29, 11, -56, -1, -93, -86, -90, -20, 67, -58, 31, -51, -49, 9, 32, -76, -110,
            116, -115, 84, 122, -56, 84, -88, 0, -11, -83, -23, -21, 119, -97, 32, -62, -60, 63, 34, 124,
            -6, -69, 66, 50, 93, -101, -27, 1, 97, -109, 106, -91, 59, -128, -10, -54, -8, 120, -36, 80,
            -58, -47, 86, -2, -76, -79, -32, 120, 112, -122, -26, -70, -115, 66, 1, 83, 0, -48, 61, 15,
            -104, -17, 74, 22, -32, 73, -22, 54, -128, -79, 92, 100, -72, -86, -38, 78, -91, 64, -68, -105,
            55, -9, 51, 27, 15, 54, -105, -46, 111, -114, -66, -90, 41, 119, 123, -75, -87, -78, 87, -30,
            -114, -46, -117, 44, -10, -78, -93, 31, -37, -36, -45, -45, 93, 97, -118, -42, 20, -80, -13,
            -105, -75, 125, -90, 103, 121, -32, -89, -80, 23, -65, -36, -59, -1, -103, -46, 119, -7, -82,
            80, 88, -14, 85, -76, -59, -8, -35, 8, -14, 66, -111, 102, 10, 122, -4, 58, -15, -58, -79, -13,
            -123, -105, -31, 118, -53, 67, -51, -115, -100, -38, -36, -53, 61, -37, 55, 116, -105, -29, 41,
            11, -65, 105, 56, -95, -112, -31, -16, 102, -124, 34, 19, -92, 47, 78, 28, -17, 110, 92, 121,
            -35, 15, 47, -36, 85, -82, 18, 15, -96, 103, 113, 79, -110, 45, -58, 37, -118, -106, -110, -95,
            -2, -81, 27, -96, 118, -102, 26, 59, -85, 50, 112, -2, -61, -8, 17, 36, 11, 5, -15, -83, -63,
            -63, 98, 77, 43, 56, -102, 57, 65, -96, -108, 47, -98, -14, -48, -78, -114, 85, 90, -97, -12,
            -96, -54, 116, -31, 58, -8, 79, 22, -77, 88, -46, 2, -24, -117, -115, 85, -42, 11, 40, 125, -76,
            -63, 1, -128, -115, 3, 82, 36, -14, 88, 6, -97, -77, 70, -9, -39, 27, -38, -70, 103, -80, -114, 15,
            73, 102, -13, -15, -57, 42, 47, -93, 126, 87, 13, -46, 7, 57, -116, 61, 27, 40, 87, -89, 33, -2,
            -62, 77, 19, -26, -66, 102, -44, -125, 110, -80, -32, -83, 105, 7, 62, 13, 78, 73, 30, -74, 69, 3,
            -7, 118, -105, 59, -95, 97, 3, 12, -4, -19, 36, -74, 7, -27, 74, 14, -19, 86, 123, 84, -64, -106, 8,
            112, 73, -8, 111, 80, -50, 33, -69, -45, 35, -13, -20, -76, 63, 7, -72, -125, 113, 28, 98, -30, -120,
            33, -36, -32, 126, -116, -114, 35, 38, -106, -53, -8, -117, 40, 51, 68, 56, 65, -3, -79, -111, 27, 71,
            60, -14, 116, 16, -73, -118, 4, -24, -2, -51, -26, -67, 55, 124, -87, 64, -79, -71, 122, -118, -13, 125,
            -118, 64, 87, -70, -53, 40, 52, 85, -67, -91, -31, 3, -13, -79, 15, 7, 17, 114, 23, 119, -120, 118, -124,
            1, 99, 79, -44, -7, -128, -87, 49, 58, 64, -5, -40, -93, -17, -40, -82, 77, -107, -73, -102, 85, -61, -46,
            -14, 37, 104, 29, 38, 82, -48, 90, 65, 41, -6, -15, -91, -54, 126, -108, -80, 122, 115, 106, -36, -42, -14,
            115, -111, 51, 51, -96, -62, 115, -23, -67, -91, 89, -10, -72, 0, 80, -113, 78, -93, -15, 119, -82, -69, -92,
            -94, 112, -104, 60, -37, 11, 127, 86, 122, 18, 28, 29, 73, 81, -39, -124, 40, 6, 73, -32, -10, -28, -98, 1,
            29, 78, -8, -104, -33, -37, 42, 52, -45, -62, -39, 1, -69, 94, 120, -32, -115, -63, -85, -67, 102, -124, 24,
            126, -21, -40, 3, -70, -9, 78, -115, 107, 30, -16, 121, -109, 29, 89, 99, 98, -46, -103, -112, 124, 8, 40,
            47, -44, 64, -80, -71, 109, -26, 46, 113, -73, 98, 3, 6, -99, 21, 96, -92, 36, -42, 8, -16, 3, -57, -66,
            125, 41, -126, -52, 79, 48, 1, 20, 71, -30, -9, -126, 61, -12, 107, 88, 104, -52, -25, 89, -76, -15, -13,
            7, 108, -69, 47, -125, 111, 27, 97, 92, -71, -75, -16, 11, 35, 18, 26, -128, 55, -124, -84, -90, 116, 22,
            -17, 62, 11, -5, -118, -18, 38, 23, 44, -11, -45, -117, 105, -69, -16, -71, -29, -85, 97, -19, -60, -5,
            -13, -119, -29, 119, 99, 108, -103, -25, -29, 0, 72, 44, 32, -70, -107, -125, -92, -28, -126, -102, 109,
            3, 73, -77, -57, 48, -1, -59, -69, 87, 48, -10, -82, 10, -21, 27, -24, -72, 29, -60, -39, -37, 10, -81,
            115, 20, 66, -7, -20, -53, -33, -114, -121, 117, -2, -72, 109, 96, -25, -99, 27, -29, 103, -50, 37, 0,
            -67, -2, -45, 59, 12, -10, 12, 115, 14, -110, -10, 95, -48, 50, 55, 86, 24, 117, -19, -41, 29, 109, -88,
            -59, 116, -66, 110, 16, -94, -42, -12, 8, -123, -23, 61, -118, 60, -21, 124, -104, -66, 35, 6, -70, -61,
            12, 86, -90, 127, -116, -47, -54, 89, 38, -108};
    public static final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtrLB4PqiWsVNdkEiINPrU/ERjaNHCx2qV6kNKexS9BhcIeMeXPjNr0V/Q4wwMHUn6bRJUyYCAu19FXcWcHix3lATyPsXef8zjprOdbxUMLqiLWdo6VmntjoKnxD+W+6+nqVLK+B+Bk8AGFeik86hFEic9wldX+81strnSrB2Ij43JSg3RX+n16S4tZryd4hcb54PirziRrYWGVrkC9tOaGFXpHmJIALev94lVbHh+IiMSZOoKC0LCcPcLoruOC/J1kDlHo0iB4utWO+O3quX3m+k6/QidMmZb/lByUukv3EtcFqEgGpYC+skeyF2UVHqLuj7KBYoIusYnjNWT8RhmwIDAQAB";
    private static C instance;
    private final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
    public String[] shareViaApps = {"Messaging", "Gmail", /*"WhatsApp",*/ "Hangouts", /*"Messenger",*/ "Snapchat", "Instagram", "hike messenger"/*, "Facebook"*/};
    public int currentDate, currentMonth, currentYear, currentDay;
    public String ID = "_id";
    public String DATE = "_date";
    public String MONTH = "_month";
    public String YEAR = "_year";
    public String DAY = "_day";
    public String APPLICATION = "_apps";
    public String IMAGE = "image";
    public String PRICE = "price";
    public String TITLE = "title";
    public String NAME = "name";
    public String CHIPS = "chips";
    public String SKU_ID = "sku_id";
    public String TAG = "tag";
    public String DISCOUNT = "discount";
    public String BOOT_VALUE = "boot_value";
    public String PLAYED = "played";
    public String WIN = "win";
    public String BIG_WON = "big_won";
    public String DECK = "deck";
    public String CATEGORY = "category";
    public String MODE = "mode";
    public int magiccolleccnt = 0;
    public boolean showMessageOnDashboard = false;
    public int errorCode = 0;
    public boolean showrateDialog = false;
    public boolean exitFromPlayScreen = false;
    public boolean adDisplay;
    public int easygamecounter = 5;
    public int leaderImage[] = {
            R.drawable.u1, R.drawable.u2, R.drawable.u3, R.drawable.u4, R.drawable.u5,
            R.drawable.u6, R.drawable.u7, R.drawable.u8, R.drawable.u9, R.drawable.u10,
            R.drawable.u11, R.drawable.u12, R.drawable.u13, R.drawable.u14, R.drawable.u15,
            R.drawable.u16, R.drawable.u17, R.drawable.u18, R.drawable.u19, R.drawable.u20,
            R.drawable.u21, R.drawable.u22, R.drawable.u23, R.drawable.u24, R.drawable.u25,
            R.drawable.u26, R.drawable.u27, R.drawable.u28, R.drawable.u29, R.drawable.u30,
            R.drawable.u31, R.drawable.u32, R.drawable.u33, R.drawable.u34, R.drawable.u35,
            R.drawable.u36, R.drawable.u37, R.drawable.u38, R.drawable.u39, R.drawable.u40,
            R.drawable.u41, R.drawable.u42, R.drawable.u43, R.drawable.u44, R.drawable.u45,
            R.drawable.u46, R.drawable.u47, R.drawable.u48, R.drawable.u49, R.drawable.u50,
            R.drawable.u51, R.drawable.u52, R.drawable.u53, R.drawable.u54, R.drawable.u55,
            R.drawable.u56, R.drawable.u57, R.drawable.u58, R.drawable.u59, R.drawable.u60,
            R.drawable.u61, R.drawable.u62, R.drawable.u63, R.drawable.u64, R.drawable.u65,
            R.drawable.u66, R.drawable.u67, R.drawable.u68, R.drawable.u69, R.drawable.u70,
            R.drawable.u71, R.drawable.u72, R.drawable.u73, R.drawable.u74, R.drawable.u75,
            R.drawable.u76, R.drawable.u77, R.drawable.u78, R.drawable.u79, R.drawable.u80,
            R.drawable.u81, R.drawable.u82, R.drawable.u83, R.drawable.u84, R.drawable.u85,
            R.drawable.u86, R.drawable.u87, R.drawable.u88, R.drawable.u89, R.drawable.u90,
            R.drawable.u91, R.drawable.u92, R.drawable.u93, R.drawable.u94, R.drawable.u95,
            R.drawable.u96, R.drawable.u97, R.drawable.u98, R.drawable.u99, R.drawable.u100,
            R.drawable.u101, R.drawable.u102, R.drawable.u103, R.drawable.u104,
            R.drawable.u105,
            R.drawable.u106, R.drawable.u107, R.drawable.u108, R.drawable.u109,
            R.drawable.u110,
            R.drawable.u111, R.drawable.u112, R.drawable.u113, R.drawable.u114,
            R.drawable.u115,
            R.drawable.u116, R.drawable.u117, R.drawable.u118, R.drawable.u118
    };
    public boolean isSwitchingTable = false;
    public boolean isCloseLoaderOnDashBoardOnResume = false;
    public boolean isShowTutorial = false;
    public String SUPER_SONIC_APPLICATION_KEY = "5806f21d"; // Gin Rummy Offline
    public boolean exitfromplaying = false;
    public int initialBootValue;
    public int initialRoundPoint = 50;
    public Typeface typeface;
    public int width, height;
    public String themeId = null;
    public boolean isGined = false;
    public boolean isKnocked = false;
    public int SeatIndexOfUser = -1;
    public boolean isPlayingScreenOpen;
    public String version;
    public int DbcollectCount;
    public long ChipBeforePlay;
    public String myAdUnitId;
    public int Shareclickintime;
    public int hours, minutes, seconds;
    public long remainTime;
    public boolean isDisplaySecondBox = false;
    public int tableindex;
    public boolean isShowHourlyBonus;
    public String httpresp = "";
    public int gameType = 0;
    public boolean isDouble;
    public int multi = 1;
    public int lostCount = 0;
    public Context con;
    public AdRequest mRequest;
    //    public boolean is_Ads_Show_After_Two_Day;
    // aa bhai shu kaam lidhu chhe? code karvo to dil thi karo patiya na maaaro
    public Handler payment_Handler;
    public Activity activity;
    public int gameCount;
    public long BonusmilliLeft = 0;
    public boolean isBonusTimerStart;
    public int lostChip = 0;
    public boolean isRewardCredit = true;
    public boolean isRewardClosedCalled = false;
    //==============================================================================================
    public String unique_id, deviceinfo;
    public IronSource mMediationAgent;
    public Placement mPlacement;
    public Context contex;
    public NumberFormat formatter = new DecimalFormat(
            "###,###,###,###,###,###,###,###,###,###,###,###,###");
    // mini games and coinstore
    public NumberFormat formatter1 = new DecimalFormat(
            "##,##,##,##,##,##,##,##,##,##,##,##,###");
    public long Chips;
    public boolean isAutoSortOn = false;
    public int NotificationCount = 0;
    public int MessageCount = 0;
    public int Laal = 0, Kaali = 1, Chirkut = 2, Falli = 3;
    public int ROUND_START_TIMER;
    public int SLOW_TABLE_TIMER = 30;
    public int ERROR_CODE;
    //============================ Hourlym Bonus ==================================================
    Dialog ConfirmationDialogCoinStore = null;
    private int width_ratio, height_ratio;
    private Dialog ConfirmationDialogInfo;
    OfferwallListener mOfferwallListener = new OfferwallListener() {
        /**
         * Invoked when the Offerwall is prepared and ready to be shown to the
         * user
         */
        public void onOfferwallInitSuccess() {
            //	Toast.makeText(contex,"11111111",Toast.LENGTH_LONG).show();
            Logger.print("11111111111>>>>>>>onOfferwallInitSuccess()");
            Logger.print("OFFERWALL >>>> onOfferwallInitSuccess");
        }

        /**
         * Invoked when the Offerwall does not load
         */
//        public void onOfferwallInitFail(SupersonicError error) {
//            //	Toast.makeText(contex,"2222222 "+error,Toast.LENGTH_LONG).show();
//            Logger.print("22222222222>>>>>>>onOfferwallInitFail()");
//            Logger.print("22222222222>>>>>>>error>>>> "+error );
//        }
        @Override
        public void onOfferwallAvailable(boolean b) {
            Logger.print("OFFERWALL >>>> onOfferwallAvailable " + b);
        }

        /**
         * Invoked when the Offerwall successfully loads for the user, after
         * calling the 'showOfferwall' method
         */
        public void onOfferwallOpened() {
            //	Toast.makeText(contex,"3333333",Toast.LENGTH_LONG).show();
            Logger.print("3333333333>>>>>>>onOfferwallOpened()");
            Logger.print("OFFERWALL >>>> onOfferwallOpened");
        }

        @Override
        public void onOfferwallShowFailed(IronSourceError ironSourceError) {
            Logger.print("OFFERWALL >>>> onOfferwallShowFailed " + ironSourceError.getErrorMessage() + " : " + ironSourceError.getErrorCode());
        }

        /**
         * Invoked when the method 'showOfferWall' is called and the OfferWall
         * fails to load. //@param supersonicError - A SupersonicError Object
         * which represents the reason of 'showOfferwall' failure.
         */
//        public void onOfferwallShowFail(SupersonicError supersonicError) {
//            //	Toast.makeText(contex,"4444444 "+supersonicError,Toast.LENGTH_LONG).show();
//            Logger.print("44444444444>>>>>>>onOfferwallShowFail()");
//            Logger.print("44444444444>>>>>>>supersonicError>>>> "+supersonicError );
//        }

        /**
         * Invoked each time the user completes an Offer. Award the user with
         * the credit amount corresponding to the value of the �credits�
         * parameter.
         *
         * @param credits
         *            - The number of credits the user has earned.
         * @param totalCredits
         *            - The total number of credits ever earned by the user.
         * @param totalCreditsFlag
         *            - In some cases, we won�t be able to provide the exact
         *            amount of credits since the last event (specifically if
         *            the user clears the app�s data). In this case the
         *            �credits� will be equal to the �totalCredits�, and this
         *            flag will be �true�.
         * @return boolean - true if you received the callback and rewarded the
         *         user, otherwise false.
         */
        public boolean onOfferwallAdCredited(int credits, int totalCredits, boolean totalCreditsFlag) {

            //Toast.makeText(contex,"6666666 "+ totalCredits+" "+ credits+" "+totalCreditsFlag,Toast.LENGTH_LONG).show();
            Logger.print("55555555555>>>>>>>onOfferwallAdCredited()");
            Logger.print("55555555555>>>>>>> credits>>>>>>>>>>>>>> " + credits);
            Logger.print("55555555555>>>>>>> totalCredits>>>>>>>>> " + totalCredits);
            Logger.print("55555555555>>>>>>> totalCreditsFlag>>>>> " + totalCreditsFlag);
            int winchip;
            if (totalCreditsFlag) {
                //if(credits==totalCredits){
//					int oldearn=PreferenceManager.getEarnchip();
//					int addcredit=credits-oldearn;
//					winchip=addcredit;
//					PreferenceManager.setEarnchip(credits);
//					PreferenceManager.setChips(PreferenceManager.getChips()+addcredit);
//					Chips=PreferenceManager.getChips();
//
//				AlertForAutoLeaveTable("Reward",winchip+" Chips Added");

            } else {
                int oldearn = PreferenceManager.getEarnchip();
                if (oldearn != credits) {
                    winchip = credits - oldearn;
                    PreferenceManager.setEarnchip(credits);
                    PreferenceManager.setChips(PreferenceManager.getChips() + winchip);
                    Chips = PreferenceManager.getChips();
                    showInfoDialog("Reward", winchip + " Chips Added");

                    //====================== Quest Counter ====================================
                    PreferenceManager.SetQuestOfferWallCount(PreferenceManager.GetQuestOfferwallCount() + 1);
                    Logger.print("MMMMMMMMM QUEST Counter>>> Offerwall increment >>> " + PreferenceManager.GetQuestOfferwallCount());
                    //=========================================================================

                } else {
                    Logger.print("55555555555>>>>>>>>>>>>No credits was added");
                }


            }

            //   showInfoDialog("Reward",/*winchip+*/" Chips Added");

            return totalCreditsFlag;
        }

        @Override
        public void onGetOfferwallCreditsFailed(IronSourceError ironSourceError) {
            Logger.print("OFFERWALL >>>> onGetOfferwallCreditsFailed");

        }

        /**
         * Invoked when the method 'getOfferWallCredits' fails to retrieve the
         * user's credit balance info.
         *
         * @param supersonicError
         *            - A SupersonicError object which represents the reason of
         *            'getOffereallCredits' failure.
         */
//        public void onGetOfferwallCreditsFail(SupersonicError supersonicError) {
//            //Toast.makeText(contex,"7777777 "+ supersonicError,Toast.LENGTH_LONG).show();
//            Logger.print("66666666666>>>>>>>onGetOfferwallCreditsFail()");
//            Logger.print("66666666666>>>>>>>supersonicError>>>> "+supersonicError);
//
//        }

        /**
         * Invoked when the user is about to return to the application after
         * closing the Offerwall.
         */
        public void onOfferwallClosed() {
            //Toast.makeText(contex,"88888888",Toast.LENGTH_LONG).show();
            Logger.print("11111111111>>>>>>>onOfferwallClosed()");
            Logger.print("OFFERWALL >>>> onOfferwallClosed");

        }
    };

    private C() {
        // Constructor hidden because this is a singleton
    }

    public static void initInstance() {

        if (instance == null) {
            instance = new C();
        }
    }

    public static C getInstance() {
        // Return the instance

        if (instance == null) {
            initInstance();
        }
        return instance;

    }

    public static final void showDialog(Dialog d) {
        try {
            Context context = ((ContextWrapper) d.getContext())
                    .getBaseContext();
            Logger.print("_ERROR_DIALOG 11 : ");

            // if the Context used here was an activity AND it hasn't been
            // finished or destroyed
            // then dismiss it
            if (context instanceof Activity) {

                // Api >=17
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    try {
                        Logger.print("_ERROR_DIALOG c1 : "
                                + (!((Activity) context).isFinishing()));
                        Logger.print("_ERROR_DIALOG c2 : "
                                + (!((Activity) context).isDestroyed()));
                        Logger.print("_ERROR_DIALOG c2 : " + (!d.isShowing()));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!((Activity) context).isFinishing()
                            && !((Activity) context).isDestroyed()
                            && !d.isShowing()) {
                        try {
                            Logger.print("_ERROR_DIALOG 12 : ");
                            d.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Logger.print("_ERROR_DIALOG 22 : ");
                    }
                } else {
                    // Api < 17. Unfortunately cannot check for isDestroyed()
                    if (!((Activity) context).isFinishing() && !d.isShowing()) {
                        try {
                            Logger.print("_ERROR_DIALOG 13 : ");
                            d.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Logger.print("_ERROR_DIALOG 23 : ");
                    }
                }
            } else {
                // if the Context used wasn't an Activity, then dismiss it too
                try {
                    if (!d.isShowing()) {
                        Logger.print("_ERROR_DIALOG 14 : ");
                        d.show();
                    } else {
                        Logger.print("_ERROR_DIALOG 24 : ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void dismissDialog(Dialog d) {
        try {
            Context context = ((ContextWrapper) d.getContext())
                    .getBaseContext();

            // if the Context used here was an activity AND it hasn't been
            // finished or destroyed
            // then dismiss it
            if (context instanceof Activity) {

                // Api >=17
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!((Activity) context).isFinishing()
                            && !((Activity) context).isDestroyed()
                            && d.isShowing()) {
                        try {
                            d.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // Api < 17. Unfortunately cannot check for isDestroyed()
                    if (!((Activity) context).isFinishing() && d.isShowing()) {
                        try {
                            d.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                // if the Context used wasn't an Activity, then dismiss it too
                try {
                    if (d.isShowing()) {
                        d.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message msg = new Message();
            msg.what = ResponseCodes.TryCatch;

            if (DailyBonus3.handler != null) {
                DailyBonus3.handler.sendMessage(msg);
            }
        }
    }




    public void ShowInfoDialog(final String Message, final String Tittle) {


        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                LinearLayout.LayoutParams lin = null;
                if (ConfirmationDialogCoinStore != null || (ConfirmationDialogCoinStore != null && ConfirmationDialogCoinStore.isShowing())) {
                    if (ConfirmationDialogCoinStore.isShowing()) {
                        ConfirmationDialogCoinStore.dismiss();
                    }
                    ConfirmationDialogCoinStore = null;
                }

                ConfirmationDialogCoinStore = new Dialog(con,
                        R.style.Theme_Transparent);
                ConfirmationDialogCoinStore
                        .requestWindowFeature(Window.FEATURE_NO_TITLE);
                ConfirmationDialogCoinStore.setContentView(R.layout.dialog);

                TextView title = (TextView) ConfirmationDialogCoinStore
                        .findViewById(R.id.tvTitle);
                title.setPadding(getwidth(20), getheight(20), getwidth(20),
                        getheight(20));
                title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
                title.setTypeface(typeface);
                title.setText(Tittle);

                lin = (LinearLayout.LayoutParams) ConfirmationDialogCoinStore
                        .findViewById(R.id.line).getLayoutParams();
                lin.height = getheight(4);
                lin.width = getwidth(600);

                TextView message1 = (TextView) ConfirmationDialogCoinStore
                        .findViewById(R.id.tvMessage);
                message1.setTypeface(typeface);
                message1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getwidth(32));

                Button btnOne = (Button) ConfirmationDialogCoinStore
                        .findViewById(R.id.button1);
                btnOne.setTypeface(typeface);
                btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
                btnOne.setText("Ok");

                Button btnTwo = (Button) ConfirmationDialogCoinStore
                        .findViewById(R.id.button2);
                btnTwo.setVisibility(View.GONE);

                lin = (LinearLayout.LayoutParams) ConfirmationDialogCoinStore
                        .findViewById(R.id.button1).getLayoutParams();
                lin.height = getheight(60);
                lin.width = getwidth(120);

                lin = (LinearLayout.LayoutParams) ConfirmationDialogCoinStore
                        .findViewById(R.id.tvMessage).getLayoutParams();
                lin.topMargin = getheight(30);

                lin = (LinearLayout.LayoutParams) ConfirmationDialogCoinStore
                        .findViewById(R.id.llButtons).getLayoutParams();
                lin.topMargin = getheight(30);

                lin = (LinearLayout.LayoutParams) ConfirmationDialogCoinStore
                        .findViewById(R.id.button2).getLayoutParams();
                lin.height = getheight(60);
                lin.width = getwidth(120);

                message1.setText(Message);

                btnOne.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Music_Manager.buttonclick();
                        adDisplay = false;
                        ConfirmationDialogCoinStore.dismiss();
                    }

                });

                if (!activity.isFinishing()) {
                    ConfirmationDialogCoinStore.show();
                }
            }
        });

    }

    public String random() {
        final int sizeOfRandomString = 15;
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public float getSize(float value) {

        if (width == 0) {
            width = 1280;
        }

        float size = (width * value) / 1280;
        return size;
    }

    public void supersonicoffer(Context c, Dashboard dashboard) {
        try {
            mMediationAgent.shouldTrackNetworkState(contex, true);
            //Set the Offerwall Listener
            mMediationAgent.setOfferwallListener(mOfferwallListener);

            //Set the unique id of your end user.
            String mUserId = PreferenceManager.get_id();
            Logger.print(">>>>>>>>>>>>>UserId::::: " + mUserId);
            //Set the Application Key - can be retrieved from Supersonic platform
            String mAppKey = SUPER_SONIC_APPLICATION_KEY;

            //You can set Optional Custom parameters which will be passed to
            //your server if you use server-to-server callbacks.
            Map<String, String> owParams = new HashMap<String, String>();
            owParams.put("userType", "gamer");
            SupersonicConfig.getConfigObj().setOfferwallCustomParams(owParams);

            //You can set optional parameters as well via the .getConfigObj
            SupersonicConfig.getConfigObj().setClientSideCallbacks(true);

            //Init Offerwall
            mMediationAgent.init(dashboard, SUPER_SONIC_APPLICATION_KEY, IronSource.AD_UNIT.OFFERWALL);

            mMediationAgent.setLogListener(new LogListener() {
                @Override
                public void onLog(IronSourceLogger.IronSourceTag ironSourceTag, String s, int i) {
                    Logger.print("tag >>>>>>>" + ironSourceTag);
                    Logger.print("message >>>" + s);
                    Logger.print("logLevel >>" + i);
                }


            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showInfoDialog(String DialogTitle, String Message) {
        // TODO Auto-generated method stub

        LinearLayout.LayoutParams lin;

        if (ConfirmationDialogInfo != null || (ConfirmationDialogInfo != null && ConfirmationDialogInfo.isShowing())) {
            if (ConfirmationDialogInfo.isShowing()) {
                ConfirmationDialogInfo.dismiss();
            }
            ConfirmationDialogInfo = null;
        }

        ConfirmationDialogInfo = new Dialog(contex,
                R.style.Theme_Transparent);
        ConfirmationDialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialogInfo.setContentView(R.layout.dialog);

        TextView title = (TextView) ConfirmationDialogInfo
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

        TextView message = (TextView) ConfirmationDialogInfo
                .findViewById(R.id.tvMessage);
        message.setTypeface(typeface);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

        Button btnOne = (Button) ConfirmationDialogInfo.findViewById(R.id.button1);
        btnOne.setTypeface(typeface);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText("Ok");

        Button btnTwo = (Button) ConfirmationDialogInfo.findViewById(R.id.button2);
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

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                ConfirmationDialogInfo.dismiss();
            }
        });
        ConfirmationDialogInfo.show();
//        if (!isFinishing())
//            ConfirmationDialogInfo.show();

    }

   /* public int getheight() {
        return Screen_Hight;
    }

    public int getwidth() {
        return Screen_Width;
    }*/

    private int getheight(int val) {
        // TODO Auto-generated method stub
        return height * val / 720;
    }

    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return width * val / 1280;
    }

    public int getwidth2(int val, int widht) {
        return (widht * val) / 1280;
    }

    public int getHight(int val, int screen_hight) {
        return (screen_hight * val) / 720;
    }

//    public InterstitialAd mInterstitial;
//
//    public void showAds(Context context) {
//        Logger.print("GETTTTTT ADDDDDDDDDDDDDDDDD Winner class C class");
//        try {
//
//            mInterstitial = new InterstitialAd(context);
//            mInterstitial.setAdUnitId(context.getResources().getString(R.string.interstitial_ads_id));
//            AdRequest adRequest = new AdRequest.Builder()
//                    .build();
//
//            mInterstitial.loadAd(adRequest);
//
//            mInterstitial.setAdListener(new AdListener() {
//                @Override
//                public void onAdClosed() {
//
//
//                    requestNewInterstitial();
//
//
//                }
//            });
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//    }
//    public void requestNewInterstitial() {
//
//        try {
//
//
//            if (mInterstitial != null) {
//                AdRequest adRequest = new AdRequest.Builder()
//                        //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                        .build();
//
//                mInterstitial.loadAd(adRequest);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//    public InterstitialAd mInterstitial2;
//
//    public void showAds2(Context context) {
//        Logger.print("GETTTTTT ADDDDDDDDDDDDDDDDD Winner class C class2");
//        try {
//
//            mInterstitial2 = new InterstitialAd(context);
//            mInterstitial2.setAdUnitId(context.getResources().getString(R.string.interstitial_ads_id));
//            AdRequest adRequest = new AdRequest.Builder()
//                    .build();
//
//            mInterstitial2.loadAd(adRequest);
//
//            mInterstitial2.setAdListener(new AdListener() {
//                @Override
//                public void onAdClosed() {
//
//
//                    requestNewInterstitial2();
//
//
//                }
//            });
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//    }
//    public void requestNewInterstitial2() {
//
//        try {
//            if (mInterstitial2 != null) {
//                AdRequest adRequest = new AdRequest.Builder()
//                        //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                        .build();
//
//                mInterstitial2.loadAd(adRequest);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public int getheight1(int val) {
        return height * val / 720;
    }

    public int getwidth1(int val) {
        return width * val / 1280;
    }

    public String getNumberFormatedValue(long val) {

        String returnVal = "";

        if (val < 1000000) {
            returnVal = formatter.format(val);
        } else {
            returnVal = numDifferentiation(val);
        }

        return returnVal;
    }

    public String numDifferentiation(long val) {
        try {
            String returnVal = "";

            double d;
            String iteration;

            if (val >= 1000000000000L) {
                d = (double) val / 1000000000000L;
                iteration = "T";
            } else if (val >= 1000000000) {
                d = (double) val / 1000000000;
                iteration = "B";
            } else if (val >= 1000000) {
                d = (double) val / 1000000;
                iteration = "M";
            } else {
                return formatter.format(val);
            }

            boolean isRound = (d * 10) % 10 == 0;
            if (isRound)
                returnVal = (double) (d * 10 / 10) + "" + iteration;
            else
                returnVal = RoundTo2Decimals(d) + "" + iteration;

            return returnVal;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return String.valueOf(val);
        }
    }

    public double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }

    public String getTimeStamp(long millis) {
        Date date = new Date(millis);
        DateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    public int getCardSuit(String value) {

        if (value.equalsIgnoreCase("f")) {
            return Falli;
        } else if (value.equalsIgnoreCase("l")) {
            return Laal;
        } else if (value.equalsIgnoreCase("c")) {
            return Chirkut;
        } else {
            return Kaali;
        }

    }

    public void hideSystemUI(View v) {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        if (AndroidVersion.isKitKat()) {
            v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide
                            // nav
                            // bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
            /* | View.SYSTEM_UI_FLAG_IMMERSIVE */);
        }
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


}
