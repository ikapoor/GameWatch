package kapoor.ishan.ca.game_watch.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kapoor.ishan.ca.game_watch.APIcalls;
import kapoor.ishan.ca.game_watch.Adapters.GameScoresDialogFragment;
import kapoor.ishan.ca.game_watch.Adapters.NBAGameAdapter;
import kapoor.ishan.ca.game_watch.Adapters.NHLGameAdpater;
import kapoor.ishan.ca.game_watch.Game;
import kapoor.ishan.ca.game_watch.JSONParsing;
import kapoor.ishan.ca.game_watch.MainActivity;
import kapoor.ishan.ca.game_watch.R;

/**
 * Created by ishan on 2017-10-07.
 */

public class NHLFragment extends Fragment implements SportFragment{
    public static final String TAG = NHLFragment.class.getSimpleName();
    private ArrayList<Game> NHLSchedule= new ArrayList<Game>();
    private HashMap<String, int[]> gamesScores = new HashMap<>();
    private HashMap<String, String> records = new HashMap<>();
    private String date;
    private NHLGameAdpater adapter;
    private boolean scoresGot = false;

    @BindView(R.id.list_view)
    ListView listView;

    @BindView(R.id.no_game_textView)
    TextView noGameTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.nba_fragment, null);
        ButterKnife.bind(this, view);
        adapter = new NHLGameAdpater(getContext(), R.layout.list_item_game, NHLSchedule, this);
        listView = (ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        date  = ((MainActivity)getActivity()).getCurrFullDate();
        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        super.onViewCreated(view, savedInstanceState);
        onDateChanged();
    }

    @Override
    public void onDateChanged() {
        Log.d(TAG, "onDatechanged()");
        date  = ((MainActivity)getActivity()).getCurrFullDate();
        new getNHLschedule().execute();
        scoresGot = false;

    }

    @Override
    public String getSeason(String date) {
        Log.d(TAG, "getNBASeason: " + date);
        int intDate = Integer.parseInt(date);
        if (intDate>20180418)return "latest";
        else if (intDate<=20180418 && intDate>=20171009) return "2017-2018-regular";

        else if (intDate <= 20170611 && intDate>=20161012) return "2016-2017-regular";

        else if (intDate<=20160612 && intDate >=20151007) return "2015-2016-regular";

        else if (intDate<=20150615 && intDate>= 20141008) return "2014-2015-regular";

        else if (intDate <= 20140613 && intDate >=20131001) return "2013-2014-regular";

        else if (intDate<=20130624 && intDate>=20130119) return "2012-2013-regular";

        else if (intDate<=20120611 && intDate>=20111006) return "2011-2012-regular";

        else if (intDate<=20110615 && intDate>=20101007) return "2010-2011-regular";

        else if (intDate<=20100609 && intDate>=20091001) return "2009-2010-regular";

        else if (intDate<=20090612 && intDate>=20081004) return "2008-2009-regular";

        else if (intDate<=20080604 && intDate>=20070924) return "2007-2008-regular";
        else return "latest";
    }

    @Override
    public void setSchedule(List<Game> list) {
        if (getActivity()!=null&& list!=null) {
            noGameTextView.setVisibility(View.GONE);
            listView.bringToFront();
            listView.setVisibility(View.VISIBLE);
            NHLSchedule.clear();
            NHLSchedule.addAll(list);
            adapter.notifyDataSetChanged();
        }

    }

    public class getNHLschedule extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings){
            return APIcalls.getSchedule(getSeason(date), date, APIcalls.SPORT_NHL);
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayList<Game> tempList = JSONParsing.parseSchedule(s);
            if (tempList == null)
                noGamesOnSelectedDateView();
            else
                setSchedule(tempList);
        }
    }

    @Override
    public void onGameClicked(String position, String id) {

        if (Integer.valueOf(date) > Integer.valueOf(((MainActivity)getActivity()).getTodaysDate())){
            Toast.makeText(getContext(), "This Game is yet to happen ", Toast.LENGTH_SHORT).show();
        }
        else{
            if (!scoresGot)
                new getNHLScores().execute(position, id);
            else {
                showGameScores(position, id);
            }
        }

    }

    private void showGameScores(String positionCLicked, String idClicked) {
        int pos = Integer.valueOf(positionCLicked);
        Game currGame = NHLSchedule.get(pos);
        Bundle bundle = new Bundle();
        bundle.putString(GameScoresDialogFragment.SOURCE, GameScoresDialogFragment.SOURCE_NHL);
        bundle.putString(GameScoresDialogFragment.HOME_TEAM_NAME_KEY, currGame.getHomeTeam().getName());
        bundle.putString(GameScoresDialogFragment.AWAY_TEAM_NAME_KEY, currGame.getAwayTeam().getName());
        bundle.getString(GameScoresDialogFragment.HOME_TEAM_CITY_KEY, currGame.getHomeTeam().getCity());
        bundle.putString(GameScoresDialogFragment.AWAY_TEAM_CITY_KEY, currGame.getAwayTeam().getCity());
        bundle.putString(GameScoresDialogFragment.HOME_TEAM_ABBREVIATION_KEY, currGame.getHomeTeam().getAbbreviation());
        bundle.putString(GameScoresDialogFragment.AWAY_TEAM_ABBREVIATION_KEY, currGame.getAwayTeam().getAbbreviation());
        if (gamesScores.get(idClicked) == null){
            bundle.putInt(GameScoresDialogFragment.HOME_TEAM_SCORE_KEY, 0);
            bundle.putInt(GameScoresDialogFragment.AWAY_TEAM_SCORE_KEY, 0);
        }
        else {
            bundle.putInt(GameScoresDialogFragment.HOME_TEAM_SCORE_KEY, gamesScores.get(idClicked)[0]);
            bundle.putInt(GameScoresDialogFragment.AWAY_TEAM_SCORE_KEY, gamesScores.get(idClicked)[1]);
        }
        String currHomeID  = currGame.getHomeTeam().getId();
        String currAwayId = currGame.getAwayTeam().getId();
        bundle.putString(GameScoresDialogFragment.HOME_TEAM_RECORD_KEY, records.get(currHomeID));
        bundle.putString(GameScoresDialogFragment.AWAY_TEAM_RECORD_KEY, records.get(currAwayId));
        GameScoresDialogFragment gameScoresDialogFragment = new GameScoresDialogFragment();
        gameScoresDialogFragment.setArguments(bundle);
        gameScoresDialogFragment.show(getActivity().getFragmentManager(), "tag");


    }

    public void noGamesOnSelectedDateView(){
        listView.setVisibility(View.GONE);
        noGameTextView.bringToFront();
        noGameTextView.setVisibility(View.VISIBLE);
    }


    public class getNHLScores extends AsyncTask<String, String, String>{

        public String idClicked;
        public String positionCLicked;
        String record;

        @Override
        protected String doInBackground(String... strings) {
            String stringFromAPICall = APIcalls.getScore(getSeason(date), date, APIcalls.SPORT_NHL);
            Log.d(TAG, stringFromAPICall);
            positionCLicked = strings[0];
            idClicked = strings[1];
            record = APIcalls.getRecord(getSeason(date), APIcalls.SPORT_NHL);
            Log.d(TAG, record);
            scoresGot = true;
            return stringFromAPICall;
        }

        @Override
        protected void onPostExecute(String s) {
            HashMap<String, int[]> scores = JSONParsing.parseNBAScore(s);
            if (scores == null) return;
            //TODO if its today's date, scores equals null and you have to click twice, fix this
            gamesScores = JSONParsing.parseNBAScore(s);
            records = JSONParsing.parseRecord(record);
            showGameScores(positionCLicked, idClicked);

        }

    }


}
