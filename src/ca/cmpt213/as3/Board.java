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

    public String getBoardState() {
        String boardState = "";
        //turn Board state into string
        return boardState;
    }

    public void takeTurn(int row, int col) {
        //take turns
    }

    public boolean isGameOver() {
        //if fortress is dead or numTanksAlive is 0, return true.
        return true;
    }
}
