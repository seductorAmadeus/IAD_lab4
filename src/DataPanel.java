import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class DataPanel extends JPanel implements ItemListener, ActionListener, MouseListener {
    private JLabel labelXData = new JLabel("x = ");
    private JLabel labelYData = new JLabel("y = ");
    private GraphPanel graphPanel;
    private double yPoint;
    private double xPoint;
    private DecimalFormat decimalFormat = new DecimalFormat("##0.0");
    private JCheckBox checkBox;

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
            /*Test action from MainFrame:
            System.out.println("____________________________________________________");
            System.out.println("Test action from DataPanel: ");
            System.out.println("graphPanel.x = " + this.graphPanel.getXCoordinate());
            System.out.println("graphPanel.GraphWidth = " + this.graphPanel.getGraphWidth());
            System.out.println("graphPanel.step = " + this.graphPanel.getStep());
            System.out.println("____________________________________________________");
            /*end of test action*/
            //  this.changeLabelX("x = " + decimalFormat.format(this.graphPanel.getXCoordinate()));
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
        checkBox = (JCheckBox) itemEvent.getItem();
        checkBox.addMouseListener(this);
        if (checkBox.isSelected()) {
            yPoint = Double.parseDouble(checkBox.getText());
            /*Test action from MainFrame:
            System.out.println("____________________________________________________");
            System.out.println("Test action from DataPanel: ");
            System.out.println("graphPanel.y = " + graphPanel.getYCoordinate());
            System.out.println("graphPanel.GraphHeight = " + graphPanel.getGraphHeight());
            System.out.println("graphPanel.step = " + graphPanel.getStep());
            System.out.println("____________________________________________________");
            /*end of test action*/
            //this.changeLabelY("y = " + decimalFormat.format(graphPanel.getYCoordinate()));
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

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        // graphPanel = (GraphPanel) mouseEvent.getSource();
        graphPanel.setRed(0);
        graphPanel.setGreen(0);
        graphPanel.setX(xPoint);
        graphPanel.setY(yPoint);
        graphPanel.setFlag(true);
        graphPanel.repaint();
        String pattern = "##0.0";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        /*Test action from DataPanel: */
        System.out.println("____________________________________________________");
        System.out.println("Test action from MainFrame: ");
        System.out.println("graphPanel.x = " + graphPanel.getXCoordinate());
        System.out.println("graphPanel.y = " + graphPanel.getYCoordinate());
        System.out.println("graphPanel.GraphWidth = " + graphPanel.getGraphWidth());
        System.out.println("graphPanel.GraphHeight = " + graphPanel.getGraphHeight());
        System.out.println("graphPanel.step = " + graphPanel.getStep());
        System.out.println("____________________________________________________");
        /*end of test action*/
        changeLabelX("x = " + decimalFormat.format(graphPanel.getXCoordinate()));
        changeLabelY("y = " + decimalFormat.format(graphPanel.getYCoordinate()));
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
