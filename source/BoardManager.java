// Author:   Jose Santos
// FileName: BoardManager.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//******************************************************************************************************
/** This class creates a BoardManager object which holds two Boards. One Board is used to display
  * the falling Shapes of the Tetris game. The other is used to display the next Shape.
  */
//******************************************************************************************************
public class BoardManager extends JPanel implements ActionListener, KeyListener, Subject
{
	// reference to the subject implemenation
	private SubjectImpl subject;

	// data members
	private Board        gameBoard;
	private Board        nextShapeBoard;
	private int          animationDelay = 1000;
	private JFrame       window;
	private JPanel       gameBoardPanel;
	private JPanel       nextShapeBoardPanel;
	private JPanel       scorePanel;
	private Timer        animationTimer;
	private int          level;
	private int          score;

	//**************************************************************************************************
	/** Creates a BoardManager object consisting of a game Board and a Board containing the next
	  * shape.
	  * @param gameBoard the game Board
	  * @param window the window for which to register a keyListener
	  */
	//**************************************************************************************************
	public BoardManager(Board gameBoard, TetrisGUI window)
	{
		// store the game Board
		this.gameBoard = gameBoard;

		// create the nextShapeBoard
		nextShapeBoard = new Board(2, 5, "Next Shape");

		// store a reference to the window that this BoardManager is associated with
		this.window = window;

		// initialize the level to 1
		level = 1;

		// initialize the score to 0
		score = 0;

		// initialize the panels
		initPanels();

		// add the gameBoard to the gameBoardPanel
		gameBoardPanel.add(gameBoard, BorderLayout.CENTER);

		// add the nextShapeBoard to the nextShapeBoardPanel
		nextShapeBoardPanel.add(nextShapeBoard);

		// create the subject
		subject = new SubjectImpl();
	}

	//**************************************************************************************************
	/** Moves the current Shape down when the animation timer initiates this method.
	  */
	//**************************************************************************************************
	public void actionPerformed(ActionEvent event)
	{
		if (gameOver())
		{
			// end the game
			endGame();

			// call the restart() method which restarts the game if the user chooses to do so
			restartGame();
		}
		// only run the logic if the current Shape of the game Board can no longer move down
		else if (!gameBoard.getShape().canMoveDown())
		{
			// run the logic of the game
			runLogic();
		}
		else
		{
			// move the current shape of the game Board down one position
			gameBoard.moveShape("Down");
		}
	}

	//**************************************************************************************************
	/** Adds an Observer to this BoardManager (which is a Subject).
	  * @param observer the Observer that will now be observing this BoardManager
	  */
	//**************************************************************************************************
	public void attach(Observer observer)
	{
		// attach the Observer to the Subject
		subject.attach(observer);
	}

	//**************************************************************************************************
	/** Detaches an observer from this BoardManager (which is a Subject).
	  * @param observer the Observer that will no longer be observing this BoardManager
	  */
	//**************************************************************************************************
	public void detach(Observer observer)
	{
		// detach the Observer from the Subject
		subject.detach(observer);
	}

	//**************************************************************************************************
	/** Displays the two Boards in this BoardManager.
	  */
	//**************************************************************************************************
	public void displayMainPanel()
	{
		// display the gameBoard
		gameBoard.displayBoardPanel();

		// display the Board containing the next Shape
		nextShapeBoard.displayBoardPanel();
	}

	//**************************************************************************************************
	/** Ends the game. Note that this stops the animation timer and removes the keyListener.
	  */
	//**************************************************************************************************
	public void endGame()
	{
		// stop the animation timer
		animationTimer.stop();

		// remove the key listener only there's actually a TetrisGUI associated with this BoardManager
		if (window != null)
		{
			// this BoardManager will no longer receive key events
			window.removeKeyListener(this);
		}
	}

	//**************************************************************************************************
	/** Returns true if it is game over. This happens when the current shape is on the first row and
	  * it can no longer move down.
	  */
	//**************************************************************************************************
	public boolean gameOver()
	{
		// holds whether or not it is game over
		boolean retVal = false;

		// determine if it is game over
		if ( gameBoard.getShape().onFirstRow() &&
		    !gameBoard.getShape().canMoveDown())
		{
			// it is game over
			retVal = true;
		}

		return retVal;
	}

	//**************************************************************************************************
	/** Returns the animation timer of this BoardManager.
	  * @return the animation timer of this BoardManager
	  */
	//**************************************************************************************************
	public Timer getAnimationTimer()
	{
		// get the animation timer
		return animationTimer;
	}

	//**************************************************************************************************
	/** Returns the game Board.
	  * @return the game Board
	  */
	//**************************************************************************************************
	public Board getGameBoard()
	{
		// get the game Board
		return gameBoard;
	}

	//**************************************************************************************************
	/** Returns the level of the game.
	  * @return the level of the game
	  */
	//**************************************************************************************************
	public int getLevel()
	{
		// get the level of the game
		return level;
	}

	//**************************************************************************************************
	/** Returns the score of the game.
	  * @return the score of the game
	  */
	//**************************************************************************************************
	public int getScore()
	{
		// get the score of the game
		return score;
	}

	//**************************************************************************************************
	/** Returns the score panel.
	  * @return the score panel
	  */
	//**************************************************************************************************
	public JPanel getScorePanel()
	{
		// get the score panel
		return scorePanel;
	}

	//**************************************************************************************************
	/** Returns the Board containing the next Shape.
	  * @return the Board containing the next Shape
	  */
	//**************************************************************************************************
	public Board getNextShapeBoard()
	{
		// get the nextShapeBoard
		return nextShapeBoard;
	}

	//**************************************************************************************************
	private void initPanels()
	{
		// create the panel that will hold the boardPanel and the nextShapePanel
		setLayout(new BorderLayout());

		// create the panel that will hold the gameBoard
		gameBoardPanel = new JPanel(new BorderLayout());

		// create the panel that will hold a label displaying the score
		scorePanel = new JPanel(new GridLayout(1, 2));

		// create the panel that will hold the nextShapeBoard
		nextShapeBoardPanel = new JPanel(new GridLayout(5, 1));

		// add the boardPanel and the nextShapePanel to the mainPanel
		add(gameBoardPanel, BorderLayout.CENTER);
		add(nextShapeBoardPanel, BorderLayout.EAST);

		// add the scorePanel to the gameBoardPanel
		gameBoardPanel.add(scorePanel, BorderLayout.SOUTH);
	}

	//**************************************************************************************************
	/** Moves the current to the direction indicated by the keypress. Note that the following are the
	  * key being read:
	  *                  left arrow key  = move left,
	  *                  right arrow key = move right,
	  *                  down arrow key  = move down,
	  *                  up arrow key    = rotate
	  */
	//**************************************************************************************************
	public void keyPressed(KeyEvent event)
	{
		// get the String representation of the key that was just pressed
		String key = event.getKeyText(event.getKeyCode());

		// move the shape of game board according to the key that was pressed
		gameBoard.moveShape(key);
	}

	//**************************************************************************************************
	/** Note that this method is never used. It is only here because this class implements a
	  * KeyListener.
	  */
	//**************************************************************************************************
	public void keyReleased(KeyEvent event)
	{
	}

	//**************************************************************************************************
	/** Note that this method is never used. It is only here because this class implements a
	  * KeyListener.
	  */
	//**************************************************************************************************
	public void keyTyped(KeyEvent event)
	{
	}

	//**************************************************************************************************
	/** Allows for this BoardManager to notify its Observers that a change to it has occurred.
	  */
	//**************************************************************************************************
	public void notifyObservers()
	{
		// notify all the Observers of the Subject that a change has occurred
		subject.notifyObservers();
	}

	//**************************************************************************************************
	/** Restarts the game if the user chooses to do so. Note that upon calling this method, a dialog
	  * box will appear that prompts the user whether or not to continue. If the yes option is selected
	  * then the game will be restarted. If the no option is selected, then the game will be exited.
	  */
	//**************************************************************************************************
	public void restartGame()
	{
		// store the choice
		int choice = JOptionPane.showConfirmDialog(null,
		                                          "Begin a new game?",
		                                          "New Game",
		                                           JOptionPane.YES_NO_OPTION);

        //*****************************************************************************************//
		// NOTE: Restart the game only if the user selects the YES_OPTION. If either the NO_OPTION //
		//       is selected or the 'x' button is clicked, exit the application.                   //
		//*****************************************************************************************//
		if (choice == JOptionPane.YES_OPTION)
		{
			// reset the score
			score = -1;

			// reset the level
			level = 1;

			// reset the animation delay
			animationDelay = 1000;

			// start the game again
			startGame();

			// notify the observers
			notifyObservers();
		}
		else
		{
			// exit the application
			System.exit(0);
		}
	}

	//**************************************************************************************************
	/** Runs the logic of the Tetris game. This includes things like cascading full blocks when the
	  * current Shape can no longer move downward.
	  */
	//**************************************************************************************************
	public void runLogic()
	{
		//************************************************************************************//
		// NOTE: Set the current Shape of the game Board to be that which is contained in the //
		//       nextShapeBoard.                                                              //
		//************************************************************************************//
		gameBoard.setShape( nextShapeBoard.getShape());

		// generate a new next Shape
		nextShapeBoard.setShape( nextShapeBoard.generateShape());

		// cascade rows if there exists at least one full row
		if (gameBoard.fullRowExists())
		{
			// cascade the full rows
			gameBoard.cascadeRows();
		}

		// notify all observers
		notifyObservers();
	}

	//**************************************************************************************************
	/** Sets the level of the game.
	  * @param level the level to set
	  */
	//**************************************************************************************************
	public void setLevel(int level)
	{
		// set the level of the game
		this.level = level;
	}

	//**************************************************************************************************
	/** Sets the score of the game.
	  * @param score the score to set
	  */
	//**************************************************************************************************
	public void setScore(int score)
	{
		// set the score of the game
		this.score = score;
	}

	//**************************************************************************************************
	/** Starts the game.
	  */
	//**************************************************************************************************
	public void startGame()
	{
		// clear both boards
		gameBoard.clearBoard();
		nextShapeBoard.clearBoard();

		// generate a new shape for the game board
		gameBoard.setShape( gameBoard.generateShape());

		// generate a new shape for the nextShapeBoard
		nextShapeBoard.setShape( nextShapeBoard.generateShape());

		// create the animation Timer
		animationTimer = new Timer(animationDelay, this);

		// display the main panel
		displayMainPanel();

		// initiate the animation Timer
		animationTimer.start();

		// have BoardManager process Key events only if there's a TetrisGUI associated with this BoardManager
		if (window != null)
		{
			window.addKeyListener(this);
		}
	}
}