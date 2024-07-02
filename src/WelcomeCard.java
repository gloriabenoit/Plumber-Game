import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

//**** CLASSE PANNEAU DE DEPART ****
public class WelcomeCard extends JPanel {
	// ** VALEURS **
	private static Color customColor;

	// ** CONSTRUCTEURS **
	public WelcomeCard(Color color) {
		// Définition de la couleur
		customColor = color;
		// Définir les dimensions de la fenêtre
		setPreferredSize(new Dimension(300, 40)); 
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
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.setColor(Color.WHITE);

		// Centrer le texte dans le rectangle
		String text = "Bienvenue dans le jeu du plombier !";
		FontMetrics metrics = g.getFontMetrics();
		int x = (getWidth() - metrics.stringWidth(text)) / 2;
		int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

		// Affichage du texte
		g.drawString(text, x, y);
	}
}