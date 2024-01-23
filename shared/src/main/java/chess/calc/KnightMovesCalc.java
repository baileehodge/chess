package chess.calc;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalc {
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece.PieceType promo = null;
        Collection<ChessMove> acceptableMoves = new ArrayList<>();
        ChessPosition endPosition;
        ChessPiece target;
        ChessPiece mainCharacter = board.getPiece(myPosition);

        // Check up & to the right
        if (row <= 6 && col <= 7) {
            endPosition = new ChessPosition(row + 2, col + 1);
            target = board.getPiece(endPosition);
            if (target == null || target.getTeamColor() != mainCharacter.getTeamColor()) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
            }
        }
        if (row <= 7 && col <= 6) {
            endPosition = new ChessPosition(row + 1, col + 2);
            target = board.getPiece(endPosition);
            if (target == null || target.getTeamColor() != mainCharacter.getTeamColor()) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
            }
        }

        // Check up & to the left
        if (row <= 6 && col >= 2) {
            endPosition = new ChessPosition(row + 2, col - 1);
            target = board.getPiece(endPosition);
            if (target == null || target.getTeamColor() != mainCharacter.getTeamColor()) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
            }
        }
        if (row <= 7 && col >= 3) {
            endPosition = new ChessPosition(row + 1, col - 2);
            target = board.getPiece(endPosition);
            if (target == null || target.getTeamColor() != mainCharacter.getTeamColor()) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
            }
        }

        // Check down & to the right
        if (row >= 3 && col <= 7) {
            endPosition = new ChessPosition(row - 2, col + 1);
            target = board.getPiece(endPosition);
            if (target == null || target.getTeamColor() != mainCharacter.getTeamColor()) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
            }
        }
        if (row >= 2 && col <= 6) {
            endPosition = new ChessPosition(row - 1, col + 2);
            target = board.getPiece(endPosition);
            if (target == null || target.getTeamColor() != mainCharacter.getTeamColor()) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
            }
        }

        // Check down & to the left
        if (row >= 3 && col >= 2) {
            endPosition = new ChessPosition(row - 2, col - 1);
            target = board.getPiece(endPosition);
            if (target == null || target.getTeamColor() != mainCharacter.getTeamColor()) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
            }
        }
        if (row >= 2 && col >= 3) {
            endPosition = new ChessPosition(row - 1, col - 2);
            target = board.getPiece(endPosition);
            if (target == null || target.getTeamColor() != mainCharacter.getTeamColor()) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
            }
        }

        return acceptableMoves;
    }
}
