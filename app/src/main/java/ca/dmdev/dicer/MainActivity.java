package ca.dmdev.dicer;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //constants
    private final int POSITION_ROLLER = 0;
    private final int POSITION_FAVOURITE = 1;
    private final int POSITION_HISTORY = 2;

    //vars
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int currentTabIndex;
    private ArrayList<Sequence> sequenceHistory;

    public MainActivity(){
        sequenceHistory = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each tab
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                FloatingActionButton fabClear = (FloatingActionButton) findViewById(R.id.fab_clear);
                FloatingActionButton fabSave = (FloatingActionButton) findViewById(R.id.fab_save);

                if (position == POSITION_ROLLER) {
                    currentTabIndex = POSITION_ROLLER; //update current position
                    fabClear.show(); //show clear roll sequence
                    fabSave.show(); //show save sequence
                } else if (position == POSITION_FAVOURITE) { //update current position
                    currentTabIndex = POSITION_FAVOURITE;
                    fabClear.hide();
                    fabSave.hide();
                } else {
                    currentTabIndex = POSITION_HISTORY;
                    fabClear.show(); //show only clear all history button
                    fabSave.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //roll fragment buttons onClick triggers to fragment functions
    public void btnDiceOnClick(View v) { mSectionsPagerAdapter.rollerFragment.btnDiceOnClick(v); }
    public void btnNumOnClick(View v) { mSectionsPagerAdapter.rollerFragment.btnNumOnClick(v); }
    public void btnOperandOnClick(View v) { mSectionsPagerAdapter.rollerFragment.btnOperandOnClick(v); }
    public void btnRollOnClick(View v) { mSectionsPagerAdapter.rollerFragment.btnRollOnClick(v); }

    //floating action buttons: save/clear
    public void fabClearOnClick(View v) {
        if (currentTabIndex == POSITION_ROLLER) { //clear the textview
            mSectionsPagerAdapter.rollerFragment.fabClearOnClick(v);
        }
        else if (currentTabIndex == POSITION_HISTORY) { //clear the history listview

        }
    }
    public void fabSaveOnClick(View v) {
        if (currentTabIndex == POSITION_ROLLER) { //only call save when in the roller fragment
            mSectionsPagerAdapter.rollerFragment.fabSaveOnClick(v);
        }
    }

    public void addSequenceToHistory(Sequence sq) {
        sequenceHistory.add(sq);
        Log.d("Dicer:", "----BEGIN SEQUENCE DUMP----");
        for (int i = 0; i < sequenceHistory.size(); i++){
            Log.d("Dicer:", "sequenceHistory.get("+i+").getSq().toString():" + sequenceHistory.get(i).getSq().toString() + " sequenceHistory.get(i).toString(): " + sequenceHistory.get(i).toString());
        }
        Log.d("Dicer:", "----END SEQUENCE DUMP----");
    }
    public ArrayList<Sequence> getSequenceHistory(){
        return sequenceHistory;
    }

}
