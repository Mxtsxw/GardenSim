import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {

    protected Model m;
    protected JComponent panel;

    public View(Model m) {
        super();

        this.m = m;
        this.panel = new JPanel (new GridLayout(10,10));

        build();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });
    }

    public View(){
        super();

        this.m = new Model();
        this.panel = new JPanel (new GridLayout(10,10));

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
     * Se charge de la création de la création
     */
    public void build() {

        setTitle("Tomatoes Simulator");
        setSize(400, 400);

        Border blackline = BorderFactory.createLineBorder(Color.black,1);

        for(int i = 0; i<100;i++){
            JComponent pcase = new Parcel();
            pcase.setBorder(blackline);
            this.panel.add(pcase);
        }

        panel.setBorder(blackline);
        add(panel);
    }

    @Override
    public void update(Observable obs, Object obj){
        for(int i=0; i<10; i++)
            for(int j=0; j<10; j++) {
                int index = i * 10 + j;
                if (m.grid[i][j])
                    this.panel.getComponent(index).setBackground(Color.RED);
                else
                    this.panel.getComponent(index).setBackground(Color.WHITE);
            }
    }
}
