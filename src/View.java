import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;

public class View extends JFrame implements Observer {

    protected Model m;
    protected JPanel mainPanel;
    protected JPanel gridPanel;
    protected JPanel infoPanel;
    protected JLabel tomatoLabel;
    protected JLabel onionLabel;
    protected JLabel potatoLabel;
    protected JTextArea tomatoTextArea;
    protected JTextArea onionTextArea;
    protected JTextArea potatoTextArea;

    private JMenuItem pauseMenuItem;

    protected JScrollPane scrollPane;
    protected JScrollBar scrollbar;

    protected JButton faster;
    protected JButton slower;
    protected JButton reset;

    protected JPanel inventoryPanel;

    public View(Model m) {
        super();

        this.m = m;
        this.mainPanel = new JPanel(new BorderLayout());
        this.gridPanel = new JPanel(new GridLayout(10, 10));
        this.infoPanel = new JPanel(new GridBagLayout());
        this.tomatoLabel = new JLabel("Tomates :");
        this.inventoryPanel = new JPanel(new GridBagLayout());
        this.onionLabel = new JLabel("Oignons :");
        this.potatoLabel = new JLabel("Patates :");
        this.tomatoTextArea = new JTextArea();
        this.tomatoTextArea.setText("   10");
        this.onionTextArea = new JTextArea();
        this.potatoTextArea = new JTextArea();
        this.scrollbar=new JScrollBar();
        scrollbar.setBounds(200,150,150,150);
        this.pauseMenuItem = new JMenuItem("Pause");

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
        this.inventoryPanel = new JPanel(new GridBagLayout());
        this.tomatoLabel = new JLabel("Tomates :");
        this.onionLabel = new JLabel("Oignons :");
        this.potatoLabel = new JLabel("Patates :");
        this.tomatoTextArea = new JTextArea();
        this.onionTextArea = new JTextArea();
        this.potatoTextArea = new JTextArea();
        this.scrollbar=new JScrollBar();
        scrollbar.setBounds(200,150,150,150);
        this.pauseMenuItem = new JMenuItem("Pause");

        build();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });
    }

    public void updatePauseMenuItem() {
        if (m.isPaused()) {
            pauseMenuItem.setText("Play");
        } else {
            pauseMenuItem.setText("Pause");
        }
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

        // Création de la barre de menu
        JMenuBar menuBar = new JMenuBar();

        // Création du menu "Game"
        JMenu fileMenu = new JMenu("Game");

        // Création des éléments du menu "Fichier"
        JMenuItem PauseItem = new JMenuItem("Pause");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Quit");

        // Ajout des éléments au menu "Game"
        fileMenu.add(PauseItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator(); // Ajout d'un séparateur
        fileMenu.add(exitItem);

        ActionListener exitActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        PauseItem.addActionListener(e -> {
            if (PauseItem.getText().equals("Pause")) {
                PauseItem.setText("Play");
            } else {
                PauseItem.setText("Pause");
            }
        });

        // Ajout de l'ActionListener à exitItem
        exitItem.addActionListener(exitActionListener);

        // Ajout du menu "Game" à la barre de menu
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
        this.setJMenuBar(menuBar);

        slower = new JButton("-");
        reset = new JButton("=");
        faster = new JButton("+");

        this.faster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tpsRefresh= m.cooldown - 500;
                if (tpsRefresh<=100)
                {
                    tpsRefresh= 100;
                }
                m.setRefreshRate(tpsRefresh);
                System.out.println(e);
                // Ajoutez d'autres instructions à exécuter lorsque le bouton est cliqué
            }
        });

        this.reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.setRefreshRate(1000);
                System.out.println(e);
                // Ajoutez d'autres instructions à exécuter lorsque le bouton est cliqué
            }
        });

        this.slower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tpsRefresh= m.cooldown + 500;
                if (tpsRefresh>=5000)
                {
                    tpsRefresh= 5000;
                }
                m.setRefreshRate(tpsRefresh);
                System.out.println(e);
                // Ajoutez d'autres instructions à exécuter lorsque le bouton est cliqué
            }
        });

        // Modification de l'infoPanel en utilisant un GridBagLayout
        inventoryPanel.setLayout(new GridBagLayout());

        // Création des contraintes pour la disposition GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST; // Alignement à gauche
        gbc.insets = new Insets(5, 5, 5, 5); // Espacement autour des composants

        // Ajout du JLabel "Tomates" et du JTextField associé
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 0; // Ligne 0
        gbc.gridwidth = 2; // Largeur de 2 cellules
        inventoryPanel.add(tomatoLabel,gbc);

        gbc.gridx = 1; // Colonne 1
        gbc.gridy = 0; // Ligne 0
        gbc.gridwidth = 1; // Largeur de 1 cellule
        gbc.weightx = 0.0; // Poids horizontal
        gbc.weighty = 1.0; // Poids vertical
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal
        tomatoTextArea.setEditable(false); // Désactive le champ de texte
        inventoryPanel.add(tomatoTextArea,gbc);


        // Ajout du JLabel "Oignons" et du JTextField associé
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 1; // Ligne 1
        gbc.gridwidth = 2; // Largeur de 2 cellules
        inventoryPanel.add(onionLabel,gbc);

        gbc.gridx = 1; // Colonne 1
        gbc.gridy = 1; // Ligne 1
        gbc.gridwidth = 1; // Largeur de 1 cellule
        gbc.weighty = 1.0; // Poids vertical
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal
        onionTextArea.setEditable(false); // Désactive le champ de texte
        onionTextArea.setText("   10  ");
        inventoryPanel.add(onionTextArea,gbc);

        // Ajout du JLabel "Patates" et du JTextField associé
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 2; // Ligne 2
        gbc.gridwidth = 2; // Largeur de 2 cellules
        inventoryPanel.add(potatoLabel,gbc);

        gbc.gridx = 1; // Colonne 1
        gbc.gridy = 2; // Ligne 2
        gbc.gridwidth = 1; // Largeur de 1 cellule
        gbc.weighty = 1.0; // Poids vertical
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal
        potatoTextArea.setEditable(false); // Désactive le champ de texte
        potatoTextArea.setText("   10  ");
        inventoryPanel.add(potatoTextArea,gbc);
/*
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 3; // Ligne 3
        gbc.gridwidth = 2; // Largeur de 2 cellules
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal
        gbc.anchor = GridBagConstraints.CENTER; // Alignement au centre
*/

        // Ajout du bouton "+"
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 4; // Ligne 4
        gbc.weightx = 1.0; // Poids horizontal
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal
        inventoryPanel.add(faster, gbc);

        // Ajout du bouton "="
        gbc.gridx = 1; // Colonne 1
        gbc.gridy = 4; // Ligne 4
        inventoryPanel.add(reset, gbc);

        // Ajout du bouton "-"
        gbc.gridx = 2; // Colonne 2
        gbc.gridy = 4; // Ligne 4
        inventoryPanel.add(slower, gbc);

        //this.scrollPane = new JScrollPane(infoPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ou _AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane = new JScrollPane(inventoryPanel);
        this.inventoryPanel.add(infoPanel);  //INFORMATIONS pour le moment je laisse ça comme ça, j'ai pas réussi à dissocier les boutons du scrollpane
        this.mainPanel.add(gridPanel, BorderLayout.CENTER);
        this.mainPanel.add(scrollPane, BorderLayout.EAST);
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
