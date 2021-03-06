package cartzz;

/**
 * Enumeration presentant les couleurs possibles des cartes du jeu Cart'ZZ
 * @author Adil Mania - Bastien Rousset
 */

public enum Couleur {
	
	ROUGE("Rouge"),
	JAUNE("Jaune"),
	VERT("Vert"),
	BLEU("Bleu");
	
	/**
	 * La valeur de la couleur, en fait c'est une chaine de caracteres contenant le nom de la couleur
	 * utile dans la methode toString()
	 */
	
	private String valeurcouleur;
	
	/**
	 * Constructeur de couleur a partir d'une valeur donnee
	 * @param valeur : la valeur de la couleur
	 */
	
	private Couleur(String valeurcouleur) {
		this.valeurcouleur = valeurcouleur;
	}
	
	/**
	 * @return La valeur de la couleur
	 */
	
	public String getValeurCouleur() {
		return valeurcouleur;
	}
}
