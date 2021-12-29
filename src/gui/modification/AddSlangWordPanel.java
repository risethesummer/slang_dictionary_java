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
 * Description: ...
 */
public class AddSlangWordPanel extends ModifySlangWordPanel {

    public AddSlangWordPanel(String title, Function<String, String> onGetDefinition, BiPredicate<SlangWord, BooleanSupplier> onAddSlang)
    {
        super(title, "Insert definition", onGetDefinition);

        JButton addButton = new JButton("Add new slang word");
        addButton.setAlignmentX(CENTER_ALIGNMENT);
        addButton.addActionListener(e -> {
            SlangWord updateInformation = new SlangWord(slangPanel.getTextField().getText(), updateDefinition.getTextField().getText());
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
