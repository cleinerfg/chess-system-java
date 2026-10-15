package chess.system.chess.pieces;

import chess.system.boardgame.Board;
import chess.system.chess.ChessPiece;
import chess.system.chess.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }
}
