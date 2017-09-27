package kapoor.ishan.ca.game_watch.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import kapoor.ishan.ca.game_watch.Fragments.NBAFragment;

/**
 * Created by ishan on 2017-09-21.
 */

public class TabViewPagerAdapter extends FragmentPagerAdapter {

    // ints to identify the app
    public static final int TAB_NBA = 0;
    public static final int TAB_NHL = 1;
    public static final int  TAB_NFL =2;
    public static final int  TAB_MLB = 3;

    private NBAFragment nbaFragment;
    public TabViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case TAB_NBA:
                if(nbaFragment == null){
                    nbaFragment = new NBAFragment();
                }
                return nbaFragment;


            // do rest of cases


        }






        return null;
    }

    @Override
    public int getCount() {
        // static tabs, only four tabs should exist at all times
        return 4;
    }
}
