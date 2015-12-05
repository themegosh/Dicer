package ca.dmdev.dicer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Doug on 2015-11-29.
 */
public class RollerFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private TextView txtSequence;
    private Sequence sq;

    public RollerFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RollerFragment newInstance() {
        RollerFragment fragment = new RollerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.roller_fragment, container, false);

        txtSequence = (TextView) v.findViewById(R.id.txtSequence);
        sq = new Sequence();

        ImageButton btnSaveRoll = (ImageButton) v.findViewById(R.id.btnDelete);
        btnSaveRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDelete(view);
            }
        });

        return v;
    }

    public void btnDelete(View view) {
        if (sq.count() > 0) {
            sq.deleteLastAction();
            txtSequence.setText(sq.toString());
        }
    }

    public void btnDiceOnClick(View view) {
        Button b = (Button)view;
        sq.addDice(new Dice(b.getText().toString()));
        txtSequence.setText(sq.toString());
    }
    public void btnNumOnClick(View v) {
        Button b = (Button)v;
        sq.addNum(Integer.parseInt(b.getText().toString()));
        txtSequence.setText(sq.toString());
    }
    public void btnOperandOnClick(View v) {
        Button b = (Button)v;
        sq.addAction(b.getText().toString());
        txtSequence.setText(sq.toString());
    }
    public void btnRollOnClick(View v) {
        sq.reRollShowPopup(v);
        ((MainActivity) getActivity()).addSequenceToHistory(sq.clone()); //add sequence to history
    }
    public void fabClearOnClick(View v) {
        sq.clear();
        txtSequence.setText(sq.toString());

        Toast.makeText(getActivity().getApplicationContext(), "Dice sequence cleared.",
                Toast.LENGTH_LONG).show();
    }
    public void fabSaveOnClick(View v) {
        if (txtSequence.getText().toString().isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "You must enter a sequence before saving.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Intent myIntent = new Intent(getActivity(), SaveFavourite.class);
            myIntent.putExtra("Sequence", sq);
            startActivityForResult(myIntent, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        //if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {

                sq = (Sequence)data.getSerializableExtra("Sequence");
                String title = data.getStringExtra("Title");

                ((MainActivity)getActivity()).addSequenceToFavourites(sq, title);
                //todo save to favourites tab

                Toast.makeText(getActivity().getApplicationContext(), "Dice sequence saved.",
                        Toast.LENGTH_LONG).show();
            }
        //}
    }
}
