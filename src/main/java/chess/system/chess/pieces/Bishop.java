package chess.system.chess.pieces;

import chess.system.boardgame.Board;
import chess.system.boardgame.Position;
import chess.system.chess.ChessPiece;
import chess.system.chess.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    private void checkDirection(boolean[][] moves, int rowOffset, int colOffset) {
        Position targetPosition = new Position(position.getRow() + rowOffset, position.getColumn() + colOffset);

        while (getBoard().positionExists(targetPosition) && !getBoard().thereIsAPiece(targetPosition)) {
            moves[targetPosition.getRow()][targetPosition.getColumn()] = true;
            targetPosition.setValues(targetPosition.getRow() + rowOffset, targetPosition.getColumn() + colOffset);
        }

        if (getBoard().positionExists(targetPosition) && isThereAnOpponentPiece(targetPosition)) {
            moves[targetPosition.getRow()][targetPosition.getColumn()] = true;
        }
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        // Diagonal moves (NW, NE, SW, SE)
        checkDirection(possibleMoves, -1, -1);
        checkDirection(possibleMoves, -1, 1);
        checkDirection(possibleMoves, 1, -1);
        checkDirection(possibleMoves, 1, 1);

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "B";
    }
}