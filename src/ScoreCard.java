import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

//**** CLASSE PANNEAU DE FIN ****
public class ScoreCard extends JPanel {
	// ** VALEURS **
	private static Color customColor;
	private int score;

	// ** CONSTRUCTEURS **
	public ScoreCard(Color color, int score) {
		this.score = score;
		// Définition de la couleur
		customColor = color;

		// Définir les dimensions de la fenêtre
		setPreferredSize(new Dimension(60, 50));
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
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.setColor(Color.WHITE);

		// Centrer le texte dans le rectangle	
		FontMetrics metrics = g.getFontMetrics();
		int lineHeight = metrics.getHeight();
		int totalHeight = lineHeight * 2; // 2 lignes à afficher

		// Affichage du texte
		String text = "Score";
		int x = (getWidth() - metrics.stringWidth(text)) / 2;
		int y = ((getHeight() - totalHeight) / 2) + metrics.getAscent();
		g.drawString(text, x, y);

		// Affichage du score
		String scoreValue = String.valueOf(score);
		x = (getWidth() - metrics.stringWidth(scoreValue)) / 2;
		y = y + lineHeight; // 20 est l'écart entre les deux lignes
		g.drawString(scoreValue, x, y);

	}
}