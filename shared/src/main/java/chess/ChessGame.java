package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    static ChessBoard board;
    static TeamColor turn;
    static boolean blackCheck = false;
    static boolean blackCheckmate = false;
    static boolean blackStale = false;
    static boolean whiteCheck = false;
    static boolean whiteCheckmate = false;
    static boolean whiteStale = false;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if (board.getPiece(startPosition) == null) {
            return null;
        }
        else {
            return ChessPiece.pieceMoves(board, startPosition);
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // see if move is in validMoves
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        if (!moves.contains(move)) {
            throw new InvalidMoveException("Piece cannot move there");
        }
        // see if it's actually the team's turn
        if (board.getPiece(move.getStartPosition()).getTeamColor() != turn) {
            throw new InvalidMoveException("It's not your turn");
        }
        // see if move leaves the team's king in danger
        if (!Simulation.simulateMoves(board, move)) {
            throw new InvalidMoveException("Puts own king in check");
        }

        // if it puts the other king in check, update blackCheck or whiteCheck

        // since none of those went off, make the move

        throw new InvalidMoveException("Make move not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        if (teamColor == TeamColor.BLACK) {
            return blackCheck;
        }

        else if (teamColor == TeamColor.WHITE) {
            return whiteCheck;
        }
        else {
            throw new RuntimeException("No Team Color");
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (teamColor == TeamColor.BLACK) {
            return blackCheckmate;
        }

        else if (teamColor == TeamColor.WHITE) {
            return whiteCheckmate;
        }
        else {
            throw new RuntimeException("No Team Color");
        }
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (teamColor == TeamColor.BLACK) {
            return blackStale;
        }

        else if (teamColor == TeamColor.WHITE) {
            return whiteStale;
        }
        else {
            throw new RuntimeException("No Team Color");
        }
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param chessBoard the new board to use
     */
    public void setBoard(ChessBoard chessBoard) {
        board = chessBoard;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
