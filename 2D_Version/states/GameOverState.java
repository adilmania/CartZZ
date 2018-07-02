package main.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import main.gameObjects.Jeu;

/**
 * L'etat ( State ) de fin du  jeu
 * @author Adil Mania - Bastien Rousset
 */

public class GameOverState extends BasicGameState {
	
    /**
     * Identificateur de l'etat ( State )
     */
	
    public static int stateID;

    /**
     * Constructeur
     * @param stateID Identificateur de l'etat ( state )
     */
    
    public GameOverState( int stateID ) {
        GameOverState.stateID = stateID;
    }

    /**
     * Initialiser l'etat ( state )
     */
    
    @Override
    public void init( GameContainer arg0, StateBasedGame arg1 ) throws SlickException {
        // rien a initialiser !
    }

    /**
     * Mettre a jour la logique de l'etat
     */
    
    @Override
    public void update( GameContainer container, StateBasedGame sbg, int delta ) throws SlickException {
    	
        Input input = container.getInput(); // utilise pour tester l'entree de l'utilisateur ( clavier et souris )
        
        if ( input.isKeyPressed( Input.KEY_ESCAPE ) | input.isKeyPressed( Input.KEY_N ) ) { // touche echap ou n
            System.exit( 0 ); // Quitter l'application
        }
        
        if ( input.isKeyPressed( Input.KEY_ENTER ) | input.isKeyPressed( Input.KEY_O ) ) { // touche entree ou o
            // Reinitialisation de l'etat du jeu
            sbg.getState( GameState.stateID ).init( container, sbg );
            // Commuter vers l'etat du jeu
            sbg.enterState( GameState.stateID );
        }
        
    }

    /**
     * Dessiner la logique du menu
     */
    
    @Override
    public void render( GameContainer gc, StateBasedGame sbg, Graphics g ) throws SlickException {
    	
        g.setColor( Color.white );
        String message = String.format( "Fin du jeu, %s a gagne !", Jeu.joueurCourant.pseudo );
        
        // Texte centre et au milieu de l'ecran
        int x = gc.getWidth() / 2 - g.getFont().getWidth( message ) / 2, y = gc.getHeight() / 2;
        g.drawString( message, x, y );
        message = "Jouer encore ? [O/N]";
        
        x = gc.getWidth() / 2 - g.getFont().getWidth( message ) / 2;
        g.drawString( message, x, y + 20 );
        
    }

    /**
     * Retourner l'identificateur de l'etat
     */
    
    @Override
    public int getID() {
        return stateID;
    }
}