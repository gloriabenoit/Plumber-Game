
import java.io.File;
import java.util.Scanner;

//**** CLASSE PARSER ****
public class Parser {
	// ** METHODES **
	// Lecture du fichier et retour de la grille réponse correspondante
	public static Grid lire(String fileName) {
		// Ouverture du fichier
		File f = null;
		Scanner sc = null;
		try {
			f = new File(fileName);
			sc = new Scanner(f);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		// Hauteur
		int h = sc.nextInt();
		// Largeur
		int l = sc.nextInt();
		Grid grille = new Grid(h, l);
		// Pour chaque case
		for (int i=0; i<h; i++) {
			for (int j=0; j<l; j++) {
				// Lecture de la lettre et du chiffre
				String s = sc.next();
				// Extraction de la lettre
				char c = s.charAt(0);
				int n;
				if (c == '.') {
					n = 1; // Peu importe sa direction
				} else {
					// Extraction du chiffre
					n = Integer.valueOf(s.charAt(1) + "");
				}
				// Création de la case correspondante dans la grille
				grille.setCase(new Case(c, n), i, j);
			}
		}
		return grille;
	}
}