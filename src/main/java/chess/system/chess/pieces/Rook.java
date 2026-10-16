package chess.system.chess.pieces;

import chess.system.boardgame.Board;
import chess.system.boardgame.Position;
import chess.system.chess.ChessPiece;
import chess.system.chess.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        checkDirection(possibleMoves, -1, 0); // Above
        checkDirection(possibleMoves, 1, 0);  // Below
        checkDirection(possibleMoves, 0, -1); // Left
        checkDirection(possibleMoves, 0, 1);  // Right

        return possibleMoves;
    }

    private void checkDirection(boolean[][] mat, int rowOffset, int columnOffset) {
        Position p = new Position(position.getRow() + rowOffset, position.getColumn() + columnOffset);

        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + rowOffset, p.getColumn() + columnOffset);
        }

        if (getBoard().positionExists(p) && isThereAnOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
    }

    @Override
    public String toString() {
        return "R";
    }

}
