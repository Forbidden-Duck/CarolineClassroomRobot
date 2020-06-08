package carolineclassroomrobot;

// Layout & Java GUI Imports
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * The main file for Caroline's Classroom Robot
 *
 * @author Harrison Howard
 */
public class CarolineClassroomRobot extends JFrame {

    // Variables to define the amount of rows and columns
    // Is hardcoded due to classroom not changing in size
    int totalX = 19;
    int totalY = 10;

    // Creates a new multi-dimensional array for the classroom
    JTextField[][] txtFields = new JTextField[totalX][totalY];

    // Define the labels
    JLabel lblTeacher, lblClass, lblRoom, lblDate;

    // Define the buttons
    JButton btnSort, btnFind, btnExit, btnClearFind;

    // Define the textfields
    JTextField txtFind;

    // Define MenuBar, Menu and MenuItem
    JMenuBar myMenuBar;
    JMenu menuFile, menuEdit;
    JMenuItem itemSaveClass, itemSaveRAF,
            itemClear, itemRestore,
            itemOpen;
    // String to hold the name of the front desk
    String frontDeskStr = "Front Desk";

    // Creates a new instance of EventHandling
    // EventHandling handles all of the events
    EventHandling event = new EventHandling();
    // Creates a new instance of FileManagement
    // FileManagement handles of all the file management
    FileManagement filemng = new FileManagement(this);

    public static void main(String[] args) {
        // Creates a new instance of BillsReportingSystem
        // To execute a non static method in a static context
        new CarolineClassroomRobot().InitializeFrame();
    }

    /**
     * Instantiate the Frame and set the properties
     */
    private void InitializeFrame() {
        // Set the size of the frame
        // Set the location to the centre of the screen
        setSize(totalY * 71 + 62, totalX * 21 + 160);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);

        // Set the title of the frame
        setTitle("Classroom Robot");

        // Call method
        displayGUI();

        // Set the frame resizablity
        // Set the frame visibility
        setResizable(false);
        setVisible(true);
    }

    /**
     * Display the Graphical User Interface Components
     */
    private void displayGUI() {
        // New instance of SpringLayout
        // Set the frame layout to springLayout
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);

        // Give the event instance the Frame and SpringLayout
        event.setFrame(this, springLayout);
        // Display all the components
        displayLabels(springLayout);
        displayTextFields(springLayout);
        displayButtons(springLayout);

        // Display Menus
        displayMenuBar();
        displayMenu();
        displayMenuItem();
        // Set MenuBar to frame
        setJMenuBar(myMenuBar);
    }

    /**
     * Display the MenuBar
     */
    private void displayMenuBar() {
        // Creates a new MenuBar instance
        myMenuBar = new JMenuBar();
    }

    /**
     * Display the Menus
     */
    private void displayMenu() {
        // Creates the menus
        // MENUBAR, MENU, MENUNAME
        menuFile = LibraryComponents.LocateMenu(myMenuBar, menuFile, "File");
        menuEdit = LibraryComponents.LocateMenu(myMenuBar, menuEdit, "Edit");
    }

    private void displayMenuItem() {
        // Create the MenuItems
        // EVENT, MENU, MENUITEM, MENUITEMNAME
        // MENU FILE ITEMS
        itemOpen = LibraryComponents.LocateMenuItem(event, menuFile, itemOpen, "Open");
        itemSaveClass = LibraryComponents.LocateMenuItem(event, menuFile, itemSaveClass, "Save");
        itemSaveRAF = LibraryComponents.LocateMenuItem(event, menuFile, itemSaveRAF, "Save RAF");

        // MENU EDIT ITEMS
        itemClear = LibraryComponents.LocateMenuItem(event, menuEdit, itemClear, "Clear");
        itemRestore = LibraryComponents.LocateMenuItem(event, menuEdit, itemRestore, "Restore");

        // Set the MenuItems in the event instance
        event.setItems(
                // File Items
                new JMenuItem[]{itemSaveClass, itemSaveRAF, itemOpen},
                // Edit Items
                new JMenuItem[]{itemClear, itemRestore});
    }

    /**
     * Display the labels
     *
     * @param layout The layout used on the frame
     */
    private void displayLabels(SpringLayout layout) {
        // Loop through all the x axis
        for (int x = 0; x < totalX; x++) {
            // Set the Y position of the labels
            int xPos = x * 21 + 60;
            LibraryComponents.LocateLabel(this, layout, Integer.toString(x), new int[]{15, 20}, new int[]{xPos, 10}, new int[]{148, 185, 224}, false, true);
        }
        // Loop through all the y axis
        for (int y = 0; y < totalY; y++) {
            // Set the Y position of the labels
            int yPos = y * 71 + 60;
            LibraryComponents.LocateLabel(this, layout, Integer.toString(y), new int[]{15, 20}, new int[]{40, yPos}, new int[]{148, 185, 224}, false, true);
        }

        // Create the Labels
        // FRAME, LAYOUT, TEXT, SIZE, LOCATION, BACKGROUND COLOUR, BACKGROUND, BOLD
        lblTeacher = LibraryComponents.LocateLabel(this, layout, "Teacher: Caroline", new int[]{208, 20}, new int[]{15, 30}, new int[]{148, 185, 224}, true, true);
        lblClass = LibraryComponents.LocateLabel(this, layout, "Class: 5B", new int[]{200, 20}, new int[]{15, 238}, new int[]{148, 185, 224}, true, true);
        lblRoom = LibraryComponents.LocateLabel(this, layout, "Room: B16", new int[]{200, 20}, new int[]{15, 438}, new int[]{148, 185, 224}, true, true);

        // Set date format
        // Get current date time
        // Create date label
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDateTime now = LocalDateTime.now();
        lblDate = LibraryComponents.LocateLabel(this, layout, "Date: " + dtf.format(now), new int[]{100, 20}, new int[]{15, 638}, new int[]{148, 185, 224}, true, true);
    }

    /**
     * Display the text fields
     *
     * @param layout The layout used on the frame
     */
    private void displayTextFields(SpringLayout layout) {
        // Loop through all the Y Positions and X Positions
        for (int y = 0; y < totalY; y++) {
            for (int x = 0; x < totalX; x++) {
                // Set the X & Y position of the text field
                int xPos = x * 21 + 60;
                int yPos = y * 71 + 30;
                // Create the text field
                // Add a key listener to the text field
                // FRAME, LAYOUT, TEXT, SIZE, LOCATION, BOLD
                txtFields[x][y] = LibraryComponents.LocateTextField(this, layout, "", new int[]{70, 20}, new int[]{xPos, yPos}, false);
                txtFields[x][y].addKeyListener(event);
            }
        }
        // Set the components in file management
        filemng.setComponents(txtFields);
        // Read the file
        filemng.readFile();

        // Create the text fields
        // FRAME, LAYOUT, TEXT, SIZE, LOCATION, BOLD
        txtFind = LibraryComponents.LocateTextField(this, layout, "", new int[]{100, 20}, new int[]{475, 215}, false);
    }

    /**
     * Display the buttons
     *
     * @param layout The layout used on the frame
     */
    private void displayButtons(SpringLayout layout) {
        // Create the buttons
        // EVENT, FRAME, LAYOUT, NAME, LOCATION, SIZE
        btnSort = LibraryComponents.LocateButton(event, this, layout, "Sort", new int[]{30, 472}, new int[]{80, 25});
        btnFind = LibraryComponents.LocateButton(event, this, layout, "Find", new int[]{130, 472}, new int[]{80, 25});
        btnExit = LibraryComponents.LocateButton(event, this, layout, "Exit", new int[]{659, 472}, new int[]{80, 25});

        // Buttons the are not visible upon loading
        btnClearFind = LibraryComponents.LocateButton(event, this, layout, "Clear", new int[]{130, 472}, new int[]{80, 25});
        btnClearFind.setVisible(false);

        // Set the components in the event instance
        // Add the frame to the window listener in the event instance
        event.setComponents(
                new JButton[]{btnSort, btnFind, btnExit, btnClearFind},
                txtFields,
                new JTextField[]{txtFind},
                new int[]{totalX, totalY});
        this.addWindowListener(event);
    }

    /**
     * Set the text fields
     */
    public void setFields() {
        for (int x = 0; x < totalX; x++) {
            for (int y = 0; y < totalY; y++) {
                // Set all of the text fields to uneditable
                txtFields[x][y].setEditable(false);
            }
        }
    }

    /**
     * Set the headings on the frame
     *
     * @param headings The headings array
     */
    public void setHeadings(String[] headings) {
        lblTeacher.setText("Teacher: " + headings[0]);
        lblClass.setText("Class: " + headings[1]);
        lblRoom.setText("Room: " + headings[2]);
        lblDate.setText("Date: " + headings[3]);
    }
    
    /**
     * Get the headings from the frame
     * 
     * @return The string array of headings
     */
    public String[] getHeadings() {
        // Set the variable
        String[] headings = new String[4];
        
        // Assign the 4 items
        headings[0] = lblTeacher.getText().split("Teacher: ")[1];
        headings[1] = lblClass.getText().split("Class: ")[1];
        headings[2] = lblRoom.getText().split("Room: ")[1];
        headings[3] = lblDate.getText().split("Date: ")[1];
        return headings;
    }
}
