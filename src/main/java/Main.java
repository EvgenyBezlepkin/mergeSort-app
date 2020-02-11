
import org.apache.commons.cli.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        ConsolePresenter presenter = new ConsolePresenter();
        if (args.length == 0) {
            presenter.showError("Empty arguments");
            return;
        }

        Option asc = Option.builder("a").desc("ascending sort").build();
        Option desc = Option.builder("d").desc("descending sort").build();
        Option i = Option.builder("i").desc("numeric data format").build();
        Option s = Option.builder("s").desc("string data format").build();

        OptionGroup optionGroupDatas = new OptionGroup();
        optionGroupDatas
                .addOption(i)
                .addOption(s)
                .setRequired(true);

        HelpFormatter formatter = new HelpFormatter();

        Options options = new Options();
        options
                .addOption(asc)
                .addOption(desc)
                .addOptionGroup(optionGroupDatas);

        CommandLine line;
        try {
            if (args[0].equals("-help")) {
                formatter.printHelp("help",
                        "Required the following commands: " + System.lineSeparator() +
                                "the data sorting key is -a as ascending or -d as descending or empty (-d) " + System.lineSeparator() +
                                "the data typing key is -i as number or -s as string, the key is required " + System.lineSeparator() +
                                "the name of the output file is required " + System.lineSeparator() +
                                "the name of the input files (min 1) is required"+ System.lineSeparator() +
                                "the files must contain in the program directory and you must enter the file name only",
                        options, "");
                return;
            }
            line = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return;
        }

        int countOptions = line.getOptions().length;
        int countArgs = args.length;
        List<String> files = Arrays.asList(args);

        switch (countOptions) {
            case 1:
                if (countArgs <= 2) {
                    presenter.showError("Missing outputFile or inputFile");
                    return;
                }
                changeFormatType(line, Engine.FormatEnum.ASC, files.subList(1, files.size()));
                break;
            case 2:
                if (countArgs <= 3) {

                    System.out.println("Missing outputFile or inputFile");
                    return;
                }
                if (line.hasOption("a")) {
                    changeFormatType(line, Engine.FormatEnum.ASC, files.subList(2, files.size()));
                }
                if (line.hasOption("d")) {
                    changeFormatType(line, Engine.FormatEnum.DESC, files.subList(2, files.size()));
                }
                break;
            default:
                presenter.showError("Unknown command.");
        }
    }

    private static void changeFormatType(CommandLine line, Engine.FormatEnum fe, Collection<String> files) {
        ConsolePresenter presenter = new ConsolePresenter();
        if (line.hasOption("s")) {
            Parser<String> txtParser = new TxtParser<>();
            Engine<String> engine = new Engine<>(fe, Engine.FormatEnum.STRING, files, txtParser, presenter);
            engine.sort();
        }
        if (line.hasOption("i")) {
            Parser<Integer> txtParser = new TxtParser<>();
            Engine<Integer>engine = new Engine<>(fe, Engine.FormatEnum.INTEGER, files, txtParser, presenter);
            engine.sort();
        }
    }
}
