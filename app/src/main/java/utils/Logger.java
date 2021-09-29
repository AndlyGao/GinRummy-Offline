package utils;


public class Logger {

    public static boolean Logging = false;

    public static void print(String log) {
        System.out.println("GIN RUMMY >>> " + log + "\n");
    }

    public static void prints(String log) {
        System.out.println("GIN RUMMY Ads >>> " + log + "\n");
    }

    public static boolean isLogging() {
        return Logging;
    }

    public static void setLogging(boolean logging) {
        Logging = logging;
    }

}
