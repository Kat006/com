package com.kat;

import java.awt.Color;
import java.util.Random;

public class Fourmis extends Automate {
	private static int lig, col, cote, etatActuel, oldLig, oldCol;
	private static final int[] haut = {-1,0},droite= {0,+1},gauche= {0,-1},bas= {+1,0};
	private static final int[][] lesCotes= {haut,bas,gauche,droite};
	private Direction ouAller;
	private final int vide = 0, terre = 1, fourmi =2;

	public Fourmis(int taille) {
		super(taille);
		inisialiserVariablesAvecRandom(taille);
		etatActuel =vide;
		etat[col][lig]= fourmi;
		wantSomeDirections();
	}

	private void inisialiserVariablesAvecRandom(int taille) {
		lig = oldLig = new Random().nextInt(taille);
		col = oldCol = new Random().nextInt(taille);
		cote = new Random().nextInt(lesCotes.length);
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
		//case presedent change white(0) black(2)
		//modifier le dernier
		etat[oldCol][oldLig] = etatActuel;
		
		bouger();
		
		etatActuel = etat[col][lig];
		oldLig = lig;
		oldCol = col;
		
		changerDirection();
		
		//premier pas
		col += lesCotes[cote][1];
		lig += lesCotes[cote][0];
		etat[col][lig] = 1;
		//changer le couleur
		
		

		//nouveauEtat getEtat
		etatActuel = getEtat(lig, col);
		//mettreEtat setEtat
		etat[lig][col] = etatActuel;
	}

	private void changerDirection() {
		if(etatActuel == vide) {
			ouAller = ouAller.getGauche();
			etatActuel = terre;
		}
		if(etatActuel ==2) {
			ouAller = ouAller.getDroite();
			etatActuel = vide;
		}
	}

	private void bouger() {
		if(ouAller.getDirection() == "haut")
			col--;
		if(ouAller.getDirection() == "bas")
			lig++;
		if(ouAller.getDirection() == "gauche")
			col++;
		if(ouAller.getDirection() == "droite")
			lig--;
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
