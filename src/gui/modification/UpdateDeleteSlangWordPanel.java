package gui.modification;

import collections.SlangWord;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/29/2021 - 10:59 PM
 * Description: ...
 */
public class UpdateDeleteSlangWordPanel extends ModifySlangWordPanel {
    public UpdateDeleteSlangWordPanel(String title, Function<String, String> onGetDefinition, Consumer<SlangWord> onUpdate,
                                      Consumer<String> onDelete)
    {
        super(title, "Update definition", onGetDefinition);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton updateButton = new JButton("Update the slang word");
        updateButton.addActionListener(e -> {
            String result = oldDefinition.getTextField().getText();
            if (result == null || result.isBlank())
            {
                JOptionPane.showMessageDialog(this, "The slang word does not exist");
                return;
            }
            SlangWord updateInformation = new SlangWord(slangPanel.getTextField().getText(), updateDefinition.getTextField().getText());
            onUpdate.accept(updateInformation);
            JOptionPane.showMessageDialog(this, "Update the slang word successfully");
        });

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
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.YES_NO_OPTION,
                    null,
                    new String[]{"Yes", "No"},
                    0);
            if (choice != 0)
                return;
            onDelete.accept(slangPanel.getTextField().getText());
            JOptionPane.showMessageDialog(this, "Delete the slang word successfully");
        });

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20));
    }
}
