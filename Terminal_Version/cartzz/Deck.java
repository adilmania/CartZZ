package cartzz;

/**
 * Classe representant la pioche du jeu (le deck du jeu)
 * @author Adil Mania - Bastien Rousset
 */

public class Deck extends Pile {

	/**
	 * Constructeur : permet de construire le deck et d'y inserer toutes les cartes necessaires
	 */

	public Deck() {
		for(Couleur couleur : Couleur.values()) {
			// 1 Cartes 1..12 pour chaque couleur
			for(int i = 1; i <= 12; i++) {
				switch (i)
				{
				  case 1:
					if( couleur.getValeurCouleur() == "Bleu" )
					{
						ajouter(new Carte(couleur, i, Effet.PLUS10));
					}
					else
					{
						ajouter(new Carte(couleur, i,  Effet.NONE));
					}
				    break;
				  case 2:
					  ajouter(new Carte(couleur, i, Effet.PLUS2));
				    break;
				  case 7:
					  ajouter(new Carte(couleur, i, Effet.JOKER));
				    break;
				 case 10:
					  ajouter(new Carte(couleur, i, Effet.PASSER));
					    break;
				 case 12:
					  ajouter(new Carte(couleur, i, Effet.INVERSER));
					    break;
				  default:
					  ajouter(new Carte(couleur, i, Effet.NONE));
				}
			}
		}
	}

	/**
	 * Cette methode est utilisee pour retourner une carte aleatoirement dans le Deck
	 * dans le cas ou la premiere carte est une carte a effet ( au debut du jeu )
	 * @param carte
	 */
	
	private void retournerCarte(Carte carte) {
		int i = rand.nextInt(cartes.size());	// Entier aleatoire entre 0 et cartes.size() - 1
		cartes.add(i, carte);
	}
	
	/**
	 * cette methode est appelee par le plateau pour qu'elle lui retourne sa premiere carte
	 * la carte ne doit pas etre a effet
	 * @return
	 */
	
	public Carte premiereCartePlateau() {
		Carte carte;
		while (true) {
			carte = depiler();	// Retirer une carte
			if (carte.effet.getNomEffet() != "NONE") {	// C'est une carte a effet
				// Il faut dans ce cas la rajouter aleatoirement dans le deck
				retournerCarte(carte);
			} else {
				return carte;
			}
		}
	}


}
