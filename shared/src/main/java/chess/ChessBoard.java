package chess;

import java.util.Arrays;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessPiece.PieceType.*;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] squares = new ChessPiece[8][8];

    public ChessBoard() {

    }
    public ChessBoard(ChessBoard original) {
        squares = new ChessPiece[8][8];
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPiece originalPiece = original.getPiece(new ChessPosition(i,j));
                if (originalPiece != null) {
                    ChessPiece copyPiece = new ChessPiece(originalPiece.getTeamColor(), originalPiece.getPieceType());
                    addPiece(new ChessPosition(i,j), copyPiece);
                }
            }
        }
    }

    public ChessPiece[][] getSquares() {
        return squares;
    }


    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow() - 1;
        int column = position.getColumn() - 1;

        // Check if the row index is within the valid range
        if (row < 0 || row >= squares.length) {
            throw new IllegalArgumentException("Invalid row index");
        }

        // Check if the column index is within the valid range
        if (column < 0 || column >= squares[row].length) {
            throw new IllegalArgumentException("Invalid column index");
        }

        squares[row][column] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        if (row <= 0 || col <= 0 || row > 8 || col > 8) {
            return null;
        }
        else {
            return squares[position.getRow() - 1][position.getColumn() - 1];
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        ChessPosition position;
        ChessPiece piece;

        // add white pawns
        for (int i = 1; i <= 8; i++) {
            position = new ChessPosition(2, i);
            piece = new ChessPiece(WHITE, PAWN);
            addPiece(position, piece);
        }

        // add black pawns
        for (int i = 1; i <= 8; i++) {
            position = new ChessPosition(7, i);
            piece = new ChessPiece(BLACK, PAWN);
            addPiece(position, piece);
        }

        // add rooks
        piece = new ChessPiece(WHITE, ROOK);
        addPiece(new ChessPosition(1, 1), piece);
        addPiece(new ChessPosition(1, 8), piece);
        piece = new ChessPiece(BLACK, ROOK);
        addPiece(new ChessPosition(8, 1), piece);
        addPiece(new ChessPosition(8, 8), piece);

        // add knights
        piece = new ChessPiece(WHITE, KNIGHT);
        addPiece(new ChessPosition(1, 2), piece);
        addPiece(new ChessPosition(1, 7), piece);
        piece = new ChessPiece(BLACK, KNIGHT);
        addPiece(new ChessPosition(8, 2), piece);
        addPiece(new ChessPosition(8, 7), piece);

        // add bishops
        piece = new ChessPiece(WHITE, BISHOP);
        addPiece(new ChessPosition(1, 3), piece);
        addPiece(new ChessPosition(1, 6), piece);
        piece = new ChessPiece(BLACK, BISHOP);
        addPiece(new ChessPosition(8, 3), piece);
        addPiece(new ChessPosition(8, 6), piece);

        // add kings
        piece = new ChessPiece(WHITE, KING);
        addPiece(new ChessPosition(1, 5), piece);
        piece = new ChessPiece(BLACK, KING);
        addPiece(new ChessPosition(8, 5), piece);

        // add Queens
        piece = new ChessPiece(WHITE, QUEEN);
        addPiece(new ChessPosition(1, 4), piece);
        piece = new ChessPiece(BLACK, QUEEN);
        addPiece(new ChessPosition(8, 4), piece);


    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ChessPiece[] square : squares) {
            for (ChessPiece piece : square) {
                if (piece != null) {
                    sb.append(piece.toString());
                } else {
                    sb.append("-");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

}


