package chess.calc;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

import static chess.ChessPiece.PieceType.*;


public class PawnMovesCalc {
    static Collection<ChessMove> acceptableMoves = new ArrayList<>();
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        acceptableMoves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece.PieceType promo = null;
        //change this if we're in row 7
        ChessPosition endPosition;
        ChessPiece target;
        ChessPiece mainCharacter = board.getPiece(myPosition);

        if (mainCharacter.getTeamColor() == ChessGame.TeamColor.WHITE) {
            // Check one step forward
            endPosition = new ChessPosition(row + 1, col);
            target = board.getPiece(endPosition);
            if (target == null) {
                if (endPosition.getRow() != 8) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                }
                else {
                    addPromo(myPosition, endPosition);
                }

                // Check two steps forward (only for pawns on the starting square)
                if (row == 2) {
                    endPosition = new ChessPosition(row + 2, col);
                    target = board.getPiece(endPosition);
                    if (target == null) {
                        if (endPosition.getRow() != 8) {
                            acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                        }
                        else {
                            addPromo(myPosition, endPosition);
                        }
                    }
                }
            }
            // Check one step diagonal to the left
            endPosition = new ChessPosition(row + 1, col - 1);
            target = board.getPiece(endPosition);
            if (target != null && target.getTeamColor() != mainCharacter.getTeamColor()) {
                if (endPosition.getRow() != 8) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                }
                else {
                    addPromo(myPosition, endPosition);
                }
            }

            // Check one step diagonal to the right
            endPosition = new ChessPosition(row + 1, col + 1);
            target = board.getPiece(endPosition);
            if (target != null && target.getTeamColor() != mainCharacter.getTeamColor()) {
                if (endPosition.getRow() != 8) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                }
                else {
                    addPromo(myPosition, endPosition);
                }
            }
        }
        if (mainCharacter.getTeamColor() == ChessGame.TeamColor.BLACK) {
            // Check one step forward
            endPosition = new ChessPosition(row - 1, col);
            target = board.getPiece(endPosition);
            if (target == null) {
                if (endPosition.getRow() != 1) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                }
                else {
                    addPromo(myPosition, endPosition);
                }

                // Check two steps forward (only for pawns on the starting square)
                if (row == 7) {
                    endPosition = new ChessPosition(row - 2, col);
                    target = board.getPiece(endPosition);
                    if (target == null) {
                        if (endPosition.getRow() != 1) {
                            acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                        }
                        else {
                            addPromo(myPosition, endPosition);
                        }
                    }
                }
            }
            // Check one step diagonal to the left
            endPosition = new ChessPosition(row - 1, col + 1);
            target = board.getPiece(endPosition);
            if (target != null && target.getTeamColor() != mainCharacter.getTeamColor()) {
                if (endPosition.getRow() != 1) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                }
                else {
                    addPromo(myPosition, endPosition);
                }
            }

            // Check one step diagonal to the right
            endPosition = new ChessPosition(row - 1, col - 1);
            target = board.getPiece(endPosition);
            if (target != null && target.getTeamColor() != mainCharacter.getTeamColor()) {
                if (endPosition.getRow() != 1) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                }
                else {
                    addPromo(myPosition, endPosition);
                }
            }
        }

        return acceptableMoves;
    }

    public static void addPromo(ChessPosition myPosition, ChessPosition endPosition) {
        acceptableMoves.add(new ChessMove(myPosition, endPosition, QUEEN));
        acceptableMoves.add(new ChessMove(myPosition, endPosition, BISHOP));
        acceptableMoves.add(new ChessMove(myPosition, endPosition, KNIGHT));
        acceptableMoves.add(new ChessMove(myPosition, endPosition, ROOK));
    }
}
