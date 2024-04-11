import chess.ChessGame;
import chess.ChessPiece;
import dataAccess.DataAccessException;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.Server;
import spark.Spark;


public class Main {

    public static void main(String[] args) throws DataAccessException {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
        Server server = new Server();
        server.run(8080);
        Spark.webSocket("/connect", Server.class); // SERVER RECEIVE???
        Spark.get("/echo/:msg", (req, res) -> "HTTP response: " + req.params(":msg")); // SERVER RECEIVE?
        System.out.println("SERVER RECEIVE...?");
    }



}