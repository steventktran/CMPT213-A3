package ca.cmpt213.as3.Logic;

/**
 * Object that represents a single space on the Fortress Defense board. This object keeps track of three states:
 *  Whether it can be used by the Tank's tetromino creation algorithm, determined by whether it is occupied, or if it is impossible
 *  to create a Tetromino based on its surrounding Units
 *  Whether it is occupied by a tank
 *  Whether its contents can be seen by the player
 */
public class Unit {
    private boolean isUsable;
    private boolean isOccupied;
    private boolean isVisibile;

    public Unit(boolean isOccupied, boolean isVisible) {
        
        this.isOccupied = isOccupied;
        this.isVisibile = isVisible;
        this.isUsable = true;
    }

    public void reveal() {

        isVisibile = true;
    }

    public void unreveal() {

        isVisibile = false;
    }

    public boolean getOccupied() {

        return isOccupied;
    }

    public boolean getVisibility() {

        return isVisibile;
    }

    public void setOccupied() {

        isOccupied = true;
    }

    public void setUnoccupied() {

        isOccupied = false;
    }

    public boolean getUsable() {

        return isUsable;
    }

    public void setUnusable() {

        isUsable = false;
    }
}
