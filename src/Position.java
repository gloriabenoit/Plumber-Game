// **** CLASSE POSITION ****


public class Position {
	// ** VALEURS **
	private int i;
	private int j;
	
	// ** CONSTRUCTEURS **
	Position(int i, int j){
		this.i = i;
		this.j = j;
	}
	
	// ** METHODES **
	// Déplacement d'une position selon une direction
	public Position travel(Direction dir) {
		return new Position(this.i + dir.getDi(), this.j + dir.getDj());
	}
	
	// Récupération de I
	public int getI() {
		return this.i;
	}
	
	// Récupération de J
	public int getJ() {
		return this.j;
	}
}