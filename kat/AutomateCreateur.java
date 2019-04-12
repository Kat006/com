package com.kat;

public class AutomateCreateur {
    /**
     * Fonction creant une instance de la classe JeuDeLaVie
     * 
     * @param taille la taille de l'automate
     * @return Automate l'automate cree
     */
    public static Automate createJeuDeLaVie(int taille) {
        return new JeuDeLaVie(taille); // Remplacer par votre classe qui étend 'Automate'
    }

    /**
     * Fonction creant une instance de la classe Fourmis
     * 
     * @param taille la taille de l'automate
     * @return Automate l'automate cree
     */
    public static Automate createFourmis(int taille) {
        return new Fourmis(taille);
    }

    /**
     * Fonction creant une instance de la classe FeuForet
     * 
     * @param taille           la taille de l'automate
     * @param probaFeu         la probabilite d'apparition d'un feu
     * @param probaForet       la probabilite d'apparition d'un arbre
     * @param probaPropagation la probabilite de propagation d'un feu
     * @return Automate l'automate cree
     */
    public static Automate createFeuForet(int taille, double probaFeu, double probaForet, double probaPropagation) {
        return new FeuForet(taille);
    }
}
