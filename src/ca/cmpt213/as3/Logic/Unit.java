package ca.cmpt213.as3.Logic;

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
