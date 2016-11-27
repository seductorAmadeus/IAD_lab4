import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.border.Border;

public class MainFrame extends JFrame implements ItemListener {
    private GraphPanel graphPanel = new GraphPanel();
    private JLabel labelXData = new JLabel("x = ");
    private JLabel labelYData = new JLabel("y = ");
    private double yPoint;

    MainFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setPreferredSize(new Dimension(250, 500));

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        checkBoxPanel.setPreferredSize(new Dimension(400, 100));

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS));
        comboBoxPanel.setPreferredSize(new Dimension(200, 100));

        // add checkboxes to the panel
        for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
            checkBoxPanel.add(addYCoordinateOnPanel(i));
        }
        //  add a combobox to the panel
        comboBoxPanel.add(addXCoordinateOnPanel());

        this.graphPanel.addMouseListener(new mouseListener());

        JLabel labelChoiceX = new JLabel("Choose the X-coordinate of a point:");
        labelChoiceX.add(Box.createVerticalStrut(300));
        labelChoiceX.setAlignmentX(Component.LEFT_ALIGNMENT);

        dataPanel.add(labelChoiceX);
        dataPanel.add(comboBoxPanel);

        // add labels to the data panel
        dataPanel.add(new JLabel("Choose the Y-coordinate of a point:"));
        dataPanel.add(checkBoxPanel);
        dataPanel.add(new JLabel("Choose the value of a radius:"));
        dataPanel.add(labelXData);
        dataPanel.add(labelYData);
        // add a button on the data panel
        Font font = new Font("Arial", Font.CENTER_BASELINE, 14);
        JButton button = new JButton("Add the point");
        button.setFont(font);
        button.setMargin(new Insets(30, 40, 30, 40));
        dataPanel.add(button);
        // add spinner to the data panel
        dataPanel.add(getSpinner());
        // add the spacer on the data panel
        dataPanel.add(Box.createVerticalStrut(100));

        // CHANGE IT!!!!
        // ActionListener actionListener = new GraphPanel().TestActionListener();
        button.addActionListener(new GraphPanel());

        // add a panels to the main panel
        mainPanel.add(graphPanel, BorderLayout.EAST);
        mainPanel.add(dataPanel, BorderLayout.WEST);
        // add a borders for display panels
        Border etched = BorderFactory.createEtchedBorder(new Color(0xFF), new Color(0xFF719F));
        labelChoiceX.setBorder(etched);
        comboBoxPanel.setBorder(etched);
        checkBoxPanel.setBorder(etched);
        dataPanel.setBorder(etched);
        mainPanel.setBorder(etched);
        graphPanel.setBorder(etched);
        // add menu
        JMenu jMenu = new JMenu("About");
        JMenuItem menuItemAbout = new JMenuItem("Author");
        jMenu.add(menuItemAbout);
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);
        this.setJMenuBar(jMenuBar);
        // add the main panel on the frame
        this.getContentPane().add(mainPanel);
        this.setPreferredSize(new Dimension(730, 520));
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private JComboBox<String> addXCoordinateOnPanel() {
        String[] dataCoordinates = new String[Data.getCountOfCoordinates()];
        for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
            dataCoordinates[i] = Double.toString(Data.getX(i));
        }
        return new JComboBox<>(dataCoordinates);
    }

    private JCheckBox addYCoordinateOnPanel(int i) {
        String checkBoxName;
        JCheckBox checkBox;
        checkBoxName = Double.toString(Data.getY(i));
        checkBox = new JCheckBox(checkBoxName);
        checkBox.addItemListener(this);
        Data.setCheckBox(i, checkBox);
        return checkBox;
    }

    private JSpinner getSpinner() {
        SpinnerModel spinnerModel = new SpinnerNumberModel(4, 1, 10, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        // forbid spinner editing.
        JTextField tempTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        tempTextField.setEditable(false);
        spinner.addChangeListener(event -> {
            graphPanel.setRadius((int) ((JSpinner) event.getSource()).getValue());
            if (!graphPanel.getFlag()) {
                graphPanel.setY((int) yPoint);
            }
            graphPanel.repaint();
        });
        return spinner;
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        JCheckBox checkBox = (JCheckBox) itemEvent.getItem();
        if (checkBox.isSelected())
            yPoint = Double.parseDouble(checkBox.getText());
        for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
            if (Data.getCheckBox(i) != checkBox)
                Data.getCheckBox(i).setEnabled(false);
        }
        if (!checkBox.isSelected())
            for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
                Data.getCheckBox(i).setEnabled(true);
            }
    }

    private class mouseListener implements MouseListener {
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
            labelXData.setText("x = " + decimalFormat.format((graphPanel.getXCoordinate() - graphPanel.getGraphWidth() / 2) / graphPanel.getStep()));
            labelYData.setText("y = " + decimalFormat.format(-(graphPanel.getYCoordinate() - graphPanel.getGraphHeight() / 2) / graphPanel.getStep()));
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
}