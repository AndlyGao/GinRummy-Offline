package utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.artoon.ginrummyoffline.Login;
import com.artoon.ginrummyoffline.R;

import java.util.Calendar;
import java.util.List;

public class NotifyUser extends BroadcastReceiver {

    private static final int NOTI_ACHIEVEMENT = 1;
    private static final int NOTI_QUEST = 2;
    NotificationManager nm;

    @Override
    public void onReceive(Context con, Intent data) {

        Calendar cal = Calendar.getInstance();
        int dayofyear = cal.get(Calendar.DAY_OF_YEAR);
        int dateofyear = cal.get(Calendar.DAY_OF_MONTH);

        Context context = con.getApplicationContext();
        Logger.print("MMMM RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR NNNNNNNNNNNNNNNNNNNN day  : " + dayofyear);
        Logger.print("MMMM RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR NNNNNNNNNNNNNNNNNNNN date : " + dateofyear);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_stat_notify);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (!PreferenceManager.getNotification()) {
            Logger.print("MMMM RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR NNNNNNNNNNNNNNNNNNNN >>>> NotiFication Service off");
            Logger.print("MMMM RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR NNNNNNNNNNNNNNNNNNNN Return 11111");
            return;
        }

        String Messsagetopass = "";

        if (data.hasExtra("for")) {
            Logger.print("MMMM RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR NNNNNNNNNNNNNNNNNNNN 111111");
            if (data.getStringExtra("for").equals("DailyBonus2hour")) {
                manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                Notification notification;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    notification = new Notification(R.drawable.ic_stat_notify2, context.getResources().getString(R.string.hourly_noti), System.currentTimeMillis());
                } else {
                    notification = new Notification(R.drawable.ic_stat_notify, context.getResources().getString(R.string.hourly_noti), System.currentTimeMillis());
                }

                notification.flags |= Notification.FLAG_AUTO_CANCEL;


                Intent intent = new Intent(context, Login.class);
                intent.putExtra("msg", "hourly");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Messsagetopass = "We Have Bonus for you! Let's collect your bonus";
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
                String title = context.getResources().getString(R.string.app_name);
                //       notification.setLatestEventInfo(context, title, context.getResources().getString(R.string.daily_noti), pendingIntent);
                Notification.Builder builder = new Notification.Builder(context);

                Calendar cc = Calendar.getInstance();
                int currentHour = cc.get(Calendar.HOUR_OF_DAY);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setSmallIcon(R.drawable.ic_stat_notify2)
                            .setLargeIcon(largeIcon)
                            .setContentTitle(title)
                            .setContentText(Messsagetopass)
                            .setContentIntent(pendingIntent);
                } else {
                    builder.setSmallIcon(R.drawable.ic_stat_notify)
                            .setContentTitle(title)
                            .setContentText(Messsagetopass)
                            .setContentIntent(pendingIntent);
                }

                notification = builder.getNotification();

                notification.defaults |= Notification.DEFAULT_SOUND;
                notification.defaults |= Notification.DEFAULT_VIBRATE;

                manager.notify(0, notification);
            } else if (data.getStringExtra("for").equals("Quest")) {
                Calendar c = Calendar.getInstance();
                int currentDate = c.get(Calendar.DAY_OF_MONTH);

                if (currentDate != PreferenceManager.GetDate()) {
                    Logger.print("MMMM RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR NNNNNNNNNNNNNNNNNNNN Return 2222");
                    return;

                }


                Intent intent = null;
                boolean a = PreferenceManager.getNotification();
                //  if (GameisOn(context) || (PreferenceManager.getDailyBonusDate() == currentDate)) {
                Logger.print(">>>>>>> MMMM questNoti  is_game_on =" + GameisOn(context) + ";;get push notification-" + a + ";;get current date of PreferenceManager.GetDBDate() " + PreferenceManager.GetDBDate() + ";;and current date = ");
                if (GameisOn(context) || !a) {
                    Logger.print(">>>>>>>>>>>> MMMM questNoti  noti no .avyu..===========.");
                    Logger.print("MMMM RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR NNNNNNNNNNNNNNNNNNNN Return 5555");
                } else {

                    if (data.getExtras().getString("for").equalsIgnoreCase(Parameters.achivement)) {
                        Messsagetopass = context.getResources().getString(R.string.achivement_noti);
                        Logger.print(">>>>>>>>>>>> MMMM  questNoti  noti avyu QUEST ..........");
                    } else if (data.getExtras().getString("for").equalsIgnoreCase(Parameters.quest)) {
                        Messsagetopass = context.getResources().getString(R.string.quest_noti);
                        Logger.print(">>>>>>>>>>>> MMMM questNoti  noti avyu ACHIVEMENT..........");
                    }

                    Notification notification;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        notification = new Notification(R.drawable.ic_stat_notify2, Messsagetopass, System.currentTimeMillis());
                    } else {
                        notification = new Notification(R.drawable.ic_stat_notify, Messsagetopass, System.currentTimeMillis());
                    }

                    notification.flags |= Notification.FLAG_AUTO_CANCEL;


//=============================================================
                    intent = new Intent(context, Login.class);
                    intent.putExtra("msg", context.getResources().getString(R.string.daily_noti));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pendingIntent;

                    if (data.getExtras().getString("for").equalsIgnoreCase(Parameters.achivement)) {
                        pendingIntent = PendingIntent.getActivity(context, 4, intent, 0);
                    } else if (data.getExtras().getString("for").equalsIgnoreCase(Parameters.quest)) {
                        pendingIntent = PendingIntent.getActivity(context, 5, intent, 0);
                    } else {
                        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                    }

                    String title = context.getResources().getString(R.string.app_name);
//       notification.setLatestEventInfo(context, title, context.getResources().getString(R.string.daily_noti), pendingIntent);
                    Notification.Builder builder = new Notification.Builder(context);

                    Calendar cc = Calendar.getInstance();
                    int currentHour = cc.get(Calendar.HOUR_OF_DAY);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder.setSmallIcon(R.drawable.ic_stat_notify2)
                                .setLargeIcon(largeIcon)
                                .setContentTitle(title)
                                .setContentText(Messsagetopass)
                                .setContentIntent(pendingIntent);
                    } else {
                        builder.setSmallIcon(R.drawable.ic_stat_notify)
                                .setContentTitle(title)
                                .setContentText(Messsagetopass)
                                .setContentIntent(pendingIntent);
                    }

                    notification = builder.getNotification();

                    notification.defaults |= Notification.DEFAULT_SOUND;
                    notification.defaults |= Notification.DEFAULT_VIBRATE;

                    if (data.getExtras().getString("for").equalsIgnoreCase(Parameters.achivement)) {
                        manager.notify(NOTI_ACHIEVEMENT, notification);
                    } else if (data.getExtras().getString("for").equalsIgnoreCase(Parameters.quest)) {
                        manager.notify(NOTI_QUEST, notification);
                    }

                }

            }
        } else {
            Logger.print("MMMM RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR NNNNNNNNNNNNNNNNNNNN 22222 ");
            Calendar c = Calendar.getInstance();
            int currentDate = c.get(Calendar.DAY_OF_MONTH);

            if (currentDate == PreferenceManager.GetDate()) {
                Logger.print("MMMM RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR NNNNNNNNNNNNNNNNNNNN Return 3333");
                return;
            }

            Notification notification;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notification = new Notification(R.drawable.ic_stat_notify2, context.getResources().getString(R.string.daily_noti), System.currentTimeMillis());
            } else {
                notification = new Notification(R.drawable.ic_stat_notify, context.getResources().getString(R.string.daily_noti), System.currentTimeMillis());
            }

            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            Intent intent = null;
            boolean a = PreferenceManager.getNotification();
            boolean b = PreferenceManager.GetDBcollected();
            //  if (GameisOn(context) || (PreferenceManager.getDailyBonusDate() == currentDate)) {
            Logger.print(">>>>>>> is_game_on =" + GameisOn(context) + ";;get push notification-" + a + ";;get current date of PreferenceManager.GetDBDate() " + PreferenceManager.GetDBDate() + ";;and current date = " + currentDate);
            if (GameisOn(context) || !a) {
                Logger.print("MMMM RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR NNNNNNNNNNNNNNNNNNNN Return 44444");
                Logger.print(">>>>>>>>>>>>   noti no .avyu..===========.");
            } else {


                Logger.print(">>>>>>>>>>>>   noti avyu..........");
//=============================================================
                intent = new Intent(context, Login.class);
                intent.putExtra("msg", context.getResources().getString(R.string.daily_noti));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                String title = context.getResources().getString(R.string.app_name);
//       notification.setLatestEventInfo(context, title, context.getResources().getString(R.string.daily_noti), pendingIntent);
                Notification.Builder builder = new Notification.Builder(context);

                Calendar cc = Calendar.getInstance();
                int currentHour = cc.get(Calendar.HOUR_OF_DAY);

                if ((currentHour == 13) || (currentHour == 21)) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder.setSmallIcon(R.drawable.ic_stat_notify2)
                                .setLargeIcon(largeIcon)
                                .setContentTitle(title)
                                .setContentText(context.getResources().getString(R.string.daily_noti))
                                .setContentIntent(pendingIntent);
                    } else {
                        builder.setSmallIcon(R.drawable.ic_stat_notify)
                                .setContentTitle(title)
                                .setContentText(context.getResources().getString(R.string.daily_noti))
                                .setContentIntent(pendingIntent);
                    }
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder.setSmallIcon(R.drawable.ic_stat_notify2)
                                .setLargeIcon(largeIcon)
                                .setContentTitle(title)
                                .setContentText(context.getResources().getString(R.string.daily_noti))
                                .setContentIntent(pendingIntent);
                    } else {
                        builder.setSmallIcon(R.drawable.ic_stat_notify)
                                .setContentTitle(title)
                                .setContentText(context.getResources().getString(R.string.daily_noti))
                                .setContentIntent(pendingIntent);
                    }


                }

                notification = builder.getNotification();

                notification.defaults |= Notification.DEFAULT_SOUND;
                notification.defaults |= Notification.DEFAULT_VIBRATE;

                manager.notify(0, notification);

            }


        }


    }

    private boolean GameisOn(Context con) {

        Context context = con.getApplicationContext();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).topActivity.getPackageName().toString()
                    .equalsIgnoreCase(context.getPackageName().toString())) {
//                Logger.print("MMMM RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR NNNNNNNNNNNNNNNNNNNN Return 4444");
                return true;
            }
        }
//        Logger.print("MMMM RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR NNNNNNNNNNNNNNNNNNNN Return 5555");
        return false;
    }
}