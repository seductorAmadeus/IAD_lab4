import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class MainFrame extends JFrame implements MouseListener {
    private DataPanel dataPanel;
    private GraphPanel graphPanel;

    MainFrame() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        graphPanel = new GraphPanel();
        mainPanel.add(graphPanel, BorderLayout.EAST);
        dataPanel = new DataPanel(graphPanel);
        mainPanel.add(dataPanel, BorderLayout.WEST);
        graphPanel.addMouseListener(this);
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

    public BufferedImage getGraphPanelScreenshot(JPanel panel) {
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

    private Color getColorOfPixel(int c) {
        int red = (c & 0x00ff0000) >> 16;
        int green = (c & 0x0000ff00) >> 8;
        int blue = c & 0x000000ff;
        return new Color(red, green, blue);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        // set color of pixel
        Data.setBufferedImage(getGraphPanelScreenshot(graphPanel));
        Data.setColorOfPixel(getColorOfPixel(Data.getBufferedImage().getRGB(mouseEvent.getX(), mouseEvent.getY())));
        System.out.print(Data.getColorOfPixel());

        GraphPanel graphPanel = (GraphPanel) mouseEvent.getSource();
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