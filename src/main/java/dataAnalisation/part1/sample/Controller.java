package dataAnalisation.part1.sample;

import dataAnalisation.part1.reader.FilesManager;
import dataAnalisation.part1.sample.analysers.DataAnalyser;
import dataAnalisation.part1.sample.models.AgreementCriterionRow;
import dataAnalisation.part1.sample.models.ClassRow;
import dataAnalisation.part1.sample.models.UnchangedСharacteristicRow;
import dataAnalisation.part1.sample.models.VarRow;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private LineChart<Double, Double> varNetchart;
    @FXML
    private NumberAxis netZ;

    @FXML
    private LineChart<Double, Double> classbarchart;
    @FXML
    private LineChart<Double, Double> varlinechart;
    @FXML
    private LineChart<Double, Double> classlinechart;

    @FXML
    private TableView<AgreementCriterionRow> agreementTableView;
    @FXML
    private TableColumn<AgreementCriterionRow, Double> criterion;
    @FXML
    private TableColumn<AgreementCriterionRow, Double> critical_value;
    @FXML
    private TableColumn<AgreementCriterionRow, String> conclusion;

    @FXML
    private TableView<String> namesTableView;
    @FXML
    private TableColumn<String, String> names;


    @FXML
    private TableView<VarRow> varsTableView;
    @FXML
    private TableColumn<VarRow, Integer> number;
    @FXML
    private TableColumn<VarRow, Double> value;
    @FXML
    private TableColumn<VarRow, Integer> frequency;
    @FXML
    private TableColumn<VarRow, Double> r_Frequency;
    @FXML
    private TableColumn<VarRow, Double> distrib;

//// TODO: 12/29/2016 kvantil Fisher, vilcolsonSigned, mediumCheck 
    @FXML
    private TableView<ClassRow> classTableView;
    @FXML
    private TableColumn<ClassRow, Integer> number_1;
    @FXML
    private TableColumn<ClassRow, ArrayList<Double>> borders;
    @FXML
    private TableColumn<ClassRow, Integer> c_Frequency;
    @FXML
    private TableColumn<ClassRow, Double> c_RFrequency;
    @FXML
    private TableColumn<ClassRow, Double> c_Distrib;


    @FXML
    private TableView<UnchangedСharacteristicRow> chareсteristicsTableView;
    @FXML
    private TableColumn<UnchangedСharacteristicRow, Double> value_1;
    @FXML
    private TableColumn<UnchangedСharacteristicRow, String> sq_conc;
    @FXML
    private TableColumn<UnchangedСharacteristicRow, ArrayList<String>> truth_interval;


    @FXML
    private Button openFileButton;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button createFileButton;

    @FXML
    private CheckBox delAnomData;
    @FXML
    private CheckBox changeM;
    @FXML
    private TextField anom;
    @FXML
    private TextField setM;


    private final FilesManager manager = new FilesManager();
    private final ArrayList<VarRow> varTabList = new ArrayList<>();
    private final ArrayList<ClassRow> classTabList = new ArrayList<>();
    private final ArrayList<UnchangedСharacteristicRow> unchangedTabList = new ArrayList<>();
    private final ArrayList<Double[]> varNetList = new ArrayList<>();
    private final ArrayList<Double[]> densityList = new ArrayList<>();
    private final ArrayList<Double[]> distributionList = new ArrayList<>();
    private final ArrayList<AgreementCriterionRow> agreeList = new ArrayList<>();
    private DataAnalyser analyser;

    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        initVarsTable();
        initNamesTable();
        initClassesTable();
        initChracteristicTable();
        initAgreeTable();

        openFileOnClick();
        createFileOnClick();
        changeDataClick();
    }


    private void openFileOnClick() {
        openFileButton.setOnMouseClicked(click -> {
            clearAllTabs();
            try {
                btnConfirm.setDisable(false);
                List<Double> list = manager.readData();
                //System.out.println("list " + list.size());
                analyser = new DataAnalyser(list, "Pareto");
                analyser.defaultSplitting();
                anom.setText(analyser.getAnomString());
                tabs_addAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setAllTablesItems();
            setAllCharts();
        });
    }

    private void changeDataClick() {
        btnConfirm.setOnMouseClicked(click -> {
            if (delAnomData.isSelected() || changeM.isSelected()) {
                clearAllTabs();
                if (delAnomData.isSelected()) {
                    List<Double> list = analyser.reload();
                    analyser = new DataAnalyser(list,"Pareto");
                }
                if (changeM.isSelected()) {
                    analyser.setSplitting(Integer.parseInt(setM.getText()));
                }
                System.out.println();
                anom.setText(analyser.getAnomString());
                tabs_addAll();
                setAllTablesItems();
                setAllCharts();
            }
        });
    }

    private void createFileOnClick() {
        createFileButton.setOnMouseClicked(click -> {
            try {
                manager.create();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void initVarsTable() {
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        frequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        r_Frequency.setCellValueFactory(new PropertyValueFactory<>("r_Frequency"));
        distrib.setCellValueFactory(new PropertyValueFactory<>("distrib"));
    }

    private void initAgreeTable() {
        criterion.setCellValueFactory(new PropertyValueFactory<>("criterion"));
        critical_value.setCellValueFactory(new PropertyValueFactory<>("criticalValue"));
        conclusion.setCellValueFactory(new PropertyValueFactory<>("conclusion"));
    }

    private void initClassesTable() {
        number_1.setCellValueFactory(new PropertyValueFactory<>("number_1"));
        borders.setCellValueFactory(new PropertyValueFactory<>("borders"));
        c_Frequency.setCellValueFactory(new PropertyValueFactory<>("c_Frequency"));
        c_RFrequency.setCellValueFactory(new PropertyValueFactory<>("c_RFrequency"));
        c_Distrib.setCellValueFactory(new PropertyValueFactory<>("c_Distrib"));
    }

    private void initChracteristicTable() {
        value_1.setCellValueFactory(new PropertyValueFactory<>("value_1"));
        sq_conc.setCellValueFactory(new PropertyValueFactory<>("sq_conc"));
        truth_interval.setCellValueFactory(new PropertyValueFactory<>("truthList"));
    }

    private void initNamesTable() {

        ObservableList<String> data = FXCollections.observableArrayList(
                Arrays.asList(
                        "Середнє арифметичне",
                        "Медіана",
                        "Середньоквадратичне",
                        "Коефіцієнт асиметрії",
                        "Коефіцієнт ексцесу",
                        "Коефіцієнт контрексцесу",
                        "Коефіцієнт варіації",
                        "Оцінка пар-ра а",
                        "Оцінка пар-ра k"
                )
        );
        names.setCellValueFactory((param -> new SimpleStringProperty(param.getValue())));
        namesTableView.setItems(data);
    }

    private void tabs_addAll() {
        varTabList.addAll(analyser.getVarRowList());
        classTabList.addAll(analyser.getClassRowList());
        unchangedTabList.addAll(analyser.getCharesteristicsList());
        unchangedTabList.addAll(analyser.getMarks(varTabList));
        varNetList.addAll(analyser.getVarNetList(varTabList));
        densityList.addAll(analyser.getDensityList(varTabList));
        distributionList.addAll(analyser.getDistributionList(varTabList));
        agreeList.add(analyser.getAgreementCriterionRow(classTabList, varTabList));
    }

    private void setAllTablesItems() {
        ObservableList<VarRow> data1 = FXCollections.observableArrayList(varTabList);
        ObservableList<ClassRow> data2 = FXCollections.observableArrayList(classTabList);
        ObservableList<UnchangedСharacteristicRow> data3 = FXCollections.observableArrayList(unchangedTabList);
        ObservableList<AgreementCriterionRow> data4 = FXCollections.observableArrayList(agreeList);
        long start = System.currentTimeMillis();
        varsTableView.setItems(data1);
        classTableView.setItems(data2);
        chareсteristicsTableView.setItems(data3);
        agreementTableView.setItems(data4);
        long end = System.currentTimeMillis();
        System.out.println("adding time: " + (end - start) + "ms");
    }

    private void clearAllTabs() {
        varTabList.clear();
        classTabList.clear();
        unchangedTabList.clear();
        varNetList.clear();
        densityList.clear();
        distributionList.clear();
        agreeList.clear();

        varlinechart.getData().clear();
        classbarchart.getData().clear();
        classlinechart.getData().clear();
        varNetchart.getData().clear();
    }


////////////////////////////////////////////    ///CHARTS METHODS///    ///////////////////////////////////////////////////////////
    private void setAllCharts() {
        long start = System.currentTimeMillis();
        setClassBarchart(classTabList);
        setVarlinechart(varTabList);
        setClasslinechart(classTabList);
        setVarNetchart(varNetList);
        setDensityChart(densityList);
        setDistributionChart(distributionList);
        long end = System.currentTimeMillis();
        System.out.println("graphics time: " + (end - start) + "ms\n");
    }


    private void setClassBarchart(List<ClassRow> list) {
        addChartData(classbarchart, list, true);
    }

    private void setVarlinechart(List<VarRow> list) {
        Series<Double, Double> series = new Series<>();
        for (int i = 0, j = 1; i < list.size() - 1; i++, j++) {
            Double id = list.get(i).getValue();
            Double data = list.get(i).getDistrib();
            series.getData().add(new Data<>(id, data));
            id = list.get(j).getValue();
            data = list.get(i).getDistrib();
            series.getData().add(new Data<>(id, data));
        }
        series.setName("Емпірична ф-ція");
        varlinechart.getData().add(series);
    }

    private void setVarNetchart(List<Double[]> list) {
        final Comparator<Double> comp = Double::compare;
        List<Double> list1 = new ArrayList<>();
        Series<Double, Double> series = new Series<>();
        for (int i = 0; i < list.size() - 1; i++) {
            list1.add(list.get(i)[1]);
            series.getData().add(new Data<>(list.get(i)[0], list.get(i)[1]));
        }
        netZ.setLowerBound(list1.stream().min(comp).get()-0.1);
        netZ.setUpperBound(list1.stream().max(comp).get()+0.1);
        varNetchart.getData().add(series);
    }

    private void setDistributionChart(List<Double[]> list){
        Series<Double, Double> series = new Series<>();
        for (Double[] coord : list)
            series.getData().add(new Data<>(coord[0], coord[1]));
        series.setName("ф-ція розподілу");
        varlinechart.getData().add(series);
    }

    private void setClasslinechart(List<ClassRow> list) {
        addChartData(classlinechart, list, false);
    }

    private void setDensityChart(List<Double[]> list){
        Series<Double, Double> series = new Series<>();
        for (Double[] coord : list)
            series.getData().add(new Data<>(coord[0], coord[1]));
        classbarchart.getData().add(series);
    }

    private void addChartData(LineChart<Double, Double> chart, List<ClassRow> list, boolean isBar) {
        Double[] id = new Double[2];
        Double data;
        Series<Double, Double> series = new Series<>();
        for (ClassRow aList : list) {
            id[0] = aList.getBorders().get(0);
            id[1] = aList.getBorders().get(1);
            data = (isBar) ? aList.getC_Frequency() : aList.getC_Distrib();
            if (!isBar) {
                Series<Double, Double> series1 = new Series<>();
                series1.getData().add(new Data<>(id[0], data));
                series1.getData().add(new Data<>(id[1], data));
                chart.getData().add(series1);
            } else {
                if (isBar) series.getData().add(new Data<>(id[0], 0.0));
                series.getData().add(new Data<>(id[0], data));
                series.getData().add(new Data<>(id[1], data));
                if (isBar) series.getData().add(new Data<>(id[1], 0.0));
            }
        }
        if (isBar) chart.getData().add(series);
    }

}
