package chess.calc;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalc {
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        boolean collision = false;
        Collection<ChessMove> acceptableMoves = new ArrayList<>();
        ChessPosition endPosition;
        ChessPiece target;
        ChessPiece mainCharacter = board.getPiece(myPosition);

        // Go back and put duplicate code into functions

        //Check up
        while (row <= 8 && row >= 1 && !collision) {
            endPosition = new ChessPosition(row, col);
            target = board.getPiece(endPosition);
            if (target != null) {
                collision = true;
                if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
            if (!collision) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            row++;
            if (target == mainCharacter) {
                collision = false;
            }
        }

        // reset
        row = myPosition.getRow();
        col = myPosition.getColumn();
        collision = false;

        // check down
        while (row <= 8 && row >= 1 && !collision) {
            endPosition = new ChessPosition(row, col);
            target = board.getPiece(endPosition);
            if (target != null) {
                collision = true;
                if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
            if (!collision) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            row--;
            if (target == mainCharacter) {
                collision = false;
            }
        }

        // reset
        row = myPosition.getRow();
        col = myPosition.getColumn();
        collision = false;

        //check to the right
        while (col <= 8 && col >= 1 && !collision) {
            endPosition = new ChessPosition(row, col);
            target = board.getPiece(endPosition);
            if (target != null) {
                collision = true;
                if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
            if (!collision) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            col++;
            if (target == mainCharacter) {
                collision = false;
            }
        }

        // reset
        row = myPosition.getRow();
        col = myPosition.getColumn();
        collision = false;

        // check to the left
        while (col <= 8 && col >= 1 && !collision) {
            endPosition = new ChessPosition(row, col);
            target = board.getPiece(endPosition);
            if (target != null) {
                collision = true;
                if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
            if (!collision) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            col--;
            if (target == mainCharacter) {
                collision = false;
            }
        }

        // reset
        row = myPosition.getRow();
        col = myPosition.getColumn();
        collision = false;


        //Check up & to the right
        while (col <= 8 && col >= 1 && row <= 8 && row >= 1 && !collision) {
            endPosition = new ChessPosition(row,col);
            target = board.getPiece(endPosition);
            if (target != null) {
                collision = true;
                if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
            if (!collision) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            row++;
            col++;
            if (target == mainCharacter) {
                collision = false;
            }
        }

        // reset
        row = myPosition.getRow();
        col = myPosition.getColumn();
        collision = false;

        // check up & to the left
        while (col <= 8 && col >= 1 && row <= 8 && row >= 1 && !collision) {
            endPosition = new ChessPosition(row,col);
            target = board.getPiece(endPosition);
            if (target != null) {
                collision = true;
                if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
            if (!collision) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            row++;
            col--;
            if (target == mainCharacter) {
                collision = false;
            }
        }

        // reset
        row = myPosition.getRow();
        col = myPosition.getColumn();
        collision = false;

        //check down & to the right
        while (col <= 8 && col >= 1 && row <= 8 && row >= 1 && !collision) {
            endPosition = new ChessPosition(row,col);
            target = board.getPiece(endPosition);
            if (target != null) {
                collision = true;
                if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
            if (!collision) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            row--;
            col++;
            if (target == mainCharacter) {
                collision = false;
            }
        }

        // reset
        row = myPosition.getRow();
        col = myPosition.getColumn();
        collision = false;

        // check down and to the left
        while (col <= 8 && col >= 1 && row <= 8 && row >= 1 && !collision) {
            endPosition = new ChessPosition(row,col);
            target = board.getPiece(endPosition);
            if (target != null) {
                collision = true;
                if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                    acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
                }
            }
            if (!collision) {
                acceptableMoves.add(new ChessMove(myPosition, endPosition, null));
            }
            row--;
            col--;
            if (target == mainCharacter) {
                collision = false;
            }
        }

        // reset
        row = myPosition.getRow();
        col = myPosition.getColumn();
        collision = false;


        return acceptableMoves;
    }
}
