import java.util.Random;

public class MazeTester
{
	public static void main(String[] args)
	{
		Random rand = new Random();
		
		rand.setSeed(System.currentTimeMillis());
		
		int width = rand.nextInt(91) + 10;
		int height = rand.nextInt(91) + 10;
		
		System.out.println("Constructing a maze size " + width + " by " + height + ".");
		
		Maze maze = new Maze(width, height);
		
		// Check the method getWidth();
		
		System.out.print("Check the method getWidth(): ");
		
		if(maze.getWidth() != width)
		{
			System.out.println("FAIL");
			System.out.println("The method getWidth() should return " + width + ".");
			System.out.println("But your method getWidth() returns " + maze.getWidth() + ".");
		}
		else
		{
			System.out.println("PASS");
		}
		
		// Check the method getHeight();
		
		System.out.print("Check the method getHeight(): ");
		
		if(maze.getHeight() != height)
		{
			System.out.println("FAIL");
			System.out.println("The method getHeight() should return " + height + ".");
			System.out.println("But your method getHeight() returns " + maze.getHeight() + ".");
		}
		else
		{
			System.out.println("PASS");
		}
		
		// Check all outside east and west walls
		
		System.out.print("Checking all outside east and west walls: ");
		
		for(int row = 0; row < height; row++)
		{
			if(!maze.isWestWall(row,0))
			{
				System.out.println("FAIL");
				System.out.println("Chamber (" + row + ",0) must have a west wall.");
				System.out.println("But your method isWestWall(" + row + ",0) returns " + maze.isWestWall(row,0) + ".");
				return;
			}
			
			if(!maze.isEastWall(row, width - 1))
			{
				System.out.println("FAIL");
				System.out.println("Call (" + row + "," + (width - 1) + ") must have an east wall.");
				System.out.println("But your method isEastWall(" + row + "," + (width - 1) + ") returns " + maze.isEastWall(row, width - 1) + ".");
				return;
			}
		}
		
		System.out.println("PASS");
		
		// Check all outside north and south walls
		
		System.out.print("Checking all outside north and south walls: ");
		
		for(int column = 0; column < width; column++)
		{
			if(!maze.isNorthWall(0, column))
			{
				System.out.println("FAIL");
				System.out.println("Chamber (0," + column + ") must have a north wall.");
				System.out.println("But your method isNorthWall(0," + column + ") returns " + maze.isNorthWall(0, column) + ".");
				return;
			}
			
			if(!maze.isSouthWall(height - 1, column))
			{
				System.out.println("FAIL");
				System.out.println("Chamber (" + (height - 1) + "," + column + ") must have a south wall.");
				System.out.println("But your method isSouthWall(" + (height - 1) + "," + column + ") returns " + maze.isSouthWall(height - 1, column) + ".");
				return;
			}
		}
		
		System.out.println("PASS");
		
		// Check all inside wall
		
		System.out.print("Checking adjacent walls from all adjacent chambers: ");
		
		for(int row = 0; row < height; row++)
		{
			for(int column = 0; column < width; column++)
			{
				if(row - 1 >= 0)
				{
					if(maze.isNorthWall(row, column) != maze.isSouthWall(row - 1, column))
					{
						System.out.println("FAIL");
						System.out.println("Chamber (" + row + "," + column + ")'s north wall (if exists) is the same wall as chamber (" + (row - 1) + "," + column + ")'s south wall.");
						System.out.println("But your methods isNorthWall(" + row + "," + column + ") and isSouthWall(" + (row - 1) + "," + column + ") return different values.");
						return;
					}
				}
				
				if(column + 1 < width)
				{
					if(maze.isEastWall(row, column) != maze.isWestWall(row, column + 1))
					{
						System.out.println("FAIL");
						System.out.println("Chamber (" + row + "," + column + ")'s east wall (if exists) is the same wall as chamber (" + row + "," + (column + 1) + ")'s west wall.");
						System.out.println("But your methods isEastWall(" + row + "," + column + ") and isWestWall(" + row + "," + (column + 1) + ") return different values.");
						return;
					}
				}
				
				if(row + 1 < height)
				{
					if(maze.isSouthWall(row, column) != maze.isNorthWall(row + 1, column))
					{
						System.out.println("FAIL");
						System.out.println("Chamber (" + row + "," + column + ")'s south wall (if exists) is the same wall as chamber (" + (row + 1) + "," + column + ")'s north wall.");
						System.out.println("But your methods isSouthWall(" + row + "," + column + ") and isNorthWall(" + (row + 1) + "," + column + ") return different values.");
						return;
					}
				}
				
				if(column - 1 >= 0)
				{
					if(maze.isWestWall(row, column) != maze.isEastWall(row, column - 1))
					{
						System.out.println("FAIL");
						System.out.println("Chamber (" + row + "," + column + ")'s west wall (if exists) is the same wall as chamber (" + row + "," + (column - 1) + ")'s east wall.");
						System.out.println("But your methods isWestWall(" + row + "," + column + ") and isEastWall(" + row + "," + (column - 1) + ") return different values.");
						return;
					}
				}
			}
		}
		
		System.out.println("PASS");
		
		// Searching for a long wall with only one opening
		
		System.out.print("Checking for a long wall with only one opening: ");
		
		int numberOfOneOpeningWalls = 0;
		
		for(int row = 0; row < height - 1; row++)
		{
			int numOpening = 0;
			
			for(int column = 0; column < width; column++)
			{
				if(!maze.isSouthWall(row, column))
				{
					numOpening++;
				}
			}
			
			if(numOpening == 1)
			{
				numberOfOneOpeningWalls++;
			}
		}
		
		for(int column = 0; column < width - 1; column++)
		{
			int numOpening = 0;
			
			for(int row = 0; row < height; row++)
			{
				if(!maze.isEastWall(row,column))
				{
					numOpening++;
				}
			}
			
			if(numOpening == 1)
			{
				numberOfOneOpeningWalls++;
			}
		}
		
		if(numberOfOneOpeningWalls == 1)
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
			System.out.println("According to the algorithm for generating your maze,");
			System.out.println("your maze suppose to contain exactly one long wall with only one opening.");
			System.out.println("But your maze contains " + numberOfOneOpeningWalls + " walls with one opening.");
			return;
		}
		
		// Searching for a closed chamber

		System.out.print("Checking for closed rectangular or square chamber: ");
		
		for(int startRow = 0; startRow < height; startRow++)
		{
			for(int startColumn = 0; startColumn < width; startColumn++)
			{
				for(int endRow = startRow; endRow < height; endRow++)
				{
					for(int endColumn = startColumn; endColumn < width; endColumn++)
					{
						if(startRow != 0 && startColumn != 0 && endRow != height - 1 && endColumn != width - 1)
						{
							if(isClosed(maze, startRow, startColumn, endRow, endColumn))
							{
								System.out.println("FAIL");
								System.out.println("Chamber where its top left corner is (" + startRow + "," + startColumn + ") and its bottom right corner is (" + endRow + "," + endColumn + ") is closed.");
								return;
							}
						}
					}
				}
			}
		}
		
		System.out.println("PASS");
	}

	public static boolean isClosed(Maze maze, int startRow, int startColumn, int endRow, int endColumn)
	{
		boolean openingExists = false;
		
		for(int column = startColumn; column <= endColumn; column++)
		{
			if(!maze.isNorthWall(startRow, column))
			{
				openingExists = true;
			}
			
			if(!maze.isSouthWall(endRow, column))
			{
				openingExists = true;
			}
		}
		
		for(int row = startRow; row <= endRow; row++)
		{
			if(!maze.isWestWall(row, startColumn))
			{
				openingExists = true;
			}
			
			if(!maze.isEastWall(row, endColumn))
			{
				openingExists = true;
			}
		}
		
		return !openingExists;
	}
	
}