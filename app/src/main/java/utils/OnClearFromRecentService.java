package utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.artoon.ginrummyoffline.Dashboard;

import java.util.Calendar;

/**
 * Created by Artoon on 19-Dec-16.
 */
public class OnClearFromRecentService extends Service {

    C c = C.getInstance();
    AlarmManager am;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ClearFromRecentService", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ClearFromRecentService", "Service Destroyed");
    }

    public void onTaskRemoved(Intent rootIntent) {
        Log.e("ClearFromRecentService", "END");
        //Code here
        Log.e("MMMMMMMMMMMMMMMM", "KKKKKKKKKKKKKK");
        try {

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancelAll();
            Dashboard.DisconnectGoogleClient();
            setQuestAlarm();
            setAchivementAlarm();
            setHourlyBonusNotification();

            Logger.print("REWARD VIDEO >>>>>> Clear Applicartion 1111");

            if (!c.isRewardCredit && !c.isRewardClosedCalled) {
                Logger.print("REWARD VIDEO >>>>>> Clear Applicartion 2222");
            }

        } catch (Exception e) {
            Logger.print("ON Resume::::Notification");
        }
        stopSelf();
    }

    private void setQuestAlarm() {
        if (PreferenceManager.GetIsQuestNotiEnable()) {
            Logger.print(">>>>>>>>>>>> MMMM Alarm Quest kill >>>>===========.");

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
            PendingIntent pendingIntent11 = PendingIntent.getBroadcast(this, 18, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            boolean alarmUp1 = (PendingIntent.getBroadcast(this, 18, intent1, PendingIntent.FLAG_NO_CREATE) != null);

            if (alarmUp1) {
                am1.cancel(pendingIntent11);
            }

            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 18, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            am1.setRepeating(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar1.getTimeInMillis(), 0, pendingIntent2);

        }
    }

    private void setAchivementAlarm() {
        if (PreferenceManager.GetIsAchivementNotiEnable()) {
            Logger.print(">>>>>>>>>>>> MMMM Alarm Achivement Kill >>>>===========.");
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
            PendingIntent pendingIntent11 = PendingIntent.getBroadcast(this, 19, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            boolean alarmUp1 = (PendingIntent.getBroadcast(this, 19, intent1, PendingIntent.FLAG_NO_CREATE) != null);

            if (alarmUp1) {
                am1.cancel(pendingIntent11);
            }

            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 19, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            am1.setRepeating(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar1.getTimeInMillis(), 0, pendingIntent2);

        }
    }

    public void setHourlyBonusNotification() {
        Logger.print(">>>> Hourly Bonus >>>> ClearFromRecentService");

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

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 16, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP,/*60*1000 */calendar.getTimeInMillis(), 0, pendingIntent);

    }

}
