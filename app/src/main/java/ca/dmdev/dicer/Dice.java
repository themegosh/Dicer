package ca.dmdev.dicer;

import android.util.Log;

import java.util.Random;

/**
 * Created by mathe_000 on 2015-11-28.
 */
public class Dice {
    private int sides;
    private int count;
    private String lastRoll = "";

    public Dice(int sides, int count){
        this.sides = sides;
        this.count = count;
    }

    public Dice (int sides){
        this.sides = sides;
        this.count = 1;
    }

    public Dice (String dStr){
        String strNums = "";
        boolean foundD = false;
        for (int i = 0; i < dStr.length(); i++){
            if (foundD)
                strNums += dStr.charAt(i);
            if (dStr.charAt(i) == 'd')
                foundD = true;

        }
        this.sides = Integer.parseInt(strNums);
        this.count = 1;
    }

    public void addCount(int by){
        count += by;
    }

    public int roll(){
        Random r = new Random();
        int total = 0;
        int curRoll = 0;
        lastRoll = " " + this.toString() + ": (";

        for (int i = 0; i < count; i++){
            curRoll = r.nextInt(sides) + 1;
            total += curRoll;
            lastRoll += String.valueOf(curRoll);
            if (i != (count - 1))
                lastRoll += ", ";
        }
        lastRoll += ") ";

        return total;
    }

    public String getLastRoll() {
        return lastRoll;
    }

    public String toString() {
        return count+"d"+sides;
    }

    public int getSides() {
        return sides;
    }

}
