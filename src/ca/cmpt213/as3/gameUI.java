package ca.cmpt213.as3;

public class gameUI {
    public static void main(String[] args) {
        if (args.length > 2) {
            System.out.println("Invalid number of Arguments.");
        }

        if (args.length == 0) {
            //board has 5 tanks.
        } else if (args.length == 1) {
            //number of tanks is args[0]
        } else {
            //number of tanks is args[0]
            //if args[1] == cheat, call cheat board.
        }
    }
}
