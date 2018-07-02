package main.gameObjects;

import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import main.Game;
import main.common.Config;
import main.common.Debug;
import main.gfx.Sprite;
import main.io.Audio;
import main.io.GetColorDialog;
import main.states.GameOverState;

/**
 * Classe representant le jeu, elle est responsable du deroulement du jeu
 * suivie du sens du jeu, de l'activation des effets speciales, ...
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
	
	private static int nbJoueurs;
	
	/**
	 * La liste des joueurs
	 */
	
	private Joueur[] joueurs;
	
	/**
	 * Le sens du jeu, peut avoir deux valeurs possibles : -1 de droite a gauche, 1 de gauche a droite
	 * [0] + (1) -> [1] ...
	 * [1] + (-1) -> [0] ...
	 */
	
	private static int sens = 1;	// par defaut a droite
	
    /**
     * L'indice du joueur courant, initialement le premier
     */
	
    static int                   tour  = 0;
    
    /**
     * L'objet Joueur a l'indice indiceJoueurCourant du tableau joueurs
     */
    
    public static Joueur         joueurCourant;
    
    /**
     * Entier qui contient l'identifiant du joueur actuel
     */
    
    static Input                 input = null;
    
    /**
     * Utilise pour arreter le jeu jusqu'a ce que le joueur clique sur une carte !
     */
    
    static CountDownLatch        countDownLatch;
    
    /**
     * Utilise pour attendre la couleur choisie par le joueur
     */
    
    public static CountDownLatch waitForDialogCountDownLatch;
    
    /**
     * Dialogue du choix de la couleur
     */
    
    public static GetColorDialog dialog;

    /**
     * Constructeur
     * 
     * @throws SlickException
     * @throws UnsupportedLookAndFeelException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     */
    
    public Jeu() throws SlickException, ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        init();
    }

    /**
     * Permet de demarrer le jeu
     * @param sbg 
     * @throws InterruptedException 
     * @throws UnsupportedLookAndFeelException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     * @throws SlickException 
     * @throws LWJGLException 
     */
    
    public void lancer( StateBasedGame sbg ) throws InterruptedException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SlickException {
        Audio.playMusic();
        
		System.out.println("=== Le jeu commence ===");
		boolean effetSpecial = false;
		
        while ( true ) { // boucle du jeu
        	
            joueurCourant = joueurs[tour];
            System.out.println( "Tour de " + joueurCourant.pseudo );
            
            // effetSpecial : pour qu'on n'active pas l'effet plus qu'une fois !
            // par exemple : 2 joueurs le premier joue une carte speciale, l'autre n'a pas de
            // cartes jouables, donc, il passe son tour, la, on ne doit pas reactiver l'effet speciale
            // de la carte au sommet du talon
            
            if (effetSpecial && plateau.sommet().effet.getNomEffet() != "NONE") {	
				effetSpecial = false; // booleen qui sert a dire si on a recu un effet special du joueur precedent ou pas
				if ((plateau.sommet()).getEffet() == Effet.PASSER) {	// le joueur courant doit passer son tour
			        Thread.sleep( 1000 ); // Thinks for 1 second
					System.out.println(joueurCourant.pseudo + " doit passer son tour -> effet de la carte : " + plateau.sommet().toString());
					System.out.println("----------------------------------");
					joueurSuivant();
					continue;
				}
				
				if ((plateau.sommet()).getEffet() == Effet.PLUS2) {	// le joueur precedant a joue +2
					// le joueur courant doit piocher 2 cartes
					System.out.println(joueurCourant.pseudo + " doit piocher 2 cartes et passer son tour -> effet de la carte " + plateau.sommet());
					System.out.println("----------------------------------");
			        Thread.sleep( 1000 ); // Thinks for 1 second
					for (int i = 0; i < 2; i++) {
						joueurCourant.prendreCarte();
					}
					
					// et passer son tour
					joueurSuivant();
					continue;
				}
				
				if ((plateau.sommet()).getEffet() == Effet.PLUS10) {	// le joueur precedant a joue +10
					// le joueur courant doit piocher 10 cartes
					System.out.println(joueurCourant.pseudo + " doit piocher 10 cartes et passer son tour -> effet de la carte " + plateau.sommet());
					System.out.println("----------------------------------");
			        Thread.sleep( 1000 ); // Thinks for 1 second
					for (int i = 0; i < 10; i++) {
						joueurCourant.prendreCarte();
					}
					
					// et passer son tour
					joueurSuivant();
					continue;
				}
				
			}
            
            joueurCourant.jouerTour();
            
            Debug.log( "======== Fin du Tour ========" );
            
            // TODO : on doit mettre a jour les cartes des mains des joueurs apres chaque tour ! ( jouabilite ! )
            updatePlayersHands();

            if ( joueurCourant.nbCartes() == 0 ) { // on teste si le joueur courant a vide sa main
                System.out.println( joueurCourant.pseudo + " a gagne !" );
                Audio.playSound( "winSound" );
                break;
            }
            
			// On doit tester la carte inverser a ce niveau
			
			if (Joueur.apioche == 0 && plateau.sommet().effet.getNomEffet() != "NONE") {	// le joueur courant a joue une carte speciale
				effetSpecial = true; // booleen qui sert a dire si le joueur suivant va recevoir un effet special ou pas
				
				if ((plateau.sommet()).getEffet() == Effet.INVERSER) {
					// le joueur courant a inverse le sens
					
					System.out.println(joueurCourant.pseudo + " a inverse le sens du jeu");
					sens *= -1;	// la valeur de sens est soit 1 soit -1, on multiplie par -1 pour changer
					System.out.println("----------------------------------");
				}
		        
			}
            
            joueurSuivant();
            System.out.println();
        }
        System.out.println( "=== Fin du jeu ===" );
        Audio.stopMusic();
        // go to the game over state
        sbg.enterState( GameOverState.stateID );
    }

    /**
     * Mise a jour de la jouabilite des cartes des joueurs
    * @param joueurCourant 
     */
    
    private void updatePlayersHands() {
        for ( Joueur joueur : joueurs ) { // Pour chaque joueur ...
            for ( Carte carte : joueur.main.cartes ) { // On parcourt ses cartes ...
                // Et on les met a jour ( jouabilite avec la carte du sommet du talon )
                carte.jouable = carte.compatible( plateau.sommet() );
            }
        }
    }

    /**
     * Permet de passer au joueur suivant selon le sens du jeu
     */
    
    static void joueurSuivant() {
        // avancer vers le joueur suivant
        tour += sens;
        // On doit verifier si l'indice a depasser les bornes du tableau
        if ( tour < 0 ) {
            tour += nbJoueurs;
            // exemple : 3 joueurs -> [ 0 , 1 , 2 ]
            // 0 + (-1) = -1 < 0 -> -1 + 3 -> 2 ( dernier joueur )
        }
        if ( tour > nbJoueurs - 1 ) { // indice >= nbJoueurs
            tour -= nbJoueurs;
            // exemple : 3 joueurs -> [ 0 , 1 , 2 ]
            // 2 + (1) = 3 > 2 -> 3 - 3 -> 0 ( premier joueur )
        }
    }

    public void update( GameContainer container ) throws SlickException {
        input = container.getInput();
        for ( int i = 0; i < joueurs.length; i++ ) { // mettre a jour l'etat des joueurs
            joueurs[i].update( container );
        }
    }

    /**
     * Permet d'afficher les mises a jour sur le jeu
     * 
     * @param g
     * @throws SlickException
     */
    
    public void render( Graphics g ) throws SlickException {
    	
    	changeBackgroundColorTo( plateau.sommet().couleur );
    	
        for ( int i = 0; i < joueurs.length; i++ ) { // affichage des joueurs
            joueurs[i].render( g );
        }

        deck.render( g );
        plateau.render( g );
    }

    public static void changeBackgroundColorTo( Couleur couleur ) throws SlickException {
    	
    	Image bgImage = Sprite.getBackground( couleur );
    	
        if ( bgImage == null ) {
            Debug.err( "derniere carte jouee noire ?" );
            return;
        }
        
        bgImage.draw( 0, 0, Game.WIDTH, Game.HEIGHT );
    }
    
    public void init() throws SlickException, ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {

        dialog = new GetColorDialog();

        Sprite.load();
        Config.load();
        deck = new Deck();
        deck.melanger();
        plateau = new Plateau( deck );

        // TODO : get nbJoueurs from server

        nbJoueurs = Integer.parseInt( Config.get( "nbJoueurs" ) );
        
        joueurs = new Joueur[nbJoueurs];
        Position positionsJoueurs[] = new Position[nbJoueurs];

        switch ( nbJoueurs ) {
        case 2:
            // we place the players face to face :)
            positionsJoueurs[0] = Position.BAS;
            positionsJoueurs[1] = Position.HAUT;
            break;
        case 3:
            // we place the players like so : right, left, down
            positionsJoueurs[0] = Position.BAS;
            positionsJoueurs[1] = Position.DROITE;
            positionsJoueurs[2] = Position.GAUCHE;
            break;
        case 4:
            // we place the players in the four edges of the screen, making a
            // cercle
            positionsJoueurs[0] = Position.BAS;
            positionsJoueurs[1] = Position.DROITE;
            positionsJoueurs[2] = Position.HAUT;
            positionsJoueurs[3] = Position.GAUCHE;
            break;
        default:
            break;
        }

        // 1er joueur Humain, les autres sont des Bots
        for ( int i = 0; i < nbJoueurs; i++ ) {

            if ( i == 0 ) {
                // first player is humain
                String pseudoJoueur = JOptionPane.showInputDialog( "Salut! Ecrit soit ZZ soit un pseudo: " );
                joueurs[i] = new Humain( pseudoJoueur, deck, plateau );
            } else {
                // the rest are bots
                joueurs[i] = new Bot( "Bot" + i, deck, plateau );
            }

            joueurs[i].position = positionsJoueurs[i];
            joueurs[i].id = i;
        }

        
        for ( int i = 0; i < joueurs.length; i++ ) { // Distirbution des cartes
            for ( int j = 0; j < 4; j++ ) { // Chaque joueur va prendre 4 cartes
                joueurs[i].prendreCarte();
            }
        }

    }

}
