package ca.dmdev.dicer;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton fabClear;
    private FloatingActionButton fabSave;
    private int currentTabIndex;

    private final int POSITION_ROLLER = 0;
    private final int POSITION_FAVOURITE = 1;
    private final int POSITION_HISTORY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide(); //hide that auction bar! only tabs, please
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

        fabClear = (FloatingActionButton) findViewById(android.R.id.content).findViewById(R.id.fab_clear);
        fabClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fabClearClicked();
            }
        });
        fabSave = (FloatingActionButton) findViewById(android.R.id.content).findViewById(R.id.fab_save);
        fabSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fabSaveClicked();
            }
        });

    }

    public void btnDiceOnClick(View v) {
        mSectionsPagerAdapter.rollerFragment.btnDiceOnClick(v);
    }

    private void fabClearClicked() {
        if (currentTabIndex == POSITION_ROLLER) { //clear the textview
            mSectionsPagerAdapter.rollerFragment.btnFABClear(findViewById(android.R.id.content).findViewById(R.id.fab_clear));
        }
        else if (currentTabIndex == POSITION_HISTORY) { //clear the history listview

        }
    }

    private void fabSaveClicked() {

        //open new intent activity to save the dice sequence

    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class RollerFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private TextView txtSequence;
        private Sequence sq;

        public RollerFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static RollerFragment newInstance() {
            RollerFragment fragment = new RollerFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.roller_main, container, false);

            txtSequence = (TextView) v.findViewById(R.id.txtSequence);
            sq = new Sequence();

            Button btn0 = (Button) v.findViewById((R.id.button0));
            btn0.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    n0Click();
                }
            });
            Button btn1 = (Button) v.findViewById((R.id.button1));
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    n1Click();
                }
            });
            Button btn2 = (Button) v.findViewById((R.id.button2));
            btn2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    n2Click();
                }
            });
            Button btn3 = (Button) v.findViewById((R.id.button3));
            btn3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    n3Click();
                }
            });
            Button btn4 = (Button) v.findViewById((R.id.button4));
            btn4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    n4Click();
                }
            });
            Button btn5 = (Button) v.findViewById((R.id.button5));
            btn5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    n5Click();
                }
            });
            Button btn6 = (Button) v.findViewById((R.id.button6));
            btn6.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    n6Click();
                }
            });
            Button btn7 = (Button) v.findViewById((R.id.button7));
            btn7.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    n7Click();
                }
            });
            Button btn8 = (Button) v.findViewById((R.id.button8));
            btn8.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    n8Click();
                }
            });
            Button btn9 = (Button) v.findViewById((R.id.button9));
            btn9.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    n9Click();
                }
            });
            Button btnPlus = (Button) v.findViewById((R.id.buttonPlus));
            btnPlus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    plusClick();
                }
            });
            Button btnMinus = (Button) v.findViewById((R.id.buttonMinus));
            btnMinus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    minusClick();
                }
            });
            Button btnRoll = (Button) v.findViewById((R.id.buttonRoll));
            btnRoll.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    sq.reRoll();

                    Dialog myDialog = new Dialog(v.getContext(), R.style.CustomDialogTheme);
                    myDialog.setContentView(R.layout.roll_dilogue);

                    TextView txtRollTotal = (TextView) myDialog.findViewById(R.id.txtRollTotal);
                    txtRollTotal.setText("Total: " + sq.lastTotal);

                    TextView txtSequenceData = (TextView) myDialog.findViewById(R.id.txtSequenceData);
                    txtSequenceData.setText("Sequence: " + sq.lastSequence);

                    myDialog.show();
                }
            });

            return v;
        }

        public void btnDiceOnClick(View view)
        {
            Button b = (Button)view;
            sq.addDice(new Dice(b.getText().toString()));
            txtSequence.setText(sq.toString());
        }

        public void btnFABClear(View view)
        {
            switch(view.getId())
            {
                case R.id.fab_clear:
                    sq.clear();
                    txtSequence.setText(sq.toString());
                    break;
            }
        }


        private void n0Click(){
            sq.addNum(0);
            txtSequence.setText(sq.toString());
        }
        private void n1Click(){
            sq.addNum(1);
            txtSequence.setText(sq.toString());
        }
        private void n2Click(){
            sq.addNum(2);
            txtSequence.setText(sq.toString());
        }
        private void n3Click(){
            sq.addNum(3);
            txtSequence.setText(sq.toString());
        }
        private void n4Click(){
            sq.addNum(4);
            txtSequence.setText(sq.toString());
        }
        private void n5Click(){
            sq.addNum(5);
            txtSequence.setText(sq.toString());
        }
        private void n6Click(){
            sq.addNum(6);
            txtSequence.setText(sq.toString());
        }
        private void n7Click(){
            sq.addNum(7);
            txtSequence.setText(sq.toString());
        }
        private void n8Click(){
            sq.addNum(8);
            txtSequence.setText(sq.toString());
        }
        private void n9Click(){
            sq.addNum(9);
            txtSequence.setText(sq.toString());
        }
        private void plusClick(){
            sq.addAction("+");
            txtSequence.setText(sq.toString());
        }
        private void minusClick(){
            sq.addAction("-");
            txtSequence.setText(sq.toString());
        }
        private void rollClick(){}

        public void clearSequence(){
            sq.clear();
        }
    }

    public static class FavouritesFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        public FavouritesFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static FavouritesFragment newInstance() {
            FavouritesFragment fragment = new FavouritesFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.favourites_layout, container, false);
            return rootView;
        }
    }

    public static class HistoryFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        public HistoryFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static HistoryFragment newInstance() {
            HistoryFragment fragment = new HistoryFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.favourites_layout, container, false);
            return rootView;
        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        RollerFragment rollerFragment;
        FavouritesFragment favouritesFragment;
        HistoryFragment historyFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a RollerFragment (defined as a static inner class below).

            if (position == 0) {
                rollerFragment = RollerFragment.newInstance();
                return rollerFragment;
            }
            else if (position == 1) {
                favouritesFragment = FavouritesFragment.newInstance();
                return favouritesFragment;
            }
            else {
                historyFragment = HistoryFragment.newInstance();
                return historyFragment;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Sequences";
                case 1:
                    return "Favourites";
                case 2:
                    return "History";
            }
            return null;
        }
    }

}
