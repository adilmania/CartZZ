package main.gameObjects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import main.Game;
import main.common.Config;

/**
 * Classe representant le Plateau du jeu
 * @author Adil Mania - Bastien Rousset
 */

public class Plateau extends Pile {
  
	/**
	 * Constructeur du Plateau
	 * @param deck : le Deck du jeu dont on doit prendre une carte pour quelle soit la 1ere carte du Plateau
	 */
	
	public Plateau(Deck deck) {
		ajouterPremiereCarte(deck);
	}

	/**
	 * Permet d'ajouter la premiere carte ( au debut du jeu )
	 * @param deck : le deck d'ou on veut tirer la carte
	 */
	
	private void ajouterPremiereCarte(Deck deck) {
		empiler(deck.premiereCartePlateau());
	}

	/**
	 * Retourne une chaine decrivant le Plateau - Affichage sur le jeu
	 */
	
	public String toString() {
		String str = "";
		
		if (cartes.isEmpty()) {
			str = "[VIDE]";
		}
		
		for(int i = 0; i < cartes.size(); ++i) {
			
			Carte carte = cartes.get(i);
			
			str = str + i + ") " + carte.toString();
			
			if (i != cartes.size() - 1) {	// si ce n'est pas la derniere iteration
				str = str + "\n";	// ajouter un retour chariot
			}
			
		}
		
		return str;
	}

	/**
	 * Permet d'afficher le Plateau - Affiche des cartes jouables en plus de l'option piocher
	 */
	
	public void afficher() {
		System.out.println(this);
	}
	

	/**
	 * Permet d'afficher la carte au sommet du Plateau
	 */
	
	public void afficherSommet() {
		System.out.println("\n-> Sommet du Plateau : " + sommet().toString() + "\n");
	}

    /**
     * La methode responsable d'afficher le plateau sur l'ecran
     * @param g
     * @throws SlickException 
     */
	
    public void render( Graphics g ) throws SlickException {
        Carte carteSommet = this.sommet();

        Image image = this.sommet().image;
        image.setRotation( 0 );

        carteSommet.x = Game.WIDTH / 2 - Carte.WIDTH_PLATEAU / 2 - Integer.parseInt( Config.get( "offsetPiocheTalon" ) );
        carteSommet.y = Game.HEIGHT / 2 - Carte.HEIGHT_PLATEAU / 2;
        carteSommet.angle = 0;
        carteSommet.updatePlateauBounds();

        // Pour voir a peu pres combien y en a de cartes
        float yVal = carteSommet.y;
        for ( int i = 0; i < cartes.size(); ++i ) {
        	g.drawImage(image.getScaledCopy(Carte.WIDTH_PLATEAU, Carte.HEIGHT_PLATEAU), carteSommet.x, yVal);
            if ( i % 5 == 0 ) {
                yVal -= 2;
            }
        }

        // showing number of cards
        int offset = 20;
        String str = String.valueOf( this.cartes.size() );
        g.drawString( str,
                // centered over the last card
                Game.WIDTH / 2 - Integer.parseInt( Config.get( "offsetPiocheTalon" ) )
                        - g.getFont().getWidth( str ) / 2,
                // a little bit above the last card
                yVal - offset // pour que ca soit pres de la carte du sommet de la pile
        );
    }

}
