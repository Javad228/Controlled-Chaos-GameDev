package etc;

public class TimeFormat {
    public static String formatTimeH(long time) {
        return  String.format("%01d", (time / ( 1000000000L * 60    * 60))) + ":" +     //H
                String.format("%02d", (time / ( 1000000000L * 60 )) % 60)   + ":" +     //MM
                String.format("%02d", (time /   1000000000) % 60    )       + "." +     //SS
                String.format("%03d", (time /   1000000)    % 1000  );                  //msmsms
    }
    public static String formatTimeM(long time) {
        return  String.format("%02d", (time / ( 1000000000L * 60 )) % 60)   + ":" +
                String.format("%02d", (time /  1000000000)  % 60    )       + "." +     //SS
                String.format("%03d", (time /   1000000)    % 1000  );                  //msmsms
    }
}
