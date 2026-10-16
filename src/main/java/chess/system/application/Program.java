package chess.system.application;

import chess.system.chess.ChessMatch;
import chess.system.chess.ChessPiece;
import chess.system.chess.ChessPosition;

public class Program {

    static void main() {

        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces());
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition();

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition();

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                UI.nextLine();
            }
        }
    }
}