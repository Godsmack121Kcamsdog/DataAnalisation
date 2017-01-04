package numericMethods.lab1.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import numericMethods.lab1.sample.logic.Function;
import numericMethods.lab1.sample.logic.interpol.Form;
import numericMethods.lab1.sample.logic.interpol.LagrangeManager;
import numericMethods.lab1.sample.logic.interpol.LeastSquareManager;
import numericMethods.lab1.sample.logic.interpol.NewtonsManager;
import numericMethods.lab1.sample.model.Coordinate;

import java.util.LinkedList;

public class Controller {

    @FXML
    Button Count;
    /////////////////////////////////////////////////
    @FXML
    TableView<Coordinate> tableL;
    @FXML
    TableColumn<Coordinate, Double> colXL;
    @FXML
    TableColumn<Coordinate, Double> colfL;
    @FXML
    TableColumn<Coordinate, String> colRL;
    @FXML
    TableColumn<Coordinate, Double> colLL;
    private ObservableList<Coordinate> DataL = FXCollections.observableArrayList();
    /////////////////////////////////////////////////////////////
    @FXML
    TableView<Coordinate> tableN;
    @FXML
    TableColumn<Coordinate, Double> colXN;
    @FXML
    TableColumn<Coordinate, Double> colfN;
    @FXML
    TableColumn<Coordinate, String> colRN;
    @FXML
    TableColumn<Coordinate, Double> colLN;
    private ObservableList<Coordinate> DataN = FXCollections.observableArrayList();
    ///////////////////////////////////////////////////////////////////////////
    @FXML
    TableView<Coordinate> tableLSM;
    @FXML
    TableColumn<Coordinate, Double> colXLSM;
    @FXML
    TableColumn<Coordinate, Double> colfLSM;
    @FXML
    TableColumn<Coordinate, String> colRLSM;
    @FXML
    TableColumn<Coordinate, Double> colLLSM;
    private ObservableList<Coordinate> DataLSM = FXCollections.observableArrayList();
    ////////////////////////////////////////////////////////////////////

    @FXML
    NumberAxis xAxis = new NumberAxis();
    @FXML
    NumberAxis yAxis = new NumberAxis();
    @FXML
    LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
    /////////////////////////////////////////////////////////////////////////

    private final Double ALPHA;
    private final Double BETA;

    private Function func = new Function(ALPHA = -1.0, BETA = -3.25, 5);
    private LeastSquareManager lsm = new LeastSquareManager(func, 5);
    private NewtonsManager nf = new NewtonsManager(func);
    private LagrangeManager lf = new LagrangeManager(func);

    @FXML
    public void BuildTable() {
        lsm = new LeastSquareManager(func, 5);
        nf = new NewtonsManager(func);
        lf = new LagrangeManager(func);
        form(lf, DataL, colXL, colfL, colLL, colRL, tableL);
        form(nf, DataN, colXN, colfN, colLN, colRN, tableN);
        form(lsm, DataLSM, colXLSM, colfLSM, colLLSM, colRLSM, tableLSM);
        chart();
    }


    private void chart() {
        //ArrayList<Double[]> fullnodes = func.getFullnodes();
        chart.getData().clear();
        xAxis.setTickUnit(1);
        Series<Number, Number> series = new Series<>();
        Series<Number, Number> seriesL = new Series<>();
        Series<Number, Number> seriesN = new Series<>();
        Series<Number, Number> seriesLSM = new Series<>();
        series.setName("f(x)=" + ALPHA + "*" + "cos(x)" + BETA + "*" + "tg(1/x)");
        seriesL.setName("В форме Лагранжа");
        seriesN.setName("В форме Ньютона");
        seriesLSM.setName("Методом наименьших квадратов");

        for (Coordinate coord : lf.getResult()) {
            System.out.println("lf " + coord.getValue() + "|" + coord.getLValue());
            seriesL.getData().add(new Data<>(coord.getValue(), coord.getLValue()));
        }
        for (Coordinate coord : nf.getResult()) {
            System.out.println("nf " + coord.getValue() + "|" + coord.getLValue());
            seriesN.getData().add(new Data<>(coord.getValue(), coord.getLValue()));
        }

        for (Coordinate coord : lsm.getResult()) {
            System.out.println("lsm " + coord.getValue() + "|" + coord.getLValue());
            seriesLSM.getData().add(new Data<>(coord.getValue(), coord.getLValue()));
        }

        for (Double[] coord : func.getFullnodes()) {
            System.out.println("func " + coord[0] + "|" + coord[1]);
            series.getData().add(new Data<>(coord[0], coord[1]));
        }

        Series[] seriesArray = new Series[]{series, seriesL, seriesN, seriesLSM};
        for (Series s : seriesArray) {
            chart.getData().add(s);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void form(
            Form f,
            ObservableList<Coordinate> data,
            TableColumn<Coordinate, Double> colX,
            TableColumn<Coordinate, Double> colf,
            TableColumn<Coordinate, Double> colL,
            TableColumn<Coordinate, String> colR,
            TableView<Coordinate> table
    ) {

        data.clear();
        data.addAll(f.getResult1());
        data.addAll(f.getResult2());
        colX.setCellValueFactory(cellData -> cellData.getValue().xProperty().asObject());
        colf.setCellValueFactory(cellData -> cellData.getValue().fxProperty().asObject());
        colL.setCellValueFactory(cellData -> cellData.getValue().LxProperty().asObject());
        colR.setCellValueFactory(cellData -> cellData.getValue().RxProperty());
        // заполняем таблицу данными
        table.setItems(data);
        table.setVisible(true);
    }





















































    private LinkedList<Double[]> не_костыль_а_фича() {
        LinkedList<Double[]> fullnodes = new LinkedList<>(func.getFullnodes());
        System.out.println("length"+fullnodes.size());
        fullnodes.removeFirst();
        fullnodes.addFirst(new Double[]{lf.getResult().get(0).getValue(), lf.getResult().get(0).getLValue()});
        System.out.println("length"+fullnodes.size());
        return fullnodes;
    }

    private LinkedList<Double[]> new_LinkedList(){
        return не_костыль_а_фича();
    }

}
