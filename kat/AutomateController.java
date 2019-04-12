package com.kat;

import java.awt.event.*;

import javax.swing.JComboBox;
import javax.swing.event.*;

/**
 * Classe controlant l'interface graphique de la classe AutomateViewer Les
 * differents listener sont implantes dans cette classe, ainsi que le thread
 * d'execution des automates
 * 
 * @author Melissa Jourdain
 */
public class AutomateController {
    private Automate automate;
    private AutomateViewer viewer;
    // variable permettant le controle du thread d'execution des automates
    private boolean hasStarted, stepByStepMode;
    private Thread thread;
    private AutomateThread automateThread;
    private int delaiEntrePas;

    /**
     * Constructeur
     * 
     * @param viewer L'interface graphique
     * @param taille La taille des automates a dessiner
     */
    AutomateController(AutomateViewer viewer, int taille) {
        this.viewer = viewer;
        // ajoute les differents listeners aux composantes de l'interface graphique
        this.viewer.addAutomateStarter(new AutomateStarter());
        this.viewer.addAutomateReset(new AutomateReset());
        this.viewer.addAutomateChanger(new AutomateChanger());
        this.viewer.addAutomateSteper(new AutomateSteper());
        this.viewer.addChangeDelay(new SpeedChanger());
        // par defaut, on creer un automate
        automate = AutomateCreateur.createJeuDeLaVie(taille);
        this.viewer.setAutomate(automate);

        // au depart, le thread n'est pas encore parti, et on ne fonctionne pas "pas par
        // pas"
        hasStarted = false;
        stepByStepMode = false;
        automateThread = new AutomateThread();
        delaiEntrePas = viewer.sliderSpeed.getValue();
    }

    /**
     * Classe interne listener associe au bouton "Commencer" de l'interface
     * graphique Si le thread n'est pas deja parti, on l'initialise et on le lance
     * On arrete le "pas a pas" en donnant la valeur false a mStepByStep
     */
    class AutomateStarter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!hasStarted) {
                hasStarted = true;
                thread = new Thread(automateThread);
                thread.start();
            }
            stepByStepMode = false;
        }
    }

    /**
     * Classe interne listener associee au bouton "Step" de l'interface graphique
     * Arrete l'execution du thread,, et fait en sorte qu'un pas de l'automate est
     * execute que lorsque l'on clique sur le bouton
     */
    class AutomateSteper implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stepByStepMode = true;
            automate.step();
            viewer.repaint();
        }
    }

    /**
     * Classe interne listener associee au slider de delai Ajuste la vitesse
     * d'execution du thread selon la valeur du slider
     */
    class SpeedChanger implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            delaiEntrePas = viewer.sliderSpeed.getValue();
        }
    }

    /**
     * Classe interne listener associe au bouton "Reinitialiser" de l'interface
     * graphique Arrete l'execution du thread s'il etait deja en cours d'execution
     * Cree un nouvel automate Reinitialise la variable stepByStep pour ne pas que
     * le thread ne se relance avant la prochaine fois que l'on clique sur
     * "commencer"
     */
    class AutomateReset implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // on arrete le thread d'execution de l'automate
            stepByStepMode = true;
            if (hasStarted)
                thread.interrupt();
            String automateType = (String) (viewer.listAutomate.getSelectedItem());

            if (automateType.equals(AutomateViewer.automateNom[0])) {
                automate = AutomateCreateur.createJeuDeLaVie(automate.getTaille());
            } else if (automateType.equals(AutomateViewer.automateNom[2])) {
                double probaFire = Float.parseFloat(viewer.probaFire.getText());
                double probaGrow = Float.parseFloat(viewer.probaGrow.getText());
                double probaPropagate = Float.parseFloat(viewer.probaPropagate.getText());
                automate = AutomateCreateur.createFeuForet(automate.getTaille(), probaFire, probaGrow, probaPropagate);
            } else
                automate = AutomateCreateur.createFourmis(automate.getTaille());

            viewer.setAutomate(automate);
            stepByStepMode = true;
            hasStarted = false;
            viewer.repaint();
        }
    }

    /**
     * Classe listener associe a la liste d'automates Si le thread est lance, on
     * l'arrete On cree un nouvel automate choisi selon le choix de l'utilisateur On
     * reinitialise les variables associes au thread pour que le thread ne se
     * relance qu'une fois le boutton "Commencer " clique
     */
    class AutomateChanger implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (hasStarted)
                thread.interrupt();
            @SuppressWarnings("unchecked")
            JComboBox<String> combo = (JComboBox<String>) e.getSource();

            String automateType = (String) (combo.getSelectedItem());
            if (automateType.equals("Jeu de la vie")) {
                automate = AutomateCreateur.createJeuDeLaVie(automate.getTaille());
            } else if (automateType.equals("Fourmis de Langton")) {
                automate = AutomateCreateur.createFourmis(automate.getTaille());
            } else {
                double probaFire = Float.parseFloat(viewer.probaFire.getText());
                double probaGrow = Float.parseFloat(viewer.probaGrow.getText());
                double probaPropagate = Float.parseFloat(viewer.probaPropagate.getText());
                automate = AutomateCreateur.createFeuForet(automate.getTaille(), probaFire, probaGrow, probaPropagate);

            }

            viewer.setAutomate(automate);
            stepByStepMode = true;
            hasStarted = false;
            // on attend une petite milliseconde pour etre certain que le thread
            // d'execution de l'automate s'est arrete
            // On ne voudrait pas qu'il ecrive dans notre automate tout neuf!
            try {
                Thread.sleep(1);
            } catch (Exception exception) {
            }
            viewer.repaint();
        }
    }

    /**
     * Thread d'execution de l'automate
     */
    class AutomateThread implements Runnable {
        public void run() {
            try {
                Thread.sleep(delaiEntrePas);

                while (true) {
                    // si on ne fonctionne pas step by step
                    // on attend un certain delai, et on refait un pas de l'automate
                    if (stepByStepMode == false) {
                        Thread.sleep(delaiEntrePas);
                        automate.step();
                        viewer.repaint();
                    }
                    // sinon on fait dormir le thread, et on attend plutot que le bouton step soit
                    // clique pour faire un pas de l'automate
                    else
                        while (stepByStepMode)
                            Thread.sleep(10);
                }
            } catch (InterruptedException ie) {
            }
        }
    }
}
