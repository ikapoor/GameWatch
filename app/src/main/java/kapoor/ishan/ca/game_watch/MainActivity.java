package kapoor.ishan.ca.game_watch;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import kapoor.ishan.ca.game_watch.Adapters.TabViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    Toolbar myToolbar;
    TabLayout myTablayout;
    ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myTablayout = (TabLayout)findViewById(R.id.tabLayout);
        myTablayout.addTab(myTablayout.newTab().setText("TAB 1"));
        myTablayout.addTab(myTablayout.newTab().setText("TAB 2"));
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(myTablayout));

        myTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
}
