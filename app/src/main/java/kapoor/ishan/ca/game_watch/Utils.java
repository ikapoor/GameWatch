package kapoor.ishan.ca.game_watch;

/**
 * Created by ishan on 2017-10-08.
 */

public class Utils {

    public static String parseDate(int year, int month, int date){
        String Y;       // year
        String M;       //month
        String D;       // date
        if (month+1 < 10){M = "0" + Integer.toString(month+1);}
        else{M = Integer.toString(month+1);}
        if (date<10){D = "0" + Integer.toString(date);}
        else {D = Integer.toString(date);}
        Y = Integer.toString(year);
        return Y+M+D;
    }
}
