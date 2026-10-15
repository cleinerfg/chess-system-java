package chess.system.application;

import chess.system.chess.ChessMatch;

public class Program {

    static void main() {

        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());
    }
}
