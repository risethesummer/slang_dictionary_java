package gui.modification;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * gui.modification
 * Created by NhatLinh - 19127652
 * Date 1/7/2022 - 11:29 AM
 * Description: ...
 */
public class DeleteSlangWordPanel extends ModifySlangWordPanel {

    /**
     * Construct a new panel with full information
     * @param onDelete        the delete callback
     * @param title           the title for the function
     * @param onGetDefinition the callback used to get definition when user input slang words (get hints)
     */
    public DeleteSlangWordPanel(String title, Function<String, String> onGetDefinition, Consumer<String> onDelete) {
        super(title, onGetDefinition);

        JButton deleteButton = new JButton("Delete the slang word");
        deleteButton.addActionListener(e -> {
            String result = oldDefinition.getTextField().getText();
            if (result == null || result.isBlank())
            {
                JOptionPane.showMessageDialog(this, "The slang word does not exist");
                return;
            }
            int choice = JOptionPane.showOptionDialog(this,
                    "Do you really want to delete the slang word?",
                    "Confirm deleting the slang word",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Yes", "No"},
                    0);
            if (choice != 0)
                return;
            onDelete.accept(slangPanel.getTextField().getText());
            JOptionPane.showMessageDialog(this, "Delete the slang word successfully");
        });
        deleteButton.setAlignmentX(CENTER_ALIGNMENT);
        deleteButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        mainPanel.add(deleteButton);
        mainPanel.add(Box.createVerticalStrut(20));
    }
}
