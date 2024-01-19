package chess;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import chess.calc.BishopMovesCalc;
import chess.calc.KingMovesCalc;
import chess.calc.*;


import java.util.ArrayList;
import java.util.Collection;

public class PieceMovesCalc<board, position> {

    public static Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        ChessPiece piece = board.getPiece(myPosition);
        ChessPiece.PieceType type = piece.getPieceType();
        if (type == ChessPiece.PieceType.BISHOP) {
            BishopMovesCalc.getMoves(board, myPosition);
        }
        else if (type == ChessPiece.PieceType.KING) {
            KingMovesCalc.getMoves(board, myPosition);
        }
        else if (type == ChessPiece.PieceType.QUEEN) {
            QueenMovesCalc.getMoves(board, myPosition);
        }
        else if (type == ChessPiece.PieceType.ROOK) {
            RookMovesCalc.getMoves(board, myPosition);
        }
        else if (type == ChessPiece.PieceType.KNIGHT) {
            KnightMovesCalc.getMoves(board, myPosition);
        }
        else if (type == ChessPiece.PieceType.PAWN) {
            PawnMovesCalc.getMoves(board, myPosition);
        }


        return new ArrayList<>();
    }
}
