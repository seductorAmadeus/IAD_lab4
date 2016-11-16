import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

public class MainFrame implements ItemListener {
    private Graph graph = new Graph();
    private JLabel labelXData = new JLabel("x = ");
    private JLabel labelYData = new JLabel("y = ");
    private double yPoint;
    private ArrayList<JCheckBox> checkBoxesList = new ArrayList<>();
    private JSpinner spinner;

    MainFrame() {
        JFrame mainFrame = new JFrame("Lab 4");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel graphPanel = new JPanel();
        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
        graphPanel.setPreferredSize(new Dimension(450, 250));

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setPreferredSize(new Dimension(250, 500));

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        checkBoxPanel.setPreferredSize(new Dimension(400, 100));

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS));
        comboBoxPanel.setPreferredSize(new Dimension(200, 100));

        addXCoordinateOnPanel(checkBoxPanel); // добавляем чекбоксы на панель
        comboBoxPanel.add(addYCoordinateOnPanel()); //  добавляем комбобоксы на панель

        graph.addMouseListener(new MainFrame.mListener());

        JLabel labelChoiceX = new JLabel("Выберите координату х для точки:");
        labelChoiceX.add(Box.createVerticalStrut(300));
        labelChoiceX.setAlignmentX(Component.LEFT_ALIGNMENT);

        dataPanel.add(labelChoiceX);

        dataPanel.add(comboBoxPanel);

        // добавляем метки на панель данных

        dataPanel.add(new JLabel("Выберите координату y для точки:"));
        dataPanel.add(checkBoxPanel);
        dataPanel.add(new JLabel("Выберите значение радиуса:"));
        EventSpinner();
        dataPanel.add(spinner);
        dataPanel.add(labelXData);
        dataPanel.add(labelYData);
        // добавляем кнопку на панель данных
        Font font = new Font("Arial", Font.CENTER_BASELINE, 15);
        JButton button = new JButton("Отметить точку");
        button.setFont(font);
        button.setPreferredSize(new Dimension(200, 200));
        button.setMargin(new Insets(30, 40, 30, 40));
        dataPanel.add(button);
        // добавляем spinner на панель

        dataPanel.add(Box.createVerticalStrut(70));
        ActionListener actionListener = new MainFrame.TestActionListener();
        button.addActionListener(actionListener);

        graphPanel.add(graph);
        // добавляем панели на главную панель
        mainPanel.add(graphPanel, BorderLayout.EAST);
        mainPanel.add(dataPanel, BorderLayout.WEST);
     /*   // рамка для отображения панелей
        Border etched = BorderFactory.createEtchedBorder(new Color(0xFF), new Color(0xFF719F));
        // выставляем рамки
        labelChoiceX.setBorder(etched);
        comboBoxPanel.setBorder(etched);
        checkBoxPanel.setBorder(etched);
        dataPanel.setBorder(etched);
        mainPanel.setBorder(etched);
        graphPanel.setBorder(etched);
     */
        // Добавляем меню.
        JMenu jMenu = new JMenu("About");
        JMenuItem menuItemAbout = new JMenuItem("Author");
        jMenu.add(menuItemAbout);
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);
        mainFrame.setJMenuBar(jMenuBar);
        // добавляем главную панель на окно
        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setPreferredSize(new Dimension(710, 480));
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }

    private JComboBox addYCoordinateOnPanel() {
        String[] dataCoordinates = new String[Data.getCountOfCoordinates()];
        for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
            dataCoordinates[i] = Double.toString(Data.getX(i));
        }
        return new JComboBox(dataCoordinates);
    }

    private void addXCoordinateOnPanel(JPanel checkBoxPanel) {
        for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
            String checkBoxName;
            JCheckBox checkBox;
            checkBoxName = Double.toString(Data.getY(i));
            checkBox = new JCheckBox(checkBoxName);
            checkBoxPanel.add(checkBox);
            checkBox.addItemListener(this);
            checkBoxesList.add(checkBox);
        }
    }

    private void EventSpinner() {
        SpinnerModel spinnerModel = new SpinnerNumberModel(4, 1, 10, 1);
        spinner = new JSpinner(spinnerModel);
        // запрещаем редактировование spinner-а.
        JTextField tempTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        tempTextField.setEditable(false);
        spinner.addChangeListener(event -> {
            graph.setRadius((int) ((JSpinner) event.getSource()).getValue());
            Paint(graph);
        });
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        JCheckBox checkBox = (JCheckBox) itemEvent.getItem();
        if (checkBox.isSelected())
            yPoint = Double.parseDouble(checkBox.getText());
        for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
            if (checkBoxesList.get(i) != checkBox)
                checkBoxesList.get(i).setEnabled(false);
        }
        if (!checkBox.isSelected())
            for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
                checkBoxesList.get(i).setEnabled(true);
            }
    }

    private void Paint(Graph graph) {
        if (!graph.getFlag()) {
            graph.setY((int) yPoint);
        }
        graph.repaint();
    }

    private class mListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            Graph graph = (Graph) mouseEvent.getSource();
            graph.setRed(0);
            graph.setGreen(0);
            graph.setX(mouseEvent.getX());
            graph.setY(mouseEvent.getY());
            graph.setFlag(true);
            graph.repaint();
            String pattern = "##0.0";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            labelXData.setText("x = " + decimalFormat.format((graph.getXCoordinate() - graph.getGraphWidth() / 2) / graph.getStep()));
            labelYData.setText("y = " + decimalFormat.format(-(graph.getYCoordinate() - graph.getGraphHeight() / 2) / graph.getStep()));
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

    private class TestActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            graph.setFlag(false);
            Paint(graph);
        }
    }
}