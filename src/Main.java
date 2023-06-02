import javax.swing.SwingUtilities;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Initialisation du modèle
                Model m = new Model();

                // Initialisation de la vue
                View view = null;
                try {
                    view = new View(m);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

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
