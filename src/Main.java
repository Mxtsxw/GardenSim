/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.SwingUtilities;

/**
 * Executable
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Initialisation du modèle
                Model m = new Model();

                // Initialisation de la vue
                View view = new View(m);

                // Ajout d'un observer
                m.addObserver(view);

                view.setVisible(true);

                // Création d'un thread pour le rafraichissement du modèle
                new Thread(m).start();
            }
        });
    }
}