package ca.dmdev.dicer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Doug on 2015-11-29.
 */
public class HistoryFragment extends Fragment {

    private ListView historyView;
    private ArrayList<Sequence> sequenceHistory;

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
        View v = inflater.inflate(R.layout.history_fragment, container, false);

        sequenceHistory = ((MainActivity) getActivity()).getSequenceHistory(); //add sequence to history
        historyView = (ListView) v.findViewById(R.id.historyView);
        HistoryAdapter adapter = new HistoryAdapter(getActivity(), sequenceHistory);
        historyView.setAdapter(adapter);

        return v;
    }


}
