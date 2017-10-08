package kapoor.ishan.ca.game_watch.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kapoor.ishan.ca.game_watch.Game;
import kapoor.ishan.ca.game_watch.R;

/**
 * Created by ishan on 2017-09-26.
 */

public class GameAdapter extends ArrayAdapter<Game> {

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

    ArrayList<Game> schedule;
    Context con;

    public GameAdapter(@NonNull Context context, int resource, ArrayList<Game> games) {
        super(context, resource);
        con = context;
        schedule = games;
    }
    @Override
    public int getCount() {
        return schedule.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View View, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gameView = inflater.inflate(R.layout.list_item_game, parent, false);
        ButterKnife.bind(this, gameView);
        Game currGame = schedule.get(position);
        homeTeam.setImageResource(getNBAImageID(currGame.getHomeTeam().getAbbreviation()));
        awayTeam.setImageResource(getNBAImageID(currGame.getAwayTeam().getAbbreviation()));
        dateTV.setText(currGame.getDate());
        timeTV.setText(currGame.getTime());
        locationTV.setText(currGame.getLocation());
        return gameView;
    }

    public static int getNBAImageID(String abb) {

        switch (abb) {

            case "ATL":
                return R.drawable.atl;
            case "BRO":
                return R.drawable.bkn;
            case "BOS":
                return R.drawable.bos;
            case "CHA":
                return R.drawable.cha;
            case "CHI":
                return R.drawable.chi;
            case "CLE":
                return R.drawable.cle;
            case "DAL":
                return R.drawable.dal;
            case "DEN":
                return R.drawable.den;
            case "DET":
                return R.drawable.det;
            case "GSW":
                return R.drawable.gsw;
            case "HOU":
                return R.drawable.hou;
            case "IND":
                return R.drawable.ind;
            case "LAC":
                return R.drawable.lac;
            case "LAL":
                return R.drawable.lal;
            case "MEM":
                return R.drawable.mem;
            case "MIA":
                return R.drawable.mia;
            case "MIL":
                return R.drawable.mil;
            case "MIN":
                return R.drawable.min;
            case "NOP":
                return R.drawable.nop;
            case "NYK":
                return R.drawable.nyk;
            case "OKL":
                return R.drawable.okc;
            case "ORL":
                return R.drawable.orl;
            case "PHI":
                return R.drawable.phi;
            case "PHX":
                return R.drawable.phx;
            case "POR":
                return R.drawable.por;
            case "SAC":
                return R.drawable.sac;
            case "SAS":
                return R.drawable.sas;
            case "TOR":
                return R.drawable.tor;
            case "UTA":
                return R.drawable.uta;
            case "WAS":
                return R.drawable.was;
            default:
                return 0;


        }

    }


}
