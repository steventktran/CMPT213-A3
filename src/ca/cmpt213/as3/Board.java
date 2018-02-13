package ca.cmpt213.as3;

public class Board {
    private int numTanksAlive;

    public Board() {
        numTanksAlive = 5;
    }

    public Board(int numTanks) {
        numTanksAlive = numTanks;
    }

    public Board(int numTanks, String cheat) {
        numTanksAlive = numTanks;
    }
}
