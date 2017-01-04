package dataAnalisation.part2.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

class FileReader {

    private static final String DEFAULT_FILE = "normdat.txt";

    static List<List<Double>> read(File file) throws IOException {

        List<String> list = Files.lines(file.toPath()).map(d -> d).collect(toList());
        List<List<Double>> rez = new ArrayList<>();
        for (int i = 0; i < list.get(0).split("     ").length; i++)
            rez.add(new ArrayList<>());

        for (String aList : list) {
            String[] tmp = aList.split("     ");
            for (int j = 0; j < tmp.length; j++) {
                if (tmp[j] != null) rez.get(j).add(Double.parseDouble(tmp[j]));
            }
        }
        return rez;
    }

    static List<List<Double>> read() throws IOException {
        return read(new File(DEFAULT_FILE));
    }
}
