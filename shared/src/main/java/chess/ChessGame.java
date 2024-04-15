package chess;

import java.util.Collection;

import static chess.ChessGame.TeamColor.WHITE;
import static chess.Simulation.kingSafe;



/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    static ChessBoard board;
    static TeamColor turn;

    public ChessGame() {
        board = new ChessBoard();
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
    public void switchTeamTurn() {

        if (turn == WHITE) {
            turn = TeamColor.BLACK;
        }
        else if (turn == TeamColor.BLACK) {
            turn = WHITE;
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK,
        NONE
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
            Collection<ChessMove> moves = ChessPiece.pieceMoves(board, startPosition);
            // remove if it puts your own king in danger
            moves.removeIf(move -> !Simulation.simulateMoves(board, move));
            return moves;
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
        else if (board.getPiece(move.getStartPosition()).getTeamColor() != turn) {
            throw new InvalidMoveException("It's not your turn");
        }
        // see if move leaves the team's king in danger
        else if (!Simulation.simulateMoves(board, move)) {
            throw new InvalidMoveException("Puts own king in check");
        }
        // since none of those went off, make the move
        else if (move.getPromotionPiece() != null){
            ChessPiece.PieceType type = move.getPromotionPiece();
            board.addPiece(move.getEndPosition(), new ChessPiece(turn,type));
            board.addPiece(move.getStartPosition(), null);

            switchTeamTurn();
        }
        else {
            board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
            board.addPiece(move.getStartPosition(), null);

            switchTeamTurn();
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return !kingSafe(board, teamColor);

    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // it's their turn
        // their king is in check
        // they cannot make a valid move

        return teamColor == turn && isInCheck(teamColor) && isInStalemate(teamColor);

    }


    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ChessPosition tempPosition;
        ChessPiece tempPiece;

        // go through the whole board
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                tempPosition = new ChessPosition(i, j);
                tempPiece = board.getPiece(tempPosition);
                // if you find a piece of that team
                if (tempPiece != null && tempPiece.getTeamColor() == teamColor) {
                    // call validMoves on each piece of that color
                    // if any of the calls returns one or more valid move, return false
                    if (!validMoves(tempPosition).isEmpty()) {
                        return false;
                    }
                }
            }
        }

        return true;

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
