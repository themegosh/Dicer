package ca.dmdev.dicer;

import java.util.ArrayList;

/**
 * Created by mathe_000 on 2015-12-01.
 */
public class FavouriteSequence extends Sequence {

    String title;

    public FavouriteSequence(){
        super();
    }

    public FavouriteSequence(Sequence sq, String title){
        super(sq.clone().getSq(), sq.getSequenceData(), sq.getTotal(), sq.getId());
        this.title = title.trim();
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public FavouriteSequence clone() {
        Sequence temp = super.clone();
        return new FavouriteSequence(temp, title);
    }


}
