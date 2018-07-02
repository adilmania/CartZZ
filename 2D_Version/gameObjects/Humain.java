package main.gameObjects;

import java.awt.Point;
import java.util.concurrent.CountDownLatch;

import javax.swing.UnsupportedLookAndFeelException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import main.common.Debug;
import main.io.Audio;

/**
 * Cette classe representant un joueur humain
 * @author Adil Mania - Bastien Rousset
 */

public class Humain extends Joueur {

    public Humain( String pseudo, Deck deck, Plateau plateau ) {
        super( pseudo, deck, plateau );
    }

    /**
     * @return le pseudo du joueur courant
     */
    
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return chaine decrivant le joueur en cours
     * le joueur est identifie par son pseudo
     */
    
    @Override
    public String toString() {
        return "[Humain] : " + getPseudo();
    }

    @Override
    public void render( Graphics g ) throws SlickException {
        //this.afficherMain(g);
        super.render( g );
    }

    /**
     * permet au joueur de donner une couleur
     * @return la couleur choisie
     * @throws UnsupportedLookAndFeelException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     * @throws InterruptedException 
     * @throws SlickException 
     */
    
    private Couleur donnerCouleur() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException, InterruptedException, SlickException {
    	
        // Here we should show the dialog and wait for a response from the user
        Jeu.waitForDialogCountDownLatch = new CountDownLatch( 1 ); // used to wait the dialog

        Jeu.dialog.selectedColor = null; // reset chosen color value
        Jeu.dialog.setVisible( true ); // showing dialog

        Debug.log( "Waiting for dialog..." );
        Jeu.waitForDialogCountDownLatch.await(); // hold

        Debug.log( "Dialog is done..." );

        Debug.log( "color chosen : " + Jeu.dialog.selectedColor );
        
        // go to the get color state
        Couleur couleur = Couleur.getStringCouleur( Jeu.dialog.selectedColor );
        
        return couleur;
 
    }

    @Override
    public void jouerTour() throws InterruptedException, ClassNotFoundException, InstantiationException,
    IllegalAccessException, UnsupportedLookAndFeelException, SlickException { 

		playedCard = null;		
		plateau.afficherSommet();

		apioche = 0;
		
		afficherMain(); // Sur la console
		
		if( nbCartesJouables() == 0 )
		{
			System.out.println("Vous avez 0 cartes jouables, vous devez piocher!");
		}
		else
		{
			System.out.println("Vous avez " + nbCartesJouables() + " cartes jouables");
		}

	    // Ici, on doit attendre le joueur jusqu'a qu'il clique sur une carte jouable !
	    Debug.log( "Waiting for a click ..." );

	    Jeu.countDownLatch = new CountDownLatch( 1 ); // reinitialisation
	    Jeu.countDownLatch.await();

	    Debug.log( "Click received ! ..." );

		if( Joueur.apioche == 0 )
		{	
		        
			if ( playedCard.effet == Effet.JOKER ) { // TODO : ce test doit etre deleguee a la classe Jeu
        	// On doit demander une couleur au joueur
            System.out.println( "Vous devez choisir une couleur" );
            Couleur couleur = donnerCouleur();
            ( (Carte) playedCard ).setCouleur( couleur );
            // Changer la couleur de l'arriere-plan
            // la couleur va etre mis a jour automatiquement dans la methode render()
            }
		
		    main.retirer( playedCard ); // remove the card from the player's hand !
		    plateau.empiler( playedCard ); // add it to the discard pile
		    System.out.println( pseudo + " a joue " + playedCard );
					
		}
		
			System.out.println("----------------------------------");
	    }		
   
    /**
     * permet au joueur de jouer une carte
     * @throws InterruptedException 
     * @throws UnsupportedLookAndFeelException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     * @throws SlickException 
     */
  
    @Override
    public void jouerCarte() throws InterruptedException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException, SlickException {

    }

    @Override
    public void update( GameContainer container ) throws SlickException {

        if ( id != Jeu.tour ) {
            return; // ce n'est pas le tour de ce joueur !
        }

        if ( Jeu.input.isMousePressed( Input.MOUSE_LEFT_BUTTON ) ) { // click detected

			int mx = Jeu.input.getMouseX(), my = Jeu.input.getMouseY();			
			Carte carte_deck = deck.sommet();
			carte_deck.updateBounds();			
			Debug.log("Limites du deck : " + carte_deck.bounds);
			
			if ( carte_deck.isClicked(new Point(mx, my)) ) {         	/* Si clique sur deck */
				
                Debug.log( "Waiting for a deck click..." );
                Jeu.countDownLatch.countDown(); // UP : release the block, MUST BE AFTER SETTING THE CARD OR ELSE NullException !
				System.out.println( "Deck clicked !" );
				Debug.log("pioche, update()");
				Audio.playSound("clickSound");
				/* Fonction Piocher */
				Carte c = prendreCarte();
				System.out.println("Vous avez pioche, La carte piochee est : " + c);
			}
			
			else
			{
				
				 Debug.log( "Carte update() / id = " + this.id );
		         
		         for ( int i = main.cartes.size() - 1; i >= 0; --i ) {
		        	 
		           Carte carte = main.cartes.get( i );
		                
		           if ( carte.isClicked( new Point( mx, my ) ) ) { // the click was on one of the cards
		        	   
		              if ( carte.jouable ) { // if the card is playable

                        System.out.println( "on a clique sur une carte jouable !" );
                        this.playedCard = carte; // saving the played card

                        Debug.log( "Waiting for a card click..." );
                        Jeu.countDownLatch.countDown(); // UP : release the block, MUST BE AFTER SETTING THE CARD OR ELSE NullException !
                        Debug.log( "Card click detected!" );

                        System.out.println( carte );        
		                break; // stop propagating the click event !
		                
		                } 
		             else 
		             	{
		                        Debug.log( "This card is not playable !" );
		                        Audio.playSound( "invalidClickSound" );
		                        break; // break the loop ! no need to check for the other cards !
		             	}
		         }
		       }
			}
           
            Debug.log( "==================================================" );
        }
    }

}
