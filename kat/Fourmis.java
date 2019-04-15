package com.kat;

import java.awt.Color;
import java.util.Random;

public class Fourmis extends Automate {
	private static int lig, col, cote, etatActuel, oldLig, oldCol;
	private static final int[] haut = {-1,0},droite= {0,+1},gauche= {0,-1},bas= {+1,0};
	private static final int[][] lesCotes= {haut,bas,gauche,droite};
	private Direction ouAller;

	public Fourmis(int taille) {
		super(taille);
		lig = oldLig = new Random().nextInt(taille);
		col = oldCol = new Random().nextInt(taille);
		cote = new Random().nextInt(lesCotes.length);
		etat[col][lig]= 1;
		wantSomeDirections();
	}

	public Color getCouleur(int etat) {
		switch (etat) {
		case 0:
			return Color.WHITE;
		case 1:
			return Color.RED;
		case 2:
			return Color.BLACK;
		default:
			return Color.CYAN;
		}
	}

	@Override
	public int getNombreDEtatsMaximum() {
		return 3;
	}

	@Override
	public void step() {
		//case presedent change white(0) black(2)
		oldLig = lig;
		oldCol = col;
		
		if(etatActuel == 0) {
			etatActuel = 2;
		}
		if(etatActuel ==2) {
			etatActuel = 0;
		}
		
		//premier pas
		col += lesCotes[cote][1];
		lig += lesCotes[cote][0];
		etat[col][lig] = 1;
		//changer le couleur
		
		//tourner

		//nouveauEtat getEtat
		etatActuel = getEtat(lig, col);
		//mettreEtat setEtat
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
			this.direction = dir;
		}
		
		public Direction getGauche() {
			return gauche;
		}


		public Direction getGroite() {
			return droite;
		}


		public void setDroite(Direction droite) {
			this.droite = droite;
			droite.gauche = this;
		}
	}
}
