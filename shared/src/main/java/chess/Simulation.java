package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


public class Simulation {

    public static boolean simulateMoves(ChessBoard board, ChessMove move) {
        ChessBoard simBoard = board;
        // find the position of the king

        // make the move on simBoard


        // iterate through every enemy piece on simBoard
        // call simulateMove on each
        // keep going while the answer is true
        // if we get a false, return false
        // if it all passes through true, return true


        return true;
    }

    private boolean simulateMove(ChessBoard simBoard, ChessPosition enemyPosition, ChessPosition kingPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        moves = PieceMovesCalc.pieceMoves(simBoard, enemyPosition);
        // iterate through the end positions
        // see if any of them are equal to the kingPosition
        // if they are, return false
        // if we get through true, return true
        // while (status == true) {} probably

        return true; // fix this
    }

}
