//**** CLASSE JEU ****


public class Game {
	// ** VALEURS **
	Grid grille;
	private static Grid soluce;
	private boolean fin = false;
	private String[] filenames;
	private int niveau;

	// Retour arrière
	private Position[] coupPrecedent = new Position[5];
	private int indiceCoup = 0;
	public boolean possibleUndo = false;

	// ** CONSTRUCTEURS **
	Game(String[] filenames, int niveau){
		this.filenames = filenames;
		this.niveau = niveau;

		// Lecture du fichier solution
		grille = Parser.lire(filenames[niveau]);

		// Sauvegarde de la solution
		soluce = grille.copy();
		soluce.initPropag();

		// Mélange initial des cases
		grille.shuffleGrid();

		// Activation initiale
		grille.initPropag();
	}

	// ** METHODES **
	// Accesseurs des niveaux
	// Récupération du nom du fichier de niveau
	String[] getFilenames() {
		return this.filenames;
	}

	// Récupération du numéro de niveau
	int getLevel() {
		return this.niveau;
	}

	// Jouer un tour
	public int play(int i, int j) {
		/* return 3 : jeu déjà fini
		 * return 2 : première fin
		 * return 1 : tour joué
		 * return 0 : case vide
		 * return -1 : hors du plateau
		 */

		// Si le plateau n'est pas déjà rempli
		if (grille.win() != true) {
			// Vérification des coordonnées
			if ((i < 0) || (j < 0) || (i >= grille.getHeight()) || (j >= grille.getWidth())) {
				// Si on est hors du plateau
				return -1;
			} else {
				if (grille.getCase(i, j).model.getInitiale() == '.'){
					// Si la case est vide
					return 0;
				}
				// Tour de jeu
				grille.rotate(i, j);
				grille.initPropag();

				// Stockage du coup effectué
				coupPrecedent[indiceCoup] = new Position(i, j);
				indiceCoup++;
				possibleUndo = true;
			}
		} if ((grille.win() == true) && (!fin)){
			// Si le plateau est rempli pour la première fois
			fin = true;
			possibleUndo = false;
			return 2;
		} else if ((grille.win() == true) && (fin)) {
			// Si le jeu est déjà fini
			return 3;
		}
		// Traitement si on dépasse la capacité
		if (indiceCoup == coupPrecedent.length - 1) {
			int taille = 2 * coupPrecedent.length;
			Position[] contentdouble = new Position[taille];
			// Mise à jour
			for (int c = 0; c < coupPrecedent.length; c++) {
				contentdouble[c] = coupPrecedent[c];
			}
			coupPrecedent = contentdouble;
		}
		return 1;
	}

	// Retour arrière
	public boolean undo() {
		// Si retour arrière impossible
		if (indiceCoup == 0) {
			possibleUndo = false;
		} else {
			grille.invRotate(coupPrecedent[indiceCoup-1].getI(), coupPrecedent[indiceCoup-1].getJ());
			indiceCoup--;
			// Mise à jour de la propagation
			grille.initPropag();
			// Si prochain retour arrière impossible
			if (indiceCoup == 0) {
				possibleUndo = false;
			}
		}
		return possibleUndo;
	}

	// Effacer l'historique des coups
	public void resetHistory() {
		indiceCoup = 0;
	}

	// Récupérer la solution
	public void solution() {
		grille = soluce.copy();
		grille.initPropag();
		fin = true;
		possibleUndo = false;
	}

	// Recommencer le niveau
	public void restart() {
		grille.shuffleGrid();
		grille.initPropag();
		fin = false;
		possibleUndo = false;
	}
}