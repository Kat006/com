package com.kat;

import java.awt.Color;
import java.util.Random;

public class FeuForet extends Automate {
	private final int terre = 0, arbre = 1, feu = 2;
	private Random rand = new Random();
	private double probaFeu, probaForet, probaPropagation;

	public FeuForet(int taille, double probaFeu, double probaForet, double probaPropagation) {
		super(taille);
		this.probaFeu = probaFeu;
		this.probaForet = probaForet;
		this.probaPropagation = probaPropagation;
	}

	public int getNombreDEtatsMaximum() {
		return 3;
	}

	public Color getCouleur(int etat) {
		switch (etat) {
		case terre:
			return Color.WHITE;
		case arbre:
			return Color.GREEN;
		default:
			return Color.PINK;
		}
	}

	@Override
	public void step() {
		int[][] etatNouveau = new int[taille][taille];
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				pbabiliteDeProchainPas(etatNouveau, i, j);
			}
		}
		etat = etatNouveau;
	}

	private void pbabiliteDeProchainPas(int[][] etatNouveau, int i, int j) {
		double probabilite = rand.nextDouble();
		siTerre(etatNouveau, i, j, probabilite);
		siFeu(etatNouveau, i, j);
		siArbre(etatNouveau, i, j, probabilite);
	}

	private void siArbre(int[][] etatNouveau, int i, int j, double probabilite) {
		if (etat[i][j] == arbre) {
			if (probabilite < probaFeu) {
				etatNouveau[i][j] = feu;
			} else if (probabilite < probaPropagation && feuProche(i, j)) {
				etatNouveau[i][j] = feu;
			} else {
				etatNouveau[i][j] = arbre;
			}
		}
	}

	private void siFeu(int[][] etatNouveau, int i, int j) {
		if (etat[i][j] == feu) {
			etatNouveau[i][j] = terre;
		}
	}

	private void siTerre(int[][] etatNouveau, int i, int j, double probabilite) {
		if (etat[i][j] == terre) {
			if (probabilite < probaForet) {
				etatNouveau[i][j] = arbre;
			} else {
				etatNouveau[i][j] = terre;
			}
		}
	}

	public boolean feuProche(int lig, int col) {
		for (int i = lig - 1; i <= lig + 1; i++) {
			for (int j = col - 1; j <= col + 1; j++) {
				try {
					if (etat[i][j] == feu) {
						return true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}
		}
		return false;
	}

}
