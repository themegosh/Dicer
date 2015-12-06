package ca.dmdev.dicer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
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
    private ImageButton btnDelete;
    private RelativeLayout layoutHiddenSequence;
    private DatabaseConnector db;

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
        db = new DatabaseConnector(convertView.getContext());


        txtFavRollTotal=(TextView) convertView.findViewById(R.id.txtFavRollTotal);
        txtFavTitle=(TextView) convertView.findViewById(R.id.txtFavTitle);
        txtFavSequence=(TextView) convertView.findViewById(R.id.txtFavSequence);
        txtFavSequenceData=(TextView) convertView.findViewById(R.id.txtFavSequenceData);
        btnFavExpander=(ImageButton) convertView.findViewById(R.id.btnFavExpander);
        layoutHiddenSequence=(RelativeLayout) convertView.findViewById(R.id.layoutHiddenSequence);
        btnDelete=(ImageButton) convertView.findViewById(R.id.btnDeleteFav);

        txtFavRollTotal.setText(String.valueOf(mDataset.get(position).getTotal()));
        txtFavTitle.setText(String.valueOf(mDataset.get(position).getTitle()));
        txtFavSequence.setText(mDataset.get(position).toString());
        txtFavSequenceData.setText(mDataset.get(position).getSequenceData());

        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDataset.get(position).reRollShowPopup(v);
                ((MainActivity) v.getContext()).addSequenceToHistory(mDataset.get(position).clone()); //save this roll to the history
                db.open();
                db.updateSequence(mDataset.get(position).clone()); //save this roll to the history
                db.close();
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                db.open();
                                db.deleteContact(mDataset.get(position).getId()); //delete them
                                db.close();
                                mDataset.remove(position);
                                notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure you want to delete \"" + mDataset.get(position).getTitle() + "\"?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });

        return convertView;
    }
}