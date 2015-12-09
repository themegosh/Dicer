package ca.dmdev.dicer;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
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
    private final int POSITION_ABOUT = 3;

    //vars
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int currentTabIndex;
    private ArrayList<Sequence> sequenceHistory;
    private ArrayList<FavouriteSequence> sequenceFavourites;
    DatabaseConnector dbC;

    public MainActivity(){
        sequenceHistory = new ArrayList<>();
        sequenceFavourites = new ArrayList<>();
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
                } else if (position == POSITION_HISTORY){
                    currentTabIndex = POSITION_HISTORY;
                    fabClear.show(); //show only clear all history button
                    fabSave.hide();
                } else if (position == POSITION_ABOUT) {
                    currentTabIndex = POSITION_ABOUT;
                    fabClear.hide(); //show nothing
                    fabSave.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // fix fragments crashing on orientation change!
        if (savedInstanceState != null) {
            mSectionsPagerAdapter.rollerFragment = (RollerFragment) getSupportFragmentManager().getFragment(
                    savedInstanceState, RollerFragment.class.getName());
            mSectionsPagerAdapter.favouritesFragment = (FavouritesFragment) getSupportFragmentManager().getFragment(
                    savedInstanceState, FavouritesFragment.class.getName());
            mSectionsPagerAdapter.historyFragment = (HistoryFragment) getSupportFragmentManager().getFragment(
                    savedInstanceState, HistoryFragment.class.getName());
        }
        if (mSectionsPagerAdapter.rollerFragment == null)
            mSectionsPagerAdapter.rollerFragment = new RollerFragment();
        if (mSectionsPagerAdapter.favouritesFragment == null)
            mSectionsPagerAdapter.favouritesFragment = new FavouritesFragment();
        if (mSectionsPagerAdapter.historyFragment == null)
            mSectionsPagerAdapter.historyFragment = new HistoryFragment();

        dbC = new DatabaseConnector(MainActivity.this);
        dbC.open();
        sequenceFavourites = dbC.getAllFavourites();
        dbC.close();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        getSupportFragmentManager()
                .putFragment(outState, RollerFragment.class.getName(), mSectionsPagerAdapter.rollerFragment);
    }

    //roll fragment buttons onClick triggers to fragment functions
    public void btnActionOnClick(View v) { mSectionsPagerAdapter.rollerFragment.btnActionOnClick(v); }
    public void btnRollOnClick(View v) { mSectionsPagerAdapter.rollerFragment.btnRollOnClick(v); }
    public void btnDeleteOnClick(View v) { mSectionsPagerAdapter.rollerFragment.btnDeleteOnClick(v); }

    //floating action buttons: save/clear
    public void fabClearOnClick(View v) {
        if (currentTabIndex == POSITION_ROLLER) { //clear the textview
            mSectionsPagerAdapter.rollerFragment.fabClearOnClick(v);
        }
        else if (currentTabIndex == POSITION_HISTORY) { //clear the history listview
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            sequenceHistory.clear();
                            mSectionsPagerAdapter.historyFragment.refreshList();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("Are you sure you want to clear all roll history?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }
    }
    public void fabSaveOnClick(View v) {
        if (currentTabIndex == POSITION_ROLLER) { //only call save when in the roller fragment
            mSectionsPagerAdapter.rollerFragment.fabSaveOnClick(v);
        }
    }

    public void addSequenceToHistory(Sequence sq) {
        sequenceHistory.add(0, sq);
        mSectionsPagerAdapter.historyFragment.refreshList();
    }


    public void addSequenceToFavourites(Sequence sq, String title){
        FavouriteSequence fs = new FavouriteSequence(sq, title);
        sequenceFavourites.add(fs);
        dbC.open();
        dbC.insertSequence(fs);
        dbC.close();
        mSectionsPagerAdapter.historyFragment.refreshList();
    }

    public ArrayList<Sequence> getSequenceHistory(){
        return sequenceHistory;
    }
    public ArrayList<FavouriteSequence> getSequenceFavourites(){
        return sequenceFavourites;
    }

}
