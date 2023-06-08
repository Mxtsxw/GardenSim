import Plants.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;

public class View extends JFrame implements Observer {

    protected Model model;
   
    protected JPanel mainPanel;

    protected JPanel rightPanel;

    protected JScrollPane seedSelector;

    protected CardLayout card;
    protected JPanel cardPanel;

    protected Map<PlantNames, JLabel> labelMap;

    protected JPanel cardContainer;

    protected JPanel gridPanel;

    protected JTextArea moneyLevel;

    protected JLabel moneyLabel;

    protected JMenuItem pauseMenuItem;

    protected ImageIcon moneyImage;
    protected ImageIcon meteoImage;

    private JLabel selectedLabel;

    protected Inventory inventaire = new Inventory();
    private JLabel meteoIcon;

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

        this.model = new Model(new Weather());

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
        this.setSize(750, 500);

        // Ajout de la barre de menu
        JMenuBar menu = buildJMenuBar();
        this.setJMenuBar(menu);

        // Création du Panel pour la grille de parcel
        this.gridPanel = buildParcelPanel();

        //par défaut on affiche l'infoPanel, sinon on affiche boutiquePanel
        this.rightPanel = buildInfoPanel(model.getArgent());
        cardPanel.add("INFO",this.rightPanel);
        JPanel rightPanel2= buildBoutiquePanel(model.getArgent());
        cardPanel.add("BOUTIQUE",rightPanel2);

        this.mainPanel.add(this.gridPanel, BorderLayout.CENTER);
        this.mainPanel.add(cardPanel, BorderLayout.EAST);

        this.mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        this.add(mainPanel);
    }

    @Override
    public void update(Observable obs, Object obj) {
        if (obs instanceof Model) {
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

            //mise à jour de l'argent
            //this.moneyLevel.setText(model.getStringArgent());
            this.moneyLabel.setText("Argent: "+model.getStringArgent());

            //mise à jour de l'affichage de l'inventaire
            updateLabels();
        }

        if (obs instanceof Weather){
            updateMeteoLabel();
            System.out.println("Log: Météo mise à jour → " + ((Weather) obs).getWeatherState());
        }
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
        JMenuItem sunItem = new JMenuItem("Soleil");
        JMenuItem droughtItem = new JMenuItem("Sécheresse");
        JMenuItem winterItem = new JMenuItem("Neige");
        JMenuItem rainItem = new JMenuItem("Pluvieux");

        final Model model = this.model;

        // Create ActionListener for each JMenuItem
        ActionListener aleaListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setWeatherRandomState(true);
                System.out.println("Log: Sélection méteo aléatoire");
            }
        };

        ActionListener sunListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setWeatherRandomState(false);
                model.setWeather(Weather.WeatherNames.SOLEIL);
            }
        };

        ActionListener droughtListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setWeatherRandomState(true);
                model.setWeather(Weather.WeatherNames.SECHERESSE);
            }
        };

        ActionListener winterListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setWeatherRandomState(true);
                model.setWeather(Weather.WeatherNames.NEIGE);
            }
        };

        ActionListener rainListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setWeatherRandomState(true);
                model.setWeather(Weather.WeatherNames.PLUVIEUX);
            }
        };

        // Add ActionListener to each JMenuItem
        aleaItem.addActionListener(aleaListener);
        sunItem.addActionListener(sunListener);
        droughtItem.addActionListener(droughtListener);
        winterItem.addActionListener(winterListener);
        rainItem.addActionListener(rainListener);

        // Ajout des éléments au menu "Plants"
        plantsMenu.add(aleaItem);
        plantsMenu.add(sunItem);
        plantsMenu.add(droughtItem);
        plantsMenu.add(winterItem);
        plantsMenu.add(rainItem);

        // Ajout du menu "Plants" à la barre de menu
        menuBar.add(plantsMenu);


        return menuBar;
    }

    public JPanel buildBoutiquePanel(int money){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Boutons contrôle taux de rafraichissement
        JPanel resfreshPanel = buildRefreshRateAction();

        // Scroll Panel
        JScrollPane boutiqueScrollPane = buildScrollBoutiquePanel();

        //lignes pour l'argent, la météo et le label de vitesse du jeu
        JPanel moneyPanel = buildMoney(money);
        JPanel boutiqueButton = buildButtonBoutique("retour"); //fermeture
        JPanel timePanel = buildLabelTime();

        panel.add(boutiqueScrollPane);
        panel.add(boutiqueButton);
        panel.add(moneyPanel);
        panel.add(timePanel);
        panel.add(resfreshPanel);

        return panel;
    }
    public JPanel buildInfoPanel(int money){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Boutons contrôle taux de rafraichissement
        JPanel resfreshPanel = buildRefreshRateAction();

        // Scroll Panel
        seedSelector = buildScrollSelectionPanel();

        //lignes pour l'argent, la météo et le label de vitesse du jeu
        //JPanel moneyPanel = buildMoney(money);
        JPanel meteoPanel = buildMeteo();
        JPanel boutiqueButton = buildButtonBoutique("boutique"); //ouverture
        JPanel timePanel = buildLabelTime();

        panel.add(meteoPanel);
        panel.add(seedSelector);
        panel.add(boutiqueButton);
        //panel.add(moneyPanel);
        panel.add(timePanel);
        panel.add(resfreshPanel);

        return panel;
    }
    public JPanel buildMeteo(){
        JPanel panel = new JPanel(new FlowLayout());
        JLabel meteoLabel = new JLabel("Météo :");

        meteoIcon= new JLabel();
        updateMeteoLabel();
        panel.add(meteoLabel);
        panel.add(meteoIcon);
        return panel;
    }

    private void updateMeteoLabel() {
        try {
            this.meteoImage = Weather.getWeatherIcon(model.getMeteo().getWeatherState());
            //sun, hot, cold, cloud, bug
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Définissez la taille souhaitée en pixels
        int largeur = 25;
        int hauteur = 25;

        // Redimensionnez l'image en utilisant la méthode getImage() et getScaledInstance()
        Image imageRedimensionnee = this.meteoImage.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);

        // Créez une nouvelle instance de l'icône en utilisant l'image redimensionnée
        meteoIcon.setIcon(new ImageIcon(imageRedimensionnee));
    }

    public JPanel buildMoney(int money){
        JPanel panel = new JPanel(new FlowLayout());
        this.moneyLabel = new JLabel("Argent: "+money);
        //this.moneyLevel= new JTextArea(model.getStringArgent());
        //moneyLevel.setEditable(false);
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
        //panel.add(moneyLevel);
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
                    System.out.println("Log: Ouverture de la boutique");
                }
                else
                {
                    card.show(cardPanel, "INFO");
                    System.out.println("Log: Fermeture de la boutique");
                }
            }
        });
        panel.add(boutiqueButton);
        return panel;
    }

    public JPanel buildLabelTime(){
        JPanel panel = new JPanel(new FlowLayout());
        JLabel timeLabel = new JLabel("Vitesse du jeu");
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
                    if (parcel.getPlant() != null){
                        if (parcel.getPlant().getGerminationState() == Plants.GerminationState.GERMINATED){
                            JPopupMenu menu = buildPopupMenu(parcel, finalI, true);
                            menu.show(parcel, e.getX(), e.getY());
                        } else {
                            JPopupMenu menu = buildPopupMenu(parcel, finalI, false);
                            menu.show(parcel, e.getX(), e.getY());
                        }
                    } else {
                        planter(parcel, finalI);
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

        // Create a map to store the labels for each plant
        this.labelMap = new HashMap<>();

        for (PlantNames p: PlantNames.values())
        {
            JLabel label = new JLabel(String.valueOf(inventaire.getNombrePlantes(p)));
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            label.setPreferredSize(new Dimension(50, 50));
            //label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);

            label.setOpaque(true);
            label.setBackground(null);


            // ajout du label à la map
            this.labelMap.put(p, label);

            try {
                label.setIcon(new ImageIcon(Plants.getSeedIcon(p)));
            } catch (IOException e) {
                label.setText(p.toString());
                e.printStackTrace();
            }

            // Mouse Listener
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Handle the mouse click event

                    if (inventaire.getNombrePlantes(p)>0) {
                        model.setSelected(p);
                        System.out.println("Log: Plante [" + p + "] sélectionnée.");

                        if (selectedLabel != null) {
                            selectedLabel.setBackground(null);
                        }

                        label.setBackground(Color.lightGray);
                        selectedLabel = label;
                    }
                    else {
                        System.out.println("Log: [" + p + "] sélectionné sans graines.");
                        if (selectedLabel != null) {
                            selectedLabel.setBackground(null);
                        }
                        model.resetSelection();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
                    label.setCursor(cursor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    Cursor cursor = Cursor.getDefaultCursor();
                    label.setCursor(cursor);
                }
            });

            panel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    // Update the labels with the inventory quantities
    public void updateLabels() {
        for (PlantNames p : PlantNames.values()) {
            JLabel label = labelMap.get(p);
            if (label != null) {
                label.setText(String.valueOf(inventaire.getNombrePlantes(p)));
            }
        }
    }

    public JScrollPane buildScrollBoutiquePanel(){
        JPanel panel = new JPanel(new GridLayout((PlantNames.values().length/2), 2));

        for (PlantNames p: PlantNames.values()) //pour chaque plantes
        {
            //image de la pièce
            try {
                this.moneyImage = new ImageIcon(getClass().getResource("/resources/images/Coin.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Définissez la taille souhaitée en pixels
            int largeur = 15;
            int hauteur = 15;
            // Redimensionnez l'image en utilisant la méthode getImage() et getScaledInstance()
            java.awt.Image imageRedimensionnee = this.moneyImage.getImage().getScaledInstance(largeur, hauteur, java.awt.Image.SCALE_SMOOTH);

            // Créez une nouvelle instance de l'icône en utilisant l'image redimensionnée
            this.moneyImage = new ImageIcon(imageRedimensionnee);
            JLabel imageMoney= new JLabel();
            imageMoney.setIcon(this.moneyImage);

            //int price = getSeedPrice(p);
            //int price = Onion.getPrice();
            int price =Plants.getPriceByName(p);
            String priceString = Integer.toString(price);
            JLabel label = new JLabel(priceString);
            //label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            label.setPreferredSize(new Dimension(75, 50));
            //label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);

            try {
                label.setIcon(new ImageIcon(Plants.getSeedIcon(p)));
            } catch (IOException e) {
                label.setText(p.toString());
                e.printStackTrace();
            }

            // Mouse Listener
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Handle the mouse click event
                    System.out.print("Log: ["+ p + "] acheté pour : "+Plants.getPriceByName(p));
                    if (model.isEnoughMoney(Plants.getPriceByName(p))){
                        model.diminution(Plants.getPriceByName(p));
                        System.out.println("| Argent Restant : " + model.getStringArgent());
                        inventaire.ajouterPlante(p);
                    }
                    else {
                        System.out.println("Log: Echec achat ["+p+"] pas assez d'argent!");
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
                    label.setCursor(cursor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    Cursor cursor = Cursor.getDefaultCursor();
                    label.setCursor(cursor);
                }
            });

            panel.add(label);
            panel.add(imageMoney);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    public JPopupMenu buildPopupMenu(Parcel parcel, int index, boolean state){
        // Create a JPopupMenu
        JPopupMenu popupMenu = new JPopupMenu();

        // Create menu items
        JMenuItem menuItem1 = new JMenuItem("Replanter");
        JMenuItem menuItem2 = new JMenuItem("Récolter");

        menuItem2.setEnabled(state);

        // Add action listeners to the menu items
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to be executed when Option 1 is selected
                planter(parcel, index);
            }
        });

        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to be executed when Option 2 is selected
                recolt(parcel, index);
            }
        });

        // Add menu items to the popup menu
        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);

        return popupMenu;
    }

    public void planter(Parcel parcel, int i){
        if (model.getSelected() != null){
            try {
                PlantNames p = model.getSelected();
                if (inventaire.getNombrePlantes(p)>0)
                {
                    inventaire.removePlante(p);
                    Plants plant = Plants.getSeedClass(p);
                    parcel.setImagePlant(Plants.getSeedIcon(p));
                    model.setPlants((int) i /10, i %10, plant);
                    parcel.setPlant(plant);
                }
                else
                {
                    model.resetSelection();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
                model.setPlants(0, 0, null);
            }
        }
    }

    public void recolt(Parcel parcel, int i) {
        if (model.getPlants()[(int) i / 10][i % 10] != null) {
            // Perform the harvesting logic here
            model.getPlants()[(int) i / 10][i % 10] = null;
            int price= Plants.getPriceByName(PlantNames.AUBERGINE)+5;
            model.augmentation(2*Plants.getPrice());
            // Reset the parcel's plant to null after harvesting
            model.setPlants((int) i / 10, i % 10, null);
            System.out.println("Log: ["+parcel.getPlant().getName()+"] recolté pour "+ price + " | Argent : " + model.getArgent());
            parcel.setPlant(null);
        }
    }
}
