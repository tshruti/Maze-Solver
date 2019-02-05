package gentrack.maze.solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MazeUtil {
	private static final int PASSAGE = 0;
	private static final int WALL = 1;
	private static final int START = 2;
	private static final int EXIT = 3;
	private static final int PATH = 4;

	private int[][] maze;
	private boolean[][] visited;
	public Coordinates start;
	public Coordinates end;

	public MazeUtil(File inputFile) throws FileNotFoundException {
		String givenMaze = "";
		try (Scanner input = new Scanner(inputFile)) {
			if (!input.hasNext()) {
				throw new IllegalArgumentException("No data found");
			}
			// Set maze size
			int width = input.nextInt();
			int height = input.nextInt();
			this.maze = new int[height][width];

			// Set maze start
			visited = new boolean[height][width];
			int startX = input.nextInt();
			int startY = input.nextInt();
			maze[startY][startX] = START;
			start = new Coordinates(startY, startX);

			// Set maze exit
			int exitX = input.nextInt();
			int exitY = input.nextInt();
			maze[exitY][exitX] = EXIT;
			end = new Coordinates(exitY, exitX);

			while (input.hasNextLine()) {
				givenMaze += input.nextLine().replaceAll(" ", "") + "\n";
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(e.getMessage());
		}
		initializeMaze(givenMaze);
	}

	private void initializeMaze(String text) {
		if (text == null || (text = text.trim()).length() == 0) {
			throw new IllegalArgumentException("No data found");
		}

		String[] lines = text.split("[\r]?\n");

		for (int row = 0; row < getHeight(); row++) {
			if (lines[row].length() != getWidth()) {
				throw new IllegalArgumentException("Wrong width should be: "+ getWidth());
			}

			for (int col = 0; col < getWidth(); col++) {
				if (!((row == start.x && col == start.y) || (row == end.x && col == end.y))) {
					if (lines[row].charAt(col) == '1')
						maze[row][col] = WALL;
					else if (lines[row].charAt(col) == '0')
						maze[row][col] = PASSAGE;
				}
			}
		}
	}

	public int getHeight() {
		return maze.length;
	}

	public int getWidth() {
		return maze[0].length;
	}

	public boolean isExit(int x, int y) {
		return x == end.getX() && y == end.getY();
	}

	public boolean isStart(int x, int y) {
		return x == start.getX() && y == start.getY();
	}

	public boolean isExplored(int row, int col) {
		return visited[row][col];
	}

	public boolean isWall(int row, int col) {
		return maze[row][col] == WALL;
	}

	public void setVisited(int row, int col, boolean value) {
		visited[row][col] = value;
	}

	public boolean isValidLocation(int row, int col) {
		if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
			return false;
		}
		return true;
	}

	public Coordinates getNextCoordinate(int row, int col, int i, int j) {
		int newRow = row + i, newCol = col + j;
		if (newRow == -1) {
			if (!isWall(getWidth() - 1, col))
				newRow = getWidth() - 1;
		}
		if (newRow == getWidth()) {
			if (!isWall(0, col))
				newRow = 0;
		}
		if (newCol == -1) {
			if (!isWall(row, (getHeight() - 1)))
				newCol = getHeight() - 1;
		}
		if (newCol == getHeight()) {
			if (!isWall(row, 0))
				newCol = 0;
		}
		return new Coordinates(newRow, newCol);
	}

	public void printPath(List<Coordinates> path) {
		if (path.size() == 0) {
			System.out.println("No path found!!");
			return;
		}
		int[][] tempMaze = Arrays.stream(maze).map(int[]::clone).toArray(int[][]::new);
		for (Coordinates coordinate : path) {
			if (isStart(coordinate.getX(), coordinate.getY()) || isExit(coordinate.getX(), coordinate.getY())) {
				continue;
			}
			tempMaze[coordinate.getX()][coordinate.getY()] = PATH;
		}
		System.out.println(toString(tempMaze));
	}

	public String toString(int[][] maze) {
		StringBuilder result = new StringBuilder(getWidth() * (getHeight() + 1));
		for (int row = 0; row < getHeight(); row++) {
			for (int col = 0; col < getWidth(); col++) {
				if (maze[row][col] == PASSAGE) {
					result.append(' ');
				} else if (maze[row][col] == WALL) {
					result.append('#');
				} else if (maze[row][col] == START) {
					result.append('S');
				} else if (maze[row][col] == EXIT) {
					result.append('E');
				} else {
					result.append('X');
				}
			}
			result.append('\n');
		}
		return result.toString();
	}

	public void reset() {
		for (int i = 0; i < visited.length; i++)
			Arrays.fill(visited[i], false);
	}
}