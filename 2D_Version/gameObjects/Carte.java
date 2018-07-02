package main.gameObjects;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import main.common.Debug;
import main.gfx.Sprite;

/**
 * Cette classe regroupe les aspects communs de toutes les cartes
 * @author Adil Mania - Bastien Rousset
 *
 */

public class Carte extends GameObject {

    /**
     * Dimensions de la carte
     */
	
	public static final int WIDTH = 86, HEIGHT = 129;
    /* public static final int WIDTH = 200, HEIGHT = 300; */

    /**
     * Dimensions de la carte du plateau
     */
	
    public static final int WIDTH_PLATEAU = 200, HEIGHT_PLATEAU = 300;
    
    /**
     * La couleur de la carte
     * @see Couleur
     */
    
    public Couleur couleur;
	public int valeur;
	public Effet effet;

    /**
     * L'image / sprite de la carte
     */
    
    public Image            image = null;

    /**
     * Carte jouable ou pas, c.-a-d. compatible avec la carte du sommet du talon ou pas
     */
    
    public boolean          jouable;

    /**
     * Angle de rotation de l'image / sprite
     */
    
    public float            angle;
    
    /**
     * Surface clickable de la carte
     */
    
    public Shape            bounds;

	/**
	 * Constructeur - On ajoute la valeur a la carte coloree
	 * @param couleur : la couleur de la carte
	 * @param valeur : la valeur de la carte
	 */
	
	public Carte(Couleur couleur, int valeur, Effet effet) throws SlickException {
		this.couleur = couleur;
		this.valeur = valeur;
		this.effet = effet;
		
		image = Sprite.get( valeur, couleur );
	}

	/**
	 * @return true : si la carte courante et celle passee en parametre sont compatibles ( cad jouable )
	 * @return false : sinon
	 */

	public boolean compatible(Carte carte) {
		return ( carte.couleur == couleur ) || ( (carte.valeur == valeur) );	// meme couleur ou meme valeur
	}

	/**
	 * Permet de retourner la couleur de la carte courante
	 * @return la couleur de la carte courante
	 */
	
	public Couleur getCouleur() {
		return couleur;
	}
		
	/**
	 * Permet de retourner le effet de la carte a effet
	 * @return le effet de la carte a effet
	 */
	
	public Effet getEffet() {
		return effet;
	}
	
	/**
	 * Retourne une chaine contenant la representation de la carte (Affichage de la carte)
	 */

	public String toString() {
		String affichage;
		if(effet.getNomEffet() == "NONE")
		{
			affichage = couleur.getValeurCouleur() + " " + valeur ;
		}
		else
		{
			affichage = couleur.getValeurCouleur() + " " + valeur + " " + effet.getNomEffet();
		}
		return affichage;
	}
	
	/**
	 * Permet de changer la couleur de la carte speciale et ceci n'est possible
	 * que si la couleur initiale de la carte est NOIR
	 * @param couleur : peut etre ROUGE, JAUNE, VERT ou BLEU
	 */
	
	public void setCouleur(Couleur couleur) {
		if (this.valeur == 7) {
			// On ne peut changer la couleur que quand la carte a pour valeur 7
			this.couleur = couleur;
		}
	}
	
    /**
     * La methode qui permet d'afficher la carte sur l'ecran
     * @throws SlickException 
     */
	
    @Override
    public void render( Graphics g ) throws SlickException {

        if ( image == null )
            Debug.err( this );

        this.rotate( angle ); // rotates image + update card's bounds
        
        image.draw( x, y, 0.7f);

        if ( !jouable ) { // si la carte n'est pas jouable ajouter un filtre gris sur la carte
            Image inactiveCardImg = Sprite.getInactiveCard();
            inactiveCardImg.setRotation( angle );
            inactiveCardImg.draw( x, y, 0.7f);
        }

        updateBounds();
    }

    public void rotate( float degree ) {
    	
        this.angle = degree;
        image.setRotation( degree ); // rotate sprite
        updateBounds();
        
    }

    public boolean isClicked( Point2D point ) {
        return bounds.contains( point );
    }

    public void updateBounds() {
    	
        int rectX = (int) x, rectY = (int) y, rectWidth = 140, rectHeight = 210;
        Shape rect = new Rectangle( rectX, rectY, rectWidth, rectHeight ); //creating the rectangle you want to rotate

        if ( angle == 0 ) {
            this.bounds = rect;
            return;
        }
        // rotate the rectangle
        AffineTransform transform = new AffineTransform();
        
        transform.rotate( Math.toRadians( angle ), rectX + rectWidth / 2, rectY + rectHeight / 2 ); //rotating in central axis
        
        rect = transform.createTransformedShape( rect );
        this.bounds = rect;
        
    }
    
    public void updatePlateauBounds() {
        int rectX = (int) x, rectY = (int) y, rectWidth = WIDTH_PLATEAU, rectHeight = HEIGHT_PLATEAU;
        Shape rect = new Rectangle( rectX, rectY, rectWidth, rectHeight ); //creating the rectangle you want to rotate

        if ( angle == 0 ) {
            this.bounds = rect;
            return;
        }
        // rotate the rectangle
        AffineTransform transform = new AffineTransform();
        transform.rotate( Math.toRadians( angle ), rectX + rectWidth / 2, rectY + rectHeight / 2 ); //rotating in central axis
        rect = transform.createTransformedShape( rect );
        this.bounds = rect;
    }

	@Override
	public void update(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		
	}

}
