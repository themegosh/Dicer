package ca.dmdev.dicer;

import android.app.Activity;
import android.util.Log;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        convertView=inflater.inflate(R.layout.history_row_layout, parent, false);
        txtHistoryTotal=(TextView) convertView.findViewById(R.id.txtFavRollTotal);
        txtHistorySequence=(TextView) convertView.findViewById(R.id.txtRollHistorySequence);
        txtHistoryData=(TextView) convertView.findViewById(R.id.txtRollHistoryData);

        txtHistoryTotal.setText(String.valueOf(mDataset.get(position).getTotal()));
        if (mDataset.get(position).getClass() == FavouriteSequence.class)
            txtHistorySequence.setText(((FavouriteSequence)mDataset.get(position)).getTitle() + ": " + mDataset.get(position).toString());
        else
            txtHistorySequence.setText(mDataset.get(position).toString());
        txtHistoryData.setText(mDataset.get(position).getSequenceData());

        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Sequence sq = mDataset.get(position).clone(); //clone the tapped sequence
                mDataset.add(0, sq); //add it to the top
                sq.reRollShowPopup(v); //show the roll
                notifyDataSetChanged(); //update the list visually
            }
        });

        return convertView;
    }

}