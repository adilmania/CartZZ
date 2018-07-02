package main.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * Classe responsable de l'extraction des configurations du fichier .ini
 * @author Adil Mania - Bastien Rousset
 */

public class Config {

    /**
     * Nom du fichier de configuration ( fichier .ini )
     */
	
    private static final String                  configFilePath = "config.ini";
    
    /**
     * Contient la configuration par defaut du jeu
     */
    
    private static final Properties              defaults       = new Properties();
    
    /**
     * Contient la configuration du jeu
     * la configuration est lue une seule fois du fichier et tout au long du jeu a 
     * partir de cette table de hachage
     */
    
    private static final HashMap<String, String> values         = new HashMap<>();
    
    /**
     * Drapeau ( flag ) utilise pour s'assurer que la methode init() a ete invoque une seule fois
     */
    
    private static boolean                       isInitialized  = false;
    
    /**
     * Objet utilise pour lire ( parser ) le fichier de configuration
     */
    
    static Properties                            p              = new Properties();

    /**
     * Methode d'initialisation, initialise les valeurs par defaut
     */
    
    private static void init() {
        if ( isInitialized ) { // si la methode a ete deja invoquee ...
            return; // ... quitter
        }
        isInitialized = true; // sinon, on marque que c'est invoquee pour ne pas l'invoquer une 2eme fois
        // les configurations par defauts ( utiles quand on ne trouve pas les configurations dans le fichier )
        defaults.put("height", "1366");
        defaults.put( "nbJoueurs", "4" );
        defaults.put("width", "768");
        defaults.put( "title", "CartZZ" );
        defaults.put( "imgPath", "res/gfx/" );
        defaults.put( "soundPath", "res/sons/" );
        defaults.put( "offsetPiocheTalon", "90" );
        defaults.put( "fs", "true" );

        defaults.put( "jouerMusique", "false" );
        defaults.put( "jouerSons", "false" );

        // sons
        defaults.put( "clickSound", "cardClicked.wav" );
        defaults.put( "invalidClickSound", "invalidCardClicked.wav" );
        defaults.put( "unoSound", "uno.wav" );
        defaults.put( "skipSound", "passe.wav" );
        defaults.put( "reverseSound", "reverse.wav" );
        defaults.put( "plus2Sound", "+2.wav" );
        defaults.put( "wildSound", "wild.wav" );
        defaults.put( "hardLuckSound", "hardLuck.wav" );
        defaults.put( "noPlayableCardsSound", "noPlayableCards.wav" );
        defaults.put( "plus4Sound", "+4.wav" );
        defaults.put( "winSound", "win.wav" );
        // music
        defaults.put( "bgMusic", "bgMusic.wav" );
    }

    /**
     * Lit ( parse ) le fichier de configuration
     */
    
    private static void readConfigFile() {
        try {
            init(); // initialisation
            InputStream is = new FileInputStream( configFilePath );
            p.load( is );
            is.close();
        } catch ( IOException e ) {
            Debug.err( "Fichier introuvable : " + configFilePath );
        }
    }

    /**
     * Utilisee pour avoir une valeur du fichier de configuration
     * @param key nom du parametre demande
     * @return la valeur du parametre demande
     */
    
    public static String get( String key ) {
        return values.get( key );
    }

    /**
     * Permet de charger les configurations pour qu'elles soient utilisees dans le jeu
     */
    
    public static void load() {
        try {
            readConfigFile(); // lecture du fichier
            // charger les configurations du fichier
            Enumeration<?> e = p.propertyNames();
            while ( e.hasMoreElements() ) {
                String key = (String) e.nextElement();
                values.put( key, p.getProperty( key ) );
            }
            // s'il y on a des configurations manquantes, on met les configurations par defaut
            e = defaults.propertyNames();
            while ( e.hasMoreElements() ) {
                String key = (String) e.nextElement();
                if ( !values.containsKey( key ) ) { // si le nom du parametre n'est pas trouve dans la liste des configurations ...
                    // ... on l'ajoute
                    values.put( key, defaults.getProperty( key ) );
                }
            }
            // sauvegarde des configurations dans le fichier
            OutputStream os = new FileOutputStream( configFilePath );
            p.store( os, null );
        } catch ( IOException e ) {
            Debug.err( "Fichier introuvable: " + configFilePath );
        }
    }
}
