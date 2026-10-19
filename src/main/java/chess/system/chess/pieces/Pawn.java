package chess.system.chess.pieces;

import chess.system.boardgame.Board;
import chess.system.boardgame.Position;
import chess.system.chess.ChessMatch;
import chess.system.chess.ChessPiece;
import chess.system.chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
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

    private void checkEnPassant(boolean[][] moves, int direction) {
        int targetRow = (getColor() == Color.WHITE) ? 3 : 4;

        if (position.getRow() == targetRow) {
            // Left
            Position left = new Position(position.getRow(), position.getColumn() - 1);
            if (getBoard().positionExists(left) &&
                    isThereAnOpponentPiece(left) &&
                    getBoard().getPiece(left) == chessMatch.getEnPassantVulnerable()) {
                moves[left.getRow() + direction][left.getColumn()] = true;
            }

            // Right
            Position right = new Position(position.getRow(), position.getColumn() + 1);
            if (getBoard().positionExists(right) &&
                    isThereAnOpponentPiece(right) &&
                    getBoard().getPiece(right) == chessMatch.getEnPassantVulnerable()) {
                moves[right.getRow() + direction][right.getColumn()] = true;
            }
        }
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        int direction = (getColor() == Color.WHITE) ? -1 : 1;

        checkForwardMove(possibleMoves, direction);
        checkCaptures(possibleMoves, direction);
        checkEnPassant(possibleMoves, direction);

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "P";
    }
}