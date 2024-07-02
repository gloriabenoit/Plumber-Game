import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Window;

//**** CLASSE PANNEAU DE FIN ****
public class EndCard extends JPanel {
	// ** VALEURS **
	private static Color customColor;
	private static int currentLevel;
	private static int lastLevel;
	private int score;

	// ** CONSTRUCTEURS **
	public EndCard(Color color, String[] filesList, int currentLevel, int score) {
		this.score = score;
		// Définition de la couleur
		customColor = color;
		// Spécification du numéro du niveau actuel
		EndCard.currentLevel = currentLevel;
		// Spécification du numéro du dernier niveau
		lastLevel = filesList.length - 1;
		
		// Définir les dimensions de la fenêtre
		setPreferredSize(new Dimension(250, 60));
		if (currentLevel == lastLevel) {
			setPreferredSize(new Dimension(350, 60));
		}

		this.setLayout(new BorderLayout());

		// Nom du bouton selon le niveau regardé
		String buttonName = "Prochain niveau";
		if (currentLevel == lastLevel) {
			buttonName = "Retour au menu";
		}

		// Ajout d'un bouton niveau suivant	OU retour au menu
		CustomButton next = new CustomButton(buttonName, Color.WHITE);
		this.add(next, BorderLayout.SOUTH);

		// Création de l'écoute
		ActionListener readNext = new ActionListener () {
			/* Redéfinition de actionPerformed : 
			 * Action lors du clic du bouton
			 */ 
			public void actionPerformed (ActionEvent e) {
				// Fermer la fenêtre du jeu actuel
				Window window = SwingUtilities.windowForComponent(EndCard.this);
				window.dispose();

				// Si ce niveau est le dernier
				if (currentLevel == lastLevel) {
					System.out.println ("Dernier niveau, retour au menu.");
				} else {
					// Ouvrir la fenêtre du niveau suivant
					JFrame frame = new JFrame();
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					//Ajout à la fenêtre d'une nouveau plateau pour le niveau correspondant
					frame.getContentPane().add(new Board(new Game(filesList, currentLevel + 1), color, score));
					// Ajustement de la fenêtre aux dimensions de son panneau interne
					frame.pack();
					// Montée de la fenêtre à l'écran
					frame.setVisible(true);
					System.out.println("Niveau " + (currentLevel + 2));
					System.out.println ("Prochain niveau.");
				}
			}
		};
		// Cablage du bouton à son écoute
		next.addActionListener(readNext);
	}

	// ** METHODES **
	/* Redéfinition de paintComponent : 
	 * Affichage du contenu de la fenêtre
	 */ 
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Affichage de la fenêtre
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

		g.setColor(customColor);
		g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);

		// Définition du type de texte
		g.setFont(new Font("SansSerif", Font.PLAIN, 14));
		g.setColor(Color.BLACK);

		// Nom du bouton selon le niveau regardé
		String text = "Bravo! Vous avez reussi le niveau.";
		if (currentLevel == lastLevel) {
			if (score == lastLevel+1) {
				text = "Bravo! Vous avez obtenu le score maximal.";
			} else {
				text = "Bravo! Vous avez reussi le dernier niveau.";
			}
		}

		// Centrer le texte dans le rectangle
		FontMetrics metrics = g.getFontMetrics();
		int x = (getWidth() - metrics.stringWidth(text)) / 2;
		int y = ((getHeight() - metrics.getHeight()) / 2) + 4;

		// Affichage du texte
		g.drawString(text, x, y);
	}
}