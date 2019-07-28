package MVC;

import java.util.Scanner;

/**
 * Der GameController steuert den Ablauf des Spiels und gew�hrleistet, dass alle Methoden in der richtigen Reihenfolge aufgerufen werden.
 * Er enth�lt au�erdem die anderen Klassen {@link BaghChalPlayerBoard} und {@link BaghChalInputOutput}.
 * 
 * @author Tim H�nlein
 *
 */

public class BaghChalGameController{
	/**
	 * Spielbrett Objekt 
	 */
	private BaghChalPlayerBoard playerBoard;
	/**
	 * InputOutput Objekt
	 */
	private BaghChalInputOutput inputOutput;
	/**
	 * Beschreibt den n�chsten Zug des Spielers.
	 * String ist entweder kodiert mit:
	 * ziely-zielx
	 * oder
	 * starty-startx,ziely-zielx
	 */
	private String nextMove;
	
	/**
	 * Erzeugt den GameController und initiiert die Programm-Schleife, in der der Programmablauf wiederholt wird.
	 * @param scanner zum Abfangen von User-Eingaben
	 */
	
	public BaghChalGameController (Scanner scanner) //Konstruktor
	{
		this.playerBoard = new BaghChalPlayerBoard();
		this.inputOutput = new BaghChalInputOutput(scanner);
		
		mainLoop();
	}
	
	/**
	 * Die Hauptschleife des Programmablaufs.
	 * Das Spiel wird solange ausgef�hrt, bis ein Spieler gewonnen hat. Dabei wechseln sich Spieler Z und Spieler T ab.
	 * Ist Spieler Z am Zug, setzt dieser entweder eine Ziege, oder bewegt eine Ziege, sollte er keine mehr setzen k�nnen {@link BaghChalPlayerBoard#getGoatsLeft()}}.
	 * Ist Spieler T am Zug, bewegt dieser einen Tiger.
	 */
	private void mainLoop() //Hauptschleife des Spiels
	{
		while (!checkWon()) 
		{
			inputOutput.generateBoard(playerBoard.getBoard());
			inputOutput.showStats(playerBoard.getEatenGoats(), playerBoard.getGoatsLeft(), playerBoard.getPlayersTurn());
			inputOutput.resetNextMove();
			if (playerBoard.getPlayersTurn()) //Wenn Ziege am Zug
			{
				if (playerBoard.getGoatsLeft()>0) //Wenn Ziegen �brig sind
				{
					this.nextMove = inputOutput.setGoat();
					while ((!playerBoard.checkValidCordsSet(this.nextMove)) || (!playerBoard.isGoatTurnValid(this.nextMove))) //Pr�fe, ob Zug valid ist
					{
						inputOutput.inputNotValid();
						this.nextMove = inputOutput.setGoat();
					}
					playerBoard.setGoat(this.nextMove);
				}
				else //Wenn keine Ziegen �brig sind
				{
					this.nextMove = inputOutput.moveGoat();
					while (!playerBoard.checkValidCordsMove(this.nextMove) || (!playerBoard.isGoatTurnValid(this.nextMove))) //Pr�fe, ob Zug valid ist
					{
						inputOutput.inputNotValid();
						this.nextMove = inputOutput.moveGoat();
					}
					playerBoard.moveGoat(this.nextMove);
				}
			}
			else //Wenn Tiger am Zug
			{
				this.nextMove = inputOutput.moveTiger();
				while ((!playerBoard.checkValidCordsMove(this.nextMove)) || (playerBoard.isPlayerMoveValid(this.nextMove)==0)) //Pr�fe, ob Zug valid ist
				{
					inputOutput.inputNotValid();
					this.nextMove = inputOutput.moveTiger();
				}
				playerBoard.moveTiger(this.nextMove);
			}
		}	
	}
	
	/**
	 * Pr�ft, ob ein Spieler das Spiel gewonnen hat oder, ob es fortgesetzt wird.
	 * Spieler T hat gewonnen, wenn 5 Ziegen gefressen (�bersprungen) wurden.
	 * Spieler Z hat gewonnen, wenn Spieler T keinen Tieger mehr bewegen kann.
	 * @return Ob Spiel beendet wurde
	 */
	private boolean checkWon() //Pr�fe, ob das Spiel beendet wurde
	{
		if (playerBoard.getEatenGoats()==5) //Spieler T hat 5 Ziegen gefressen
		{
			inputOutput.generateBoard(playerBoard.getBoard());
			inputOutput.gameOverGoat();
			return true;
		}
		else if (!playerBoard.tigerMoveable()) //Spieler Z hat alle Tiger von Spieler T isoliert
		{
			inputOutput.generateBoard(playerBoard.getBoard());
			inputOutput.gameOverTiger();
			return true;
		}
		else
		{
			return false;
		}
	}
}
