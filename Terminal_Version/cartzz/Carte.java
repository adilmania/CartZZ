package cartzz;

/**
 * Cette classe regroupe les aspects communs de toutes les cartes
 * @author Adil Mania - Bastien Rousset
 */

public class Carte {
	
	/**
	 * La couleur de la carte
	 * @see Couleur
	 */
	
	protected Couleur couleur;
	public int valeur;
	public Effet effet;
	
	/**
	 * Constructeur - On ajoute la valeur et l'effet a la carte coloree
	 * @param couleur : la couleur de la carte
	 * @param valeur : la valeur de la carte
	 * @param effet : l'effet de la carte
	 */
	
	public Carte(Couleur couleur, int valeur, Effet effet) {
		this.couleur = couleur;
		this.valeur = valeur;
		this.effet = effet;
	}
	
	/**
	 * Permet de retourner la couleur de la carte courante
	 * @return la couleur de la carte courante
	 */
	
	public Couleur getCouleur() {
		return couleur;
	}
		
	/**
	 * Permet de retourner l'effet de la carte a effet
	 * @return l'effet de la carte a effet
	 */
	
	public Effet getEffet() {
		return effet;
	}
	
	/**
	 * Retourne une chaine contenant la representation de la carte (Affichage de la carte)
	 */

	public String toString() {
		String affichage;
		if(effet.getNomEffet() == "NONE")
		{
			affichage = couleur.getValeurCouleur() + " " + valeur ;
		}
		else
		{
			affichage = couleur.getValeurCouleur() + " " + valeur + " " + effet.getNomEffet();
		}
		return affichage;
	}
	
	/**
	 * @return true : si la carte courante et celle passee en parametre sont compatibles ( cad jouable )
	 * @return false : sinon
	 */

	public boolean compatible(Carte carte) {
		return ( carte.couleur == couleur ) || ( (carte.valeur == valeur) );	// meme couleur ou meme valeur
	}
	
	/**
	 * Permet de changer la couleur si un joker est joue
	 * @param couleur : peut etre ROUGE, JAUNE, VERT ou BLEU
	 */
	
	public void setCouleur(Couleur couleur) {
		if (this.valeur == 7) {
			// On ne peut changer la couleur que quand la carte a pour valeur 7
			this.couleur = couleur;
		}
	}
	
}