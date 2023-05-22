import javax.swing.SwingUtilities;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

                // Création de la barre de menu
                JMenuBar menuBar = new JMenuBar();

                // Création du menu "Fichier"
                JMenu fileMenu = new JMenu("Fichier");

                // Création des éléments du menu "Fichier"
                JMenuItem openItem = new JMenuItem("Ouvrir");
                JMenuItem saveItem = new JMenuItem("Enregistrer");
                JMenuItem exitItem = new JMenuItem("Quitter");

                // Ajout des éléments au menu "Fichier"
                fileMenu.add(openItem);
                fileMenu.add(saveItem);
                fileMenu.addSeparator(); // Ajout d'un séparateur
                fileMenu.add(exitItem);

                // Création de l'ActionListener pour exitItem
                ActionListener exitActionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                };

                // Ajout de l'ActionListener à exitItem
                exitItem.addActionListener(exitActionListener);

                // Ajout du menu "Fichier" à la barre de menu
                menuBar.add(fileMenu);

                // Création du menu "Plants"
                JMenu plantsMenu = new JMenu("Plants");

                // Création des éléments du menu "Plants"
                JMenuItem tomatoesItem = new JMenuItem("Tomates");
                JMenuItem onionsItem = new JMenuItem("Oignons");
                JMenuItem potatoesItem = new JMenuItem("Patates");

                // Ajout des éléments au menu "Plants"
                plantsMenu.add(tomatoesItem);
                plantsMenu.add(onionsItem);
                plantsMenu.add(potatoesItem);

                // Ajout du menu "Plants" à la barre de menu
                menuBar.add(plantsMenu);

                // Définition de la barre de menu pour la vue
                view.setJMenuBar(menuBar);

                view.setVisible(true);

                // Création d'un thread pour le rafraîchissement du modèle
                new Thread(m).start();
            }
        });
    }
}
