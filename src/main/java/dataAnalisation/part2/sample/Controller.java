package dataAnalisation.part2.sample;

import dataAnalisation.part1.sample.analysers.DataAnalyser;
import dataAnalisation.part1.sample.models.ClassRow;
import dataAnalisation.part1.sample.models.UnchangedСharacteristicRow;
import dataAnalisation.part1.sample.models.VarRow;
import dataAnalisation.part2.reader.FilesManager;
import dataAnalisation.part2.sample.analysers.SelectionsSimilarityAnalyser;
import dataAnalisation.part2.sample.models.SelectionRow;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableView<String> namesTableView1;
    @FXML
    private TableView<String> namesTableView2;
    @FXML
    private TableColumn<String, String> names1;
    @FXML
    private TableColumn<String, String> names2;

    @FXML
    private TableView<UnchangedСharacteristicRow> chareсteristicsTableView1;
    @FXML
    private TableColumn<UnchangedСharacteristicRow, Double> value_11;
    @FXML
    private TableColumn<UnchangedСharacteristicRow, String> sq_conc1;
    @FXML
    private TableColumn<UnchangedСharacteristicRow, ArrayList<String>> truth_interval1;

    @FXML
    private TableView<UnchangedСharacteristicRow> chareсteristicsTableView2;
    @FXML
    private TableColumn<UnchangedСharacteristicRow, Double> value_12;
    @FXML
    private TableColumn<UnchangedСharacteristicRow, String> sq_conc2;
    @FXML
    private TableColumn<UnchangedСharacteristicRow, ArrayList<String>> truth_interval2;

    @FXML
    private TableView<SelectionRow> selectionTableView1;
    @FXML
    private TableColumn<SelectionRow, Double> dispersion1;
    @FXML
    private TableColumn<SelectionRow, Double> medium1;
    @FXML
    private TableColumn<SelectionRow, String> canceled1;

    @FXML
    private TableView<SelectionRow> selectionTableView2;
    @FXML
    private TableColumn<SelectionRow, Double> dispersion2;
    @FXML
    private TableColumn<SelectionRow, Double> medium2;
    @FXML
    private TableColumn<SelectionRow, String> canceled2;

    private FilesManager manager = new FilesManager();

    private final ArrayList<VarRow> varTabList1 = new ArrayList<>();
    private final ArrayList<VarRow> varTabList2 = new ArrayList<>();
    private final ArrayList<ClassRow> classTabList1 = new ArrayList<>();
    private final ArrayList<ClassRow> classTabList2 = new ArrayList<>();
    private final ArrayList<UnchangedСharacteristicRow> unchangedTabList1 = new ArrayList<>();
    private final ArrayList<UnchangedСharacteristicRow> unchangedTabList2 = new ArrayList<>();
    private final List<SelectionRow> selectionTabList = new ArrayList<>();
    private List<SelectionRow> selectionTabList2 = new ArrayList<>();
    private DataAnalyser[] analyser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNamesTable();
        initChracteristicTable();
        initSelectionTable();
    }

    @FXML
    private void click() {
        clearAllTabs();
        try {
            List<List<Double>> list = manager.readData();
            analyser = new DataAnalyser[list.size()];
            for (int i = 0; i < list.size(); i++){
                analyser[i] = new DataAnalyser(list.get(i), "Normal");
            }
            tabs_addAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAllTableItems();
    }

    private void initNamesTable() {
        ObservableList<String> data = FXCollections.observableArrayList(
                Arrays.asList(
                        "Середнє арифметичне:",
                        "Медіана:",
                        "Середньоквадратичне:",
                        "Коефіцієнт асиметрії:",
                        "Коефіцієнт ексцесу:",
                        "Коефіцієнт контрексцесу:",
                        "Коефіцієнт варіації:",
                        "Оцінка пар-ра m:",
                        "Оцінка пар-ра q:"
                )
        );
        ObservableList<String> data2 = FXCollections.observableArrayList(
                Arrays.asList(
                        "дисперсія:",
                        "середнє:",
                        "висновок:"
                )
        );
        names1.setCellValueFactory((param -> new SimpleStringProperty(param.getValue())));
        names2.setCellValueFactory((param -> new SimpleStringProperty(param.getValue())));
        namesTableView1.setItems(data);
        namesTableView2.setItems(data2);
    }

    private void initChracteristicTable() {
        value_11.setCellValueFactory(new PropertyValueFactory<>("value_1"));
        sq_conc1.setCellValueFactory(new PropertyValueFactory<>("sq_conc"));
        truth_interval1.setCellValueFactory(new PropertyValueFactory<>("truthList"));

        value_12.setCellValueFactory(new PropertyValueFactory<>("value_1"));
        sq_conc2.setCellValueFactory(new PropertyValueFactory<>("sq_conc"));
        truth_interval2.setCellValueFactory(new PropertyValueFactory<>("truthList"));
    }

    private void initSelectionTable() {
        dispersion1.setCellValueFactory(new PropertyValueFactory<>("dispersion"));
        medium1.setCellValueFactory(new PropertyValueFactory<>("medium"));
        canceled1.setCellValueFactory(new PropertyValueFactory<>("canceled"));

        dispersion2.setCellValueFactory(new PropertyValueFactory<>("dispersion"));
        medium2.setCellValueFactory(new PropertyValueFactory<>("medium"));
        canceled2.setCellValueFactory(new PropertyValueFactory<>("canceled"));
    }


    private void tabs_addAll() {
        varTabList1.addAll(analyser[0].getVarRowList());
        classTabList1.addAll(analyser[0].getClassRowList());
        unchangedTabList1.addAll(analyser[0].getCharesteristicsList());
        unchangedTabList1.addAll(analyser[0].getMarks(varTabList1));

        varTabList2.addAll(analyser[1].getVarRowList());
        classTabList2.addAll(analyser[1].getClassRowList());
        unchangedTabList2.addAll(analyser[1].getCharesteristicsList());
        unchangedTabList2.addAll(analyser[1].getMarks(varTabList2));

        selectionTabList.addAll(new SelectionsSimilarityAnalyser(analyser[0].getList(),analyser[1].getList()).getSelectionRow(true));
        selectionTabList2.addAll(new SelectionsSimilarityAnalyser(analyser[0].getList(),analyser[1].getList()).getSelectionRow(false));
//        selectionTabList.add(new SelectionsSimilarityAnalyser(varTabList1,varTabList2).getSelectionRow(false));
//        selectionTabList2.add(new SelectionsSimilarityAnalyser(varTabList1,varTabList2).getSelectionRow(false));
    }

    private void setAllTableItems(){
        setTableItems(unchangedTabList1, chareсteristicsTableView1);
        setTableItems(unchangedTabList2,chareсteristicsTableView2);
        ObservableList<SelectionRow> data = FXCollections.observableArrayList(selectionTabList);
        ObservableList<SelectionRow> data2 = FXCollections.observableArrayList(selectionTabList2);
        selectionTableView1.setItems(data);
        selectionTableView2.setItems(data2);
    }

    private void setTableItems(ArrayList<UnchangedСharacteristicRow> unchangedTabList, TableView<UnchangedСharacteristicRow> tableView) {
        ObservableList<UnchangedСharacteristicRow> data = FXCollections.observableArrayList(unchangedTabList);
        tableView.setItems(data);
    }

    private void clearAllTabs() {
        clearTabs(varTabList1,classTabList1,unchangedTabList1);
        clearTabs(varTabList2,classTabList2,unchangedTabList2);
        selectionTabList.clear();
        selectionTabList2.clear();
    }

    private void clearTabs(ArrayList<VarRow> varTabList, ArrayList<ClassRow> classTabList, ArrayList<UnchangedСharacteristicRow> unchangedTabList) {
        varTabList.clear();
        classTabList.clear();
        unchangedTabList.clear();
    }

}
