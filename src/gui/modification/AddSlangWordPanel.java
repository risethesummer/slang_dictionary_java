package gui.modification;
import collections.SlangWord;

import javax.swing.*;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 4:40 PM
 * Description: A panel for adding new slang word
 */
public class AddSlangWordPanel extends ModifySlangWordPanel {

    /**
     * Construct a new panel with full information
     * @param title the title for the function
     * @param onGetDefinition the callback used to get definition when user input slang words (get hints)
     * @param onAddSlang the callback called to add the slang word (when clicking the add button)
     */
    public AddSlangWordPanel(String title, Function<String, String> onGetDefinition, BiPredicate<SlangWord, BooleanSupplier> onAddSlang)
    {
        super(title, "Insert definition", onGetDefinition);

        JButton addButton = new JButton("Add new slang word");
        addButton.setAlignmentX(CENTER_ALIGNMENT);
        addButton.addActionListener(e -> {
            String insertDef = updateDefinition.getTextField().getText();
            if (insertDef == null || insertDef.isBlank())
            {
                JOptionPane.showMessageDialog(this, "Can not leave the definition empty!", "The definition is empty!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            SlangWord updateInformation = new SlangWord(slangPanel.getTextField().getText(), insertDef);
            boolean add = onAddSlang.test(updateInformation, () -> {
                int choice = JOptionPane.showOptionDialog(this,
                        "The slang word has already existed\n" +
                                "Do you want to overwrite it?",
                        "Confirm overwriting the slang word",
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.YES_NO_OPTION,
                        null,
                        new String[]{"Yes", "No"},
                        0);
                return choice == 0;
            });

            String msg = add ? "Added the slang word successfully" : "Failed to add the new slang word";
            JOptionPane.showMessageDialog(this, msg);
        });
        mainPanel.add(addButton);
    }
}
