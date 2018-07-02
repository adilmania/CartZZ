package cartzz;

/**
 * Classe representant le Plateau du jeu
 * @author Adil Mania - Bastien Rousset
 */

public class Plateau extends Pile {

	/**
	 * Constructeur du Plateau
	 * @param deck : le deck du jeu dont on doit prendre une carte pour quelle soit la 1ere carte du Plateau
	 */
	
	public Plateau(Deck deck) {
		ajouterPremiereCarte(deck);
	}
	
	/**
	 * Permet d'ajouter la premiere carte ( au debut du jeu )
	 * @param deck : le deck d'ou on veut tirer la carte
	 */
	
	private void ajouterPremiereCarte(Deck deck) {
		empiler(deck.premiereCartePlateau());
	}
	
	/**
	 * Retourne une chaine decrivant le plateau - Affichage sur le jeu
	 */
	
	public String toString() {
		String str = "";
		
		if (cartes.isEmpty()) {
			str = "[VIDE]";
		}
		
		for(int i = 0; i < cartes.size(); ++i) {
			
			Carte carte = cartes.get(i);
			
			str = str + i + ") " + carte.toString();
			
			if (i != cartes.size() - 1) {	// si ce n'est pas la derniere iteration
				str = str + "\n";	// ajouter un retour chariot
			}
			
		}
		
		return str;
	}
	
	/**
	 * Permet d'afficher le Plateau - Affiche des cartes jouables en plus de l'option piocher
	 */
	
	public void afficher() {
		System.out.println(this);
	}
	
	/**
	 * Permet d'afficher la carte au sommet du Plateau
	 */
	
	public void afficherSommet() {
		System.out.println("\n-> Sommet du Plateau : " + sommet().toString() + "\n");
	}
	
}
