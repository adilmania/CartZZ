package cartzz;

import java.util.HashMap;

import io.Clavier;
import cartzz.Joueur;

/**
 * Classe representant le jeu, elle est responsable du deroulement du jeu
 * suivie du sens du jeu, de l'activation des effets speciaux, ...
 * @author Adil Mania - Bastien Rousset
 */

public class Jeu {
	
	/**
	 * Le deck
	 */
	
	private Deck deck;
	
	/**
	 * Le plateau
	 */
	
	private Plateau plateau;
	
	/**
	 * Le nombre de joueurs ( doit etre entre 2 et 4 )
	 */
	
	private int nbJoueurs;
	
	/**
	 * La liste des joueurs
	 */
	
	private Joueur[] joueurs;
	
	/**
	 * Le sens du jeu, peut avoir deux valeurs possibles : -1 de droite a gauche, 1 de gauche a droite
	 * [0] + (1) -> [1] ...
	 * [1] + (-1) -> [0] ...
	 */
	
	private int sens = 1;	// par defaut a droite
	
	/**
	 * L'indice du joueur courant, initialement le premier
	 */
	
	private int indiceJoueurCourant = 0;
	
	/**
	 * L'objet Joueur a l'indice indiceJoueurCourant du tableau joueurs
	 */
	
	private Joueur joueurCourant;
	
	/**
	 * Constructeur du Jeu
	 */
	
	public Jeu() {
		
		deck = new Deck(); // On cree un deck
		deck.melanger();
		
		plateau = new Plateau(deck); // On cree un plateau
		
		System.out.println("Entrez le nombre de joueurs: "); // On lit le nombre de joueurs
		nbJoueurs = Clavier.lireEntier(2, 4);
		joueurs = new Joueur[nbJoueurs];
		
		for (int i = 0; i < nbJoueurs; i++) {
			
			String pseudoJoueur;
			boolean pseudoUnique;
			
			do {
				
				System.out.println("Nom du joueur ?"); // Demander les pseudos
				pseudoJoueur = Clavier.lireChaine();
				pseudoUnique = pseudoValide(pseudoJoueur, i);
				
				if (!pseudoUnique) {
					
					System.out.println("Le pseudo <" + pseudoJoueur + "> est deja utilise !");
					
				}
				
			} while (!pseudoUnique);	// le pseudo du joueur doit etre unique !
			
			joueurs[i] = new Joueur(pseudoJoueur, deck, plateau);
			System.out.println("Le joueur <" + joueurs[i].getPseudo() + "> est connecte");
			
		}
		
		for (int i = 0; i < joueurs.length; i++) {	// Distiribution des cartes
			for (int j = 0; j < 4; j++) {	// Chaque joueur va prendre 4 cartes
				joueurs[i].prendreCarte();
			}
			
		}

	}
	
	/**
	 * Permet au joueur de donner une couleur apres avoir joue un joker
	 * @return la couleur choisie
	 */
	
	public Couleur donnerCouleur() {
		
		HashMap<Integer, Couleur> menu = new HashMap<>();
		int i = -1;
		
		for(Couleur couleur : Couleur.values()) { // Affichage des choix de couleur
				i++;
				menu.put(i, couleur);
				System.out.println(i + ") " + couleur.getValeurCouleur());
		}
		
		int choix = Clavier.lireEntier(0, i);	// Le choix doit etre entre 0 et i
		return menu.get(choix);
		
	}
	
	/**
	 * Permet de demarrer le jeu
	 */
	
	public void lancer() {
		
		System.out.println("=== Le jeu commence ===");
		boolean effetSpecial = false;
		
		while (true) { // boucle du jeu
			
			joueurCourant = joueurs[indiceJoueurCourant];
			System.out.println("Tour de " + joueurCourant.getPseudo());
			
			if (effetSpecial && plateau.sommet().effet.getNomEffet() != "NONE") {	
				effetSpecial = false; // booleen qui sert a dire si on a recu un effet special du joueur precedent ou pas
				if ((plateau.sommet()).getEffet() == Effet.PASSER) {	// le joueur courant doit passer son tour
					System.out.println(joueurCourant.getPseudo() + " doit passer son tour -> effet de la carte : " + plateau.sommet().toString());
					System.out.println("----------------------------------");
					joueurSuivant();
					continue;
				}
				
				if ((plateau.sommet()).getEffet() == Effet.PLUS2) {	// le joueur precedant a joue +2
					// le joueur courant doit piocher 2 cartes
					System.out.println(joueurCourant.getPseudo() + " doit piocher 2 cartes et passer son tour -> effet de la carte " + plateau.sommet());
					System.out.println("----------------------------------");
					for (int i = 0; i < 2; i++) {
						joueurCourant.prendreCarte();
					}
					
					// et passer son tour
					joueurSuivant();
					continue;
				}
				
				if ((plateau.sommet()).getEffet() == Effet.PLUS10) {	// le joueur precedant a joue +10
					// le joueur courant doit piocher 10 cartes
					System.out.println(joueurCourant.getPseudo() + " doit piocher 10 cartes et passer son tour -> effet de la carte " + plateau.sommet());
					System.out.println("----------------------------------");
					for (int i = 0; i < 10; i++) {
						joueurCourant.prendreCarte();
					}
					
					// et passer son tour
					joueurSuivant();
					continue;
				}
				
			}
			
			joueurCourant.jouerCarte(); // On joue une carte
				
			if (joueurCourant.nbCartes() == 0) {	// on teste si le joueur courant a vide sa main
				System.out.println(joueurCourant.getPseudo() + " a gagne !");
				break;
			}
			
			// On doit tester la carte inverser et le joker a ce niveau
			
			if (Joueur.apioche == 0 && plateau.sommet().effet.getNomEffet() != "NONE") {	// le joueur courant a joue une carte a effet
				effetSpecial = true; // booleen qui sert a dire si le joueur suivant va recevoir un effet ou pas
				
				if ((plateau.sommet()).getEffet() == Effet.INVERSER) {
					// le joueur courant a inverse le sens
					
					System.out.println(joueurCourant.getPseudo() + " a inverse le sens du jeu");
					sens *= -1;	// la valeur de sens est soit 1 soit -1, on multiplie par -1 pour changer
					System.out.println("----------------------------------");
				}
				
				if ((plateau.sommet()).getEffet() == Effet.JOKER) {
					// le joueur courant a lancer un joker
					// On doit demander une couleur au joueur
					System.out.println("Vous devez choisir une couleur");
					Couleur couleur = donnerCouleur();
					(plateau.sommet()).setCouleur(couleur);
					System.out.println(joueurCourant.getPseudo() + " a choisi la couleur " + couleur);
					System.out.println("----------------------------------");
				}
				
			}

			joueurSuivant(); // Passer au joueur suivant
			System.out.println();
			
		}
		
		System.out.println("=== Fin du jeu ===");
		
	}
	
	/**
	 * Permet de passer au joueur suivant selon le sens du jeu
	 */
	
	private void joueurSuivant() {
		
		// Avancer vers le joueur suivant
		indiceJoueurCourant += sens;
		
		// On doit verifier si l'indice a depasser les bornes du tableau
		if (indiceJoueurCourant < 0) {
			
			indiceJoueurCourant += nbJoueurs;
			// Exemple : 3 joueurs -> [ 0 , 1 , 2 ]
			// 0 + (-1) = -1 < 0 -> -1 + 3 -> 2 ( dernier joueur )
		}
		
		if (indiceJoueurCourant > nbJoueurs - 1) {	
			
			// indice >= nbJoueurs
			indiceJoueurCourant -= nbJoueurs;
			// Exemple : 3 joueurs -> [ 0 , 1 , 2 ]
			// 2 + (1) = 3 > 2 -> 3 - 3 -> 0 ( premier joueur )
			
		}
		
	}

	/**
	 * Permet de verifier si le pseudo entre est unique ou pas
	 * @param pseudoJoueur : le pseudo a verifier
	 * @return true : si le pseudo est valide ( n'est pas utilse deja ! )
	 * @return false : sinon
	 * @param indice : l'indice de la case du tableau a verifer
	 */
	
	private boolean pseudoValide(String pseudoJoueur, int indice) {
		
		if (indice == 0) {	
			
			// Si c'est le premier pseudo a verifier
			return true;
			// Il est valide, aucun autre pseudo le precede !
			
		}
		// On doit verifier les cases 0 jusqu'a indice - 1
		
		for (int j = 0; j < indice; j++) {
			
			// Il faut verifier toutes les cartes qui precedent la case d'indice (indice)
			
			if (joueurs[j].getPseudo().equalsIgnoreCase(pseudoJoueur)) {
				
				// Si on a trouve un joueur ayant le meme pseudo
				return false;	// Le pseudo n'est pas valide
				
			}
			
		}
		
		return true;	// Tous les pseudos precedants sont != que le pseudo a verifier, il est donc valide
	}
	
}
