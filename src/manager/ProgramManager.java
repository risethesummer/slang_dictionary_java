package manager;

import collections.BidirectionalMap;
import collections.SearchedSlangWord;
import gui.ProgramFrame;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * manager
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 11:49 AM
 * Description: ...
 */
public class ProgramManager {

    private final int MAX = 8000;

    private final String SLANG_PATH = "slangDefault.txt";
    private final String DEFINITION_PATH = "definitionDefault.txt";
    private final String ORIGINAL_PATH = "src/slang.txt";

    private static ProgramManager instance;

    private HashSet<SearchedSlangWord> searchedSlangWords = new HashSet<>();

    private BidirectionalMap data = new BidirectionalMap(MAX);

    private ProgramFrame frame;

    public static ProgramManager getInstance()
    {
        if (instance == null)
            instance = new ProgramManager();
        return instance;
    }

    private ProgramManager()
    {
        if (new File(SLANG_PATH).isFile() && new File(DEFINITION_PATH).isFile())
            data.loadStructuredFile(SLANG_PATH, DEFINITION_PATH);
        else
            data.load(ORIGINAL_PATH);

        Function<String, String> onSearchDefinition = s->{
            String result = data.getSlangMap().get(s);
            if (result != null)
                searchedSlangWords.add(new SearchedSlangWord(s, result, LocalDateTime.now()));
            return result;
        };

        Supplier<List<String>> onGetHistory = () ->
        {
            try
            {
                List<String> words = new ArrayList<>(searchedSlangWords.size());
                for (SearchedSlangWord w : searchedSlangWords)
                    words.add(w.toString());
                return words;
            }
            catch (Exception e)
            {
                return null;
            }
        };

        frame = new ProgramFrame(onSearchDefinition, data::getSlangWords, onGetHistory, data::add, data::update, data::delete, () -> data.saveFile("", ""), null);
    }


    public static void main(String[] args)
    {
        ProgramManager.getInstance();
    }
}
