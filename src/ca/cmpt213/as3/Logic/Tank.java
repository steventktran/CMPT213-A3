package ca.cmpt213.as3.Logic;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Tank {
    private Unit[] tetromino;
    private int health;
    private int damage;
    private boolean isDestroyed;

    // 20/4 = 5, 5/4 = 2, 2/4 = 1,
    private static final int DAMAGE_DROPOFF_FACTOR = 4;
    //Unusable spaces were not checked perfectly so would run into infinite loop if UNUSABLE_SPACE_THRESHOLD were perfect (only <= 3 spaces can be unusable)
    private static final int UNUSABLE_SPACE_THRESHOLD = 15;


    public Tank(Unit[][] board, int x, int y) throws Exception {

        health = 4;
        damage = 20;
        isDestroyed = false;

        tetromino = new Unit[4];
        createTetromino(board, x, y);

    }


    public void createTetromino(Unit[][] board, int x, int y) throws Exception {

        List<Unit> PossibleTankCells = new ArrayList<>();
        int numUnitsInTetromino = 0;

        int currentX = x;
        int currentY = y;
        Unit currentUnit;

        int[] coordinatesCurrentUnit = new int[2];
        List<Unit> tempTetromino = new ArrayList<>();


        PossibleTankCells.add(board[currentX][currentY]);
        tempTetromino.add(PossibleTankCells.get(0));

        board[currentX][currentY].setOccupied();
        board[currentX][currentY].setUnusable();
        PossibleTankCells.remove(0);

        numUnitsInTetromino++;


        while (numUnitsInTetromino < 4 && hasUsableSpaces(board)) {

            if (currentX + 1 < board.length && !board[currentX + 1][currentY].getOccupied() && !PossibleTankCells.contains(board[currentX + 1][currentY])) {
                PossibleTankCells.add(board[currentX + 1][currentY]);
            }

            if (currentX - 1 > 0 && !board[currentX - 1][currentY].getOccupied() && !PossibleTankCells.contains(board[currentX - 1][currentY])) {
                PossibleTankCells.add(board[currentX - 1][currentY]);
            }

            if (currentY + 1 < board[0].length && !board[currentX][currentY + 1].getOccupied() && !PossibleTankCells.contains(board[currentX][currentY + 1])) {
                PossibleTankCells.add(board[currentX][currentY + 1]);
            }

            if (currentY - 1 > 0 && !board[currentX][currentY - 1].getOccupied() && !PossibleTankCells.contains(board[currentX][currentY - 1])) {
                PossibleTankCells.add(board[currentX][currentY - 1]);
            }


            while (PossibleTankCells.size() == 0 && hasUsableSpaces(board)) {

                for (Unit oldUnits : tempTetromino) {
                    oldUnits.setUnusable();
                    oldUnits.setUnoccupied();
                    oldUnits.unreveal();
                }

                tempTetromino.clear();
                numUnitsInTetromino = 0;

                currentX = (int) Math.floor(Math.random() * (board.length - 1));
                currentY = (int) Math.floor(Math.random() * (board[0].length - 1));

                if(!board[currentX][currentY].getOccupied()) {

                    PossibleTankCells.add(board[currentX][currentY]);
                    tempTetromino.add(PossibleTankCells.get(0));

                    board[currentX][currentY].setOccupied();
                    board[currentX][currentY].setUnusable();
                    PossibleTankCells.remove(0);

                    numUnitsInTetromino++;


                    if (currentX + 1 < board.length && !board[currentX + 1][currentY].getOccupied() && !PossibleTankCells.contains(board[currentX + 1][currentY])) {
                        PossibleTankCells.add(board[currentX + 1][currentY]);
                    }

                    if (currentX - 1 > 0 && !board[currentX - 1][currentY].getOccupied() && !PossibleTankCells.contains(board[currentX - 1][currentY])) {
                        PossibleTankCells.add(board[currentX - 1][currentY]);
                    }

                    if (currentY + 1 < board[0].length && !board[currentX][currentY + 1].getOccupied() && !PossibleTankCells.contains(board[currentX][currentY + 1])) {
                        PossibleTankCells.add(board[currentX][currentY + 1]);
                    }

                    if (currentY - 1 > 0 && !board[currentX][currentY - 1].getOccupied() && !PossibleTankCells.contains(board[currentX][currentY - 1])) {
                        PossibleTankCells.add(board[currentX][currentY - 1]);
                    }

                } else {
                    board[currentX][currentY].setUnusable();
                }
            }


            currentUnit = PossibleTankCells.get((int) (Math.random() * (PossibleTankCells.size() - 1)));
            tempTetromino.add(currentUnit);

            coordinatesCurrentUnit = getIndexOnBoard(board, currentUnit);

            currentX = coordinatesCurrentUnit[0];
            currentY = coordinatesCurrentUnit[1];

            board[currentX][currentY].setOccupied();
            board[currentX][currentY].setUnusable();

            numUnitsInTetromino++;

            PossibleTankCells.remove(currentUnit);

        }


        if (tempTetromino.size() != 4 || !hasUsableSpaces(board)) {
            throw new Exception("Unable to add another tetromino.");
        }


        for (int index = 0; index < tetromino.length; index++) {
            tetromino[index] = tempTetromino.get(index);
        }

    }


    public boolean isDestroyed() {

        return isDestroyed;
    }


    public boolean hasUsableSpaces(Unit[][] board) {

        int numUsableSpaces = board.length * board[0].length;

        for (int row = 0; row < board.length; row++) {

            for (int col = 0; col < board[0].length; col++) {

                if (!board[row][col].getUsable()) {
                    numUsableSpaces--;
                }
            }
        }

        return numUsableSpaces > UNUSABLE_SPACE_THRESHOLD;

    }


    public int[] getIndexOnBoard(Unit[][] board, Unit searchTarget) {

        int[] indexes = new int[2];

        for (int row = 0; row < board.length; row++) {

            for (int col = 0; col < board[row].length; col++) {

                if (searchTarget == board[row][col]) {
                    indexes[0] = row;
                    indexes[1] = col;
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
