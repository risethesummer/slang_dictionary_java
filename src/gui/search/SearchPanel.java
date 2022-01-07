package gui.search;
import gui.FunctionPanel;
import gui.utilities.SyncDefinitionListener;
import gui.utilities.TextWithLabel;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.function.Function;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 11:26 AM
 * Description: A panel for searching activities in the program
 */
public class SearchPanel extends FunctionPanel {

    /**
     * The panel for inputting searched key word
     */
    protected final TextWithLabel searchPanel;
    /**
     * The text field showing the result
     */
    protected JTextComponent resultText;
    /**
     * The search button calling the callback
     */
    protected final JButton searchButton;
    /**
     * The callback used to get hints when user input
     */
    protected final Function<String, String> onSearch;

    /**
     * Construct a new searched panel with full information
     * @param title the tittle of the function
     * @param searchDisplay the text displayed on the label near the input field
     * @param onSearch the callback used for giving hints to users when they input
     * @param onSearchSaving the callback used for giving results to users and save the result to the system when they click the button
     */
    public SearchPanel(String title, String searchDisplay, Function<String, String> onSearch, Function<String, String> onSearchSaving)
    {
        super(title);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        searchPanel = new TextWithLabel(searchDisplay);

        JLabel resultLabel = new JLabel("Result");
        resultLabel.setAlignmentX(CENTER_ALIGNMENT);

        searchButton = new JButton("Save search history");
        searchButton.setAlignmentX(CENTER_ALIGNMENT);
        this.onSearch = onSearch;

        searchButton.addActionListener(e -> {
            if (!searchPanel.getTextField().getText().isBlank())
            {
                String result = onSearchSaving.apply(searchPanel.getTextField().getText());
                SwingUtilities.invokeLater(() -> resultText.setText(result));
            }
        });

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(searchPanel);
        mainPanel.add(searchButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(resultLabel);
    }

    protected void addHintWhenInputting()
    {
        //Add hints when inputting new text
        searchPanel.getTextField().getDocument().addDocumentListener(new SyncDefinitionListener(searchPanel.getTextField(), resultText, onSearch));
    }
}
