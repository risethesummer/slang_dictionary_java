package gui.search;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/**
 * gui.search
 * Created by NhatLinh - 19127652
 * Date 12/30/2021 - 1:10 PM
 * Description: A panel for searching definition from slang word function
 */
public class SearchDefinitionPanel extends SearchPanel {

    /**
     * Construct a new searched panel with full information
     * @param title the tittle of the function
     * @param searchDisplay the text displayed on the label near the input field
     * @param onSearch the callback used for giving hints to users when they input
     * @param onSearchSaving the callback used for giving definitions to users and save the result to the system when they click the button
     */
    public SearchDefinitionPanel(String title, String searchDisplay, Function<String, String> onSearch, Function<String, String> onSearchSaving)
    {
        super(title, searchDisplay, onSearch, onSearchSaving);

        resultText = new JTextField();
        resultText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        resultText.setEditable(false);
        addHintWhenInputting();

        mainPanel.add(resultText);
        mainPanel.add(Box.createVerticalStrut(20));
    }

}
