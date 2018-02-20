package ca.cmpt213.as3;

import java.sql.SQLOutput;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class gameUI {
    public static Board board;

    public static void printFortressHealth() {
        System.out.println("Fortress Structure Left: " + board.getFortressHealth());
    }

    public static void printTankDamages() {
        int[] tankDamages;
        tankDamages = board.getTankDamages();
        for (int i = 0; i < board.getNumTanks(); i++) {
            if (tankDamages[i] != 0) {
                System.out.println("Alive Tanks #" + (i + 1) + " of " + board.getNumTanks() + " hit you for " + tankDamages[i] + "!");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String input;
        int x;
        int y;

        if (args.length > 2) {
            System.out.println("Invalid number of Arguments.");
        }

        if (args.length == 0) {
            //board has 5 tanks.
            board = new Board();
        } else if (args.length == 1) {
            //number of tanks is args[0]
            board = new Board(parseInt(args[0]));
        } else {
            //number of tanks is args[0]
            //if args[1] == cheat, call cheat board.
            board = new Board(parseInt(args[0]), args[1]);
        }

        if (board.getIsCheat()) {
            System.out.println(board.getFinalBoardState());
        }

        while (!board.isGameOver()) {
            System.out.println(board.getBoardState());
            printFortressHealth();
            System.out.print("Enter your move: ");
            input = scan.nextLine().toLowerCase().trim();
            while (input.length() == 3 && input.charAt(2) != '0') {
                System.out.println("Out of bounds. Please enter an x-coordinate between 0 and 10.");
                input = scan.nextLine().toLowerCase().trim();
            }
            while (input.length() <= 1 || input.charAt(0) < 'a' || input.charAt(0) > 'j') {
                System.out.println("Out of bounds. Please enter a y-coordinate between A and J, case-insensitive.");
                input = scan.nextLine().toLowerCase().trim();
            }
            while (input.length() > 3 || input.charAt(1) < '1' || input.charAt(1) > '9') {
                System.out.println("Invalid input. Please enter a coordinate in the format 'A1', case-insensitive.");
                input = scan.nextLine().toLowerCase().trim();
            }

            x = (int) input.charAt(0) - 'a';
            y = parseInt(input.substring(1)) - 1;

            board.takeTurn(x, y);

            if (board.getHitStatus(x, y)) {
                System.out.println("HIT!");
            } else {
                System.out.println("Miss.");
            }
            if (board.isPlayerWin()) {
                System.out.println(board.getBoardState());
                printFortressHealth();
                System.out.println("Congratulations! You won!");
                break;
            }

            printTankDamages();

            System.out.println();
        }

        if (!board.isPlayerWin()) {

            System.out.println("I'm sorry, your fortress has been smashed!");
        }

        System.out.println(board.getFinalBoardState());
        printFortressHealth();
    }
}
