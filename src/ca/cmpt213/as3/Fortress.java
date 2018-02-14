package ca.cmpt213.as3;

public class Fortress {
    private int health;

    public Fortress() {
        health = 1500;
    }

    public int getHealth() {
        return health;
    }

    public void fire(Unit coordinate) {
        //fire at coordinate
        if (coordinate.getVisibility() == false) {
            coordinate.reveal();

//            if (coordinate.getOccupier() == true) {
//
//            }
        }
    }

    public void takeDamage(int damage) {
        //if health is greater than 0, health - damage;
        //if resulting health is less than 0, just set to 0 and fortress is destroyed.
    }

    //function here that returns true if fortress is destroyed.
    public boolean isFortressDestroyed() {
        if (health == 0) {
            return true;
        }

        return false;
    }
}
