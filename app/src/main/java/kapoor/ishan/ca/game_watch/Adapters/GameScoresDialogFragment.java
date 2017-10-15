package kapoor.ishan.ca.game_watch.Adapters;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by ishan on 2017-10-14.
 */

public class GameScoresDialogFragment  extends DialogFragment{
    public static final String HOME_TEAM_NAME_KEY = "hometeam";
    public static final String AWAY_TEAM_NAME_KEY = "awayteam";
    public static final String HOME_TEAM_SCORE_KEY = "homeScore";
    public static final String AWAY_TEAM_SCORE_KEY = "awayScore";
    public static final String HOME_TEAM_CITY_KEY = "homecity";
    public static final String AWAY_TEAM_CITY_KEY = "awaycity";
    public static final String HOME_TEAM_ABBREVIATION_KEY = "homeabb";
    public static final String AWAY_TEAM_ABBREVIATION_KEY = "awayabb";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
