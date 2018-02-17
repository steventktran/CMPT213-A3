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
      createTetromino(board, x, y);
    }
    public void createTetromino(Unit[][] board, int x, int y) {
      List<Unit> possibleSpaces = new ArrayList<>();
      int numUnits = 0;
      int currentX = x;
      int currentY = y;
      Unit currentUnit;
      int[] indexes = new int[2];

      possibleSpaces.add(board[currentX][currentY]);
      tetromino[0] = possibleSpaces.get(0);
      board[currentX][currentY].setOccupied();
      possibleSpaces.remove(0);
      numUnits++;

      while(numUnits < 4) {
        if(currentX + 1 < board.length && !board[currentX + 1][currentY].getOccupier()) {
          possibleSpaces.add(board[currentX + 1][currentY]);
        }
        if(currentX - 1 > 0 && !board[currentX - 1][currentY].getOccupier()) {
          possibleSpaces.add(board[currentX - 1][currentY]);
        }
        if(currentY + 1 < board[0].length && !board[currentX][currentY + 1].getOccupier()) {
          possibleSpaces.add(board[currentX][currentY + 1]);
        }
        if(currentY - 1 > 0 && !board[currentX][currentY - 1].getOccupier()) {
          possibleSpaces.add(board[currentX][currentY - 1]);
        }

        if(possibleSpaces.size() == 0) {
            new Exception("Unable to create tetromino.");
        }
        currentUnit = possibleSpaces.get((int) Math.random() * (possibleSpaces.size() - 1));
        tetromino[numUnits] = currentUnit;
        indexes = getIndexOnBoard(board, currentUnit);
        currentX = indexes[0];
        currentY = indexes[1];
        board[currentX][currentY].setOccupied();
        numUnits++;
        possibleSpaces.remove(currentUnit);
      }
    }

    public int[] getIndexOnBoard(Unit[][] board, Unit searchTarget) {
      int[] indexes = new int[2];
      for(int i = 0; i < board.length; i++) {
        for(int j = 0; j < board[i].length; j++) {
          if(searchTarget == board[i][j]) {
            indexes[0] = i;
            indexes[1] = j;
          }
        }
      }
      return indexes;
    }

    public boolean containsUnit(Unit searchTarget) {
        for(Unit unit: tetromino) {
            if(unit == searchTarget) {
                return true;
            }
        }
        return false;
    }

    public Unit getUnit(int i) {
      if(i > 0 && i < tetromino.length) {
        return tetromino[i];
      }
      return null;
    }

    public void takeDamage() {
        health--;
        damage = (int) Math.ceil(damage/DAMAGE_DROPOFF_FACTOR); // 20/4 = 5, 5/4 = 2, 2/4 = 1,
        if(health <= 0) {
          isDestroyed = true;
          damage = 0;
        }
    }

    public int dealDamage() {
      if(isDestroyed) {
        return 0;
      } else {
        return damage;
      }
    }
}
