package chess.system.chess.pieces;

import chess.system.boardgame.Board;
import chess.system.boardgame.Position;
import chess.system.chess.ChessPiece;
import chess.system.chess.Color;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {
        super(board, color);
    }

    private boolean canMoveTo(Position target) {
        return getBoard().positionExists(target) && !getBoard().thereIsAPiece(target);
    }

    private boolean canCaptureAt(Position target) {
        return getBoard().positionExists(target) && isThereAnOpponentPiece(target);
    }

    private void checkForwardMove(boolean[][] moves, int direction) {
        Position oneStep = new Position(position.getRow() + direction, position.getColumn());

        if (canMoveTo(oneStep)) {
            moves[oneStep.getRow()][oneStep.getColumn()] = true;

            Position twoSteps = new Position(position.getRow() + (2 * direction), position.getColumn());
            if (getMoveCount() == 0 && canMoveTo(twoSteps)) {
                moves[twoSteps.getRow()][twoSteps.getColumn()] = true;
            }
        }
    }

    private void checkCaptures(boolean[][] moves, int direction) {
        Position leftDiagonal = new Position(position.getRow() + direction, position.getColumn() - 1);
        if (canCaptureAt(leftDiagonal)) {
            moves[leftDiagonal.getRow()][leftDiagonal.getColumn()] = true;
        }

        Position rightDiagonal = new Position(position.getRow() + direction, position.getColumn() + 1);
        if (canCaptureAt(rightDiagonal)) {
            moves[rightDiagonal.getRow()][rightDiagonal.getColumn()] = true;
        }
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        int direction = (getColor() == Color.WHITE) ? -1 : 1;

        checkForwardMove(possibleMoves, direction);
        checkCaptures(possibleMoves, direction);

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "P";
    }
}