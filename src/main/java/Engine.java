import java.io.*;
import java.util.*;
import java.util.function.Function;


public class Engine<T> {

    public enum FormatEnum {
        INTEGER, STRING, ASC, DESC,
    }

    private FormatEnum feOrder;
    private FormatEnum feData;
    private String outFile;
    private Collection<String> inFiles;
    private Parser<T> parser;
    private ConsolePresenter presenter;


    Engine(FormatEnum feOrder, FormatEnum feData, Collection<String> files,
           Parser<T> parser, ConsolePresenter presenter) {
        this.feOrder = feOrder;
        this.feData = feData;
        ArrayList<String> al = new ArrayList<>(files);
        this.outFile = al.get(0);
        this.inFiles = new ArrayList<>();
        this.inFiles.addAll(al.subList(1, al.size()));
        this.parser = parser;
        this.presenter = presenter;

        presenter.showInfo("the entered sort key is " + feOrder);
        presenter.showInfo("the entered data key is " + feData);
    }


    public void sort() {

        List<T> resultList = new ArrayList<>();
        Collection<T> currentFile;

        SortModel<T> sm = createSortModel();

        for (String inFile : inFiles) {
            currentFile = this.parse(inFile);
            resultList.addAll(currentFile);
            resultList = sm.sortFile(resultList);
        }

        try {
            parser.wright(resultList, outFile);
        } catch (FileNotFoundException e) {
            presenter.showError("FileNotFound - File must contains in the program directory and you must enter the file name only");
        }
        presenter.showInfo("The program is finished");
    }

    private SortModel<T> createSortModel() {
        return new SortModel<>(new SortComparator<>(feOrder));
    }


    private Collection<T> parse(String inFile) {

        Collection<T> currentFile = null;

        try {
            Function<String, ?> fString = o -> o;
            Function<String, ?> fInteger = Integer::parseInt;

            if (feData == FormatEnum.STRING) {
                currentFile = parser.readFile(fString, inFile);
            }
            if (feData == FormatEnum.INTEGER) {
                currentFile = parser.readFile(fInteger, inFile);
            }
            try {
                if (Objects.requireNonNull(currentFile).isEmpty()) {
                    presenter.showError("Empty file: " + inFile);
                }
            } catch (NullPointerException ex) {
                presenter.showError("Empty file");
            }

        } catch (FileNotFoundException ex) {
            presenter.showError("FileNotFound - File must contains in the program directory and you must enter the file name only");
        } catch (IOException ex) {
            presenter.showError("Unknown IOException");
        }

        return currentFile;
    }

}
