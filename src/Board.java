import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//**** CLASSE PLATEAU ****
public class Board extends JPanel implements MouseListener {
	// ** VALEURS **
	// Taille de la fenêtre
	private final int width;
	private final int height;
	// Banque de sprites
	private BufferedImage[][][] sprites;
	// Spécificité du niveau
	private Game Jeu;
	private int lines;
	private int columns;
	private int score;
	// Animation
	private int currentI;
	private int currentJ;
	private double angle;
	private double angleMax;
	private boolean moving;
	private Timer timer;
	private Date dr;
	// Retour arrière
	private boolean possibleUndo = false;
	CustomButton undoButton;
	// Couleur
	private static Color customColor;

	// ** CONSTRUCTEURS **
	public Board(Game Jeu, Color color, int score) {
		// Initialisation
		this.Jeu = Jeu;
		this.width = (Jeu.grille.getWidth() + 2) * 100;
		this.height = (Jeu.grille.getHeight() + 2) * 100;
		lines = Jeu.grille.getHeight();
		columns = Jeu.grille.getWidth();
		customColor = color;
		this.score = score;

		// Récupération des sprites
		getSprites("../img/pipes.png", 3, 5, 120, 20);

		// Réglage des dimensions du panneau
		this.setPreferredSize(new Dimension(width, height));

		// Création du layout
		this.setLayout(new BorderLayout());

		// Création du timer
		timer = new Timer();

		// Ajout de l'écouteur de souris
		addMouseListener(this);

		// Créations des boutons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);

		// Ajout d'un bouton retour menu	
		CustomButton menu = new CustomButton("Menu", Color.WHITE);
		buttonPanel.add(menu);
		// Création de l'écoute
		ActionListener readMenu = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				System.out.println ("Retour au menu.");
				// Fermer la fenêtre du jeu sans quitter l'application
				Window window = SwingUtilities.windowForComponent(Board.this);
				window.dispose();
			}
		};
		// Cablage du bouton à son écoute
		menu.addActionListener(readMenu);

		// Ajout d'un bouton retour arrière		
		CustomButton undo = new CustomButton("Retour arriere", Color.GRAY);
		undoButton = undo;
		buttonPanel.add(undo);
		// Création de l'écoute
		ActionListener readUndo = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if (possibleUndo) {
					undo.setCustomColor(customColor);
					boolean sortie = Jeu.undo();
					System.out.println ("Retour arriere.");
					System.out.println(Jeu.grille.toString());
					if (!sortie) {
						// Prochain retour ne sera pas possible
						possibleUndo = false;
						undo.setCustomColor(Color.GRAY);
					}
				} else {
					// Retour impossible
					possibleUndo = false;
					undo.setCustomColor(Color.GRAY);
				}
				repaint();
			}
		};
		// Cablage du bouton à son écoute
		undo.addActionListener(readUndo);

		// Ajout d'un bouton solution	
		CustomButton soluce = new CustomButton("Solution", customColor);
		buttonPanel.add(soluce);
		// Création de l'écoute
		ActionListener readSolution = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				Jeu.solution();
				repaint();
				possibleUndo = false;
				undoButton.setCustomColor(Color.GRAY);
				System.out.println ("Affichage de la solution.");
			}
		};
		// Cablage du bouton à son écoute
		soluce.addActionListener(readSolution);

		// Ajout d'un bouton redémarrer	
		CustomButton restart = new CustomButton("Redemarrage", customColor);
		buttonPanel.add(restart);
		// Création de l'écoute
		ActionListener readRestart = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				Jeu.restart();
				repaint();
				possibleUndo = false;
				Jeu.resetHistory();
				undoButton.setCustomColor(Color.GRAY);
				System.out.println ("Redemarrage du niveau.");
			}
		};
		// Cablage du bouton à son écoute
		restart.addActionListener(readRestart);

		// Ajout de l'espace dédié aux boutons en haut du plateau
		this.add(buttonPanel, BorderLayout.NORTH);

		// Affichage du score
		JPanel scorePanel = new JPanel();
		scorePanel.setOpaque(false);
		scorePanel.add(new ScoreCard(customColor,score));
		this.add(scorePanel, BorderLayout.EAST);
	}

	// ** METHODES **
	// Stockage des sprites dans un tableau
	public void getSprites(String filename, int lines, int columns, int size, int offset) {		
		BufferedImage base = null;
		try {
			base = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// Répartition des sous-images dans la banque
		sprites = new BufferedImage[lines][columns][4];
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				// Récupération de la sous image
				BufferedImage subImg = 
						base.getSubimage((size + offset) * j,
								(size + offset) * i,
								size, size);
				// Création des versions tournées des images
				for (int r = 0; r < 4; r++) {
					// Rotation depuis le centre
					AffineTransform t = new AffineTransform();
					t.setToQuadrantRotation(r, size/2, size/2);
					// Création de l'image cible
					sprites[i][j][r] = new BufferedImage
							(size, size, BufferedImage.TYPE_INT_ARGB);
					Graphics2D g2 = (Graphics2D) sprites[i][j][r].getGraphics();
					// Copie de la sous-image
					g2.drawImage(subImg, t, null);		    
				}
			}
		}
	}

	/* Redéfinition de paintComponent : 
	 * Affichage du plateau
	 */ 
	protected void paintComponent(Graphics g) {
		// Couleur de fond
		g.setColor(Color.BLACK);
		g.fillRect(0,  0, width, height);

		// Mise à l'échelle pour obtenir des cases de 100x100
		double scale = (double) 100 / 120;
		int x = 0;
		int y = 0;

		// Parse le plateau et affiche la bonne case
		for (int i = -1; i < lines+1; i++) {
			for (int j = -1; j < columns+1; j++) {
				Graphics2D g2 = (Graphics2D) g;
				AffineTransform t = AffineTransform.getTranslateInstance(y, x);
				t.concatenate(AffineTransform.getScaleInstance(scale, scale));

				// Bordure du plateau
				if ((i == -1) || (j == -1) || (i == lines) || (j == columns)) {
					int indexSprite = 0;
					int rotSprite = 0;
					if ((i >= -1) && ((j == -1) || (j == columns))) {
						// Bordures droites et gauches
						indexSprite = (i == -1) ? 1 : (i == lines) ? 1 : 2;
						if (j == -1) {
							rotSprite = (i == -1) ? 0 : (i > -1) ? 3 : 0;
						} else if (j == columns) {
							rotSprite = (i == -1) ? 1 : (i > -1) ? 1 : 0;
							if (i == lines) {
								rotSprite = 2;
							}
						}
					} else if (j >= -1) {
						indexSprite = (j == -1) ? 1 : (j == columns) ? 1 : 2;
						if (i == -1) {
							// Bordures supérieures
							rotSprite = 0;
						} else if (i == lines) {
							// Bordures inférieures
							rotSprite = 2;
						}
					}
					g2.drawImage(sprites[2][indexSprite][rotSprite], t, null);
				} else {
					g2.drawImage(sprites[2][0][0], t, null); // Fond

					// Récupération des informations de la case
					Case actuelle = Jeu.grille.getCase(i, j);
					int nbRot = actuelle.entree.getRotation();
					int tuyau = actuelle.model.getOrdinal();

					// Si la Case est celle en rotation
					if ((i == currentI) && (j == currentJ) && moving) {
						// Mise à jour du nombre de rotation
						nbRot --;
						if (nbRot == -1) {
							nbRot = 3;
						}
						// Ajout de la rotation
						AffineTransform transform = new AffineTransform();
						transform.rotate(Math.toRadians(angle), 60, 60);
						t.concatenate(transform);
					}

					// Case vide
					if (tuyau == 5) {
						y = y + 100;
						continue;
					} else {
						if (actuelle.filled) {
							// Tuyau rouge
							g2.drawImage(sprites[1][tuyau][nbRot], t, null);
						} else {
							// Tuyau blanc
							g2.drawImage(sprites[0][tuyau][nbRot], t, null);
						}
					}
				}
				y = y + 100;
			}
			y = 0;
			x = x + 100;
		}
	}

	/* Redéfinition de update() : 
	 * Suivi de la rotation
	 */ 
	TimerTask update() {
		return new TimerTask() {
			public void run() {
				// Incrémentation de l'angle
				angle += 5.0 ;
				repaint();
				// Arrêt de la rotation
				if (angle > angleMax) {
					moving = false;
					return;
				}
				dr = new Date(dr.getTime() + 5);
				timer.schedule(update(), dr);
			}
		};
	}

	/* Redéfinition de mouseClicked : 
	 * Action lors du clic sur une case
	 */ 
	public void mouseClicked(MouseEvent e) {
		if (moving) {
			return;
		}
		// Obtenir les coordonnées du clic
		int mouseX = e.getX();
		int mouseY = e.getY();
		// Calcul de la ligne et colonne correspondante
		int i = (mouseY / 100) - 1; // 100 étant la hauteur d'une case
		int j = (mouseX / 100) - 1; // 100 étant la largeur d'une case

		// Animation
		currentI = i;
		currentJ = j;
		angle = 0;
		angleMax = (double) angle + 90;
		moving = true;
		dr = new Date(System.currentTimeMillis() + 5);
		timer.schedule(update(), dr);

		// Tour de la case correspondante dans le jeu
		int tour = Jeu.play(i, j);

		// Affichage de l'écran de fin
		if (tour == 2) {
			// Ecran de fin de niveau
			JPanel centerPanel = new JPanel();
			centerPanel.setOpaque(false);
			centerPanel.add(new EndCard(customColor, this.Jeu.getFilenames(), this.Jeu.getLevel(), score+1));
			this.add(centerPanel, BorderLayout.SOUTH);
			this.revalidate();
		}

		// Mise à jour de la couleur si tour valide
		if (tour == 1) {
			possibleUndo = Jeu.possibleUndo;
			undoButton.setCustomColor(customColor);
		}

		// Affichage en console
		showDetails(tour);

		// Mise à jour de l'affichage après le tour
		repaint();
	}

	// Affichage des détails du tour effectué
	public void showDetails(int tour) {
		if (tour == -1) {
			System.out.println("Vous etes hors du plateau.");
		} else if (tour == 0) {
			System.out.println("Cette case est vide.");
		} else if (tour == 1) {
			System.out.println(Jeu.grille.toString());
		} else if (tour == 2) {
			System.out.println("Bravo! Vous avez reussi le niveau.");
		} else if (tour == 3) {
			System.out.println("Vous avez deja reussi a resoudre le niveau.");
		} 
	}

	// Autres méthodes d'écouteurs laissées vides car non nécessaires
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}