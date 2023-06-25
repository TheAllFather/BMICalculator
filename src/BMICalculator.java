import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BMICalculator extends JFrame {
    private JTextField heightField;
    private JTextField weightField;
    private JButton calculateButton;
    private JLabel resultLabel;

    public BMICalculator() {
        // Set up the GUI components
        JLabel heightLabel = new JLabel("Height (in cm):");
        heightField = new JTextField(10);
        JLabel weightLabel = new JLabel("Weight (in kg):");
        weightField = new JTextField(10);
        calculateButton = new JButton("Calculate BMI");
        resultLabel = new JLabel();

        // Set up the layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(calculateButton);
        panel.add(resultLabel);
        add(panel);

        // Add event listener
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateBMI();
            }
        });

        // Set window properties
        setTitle("BMI Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void calculateBMI() {
        try {
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());

            double bmi = weight / Math.pow(height / 100, 2);
            String category = getCategory(bmi);

            String result = "BMI: " + String.format("%.2f", bmi) + " (" + category + ")";
            resultLabel.setText(result);

            writeToFile(result); // Write the BMI result to a file
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this, "Error: Unable to write to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void writeToFile(String result) throws IOException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("bmi_results.txt", true);
            fileWriter.write(result + "\n");
            JOptionPane.showMessageDialog(this, "BMI result saved to file successfully!");
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    // Ignore the exception when closing the file writer
                }
            }
        }
    }

    private String getCategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal weight";
        } else if (bmi < 30) {
            return "Overweight";
        } else if (bmi > 40) {
            return "Obese";
        } else {
            return "Error!";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BMICalculator();
            }
        });
    }
}
