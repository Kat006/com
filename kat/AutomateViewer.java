package com.kat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

/**
 * Classe definissant un interface graphique permettant la visualisation
 * d'automate cellulaire
 * 
 * @author Melissa Jourdain
 */
public class AutomateViewer extends JFrame {
    private static final long serialVersionUID = 1L;

    public static final String[] automateNom = { "Jeu de la vie", "Fourmis de Langton", "Feu et foret" };
    private Automate automate;

    // l'interface GUI
    private JButton buttonStart;
    private JButton buttonReset;
    private JButton buttonStep;
    JTextField probaFire, probaGrow, probaPropagate;

    JComboBox<String> listAutomate;
    AutomateDrawer automateDrawer;
    JSlider sliderSpeed;

    public AutomateViewer(int tailleAutomate) {
        Container wholeWindow = this.getContentPane();
        automate = null;

        // creation des composantes GUI
        buttonStart = new JButton("Commencer");
        buttonReset = new JButton("Reinitialiser");
        buttonStep = new JButton("Step");
        listAutomate = new JComboBox<String>(automateNom);
        sliderSpeed = new JSlider(25, 200);
        sliderSpeed.setPaintTicks(true);
        sliderSpeed.setSnapToTicks(true);
        sliderSpeed.setMajorTickSpacing(25);
        sliderSpeed.setMinorTickSpacing(10);
        sliderSpeed.setPaintLabels(true);
        // taille du panel de dessin de l'automate
        automateDrawer = new AutomateDrawer(tailleAutomate);
        // panel contenant les composantes de definition des probabilites de l'automate
        // Feu et Foret
        JPanel automateParameters = new JPanel();
        automateParameters.setLayout(new FlowLayout());
        probaFire = new JTextField("0.00001", 10);
        probaGrow = new JTextField("0.2", 10);
        probaPropagate = new JTextField("0.8", 10);
        automateParameters.add(new JLabel("Proba de feu"));
        automateParameters.add(probaFire);
        automateParameters.add(new JLabel("Proba arbre"));
        automateParameters.add(probaGrow);
        automateParameters.add(new JLabel("Proba propagation"));
        automateParameters.add(probaPropagate);

        // panel contenant les differents controles d'execution de l,automate
        JPanel communicationPart = new JPanel();
        communicationPart.add(listAutomate);
        communicationPart.add(buttonStart);
        communicationPart.add(new JLabel("Delai"));
        communicationPart.add(sliderSpeed);
        communicationPart.add(buttonReset);
        communicationPart.add(buttonStep);

        // ajout des differents panel
        wholeWindow.add(automateParameters, BorderLayout.NORTH);
        // terrible hack, desolee...
        wholeWindow.add(new JLabel("                            "), BorderLayout.WEST);
        wholeWindow.add(automateDrawer, BorderLayout.CENTER);
        wholeWindow.add(communicationPart, BorderLayout.SOUTH);

        pack();
        setTitle("Automatons!!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
    }

    public void setAutomate(Automate pAutomate) {
        automate = pAutomate;
    }

    public void addChangeDelay(ChangeListener changeListener) {
        sliderSpeed.addChangeListener(changeListener);
    }

    public void addAutomateStarter(ActionListener actionListener) {
        buttonStart.addActionListener(actionListener);
    }

    public void addAutomateSteper(ActionListener actionListener) {
        buttonStep.addActionListener(actionListener);
    }

    public void addAutomateReset(ActionListener actionListener) {
        buttonReset.addActionListener(actionListener);
    }

    public void addAutomateChanger(ActionListener actionListener) {
        listAutomate.addActionListener(actionListener);
    }

    class AutomateDrawer extends JPanel {
        public AutomateDrawer(int taille) {
            if (automate != null)
                setSize(610, 610);
        }

        public void paint(Graphics g) {
            if (automate == null)
                return;
            setSize(610, 610);

            Color[] cTab = new Color[automate.getNombreDEtatsMaximum()];

            int tailleAutomate = automate.getTaille();
            int widthCell, heightCell;
            for (int i = 0; i < cTab.length; i++)
                cTab[i] = automate.getCouleur(i);

            widthCell = (getWidth()) / tailleAutomate;
            heightCell = (getHeight()) / tailleAutomate;

            for (int i = 0; i < tailleAutomate; i++)
                for (int j = 0; j < tailleAutomate; j++) {
                    g.setColor(cTab[automate.getEtat(i, j)]);
                    g.fillRect(i * widthCell, j * heightCell, widthCell, heightCell);
                    if (tailleAutomate < 200) {
                        g.setColor(Color.BLACK);
                        g.drawRect(i * widthCell, j * heightCell, widthCell, heightCell);
                        // g.drawString("" + i +"," + j, (i * widthCell), (j * heightCell) + 10);
                    }
                }
        }
    }
}
