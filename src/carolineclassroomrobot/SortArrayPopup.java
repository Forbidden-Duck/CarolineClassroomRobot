package carolineclassroomrobot;

// Layout & Java GUI Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Sort Array Form for EventHandling
 *
 * @author Harrison Howard
 */
public class SortArrayPopup extends JFrame implements MouseListener {

    // Create a variable for the amount of student names
    int totalNames = 0;

    // Variable to hold the student names
    String[][] studentNames = new String[totalNames][3];
    // Variable to hold the student labels
    JLabel[][] studentLabels = new JLabel[totalNames][3];

    // Variable for holding currently highlighted student
    int studentIndex = -1;

    /**
     * Instantiates a new Sort Array Popup
     *
     * @param namesTotal the total amount of names
     * @param allNames an array of all the student names
     */
    public SortArrayPopup(int namesTotal, String[][] allNames) {
        totalNames = namesTotal;
        studentNames = allNames;
        studentLabels = new JLabel[totalNames][3];

        // Initialize the frame
        InitializeFrame();
    }

    /**
     * Initialize the frame component
     */
    private void InitializeFrame() {
        // Set the size of the frames
        setSize(265, totalNames * 20 + 70);
        // Centre the frame to the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);

        // Set the frame title
        setTitle("Sorted Students");

        // Display the graphical user interface
        displayGUI();

        // Set the frame resizablility
        // Set the frame visibility
        setResizable(false);
        setVisible(true);
    }

    /**
     * Display the frames graphical user interface
     */
    private void displayGUI() {
        // New instance of SpringLayout
        // Set the frame layout to springlayout
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);

        // Displays all of the header text fields
        displayTextFields(springLayout);
        // Display all of the student labels
        displayLabels(springLayout);
    }

    /**
     * Display the text fields
     *
     * @param layout the frame layout
     */
    private void displayTextFields(SpringLayout layout) {
        // Creates the text field
        // FRAME, LAYOUT, TEXT, SIZE, LOCATION, BOLD
        JTextField student = LibraryComponents.LocateTextField(this, layout, " Student", new int[]{80, 20}, new int[]{10, 10}, true);
        JTextField across = LibraryComponents.LocateTextField(this, layout, " Across", new int[]{80, 20}, new int[]{10, 90}, true);
        JTextField down = LibraryComponents.LocateTextField(this, layout, " Down", new int[]{80, 20}, new int[]{10, 170}, true);

        // Set the backgrounds
        setTextFields(student);
        setTextFields(across);
        setTextFields(down);
    }

    /**
     * Display the labels
     *
     * @param layout the frame layout
     */
    private void displayLabels(SpringLayout layout) {
        // Create the Labels
        // FRAME, LAYOUT, TEXT, SIZE, LOCATION, BACKGROUND COLOUR, BOLD

        // Check if names exist
        if (totalNames > 0) {
            // Loop through the names
            for (int x = 0; x < totalNames; x++) {
                // Get the name
                String studentName = studentNames[x][0];
                // Get the x index
                String xIndex = studentNames[x][1];
                // Get the y index
                String yIndex = studentNames[x][2];

                // Create the x position
                int xPos = x * 20 + 30;
                // Create the labels
                studentLabels[x][0] = LibraryComponents.LocateLabel(this, layout, studentName, new int[]{80, 20}, new int[]{xPos, 15}, new int[]{0, 0, 0}, false, false);
                studentLabels[x][1] = LibraryComponents.LocateLabel(this, layout, xIndex, new int[]{80, 20}, new int[]{xPos, 95}, new int[]{0, 0, 0}, false, false);
                studentLabels[x][2] = LibraryComponents.LocateLabel(this, layout, yIndex, new int[]{80, 20}, new int[]{xPos, 175}, new int[]{0, 0, 0}, false, false);

                studentLabels[x][0].addMouseListener(this);

            }
        }
    }

    /**
     * Change the text field settings
     *
     * @param field
     */
    private void setTextFields(JTextField field) {
        // Set background
        field.setBackground(new Color(173, 196, 219));
        // Set editability
        field.setEditable(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Variable for checking if the user clicked the label with a background
        boolean indexClicked = false;

        // Check if a label has been set with a background
        if (studentIndex > -1) {
            // Check if the index label was clicked
            if (e.getSource() == studentLabels[studentIndex][0]) {
                // Set index clicked to true
                indexClicked = true;
            }

            // Update the label with no background
            UpdateLabel(studentIndex, false);
            UpdateLabel(studentIndex, false);
            UpdateLabel(studentIndex, false);
            // Reset the index
            studentIndex = -1;
        }

        // Check if the index label was clicked
        if (!indexClicked) {
            // Loop through the labels
            for (int x = 0; x < totalNames; x++) {
                // Check for the clicked label
                if (e.getSource() == studentLabels[x][0]) {
                    // Update the label with a background
                    UpdateLabel(x, true);
                    UpdateLabel(x, true);
                    UpdateLabel(x, true);
                    // Set the index to the student
                    studentIndex = x;
                }
            }
        }
    }

    /**
     * Update the label
     *
     * @param index The index of the label
     * @param isOpaque The opaque status of the label
     */
    private void UpdateLabel(int index, boolean isOpaque) {
        // Set the label with the defined opaque
        studentLabels[index][0].setOpaque(isOpaque);
        studentLabels[index][1].setOpaque(isOpaque);
        studentLabels[index][2].setOpaque(isOpaque);

        // Check if opaque is true
        if (isOpaque) {
            // Set the background
            studentLabels[index][0].setBackground(new Color(213, 227, 242));
            studentLabels[index][1].setBackground(new Color(213, 227, 242));
            studentLabels[index][2].setBackground(new Color(213, 227, 242));
        }

        // Repaint the labels
        studentLabels[index][0].repaint();
        studentLabels[index][1].repaint();
        studentLabels[index][2].repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
