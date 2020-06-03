package carolineclassroomrobot;

// Dimension & Java GUI Imports
import javax.swing.*;
import java.awt.*;

/**
 * The library components for Caroline's Classroom Robot
 *
 * @author Harrison Howard
 */
public class LibraryComponents {

    /**
     * Locate a Menu
     *
     * @param menuBar the menubar to attach the menu to
     * @param menu the menu itself
     * @param menuName the name of the menu
     * @return the menu after instantiation
     */
    public static JMenu LocateMenu(JMenuBar menuBar, JMenu menu, String menuName) {
        // Instantiate the menu
        menu = new JMenu(menuName);
        // Add the menu to the menubar
        menuBar.add(menu);
        return menu;
    }

    /**
     * Locate a Menu Item
     *
     * @param event the event to add the action listener to
     * @param menu the menu to add the menuitem to
     * @param menuItem the menuitem itself
     * @param menuItemName the name of the menuitem
     * @return the menuitem after instantiation
     */
    public static JMenuItem LocateMenuItem(EventHandling event, JMenu menu, JMenuItem menuItem, String menuItemName) {
        // Instantiate the menuitem
        menuItem = new JMenuItem(menuItemName);
        // Add the menuitem to the menu
        menu.add(menuItem);
        // Add the event action listener to the menuitem
        menuItem.addActionListener(event);
        return menuItem;
    }

    /**
     * Locate a Label
     *
     * @param frame the frame to add the label to
     * @param layout the layout of the frame
     * @param text the text that goes into the label
     * @param size the size of the label
     * @param location the location of the label
     * @param background the background colour
     * @return the label after instantiation
     */
    public static JLabel LocateLabel(JFrame frame, SpringLayout layout, String text, int[] size, int[] location, int[] background, boolean setBackground, boolean isBold) {
        // Instantiate the Label
        JLabel label = new JLabel(text);
        if (setBackground) {
            // Set the opaque to true
            label.setOpaque(true);
            // Set the background and foregorund colour
            label.setBackground(new Color(background[0], background[1], background[2]));
        }
        // Check if bold
        if (!isBold) {
            // Set the text to bold
            label.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        }
        // Set the preffered size
        label.setPreferredSize(new Dimension(size[0], size[1]));
        // Add to frame
        frame.add(label);
        // Set the location
        layout.putConstraint(SpringLayout.WEST, label, location[1], SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, label, location[0], SpringLayout.NORTH, frame);
        return label;
    }

    /**
     * Locate a Text Field
     *
     * @param frame the frame to add the text field to
     * @param layout the layout of the frame
     * @param text the text that goes into the text field
     * @param size the size
     * @param location the location
     * @return the textfield after instantiation
     */
    public static JTextField LocateTextField(JFrame frame, SpringLayout layout, String text, int[] size, int[] location, boolean isBold) {
        // Instantiate the TextField
        JTextField txtField = new JTextField(text);
        // Check if bold is enabled
        if (isBold) {
            // Set the text to bold
            txtField.setFont(new Font(Font.DIALOG, Font.BOLD, 11));
        }
        // Set txtField preferred size
        txtField.setPreferredSize(new Dimension(size[0], size[1]));
        // Add txtField to frame
        frame.add(txtField);
        // Set the location of txtField
        layout.putConstraint(SpringLayout.WEST, txtField, location[1], SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, txtField, location[0], SpringLayout.NORTH, frame);
        return txtField;
    }

    /**
     * Locate a Text Area
     *
     * @param frame the frame to add the text area to
     * @param layout the layout of the frame
     * @param text the text that goes into the text area
     * @param size the size
     * @param location the location
     * @param edit the editable state
     * @return the textarea after instantiation
     */
    public static JTextArea LocateTextArea(JFrame frame, SpringLayout layout, String text, int[] size, int[] location, boolean edit) {
        // Instantiate the TextArea
        JTextArea txtArea = new JTextArea(text, size[1], size[0]);
        // Set the editable state
        txtArea.setEditable(edit);
        // Add txtArea to frame
        frame.add(txtArea);
        // Set the location of txtArea
        layout.putConstraint(SpringLayout.WEST, txtArea, location[0], SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, txtArea, location[1], SpringLayout.NORTH, frame);
        return txtArea;
    }

    /**
     * Locate a Button
     *
     * @param event the event to add the action listener to
     * @param frame the frame to add the button to
     * @param layout the layout of the frame
     * @param text the text that goes into the button
     * @param location the location
     * @param size the size
     * @return the button after the instantiation
     */
    public static JButton LocateButton(EventHandling event, JFrame frame, SpringLayout layout, String text, int[] location, int[] size) {
        // Instantiate the Button
        JButton btnButton = new JButton(text);
        // Add btnButton to frame
        frame.add(btnButton);
        // Set the location of btnButton
        layout.putConstraint(SpringLayout.WEST, btnButton, location[0], SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, btnButton, location[1], SpringLayout.NORTH, frame);
        // Set Size
        btnButton.setPreferredSize(new Dimension(size[0], size[1]));
        // Add the event listener
        btnButton.addActionListener(event);
        return btnButton;
    }
}
