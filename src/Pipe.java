// **** CLASSE TUYAU ****


public enum Pipe {
	// ** VALEURS **
	// Types de tuyaux, de 0 à 5
	END, LINE, TURN, FORK, CROSS, EMPTY;

	// Informations sur les ouvertures (ordre : nord, est, sud, ouest)
	private static boolean[][] map =
		{{true, false, false, false},       	// End 
				{true, false, true, false}, 	// Line
				{true, true, false, false}, 	// Turn
				{true, true, true, false},  	// Fork
				{true, true, true, true},		// Cross
				{false, false, false, false}};  // Empty

	// Initiale du tuyau à afficher
	private static char[] initiale = {'E', 'L', 'T', 'F', 'C', '.'};

	// ** METHODES **
	// Renvoi d'un tuyau selon son initiale
	public static Pipe valueOf(char c) {
		for (int i = 0; i < 6; i++) {
			if (c == (initiale[i])) {
				// Si le caractère est valide
				return values()[i];
			}
		}
		// Sinon ne rien retourner
		return null;
	}

	// Récupération de l'initiale du tuyau
	public char getInitiale() {
		return initiale[this.ordinal()];
	}

	// Récupération de l'ordinale du tuyau
	public int getOrdinal() {
		return this.ordinal();
	}

	// Vérification de l'ouverture dans une direction
	public boolean isOpen(Direction dir) {
		return map[this.ordinal()][dir.ordinal()];
	}
}
