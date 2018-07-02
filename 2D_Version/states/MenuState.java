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
import main.gameObjects.Couleur;
import main.gfx.Sprite;

/**
 * L'etat du menu affiche au debut du jeu
 * @author Adil Mania - Bastien Rousset
 */

public class MenuState extends BasicGameState {
	
    /**
     * Liste des options
     */
	
    private String[]  menuOptions = new String[] {
            "Jouer",
            "Quitter",
            "Regles"
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
    
    public MenuState( int stateID ) {
        MenuState.stateID = stateID;
    }

    /**
     * Initialiser l'etat du menu
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
        if ( input.isKeyPressed( Input.KEY_UP ) ) { // si la touche Fleche-Haut est appuiye
            index--;
            if ( index == -1 ) { // si on a deborde le tableau du haut ...
                index = menuOptions.length - 1; // ... on recommance du bas
            }
        } else if ( input.isKeyPressed( Input.KEY_DOWN ) ) { // si la touche Fleche-Bas est appuiye
            index++;
            if ( index == menuOptions.length ) { // si on a deborde le tableau du bas ...
                index = 0; // ... on recommance du haut
            }
        } else if ( input.isKeyPressed( Input.KEY_ENTER ) ) { // si la touche Entree est appuiye
            if ( index == 0 ) { // premiere option : Jouer
                // On entre l'etat ( State ) du jeu avec des transitions
                sbg.enterState( GameState.stateID, new FadeOutTransition(), new FadeInTransition() );
            } else if ( index == 1 ) { // 2eme option : Quitter
                // Quitter l'application
                System.exit( 0 );
            } else if ( index == 2 ) { // 3eme option : Regles
	            // Afficher les regles du Jeu
	        	sbg.enterState( RulesState.stateID, new FadeOutTransition(), new FadeInTransition() );
	        }
        }
        
    }

    /**
     * Dessiner la logique du menu
     */
    
    @Override
    public void render( GameContainer gc, StateBasedGame sbg, Graphics g ) throws SlickException {

    	Image bgImage = Sprite.getBackground( Couleur.ROUGE );
    	Image logo = Sprite.getlogo();
    	Image logoisima = Sprite.getlogoisima();
    	
        bgImage.draw( 0, 0, Game.WIDTH, Game.HEIGHT );
        logo.draw(gc.getWidth() / 2 - 250,0);
        logoisima.draw(gc.getWidth() / 2 - 250,Game.HEIGHT - 169);
        
        for ( int i = 0; i < menuOptions.length; ++i ) { // On parcourt toutes les options
            if ( i == index ) { // l'option selectionnee ...
                g.setColor( Color.red ); // ... est coloree en rouge
            } else { // les autres
                g.setColor( Color.white ); // sont colorees en blanc
            }
            int step = gc.getHeight() / ( menuOptions.length + 1 ) - 100; // l'espace entre les differents options
            
            /*
             * ----------
             * <step>
             * option1
             * <step>
             * option2
             * <step>
             * ----------
             * On doit donc diviser la hauteur de la fenetre par (le nombre d'options + 1)
             */
            
            // afficher l'option centre
            g.drawString(
                    menuOptions[i],
                    gc.getWidth() / 2 - g.getFont().getWidth( menuOptions[i] ) / 2,
                    220 + step * ( i + 1 ) );
        }
    }

    /**
     * Retourne l'identificateur de l'etat
     */
    
    @Override
    public int getID() {
        return stateID;
    }
}
