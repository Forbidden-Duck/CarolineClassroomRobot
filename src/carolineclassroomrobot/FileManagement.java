package carolineclassroomrobot;

// Import the applicable Java IO libraries
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
// Swing Imports
import javax.swing.*;

/**
 * The file management for Caroline's Classroom Robot
 *
 * @author Harrison Howard
 */
public class FileManagement {

    // Set the name of the file
    private String fileName = "ClassroomLayout.csv";
    // Set the name of the RAF file
    private String RAFName = "RandomAccessFile-ClassroomLayout.txt";

    // Size of the classroom
    int totalX = 19;
    int totalY = 10;

    // The multi-dimensional array of text fields
    private JTextField[][] txtFields;
    // Define the headings
    private String[] headings = new String[4];

    // The instance of the main instance in BillsReportingSystem
    // Note: This is so we can access public methods in the main instance
    //       This is so we don't have to create a new instance
    CarolineClassroomRobot carolineObj;

    /**
     * Create a new instance of File Management with
     *
     * @param txtArray the multi-dimensional array of text fields
     * @param fieldsAxis an array of the X and Y axis
     */
    public FileManagement(CarolineClassroomRobot myObj) {
        // Set the object
        carolineObj = myObj;
    }

    public void setComponents(JTextField[][] txtArray) {
        // Set the fields
        txtFields = txtArray;
        // Set the fields
        carolineObj.setFields();
    }

    /**
     * Reads and displays the CSV File
     */
    public void readFile() {
        // Try to read and display the CSV File
        // Catch any exceptions
        try {
            // Instantiate a new Buffered Reader with the dataFileName
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // Define the amount of headings
            int headingEndIndex = 4;

            // Instantiate x
            int x = 1;
            // Instantiate readLine
            String readLine;
            // Loop the fields on the X Axis (Starting from 1)
            while ((readLine = br.readLine()) != null) {
                // Check if the headings are still being present
                if (x <= headingEndIndex) {
                    // Skip date
                    // We want date to increment everyday
                    if (x < headingEndIndex) {
                        headings[x - 1] = readLine.split(",")[1];
                    } else {
                        // Set date format
                        // Get current date time
                        // Create date label
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDateTime now = LocalDateTime.now();
                        headings[x - 1] = dtf.format(now);
                    }
                } else {
                    // Define the line values
                    String[] temp = readLine.split(",");
                    // Set the indexes
                    int yIndex = Integer.parseInt(temp[0]);
                    int xIndex = Integer.parseInt(temp[1]);
                    // Set the title
                    String title = temp[2];

                    if (title.equals("BKGRND FILL")) {
                        // Check if teacher desk is active
                        if (xIndex == 1) {
                            // Set the text field behind to editable
                            txtFields[xIndex - 1][yIndex].setEditable(true);
                        } else {
                            // Set the text field ahead to editable
                            txtFields[xIndex + 1][yIndex].setEditable(true);
                        }
                        // Set the background colour
                        txtFields[xIndex][yIndex].setBackground(new Color(148, 185, 224));
                    } else {
                        // Set the text field name
                        txtFields[xIndex][yIndex].setText(title);
                    }
                }
                x++;
            }
            br.close();

            // Set the headings
            carolineObj.setHeadings(headings);
        } catch (Exception e) {
            System.out.println("Error Reading:");
            // Print the error in console
            e.printStackTrace();
        }
    }

    /**
     * Saves the file into the CSV Format
     */
    public void saveFile() {
        // Try to save the file
        // Catch any exceptions
        try {
            // Instantiate a new Buffered Writer with the dataFileName
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

            // Get the headings
            String[] headings = carolineObj.getHeadings();
            // Write teacher heading
            bw.write("Teacher:," + headings[0] + "\n");
            // Write class heading
            bw.write("Class:," + headings[1] + "\n");
            // Write room heading
            bw.write("Room:," + headings[2] + "\n");
            // Write date heading
            bw.write("Date:," + headings[3] + "\n");

            // Loop the fields on the X Axis
            // Loop the fields on the Y Axis
            for (int x = 0; x < totalX; x++) {
                for (int y = 0; y < totalY; y++) {
                    // Get the RGB
                    int getRGB = txtFields[x][y].getBackground().getRGB();
                    int[] rgb = new int[]{(getRGB >> 16 & 0xFF), (getRGB >> 8 & 0xFF), (getRGB & 0xFF)};
                    // Get blue color
                    int[] blue = new int[]{148, 185, 224};

                    // Check if the field as text
                    // Check if the field has blue background
                    if (txtFields[x][y].getText().length() > 0) {
                        bw.write(y + "," + x + "," + txtFields[x][y].getText());
                        // Print new line
                        bw.write("\n");
                    } else if (rgb[0] == blue[0] && rgb[1] == blue[1] && rgb[2] == blue[2]) {
                        bw.write(y + "," + x + "," + "BKGRND FILL,blue");
                        // Print new line
                        bw.write("\n");

                        // Set the editability
                        if (x == 1) {
                            // Set the text field behind to editable
                            txtFields[x - 1][y].setEditable(true);
                        } else {
                            // Set the text field ahead to editable
                            txtFields[x + 1][y].setEditable(true);
                        }
                    }
                }
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error Saving CSV:");
            // Print the error in console
            e.printStackTrace();
        }
    }

    /**
     * Save the file as a Random Access File
     */
    public void saveRAF() {
        // Try to save to a RAF
        // Catch any exceptions
        try {
            // Instantiate a new Random Access File with
            // The file name and with Read & Write access (rw)
            RandomAccessFile raf = new RandomAccessFile(RAFName, "rw");

            // Create a variable for holding the string
            String str = "";

            // Loop the fields on the X Axis (Starting from 1)
            // Loop the fields on the Y Axis
            for (int x = 0; x < totalX; x++) {
                for (int y = 0; y < totalY; y++) {
                    // Get the RGB
                    int getRGB = txtFields[x][y].getBackground().getRGB();
                    int[] rgb = new int[]{(getRGB >> 16 & 0xFF), (getRGB >> 8 & 0xFF), (getRGB & 0xFF)};
                    // Get blue color
                    int[] blue = new int[]{148, 185, 224};

                    // Check if the field as text
                    // Check if the field has blue background
                    if (txtFields[x][y].getText().length() > 0) {
                        // Set the string
                        str = y + "," + x + "," + txtFields[x][y].getText() + "\n";
                        // Write the bytes to the RAF
                        raf.writeUTF(str);
                    } else if (rgb[0] == blue[0] && rgb[1] == blue[1] && rgb[2] == blue[2]) {
                        // Set the string
                        str = y + "," + x + "," + "BKGRND FILL,blue\n";
                        // Write the bytes to the RAF
                        raf.writeUTF(str);

                        // Set the editability
                        if (x == 1) {
                            // Set the text field behind to editable
                            txtFields[x - 1][y].setEditable(true);
                        } else {
                            // Set the text field ahead to editable
                            txtFields[x + 1][y].setEditable(true);
                        }
                    }
                }
            }
            raf.close();
        } catch (Exception e) {
            System.out.println("Error Saving RAF:");
            // Print the error in console
            e.printStackTrace();
        }
    }
}
