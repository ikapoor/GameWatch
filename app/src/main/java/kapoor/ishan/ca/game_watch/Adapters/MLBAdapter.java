package kapoor.ishan.ca.game_watch.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

import kapoor.ishan.ca.game_watch.Game;

/**
 * Created by ishan on 2017-10-11.
 */

public class MLBAdapter extends ArrayAdapter<Game> {

    private List<Game> nflGameList;
    private Context context;


    public MLBAdapter(@NonNull Context context, @LayoutRes int resource, List<Game> gameList) {
        super(context, resource);
        this.context = context;
        this.nflGameList = gameList;
    }


}
