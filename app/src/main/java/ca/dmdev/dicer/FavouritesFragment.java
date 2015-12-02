package ca.dmdev.dicer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Doug on 2015-11-29.
 */
public class FavouritesFragment extends Fragment {

    private ListView historyView;
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
        historyView = (ListView) v.findViewById(R.id.favouritesView);
        adapter = new FavouriteAdapter(getActivity(), sequenceFavourite);
        historyView.setAdapter(adapter);

        /*historyView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                Toast.makeText(getActivity().getApplicationContext(), "Click! position: " + position + " id: " + id + " view: " + view.getClass().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });*/



        return v;
    }
}
