package main.gameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import main.Game;
import main.common.Config;
import main.gfx.Sprite;

/**
 * Classe representant la pioche du jeu (le deck du jeu)
 * @author Adil Mania - Bastien Rousset
 *
 */

public class Deck extends Pile {

	/**
	 * Constructeur : permet de construire le deck et d'y inserer toutes les cartes necessaires
	 */

	public Deck() throws SlickException {
		for(Couleur couleur : Couleur.values()) {
			// 1 Cartes 1..9 pour chaque couleur en plus des cartes a effet
			for(int i = 1; i <= 12; i++) {
				switch (i)
				{
				  case 1:
					if( couleur.getValeurCouleur() == "Bleu" )
					{
						ajouter(new Carte(couleur, i, Effet.PLUS10));
					}
					else
					{
						ajouter(new Carte(couleur, i,  Effet.NONE));
					}
				    break;
				  case 2:
					  ajouter(new Carte(couleur, i, Effet.PLUS2));
				    break;
				  case 7:
					  ajouter(new Carte(couleur, i, Effet.JOKER));
				    break;
				 case 10:
					  ajouter(new Carte(couleur, i, Effet.PASSER));
					    break;
				 case 12:
					  ajouter(new Carte(couleur, i, Effet.INVERSER));
					    break;
				  default:
					  ajouter(new Carte(couleur, i, Effet.NONE));
				}
			}
		}
	}
	
	/**
	 * Cette methode est utilisee pour retourner une carte aleatoirement dans le Deck
	 * dans le cas ou la premiere carte est une carte a effet ( au debut du jeu )
	 * @param carte
	 */
	
	private void retournerCarte(Carte carte) {
		int i = rand.nextInt(cartes.size());	// Entier aleatoire entre 0 et cartes.size() - 1
		cartes.add(i, carte);
	}

	/**
	 * cette methode est appelee par le plateau pour qu'elle lui retourne sa premiere carte
	 * la carte ne doit pas etre a effet
	 * @return
	 */
	
	public Carte premiereCartePlateau() {
		Carte carte;
		while (true) {
			carte = depiler();	// Retirer une carte
			if (carte.effet.getNomEffet() != "NONE") {	// C'est une carte a effet
				// Il faut dans ce cas la rajouter aleatoirement dans le deck
				retournerCarte(carte);
			} else {
				return carte;
			}
		}
	}

    /**
     * La methode responsable d'afficher la pioche sur l'ecran
     * @param g
     * @throws SlickException 
     */
	
    public void render( Graphics g ) throws SlickException {
        Carte carteSommet = this.sommet();
        if ( carteSommet == null ) {
            return; // wait until the draw card is populated again
        }

        Image image = Sprite.getHiddenCard();
        image.setRotation( 0 );

        carteSommet.x = Game.WIDTH / 2 - Carte.WIDTH / 2 + Integer.parseInt( Config.get( "offsetPiocheTalon" ) );
        carteSommet.y = Game.HEIGHT / 2 - Carte.HEIGHT / 2;
        carteSommet.angle = 0;

        // Pour voir a peu pres combien y en a de cartes
        float yVal = carteSommet.y;
        for ( int i = 0; i < cartes.size(); ++i ) {
            g.drawImage( image, carteSommet.x, yVal );
            if ( i % 5 == 0 ) {
                yVal -= 2;
            }
        }

        // showing number of cards
        int offset = 20;
        String str = String.valueOf( this.cartes.size() );
        g.drawString( str,
                // centered over the last card
                Game.WIDTH / 2 + Integer.parseInt( Config.get( "offsetPiocheTalon" ) )
                        - g.getFont().getWidth( str ) / 2,
                // a little bit above the last card
                yVal - offset // pour que ca soit pres de la carte du sommet de la pile
        );
    }

    public void update( GameContainer container ) throws SlickException {

    }

}
