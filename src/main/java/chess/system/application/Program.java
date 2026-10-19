package chess.system.application;

import chess.system.chess.ChessMatch;
import chess.system.chess.ChessPiece;
import chess.system.chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

    static void main() {

        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> capturedPieces = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (!chessMatch.isCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, capturedPieces);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition();

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition();

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                if (capturedPiece != null) capturedPieces.add(capturedPiece);

                if (chessMatch.getPromoted() != null) {
                    System.out.println("Enter piece to promote(B/N/R/Q): ");
                    String type = scanner.nextLine();
                    chessMatch.replacePromotedPiece(type);
                }

            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                UI.nextLine();
            }
        }

        UI.clearScreen();
        UI.printMatch(chessMatch, capturedPieces);
    }
}