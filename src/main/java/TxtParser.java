import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;


public class TxtParser<T> implements Parser<T>{

    public Collection<T> readFile(Function<String, ?> func, String str) throws IOException {

        Collection<T> currentFileList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(Paths.get("").toAbsolutePath().toString() + File.separator + str),
                        StandardCharsets.UTF_8))) {
            String s;
            while ((s = reader.readLine()) != null) {
                try {
                    currentFileList.add((T) func.apply(s));
                } catch (NumberFormatException ex) {
                    System.err.println("Wrong data format: " + ex.getMessage());
                }
            }
        }
        return currentFileList;
    }

    public void wright(Collection<T> c, String str) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(Paths.get("").toAbsolutePath().normalize().toString() + "\\" + str)) {
            for (T item : c) {
                out.println(item);
            }
            out.flush();

        }
    }
}

