package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;


import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static ui.EscapeSequences.*;

public class DrawBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String BLANK = "   ";
    private static final String PAWN = " P ";
    private static final String ROOK = " R ";
    private static final String KNIGHT = " N ";
    private static final String BISHOP = " B ";
    private static final String QUEEN = " Q ";
    private static final String KING = " K ";

//    Draws a blank board, used for testing purposes

    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        board.resetBoard();
//        run(board, BLACK);
//        run(board, WHITE);
        drawBoard(board, null);

    }


    // takes a ChessBoard object and a TeamColor. Draws orientation based on TeamColor
    // use null as color to print both
    public static void drawBoard(ChessBoard board, ChessGame.TeamColor color) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        //out.print(ERASE_SCREEN);

        if (color == WHITE) {
            printWhiteTeamView(board, out);
        }
        else if (color == BLACK) {
            printBlackTeamView(board, out);
        }
        else {
            printWhiteTeamView(board, out);
            out.println();
            printBlackTeamView(board, out);
        }

        out.println();

    }

    private static void printBlackTeamView(ChessBoard board, PrintStream out) {
        int increment;
        int start;
        start = 0;
        increment = 1;

        printKey(out);

        drawHeader(out, start, increment);
        drawChessBoard(out, start, increment, board);
        drawHeader(out, start, increment);

    }

    private static void printWhiteTeamView(ChessBoard board, PrintStream out) {
        int start = 7;
        int increment = -1;

        printKey(out);

        drawHeader(out, start, increment);
        drawChessBoard(out, start, increment, board);
        drawHeader(out, start, increment);

    }

    private static void drawHeader(PrintStream out, int start, int increment) {

        setBlack(out);

        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print("   ");
        for (int col = start; col < BOARD_SIZE_IN_SQUARES && col >= 0; col += increment) {
            out.print(" " + headers[col] + " ");
        }
        out.print("   ");
        setBlack(out);

        out.println();
    }

    private static void drawChessBoard(PrintStream out, int start, int increment, ChessBoard board) {

        for (int col = start; col < BOARD_SIZE_IN_SQUARES && col >= 0; col += increment) {
            for (int row = start; row < BOARD_SIZE_IN_SQUARES && row >= 0; row += increment) {
                // draw vertical border
                if (row == start) {
                    int label = 8 - col;
                    out.print(SET_BG_COLOR_DARK_GREY);
                    out.print(SET_TEXT_COLOR_WHITE);
                    out.print(" " + label + " ");
                }
                // set square color
                if ((row + col) % 2 == 0) {
                    out.print(SET_BG_COLOR_WHITE);
                    out.print(SET_TEXT_COLOR_BLACK);
                }
                else {
                    out.print(SET_BG_COLOR_BLACK);
                    out.print(SET_TEXT_COLOR_WHITE);
                }
                var squares = board.getSquares();
                var square = squares[col][row];

                if (square != null) {
                    // set the color
                    if (square.getTeamColor() == WHITE) {
                        out.print(SET_TEXT_COLOR_BLUE);
                    }
                    else {
                        out.print(SET_TEXT_COLOR_RED);
                    }

                    // get the type
                    if (square.getPieceType().equals(ChessPiece.PieceType.PAWN)) {
                        out.print(PAWN);
                    }
                    else if (square.getPieceType().equals(ChessPiece.PieceType.ROOK)) {
                        out.print(ROOK);
                    }
                    else if (square.getPieceType().equals(ChessPiece.PieceType.KNIGHT)) {
                        out.print(KNIGHT);
                    }
                    else if (square.getPieceType().equals(ChessPiece.PieceType.BISHOP)) {
                        out.print(BISHOP);
                    }
                    else if (square.getPieceType().equals(ChessPiece.PieceType.QUEEN)) {
                        out.print(QUEEN);
                    }
                    else if (square.getPieceType().equals(ChessPiece.PieceType.KING)) {
                        out.print(KING);
                    }
                }
                // if there is no piece there
                else {
                    out.print(BLANK);
                }

                if (row == start + (7 * increment)) {
                    // Draw vertical border
                    int label = 8 - col;
                    out.print(SET_BG_COLOR_DARK_GREY);
                    out.print(SET_TEXT_COLOR_WHITE);
                    out.print(" " + label + " ");
                }

                setBlack(out);
            }

            out.println();
        }
    }

    private static void printKey(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print("Blue = White Team; Red = Black Team");
        out.println();
    }


    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }
}