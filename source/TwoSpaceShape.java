// Author:   Jose Santos
// FileName: TwoSpaceShape

import java.awt.Color;
import java.util.*;

//******************************************************************************************************
/** This class constructs a TwoSpaceShape object that is able to rotate around a 2 x 2 space. Such
  * shapes include the OShape. Note that this class inherits from the Shape class.
  */
//******************************************************************************************************
public abstract class TwoSpaceShape extends Shape
{
	//**************************************************************************************************
	/** Constructs a TwoSpaceShape object that is set relative to the passed in Board.
	  * @param b the Board to which this Shape is relative
	  * @param color the color of this Shape
	  */
	//**************************************************************************************************
	public TwoSpaceShape(Board b, Color color)
	{
		// call the base constructor in order to initialize its data members
		super(b, color);
	}

	//**************************************************************************************************
	/** Returns true if this Shape can be rotated. Note that since a TwoSpaceShape is never really
	  * rotated, it will always be rotatable. Hence, this method will always return true.
	  * @return true if this Shape can be rotated
	  */
	//**************************************************************************************************
	public boolean canRotate()
	{
		return true;
	}

	//**************************************************************************************************
	/** Initializes the coordinates of this TwoSpaceShape. Note that this method will be overridden
	  * in derived classes.
	  */
	//**************************************************************************************************
	protected abstract void initCoordinates();

	//**************************************************************************************************
	/** Rotates this Shape clockwise. A rotation is never really made since one would never notice
	  * whether or not a TwoSpaceShape were rotated.
	  * @param remove true if this Shape should be remove from the Board prior to rotating it
	  * @param update true if this Shape should be updated on the Board after it has been rotated
	  */
	//**************************************************************************************************
	public void rotate(boolean remove, boolean update)
	{
	}
}