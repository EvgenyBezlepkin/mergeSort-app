import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;

public interface Parser<T> {
    Collection readFile(Function<String, ?> func, String str) throws IOException;
    void wright(Collection<T> c, String str) throws FileNotFoundException;
}
