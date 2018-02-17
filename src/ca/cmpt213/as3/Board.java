package ca.cmpt213.as3;

import java.util.Random;

public class Board {
    Unit[][] board;
    Fortress fortress;
    Tank[] tanks;
    private int numTanksAlive;
    private boolean isCheat;

    private static final int ROWS = 10;
    private static final int COLS = 10;


    public Board() {
        board = new Unit[ROWS][COLS];
        tanks = new Tank[5];
        numTanksAlive = 5;
        isCheat = false;
        fortress = new Fortress();

        boolean isOccupied = false;
        boolean isVisible = false;

        for(int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Unit(isOccupied, isVisible);
            }
        }

        for(int i = 0; i < tanks.length; i++) {
            int x = (int) (Math.random() * (ROWS - 1));
            int y = (int) (Math.random() * (COLS - 1));

            while(board[x][y].getOccupier()) {
                x = (int) (Math.random() * (ROWS - 1));
                y = (int) (Math.random() * (COLS - 1));
            }

            System.out.println("x: " + x + ", y: " + y + "\n");
            tanks[i] = new Tank(board, x, y);
            System.out.println(getBoardState());
        }
    }

    public Board(int numTanks) {
        board = new Unit[ROWS][COLS];
        tanks = new Tank[numTanks];
        numTanksAlive = numTanks;
        isCheat = false;
        fortress = new Fortress();

        boolean isOccupied = false;
        boolean isVisible = false;

        for(int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Unit(isOccupied, isVisible);
            }
        }

        for(int i = 0; i < tanks.length; i++) {
            int x = (int) Math.random() * (ROWS - 1);
            int y = (int) Math.random() * (COLS - 1);
            tanks[i] = new Tank(board, x, y);
            System.out.println(getBoardState());
        }
    }

    public Board(int numTanks, String cheat) {
        board = new Unit[ROWS][COLS];
        tanks = new Tank[numTanks];
        numTanksAlive = numTanks;
        isCheat = true;
        fortress = new Fortress();

        boolean isOccupied = false;
        boolean isVisible = false;

        for(int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Unit(isOccupied, isVisible);
            }
        }

        for(int i = 0; i < tanks.length; i++) {
            int x = (int) Math.random() * (ROWS - 1);
            int y = (int) Math.random() * (COLS - 1);
            tanks[i] = new Tank(board, x, y);
            System.out.println(getBoardState());
        }
    }

    public boolean getIsCheat() {
        return isCheat;
    }

    public void takeTurn(int row, int col) {
        //take turns
        //enter row and col to find Unit[row][col]
        Unit destination = board[row][col];
        //fortress fires at coordinate
        if (fortress.fire(destination) == true) {
            tanks[getTankIndex(row, col)].dealDamage();
        }
        //check if game is over.
        if (isGameOver() ==  true) {
            if (numTanksAlive == 0) {
                System.out.println("tanks lost");
            } else {
                System.out.println("fortress lost");
            }
        } else {
            //all tanks fire at fortress.
            for (Tank eachTank : tanks) {
                int damage = eachTank.dealDamage();
                fortress.takeDamage(damage);
                //check if game is over.
                if (isGameOver() == true) {
                    if (numTanksAlive == 0) {
                        System.out.println("tanks lost");
                    } else {
                        System.out.println("fortress lost");
                    }
                }
            }
        }
    }

    public String getBoardState() {
        String boardState = "";

        boardState += "  1 2 3 4 5 6 7 8 9 10\n";
        for (int i = 0; i < ROWS; i++) {
            boardState += (char)('A' + i) + " ";
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].getOccupier() && board[i][j].getVisibility()) {
                    boardState += "X ";
                } else if (!board[i][j].getOccupier() && board[i][j].getVisibility()) {
                    boardState += "  ";
                } else {
                    boardState += "~ ";
                }
            }
            boardState += "\n";
        }
        return boardState;
    }

    public String getFinalBoardState() {
        String boardState = "";

        boardState += "  1 2 3 4 5 6 7 8 9 10\n";
        for (int i = 0; i < ROWS; i++) {
            boardState += (char) ('A' + i) + " ";
            for (int j = 0; j < COLS; j++) {
                int tankIndex = getTankIndex(i, j);
                if(tankIndex != -1) {
                    boardState += (char) ('A' + tankIndex) + " ";
                } else {
                    boardState += ". ";
                }
            }
            boardState += "\n";
        }
        return boardState;
    }

    public int getTankIndex(int x, int y) {
        for(int i = 0; i < tanks.length; i++) {
            if(tanks[i].containsUnit(board[x][y])) {
                return i;
            }
        }
        return -1;
    }

    public boolean isGameOver() {
        //if fortress is dead or numTanksAlive is 0, return true.
        if (fortress.isFortressDestroyed() == true) {
            return true;
        }

        if (numTanksAlive == 0) {
            return true;
        }

        return false;
    }
}
