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
public class Minimax
{
	public static Result minimax(Board board, int depth, boolean maximum, int alpha, int beta)
    {
        int score = board.evaluate();
        if (score == 10) return new Result(score - depth);
        else if (score == -10) return new Result(score + depth);
        else if (board.full() || depth == 9) return new Result(0);
        if (maximum)
        {
            Result best = new Result(Integer.MIN_VALUE);
            for (int j = 0; j < board.board[0].length; j++)
            {
                int i = board.getTop(j);
                if (i != -1 && board.board[i][j] == ' ')
                {
                    board.board[i][j] = Game.player;
                    Result next = minimax(board, depth + 1, !maximum, alpha, beta);
                    next.add(i, j);
                    board.board[i][j] = ' ';
                    if (next.value > best.value) best = next;
                    alpha = Math.max(alpha, best.value);
                }
                if (beta <= alpha) break;
            }
            return best;
        }
        else
        {
            Result best = new Result(Integer.MAX_VALUE);
            for (int j = 0; j < board.board[0].length; j++)
            {
                int i = board.getTop(j);
                if (i != -1 && board.board[i][j] == ' ')
                {
                    board.board[i][j] = Game.opponent;
                    Result next = minimax(board, depth + 1, !maximum, alpha, beta);
                    next.add(i, j);
                    board.board[i][j] = ' ';
                    if (next.value < best.value) best = next;
                    beta = Math.min(beta, best.value);
                }
                if (beta <= alpha) break;
            }
            return best;
        }
    }
}