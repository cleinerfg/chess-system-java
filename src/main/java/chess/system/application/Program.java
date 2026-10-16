package chess.system.application;

import chess.system.chess.ChessMatch;
import chess.system.chess.ChessPiece;
import chess.system.chess.ChessPosition;

public class Program {

    static void main() {
        
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            UI.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.println("Source: ");
            ChessPosition source = UI.readChessPosition();

            System.out.println();
            System.out.println("Target: ");
            ChessPosition target = UI.readChessPosition();

            ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

        }
    }
}
