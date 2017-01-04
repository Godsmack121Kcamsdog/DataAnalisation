package dataAnalisation.part1.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

class FileReader {
    private static final String DEFAULT_FILE = "data0.int";

    static List<Double> read() throws IOException {
        return read(new File(DEFAULT_FILE));
    }

    static List<Double> read(File file) throws IOException {
        return Files.lines(file.toPath()).map(Double::valueOf).collect(toList());
    }

    static List<Double> readwithREGEX(File file) throws IOException{
        List<Double> list = new ArrayList<>();
        String pattern = "[0-9]+";
        String text = Files.lines(file.toPath()).toString();
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        while(m.find()) {
            list.add(Double.parseDouble(text.substring(m.start(), m.end())));
        }
        System.out.println("ist"+list);
        return list;
    }

}
