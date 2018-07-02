package main.gameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Classe abstraite qui represente un objet du jeu
 * @author Adil Mania - Bastien Rousset
 */

public abstract class GameObject {

    /**
     * positions de l'objet sur l'ecran
     */
	
    public float x    = 0, y = 0;
    
    /**
     * vitesses de deplacement de l'objet
     */
    
    public float velX = 0, velY = 0; // velocity : speed

    /**
     * permet de mettre a jour l'etat de l'objet
     * @param container
     * @throws SlickException 
     */
    
    public abstract void update( GameContainer container ) throws SlickException;

    /**
     * permet d'afficher l'etat actuel de l'objet
     * @param g
     * @throws SlickException
     */
    
    public abstract void render( Graphics g ) throws SlickException;

}
