package pickup.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by YongGang on 2016/10/30.
 */
public class AuthorConsumer {
    public static void main(String [] args) {
        Map<Integer, Author> visitCounts1 = new HashMap<Integer, Author>() {{
            put(1, new Author(1, 2));
            put(2, new Author(2, 4));
            put(13, new Author(3, 6));
            put(4, new Author(4, 8));
            put(5, new Author(5, 10));

        }};

        Map<Integer, Author> visitCounts2 = new HashMap<Integer, Author>() {{
            put(13, new Author(30, 9));
            put(5, new Author(50, 15));
            put(7, new Author(70, 21));
        }};


        // Of course, trying to merge maps with duplicate keys will result in an exception.
        // A third lambda parameter, known as the "merger".
        // This lambda function will be called every time duplicate keys are detected.
        // The two possible values are passed as parameters, and it is left to the logic in the function,
        // to decide what the ultimate value will be.

        // 因为是 map，所以 key 不能重复；但是，
        // 是合并多个来源，这些来源中，有可能重复；
        // 第三个参数 merger，就是当 key 重复时，如何处理 value，比如：
        // map 的 key=3 是重复的，一个 count 是 6，另一个是 9，结果就是 6+9
        Map<Integer, Integer> totalVisitCounts =
            Stream.concat(visitCounts1.entrySet().stream(), visitCounts2.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,                       // the key
                        (itm) -> itm.getValue().getCount(),     // The value
                        Integer::sum        // The "merger"
                        )
                );

        System.out.println(totalVisitCounts);

    }
}
