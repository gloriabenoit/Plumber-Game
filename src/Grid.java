
import java.util.Random;

// **** CLASSE GRILLE ****
public class Grid {
	// ** VALEURS **
	private int height; 
	private int width; 
	private Case[][] grille;

	// ** CONSTRUCTEUR **
	Grid(int hauteur, int largeur) {
		this.height = hauteur;
		this.width = largeur;
		grille = new Case[hauteur][largeur];
	}

	// ** METHODES **
	// Récupération de la hauteur
	public int getHeight() {
		return height;
	}

	// Récupération de la largeur
	public int getWidth() {
		return width;
	}

	// Récupération du mileu
	public int getCenterI() {
		return this.height / 2;
	}
	public int getCenterJ() {
		return this.width / 2;
	}

	// Récupération d'une case à partir des indices de position
	public Case getCase(int i, int j) {
		return this.grille[i][j];
	}

	// Récupération d'une case à partir d'une position
	public Case getCase(Position p) {
		return getCase(p.getI(), p.getJ());
	}

	// Mélange des directions de chaque case
	public void shuffleGrid() {
		Random random = new Random();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int randRot = random.nextInt(4);
				char c = grille[i][j].model.getInitiale();
				grille[i][j] = new Case(c, randRot);
			}
		}
	}

	// Copie profonde de la grille
	public Grid copy() {
		Grid newGrid = new Grid(height, width);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				newGrid.grille[i][j] = grille[i][j].copy();
			}
		}
		return newGrid;
	}

	// Validité d'une position
	public boolean inGrid(Position pos) {
		return inGrid(pos.getI(), pos.getJ());
	}

	// Validité des indices d'une position
	public boolean inGrid(int i, int j) {
		return i >= 0 && j >= 0 && i < this.height && j < this.width;
	}

	// Rotation d'une case (dans le sens horaire)
	public void rotate(int i, int j) {
		grille[i][j].rotate();
	}

	// Rotation d'une case (dans le sens anti horaire)
	public void invRotate(int i, int j) {
		grille[i][j].invRotate();
	}

	// Déclaration d'une case selon des indices
	void setCase (Case c, int i, int j) {
		grille[i][j] = c;
	}

	// Affichage de la grille
	public String toString() {
		String gridstr = "";
		for (int i=0; i<height; i++) {
			// Pour chaque case, afficher le type de tuyau et son nombre de rotations
			for (int j=0; j<width; j++) {
				gridstr += this.grille[i][j].toString() + " "; 
			}
			// Passer à la ligne à la fin de chaque ligne
			gridstr += "\n";
		}
		gridstr += "\n";
		return gridstr;
	}

	// Suppression du courant d'eau de toute la grille
	public void resetFlow() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				getCase(i, j).filled = false;
			}
		}
	}

	// Initialisation de la propagation depuis le centre
	public void initPropag() {
		resetFlow();
		Position pCentre = new Position(this.getCenterI(), this.getCenterJ());
		propagation(pCentre);

		// Activation de toutes les cases vides
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Case actuelle = this.grille[i][j];
				if (actuelle.model.getInitiale() == '.') {
					actuelle.activateFlow();
				}
			}
		}
	}

	// Propagation récursive
	public void propagation(Position pos) {
		Case curr = getCase(pos);
		if (curr.filled) {
			// Position déjà remplie
			return;
		}
		curr.activateFlow();
		for (Direction dir : Direction.values()) {
			if (curr.isOpen(dir)) {
				Position pnext = pos.travel(dir);
				if (!inGrid(pnext)) {
					// Case ne donne pas sur du vide
					continue;
				}
				Case next = getCase(pnext);
				Direction op = dir.getOpposed();
				if (next.isOpen(op)) {
					// Déplacement sur la case la plus proche
					propagation(pnext);
				}
			}
		}
	}

	// Vérification de la fin du jeu
	public boolean win() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (!getCase(i, j).filled) {
					// Une case n'est pas remplie
					return false;
				}
				Position pos = new Position(i, j);
				Case curr = getCase(i, j);
				for (Direction dir : Direction.values()) {
					if (curr.isOpen(dir)) {
						Position pnext = pos.travel(dir);
						if (!inGrid(pnext)) {
							// Hors de la grille
							return false;
						}
						Case next = getCase(pnext);
						Direction op = dir.getOpposed();
						if ((!next.isOpen(op)) || (next.model.getInitiale() == '.')){
							// Ouverture sur du vide
							return false;
						}
					}
				}
			}
		}
		// Version gagnante de la grille
		return true;
	}
}