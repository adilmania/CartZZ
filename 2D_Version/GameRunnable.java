package main;

import org.newdawn.slick.state.StateBasedGame;

import main.gameObjects.Jeu;

/**
 * La classe qui va contenir le code a executer par le processus / Thread lancee
 * du processus principale ( lancement du jeu )
 * @author Adil Mania - Bastien Rousset
 */

public class GameRunnable implements Runnable {
	
    /**
     * objet representant le jeu
     */
	
    Jeu            jeu = null;
    
    /**
     * l'orchestrateur des etats du jeu
     * avec cet objet on peut changer d'un etat a un autre
     */
    
    StateBasedGame sbg = null;

    /**
     * constructeur
     * @param jeu
     * @param sbg
     */
    
    public GameRunnable( Jeu jeu, StateBasedGame sbg ) {
        this.jeu = jeu;
        this.sbg = sbg;
    }

    /**
     * le code a executer par le Thread
     */
    
    @Override
    public void run() {
        try {
            jeu.lancer( sbg ); // lancer le jeu
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
