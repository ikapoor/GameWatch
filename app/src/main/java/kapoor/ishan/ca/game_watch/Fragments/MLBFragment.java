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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kapoor.ishan.ca.game_watch.APIcalls;
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
    ArrayList<Game> mlbSchedule = new ArrayList<>();
    MLBGameAdapter adapter;
    String date;


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
        adapter = new MLBGameAdapter(getContext(), R.layout.list_item_game, mlbSchedule);
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
        date  = ((MainActivity)getActivity()).getCurrFullDate();
        new getMLBschedule().execute();

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
    public void onGameClicked(String id) {

    }


    public void noGamesOnSelectedDateView(){
        listView.setVisibility(View.GONE);
        noGameTextView.bringToFront();
        noGameTextView.setVisibility(View.VISIBLE);
    }
}
