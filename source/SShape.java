// Author:   Jose Santos
// FileName: SShape.java

import java.awt.Color;
import java.util.*;

//******************************************************************************************************
/** This class creates a SShape object that will take on the shape of a S. Note that this class inherits
  * from the ThreeSpaceShape class.
  */
//******************************************************************************************************
public class SShape extends ThreeSpaceShape
{
	//**************************************************************************************************
	/** Constructs a SShape object that is set relative to the passed in Board.
	  * @param b the Board to which this Shape is relative
	  * @param color the color of this Shape
	  */
	//**************************************************************************************************
	public SShape(Board b, Color color)
	{
		// call the base constructor so that data members are initialized
		super(b, color);

		// initialize the coordinates of this SShape
		initCoordinates();
	}

	//**************************************************************************************************
	/** Initializes the coordinates of this Shape.
	  */
	//**************************************************************************************************
	protected void initCoordinates()
	{
		// get the List of Blocks for this Shape
		List<Block> blocks = getBlocks();
		blocks.clear();

		// get the focal Block
		Block focalBlock = getFocalBlock();

		//**********************************************************************************//
		// NOTE: This holds the x and y distances from the focal Block. For example the     //
		//       the second array reads as follows: {1, 0}. This indicates that a Block     //
		//       exists 1 x positions to the right of the focal Block's x position and 0 y  //
		//       positions above the y position of the focal Block's y position. So in      //
		//       in this case, the Block is directly to the right of the focal Block.       //
		//       NOTE that the first block in the List of blocks is the focal Block itself. //
		//**********************************************************************************//
		int posFromFocus[][] = {{0, 0}, {1, 0}, {0, 1}, {-1, 1}};

		// set the focal Block as the first Block in the List of Blocks
		blocks.add(focalBlock);

		// set the rest of the Blocks
		for (int i = 1; i < posFromFocus.length; i++)
		{
			// create a Block with it's corresponding x and y positions
			Block block = new Block(focalBlock.getXPos() + posFromFocus[i][0],
			                        focalBlock.getYPos() + posFromFocus[i][1],
			                        getColor());

			// add the Block to the List of Blocks
			blocks.add(block);
		}
	}
}