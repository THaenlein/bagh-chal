package MVC;

import java.util.Scanner;

/**
 * InputOutput empf�ngt User-Eingaben und und erzeugt Konsolen-Ausgaben.
 * 
 * @author Tim H�nlein
 *
 */

public class BaghChalInputOutput {
	
	/**
	 * Scanner nimmt User-Eingaben entgegen.
	 */ 
	private Scanner scanner;
	
	/**
	 * Beschreibt den n�chsten Zug des Spielers.
	 */
	private String nextMove;
	
	/**
	 * Speichert die Konsolenausgabe des Spielbretts als Zeichenkette.
	 */
	private String boardConsoleOutput;
	
	/**
	 * Erzeugt ein neues InputOutput-Objekt mit dem Scanner des GameControllers.
	 * Au�erdem wird zu Spielstart ein Intro-Text ausgegeben.
	 * @param scanner Scanner des GameControllers, der Eingaben entgegen nimmt.
	 */
	public BaghChalInputOutput (Scanner scanner)
	{
		System.out.println("BaghChal - Ein Brettspiel mit Ziegen und Tigern.");
		System.out.println("");
		System.out.println("Eingabemuster: Zeile-Spalte");
		this.scanner = scanner;
	}
	
	/**
	 * Gibt den eingelesenen n�chsten Zug des Spielers als String zur�ck.
	 * @return n�chster Zug.
	 */
	public String getNextMove()
	{
		return this.nextMove;
	}
	
	/**
	 * L�scht die Eingabe des letzten Spielerzuges.
	 */
	public void resetNextMove()
	{
		nextMove = null;
	}
	
	/**
	 * Gibt die Konsolenausgabe des Spielbretts als Zeichenkette zur�ck.
	 * @return Ausgabe des Spielbretts in der Konsole.
	 */
	public String getOutput()
	{
		return this.boardConsoleOutput;
	}
	
	/**
	 * Gibt Text zum Setzen einer Ziege aus und ruft die User-Eingabe auf.
	 * @return n�chster Spielerzug
	 */
	public String setGoat()
	{
		System.out.println("Ziege setzen.");
		System.out.print("Ziel: ");
		waitForInput();
		return nextMove;
	}
	
	/**
	 * Gibt Text zum Bewegen einer Ziege aus und ruft die User-Eingabe auf.
	 * @return n�chster Spielerzug
	 */
	public String moveGoat()
	{
		System.out.println("Ziege bewegen.");
		System.out.print("Start: ");
		waitForInput();
		System.out.print("Ziel: ");
		waitForInput();
		return nextMove;
	}
	
	/**
	 * Gibt Text zum Bewegen eines Tigers aus und ruft die User-Eingabe auf.
	 * @return n�chster Spielerzug
	 */
	public String moveTiger()
	{
		System.out.println("Tiger bewegen.");
		System.out.print("Start: ");
		waitForInput();
		System.out.print("Ziel: ");
		waitForInput();
		return nextMove;
	}
	
	/**
	 * Generiert den Aufbau des Spielbretts f�r die Ausgabe in der Konsole.
	 * Liest den Spielplan ein und bestimmt aus den Integerwerten, ob eine Ziege, ein Tiger oder ein leeres Spielfeld vorliegt.
	 * Das allgemeine Layout der Augabe wird hier ebenso erzeugt.  
	 * @param board Spielbrett mit enthaltenen Spielfiguren
	 */
	public void generateBoard(int [][] board)
	{
		boardConsoleOutput="\n";
		int rowCounter = board.length;
		int colCounter;
		int counter = 0;
		
		boardConsoleOutput += " 0 1 2 3 4" + "\n";
		
		for (int[] row:board) //F�r jede Zeile �berpr�fen
		{
			boardConsoleOutput += counter;
			colCounter = row.length;
			for (int col:row) //F�r jedes Element jeder Zeile �berpr�fen
			{
				switch (col)
				{
					case 1: boardConsoleOutput += "Z"; break; //Ziege an der Stelle
					case 2: boardConsoleOutput += "T"; break; //Tiger an der Stelle
					default: boardConsoleOutput += "+"; break; //leeres Feld
				}
				colCounter--;
				if (colCounter > 0)
				{
					boardConsoleOutput += "-";
				}
			}
			rowCounter--;
			if (rowCounter > 0)
			{
				if (rowCounter%2==0) //Gibt die Zeile zwischen den Spielfiguren aus
				{
					boardConsoleOutput +="\n" + " |\\|/|\\|/|" +"\n";
				}
				else
				{
					boardConsoleOutput +="\n" +  " |/|\\|/|\\|" + "\n";
				}
			}
			counter++;
		}
		System.out.println(boardConsoleOutput +"\n");
	}
	
	private void waitForInput()
	{
		if (this.nextMove==null)
		{
			nextMove = scanner.nextLine(); //Erste Eingabe, oder nur zum Setzen
		}
		else
		{
			nextMove +="," + scanner.nextLine(); //Folge Eingabe zum Bewegen
		}
		
	}
	
	/**
	 * Gibt die aktuelle Anzahl der gefressenen Ziegen und zu setzenden Ziegen aus.
	 * Ebenso wird ausgegeben welcher Spieler am Zug ist.
	 * @param eatenGoats Gegessene Ziegen
	 * @param goatsLeft Zu setzende Ziegen
	 * @param currentPlayer Spieler, der gerade am Zug ist
	 */
	public void showStats(int eatenGoats, int goatsLeft, boolean currentPlayer)
	{
		System.out.println("Anzahl gefressener Ziegen: " + eatenGoats);
		System.out.println("Anzahl zu setzender Ziegen: " + goatsLeft);

		if (currentPlayer)
		{
			System.out.println("Ziege am Zug.");
		}
		else
		{
			System.out.println("Tiger am Zug.");
		}
		
	}
	
	/**
	 * Gibt aus, dass der gew�nschte Zug ung�ltig ist
	 */
	public void inputNotValid()
	{
		resetNextMove();
		System.out.println("Ung�ltige Koordinaten!" + "\n" + "Bitte �berpr�fen Sie Ihre Eingabe.");
	}
	
	/**
	 * GameOver-Text f�r den Fall, dass Spieler T gewonnen hat.
	 */
	public void gameOverGoat()
	{
		System.out.println("Spiel beendet. Spieler T hat gewonnen!");
	}

	/**
	 * GameOver-Text f�r den Fall, dass Spieler Z gewonnen hat.
	 */
	public void gameOverTiger()
	{
		System.out.println("Spiel beendet. Spieler Z hat gewonnen!");
	}
}
