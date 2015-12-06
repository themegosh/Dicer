package ca.dmdev.dicer;

import android.util.Log;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by mathe_000 on 2015-11-28.
 */
public class Dice implements Cloneable, Serializable {
    private int sides;
    private int count;
    private String lastRoll = "";

    public Dice(int count, int sides){
        this.sides = sides;
        this.count = count;
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
                lastRoll += " + ";
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

    @Override
    public Dice clone() {
        try {
            return (Dice)super.clone();
        }
        catch (Exception e) {
            return null;
        }
    }

}
