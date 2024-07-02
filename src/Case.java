// **** CLASSE CASE ****


public class Case {
	// ** VALEURS **
	// Tuyau de la case
	public Pipe model;
	// Quelle est l'entrée de la case ? Par défaut c'est Nord, mais ça change avec la rotation
	public Direction entree = Direction.N;
	// Présence d'eau ? Par défaut c'est vide
	public boolean filled = false;

	// ** CONSTRUCTEURS **
	// Constructeur à partie de l'initiale et du nombre de rotation
	Case(char pipeType, int direction) {
		this.model = Pipe.valueOf(pipeType);
		this.entree =  Direction.valueOf(direction);
	}

	// ** METHODES **
	/* Redéfinition de ToString : 
	 * Affichage de l'initiale du tuyau et du nombre de rotations
	 */ 
	public String toString() {
		if (this.model.getInitiale() == '.') {
			// Affichage des cases vides
			return this.model.getInitiale() + " ";
		} else {
			if(filled) {
				// Si rempli, affichage avec *
				return this.model.getInitiale() + "" + this.entree.getRotation() + "*";
			} else {
				return this.model.getInitiale() + "" + this.entree.getRotation();
			}
		}
	}

	// Copie profonde d'une case
	public Case copy() {
		return new Case(this.model.getInitiale(), this.entree.getRotation());
	}

	// Rotation d'une case (dans le sens horaire)
	public void rotate() {
		this.entree = this.entree.rotate();
	}

	// Rotation d'une case (dans le sens anti horaire)
	public void invRotate() {
		this.entree = this.entree.invRotate();
	}

	// Vérification de l'ouverture dans une direction
	public boolean isOpen(Direction dir) {
		int indice = (dir.ordinal() - this.entree.getRotation()) % 4;
		if (indice < 0) {
			indice = 4 + indice;
		}
		return this.model.isOpen(Direction.values()[indice]);
	}

	// Réinitialisation du courant d'eau
	public void resetFlow() {
		this.filled = false;
	}

	// Activation du courant d'eau
	public void activateFlow() {
		this.filled = true;
	}
}