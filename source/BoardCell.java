// Author:   Jose Santos
// FileName: BoardCell.java

import java.awt.Color;
import javax.swing.*;

//******************************************************************************************************
/** This class creates a BoardCell object, which will eventually be placed into a Board.
  */
//******************************************************************************************************
public class BoardCell
{
	// data members
	private boolean m_empty;
	private Color   m_color;
	private int     m_xPos;
	private int     m_yPos;
	private JButton m_cell;

	//**************************************************************************************************
	/** Constructs a copy of the passed in BoardCell.
	  * @param boardCell the BoardCell to copy
	  */
	//**************************************************************************************************
	public BoardCell(BoardCell boardCell)
	{
		// initialize the BoardCell
		this(boardCell.getXPos(), boardCell.getYPos(), new Color(boardCell.getColor().getRGB()) );

		// set the empty status of this BoardCell
		m_empty = boardCell.isEmpty();

		// set the cell to be enabled or disabled, depending on the status of the passed in BoardCell
		m_cell.setEnabled(boardCell.getCellJButton().isEnabled());

		// set the cell to be visible or invisible, depending on the status of the passed in BoardCell
		m_cell.setVisible(boardCell.getCellJButton().isVisible());

		// set the cell's border paint on or off, depending on the status of the passed in BoardCell
		m_cell.setBorderPainted(boardCell.getCellJButton().isBorderPainted());
	}

	//**************************************************************************************************
	/** Constructs a BoardCell object of the specified Color. Note that it will initially be empty.
	  * @param xPos the x position on which this BoardCell will be placed
	  * @param yPos the y position on which this BoardCell will be placed
	  * @param color the Color of this BoardCell
	  */
	//**************************************************************************************************
	public BoardCell(int xPos, int yPos, Color color)
	{
		// set the x and y positions of this BoardCell
		m_xPos = xPos;
		m_yPos = yPos;

		// set this BoardCell to be empty
		m_empty = true;

		// set the Color of this BoardCell
		m_color = color;

		// create the cell (which will be represented as a JButton)
		m_cell = new JButton();

		// set the color of the cell
		m_cell.setBackground(color);

		// we want to disable the cell (this is, the JButton) since we don't want it to be pressed
		m_cell.setEnabled(false);

		// set the cell to be invisible since it's empty
		m_cell.setVisible(false);

		// disable the border paint of the cell
		m_cell.setBorderPainted(false);
	}

	//**************************************************************************************************
    /** Displays the JButton that represents this BoardCell. That is, the JButton is made visible. Note,
      * however, that it will only be set to visible if the BoardCell is non-empty.
      */
    //**************************************************************************************************
    public void display()
    {
		// make the cell visible
		m_cell.setVisible(true);
	}

	//**************************************************************************************************
	/** Returns the JButton that represents this BoardCell.
	  * @return the JButton that represents this BoardCell
	  */
	//**************************************************************************************************
	public JButton getCellJButton()
	{
		// get the JButton that represents this BoardCell
		return m_cell;
	}

	//**************************************************************************************************
	/** Returns the Color of this BoardCell.
	  * @return the Color of this BoardCell
	  */
	//**************************************************************************************************
	public Color getColor()
	{
		// get the Color
		return m_color;
	}

    //**************************************************************************************************
    /** Returns the x position of this BoardCell.
      * @return the x position of this BoardCell
      */
    //**************************************************************************************************
    public int getXPos()
    {
		// get the x position
		return m_xPos;
	}

    //**************************************************************************************************
    /** Returns the y position of this BoardCell.
      * @return the y position of this BoardCell
      */
    //**************************************************************************************************
    public int getYPos()
    {
		// get the y position
		return m_yPos;
	}

	//**************************************************************************************************
	/** Returns true if this BoardCell is empty. False is returned otherwise.
	  * @return true if this BoardCell is empty (false otherwise)
	  */
	//**************************************************************************************************
	public boolean isEmpty()
	{
		// returns true if empty
		return m_empty;
	}

	//**************************************************************************************************
	/** Sets the BoardCell to be empty if the passed in value is true. If the passed in value is false,
	  * then the BoardCell is set to be non-empty.
	  * @param e true if the BoardCell is to be set as empty (false otherwise)
	  */
	//**************************************************************************************************
	public void setEmpty(boolean e)
	{
		// set whether or not this BoardCell is empty
		m_empty = e;
	}

	//**************************************************************************************************
	/** Sets the Color of this BoardCell.
	  * @param color the Color of this BoardCell
	  */
	//**************************************************************************************************
	public void setColor(Color color)
	{
		// set the Color of this BoardCell
		m_color = color;
		m_cell.setBackground(color);
	}
}