import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

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

                // Création du curseur personnalisé
                Image cursorImage = null;
                try {
                    URL imageURL = Main.class.getResource("/resources/images/Pelle.png");
                    if (imageURL != null) {
                        cursorImage = ImageIO.read(imageURL);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (cursorImage != null) {
                    // Redimensionner l'image si nécessaire
                    int newWidth = 100;
                    int newHeight = 100;
                    Image resizedImage = cursorImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                    // Créer le curseur personnalisé avec l'image redimensionnée
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    Cursor customCursor = toolkit.createCustomCursor(resizedImage, new Point(15, 0), "Custom cursor");
                    view.setCursor(customCursor);
                }


                // Scheduler
                Thread scheduler = new Thread(new Scheduler(m));
                scheduler.start();

                view.setVisible(true);

            }
        });
    }
}
