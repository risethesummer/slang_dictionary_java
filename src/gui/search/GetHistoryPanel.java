package gui.search;

import gui.FunctionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 3:57 PM
 * Description: ...
 */
public class GetHistoryPanel extends FunctionPanel {

    private JList historyList;

    public GetHistoryPanel(String title, Supplier<List<String>> onViewHistory)
    {
        super(title);
        mainPanel.setLayout(new BorderLayout());

        JButton loadButton = new JButton("Load");

        historyList = new JList();

        loadButton.addActionListener(e -> {
            List<String> history = onViewHistory.get();
            if (history != null)
            {
                Object[] object = history.toArray();
                SwingUtilities.invokeLater(() -> {
                    historyList.setListData(object);
                });
            }
        });

        mainPanel.add(loadButton, BorderLayout.PAGE_START);
        mainPanel.add(historyList, BorderLayout.CENTER);
    }
}
