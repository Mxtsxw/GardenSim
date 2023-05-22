import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {

    protected Model m;
    protected JPanel mainPanel;
    protected JPanel gridPanel;
    protected JPanel infoPanel;
    protected JLabel tomatoLabel;
    protected JLabel onionLabel;
    protected JLabel potatoLabel;
    protected JTextField tomatoTextField;
    protected JTextField onionTextField;
    protected JTextField potatoTextField;

    public View(Model m) {
        super();

        this.m = m;
        this.mainPanel = new JPanel(new BorderLayout());
        this.gridPanel = new JPanel(new GridLayout(10, 10));
        this.infoPanel = new JPanel(new GridBagLayout());
        this.tomatoLabel = new JLabel("Tomates :");
        this.onionLabel = new JLabel("Oignons :");
        this.potatoLabel = new JLabel("Patates :");
        this.tomatoTextField = new JTextField();
        this.onionTextField = new JTextField();
        this.potatoTextField = new JTextField();

        build();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });
    }

    public View() {
        super();

        this.m = new Model();
        this.mainPanel = new JPanel(new BorderLayout());
        this.gridPanel = new JPanel(new GridLayout(10, 10));
        this.infoPanel = new JPanel(new GridBagLayout());
        this.tomatoLabel = new JLabel("Tomates :");
        this.onionLabel = new JLabel("Oignons :");
        this.potatoLabel = new JLabel("Patates :");
        this.tomatoTextField = new JTextField();
        this.onionTextField = new JTextField();
        this.potatoTextField = new JTextField();

        build();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });
    }

    /**
     * Gère la création des composants de l'interface utilisateur
     */
    public void build() {
        setTitle("Simulateur de Tomates");
        setSize(600, 400);

        Border blackLine = BorderFactory.createLineBorder(Color.black, 1);

        for (int i = 0; i < 100; i++) {
            JComponent parcel = new Parcel();
            parcel.setBorder(blackLine);
            this.gridPanel.add(parcel);
        }

// Création des contraintes pour la disposition GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST; // Alignement à gauche
        gbc.insets = new Insets(5, 5, 5, 5); // Espacement autour des composants

        // Ajout du JLabel "Tomates"
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 0; // Ligne 0
        this.infoPanel.add(tomatoLabel, gbc);

        // Ajout du JTextField pour les tomates
        gbc.gridx = 1; // Colonne 1
        gbc.gridy = 0; // Ligne 0
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expansion horizontale
        gbc.weightx = 1.0; // Poids horizontal
        tomatoTextField.setColumns(7); // Définition du nombre de colonnes pour la taille
        this.infoPanel.add(tomatoTextField, gbc);

        // Ajout du JLabel "Oignons"
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 1; // Ligne 1
        this.infoPanel.add(onionLabel, gbc);

        // Ajout du JTextField pour les oignons
        gbc.gridx = 1; // Colonne 1
        gbc.gridy = 1; // Ligne 1
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expansion horizontale
        gbc.weightx = 1.0; // Poids horizontal
        onionTextField.setColumns(7); // Définition du nombre de colonnes pour la taille
        this.infoPanel.add(onionTextField, gbc);

        // Ajout du JLabel "Patates"
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 2; // Ligne 2
        this.infoPanel.add(potatoLabel, gbc);

        // Ajout du JTextField pour les patates
        gbc.gridx = 1; // Colonne 1
        gbc.gridy = 2; // Ligne 2
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expansion horizontale
        gbc.weightx = 1.0; // Poids horizontal
        potatoTextField.setColumns(7); // Définition du nombre de colonnes pour la taille
        this.infoPanel.add(potatoTextField, gbc);

        this.mainPanel.add(gridPanel, BorderLayout.CENTER);
        this.mainPanel.add(infoPanel, BorderLayout.EAST);
        this.mainPanel.setBorder(blackLine);

        add(mainPanel);
    }

    @Override
    public void update(Observable obs, Object obj) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int index = i * 10 + j;
                if (m.grid[i][j])
                    this.gridPanel.getComponent(index).setBackground(Color.RED);
                else
                    this.gridPanel.getComponent(index).setBackground(Color.WHITE);
            }
        }
    }
}
