// Author:   Jose Santos
// FileName: Board.java

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class Board extends JPanel
{
	// data members
	private BoardCell boardCells[][];
	private Color     color;
	private int       numOfCols;
	private int       numOfRows;
	private JLabel    titleLabel;
	private JPanel    boardPanel;
	private Shape     currentShape;
	private String    title;

	//**************************************************************************************************
	/** Creates a Board object that will have the specified dimension, along with a title. Note that the
	  * default color of the Board is set to be gray.
	  * @param rows the number of rows composing this Board
	  * @param cols the number of columns composing this Board
	  * @param title the title that is displayed above this Board
	  */
	//**************************************************************************************************
	public Board(int rows, int cols, String title)
	{
		this(rows, cols, title, new Color(205, 205, 205));
	}

	//**************************************************************************************************
	/** Creates a Board object that will have the specified dimension, along with a color and a title.
	  * @param rows the number of rows composing this Board
	  * @param cols the number of columns composing this Board
	  * @param title the title that is displayed above this Board
	  * @param color the color of this Board
	  */
	//**************************************************************************************************
	public Board(int rows, int cols, String title, Color color)
	{
		// set the number of columns
		numOfCols = cols;

		// set the number of rows
		numOfRows = rows;

		// set the color of this Board
		this.color = color;

		// set the title of this Board
		this.title = title;

		// create the Label that will contain the title
		titleLabel = new JLabel(title, SwingConstants.CENTER);

		// initially, make the label invisible
		titleLabel.setVisible(false);

		// initialize all the panels
		initPanels();

		// initialize the board cells
		initBoardCells();

		// generate the current Shape
		currentShape = generateShape();

		// add the current Shape to this Board
		currentShape.addToBoard(true);
	}

	//**************************************************************************************************
	/** Attaches the List of BoardCells to the indicated row. Note that if the indicated row is invalid,
	  * nothing is done.
	  * @param rowNum the number of the row at which to attach the List of BoardCells
	  * @param row the List of BoardCells
	  */
	//**************************************************************************************************
	public void attachRow(int rowNum, List row)   
	{
		// make sure that the indicated row is valid
		if (validYPos(rowNum))
		{
			// attach all the BoardCells in the passed in List to the indicated row of this Board
			for (int col = 0; col < getNumOfCols(); col++)
			{
				// get the next BoardCell from the passed in List
				BoardCell boardCell = (BoardCell)row.get(col);

				// set the empty status of the BoardCell in this Board
				boardCells[rowNum][col].setEmpty( boardCell.isEmpty());

				// set the color of the BoardCell in this Board
				boardCells[rowNum][col].setColor( boardCell.getColor());

				// set the border paint status of the BoardCell in this Board
				boardCells[rowNum][col].getCellJButton().setBorderPainted(boardCell.getCellJButton().isBorderPainted());
			}
		}
	}

	//**************************************************************************************************
	/** Cascades any rows of this Board that are completely filled.
	  */
	//**************************************************************************************************
	public void cascadeRows()
	{
		// will hold copies of all the non-empty rows
		List nonEmptyRows = new ArrayList();

		// temporarily remove the current shape from the Board
		currentShape.removeFromBoard(true);

		// clear any rows that are completely filled
		for (int row = getNumOfRows() - 1; row >= 0; row--)
		{
			// check to see if the next row is full
			if (rowIsFull(row))
			{
				// clear this row since it's full
				clearRow(row);
			}
		}

		// store copies of all the non-empty rows
		for (int row = getNumOfRows() - 1; row >= 0; row--)
		{
			// check to see if the next row is non-empty
			if (!rowIsEmpty(row))
			{
				// store a copy of the non-empty row
				nonEmptyRows.add( copyOfRow(row));

				// remove the actual row from the Board
				clearRow(row);
			}
		}

		// add the non-empty rows back to the Board in the order that they were stored
		for (int i = 0; i < nonEmptyRows.size(); i++)
		{
			// get the next non-empty row of BoardCells
			List nonEmptyRow = (List)nonEmptyRows.get(i);

			// add the non-empty row back to the Board
			attachRow( (getNumOfRows() - 1) - i, nonEmptyRow);
		}

		// add the current Shape back to the Board
		currentShape.addToBoard(true);

	}

	//**************************************************************************************************
	/** Clears this entire Board so that all the BoardCells are empty.
	  */
	//**************************************************************************************************
	public void clearBoard()
	{
		// clear all the rows of this Board
		for (int row = 0; row < getNumOfRows(); row++)
		{
			// clear the row
			clearRow(row);
		}
	}
	//**************************************************************************************************
	/** Clears the specified row of any non-empty BoardCells. Note that if the indicated row does not
	  * exist, nothing is done.
	  * @param rowNum the row of BoardCells to clear
	  */
	//**************************************************************************************************
	public void clearRow(int rowNum)
	{
		// make sure that the indicated row is valid
		if (validYPos(rowNum))
		{
			// set each BoardCell in this row to be empty
			for (int col = 0; col < getNumOfCols(); col++)
			{
				// get the next BoardCell in the indicated row
				BoardCell boardCell = boardCells[rowNum][col];

				// set this BoardCell to be empty
				boardCell.setEmpty(true);

				// the color of the BoardCell will now be that of the Board
				boardCell.setColor(getColor());

				// disable the border paint on the BoardCell
				boardCell.getCellJButton().setBorderPainted(false);
			}
		}
	}

	//**************************************************************************************************
	/** Returns a List containing a copy of the indicated row of BoardCells. Note that if the indicated
	  * row number is invalid, a null value is returned. Because this is a deep copy of BoardCells,
	  * the returned List will contain different BoardCell references than the ones in the actual
	  * Board.
	  * @param rowNum the row of BoardCells desired
	  * @return a List containing a copy of the indicated row of BoardCells
	  */
	//**************************************************************************************************
	public List copyOfRow(int rowNum)
	{
		// will holds the BoardCells contained in the specified row
		List rowOfBoardCells = null;

		// make sure that the indicated row is valid
		if (validYPos(rowNum))
		{
			// create the List of BoardCells
			rowOfBoardCells = new ArrayList();

			// copy all the BoardCells of the indicated row
			for (int col = 0; col < getNumOfCols(); col++)
			{
				rowOfBoardCells.add(new BoardCell(boardCells[rowNum][col]));
			}
		}

		return rowOfBoardCells;
	}

	//**************************************************************************************************
	/** Displays the board panel. That is, all the BoardCells in this Board are displayed.
	  */
	//**************************************************************************************************
	public void displayBoardPanel()
	{
		// display the title
		titleLabel.setVisible(true);

		// display the board panel
		for (int row = 0; row < boardCells.length; row++)
		{
			for (int col = 0; col < boardCells[row].length; col++)
			{
				// display this particular BoardCell
				boardCells[row][col].display();
			}
		}
	}

	//**************************************************************************************************
	/** Returns true if a full row exists in this Board. False is returned otherwise.
	  * @return true if a full row exists in this Board (false otherwise)
	  */
	//**************************************************************************************************
	public boolean fullRowExists()
	{
		// holds whether or not there exists a full row
		boolean retVal = false;

		// go through each row of the Board to see if it's full
		for (int row = 0; row < getNumOfRows(); row++)
		{
			// check to see if the next row if empty
			if (rowIsFull(row))
			{
				// this row is full
				retVal = true;
				break;
			}
		}

		return retVal;
	}

	//**************************************************************************************************
	/** Returns a randomly generated Shape.
	  * @return a randomly generated Shape
	  */
	//**************************************************************************************************
    public Shape generateShape()
    {
		// generate a random value (between 0 and 255) for the r value
		int r = (int)(Math.random() * 256);

		// generate a random value (between 0 and 255) for the b value
		int g = (int)(Math.random() * 256);

		// generate a random value (between 0 and 255) for the g value
		int b = (int)(Math.random() * 256);

		// generate a random color
		Color color = new Color(r, g, b);

		// the array from which we will randomly select a Shape
		Shape shapes[] = {new TShape(this, color), new LShape(this, color), new JShape(this, color),
		                  new ZShape(this, color), new SShape(this, color), new OShape(this, color),
		                  new IShape(this, color)};


		// from the array, randomly select a Shape and store it
		Shape shape = shapes[(int)(Math.random() * shapes.length)];

		// get the Shape
		return shape;
	}

	//**************************************************************************************************
	/** Returns a BoardCell at the indicated x and y position. Note that if either the x or
	  * y position is invalid, a null value will be returned.
	  * @param x the x position of the BoardCell to retrieve
	  * @param y the y position of the BoardCell to retrieve
	  * @return a BoardCell at the indicated x and y position
	  */
	//**************************************************************************************************
	public BoardCell getBoardCell(int x, int y)
	{
		// will hold the desired BoardCell
		BoardCell boardCell = null;

		// make sure the specified x and y values are valid
		if (validPos(x, y))
		{
			// get the indicated BoardCell
			boardCell = boardCells[y][x];
		}

		return boardCell;
	}

	//**************************************************************************************************
	/** Returns all the BoardCells of this Board as a 2D array.
	  * @return all the BoardCells of this Board
	  */
	//**************************************************************************************************
	public BoardCell[][] getBoardCells()
	{
		// get all the BoardCells of this Board
		return boardCells;
	}

	//**************************************************************************************************
	/** Returns the color of this Board.
	  * @return the color of this Board
	  */
	//**************************************************************************************************
	public Color getColor()
	{
		// get the color of this Board
		return color;
	}

	//**************************************************************************************************
	/** Returns the number of columns composing this Board.
	  * @return the number of columns composing this Board
	  */
	//**************************************************************************************************
	public int getNumOfCols()
	{
		// get the number of columns
		return numOfCols;
	}

	//**************************************************************************************************
	/** Returns the number of rows composing this Board.
	  * @return the number of rows composing this Board
	  */
	//**************************************************************************************************
	public int getNumOfRows()
	{
		// get the number of rows
		return numOfRows;
	}

	//**************************************************************************************************
	/** Returns a List containing the indicated row of BoardCells. Note that if the indicated row
	  * number is invalid, a null value is returned.
	  * @param rowNum the row of BoardCells desired
	  */
	//**************************************************************************************************
	public List getRow(int rowNum)
	{
		// will holds the BoardCells contained in the specified row
		List rowOfBoardCells = null;

		// make sure that the indicated row is valid
		if (validYPos(rowNum))
		{
			// create the List of BoardCells
			rowOfBoardCells = new ArrayList();

			// store all the BoardCells of the indicated row
			for (int col = 0; col < getNumOfCols(); col++)
			{
				rowOfBoardCells.add(boardCells[rowNum][col]);
			}
		}

		return rowOfBoardCells;
	}

	//**************************************************************************************************
	/** Returns the current Shape of this Board.
	  * @return the current Shape of this Board
	  */
	//**************************************************************************************************
	public Shape getShape()
	{
		// get the current Shape
		return currentShape;
	}

	//**************************************************************************************************
	/** Returns the title of this Board.
	  * @return the title of this Board
	  */
	//**************************************************************************************************
	public String getTitle()
	{
		// get the title
		return title;
	}

	//**************************************************************************************************
	private void initBoardCells()
	{
		//***************************************************************************************//
		// NOTE: Create arrays in the 2D array. Note that the number of rows in the 2D array are //
		//       determined by the number of rows in the board.                                  //
		//***************************************************************************************//
		boardCells = new BoardCell[numOfRows][];

		//*******************************************************************************//
		// NOTE: Create a BoardCell for each area of the boardPanel. Note that since the //
		//       boardPanel has a rowNum x colNum grid layout, these BoardCells will act //
		//       as grid cells. That is, the appropriate ones will light up when a shape //
		//       happens to be on the board.                                             //
		//*******************************************************************************//
		for (int row = 0; row < numOfRows; row++)
		{
			// create columns for the next array in the 2D array of BoardCells
			boardCells[row] = new BoardCell[numOfCols];

			for (int col = 0; col < numOfCols; col++)
			{
				// create a BoardCell for this particular area of the boardPanel
				boardCells[row][col] = new BoardCell(col, row, getColor());

				// add the BoardCell to the boardPanel
				boardPanel.add(boardCells[row][col].getCellJButton());
			}
		}
	}

	//**************************************************************************************************
	private void initPanels()
	{
		// set the layout of this Board to a BorderLayout
		setLayout(new BorderLayout());

		// create the board panel
		boardPanel = new JPanel(new GridLayout(numOfRows, numOfCols));

		// add the title of the board to the NORTH of this Board
		add(titleLabel, BorderLayout.NORTH);

		// add the board panel to the CENTER of this Board
		add(boardPanel, BorderLayout.CENTER);
	}

	//**************************************************************************************************
	/** Moves the curent shape to the direction indicated. Note that the Shape can only be moved if
	  * no obstacles are obtructing it. An obstacle can include a boundary or any Block from the Board
	  * that is currently filled.
	  * @param direction the direction in which to move the current Shape
	  */
	//**************************************************************************************************
	public void moveShape(String direction)
	{
		if (direction.equals("Left"))
		{
			// move the current shape to the left
			currentShape.moveLeft();
		}
		else if (direction.equals("Right"))
		{
			// move the current shape to the right
			currentShape.moveRight();
		}
		else if (direction.equals("Down"))
		{
			// move the current shape down
			currentShape.moveDown();
		}
		else if (direction.equals("Up"))
		{
			// make sure the current shape can be rotated
			if (currentShape.canRotate())
			{
				// rotate the current shape
				currentShape.rotate(true, true);
			}
		}
	}

	//**************************************************************************************************
	/** Returns true if the indicated row is completely empty. False is returned otherwise.
	  * @param rowNum the row to check
	  * @return true if the indicated row is completely empty (false otherwise)
	  */
	//**************************************************************************************************
	public boolean rowIsEmpty(int rowNum)
	{
		// holds whether or not the indicated row is empty
		boolean retVal = true;

		// make sure that the indicated row is valid
		if (validYPos(rowNum))
		{
			// check each column of the indicated row to see whether or not it's empty
			for (int col = 0; col < getNumOfCols(); col++)
			{
				if (!boardCells[rowNum][col].isEmpty())
				{
					// this BoardCell is not empty and therefore, the indicated row is not fully empty
					retVal = false;
					break;
				}
			}
		}

		return retVal;
	}

	//**************************************************************************************************
	/** Returns true if the indicated row is completely full. False is returned otherwise.
	  * @param rowNum the row to check
	  * @return true if the indicated row is completely full (false otherwise)
	  */
	//**************************************************************************************************
	public boolean rowIsFull(int rowNum)
	{
		// holds whether or not the indicated row is full
		boolean retVal = true;

		// make sure that the indicated row is valid
		if (validYPos(rowNum))
		{
			// check each column of the indicated row to see whether or not it's full
			for (int col = 0; col < getNumOfCols(); col++)
			{
				if (boardCells[rowNum][col].isEmpty())
				{
					// this BoardCell is empty and therefore, the indicated row is not completely full
					retVal = false;
					break;
				}
			}
		}

		return retVal;
	}
	//**************************************************************************************************
	/** Sets the current Shape of this Board.
	  * @param shape the current Shape of this Board
	  */
	//**************************************************************************************************
	public void setShape(Shape shape)
	{
		// remove the old shape from its Board
		shape.removeFromBoard(true);

		// the associated Board of the passed in Shape will be this Board
		shape.setBoard(this);

		// set the current Shape
		currentShape = shape;

		// set the focal Block of the current Shape to be centered
		currentShape.getFocalBlock().setXPos(getNumOfCols() / 2);
		currentShape.getFocalBlock().setYPos(0);

		// readjust the Blocks of the Shape according to where the focal block is positioned
		currentShape.initCoordinates();

		// add the current Shape to this Board
		currentShape.addToBoard(true);
	}

	//**************************************************************************************************
	/** Sets the title of this Board.
	  * @param title the title to set
	  */
	//**************************************************************************************************
	public void setTitle(String title)
	{
		// set the title of this Board
		this.title = title;

		// place the new title into the title Label
		titleLabel.setText(title);
	}

	//**************************************************************************************************
	/** Returns true if the passed in Shape happens to lie on top of the specified BoardCell. False is
	  * returned otherwise.
	  * @param shape the shape to be checked
	  * @param boardCell the BoardCell to be checked
	  * @return true if the passed in Shape lies on top of the specified BoardCell (false otherwise)
	  */
	//**************************************************************************************************
	public boolean shapeOnBoardCell(Shape shape, BoardCell boardCell)
	{
		// we'll assume the Shape doesn't lie on top of the BoardCell
		boolean retVal = false;

		// get the List of Blocks that compose the Shape
		List blocks = shape.getBlocks();

		for (int i = 0; i < blocks.size(); i++)
		{
			// get the next Block of the Shape
			Block block = (Block)blocks.get(i);

			// check to see the BoardCell has the same coordinates as the Block
			if (block.getXPos() == boardCell.getXPos() &&
			    block.getYPos() == boardCell.getYPos())
			{
				// this Shape lies on the BoardCell
				retVal = true;
				break;
			}
		}

		return retVal;
	}

	//**************************************************************************************************
	/** Returns true if both the x and y values are valid.
	  * @param x the x position to check
	  * @param y the y position to check
	  * @return true if both the x and y values are valid
	  */
	//**************************************************************************************************
	public boolean validPos(int x, int y)
	{
		// holds whether or not the passed position is valid
		boolean retVal = false;

		// check for the validity of the passed in position
		if (validXPos(x) && validYPos(y))
		{
			// the passed in position value IS valid
			retVal = true;
		}

		return retVal;
	}

	//**************************************************************************************************
	/** Returns true if the passed in x value is valid.
	  * @param x the x position to check
	  * @return true if the passed in x value is valid
	  */
	//**************************************************************************************************
	public boolean validXPos(int x)
	{
		// holds whether or not the passed in x value is valid
		boolean retVal = false;

		// check for the validity of the passed in x value
		if (x >= 0 && x < getNumOfCols())
		{
			// the passed in x value IS valid
			retVal = true;
		}

		return retVal;
	}

	//**************************************************************************************************
	/** Returns true if the passed in y value is valid.
	  * @param y the y position to check
	  * @return true if the passed in y value is valid
	  */
	//**************************************************************************************************
	public boolean validYPos(int y)
	{
		// holds whether or not the passed in y value is valid
		boolean retVal = false;

		// check for the validity of the passed in y value
		if (y >= 0 && y < getNumOfRows())
		{
			// the passed in y value IS valid
			retVal = true;
		}

		return retVal;
	}

}