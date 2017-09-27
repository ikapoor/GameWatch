package kapoor.ishan.ca.game_watch.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kapoor.ishan.ca.game_watch.Adapters.GameAdapter;
import kapoor.ishan.ca.game_watch.Game;
import kapoor.ishan.ca.game_watch.R;

/**
 * Created by ishan on 2017-09-21.
 */

public class NBAFragment extends Fragment implements GameView {

    ArrayList<Game> nbaSchedule= new ArrayList<Game>();
    NBAPresenter presenter;
    GameAdapter adapter;

    @BindView(R.id.nba_list_view)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.nba_fragment, null);
        ButterKnife.bind(this, view);
        adapter = new GameAdapter(getContext(), R.layout.list_item_game, nbaSchedule);
        listView.setAdapter(adapter);

        if(presenter == null){
            presenter = new NBAPresenter();
        }

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.subscribe(this);
        presenter.loadGames();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribe();
    }





}
