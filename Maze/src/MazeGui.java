package Maze;
import Generators.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class MazeGui
{
	private JFrame frame;

	private JPanel GeneratorPanel;
	private JPanel setPanel;
	private JPanel containerPanel;

	private JLabel heightSliderLabel;
	private JLabel widthSliderLabel;
	private JLabel generatorLabel;

	private JComboBox<String> generatorList;
	private JComboBox<String> solverList;
	private JComboBox<String> HeightList;
	private JComboBox<String> WidthList;

	private JButton resetMaze;
	private JButton GenerateMaze;

	private int MazeDim;
	private int height;
	private int width;
	private int MazePos1;
	private int MazePos2;

	private VirtualMaze finalMaze;

	public MazeGui()
	{
		// Initializing Maze Position 1 & 2
		MazePos1 = 100;
		MazePos2 = 100;

		// Initializing Maze width and height
		MazeDim = 600;
		
		// Set default height and width
		height = 10;
		width = 10;

		// Creating Maze GUI Frame
		frame = new JFrame();

		/////////////////////////////////////////
		
		// Creating first panel
		setPanel = new JPanel(new GridLayout(8,1));	

		/////////////////////////////////////////

		// Height Slider Label
		heightSliderLabel = new JLabel("Maze Height");
		
		// Height Slider 
		String[] HeightValues = {"5", "10", "20", "30", "40", "50", "60","100"};
		HeightList = new JComboBox<String>(HeightValues);

		/////////////////////////////////////////

		// Width Slider Label
		widthSliderLabel = new JLabel("Maze Width");

		// Width Slider Label
		String[] widthValues = {"5", "10", "20", "30", "40", "50", "60", "100"};
		WidthList = new JComboBox<String>(widthValues);

		/////////////////////////////////////////
		
		// Reset Maze Button	
		resetMaze = new JButton("Set Maze"); 

		/////////////////////////////////////////
		
		// Generator Label
		GeneratorPanel = new JPanel(new GridLayout(8,1));
		generatorLabel = new JLabel("Generator");
		
		// Generator Selection
		String[] generatorAlgos = {"Depth-First Algorithm", "Prims Algorithm"}; 
		generatorList = new JComboBox<String>(generatorAlgos);

		// Generate Maze Button
		GenerateMaze = new JButton("Generate Maze");

		/////////////////////////////////////////

		// Creating container panel
		containerPanel = new JPanel(new GridLayout(3,1));
	}

	public void createGui()
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,900);
		
		setPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		setPanel.setBackground(Color.LIGHT_GRAY);

		setPanel.add(heightSliderLabel);
		setPanel.add(HeightList);
		setPanel.add(widthSliderLabel);
		setPanel.add(WidthList);

		setPanel.add(resetMaze);
		//setPanel.setBackground(Color.black);

		resetMaze.addActionListener(new resetButton());

		GeneratorPanel.add(generatorLabel);
		GeneratorPanel.add(generatorList);
		GeneratorPanel.add(GenerateMaze);
		GenerateMaze.addActionListener(new GeneratorButton());
		
		// Create Container Panel that holds multiple panel
		containerPanel.setBorder(BorderFactory.createLineBorder(Color.black));	
		containerPanel.setBackground(Color.LIGHT_GRAY);
		containerPanel.add(setPanel);
		containerPanel.add(GeneratorPanel);

		// Create initial Maze	
		ResetMazePanel maze = new ResetMazePanel();
		frame.getContentPane().add(BorderLayout.CENTER, maze);	
		frame.setVisible(true);

		// Creating Default Maze
		frame.getContentPane().add(BorderLayout.EAST, containerPanel);	
		frame.setVisible(true);
	}

	class GeneratorButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			GenerateMazePanel maze = new GenerateMazePanel();
			frame.getContentPane().add(BorderLayout.CENTER, maze);	
			frame.setVisible(true);
		}
	}

	class resetButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			ResetMazePanel maze = new ResetMazePanel();
			frame.getContentPane().add(BorderLayout.CENTER, maze);	
			frame.setVisible(true);
		}
	}


	class ResetMazePanel extends JPanel 
	{
	    public void paintComponent(Graphics g) 
	    {
		    int thickness = 2;
		    Graphics2D g2 = (Graphics2D) g;
		    super.paintComponent(g);
		    g2.setStroke(new BasicStroke(thickness));
		    g2.setColor(Color.black);
		    g2.drawRect(MazePos1, MazePos2, MazeDim, MazeDim);

		    width = Integer.parseInt(WidthList.getSelectedItem().toString());
		    height = Integer.parseInt(HeightList.getSelectedItem().toString());

		    int inc1 = MazeDim / width;
		    int inc2 = MazeDim / height;
			
		    for(int j = MazePos2; j <= MazeDim + MazePos2 - inc2; j += inc2)
		    {
			    for(int i = MazePos1; i <= MazeDim + MazePos1 - inc1; i += inc1)
			    {
				   g2.drawLine(i, j + inc2, i + inc1, j + inc2); 
			    }
		    }
		    for(int j = MazePos1; j <= MazeDim + MazePos1 - inc1; j += inc1)
		    {
			    for(int i = MazePos2; i <= MazeDim + MazePos2 - inc2; i += inc2)
			    {
				    g2.drawLine(j + inc1, i,j + inc1, i + inc2); 
			    } 
		    }
	    }
	}

	class GenerateMazePanel extends JPanel
	{
		public void paintComponent(Graphics g)
		{
			int thickness = 2;
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g);
			g2.setStroke(new BasicStroke(thickness));
			g2.setColor(Color.black);
			g2.drawRect(MazePos1, MazePos2, MazeDim, MazeDim);

			if(generatorList.getSelectedItem().toString() == "Depth-First Algorithm")
			{
				DepthFirstGenerator DF = new DepthFirstGenerator(height, width);
				DF.generateMaze();
				finalMaze = DF.Maze;
			}
			else if(generatorList.getSelectedItem().toString() == "Prims Algorithm")
			{
				PrimsGenerator Prim = new PrimsGenerator(height, width);
				Prim.generateMaze();
				finalMaze = Prim.Maze;
			}

			for(int i = 0; i < height; ++i)
			{
				for(int j = 0; j < width; ++j)
				{ 
					// North Wall
					if(finalMaze.VMaze.get(i).get(j).walls[0])
					{
						drawTopWall(i,j, g2);
					}
					// East Wall
					if(finalMaze.VMaze.get(i).get(j).walls[1])
					{
						drawSideWall(i,j+1,g2);
					}
					// South Wall
					if(finalMaze.VMaze.get(i).get(j).walls[2])
					{
						drawTopWall(i+1,j, g2);
					}
					// West Wall
					if(finalMaze.VMaze.get(i).get(j).walls[3])
					{
						drawSideWall(i,j, g2);
					}
				}
			}
		}

		private void drawTopWall(int i, int j, Graphics2D g2)
		{	
			int inc1 = MazeDim / width;
			int inc2 = MazeDim / height;
			int xStart = MazePos1 + j * inc1;
			int yStart = MazePos2 + (i) * inc2;
			g2.drawLine(xStart, yStart, xStart+inc1, yStart);
		}

		private void drawSideWall(int i, int j, Graphics2D g2)
		{
			int inc1 = MazeDim / width;
			int inc2 = MazeDim / height;
			int xStart = MazePos1 + (j) * inc1;
			int yStart = MazePos2 + i  * inc2;
			g2.drawLine(xStart, yStart, xStart, yStart + inc2);
		}
	}
}
