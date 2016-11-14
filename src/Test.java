import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

public class Test implements ItemListener {
    private Graph graph = new Graph();
    private JLabel label1 = new JLabel("x = ");
    private JLabel label2 = new JLabel("y = ");
    private int radius = 4;
    private double xPoint;
    private double yPoint;
    private ArrayList<JCheckBox> checkBoxesList = new ArrayList<>();

    Test() {
        JFrame mainFrame = new JFrame("Lab 4");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel mainGraphPanel = new JPanel(); // панель для панели для графика
        // напрямую не добавить, иначе панель не раздвинется.
        mainGraphPanel.setLayout(new BoxLayout(mainGraphPanel, BoxLayout.X_AXIS));

        JPanel graphPanel = new JPanel(); // панель для графика
        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.X_AXIS));
        graphPanel.setPreferredSize(new Dimension(450, 250)); // раздвигаем панель для графика
        // (раздвигая при этом панель под панель для графика
        mainGraphPanel.add(graphPanel);

        mainPanel.add(mainGraphPanel, BorderLayout.EAST);
        graphPanel.add(graph);

        // рамка для отображения панелей
        Border etched = BorderFactory.createEtchedBorder(new Color(0xFF0045), null);
        /*выставляем рамки*/
        mainPanel.setBorder(etched);
        mainGraphPanel.setBorder(etched);
        graphPanel.setBorder(etched);
        /*JCheckBox checkbox = new JCheckBox();
        JCheckBox checkbox1 = new JCheckBox();
        graphPanel.add(checkbox);
        graphPanel.add(checkbox1);
        // end
        /*test code
        JButton button1 = new JButton("12345");
        JButton button2 = new JButton("98765");
        mainPanel.add(button1, BorderLayout.EAST);
        mainPanel.add(button2, BorderLayout.WEST);
        // end of test code
        */
        /* JButton button = new JButton("first Button");
        mainPanel.add(button, BorderLayout.EAST);

        /*JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.X_AXIS));

        dataPanel.add(new JButton("Test"));
        mainPanel.add(dataPanel, BorderLayout.WEST);
         /*
        JPanel panel2 = new JPanel();// панель для разделения области под график и области под данные
        JPanel panel3 = new JPanel(); // панель для разделения графика
        JPanel panel4 = new JPanel(); // панель для данных
        JPanel panel5 = new JPanel(); // панель для значений y

        panel1.setLayout(new BorderLayout());
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));

        panel3.add(graph);

        panel2.add(panel3);
        panel2.add(panel4);
        panel1.add(panel2);
        graph.addMouseListener(new Test.mListener());

       //panel4.add(label1);
       //panel4.add(label2);
        panel4.add(new JLabel("Выберите координату х для точки:"));

        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i < Data.X.length; i++) {
            String data = Double.toString(Data.X[i]);
            listModel.addElement(data);
        }

        final JList list = new JList(listModel);
        list.setSelectedIndex(0);
        JScrollPane scr = new JScrollPane(list);
        panel4.add(scr);
        EventList(list);

        panel4.add(new JLabel("Выберите координату y для точки:"));
        panel4.add(panel5, BorderLayout.CENTER);
        for (int i = 0; i < Data.Y.length; i++) {
            String data = Double.toString(Data.Y[i]);
            JCheckBox jcb = new JCheckBox(data);
            panel5.add(jcb);
            jcb.addItemListener(this);
            checkBoxesList.add(jcb);
        }

        panel4.add(new JLabel("Введите значение radius:"));
        EventSpinner();
        panel3.add(graph);
        panel4.add(spinner);

        //panel4.add(panel6);
        JButton button = new JButton("Отметить точку");
        panel5.add(button);
        //panel6.add(button);
        ActionListener actionListener = new Test.TestActionListener();
        button.addActionListener(actionListener);

        mainFrame.getContentPane().add(panel2);
        //
        */
        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setPreferredSize(new Dimension(800,600));
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
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
        if (!Graph.flag) {
            Graph.x = (int) xPoint;
            Graph.y = (int) yPoint;
        }
        graph.repaint();
    }

    private class mListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            Graph g = (Graph) e.getSource();
            g.red = 0;
            g.green = 0;
            g.x = e.getX();
            g.y = e.getY();
            Graph.flag = true;
            g.repaint();
            String pattern = "##0.0";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            label1.setText("x = " + decimalFormat.format((g.x - g.w / 2) / g.step));
            label2.setText("y = " + decimalFormat.format(-(g.y - g.h / 2) / g.step));

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
            Graph.flag = false;
            Paint(graph);
        }
    }
}