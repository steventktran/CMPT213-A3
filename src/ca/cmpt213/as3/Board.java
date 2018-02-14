package ca.cmpt213.as3;

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
        tank = new Tank[5];
        numTanksAlive = 5;
    }

    public Board(int numTanks) {
        board = new Unit[10][10];
        tank = new Tank[numTanks];
        numTanksAlive = numTanks;
    }

    public Board(int numTanks, String cheat) {
        board = new Unit[10][10];
        tank = new Tank[numTanks];
        numTanksAlive = numTanks;
    }

    public String getBoardState() {
        String boardState = "";

        boardState += " A B C D E F G H I J K\n";
        for(int i = 0; i < ROWS; i++) {
          boardState += i;
          for(int j = 0; j < COLS; j++) {
            if(board[i][j].isOccupied && board[i][j].isVisible) {
              boardState += "X ";
            } else if(!board[i][j].isOccupied && board[i][j].isVisible) {
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
      return NULL;
    }

    public void takeTurn(int row, int col) {
        //take turns
    }

    public boolean isGameOver() {
        //if fortress is dead or numTanksAlive is 0, return true.
        return true;
    }
}
