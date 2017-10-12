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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kapoor.ishan.ca.game_watch.APIcalls;
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

    @BindView(R.id.list_view)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view  = inflater.inflate(R.layout.nba_fragment, null);
        ButterKnife.bind(this, view);
        adapter = new NFLGameAdapter(getContext(), R.layout.list_item_game, gameList);
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
        date = ((MainActivity)getActivity()).getCurrFullDate();
        new getNFLschedule().execute();

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
        if (getActivity()!=null&& list!=null) {
            gameList.clear();
            gameList.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    public class getNFLschedule extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings){
            return APIcalls.getSchedule(getSeason(date), date, APIcalls.SPORT_NFL);
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayList<Game> tempList = JSONParsing.parseSchedule(s);
            setSchedule(tempList);
        }
    }
}
