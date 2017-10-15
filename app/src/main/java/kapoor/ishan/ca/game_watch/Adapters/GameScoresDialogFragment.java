package kapoor.ishan.ca.game_watch.Adapters;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import kapoor.ishan.ca.game_watch.Fragments.NBAFragment;
import kapoor.ishan.ca.game_watch.R;

/**
 * Created by ishan on 2017-10-14.
 */

public class GameScoresDialogFragment  extends DialogFragment{

    public static final String SOURCE = "source";
    public static final String SOURCE_NBA = "nba";
    public static final String SOURCE_NFL = "nfl";
    public static final String SOURCE_NHL = "nhl";
    public static final String SOURCE_MLB = "mlb";

    public static final String HOME_TEAM_NAME_KEY = "hometeam";
    public static final String AWAY_TEAM_NAME_KEY = "awayteam";
    public static final String HOME_TEAM_SCORE_KEY = "homeScore";
    public static final String AWAY_TEAM_SCORE_KEY = "awayScore";
    public static final String HOME_TEAM_CITY_KEY = "homecity";
    public static final String AWAY_TEAM_CITY_KEY = "awaycity";
    public static final String HOME_TEAM_ABBREVIATION_KEY = "homeabb";
    public static final String AWAY_TEAM_ABBREVIATION_KEY = "awayabb";
    public static final String HOME_TEAM_RECORD_KEY = "homeRecord";
    public static final String AWAY_TEAM_RECORD_KEY = "awayRecord";


    @BindView(R.id.HomeTeamLogo)
    ImageView homeTeamLogo;

    @BindView(R.id.AwayTeamLogo)
    ImageView awayTeamLogo;

    @BindView(R.id.ScoreTV)
    TextView scoreTV;

    @BindView(R.id.HomeTeamRecord)
    TextView homeTeamRecord;

    @BindView(R.id.AwayTeamRecord)
    TextView awayTeamRecord;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_scores_game, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle.getString(SOURCE) == this.SOURCE_NBA) {
            homeTeamLogo.setImageResource(NBAGameAdapter.getNBAImageID(bundle.getString(HOME_TEAM_ABBREVIATION_KEY)));
            awayTeamLogo.setImageResource(NBAGameAdapter.getNBAImageID(bundle.getString(AWAY_TEAM_ABBREVIATION_KEY)));
        }

        String score = Integer.toString(bundle.getInt(HOME_TEAM_SCORE_KEY)) + " " +
                        Integer.toString(bundle.getInt(AWAY_TEAM_SCORE_KEY));
        scoreTV.setText(score);
        homeTeamRecord.setText(bundle.getString(HOME_TEAM_RECORD_KEY));
        awayTeamRecord.setText(bundle.getString(AWAY_TEAM_RECORD_KEY));
        return view;
    }
}
