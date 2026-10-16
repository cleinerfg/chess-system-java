package chess.system.application;

import chess.system.chess.ChessPiece;
import chess.system.chess.ChessPosition;
import chess.system.chess.Color;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.InputMismatchException;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UI {

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_BG_LIGHT = "\u001B[48;5;246m";
    public static final String ANSI_BG_DARK = "\u001B[48;5;238m";
    public static final String ANSI_BG_POSSIBLE_MOVE = "\u001B[48;2;122;154;96m";

    private static final String ANSI_PIECE_WHITE = "\u001B[1;38;5;255m";
    private static final String ANSI_PIECE_BLACK = "\u001B[1;38;5;232m";

    private static Scanner scanner = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }

    public static void nextLine() {
        scanner.nextLine();
    }

    public static ChessPosition readChessPosition() {
        try {
            String position = scanner.nextLine();
            char column = position.charAt(0);
            int row = Integer.parseInt(position.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Error reading ChessPosition. Valid values are a1 to h8.");
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        printBoard(pieces, null);
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces[i].length; j++) {
                boolean isPossibleMove = possibleMoves != null && possibleMoves[i][j];
                String background = getTileBackground(i, j, isPossibleMove);
                printPiece(pieces[i][j], background);
            }
            System.out.println();
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }

    private static String getTileBackground(int row, int col, boolean isPossibleMove) {
        if (isPossibleMove) return ANSI_BG_POSSIBLE_MOVE;
        return ((row + col) % 2 == 0) ? ANSI_BG_LIGHT : ANSI_BG_DARK;
    }

    private static void printPiece(ChessPiece piece, String background) {
        System.out.print(background);
        if (piece == null) {
            System.out.print("   " + ANSI_RESET);
        } else {
            String color = (piece.getColor() == Color.WHITE) ? ANSI_PIECE_WHITE : ANSI_PIECE_BLACK;
            System.out.print(" " + color + piece + ANSI_RESET + background + " " + ANSI_RESET);
        }
    }
}