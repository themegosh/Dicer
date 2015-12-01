package ca.dmdev.dicer;

import android.app.Activity;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Doug on 2015-11-30.
 */
public class HistoryAdapter extends BaseAdapter {
    private ArrayList<Sequence> mDataset;
    private Activity activity;
    private TextView txtHistoryTotal;
    private TextView txtHistorySequence;
    private TextView txtHistoryData;

    public HistoryAdapter(Activity activity, ArrayList<Sequence> mDataset){
        super();
        this.mDataset = mDataset;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){
            convertView=inflater.inflate(R.layout.history_row_layout, parent, false);

            txtHistoryTotal=(TextView) convertView.findViewById(R.id.txtRollHistoryTotal);
            txtHistorySequence=(TextView) convertView.findViewById(R.id.txtRollHistorySequence);
            txtHistoryData=(TextView) convertView.findViewById(R.id.txtRollHistoryData);
        }

        txtHistoryTotal.setText(String.valueOf(mDataset.get(position).getTotal()));
        txtHistorySequence.setText(mDataset.get(position).toString());
        txtHistoryData.setText(mDataset.get(position).getSequenceData());

        return convertView;
    }

}