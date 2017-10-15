package kapoor.ishan.ca.game_watch;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ishan on 2017-09-26.
 */

public class APIcalls {
    private static final String TAG = APIcalls.class.getSimpleName();

    public static final String BASE_URL = "https://api.mysportsfeeds.com/v1.1/pull/";
    public static final String SPORT_NBA = "nba/";
    public static final String SPORT_NFL = "nfl/";
    public static final String SPORT_MLB = "mlb/";
    public static final String SPORT_NHL = "nhl/";
    private static final String ENCODING = "aXNoYW5rOmJyeWFudDI0";
    public static final String ARGUEMENT_DAILY_GAME_SCHEDULE = "/daily_game_schedule.json?fordate=";
    public static final String ARGUEMENT_SCOREBOARD = "/scoreboard.json?fordate=";
    public static final String ARGUEMENT_RECORD = "/overall_team_standings.json?teamstats=W,L";

    public static String getSchedule(String SeasonType, String Date, String sport) {
        Log.d(TAG, "getSchedule()" + sport + " " + SeasonType + " " + Date);
        try {
            // connecting to the API with HTTP GET request
            URL url = new URL(BASE_URL + sport + SeasonType + ARGUEMENT_DAILY_GAME_SCHEDULE + Date);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setRequestProperty("Authorization", "Basic " + ENCODING);

            // parsing the data and putting it in a string buffer
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();

            String line;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            in.close();
            // returning string
            Log.d(TAG, stringBuffer.toString());
            return stringBuffer.toString();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return e.toString();
        }
    }

    public static String getScore(String SeasonType, String Date, String sport) {
        Log.d(TAG, "getScoreBoard() " + sport + " " + SeasonType + " " + Date);
        try {
            URL url = new URL(BASE_URL + sport + SeasonType + ARGUEMENT_SCOREBOARD + Date);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setRequestProperty("Authorization", "Basic " + ENCODING);
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();

            String line;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            in.close();
            // returning string
            Log.d(TAG, stringBuffer.toString());
            return stringBuffer.toString();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return e.toString();
        }
    }



    public static String getRecord(String SeasonType, String sport){
        try {
            URL url = new URL (BASE_URL + sport +  SeasonType +ARGUEMENT_RECORD);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + ENCODING);
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();

            String line;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            in.close();
            // returning string
            Log.d(TAG, stringBuffer.toString());
            return stringBuffer.toString();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return e.toString();
        }
    }

}
