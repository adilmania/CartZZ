package cartzz.test;

import cartzz.Joueur;
import cartzz.Deck;
import cartzz.Plateau;

public class TestJoueur { // Tester l'execution d'un Tour

	public static void main(String[] args) {
		
		Deck deck = new Deck(); // Initialisation Deck/Plateau
		deck.melanger();
		Plateau plateau = new Plateau(deck);
		
		int nbJoueurs = 2; // Initialisation des Joueurs
		Joueur[] joueurs = new Joueur[nbJoueurs];
		
		for (int i = 1; i <= nbJoueurs; i++) {
			joueurs[i - 1] = new Joueur("j" + i, deck, plateau);
		}
		
		for (int i = 0; i < joueurs.length; i++) {
			for (int j = 0; j < 4; j++) {	// Chaque joueur va prendre 4 cartes
				joueurs[i].prendreCarte();
			}
		}
		
		int direction = 1;	//	Peut etre soit 1 soit -1
		int indice = 0;	// Indice du joueur qui a le tour de jouer
		Joueur joueurCourant;
		
		while (true) { // Tant que le jeu n'est pas fini
			
			joueurCourant = joueurs[indice];
			System.out.println("Tour de " + joueurCourant.getPseudo());
			joueurCourant.jouerCarte();
			
			if (joueurCourant.nbCartes() == 0) {
				System.out.println(joueurCourant.getPseudo() + " a gagne !");
				break;
			}
			
			// Avancer vers le joueur suivant
			
			indice += direction;
			
			if (indice < 0) {
				indice += nbJoueurs;
			}
			if (indice > nbJoueurs - 1) {	// indice >= nbJoueurs
				indice -= nbJoueurs;
			}
			
			System.out.println();
			
		}
		
		System.out.println("=== Fin de la partie ===");
		
	}
	
}
