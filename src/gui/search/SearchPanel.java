package gui.search;

import gui.FunctionPanel;
import gui.utilities.SyncDefinitionListener;
import gui.utilities.TextWithLabel;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 11:26 AM
 * Description: ...
 */
public class SearchPanel extends FunctionPanel {

    private final TextWithLabel searchPanel;
    private final JTextArea resultTA;
    private final JButton searchButton;

    public SearchPanel(String title, String searchDisplay, Function<String, String> onSearch)
    {
        super(title);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        searchPanel = new TextWithLabel(searchDisplay);

        JLabel resultLabel = new JLabel("Result");
        resultLabel.setAlignmentX(CENTER_ALIGNMENT);

        resultTA = new JTextArea();
        JScrollPane resultScroll = new JScrollPane(resultTA);
        resultTA.setEditable(false);

        searchButton = new JButton("Search");
        searchButton.setAlignmentX(CENTER_ALIGNMENT);
        searchButton.addActionListener(e->{
            if (!searchPanel.getTextField().getText().isBlank())
            {
                String result = onSearch.apply(searchPanel.getTextField().getText());
                SwingUtilities.invokeLater(() -> {
                    resultTA.setText(result);
                });
            }
        });

        //Add hints when inputting new text
        searchPanel.getTextField().getDocument().addDocumentListener(new SyncDefinitionListener(searchPanel.getTextField(), resultTA, onSearch));

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(searchPanel);
        mainPanel.add(searchButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(resultLabel);
        mainPanel.add(resultScroll);
        mainPanel.add(Box.createVerticalStrut(20));

    }
}
