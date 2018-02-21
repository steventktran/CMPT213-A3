package ca.cmpt213.as3.UI;

import ca.cmpt213.as3.Logic.Board;

import java.util.Scanner;
import static java.lang.Integer.parseInt;

/**
 * Main class that handles UI of Fortress Defense game. Receives input through the user's keyboard command, makes a move.
 * It will then print output onto the terminal accordingly, depending on whether or not the game has finished, and if
 * the player won or the tanks won.
 */
public class gameUI {

    public static Board board;


    public static void printBoard() {

        System.out.println("Game Board: ");
        System.out.println(board.getBoardState());
        System.out.println("Fortress Structure Left: " + board.getFortressHealth());
    }

    public static void printFinalBoard() {

        System.out.println("Game Board: ");
        System.out.println(board.getFinalBoardState());
        System.out.println("Fortress Structure Left: " + board.getFortressHealth());
    }


    public static void printTankDamage() {

        int[] tankDamages;
        tankDamages = board.getTankDamages();

        for (int i = 0; i < board.getNumTanks(); i++) {

            if (tankDamages[i] != 0) {

                System.out.println("Alive Tanks #" + (i + 1) + " of " + board.getNumTanks() + " hit you for " + tankDamages[i] + "!");
            }
        }

    }


    public static void printIntro() {
        int numTanks = board.getNumTanks();
        if(numTanks == 1) {
            System.out.println( "Starting game with " + numTanks + " tank.");
        } else {
            System.out.println( "Starting game with " + numTanks + " tanks.");
        }
        System.out.println( "----------------------------   \n" +
                            "Welcome to Fortress Defense!   \n" +
                            "  By Lora Koo, Steven Tran     \n" +
                            "----------------------------");
    }


    public static void takeTurn() {
        Scanner scan = new Scanner(System.in);

        String input;
        int x;
        int y;

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

    }


    public static void main(String[] args) {

        try {

            if(args.length > 2) {
                throw new Exception();
            }

            if (args.length == 2 && args[1] != "--cheat") {
                throw new Exception();
            }

        } catch (Exception e){

            System.out.println( "Invalid arguments. Possible arguments are :\n" +
                                "\t a. No arguments                            \n" +
                                "\t b. Number of tanks to be placed on board   \n" +
                                "\t c. Number of tanks to be placed on board, '--cheat'");
            return;
        }

        try {

            if (args.length == 0) {

                //board has 5 tanks.
                board = new Board();

            } else if (args.length == 1) {

                //number of tanks is args[0]
                board = new Board(parseInt(args[0]));

            } else  if (args.length == 2 && args[1] == "--cheat"){

                //number of tanks is args[0]
                //if args[1] == cheat, call cheat board.
                board = new Board(parseInt(args[0]), args[1]);

            }

        } catch (Exception e){

            System.out.println("Error: Unable to place " + board.getNumTanks() + " tanks.");
            System.out.println("       Try running game again with fewer tanks.");

            return;
        }

        if (board.getIsCheat()) {

            printFinalBoard();
        }

        printIntro();

        while (!board.isGameOver()) {

            printBoard();
            takeTurn();

            if (board.isPlayerWin()) {

                System.out.println();
                System.out.println("Congratulations! You won.");

            } else {

                printTankDamage();

                if (board.isTankWin()) {

                    System.out.println();
                    System.out.println("You lost.");
                }
            }

            System.out.println();
        }

        printFinalBoard();
    }

}
