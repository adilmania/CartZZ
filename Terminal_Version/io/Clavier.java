package io;

import java.util.ArrayList;
import java.util.Scanner;

import cartzz.Carte;

/**
 * cette classe nous permet d'interagir avec le joueur a travers le clavier
 * @author Adil Mania - Bastien Rousset
 */

public class Clavier {

    /**
     * Grace a cet objet on peut lire a partir du clavier
     */
	
    private static Scanner scanner = new Scanner( System.in );

    /**
     * Permet de lire un entier a partir du clavier
     * @return l'entier lu
     */
    
    public static int lireEntier() {
    	
        System.out.print( "? >> " );
        int res = scanner.nextInt();
        scanner.nextLine(); // pour consommer le caractere \n a la fin de la chaine
        return res;
        
    }

    /**
     * Permet de lire un entier entre min et max
     * @param min	la borne inferieure de l'entier a lire
     * @param max	la borne superieure de l'entier a lire
     * @return		l'entier qu'on veut lire du clavier
     */
    
    public static int lireEntier( int min, int max ) {
        if ( min > max ) {
            // Inverser les bornes
            int temp = min;
            min = max;
            max = temp;
        }
        boolean horsBornes;
        int num;
        do {
            num = lireEntier();
            horsBornes = (num < min && num != 99) || (num > max && num != 99);
            if ( horsBornes ) {
                System.out.println( "indice hors bornes ! [" + min + ".." + max + "]" );
            }
        } while ( horsBornes );
        return num;
    }

    /**
     * Permet de lire un entier qui represente l'une des cases de la liste arrayList
     * @param arrayList la liste dont on veut lire un indice valide
     * @return l'indice de l'une des cases de la liste arrayList
     */
    
    public static int lireEntier( ArrayList<Carte> arrayList ) {
        if ( arrayList.isEmpty() ) {
            return -1;
        }
        return lireEntier( 0, arrayList.size() - 1 ); // les listes sont indexes de 0 a nbElements - 1
    }

    /**
     * Permet de lire une chaine de caracteres a partir du clavier
     * @return la chaine lue
     */
    
    public static String lireChaine() {
        System.out.print( "? >> " );
        return scanner.nextLine();
    }

    /**
     * Permet de fermer la variable scanner a la fin du programme
     */
    
    public static void fermer() {
        scanner.close();
    }

}
