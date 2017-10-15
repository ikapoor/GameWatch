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
import kapoor.ishan.ca.game_watch.Adapters.NFLGameAdapter;
import kapoor.ishan.ca.game_watch.Adapters.NHLGameAdpater;
import kapoor.ishan.ca.game_watch.Game;
import kapoor.ishan.ca.game_watch.JSONParsing;
import kapoor.ishan.ca.game_watch.MainActivity;
import kapoor.ishan.ca.game_watch.R;

/**
 * Created by ishan on 2017-10-07.
 */

public class NFLFragment extends Fragment implements SportFragment {

    public static final String TAG = NFLFragment.class.getSimpleName();
    ArrayList<Game> gameList = new ArrayList<Game>();
    NFLGameAdapter adapter;
    String date;
    HashMap<String, int[]> gamesScores = new HashMap<>();
    HashMap<String, String> records = new HashMap<>();
    private boolean scoresGot = false;

    @BindView(R.id.list_view)
    ListView listView;

    @BindView(R.id.no_game_textView)
    TextView noGameTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view  = inflater.inflate(R.layout.nba_fragment, null);
        ButterKnife.bind(this, view);
        adapter = new NFLGameAdapter(getContext(), R.layout.list_item_game, gameList, this);
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
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView()");
        super.onDestroyView();
    }


    @Override
    public void onDateChanged() {
        Log.d(TAG, "onDateChanged()");
        date  = ((MainActivity)getActivity()).getCurrFullDate();
        new getNFLschedule().execute();
        scoresGot = false;

    }

    @Override
    public String getSeason(String date) {
        Log.d(TAG, "getSeason: " + date);
        int intDate =  Integer.valueOf(date);
        if (intDate>=20140904 && intDate <= 20141228)
            return "2014-regular";
        else if (intDate>= 20150103 && intDate <=20150201)
            return "2015-playoff";
        else if (intDate>= 20150910 && intDate <= 20160103)
            return "2015-regular";
        else if (intDate>= 20160109 && intDate <= 20160207)
            return "2016-playoff";
        else if (intDate>=20160908&& intDate<=20170101)
            return "2016-regular";
        else if (intDate>=20170107 && intDate<=2070205)
            return "2017-playoff";
        else if (intDate >= 20170907 && intDate<=20171231)
            return "2017-regular";
        else return "latest";

    }

    @Override
    public void setSchedule(List<Game> list) {
        Log.d(TAG, "setSchedule()");
        if (getActivity()!=null) {
            noGameTextView.setVisibility(View.GONE);
            listView.bringToFront();
            listView.setVisibility(View.VISIBLE);
            gameList.clear();
            gameList.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGameClicked(String position, String id) {

        if (Integer.valueOf(date) > Integer.valueOf(((MainActivity)getActivity()).getTodaysDate())){
            Toast.makeText(getContext(), "This Game is yet to happen ", Toast.LENGTH_SHORT).show();
        }
        else{
            if (!scoresGot)
                new getNFLScore().execute(position, id);
            else {
                showGameScores(position, id);
            }
        }


    }
    public void noGamesOnSelectedDateView(){
        listView.setVisibility(View.GONE);
        noGameTextView.bringToFront();
        noGameTextView.setVisibility(View.VISIBLE);
    }
    private void showGameScores(String positionCLicked, String idClicked) {
        int pos = Integer.valueOf(positionCLicked);
        Game currGame = gameList.get(pos);
        Bundle bundle = new Bundle();
        bundle.putString(GameScoresDialogFragment.SOURCE, GameScoresDialogFragment.SOURCE_NFL);
        bundle.putString(GameScoresDialogFragment.HOME_TEAM_NAME_KEY, currGame.getHomeTeam().getName());
        bundle.putString(GameScoresDialogFragment.AWAY_TEAM_NAME_KEY, currGame.getAwayTeam().getName());
        bundle.getString(GameScoresDialogFragment.HOME_TEAM_CITY_KEY, currGame.getHomeTeam().getCity());
        bundle.putString(GameScoresDialogFragment.AWAY_TEAM_CITY_KEY, currGame.getAwayTeam().getCity());
        bundle.putString(GameScoresDialogFragment.HOME_TEAM_ABBREVIATION_KEY, currGame.getHomeTeam().getAbbreviation());
        bundle.putString(GameScoresDialogFragment.AWAY_TEAM_ABBREVIATION_KEY, currGame.getAwayTeam().getAbbreviation());
        bundle.putInt(GameScoresDialogFragment.HOME_TEAM_SCORE_KEY, gamesScores.get(idClicked)[0]);
        bundle.putInt(GameScoresDialogFragment.AWAY_TEAM_SCORE_KEY, gamesScores.get(idClicked)[1]);
        String currHomeID  = currGame.getHomeTeam().getId();
        String currAwayId = currGame.getAwayTeam().getId();
        bundle.putString(GameScoresDialogFragment.HOME_TEAM_RECORD_KEY, records.get(currHomeID));
        bundle.putString(GameScoresDialogFragment.AWAY_TEAM_RECORD_KEY, records.get(currAwayId));
        GameScoresDialogFragment gameScoresDialogFragment = new GameScoresDialogFragment();
        gameScoresDialogFragment.setArguments(bundle);
        gameScoresDialogFragment.show(getActivity().getFragmentManager(), "tag");


    }


    public class getNFLschedule extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings){
            return APIcalls.getSchedule(getSeason(date), date, APIcalls.SPORT_NFL);
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

    public class getNFLScore extends AsyncTask<String, String, String>{
        public String idClicked;
        public String positionCLicked;
        String record;

        @Override
        protected String doInBackground(String... strings) {
            String stringFromAPICall = APIcalls.getScore(getSeason(date), date, APIcalls.SPORT_NFL);
            Log.d(TAG, stringFromAPICall);
            positionCLicked = strings[0];
            idClicked = strings[1];
            record = APIcalls.getRecord(getSeason(date), APIcalls.SPORT_NFL);
            Log.d(TAG, record);
            scoresGot = true;
            return stringFromAPICall;
        }

        @Override
        protected void onPostExecute(String s) {
            HashMap<String, int[]> scores = JSONParsing.parseNBAScore(s);
            if (scores == null) return;
            gamesScores = JSONParsing.parseNBAScore(s);
            records = JSONParsing.parseRecord(record);
            showGameScores(positionCLicked, idClicked);
        }
    }



}
