package ca.dmdev.dicer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Doug on 2015-11-29.
 */
public class RollerFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    TextView txtSequence;
    private Sequence sq;
    static final String SEQUENCE = "R_SEQUENCE";

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
        FrameLayout v = new FrameLayout(getActivity());

        if (sq == null) //only set new sequence if we need to otherwise sq is saved from previous tab change
            sq = new Sequence();

        populateViewForOrientation(inflater, v);

        if (savedInstanceState != null && savedInstanceState.getStringArrayList(SEQUENCE) != null){
            for (int i = 0; i < savedInstanceState.getStringArrayList(SEQUENCE).size(); i++){
                sq.addAction(savedInstanceState.getStringArrayList(SEQUENCE).get(i));
            }
            txtSequence.setText(sq.toString());
        }

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        if (sq != null) {
            ArrayList<String> strSq = new ArrayList<>();
            for (int i = 0; i < sq.getSq().size(); i++) {
                strSq.add(sq.getSq().get(i).toString());
            }
            savedInstanceState.putStringArrayList(SEQUENCE, strSq);
        }

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        populateViewForOrientation(inflater, (ViewGroup) getView());
    }

    private void populateViewForOrientation(LayoutInflater inflater, ViewGroup viewGroup) {
        viewGroup.removeAllViewsInLayout();
        View subview = inflater.inflate(R.layout.roller_fragment, viewGroup);
        txtSequence = (TextView) subview.findViewById(R.id.txtSequence);
        txtSequence.setText(sq.toString());
        // Find your buttons in subview, set up onclicks, set up callbacks to your parent fragment or activity here.
    }

    public void btnDeleteOnClick(View view){
        if (sq.count() > 0) {
            sq.deleteLastAction();
            txtSequence.setText(sq.toString());
        }
    }
    public void btnActionOnClick(View view) {
        Button b = (Button)view;

        if (isNumeric(b.getText().toString())) {
            if (sq.getSq().size() > 0){
                if (isNumeric(sq.getSq().get(sq.getSq().size() - 1).toString())) {
                    if (sq.getSq().get(sq.getSq().size() - 1).toString().length() > 3) {
                        Toast.makeText(view.getContext(), "You cannot enter a number with more than 4 digits.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        }
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
                Toast.LENGTH_SHORT).show();
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
        if (resultCode == Activity.RESULT_OK) {

            sq = (Sequence)data.getSerializableExtra("Sequence");
            String title = data.getStringExtra("Title");

            //save to favourites tab
            ((MainActivity)getActivity()).addSequenceToFavourites(sq, title);

            Toast.makeText(getActivity().getApplicationContext(), "Dice sequence saved.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
