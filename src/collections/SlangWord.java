package collections;

/**
 * collections
 * Created by NhatLinh - 19127652
 * Date 12/29/2021 - 9:44 PM
 * Description: ...
 */
public class SlangWord {

    protected final String definition;
    protected final String word;

    public SlangWord(String word, String definition)
    {
        this.word = word;
        this.definition = definition;
    }

    @Override
    public String toString()
    {
        return String.format("%s (%s)", word, definition);
    }

}
