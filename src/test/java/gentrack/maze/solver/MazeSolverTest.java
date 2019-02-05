package gentrack.maze.solver;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

public class MazeSolverTest {

	@Test(expected = FileNotFoundException.class)
	public void testMazeSolverWhenNoFileExist() throws Exception {
		MazeSolver mazeSolver = new MazeSolver();
		File file = new File("/");
		mazeSolver.solve(file);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMazeSolverIfEmptyFile() throws Exception {
		MazeSolver mazeSolver = new MazeSolver();
		File file = new File("src/main/resources/maze/emptyFile.txt");
		mazeSolver.solve(file);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMazeSolverIfNoMazeExistsInFile() throws Exception {
		MazeSolver mazeSolver = new MazeSolver();
		File file = new File("src/main/resources/maze/noMaze.txt");
		mazeSolver.solve(file);
	}
	
	@Test
	public void testMazeSolverSuccess() throws Exception {
		// Given
		MazeSolver mazeSolver = new MazeSolver();
		File file = new File("src/main/resources/maze/input.txt");
		
		// When
		List<Coordinates> path = mazeSolver.solve(file);
		Coordinates startCoordinates = new Coordinates(1, 1);
		Coordinates endCoordinates = new Coordinates(3, 3);
		
		// Then
		assertEquals(startCoordinates.getX(), path.get(0).getX());
		assertEquals(endCoordinates.getX(), path.get(path.size()-1).getX());
		assertEquals(startCoordinates.getY(), path.get(0).getY());
		assertEquals(endCoordinates.getY(), path.get(path.size()-1).getY());
	}
	
	@Test
	public void testMazeSolverHorizontalWrap() throws Exception {
		//Given
		MazeSolver mazeSolver = new MazeSolver();
		File file = new File("src/main/resources/maze/small_wrap2.txt");
		
		// When
		List<Coordinates> path = mazeSolver.solve(file);
		Coordinates startCoordinates = new Coordinates(1, 1);
		Coordinates endCoordinates = new Coordinates(3, 3);
		
		// Then
		assertEquals(startCoordinates.getX(), path.get(0).getX());
		assertEquals(endCoordinates.getX(), path.get(path.size()-1).getX());
		assertEquals(startCoordinates.getY(), path.get(0).getY());
		assertEquals(endCoordinates.getY(), path.get(path.size()-1).getY());
	}
	
	@Test
	public void testMazeSolverVerticalWrap() throws Exception {
		// Given
		MazeSolver mazeSolver = new MazeSolver();
		File file = new File("src/main/resources/maze/vertical_wrap_input.txt");
		
		// When
		List<Coordinates> path = mazeSolver.solve(file);
		Coordinates startCoordinates = new Coordinates(1, 1);
		Coordinates endCoordinates = new Coordinates(3, 3);
		
		// Then
		assertEquals(startCoordinates.getX(), path.get(0).getX());
		assertEquals(endCoordinates.getX(), path.get(path.size()-1).getX());
		assertEquals(startCoordinates.getY(), path.get(0).getY());
		assertEquals(endCoordinates.getY(), path.get(path.size()-1).getY());
	}
}
