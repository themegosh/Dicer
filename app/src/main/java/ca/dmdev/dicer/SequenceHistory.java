package ca.dmdev.dicer;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Doug on 2015-11-29.
 */
public class SequenceHistory {
    private ArrayList<Sequence> history;

    public SequenceHistory(){
        history = new ArrayList<>();

    }

    public String getStr(int id) {
        Log.d("Dicer:", "SequenceHistory: getStr: lastTotal:" + String.valueOf(history.get(id).getLastTotal()) + " id: " + String.valueOf(id));
        return String.valueOf(history.get(id).getLastTotal());
    }

    public void addSequence(Sequence sq) {
        Log.d("Dicer:", "SequenceHistory: add: sq.toString():" + sq.toString() + " lastTotal: " + sq.getLastTotal() + " size: " + String.valueOf(history.size()));
        history.add(sq);
        Log.d("Dicer:", "SequenceHistory: AFTER add: sq.toString():" + history.get(history.size() - 1).toString() + " lastTotal: " + history.get(history.size() - 1).getLastTotal() + " size: " + String.valueOf(history.size()));
    }

    public void clear() {
        history.clear();
    }

    public void remove(int id) {
        history.remove(id);
    }

    public int size(){
        return history.size();
    }
}
