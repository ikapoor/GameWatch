package kapoor.ishan.ca.game_watch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ishan on 2017-10-08.
 */

public class JSONParsing {

    public static final String TAG  = JSONParsing.class.getSimpleName();

    public static ArrayList<Game> parseSchedule (String s){
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
                    Log.d(TAG, "parseGameSuccess");
                }
                return listOfGames;

            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.toString());
            Log.d(TAG, "parseGameFail");
        }
        return null;
    }

    public static HashMap<String, int[]> parseNBAScore(String rawJSON){
        HashMap<String, int[]> hashMap = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(rawJSON);
            JSONObject scoreboard = jsonObject.getJSONObject("scoreboard");
            if (!scoreboard.has("gameScore")) return null;
            JSONArray gameScore = scoreboard.getJSONArray("gameScore");

            for (int i = 0; i<gameScore.length(); i++){
                JSONObject currGame = gameScore.getJSONObject(i);
                int[] score= new int[2];
                JSONObject game = currGame.getJSONObject("game");
                String id = game.getString("ID");
                score[0] = Integer.parseInt(currGame.getString("homeScore"));
                score[1] = Integer.parseInt(currGame.getString("awayScore"));
                hashMap.put(id, score);
            }
            return hashMap;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static HashMap<String, String>parseRecord(String rawJSON){
        HashMap<String, String> records = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(rawJSON);
            JSONObject overallTeamStanding = jsonObject.getJSONObject("overallteamstandings");
            JSONArray array = overallTeamStanding.getJSONArray("teamstandingsentry");
            for(int i = 0; i<array.length(); i++){
                JSONObject team = array.getJSONObject(i).getJSONObject("team");
                String id = team.getString("ID");
                JSONObject stats = array.getJSONObject(i).getJSONObject("stats");
                // weird api thing, NHL games has a stats object in stats object
                if (stats.has("stats")){
                    stats = stats.getJSONObject("stats");
                }
                String wins = stats.getJSONObject("Wins"). getString("#text");
                String losses = stats.getJSONObject("Losses"). getString("#text");
                records.put(id, wins + " - " + losses);
            }
            return records;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}
