package ca.dmdev.dicer;

import java.util.ArrayList;

/**
 * Created by Doug on 2015-11-29.
 */
public class SequenceHistory {
    private ArrayList<Sequence> history;

    public SequenceHistory(){
        history = new ArrayList<>();

    }


    public void addSequence(Sequence sq) {
        history.add(sq);
    }

    public void clear() {
        history.clear();
    }

    public void remove(int id) {
        history.remove(id);
    }
}
