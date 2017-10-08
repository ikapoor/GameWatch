package kapoor.ishan.ca.game_watch.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kapoor.ishan.ca.game_watch.R;

/**
 * Created by ishan on 2017-10-07.
 */

public class NFLFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nba_fragment, null);
        return view;
    }
}
