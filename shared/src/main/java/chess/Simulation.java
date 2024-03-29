package chess;

import java.util.ArrayList;
import java.util.Collection;

import static chess.ChessPiece.PieceType.KING;


public class Simulation {

    public static boolean simulateMoves(ChessBoard board, ChessMove move) {
        ChessBoard simBoard = new ChessBoard(board);
        ChessPiece protagonist = board.getPiece(move.getStartPosition());
        ChessGame.TeamColor teamColor = protagonist.getTeamColor();

        // make the move on simBoard
        simBoard.addPiece(move.getEndPosition(), simBoard.getPiece(move.getStartPosition()));
        simBoard.addPiece(move.getStartPosition(), null);

        // return true if the move leaves the king safe / does not put the king in check
        return kingSafe(simBoard, teamColor);
    }

    static boolean kingSafe(ChessBoard board, ChessGame.TeamColor teamColor) {
        ChessPosition tempPosition;
        ChessPiece tempPiece;
        ChessPosition kingPosition = null;
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

        // go through the whole board
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                tempPosition = new ChessPosition(i, j);
                tempPiece = board.getPiece(tempPosition);
                // if you find an enemy piece
                if (tempPiece != null && tempPiece.getTeamColor() != teamColor) {
                    // if the piece is a threat, return false
                    if (!simulateMove(board, tempPosition, kingPosition)) {
                        return false;
                    }
                }
            }
        }

        // if none of the pieces are a threat, return true
        return true;
    }

    private static boolean simulateMove(ChessBoard board, ChessPosition enemyPosition, ChessPosition kingPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        // look at all the moves it can make
        moves = PieceMovesCalc.pieceMoves(board, enemyPosition);

        // if any moves can hit the king, return false
        assert moves != null;
        for (ChessMove move : moves) {
            if (move.getEndPosition().equals(kingPosition)) {
                return false;
            }
        }
        // if none of the moves can hit the king...
        return true;
    }

}
