package kapoor.ishan.ca.game_watch.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import kapoor.ishan.ca.game_watch.Adapters.MLBGameAdapter;
import kapoor.ishan.ca.game_watch.Adapters.NBAGameAdapter;
import kapoor.ishan.ca.game_watch.Game;
import kapoor.ishan.ca.game_watch.JSONParsing;
import kapoor.ishan.ca.game_watch.MainActivity;
import kapoor.ishan.ca.game_watch.R;

/**
 * Created by ishan on 2017-10-07.
 */

public class MLBFragment extends Fragment implements SportFragment {

    public static final String TAG  = MLBFragment.class.getSimpleName();
    private ArrayList<Game> mlbSchedule = new ArrayList<>();
    private MLBGameAdapter adapter;
    private HashMap<String, int[]> gamesScores = new HashMap<>();
    private HashMap<String, String> records = new HashMap<>();
    private String date;
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
        adapter = new MLBGameAdapter(getContext(), R.layout.list_item_game, mlbSchedule, this);
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

    public class getMLBschedule extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings){
            return APIcalls.getSchedule(getSeason(date), date, APIcalls.SPORT_MLB);
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
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView()");
        super.onDestroyView();
    }


    @Override
    public void onDateChanged() {
        Log.d(TAG, "onDateCHanged()");
        date  = ((MainActivity)getActivity()).getCurrFullDate();
        new getMLBschedule().execute();
        scoresGot = false;

    }

    @Override
    public String getSeason(String date) {
        int intDate = Integer.valueOf(date);
        if (intDate >=20160403 && intDate<=20161002)
            return "2016-regular";
        else if (intDate>=20161003  && intDate <= 20161102)
            return "2016-playoff";
        else if (intDate>=20170402 && intDate<=20161001)
            return "2017-regular";
        else if (intDate>=20161002 && intDate<=20171101)
            return "2017-playoff";

        return null;
    }

    @Override
    public void setSchedule(List<Game> list) {

        if (getActivity()!=null&& list!=null) {
            noGameTextView.setVisibility(View.GONE);
            listView.bringToFront();
            listView.setVisibility(View.VISIBLE);
            mlbSchedule.clear();
            mlbSchedule.addAll(list);
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
                new getMLBScores().execute(position, id);
            else {
                showGameScores(position, id);
            }
        }

    }
    private void showGameScores(String positionCLicked, String idClicked) {

        int pos = Integer.valueOf(positionCLicked);
        Game currGame = mlbSchedule.get(pos);
        Bundle bundle = new Bundle();
        bundle.putString(GameScoresDialogFragment.SOURCE, GameScoresDialogFragment.SOURCE_MLB);
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

    public class getMLBScores extends AsyncTask<String, String, String>{

        public String idClicked;
        public String positionCLicked;
        String record;

        @Override
        protected String doInBackground(String... strings) {
            String stringFromAPICall = APIcalls.getScore(getSeason(date), date, APIcalls.SPORT_MLB);
            Log.d(TAG, stringFromAPICall);
            positionCLicked = strings[0];
            idClicked = strings[1];
            record = APIcalls.getRecord(getSeason(date), APIcalls.SPORT_MLB);
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
