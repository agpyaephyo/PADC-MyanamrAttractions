package xyz.aungpyaephyo.padc.myanmarattractions.events;

/**
 * Created by aung on 6/17/17.
 */

public class SearchButtonClickedEvent {

    private long x;
    private long y;


    public SearchButtonClickedEvent(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }
}
