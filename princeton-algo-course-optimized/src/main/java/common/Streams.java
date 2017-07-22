package common;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Streams {
    
    public static <T> Stream<T> fromIterable(Iterable<T> it) {
        return StreamSupport.stream(it.spliterator(), false);
    }
}
