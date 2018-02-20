package ca.cmpt213.as3;

import java.sql.SQLOutput;
import java.util.Scanner;

import static java.lang.Integer.parseInt;


public class gameUI {
    public static void main(String[] args) throws Exception {
        Board board;
        Scanner scan = new Scanner(System.in);
        String input;
        int x;
        int y;
        int[] tankDamages;

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
            System.out.println("Fortress Structure Left: " + board.getFortressHealth());
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

            if (board.playerWin() == true) {
                //you win
                if (board.getHitStatus(x, y)) {
                    System.out.println("HIT!");
                } else {
                    System.out.println("Miss.");
                }

                System.out.println();
                System.out.println("Congratulations! You won.");
            } else {
                if (board.getHitStatus(x, y)) {
                    System.out.println("HIT!");
                } else {
                    System.out.println("Miss.");
                }
                //tanks shoot at you.
                tankDamages = board.getTankDamages();
                for (int i = 0; i < board.getNumTanks(); i++) {
                    if (tankDamages[i] != 0) {
                        System.out.println("Alive Tanks #" + (i + 1) + " of " + board.getNumTanks() + " hit you for " + tankDamages[i] + "!");
                    }
                }
                if (board.tanksWin() == true) {
                    //you lose
                    System.out.println();
                    System.out.println("You lost.");
                }
            }

            System.out.println();
        }

        System.out.println(board.getFinalBoardState());
        System.out.println("Fortress Structure Left: " + board.getFortressHealth());
    }
}
