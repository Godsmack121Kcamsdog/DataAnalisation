package dataAnalisation.part1.sample.controllers;

import dataAnalisation.part1.sample.models.VarRow;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class VarTableController extends Control {

    VarTableController(String name) {
        super(name);
    }

    @FXML
    static TableView<VarRow> varsTable;

        @FXML
        private static TableColumn<VarRow,Integer> number;
        @FXML
        private static TableColumn<VarRow,Double> value;
        @FXML
        private static TableColumn<VarRow,Integer> frequency;
        @FXML
        private  static TableColumn<VarRow, Double> r_Frequency;
        @FXML
        private static TableColumn<VarRow,Double> distrib;

    static void init(){
//        number.setCellValueFactory(new PropertyValueFactory<>("number"));
//        value.setCellValueFactory(new PropertyValueFactory<>("value"));
//        frequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
//        r_Frequency.setCellValueFactory(new PropertyValueFactory<>("r_Frequency"));
//        distrib.setCellValueFactory(new PropertyValueFactory<>("distrib"));
    }

}
