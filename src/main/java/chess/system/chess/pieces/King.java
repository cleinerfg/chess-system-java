package chess.system.chess.pieces;

import chess.system.boardgame.Board;
import chess.system.boardgame.Position;
import chess.system.chess.ChessMatch;
import chess.system.chess.ChessPiece;
import chess.system.chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    private boolean canMove(Position targetPosition) {
        ChessPiece piece = (ChessPiece) getBoard().getPiece(targetPosition);
        return piece == null || piece.getColor() != getColor();
    }

    private boolean testRookCastling(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().getPiece(position);
        return piece instanceof Rook &&
                piece.getColor() == getColor()
                && piece.getMoveCount() == 0;
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

        // Special move: castling
        if (getMoveCount() == 0 && !chessMatch.isCheck()) {
            // Kingside rook
            Position posRook1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(posRook1)) {
                Position posRight1 = new Position(position.getRow(), position.getColumn() + 1);
                Position posRight2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().getPiece(posRight1) == null && getBoard().getPiece(posRight2) == null) {
                    possibleMoves[position.getRow()][position.getColumn() + 2] = true;
                }
            }

            // Queenside rook
            Position posRook2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(posRook2)) {
                Position posLef1 = new Position(position.getRow(), position.getColumn() - 1);
                Position posLeft2 = new Position(position.getRow(), position.getColumn() - 2);
                Position posLeft3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().getPiece(posLef1) == null &&
                        getBoard().getPiece(posLeft2) == null &&
                        getBoard().getPiece(posLeft3) == null) {
                    possibleMoves[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "K";
    }
}