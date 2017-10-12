package kapoor.ishan.ca.game_watch.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kapoor.ishan.ca.game_watch.Game;
import kapoor.ishan.ca.game_watch.R;

/**
 * Created by ishan on 2017-10-08.
 */

public class NFLGameAdapter extends ArrayAdapter<Game> {

    @BindView(R.id.HomeTeam)
    ImageView homeTeam;

    @BindView(R.id.AwayTeam)
    ImageView awayTeam;

    @BindView(R.id.date)
    TextView dateTV;

    @BindView(R.id.time)
    TextView timeTV;

    @BindView(R.id.location)
    TextView locationTV;


    private ArrayList<Game> nflGames;
    private Context con;

    public NFLGameAdapter(@NonNull Context context, int resource, ArrayList<Game> games) {
        super(context, resource);
        this.con = context;
        this.nflGames = games;
    }

    @Override
    public int getCount() {
        return nflGames.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View View, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gameView = inflater.inflate(R.layout.list_item_game, parent, false);
        ButterKnife.bind(this, gameView);
        Game currGame = nflGames.get(position);
       /* homeTeam.setImageResource(getNBAImageID(currGame.getHomeTeam().getAbbreviation()));
        awayTeam.setImageResource(getNBAImageID(currGame.getAwayTeam().getAbbreviation()));*/
        dateTV.setText(currGame.getDate());
        timeTV.setText(currGame.getTime());
        locationTV.setText(currGame.getLocation());
        TextView homeTV = new TextView(parent.getContext());
        homeTV.setText(currGame.getHomeTeam().getCity() + " " + currGame.getHomeTeam().getName());
        TextView awayTV = new TextView(parent.getContext());
        awayTV.setText(currGame.getAwayTeam().getCity() +  " " + currGame.getAwayTeam().getName());
        LinearLayout linearLayout = (LinearLayout)gameView.findViewById(R.id.linear_layout);
        linearLayout.addView(homeTV);
        linearLayout.addView(awayTV);
        return gameView;
    }
}
