package collections;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * collections
 * Created by NhatLinh - 19127652
 * Date 12/23/2021 - 4:27 PM
 * Description: ...
 */
public class SearchedSlangWord extends SlangWord {

    private final LocalDateTime time;

    public SearchedSlangWord(String word, String definition, LocalDateTime time)
    {
        super(word, definition);
        this.time = time;
    }

    @Override
    public String toString()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return time.format(formatter) + ": " + super.toString();
    }
}
