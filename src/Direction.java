// **** CLASSE DIRECTION ****


public enum Direction {
	// ** VALEURS **
	// Types de direction, de 0 à 3
	N, E, S, W;

	// Valeurs d'un déplacement selon la direction
	private static int[] deplI = {-1, 0, 1, 0};
	private static int[] deplJ = {0, 1, 0, -1};

	// ** METHODES **
	// Renvoi d'une direction selon son nombre de rotation
	static Direction valueOf(int i) {
		return values()[i];
	}

	// Récupération du nombre de rotation
	public int getRotation() {
		return this.ordinal();
	}

	// Récupération du déplacement vertical
	public int getDi() {
		return deplI[this.ordinal()];
	}

	// Récupération du déplacement latéral
	public int getDj() {
		return deplJ[this.ordinal()];
	}

	// Récupération de la direction opposée
	public Direction getOpposed() {
		int indice = (this.ordinal() + 2) % 4;
		return Direction.values()[indice];  
	}

	// Réalisation d'une rotation (dans le sens horaire)
	public Direction rotate() {
		return values()[(this.ordinal() + 1) % 4];
	}

	// Réalisation d'une rotation (dans le sens anti horaire)
	public Direction invRotate() {
		int newOrdinal = (this.ordinal() - 1) % 4;
		// Si le résultat est négatif, ajustez-le pour revenir à 3
		if (newOrdinal < 0) {
			newOrdinal = 3;
		}
		return values()[newOrdinal];
	}
}