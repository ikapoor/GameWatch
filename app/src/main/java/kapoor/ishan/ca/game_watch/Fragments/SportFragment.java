package kapoor.ishan.ca.game_watch.Fragments;

import java.util.ArrayList;
import java.util.List;

import kapoor.ishan.ca.game_watch.Game;

/**
 * Created by ishan on 2017-10-08.
 */

public interface SportFragment {
    void onDateChanged();
    String getSeason(String date);
    void setSchedule(List<Game> list);
    void noGamesOnSelectedDateView();
    void onGameClicked(String position, String id);
}
