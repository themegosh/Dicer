package ca.dmdev.dicer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mathe_000 on 2015-12-01.
 */
public class FavouriteAdapter extends BaseAdapter{

    private ArrayList<FavouriteSequence> mDataset;
    private Activity activity;
    private TextView txtFavRollTotal;
    private TextView txtFavTitle;
    private TextView txtFavSequence;
    private TextView txtFavSequenceData;
    private ImageButton btnFavExpander;
    private RelativeLayout layoutHiddenSequence;

    public FavouriteAdapter(Activity activity, ArrayList<FavouriteSequence> mDataset){
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
        convertView=inflater.inflate(R.layout.favourites_row_layout, parent, false);
        txtFavRollTotal=(TextView) convertView.findViewById(R.id.txtFavRollTotal);
        txtFavTitle=(TextView) convertView.findViewById(R.id.txtFavTitle);
        txtFavSequence=(TextView) convertView.findViewById(R.id.txtFavSequence);
        txtFavSequenceData=(TextView) convertView.findViewById(R.id.txtFavSequenceData);
        btnFavExpander=(ImageButton) convertView.findViewById(R.id.btnFavExpander);
        layoutHiddenSequence=(RelativeLayout) convertView.findViewById(R.id.layoutHiddenSequence);

        txtFavRollTotal.setText(String.valueOf(mDataset.get(position).getTotal()));
        txtFavTitle.setText(String.valueOf(mDataset.get(position).getTitle()));
        txtFavSequence.setText(mDataset.get(position).toString());
        txtFavSequenceData.setText(mDataset.get(position).getSequenceData());

        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDataset.get(position).reRollShowPopup(v);
                notifyDataSetChanged();
            }
        });

        btnFavExpander.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RelativeLayout layoutHiddenSequence = (RelativeLayout) ((LinearLayout) ((ViewGroup) v.getParent()).getParent()).findViewById(R.id.layoutHiddenSequence);
                ImageButton imgBtn = (ImageButton) ((LinearLayout) ((ViewGroup) v.getParent()).getParent()).findViewById(R.id.btnFavExpander);
                if (layoutHiddenSequence.getVisibility() == View.GONE) {
                    layoutHiddenSequence.setVisibility(View.VISIBLE);
                    imgBtn.setImageResource(R.drawable.ic_arrow_up_black_md);
                } else {
                    layoutHiddenSequence.setVisibility(View.GONE);
                    imgBtn.setImageResource(R.drawable.ic_arrow_down_black_md);
                }
            }
        });

        return convertView;
    }
}