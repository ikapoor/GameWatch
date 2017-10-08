package kapoor.ishan.ca.game_watch.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import kapoor.ishan.ca.game_watch.Adapters.GameAdapter;
import kapoor.ishan.ca.game_watch.Game;
import kapoor.ishan.ca.game_watch.R;

/**
 * Created by ishan on 2017-09-21.
 */

public class NBAFragment extends Fragment {

    ArrayList<Game> nbaSchedule= new ArrayList<Game>();
    GameAdapter adapter;

   /* @BindView(R.id.nba_list_view)
    ListView listView;*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("onCreateView", "onCreate");
        View view  = inflater.inflate(R.layout.nba_fragment, null);
        ButterKnife.bind(this, view);
        adapter = new GameAdapter(getContext(), R.layout.list_item_game, nbaSchedule);
        /*listView = (ListView)view.findViewById(R.id.nba_list_view);
        listView.setAdapter(adapter);*/


        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }





}
