package kapoor.ishan.ca.game_watch.Fragments;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kapoor.ishan.ca.game_watch.APIcalls;
import kapoor.ishan.ca.game_watch.Game;

/**
 * Created by ishan on 2017-09-26.
 */

public class NBAPresenter {

    private static final String TAG  =NBAPresenter.class.getSimpleName();
    private GameView view;
    String seasonType;
    String date;



    public NBAPresenter(){
    }

    public void subscribe(GameView gameView){
        this.view = gameView;
    }

    public void unsubscribe(){
        this.view = null;
    }


    public void loadGames() {
        new ScheduleGetter().execute();
    }

    public class ScheduleGetter extends AsyncTask< Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            ArrayList game = new ArrayList();
            String s = APIcalls.getSchedule(seasonType, date, APIcalls.SPORT_NBA);
            return null;
        }


        @Override
        protected void onPostExecute(String games) {
            Log.d(TAG, games);
            super.onPostExecute(games);
        }
    }






}
