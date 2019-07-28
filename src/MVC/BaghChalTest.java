package MVC;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BaghChalTest extends TestCase {
	
	//Model Methoden Tests
	
	public void testKonstruktorModel()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		assertTrue(model != null);
	}
	
	public void testGetBoard()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		int [][] expectedBoard ={{2,0,0,0,2},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{2,0,0,0,2}};
		assertTrue(Arrays.deepEquals(expectedBoard,model.getBoard()));
	}
	
	public void testGetEatenGoats1()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		assertEquals(0,model.getEatenGoats());
	}
	
	public void testGetEatenGoats2()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		model.setGoat("1-1");
		model.moveTiger("0-0,2-2");
		assertEquals(1,model.getEatenGoats());
	}
	
	public void testGetGoatsLeft1()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		assertEquals(model.getGoatsLeft(),20);
	}
	
	public void testGetGoatsLeft2()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		model.setGoat("1-1");
		assertEquals(model.getGoatsLeft(),19);
	}
	
	public void testCheckValidCordsSet1()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		String nextMove = "2-3";
		assertTrue(model.checkValidCordsSet(nextMove));
	}
	
	public void testCheckValidCordsSet2()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		String nextMove = "2-5";
		assertFalse(model.checkValidCordsSet(nextMove));
	}
	
	public void testCheckValidCordsSet3()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		String nextMove = " 2 - 3";
		assertTrue(model.checkValidCordsSet(nextMove));
	}
	
	public void testCheckValidCordsSet4()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		String nextMove = "\n2 - 3\n\t ";
		assertTrue(model.checkValidCordsSet(nextMove));
	}

	public void testCheckValidCordsMove1()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		String nextMove = "2-3,3-3";
		assertTrue(model.checkValidCordsMove(nextMove));
	}
	
	public void testCheckValidCordsMove2()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		String nextMove = "2-7,3-6";
		assertFalse(model.checkValidCordsMove(nextMove));
	}
	
	public void testCheckValidCordsMove3()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		String nextMove = " 2 - 3, 3- 3";
		assertTrue(model.checkValidCordsMove(nextMove));
	}
	
	public void testCheckValidCordsMove4()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		String nextMove = "2\n-3,\n 3 -\t3";
		assertTrue(model.checkValidCordsMove(nextMove));
	}
	
	public void testIsTigerMoveValid1()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		String nextMove = "0-0,0-0";
		assertEquals(0,model.isPlayerMoveValid(nextMove));
	}
	
	public void testIsTigerMoveValid2()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		assertEquals(1,model.isPlayerMoveValid("0-0,1-1"));
	}
	
	public void testIsTigerMoveValid3()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		model.setGoat("1-1");
		assertEquals(2,model.isPlayerMoveValid("0-0,2-2"));
	}
	
	public void testMoveTiger1()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		model.setGoat("4-1");
		model.moveTiger("0-0,1-0");
		int [][] expectedBoard ={{0,0,0,0,2},{2,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{2,1,0,0,2}};
		assertTrue(Arrays.deepEquals(expectedBoard,model.getBoard()));
	}
	
	public void testMoveTiger2()
	{
		BaghChalPlayerBoard model = new BaghChalPlayerBoard();
		model.setGoat("1-1");
		model.moveTiger("0-0,2-2");
		int [][] expectedBoard ={{0,0,0,0,2},{0,0,0,0,0},{0,0,2,0,0},{0,0,0,0,0},{2,0,0,0,2}};
		assertTrue(Arrays.deepEquals(expectedBoard,model.getBoard()));
	}
	
	//IO Methoden Tests
	
	public void testKonstruktorIO()
	{
		Scanner scanner = new Scanner(System.in);
		BaghChalInputOutput io = new BaghChalInputOutput(scanner);
		assertTrue(io != null);
	}
	
	public void testGenerateBoard1()
	{
		Scanner scanner = new Scanner(System.in);
		BaghChalInputOutput io = new BaghChalInputOutput(scanner);
		int [][] board = {{2,0,0,0,2},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{2,0,0,0,2}};
		io.generateBoard(board);
		String expectedOutput = "\n 0 1 2 3 4\n0T-+-+-+-T\n |\\|/|\\|/|\n1+-+-+-+-+\n |/|\\|/|\\|\n2+-+-+-+-+\n |\\|/|\\|/|\n3+-+-+-+-+\n |/|\\|/|\\|\n4T-+-+-+-T";
		assertEquals(expectedOutput,io.getOutput());
	}
	
	public void testGenerateBoard2()
	{
		Scanner scanner = new Scanner(System.in);
		BaghChalInputOutput io = new BaghChalInputOutput(scanner);
		int [][] board = {{2,1,1,0,2},{2,0,0,2,0},{2,1,0,1,1},{0,0,-1,0,0},{2,-1,0,0,2}};
		io.generateBoard(board);
		String expectedOutput = "\n 0 1 2 3 4\n0T-Z-Z-+-T\n |\\|/|\\|/|\n1T-+-+-T-+\n |/|\\|/|\\|\n2T-Z-+-Z-Z\n |\\|/|\\|/|\n3+-+-+-+-+\n |/|\\|/|\\|\n4T-+-+-+-T";
		assertEquals(expectedOutput,io.getOutput());
	}
	
	public void testResetNextMove()
	{
		Scanner scanner = new Scanner(System.in);
		BaghChalInputOutput io = new BaghChalInputOutput(scanner);
		io.resetNextMove();
		assertEquals(null,io.getNextMove());
	}
	
	//Komplette Spiele Tests
	
	public void testGameTigerWins() {
		ByteArrayOutputStream expectedOutput = new ByteArrayOutputStream();
		System.setOut(new PrintStream(expectedOutput));
		String moves = "0-1\n0-0\n0-2\n0-1\n0-2\n0-0\n0-1\n0-0\n0-2\n0-1\n0-2\n0-0\n0-1\n0-0\n0-2\n";
		System.setIn(new ByteArrayInputStream(moves.getBytes()));
		Scanner scanner = new Scanner(System.in);
		BaghChalGameController controller= new BaghChalGameController(scanner);
		
		assertTrue(expectedOutput.toString().endsWith("Spiel beendet. Spieler T hat gewonnen!\r\n"));
	}
	
	public void testGameGoatWins() {
		ByteArrayOutputStream expectedOutput = new ByteArrayOutputStream();
		System.setOut(new PrintStream(expectedOutput));
		String moves = "4-1\n4-0\n3-1\n4-0\n3-1\n2-1\n4-2\n2-1\n1-1\n4-3\n4-4\n3-3\n1-0\n3-3\n2-2\n2-0\n2-2\n1-2\n2-1\n1-1\n0-1\n2-4\n1-2\n0-2\n2-2\n0-4\n0-3\n2-3\n0-3\n0-4\n3-0\n0-4\n0-3\n1-1\n0-3\n0-4\n1-2\n0-4\n0-3\n1-3\n0-3\n0-4\n1-4\n0-4\n0-3\n0-4\n";
		System.setIn(new ByteArrayInputStream(moves.getBytes()));
		Scanner scanner = new Scanner(System.in);
		BaghChalGameController controller= new BaghChalGameController(scanner);
		
		assertTrue(expectedOutput.toString().endsWith("Spiel beendet. Spieler Z hat gewonnen!\r\n"));
	}
	
}
