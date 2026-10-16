package chess.system.chess.pieces;

import chess.system.boardgame.Board;
import chess.system.boardgame.Position;
import chess.system.chess.ChessPiece;
import chess.system.chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    private boolean canMove(Position targetPosition) {
        ChessPiece piece = (ChessPiece) getBoard().getPiece(targetPosition);
        return piece == null || piece.getColor() != getColor();
    }

    private void checkDirection(boolean[][] moves, int rowOffset, int colOffset) {
        Position targetPosition = new Position(position.getRow() + rowOffset, position.getColumn() + colOffset);
        if (getBoard().positionExists(targetPosition) && canMove(targetPosition)) {
            moves[targetPosition.getRow()][targetPosition.getColumn()] = true;
        }
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        // Orthogonal moves (Above, Below, Left, Right)
        checkDirection(possibleMoves, -1, 0);
        checkDirection(possibleMoves, 1, 0);
        checkDirection(possibleMoves, 0, -1);
        checkDirection(possibleMoves, 0, 1);

        // Diagonal moves (Northwest, Northeast, Southwest, Southeast)
        checkDirection(possibleMoves, -1, -1);
        checkDirection(possibleMoves, -1, 1);
        checkDirection(possibleMoves, 1, -1);
        checkDirection(possibleMoves, 1, 1);

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "K";
    }
}