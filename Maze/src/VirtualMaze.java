package Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VirtualMaze
{
	private int height;
	private int width;

	public ArrayList<ArrayList<Cell>> VMaze = new ArrayList<ArrayList<Cell>>();

	public VirtualMaze()
	{
		height = 10;
		width = 10;
		this.makeDefaultMaze();
	}

	public VirtualMaze(int mazeHeight, int mazeWidth)
	{
		this.height = mazeHeight;
		this.width = mazeWidth;
		this.makeDefaultMaze();
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

	private void makeDefaultMaze()
	{
		for(int i = 0; i < height; ++i)
		{
			VMaze.add(new ArrayList<Cell>());
		}
		for(int i = 0; i < height; ++i)
		{
			for(int j = 0; j < width; ++j)
			{
				Cell c = new Cell(i,j);
				VMaze.get(i).add(c);
			}
		}
	}

	public boolean allCellsVisited()
	{
		for(int i = 0; i < height; ++i)
		{
			for(int j = 0; j < width; ++j)
			{
				if(VMaze.get(i).get(j).visited == false)
				{
					return false;
				}
			}
		}
		return true;
	}

	public enum NLocation
	{
		NORTH,
		EAST,
		SOUTH,
		WEST,
		NONE;
	}

	public class Cell
	{
		public int row;
		public int col;
		public boolean visited;
		public boolean walls[] = {true, true, true, true};
		public NLocation neighbors[] = {
			NLocation.NONE, NLocation.NONE, NLocation.NONE, NLocation.NONE};

		public Cell(int x, int y)
		{
			this.row = x;
			this.col = y;
			this.visited = false;
			this.findNeighbors();
		}

		private void findNeighbors()
		{
			if(this.row > 0)
			{
				neighbors[0] = NLocation.NORTH;
			}
			if(this.col < width - 1)
			{
				neighbors[1] = NLocation.EAST;
			}
			if(this.row < height - 1)
			{
				neighbors[2] = NLocation.SOUTH;
			}
			if(this.col > 0)
			{
				neighbors[3] = NLocation.WEST;
			}
		}
	}
}
