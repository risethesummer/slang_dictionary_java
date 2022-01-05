package collections;
import java.io.Serializable;

/**
 * collections
 * Created by NhatLinh - 19127652
 * Date 12/29/2021 - 9:44 PM
 * Description: Represents a slang word object (slang word and its definition)
 */
public class SlangWord implements Serializable {

    /**
     * The definition of the slang word
     */
    protected final String definition;
    /**
     * The slang word
     */
    protected final String word;

    /**
     * Create a new slang word with full information
     * @param word the slang word
     * @param definition the definition
     */
    public SlangWord(String word, String definition)
    {
        this.word = word;
        this.definition = definition;
    }

    /**
     * Convert the slang word to the form "slang word (definition)"
     * @return the converted slang word string
     */
    @Override
    public String toString()
    {
        return String.format("%s (%s)", word, definition);
    }

}
