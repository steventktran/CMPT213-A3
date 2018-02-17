package ca.cmpt213.as3;

import java.sql.SQLOutput;
import java.util.Scanner;

import static java.lang.Integer.parseInt;


public class gameUI {
    public static void main(String[] args) {
        Board board;
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

        if(board.getIsCheat()) {
            System.out.println(board.getFinalBoardState());
        }

        while(!board.isGameOver()) {
            input = scan.nextLine().toLowerCase().trim();
            while(input.length() > 3 || input.length() <= 1) {
                System.out.println("Invalid input. Please enter a coordinate in the format 'A1', case-insensitive.\n");
                input = scan.nextLine().toLowerCase().trim();
            }
            x = (int)input.charAt(0) - 'a';
            y = parseInt(input.substring(1)) - 1;

            System.out.println("x: " + x + "y: " + y + "\n");
            board.takeTurn(x, y);
            System.out.println(board.getBoardState());
        }

        System.out.println(board.getFinalBoardState());
    }
}
