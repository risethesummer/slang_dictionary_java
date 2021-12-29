package gui;

import collections.SlangWord;
import gui.modification.AddSlangWordPanel;
import gui.modification.UpdateDeleteSlangWordPanel;
import gui.search.GetHistoryPanel;
import gui.search.SearchPanel;
import gui.settings.SettingPanel;

import javax.swing.*;
import java.awt.*;
import java.util.function.*;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 10:20 AM
 * Description: ...
 */
public class ProgramFrame extends JFrame {

    private final String SEARCH_DEFINITION = "Search definition by slang word";
    private final String SEARCH_SLANG = "Search slang words by definition";
    private final String GET_HISTORY = "Get search history";
    private final String ADD_SLANG = "Add new slang word";
    private final String UPDATE_SLANG = "Update/Delete a slang word";
    private final String SETTING = "Settings";

    public ProgramFrame(Function<String, String> onSearchDefinition, Function<String, String> onSearchSlang,
                        Supplier<java.util.List<String>> onGetHistory, BiPredicate<SlangWord, BooleanSupplier> onAddSlangWord,
                        Consumer<SlangWord> onUpdate, Consumer<String> onDelete, Runnable onReset, Runnable onSave)
    {
        JTabbedPane mainPanel = new JTabbedPane();
        mainPanel.setFont(new Font( "Arial", Font.BOLD, 15 ));
        mainPanel.setTabPlacement(JTabbedPane.LEFT);
        mainPanel.add(SEARCH_DEFINITION, new SearchPanel(SEARCH_DEFINITION, "Slang", onSearchDefinition));
        mainPanel.add(SEARCH_SLANG, new SearchPanel(SEARCH_SLANG, "Definition", onSearchSlang));
        mainPanel.add(GET_HISTORY, new GetHistoryPanel(GET_HISTORY, onGetHistory));
        mainPanel.add(ADD_SLANG, new AddSlangWordPanel(ADD_SLANG, onSearchDefinition, onAddSlangWord));
        mainPanel.add(UPDATE_SLANG, new UpdateDeleteSlangWordPanel(UPDATE_SLANG, onSearchDefinition, onUpdate, onDelete));
        mainPanel.add(SETTING, new SettingPanel(SETTING, onReset, onSave));
        this.getContentPane().add(mainPanel);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
