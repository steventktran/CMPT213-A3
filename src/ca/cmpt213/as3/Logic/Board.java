package ca.cmpt213.as3.Logic;

public class Board {
    private Unit[][] board;
    private Fortress fortress;
    private Tank[] tanks;
    private int numTanksAlive;
    private boolean isCheat;

    private static final int ROWS = 10;
    private static final int COLS = 10;


    public Board() throws Exception {
        board = new Unit[ROWS][COLS];
        tanks = new Tank[5];
        numTanksAlive = 5;
        isCheat = false;
        fortress = new Fortress();

        boolean isOccupied = false;
        boolean isVisible = false;
        int x;
        int y;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Unit(isOccupied, isVisible);
            }
        }

        for (int i = 0; i < tanks.length; i++) {
            x = (int) (Math.random() * (ROWS - 1));
            y = (int) (Math.random() * (COLS - 1));

            while (board[x][y].getOccupied()) {
                x = (int) (Math.random() * (ROWS - 1));
                y = (int) (Math.random() * (COLS - 1));
            }
            tanks[i] = new Tank(board, x, y);
        }
    }

    public Board(int numTanks) throws Exception {
        board = new Unit[ROWS][COLS];
        tanks = new Tank[numTanks];
        numTanksAlive = numTanks;
        isCheat = false;
        fortress = new Fortress();

        boolean isOccupied = false;
        boolean isVisible = false;
        int x;
        int y;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Unit(isOccupied, isVisible);
            }
        }

        for (int i = 0; i < tanks.length; i++) {
            x = (int) (Math.random() * (ROWS - 1));
            y = (int) (Math.random() * (COLS - 1));

            while (board[x][y].getOccupied()) {
                x = (int) (Math.random() * (ROWS - 1));
                y = (int) (Math.random() * (COLS - 1));
            }

            tanks[i] = new Tank(board, x, y);
        }
    }

    public Board(int numTanks, String cheat) throws Exception {
        board = new Unit[ROWS][COLS];
        tanks = new Tank[numTanks];
        numTanksAlive = numTanks;
        isCheat = true;
        fortress = new Fortress();

        boolean isOccupied = false;
        boolean isVisible = false;
        int x;
        int y;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Unit(isOccupied, isVisible);
            }
        }
        System.out.println(tanks.length);
        for (int i = 0; i < tanks.length; i++) {
            x = (int) (Math.random() * (ROWS - 1));
            y = (int) (Math.random() * (COLS - 1));

            while (board[x][y].getOccupied()) {
                x = (int) (Math.random() * (ROWS - 1));
                y = (int) (Math.random() * (COLS - 1));
            }
            tanks[i] = new Tank(board, x, y);
        }
    }

    public boolean getIsCheat() {
        return isCheat;
    }

    public int getNumTanks() {
        return tanks.length;
    }

    public void takeTurn(int row, int col) {
        //take turns
        //enter row and col to find Unit[row][col]
        Unit destination = board[row][col];
        //fortress fires at coordinate
        if (fortress.fire(destination)) {
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

    public boolean isPlayerWin() {
        return numTanksAlive == 0;
    }

    public boolean isTankWin() {
        return getFortressHealth() == 0;
    }

    public boolean getHitStatus(int x, int y) {
        return board[x][y].getOccupied() && board[x][y].getVisibility();
    }

    public int[] getTankDamages() {
        int[] tankDamages = new int[tanks.length];
        for (int i = 0; i < tanks.length; i++) {
            tankDamages[i] = tanks[i].getDamage();
        }
        return tankDamages;
    }

    public int getFortressHealth() {
        return fortress.getHealth();
    }

    public String getBoardState() {
        String boardState = "";

        boardState += "  1 2 3 4 5 6 7 8 9 10\n";
        for (int i = 0; i < ROWS; i++) {
            boardState += (char) ('A' + i) + " ";
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].getOccupied() && board[i][j].getVisibility()) {
                    boardState += "X ";
                } else if (!board[i][j].getOccupied() && board[i][j].getVisibility()) {
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
                if (tankIndex != -1) {
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
        for (int i = 0; i < tanks.length; i++) {
            if (tanks[i].containsUnit(board[x][y])) {
                return i;
            }
        }
        return -1;
    }

    public boolean isGameOver() {
        //if fortress is dead or numTanksAlive is 0, return true.
        return fortress.isFortressDestroyed() || numTanksAlive == 0;
    }
}
