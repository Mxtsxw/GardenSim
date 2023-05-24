import javax.swing.SwingUtilities;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main {

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

                // Création d'un thread pour le rafraîchissement du modèle
                Thread modelThread = new Thread(m);
                modelThread.start();

            }
        });
    }
}
