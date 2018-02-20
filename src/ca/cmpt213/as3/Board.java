package ca.cmpt213.as3;

import java.util.Random;

public class Board {
    Unit[][] board;
    Fortress fortress;
    Tank[] tanks;
    private int numTanksAlive;

    private static final int SIZE_OF_TETROMINO = 4;

    private static final int ROWS = 10;
    private static final int COLS = 10;
    public Board() {
        board = new Unit[10][10];
        tanks = new Tank[5];
        numTanksAlive = 5;

        boolean isOccupied = false;
        boolean isVisible = false;

        for (Unit[] unitRow : board) {
            for (Unit unit : unitRow) {
                unit = new Unit(isOccupied, isVisible);
            }
        }

        for (Tank indivTank : tanks) {
            Random randX = new Random(System.currentTimeMillis());
            int x = randX.nextInt() % 10;
            Random randY = new Random(System.currentTimeMillis());
            int y = randY.nextInt() % 10;

            indivTank = new Tank(board, x, y);
        }
    }

    public Board(int numTanks) {
        board = new Unit[10][10];
        tanks = new Tank[numTanks];
        numTanksAlive = numTanks;

        boolean isOccupied = false;
        boolean isVisible = false;

        for (Unit[] unitRow : board) {
            for (Unit unit : unitRow) {
                unit = new Unit(isOccupied, isVisible);
            }
        }

        for (Tank indivTank : tanks) {
            Random randX = new Random(System.currentTimeMillis());
            int x = randX.nextInt() % 10;
            Random randY = new Random(System.currentTimeMillis());
            int y = randY.nextInt() % 10;

            indivTank = new Tank(board, x, y);
        }
    }

    public Board(int numTanks, String cheat) {
        board = new Unit[10][10];
        tanks = new Tank[numTanks];
        numTanksAlive = numTanks;

        boolean isOccupied = false;
        boolean isVisible = true;

        for (Unit[] unitRow : board) {
            for (Unit unit : unitRow) {
                unit = new Unit(isOccupied, isVisible);
            }
        }

        for (Tank indivTank : tanks) {
            Random randX = new Random(System.currentTimeMillis());
            int x = randX.nextInt() % 10;
            Random randY = new Random(System.currentTimeMillis());
            int y = randY.nextInt() % 10;

            indivTank = new Tank(board, x, y);
        }
    }

    public void takeTurn(int row, int col) {
        //take turns
        //enter row and col to find Unit[row][col]
        Unit destination = board[row][col];
        //fortress fires at coordinate
        if (fortress.fire(destination) == true) {
            Tank tank = tanks[getTankIndex(row, col)];
            tank.takeDamage();
            if(tank.isDestroyed()) {
                numTanksAlive--;
            }
        }
        //check if game is over.
        if (isGameOver() == true) {
            return;
        } else {
            //all tanks fire at fortress.
            for (Tank eachTank : tanks) {
                int damage = eachTank.dealDamage();
                fortress.takeDamage(damage);
            }
        }
    }

    public String getBoardState() {
        String boardState = "";

        boardState += " A B C D E F G H I J K\n";
        for(int i = 0; i < ROWS; i++) {
          boardState += i;
          for(int j = 0; j < COLS; j++) {
            if(board[i][j].getOccupier() && board[i][j].getVisibility()) {
              boardState += "X ";
            } else if(!board[i][j].getOccupier() && board[i][j].getVisibility()) {
              boardState += "  ";
            } else {
              boardState += ". ";
            }
          }
          boardState += "\n";
        }
        return boardState;
    }

    public Tank getOwner(int x, int y) {
      for(Tank tank: tanks) {
        for(int i = 0; i < SIZE_OF_TETROMINO; i++) {
          if(tank.getUnit(i) == board[x][y]) {
            return tank;
          }
        }
      }
      return null;
    }

    public boolean isGameOver() {
        //if fortress is dead or numTanksAlive is 0, return true.
        return fortress.isFortressDestroyed() || numTanksAlive == 0;
    }

    public boolean isPlayerWin() {
        return numTanksAlive == 0;
    }
}
