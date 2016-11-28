import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class DataPanel extends JPanel implements ItemListener, ActionListener {
    private JLabel labelXData = new JLabel("x = ");
    private JLabel labelYData = new JLabel("y = ");
    private GraphPanel graphPanel;
    private double yPoint;
    private double xPoint;
    private DecimalFormat decimalFormat = new DecimalFormat("##0.0");

    DataPanel(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(230, 500));

        JLabel labelChoiceX = new JLabel("Choose the X-coordinate of a point:");
        labelChoiceX.add(Box.createVerticalStrut(300));
        labelChoiceX.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        checkBoxPanel.setPreferredSize(new Dimension(400, 100));

        // add checkboxes to the panel
        for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
            checkBoxPanel.add(addYCoordinateOnPanel(i));
        }

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS));
        comboBoxPanel.setPreferredSize(new Dimension(200, 100));
        //  add a combobox to the panel
        ActionListener actionListenerComboBox = e -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            String value = comboBox.getSelectedItem().toString();
            comboBox.actionPerformed(e);
            xPoint = Double.parseDouble(value);
            //fix it!
            this.changeLabelX("x = " + decimalFormat.format((graphPanel.getXCoordinate() - graphPanel.getGraphWidth() / 2) / graphPanel.getStep()));
            comboBox.actionPerformed(e);
        };

        JComboBox<String> comboBox;
        comboBox = addXCoordinateOnPanel();
        comboBox.addActionListener(actionListenerComboBox);
        comboBoxPanel.add(comboBox);

        this.add(labelChoiceX);
        this.add(comboBoxPanel);
        this.add(new JLabel("Choose the Y-coordinate of a point:"));
        this.add(checkBoxPanel);
        this.add(new JLabel("Choose the value of a radius:"));
        this.add(getSpinner());
        this.add(labelXData);
        this.add(labelYData);
        Font font = new Font("Arial", Font.CENTER_BASELINE, 14);
        JButton button = new JButton("Add the point");
        button.setFont(font);
        button.setMargin(new Insets(30, 40, 30, 40));
        // CHANGE IT!!!!
        // ActionListener actionListener = new GraphPanel().TestActionListener();

        button.addActionListener(this);
        // add the spacer on the data panel
        this.add(Box.createVerticalStrut(20));
        this.add(button);
        // add the spacer on the data panel
        this.add(Box.createVerticalStrut(100));
    }

    public void changeLabelX(String text) {
        labelXData.setText(text);
    }

    public void changeLabelY(String text) {
        labelYData.setText(text);
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
        if (checkBox.isSelected()) {
            yPoint = Double.parseDouble(checkBox.getText());
            //fix it!
            this.changeLabelY("y = " + decimalFormat.format(-(graphPanel.getYCoordinate() - graphPanel.getGraphHeight() / 2) / graphPanel.getStep()));
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

    private JComboBox<String> addXCoordinateOnPanel() {
        String[] dataCoordinates = new String[Data.getCountOfCoordinates() + 1];
        for (int i = 0; i < Data.getCountOfCoordinates(); i++) {
            dataCoordinates[i] = Double.toString(Data.getX(i));
        }
        dataCoordinates[Data.getCountOfCoordinates()] = "";
        JComboBox<String> jComboBox = new JComboBox<>(dataCoordinates);
        jComboBox.setSelectedIndex(Data.getCountOfCoordinates());
        return jComboBox;
    }


    private JCheckBox addYCoordinateOnPanel(int i) {
        String checkBoxName;
        checkBoxName = Double.toString(Data.getY(i));
        JCheckBox checkBox = new JCheckBox(checkBoxName);
        checkBox.addItemListener(this);
        Data.setCheckBox(i, checkBox);
        return checkBox;
    }

    private void Paint(GraphPanel graphPanel) {
        if (!graphPanel.getFlag()) {
            graphPanel.setY((int) yPoint);
            graphPanel.setX((int) xPoint);
        }
        graphPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        graphPanel.setFlag(false);
        Paint(graphPanel);
    }

}
