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
    protected ArrayList<Object> sq; //this is the object array of dice, numbers and operators, store sequentially
    protected String sequenceData = "";
    protected int lastTotal;
    protected long id;

    //constructor
    public Sequence(){
        sq = new ArrayList<>();
    }

    public Sequence(ArrayList<Object> sq, String sequenceData, int lastTotal, long id) {
        this.sq = sq;
        this.sequenceData = sequenceData;
        this.lastTotal = lastTotal;
        this.id = id;
    }

    //this function is where the magic happens
    //Processing an "action" in the following forms: "+", "-", "1", "1d5"
    //add each action to the sequence and cast the right object class
    public void addAction(String str){
        str = str.trim();
        boolean isDice = false;
        String firstHalf = "";
        String secondHalf = "";
        //determine what we have (dice, operator, or number)
        if (str.charAt(0) == '+' || str.charAt(0) == '-'){ //handle operator processing
            if (sq.size() > 0)
                if(!sq.get(sq.size() - 1).toString().equalsIgnoreCase("+") && !sq.get(sq.size() - 1).toString().equalsIgnoreCase("-")) //if this isn't the first action, and the last wasnt an operator
                    sq.add(str.charAt(0)); //add it
        } else { //either a dice or a number
            for (int i = 0; i < str.length(); i++) { //loop through to determine what it is
                if (str.charAt(i) == 'd') //we've got a dice
                    isDice = true;
                if (!isDice)
                    firstHalf += str.charAt(i); //concat the (num of dice OR int) together
                else
                    if (str.charAt(i) != 'd')
                        secondHalf += str.charAt(i);//concat the number of sides of the dice
            }
            if (isDice){ //handle dice processing
                if (firstHalf == "")
                    firstHalf = "1"; //default 1
                Dice aDice = new Dice(Integer.valueOf(firstHalf.trim()),Integer.valueOf(secondHalf.trim()));
                if (sq.size() == 0){ //nothing in sequence yet
                    sq.add(aDice);
                }
                else { //check if there is already a dice sequence
                    if (sq.get(sq.size() - 1).getClass() == Dice.class) { //if the last object was a dice
                        Dice prev = (Dice) sq.get(sq.size() - 1); //get the last dice added
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
            else { //handle number processing
                if (sq.size() == 0){ //nothing in sequence yet
                    sq.add(Integer.valueOf(str)); //add it
                }
                else {
                    if (sq.get(sq.size() - 1).getClass() == Integer.class) { //if the last object is a number
                        sq.set(sq.size() - 1, Integer.parseInt(sq.get(sq.size() - 1).toString() + firstHalf.trim())); //concat and convert to int, the last number, and the current number
                    }
                    else if (sq.get(sq.size() - 1).getClass() == Dice.class){ //last object is dice
                        sq.add("+"); //add an operator
                        sq.add(Integer.valueOf(str)); //addNum it
                    }
                    else //operator
                        sq.add(Integer.valueOf(str));
                }
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
        Character operation = '+';
        lastTotal = 0;
        sequenceData = "";

        //each object in the sequence
        for (int i = 0; i < sq.size(); i++) {
            if (sq.get(i).getClass() == Integer.class) { //number
                if (operation == '+'){
                    lastTotal += (Integer) sq.get(i);
                    if (i > 0)
                        sequenceData += " + ";
                    sequenceData += Integer.valueOf((Integer) sq.get(i));
                }
                else if (operation == '-'){
                    lastTotal -= (Integer) sq.get(i);
                    if (i > 0)
                        sequenceData += " - ";
                    sequenceData += Integer.valueOf((Integer) sq.get(i));
                }
                else
                    Log.d("Dicer CRITCAL ERRROR: ", "reRoll operation is NOT \"+\" or \"-\"... operation: " + operation); //this will probably crash
            }
            else if (sq.get(i).getClass() == Dice.class) { //Dice
                if (operation == '+') {
                    lastTotal += ((Dice) sq.get(i)).roll();
                    if (i > 0)
                        sequenceData += " + ";
                    sequenceData += ((Dice) sq.get(i)).getLastRoll();
                }
                else if (operation == '-'){
                    lastTotal -= ((Dice) sq.get(i)).roll();
                    if (i > 0)
                        sequenceData += " - ";
                    sequenceData += ((Dice) sq.get(i)).getLastRoll();
                }
                else
                Log.d("Dicer CRITCAL ERRROR: ", "reRoll operation is NOT \"+\" or \"-\"... operation: " + operation); //this will probably crash
            }
            else if (sq.get(i).getClass() == Character.class) { //an operator
                operation = (Character)sq.get(i);
            }
            else if (sq.get(i).getClass() == String.class) {
                operation = ((String)sq.get(i)).charAt(0);
            }
            else
                Log.d("Dicer CRITCAL ERRROR: ", "reRoll sq.get(i) resulted in non-operator, non-number, non-int, non-dice" + sq.get(i).getClass().toString()); //this will probably crash
        }
        Log.d("Dicer: ", "reRoll sq.toString" + sq.toString());
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

        for (int i = 0; i < sq.size(); i++) {
            str += sq.get(i).toString() + " ";
        }

        return str;
    }

    public ArrayList<Object> getSq() {
        return sq;
    }

    public Sequence clone() {
        ArrayList<Object> clone = new ArrayList<Object>();
        for(Object item: sq){
            clone.add(item);
        }
        return new Sequence(clone, sequenceData, lastTotal, id);
    }

    public void deleteLastAction(){
        sq.remove(sq.size() - 1);
    }

    public int count(){
        return sq.size();
    }

    public void setSq(ArrayList<Object> newSq){
        sq = newSq;
    }

    public void setSequenceData(String newSequenceData) {
        sequenceData = newSequenceData;
    }

    public void setLastTotal (int newLastTotal) {
        lastTotal = newLastTotal;
    }

    public void setId(long newId){
        id = newId;
    }

    public long getId(){
        return id;
    }


}
