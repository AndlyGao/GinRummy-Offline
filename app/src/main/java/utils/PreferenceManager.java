package utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.artoon.ginrummyoffline.Dashboard;
import com.artoon.ginrummyoffline.R;

import java.net.InetAddress;


@SuppressWarnings("all")
public class PreferenceManager extends MultiDexApplication {

    protected static final boolean LocalFirst = true;
    public static String _id = "", username = "", password = "", email = "",
            fb_id = "", fb_accessToken = "";
    public static boolean CONFIRM = false, GotRewards = false,
            showOptionYourTurn = true, showOptionTheirTurn = true;
    public static boolean isconnect = true;
    public static Context Pref_Context;
    public static boolean isPlayingScreenOpen = false;
    public static String APP_ID = "737379479670473";
    public static String APP_NAMESPACE = "Gin Rummy Offline";
    public static String SENDASK_ObjectID = "737047783046968";

    // Live- 737379479670473
    // Dev- 741305969277824

    // public static String APP_ID = "649247345196189";
    static SharedPreferences preferences;
    static SharedPreferences.Editor prefEditor;
    static InetAddress inet;

    // public static String SENDASK_ObjectID = "911606295556669";
    // mini games and coin store
    public static void Setisfree(String val) {
        // TODO Auto-generated method stub
        prefEditor.putString("isfree", val);
        prefEditor.commit();
    }

    public static String getisfree() {
        // TODO Auto-generated method stub
        return preferences.getString("isfree", "");
    }

	/*public static Permission[] permissions = new Permission[] {
            Permission.PUBLIC_PROFILE, Permission.PUBLISH_ACTION,
			Permission.EMAIL, Permission.USER_FRIENDS };*/

    // public boolean isFirst=false;

    public static String getPrevTableId() {
        String tableId = preferences.getString("PrevTableId", "");
        return tableId;
    }


    public static String getRegistrationId() {
        return preferences.getString("registrationId", "");
    }

    public static void setRegistrationId(String registrationid) {
        prefEditor.putString("registrationId", registrationid).commit();
    }

    public static int getEarnchip() {
        return preferences.getInt("Earnchip", 0);
    }

    public static void setEarnchip(int counter) {
        prefEditor.putInt("Earnchip", counter).commit();
    }

    public static String get_picPath() {
        return preferences.getString("pickPathc", "");
    }

    public static void set_Picpath(String path) {
        Logger.print("PPPPPPPPP>>> PATH PREFRENCE " + path);
        prefEditor.putString("pickPathc", path).commit();
    }

    public static boolean getisshowAd() {
        return preferences.getBoolean("Add", true);
    }

    public static void setisshowAd(boolean b) {
        prefEditor.putBoolean("Add", b).commit();
    }

    public static boolean getisAutosort() {
        return preferences.getBoolean("Autosort", false);
    }

    public static void setisAutosort(boolean b) {
        prefEditor.putBoolean("Autosort", b).commit();
    }


    public static boolean getremoveAdPurchased() {
        return preferences.getBoolean("Ad", false);
    }

    public static void SetremoveAdPurchased(boolean b) {

        prefEditor.putBoolean("Ad", b).commit();

    }

    public static String getUniqueId() {
        return preferences.getString("uniqueId", "");
    }

    public static void setUniqueId(String uniqueid) {
        prefEditor.putString("uniqueId", uniqueid).commit();
    }

    public static String getMacId() {
        return preferences.getString("macId", "");
    }

    public static void setMacId(String macid) {
        prefEditor.putString("macId", macid).commit();
    }

    public static int getOpenCounter() {
        return preferences.getInt("openCounter", 0);
    }

    public static void setOpenCounter(int counter) {
        prefEditor.putInt("openCounter", counter).commit();
    }

    public static int getDBVersion() {
        return preferences.getInt("dbVersion", 0);
    }

    public static void setDBVersion(int counter) {
        prefEditor.putInt("dbVersion", counter).commit();
    }

    public static boolean getFlagForAdOnSpecialOffer() {
        return preferences.getBoolean("FlagForAdOnSpecialOffer", false);
    }

    public static void setFlagForAdOnSpecialOffer(boolean b) {
        prefEditor.putBoolean("FlagForAdOnSpecialOffer", b);
        prefEditor.commit();
    }

    public static boolean getTimerBonusCollect() {
        return preferences.getBoolean("TimerBonus", true);
    }

    public static void setTimerBonusCollect(boolean iscollected) {
        prefEditor.putBoolean("TimerBonus", iscollected);
        prefEditor.commit();
    }

    // public static int getDivisor() {
    // return preferences.getInt("Divisor", 14);
    // }
    //
    // public static void setDivisor(int divisor) {
    // prefEditor.putInt("Divisor", divisor).commit();
    // }

    public static long getChips() {

        return preferences.getLong("chips", 15000);
    }

    public static void setChips(long chips1) {
        prefEditor.putLong("chips", chips1).commit();
    }


    public static void setprofilepic(int pic) {
        prefEditor.putInt("profilepic", pic).commit();
    }

    public static int getprofilepic() {
        return preferences.getInt("profilepic", R.drawable.me);
    }

    public static boolean getMute() {
        return preferences.getBoolean("Mute", false);
    }

    public static void setMute(boolean b) {
        prefEditor.putBoolean("Mute", b).commit();
    }

    public static boolean getControllVisibility() {
        return preferences.getBoolean("Controlls", true);
    }

    public static void setControllVisibility(boolean b) {
        prefEditor.putBoolean("Controlls", b).commit();
    }

    public static boolean isDirectoryDeleted() {
        if (preferences != null) {
            return preferences.getBoolean("dirdelete", false);
        }
        return false;
    }

    public static void setDirectoryDeleted(boolean b) {
        prefEditor.putBoolean("dirdelete", b).commit();
    }

    public static boolean getNotification() {
        return preferences.getBoolean("Notification", true);
    }

    public static void setNotification(boolean b) {
        prefEditor.putBoolean("Notification", b).commit();
    }

    public static boolean getVibrate() {
        return preferences.getBoolean("Vibrate", false);
    }

    public static void setVibrate(boolean b) {
        prefEditor.putBoolean("Vibrate", b).commit();
    }

    public static boolean getPopupShown() {
        return preferences.getBoolean("popupshown", false);
    }

    public static void setPopupShown(boolean b) {
        prefEditor.putBoolean("popupshown", b).commit();
    }

    public static int getNumberOfPlayers() {
        return preferences.getInt("players", 2);
    }

    public static void setNumberOfPlayers(int b) {
        prefEditor.putInt("players", b).commit();
    }

    public static int getgameType() {
        return preferences.getInt("typeGame", 0);
    }

    public static void setgameType(int b) {
        prefEditor.putInt("typeGame", b).commit();
    }

    public static String getLastDiscardedVersion() {
        String version = "";
        try {
            PackageManager manager = Pref_Context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    Pref_Context.getPackageName(), 0);
            version = info.versionName;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return preferences.getString("LastDiscardedVersion", version);
    }

    public static void setLastDiscardedVersion(String version) {
        prefEditor.putString("LastDiscardedVersion", version).commit();
    }

    public static boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
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

    public static Context getContext() {
        return Pref_Context;
    }

    public static String get_id() {
        return preferences.getString(Parameters.User_Id, "6005");
    }

    public static void set_id(String _id) {
//        PreferenceManager._id = _id;
        prefEditor.putString(Parameters.User_Id, _id).commit();
    }

    public static String get_FbId() {
        return preferences.getString(Parameters.FB_Id, "");
    }

    public static void set_FbId(String FB_Id) {
        fb_id = FB_Id;
        prefEditor.putString(Parameters.FB_Id, FB_Id).commit();
    }

    public static float getOldVersion() {
        return preferences.getFloat(Parameters.oldVersion, -1.0f);
    }

    public static void setOldVersion(float version) {

        prefEditor.putFloat(Parameters.oldVersion, version).commit();
    }

    public static int getOpenCounter1() {
        return preferences.getInt("openCounter", 0);
    }

    public static void setOpenCounter1(int counter) {
        prefEditor.putInt("openCounter", counter).commit();
    }

    public static String get_FB_accessToken() {
        return preferences.getString(Parameters.FB_Token, "");
    }

    public static void set_FB_accessToken(String FB_accessToken) {
        fb_accessToken = FB_accessToken;
        prefEditor.putString(Parameters.FB_Token, FB_accessToken).commit();
    }

    public static String getUserName() {
        return preferences.getString(Parameters.User_Name, "Guest");
    }

    public static void setUserName(String username) {
        PreferenceManager.username = username;
        prefEditor.putString(Parameters.User_Name, username).commit();
    }

    public static String getGuestUserName() {
        return preferences.getString("GuestUserName", "");
    }

    public static void setGuestUserName(String username) {
        prefEditor.putString("GuestUserName", username).commit();
    }

    public static String getUserEmail() {
        return preferences.getString(Parameters.User_Email, "");
    }

    public static void setUserEmail(String email) {
        PreferenceManager.email = email;
        prefEditor.putString(Parameters.User_Email, email).commit();
    }

    public static void setFull() {
        prefEditor.putBoolean("FULL", true);
        prefEditor.commit();
    }

    public static Boolean isFull() {
        return preferences.getBoolean("FULL", false);
    }

    public static void disableFull() {
        prefEditor.putBoolean("FULL", false);
        prefEditor.commit();
    }

    public static void SetDBcollected(boolean status) {
        prefEditor.putBoolean("SetDBcollected", status).commit();
    }

    public static boolean GetDBcollected() {
        return preferences.getBoolean("SetDBcollected", false);
    }


    public static void SetOldVersion(String version) {
        prefEditor.putString("oldVersion", version).commit();
    }

    public static String GetOldVersion() {
        return preferences.getString("oldVersion", "0");
    }

    public static int GetDate() {
        return preferences.getInt("DATE", 0);
    }

    public static void SetDate(int date) {
        prefEditor.putInt("DATE", date).commit();
    }

    public static String get_UserLoginType() {
        // TODO Auto-generated method stub
        return preferences.getString(Parameters.User_LoginType,
                Parameters.Guest);
    }

    public static void set_UserLoginType(String string) {
        // TODO Auto-generated method stub
        prefEditor.putString(Parameters.User_LoginType, string);
        prefEditor.commit();
    }

    public static int GetDBDate() {
        return preferences.getInt("DB_DATE", 0);
    }

    public static int GetDBCollectCount() {
        return preferences.getInt("DB_COUNT", 0);
    }

    public static void SetDBCollectDay(int day) {
        prefEditor.putInt("DB_COLLECT_DAY", day).commit();
    }

    public static int GetDBCollectDay() {
        return preferences.getInt("DB_COLLECT_DAY", 0);
    }

    public static void SetInstallday(int day) {
        prefEditor.putInt("INSTALL_DAY", day).commit();
    }

    public static int GetInstallday() {
        return preferences.getInt("INSTALL_DAY", 0);
    }

    public static void SetInstallyear(int day) {
        prefEditor.putInt("INSTALL_YEAR", day).commit();
    }

    public static int GetInstallyear() {
        return preferences.getInt("INSTALL_YEAR", 0);
    }

    public static void SetDBCollectCount(int date) {
        prefEditor.putInt("DB_COUNT", date).commit();
    }

    public static void SetSmartCount(int date) {
        prefEditor.putInt("SmartCount", date).commit();
    }

    public static int GetSmartCount() {
        return preferences.getInt("SmartCount", 0);
    }

    public static void SetDBDate(int date) {
        prefEditor.putInt("DB_DATE", date).commit();
    }

    public static int GetPendingChips() {
        return preferences.getInt("PendingChips", 0);
    }

    public static void SetPendingChips(int date) {
        prefEditor.putInt("PendingChips", date).commit();
    }

    public static Boolean get_FirstTime() {
        // TODO Auto-generated method stub
        return preferences.getBoolean(Parameters.isFirstTime, true);
    }

    public static void set_FirstTime(Boolean b) {
        // TODO Auto-generated method stub

        prefEditor.putBoolean(Parameters.isFirstTime, b);
        prefEditor.commit();

    }

    public static Boolean get_Google_Sing_in() {
        // TODO Auto-generated method stub
        return preferences.getBoolean(Parameters.Google_Sing_in, true);
    }

    public static void set_Google_Sing_in(Boolean b) {
        // TODO Auto-generated method stub

        prefEditor.putBoolean(Parameters.Google_Sing_in, b);
        prefEditor.commit();

    }

    public static boolean getChallengePopup() {
        return preferences.getBoolean("ChallengePopup", true);
    }

    public static void setChallengePopup(boolean status) {
        prefEditor.putBoolean("ChallengePopup", status).commit();
    }

    public static int getRateAt() {
        return preferences.getInt(Parameters.rateLevel, -1);
    }

    public static void setRateAt(int level) {
        prefEditor.putInt(Parameters.rateLevel, level);
        prefEditor.commit();
    }

    public static String get_ReferenceCode() {
        // TODO Auto-generated method stub
        return preferences.getString(Parameters.ReferrerCode, "");
    }

    public static void set_ReferenceCode(String b) {
        // TODO Auto-generated method stub
        prefEditor.putString(Parameters.ReferrerCode, b);
        prefEditor.commit();
    }

    public static String get_ReferenceLink() {
        // TODO Auto-generated method stub
        return preferences.getString(Parameters.rfl, "");
    }

    public static void set_ReferenceLink(String b) {
        // TODO Auto-generated method stub
        prefEditor.putString(Parameters.rfl, b);
        prefEditor.commit();
    }

    public static boolean getRategiven() {
        return preferences.getBoolean("RateGive", false);
    }

    public static void setRategiven(boolean b) {
        prefEditor.putBoolean("RateGive", b).commit();
    }

    public static int getLounchCount() {
        return preferences.getInt("count", 0);
    }

    public static void setLounchCount(int b) {
        prefEditor.putInt("count", b).commit();
    }

    public static boolean getisFirstbonusCollect() {
        return preferences.getBoolean("FIRSTBONUSSS", true);
    }

    public static void setisFirstbonusCollect(boolean b) {
        prefEditor.putBoolean("FIRSTBONUSSS", b).commit();
    }

    public static boolean getisSecondbonusCollect() {
        return preferences.getBoolean("SECONDBONUSSS", true);
    }

    public static void setisSecondbonusCollect(boolean b) {
        prefEditor.putBoolean("SECONDMAGICBOX", b).commit();
    }

    public static boolean getisSecondMagicBoxDisplay() {
        return preferences.getBoolean("SECONDMAGICBOX", true);
    }

    public static void setisSecondMagicBoxDisplay(boolean b) {
        prefEditor.putBoolean("SECONDBONUSSS", b).commit();
    }

    public static void SetQuestGinCount(int count) {
        prefEditor.putInt("quest_gin", count).commit();
    }

    public static int GetQuestGinCount() {
        return preferences.getInt("quest_gin", 0);
    }

    public static void SetQuestGinTodayCount(int count) {
        prefEditor.putInt("quest_gin_today", count).commit();
    }


//    public static void SetShareCount(int count) {
//        prefEditor.putInt("ShareCount", count).commit();
//    }
//
//
//    public static int GetShareCount() {
//        return preferences.getInt("ShareCount", 0);
//    }


    //=====================================Quest Data ============================================


    //=============================== Gin =========================================================

    public static int GetQuestGinTodayCount() {
        return preferences.getInt("quest_gin_today", 0);
    }

    public static void SetQuestGinClaimCountToday(int count) {
        prefEditor.putInt("quest_gin_calim_today", count).commit();
    }

    public static int GetQuestGinClaimCountToday() {
        return preferences.getInt("quest_gin_calim_today", 0);
    }

    public static void SetQuestGinClaimCountLife(int count) {
        prefEditor.putInt("quest_gin_calim_life", count).commit();
    }

    public static int GetQuestGinClaimCountLife() {
        return preferences.getInt("quest_gin_calim_life", 0);
    }

    public static void SetQuestBigGinCount(int count) {
        prefEditor.putInt("quest_big_gin", count).commit();
    }

    public static int GetQuestBigGinCount() {
        return preferences.getInt("quest_big_gin", 0);
    }

    public static void SetQuestBigGinTodayCount(int count) {
        prefEditor.putInt("quest_big_gin_today", count).commit();
    }


    //=============================== Big Gin =========================================================

    public static int GetQuestBigGinTodayCount() {
        return preferences.getInt("quest_big_gin_today", 0);
    }

    public static void SetQuestBigGinClaimCountToday(int count) {
        prefEditor.putInt("quest_big_gin_calim_today", count).commit();
    }

    public static int GetQuestBigGinClaimCountToday() {
        return preferences.getInt("quest_big_gin_calim_today", 0);
    }

    public static void SetQuestBigGinClaimCountLife(int count) {
        prefEditor.putInt("quest_big_gin_calim_life", count).commit();
    }

    public static int GetQuestBigGinClaimCountLife() {
        return preferences.getInt("quest_big_gin_calim_life", 0);
    }

    public static void SetQuestKnockCount(int count) {
        prefEditor.putInt("quest_knock", count).commit();
    }

    public static int GetQuestKnockCount() {
        return preferences.getInt("quest_knock", 0);
    }

    public static void SetQuestKnockTodayCount(int count) {
        prefEditor.putInt("quest_knock_today", count).commit();
    }


    //=============================== Knock =========================================================

    public static int GetQuestKnockTodayCount() {
        return preferences.getInt("quest_knock_today", 0);
    }

    public static void SetQuestKnockClaimCountToday(int count) {
        prefEditor.putInt("quest_knock_calim_today", count).commit();
    }

    public static int GetQuestKnockClaimCountToday() {
        return preferences.getInt("quest_knock_calim_today", 0);
    }

    public static void SetQuestKnockClaimCountLife(int count) {
        prefEditor.putInt("quest_knock_calim_life", count).commit();
    }

    public static int GetQuestKnockClaimCountLife() {
        return preferences.getInt("quest_knock_calim_life", 0);
    }

    public static void SetQuestStraightCount(int count) {
        prefEditor.putInt("quest_Straight", count).commit();
    }

    public static int GetQuestStraightCount() {
        return preferences.getInt("quest_Straight", 0);
    }

    public static void SetQuestStraightTodayCount(int count) {
        prefEditor.putInt("quest_Straight_today", count).commit();
    }

    //=============================== Straight Mode =========================================================

    public static int GetQuestStraightTodayCount() {
        return preferences.getInt("quest_Straight_today", 0);
    }

    public static void SetQuestStraightClaimCountToday(int count) {
        prefEditor.putInt("quest_Straight_calim_today", count).commit();
    }

    public static int GetQuestStraightClaimCountToday() {
        return preferences.getInt("quest_Straight_calim_today", 0);
    }

    public static void SetQuestStraightClaimCountLife(int count) {
        prefEditor.putInt("quest_Straight_calim_life", count).commit();
    }

    public static int GetQuestStraightClaimCountLife() {
        return preferences.getInt("quest_Straight_calim_life", 0);
    }

    public static void SetQuestOklahomaCount(int count) {
        prefEditor.putInt("quest_Oklahoma", count).commit();
    }

    public static int GetQuestOklahomaCount() {
        return preferences.getInt("quest_Oklahoma", 0);
    }

    public static void SetQuestOklahomaTodayCount(int count) {
        prefEditor.putInt("quest_Oklahoma_today", count).commit();
    }
    //=============================== Oklahoma Mode =========================================================

    public static int GetQuestOklahomaTodayCount() {
        return preferences.getInt("quest_Oklahoma_today", 0);
    }

    public static void SetQuestOklahomaClaimCountToday(int count) {
        prefEditor.putInt("quest_Oklahoma_calim_today", count).commit();
    }

    public static int GetQuestOklahomaClaimCountToday() {
        return preferences.getInt("quest_Oklahoma_calim_today", 0);
    }

    public static void SetQuestOklahomaClaimCountLife(int count) {
        prefEditor.putInt("quest_Oklahoma_calim_life", count).commit();
    }

    public static int GetQuestOklahomaClaimCountLife() {
        return preferences.getInt("quest_Oklahoma_calim_life", 0);
    }

    public static void SetQuestOfferWallCount(int count) {
        prefEditor.putInt("quest_offerwall", count).commit();
    }

    public static int GetQuestOfferwallCount() {
        return preferences.getInt("quest_offerwall", 0);
    }

    public static void SetQuestOfferWallTodayCount(int count) {
        prefEditor.putInt("quest_offerwall_today", count).commit();
    }


    //=============================== Offerwall =========================================================

    public static int GetQuestOfferwallTodayCount() {
        return preferences.getInt("quest_offerwall_today", 0);
    }

    public static void SetQuestOfferClaimCountToday(int count) {
        prefEditor.putInt("quest_offer_calim_today", count).commit();
    }

    public static int GetQuestOfferClaimCountToday() {
        return preferences.getInt("quest_offer_calim_today", 0);
    }

    public static void SetQuestOfferClaimCountLife(int count) {
        prefEditor.putInt("quest_offer_calim_life", count).commit();
    }

    public static int GetQuestOfferClaimCountLife() {
        return preferences.getInt("quest_offer_calim_life", 0);
    }

    public static void SetQuestWatchVideoCount(int count) {
        prefEditor.putInt("quest_watchvideo", count).commit();
    }

    public static int GetQuestWatchVideoCount() {
        return preferences.getInt("quest_watchvideo", 0);
    }

    public static void SetQuestWatchVideoTodayCount(int count) {
        prefEditor.putInt("quest_watchvideo_today", count).commit();
    }


    //=============================== Watch Video =========================================================

    public static int GetQuestWatchVideoTodayCount() {
        return preferences.getInt("quest_watchvideo_today", 0);
    }

    public static void SetQuestVideoClaimCountToday(int count) {
        prefEditor.putInt("quest_video_calim_today", count).commit();
    }

    public static int GetQuestVideoClaimCountToday() {
        return preferences.getInt("quest_video_calim_today", 0);
    }

    public static void SetQuestVideoClaimCountLife(int count) {
        prefEditor.putInt("quest_video_calim_life", count).commit();
    }

    public static int GetQuestVideoClaimCountLife() {
        return preferences.getInt("quest_video_calim_life", 0);
    }

    public static void SetQuestShareCount(int count) {
        prefEditor.putInt("quest_share", count).commit();
    }

    public static int GetQuestShareCount() {
        return preferences.getInt("quest_share", 0);
    }

    public static void SetQuestShareTodayCount(int count) {
        prefEditor.putInt("quest_share_today", count).commit();
    }


    //=============================== Share =========================================================

    public static int GetQuestShareTodayCount() {
        return preferences.getInt("quest_share_today", 0);
    }

    public static void SetQuestShareClaimCountToday(int count) {
        prefEditor.putInt("quest_share_calim_today", count).commit();
    }

    public static int GetQuestShareClaimCountToday() {
        return preferences.getInt("quest_share_calim_today", 0);
    }

    public static void SetQuestShareClaimCountLife(int count) {
        prefEditor.putInt("quest_share_calim_life", count).commit();
    }

    public static int GetQuestShareClaimCountLife() {
        return preferences.getInt("quest_share_calim_life", 0);
    }

    //===========================================================================================
    public static void SetQuestRemoveAdsClaimCountLife(int count) {
        prefEditor.putInt("quest_removeads_calim_life", count).commit();
    }

    public static int GetQuestRemoveAdsClaimCountLife() {
        return preferences.getInt("quest_removeads_calim_life", 0);
    }

    public static void SetQuestRemoveAdsCount(int count) {
        prefEditor.putInt("quest_RemoveAds", count).commit();
    }

    public static int GetQuestRemoveAdsCount() {
        return preferences.getInt("quest_RemoveAds", 0);
    }

    public static void SetQuestPackOneDollerCount(int count) {
        prefEditor.putInt("quest_PackOneDoller", count).commit();
    }

    public static int GetQuestPackOneDollerCount() {
        return preferences.getInt("quest_PackOneDoller", 0);
    }

    public static boolean GetQuestPackOneDollerCollected() {
        return preferences.getBoolean("pack_1.49", true);
    }

    //===========================Quest Purchase TAsk ====================================

    public static void SetQuestPackOneDollerCollected(boolean isCollect) {
        prefEditor.putBoolean("pack_1.49", isCollect).commit();
    }

    public static void SetQuestPackThreeDollerCount(int count) {
        prefEditor.putInt("quest_PackThreeDoller", count).commit();
    }

    public static int GetQuestPackThreeDollerCount() {
        return preferences.getInt("quest_PackThreeDoller", 0);
    }

    public static boolean GetQuestPackThreeDollerCollected() {
        return preferences.getBoolean("pack_3.49", true);
    }

    public static void SetQuestPackThreeDollerCollected(boolean isCollect) {
        prefEditor.putBoolean("pack_3.49", isCollect).commit();
    }

    public static void SetQuestPackFiveDollerCount(int count) {
        prefEditor.putInt("quest_PackFiveDoller", count).commit();
    }

    public static int GetQuestPackFiveDollerCount() {
        return preferences.getInt("quest_PackFiveDoller", 0);
    }

    public static boolean GetQuestPackFiveDollerCollected() {
        return preferences.getBoolean("pack_5.99", true);
    }

    public static void SetQuestPackFiveDollerCollected(boolean isCollect) {
        prefEditor.putBoolean("pack_5.99", isCollect).commit();
    }

    public static void SetQuestPackTenDollerCount(int count) {
        prefEditor.putInt("quest_PackTenDoller", count).commit();
    }

    public static int GetQuestPackTenDollerCount() {
        return preferences.getInt("quest_PackTenDoller", 0);
    }

    public static boolean GetQuestPackTenDollerCollected() {
        return preferences.getBoolean("pack_11.99", true);
    }

    public static void SetQuestPackTenDollerCollected(boolean isCollect) {
        prefEditor.putBoolean("pack_11.99", isCollect).commit();
    }

    public static boolean GetIsQuestNotiEnable() {
        return preferences.getBoolean("quest_noti", false);
    }

    public static boolean GetIsAchivementNotiEnable() {
        return preferences.getBoolean("achivement_noti", false);
    }

    public static void SetIsQuestNotiEnable(boolean value) {
        prefEditor.putBoolean("quest_noti", value).commit();
    }

    // ======================== Quest Notifiacation ========================

    public static void SetIsAchivementNotiEnable(boolean value) {
        prefEditor.putBoolean("achivement_noti", value).commit();
    }

    public static boolean getexitfromplaying() {
        return preferences.getBoolean("exitfromplaying", false);
    }

    public static void setexitfromplaying(boolean b) {
        prefEditor.putBoolean("exitfromplaying", b).commit();
    }

    public static long getChipsBeforePlay() {

        return preferences.getLong("BeforePlaychips", 0);
    }


    //==============================================================================================

    public static void setChipsBeforePlay(long chips1) {
        prefEditor.putLong("BeforePlaychips", chips1).commit();
    }

    public static long getBootvalue() {

        return preferences.getLong("Bootvaluem", 500);
    }

    public static void setBootvalue(long chips1) {
        prefEditor.putLong("Bootvaluem", chips1).commit();
    }

    public static int getpoints() {

        return preferences.getInt("pointsm", 10);
    }

    public static void setpoints(int chips1) {
        prefEditor.putInt("pointsm", chips1).commit();
    }

    public static int getwidth() {

        return preferences.getInt("widthm", 0);
    }

    public static void setwidth(int width) {
        try {
            prefEditor.putInt("widthm", width).commit();
        } catch (NullPointerException n) {
            Log.e("NullPointerException", "" + n);
        } catch (RuntimeException e) {
            Log.e("RuntimeException", "" + e);
        }
    }

    public static int getheight() {

        return preferences.getInt("heightm", 0);
    }

    public static void setheight(int height) {
        try {
            prefEditor.putInt("heightm", height).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getIsGameResume() {
        return preferences.getBoolean("isResume", false);
    }

    public static void setIsGameResume(boolean isResume) {
        prefEditor.putBoolean("isResume", isResume).commit();
    }

    //============== bottom user cards ============================
    public static String getBottomUSerCard(int i) {
        return preferences.getString("bottomCards" + i, " ");
    }

    //====================== Resume Game ======================================

    public static void setBottomUSerCard(String card, int i) {
        prefEditor.putString("bottomCards" + i, card).commit();
    }

    public static void removeBottomUSerCard(int i) {
        prefEditor.remove("bottomCards" + i);
    }

    public static int getBottomUSerCardSize() {
        return preferences.getInt("bottomCardSize", 0);
    }

    public static void setBottomUSerCardSize(int size) {
        prefEditor.putInt("bottomCardSize", size).commit();
    }

    public static int getBottomUSerScore() {
        return preferences.getInt("bottomScore", 0);
    }

    public static void setBottomUSerScore(int Score) {
        prefEditor.putInt("bottomScore", Score).commit();
    }

    //============== left user cards ============================
    public static String getleftUSerCard(int i) {
        return preferences.getString("leftCards" + i, " ");
    }

    public static void setleftUSerCard(String card, int i) {
        prefEditor.putString("leftCards" + i, card).commit();
    }

    public static void removeleftUSerCard(int i) {
        prefEditor.remove("leftCards" + i);
    }

    public static void setleftUSerCardSize(int size) {
        prefEditor.putInt("leftCardSize", size).commit();
    }

    public static int getleftUSerCardSize() {
        return preferences.getInt("leftCardSize", 0);
    }

    public static void setleftUSerScore(int Score) {
        prefEditor.putInt("leftScore", Score).commit();
    }

    public static int getleftUSerScore() {
        return preferences.getInt("leftScore", 0);
    }

    //============== right user cards ============================
    public static String getrightUSerCard(int i) {
        return preferences.getString("rightCards" + i, " ");
    }

    public static void setrightUSerCard(String card, int i) {
        prefEditor.putString("rightCards" + i, card).commit();
    }

    public static void removerightUSerCard(int i) {
        prefEditor.remove("rightCards" + i);
    }

    public static void setrightUSerCardSize(int size) {
        prefEditor.putInt("rightCardSize", size).commit();
    }

    public static int getrightUSerCardSize() {
        return preferences.getInt("rightCardSize", 0);
    }

    public static void setrightUSerScore(int Score) {
        prefEditor.putInt("rightScore", Score).commit();
    }

    public static int getrightUSerScore() {
        return preferences.getInt("rightScore", 0);
    }

    //============== OpenBunch cards ============================
    public static String getOpenBunchCard(int i) {
        return preferences.getString("OpenBunchCards" + i, " ");
    }

    public static void setOpenBunchCard(String card, int i) {
        prefEditor.putString("OpenBunchCards" + i, card).commit();
    }

    public static void removeOpenBunchCard(int i) {
        prefEditor.remove("OpenBunchCards" + i);
    }

    public static int getOpenBunchCardSize() {
        return preferences.getInt("OpenBunchCardSize", 0);
    }

    public static void setOpenBunchCardSize(int size) {
        prefEditor.putInt("OpenBunchCardSize", size).commit();
    }

    //============== CloseBunch cards ============================
    public static String getCloseBunchCard(int i) {
        return preferences.getString("CloseBunchCards" + i, " ");
    }

    public static void setCloseBunchCard(String card, int i) {
        prefEditor.putString("CloseBunchCards" + i, card).commit();
    }

    public static void removeCloseBunchCard(int i) {
        prefEditor.remove("CloseBunchCards" + i);
    }

    public static int getCloseBunchCardSize() {
        return preferences.getInt("CloseBunchCardSize", 0);
    }

    public static void setCloseBunchCardSize(int size) {
        prefEditor.putInt("CloseBunchCardSize", size).commit();
    }

    public static int getRoundNumber() {
        return preferences.getInt("RoundNumber", 0);
    }

    public static void setRoundNumber(int num) {
        prefEditor.putInt("RoundNumber", num).commit();
    }

    ///================ New modes Added ===========================
    public static void SetOklahomaPlayed(int count) {
        prefEditor.putInt("Oklahoma_played", count).commit();
    }

    public static int GetOklahomaPlayed() {
        return preferences.getInt("Oklahoma_played", 0);
    }

    public static void SetOklahomaWon(int count) {
        prefEditor.putInt("Oklahoma_Won", count).commit();
    }

    public static int GetOklahomaWon() {
        return preferences.getInt("Oklahoma_Won", 0);
    }

    public static void SetStraightPlayed(int count) {
        prefEditor.putInt("Straight_played", count).commit();
    }

    public static int GetStraightPlayed() {
        return preferences.getInt("Straight_played", 0);
    }

    public static void SetStraightWon(int count) {
        prefEditor.putInt("Straight_Won", count).commit();
    }

    public static int GetStraightWon() {
        return preferences.getInt("Straight_Won", 0);
    }

    public static void SetGinPlayed(int count) {
        prefEditor.putInt("Gin_played", count).commit();
    }

    public static int GetGinPlayed() {
        return preferences.getInt("Gin_played", 0);
    }

    public static void SetGinWon(int count) {
        prefEditor.putInt("Gin_Won", count).commit();
    }

    public static int GetGinWon() {
        return preferences.getInt("Gin_Won", 0);
    }

    public static void SetHollyWoodPlayed(int count) {
        prefEditor.putInt("HollyWood_played", count).commit();
    }

    public static int GetHollyWoodPlayed() {
        return preferences.getInt("HollyWood_played", 0);
    }

    public static void SetHollyWoodWon(int count) {
        prefEditor.putInt("HollyWood_Won", count).commit();
    }

    public static int GetHollyWoodWon() {
        return preferences.getInt("HollyWood_Won", 0);
    }

    public static void SetIsNewVersion(boolean count) {
        prefEditor.putBoolean("isnewVersion", count).commit();
    }

    public static boolean GetIsNewVersion() {
        return preferences.getBoolean("isnewVersion", true);
    }

    public static boolean getShareBonushCollected_Facebook() {
        return preferences.getBoolean("isShareBonushCollected_Facebook", true);
    }

    public static void setShareBonushCollected_Facebook(boolean value) {
        prefEditor.putBoolean("isShareBonushCollected_Facebook", value).commit();
    }

    public static boolean getShareBonushCollected_WhatsApp() {
        return preferences.getBoolean("isShareBonushCollected_WhatsApp", true);
    }

    public static void setShareBonushCollected_WhatsApp(boolean value) {
        prefEditor.putBoolean("isShareBonushCollected_WhatsApp", value).commit();
    }

    //=============== Playing New Bonus timer ==============================
    public static int getBonusTimerCount() {
        return preferences.getInt("playing_bonus_collect_count", 0);
    }

    public static void setBonusTimerCount(int count) {
        prefEditor.putInt("playing_bonus_collect_count", count).commit();
    }

    public static long getPlayerPoints(int gameType, int numberOfPlayers) {
        return preferences.getLong("PlayerPoint_" + numberOfPlayers + "_" + gameType, 50);
    }

    public static void setPlayerPoints(long twoPlayerPoints, int gameType, int numberOfPlayers) {
        prefEditor.putLong("PlayerPoint_" + numberOfPlayers + "_" + gameType, twoPlayerPoints).commit();
    }

    public static String getBKImagePath() {
        return preferences.getString("BKImagePath", "");
    }

    public static void setBKImagePath(String BKImagePath) {
        prefEditor.putString("BKImagePath", BKImagePath).commit();
    }

    public static String getUPImagePath() {
        return preferences.getString("UPImagePath", "");
    }

    public static void setUPImagePath(String UPImagePath) {
        prefEditor.putString("UPImagePath", UPImagePath).commit();
    }

    public static int getContinuesWin() {
        return preferences.getInt("setContinuesWin", 0);
    }

    //>>>>>>>>>>>>> savan.nasit <<<<<<<<<<<<<<<
    /*>>>>>>>>>>> New Rate Us Logic <<<<<<<<<<*/
    public static void setContinuesWin(int Wins) {
        prefEditor.putInt("setContinuesWin", Wins).commit();
    }

    public static void SetShowRateOnWinner(boolean status) {
        prefEditor.putBoolean("GetIsRateGiveWith2", status).commit();
    }

    public static boolean GetGetIsRateGiveWith2() {
        return preferences.getBoolean("GetIsRateGiveWith2", true);
    }

    public static void SetIsRateGive(boolean status) {
        prefEditor.putBoolean("IsRateGived", status).commit();
    }

    public static boolean GetIsRateGive() {
        return preferences.getBoolean("IsRateGived", false);
    }

    public static void SetIsDaySeven(boolean status) {
        prefEditor.putBoolean("IsDaySeven", status).commit();
    }

    public static boolean GetIsDaySeven() {
        return preferences.getBoolean("IsDaySeven", false);
    }
    /*>>>>>>>>>>> New Rate Us Logic <<<<<<<<<<*/

    /*>>>>>>>>>>> Special Offer <<<<<<<<<<*/

    public static void SetPurchaseAnyPack(boolean status) {
        prefEditor.putBoolean("PurchaseAnyPack", status).commit();
    }

    public static boolean GetPurchaseAnyPack() {
        return preferences.getBoolean("PurchaseAnyPack", false);
    }

    public static void SetPurchaseJsonObject(String JsonObj) {
        prefEditor.putString("PurchaseJsonObject", JsonObj);
    }

    public static String GetPurchaseJsonObject() {
        return preferences.getString("PurchaseJsonObject", "");
    }

    /*>>>>>>>>>>> Special Offer <<<<<<<<<<*/

    //>>>>>>>>>>>>> savan.nasit <<<<<<<<<<<<<<<

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Pref_Context = getApplicationContext();
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        preferences = getSharedPreferences("myGamePreferences", MODE_PRIVATE);
        prefEditor = preferences.edit();

        initSingletons();

        // set log to true
        utils.Logger.setLogging(true);
        //Logger.DEBUG_WITH_STACKTRACE = true;
        final C c = C.getInstance();

        try {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle bundle) {
                }

                @Override
                public void onActivityStarted(Activity activity) {
                }

                @Override
                public void onActivityResumed(Activity activity) {
                }

                @Override
                public void onActivityPaused(Activity activity) {
                }

                @Override
                public void onActivityStopped(Activity activity) {
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity,
                                                        Bundle bundle) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {

                    try {
                        if (activity instanceof Dashboard) {
                            NotificationManager mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            mNM.cancelAll();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void initSingletons() {
        // Initialize the instance of MySingleton
        C.initInstance();

    }

    public void initFacebook() {

		/*SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(APP_ID).setNamespace(APP_NAMESPACE)
				.setPermissions(permissions)
				.setDefaultAudience(SessionDefaultAudience.EVERYONE)
				.setAskForAllPermissionsAtOnce(false).build();

		SimpleFacebook.setConfiguration(configuration);*/
    }

}
