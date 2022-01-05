package collections;
import java.util.List;

/**
 * collections
 * Created by NhatLinh - 19127652
 * Date 12/30/2021 - 2:20 PM
 * Description: A collection contains the content of a question in puzzles
 */
public record Question(String question, List<String> answer, int right) {

    /**
     * The number of answers of a question
     */
    public static final int NUMBER_OF_ANSWER = 4;
}
