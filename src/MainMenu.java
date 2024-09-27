import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// **** CLASSE MENU PRINCIPAL ****
public class MainMenu extends JPanel {
	// ** VALEURS **
	private static final Color lightCustomColor = Color.decode("#d38a5b");
	private static final Color darkCustomColor = Color.decode("#66240a");
	// Score de départ
	private int score = 0;

	// ** CONSTRUCTEURS **
	public MainMenu(String[] filesList) {
		System.out.println ("Ouverture du jeu.");
		// Nombre de niveaux
		int numLevels = filesList.length;
		
		setLayout(new BorderLayout());
		
		// Affichage spécifique selon le nombre de boutons
		int numGrid;
		if (numLevels % 2 == 0) {
			// Nombre pair
			numGrid = numLevels / 2;
		} else {
			// Nombre impair
			numGrid = numLevels / 2 + 1;
		}
		JPanel levelPanel = new JPanel(new GridLayout(numGrid, 2));;
		int border = 20;
		levelPanel.setBorder(new EmptyBorder(border, border, border, border));
		levelPanel.setBackground(lightCustomColor);

		// Création de chaque bouton de niveau
		for (int i = 0; i < numLevels; i++) {
		    int levelNum = i;
		    JPanel levelButtonPanel = new JPanel(new BorderLayout());
		    levelButtonPanel.setBackground(lightCustomColor);
		    CustomButton levelButton = new CustomButton("Niveau " + (i + 1), Color.WHITE);

		    // Ajout d'une bordure autour de chaque bouton
		    int levelBorder = 10;
		    levelButtonPanel.setBorder(new EmptyBorder(levelBorder, levelBorder, levelBorder, levelBorder));

		    // Ajout de l'ouverture du niveau correspondant
		    levelButton.addActionListener(new ActionListener() {
		    	/* Redéfinition de actionPerformed : 
				 * Action lors du clic du bouton
				 */ 
		    	public void actionPerformed(ActionEvent e) {
		            // Création de la fenêtre du niveau
		            JFrame frame = new JFrame();
		            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		            // Ajout à la fenêtre d'une nouveau plateau pour le niveau correspondant
		            System.out.println("Niveau " + (levelNum + 1));
		            frame.getContentPane().add(new Board(new Game(filesList, levelNum), lightCustomColor, score));
		            // Ajustement de la fenêtre aux dimensions de son panneau interne
		            frame.pack();
		            // Montée de la fenêtre à l'écran
		            frame.setVisible(true);
		        }
		    });

		    levelButtonPanel.add(levelButton, BorderLayout.CENTER);
		    levelPanel.add(levelButtonPanel);
		}

		add(levelPanel, BorderLayout.CENTER);
		
		// Ajout des boutons
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		// Ajout du bouton règles
		CustomButton rules = new CustomButton("Regles", darkCustomColor, 15);
		rules.setForeground(Color.WHITE);
		rules.addActionListener(new ActionListener() {
			/* Redéfinition de actionPerformed : 
			 * Action lors du clic du bouton
			 */ 
			public void actionPerformed(ActionEvent e) {
				System.out.println ("Affichage des règles.");
				
				// Création de la nouvelle fenêtre
				JFrame newFrame = new JFrame("Nouvelle Fenetre");
				newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				// Ajout du contenu à la nouvelle fenêtre
				newFrame.getContentPane().add(new RulesCard(lightCustomColor));
				// Ajustement de la taille de la fenêtre en fonction de son contenu
				newFrame.pack();
				// Rendre la nouvelle fenêtre visible
				newFrame.setVisible(true);
			}
		});
		buttonPanel.add(rules);

		// Ajout du bouton quitter
		CustomButton quit = new CustomButton("Quitter", darkCustomColor, 15);
		quit.setForeground(Color.WHITE);
		quit.addActionListener(new ActionListener() {
			/* Redéfinition de actionPerformed : 
			 * Action lors du clic du bouton
			 */ 
			public void actionPerformed(ActionEvent e) {
				System.out.println ("Fermeture du jeu.");
				System.exit(0);
			}
		});
		buttonPanel.add(quit);
		
		add(buttonPanel, BorderLayout.SOUTH);
	}

	// ** METHODES **
	// Ouverture de la fenêtre
	static void runMenu(String[] levelFiles) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Création de la fenêtre principale
				JFrame frame = new JFrame("Plumber Game");
				frame.setSize(new Dimension(600, 500));
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				// Création de l'écran d'accueil
				MainMenu mainMenu = new MainMenu(levelFiles);

				// Utilisation d'un BorderLayout pour organiser les composants dans le JFrame
				frame.setLayout(new BorderLayout());
				// Ajout du label de bienvenue en haut du JFrame
				frame.add(new WelcomeCard(darkCustomColor), BorderLayout.NORTH);
				// Ajout du MainMenu au centre du JFrame
				frame.add(mainMenu, BorderLayout.CENTER);

				// Positionnement de la fenêtre au centre de l'écran
				frame.setLocationRelativeTo(null);
				// Rendre la fenêtre principale visible
				frame.setVisible(true);
			}
		});
	}

	// *** MAIN ***
	public static void main(String[] args) {
		// fichiers de niveaux
		String[] niveaux = {"../data/grille-1.txt", "../data/grille0.txt", "../data/pipe5.p", "../data/pipe9.p", "../data/pipe2.p", "../data/pipe1.p", "../data/pipe4.p",
				"../data/pipe7.p", "../data/pipe8.p", "../data/pipe3.p", "../data/pipe6.p", "../data/pipe0.p"};
		runMenu(niveaux);

	}
}