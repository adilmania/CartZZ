package main.io;

import java.util.HashMap;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import main.common.Config;

/**
 * classe responsable de jouer les sons du jeu
 * @author Adil Mania - Bastien Rousset
 */

public class Audio {

    /**
     * son de l'arriere-plan
     */
	
    private static Music                  music;
    
    /**
     * effets sonores
     */
    
    private static HashMap<String, Sound> sounds = new HashMap<>();

    /**
     * charger les sons
     * @throws SlickException exception de la librairie slick2d
     */
    
    public static void load() throws SlickException {
        if ( soundEnabled ) { // si les sons sont autorises
            String soundNames[] = {
                    "clickSound", "invalidClickSound", "unoSound",
                    "skipSound", "reverseSound", "plus2Sound",
                    "wildSound", "hardLuckSound", "noPlayableCardsSound",
                    "plus4Sound", "winSound"
            };
            // Chargement des sons
            for ( String soundName : soundNames ) {
                sounds.put( soundName, new Sound( Config.get( "soundPath" ) + Config.get( soundName ) ) );
            }
            // Charger la musique du fond
            music = new Music( Config.get( "soundPath" ) + Config.get( "bgMusic" ) );
        }
    }

    /**
     * musique autorisee ?
     * true -> oui
     * false -> non
     */
    
    public static boolean musicEnabled = true;
    
    /**
     * sons autorisees ?
     * true -> oui
     * false -> non
     */
    
    public static boolean soundEnabled = true;

    /**
     * permet de jouer un son
     * @param soundName	nom de l'effet sonore
     * @throws SlickException exception de la librairie slick2d
     */
    
    public static void playSound( String soundName ) throws SlickException {
        if ( soundEnabled ) { // si les sons sont autorises
            sounds.get( soundName ).play(); // jouer le son
        }
    }

    /**
     * permet de jouer la musique en boucle
     * @throws SlickException exception de la librairie slick2d
     */
    
    public static void playMusic() throws SlickException {
        if ( musicEnabled ) { // si la musique est autorisee
            music.loop(); // jouer la musique en boucle
        }
    }

    /**
     * permet d'arretre la musique
     * @throws SlickException exception de la librairie slick2d
     */
    
    public static void stopMusic() throws SlickException {
        if ( musicEnabled ) { // si la musique est autorisee
            music.stop(); // arretre la musique
        }
    }
}
