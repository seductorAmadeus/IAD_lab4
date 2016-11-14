import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

public class Main implements ItemListener {
    private Graph graph = new Graph();
    private JLabel labelXData = new JLabel("x = ");
    private JLabel label2 = new JLabel("y = ");
    private int radius = 4;
    private double xPoint;
    private double yPoint;
    private ArrayList<JCheckBox> checkBoxesList = new ArrayList<>();

    Main() {
        JFrame mainFrame = new JFrame("Lab 4");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel graphPanel = new JPanel(); // панель для графика
        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
        graphPanel.setPreferredSize(new Dimension(450, 250)); // раздвигаем панель для графика

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setPreferredSize(new Dimension(450, 500)); // раздвигаем панель для данных

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.X_AXIS));
        checkBoxPanel.setPreferredSize(new Dimension(400, 100)); // раздвигаем панель для чекбоксов

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.X_AXIS));
        comboBoxPanel.setPreferredSize(new Dimension(200, 100)); // раздвигаем панель для чекбоксов

        addXCoordinateOnPanel(checkBoxPanel); // добавляем чекбоксы на панель
        comboBoxPanel.add(addYCoordinateOnPanel());
        // добавляем чекбоксы и график на панель графика
        dataPanel.add(checkBoxPanel);
        dataPanel.add(comboBoxPanel);
        graphPanel.add(graph);
        // добавляем панели на главную панель
        mainPanel.add(graphPanel, BorderLayout.EAST);
        mainPanel.add(dataPanel, BorderLayout.WEST);
        // рамка для отображения панелей
        Border etched = BorderFactory.createEtchedBorder(new Color(0xFF), new Color(0xFF719F));
        // выставляем рамки
        comboBoxPanel.setBorder(etched);
        checkBoxPanel.setBorder(etched);
        dataPanel.setBorder(etched);
        mainPanel.setBorder(etched);
        graphPanel.setBorder(etched);
        /*
        JPanel panel2 = new JPanel();// панель для разделения области под график и области под данные
        JPanel panel3 = new JPanel(); // панель для разделения графика
        JPanel panel4 = new JPanel(); // панель для данных
        JPanel panel5 = new JPanel(); // панель для значений y
        
        panel3.add(graph);
        panel2.add(panel3);
        panel2.add(panel4);
        panel1.add(panel2);
        graph.addMouseListener(new Main.mListener());
       //panel4.add(labelXData);
       //panel4.add(label2);

        panel4.add(new JLabel("Выберите координату х для точки:"));
        DefaultListModel listModel = new DefaultListModel();


        final JList list = new JList(listModel);
        list.setSelectedIndex(0);
        JScrollPane scr = new JScrollPane(list);
        panel4.add(scr);
        EventList(list);

        panel4.add(new JLabel("Выберите координату y для точки:"));
        panel4.add(panel5, BorderLayout.CENTER);


        panel4.add(new JLabel("Введите значение radius:"));
        EventSpinner();
        panel3.add(graph);
        panel4.add(spinner);

        //panel4.add(panel6);
        JButton button = new JButton("Отметить точку");
        panel5.add(button);
        //panel6.add(button);
        ActionListener actionListener = new Main.TestActionListener();
        button.addActionListener(actionListener);
        mainFrame.getContentPane().add(panel2);
        */
        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setPreferredSize(new Dimension(900, 480));
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }

    private JComboBox addYCoordinateOnPanel() {
        String[] dataCoordinates = new String[Data.X.length];
        for (int i = 0; i < Data.X.length; i++) {
            dataCoordinates[i] = Double.toString(Data.X[i]);
        }
        return new JComboBox(dataCoordinates);
    }

    private void addXCoordinateOnPanel(JPanel checkBoxPanel) {
        // добавляем на панель чекбоксы
        for (int i = 0; i < Data.Y.length; i++) {
            String checkBoxName;
            JCheckBox checkBox;
            if (i < Data.Y.length) {
                checkBoxName = Double.toString(Data.Y[i]);
                checkBox = new JCheckBox(checkBoxName);
                checkBoxPanel.add(checkBox, BoxLayout.X_AXIS);
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
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.addChangeListener(e -> {
            radius = (int) ((JSpinner) e.getSource()).getValue();
            Paint(graph);
        });
    }

    private void EventList(JList list) // обработчик событий для JList
    {
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 1 || evt.getClickCount() == 2) {
                    String value = list.getSelectedValue().toString();
                    xPoint = Double.parseDouble(value);
                }
            }
        });
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        JCheckBox cb = (JCheckBox) ie.getItem();
        if (cb.isSelected())
            yPoint = Double.parseDouble(cb.getText());
        for (int i = 0; i < Data.Y.length; i++) {
            if (checkBoxesList.get(i) != cb)
                checkBoxesList.get(i).setEnabled(false);
        }
        if (!cb.isSelected())
            for (int i = 0; i < Data.X.length; i++) {
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
            label2.setText("y = " + decimalFormat.format(-(g.getYCoordinate() - g.h / 2) / g.step));
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