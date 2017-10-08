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
import kapoor.ishan.ca.game_watch.Adapters.GameAdapter;
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
    GameAdapter adapter;
    String date;

   @BindView(R.id.nba_list_view)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view  = inflater.inflate(R.layout.nba_fragment, null);
        ButterKnife.bind(this, view);
        adapter = new GameAdapter(getContext(), R.layout.list_item_game, nbaSchedule);
        listView = (ListView)view.findViewById(R.id.nba_list_view);
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


    public class getNBAschedule extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings){
            return APIcalls.getSchedule(getSeason(date), date, APIcalls.SPORT_NBA);
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayList<Game> tempList = JSONParsing.parseNBASchedule(s);
            setSchedule(tempList);
        }
    }

    @Override
    public void onDateChanged() {
        date  = ((MainActivity)getActivity()).getCurrFullDate();
        new getNBAschedule().execute();
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
            nbaSchedule.clear();
            nbaSchedule.addAll(list);
            adapter.notifyDataSetChanged();
        }

    }
}
