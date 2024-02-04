package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static chess.ChessPiece.PieceType.KING;


public class Simulation {

    public static boolean simulateMoves(ChessBoard board, ChessMove move) {
        ChessBoard simBoard = new ChessBoard();
        simBoard = new ChessBoard(board);
        ChessPosition kingPosition = null;
        ChessPiece tempPiece;
        ChessPosition tempPosition;
        ChessPiece protagonist = board.getPiece(move.getStartPosition());
        ChessGame.TeamColor teamColor = protagonist.getTeamColor();
        boolean result = true;

        // make the move on simBoard
        simBoard.addPiece(move.getEndPosition(), simBoard.getPiece(move.getStartPosition()));
        simBoard.addPiece(move.getStartPosition(), null);

        return kingSafe(simBoard, teamColor);
    }

    static boolean kingSafe(ChessBoard board, ChessGame.TeamColor teamColor) {
        ChessPosition tempPosition;
        ChessPiece tempPiece;
        ChessPosition kingPosition = null;
        boolean result;
        // find the position of the home team king
        outerLoop:
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                tempPiece = board.getPiece(new ChessPosition(i,j));
                if (tempPiece != null) {
                    if (tempPiece.getPieceType() == KING && tempPiece.getTeamColor() == teamColor) {
                        kingPosition = new ChessPosition(i,j);
                        break outerLoop;
                    }
                }
            }
        }


        // iterate through every enemy piece on simBoard
        // call simulateMove on each
        // if we get a false, return false
        // if it all passes through true, return true

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                tempPosition = new ChessPosition(i, j);
                tempPiece = board.getPiece(tempPosition);
                if (tempPiece != null) {
                    if (tempPiece.getTeamColor() != teamColor) {
                        result = simulateMove(board, tempPosition, kingPosition);
                        if (!result) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private static boolean simulateMove(ChessBoard board, ChessPosition enemyPosition, ChessPosition kingPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        moves = PieceMovesCalc.pieceMoves(board, enemyPosition);


        assert moves != null;
        for (ChessMove move : moves) {
            if (move.getEndPosition().equals(kingPosition)) {
                return false;
            }
        }
        return true;
    }

}
