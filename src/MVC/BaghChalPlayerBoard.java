package MVC;


/**
 *Das Spielbrett ist das Modell und somit verantwortlich für die Logik des Spiels.
 *Es enthält außerdem das Spielbrett an sich und die möglichen Aktionen der Spieler.
 *
 * @author Tim Hänlein
 *
 */

public class BaghChalPlayerBoard{

	/**
	 * Das Spielbrett mit allen Spielfiguren und entsprechender Position.
	 * 0: enspricht keiner Spielfigur, also ein leeres Feld.
	 * 1: entspricht einer Ziege.
	 * 2: entspricht einem Tiger.
	 */
	private int [][] board;
	/**
	 * Koordinaten, die den nächsten Spielzug angeben in einem Array als Integerwerte gespeichert.
	 * {{starty-startx},{ziely-zielx}}
	 */
	private int [][] coordinates;
	/**
	 * Anzahl der vom Tiger gegessenen Ziegen. Kann maximal den Wert 5 annehmen. Ist bei Beginn 0.
	 */
	private int eatenGoats;
	/**
	 * Anzahl der noch zu setzenden Ziegen. Ist zu Beginn 20. Wird auf 0 heruntergezählt.
	 */
	private int goatsLeft;
	/**
	 * Beschreibt welcher Spieler gerade am Zug ist.
	 * true: Spieler Z ist am Zug
	 * false: Spieler T ist am Zug
	 */
	private boolean playersTurn;
	/**
	 * 25x25 Matrix, die beschreibt, ob Züge potentiell ausführbar sind.
	 * 0: Zug ist nicht ausführbar
	 * 1: Spielfigur kann potentiell auf ein Nachbarfeld bewegt werden
	 * 2: Spielfigur kann potentiell ein Feld überspringen 
	 */
	private static int [] [] moves =
									{
									{0, 1, 2, 0, 0, 1, 1, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{1, 0, 1, 2, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{2, 1, 0, 1, 2, 0, 1, 1, 1, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 2, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 2, 1, 0, 0, 0, 0, 1, 1, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{1, 0, 0, 0, 0, 0, 1, 2, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
									{1, 1, 1, 0, 0, 1, 0, 1, 2, 0, 1, 1, 1, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0},
									{0, 0, 1, 0, 0, 2, 1 ,0, 1, 2, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 1, 1, 1, 0, 2, 1, 0, 1, 0, 0, 1, 1, 1, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 1, 0, 0, 2, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
									{2, 0, 2, 0, 0, 1, 1, 0, 0, 0, 0, 1, 2, 0, 0, 1, 1, 0, 0, 0, 2, 0, 2, 0, 0},
									{0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 2, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0},
									{2, 0, 2, 0, 2, 0, 1, 1, 1, 0, 2, 1, 0, 1, 2, 0, 1, 1, 1, 0, 2, 0, 2, 0, 2},
									{0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 2, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0},
									{0, 0, 2, 0, 2, 0, 0, 0, 1, 1, 0, 0, 2, 1, 0, 0, 0, 0, 1, 1, 0, 0, 2, 0, 2},
									{0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 2, 0, 0, 1, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 1, 1, 1, 0, 0, 1, 0, 1, 2, 0, 1, 1, 1, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 2, 1, 0, 1, 2, 0, 0, 1, 0, 0},
									{0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 1, 1, 1, 0, 2, 1, 0, 1, 0, 0, 1, 1, 1},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 2, 1, 0, 0, 0, 0, 0, 1},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 1, 1, 0, 0, 0, 0, 1, 2, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 2, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 2, 0, 1, 1, 1, 0, 2, 1, 0, 1, 2},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 2, 1, 0, 1},
									{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 1, 1, 0, 0, 2, 1, 0}
									};

	/**
	 * Erzeugt ein neues Spielbrett mit Spielfiguren in der Ausgangsposition.
	 */
	
	public BaghChalPlayerBoard ()
	{
		this.board = new int [][] {{2,0,0,0,2},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{2,0,0,0,2}};
		this.coordinates = new int [][] {{-1,-1},{-1,-1}};
		this.eatenGoats=0;
		this.goatsLeft=20;
		this.playersTurn = true;
	}
	
	/**
	 * Gibt aktuelle Spielbrett zurück
	 * @return Positionen der Spielfiguren
	 */
	public int[][] getBoard ()
	{
		return this.board;
	}
	
	/**
	 * Gibt die Koordinaten des nächsten Zuges zurück
	 * @return Start- und Zielkoordinaten des nächsten Zuges
	 */
	public int[][] getCoordinates()
	{
		return this.coordinates;
	}
	
	/**
	 * Gibt die Anzahl der von Spieler T gegessene Ziegen zurück
	 * @return Anzahl der gegessenen Ziegen
	 */
	public int getEatenGoats()
	{
		return this.eatenGoats;
	}
	
	/**
	 * Gibt die Anzahl der noch zu setzenden Ziegen zurück
	 * @return Anzahl der Ziegen im Vorrat
	 */
	public int getGoatsLeft()
	{
		return this.goatsLeft;
	}
	
	/**
	 * Gibt den Spieler, der am Zug ist, zurück
	 * @return aktueller Spieler
	 */
	public boolean getPlayersTurn()
	{
		return this.playersTurn;
	}
	
	private void incEatenGoats()
	{
		this.eatenGoats++;
	}
	
	private void decGoatsLeft()
	{
		this.goatsLeft--;
	}
	
	private void togglePlayer() //Schalte Spieler um
	{
		this.playersTurn = !this.playersTurn;
	}

	/**
	 * Prüft, ob sich irgendein Tiger auf dem Spielbrett bewegen kann.
	 * Wird benötigt, um das Spielende für Spieler T festzustellen.
	 * @return true: Tiger kann sich bewegen. false: Kein Tiger kann sich bewegen
	 */
	public boolean tigerMoveable() //Prüfe, ob sich Tiger bewegen können
	{
		int x,y=0;
		
		for (int[] row:this.board) //Für jede Zeile überprüfen
		{
			x=0;
			for (int col:row) //Für jedes Element jeder Zeile überprüfen
			{
				switch (col)
				{
					case 2: if(checkMoves(x,y))
							{
								return true; //Gibt true zurück, wenn sich ein Tiger bewegen kann
							} break;
				}
				x++;
			}
			y++;
		}
		return false; //Gibt false zurück, wenn sich kein Tiger bewegen kann
	}
	
	private boolean checkMoves(int x, int y)
	{
		int u,v;
		u = y*5+x;
		for (int row=0; row < this.board.length;row++) //Für jede Zeile überprüfen
		{
			for (int col=0;col < this.board[row].length;col++) //Für jedes Element jeder Zeile überprüfen
			{
				v = row*5+col;
				this.coordinates = new int [][] {{y,x},{row,col}};
				switch (moves[u][v])
				{
					case 1: if (this.board[row][col]==0) {return true;} break;
					case 2: if ((this.board[getMiddle()[0]][getMiddle()[1]]==1)&&(this.board[row][col]==0)) {return true;} break;
				}
			}
		}
		return false;
	}
	
	/**
	 * Prüft, ob sich eine Spielfigur zu einem bestimmten Feld bewegen darf.
	 * @param nextMove Gibt den gewünschten Zug vom Spieler an
	 * @return 0: Zug darf nicht durchgeführt werden. 1: Spielfigur darf auf ein benachbartes Feld gesetzt werden. 2: Spielfigur darf ein Feld überspringen.
	 */
	public int isPlayerMoveValid(String nextMove) //Prüfe, ob sich ein Tiger bewegen darf
	{
		int u,v;
		
		decodeCords(nextMove);
		u = this.coordinates[0][0]*5+this.coordinates[0][1];
		v = this.coordinates[1][0]*5+this.coordinates[1][1];
			switch(moves[u][v])
			{
				case 0: return 0; //Zug nicht möglich
				case 1: if (this.board[this.coordinates[1][0]][this.coordinates[1][1]]==0) {return 1;} //Direkte Bewegung möglich
						else {return 0;}
				case 2: if (this.board[getMiddle()[0]][getMiddle()[1]]==1) {return 2;} //Sprung möglich
						else {return 0;}
				default: return 0;
			}
	}
	
	/**
	 * Bewegt einen Tiger von einem Startfeld zu einem Zielfeld, sobald geprüft wurde, ob der Zug durchführbar ist. Entfernt auch Ziegen, falls eine übersprungen wird.
	 * {@link BaghChalPlayerBoard#isPlayerMoveValid(String)}
	 * @param nextMove Gibt den gewünschten Zug von Spieler T an
	 */
	public void moveTiger(String nextMove)
	{
		if(!this.playersTurn)
		{
			switch(isPlayerMoveValid(nextMove))
			{
				case 0: break; //Mache nicht, weil Zug nicht möglich ist
				case 1: if (this.board[this.coordinates[1][0]][this.coordinates[1][1]]==0 && this.board[this.coordinates[0][0]][this.coordinates[0][1]]==2) //Bewege auf Nachbarfeld
							{
								this.board[this.coordinates[1][0]][this.coordinates[1][1]]=2;
								this.board[this.coordinates[0][0]][this.coordinates[0][1]]=0;
								togglePlayer();
							} break; 
				case 2: if (this.board[getMiddle()[0]][getMiddle()[1]]==1 && this.board[this.coordinates[0][0]][this.coordinates[0][1]]==2) //Überspringe Ziege
							{
								this.board[this.coordinates[1][0]][this.coordinates[1][1]]=2;
								this.board[this.coordinates[0][0]][this.coordinates[0][1]]=0;
								this.board[getMiddle()[0]][getMiddle()[1]]=0;
								incEatenGoats();
								togglePlayer();
							} break;
				default: break;
			}
		}
	}
	
	/**
	 * Prüft, ob sich eine Ziege zu einem bestimmten Feld bewegen oder gesetzt werden darf.
	 * @param nextMove Gibt den gewünschten Zug von Spieler Z an
	 * @return true: Zug darf durchgeführt werden. false: Zug darf nicht durchgeführt werden.
	 */
	public boolean isGoatTurnValid(String nextMove) //Prüfe, ob Zug von Spieler Z valid ist
	{
		decodeCords(nextMove);
		if (this.board[this.coordinates[1][0]][this.coordinates[1][1]]==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Setzt eine Ziege aus dem Vorrat auf das Spielbrett, sobald geprüft wurde, ob diese an die gewünschte Postition gesetzt werden darf.
	 * @param nextMove Gibt den gewünschten Zug von Spieler Z an
	 */
	public void setGoat(String nextMove) //Setze Ziege
	{
		if (this.playersTurn)
		{
			if (isGoatTurnValid(nextMove))
			{
				this.board[this.coordinates[1][0]][this.coordinates[1][1]]=1;
				decGoatsLeft();
				togglePlayer();
			}
		}
	}
	
	/**
	 * Bewegt eine Ziege von einem Startfeld zu einem Zielfeld, sobald geprüft wurde, ob der Zug durchführbar ist.
	 * {@link BaghChalPlayerBoard#isGoatTurnValid(String)}
	 * @param nextMove Gibt den gewünschten Zug von Spieler Z an
	 */
	public void moveGoat(String nextMove) //Bewege Ziege
	{
		//decodeCords(nextMove);
		if (this.playersTurn)
		{
			switch (isPlayerMoveValid(nextMove))
			{
			case 0: break;
			case 1: if (this.board[this.coordinates[1][0]][this.coordinates[1][1]]==0 && this.board[this.coordinates[0][0]][this.coordinates[0][1]]==1) 
					{
						this.board[this.coordinates[1][0]][this.coordinates[1][1]]=1;
						this.board[this.coordinates[0][0]][this.coordinates[0][1]]=0;
						togglePlayer();
					}
			case 2: break;
			}
		}
	}
	
	private void decodeCords(String nextMove) //Splitte String an , und jeweils an - um Integer Koordinaten zu erhalten
	{
		String [] temp;
		String [] origin;
		String [] target;
		if (checkValidCordsSet(nextMove)) //Ziege setzen
		{
			origin = new String[] {"-1","-1"};
			target = nextMove.split("-");
		}
		else if (checkValidCordsMove(nextMove)) //Ziege oder Tiger bewegen
		{
			temp = nextMove.split(",");
			origin = temp[0].split("-");
			target = temp[1].split("-");
		}
		else //Ungültige Eingabe
		{
			origin = new String[] {"-1","-1"};
			target = new String[] {"-1","-1"};
		}
		
		this.coordinates = new int [][]{{Integer.parseInt(origin[0]), Integer.parseInt(origin[1])} , {Integer.parseInt(target[0]), Integer.parseInt(target[1])}};
	}
	
	/**
	 * Prüft, ob die Konvention der Eingabe für das Setzen einer Spielfigur eingehalten wurde.
	 * @param nextMove Gibt den gewünschten Zug des Spielers an
	 * @return true: Konvention wurde eingehalten. false: Konvention wurde nicht eingehalten. 
	 */
	public boolean checkValidCordsSet(String nextMove) //Prüfe Konvention der Eingabe zum Setzen einer Ziege
	{
		if (normalizeString(nextMove).matches("([0-4]-[0-4])"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Prüft, ob die Konvention der Eingabe für die Bewegung einer Spielfigur eingehalten wurde.
	 * @param nextMove Gibt den gewünschten Zug des Spielers an
	 * @return true: Konvention wurde eingehalten. false: Konvention wurde nicht eingehalten. 
	 */
	public boolean checkValidCordsMove(String nextMove) //Prüfe Konvention der Eingabe zum Bewegen einer Spielfigur
	{
		if (normalizeString(nextMove).matches("([0-4]-[0-4],[0-4]-[0-4])"))
		{
			return true;
		}
		else
		{
			System.out.println(nextMove);
			return false;
		}
	}
	
	private int[] getMiddle() //Ermittle Spielfigur zwischen Start und Zielkoordinaten, falls diese zwei Felder auseinanderliegen
	{
		return new int[]{(this.coordinates[0][0] + this.coordinates[1][0]) / 2 , (this.coordinates[0][1] + this.coordinates[1][1]) / 2};
	}
	
	private String normalizeString(String nextMove) //Normalisiere String, um Leerzeichen zu ignorieren
	{
		return nextMove.replaceAll("\\s","");
	}
}
