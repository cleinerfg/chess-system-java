package chess.system.application.boardgame;

import lombok.AccessLevel;
import lombok.Getter;

public class Piece {

    @Getter(AccessLevel.PROTECTED)
    protected Position position;

    private Board board;

    public Piece(Board board) {
        this.board = board;
    }
}
