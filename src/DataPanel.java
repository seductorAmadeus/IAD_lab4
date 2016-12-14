import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class DataPanel extends JPanel implements ItemListener, ChangeListener, ActionListener, MouseListener {
    private JLabel labelXData = new JLabel("x = 0,0");
    private JLabel labelYData = new JLabel("y = 0,0");
    private GraphPanel graphPanel;
    private double yPoint;
    private double xPoint;
    private JSpinner valueR;

    DataPanel(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(230, 500));

        JLabel labelChoiceX = new JLabel("Choose the X-coordinate of a point:");
        labelChoiceX.add(Box.createVerticalStrut(300));
        labelChoiceX.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.X_AXIS));
        comboBoxPanel.setPreferredSize(new Dimension(200, 100));

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        checkBoxPanel.setPreferredSize(new Dimension(400, 100));

        for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
            checkBoxPanel.add(getCheckBox(i));
        }

        ActionListener actionListenerComboBox = e -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            String value = comboBox.getSelectedItem().toString();
            comboBox.actionPerformed(e);
            xPoint = Double.parseDouble(value);
            comboBox.actionPerformed(e);
        };

        JComboBox<String> comboBox;
        comboBox = getComboBox();
        comboBox.addActionListener(actionListenerComboBox);
        comboBoxPanel.add(comboBox);
        this.add(labelChoiceX);
        this.add(comboBoxPanel);
        this.add(new JLabel("Choose the Y-coordinate of a point:"));
        this.add(checkBoxPanel);
        this.add(new JLabel("Choose the value of a radius:"));
        this.add(SpinnerInitialize());
        this.add(labelXData);
        this.add(labelYData);
        Font font = new Font("Arial", Font.CENTER_BASELINE, 14);
        JButton addButton = new JButton("Add the point");
        addButton.setFont(font);
        addButton.addActionListener(this);
        addButton.addMouseListener(this);
        Data.setButton(addButton);
        JButton clearButton = new JButton("Clear graph");
        ActionListener clearAllPoints = e -> {

        };
        clearButton.addActionListener(clearAllPoints);
        clearButton.setFont(font);
        // add the spacer on the data panel
        this.add(Box.createVerticalStrut(20));
        this.add(addButton);
        this.add(clearButton);
        this.add(Box.createVerticalStrut(100));
    }

    public void changeLabelX(String text) {
        labelXData.setText(text);
    }

    public void changeLabelY(String text) {
        labelYData.setText(text);
    }

    private JPanel SpinnerInitialize() {
        JPanel SpinnerBox = new JPanel(new GridLayout(1, 2));
        valueR = new JSpinner(new SpinnerNumberModel(5, 1, 100, 1));
        valueR.addChangeListener(this);
        SpinnerBox.add(valueR);
        Data.setSpinner(valueR);
        return SpinnerBox;
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        graphPanel.EventSpinner(valueR);
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        JCheckBox checkBox = (JCheckBox) itemEvent.getItem();
        if (checkBox.isSelected()) {
            yPoint = Double.parseDouble(checkBox.getText());
        } else {
            yPoint = 0.0;
        }
        for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
            if (Data.getCheckBox(i) != checkBox)
                Data.getCheckBox(i).setEnabled(false);
        }
        if (!checkBox.isSelected())
            for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
                Data.getCheckBox(i).setEnabled(true);
            }
    }

    private JComboBox<String> getComboBox() {
        String[] dataCoordinates = new String[Data.getCountOfCoordinates()];
        for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
            dataCoordinates[i] = Double.toString(Data.getX(i));
        }
        JComboBox<String> jComboBox = new JComboBox<>(dataCoordinates);
        jComboBox.setSelectedIndex(4); // set "0.0"
        return jComboBox;
    }

    private JCheckBox getCheckBox(int i) {
        String checkBoxName;
        checkBoxName = Double.toString(Data.getY(i));
        JCheckBox checkBox = new JCheckBox(checkBoxName);
        checkBox.addItemListener(this);
        Data.setCheckBox(i, checkBox);
        return Data.getCheckBox(i);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        graphPanel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        graphPanel.repaint();
        changeLabelX("x = " + xPoint);
        changeLabelY("y = " + yPoint);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

}
