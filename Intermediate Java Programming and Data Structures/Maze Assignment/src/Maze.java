import java.util.Random;

// No other import statement is allowed

public class Maze {
	int width = 0;
	int height = 0;
	boolean[][] northWall = null;
	boolean[][] eastWall = null;
	boolean[][] westWall = null;
	boolean[][] southWall = null;
	int currentX = 0;
	int currentY = 0;
	int startX = 0;
	int startY = 0;
	Random rnd = new Random();

	/**
	 * Constructor
	 * 
	 * @param aWidth
	 *            the number of chambers in each row
	 * @param aHeight
	 *            the number of chamber in each column
	 */
	public Maze(int aWidth, int aHeight) {
		width = aWidth;
		height = aHeight;
		northWall = new boolean[width][height];
		eastWall = new boolean[width][height];
		westWall = new boolean[width][height];
		southWall = new boolean[width][height];
		startX = rnd.nextInt(width);
		startY = rnd.nextInt(height);
		MakeTheMaze();
	}

	private void MakeWalls(int northernBound, int westernBound,
			int easternBound, int southernBound) {
		boolean goodPointX = false;
		boolean goodPointY = false;
		int leftWallLength = 0;
		int rightWallLength = 0;
		int northWallLength = 0;
		int southWallLength = 0;
		int centerX = 0;
		int centerY = 0;
		int randomOpen = rnd.nextInt(4);
		if ((easternBound - westernBound == 0)
				|| southernBound - northernBound == 0) {
			return;
		}
		if ((easternBound - westernBound == 1)
				&& (southernBound - northernBound == 1)) {
			centerX = westernBound;
			centerY = northernBound;
			if (randomOpen == 0) {
				eastWall[centerX][centerY] = true;
				westWall[centerX + 1][centerY] = true;
			} else if (randomOpen == 1) {
				southWall[centerX][centerY] = true;
				northWall[centerX][centerY + 1] = true;
			} else if (randomOpen == 2) {
				eastWall[centerX][centerY + 1] = true;
				westWall[centerX + 1][centerY + 1] = true;
			} else {
				southWall[centerX + 1][centerY] = true;
				northWall[centerX + 1][centerY + 1] = true;
			}
			return;
		}
		if ((easternBound - westernBound) > 0
				&& (southernBound - northernBound) > 0) {
			if ((easternBound - westernBound) == 1) {
				centerX = westernBound + 1;
				goodPointX = true;
				leftWallLength = 1;
				rightWallLength = 0;
			}
			if ((southernBound - northernBound) == 1) {
				centerY = northernBound+1;
				goodPointY = true;
				northWallLength = 1;
				southWallLength = 0;
			}
			while (!goodPointX) {
				centerX = rnd.nextInt(easternBound - westernBound)
						+ westernBound+1;
				leftWallLength = centerX - westernBound;
				rightWallLength = easternBound - centerX;
				if (leftWallLength >= 1 && rightWallLength >= 1 && centerX!=easternBound && centerX!=westernBound) {
					goodPointX = true;
				}
			}
			while (!goodPointY) {
				centerY = rnd.nextInt(southernBound - northernBound)
						+ northernBound+1;
				northWallLength = centerY - northernBound;
				southWallLength = southernBound - centerY;
				if (northWallLength >= 1 && southWallLength >= 1 && centerY!=southernBound && centerY!=northernBound) {
					goodPointY = true;
				}
			}
		}
		for (int i = centerX; (i <= easternBound); i++) {
			if (centerY > northernBound) {
				northWall[i][centerY] = true;
				southWall[i][centerY - 1] = true;
			} else {
				southWall[i][centerY] = true;
				northWall[i][centerY + 1] = true;
			}
		}
		for (int i = centerX; (i >= westernBound); i--) {
			if (centerY > northernBound) {
				northWall[i][centerY] = true;
				southWall[i][centerY - 1] = true;
			} else {
				southWall[i][centerY] = true;
				northWall[i][centerY + 1] = true;
			}
		}
		for (int i = centerY; (i <= southernBound); i++) {
			if (centerX > westernBound) {
				westWall[centerX][i] = true;
				eastWall[centerX - 1][i] = true;
			} else {
				eastWall[centerX][i] = true;
				westWall[centerX][i] = true;
			}
		}
		for (int i = centerY; (i >= northernBound); i--) {
			if (centerX > westernBound) {
				westWall[centerX][i] = true;
				eastWall[centerX - 1][i] = true;
			} else {
				eastWall[centerX][i] = true;
				westWall[centerX + 1][i] = true;
			}
		}

		for (int i = centerY; (i <= southernBound); i++) {
			if (centerX > westernBound) {
				westWall[centerX][i] = true;
				eastWall[centerX - 1][i] = true;
			} else {
				eastWall[centerX][i] = true;
				westWall[centerX + 1][i] = true;
			}
		}
			int rightWallOpen= rnd.nextInt(rightWallLength+1);
			int leftWallOpen= rnd.nextInt(leftWallLength);
			int northWallOpen=rnd.nextInt(northWallLength);
			int southWallOpen=rnd.nextInt(southWallLength+1);
		if (randomOpen==0){
			northWall[centerX+rightWallOpen][centerY]=false;
			southWall[centerX+rightWallOpen][centerY-1]=false;
			northWall[centerX-leftWallOpen-1][centerY]=false;
			southWall[centerX-leftWallOpen-1][centerY-1]=false;
			westWall[centerX][centerY-northWallOpen-1]=false;
			eastWall[centerX-1][centerY-northWallOpen-1]=false;
		}
		else if (randomOpen==1){
			northWall[centerX+rightWallOpen][centerY]=false;
			southWall[centerX+rightWallOpen][centerY-1]=false;
			northWall[centerX-leftWallOpen-1][centerY]=false;
			southWall[centerX-leftWallOpen-1][centerY-1]=false;
			westWall[centerX][centerY+southWallOpen]=false;
			eastWall[centerX-1][centerY+southWallOpen]=false;
		}
		else if (randomOpen==2){
			northWall[centerX+rightWallOpen][centerY]=false;
			southWall[centerX+rightWallOpen][centerY-1]=false;
			westWall[centerX][centerY-northWallOpen-1]=false;
			eastWall[centerX-1][centerY-northWallOpen-1]=false;
			westWall[centerX][centerY+southWallOpen]=false;
			eastWall[centerX-1][centerY+southWallOpen]=false;
		}
		else{
			northWall[centerX-leftWallOpen-1][centerY]=false;
			southWall[centerX-leftWallOpen-1][centerY-1]=false;
			westWall[centerX][centerY-northWallOpen-1]=false;
			eastWall[centerX-1][centerY-northWallOpen-1]=false;
			westWall[centerX][centerY+southWallOpen]=false;
			eastWall[centerX-1][centerY+southWallOpen]=false;
		}
		MakeWalls(centerY, centerX, easternBound, southernBound);
		MakeWalls(centerY,westernBound,centerX-1,southernBound);
		MakeWalls(northernBound,centerX,easternBound,centerY-1);
		MakeWalls(northernBound,westernBound,centerX-1,centerY-1);
		
	}

	private void MakeTheMaze() {
		for (int i = 0; i < width; i++) {
			northWall[i][0] = true;
		}
		for (int i = 0; i < height; i++) {
			eastWall[width - 1][i] = true;
		}
		for (int i = 0; i < width; i++) {
			southWall[i][height - 1] = true;
		}
		for (int i = 0; i < height; i++) {
			westWall[0][i] = true;
		}
		MakeWalls(0, 0, width - 1, height - 1);

	}

	/**
	 * getWidth
	 * 
	 * @return the width of this maze
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * getHeight
	 * 
	 * @return the height of this maze
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * isNorthWall
	 * 
	 * @param row
	 *            the row identifier of a chamber
	 * @param column
	 *            the column identifier of a chamber
	 * @return true if the chamber at row row and column column contain a north
	 *         wall. Otherwise, return false
	 */
	public boolean isNorthWall(int row, int column) {
		return northWall[column][row];
	}

	/**
	 * isEastWall
	 * 
	 * @param row
	 *            the row identifier of a chamber
	 * @param column
	 *            the column identifier of a chamber
	 * @return true if the chamber at row row and column column contain an east
	 *         wall. Otherwise, return false
	 */
	public boolean isEastWall(int row, int column) {
		return eastWall[column][row];
	}

	/**
	 * isSouthWall
	 * 
	 * @param row
	 *            the row identifier of a chamber
	 * @param column
	 *            the column identifier of a chamber
	 * @return true if the chamber at row row and column column contain a south
	 *         wall. Otherwise, return false
	 */
	public boolean isSouthWall(int row, int column) {
		return southWall[column][row];
	}

	/**
	 * isWestWall
	 * 
	 * @param row
	 *            the row identifier of a chamber
	 * @param column
	 *            the column identifier of a chamber
	 * @return true if the chamber at row row and column column contain a west
	 *         wall. Otherwise, return false
	 */

	public boolean isWestWall(int row, int column) {
		return westWall[column][row];
	}
}