import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import javax.swing.border.Border;

public class MainFrame extends JFrame implements MouseListener {
    private DataPanel dataPanel;
    private GraphPanel graphPanel;

    MainFrame() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        // add a panels to the main panel
        graphPanel = new GraphPanel();
        mainPanel.add(graphPanel, BorderLayout.EAST);
        dataPanel = new DataPanel(graphPanel);
        mainPanel.add(dataPanel, BorderLayout.WEST);
        graphPanel.addMouseListener(this);
        // add a borders for display panels
        Border etched = BorderFactory.createEtchedBorder(new Color(0xFF), new Color(0xFF719F));
        dataPanel.setBorder(etched);
        mainPanel.setBorder(etched);
        graphPanel.setBorder(etched);
        // add menu
        this.setJMenuBar(getjMenuBar());
        // add the main panel on the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(mainPanel);
        this.setPreferredSize(new Dimension(710, 520));
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public BufferedImage createImage(JPanel panel) {
        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.paint(g);
        return bi;
    }

    private JMenuBar getjMenuBar() {
        JMenu jMenu = new JMenu("About");
        JMenuItem menuItemAbout = new JMenuItem("Author");
        jMenu.add(menuItemAbout);
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);
        return jMenuBar;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Data.setBufferedImage(createImage(graphPanel));
        int c = Data.getBufferedImage().getRGB((int) mouseEvent.getX(), mouseEvent.getY());
        int red = (c & 0x00ff0000) >> 16;
        int green = (c & 0x0000ff00) >> 8;
        int blue = c & 0x000000ff;
        Color color = new Color(red, green, blue);
        Data.setColorOfP(color);
        System.out.print(Data.getColorOfP());

        GraphPanel graphPanel = (GraphPanel) mouseEvent.getSource();
        graphPanel.setRed(0);
        graphPanel.setGreen(0);
        graphPanel.setX(mouseEvent.getX());
        graphPanel.setY(mouseEvent.getY());
        graphPanel.setFlag(true);
        graphPanel.repaint();
        /*Test action from MainFrame: */
        System.out.println("____________________________________________________");
        System.out.println("Test action from MainFrame: ");
        System.out.println("graphPanel.x = " + graphPanel.getXCoordinate());
        System.out.println("graphPanel.y = " + graphPanel.getYCoordinate());
        System.out.println("graphPanel.GraphWidth = " + graphPanel.getGraphWidth());
        System.out.println("graphPanel.GraphHeight = " + graphPanel.getGraphHeight());
        System.out.println("____________________________________________________");
        /*end of test action*/
        dataPanel.changeLabelX("x = " + new DecimalFormat("##0.0").format((graphPanel.getXCoordinate() - graphPanel.getGraphWidth() / 2) / 20));
        dataPanel.changeLabelY("y = " + new DecimalFormat("##0.0").format(-(graphPanel.getYCoordinate() - graphPanel.getGraphHeight() / 2) / 20));
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

}