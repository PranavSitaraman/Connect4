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
public class Move
{
    public int row;
    public int column;
    public static Result[] eval = new Result[7];
    public Move(int row, int column)
    {
        this.row = row;
        this.column = column;
    }
    public String toString()
    {
        return "(" + (column + 1) + ", " + (7 - (row + 1)) + ")";
    }
    public static void updateEval(Board board)
    {
        for (int j = 0; j < board.board[0].length; j++)
        {
            int i = board.getTop(j);
            if (i != -1 && board.board[i][j] == ' ')
            {
                board.board[i][j] = Game.player;
                eval[j] = Minimax.minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (Game.player != 'x') eval[j].value *= -1;
                board.board[i][j] = ' ';
            }
            else eval[j] = new Result(Integer.MAX_VALUE);
        }
    }
    public static Move computerMove(Board board)
    {
        Move bestMove = new Move(-1, -1);
        int best = Integer.MAX_VALUE;
        ArrayList<Integer> possible = new ArrayList<Integer>();
        for (int j = 0; j < board.board[0].length; j++)
        {
            if (eval[j].value < best)
            {
                best = eval[j].value;
                possible = new ArrayList<Integer>();
                possible.add(j);
            }
            else if (eval[j].value == best) possible.add(j);
        }
        bestMove.column = possible.get((int)(Math.random() * possible.size()));
        bestMove.row = board.getTop(bestMove.column);
        return bestMove;
    }
}