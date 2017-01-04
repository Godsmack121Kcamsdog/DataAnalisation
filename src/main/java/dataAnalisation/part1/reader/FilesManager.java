package dataAnalisation.part1.reader;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static dataAnalisation.part1.reader.FileReader.read;

public class FilesManager {

    private final FileChooser chooser;

    public FilesManager() {
        chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));
        chooser.setTitle("Выберите файл для загрузки");
    }

    public List<Double> readData() throws IOException {
        File file = chooser.showOpenDialog(null);
        return file != null ? read(file) : read();
    }

    public void create() throws IOException {
        //File file = new File("user_data.txt");
        Runtime runtime = Runtime.getRuntime();
        try{
            runtime.exec("notepad");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
