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
public class Board
{
    public char[][] board;
    public Board()
    {
        board = new char[6][7];
        for (int i = 0; i < board.length; i++) for (int j = 0; j < board[i].length; j++) board[i][j] = ' ';
    }
    public boolean full()
    {
        for (int i = 0; i < board.length; i++) for (int j = 0; j < board[i].length; j++) if (board[i][j] == ' ') return false;
        return true;
    }
    public int getTop(int col)
    {
        int row = board.length - 1;
        while (row >= 0 && board[row][col] != ' ') row--;
        return row;
    }
    public int evaluate()
    {
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length - 3; j++)
            {
                if (board[i][j] == board[i][j + 1] && board[i][j + 1] == board[i][j + 2] && board[i][j + 2] == board[i][j + 3])
                {
                    if (board[i][j] == Game.player) return 10;
                    else if (board[i][j] == Game.opponent) return -10;
                }
            }
        }
        for (int i = 0; i < board.length - 3; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j] == board[i + 1][j] && board[i + 1][j] == board[i + 2][j] && board[i + 2][j] == board[i + 3][j])
                {
                    if (board[i][j] == Game.player) return 10;
                    else if (board[i][j] == Game.opponent) return -10;
                }         
            }
        }
        for (int i = 3; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length - 3; j++)
            {
                if (board[i][j] == board[i - 1][j + 1] && board[i - 1][j + 1] == board[i - 2][j + 2] && board[i - 2][j + 2] == board[i - 3][j + 3])
                {
                    if (board[i][j] == Game.player) return 10;
                    else if (board[i][j] == Game.opponent) return -10;
                }
            }
        }
        for (int i = 3; i < board.length; i++)
        {
            for (int j = 3; j < board[i].length; j++)
            {
                if (board[i][j] == board[i - 1][j - 1] && board[i - 1][j - 1] == board[i - 2][j - 2] && board[i - 2][j - 2] == board[i - 3][j - 3])
                {
                    if (board[i][j] == Game.player) return 10;
                    else if (board[i][j] == Game.opponent) return -10;
                }
            }
        }
        return 0;
    }
}