package kapoor.ishan.ca.game_watch.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import kapoor.ishan.ca.game_watch.Fragments.NBAFragment;

/**
 * Created by ishan on 2017-09-21.
 */

public class TabViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = TabViewPagerAdapter.class.getSimpleName();

    // ints to identify the app
    public static final int TAB_NBA = 0;
    public static final int TAB_NHL = 1;
    public static final int TAB_NFL = 2;
    public static final int TAB_MLB = 3;

    private NBAFragment nbaFragment;
    private NBAFragment nflFragment;
    private NBAFragment nhlFragment;
    private NBAFragment mlbFragment;

    public TabViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem()");
        switch (position) {
            case TAB_NBA:
                if (nbaFragment == null) {
                    nbaFragment = new NBAFragment();
                }
                return nbaFragment;
            case TAB_NHL:
                if (nhlFragment == null) {
                    nhlFragment = new NBAFragment();
                }
                return nhlFragment;
            case TAB_NFL:
                if (nflFragment == null) {
                    nflFragment = new NBAFragment();
                }
                return nflFragment;
            case TAB_MLB:
                if (mlbFragment == null) {
                    mlbFragment = new NBAFragment();
                }
                return mlbFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        // static tabs, only four tabs should exist at all times
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case TAB_MLB:
                return "MLB";
            case TAB_NBA:
                return "NBA";
            case TAB_NFL:
                return "NFL";
            case TAB_NHL:
                return "NHL";
        }
        return null;
    }
}
