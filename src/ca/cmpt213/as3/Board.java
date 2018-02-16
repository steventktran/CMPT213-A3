package ca.cmpt213.as3;

import java.util.Random;

public class Board {
    Unit[][] board;
    Fortress fortress;
    Tank[] tanks;
    private int numTanksAlive;
    private boolean isCheat;
    private static final int SIZE_OF_TETROMINO = 4;

    private static final int ROWS = 10;
    private static final int COLS = 10;


    public Board() {
        board = new Unit[ROWS][COLS];
        tanks = new Tank[5];
        numTanksAlive = 5;
        isCheat = false;

        boolean isOccupied = false;
        boolean isVisible = false;

        for (Unit[] unitRow : board) {
            for (Unit unit : unitRow) {
                unit = new Unit(isOccupied, isVisible);
            }
        }

        for (Tank indivTank : tanks) {
            Random randX = new Random(System.currentTimeMillis());
            int x = Math.abs(randX.nextInt() % 10);
            Random randY = new Random(System.currentTimeMillis());
            int y = Math.abs(randY.nextInt() % 10);
            System.out.println("x = " + x);
            System.out.println("y = " + y);
            indivTank = new Tank(board, x, y);
        }
    }

    public Board(int numTanks) {
        board = new Unit[ROWS][COLS];
        tanks = new Tank[numTanks];
        numTanksAlive = numTanks;
        isCheat = false;

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
        board = new Unit[ROWS][COLS];
        tanks = new Tank[numTanks];
        numTanksAlive = numTanks;
        isCheat = true;

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

        boardState += " A B C D E F G H I J K\n";
        for (int i = 0; i < ROWS; i++) {
            boardState += i;
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].getOccupier() && board[i][j].getVisibility()) {
                    boardState += "X ";
                } else if (!board[i][j].getOccupier() && board[i][j].getVisibility()) {
                    boardState += "  ";
                } else {
                    boardState += ". ";
                }
            }
            boardState += "\n";
        }
        return boardState;
    }

    public String getFinalBoardState() {
        String boardState = "";

        boardState += " A B C D E F G H I J K\n";
        for (int i = 0; i < ROWS; i++) {
            boardState += i;
            for (int j = 0; j < COLS; j++) {
                int tankIndex = getTankIndex(i, j);
                if(tankIndex != -1) {
                    boardState += ("A" + tankIndex) + " ";
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
