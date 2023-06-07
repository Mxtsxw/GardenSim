import Plants.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;

public class View extends JFrame implements Observer {

    protected Model model;
   
    protected JPanel mainPanel;

    protected JPanel rightPanel;

    protected CardLayout card;
    protected JPanel cardPanel;

    protected JPanel gridPanel;

    protected JTextArea moneyLevel;

    protected JMenuItem pauseMenuItem;

    protected ImageIcon moneyImage;
    protected ImageIcon meteoImage;

    private boolean isOpenedBoutique;

    public View(Model model) throws IOException {
        super();

        this.model = model;

        build();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });
    }

    public View() throws IOException {
        super();

        this.model = new Model();

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
    public void build() throws IOException {
        // Initialise les JPanels
        this.mainPanel = new JPanel(new BorderLayout());
        this.gridPanel = new JPanel(new GridLayout(10, 10));

        // Initialisation du cardlayout
        this.card = new CardLayout();
        cardPanel= new JPanel();
        cardPanel.setLayout(card);

        // Configuration du JFrame
        this.setTitle("Simulateur de Tomates");
        this.setSize(600, 500);

        // Ajout de la barre de menu
        JMenuBar menu = buildJMenuBar();
        this.setJMenuBar(menu);

        // Création du Panel pour la grille de parcel
        this.gridPanel = buildParcelPanel();

        //par défaut on affiche l'infoPanel, sinon on affiche boutiquePanel
        this.rightPanel = buildInfoPanel();
        cardPanel.add("INFO",this.rightPanel);
        JPanel rightPanel2= buildBoutiquePanel();
        cardPanel.add("BOUTIQUE",rightPanel2);

        this.mainPanel.add(this.gridPanel, BorderLayout.CENTER);
        this.mainPanel.add(cardPanel, BorderLayout.EAST);

        this.mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        this.add(mainPanel);
    }

    @Override
    public void update(Observable obs, Object obj) {
        //mise à jour de la grille
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int index = i * 10 + j;
                Parcel parcel = (Parcel) this.gridPanel.getComponent(index);
                try {
                    parcel.repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        //mise à jour du reste
        this.moneyLevel.setText(model.getStringArgent());

    }

    public void updatePauseMenuItem() {
        if (model.isPaused()) {
            pauseMenuItem.setText("Play");
        } else {
            pauseMenuItem.setText("Pause");
        }
    }

    /**
     * Retourne la barre de menu
     * @return
     */
    public JMenuBar buildJMenuBar(){
        // Création de la barre de menu
        JMenuBar menuBar = new JMenuBar();

        // Création du menu "Game"
        JMenu fileMenu = new JMenu("Game");

        // Création des éléments du menu "Fichier"
        JMenuItem PauseItem = new JMenuItem("Pause");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        JMenuItem exitItem = new JMenuItem("Quit");

        // Ajout des éléments au menu "Game"
        fileMenu.add(PauseItem);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
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

        ActionListener saveActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File directory = new File("./saves");
                directory.mkdirs();
                ModelSaver.saveModel(model,"./saves/saveGame");
            }
        };

        ActionListener loadActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model loadedModel = ModelLoader.loadModel("./saves/saveGame");
                if (loadedModel != null) {
                    // Remplacer le modèle actif par le modèle chargé
                    model = loadedModel;
                }
            }
        };

        // Ajout de l'ActionListener aux items
        exitItem.addActionListener(exitActionListener);
        saveItem.addActionListener(saveActionListener);
        loadItem.addActionListener(loadActionListener);

        // Ajout du menu "Game" à la barre de menu
        menuBar.add(fileMenu);

        // Création du menu "Plants"
        JMenu plantsMenu = new JMenu("Météo");

        // Création des éléments du menu "Plants"
        JMenuItem aleaItem = new JMenuItem("Aléatoire");
        JMenuItem sunItem = new JMenuItem("Soleil");// mode de jeu normal sans altération
        JMenuItem droughtItem = new JMenuItem("Sécheresse"); //case seche jaunis croissance complètement stopée
        JMenuItem winterItem = new JMenuItem("Neige"); //case seche blanchie croissance retardée
        JMenuItem rainItem = new JMenuItem("Pluvieux"); //case humide noircis croissance accélérée
        JMenuItem bugItem = new JMenuItem("bug"); //les fruits disparaissent ou pourrissent directement

        // Ajout des éléments au menu "Plants"
        plantsMenu.add(aleaItem);
        plantsMenu.add(sunItem);
        plantsMenu.add(droughtItem);
        plantsMenu.add(winterItem);
        plantsMenu.add(rainItem);
        plantsMenu.add(bugItem);

        // Ajout du menu "Plants" à la barre de menu
        menuBar.add(plantsMenu);

        return menuBar;
    }

    public JPanel buildBoutiquePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Boutons contrôle taux de rafraichissement
        JPanel resfreshPanel = buildRefreshRateAction();

        // Scroll Panel
        JScrollPane boutiqueScrollPane = buildScrollSelectionPanel();

        //lignes pour l'argent, la météo et le label de vitesse du jeu
        JPanel moneyPanel = buildMoney();
        JPanel boutiqueButton = buildButtonBoutique("retour"); //fermeture
        JPanel timePanel = buildLabelTime();

        panel.add(boutiqueScrollPane);
        panel.add(boutiqueButton);
        panel.add(moneyPanel);
        panel.add(timePanel);
        panel.add(resfreshPanel);

        return panel;
    }
    public JPanel buildInfoPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Boutons contrôle taux de rafraichissement
        JPanel resfreshPanel = buildRefreshRateAction();

        // Scroll Panel
        JScrollPane seedSelector = buildScrollSelectionPanel();

        //lignes pour l'argent, la météo et le label de vitesse du jeu
        JPanel moneyPanel = buildMoney();
        JPanel meteoPanel = buildMeteo();
        JPanel boutiqueButton = buildButtonBoutique("boutique"); //ouverture
        JPanel timePanel = buildLabelTime();

        panel.add(meteoPanel);
        panel.add(seedSelector);
        panel.add(boutiqueButton);
        panel.add(moneyPanel);
        panel.add(timePanel);
        panel.add(resfreshPanel);

        return panel;
    }
    public JPanel buildMeteo(){
        JPanel panel = new JPanel(new FlowLayout());
        JLabel meteoLabel = new JLabel("Météo :");

        try {
            this.meteoImage = new ImageIcon(getClass().getResource("/resources/images/cloud.png"));
            //sun, hot, cold, cloud, bug
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Définissez la taille souhaitée en pixels
        int largeur = 25;
        int hauteur = 25;

        // Redimensionnez l'image en utilisant la méthode getImage() et getScaledInstance()
        java.awt.Image imageRedimensionnee = this.meteoImage.getImage().getScaledInstance(largeur, hauteur, java.awt.Image.SCALE_SMOOTH);

        // Créez une nouvelle instance de l'icône en utilisant l'image redimensionnée
        this.meteoImage = new ImageIcon(imageRedimensionnee);

        JLabel meteoIcon= new JLabel();
        meteoIcon.setIcon(this.meteoImage);
        panel.add(meteoLabel);
        panel.add(meteoIcon);
        return panel;
    }

    public JPanel buildMoney(){
        JPanel panel = new JPanel(new FlowLayout());
        JLabel moneyLabel = new JLabel("Argent :");
        this.moneyLevel= new JTextArea(model.getStringArgent());
        moneyLevel.setEditable(false);
        try {
            this.moneyImage = new ImageIcon(getClass().getResource("/resources/images/Coin.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Définissez la taille souhaitée en pixels
        int largeur = 25;
        int hauteur = 25;

        // Redimensionnez l'image en utilisant la méthode getImage() et getScaledInstance()
        java.awt.Image imageRedimensionnee = this.moneyImage.getImage().getScaledInstance(largeur, hauteur, java.awt.Image.SCALE_SMOOTH);

        // Créez une nouvelle instance de l'icône en utilisant l'image redimensionnée
        this.moneyImage = new ImageIcon(imageRedimensionnee);
        JLabel imageMoney= new JLabel();
        imageMoney.setIcon(this.moneyImage);
        panel.add(moneyLabel);
        panel.add(moneyLevel);
        panel.add(imageMoney);

        return panel;
    }

    public JPanel buildButtonBoutique(String message){
        JPanel panel = new JPanel(new FlowLayout());
        JButton boutiqueButton = new JButton(message);

        boutiqueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (message=="boutique")
                {
                    card.show(cardPanel,"BOUTIQUE");
                    System.out.println("ouverture de la boutique");
                }
                else
                {
                    card.show(cardPanel, "INFO");
                    System.out.println("fermeture de la boutique");
                }
            }
        });
        panel.add(boutiqueButton);
        return panel;
    }

    public JPanel buildLabelTime(){
        JPanel panel = new JPanel(new FlowLayout());
        JLabel timeLabel = new JLabel("Vitesse du jeu :");
        panel.add(timeLabel);
        return panel;
    }

    /**
     * Retourne la section de jeu
     * @return
     */
    public JPanel buildRefreshRateAction(){
        JPanel panel = new JPanel(new FlowLayout());

        JButton slower = new JButton("-");
        JButton reset = new JButton("=");
        JButton faster = new JButton("+");

        faster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.lowerRefreshRate(500);
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRefreshRate(1000);
                // Ajoutez d'autres instructions à exécuter lorsque le bouton est cliqué
            }
        });

        slower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.increaseRefreshRate(500);
            }
        });

        panel.add(slower);
        panel.add(reset);
        panel.add(faster);

        return panel;
    }

    /**
     * Retourne la grille de parcelles
     * @return
     */
    public JPanel buildParcelPanel(){
        JPanel panel = new JPanel(new GridLayout(10, 10));

        Border blackLine = BorderFactory.createLineBorder(Color.black, 1);

        for (int i = 0; i < 100; i++) {
            Parcel parcel = new Parcel();

            int finalI = i;
            parcel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (model.getSelected() != null){
                        try {
                            parcel.setImagePlant(getSeedIcon(model.getSelected()));
                            model.setPlants((int) finalI /10, finalI %10, getSeedClass(model.getSelected()));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                            model.setPlants(0, 0, null);
                        }
                    }
                }
            });
            parcel.setBorder(blackLine);
            panel.add(parcel);
        }

        return panel;
    }

    public JScrollPane buildScrollSelectionPanel(){
        JPanel panel = new JPanel(new GridLayout((PlantNames.values().length/2), 2));

        for (PlantNames p: PlantNames.values())
        {
            JLabel label = new JLabel();
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            label.setPreferredSize(new Dimension(50, 50));
            //label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);

            try {
                label.setIcon(new ImageIcon(getSeedIcon(p)));
            } catch (IOException e) {
                label.setText(p.toString());
                e.printStackTrace();
            }

            // Mouse Listener
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Handle the mouse click event

                    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
                    label.setCursor(cursor);

                    model.setSelected(p);
                }
            });

            panel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(panel);

        return scrollPane;
    }

    public Image getSeedIcon(PlantNames name) throws IOException {
        switch (name){
            case CARROT:
                return Carrot.getImage();
            case SALAD:
                return Salad.getImage();
            case AUBERGINE:
                return Aubergine.getImage();
            case CAULIFLOWER:
                return Cauliflower.getImage();
            case CORN:
                return Corn.getImage();
            case MUSHROOM:
                return Mushroom.getImage();
            case ONION:
                return Onion.getImage();
            case PEPPER:
                return Pepper.getImage();
            case PINEAPPLE:
                return Pineapple.getImage();
            case STRAWBERRIES:
                return Strawberries.getImage();
            default:
                return ImageIO.read(getClass().getResource("/resources/images/data.png"));
        }
    }

    public Plants getSeedClass(PlantNames name) throws IOException {
        switch (name){
            case CARROT:
                return new Carrot();
            case SALAD:
                return new Salad();
            case AUBERGINE:
                return new Aubergine();
            case CAULIFLOWER:
                return new Cauliflower();
            case CORN:
                return new Corn();
            case MUSHROOM:
                return new Mushroom();
            case ONION:
                return new Onion();
            case PEPPER:
                return new Pepper();
            case PINEAPPLE:
                return new Pineapple();
            case STRAWBERRIES:
                return new Strawberries();
            default:
                return null;
        }
    }
}
