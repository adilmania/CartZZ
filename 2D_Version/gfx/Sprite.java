package main.gfx;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import main.common.Config;
import main.common.Debug;
import main.gameObjects.Couleur;

/**
 * Cette classe est utilisee pour charger et retourner les sprites / images du jeu
 * @author Adil Mania - Bastien Rousset
 */

public class Sprite {
	
    /**
     * Liste des sprites
     */
	
    public static HashMap<String, Image> sprites = new HashMap<>();


    /**
     * Retourne la sprite de la carte ayant le nom passe en parametre
     * @param spriteName nom de la carte dont on veut afficher
     * @return	l'image de la carte demandee
     * @throws SlickException	exception offerte par la librairie Slick2D
     */
    
    private static Image get( String spriteName ) throws SlickException {
        if ( !sprites.containsKey( spriteName ) )
            Debug.err( "Image null ! " + spriteName );
        return sprites.get( spriteName );
    }


    /**
     * Retourne la sprite de la carte ayant la valeur et la couleur passes en parametres
     * @param valeur	valeur de la carte dont on veut afficher
     * @param couleur	couleur de la carte dont on veut afficher
     * @return	l'image de la carte demandee
     * @throws SlickException	exception offerte par la librairie Slick2D
     */
    
    public static Image get( int valeur, Couleur couleur ) throws SlickException { // exemple : 0-bleu
        String nomCouleur = null;
        switch ( couleur ) {
        case ROUGE:
            nomCouleur = "red";
            break;
        case JAUNE:
            nomCouleur = "yellow";
            break;
        case VERT:
            nomCouleur = "green";
            break;
        case BLEU:
            nomCouleur = "blue";
            break;
        }
        return get( valeur + "-" + nomCouleur );
    }

    /**
     * Retourne la sprite / image grise transparente a ajouter au dessus des cartes non jouables !
     * @return	la sprite / image grise transparente a ajouter au dessus des cartes non jouables !
     * @throws SlickException	exception offerte par la librairie Slick2D
     */
    
    public static Image getInactiveCard() throws SlickException {
        if ( !sprites.containsKey( "inactiveCard" ) )
            Debug.err( "getInactiveCard() null !" );
        return sprites.get( "inactiveCard" );
    }

    /**
     * Retourne la sprite / image de la carte cachee
     * @return	la sprite / image de la carte cachee
     * @throws SlickException	exception offerte par la librairie Slick2D
     */
    
    public static Image getHiddenCard() throws SlickException {
        if ( !sprites.containsKey( "hidden" ) )
            Debug.err( "getHiddenCard() null !" );
        return sprites.get( "hidden" );
    }

    /**
     * Retourne les images du jeu
     */
    
    public static Image getlogo( ) throws SlickException {
        return get( "logo" );
    }
    
    public static Image getlogoisima( ) throws SlickException {
        return get( "logoisima" );
    }
    
    public static Image getlogobde( ) throws SlickException {
        return get( "logobde" );
    }
    
    public static Image getregles( ) throws SlickException {
        return get( "regles" );
    }
    
    /**
     * Retourne la sprite / image de l'arriere-plan du jeu ( selon la couleur passee en parametre )
     * @param couleur	la couleur de l'arriere-plan
     * @return	la sprite / image de l'arriere-plan du jeu ( selon la couleur passee en parametre )
     * @throws SlickException	exception offerte par la librairie Slick2D
     */
    
    public static Image getBackground( Couleur couleur ) throws SlickException {
        String nomCouleur = null;
        switch ( couleur ) {
        case ROUGE:
            nomCouleur = "red";
            break;
        case JAUNE:
            nomCouleur = "yellow";
            break;
        case VERT:
            nomCouleur = "green";
            break;
        case BLEU:
            nomCouleur = "blue";
            break;
        }
        return get( "bg-" + nomCouleur );
    }

    /**
     * Cette methode permet de charger toutes les images du jeu
     * N.B. : TOUTES les images / sprites DOIVENT etre charges a partir du Thread principale ( GL Thread )
     * sinon on va avoir l'exception suivante : java.lang.RuntimeException: No OpenGL context found in the current thread.
     * @throws SlickException	exception offerte par la librairie Slick2D
     */
    
    public static void load() throws SlickException {
    	
        String colorNames[] = { "blue", "green", "red", "yellow" };
        String imageFileName;
        
        for ( String colorName : colorNames ) {
            // cartes chiffres : 0 .. 12
            for ( int i = 1; i <= 12; i++ ) {
                imageFileName = String.format( "%d-%s", i, colorName );
                sprites.put( imageFileName, new Image( Config.get( "imgPath" ) + imageFileName + ".png" ) );
            }
            // sprites / images de l'arriere-plan
            imageFileName = String.format( "bg-%s", colorName );
            sprites.put( imageFileName, new Image( Config.get( "imgPath" ) + imageFileName + ".png" ) );
        }
        
        // carte cachee ( le dos d'une carte )
        imageFileName = "hidden";
        sprites.put( imageFileName, new Image( Config.get( "imgPath" ) + imageFileName + ".png" ) );
        // carte non jouable ( filtre transparent gris )
        imageFileName = "inactiveCard";
        sprites.put( imageFileName, new Image( Config.get( "imgPath" ) + imageFileName + ".png" ) );
        // logo du jeu
        imageFileName = "logo";
        sprites.put( imageFileName, new Image( Config.get( "imgPath" ) + imageFileName + ".png" ) );
        // logo de l'isima
        imageFileName = "logoisima";
        sprites.put( imageFileName, new Image( Config.get( "imgPath" ) + imageFileName + ".png" ) );
        // logo du bde 
        imageFileName = "logobde";
        sprites.put( imageFileName, new Image( Config.get( "imgPath" ) + imageFileName + ".png" ) );
        // regles du Jeu
        imageFileName = "regles";
        sprites.put( imageFileName, new Image( Config.get( "imgPath" ) + imageFileName + ".png" ) );
    }
}
