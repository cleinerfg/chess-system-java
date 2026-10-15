package chess.system.chess;

import chess.system.boardgame.Board;
import chess.system.boardgame.Position;
import chess.system.chess.pieces.King;
import chess.system.chess.pieces.Rook;

public class ChessMatch {

    private Board board;

    public ChessMatch() {
        this.board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] matrix = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                matrix[i][j] = (ChessPiece) board.getPiece(i, j);
            }
        }
        return matrix;
    }

    private void initialSetup() {
        board.placePiece(new Rook(board, Color.WHITE), new Position(3, 1));
        board.placePiece(new King(board, Color.BLACK), new Position(5, 4));
        board.placePiece(new King(board, Color.WHITE), new Position(7, 4));
    }
}
