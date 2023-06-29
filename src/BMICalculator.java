/*Body Mass Index (BMI) is a person’s weight in kilograms (or pounds)
divided by the square of height in meters (or feet).
A high BMI can indicate high body fatness. BMI screens for weight
categories that may lead to health problems,
but it does not diagnose the body fatness or health of an individual.
it is measured in kg/m2*/
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
        JLabel heightLabel = new JLabel("Height (in cm):"); // Create a label for height
        heightField = new JTextField(10); // Create a text field for height input
        JLabel weightLabel = new JLabel("Weight (in kg):"); // Create a label for weight
        weightField = new JTextField(10); // Create a text field for weight input
        calculateButton = new JButton("Calculate BMI"); // Create a button for BMI calculation
        resultLabel = new JLabel(); // Create a label for displaying the BMI result

        // Set up the layout
        JPanel panel = new JPanel(); // Create a panel to hold GUI components
        panel.setLayout(new GridLayout(4, 2)); // Set the panel layout to a grid with 4 rows and 2 columns
        panel.add(heightLabel); // Add the height label to the panel
        panel.add(heightField); // Add the height text field to the panel
        panel.add(weightLabel); // Add the weight label to the panel
        panel.add(weightField); // Add the weight text field to the panel
        panel.add(calculateButton); // Add the calculate button to the panel
        panel.add(resultLabel); // Add the result label to the panel
        add(panel); // Add the panel to the frame

        // Add event listener for the calculate button
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateBMI();
            }
        });

        // Set window properties
        setTitle("BMI Calculator"); // Set the title of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Specify the default close operation
        pack(); // Pack the components within the window to their preferred sizes
        setVisible(true); // Make the frame visible
    }

    private void calculateBMI() {
        try {
            // Get the height and weight values entered by the user
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());

            // Calculate the BMI using the formula: weight / (height / 100)^2
            double bmi = weight / Math.pow(height / 100, 2);

            // Determine the BMI category based on the calculated BMI
            String category = getCategory(bmi);

            // Format the BMI result and category into a string
            String result = "BMI: " + String.format("%.2f", bmi) + " (" + category + ")";

            // Display the result on the GUI
            resultLabel.setText(result);

            // Write the BMI result to a file
            writeToFile(result);
        } catch (NumberFormatException exception) {
            // Handle the exception when invalid input is provided
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException exception) {
            // Handle the exception when an error occurs while writing to the file
            JOptionPane.showMessageDialog(this, "Error: Unable to write to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void writeToFile(String result) throws IOException {
        FileWriter fileWriter = null;
        try {
            // Create a FileWriter object to write to the file "bmi_results.txt"
            fileWriter = new FileWriter("bmi_results.txt", true);

            // Write the BMI result and a newline character to the file
            fileWriter.write(result + "\n");

            // Display a success message to the user
            JOptionPane.showMessageDialog(this, "BMI result saved to file successfully!");
        } finally {
            if (fileWriter != null) {
                // Close the file writer in the final block to ensure it is always closed
                fileWriter.close();
            }
        }
    }

    private String getCategory(double bmi) {
        // Determine the BMI category based on the BMI value
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal weight";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public static void main(String[] args) {
        // Create an instance of the BMI calculator and make it visible
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BMICalculator();
            }
        });
    }
}
