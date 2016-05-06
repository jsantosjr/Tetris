// Author:   Jose Santos
// FileName: ThreeSpaceShape.java

import java.awt.Color;
import java.util.*;

//******************************************************************************************************
/** This class constructs a ThreeSpaceShape object that is able to rotate around a 3 x 3 space. Such
  * shapes include the TShape, LShape, JShape, ZShape, and SShape. Note that this class inherits from
  * the Shape class.
  */
//******************************************************************************************************
public abstract class ThreeSpaceShape extends Shape
{
	//**************************************************************************************************
	/** Constructs a ThreeSpaceShape object that is set relative to the passed in Board.
	  * @param b the Board to which this Shape is relative
	  * @param color the color of this Shape
	  */
	//**************************************************************************************************
	public ThreeSpaceShape(Board b, Color color)
	{
		// call the base constructor in order to initialize its data members
		super(b, color);
	}

	//**************************************************************************************************
	/** Returns true if this Shape can be rotated. False is returned otherwise.
	  * @return true if this Shape can be rotated (false otherwise)
	  */
	//**************************************************************************************************
	public boolean canRotate()
	{
		// holds whether or not this Shape can be rotated
		boolean retVal = true;

		// rotate this Shape once and remove it from the Board
		rotate(true, false);

		// get the Blocks of this Shape
		List blocks = getBlocks();

		//***************************************************************************************//
		// NOTE: Check if any of the Blocks lie on top of one of the BoardCells of this Shape's  //
		//       associated Board. If such is the case then this Shape (that is, the original    //
		//       Shape) cannot be rotated.                                                       //
		//***************************************************************************************//
		for (int i = 0; i < blocks.size(); i++)
		{
			// get the next Block of this Shape
			Block block = (Block)blocks.get(i);

			// get the BoardCell that's at the same position as the new Block
			BoardCell boardCell = getBoard().getBoardCell(block.getXPos(), block.getYPos());

			//************************************************************************************//
			// NOTE: If a boardCell doesn't exist at the position of the Block, then we know      //
			//       that we can't rotate this Shape. That is, if the boardCell is equal to null  //
			//       then the Block must have been somewhere outside the bounds of the Board. Now //
			//       let's suppose the boardCell does exist. If by chance, the Block lies on top  //
			//       of a non-empty boardCell, then again, we say that this Shape cannot be       //
			//       rotated.                                                                     //
			//************************************************************************************//
			if (boardCell == null || !boardCell.isEmpty())
			{
				retVal = false;
				break;

			}
		}

		//********************************************************************************************//
		// NOTE: Rotate this Shape 3 times in order to return it back to it's original position. Note //
		//       that on the 3rd rotate, when this Shape is at its original position, we'll want to   //
		//       add it back to the Board.                                                            //
		//********************************************************************************************//
		rotate(false, false);
		rotate(false, false);
		rotate(false, true);

		return retVal;
	}

	//**************************************************************************************************
	/** Initializes the coordinates of this ThreeSpaceShape. Note that this method will be overridden
	  * in derived classes.
	  */
	//**************************************************************************************************
	protected abstract void initCoordinates();

	//**************************************************************************************************
	/** Rotates this Shape clockwise.
	  * @param remove true if this Shape should be remove from the Board prior to rotating it
	  * @param update true if this Shape should be updated on the Board after it has been rotated
	  */
	//**************************************************************************************************
	public void rotate(boolean remove, boolean update)
	{
		// remove this Shape from the Board
		removeFromBoard(remove);

		// this will hold the clockwise path around which the Shape should move
		Block path[] = new Block[8];

		// get the blocks that compose this Shape
		List blocks = getBlocks();

		//*********************************************************************************//
		// NOTE: For each path block, this holds the distance away from the focal block of //
		//       this Shape. Note that the first element in each array represents the x    //
		//       distance away from the focal block while the second element in each array //
		//       represents the y distance away from the focal block. For clarity, let's   //
		//       look at the second array. It reads as follows: {0, -1}. This indicates    //
		//       that the second path block is 0 x positions away from the focal block's x //
		//       position and -1 y positions away from the focal block's y position. This  //
		//       means that the second path block is right above the focal block.          //
		//*********************************************************************************//
		int posFromFocus[][] = {{-1, -1},{0,-1},{ 1,-1},{ 1, 0},
		                        { 1,  1},{0, 1},{-1, 1},{-1, 0}};

		//***************************************************************//
		// NOTE: Set the path. Below is a physical representation of it. //
		//       The * represents the block that stays in the same place //
		//       when the shape is turned. The #'s represent the blocks  //
		//       on which the shape is being turned.                     //
		//              # # #                                            //
		//              # * #                                            //
		//              # # #                                            //
		// 																 //
		//       Note that the path blocks will be set in the following  //
		//       order: 												 //
		//																 //
		//              0 1 2                                            //
		//              7 * 3                                            //
		//              6 5 4                                            //
		//***************************************************************//
		for (int i = 0; i < path.length; i++)
		{
			// set the x and y position of this path block
			path[i] = new Block(getFocalBlock().getXPos() + posFromFocus[i][0],
			                    getFocalBlock().getYPos() + posFromFocus[i][1]);
		}

		//****************************************************************//
		// NOTE: The code below is essentially moving the shape around    //
		//       the path that was described above. For example, suppose  //
		//       the current shape looks like this (where @'s are the     //
		//       cells occupying the shape):                              //
		//          # @ #                                                 //
		//          # * #												  //
		//          # @ @												  //
		//                                                                //
		//       After running through the code below, the shape on the   //
		//       path will look like this:								  //
		//          # # #											      //
		//          @ * @												  //
		//			@ # #												  //
		//****************************************************************//
		for (int blockNum = 0; blockNum < blocks.size(); blockNum++)
		{
			// get the next Block from the ArrayList of Blocks
			Block block = (Block)blocks.get(blockNum);

			for (int pathNum = 0; pathNum < path.length; pathNum++)
			{
				//***********************************************************************//
				// NOTE: Move this block 2 positions clockwise (around the path) only if //
				//       it's x and y position are equal to one of the x and y positions //
				//       of a block in the path.                                         //
				//***********************************************************************//
				if (block.getXPos() == path[pathNum].getXPos() &&
				    block.getYPos() == path[pathNum].getYPos())
				{
					// this block will now be moved two positions clockwise (around the path)
					int nextPathNum = (pathNum + 2) % path.length;

					// move this block to the x and y position
					block.setXPos( path[nextPathNum].getXPos());
					block.setYPos( path[nextPathNum].getYPos());

					break;
				}
			}
		}

		// add this Shape back to the Board
		addToBoard(update);
	}
}