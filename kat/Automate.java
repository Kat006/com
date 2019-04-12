package com.kat;

import java.awt.Color;

//Vous n'avez pas le droit de changer le nom de la classe ainsi que les fonctions
//Par contre, vous avez le droit d'ajouter des fonctions autant que vous voulez.
//De plus, vous pouvez(meme vous DEVEZ) ajouter des variables d'instances

// Chaque classe de jeu que vous devez faire (ex: jeu de la vie) doit étendre cette classe.
public abstract class Automate {
	
    protected int[][] etat;
    protected int taille;

    public Automate(int taille) {
        this.taille = taille;
        etat = new int[taille][taille];
    }

    public int getTaille() {
        return taille;
    }

    public int getEtat(int rangee, int colonne) {
        return etat[colonne][rangee];
    }
    
    public void setEtats(int[][] etats) {
    	this.etat = etats;
    }
    
	public void setEtat(int col, int ran, int etat) {
		
	}
	
    
    public void step() {
    }

    public int getNombreDEtatsMaximum() {
        return 2;
    }

    public Color getCouleur(int etat) {
        switch (etat) {
        case 0:
            return Color.WHITE;
        case 1:
            return Color.BLUE;
        default:
            return Color.CYAN;
        }
    }
}
