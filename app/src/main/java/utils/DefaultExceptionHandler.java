package utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Process;

import com.artoon.ginrummyoffline.Login;

import java.lang.Thread.UncaughtExceptionHandler;

public class DefaultExceptionHandler implements UncaughtExceptionHandler {

    //	private UncaughtExceptionHandler defaultUEH;
    Activity activity;

    public DefaultExceptionHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {

        exception.printStackTrace();

        //Logger.Print("MAIN ERROR::::: " + exception.getMessage());

//		StringWriter stackTrace = new StringWriter();
//      exception.printStackTrace(new PrintWriter(stackTrace));
//      System.err.println(stackTrace);// You can use LogCat too

        Intent intent = new Intent(activity, Login.class);
//        String s = stackTrace.toString();
        //you can use this String to know what caused the exception and in which Activity

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        //for restarting the Activity
        Process.killProcess(Process.myPid());
        System.exit(0);

    }

}