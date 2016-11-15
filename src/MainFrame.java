import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

public class MainFrame implements ItemListener {
    private Graph graph = new Graph();
    private JLabel labelXData = new JLabel("x = ");
    private JLabel labelYData = new JLabel("y = ");
    private int radius = 4;
    private double xPoint;
    private double yPoint;
    private ArrayList<JCheckBox> checkBoxesList = new ArrayList<>();
    private JSpinner spinner;

    MainFrame() {
        JFrame mainFrame = new JFrame("Lab 4");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel graphPanel = new JPanel(); // панель для графика
        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
        graphPanel.setPreferredSize(new Dimension(450, 250)); // раздвигаем панель для графика

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setPreferredSize(new Dimension(250, 500)); // раздвигаем панель для данных

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        checkBoxPanel.setPreferredSize(new Dimension(400, 100)); // раздвигаем панель для чекбоксов

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS));
        comboBoxPanel.setPreferredSize(new Dimension(200, 100)); // раздвигаем панель для комбобокса

        addXCoordinateOnPanel(checkBoxPanel); // добавляем чекбоксы на панель
        comboBoxPanel.add(addYCoordinateOnPanel()); //  добавляем комбобоксы на панель

        graph.addMouseListener(new MainFrame.mListener());
        // добавляем чекбоксы и график на панель графика

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
        String string = "Отметить точку";
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
        // рамка для отображения панелей
        Border etched = BorderFactory.createEtchedBorder(new Color(0xFF), new Color(0xFF719F));
        // выставляем рамки
        labelChoiceX.setBorder(etched);
        comboBoxPanel.setBorder(etched);
        checkBoxPanel.setBorder(etched);
        dataPanel.setBorder(etched);
        mainPanel.setBorder(etched);
        graphPanel.setBorder(etched);
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
            if (i < Data.getCountOfCoordinates()) {
                checkBoxName = Double.toString(Data.getY(i));
                checkBox = new JCheckBox(checkBoxName);
                checkBoxPanel.add(checkBox);
            } else {
                checkBoxName = "";
                checkBox = new JCheckBox(checkBoxName);
                checkBox.setVisible(true); // изменить алгоритм!!
                checkBoxPanel.add(checkBox);
            }
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
        spinner.addChangeListener(e -> {
            radius = (int) ((JSpinner) e.getSource()).getValue();
            Paint(graph);
        });
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        JCheckBox cb = (JCheckBox) ie.getItem();
        if (cb.isSelected())
            yPoint = Double.parseDouble(cb.getText());
        for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
            if (checkBoxesList.get(i) != cb)
                checkBoxesList.get(i).setEnabled(false);
        }
        if (!cb.isSelected())
            for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
                checkBoxesList.get(i).setEnabled(true);
            }
    }

    private void Paint(Graph graph) {
        graph.radius = radius;
        if (!Graph.getFlag()) {
            Graph.setX((int) xPoint);
            Graph.setY((int) yPoint);
        }
        graph.repaint();
    }

    private class mListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            Graph g = (Graph) e.getSource();
            g.red = 0;
            g.green = 0;
            Graph.setX(e.getX());
            Graph.setY(e.getY());
            Graph.setFlag(true);
            g.repaint();
            String pattern = "##0.0";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            labelXData.setText("x = " + decimalFormat.format((g.getXCoordinate() - g.w / 2) / g.step));
            labelYData.setText("y = " + decimalFormat.format(-(g.getYCoordinate() - g.h / 2) / g.step));
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }
    }

    private class TestActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Graph.setFlag(false);
            Paint(graph);
        }
    }
}