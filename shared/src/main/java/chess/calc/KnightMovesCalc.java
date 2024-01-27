package chess.calc;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalc {
    static Collection<ChessMove> moves = new ArrayList<>();
    static ChessBoard board = new ChessBoard();

    public static Collection<ChessMove> getMoves(ChessBoard chessBoard, ChessPosition myPosition) {
        board = chessBoard;
        calcMoves(board, myPosition);

        return moves;
    }
    public static void calcMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new ArrayList<>();

        // row col of the protagonist
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        // row col of the target
        int i;
        int j;

        // check up & to the right
        i = row + 2;
        j = col + 1;
        if (i >= 1 && i <= 8 && j >= 1 && j <= 8) {
            examine(myPosition, new ChessPosition(i,j));
        }
        i = row + 1;
        j = col + 2;
        if (i >= 1 && i <= 8 && j >= 1 && j <= 8) {
            examine(myPosition, new ChessPosition(i,j));
        }

        // check up & to the left
        i = row + 2;
        j = col - 1;
        if (i >= 1 && i <= 8 && j >= 1 && j <= 8) {
            examine(myPosition, new ChessPosition(i,j));
        }
        i = row + 1;
        j = col - 2;
        if (i >= 1 && i <= 8 && j >= 1 && j <= 8) {
            examine(myPosition, new ChessPosition(i,j));
        }

        // check down & to the right
        i = row - 2;
        j = col + 1;
        if (i >= 1 && i <= 8 && j >= 1 && j <= 8) {
            examine(myPosition, new ChessPosition(i,j));
        }
        i = row - 1;
        j = col + 2;
        if (i >= 1 && i <= 8 && j >= 1 && j <= 8) {
            examine(myPosition, new ChessPosition(i,j));
        }

        // check down & to the left
        i = row - 2;
        j = col - 1;
        if (i >= 1 && i <= 8 && j >= 1 && j <= 8) {
            examine(myPosition, new ChessPosition(i,j));
        }
        // check down & to the left
        i = row - 1;
        j = col - 2;
        if (i >= 1 && i <= 8 && j >= 1 && j <= 8) {
            examine(myPosition, new ChessPosition(i,j));
        }
    }

    public static void examine(ChessPosition start, ChessPosition end) {
        ChessPiece protagonist = board.getPiece(start);
        ChessPiece target = board.getPiece(end);

        if (target == null) {
            moves.add(new ChessMove(start, end, null));
        }
        else if (target.getTeamColor() != protagonist.getTeamColor()) {
            moves.add(new ChessMove(start, end, null));
        }
    }
}
