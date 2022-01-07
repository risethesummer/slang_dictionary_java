package gui.modification;

import gui.FunctionPanel;
import gui.utilities.SyncDefinitionListener;
import gui.utilities.TextWithLabel;
import javax.swing.*;
import java.util.function.Function;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/29/2021 - 11:14 PM
 * Description: A panel used for modifying the data of the system
 */
public class ModifySlangWordPanel extends FunctionPanel {

    /**
     * The field for inputting slang word
     */
    protected final TextWithLabel slangPanel = new TextWithLabel("Slang word");
    /**
     * The field showing current data (current definition by searched slang word)
     */
    protected final TextWithLabel oldDefinition = new TextWithLabel("Current definition");

    /**
     * Construct a new panel with full information
     * @param title the title for the function
     * @param onGetDefinition the callback used to get definition when user input slang words (get hints)
     */
    public ModifySlangWordPanel(String title, Function<String, String> onGetDefinition)
    {
        super(title);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        slangPanel.getTextField().getDocument().addDocumentListener(new SyncDefinitionListener(slangPanel.getTextField(), oldDefinition.getTextField(), onGetDefinition));
        oldDefinition.getTextField().setEditable(false);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(slangPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(oldDefinition);
        mainPanel.add(Box.createVerticalStrut(20));
    }
}
