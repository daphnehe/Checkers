//Daphne He
//Period 2
// 01/07/2022

import java.util.Scanner;
public class checkers {
    public static void main(String[] args) {
        pieces[][] board = new pieces[8][8];

        for (int rows = 0; rows < 8; rows++) {
            for (int columns = 0; columns < 8; columns++) {
                if (rows == 3 || rows == 4) {
                    board[rows][columns] = new pieces(rows, columns, false, " _ ");
                } else if (rows == 0 || rows == 2) {
                    if (columns % 2 == 1) {
                        board[rows][columns] = new pieces(rows, columns, false, " b ");
                    } else
                        board[rows][columns] = new pieces(rows, columns, false, " _ ");
                } else if (rows == 5 || rows == 7) {
                    if (columns % 2 == 0) {
                        board[rows][columns] = new pieces(rows, columns, false, " w ");
                    } else
                        board[rows][columns] = new pieces(rows, columns, false, " _ ");
                } else if (rows == 1) {
                    if (columns % 2 == 0) {
                        board[rows][columns] = new pieces(rows, columns, false, " b ");
                    } else
                        board[rows][columns] = new pieces(rows, columns, false, " _ ");
                } else if (rows == 6) {
                    if (columns % 2 == 1) {
                        board[rows][columns] = new pieces(rows, columns, false, " w ");
                    } else
                        board[rows][columns] = new pieces(rows, columns, false, " _ ");
                }
            }
        }
        int turn = 0;
        boolean p = true;
        while (p) {
            printBoard(board);
            correctPiece(turn, board);
            turn++;
            p = winner(board);
        }
    }
    public static void printBoard(pieces[][] board) {
        System.out.println(" 1  2  3  4  5  6  7  8 ");
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {
                if (board[a][b].kingStatus() == false) {
                    System.out.print(board[a][b].getColor());
                }
                else if (board[a][b].kingStatus() == true) {
                    if (board[a][b].getColor() == " b "){
                        System.out.print(" B ");
                    }
                    else if (board[a][b].getColor() == " w "){
                        System.out.print(" W ");
                    }
                    else if (board[a][b].getColor() == " _ "){
                        System.out.print(" _ ");
                    }
                }
                else {
                    System.out.print(" _ ");
                }
            }
            System.out.print(" " + (a + 1));
            System.out.println();
        }
    }

    public static void correctPiece(int turn, pieces[][] board){
        Scanner pencil = new Scanner(System.in);
        if (turn % 2 == 0) {
            System.out.println("Black's turn.");
        } else {
            System.out.println("White's turn.");
        }
        System.out.println("Enter the x coordinate of the piece you want to move from (1-8).");
        int x1;
        x1 = pencil.nextInt()-1;
        pencil.nextLine();
        System.out.println("Enter the y coordinate of the piece you want to move from (1-8).");
        int y1;
        y1 = pencil.nextInt()-1;
        pencil.nextLine();

        if (x1 < 0 || x1 > 7 ||y1 < 0 || y1 > 7 ){
            System.out.println("Outs of bounds! Try again.");
            correctPiece(turn, board);
        }
        else {
            if (turn % 2 != 0) {
                if (board[y1][x1].getColor() != " w ") {
                    System.out.println("Invalid! Choose a white piece.");
                    correctPiece(turn, board);
                } else if (board[y1][x1].getColor() == " w ") {
                    move(x1, y1, turn, board);
                }
            } else if (turn % 2 == 0) {
                if (board[y1][x1].getColor() != " b ") {
                    System.out.println("Invalid! Choose a black piece.");
                    correctPiece(turn, board);
                } else if (board[y1][x1].getColor() == " b ") {
                    move(x1, y1, turn, board);
                }
            }
        }
    }

    public static boolean OutOfBounds(int x1, int y1, int x2, int y2){
        if (x1 < 0 || x1 > 7 ||y1 < 0 || y1 > 7 || x2 < 0 || x2 > 7 ||y2 < 0 || y2 > 7){
            return true;
        }
        else {
            return false;
        }
    }

    public static void move(int x1, int y1, int turn, pieces[][] board){
        Scanner pencil = new Scanner(System.in);
        System.out.println("Enter the x coordinate of the piece you want to move to (1-8).");
        int x2;
        x2 = pencil.nextInt()-1;
        pencil.nextLine();
        System.out.println("Enter the y coordinate of the piece you want to move to (1-8).");
        int y2;
        y2 = pencil.nextInt()-1;
        pencil.nextLine();

        if(OutOfBounds(x1, y1, x2, y2) == true){
            System.out.println("Out of bounds. Try again.");
            correctPiece(turn, board);
        } else {

            if (board[y2][x2].getColor() == " _ " && checkValid(x1, y1, x2, y2, turn, board)) {

                if (board[y1][x1].kingStatus() == true) {
                    board[y2][x2].makeKing(true);
                    board[y1][x1].makeKing(false);
                } else if (board[y1][x1].kingStatus() == false) {
                    if (board[y1][x1].getColor() == " w " && y2 == 0) {
                        board[y2][x2].makeKing(true);
                    } else if (board[y1][x1].getColor() == " b " && y2 == 7) {
                        board[y2][x2].makeKing(true);
                    } else {
                        board[y2][x2].makeKing(false);
                    }
                }

                board[y1][x1].setColor(" _ ");

                if (turn % 2 == 0) {
                    board[y2][x2].setColor(" b ");
                } else if (turn % 2 != 0) {
                    board[y2][x2].setColor(" w ");
                }

            } else {
                System.out.println("Invalid move.");
                correctPiece(turn, board);
            }

            multikill(x2, y2, turn, board);

        }
    }

    public static boolean checkValid(int x1, int y1, int x2, int y2, int turn, pieces[][] board) {
        if (board[y1][x1].kingStatus() == false) {
            if (board[y1][x1].getColor() == " w " && y1 == y2 + 1 && (x1 == x2 + 1 || x1 == x2 - 1)) {
                return true;
            }
            else if (board[y1][x1].getColor() == " b " && y1 == y2 - 1 && (x1 == x2 - 1 || x1 == x2 + 1)) {
                return true;
            }
            else if (checkKill(x1, y1, x2, y2, turn, board)){
                eliminate(x1, y1, x2, y2, board);
                return true;
            }
            else{
                return false;
            }
        }
        else if (board[y1][x1].kingStatus() == true) {
            if (board[y1][x1].getColor() == " w " && y1 == y2 + 1 && (x1 == x2 + 1 || x1 == x2 - 1)) {
                return true;
            } else if (board[y1][x1].getColor() == " b " && y1 == y2 - 1 && (x1 == x2 - 1 || x1 == x2 + 1)) {
                return true;
            }
            else if (board[y1][x1].getColor() == " b " && y1 == y2 + 1 && (x1 == x2 + 1 || x1 == x2 - 1)) {
                return true;
            } else if (board[y1][x1].getColor() == " w " && y1 == y2 - 1 && (x1 == x2 - 1 || x1 == x2 + 1)) {
                return true;
            }
            else if (checkKill(x1, y1, x2, y2, turn, board)){
                eliminate(x1, y1, x2, y2, board);
                return true;
            } else{
                return false;
            }
        }
        else {
            return false;
        }

    }

    public static boolean checkKill(int x1, int y1, int x2, int y2, int turn, pieces[][] board){
        if (board[y1][x1].kingStatus() == false) {
            if (board[y1][x1].getColor() == " w " && y1 == y2 + 2 && (x1 == x2 + 2 || x1 == x2 - 2) && board[(y1 + y2) / 2][(x1 + x2) / 2].getColor() == " b " && board[y2][x2].getColor() == " _ ") {
                return true;
            } else if (board[y1][x1].getColor() == " b " && y1 == y2 - 2 && (x1 == x2 - 2 || x1 == x2 + 2) && board[(y1 + y2) / 2][(x1 + x2) / 2].getColor() == " w " && board[y2][x2].getColor() == " _ ") {
                return true;
            }
            else{
                return false;
            }
        }

        else if (board[y1][x1].kingStatus() == true){
            if (board[y1][x1].getColor() == " w " && y1 == y2 + 2 && (x1 == x2 + 2 || x1 == x2 - 2) && board[(y1 + y2) / 2][(x1 + x2) / 2].getColor() == " b " && board[y2][x2].getColor() == " _ ") {
                return true;
            } else if (board[y1][x1].getColor() == " b " && y1 == y2 - 2 && (x1 == x2 - 2 || x1 == x2 + 2) && board[(y1 + y2) / 2][(x1 + x2) / 2].getColor() == " w " && board[y2][x2].getColor() == " _ ") {
                return true;
            }else if (board[y1][x1].getColor() == " b " && y1 == y2 + 2 && (x1 == x2 + 2 || x1 == x2 - 2) && board[(y1 + y2) / 2][(x1 + x2) / 2].getColor() == " w " && board[y2][x2].getColor() == " _ ") {
                return true;
            } else if (board[y1][x1].getColor() == " w " && y1 == y2 - 2 && (x1 == x2 - 2 || x1 == x2 + 2) && board[(y1 + y2) / 2][(x1 + x2) / 2].getColor() == " b " && board[y2][x2].getColor() == " _ ") {
                return true;
            } else{
                return false;
            }
        }

        else {
            return false;
        }
    }

    public static void eliminate(int x1, int y1, int x2, int y2, pieces[][] board){
        board[(y1+y2)/2][(x1+x2)/2].setColor(" _ ");
    }

    public static boolean winner(pieces[][] board){
        int countWhite = 0;
        int countBlack = 0;

        for (int rows = 0; rows < 8; rows++) {
            for (int col = 0; col < 8; col++) {
                if (board[rows][col].getColor() == " b ") {
                    countBlack++;
                }
            }
        }

        for (int rows = 0; rows < 8; rows++) {
            for (int col = 0; col < 8; col++) {
                if (board[rows][col].getColor() == " w ") {
                    countWhite++;
                }
            }
        }

        if(countBlack == 0) {
            System.out.println("White wins!");
            return false;
        }
        else if (countWhite == 0) {
            System.out.println("Black wins!");
            return false;
        }
        else {
            return true;
        }
    }

    public static void multikill(int x, int y, int turn, pieces[][] board) {
        Scanner pencil = new Scanner(System.in);
        if (board[y][x].kingStatus() == false) {
            if (board[y][x].getColor() == " w ") {
                if (x > 1 && y > 1 && board[y - 1][x - 1].getColor() == " b " && board[y - 2][x - 2].getColor() == " _ ") {
                    System.out.println("If you just ate a piece, you can kill again. Select 1 if you can kill again. Select 0 if not.");
                    int i;
                    i = pencil.nextInt();
                    pencil.nextLine();
                    if (i == 1) {
                        move(x, y, turn, board);}
                } else if (x < 6 && y > 1 && board[y - 1][x + 1].getColor() == " b " && board[y - 2][x + 2].getColor() == " _ ") {
                    System.out.println("If you just ate a piece, you can kill again. Select 1 if you can kill again. Select 0 if not.");
                    int i;
                    i = pencil.nextInt();
                    pencil.nextLine();
                    if (i == 1) {
                        move(x, y, turn, board);}
                } else {
                }
            }
            else if (board[y][x].getColor() == " b "){
                if (x > 1 && y < 6 && board[y + 1][x - 1].getColor() == " w " && board[y + 2][x - 2].getColor() == " _ ") {
                    System.out.println("If you just ate a piece, you can kill again. Select 1 if you can kill again. Select 0 if not.");
                    int i;
                    i = pencil.nextInt();
                    pencil.nextLine();
                    if (i == 1) {
                        move(x, y, turn, board);}
                } else if (x < 6 && y < 6 && board[y + 1][x + 1].getColor() == " w " && board[y + 2][x + 2].getColor() == " _ ") {
                    System.out.println("If you just ate a piece, you can kill again. Select 1 if you can kill again. Select 0 if not.");
                    int i;
                    i = pencil.nextInt();
                    pencil.nextLine();
                    if (i == 1) {
                        move(x, y, turn, board);}
                } else {
                }
            }

        }
        else if (board[y][x].kingStatus() == true){

            if (board[y][x].getColor() == " w ") {
                if (x > 1 && y > 1 && board[y - 1][x - 1].getColor() == " b " && board[y - 2][x - 2].getColor() == " _ ") {
                    System.out.println("If you just ate a piece, you can kill again. Select 1 if you can kill again. Select 0 if not.");
                    int i;
                    i = pencil.nextInt();
                    pencil.nextLine();
                    if (i == 1) {
                        move(x, y, turn, board);}
                } else if (x < 6 && y > 1 && board[y - 1][x + 1].getColor() == " b " && board[y - 2][x + 2].getColor() == " _ ") {
                    System.out.println("If you just ate a piece, you can kill again. Select 1 if you can kill again. Select 0 if not.");
                    int i;
                    i = pencil.nextInt();
                    pencil.nextLine();
                    if (i == 1) {
                        move(x, y, turn, board);}
                } else if (x > 1 && y < 6 && board[y + 1][x - 1].getColor() == " b " && board[y + 2][x - 2].getColor() == " _ ") {
                    System.out.println("If you just ate a piece, you can kill again. Select 1 if you can kill again. Select 0 if not.");
                    int i;
                    i = pencil.nextInt();
                    pencil.nextLine();
                    if (i == 1) {
                        move(x, y, turn, board);}
                } else if (x < 6 && y < 6 && board[y + 1][x + 1].getColor() == " b " && board[y + 2][x + 2].getColor() == " _ ") {
                    System.out.println("If you just ate a piece, you can kill again. Select 1 if you can kill again. Select 0 if not.");
                    int i;
                    i = pencil.nextInt();
                    pencil.nextLine();
                    if (i == 1) {
                        move(x, y, turn, board);}
                } else {
                }

            }
            else if (board[y][x].getColor() == " b "){
                if (x > 1 && y < 6 && board[y + 1][x - 1].getColor() == " w " && board[y + 2][x - 2].getColor() == " _ ") {
                    System.out.println("If you just ate a piece, you can kill again. Select 1 if you can kill again. Select 0 if not.");
                    int i;
                    i = pencil.nextInt();
                    pencil.nextLine();
                    if (i == 1) {
                        move(x, y, turn, board);}
                } else if (x < 6 && y < 6 && board[y + 1][x + 1].getColor() == " w " && board[y + 2][x + 2].getColor() == " _ ") {
                    System.out.println("If you just ate a piece, you can kill again. Select 1 if you can kill again. Select 0 if not.");
                    int i;
                    i = pencil.nextInt();
                    pencil.nextLine();
                    if (i == 1) {
                        move(x, y, turn, board);}
                } else if (x > 1 && y > 1 && board[y - 1][x - 1].getColor() == " w " && board[y - 2][x - 2].getColor() == " _ ") {
                    System.out.println("If you just ate a piece, you can kill again. Select 1 if you can kill again. Select 0 if not.");
                    int i;
                    i = pencil.nextInt();
                    pencil.nextLine();
                    if (i == 1) {
                        move(x, y, turn, board);}
                } else if (x < 6 && y > 1 && board[y - 1][x + 1].getColor() == " w " && board[y - 2][x + 2].getColor() == " _ ") {
                    System.out.println("If you just ate a piece, you can kill again. Select 1 if you can kill again. Select 0 if not.");
                    int i;
                    i = pencil.nextInt();
                    pencil.nextLine();
                    if (i == 1) {
                        move(x, y, turn, board);}
                }
            }

        } else {

        }
    }

}