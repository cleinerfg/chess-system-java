package chess.system.chess.pieces;

import chess.system.boardgame.Board;
import chess.system.chess.ChessPiece;
import chess.system.chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return possibleMoves;
    }
}
