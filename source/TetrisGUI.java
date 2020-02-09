// Author:   Jose Santos
// FileName: GUI.java

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JOptionPane;

public class TetrisGUI extends JFrame
{
	// data members
	private Board        m_opponentBoard;
	private Board        m_playerBoard;
	private BoardManager m_opponentBoardManager;
	private BoardManager m_playerBoardManager;
	private LevelManager m_opponentLevelManager;
	private LevelManager m_playerLevelManager;
	private ScoreManager m_opponentScoreManager;
	private ScoreManager m_playerScoreManager;

	public static void main(String args[])
	{
		// create the TetrisGUI
		TetrisGUI tetrisGUI = new TetrisGUI();
	}
	//**************************************************************************************************
	public TetrisGUI()
	{
		// call the base constructor in order to title the window
		super("Tetris");

		// initialize the BoardManagers
		initBoardManagers();

		// initialize the ScoreManagers
		initScoreManagers();

		// initialize the LevelManagers
		initLevelManagers();

		// don't allow for the window to be resized
		setResizable(false);

		// set the location of the window
		setLocation(100, 200);

		// set the size of the window (based on the number of BoardManagers)
		setSize(400 * getContentPane().getComponents().length, 450);

		// display the window
		setVisible(true);

		// start the tetris game
		m_playerBoardManager.startGame();
		//opponentBoardManager.startGame();

		// close the program when the x is pressed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//**************************************************************************************************
	private void initBoardManagers()
	{
		// create the Board for the player on this machine
		m_playerBoard = new Board(16, 10, "Player 1", Color.BLACK);

		// create the Board for the opponent
		m_opponentBoard = new Board(16, 10, "Player 2", Color.BLACK);

		// set the content pane to have a 1 x 2 grid layout
		getContentPane().setLayout(new GridLayout(1, 2));

		// create the two BoardManagers
		m_playerBoardManager   = new BoardManager(m_playerBoard, this);
		m_opponentBoardManager = new BoardManager(m_opponentBoard, null);

		// add the playerBoardManager to the content pane
		getContentPane().add(m_playerBoardManager);

		// add the opponentBoardManager to the content pane
		//getContentPane().add(opponentBoardManager);
	}
	//**************************************************************************************************
	private void initLevelManagers()
	{
		// create the LevelManager that will observe the BoardManager of the player on this machine
		m_playerLevelManager = new LevelManager(m_playerBoardManager);

		// create the LevelManager that will observe the BoardManager of the opponent
		m_opponentLevelManager = new LevelManager(m_opponentBoardManager);

		// attach the playerLevelManager to the playerBoardManager
		m_playerBoardManager.attach(m_playerLevelManager);

		// attach the opponentLevelManager to the opponentBoardManager
		//opponentBoardManager.attach( opponentLevelManager);
	}
	//**************************************************************************************************
	private void initScoreManagers()
	{
		// create the ScoreManager that will observe the BoardManager of the player on this machine
		m_playerScoreManager = new ScoreManager(m_playerBoardManager);

		// create the ScoreManager that will observe the BoardManager of the opponent
		m_opponentScoreManager = new ScoreManager(m_opponentBoardManager);

		// attach the playerScoreManager to the playerBoardManager
		m_playerBoardManager.attach(m_playerScoreManager);

		// attach the opponentScoreManager to the opponentBoardManager
		//opponentBoardManager.attach( opponentScoreManager);
	}
}