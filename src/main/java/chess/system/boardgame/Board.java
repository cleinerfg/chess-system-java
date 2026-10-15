package chess.system.boardgame;

import lombok.Getter;
import lombok.Setter;

public class Board {

    @Getter @Setter
    private int rows;

    @Getter @Setter
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public Piece getPiece(int row, int column) {
        return pieces[row][column];
    }

    public Piece getPiece(Position position) {
        return pieces[position.getRow()][position.getCol()];
    }

    public void placePiece(Piece piece, Position position) {
        pieces[position.getRow()][position.getCol()] = piece;
        piece.position = position;
    }
}
