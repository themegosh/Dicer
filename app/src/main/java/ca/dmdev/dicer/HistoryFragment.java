package ca.dmdev.dicer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Doug on 2015-11-29.
 */
public class HistoryFragment extends Fragment {

    private ListView historyView;
    private ArrayList<Sequence> sequenceHistory;
    private HistoryAdapter adapter;
    static final String H_TOTAL = "H_TOTAL";
    static final String H_SEQUENCE = "H_SEQUENCE";
    static final String H_DATA = "H_DATA";
    static private boolean orientChanged = false;

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
        adapter = new HistoryAdapter(getActivity(), sequenceHistory);
        historyView.setAdapter(adapter);

        if (savedInstanceState != null && orientChanged == false)
            loadSavedInstanceState(savedInstanceState);
        refreshList();
        return v;
    }

    public void loadSavedInstanceState(Bundle savedInstanceState){

        Sequence sq = new Sequence();
        //String sReceived = savedInstanceState.getString();

        String[][] arrayReceived=null;
        Object[] objectArray = (Object[]) savedInstanceState.getSerializable(H_SEQUENCE);
        if(objectArray!=null){
            arrayReceived = new String[objectArray.length][];
            for(int i=0;i<objectArray.length;i++){
                arrayReceived[i]=(String[]) objectArray[i];
            }
        }

        for (int i = 0; i < arrayReceived.length; i++) {
            sq = new Sequence();
            sq.setLastTotal(((int[]) savedInstanceState.getSerializable(H_TOTAL))[i]);
            sq.setSequenceData(((String[]) savedInstanceState.getSerializable(H_DATA))[i]);
            int x = 0;
            while (arrayReceived[i][x] != null){
                sq.addAction(arrayReceived[i][x]);
                Log.d("DICER", "sq.arrayReceived[" + i + "][" + x + "]: " + arrayReceived[i][x]);
                orientChanged = true;
                x++;
            }
            sequenceHistory.add(sq.clone());

            sq = null;
        }

        /*List<String[]> list = Arrays.asList(arrayReceived);
        List result = new ArrayList();
        for(String[] array : list){
            result.add( Arrays.asList(array) );
        }

        for (int i = 0; i < list.size(); i++) {
            sq = new Sequence();
            sq.setLastTotal(((int[]) savedInstanceState.getSerializable(H_TOTAL))[i]);
            sq.setSequenceData(((String[]) savedInstanceState.getSerializable(H_DATA))[i]);
            for (int x = 0; x < list.get(i).length; x++){
                if (list.get(i)[x] != null)
                    sq.addAction(list.get(i)[x]);
            }
            ((MainActivity) getActivity()).getSequenceHistory().add(sq.clone());
            sq = null;
        }*/
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state

        String[][] lstActions = new String[sequenceHistory.size()][200];
        String[] lstData = new String[sequenceHistory.size()];
        int[] lstTotals = new int[sequenceHistory.size()];
        for (int i = 0; i < sequenceHistory.size(); i++){
            lstTotals[i] = sequenceHistory.get(i).getTotal();
            lstData[i] = sequenceHistory.get(i).getSequenceData();
            for (int y = 0; y < sequenceHistory.get(i).getSq().size(); y++){
                lstActions[i][y] = sequenceHistory.get(i).toString();
            }
        }

        savedInstanceState.putSerializable(H_SEQUENCE, lstActions);
        savedInstanceState.putSerializable(H_TOTAL, lstTotals);
        savedInstanceState.putSerializable(H_DATA, lstData);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void refreshList(){
        if (adapter == null)
            adapter = new HistoryAdapter(getActivity(), sequenceHistory);
        adapter.notifyDataSetChanged();
    }

}
