package Connect4;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.concurrent.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.*;
public class Game
{
    public static char player;
    public static char opponent;
    public Board board;
    public Game()
    {
        player = 'x';
        opponent = 'o';
        board = new Board();
        Move.updateEval(board);
    }
    public Iteration play(int column)
    {
        Move move = new Move(0, 0);
        player = 'x'; opponent = 'o';
        Move.updateEval(board);
        move.column = column;
        move.row = board.getTop(move.column);
        board.board[move.row][move.column] = player;
        if (board.evaluate() == 10) return new Iteration(new Move(0, 0), Outcome.USERWIN);
        player = 'o'; opponent = 'x';
        Move.updateEval(board);
        player = 'o'; opponent = 'x';
        Move.updateEval(board);
        move = Move.computerMove(board);
        board.board[move.row][move.column] = player;
        if (board.evaluate() == 10) return new Iteration(move, Outcome.COMPUTERWIN);
        player = 'x'; opponent = 'o';
        Move.updateEval(board);
        if (board.full()) return new Iteration(move, Outcome.TIE);
        else return new Iteration(move, Outcome.NOWIN);
    }
}