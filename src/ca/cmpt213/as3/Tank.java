package ca.cmpt213.as3;

import java.util.ArrayList;
import java.util.List;

public class Tank {
    private Unit[] tetromino;
    private int health;
    private int damage;
    private boolean isDestroyed;

    private static final int DAMAGE_DROPOFF_FACTOR = 4;

    public Tank(Unit[][] board, int x, int y) {
      health = 4;
      damage = 20;
      isDestroyed = false;
      tetromino = new Unit[4];


    }
    public void createTank(Unit[][] board, int x, int y) {
      List possibleSpaces = new ArrayList<Unit>();

      int numUnits = 0;
      int currentX = x;
      int currentY = y;

      while(numUnits < 4) {
        tetromino[numUnits] = board[currentX][currentY];
        board[currentX][currentY].setOccupied();
        if(currentX + 1 <= board.size) {
          possibleSpaces.add(board[currentX + 1][currentY]);
        if(currentX - 1 >= 0) {
          possibleSpaces.add(board[currentX - 1][currentY]);
        }
        if(currentY + 1 <= board[0].size) {
          possibleSpaces.add(board[currentX][currentY + 1]);
        }
        if(currentY - 1 >= 0) {
          possibleSpaces.add(board[currentX][currentY - 1]);
        }
      }
    }

    public Unit getUnit(int i) {
      if(i > 0 && i < tetromino.size) {
        return tetromino[i];
      }
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
