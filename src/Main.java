import javax.swing.SwingUtilities;
import java.awt.*;
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

                // Création d'un curseur personnalisé
                // chargement de l'image
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image cursorImage = toolkit.getImage("/resources/images/pointeurPelle.png");

                // Redefinition de la taille du cursor
                int newWidth = 32;
                int newHeight = 32;
                Image resizedImage = cursorImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                // Create a custom cursor with the resized image
                Cursor customCursor = toolkit.createCustomCursor(
                        resizedImage,
                        new Point(0, 0),
                        "Custom Cursor"
                );
                view.setCursor(customCursor);

                // Scheduler
                Thread scheduler = new Thread(new Scheduler(m));
                scheduler.start();

                view.setVisible(true);

            }
        });
    }
}
