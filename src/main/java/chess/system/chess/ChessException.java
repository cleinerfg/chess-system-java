package chess.system.chess;

import chess.system.boardgame.BoardException;

public class ChessException extends BoardException {
    public ChessException(String message) {
        super(message);
    }
}
