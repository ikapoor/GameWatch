package kapoor.ishan.ca.game_watch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ishan on 2017-10-08.
 */

public class JSONParsing {

    public static final String TAG  = JSONParsing.class.getSimpleName();

    public static ArrayList<Game> parseNBASchedule (String s){
        Log.d(TAG, "parseNBASchedule");
        Log.d(TAG, s);
        ArrayList<Game> listOfGames = new ArrayList<Game>();
        try {
            JSONObject jobj = new JSONObject(s);
            JSONObject dailySchedule = jobj.getJSONObject("dailygameschedule");
            if (!dailySchedule.has("gameentry")){return null;}
            else {
                JSONArray gameEntry =dailySchedule.getJSONArray("gameentry");

                for (int i = 0; i<gameEntry.length(); i++){
                    Game currGame = new Game();

                    JSONObject currJSONgame = (JSONObject)gameEntry.get(i);

                    Team homeTeam = new Team();
                    Team awayTeam = new Team();

                    JSONObject JSONhome = currJSONgame.getJSONObject("homeTeam");
                    JSONObject JSONaway = currJSONgame.getJSONObject("awayTeam");

                    homeTeam.setAbbreviation(JSONhome.getString("Abbreviation"));
                    homeTeam.setCity(JSONhome.getString("City"));
                    homeTeam.setID(JSONhome.getString("ID"));
                    homeTeam.setName(JSONhome.getString("Name"));

                    awayTeam.setAbbreviation(JSONaway.getString("Abbreviation"));
                    awayTeam.setCity(JSONaway.getString("City"));
                    awayTeam.setID(JSONaway.getString("ID"));
                    awayTeam.setName(JSONaway.getString("Name"));

                    currGame.setDate(currJSONgame.getString("date"));
                    currGame.setLocation(currJSONgame.getString("location"));
                    currGame.setID(currJSONgame.getString("id"));
                    currGame.setTime(currJSONgame.getString("time"));

                    currGame.setHomeTeam(homeTeam);
                    currGame.setAwayTeam(awayTeam);

                    listOfGames.add(currGame);
                    Log.d(TAG, "parseNBAsuccess");
                }
                return listOfGames;

            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.toString());
            Log.d(TAG, "parseNBAfail");
        }
        return null;
    }
}
