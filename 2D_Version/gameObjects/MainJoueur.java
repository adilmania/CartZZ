package main.gameObjects;

import java.util.ArrayList;

/**
 * Classe representant la main de chaque joueur
 * @author Adil Mania - Bastien Rousset
 */

public class MainJoueur {

	/**
	 * L'ensemble des cartes dans la main
	 */
	
	public ArrayList<Carte> cartes;

	/**
	 * Constructeur de la main du joueur
	 */
	
	public MainJoueur() {
		cartes = new ArrayList<Carte>();
	}
	

	/**
	 * Permet d'ajouter une carte a la main
	 * @param carte : la carte a ajouter
	 */
	
	public void ajouter(Carte carte) {
		cartes.add(carte);
	}
	

	/**
	 * Permet de retirer une carte de la main
	 * @param num : l'indice de la carte a retirer dans la liste
	 * @return la carte retiree
	 */
	
	public Carte retirer(int num) {
		return cartes.remove(num);
	}

    /**
     * Permet de retirer une carte de la main
     * @param playedCard
     */
    
    public void retirer( Carte playedCard ) {
        cartes.remove( playedCard );
    }
    
	/**
	 * Permet de retourner l'ensemble des cartes dans la main
	 * @return l'ensemble des cartes dans la main
	 */
	
	public ArrayList<Carte> getCartes() {
		return cartes;
	}

	/**
	 * @return le nombre de cartes dans la main
	 */
	
	public int nbCartes() {
		return cartes.size();
	}

    @Override
    public String toString() {
        String str = "";
        for ( Carte carte : cartes ) {
            str = str + carte.toString();
        }
        return str;
    }

}
