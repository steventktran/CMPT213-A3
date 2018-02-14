package ca.cmpt213.as3;

public class Board {
    Unit[][] board;
    Fortress fortress;
    Tank[] tank;
    private int numTanksAlive;

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

    public void tankPlacementAlgorithm() {
        //tank placement algorithm
    }

    public void takeTurn(int row, int col) {
        //take turns
        //enter row and col to find Unit[row][col]
        Unit destination = board[row][col];
        //fortress fires at coordinate
        fortress.fire(destination);
        //check if game is over.
        if (isGameOver() ==  true) {
            if (numTanksAlive == 0) {
                System.out.println("tanks lost");
            } else {
                System.out.println("fortress lost");
            }
        } else {
            //all tanks fire at fortress.
            for (Tank eachTank : tank) {
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
        //turn Board state into string
        return boardState;
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
