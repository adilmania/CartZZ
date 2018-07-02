package cartzz;
import java.util.Random;

/**
 * Classe representant les fonctionnalites communes entre la Deck et le Plateau (La pile de base)
 * @author Adil Mania - Bastien Rousset
 */

public class Pile extends MainJoueur {
	
	/**
	 * Generateur de valeurs aleatoires
	 */
	
	protected Random rand = new Random();

	/**
	 * Permet de depiler le sommet de la pile
	 * @return la carte supprimee
	 */
	
	public Carte depiler() {
		return cartes.remove(0);
	}
	
	/**
	 * Permet d'empiler une carte
	 * @param carte : la carte a empiler
	 */
	
	public void empiler(Carte carte) {
		cartes.add(0, carte);
	}
	
	/**
	 * Permet de melanger la pile (shuffle) en utilisant l'algorithme : Melange de Fisher-Yates
	 * @see https://fr.wikipedia.org/wiki/M%C3%A9lange_de_Fisher-Yates
	 * academiquement parlant, on ne peut pas melanger une pile ! mais dans ce contexte, la pile n'est
	 * pas une structure FIFO ( First In First Out ) ordinaire, on peut la melanger
	 * c'est une methode commune entre les classes Deck et Plateau
	 */
	
	public void melanger() {
		for(int i = cartes.size() - 1; i > 0; i--) {
			// Permuter une carte aleatoire entre la premiere et la derniere carte de la boucle
			int pick = rand.nextInt(i);	// entier aleatoire entre 0 et i - 1
			Carte randCard = cartes.get(pick);
			Carte lastCard = cartes.get(i);
			cartes.set(i, randCard);
			cartes.set(pick, lastCard);
		}
	}
	
	/**
	 * @return une chaine de carateres representant la pile courante
	 */
	
	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * @return le sommet de la pile
	 */
	
	public Carte sommet() {
		return cartes.get(0);	// Retourne le sommet de la pile ( sans le supprimer )
	}
	
}
