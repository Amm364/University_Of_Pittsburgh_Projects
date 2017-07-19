import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;


@SuppressWarnings("serial")
public class MazeComponent extends JComponent
{
	private int mazeWidth;
	private int mazeHeight;
	private int width;
	private int height;
	private int leftMargin = 10;
	private int rightMargin = 10;
	private int topMargin = 10;
	private int bottomMargin = 10;
	private Maze maze;
	private double cellSize;
	private double halfCellSize;
	private ArrayList<Pair<Integer,Integer>> solution;
	private BasicStroke thin = new BasicStroke(2);
	private BasicStroke thick = new BasicStroke(5);
	
	public MazeComponent(Maze aMaze, ArrayList<Pair<Integer,Integer>> aSolution)
	{
		maze = aMaze;
		mazeWidth = maze.getWidth();
		mazeHeight = maze.getHeight();
		solution = aSolution;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		width = this.getWidth();
		height = this.getHeight();
		
		g2.setColor(Color.WHITE);
		Rectangle2D.Double rect = new Rectangle2D.Double(0,0,width,height);
		g2.fill(rect);
		
		g2.setColor(Color.BLACK);
		g2.setStroke(thin);

		Line2D.Double line = new Line2D.Double(0,0,0,0);
		
		double hCellWidth = (double) (width - (leftMargin + rightMargin)) / mazeWidth;
		double vCellHeight = (double) (height - (topMargin + bottomMargin)) / mazeHeight;
		
		if(hCellWidth > vCellHeight)
		{
			cellSize = vCellHeight;
		}
		else
		{
			cellSize = hCellWidth;
		}
		
		halfCellSize = cellSize / 2;
		
		for(int i = 0; i < mazeWidth; i++)
		{
			for(int j = 0; j < mazeHeight; j++)
			{
				double sx, ex, sy, ey;
				
				// draw north wall
				
				if(maze.isNorthWall(j, i))
				{
					sx = leftMargin + (i * cellSize);
					ex = leftMargin + ((i + 1) * cellSize);
					sy = topMargin + (j * cellSize);
					ey = sy;
					
					line.setLine(sx,sy,ex,ey);
					g2.draw(line);
				}
				
				// draw east wall
				
				if(maze.isEastWall(j, i))
				{
					sx = leftMargin + ((i + 1) * cellSize);
					ex = sx;
					sy = topMargin + (j * cellSize);
					ey = topMargin + ((j + 1) * cellSize);
					
					line.setLine(sx,sy,ex,ey);
					g2.draw(line);
				}
				
				// draw south wall
				
				if(maze.isSouthWall(j, i))
				{
					sx = leftMargin + (i * cellSize);
					ex = leftMargin + ((i + 1) * cellSize);
					sy = topMargin + ((j + 1) * cellSize);
					ey = sy;
					
					line.setLine(sx,sy,ex,ey);
					g2.draw(line);
				}
				
				// draw west wall

				if(maze.isWestWall(j, i))
				{
					sx = leftMargin + (i * cellSize);
					ex = sx;
					sy = topMargin + (j * cellSize);
					ey = topMargin + ((j + 1) * cellSize);
					
					line.setLine(sx,sy,ex,ey);
					g2.draw(line);
				}
			}
		}

		// Draw a solution
		
		g2.setColor(Color.GREEN);
		g2.setStroke(thick);
		int size = solution.size();
		double sx, sy, ex, ey;
		Pair<Integer,Integer> start, end;
			
		for(int i = 0; i < size - 1; i++)
		{
			start = solution.get(i);
			end = solution.get(i + 1);
			sx = leftMargin + (start.snd() * cellSize) + halfCellSize;
			ex = leftMargin + (end.snd() * cellSize) + halfCellSize;
			sy = topMargin + (start.fst() * cellSize) + halfCellSize;
			ey = topMargin + (end.fst() * cellSize) + halfCellSize;
			
			line.setLine(sx,sy,ex,ey);
			g2.draw(line);
		}
	}
}