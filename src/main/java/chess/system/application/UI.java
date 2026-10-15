package chess.system.application;

import chess.system.chess.ChessPiece;
import chess.system.chess.Color;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UI {

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_BG_LIGHT = "\u001B[48;5;246m";
    public static final String ANSI_BG_DARK = "\u001B[48;5;238m";

    private static final String ANSI_PIECE_WHITE = "\u001B[1;38;5;255m";
    private static final String ANSI_PIECE_BLACK = "\u001B[1;38;5;232m";

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces[i].length; j++) {
                String background = ((i + j) % 2 == 0) ? ANSI_BG_LIGHT : ANSI_BG_DARK;
                printPiece(pieces[i][j], background);
            }
            System.out.println();
        }
        System.out.println("   a  b  c  d  e  f  g  h");
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