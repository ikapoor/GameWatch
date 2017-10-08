package kapoor.ishan.ca.game_watch;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.Calendar;

import kapoor.ishan.ca.game_watch.Adapters.TabViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    Toolbar myToolbar;
    TabLayout myTablayout;
    ViewPager viewPager;
    int year;
    int month;
    int date;
    private String currFullDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myTablayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());
        adapter.setupFragments();
        viewPager.setAdapter(adapter);
        myTablayout.setupWithViewPager(viewPager);
        final Calendar calendar = Calendar.getInstance();


        /*year = calendar.get(calendar.YEAR);
        month = calendar.get(calendar.MONTH);
        date = calendar.get(calendar.DAY_OF_MONTH);
        currFullDate = parseDate(year, month, date);*/
        currFullDate = "20161225";

    }


    public static String parseDate(int year, int month, int date){
        String Y;       // year
        String M;       //month
        String D;       // date
        if (month+1 < 10){M = "0" + Integer.toString(month+1);}
        else{M = Integer.toString(month+1);}
        if (date<10){D = "0" + Integer.toString(date);}
        else {D = Integer.toString(date);}
        Y = Integer.toString(year);
        return Y+M+D;

    }

    public String getCurrFullDate(){
        return this.currFullDate;
    }
}
