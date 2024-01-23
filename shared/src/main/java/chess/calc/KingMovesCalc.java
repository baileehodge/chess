package chess.calc;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalc {
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece.PieceType promo = null;
        boolean collision = false;
        Collection<ChessMove> acceptableMoves = new ArrayList<>();
        ChessPosition endPosition;
        ChessPiece target;
        ChessPiece mainCharacter = board.getPiece(myPosition);
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i == row && j == col) {
                    continue;
                }
                if (i <= 8 && j <= 8){
                    endPosition = new ChessPosition(i,j);
                    target = board.getPiece(endPosition);
                    if (target != null) {
                        if (target.getTeamColor() != mainCharacter.getTeamColor()) {
                            acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                        }
                    }
                    else {
                        acceptableMoves.add(new ChessMove(myPosition, endPosition, promo));
                    }
                }
            }
        }



        return acceptableMoves;
    }
}
