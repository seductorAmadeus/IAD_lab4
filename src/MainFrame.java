import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
        GraphPanel graphPanel = (GraphPanel) mouseEvent.getSource();
        graphPanel.setRed(0);
        graphPanel.setGreen(0);
        graphPanel.setX(mouseEvent.getX());
        graphPanel.setY(mouseEvent.getY());
        graphPanel.setFlag(true);
        graphPanel.repaint();
        String pattern = "##0.0";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        dataPanel.changeLabelX("x = " + decimalFormat.format((graphPanel.getXCoordinate() - graphPanel.getGraphWidth() / 2) / graphPanel.getStep()));
        dataPanel.changeLabelY("y = " + decimalFormat.format(-(graphPanel.getYCoordinate() - graphPanel.getGraphHeight() / 2) / graphPanel.getStep()));
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