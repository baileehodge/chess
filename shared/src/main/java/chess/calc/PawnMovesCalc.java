package chess.calc;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

import static chess.ChessGame.TeamColor.*;
import static chess.ChessPiece.PieceType.*;

public class PawnMovesCalc {
    static Collection<ChessMove> moves = new ArrayList<>();
    static ChessBoard board = new ChessBoard();
    static boolean collision = false;

    public static Collection<ChessMove> getMoves(ChessBoard chessBoard, ChessPosition myPosition) {
        board = chessBoard;
        calcMoves(board, myPosition);

        return moves;
    }
    public static void calcMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new ArrayList<>();
        collision = false;

        // row col of the protagonist
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece protagonist = board.getPiece(myPosition);

        // row col of the target
        int i;
        int j;

        // white
        if (protagonist.getTeamColor() == WHITE) {
            // check forward
            i = row + 1;
            j = col;
            examineForward(myPosition, new ChessPosition(i, j));

            if (row == 2 && !collision) {
                i = row + 2;
                examineForward(myPosition, new ChessPosition(i, j));
            }

            // check both diagonals
            i = row + 1;
            j = col + 1;
            examineDiagonal(myPosition, new ChessPosition(i, j));
            i = row + 1;
            j = col - 1;
            examineDiagonal(myPosition, new ChessPosition(i, j));
        }

        // black

        if (protagonist.getTeamColor() == BLACK) {
            // check forward
            i = row - 1;
            j = col;
            examineForward(myPosition, new ChessPosition(i, j));

            if (row == 7 && !collision) {
                i = row - 2;
                examineForward(myPosition, new ChessPosition(i, j));
            }

            // check both diagonals
            i = row - 1;
            j = col + 1;
            examineDiagonal(myPosition, new ChessPosition(i, j));
            i = row - 1;
            j = col - 1;
            examineDiagonal(myPosition, new ChessPosition(i, j));
        }


    }

    // only call this on a position diagonal from the start
    public static void examineDiagonal(ChessPosition start, ChessPosition end) {
        ChessPiece protagonist = board.getPiece(start);
        ChessPiece target = board.getPiece(end);

        if (target != null && target.getTeamColor() != protagonist.getTeamColor()) {
            if (protagonist.getTeamColor() == WHITE && end.getRow() == 8) {
                moves.add(new ChessMove(start, end, BISHOP));
                moves.add(new ChessMove(start, end, KNIGHT));
                moves.add(new ChessMove(start, end, QUEEN));
                moves.add(new ChessMove(start, end, ROOK));
            }
            else if (protagonist.getTeamColor() == BLACK && end.getRow() == 1) {
                moves.add(new ChessMove(start, end, BISHOP));
                moves.add(new ChessMove(start, end, KNIGHT));
                moves.add(new ChessMove(start, end, QUEEN));
                moves.add(new ChessMove(start, end, ROOK));
            }
            else {
                moves.add(new ChessMove(start,end,null));
            }

            collision = true;
        }
    }

    // only call this on a position forward from the start
    public static void examineForward(ChessPosition start, ChessPosition end) {
        ChessPiece protagonist = board.getPiece(start);
        ChessPiece target = board.getPiece(end);

        if (target == null) {
            if (protagonist.getTeamColor() == WHITE && end.getRow() == 8) {
                moves.add(new ChessMove(start, end, BISHOP));
                moves.add(new ChessMove(start, end, KNIGHT));
                moves.add(new ChessMove(start, end, QUEEN));
                moves.add(new ChessMove(start, end, ROOK));
            }
            else if (protagonist.getTeamColor() == BLACK && end.getRow() == 1) {
                moves.add(new ChessMove(start, end, BISHOP));
                moves.add(new ChessMove(start, end, KNIGHT));
                moves.add(new ChessMove(start, end, QUEEN));
                moves.add(new ChessMove(start, end, ROOK));
            }
            else {
                moves.add(new ChessMove(start,end,null));
            }
        } else {
            collision = true;
        }
    }
}
