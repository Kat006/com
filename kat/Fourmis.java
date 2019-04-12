package com.kat;

import java.awt.Color;
import java.util.Random;

public class Fourmis extends Automate {
	private static final int[] tourne= {-1,1};
	private static final int lesCotes= 4;


	public Fourmis(int taille) {
		super(taille);
		int lig = new Random().nextInt(10);
		int col = new Random().nextInt(10);
		etat[lig][col] = 1;
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
		int direction = new Random().nextInt(lesCotes);
		//changer le couleur
		setEtat(lig,col,etat = add(etat,1,tourne.length));
		//tourner
		//continuerDeBouger()
		//nouveauEtat getEtat
		//mettreEtat setEtat
	}

	private int[][] add(int[][] etat, int i, int length) {
		return null;
	}
}
