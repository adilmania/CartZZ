package main.gameObjects;

import java.util.Random;

import javax.swing.UnsupportedLookAndFeelException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import main.common.Debug;
import main.io.Audio;

/**
 * Cette classe represente un joueur non-humain
 * On peut enrichir cette classe en implementant des strategies de jeu differentes : 
 * Strategie offensive ( jouer les cartes +2 et +4 en premier ),
 * Defensive ( jouer les cartes +2 et +4 en dernier ),
 * garder la couleur courante,
 * garder les cartes a effet a la fin, ....
 * @author Adil Mania - Bastien Rousset
 *
 */

public class Bot extends Joueur {

    static Random random = new Random(); // utilise pour simuler le comportement aleatoire du joueur

    public Bot( String pseudo, Deck deck, Plateau plateau ) {
        super( pseudo, deck, plateau );
        // TODO Auto-generated constructor stub
    }

    @Override
    public void render( Graphics g ) throws SlickException {
        super.render( g );
    }

    @Override
    public void update( GameContainer container ) throws SlickException {
        // TODO Auto-generated method stub
    }

    @Override
    public void jouerTour() throws InterruptedException, ClassNotFoundException, InstantiationException,
    IllegalAccessException, UnsupportedLookAndFeelException, SlickException { 

		playedCard = null;		
		plateau.afficherSommet();

		apioche = 0;
	
		afficherMain(); // Sur la console
		
		if ( nbCartesJouables() == 0 ) {
			
		    System.out.println( "Vous n'avez pas de cartes jouables ! vous devez piocher !" );
		    
		    Audio.playSound( "noPlayableCardsSound" );

	        Thread.sleep( 5000 ); // Thinks for 2 seconds
	        
		    Carte c = prendreCarte();
		    
		    System.out.println( "La carte piochee est : " + c );
		    
		    if ( !c.compatible( plateau.sommet() ) ) { // la carte recemment piochee n'est pas compatible avec le sommet du talon
		        
		    	System.out.println(
		                "Pas de chance ! vous n'avez encore pas de cartes jouables, vous devez passer le tour" );
		    	
		        System.out.println( "----------------------------------" );
		        
		        Audio.playSound( "hardLuckSound" );
		        
		        return; // passer le tour <=> quitter la methode
		        
		    } else {
		    	
		        afficherMain(); // sur la console
		    }
		}
		else
		{
			jouerCarte();
		}
		
			System.out.println( "----------------------------------" );
			
		}
    
    @Override
    public void jouerCarte() throws SlickException {
        // TODO choose randomly !
    	
        System.out.println( "The bot have " + nbCartesJouables() + " playable cards" );
        Debug.log( "Waiting for the bot's move..." );
        
        try {
            Thread.sleep( 5000 ); // Thinks for 2 seconds
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        
        Carte carte = null;
        int nbEssais = 100;
        int indiceCarte = 0;
        
        do {
        	
            if ( nbEssais > 0 ) {
                nbEssais--;
                indiceCarte = random.nextInt( main.cartes.size() );
            } else {
                indiceCarte = ( indiceCarte + 1 ) % main.cartes.size();
            }
            
            carte = main.cartes.get( indiceCarte );
            
            if ( !carte.jouable ) {
                Debug.log( "la carte " + carte + " n'est pas jouable" );
            }
            
        } while ( !carte.jouable );

        this.playedCard = carte; // saving the played card

        if ( playedCard.effet == Effet.JOKER ) {
            // On doit demander une couleur au joueur
            Debug.log( "Bot doit choisir une couleur..." );
            Couleur couleur = donnerCouleur();
            ( (Carte) playedCard ).setCouleur( couleur );
            // Changer la couleur de l'arriere-plan
            // la couleur va etre mis a jour automatiquement dans la methode render()
            //Jeu.changeBackgroundColorTo(couleur);
        }

        main.retirer( playedCard ); // remove the card from the player's hand !
        plateau.empiler( playedCard ); // add it to the discard pile
        Debug.log( pseudo + " a joue " + playedCard );
    }

    /**
     * @return chaine decrivant le joueur en cours
     * le joueur est identifie par son pseudo
     */
    
    @Override
    public String toString() {
        return "[Bot] : " + pseudo;
    }

    private Couleur donnerCouleur() {
    	
        Debug.log( "On attend le choix de la couleur par le bot..." );
        
        String[] colorNames = { "Jaune", "Vert", "Bleu", "Rouge" };
        String selectedColor = null;
        
        try {
            Thread.sleep( 2000 ); // Thinks for 2 seconds
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        
        selectedColor = colorNames[random.nextInt( colorNames.length )];
        Debug.log( "Le bot a choisi la couleur : " + selectedColor );
        return Couleur.getStringCouleur( selectedColor );
        
    }

}
