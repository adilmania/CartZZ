package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import main.common.Config;
import main.common.Debug;
import main.io.Audio;
import main.states.GameOverState;
import main.states.GameState;
import main.states.MenuState;
import main.states.RulesState;

/**
 * Classe principale, ou se trouve le point d'entree du programme ( fonction main ) 
 * @author Adil Mania - Bastien Rousset
 */

public class Game extends StateBasedGame {
	
    /**
     * Dimensions de la fenetre du jeu
     */
	
    public static int    WIDTH, HEIGHT;
    
    /**
     * Titre de la fenetre du jeu
     */
    
    public static String TITLE;

    /**
     * Constructeur
     * @param title	titre de la fenetre du jeu
     */
    
    public Game( String title ) {
        super( title );
    }

    /**
     * Fonction main, point d'entree du programme
     * @throws SlickException Exception offerte par la librairie Slick2D
     */
    
    public static void main( String[] args ) throws SlickException {
        Config.load(); // Chargement du parametrage du jeu ( du fichier .ini )

        WIDTH = Integer.parseInt( Config.get( "largeur" ) );
        HEIGHT = Integer.parseInt( Config.get( "longueur" ) );
        Audio.musicEnabled = Boolean.parseBoolean( Config.get( "jouerMusique" ) );
        Audio.soundEnabled = Boolean.parseBoolean( Config.get( "jouerSons" ) );

        Audio.load(); // Chargement des sons

        TITLE = Config.get( "title" );

        AppGameContainer app = new AppGameContainer( new Game( TITLE ) );
        app.setDisplayMode( WIDTH, HEIGHT, true ); // largeur=WIDTH, longueur=HEIGHT, plein-ecran?=non
        Debug.log( "WIDTH = " + WIDTH + ", HEIGHT = " + HEIGHT );
        app.setShowFPS( false ); // cacher l'FPS ( affiche par defaut par slick2d )
        app.setTargetFrameRate( 60 ); // 60 FPS ( fixation de l'FPS : Frames Per Second )		
        app.start(); // afficher la fenetre
    }

    @Override
    /**
     * Initialiser la liste des etats du jeu
     */
    
    public void initStatesList( GameContainer gc ) throws SlickException {
        this.addState( new MenuState( 0 ) );	// le premier etat ( State ) est le premier a etre affiche
        this.addState( new GameState( 1 ) );
        this.addState( new GameOverState( 2 ) );
        this.addState( new RulesState( 3 ) );
    }

}
