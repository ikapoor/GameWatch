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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kapoor.ishan.ca.game_watch.APIcalls;
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
    ArrayList<Game> NHLSchedule= new ArrayList<Game>();
    String date;
    NHLGameAdpater adapter;

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
        adapter = new NHLGameAdpater(getContext(), R.layout.list_item_game, NHLSchedule);
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
        date  = ((MainActivity)getActivity()).getCurrFullDate();
        new getNHLschedule().execute();

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
    public void onGameClicked(String id) {

    }

    public void noGamesOnSelectedDateView(){
        listView.setVisibility(View.GONE);
        noGameTextView.bringToFront();
        noGameTextView.setVisibility(View.VISIBLE);
    }

}
