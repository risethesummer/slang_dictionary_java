package collections;
import java.io.Serializable;
import java.util.List;

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
    protected final List<String> definition;
    /**
     * The slang word
     */
    protected final String word;

    /**
     * Create a new slang word with full information
     * @param word the slang word
     * @param definition the definition
     */
    public SlangWord(String word, List<String> definition)
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
        return String.format("%s (%s)", word, asString(definition, "|"));
    }


    /**
     * Convert a list of words to a string
     * @param parts the converted slang words
     * @param splitter the splitter between the words
     * @return the string of slang words
     */
    public static String asString(List<String> parts, String splitter)
    {
        try
        {
            StringBuilder format = new StringBuilder();
            for (String slang : parts)
                format.append(slang).append(splitter);
            //Delete the last char
            format.deleteCharAt(format.length() - 1);
            return format.toString();
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
