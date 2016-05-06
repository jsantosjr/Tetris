// Author:   Jose Santos
// FileName: FourSpaceShape

import java.awt.Color;
import java.util.*;

//******************************************************************************************************
/** This class constructs a FourSpaceShape object that is able to rotate around a 4 x 4 space. Note that
  * only the IShape requires such space.
  */
//******************************************************************************************************
public abstract class FourSpaceShape extends Shape
{
	//**************************************************************************************************
	/** Constructs a FourSpaceShape object that is set relative to the passed in Board.
	  * @param b the Board to which this Shape is relative
	  * @param color the color of this Shape
	  */
	//**************************************************************************************************
	public FourSpaceShape(Board b, Color color)
	{
		// call the base constructor in order to initialize its data members
		super(b, color);
	}

	//**************************************************************************************************
	/** Returns true if all the Blocks of this Shape are on the same row.
	  * @return true if all the Blocks of this Shape are on the same row
	  */
	//**************************************************************************************************
	protected boolean blocksOnSameRow()
	{
		// we'll initially assume that the Blocks ARE on the same row
		boolean retVal = true;

		// get the List of Blocks that compose this Shape
		List blocks = getBlocks();

		// get the y position of the focal Block
		int yPosOfFBlock = getFocalBlock().getYPos();

		for (int i = 0; i < blocks.size(); i++)
		{
			// get the next Block of this Shape
			Block block = (Block)blocks.get(i);

			//************************************************************************************//
			// NOTE: If the y position of the next Block is not equal to that of the focal Block, //
			//       then we can say that the Blocks of this Shape are not all on the same row.   //
			//************************************************************************************//
			if (block.getYPos() != yPosOfFBlock)
			{
				retVal = false;
				break;
			}

		}

		return retVal;
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

		//*****************************************************************************************//
		// NOTE: Rotate this Shape once in order to return it back to it's original position. Note //
		//       that after rotating it, that is, when it's back to its original position, we'll   //
		//       want to add it back to the Board.                                                 //
		//*****************************************************************************************//
		rotate(false, true);

		return retVal;
	}

	//**************************************************************************************************
	/** Initializes the coordinates of this FourSpaceShape. Note that this method will be overridden
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
		// remove this Shape for the Board
		removeFromBoard(remove);

		// get the List of Blocks that compose this Shape
		List blocks = getBlocks();

        // get the focal Block
        Block focalBlock = getFocalBlock();

		// will hold the distances of each Block relative to the focal Block
		int posFromFocus[][] = null;

		//***********************************************************************************//
		// NOTE: If the all the Blocks of this Shape are on the same row, then we'll want to //
		//       rotate the Shape so that it's positioned vertically. After rotating it, the //
		//       Shape should look as follows:												 //
		// 																					 //
		//                       *    														 //
		//                       #                                                           //
		//                       * 															 //
		//                       *                                                           //
		//                        															 //
		//       Note that the # indicates the focal Block while the *'s indicate all the    //
		//       other Blocks. 																 //
		//***********************************************************************************//
		if (blocksOnSameRow())
		{
			//*************************************************************************************//
			// NOTE: This holds the distances of each Block relative to the focal Block. Note that //
			//       the first element of each array holds the relative x position while the       //
			//       second element of each array holds the relative y position.                   //
			//    																				   //
			//                1st element = relative x position                                    //
			//                2nd element = relative y position                                    //
			//																					   //
			//       Note that the first array holds the relative x and y positions of the focal   //
			//       Block. Both values are equal to 0 since the focal Block stays put when this   //
			//       Shape is rotated. 															   //
			//*************************************************************************************//
			int relativePos[][] = {{0, 0},{0, -1},{0, 1},{0, 2}};

			// have the posFromFocus 2D array hold a reference to the 2D array we just created
			posFromFocus = relativePos;
		}

		//*********************************************************************************//
		// NOTE: Rotate the Shape so that it's positioned horizontally. After rotating it, //
		//       the Shape should look as follows:										   //
		//         																		   //
		//                             *#*** 							                   //
		//  																			   //
		//       Note that the # indicates the focal Block while the *'s indicate all the  //
		//       other Blocks. 															   //
		//*********************************************************************************//
		else
		{
			//*********************************************************************//
			// NOTE: Holds the distance of each Block relative to the focal Block. //
			// 																	   //
			//                 1st element = relative x position                   //
			//                 2nd element = relative y position                   //
			//*********************************************************************//
			int relativePos[][] = {{0, 0},{-1, 0},{1, 0},{2, 0}};

			// have the posFromFocus 2D array hold a reference to the 2D array we just created
			posFromFocus = relativePos;
		}

		// set the new position of the Blocks
		for (int i = 0; i < posFromFocus.length; i++)
		{
			// get the next block
			Block block = (Block)blocks.get(i);

			// set the new x position of the Block
			block.setXPos(focalBlock.getXPos() + posFromFocus[i][0]);

			// set the new y position of the Block
			block.setYPos(focalBlock.getYPos() + posFromFocus[i][1]);
		}

		// add this Shape back to the Board
		addToBoard(update);
	}
}