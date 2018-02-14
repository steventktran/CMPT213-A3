package ca.cmpt213.as3;

public class Unit {
    private boolean isOccupied;
    private boolean isVisibile;

    public Unit(boolean isOccupied, boolean isVisible) {
        this.isOccupied = isOccupied;
        this.isVisibile = isVisible;
    }

    public void reveal() {
        isVisibile = true;
    }

    public boolean getOccupier() {
        return isOccupied;
    }

    public boolean getVisibility() {
        return isVisibile;
    }

    public void setOccupied() {
      isOccupied = true;
    }
}
