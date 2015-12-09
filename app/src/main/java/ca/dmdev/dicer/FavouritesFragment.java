package ca.dmdev.dicer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Doug on 2015-11-29.
 */
public class FavouritesFragment extends Fragment {

    private ListView favView;
    private TextView lblFav;
    private ArrayList<FavouriteSequence> sequenceFavourite;
    private FavouriteAdapter adapter;

    public FavouritesFragment() {
    }

    public static FavouritesFragment newInstance() {
        FavouritesFragment fragment = new FavouritesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favourites_fragment, container, false);

        sequenceFavourite = ((MainActivity) getActivity()).getSequenceFavourites(); //add sequence to history
        favView = (ListView) v.findViewById(R.id.lstvFavourites);
        lblFav = (TextView) v.findViewById(R.id.lblFavDescr);
        adapter = new FavouriteAdapter(getActivity(), sequenceFavourite);
        favView.setAdapter(adapter);
        favView.setEmptyView(lblFav);
        return v;
    }
}
