package MVC;


/**
 *Das Spielbrett ist das Modell und somit verantwortlich f�r die Logik des Spiels.
 *Es enth�lt au�erdem das Spielbrett an sich und die m�glichen Aktionen der Spieler.
 *
 * @author Tim H�nlein
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
	 * Koordinaten, die den n�chsten Spielzug angeben in einem Array als Integerwerte gespeichert.
	 * {{starty-startx},{ziely-zielx}}
	 */
	private int [][] coordinates;
	/**
	 * Anzahl der vom Tiger gegessenen Ziegen. Kann maximal den Wert 5 annehmen. Ist bei Beginn 0.
	 */
	private int eatenGoats;
	/**
	 * Anzahl der noch zu setzenden Ziegen. Ist zu Beginn 20. Wird auf 0 heruntergez�hlt.
	 */
	private int goatsLeft;
	/**
	 * Beschreibt welcher Spieler gerade am Zug ist.
	 * true: Spieler Z ist am Zug
	 * false: Spieler T ist am Zug
	 */
	private boolean playersTurn;
	/**
	 * 25x25 Matrix, die beschreibt, ob Z�ge potentiell ausf�hrbar sind.
	 * 0: Zug ist nicht ausf�hrbar
	 * 1: Spielfigur kann potentiell auf ein Nachbarfeld bewegt werden
	 * 2: Spielfigur kann potentiell ein Feld �berspringen 
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
	 * Gibt aktuelle Spielbrett zur�ck
	 * @return Positionen der Spielfiguren
	 */
	public int[][] getBoard ()
	{
		return this.board;
	}
	
	/**
	 * Gibt die Koordinaten des n�chsten Zuges zur�ck
	 * @return Start- und Zielkoordinaten des n�chsten Zuges
	 */
	public int[][] getCoordinates()
	{
		return this.coordinates;
	}
	
	/**
	 * Gibt die Anzahl der von Spieler T gegessene Ziegen zur�ck
	 * @return Anzahl der gegessenen Ziegen
	 */
	public int getEatenGoats()
	{
		return this.eatenGoats;
	}
	
	/**
	 * Gibt die Anzahl der noch zu setzenden Ziegen zur�ck
	 * @return Anzahl der Ziegen im Vorrat
	 */
	public int getGoatsLeft()
	{
		return this.goatsLeft;
	}
	
	/**
	 * Gibt den Spieler, der am Zug ist, zur�ck
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
	 * Pr�ft, ob sich irgendein Tiger auf dem Spielbrett bewegen kann.
	 * Wird ben�tigt, um das Spielende f�r Spieler T festzustellen.
	 * @return true: Tiger kann sich bewegen. false: Kein Tiger kann sich bewegen
	 */
	public boolean tigerMoveable() //Pr�fe, ob sich Tiger bewegen k�nnen
	{
		int x,y=0;
		
		for (int[] row:this.board) //F�r jede Zeile �berpr�fen
		{
			x=0;
			for (int col:row) //F�r jedes Element jeder Zeile �berpr�fen
			{
				switch (col)
				{
					case 2: if(checkMoves(x,y))
							{
								return true; //Gibt true zur�ck, wenn sich ein Tiger bewegen kann
							} break;
				}
				x++;
			}
			y++;
		}
		return false; //Gibt false zur�ck, wenn sich kein Tiger bewegen kann
	}
	
	private boolean checkMoves(int x, int y)
	{
		int u,v;
		u = y*5+x;
		for (int row=0; row < this.board.length;row++) //F�r jede Zeile �berpr�fen
		{
			for (int col=0;col < this.board[row].length;col++) //F�r jedes Element jeder Zeile �berpr�fen
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
	 * Pr�ft, ob sich eine Spielfigur zu einem bestimmten Feld bewegen darf.
	 * @param nextMove Gibt den gew�nschten Zug vom Spieler an
	 * @return 0: Zug darf nicht durchgef�hrt werden. 1: Spielfigur darf auf ein benachbartes Feld gesetzt werden. 2: Spielfigur darf ein Feld �berspringen.
	 */
	public int isPlayerMoveValid(String nextMove) //Pr�fe, ob sich ein Tiger bewegen darf
	{
		int u,v;
		
		decodeCords(nextMove);
		u = this.coordinates[0][0]*5+this.coordinates[0][1];
		v = this.coordinates[1][0]*5+this.coordinates[1][1];
			switch(moves[u][v])
			{
				case 0: return 0; //Zug nicht m�glich
				case 1: if (this.board[this.coordinates[1][0]][this.coordinates[1][1]]==0) {return 1;} //Direkte Bewegung m�glich
						else {return 0;}
				case 2: if (this.board[getMiddle()[0]][getMiddle()[1]]==1) {return 2;} //Sprung m�glich
						else {return 0;}
				default: return 0;
			}
	}
	
	/**
	 * Bewegt einen Tiger von einem Startfeld zu einem Zielfeld, sobald gepr�ft wurde, ob der Zug durchf�hrbar ist. Entfernt auch Ziegen, falls eine �bersprungen wird.
	 * {@link BaghChalPlayerBoard#isPlayerMoveValid(String)}
	 * @param nextMove Gibt den gew�nschten Zug von Spieler T an
	 */
	public void moveTiger(String nextMove)
	{
		if(!this.playersTurn)
		{
			switch(isPlayerMoveValid(nextMove))
			{
				case 0: break; //Mache nicht, weil Zug nicht m�glich ist
				case 1: if (this.board[this.coordinates[1][0]][this.coordinates[1][1]]==0 && this.board[this.coordinates[0][0]][this.coordinates[0][1]]==2) //Bewege auf Nachbarfeld
							{
								this.board[this.coordinates[1][0]][this.coordinates[1][1]]=2;
								this.board[this.coordinates[0][0]][this.coordinates[0][1]]=0;
								togglePlayer();
							} break; 
				case 2: if (this.board[getMiddle()[0]][getMiddle()[1]]==1 && this.board[this.coordinates[0][0]][this.coordinates[0][1]]==2) //�berspringe Ziege
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
	 * Pr�ft, ob sich eine Ziege zu einem bestimmten Feld bewegen oder gesetzt werden darf.
	 * @param nextMove Gibt den gew�nschten Zug von Spieler Z an
	 * @return true: Zug darf durchgef�hrt werden. false: Zug darf nicht durchgef�hrt werden.
	 */
	public boolean isGoatTurnValid(String nextMove) //Pr�fe, ob Zug von Spieler Z valid ist
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
	 * Setzt eine Ziege aus dem Vorrat auf das Spielbrett, sobald gepr�ft wurde, ob diese an die gew�nschte Postition gesetzt werden darf.
	 * @param nextMove Gibt den gew�nschten Zug von Spieler Z an
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
	 * Bewegt eine Ziege von einem Startfeld zu einem Zielfeld, sobald gepr�ft wurde, ob der Zug durchf�hrbar ist.
	 * {@link BaghChalPlayerBoard#isGoatTurnValid(String)}
	 * @param nextMove Gibt den gew�nschten Zug von Spieler Z an
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
		else //Ung�ltige Eingabe
		{
			origin = new String[] {"-1","-1"};
			target = new String[] {"-1","-1"};
		}
		
		this.coordinates = new int [][]{{Integer.parseInt(origin[0]), Integer.parseInt(origin[1])} , {Integer.parseInt(target[0]), Integer.parseInt(target[1])}};
	}
	
	/**
	 * Pr�ft, ob die Konvention der Eingabe f�r das Setzen einer Spielfigur eingehalten wurde.
	 * @param nextMove Gibt den gew�nschten Zug des Spielers an
	 * @return true: Konvention wurde eingehalten. false: Konvention wurde nicht eingehalten. 
	 */
	public boolean checkValidCordsSet(String nextMove) //Pr�fe Konvention der Eingabe zum Setzen einer Ziege
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
	 * Pr�ft, ob die Konvention der Eingabe f�r die Bewegung einer Spielfigur eingehalten wurde.
	 * @param nextMove Gibt den gew�nschten Zug des Spielers an
	 * @return true: Konvention wurde eingehalten. false: Konvention wurde nicht eingehalten. 
	 */
	public boolean checkValidCordsMove(String nextMove) //Pr�fe Konvention der Eingabe zum Bewegen einer Spielfigur
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
