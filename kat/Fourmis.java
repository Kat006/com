package com.kat;

import java.awt.Color;
import java.util.Random;

public class Fourmis extends Automate {
	private static final int[] tourne= {-1,1};
	private static final int lesCotes= 4;
	private static int lig;
	private static int col;
	private static int cotes;
	private static int etatActuel;

	

	public Fourmis(int taille) {
		super(taille);
		lig = new Random().nextInt(taille);
		col = new Random().nextInt(taille);
		cotes = new Random().nextInt(lesCotes);
		etat[lig][col]= 1;
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
		
		//changer le couleur
		setEtat(lig,col,etatActuel = additionner(etatActuel, 1, tourne.length));
		//tourner
		cotes = additionner(cotes, tourne.length, lesCotes);
		continuerDeBouger(2);
		//nouveauEtat getEtat
		etatActuel = getEtat(lig, col);
		//mettreEtat setEtat
		setEtat(lig, col, tourne.length);
	}

	private void continuerDeBouger(int i) {
		if(cotes % 2 == 0 ) {
			lig = additionner(lig,(cotes ==0 ? -i : i), taille);
		}else {
			col = additionner(col,(cotes ==1 ? i : -i), taille);
		}
	}

	private static int additionner(int numero, int additioner, int longeurMaximal) {
		return (numero +(additioner >=0 ? additioner : longeurMaximal + additioner)) % longeurMaximal;
	}
}
