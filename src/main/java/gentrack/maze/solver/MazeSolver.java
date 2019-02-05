package gentrack.maze.solver;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MazeSolver {
	private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

	public static void main(String[] args) throws Exception {
		MazeSolver mazeSolver = new MazeSolver();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter file location");
		if (scanner.hasNext()) {
			File maze1 = new File(scanner.nextLine());
			mazeSolver.solve(maze1);
		}
		scanner.close();
	}

	public List<Coordinates> solve(File file) throws Exception {
		MazeUtil mazeUtil = new MazeUtil(file);
		List<Coordinates> path = new ArrayList<>();
		if (!findPath(mazeUtil, mazeUtil.start.getX(), mazeUtil.start.getY(), path)) {
			path = Collections.emptyList();
		}
		mazeUtil.printPath(path);
		mazeUtil.reset();
		return path;
	}

	private boolean findPath(MazeUtil mazeUtil, int row, int col, List<Coordinates> path) {
		if (!mazeUtil.isValidLocation(row, col) || mazeUtil.isWall(row, col) || mazeUtil.isExplored(row, col)) {
			return false;
		}

		path.add(new Coordinates(row, col));
		mazeUtil.setVisited(row, col, true);

		if (mazeUtil.isExit(row, col)) {
			return true;
		}

		for (int[] direction : DIRECTIONS) {
			Coordinates coordinate = mazeUtil.getNextCoordinate(row, col, direction[0], direction[1]);
			if (findPath(mazeUtil, coordinate.getX(), coordinate.getY(), path)) {
				return true;
			}
		}

		path.remove(path.size() - 1);
		return false;
	}

}
