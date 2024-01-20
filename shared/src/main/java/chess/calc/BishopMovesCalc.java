package chess.calc;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.*;

public class BishopMovesCalc {
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece.PieceType promo = null;
        boolean collision = false;
        Collection<ChessMove> acceptableMoves = new ArrayList<>();
        ChessPosition endPosition;
        ChessPiece target;
        ChessPiece mainCharacter = ChessBoard.getPiece(myPosition);

        // Go back and put duplicate code into functions

        //Check up & to the right
        while (col <= 8 && col >= 1 && row <= 8 && row >= 1 && !collision) {
            row++;
            col++;
            endPosition = new ChessPosition(row,col);
            target = ChessBoard.getPiece(myPosition);
            if (target != null) {
                collision = true;
                if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                }
            }
            if (!collision)
                acceptableMoves.add(new ChessMove(myPosition,endPosition,promo));
        }

        // reset
        row = myPosition.getRow();
        col = myPosition.getColumn();
        collision = false;

        // check up & to the left
        while (col <= 8 && col >= 1 && row <= 8 && row >= 1 && !collision) {
            row++;
            col--;
            endPosition = new ChessPosition(row,col);
            target = ChessBoard.getPiece(myPosition);
            if (target != null) {
                collision = true;
                if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                }
            }
            if (!collision)
                acceptableMoves.add(new ChessMove(myPosition,endPosition,promo));
        }

        // reset
        row = myPosition.getRow();
        col = myPosition.getColumn();
        collision = false;

        //check down & to the right
        while (col <= 8 && col >= 1 && row <= 8 && row >= 1 && !collision) {
            row--;
            col++;
            endPosition = new ChessPosition(row,col);
            target = ChessBoard.getPiece(myPosition);
            if (target != null) {
                collision = true;
                if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                }
            }
            if (!collision)
                acceptableMoves.add(new ChessMove(myPosition,endPosition,promo));
        }

        // reset
        row = myPosition.getRow();
        col = myPosition.getColumn();
        collision = false;

        // check down and to the left
        while (col <= 8 && col >= 1 && row <= 8 && row >= 1 && !collision) {
            row--;
            col--;
            endPosition = new ChessPosition(row,col);
            target = ChessBoard.getPiece(myPosition);
            if (target != null) {
                collision = true;
                if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                }
            }
            if (!collision)
                acceptableMoves.add(new ChessMove(myPosition,endPosition,promo));
        }

        // reset
        row = myPosition.getRow();
        col = myPosition.getColumn();
        collision = false;

        return acceptableMoves;

    }
}
