package chess.system.chess;

import chess.system.boardgame.Board;
import chess.system.boardgame.Piece;
import chess.system.boardgame.Position;
import chess.system.chess.pieces.*;
import lombok.Getter;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    @Getter
    private int turn = 1;

    @Getter
    private Color currentPlayer = Color.WHITE;

    @Getter
    private boolean check;

    @Getter
    private boolean checkMate;

    @Getter
    private ChessPiece enPassantVulnerable;

    @Getter
    private ChessPiece promoted;

    private Board board;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

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
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }

    public boolean[][] possibleMoves(ChessPosition source) {
        Position p = source.toPosition();
        validateSourcePosition(p);
        return board.getPiece(p).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        validateSourcePosition(source);
        validateTargetPosition(source, target);

        Piece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check!");
        }

        ChessPiece movedPiece = (ChessPiece) board.getPiece(target);

        // Special mode: promotion
        promoted = null;
        if (movedPiece instanceof Pawn) {
            if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) ||
                    (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
                promoted = (ChessPiece) board.getPiece(target);
                promoted = replacePromotedPiece("Q");
            }
        }

        check = testCheck(getOpponent(currentPlayer));

        if (testCheckMate(getOpponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }

        return (ChessPiece) capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) {
            throw new IllegalStateException("Promoted piece is null");
        }
        // Check if type is NOT N, R, Q, or B
        if (!type.equals("N") && !type.equals("R") && !type.equals("Q") && !type.equals("B")) {
            throw new InvalidParameterException("Invalid type for promotion!");
        }
        Position pos = promoted.getChessPosition().toPosition();
        Piece p = board.removePiece(pos);
        piecesOnTheBoard.remove(p);

        ChessPiece newPiece = newPiece(type, promoted.getColor());
        board.placePiece(newPiece, pos);
        piecesOnTheBoard.add(newPiece);

        return newPiece;
    }

    private ChessPiece newPiece(String type, Color color) {
        if (type.equals("B")) return new Bishop(board, color);
        if (type.equals("N")) return new Knight(board, color);
        if (type.equals("Q")) return new Queen(board, color);
        return new Rook(board, color);
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece piece = (ChessPiece) board.removePiece(source);
        piece.incrementMoveCount();

        Piece capturedPiece = board.removePiece(target);
        board.placePiece(piece, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        // Special move: castling KingSide rook
        if (piece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceR = new Position(source.getRow(), source.getColumn() + 3);
            Position targetR = new Position(source.getRow(), source.getColumn() + 1);

            ChessPiece rook = (ChessPiece) board.removePiece(sourceR);
            board.placePiece(rook, targetR);
            rook.incrementMoveCount();
        }

        // Special move: castling QueenSide rook
        if (piece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceR = new Position(source.getRow(), source.getColumn() - 4);
            Position targetR = new Position(source.getRow(), source.getColumn() - 1);

            ChessPiece rook = (ChessPiece) board.removePiece(sourceR);
            board.placePiece(rook, targetR);
            rook.incrementMoveCount();
        }

        // Special move: en passant
        if (piece instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnP;
                if (piece.getColor() == Color.WHITE) {
                    pawnP = new Position(target.getRow() + 1, target.getColumn());
                } else {
                    pawnP = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnP);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decrementMoveCount();

        board.placePiece(p, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        // Special move: castling KingSide rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceR = new Position(source.getRow(), source.getColumn() + 3);
            Position targetR = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetR);
            board.placePiece(rook, sourceR);
            rook.decrementMoveCount();
        }

        // Special move: castling QueenSide rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceR = new Position(source.getRow(), source.getColumn() - 4);
            Position targetR = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetR);
            board.placePiece(rook, sourceR);
            rook.decrementMoveCount();
        }

        // Special move: en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece) board.removePiece(target);
                Position pawnP;
                if (p.getColor() == Color.WHITE) {
                    pawnP = new Position(3, target.getColumn());
                } else {
                    pawnP = new Position(4, target.getColumn());
                }

                board.placePiece(p, source);
                board.placePiece(pawn, pawnP);
            }
        }
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece at the source position ");
        }
        if (currentPlayer != ((ChessPiece) board.getPiece(position)).getColor()) {
            throw new ChessException("The current player is " + currentPlayer);
        }
        if (!board.getPiece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible move to the piece");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.getPiece(source).possibleMove(target)) {
            throw new ChessException("The piece can't be moved to the target position");
        }
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Color getOpponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece getKing(Color color) {
        List<Piece> pieces = getPiecesOnTheBoardByColor(color);

        for (Piece piece : pieces) {
            if (piece instanceof King) {
                return (ChessPiece) piece;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = getKing(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = getPiecesOnTheBoardByColor(getOpponent(color));

        for (Piece piece : opponentPieces) {
            boolean[][] possibleMoves = piece.possibleMoves();
            if (possibleMoves[kingPosition.getRow()][kingPosition.getColumn()]) return true;
        }

        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) return false;

        List<Piece> piecesOnTheBoard = getPiecesOnTheBoardByColor(color);

        for (Piece p : piecesOnTheBoard) {
            boolean[][] possibleMoves = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (possibleMoves[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);

                        boolean isCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);

                        if (!isCheck) return false;

                    }
                }
            }
        }

        return true;
    }

    private List<Piece> getPiecesOnTheBoardByColor(Color color) {
        return piecesOnTheBoard.stream()
                .filter(piece -> ((ChessPiece) piece).getColor() == color)
                .toList();
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }
}