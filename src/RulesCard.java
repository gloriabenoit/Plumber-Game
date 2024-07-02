import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

//**** CLASSE PANNEAU DES REGLES ****
public class RulesCard extends JPanel {
	// ** VALEURS **
	private static Color customColor;
	private String[] rules = {
			"Le but du jeu est de relier une serie de tuyaux de",
			"maniere a ce qu'ils soient tous en contact, ",
			"sans qu'aucun ne soit ouvert sur la bordure du plateau.",
			"",
			"Le centre du plateau doit etre en contact avec tous les",
			"autres tuyaux. Cela impliquera un changement de",
			"couleur : les tuyaux en contact sont rouge, sinon blanc.",
			"",
			"Appuyez sur les tuiles pour les faire tourner."
	};

	// ** CONSTRUCTEURS **
	public RulesCard(Color color) {
		// Définition de la couleur
		customColor = color;
		// Définir les dimensions de la fenêtre
		setPreferredSize(new Dimension(450, 250)); 
	}

	// ** METHODES **
	/* Redéfinition de paintComponent : 
	 * Affichage du contenu de la fenêtre
	 */ 
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Affichage de la fenêtre
		g.setColor(customColor);
		g.fillRect(1, 1, getWidth(), getHeight());

		// Définition du type de texte
		g.setFont(new Font("Arial", Font.BOLD, 13));
		g.setColor(Color.WHITE);

		// Position centrée de x
		String longueurMax = rules[0]; 
		// Parcourir le tableau pour trouver la longueur maximale
		for (int i = 1; i < rules.length; i++) {
			if (rules[i].length() > longueurMax.length()) {
				longueurMax = rules[i];
			}
		}

		// Centrer le texte dans le rectangle	
		FontMetrics metrics = g.getFontMetrics();
		int lineHeight = metrics.getHeight();
		int totalHeight = lineHeight * rules.length; // Nombre de lignes à afficher
		int x = (getWidth() - metrics.stringWidth(longueurMax)) / 2;
		int y = ((getHeight() - totalHeight) / 2) + metrics.getAscent();

		// Affichage de chaque ligne de texte
		for (String rule : rules) {
			g.drawString(rule, x, y);
			y += lineHeight; // Augmente la position y pour la ligne suivante
		}
	}
}