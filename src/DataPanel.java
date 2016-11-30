import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class DataPanel extends JPanel implements ItemListener, ActionListener, MouseListener {
    private JLabel labelXData = new JLabel("x = 0,0");
    private JLabel labelYData = new JLabel("y = 0,0");
    private GraphPanel graphPanel;
    private double yPoint;
    private double xPoint;

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
        this.add(getSpinner());
        this.add(labelXData);
        this.add(labelYData);
        Font font = new Font("Arial", Font.CENTER_BASELINE, 14);
        JButton button = new JButton("Add the point");
        button.setFont(font);
        button.setMargin(new Insets(30, 40, 30, 40));
        button.addActionListener(this);
        button.addMouseListener(this);
        // add the spacer on the data panel
        this.add(Box.createVerticalStrut(20));
        this.add(button);
        this.add(Box.createVerticalStrut(100));
    }

    public void changeLabelX(String text) {
        labelXData.setText(text);
    }

    public void changeLabelY(String text) {
        labelYData.setText(text);
    }

    private JSpinner getSpinner() {
        SpinnerModel spinnerModel = new SpinnerNumberModel(5, 1, 10, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        // forbid spinner editing.
        JTextField tempTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        tempTextField.setEditable(false);
        spinner.addChangeListener(event -> {
            graphPanel.setRadius((int) ((JSpinner) event.getSource()).getValue());
            graphPanel.repaint();
        });
        return spinner;
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        JCheckBox checkBox = (JCheckBox) itemEvent.getItem();
        if (checkBox.isSelected()) {
            yPoint = Double.parseDouble(checkBox.getText());
            changeLabelY("y = " + new DecimalFormat("##0.0").format(yPoint));
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

    private void startPaint(GraphPanel graphPanel) {
        if (!graphPanel.getFlag()) {
            graphPanel.setY((int) yPoint);
            graphPanel.setX((int) xPoint);
        }
        graphPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        graphPanel.setFlag(false);
        startPaint(graphPanel);
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
        graphPanel.setRed(0);
        graphPanel.setGreen(0);
        graphPanel.setX(mouseEvent.getX());
        graphPanel.setY(mouseEvent.getY());
        graphPanel.setFlag(true);
        graphPanel.repaint();
        /*Test action from DataPanel: */
        System.out.println("____________________________________________________");
        System.out.println("Test action from DataPanel: ");
        System.out.println("graphPanel.x = " + graphPanel.getXCoordinate());
        System.out.println("graphPanel.y = " + graphPanel.getYCoordinate());
        System.out.println("graphPanel.GraphWidth = " + graphPanel.getGraphWidth());
        System.out.println("graphPanel.GraphHeight = " + graphPanel.getGraphHeight());
        System.out.println("____________________________________________________");
        /*end of test action*/
        changeLabelX("x = " + new DecimalFormat("##0.0").format((graphPanel.getXCoordinate() - graphPanel.getGraphWidth() / 2) / 20));
        changeLabelY("y = " + new DecimalFormat("##0.0").format(-(graphPanel.getYCoordinate() - graphPanel.getGraphHeight() / 2) / 20));
        graphPanel.setFlag(false);
        startPaint(graphPanel);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

}
