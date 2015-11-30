package ca.dmdev.dicer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Doug on 2015-11-29.
 */
public class HistoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SequenceHistory sequenceHistory;


    public HistoryFragment() {
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favourites_layout, container, false);


/*

        mRecyclerView = (RecyclerView) v.findViewById(R.id.historyRecycleView);
        if (mRecyclerView == null)
            Log.d("Dicer: ", "mRecyclerView: IS NULL ");

        sequenceHistory = ((MainActivity) getActivity()).getSequenceHistory(); //add sequence to history;

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerView.setHasFixedSize(false);

        // specify an adapter (see also next example)
        mAdapter = new HistoryAdapter(sequenceHistory);
        mRecyclerView.setAdapter(mAdapter);
*/
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        /*LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

        String[] dataset = new String[100];
        for (int i = 0; i < dataset.length; i++) {
            dataset[i] = "item" + i;
        }

        HistoryAdapter mAdapter = new HistoryAdapter(sequenceHistory);
        mRecyclerView.setAdapter(mAdapter);
        super.onViewCreated(view, savedInstanceState);*/

        TextView test = (TextView) view.findViewById(R.id.testTextView);
        test.setText("BACON!");
    }

}
