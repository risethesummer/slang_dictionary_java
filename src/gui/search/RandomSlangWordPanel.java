package gui.search;

import collections.SearchedSlangWord;
import collections.SlangWord;
import gui.FunctionPanel;
import gui.utilities.TextWithLabel;
import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

/**
 * gui.search
 * Created by NhatLinh - 19127652
 * Date 12/30/2021 - 1:33 PM
 * Description: A panel for getting and showing random slang words
 */
public class RandomSlangWordPanel extends FunctionPanel {

    /**
     * The field showing current random slang word
     */
    private final TextWithLabel currentRandom = new TextWithLabel("The random word");
    /**
     * The model of history list
     */
    private final DefaultListModel historyListModel = new DefaultListModel();

    /**
     * Construct the random panel with full information
     * @param title the title of the function
     * @param onRandom the callback used to get random slang word when users click the button
     */
    public RandomSlangWordPanel(String title, Supplier<SlangWord> onRandom)
    {
        super(title);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        this.currentRandom.getTextField().setEditable(false);

        //Make sure the button stands in the center of the panel
        JPanel randomButtonOuterPanel = new JPanel();
        JButton randomButton = new JButton("Random a slang word");
        randomButton.addActionListener(e -> {
            SlangWord randWord = onRandom.get();
            this.currentRandom.getTextField().setText(randWord.toString());
            historyListModel.add(0, new SearchedSlangWord(randWord).toString());
        });
        randomButtonOuterPanel.add(randomButton);

        JPanel historyPanel = new JPanel(new BorderLayout());
        JLabel historyLabel = new JLabel("Random history");
        historyLabel.setAlignmentY(LEFT_ALIGNMENT);
        JList<String> historyList = new JList<>(historyListModel);
        JScrollPane scrollList = new JScrollPane(historyList);
        historyPanel.add(historyLabel, BorderLayout.PAGE_START);
        historyPanel.add(scrollList, BorderLayout.CENTER);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(randomButtonOuterPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(currentRandom);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(historyPanel);
        mainPanel.add(Box.createVerticalStrut(20));
    }
}
