package main.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import main.Game;
import main.gfx.Sprite;

/**
 * L'etat des regles du Jeu
 * @author Adil Mania - Bastien Rousset
 */

public class RulesState extends BasicGameState {
	
    /**
     * Liste des options
     */
	
    private String[]  menuOptions = new String[] {
            "Retour"
    };
    
    /**
     * Permet de savoir quelle option on a selectionne ( initialement la premiere )
     */
    
    private int       index       = 0;
    
    /**
     * Identificateur de l'etat ( State )
     */
    
    public static int stateID;

    /**
     * Constructeur
     * @param stateID identificateur de l'etat ( state )
     */
    
    public RulesState( int stateID ) {
        RulesState.stateID = stateID;
    }

    /**
     * Initialiser l'etat de l'interface
     */
    
    @Override
    public void init( GameContainer arg0, StateBasedGame arg1 ) throws SlickException {
        // rien a initialiser !
    }

    @Override
    /**
     * Mettre a jour la logique de l'etat
     */
    
    public void update( GameContainer container, StateBasedGame sbg, int delta ) throws SlickException {
    	
        Input input = container.getInput(); // utilise pour tester l'entree de l'utilisateur ( clavier et souris )
        
        if ( input.isKeyPressed( Input.KEY_ENTER ) ) { // si la touche Entree est appuiye
	            // Retourner a l'etat initial
	        	sbg.enterState( MenuState.stateID, new FadeOutTransition(), new FadeInTransition() );
        }
        
    }

    /**
     * Dessiner la logique de l'interface
     */
    
    @Override
    public void render( GameContainer gc, StateBasedGame sbg, Graphics g ) throws SlickException {

    	Image regles = Sprite.getregles();
    	
        regles.draw( 0, 0, Game.WIDTH, Game.HEIGHT );
      
        for ( int i = 0; i < menuOptions.length; ++i ) { // On parcourt toutes les options
        	
            if ( i == index ) { // l'option selectionnee ...
                g.setColor( Color.red ); // ... est coloree en rouge
            } else { // les autres
                g.setColor( Color.white ); // sont colorees en blanc
            }
            
            int step = gc.getHeight() / ( menuOptions.length + 1 ) - 100; // l'espace entre les differents options
            
            // afficher le button retourner
            g.drawString(
                    menuOptions[i],
                    gc.getWidth() / 2 - g.getFont().getWidth( menuOptions[i] ) / 2 + 500,
                    220 + step * ( i + 1 ) + 200);
        }
    }

    /**
     * Retourner l'identificateur de l'etat
     */
    
    @Override
    public int getID() {
        return stateID;
    }
}
