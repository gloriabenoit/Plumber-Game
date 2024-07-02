import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
//**** CLASSE BOUTON COLORE ****
public class CustomButton extends JButton {
	// ** VALEURS **
	private Color customColor;

	// ** CONSTRUCTEURS **
	public CustomButton(String text, Color initialColor) {
		super(text);
		this.customColor = initialColor;
		setBackground(customColor);
	}
	
	// Permet de changer la taille de la police
	public CustomButton(String text, Color initialColor, int fontSize) {
		super(text);
		setFont(new Font("Arial", Font.BOLD, fontSize));
		this.customColor = initialColor;
		setBackground(customColor);
	}
	
	// ** METHODES **
	public void setCustomColor(Color color) {
        this.customColor = color;
        setBackground(customColor);
        repaint();
    }
}