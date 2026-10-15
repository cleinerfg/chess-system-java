package chess.system.chess;

import chess.system.boardgame.Board;
import chess.system.boardgame.Piece;
import lombok.Getter;

public class ChessPiece extends Piece {

    @Getter
    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }
}
