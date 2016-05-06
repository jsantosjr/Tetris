// Author:   Jose Santos
// FileName: LevelManager.java

import javax.swing.*;

//******************************************************************************************************
/** This class creates a LevelManager object which is an Observer of the BoardManager. Note that the
  * BoardManager, being a Subject, will call the LevelManager whenever a point is scored. If the score
  * is high enough, the level is increased. That is, the animation delay of the BoardManager's
  * animation timer will be decreased.
  */
//******************************************************************************************************
public class LevelManager extends JLabel implements Observer
{
	// data members
	private BoardManager boardManager;
	private int          level;

	public LevelManager(BoardManager boardManager)
	{
		// store a reference to the BoardManager that this ScoreManager is observing
		this.boardManager = boardManager;

		// set the text of this Label to display the current level
		setText("Level: " + boardManager.getLevel());

		// add LevelManager to the score panel of the BoardManager
		this.boardManager.getScorePanel().add(this);

		// set the label visible
		setVisible(true);
	}

	//**************************************************************************************************
	public void update()
	{
		// get the score of the BoardManager
		int score = boardManager.getScore();

		// get the animation delay of the animation timer in the BoardManager
		int animationDelay = boardManager.getAnimationTimer().getDelay();

		// decrease the current animation delay by 200
		animationDelay -= 200;

		// only increase the level when the score is greater than 0
		if (score > 0)
		{
			//*****************************************************************************************//
			// NOTE: Increase the level every 10 points. Note that we don't want to increase the level //
			//       if the animation delay is less than 200. This will make the game too difficult.   //
			//*****************************************************************************************//
			if (score % 10 == 0 && animationDelay >= 200)
			{
				// increase the level by 1
				boardManager.setLevel( boardManager.getLevel() + 1);

				// set the new animation delay so that the game runs faster
				boardManager.getAnimationTimer().setDelay(animationDelay);
			}
		}

		// update the level on the display
		setText("Level: " + boardManager.getLevel());
	}
}
