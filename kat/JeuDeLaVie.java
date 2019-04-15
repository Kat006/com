package com.kat;

import java.util.Random;

public class JeuDeLaVie extends Automate {
	private final int maybeInLive = 11;
	private final int mort = 0, vivante = 1;

	public JeuDeLaVie(int taille) {
		super(taille);
		positionDebut(taille);
	}

	private void positionDebut(int taille) {
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				double random = new Random().nextInt(maybeInLive);
				if (random <= vivante) {
					etat[i][j] = vivante;
				} else {
					etat[i][j] = mort;
				}

			}
		}
	}

	public void step() {
		int[][] etatNew = new int[taille][taille];
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				if (etat[i][j] == mort && compteAuTour(i, j) == 3) {
					etatNew[i][j] = vivante;
				} else if (etat[i][j] == vivante && (compteAuTour(i, j) < 2 || compteAuTour(i, j) > 3)) {
					etatNew[i][j] = mort;
				} else {
					etatNew[i][j] = etat[i][j];
				}
			}
		}
		setEtats(etatNew);
	}

	private int compteAuTour(int rangee, int colonne) {
		int vivantes = 0;
		for (int col = rangee - 1; col <= rangee + 1; col++) {
			for (int ligne = colonne - 1; ligne <= colonne + 1; ligne++) {
				try {
					if (etat[col][ligne] == vivante) {
						vivantes++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}
		}
		if (etat[rangee][colonne] == vivante) {
			vivantes = vivantes - 1;
		}
		return vivantes;
	}
}
