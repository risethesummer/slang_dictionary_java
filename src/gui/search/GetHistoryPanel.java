package gui.search;

import collections.SearchedSlangWord;
import gui.FunctionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 3:57 PM
 * Description: A panel for getting search history
 */
public class GetHistoryPanel extends FunctionPanel {

    /**
     * The list showing search slang word history
     */
    private final JList searchSlangHistoryList;
    /**
     * The list showing search definition history
     */
    private final JList searchDefinitionHistoryList;

    /**
     * Construct the history panel with full information
     * @param title the title of the function
     * @param onViewDefinitionHistory the callback used to get the history in searching definition function
     * @param onViewSlangHistory the callback used to get the history in searching slang words function
     * @param loadedDefinitionHistory the history of searching definition stored in the system
     * @param loadedSlangHistory the history of searching slang words stored in the system
     */
    public GetHistoryPanel(String title, Supplier<List<String>> onViewDefinitionHistory,
                           Supplier<List<String>> onViewSlangHistory, List<SearchedSlangWord> loadedDefinitionHistory,
                           List<SearchedSlangWord> loadedSlangHistory)
    {
        super(title);
        mainPanel.setLayout(new BorderLayout());
        JTabbedPane mainTab = new JTabbedPane();

        JButton loadButton = new JButton("Load");

        searchDefinitionHistoryList = new JList();
        JScrollPane definitionScroll = new JScrollPane(searchDefinitionHistoryList);
        searchDefinitionHistoryList.setListData(loadedDefinitionHistory.toArray());

        searchSlangHistoryList = new JList();
        JScrollPane slangScroll = new JScrollPane(searchSlangHistoryList);
        searchSlangHistoryList.setListData(loadedSlangHistory.toArray());

        loadButton.addActionListener(e -> {
            List<String> defHistory = onViewDefinitionHistory.get();
            if (defHistory != null)
            {
                SwingUtilities.invokeLater(() -> searchDefinitionHistoryList.setListData(defHistory.toArray()));
            }

            List<String> slangHistory = onViewSlangHistory.get();
            if (slangHistory != null)
            {
                SwingUtilities.invokeLater(() -> searchSlangHistoryList.setListData(slangHistory.toArray()));
            }
        });

        mainTab.add("Search definition history", definitionScroll);
        mainTab.add("Search slang words history", slangScroll);
        mainPanel.add(loadButton, BorderLayout.PAGE_START);
        mainPanel.add(mainTab, BorderLayout.CENTER);
    }
}
