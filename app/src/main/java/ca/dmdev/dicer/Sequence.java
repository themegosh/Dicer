package ca.dmdev.dicer;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by mathe_000 on 2015-11-28.
 */
public class Sequence {
    ArrayList<Object> sq;
    String lastSequence = "";
    int lastTotal;

    public Sequence(){
        sq = new ArrayList<>();
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
            else { //last object was operand
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
            else //operand
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

    public String toString(){
        String str = "";

        for (int i = 0; i < sq.size(); i++)
            str += sq.get(i).toString();

        return str;
    }

    public void clear(){
        sq.clear();
        lastSequence = "";
        lastTotal = 0;
    }

    public void reRoll() {
        String operation = "+";
        lastTotal = 0;
        lastSequence = "";


        for (int i = 0; i < sq.size(); i++) {
            if (sq.get(i).getClass() == Integer.class) { //number
                if (operation == "+"){
                    lastTotal += (Integer) sq.get(i);
                    if (i > 0)
                        lastSequence += " + ";
                    lastSequence += Integer.valueOf((Integer) sq.get(i));
                }
                else {
                    lastTotal -= (Integer) sq.get(i);
                    if (i > 0)
                        lastSequence += " - ";
                    lastSequence += Integer.valueOf((Integer) sq.get(i));
                }
            }
            else if (sq.get(i).getClass() == Dice.class) {
                if (operation == "+") {
                    lastTotal += ((Dice) sq.get(i)).roll();
                    if (i > 0)
                        lastSequence += " + ";
                    lastSequence += ((Dice) sq.get(i)).getLastRoll();
                }
                else {
                    lastTotal -= ((Dice) sq.get(i)).roll();
                    if (i > 0)
                        lastSequence += " - ";
                    lastSequence += ((Dice) sq.get(i)).getLastRoll();
                }
            }
            else if (sq.get(i).getClass() == String.class) {
                    operation = ((String)sq.get(i));

            }
            else
                Log.d("Dicer CRITCAL ERRROR: ", "reRoll sq.get(i) resulted in non-operator, non-number, non-int, non-dice"); //this will probably crash
        }

    }

}
