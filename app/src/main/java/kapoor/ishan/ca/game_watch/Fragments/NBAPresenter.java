package kapoor.ishan.ca.game_watch.Fragments;

import kapoor.ishan.ca.game_watch.Game;

/**
 * Created by ishan on 2017-09-26.
 */

public class NBAPresenter {

    private GameView view;


    public NBAPresenter(){
    }

    public void subscribe(GameView gameView){
        this.view = gameView;
    }

    public void unsubscribe(){
        this.view = null;
    }


    public void loadGames() {




    }
}
