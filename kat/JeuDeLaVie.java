package com.kat;

import java.util.Random;

public class JeuDeLaVie extends Automate {
	private final int maybeInLive = 10;

	public JeuDeLaVie(int taille) {
		super(taille);
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				double random = new Random().nextInt(maybeInLive);
				if (random <= 1) {
					etat[i][j] = 1;
				} else {
					etat[i][j] = 0;
				}

			}
		}
	}

	public void step() {

		int[][] etatNew = new int[taille][taille];
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				if (etat[i][j] == 0) {
					if (compteAuTour(i, j) == 3) {
						etatNew[i][j] = 1;
					}
				} else if (etat[i][j] == 1) {
					if (compteAuTour(i, j) != 2 && compteAuTour(i, j) != 3)
						etatNew[i][j] = 0;
				} else {
					etatNew[i][j] = etat[i][j];
				}
			}
		}
		setEtats(etatNew);
	}

	private int compteAuTour(int rangee, int colonne) {
		int vivantes = 0;
		for (int col = rangee - 1; col < rangee + 2; col++) {
			for (int ligne = colonne - 1; ligne < colonne + 2; ligne++) {
				try {
					if (etat[col][ligne] == 1) {
						vivantes++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {

				}
			}
		}
		if (etat[rangee][colonne] == 1) {
			vivantes = vivantes - 1;
		}
		return vivantes;
	}
}
