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
                    e.printStackTrace();
                }

                // Ajout d'un observer
                m.addObserver(view);

                // Scheduler
                Thread scheduler = new Thread(new Scheduler(m));
                scheduler.start();

                view.setVisible(true);

            }
        });
    }
}
