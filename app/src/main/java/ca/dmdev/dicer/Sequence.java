package ca.dmdev.dicer;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mathe_000 on 2015-11-28.
 */
public class Sequence implements Serializable {
    private ArrayList<Object> sq; //this is the object array of dice, numbers and operators, store sequentially
    private String sequenceData = "";
    private int lastTotal;

    //constructor
    public Sequence(){
        sq = new ArrayList<>();
    }

    public Sequence(ArrayList<Object> sq, String sequenceData, int lastTotal) {
        this.sq = sq;
        this.sequenceData = sequenceData;
        this.lastTotal = lastTotal;
    }

    public void addDice(Dice aDice) {
        if (sq.size() == 0){ //nothing in sequence yet
            sq.add(aDice);
        }
        else { //check if there is already a dice sequence
            if (sq.get(sq.size() - 1).getClass() == aDice.getClass()) { //if the last object was a dice
                Dice prev = (Dice) sq.get(sq.size() - 1); //the last dice added
                if (prev.getSides() == aDice.getSides()) { //were working on the same dice
                    prev.addCount(1); //increase count
                    sq.set(sq.size() - 1, prev); //update dice object
                }
                else { //new dice type
                    sq.add("+"); //addNum operator
                    sq.add(aDice);
                }
            }
            else if (sq.get(sq.size() - 1).getClass() == Integer.class){ //numbers added last
                aDice.addCount(((int) sq.get(sq.size() - 1)) - 1); //addNum the number to the dice count
                sq.set(sq.size() - 1, aDice); //update dice object
            }
            else { //last object was operator
                sq.add(aDice);
            }
        }
    }

    public void addNum(int num) {
        if (sq.size() == 0){ //nothing in sequence yet
            sq.add(num);
        }
        else { //check if there is already a dice sequence
            if (sq.get(sq.size() - 1).getClass() == Integer.class) { //if the last object is a number
                String concatNumber = sq.get(sq.size() - 1).toString() + String.valueOf(num);
                sq.set(sq.size() - 1, Integer.parseInt(concatNumber));

            }
            else if (sq.get(sq.size() - 1).getClass() == Dice.class){ //last object is dice
                sq.add("+");//addNum operator
                sq.add(num); //addNum it
            }
            else //operator
                sq.add(num);
        }
    }

    public void addAction(String action) {
        if (sq.size() > 0) {
            if (((String) sq.get(sq.size() - 1).toString() != "+") && ((String) sq.get(sq.size() - 1).toString() != "-")) {
                sq.add(action);
            }
        }
    }

    //clear the sequence (not re-rolled)
    public void clear(){
        sq.clear();
        sequenceData = "";
        lastTotal = 0;
    }

    //reroll the entire sequence, redoing totals and store the dice results in lastSequenceData
    public void reRoll() {
        String operation = "+";
        lastTotal = 0;
        sequenceData = "";

        //each object in the sequence
        for (int i = 0; i < sq.size(); i++) {
            if (sq.get(i).getClass() == Integer.class) { //number
                if (operation.equalsIgnoreCase("+")){
                    lastTotal += (Integer) sq.get(i);
                    if (i > 0)
                        sequenceData += " + ";
                    sequenceData += Integer.valueOf((Integer) sq.get(i));
                }
                else if (operation.equalsIgnoreCase("-")){
                    lastTotal -= (Integer) sq.get(i);
                    if (i > 0)
                        sequenceData += " - ";
                    sequenceData += Integer.valueOf((Integer) sq.get(i));
                }
                else
                    Log.d("Dicer CRITCAL ERRROR: ", "reRoll operation is NOT \"+\" or \"-\"... operation: " + operation); //this will probably crash
            }
            else if (sq.get(i).getClass() == Dice.class) { //Dice
                if (operation.equalsIgnoreCase("+")) {
                    lastTotal += ((Dice) sq.get(i)).roll();
                    if (i > 0)
                        sequenceData += " + ";
                    sequenceData += ((Dice) sq.get(i)).getLastRoll();
                }
                else if (operation.equalsIgnoreCase("-")){
                    lastTotal -= ((Dice) sq.get(i)).roll();
                    if (i > 0)
                        sequenceData += " - ";
                    sequenceData += ((Dice) sq.get(i)).getLastRoll();
                }
                else
                Log.d("Dicer CRITCAL ERRROR: ", "reRoll operation is NOT \"+\" or \"-\"... operation: " + operation); //this will probably crash
            }
            else if (sq.get(i).getClass() == String.class) { //an operator
                    operation = ((String)sq.get(i));

            }
            else
                Log.d("Dicer CRITCAL ERRROR: ", "reRoll sq.get(i) resulted in non-operator, non-number, non-int, non-dice"); //this will probably crash
        }

        Log.d("Dicer: ", "reRoll last sequence: " + sequenceData); //this will probably crash
    }

    public void reRollShowPopup(View v){
        reRoll();
        Dialog myDialog = new Dialog(v.getContext(), R.style.CustomDialogTheme);
        myDialog.setContentView(R.layout.roll_dilogue);

        TextView txtRollTotal = (TextView) myDialog.findViewById(R.id.txtRollTotal);
        TextView txtSequenceData = (TextView) myDialog.findViewById(R.id.txtSequenceData);

        txtRollTotal.setText(String.valueOf(lastTotal));
        txtSequenceData.setText(sequenceData);
        myDialog.show();
    }

    public int getTotal() {
        return lastTotal;
    }

    public String getSequenceData() {
        return sequenceData;
    }

    @Override
    public String toString(){
        String str = "";

        for (int i = 0; i < sq.size(); i++)
            str += sq.get(i).toString() + " ";

        return str;
    }

    public ArrayList<Object> getSq() {
        return sq;
    }

    public Sequence clone() {
        ArrayList<Object> clone = new ArrayList<Object>();
        for(Object item: sq){
            if (item.getClass() == Dice.class)
                clone.add(((Dice)item).clone());
            else if (item.getClass() == String.class)
                clone.add(item);
            else if (item.getClass() == Integer.class)
                clone.add(item);
        }
        return new Sequence(clone, sequenceData, lastTotal);
    }

    public void deleteLastAction(){
        sq.remove(sq.size() - 1);
    }

    public int count(){
        return sq.size();
    }
}
