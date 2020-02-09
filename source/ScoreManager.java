// Author:   Jose Santos
// FileName: ScoreManager.java

import javax.swing.*;

//******************************************************************************************************
/** This class creates a ScoreManager object which is an Observer of the BoardManager. Note that the
  * BoardManager, being a Subject, will call the ScoreManager when the score needs to be updated.
  */
//******************************************************************************************************
public class ScoreManager extends JLabel implements Observer
{
	// data members
	private BoardManager m_boardManager;

	public ScoreManager(BoardManager boardManager)
	{
		// store a reference to the BoardManager that this ScoreManager is observing
		m_boardManager = boardManager;

		// set the text of this Label to display the score
		setText("Score: " + boardManager.getScore());

		// add ScoreManager to the score panel of the BoardManager
		m_boardManager.getScorePanel().add(this);

		// set the label visible
		setVisible(true);
	}

	//**************************************************************************************************
	public void update()
	{
		// increment the score of the game by 1
		m_boardManager.setScore(m_boardManager.getScore() + 1);

		// update the score on the display
		setText("Score: " + m_boardManager.getScore());
	}
}
