package kapoor.ishan.ca.game_watch.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kapoor.ishan.ca.game_watch.Fragments.MLBFragment;
import kapoor.ishan.ca.game_watch.Fragments.NBAFragment;
import kapoor.ishan.ca.game_watch.Fragments.NFLFragment;
import kapoor.ishan.ca.game_watch.Fragments.NHLFragment;
import kapoor.ishan.ca.game_watch.Fragments.SportFragment;

/**
 * Created by ishan on 2017-09-21.
 */

public class TabViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = TabViewPagerAdapter.class.getSimpleName();

    private final List<SportFragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();


    // ints to identify the app

    public void setupFragments(){
        addFragment(new NBAFragment(), "NBA");
        addFragment(new NHLFragment(), "NHL");
        addFragment(new NFLFragment(), "NFL");
        addFragment(new MLBFragment(), "MLB");
    }

    public TabViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title){
        fragmentList.add((SportFragment) fragment);
        fragmentTitleList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem()");
        return (Fragment)fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    public void notifyDateChanged(){
        for (SportFragment fragment: fragmentList){
            fragment.onDateChanged();
        }
    }

}
