package kapoor.ishan.ca.game_watch.Fragments;

import android.app.FragmentManager;
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
import kapoor.ishan.ca.game_watch.Game;
import kapoor.ishan.ca.game_watch.JSONParsing;
import kapoor.ishan.ca.game_watch.MainActivity;
import kapoor.ishan.ca.game_watch.R;

/**
 * Created by ishan on 2017-09-21.
 */

public class NBAFragment extends Fragment implements SportFragment{

    public static final String TAG = NBAFragment.class.getSimpleName();
    ArrayList<Game> nbaSchedule= new ArrayList<Game>();
    HashMap<String, int[]> gamesScores = new HashMap<>();
    HashMap<String, String> records = new HashMap<>();
    NBAGameAdapter adapter;
    String date;
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
        adapter = new NBAGameAdapter(getContext(), R.layout.list_item_game, nbaSchedule, this);
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
    public String getSeason(String date) {
        Log.d(TAG, "getNBASeason: " + date);
        int intDate = Integer.parseInt(date);
        if (intDate>20180411){return "latest";}
        else if(intDate >=20171017){return "2017-2018-regular";}
        else if(intDate>= 20170415 && intDate<=20170612 ){return "2017-playoff";}
        else if (intDate >= 20160825 && intDate <= 20170412){return "2016-2017-regular";}
        else if (intDate >= 20160416 && intDate <= 20160619){return "2016-playoff";}
        else if (intDate>= 20151027 && intDate <= 20160413){return "2015-2016-regular";}
        return "current";
    }

    @Override
    public void setSchedule(List<Game> list) {
        if (getActivity()!=null) {
            noGameTextView.setVisibility(View.GONE);
            listView.bringToFront();
            listView.setVisibility(View.VISIBLE);
            nbaSchedule.clear();
            nbaSchedule.addAll(list);
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
                new getNBAScores().execute(position, id);
            else {
                showGameScores(position, id);
            }
        }
    }

    private void showGameScores(String position, String id) {
        int pos = Integer.valueOf(position);
        Game currGame = nbaSchedule.get(pos);
        Bundle bundle = new Bundle();
        bundle.putString(GameScoresDialogFragment.SOURCE, GameScoresDialogFragment.SOURCE_NBA);
        bundle.putString(GameScoresDialogFragment.HOME_TEAM_NAME_KEY, currGame.getHomeTeam().getName());
        bundle.putString(GameScoresDialogFragment.AWAY_TEAM_NAME_KEY, currGame.getAwayTeam().getName());
        bundle.getString(GameScoresDialogFragment.HOME_TEAM_CITY_KEY, currGame.getHomeTeam().getCity());
        bundle.putString(GameScoresDialogFragment.AWAY_TEAM_CITY_KEY, currGame.getAwayTeam().getCity());
        bundle.putString(GameScoresDialogFragment.HOME_TEAM_ABBREVIATION_KEY, currGame.getHomeTeam().getAbbreviation());
        bundle.putString(GameScoresDialogFragment.AWAY_TEAM_ABBREVIATION_KEY, currGame.getAwayTeam().getAbbreviation());
        if (gamesScores.get(id) == null){
            bundle.putInt(GameScoresDialogFragment.HOME_TEAM_SCORE_KEY, 0);
            bundle.putInt(GameScoresDialogFragment.AWAY_TEAM_SCORE_KEY, 0);
        }
        else {
            bundle.putInt(GameScoresDialogFragment.HOME_TEAM_SCORE_KEY, gamesScores.get(id)[0]);
            bundle.putInt(GameScoresDialogFragment.AWAY_TEAM_SCORE_KEY, gamesScores.get(id)[1]);
        }
        String currHomeID  = currGame.getHomeTeam().getId();
        String currAwayId = currGame.getAwayTeam().getId();
        bundle.putString(GameScoresDialogFragment.HOME_TEAM_RECORD_KEY, records.get(currHomeID));
        bundle.putString(GameScoresDialogFragment.AWAY_TEAM_RECORD_KEY, records.get(currAwayId));
        GameScoresDialogFragment gameScoresDialogFragment = new GameScoresDialogFragment();
        gameScoresDialogFragment.setArguments(bundle);
        gameScoresDialogFragment.show(getActivity().getFragmentManager(), "tag");
    }

    @Override
    public void onDateChanged() {
        date  = ((MainActivity)getActivity()).getCurrFullDate();
        new getNBAschedule().execute();
        scoresGot = false;
    }

    public void noGamesOnSelectedDateView(){
        listView.setVisibility(View.GONE);
        noGameTextView.bringToFront();
        noGameTextView.setVisibility(View.VISIBLE);
    }




    public class getNBAschedule extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings){
            return APIcalls.getSchedule(getSeason(date), date, APIcalls.SPORT_NBA);
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


    public class getNBAScores extends AsyncTask<String, String, String> {

        public String idClicked;
        public String positionCLicked;
        String record;

        @Override
        protected String doInBackground(String... strings) {
            String stringFromAPICall = APIcalls.getScore(getSeason(date), date, APIcalls.SPORT_NBA);
            Log.d(TAG, stringFromAPICall);
            positionCLicked = strings[0];
            idClicked = strings[1];
            record = APIcalls.getRecord(getSeason(date), APIcalls.SPORT_NBA);
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

