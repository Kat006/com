package com.kat;

import java.awt.Color;
import java.util.Random;

public class Fourmis extends Automate {
	private int lig, col, etatActuel, oldLig, oldCol;
	private Direction ouAller;
	private final int vide = 0, terre = 1, fourmi =2;

	public Fourmis(int taille) {
		super(taille);
		inisialiserVariablesAvecRandom(taille);
		etatActuel = vide;
		etat[col][lig]= fourmi;
		wantSomeDirections();
	}

	private void inisialiserVariablesAvecRandom(int taille) {
		lig = oldLig = new Random().nextInt(taille);
		col = oldCol = new Random().nextInt(taille);
	}

	public Color getCouleur(int etat) {
		switch (etat) {
		case vide:
			return Color.WHITE;
		case terre:
			return Color.BLACK;
		default:
			return Color.RED;
		}
	}

	@Override
	public int getNombreDEtatsMaximum() {
		return 3;
	}

	@Override
	public void step() {
		changerDirection();
		bouger();
		
		etat[oldCol][oldLig] = etatActuel;
		etatActuel = etat[col][lig];

		etat[col][lig] = fourmi;
		oldLig = lig;
		oldCol = col;
	}


	private void changerDirection() {
		if(etatActuel == vide) {
			ouAller = ouAller.getGauche();
			etatActuel = terre;
		}
		else if(etatActuel == terre) {
			ouAller = ouAller.getDroite();
			etatActuel = vide;
		}
	}

	private void bouger() {
		if(ouAller.getDirection() == "haut") {
			if(col==0)
				col = taille -1;
			else 
				col--;
		}
		if(ouAller.getDirection() == "bas") {
			if(lig == taille -1)
				lig = 0;
			else	
				lig++;
		}
		if(ouAller.getDirection() == "gauche") {
			if(col == taille -1 )
				col = 0;
			else
				col++;
		}
		if(ouAller.getDirection() == "droite") {
			if(lig == 0)
				lig = taille - 1;
			else
				lig--;
		}
	}
	
	private void wantSomeDirections(){
		Direction[] someDirections = {new Direction("haut"), new Direction("bas"),new Direction("gauche"),new Direction("droite")};
		someDirections[0].setDroite(someDirections[1]);
		someDirections[1].setDroite(someDirections[2]);
		someDirections[2].setDroite(someDirections[3]);
		someDirections[3].setDroite(someDirections[0]);
		ouAller = someDirections[new Random().nextInt(someDirections.length)];
	}
	
	private static class Direction {
		private Direction gauche;
		private Direction droite;
		private String direction;
		
		public Direction(String dir) {
			this.setDirection(dir);
		}
		
		public Direction getGauche() {
			return gauche;
		}


		public Direction getDroite() {
			return droite;
		}


		public void setDroite(Direction droite) {
			this.droite = droite;
			droite.gauche = this;
		}

		public String getDirection() {
			return direction;
		}

		public void setDirection(String direction) {
			this.direction = direction;
		}
	}
}
