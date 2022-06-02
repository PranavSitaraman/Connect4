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
public class Connect4 extends Application
{
	private Group root;
	private Tile board[][];
	private ArrayList<Circle> coins;
	private Game game;
	private ImageView imageView;
	private Iteration next;
	private Stage primaryStage;
	private int col;
	private boolean action;
	private Text eval[];
	private Text lines[];
	private Button engine;
	private Text title;
	private LinearGradient blue = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop[]
	{
		new Stop(0, Color.web("#abb2e0")),
		new Stop(1, Color.web("#6b6f8a"))
	});
	private LinearGradient white = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop[]
	{
		new Stop(0, Color.web("#f2f2f5")),
		new Stop(1, Color.web("#a2a2a3"))
	});
	private LinearGradient red = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop[]
	{
		new Stop(0, Color.web("#f55f5f")),
		new Stop(1, Color.web("#a11d1d"))
	});
	private LinearGradient yellow = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop[]
	{
		new Stop(0, Color.web("#ebdc7a")),
		new Stop(1, Color.web("#bfb44b"))
	});
	private void addTile()
	{
		CompletableFuture<Iteration> process = CompletableFuture.supplyAsync(() -> game.play(col));
		while (!process.isDone());
		try { next = process.get(); } catch (Exception e) { }
		if (next.result != Outcome.USERWIN) addTile(next.computerMove, yellow);
		for (int i = 0; i < eval.length; i++)
		{
			if (Move.eval[i].value != Integer.MAX_VALUE)
			{
				eval[i].setText(Integer.toString(Move.eval[i].value));
				lines[i].setText("(" + (i + 1) + ", " + (7 - (game.board.getTop(i) + 1)) + ") - " + Move.eval[i].moves);
			}
            else
			{
				eval[i].setText("~");
				lines[i].setText("~");
			}
		}
		if (next.result != Outcome.NOWIN)
		{
			if (next.result == Outcome.COMPUTERWIN) title.setText("Computer wins!");
			else if (next.result == Outcome.TIE) title.setText("Tie!");
			else if (next.result == Outcome.USERWIN) title.setText("You win!");
			engine.setText("New Game");
		}
		else action = false;
	};
	private Circle addTile(Move m, LinearGradient gradient, int q)
	{
		Circle a = new Circle(100 + 75 * m.column + 37.5, 137.5 + 0, 30);
		a.setRadius(30);
		a.setFill(gradient);
		coins.add(a);
		TranslateTransition slide = new TranslateTransition();
		slide.setToX(0);
		slide.setToY(75 * m.row + 37.5);
		slide.setNode(a);
		slide.setDuration(Duration.millis(1000));
		slide.setOnFinished(e -> addTile());
		root.getChildren().add(a);
		slide.play();
		return a;
	}
	private Circle addTile(Move m, LinearGradient gradient)
	{
		Circle a = new Circle(100 + 75 * m.column + 37.5, 137.5 + 0, 30);
		a.setRadius(30);
		a.setFill(gradient);
		coins.add(a);
		TranslateTransition slide = new TranslateTransition();
		slide.setToX(0);
		slide.setToY(75 * m.row + 37.5);
		slide.setNode(a);
		slide.setDuration(Duration.millis(1000));
		root.getChildren().add(a);
		slide.play();
		return a;
	}
	private final EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>()
	{
		@Override
		public void handle(KeyEvent event)
		{
			if (action) return;
			if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D)
			{
				action = true;
				if (col != board[0].length - 1)
				{
					col++;
					TranslateTransition slide = new TranslateTransition(Duration.millis(1000), imageView);
					slide.setToX(75 * col);
					slide.setToY(0);
					slide.setNode(imageView);
					slide.setDuration(Duration.millis(100));
					slide.play();
				}
				action = false;
			}
			else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A)
			{
				action = true;
				if (col != 0)
				{
					col--;
					TranslateTransition slide = new TranslateTransition(Duration.millis(1000), imageView);
					slide.setToX(75 * col);
					slide.setToY(0);
					slide.setNode(imageView);
					slide.setDuration(Duration.millis(100));
					slide.play();
				}
				action = false;
			} 
			else if (event.getCode() == KeyCode.ENTER && game.board.getTop(col) != -1)
			{
				action = true;
				addTile(new Move(game.board.getTop(col), col), red, 1);
			}
			event.consume();
		}
	}; 
    @Override
	public void start(Stage primaryStage)
	{
		root = new Group();
		board = new Tile[6][7];
		coins = new ArrayList<Circle>();
		game = new Game();
		col = 0;
		action = false;
		eval = new Text[7];
		lines = new Text[7];
		this.primaryStage = primaryStage;
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				board[i][j] = new Tile();
				board[i][j].rect = new Rectangle(100 + 75 * j, 137.5 + 75 * i, 75, 75);
				board[i][j].circ = new Circle(100 + 75 * j + 37.5, 137.5 + 75 * i + 37.5, 30);
				root.getChildren().addAll(board[i][j].rect, board[i][j].circ);
				board[i][j].rect.setFill(blue);
				board[i][j].circ.setFill(white);
				board[i][j].rect.setStroke(Color.web("#d2d2d4"));
				board[i][j].rect.setStrokeWidth(2);
			}
		}
		try
		{
			imageView = new ImageView(new Image(new FileInputStream("C:/Users/Pranav Sitaraman/Documents/Programming/Java/CSE/Connect4/Arrow.png")));
			imageView.setX(121); 
			imageView.setY(37.5);
			imageView.setFitHeight(75); 
			imageView.setFitWidth(75); 
			imageView.setPreserveRatio(true);
			root.getChildren().add(imageView);
		}
		catch (Exception e) { }
		title = new Text(750, 100, "Connect 4");
		title.setFont(new Font(50));
		title.setWrappingWidth(400);
		title.setTextAlignment(TextAlignment.CENTER);
		Text desc1 = new Text(750, 162.5, "Test your Connect 4 skills against a computer!");
		desc1.setFont(new Font(20));
		desc1.setWrappingWidth(400);
		desc1.setTextAlignment(TextAlignment.JUSTIFY);
		Text desc2 = new Text(750, 225, "Use the arrow keys to navigate between columns and enter to drop your piece!");
		desc2.setFont(new Font(20));
		desc2.setWrappingWidth(400);
		desc2.setTextAlignment(TextAlignment.JUSTIFY);
		Text desc3 = new Text(750, 287.5, "Click the button below to turn on engine mode!");
		desc3.setFont(new Font(20));
		desc3.setWrappingWidth(400);
		desc3.setTextAlignment(TextAlignment.JUSTIFY);
		root.getChildren().addAll(title, desc1, desc2, desc3);
		engine = new Button("Engine Mode");
		engine.setLayoutX(750);
		engine.setLayoutY(350);
		for (int i = 0; i < eval.length; i++)
		{
			if (Move.eval[i].value != Integer.MAX_VALUE) eval[i] = new Text(120 + 75 * i, 637.5, Integer.toString(Move.eval[i].value));
            else eval[i] = new Text(120 + 75 * i, 637.5, "~");
			eval[i].setFont(new Font(20));
			eval[i].setWrappingWidth(37.5);
			eval[i].setTextAlignment(TextAlignment.CENTER);
			eval[i].setVisible(false);
			lines[i] = new Text(750, 412.5 + i * 37.5, Integer.toString(Move.eval[i].value));
			lines[i].setFont(new Font(15));
			lines[i].setWrappingWidth(500);
			lines[i].setVisible(false);
			root.getChildren().addAll(eval[i], lines[i]);
		}
		engine.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e)
			{
				if (engine.getText().equals("Engine Mode"))
				{
					engine.setText("Gameplay Mode");
					for (int i = 0; i < eval.length; i++)
					{
						eval[i].setVisible(true);
						lines[i].setVisible(true);
					}
				}
				else if (engine.getText().equals("Gameplay Mode"))
				{
					engine.setText("Engine Mode");
					for (int i = 0; i < eval.length; i++)
					{
						eval[i].setVisible(false);
						lines[i].setVisible(false);
					}
				}
				else start(primaryStage);
			}
		});
		root.getChildren().add(engine);
		Scene scene = new Scene(root, 800, 600, Color.web("#f5f9ff"));
		primaryStage.setTitle("Connect 4");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setFullScreen(true);
		primaryStage.setResizable(false);
		primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, keyListener);
    }
    public static void main(String[] args)
    {
        try { launch(args); }
		catch (Exception e) { }
    }
}