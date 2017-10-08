package kapoor.ishan.ca.game_watch;

import android.app.DatePickerDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import kapoor.ishan.ca.game_watch.Adapters.TabViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    public static final String TAG = MainActivity.class.getSimpleName();
    Toolbar myToolbar;
    TabLayout myTablayout;
    ViewPager viewPager;
    int year;
    int month;
    int date;
    TabViewPagerAdapter viewPagerAdapter;
    private String currFullDate;
    DatePickerDialog datePickerDialog;

    public String getCurrFullDate(){
        return this.currFullDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myTablayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter= new TabViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.setupFragments();
        viewPager.setAdapter(viewPagerAdapter);
        myTablayout.setupWithViewPager(viewPager);
        final Calendar calendar = Calendar.getInstance();


        year = calendar.get(calendar.YEAR);
        month = calendar.get(calendar.MONTH);
        date = calendar.get(calendar.DAY_OF_MONTH);
        currFullDate = Utils.parseDate(year, month, date);
        currFullDate = "20161225";
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendarDialog();
            }
        });

    }

    public void showCalendarDialog(){
        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    Log.d(TAG, "onDateSet");
                    year = i;
                    month = i1;
                    date = i2;
                    currFullDate = Utils.parseDate(year, month, date);
                    Log.d(TAG, "date: " + currFullDate);
                    viewPagerAdapter.notifyDateChanged();

                }
            }, year, month, date);
        }
        datePickerDialog.show();
    }






}
