package chess.system.chess.pieces;

import chess.system.boardgame.Board;
import chess.system.boardgame.Position;
import chess.system.chess.ChessPiece;
import chess.system.chess.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
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

        // Orthogonal moves (Above, Below, Left, Right)
        checkDirection(possibleMoves, -1, 0);
        checkDirection(possibleMoves, 1, 0);
        checkDirection(possibleMoves, 0, -1);
        checkDirection(possibleMoves, 0, 1);

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "R";
    }

}