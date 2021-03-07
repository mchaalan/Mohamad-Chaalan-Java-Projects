package Generators;
import java.lang.*;
import Generators.MazeGenerator;
import Maze.VirtualMaze;
import Maze.VirtualMaze.NLocation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PrimsGenerator extends MazeGenerator
{
	private List<Maze.VirtualMaze.Cell> frontier = new ArrayList<Maze.VirtualMaze.Cell>();

	public PrimsGenerator(int height, int width)
	{
		super(height, width);
	}

	@Override public void generateMaze()
	{
		// Chose a cell at random
		Random r = new Random();
		int rand_row = r.nextInt(Maze.getHeight());
		int rand_col = r.nextInt(Maze.getWidth());
		
		// Mark initial random cell as in the maze.
		Maze.VMaze.get(rand_row).get(rand_col).visited = true;

		// Add initial adjacent cells to frontier that are not in the maze
		NLocation none = NLocation.NONE;
		for(int i = 0; i < 4; ++i)
		{   
			if(Maze.VMaze.get(rand_row).get(rand_col).neighbors[i] == NLocation.NORTH 
				&& Maze.VMaze.get(rand_row-1).get(rand_col).visited == false)
			{
				frontier.add(Maze.VMaze.get(rand_row-1).get(rand_col));
			}
			if(Maze.VMaze.get(rand_row).get(rand_col).neighbors[i] == NLocation.EAST &&  
				Maze.VMaze.get(rand_row).get(rand_col+1).visited == false)
			{
				frontier.add(Maze.VMaze.get(rand_row).get(rand_col+1));
			}
			if(Maze.VMaze.get(rand_row).get(rand_col).neighbors[i] == NLocation.SOUTH 
				&& Maze.VMaze.get(rand_row+1).get(rand_col).visited == false)
			{
				frontier.add(Maze.VMaze.get(rand_row+1).get(rand_col));
			}
			if(Maze.VMaze.get(rand_row).get(rand_col).neighbors[i] == NLocation.WEST &&  
				Maze.VMaze.get(rand_row).get(rand_col-1).visited == false)
			{
				frontier.add(Maze.VMaze.get(rand_row).get(rand_col-1));
			}
		}

		while(frontier.size() > 0)
		{
			// Chose a random cell in the frontier and add its neighbors to the frontier.
			// that are not already in the frontier and not in the maze.
			int rand_cell = r.nextInt(frontier.size());
			Maze.VirtualMaze.Cell C = frontier.get(rand_cell);
			frontier.remove(rand_cell);

			if(Maze.VMaze.get(C.row).get(C.col).neighbors[0] == NLocation.NORTH 
				&& Maze.VMaze.get(C.row-1).get(C.col).visited == false)
			{
				boolean infront = false;
				for(int i = 0; i < frontier.size(); ++i)
				{
					if(frontier.get(i).row == C.row-1 && frontier.get(i).col == C.col)
					{
						infront = true;
					}
				}
				if(!infront)
				{
					frontier.add(Maze.VMaze.get(C.row-1).get(C.col));
				}
			}
			if(Maze.VMaze.get(C.row).get(C.col).neighbors[1] == NLocation.EAST &&  
				Maze.VMaze.get(C.row).get(C.col+1).visited == false)
			{
				boolean infront = false;
				for(int i = 0; i < frontier.size(); ++i)
				{
					if(frontier.get(i).row == C.row && frontier.get(i).col == C.col+1)
					{
						infront = true;
					}
				}
				if(!infront)
				{
					frontier.add(Maze.VMaze.get(C.row).get(C.col+1));
				}
			}
			if(Maze.VMaze.get(C.row).get(C.col).neighbors[2] == NLocation.SOUTH 
				&& Maze.VMaze.get(C.row+1).get(C.col).visited == false)
			{
				boolean infront = false;
				for(int i = 0; i < frontier.size(); ++i)
				{
					if(frontier.get(i).row == C.row+1 && frontier.get(i).col == C.col)
					{
						infront = true;
					}
				}
				if(!infront)
				{
					frontier.add(Maze.VMaze.get(C.row+1).get(C.col));
				}
			}
			if(Maze.VMaze.get(C.row).get(C.col).neighbors[3] == NLocation.WEST &&  
				Maze.VMaze.get(C.row).get(C.col-1).visited == false)
			{
				boolean infront = false;
				for(int i = 0; i < frontier.size(); ++i)
				{
					if(frontier.get(i).row == C.row && frontier.get(i).col == C.col-1)
					{
						infront = true;
					}
				}
				if(!infront)
				{
					frontier.add(Maze.VMaze.get(C.row).get(C.col-1));
				}
			}

			// Chose a random cell next to the chosen cell that is in the maze and 
			// remove the wall between the cell in the frontier and the cell in the
			// maze.
                        List<Integer> n = new ArrayList<Integer>();
			if(Maze.VMaze.get(C.row).get(C.col).neighbors[0] == NLocation.NORTH 
				&& Maze.VMaze.get(C.row-1).get(C.col).visited == true)
			{
				n.add(0);
			}
			if(Maze.VMaze.get(C.row).get(C.col).neighbors[1] == NLocation.EAST &&  
				Maze.VMaze.get(C.row).get(C.col+1).visited == true)
			{
				n.add(1);
			}
			if(Maze.VMaze.get(C.row).get(C.col).neighbors[2] == NLocation.SOUTH 
				&& Maze.VMaze.get(C.row+1).get(C.col).visited == true)
			{
				n.add(2);
			}
			if(Maze.VMaze.get(C.row).get(C.col).neighbors[3] == NLocation.WEST &&  
				Maze.VMaze.get(C.row).get(C.col-1).visited == true)
			{
				n.add(3);
			}

			int index = r.nextInt(n.size());
                        int neighbor = n.get(index);
                        n.remove(index);

                        if(neighbor == 0)
                        {
                                // remove north wall
                                Maze.VMaze.get(C.row).get(C.col).walls[0] = false;
                                Maze.VMaze.get(C.row-1).get(C.col).walls[2] = false;
                        }
                        else if(neighbor == 1)
                        {
                                // remove east wall
                                Maze.VMaze.get(C.row).get(C.col).walls[1] = false;
                                Maze.VMaze.get(C.row).get(C.col+1).walls[3] = false;
                        }
                        else if(neighbor == 2)
                        {
                                // remove south wall
                                Maze.VMaze.get(C.row).get(C.col).walls[2] = false;
                                Maze.VMaze.get(C.row+1).get(C.col).walls[0] = false;
                        }
                        else if(neighbor == 3)
                        {
                                // remove west wall
                                Maze.VMaze.get(C.row).get(C.col).walls[3] = false;
                                Maze.VMaze.get(C.row).get(C.col-1).walls[1] = false;
                        }
			Maze.VMaze.get(C.row).get(C.col).visited = true;
		}
	}
}
