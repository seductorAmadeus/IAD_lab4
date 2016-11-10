import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

public class Test implements ItemListener {
    private Graph graph = new Graph();
    private JLabel label1 = new JLabel("x = ");
    private JLabel label2 = new JLabel("y = ");
    private JSpinner spinner;
    private int radius = 4;
    private double xPoint;
    private double yPoint;
    private ArrayList<JCheckBox> checkBoxesList = new ArrayList<>();

    Test() {
        JFrame frame = new JFrame("Lab 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        JPanel panel2 = new JPanel();// панель для разделения области под график и области под данные
        panel2.setLayout(new GridLayout(1, 2));
        JPanel panel3 = new JPanel(); // панель для разделения графика
        panel3.setLayout(new GridLayout());
        JPanel panel4 = new JPanel(); // панель для данных
        panel4.setLayout(new GridLayout(5, 2));
        JPanel panel5 = new JPanel(); // панель для значений y

        panel5.setLayout(new GridLayout(3, Data.X.length / 3));
        JPanel panel6 = new JPanel(); // панель для кнопки
        panel6.setLayout(new GridLayout(2, 4));

        panel1.add(panel2, BorderLayout.CENTER);
        panel2.add(panel3, BorderLayout.CENTER);
        panel2.add(panel4, BorderLayout.CENTER);

        panel3.add(graph);
        graph.addMouseListener(new Test.mListener());
        panel4.add(label1);
        panel4.add(label2);
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

        panel4.add(new JLabel("Введите значение R:"));
        EventSpinner();
        panel3.add(graph);
        panel4.add(spinner);

        panel4.add(panel6);
        JButton button = new JButton("Отметить точку");
        panel6.add(button);
        ActionListener actionListener = new Test.TestActionListener();
        button.addActionListener(actionListener);

        frame.getContentPane().add(panel1);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void EventSpinner() {
        SpinnerModel spinnerModel = new SpinnerNumberModel(4, 1, 10, 1);
        spinner = new JSpinner(spinnerModel);
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
        graph.R = radius;
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