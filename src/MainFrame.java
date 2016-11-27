import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class MainFrame extends JFrame {
    private DataPanel dataPanel;
    private GraphPanel graphPanel;

    MainFrame() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        // add a panels to the main panel
        graphPanel = new GraphPanel();
        mainPanel.add(graphPanel, BorderLayout.EAST);
        dataPanel = new DataPanel();
        mainPanel.add(dataPanel, BorderLayout.WEST);
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
        this.setPreferredSize(new Dimension(730, 520));
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
}