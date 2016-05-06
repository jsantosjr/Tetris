// Author:   Jose Santos
// FileName: Block.java

import java.awt.Color;

//******************************************************************************************************
/** This class creates a Block object located at a specific x and y position and having a specific
  * Color.
  */
//******************************************************************************************************
public class Block
{
	// data members
	private int   xPos;
	private int   yPos;
	private Color color;

	//**************************************************************************************************
	/** Constructs a Block object at x position 0 and y position 0. Note that the color is initialized to
	  * to be black.
	  */
	//**************************************************************************************************
	public Block()
	{
		// initialize the block
		this(0, 0, Color.BLACK);
	}

	//**************************************************************************************************
	/** Constructs a copy of the passed in Block.
	  */
	//**************************************************************************************************
	public Block(Block block)
	{
		// initialize the block
		this(block.getXPos(), block.getYPos(), block.getColor());
	}

	//**************************************************************************************************
	/** Constructs a Block object at the indicated x and y position. Note that the default color is
	  * black.
	  * @param xPos the x position on which the Block will lie
	  * @param yPos the y position on which the Block will lie
	  */
	//**************************************************************************************************
	public Block(int xPos, int yPos)
	{
		// initialize the block
		this(xPos, yPos, Color.BLACK);
	}

	//**************************************************************************************************
	/** Constructs a Block object at the indicated x and y position. The Block's color will be set to
	  * the one indicated.
	  * @param xPos the x position on which the Block will lie
	  * @param yPos the y position on which the Block will lie
	  * @param color the color of the Block
	  */
	//**************************************************************************************************
	public Block(int xPos, int yPos, Color color)
	{
		// set the x and y position of the Block
		this.xPos = xPos;
		this.yPos = yPos;

		// set the color of the Block
		this.color = color;
	}

	//**************************************************************************************************
	/** Returns the Color of this Block.
	  * @return the color of this Block
	  */
	//**************************************************************************************************
	public Color getColor()
	{
		// get the Color of this Block
		return color;
	}

	//**************************************************************************************************
	/** Returns the x position of this Block.
	  * @return the x position of this Block
	  */
	//**************************************************************************************************
	public int getXPos()
	{
		// get the x position
		return xPos;
	}

	//**************************************************************************************************
	/** Returns the y position of this Block.
	  * @return the y position of this Block
	  */
	//**************************************************************************************************
	public int getYPos()
	{
		// get the y position
		return yPos;
	}

	//**************************************************************************************************
	/** Sets the color of this Block.
	  * @param color the color that this Block will be set to
	  */
	//**************************************************************************************************
	public void setColor(Color color)
	{
		// set the Color
		this.color = color;
	}

	//**************************************************************************************************
	/** Sets the x position of this Block.
	  * @param xPos the x position to set for this Block
	  */
	//**************************************************************************************************
	public void setXPos(int xPos)
	{
		// set the x position
		this.xPos = xPos;
	}

	//**************************************************************************************************
	/** Sets the y position of this Block.
	  * @param yPos the y position to set for this Block
	  */
	//**************************************************************************************************
	public void setYPos(int yPos)
	{
		// set the y position
		this.yPos = yPos;
	}
}