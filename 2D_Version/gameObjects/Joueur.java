package main.gameObjects;

import java.util.ArrayList;

import javax.swing.UnsupportedLookAndFeelException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import main.Game;
import main.gfx.Sprite;

/**
 * Cette classe represente le joueur : ce qu'il possede et ce qu'il peut faire, cad ses proprietes et ses actions
 * @author Adil Mania - Bastien Rousset
 *
 */

public abstract class Joueur extends GameObject {

	/**
	 * La main du joueur contenant toutes ses cartes
	 */
	
	MainJoueur main;
	
	/**
	 * Le pseudo du joueur
	 */
	
	public String pseudo;
	
	/**
	 * Le Deck : la pile des cartes ou le joueur peut prendre des cartes dans le cas ou il n'a pas de cartes jouables
	 */
	
	protected Deck deck;
	
	/**
	 * Le Plateau : la pile des cartes ou les joueurs depose leurs cartes
	 */
	
	protected Plateau plateau;
	
	/**
	 * A pioche : booleen indiquant si le joueur a pioche ou pas
	 */
	
	public static int apioche;
	
    /**
     * la position du joueur sur l'ecran : haut, bas, droite, gauche
     */
	
    public Position position;
    
    /**
     * identifiant du joueur
     */
    
    public int      id;
    
    /**
     * la carte jouee
     */
    
    public Carte    playedCard;

	/**
	 * Contructeur du Joueur
	 * @param pseudo : pseudo du joueur
	 * @param deck : Le Deck
	 * @param plateau : Le Plateau
	 */
    
	public Joueur(String pseudo, Deck deck, Plateau plateau) {
		
		this.pseudo = pseudo;
		this.deck = deck;
		this.plateau = plateau;
		
		main = new MainJoueur();
	}

	/**
	 * Permet de prender une carte du deck et l'ajouter a la main du joueur
	 * @return la carte tiree
	 */
	
	public Carte prendreCarte() {
		
		if (deck.nbCartes() == 0) {	// le deck est vide !
			
			System.out.println("Le deck est vide !");
			// On remet toutes les cartes du plateau (sauf le sommet) dans le deck et on melange le deck
			Carte sommetplateau = plateau.depiler();
		
			while (plateau.nbCartes() != 0) {
				deck.empiler(plateau.depiler());// cette boucle va vider le plateau dans le deck
			}
			
			deck.melanger();
			plateau.empiler(sommetplateau);	
			// remettre le sommet du plateau
			
		}

		Carte carte = deck.depiler();
		main.ajouter(carte);
		Joueur.apioche = 1;
		return carte;
	}

    /**
     * cette methode permet d'afficher les cartes dans la main du joueur courant
     * 2 cas possibles :
     * 		(1) nombre de cartes paire : on a n cartes, n / 2 sont font des rotations de t, 2t, 3t, ...
     * 		et les n / 2 font des rotation de -t, -2t, -3t, ... avec t l'angle de rotation qui va dependre 
     * 		du nombre de cartes dans la main
     * 		(2) nombre de cartes impaire : on a n + 1 cartes, la carte au milieu reste tel quelle sans rotation
     * 		les n / 2 cartes a sa droite subissent des rotations de t, 2t, 3t, ..., (n / 2)t
     * 		et les n / 2 cartes a sa gauche subissent des rotations de -t, -2t, -3t, ...., -(n / 2)t
     * @throws SlickException 
     */
	
    public void afficherMain( Graphics g ) throws SlickException {
    	
        ArrayList<Carte> cartes = main.getCartes();
        int indiceCarteMilieu = cartes.size() / 2;

        float coefficientAngle;
        Carte carte;

        float initialX = JoueurPainter.getStartPosX( position ), initialY = JoueurPainter.getStartPosY( position );
        
        // TODO : l'angle et l'offset doivent etre proportionelles au nombre de cartes
        float angle = 10;
        float offset = 30;
        
        if ( cartes.size() < 10 ) {
            angle = 10;
            offset = 30;
        } else {
            angle = 5;
            offset = 30;
        }

        Image hiddenCardImg = Sprite.getHiddenCard();

        if ( cartes.size() % 2 == 0 ) { // nombre de cartes paire (1)

            int x = indiceCarteMilieu - 1;
            coefficientAngle = -( 1 / 2 + x );
            
            /**
             * exemple : 4 cartes
             * indiceCarteMilieu	=	2
             * x					=	1		0		-1		-2
             * coefficientAngle		=	-3/2	-1/2	1/2		3/2
             */

            /**
             * Rendering				0	-> n - 1
             * Checking click events	n-1	-> 0
             */
            
            for ( int i = 0; i < cartes.size(); i++ ) {

                carte = cartes.get( i ); // so we don't have a nullPointerException !
                
                if ( carte == null ) {
                    continue;
                } // to prevent unpredicted nullpointer exceptions !
                
                if ( JoueurPainter.getOffsetAxis( position ) == 'x' ) {
                    carte.x = initialX + coefficientAngle * offset;
                    carte.y = initialY;
                } else { // y
                    carte.x = initialX;
                    carte.y = initialY + coefficientAngle * offset;
                }

                carte.jouable = carte.compatible( plateau.sommet() );

                if ( this instanceof Humain ) {

                    carte.rotate( JoueurPainter.getAngleCorrection( position ) * coefficientAngle * angle
                            + JoueurPainter.getAdditionalRotation( position ) );
                    carte.render( g );

                } else {

                    hiddenCardImg.setRotation( JoueurPainter.getAngleCorrection( position ) * coefficientAngle * angle
                            + JoueurPainter.getAdditionalRotation( position ) );
                    hiddenCardImg.draw( carte.x, carte.y );

                }

                x--;
                coefficientAngle = -( 1 / 2 + x );
            }

        } else { // nombre de cartes impaire (2)

            coefficientAngle = -indiceCarteMilieu;

            for ( int i = 0; i < cartes.size(); i++ ) {

                carte = cartes.get( i );
                if ( JoueurPainter.getOffsetAxis( position ) == 'x' ) {
                    carte.x = initialX + coefficientAngle * offset;
                    carte.y = initialY;
                } else { // y
                    carte.x = initialX;
                    carte.y = initialY + coefficientAngle * offset;
                }

                carte.jouable = carte.compatible( plateau.sommet() );

                if ( this instanceof Humain ) {

                    carte.rotate( JoueurPainter.getAngleCorrection( position ) * coefficientAngle * angle
                            + JoueurPainter.getAdditionalRotation( position ) );
                    carte.render( g );

                } else {

                    hiddenCardImg.setRotation( JoueurPainter.getAngleCorrection( position ) * coefficientAngle * angle
                            + JoueurPainter.getAdditionalRotation( position ) );
                    hiddenCardImg.draw( carte.x, carte.y );

                }

                coefficientAngle++;
            }

        }
    }

	/**
	 * Contrairement a nbCartesJouables(), cette fonction doit etre publique
	 * les autres joueurs peuvent voir combien vous avez de cartes dans la main
	 * @return le nombre de cartes que possede le joueur dans sa main
	 */
	
	public int nbCartes() {
		return main.nbCartes();
	}

	/**
	 * Permet d'afficher les cartes dans la main du joueur courant
	 */
	
	public void afficherMain() {
		
		String str = "";
		ArrayList<Carte> cartes = main.getCartes();
		
		if (cartes.isEmpty()) {
			str = "[VIDE]";
		}
		
		for(int i = 0; i < cartes.size(); ++i) {
			
			Carte carte = cartes.get(i);
			str = str + i + ") " + carte.toString();
			
			if (carte.compatible(plateau.sommet())) {
				str = str + " [Jouable]";
			}
			
			if (i != cartes.size() - 1) {	// si ce n'est pas la derniere iteration
				str = str + "\n";	// ajouter un retour chariot
			}
			
		}
		
		str = str + "\n99) Piocher \n";
		System.out.println(str);
		
	}

	/**
	 * Cette methode doit etre prive ! seul le joueur doit connaitre combien il a de cartes jouables !
	 * @return le nombre de cartes jouables cad : compatibles avec le sommet du plateau 
	 */
	
	public int nbCartesJouables() {
		
		int n = 0;
		
		ArrayList<Carte> cartes = main.getCartes();
		
		if (cartes.isEmpty()) {
			return 0;
		}
		
		Carte sommetplateau = plateau.sommet();
		
		for(int i = 0; i < cartes.size(); ++i) {
			
			Carte carte = cartes.get(i);
			if (carte.compatible(sommetplateau)) {
				n++;
			}
			
		}
		
		return n;
	}
	
    /**
     * permet au joueur de jouer son tour
     * @throws InterruptedException 
     * @throws UnsupportedLookAndFeelException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     * @throws SlickException 
     */
	 
    public abstract void jouerTour() throws InterruptedException, ClassNotFoundException, InstantiationException,
    IllegalAccessException, UnsupportedLookAndFeelException, SlickException;

    /**
     * permet au joueur de jouer sa carte
     * @throws InterruptedException 
     * @throws UnsupportedLookAndFeelException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     * @throws SlickException 
     */
    
    public abstract void jouerCarte() throws InterruptedException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException, SlickException;

    @Override
    public void render( Graphics g ) throws SlickException {
    	
    	Image logobde = Sprite.getlogobde();
    	
        this.afficherMain( g );
        
        // afficher le nom du joueur
        int xPos, yPos, angleRotation;
        
        final int offsetHautBas = 50, offsetDroiteGauche = 50;
        
        String playerName = this.pseudo + " (" + nbCartes() + ")";

        Color oldColor = g.getColor();
        
        int tour = Jeu.tour; // pour assurer que la valeur de Jeu.tour n'a pas ete change !
        
        if ( id == tour ) {
            // si c'est le joueur courant, on change la couleur de son nom pour indiquer le sens du jeu
            //oldColor = g.getColor();
            g.setColor( Color.black );
            
        }
        
        switch ( position ) {
        
        case BAS:
            g.drawString( playerName,
                    Game.WIDTH / 2 - g.getFont().getWidth( playerName ) / 2,
                    Game.HEIGHT - Carte.HEIGHT - offsetHautBas );
            if ( id == tour ) {
            	logobde.draw(Game.WIDTH / 2 - g.getFont().getWidth( playerName ) / 2 - 100, Game.HEIGHT - Carte.HEIGHT - offsetHautBas);
            	logobde.rotate( 270 ); // rotating
            }
            break;
            
        case GAUCHE:
            xPos = Carte.HEIGHT + offsetDroiteGauche;
            yPos = Game.HEIGHT / 2 - g.getFont().getWidth( playerName ) / 2;
            angleRotation = 90;

            g.rotate( xPos, yPos, angleRotation ); // rotating
            g.drawString( playerName,
                    xPos,
                    yPos );
            g.rotate( xPos, yPos, -angleRotation ); // reset rotation
            if ( id == tour ) {
            	logobde.draw(xPos, yPos);
            	logobde.rotate( 270 ); // rotating
            }
            break;
            
        case HAUT:
            xPos = Game.WIDTH / 2 + g.getFont().getWidth( playerName ) / 2;
            yPos = Carte.HEIGHT + offsetHautBas;
            angleRotation = 180;
            g.rotate( xPos, yPos, angleRotation ); // rotating
            g.drawString( playerName,
                    xPos,
                    yPos );
            g.rotate( xPos, yPos, -angleRotation ); // reset rotation
            if ( id == tour ) {
            	logobde.draw(xPos, yPos);
            	logobde.rotate( 270 ); // rotating
            }
            break;
            
        case DROITE:
            xPos = Game.WIDTH - Carte.HEIGHT - offsetDroiteGauche;
            yPos = Game.HEIGHT / 2 + g.getFont().getWidth( playerName ) / 2; // -1/2 textWidth + textWidth
            angleRotation = 270;
            g.rotate( xPos, yPos, angleRotation ); // rotating
            g.drawString( playerName,
                    xPos,
                    yPos );
            g.rotate( xPos, yPos, -angleRotation ); // reset rotation
            if ( id == tour ) {
            	logobde.draw(xPos, yPos);
            	logobde.rotate( 270 ); // rotating
            }
            break;
            
        default:
            break;
        }
        
        if ( id == tour ) {
            // si c'est le joueur courant, on change la couleur de son nom pour indiquer le sens du jeu
            g.setColor( oldColor );
        }
    }

}

/**
 * Cette classe va etre utilise en tant que <<Utility>> pour aider
 * la classe Joueur dans le dessin / render du joueur
 *
 */

class JoueurPainter {

    public static final int offsetHautBas = 10, offsetDroiteGauche = 10;

    public static float getStartPosX( Position position ) {
        switch ( position ) {
        case BAS:
            return Game.WIDTH / 2 - Carte.WIDTH / 2;
        case DROITE:
            return Game.WIDTH - Carte.HEIGHT + offsetDroiteGauche;
        case HAUT:
            return Game.WIDTH / 2 - Carte.WIDTH / 2;
        case GAUCHE:
            return 25 + offsetDroiteGauche; // 25 pour ne pas compter les bordures de la fenetre
        }
        return 0;
    }

    public static float getStartPosY( Position position ) {
        switch ( position ) {
        case BAS:
            return Game.HEIGHT - Carte.HEIGHT - offsetHautBas;
        case DROITE:
            return Game.HEIGHT / 2 - Carte.WIDTH / 2;
        case HAUT:
            return offsetHautBas;
        case GAUCHE:
            return Game.HEIGHT / 2 - Carte.WIDTH / 2;
        }
        return 0;
    }

    public static float getAdditionalRotation( Position position ) {
        switch ( position ) {
        case BAS:
            return 0;
        case DROITE:
            return -90;
        case HAUT:
            return 180;
        case GAUCHE:
            return 90;
        }
        return 0;
    }

    public static char getOffsetAxis( Position position ) {
        switch ( position ) {
        case BAS:
        case HAUT:
            return 'x';
        case DROITE:
        case GAUCHE:
            return 'y';
        }
        return 0;
    }

    public static int getAngleCorrection( Position position ) {
        switch ( position ) {
        case BAS:
        case GAUCHE:
            return 1;
        case HAUT:
        case DROITE:
            return -1;
        }
        return 0;
    }
}
