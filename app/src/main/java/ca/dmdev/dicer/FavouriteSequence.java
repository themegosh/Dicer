package ca.dmdev.dicer;

/**
 * Created by mathe_000 on 2015-12-01.
 */
public class FavouriteSequence extends Sequence {

    String title;

    public FavouriteSequence(){
        super();
    }

    public FavouriteSequence(String title){
        super();
        this.title = title;
    }

    public FavouriteSequence(Sequence sq, String title){
        super(sq.clone().getSq(), sq.getSequenceData(), sq.getTotal());
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
