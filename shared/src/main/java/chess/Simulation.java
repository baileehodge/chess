package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static chess.ChessPiece.PieceType.KING;


public class Simulation {

    public static boolean simulateMoves(ChessBoard board, ChessMove move) {
        ChessBoard simBoard = new ChessBoard();
        simBoard = board;
        ChessPosition kingPosition = null;
        ChessPiece tempPiece;
        ChessPosition tempPosition;
        ChessPiece protagonist = board.getPiece(move.getStartPosition());
        ChessGame.TeamColor teamColor = protagonist.getTeamColor();
        boolean result = true;

        // make the move on simBoard
        simBoard.addPiece(move.getEndPosition(), simBoard.getPiece(move.getStartPosition()));
        simBoard.addPiece(move.getStartPosition(), null);

        // find the position of the home team king
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                tempPiece = simBoard.getPiece(new ChessPosition(i,j));
                if (tempPiece != null) {
                    if (tempPiece.getPieceType() == KING && tempPiece.getTeamColor() == teamColor) {
                        kingPosition = new ChessPosition(i,j);
                        break;
                    }
                }
            }
        }


        // iterate through every enemy piece on simBoard
        // call simulateMove on each
        // keep going while the answer is true
        // if we get a false, return false
        // if it all passes through true, return true
        while (result) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    tempPosition = new ChessPosition(i, j);
                    tempPiece = simBoard.getPiece(tempPosition);
                    if (tempPiece != null) {
                        if (tempPiece.getTeamColor() != teamColor) {
                            result = simulateMove(simBoard, tempPosition, kingPosition);
                        }
                    }
                }
            }
        }

        return result;
    }

    private static boolean simulateMove(ChessBoard simBoard, ChessPosition enemyPosition, ChessPosition kingPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        moves = PieceMovesCalc.pieceMoves(simBoard, enemyPosition);
        boolean result = true;

        while (result) {
            assert moves != null;
            for (ChessMove move : moves) {
                if (move.getEndPosition() == kingPosition) {
                    result = false;
                    break;
                }
            }
        }

        // iterate through the end positions
        // see if any of them are equal to the kingPosition
        // if they are, result = false
        // if we get through true, return true
        // while (status == true) {} probably

        return result;
    }

}
