package cartzz;

import java.util.ArrayList;

import io.Clavier;

/**
 * Cette classe represente le joueur : ce qu'il possede et ce qu'il peut faire, cad ses proprietes et ses actions
 * @author Adil Mania - Bastien Rousset
 */

public class Joueur {

	/**
	 * La main du joueur contenant toutes ses cartes
	 */
	
	private MainJoueur main;
	
	/**
	 * Le pseudo du joueur
	 */
	
	private String pseudo;
	
	/**
	 * Le Deck : la pile des cartes ou le joueur peut prendre des cartes
	 */
	
	private Deck deck;
	
	/**
	 * Le Plateau : la pile des cartes ou les joueurs depose leurs cartes
	 */
	
	private Plateau plateau;

	/**
	 * apioche : booleen indiquant si le joueur a pioche ou pas
	 */
	
	public static int apioche;
	
	/**
	 * Contructeur du Joueur
	 * @param pseudo : pseudo du joueur
	 * @param deck : Le Deck
	 * @param plateau : Le Plateau
	 */
	
	public Joueur(String pseudo, Deck deck, Plateau plateau) {
		this.pseudo = pseudo;
		this.deck = deck;
		this.plateau = plateau;
		main = new MainJoueur();
	}
	
	/**
	 * Permet de prendre une carte du deck et l'ajouter a la main du joueur
	 * @return la carte tiree
	 */
	
	public Carte prendreCarte() {

		if (deck.nbCartes() == 0) {	// le deck est vide !
			
			System.out.println("Le deck est vide !");
			// On remet toutes les cartes du plateau (sauf le sommet) dans le deck et on melange le deck
			Carte sommetplateau = plateau.depiler();
		
			while (plateau.nbCartes() != 0) {
				deck.empiler(plateau.depiler());// cette boucle va vider le plateau dans le deck
			}
			
			deck.melanger();
			plateau.empiler(sommetplateau);	
			// remettre le sommet du plateau
			
		}

		Carte carte = deck.depiler();
		main.ajouter(carte);
		
		return carte;
		
	}
	
	/**
	 * Permet d'afficher les cartes dans la main du joueur courant
	 */
	
	public void afficherMain() {
		
		String str = "";
		ArrayList<Carte> cartes = main.getCartes();
		
		if (cartes.isEmpty()) {
			str = "[VIDE]";
		}
		
		for(int i = 0; i < cartes.size(); ++i) {
			
			Carte carte = cartes.get(i);
			str = str + i + ") " + carte.toString();
			
			if (carte.compatible(plateau.sommet())) {
				str = str + " [Jouable]";
			}
			
			if (i != cartes.size() - 1) {	// Si ce n'est pas la derniere iteration
				str = str + "\n";	// Ajouter un retour chariot
			}
			
		}
		
		str = str + "\n99) Piocher \n";
		System.out.println(str);
		
	}
	
	/**
	 * Permet au joueur de jouer son tour, soit par jouer une carte ou piocher
	 */
	
	public void jouerCarte() {
		
		plateau.afficherSommet();
		afficherMain();
		
		boolean carteJouable = false; // Initialisation des parametres
		int num = -1;
		apioche = 0;
		ArrayList<Carte> cartes = main.getCartes();
		
		if( nbCartesJouables() == 0 ) // Affichage d'un message
		{
			System.out.println("Vous avez 0 cartes jouables, vous devez piocher!");
		}
		else
		{
			System.out.println("Vous avez " + nbCartesJouables() + " cartes jouables");
		}
		
		while (!carteJouable && num != 99) {	// Tant que la carte n'est pas jouable & qu'on a pas pioche
			
			num = Clavier.lireEntier(cartes);

			if( num == 99 ) // Si on a decide de piocher
			{
				Carte c = prendreCarte();
				apioche = 1;
				System.out.println("Vous avez pioche, La carte piochee est : " + c);
			}
			else
			{
				Carte carteAjouer = cartes.get(num);
				if (!carteAjouer.compatible(plateau.sommet())) // Si la carte ne peut pas etre jouee
				{
					System.out.println(carteAjouer + " ne peut pas etre jouee sur " + plateau.sommet());
				}
				else
				{
					carteJouable = true; // Si on a joue une carte jouable
					Carte carte = main.retirer(num);
					plateau.empiler(carte);
					System.out.println(pseudo + " a joue " + carte);
				}
			}
		}
		System.out.println("----------------------------------");
	}		
				
			
	/**
	 * Cette methode doit etre privee ! seul le joueur doit connaitre combien il a de cartes jouables !
	 * @return le nombre de cartes jouables cad : compatibles avec le sommet du plateau 
	 */
	
	private int nbCartesJouables() {
		
		int n = 0;
		ArrayList<Carte> cartes = main.getCartes();
		
		if (cartes.isEmpty()) { // Si on a plus de cartes
			return 0;
		}
		
		Carte sommetplateau = plateau.sommet();
		
		for(int i = 0; i < cartes.size(); ++i) { // Calcul du nombre de cartes jouables
			Carte carte = cartes.get(i);
			if (carte.compatible(sommetplateau)) {
				n++;
			}
		}
		
		return n;
		
	}
	
	/**
	 * Contrairement a nbCartesJouables(), cette fonction doit etre publique
	 * les autres joueurs peuvent voir combien vous avez de cartes dans la main
	 * @return le nombre de cartes que possede le joueur dans sa main
	 */
	
	public int nbCartes() {
		return main.nbCartes();
	}

	/**
	 * @return Le pseudo du joueur courant
	 */
	
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * @return Chaine decrivant le joueur en cours
	 * Le joueur est identifie par son pseudo
	 */
	
	public String toString() {
		return getPseudo();
	}
		
}
