// Author:   Jose Santos
// FileName: Shape.java

import java.awt.Color;
import java.util.*;

//******************************************************************************************************
/** This class creates an abstract Shape object. Its main functionalities include moving down,
  * moving left, moving right, and rotating. Note that each Shape is composed of 4 Blocks.
  */
//******************************************************************************************************
public abstract class Shape
{
	// data members
	private Block focalBlock;
	private Board board;
	private Color color;
	private List  blocks;

	//**************************************************************************************************
	/** Constructs a Shape object that is set relative to the passed in Board.
	  * @param b the Board to which this Shape is relative
	  * @param color the color of this Shape
	  */
	//**************************************************************************************************
	public Shape(Board b, Color color)
	{
		// set the Board to which this Shape is relative
		board = b;

		// create the ArrayList that will hold the Blocks of this Shape
		blocks = new ArrayList();

		// set the color of this Shape
		this.color = color;

		//***************************************************************************************//
		// NOTE: Create the focal block. Note that this is the Block that doesn't move when this //
		//       Shape is rotated. We'll have it positioned on the first row and the middle      //
		//       column of the associated Board.                                                 //
		//***************************************************************************************//
		focalBlock = new Block(b.getNumOfCols() / 2, 0, color);
	}

	//**************************************************************************************************
	/** Adds this Shape to the associated Board.
	  * @param add true if this Shape is be added to the Board. If the value if false, then this Shape
	  * will not be added to the Board
	  */
	//**************************************************************************************************
	public void addToBoard(boolean add)
	{
		// only add this Shape to the Board if the add variable is set to true
		if (add)
		{
			for (int i = 0; i < blocks.size(); i++)
			{
				// get the next Block of this Shape
				Block block = (Block)blocks.get(i);

				// only make changes to the Board if the block is on a valid position
				if (board.validPos(block.getXPos(), block.getYPos()))
				{
					// get the BoardCell on which the Block will be placed
					BoardCell boardCell = board.getBoardCell(block.getXPos(), block.getYPos());

					// the BoardCell will now be occupied by the Block
					boardCell.setEmpty(false);

					// the color of the BoardCell will be that of the Block
					boardCell.setColor(block.getColor());

					// enable the border paint on the BoardCell
					boardCell.getCellJButton().setBorderPainted(true);
				}
			}
		}
	}

	//**************************************************************************************************
	/** Returns true if this Shape is allowed to move down. Note that it can only do so if the BoardCell
	  * below each Block is empty.
	  * @return true if this Shape is allowed to move down (false otherwise)
	  */
	//**************************************************************************************************
	public boolean canMoveDown()
	{
		// holds whether or not this Shape can move down
		boolean retVal = true;

		// momentarily remove this Shape from the Board
		removeFromBoard(true);

		// check each Block to make sure it's not blocked below
		for (int i = 0; i < blocks.size(); i++)
		{
			// get the next Block
			Block block = (Block)blocks.get(i);

			// get the BoardCell that's below this Block
			BoardCell boardCell = board.getBoardCell(block.getXPos(), block.getYPos() + 1);

			//***************************************************************************************//
			// NOTE: If either no BoardCell exists below the Block OR the BoardCell is filled, then  //
			//       we know that this Shape can't be moved down.                                    //
			//***************************************************************************************//
			if (boardCell == null || !boardCell.isEmpty())
			{
				retVal = false;
				break;
			}
		}

		// add this Shape back to the Board
		addToBoard(true);

		return retVal;
	}

	//**************************************************************************************************
	/** Returns true if this Shape is allowed to move left. Note that it can only do so if the left of
	  * each Block is not occupied by a filled BoardCell.
	  * @return true if this Shape is allowed to move left (false otherwise)
	  */
	//**************************************************************************************************
	public boolean canMoveLeft()
	{
		// holds whether or not this Shape can move left
		boolean retVal = true;

		// momentarily remove this Shape from the Board
		removeFromBoard(true);

		// check each Block to make sure it's not blocked on the left side
		for (int i = 0; i < blocks.size(); i++)
		{
			// get the next Block
			Block block = (Block)blocks.get(i);

			// get the BoardCell to the left of this Block
			BoardCell boardCell = board.getBoardCell(block.getXPos() - 1, block.getYPos());

			//****************************************************************************************//
			// NOTE: If either no BoardCell exists on the left OR it's filled, then we know that this //
			//       Shape can't be moved to the left.                                                //
			//****************************************************************************************//
			if (boardCell == null || !boardCell.isEmpty())
			{
				retVal = false;
				break;
			}
		}

		// add this Shape back to the Board
		addToBoard(true);

		return retVal;
	}

	//**************************************************************************************************
	/** Returns true if this Shape is allowed to move right. Note that it can only do so if the right of
	  * each Block is not occupied by a filled BoardCell.
	  * @return true if this Shape is allowed to move right (false otherwise)
	  */
	//**************************************************************************************************
	public boolean canMoveRight()
	{
		// holds whether or not this Shape can move right
		boolean retVal = true;

		// momentarily remove this Shape from the Board
		removeFromBoard(true);

		// check each Block to make sure it's not blocked on the right side
		for (int i = 0; i < blocks.size(); i++)
		{
			// get the next Block
			Block block = (Block)blocks.get(i);

			// get the BoardCell to the right of this Block
			BoardCell boardCell = board.getBoardCell(block.getXPos() + 1, block.getYPos());

			//*****************************************************************************************//
			// NOTE: If either no BoardCell exists on the right OR it's filled, then we know that this //
			//       Shape can't be moved to the right.                                                 //
			//*****************************************************************************************//
			if (boardCell == null || !boardCell.isEmpty())
			{
				retVal = false;
				break;
			}
		}

		// add this Shape back to the Board
		addToBoard(true);

		return retVal;
	}

	//**************************************************************************************************
	/** Returns true if this Shape can be rotated. Note that this will be overridden by derived classes.
	  */
	//**************************************************************************************************
	public abstract boolean canRotate();

	//**************************************************************************************************
	/** Returns the List of Blocks that compose this Shape.
	  * @return the List of Blocks that compose this Shape
	  */
	//**************************************************************************************************
	public List getBlocks()
	{
		// get the Blocks composing this Shape
		return blocks;
	}

	//**************************************************************************************************
	/** Returns the Board that's associated with this Shape.
	  * @return the Board that's associated with this Shape
	  */
	//**************************************************************************************************
	public Board getBoard()
	{
		// get the associated Board
		return board;
	}

	//**************************************************************************************************
	/** Returns a randomly generated Color.
	  * @return a randomly generated color
	  */
	//**************************************************************************************************
	public Color generateColor()
	{
		// generate a random value (between 0 and 255) for the r value
		int r = (int)(Math.random() * 256);

		// generate a random value (between 0 and 255) for the b value
		int g = (int)(Math.random() * 256);

		// generate a random value (between 0 and 255) for the g value
		int b = (int)(Math.random() * 256);

		// generate a random color
		Color color = new Color(r, g, b);

		// get the color
		return color;
	}
	//**************************************************************************************************
	/** Returns the Color of this Shape.
	  * @return the Color of this Shape
	  */
	//**************************************************************************************************
	public Color getColor()
	{
		// get the color
		return color;
	}

	//**************************************************************************************************
    /** Returns the focal Block of this Shape. That is, the Block that doesn't move when this Shape is
      * rotated.
      * @return the focal Block of this Shape
      */
    //**************************************************************************************************
	protected Block getFocalBlock()
	{
		// get the focal Block
		return focalBlock;
	}

	//**************************************************************************************************
	/** Initializes the coordinates of this Shape. Note that this method will be overridden in
	  * derived classes.
	  */
	//**************************************************************************************************
	protected abstract void initCoordinates();

	//**************************************************************************************************
	/** Moves this Shape down one row.
	  */
	//**************************************************************************************************
	public void moveDown()
	{
		// move this Shape down as it's not being blocked from below
		if (canMoveDown())
		{
			// remove this Shape from the Board
			removeFromBoard(true);

			for (int i = 0; i < blocks.size(); i++)
			{
				// get the next Block of this Shape
				Block block = (Block)blocks.get(i);

				// move this Block down one row
				block.setYPos( block.getYPos() + 1);
			}

			// add this Shape back to the Board
			addToBoard(true);
		}
	}

	//**************************************************************************************************
	/** Moves this Shape one position to the left. Note that this can only be done if this Shape is
	  * not being blocked on the left side.
	  */
	//**************************************************************************************************
	public void moveLeft()
	{
		// move this Shape to the left as long as the left side is not blocked
		if (canMoveLeft())
		{
			// remove this Shape from the Board
			removeFromBoard(true);

			for (int i = 0; i < blocks.size(); i++)
			{
				// get the next Block of this Shape
				Block block = (Block)blocks.get(i);

				// move this Block one position to the left
				block.setXPos( block.getXPos() - 1);
			}

			// add this Shape back to the Board
			addToBoard(true);
		}
	}

	//**************************************************************************************************
	/** Moves this Shape one position to the right. Note that this can only be done if this Shape is
	  * not being blocked on the right side.
	  */
	//**************************************************************************************************
	public void moveRight()
	{
		// move this Shape to the right as long as the right side is not blocked
		if (canMoveRight())
		{
			// remove this Shape from the Board
			removeFromBoard(true);

			for (int i = 0; i < blocks.size(); i++)
			{
				// get the next Block of this Shape
				Block block = (Block)blocks.get(i);

				// move this Block one position to the right
				block.setXPos( block.getXPos() + 1);
			}

			// add this Shape back to the Board
			addToBoard(true);
		}
	}

	//**************************************************************************************************
	/** Returns true if any one of this Shape's blocks is on the first column.
	  * @return true if any one of this Shape's blocks is on the first column (false otherwise)
	  */
	//**************************************************************************************************
	public boolean onFirstCol()
	{
		// we'll assume the shape is not on the first column
		boolean retVal = false;

		for (int i = 0; i < blocks.size(); i++)
		{
			// get the next Block of this Shape
			Block block = (Block)blocks.get(i);

			// check to see if this Block is on the first column
			if (block.getXPos() == 0)
			{
				// the shape IS on the first column
				retVal = true;
				break;
			}
		}

		return retVal;
	}

	//**************************************************************************************************
	/** Returns true if any one of this Shape's blocks is on the first row.
	  * @return true if any one of this Shape's blocks is on the first row (false otherwise)
	  */
	//**************************************************************************************************
	public boolean onFirstRow()
	{
		// we'll assume the shape is not on the first row
		boolean retVal = false;

		for (int i = 0; i < blocks.size(); i++)
		{
			// get the next Block of this Shape
			Block block = (Block)blocks.get(i);

			// check to see if this Block is on the first row
			if (block.getYPos() == 0)
			{
				// the shape IS on the first row
				retVal = true;
				break;
			}
		}

		return retVal;
	}

	//**************************************************************************************************
	/** Returns true if any one of this Shape's blocks is on the last column.
	  * @return true if any one of this Shape's blocks is on the last column (false otherwise)
	  */
	//**************************************************************************************************
	public boolean onLastCol()
	{
		// we'll assume the shape is not on the last column
		boolean retVal = false;

		for (int i = 0; i < blocks.size(); i++)
		{
			// get the next Block of this Shape
			Block block = (Block)blocks.get(i);

			// check to see if this Block is on the last column
			if (block.getXPos() == board.getNumOfCols() - 1)
			{
				// the shape IS on the last column
				retVal = true;
				break;
			}
		}

		return retVal;
	}

	//**************************************************************************************************
	/** Returns true if any one of this Shape's blocks is on the last row.
	  * @return true if any one of this Shape's blocks is on the last row (false otherwise)
	  */
	//**************************************************************************************************
	public boolean onLastRow()
	{
		// we'll assume the shape is not on the last row
		boolean retVal = false;

		for (int i = 0; i < blocks.size(); i++)
		{
			// get the next Block of this Shape
			Block block = (Block)blocks.get(i);

			// check to see if this Block is on the last row
			if (block.getYPos() == board.getNumOfRows() - 1)
			{
				// the shape IS on the last row
				retVal = true;
				break;
			}
		}

		return retVal;
	}

	//**************************************************************************************************
	/** Removes this Shape from the associated Board.
	  * @param remove true if this Shape is be removed from the Board. If the value if false, then
	  * this Shape will not be removed from the Board
	  */
	//**************************************************************************************************
	public void removeFromBoard(boolean remove)
	{
		// only remove this Shape from the Board if the remove variable is set to true
		if (remove)
		{
			for (int i = 0; i < blocks.size(); i++)
			{
				// get the next Block of the this Shape
				Block block = (Block)blocks.get(i);

				// only make changes to the Board if the block is on a valid position
				if (board.validPos(block.getXPos(), block.getYPos()))
				{
					// get the BoardCell on which the Block is placed
					BoardCell boardCell = board.getBoardCell(block.getXPos(), block.getYPos());

					// the BoardCell will no longer be occupied by the Block
					boardCell.setEmpty(true);

					// the color of the BoardCell will now be that of the Board
					boardCell.setColor(board.getColor());

					// disable the border paint on the BoardCell
					boardCell.getCellJButton().setBorderPainted(false);
				}
			}
		}
	}

	//**************************************************************************************************
	/** Rotates this Shape. Note that this will be overridden by derived classes.
	  * @param remove true if this Shape should be remove from the Board prior to rotating it
	  * @param update true if this Shape should be updated on the Board after it has been rotated
	  */
	//**************************************************************************************************
	public abstract void rotate(boolean remove, boolean update);

	//**************************************************************************************************
	/** Sets the associated Board of this Shape.
	  * @param board the associated Board of this Shape
	  */
	//**************************************************************************************************
	public void setBoard(Board board)
	{
		// set the associated Board of this Shape
		this.board = board;
	}

	//**************************************************************************************************
	/** Sets the List of Blocks that compose this Shape.
	  * @param blocks the List of Blocks that compose this Shape
	  */
	//**************************************************************************************************
	public void setBlocks(List blocks)
	{
		// set the List of Blocks that compose this Shape
		this.blocks = blocks;
	}

	//**************************************************************************************************
	/** Sets the focal Block of this Shape.
	  * @param block the focal Block of this Shape
	  */
	//**************************************************************************************************
	protected void setFocalBlock(Block block)
	{
		// set the focal Block
		focalBlock = block;
	}
}