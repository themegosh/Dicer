package ca.dmdev.dicer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Doug on 2015-11-29.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    RollerFragment rollerFragment;
    FavouritesFragment favouritesFragment;
    HistoryFragment historyFragment;
    AboutFragment aboutFragment;

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
        else if (position == 2) {
            historyFragment = HistoryFragment.newInstance();
            return historyFragment;
        }
        else{
            aboutFragment = AboutFragment.newInstance();
            return aboutFragment;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
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
            case 3:
                return "About";
        }
        return null;
    }
}
