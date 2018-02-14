package ca.cmpt213.as3;

public class Tank {
    private Unit[] tetromino;
    private int health;
    private int damage;
    private boolean isDestroyed;

    private static final int DAMAGE_DROPOFF_FACTOR = 4;

    public Tank(Unit[][] board) {
      health = 4;
      damage = 20;
      isDestroyed = false;
      tetromino = new Unit[4];

    }

    public void takeDamage() {
        health--;
        damage = (int) Math.ceil(damage/DAMAGE_DROPOFF_FACTOR); // 20/4 = 5, 5/4 = 2, 2/4 = 1
        if(health <= 0) {
          isDestroyed = true;
        }
    }

    public int dealDamage() {
      if(isDestroyed) {
        return 0;
      } else {
        return damage;
      }
    }

    public void remove() {
        //remove
    }
}
