package gui.modification;

import collections.SlangWord;
import gui.utilities.SyncFullDefinitionListener;
import gui.utilities.TextWithCheckBox;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/29/2021 - 10:59 PM
 * Description: A panel for updating or deleting the data of the system
 */
public class UpdateSlangWordPanel extends ModifySlangWordPanel {

    /**
     * The panel containing definition rows
     */
    private final JPanel definitionsPanel;

    /**
     * The definition fields for the slang word
     */
    protected final ArrayList<TextWithCheckBox> definitionFields = new ArrayList<>();

    /**
     * Construct a new panel with full information
     * @param title the title for the function
     * @param onGetDefinition the callback used to get definition when user input slang words (get hints)
     * @param onUpdate the callback called to update the slang word (when clicking the update button)
     */
    public UpdateSlangWordPanel(String title, Function<String, String> onGetDefinition, Consumer<SlangWord> onUpdate)
    {
        super(title, onGetDefinition);

        slangPanel.getTextField().getDocument().addDocumentListener(new SyncFullDefinitionListener(slangPanel.getTextField(), onGetDefinition, this::setNewDefinitionView));

        JPanel updatePanel = new JPanel(new BorderLayout());

        JPanel outerDefinitionPanel = new JPanel(new BorderLayout());
        JPanel header = new JPanel(new GridLayout(1, 4));
        header.add(new JLabel("Old definition"));
        header.add(new JLabel("Update definition"));
        header.add(new JLabel("Check to remove definition"));
        header.add(new JLabel("Delete added row button"));
        outerDefinitionPanel.add(header, BorderLayout.PAGE_START);

        definitionsPanel = new JPanel();
        definitionsPanel.setLayout(new BoxLayout(definitionsPanel, BoxLayout.PAGE_AXIS));
        JScrollPane definitionScroll = new JScrollPane(definitionsPanel);
        outerDefinitionPanel.add(definitionScroll, BorderLayout.CENTER);

        JButton addDefinitionButton = new JButton("Add more definition");
        addDefinitionButton.addActionListener(e -> {
            //Delete the component after clicking deleting button
            TextWithCheckBox field = new TextWithCheckBox(c -> {
                definitionFields.remove(c);
                //Ignore the header row
                definitionsPanel.remove(c);
                definitionsPanel.updateUI();
            });
            definitionFields.add(field);
            definitionsPanel.add(field);
            definitionsPanel.updateUI();
        });

        JButton updateButton = new JButton("Update the slang word. The program will ignore the empty rows having no content at update column with user-added rows or keep the old definition with program-added rows");
        updateButton.addActionListener(e -> {
            String result = oldDefinition.getTextField().getText();
            if (result == null || result.isBlank())
            {
                JOptionPane.showMessageDialog(this, "The slang word does not exist");
                return;
            }

            //Get input definitions from the fields
            ArrayList<String> updateDef = new ArrayList<>(definitionFields.size());
            definitionFields.forEach(def -> {
                String text = def.getUpdateText();
                if (text != null && !text.isBlank())
                    updateDef.add(text);
            });

            SlangWord updateInformation = new SlangWord(slangPanel.getTextField().getText(), updateDef);
            onUpdate.accept(updateInformation);
            JOptionPane.showMessageDialog(this, "Update the slang word successfully");
            slangPanel.getTextField().setText(null);
            oldDefinition.getTextField().setText(null);
            setNewDefinitionView(null);
        });

        updatePanel.add(addDefinitionButton, BorderLayout.PAGE_START);
        updatePanel.add(outerDefinitionPanel, BorderLayout.CENTER);
        updatePanel.add(updateButton, BorderLayout.PAGE_END);
        mainPanel.add(updatePanel);
        mainPanel.add(Box.createVerticalStrut(20));
    }

    private void setNewDefinitionView(String[] definitions)
    {
        definitionsPanel.removeAll();
        //Clear the fields from the field list
        definitionFields.clear();
        if (definitions != null)
        {
            for (String def : definitions)
            {
                TextWithCheckBox checkBox = new TextWithCheckBox(null);
                checkBox.getFirstTextField().setText(def);
                definitionsPanel.add(checkBox);
                definitionFields.add(checkBox);
            }
        }
        definitionsPanel.updateUI();
    }
}
