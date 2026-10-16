package chess.system.boardgame;

import lombok.AccessLevel;
import lombok.Getter;

public abstract class Piece {

    @Getter(AccessLevel.PROTECTED)
    protected Position position;

    @Getter(AccessLevel.PROTECTED)
    private Board board;

    protected Piece(Board board) {
        this.board = board;
    }

    public abstract boolean[][] possibleMoves();

    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] possibleMoves = possibleMoves();
        for (int i = 0; i < possibleMoves.length; i++) {
            for (int j = 0; j < possibleMoves[i].length; j++) {
                if (possibleMoves[i][j]) return true;
            }
        }
        return false;
    }
}
