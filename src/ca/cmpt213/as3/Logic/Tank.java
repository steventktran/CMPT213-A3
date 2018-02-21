package ca.cmpt213.as3.Logic;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Tank {
    private Unit[] tetromino;
    private int health;
    private int damage;
    private boolean isDestroyed;

    private static final int DAMAGE_DROPOFF_FACTOR = 4;
    private static final int UNUSABLE_SPACE_THRESHOLD = 15;
    //Unusable spaces were not checked perfectly so would run into infinite loop if UNUSABLE_SPACE_THRESHOLD were perfect (only <= 3 spaces can be unusable)
    public Tank(Unit[][] board, int x, int y) throws Exception {
        health = 4;
        damage = 20;
        isDestroyed = false;
        tetromino = new Unit[4];
        createTetromino(board, x, y);

    }

    public void createTetromino(Unit[][] board, int x, int y) throws Exception {
        //Array list of Units called possibleSpaces
        //current number of Units is initialized to 0.
        //currentX is x value entered, currentY is y value entered.
        //currentUnit is set to null
        //an array of integers called indexes; array size 2.
        List<Unit> possibleSpaces = new ArrayList<>();
        int numUnits = 0;
        int currentX = x;
        int currentY = y;
        Unit currentUnit;
        int[] indexes = new int[2];
        List<Unit> tempTetromino = new ArrayList<>();

        //add current unit at currentX and currentY to possibleSpaces.
        //an array of unit called tetromino which holds "one tank" is the current unit.
        //set the current unit to occupied.
        //remove current unit from possibleSpaces ???
        //count of number of units increment by one.
        possibleSpaces.add(board[currentX][currentY]);
        tempTetromino.add(possibleSpaces.get(0));
        board[currentX][currentY].setOccupied();
        board[currentX][currentY].setUnusable();
        possibleSpaces.remove(0);
        numUnits++;

        //while there are 4 or less cells in tetromino,
        //as long as new possible units are inbound, and not occupied, add to possible spaces.
        while (numUnits < 4 && hasUsableSpaces(board)) {
            if (currentX + 1 < board.length && !board[currentX + 1][currentY].getOccupied() && !possibleSpaces.contains(board[currentX + 1][currentY])) {
                possibleSpaces.add(board[currentX + 1][currentY]);
            }
            if (currentX - 1 > 0 && !board[currentX - 1][currentY].getOccupied() && !possibleSpaces.contains(board[currentX - 1][currentY])) {
                possibleSpaces.add(board[currentX - 1][currentY]);
            }
            if (currentY + 1 < board[0].length && !board[currentX][currentY + 1].getOccupied() && !possibleSpaces.contains(board[currentX][currentY + 1])) {
                possibleSpaces.add(board[currentX][currentY + 1]);
            }
            if (currentY - 1 > 0 && !board[currentX][currentY - 1].getOccupied() && !possibleSpaces.contains(board[currentX][currentY - 1])) {
                possibleSpaces.add(board[currentX][currentY - 1]);
            }

            while (possibleSpaces.size() == 0 && hasUsableSpaces(board)) {
                for (Unit oldUnits : tempTetromino) {
                    oldUnits.setUnusable();
                    oldUnits.setUnoccupied();
                    oldUnits.unreveal();
                }
                tempTetromino.clear();
                numUnits = 0;

                currentX = (int) Math.floor(Math.random() * (board.length - 1));
                currentY = (int) Math.floor(Math.random() * (board[0].length - 1));
                if(!board[currentX][currentY].getOccupied()) {
                    possibleSpaces.add(board[currentX][currentY]);
                    tempTetromino.add(possibleSpaces.get(0));
                    board[currentX][currentY].setOccupied();
                    board[currentX][currentY].setUnusable();
                    possibleSpaces.remove(0);
                    numUnits++;


                    if (currentX + 1 < board.length && !board[currentX + 1][currentY].getOccupied() && !possibleSpaces.contains(board[currentX + 1][currentY])) {
                        possibleSpaces.add(board[currentX + 1][currentY]);
                    }
                    if (currentX - 1 > 0 && !board[currentX - 1][currentY].getOccupied() && !possibleSpaces.contains(board[currentX - 1][currentY])) {
                        possibleSpaces.add(board[currentX - 1][currentY]);
                    }
                    if (currentY + 1 < board[0].length && !board[currentX][currentY + 1].getOccupied() && !possibleSpaces.contains(board[currentX][currentY + 1])) {
                        possibleSpaces.add(board[currentX][currentY + 1]);
                    }
                    if (currentY - 1 > 0 && !board[currentX][currentY - 1].getOccupied() && !possibleSpaces.contains(board[currentX][currentY - 1])) {
                        possibleSpaces.add(board[currentX][currentY - 1]);
                    }
                } else {
                    board[currentX][currentY].setUnusable();
                }
            }

            //within an array of possible spaces, find a random index within size of arraylist,  set current unit to unit of that index of possible list.
            //add current unit to tetromino, set current x and y index to index of that board, set unit to occupy, increment numUnits, remove from possibleSpaces.

            currentUnit = possibleSpaces.get((int) (Math.random() * (possibleSpaces.size() - 1)));
            tempTetromino.add(currentUnit);
            indexes = getIndexOnBoard(board, currentUnit);
            currentX = indexes[0];
            currentY = indexes[1];
            board[currentX][currentY].setOccupied();
            board[currentX][currentY].setUnusable();
            numUnits++;
            possibleSpaces.remove(currentUnit);
        }
        if (tempTetromino.size() != 4 || !hasUsableSpaces(board)) {
            throw new Exception("Unable to add another tetromino.");
        }

        for (int i = 0; i < tetromino.length; i++) {
            tetromino[i] = tempTetromino.get(i);
        }
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public boolean hasUsableSpaces(Unit[][] board) {
        int numUsableSpaces = board.length * board[0].length;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (!board[i][j].getUsable()) {
                    numUsableSpaces--;
                }
            }
        }
        return numUsableSpaces > UNUSABLE_SPACE_THRESHOLD;
    }


    public int[] getIndexOnBoard(Unit[][] board, Unit searchTarget) {
        int[] indexes = new int[2];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (searchTarget == board[i][j]) {
                    indexes[0] = i;
                    indexes[1] = j;
                }
            }
        }
        return indexes;
    }

    public boolean containsUnit(Unit searchTarget) {
        for (Unit unit : tetromino) {
            if (unit == searchTarget) {
                return true;
            }
        }
        return false;
    }

    public Unit getUnit(int i) {
        if (i > 0 && i < tetromino.length) {
            return tetromino[i];
        }
        return null;
    }

    public int getDamage() {
        return damage;
    }

    public void takeDamage() {
        health--;
        damage = (int) Math.ceil((double) damage / DAMAGE_DROPOFF_FACTOR); // 20/4 = 5, 5/4 = 2, 2/4 = 1,
        if (health <= 0) {
            isDestroyed = true;
            damage = 0;
        }
    }

    public int dealDamage() {
        if (isDestroyed) {
            return 0;
        } else {
            return damage;
        }
    }
}
