package ca.cmpt213.as3;

public class Fortress {
    private int health;
    private boolean isDestroyed;

    public Fortress() {
        health = 1500;
        isDestroyed = false;
    }

    public int getHealth() {
        return health;
    }

    public boolean fire(Unit coordinate) {
        boolean hits = false;

        //fire at coordinate
        if (coordinate.getVisibility() == false) {
            coordinate.reveal();
            if (coordinate.getOccupier()) {
                hits = true;
            }
        }

        return hits;
    }

    public void takeDamage(int damage) {
        //if health is greater than 0, health - damage;
        //if resulting health is less than 0, just set to 0 and fortress is destroyed.
        if (health >= damage) {
            health -= damage;
        } else {
            health = 0;
        }

        if (health <= 0) {
          isDestroyed = true;
        }
    }

    //function here that returns true if fortress is destroyed.
    public boolean isFortressDestroyed() {
        return isDestroyed;
    }
}
