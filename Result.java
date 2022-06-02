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
public class Result
{
    public int value;
    public ArrayList<Move> moves;
    public Result(int value)
    {
        this.value = value;
        moves = new ArrayList<Move>();
    }
    public void add(int row, int col)
    {
        Move next = new Move(row, col);
        moves.add(0, next);
    }
}