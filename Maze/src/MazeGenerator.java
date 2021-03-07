package Generators;
import Maze.VirtualMaze;

abstract public class MazeGenerator
{
	public VirtualMaze Maze;

	abstract public void generateMaze();

	public MazeGenerator(int height, int width)
        {
                Maze = new VirtualMaze(height, width);
        }
}
