package dataAnalisation.part1.sample.controllers;

import dataAnalisation.part1.sample.models.ClassRow;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

/**
 * Created by Stas on 9/28/2016.
 *
 */

public class ClassTableController extends TableView{

    @FXML
    static TableView<ClassRow> classTable;

        @FXML
        private static TableColumn<ClassRow, Integer> number_1;
        @FXML
        private static TableColumn<ClassRow, ArrayList<Double>> borders;
        @FXML
        private static TableColumn<ClassRow, Integer> c_Frequency;
        @FXML
        private static TableColumn<ClassRow, String> c_RFrequency;
        @FXML
        private static TableColumn<ClassRow, Double> c_Distrib;

    static void init() {
//        number_1.setCellValueFactory(new PropertyValueFactory<>("number_1"));
//        borders.setCellValueFactory(new PropertyValueFactory<>("borders"));
//        c_Frequency.setCellValueFactory(new PropertyValueFactory<>("c_Frequency"));
//        c_RFrequency.setCellValueFactory(new PropertyValueFactory<>("c_RFrequency"));
//        c_Distrib.setCellValueFactory(new PropertyValueFactory<>("c_Distrib"));
    }
}
