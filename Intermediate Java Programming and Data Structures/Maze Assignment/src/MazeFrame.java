import java.util.ArrayList;

import javax.swing.JFrame;

public class MazeFrame
{
	public static void main(String[] args) throws InterruptedException
	{
		int width = 40;
		int height = 40;
		JFrame frame = new JFrame();
		Maze maze = new Maze(width, height);
		ArrayList<Pair<Integer,Integer>> solution = new ArrayList<Pair<Integer,Integer>>();
		MazeComponent mc = new MazeComponent(maze, solution);
		frame.setSize(800,800);
		frame.setTitle("Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mc);
		frame.setVisible(true);
		
		solution.add(new Pair<Integer,Integer>(0,0));
		Thread.sleep(1000);
		solveMaze(solution, mc, maze,0,0,4);
		mc.repaint();
	}
	
	/** Solve Maze: recursively solve the maze
	 * 
	 * @param solution   : The array list solution is needed so that every recursive call,
	 *                     a new (or more) next position can be added or removed.
	 * @param mc         : This is the MazeComponent. We need that only for the purpose of
	 *                     animation. We need to call mc.repaint() every time a new position
	 *                     is added or removed. For example,
	 *                       :
	 *                     solution.add(...);
	 *                     mc.repaint();
	 *                     Thread.sleep(sleepTime);
	 *                       :
	 *                     solution.remove(...);
	 *                     mc.repaint();
	 *                     Thread.sleep(sleepTime);
	 *                       :
	 * @param maze       : The maze data structure to be solved. 
	 * @return a boolean value to previous call to tell the previous call whether a solution is
	 *         found.
	 * @throws InterruptedException: We need this because of our Thread.sleep(50);
	 */
	public static boolean solveMaze(ArrayList<Pair<Integer,Integer>> solution, MazeComponent mc, Maze maze,int x,int y,int fromDirection) throws InterruptedException
	{
		int endWidth=maze.width-1;
		int endHeight=maze.height-1;
		if (x==endWidth && y==endHeight)
			return true;
		if (!maze.northWall[x][y] && fromDirection!=3){
			Pair<Integer,Integer> pair = new Pair<Integer,Integer>(y-1,x);
			solution.add(pair);
			mc.repaint();
			Thread.sleep(50);
			boolean isTrue=solveMaze(solution,mc,maze,x,y-1,0);
			if (isTrue) return true;
		}
		if (!maze.eastWall[x][y] && fromDirection!=2){
			Pair<Integer,Integer> pair = new Pair<Integer,Integer>(y,x+1);
			solution.add(pair);
			mc.repaint();
			Thread.sleep(50);
			boolean isTrue=solveMaze(solution,mc,maze,x+1,y,1);
			if (isTrue) return true;
		}
		if (!maze.southWall[x][y] && fromDirection!=0){
			Pair<Integer,Integer> pair = new Pair<Integer,Integer>(y+1,x);
			solution.add(pair);
			mc.repaint();
			Thread.sleep(50);
			boolean isTrue=solveMaze(solution,mc,maze,x,y+1,3);
			if (isTrue) return true;
		}
		if (!maze.westWall[x][y] && fromDirection!=1){
			Pair<Integer,Integer> pair = new Pair<Integer,Integer>(y,x-1);
			solution.add(pair);
			mc.repaint();
			Thread.sleep(50);
			boolean isTrue=solveMaze(solution,mc,maze,x-1,y,2);
			if (isTrue) return true;
		}
		solution.remove(solution.size()-1);
		mc.repaint();
		Thread.sleep(50);
		return false;
	}
	
}