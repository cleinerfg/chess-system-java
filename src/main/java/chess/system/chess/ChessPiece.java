package chess.system.chess;

import chess.system.boardgame.Board;
import chess.system.boardgame.Piece;
import chess.system.boardgame.Position;
import lombok.Getter;

public abstract class ChessPiece extends Piece {

    @Getter
    private Color color;

    @Getter
    private int moveCount;

    protected ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public void incrementMoveCount() {
        moveCount++;
    }

    public void decrementMoveCount() {
        moveCount--;
    }

    protected boolean isThereAnOpponentPiece(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().getPiece(position);
        return piece != null && piece.getColor() != color;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }
}
