package main.gameObjects;

/**
 * Enumeration representant les differents effets que les cartes du jeu peuvent posseder
 * @author Adil Mania - Bastien Rousset
 */

public enum Effet {

	PASSER("Passer"),
	INVERSER("Inverser"),
	PLUS2("+2"),
	PLUS10("+10"),
	JOKER("Joker"),
	NONE("NONE");
	
	/**
	 * Le nom de l'effet de la carte, en fait c'est une chaine de caracteres contenant le nom de l'effet
	 * utile dans la methode toString()
	 */
	
	private String nomeffet;

	/**
	 * Constructeur
	 * @param nomeffet : le nom de l'effet de la carte
	 */
	
	private Effet(String effet) {
		this.nomeffet = effet;
	}

	/**
	 * @return Le nom de l'effet de la carte
	 */
	
	public String getNomEffet() {
		return nomeffet;
	}
	
}
