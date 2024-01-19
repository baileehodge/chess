package chess.calc;

import chess.ChessBoard;
import chess.ChessPosition;

public class BishopMovesCalc {
    public static void getMoves(ChessBoard board, ChessPosition myPosition) {
        //        The method uses ChessBoard' s getPiece(ChessPosition) method, which returns a ChessPiece, to check
//                every position in the path of the ChessPiece in question. If there's a ChessPiece at that position, it
//                uses the getTeamColor(ChessPiece) method to find out if it's an enemy piece. If it's a friendly piece,
//                moving to that ChessPosition is not a valid ChessMove.

//        find a way to cycle through the positions that a bishop could move to, some sort of while i <= 8, row + 1 and column + 1
        // don't forget to check piece type for friendly fire
        // and if it runs into a piece, the rest of that track will be invalid
    }
}
