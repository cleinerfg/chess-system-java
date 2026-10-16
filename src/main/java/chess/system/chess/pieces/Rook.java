package chess.system.chess.pieces;

import chess.system.boardgame.Board;
import chess.system.chess.ChessPiece;
import chess.system.chess.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return possibleMoves;
    }

    @Override
    public String toString() {
        return "R";
    }

}
