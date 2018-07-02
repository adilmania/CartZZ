package main.io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Cette classe implemente le comportement des boutons de la fenetre du choix de la couleur
 * @author Adil Mania - Bastien Rousset
 */

public class GetColorDialogBtnActionListener implements ActionListener {

    /**
     * le nom de la couleur choisie
     */
	
    String         couleur;
    
    /**
     * reference sur la fenetre associee
     */
    
    GetColorDialog dialog;

    /**
     * constructeur
     * @param dialog
     * @param couleur
     */
    
    public GetColorDialogBtnActionListener( GetColorDialog dialog, String couleur ) {
        this.dialog = dialog;
        this.couleur = couleur;
    }

    /**
     * cette methode contient le code a executer lors d'un clique
     */
    
    @Override
    public void actionPerformed( ActionEvent e ) {
        dialog.selectedColor = couleur; // sauvegarder la couleur choisie
        System.out.println( "SelectedColor = " + couleur );
        dialog.dispose(); // fermer la fenetre
    }
}
