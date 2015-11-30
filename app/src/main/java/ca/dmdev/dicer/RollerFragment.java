package ca.dmdev.dicer;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        View v = inflater.inflate(R.layout.roller_main, container, false);

        txtSequence = (TextView) v.findViewById(R.id.txtSequence);
        sq = new Sequence();

        return v;
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
        sq.reRoll();

        Dialog myDialog = new Dialog(v.getContext(), R.style.CustomDialogTheme);
        myDialog.setContentView(R.layout.roll_dilogue);

        TextView txtRollTotal = (TextView) myDialog.findViewById(R.id.txtRollTotal);
        txtRollTotal.setText("Total: " + sq.lastTotal);

        TextView txtSequenceData = (TextView) myDialog.findViewById(R.id.txtSequenceData);
        txtSequenceData.setText("Sequence: " + sq.lastSequence);

        myDialog.show();
    }
    public void fabClearOnClick(View v) {
        sq.clear();
        txtSequence.setText(sq.toString());
    }
    public void fabSaveOnClick(View v) {
        sq.clear();
        txtSequence.setText(sq.toString());
    }
}
