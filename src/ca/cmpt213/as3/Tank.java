package ca.cmpt213.as3;

public class Tank {
    private Tetromino tank;
    private int health;
    private int damage;
    private boolean isDestroyed;

    public Tank() {
        health = 4;
        damage = 20;
        isDestroyed = false;
    }

    public void takeDamage() {
        //takeDamage:
        //if health is greater than 0, health--
        //depending on health, reduce damage
        //if health is 0, isDestroyed is true
    }

    public int dealDamage() {
        //dealDamage
        //if tank isn't destroyed and health is greater than 0, return destroyed.
        return damage;
    }

    public void remove() {
        //remove
    }
}
