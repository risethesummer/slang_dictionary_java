package gui.modification;

import gui.FunctionPanel;
import gui.utilities.SyncDefinitionListener;
import gui.utilities.TextWithLabel;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/29/2021 - 11:14 PM
 * Description: ...
 */
public class ModifySlangWordPanel extends FunctionPanel {

    protected final TextWithLabel updateDefinition;
    protected final TextWithLabel slangPanel = new TextWithLabel("Slang word");
    protected final TextWithLabel oldDefinition = new TextWithLabel("Current definition");

    public ModifySlangWordPanel(String title, String updateText, Function<String, String> onGetDefinition)
    {
        super(title);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        updateDefinition = new TextWithLabel(updateText);
        slangPanel.getTextField().getDocument().addDocumentListener(new SyncDefinitionListener(slangPanel.getTextField(), oldDefinition.getTextField(), onGetDefinition));
        oldDefinition.getTextField().setEditable(false);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(slangPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(oldDefinition);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(updateDefinition);
        mainPanel.add(Box.createVerticalStrut(20));
    }
}
