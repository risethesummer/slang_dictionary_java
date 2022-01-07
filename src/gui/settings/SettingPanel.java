package gui.settings;
import gui.FunctionPanel;
import javax.swing.*;

/**
 * gui.settings
 * Created by NhatLinh - 19127652
 * Date 12/29/2021 - 11:38 PM
 * Description: A panel used for setting the program
 */
public class SettingPanel extends FunctionPanel {

    /**
     * Construct a new setting panel with full information
     * @param title the title for the function
     * @param onReset the callback when resetting the data (back to use the original slang list)
     * @param onSave the callback when saving the current data
     */
    public SettingPanel(String title, Runnable onReset, Runnable onSave)
    {
        super(title);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JButton resetButton = new JButton("Reset to the original slang word list");
        resetButton.addActionListener(e -> {
            try
            {
                int choice = JOptionPane.showOptionDialog(this,
                        "Do you really want to reset the slang word list?",
                        "Confirm reset the slang word list",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Yes", "No"},
                        0);
                if (choice != 0)
                    return;
                onReset.run();
                JOptionPane.showMessageDialog(this, "Reset the list successfully");
            }
            catch (Exception exception)
            {
                JOptionPane.showMessageDialog(this, "Failed to reset the list");
            }
        });
        resetButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton saveButton = new JButton("Save the current list");
        saveButton.addActionListener(e -> {

            try
            {
                onSave.run();
                JOptionPane.showMessageDialog(this, "Save the list successfully");
            }
            catch (Exception exception)
            {
                JOptionPane.showMessageDialog(this, "Failed to save the list");
            }
        });
        saveButton.setAlignmentX(CENTER_ALIGNMENT);

        mainPanel.add(resetButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(saveButton);
        mainPanel.add(Box.createVerticalStrut(20));
    }
}
