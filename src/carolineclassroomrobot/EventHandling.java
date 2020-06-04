package carolineclassroomrobot;

// ActionListener Imports 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// WindowListener Imports
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
// KeyListener Imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Comparator;
// Swing Imports
import javax.swing.*;
// Misc
import java.awt.Color;
import java.awt.Font;
import static javax.swing.JOptionPane.*;
import javax.swing.JOptionPane;

/**
 * The event handling for Caroline's Classroom Robot
 *
 * @author Harrison Howard
 */
public class EventHandling implements WindowListener, ActionListener, KeyListener {

    // Define the Frame, Layout, Fields and Buttons
    JFrame frame;
    SpringLayout layout;

    // Variables to define the amount of rows and columns
    // Is hardcoded due to classroom not changing in size
    int totalX = 19;
    int totalY = 10;

    // Used to hold the axis of the student name for clear find
    int[] axisName;

    // Define the table
    JTextField[][] txtFields;
    // Define the buttons
    JButton btnSort, btnFind, btnExit, btnClearFind;
    // Define the textfields
    JTextField txtFind;
    // Define the menuitems
    JMenuItem itemSaveClass, itemSaveRAF,
            itemClear, itemRestore;

    // The instance of the main instance in BillsReportingSystem
    // Note: This is so we can access public methods in the main instance
    //       This is so we don't have to create a new instance
    CarolineClassroomRobot carolineObj;

    /**
     * Set the frame/screen
     *
     * @param myObj the instance of BillsReportingSystem
     * @param myLayout the layout of the frame
     */
    public void setFrame(CarolineClassroomRobot myObj, SpringLayout myLayout) {
        // Instantiate billsObj with the current instance
        carolineObj = myObj;
        // Convert the instance to a JFrame
        frame = myObj;
        layout = myLayout;
    }

    public void setComponents(JButton[] btnArray, JTextField[][] txtFieldsArray, JTextField[] txtArray, int[] txtFieldAxis) {
        btnSort = btnArray[0];
        btnFind = btnArray[1];
        btnExit = btnArray[2];
        btnClearFind = btnArray[3];

        txtFind = txtArray[0];

        txtFields = txtFieldsArray;

        totalX = txtFieldAxis[0];
        totalY = txtFieldAxis[1];
    }

    public void setItems(JMenuItem[] fileArray, JMenuItem[] editArray) {
        // File Items
        itemSaveClass = fileArray[0];
        itemSaveRAF = fileArray[1];

        // Edit Items
        itemClear = editArray[0];
        itemRestore = editArray[1];
    }

    /**
     * The Action Event
     *
     * @param e the cause of the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
         * BUTTON EVENTS
         */
        // BUTTON EXIT
        if (e.getSource() == btnExit) {
            System.exit(0);
        }
        // BUTTON SORT
        if (e.getSource() == btnSort) {
            // Get the names
            String[][] names = getStudentNames();
            // Get total amount of names
            int total = getTotalStudents();
            // Popup the frame
            new SortArrayPopup(total, names);
        }

        // BUTTON FIND
        if (e.getSource() == btnFind) {
            // Create a variable for the user's input
            String userInput = "";
            // Making sure that the user has entered input
            // Else show a dialog explaining why
            if (txtFind.getText().length() > 0) {
                // Set the variable with the user's input (all lower case)
                userInput = txtFind.getText().toLowerCase();
                // Create an int array for the highlight colour in R,G,B
                int[] highlightColor = new int[]{255, 230, 117};
                // Student Name's Axis
                axisName = findStudent(userInput);

                // Check if the axis exists
                if (axisName[0] != -1 && axisName[1] != -1) {
                    // Set the student name to the highlight colour
                    txtFields[axisName[0] - 1][axisName[1]].setBackground(new Color(highlightColor[0], highlightColor[1], highlightColor[2]));
                    // Make the find button disappear
                    btnFind.setVisible(false);
                    // Show the clear button
                    btnClearFind.setVisible(true);
                    // Make sure the user can't input anything
                    txtFind.setEnabled(false);
                } else {
                    showMessageDialog(null, "Can not find \"" + userInput + "\"");
                }
            } else {
                showMessageDialog(null, "Please enter something to find");
            }
        }

        // BUTTON CLEARFIND
        if (e.getSource() == btnClearFind) {
            // Show the find button
            btnFind.setVisible(true);
            // Hide the clear button
            btnClearFind.setVisible(false);
            // Allow the user to input in the field
            // Set the field to nothing
            txtFind.setEnabled(true);
            txtFind.setText("");
            // Set the student name to the non highlight colour
            txtFields[axisName[0] - 1][axisName[1]].setBackground(new Color(148, 185, 224));
            // Reset axisname
            axisName = null;
        }

        /*
         * MENUITEM EVENTS
         */
        // ITEM SAVE
        if (e.getSource() == itemSaveClass) {
            // Create a new instance of FileManagement
            // Set the components
            // Save the table to a CSV File
            FileManagement filemng = new FileManagement(carolineObj);
            filemng.setComponents(txtFields);
            filemng.saveFile();
        }
        // ITEM SAVE RAF
        if (e.getSource() == itemSaveRAF) {
            // Create a new instance of FileManagement
            // Set the components
            // Save the table to a RAF File
            FileManagement filemng = new FileManagement(carolineObj);
            filemng.setComponents(txtFields);
            filemng.saveRAF();
        }
        // ITEM CLEAR
        if (e.getSource() == itemClear) {
            clearStudentNames();
        }
        // ITEM RESTORE
        if (e.getSource() == itemRestore) {
            // Create a new instance of FileManagement
            // Set the components
            // Read the file again
            FileManagement filemng = new FileManagement(carolineObj);
            filemng.setComponents(txtFields);
            filemng.readFile();
        }
    }

    // Button Methods
    /**
     * Clear student names
     */
    private void clearStudentNames() {
        // Loop through all the fields on the x axis
        // Loop through all the fields on the y axis
        for (int x = 1; x < totalX; x++) {
            for (int y = 0; y < totalY; y++) {
                // If the student name exists
                // Delete it
                if (txtFields[x][y].getText().length() > 0) {
                    txtFields[x][y].setText("");
                }
            }
        }
    }

    /**
     * Find a student x and y axis
     *
     * @param name The user input
     * @return The x and y axis
     */
    private int[] findStudent(String name) {
        int[] output = new int[]{-1, -1};
        // Create a variable if it is found and the x positing
        boolean isFound = false;
        int x = 2;
        int y = 0;
        // Loop until the value is found
        // And X is less than the total fields on the X Axis
        // And Y is less than the total fields on the Y Axis
        while (isFound == false && x < totalX) {
            while (isFound == false && y < totalY) {
                // Create a variable for the students name
                // Set the students name (all lower case)
                String studentName = txtFields[x][y].getText().toLowerCase();
                // If the students name contains the users input
                // Then update isFound and set the Student Name's X & Y Axis
                if (studentName.indexOf(name) > -1) {
                    isFound = true;
                    output = new int[]{x, y};
                }
                y++;
            }
            x++;
            // Got to reset Y so when it gets back to it, it doesn't skip it
            y = 0;
        }
        // Return output
        return output;
    }

    /**
     * Get all of the student names
     *
     * @return An array of students
     */
    private String[][] getStudentNames() {
        // Get total number of students
        int totalStudents = getTotalStudents();
        // An array that holds the students names
        String[][] studentNames = new String[totalStudents][3];

        // Variable for checking the index of the array
        int arrayIndex = 0;
        // Loop through all the rows
        // Avoiding the teacher's desk
        for (int x = 2; x < totalX; x++) {
            // Loop through all the columns
            for (int y = 0; y < totalY; y++) {
                // Get the students name
                String studentName = txtFields[x][y].getText();

                // Check the name is larger than 1
                if (studentName.length() > 0) {
                    // Add the name to the array
                    // With the x and y index of the name attached
                    studentNames[arrayIndex][0] = studentName;
                    studentNames[arrayIndex][1] = Integer.toString(x);
                    studentNames[arrayIndex][2] = Integer.toString(y);
                    // Increment the arrayIndex
                    arrayIndex++;
                }
            }
        }

        // Return the array
        return studentNames;
    }

    /**
     * Get the total amount of students
     *
     * @return integer of the total
     */
    private int getTotalStudents() {
        // Variable for checking the counting the students
        int count = 0;
        // Loop through all the rows
        // Avoiding the teacher's desk
        for (int x = 2; x < totalX; x++) {
            // Loop through all the columns
            for (int y = 0; y < totalY; y++) {
                // Get the students name
                String studentName = txtFields[x][y].getText();

                // Check the name is larger than 1
                if (studentName.length() > 0) {
                    // Increment the count
                    count++;
                }
            }
        }

        // Return the count
        return count;
    }

    /**
     * Key Events
     *
     * @param e the source of the event
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Window Events
     *
     * @param we the source of the event
     */
    @Override
    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }
}
