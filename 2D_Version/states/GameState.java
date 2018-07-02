package main.states;

import javax.swing.UnsupportedLookAndFeelException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import main.GameRunnable;
import main.gameObjects.Jeu;

/**
 * l'etat ( State ) du jeu
 * @author Adil Mania - Bastien Rousset
 */

public class GameState extends BasicGameState {
	
    /**
     * Identificateur de l'etat ( State )
     */
	
    public static int stateID;
    
    /**
     * Objet representant le jeu
     */
    
    Jeu jeu = null;

    /**
     * Constructeur
     * @param stateID identificateur de l'etat ( state )
     */
    
    public GameState( int stateID ) {
        GameState.stateID = stateID;
    }

    /**
     * Initialiser l'etat ( state )
     */
    
    @Override
    public void init( GameContainer gc, StateBasedGame sbg ) throws SlickException {
        try {
            jeu = new Jeu(); // Initialisation du jeu
        } catch ( ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e ) {
            e.printStackTrace();
        }
        // Demarrage du jeu dans un processus ( Thread ) a part
        // On aura besoin de bloquer la logique du jeu jusqu'a obtenir
        // Un clique sur une carte
        Thread thread = new Thread( new GameRunnable( jeu, sbg ) );
        thread.start();
    }

    /**
     * delta : temps en millisecondes passe depuis l'appel precedant de la methode update()
     * par exemple : 2 FPS -> 2 mises a jour par seconde => delta = 500
     * chaque 500 milli-secondes update() est invoquee
     * 60 FPS => 1 second / 60 => 1000 ms / 60 = 16.666..
     */
    
    @Override
    public void update( GameContainer container, StateBasedGame sbg, int delta ) throws SlickException {
        jeu.update( container ); // Mettre a jour le jeu
    }

    /**
     * Dessiner la logique de l'etat ( state ) du jeu
     */
    
    @Override
    public void render( GameContainer container, StateBasedGame arg1, Graphics g ) throws SlickException {
        jeu.render( g );
    }

    /**
     * Retourne l'identificateur de l'etat
     * chaque etat ( state ) possede un identificateur ID unique
     * cet ID est utile dans la commutation entre les differents etats du jeu
     */
    
    @Override
    public int getID() {
        return stateID;
    }
}
