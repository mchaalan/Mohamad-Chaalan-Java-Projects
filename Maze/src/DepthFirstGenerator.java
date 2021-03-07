package Generators;
import Generators.MazeGenerator;
import Maze.VirtualMaze;
import Maze.VirtualMaze.NLocation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DepthFirstGenerator extends MazeGenerator
{
	public DepthFirstGenerator(int height, int width)
	{
		super(height,width);
	}
	
	@Override public void generateMaze()
	{		
		DepthFirstRecursive(0, 0);
	}

	private void DepthFirstRecursive(int row, int col)
	{
		Maze.VMaze.get(row).get(col).visited = true;

		Random r = new Random();

		if(Maze.allCellsVisited())
		{
			return;
		}
		while(true)
		{
			NLocation none = NLocation.NONE;
			List<Integer> n = new ArrayList<Integer>();
			if(Maze.VMaze.get(row).get(col).neighbors[0] == NLocation.NORTH 
				&& Maze.VMaze.get(row-1).get(col).visited == false)
			{
				n.add(0);
			}
			if(Maze.VMaze.get(row).get(col).neighbors[1] == NLocation.EAST && 
				Maze.VMaze.get(row).get(col+1).visited == false)
			{
				n.add(1);
			}
			if(Maze.VMaze.get(row).get(col).neighbors[2] == NLocation.SOUTH 
				&& Maze.VMaze.get(row+1).get(col).visited == false)
			{
				n.add(2);
			}
			if(Maze.VMaze.get(row).get(col).neighbors[3] == NLocation.WEST && 
				Maze.VMaze.get(row).get(col-1).visited == false)
			{
				n.add(3);
			}

			if(n.size() == 0)
			{
				break;
			}

			int index = r.nextInt(n.size()); 
			int neighbor = n.get(index);
			n.remove(index);

			if(neighbor == 0)
			{
				// remove north wall
				Maze.VMaze.get(row).get(col).walls[0] = false;
				Maze.VMaze.get(row-1).get(col).walls[2] = false;	
				DepthFirstRecursive(row-1, col);
			}
			else if(neighbor == 1)
			{
				// remove east wall
				Maze.VMaze.get(row).get(col).walls[1] = false;
				Maze.VMaze.get(row).get(col+1).walls[3] = false;
				DepthFirstRecursive(row, col+1);
			}
			else if(neighbor == 2)
			{
				// remove south wall
				Maze.VMaze.get(row).get(col).walls[2] = false;
				Maze.VMaze.get(row+1).get(col).walls[0] = false;
				DepthFirstRecursive(row+1, col);
			}
			else if(neighbor == 3)
			{
				// remove west wall
				Maze.VMaze.get(row).get(col).walls[3] = false;
				Maze.VMaze.get(row).get(col-1).walls[1] = false;
				DepthFirstRecursive(row, col-1);
			}
		}		
	}
}
