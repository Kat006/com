package com.kat;

/**
 * Classe permettant la visualisation de l'execution de differents types
 * d'automates
 * 
 * @author Melissa Jourdain Pour des raisons de rapidite d'affichage, ainsi que
 *         d'esthetique, le nombre de cellule ne peut exceder 200 pour un
 *         automate
 */
public class AutomateRunner {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java AutomateRunner <taille automate entre 10 et 200)");
            System.exit(0);
        }

        int taille = Integer.parseInt(args[0]);
        if (taille < 0 || taille > 200) {
            System.out.println("Usage: java AutomateRunner <taille automate entre 10 et 200)");
            System.exit(0);
        }

        // lancement de l'interface graphique, et de son controleur
        AutomateViewer view = new AutomateViewer(taille);
        new AutomateController(view, taille);
        view.setVisible(true);
    }
}
