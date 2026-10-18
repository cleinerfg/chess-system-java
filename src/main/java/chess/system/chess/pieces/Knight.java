package chess.system.chess.pieces;

import chess.system.boardgame.Board;
import chess.system.boardgame.Position;
import chess.system.chess.ChessPiece;
import chess.system.chess.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    private boolean canMove(Position targetPosition) {
        ChessPiece piece = (ChessPiece) getBoard().getPiece(targetPosition);
        return piece == null || piece.getColor() != getColor();
    }

    private void checkMove(boolean[][] moves, int rowOffset, int colOffset) {
        Position targetPosition = new Position(position.getRow() + rowOffset, position.getColumn() + colOffset);
        if (getBoard().positionExists(targetPosition) && canMove(targetPosition)) {
            moves[targetPosition.getRow()][targetPosition.getColumn()] = true;
        }
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        // L-shaped moves (8 possible positions)
        checkMove(possibleMoves, -1, -2);
        checkMove(possibleMoves, -2, -1);
        checkMove(possibleMoves, -2, 1);
        checkMove(possibleMoves, -1, 2);
        checkMove(possibleMoves, 1, 2);
        checkMove(possibleMoves, 2, 1);
        checkMove(possibleMoves, 2, -1);
        checkMove(possibleMoves, 1, -2);

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "N";
    }
}