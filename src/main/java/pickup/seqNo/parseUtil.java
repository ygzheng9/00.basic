package pickup.seqNo;

import javafx.util.Pair;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by YongGang on 2016/11/3.
 */
public class parseUtil {

    public static void main(String[] args) {
//         parseFile("c:/tmp/SBS_list.txt");

        dumpFile("c:/tmp/SBS_list.csv");
    }

    // TODO: 真正数据的第一行总有引号，还是所有都需要有引号，不是 Excel 格式，因为打开时总警告；
    public static void parseFile(final String inFileName) {
        try {
            // read all lines
            List<String> lines = Files.readAllLines(Paths.get(inFileName));

            // setup output file
            final String outputFile = genOutFileName(inFileName, false);
            PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(
                        new FileOutputStream(outputFile), "utf-8"));

            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader("ID", "From", "To");
            CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            // remove empty lines, keep the original order
            List<String> noEmptyLines =
                    lines.stream()
                            .sequential()
                            .filter( line -> line.length() > 0 )
                            .collect(Collectors.toList());

            // change 2 lines to 1 line(obj)
            List<String[]> csvData =
                IntStream.range(1, noEmptyLines.size() / 2)
                    .sequential()
                    .mapToObj(i -> new TranslationItem(noEmptyLines.get(2 * (i-1) + 1), noEmptyLines.get(2 * (i-1))))
                    .map(TranslationItem::breakToCSV)
//                    .forEach(csvFilePrinter::printRecord);
                    .collect(Collectors.toList());

            csvFilePrinter.printRecords(csvData);

            fileWriter.flush();
            fileWriter.close();
            csvFilePrinter.close();
        } catch (IOException e) {
            System.out.println("parseFile Failed...");
            e.printStackTrace();
        }
    }

    // TODO: how to read with the correct file encoding?

    public static void dumpFile(final String fileName) {
        try {
            // read in all
            Reader in = new InputStreamReader(new FileInputStream(fileName), "utf-8");

            // skip the header
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);

            // setup output file
            final String outputFile = genOutFileName(fileName, true);
            PrintWriter outFile = new PrintWriter(new OutputStreamWriter(
                            new FileOutputStream(outputFile), "utf-8"));

            StreamSupport.stream(records.spliterator(), false)
                    .sequential()
                    .map((record) -> TranslationItem.buildUp(record.get(0), record.get(1), record.get(2)))
                    .map(TranslationItem::dump)
                    .flatMap(Arrays::stream)
                    .forEach(outFile::println);

            outFile.flush();
            outFile.close();
        } catch (IOException e) {
            System.out.println("dumpFile Failed...");
            e.printStackTrace();
        }
    }

    public static String genOutFileName(final String inFileName, final boolean isDump) {
        final int i = inFileName.lastIndexOf(".");
        final String EXT = isDump ? "_out.txt" : ".csv";

        return inFileName.substring(0, i) + EXT;
    }

}
