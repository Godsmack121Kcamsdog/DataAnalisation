package numericMethods.lab2.sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.*;

public class Controller implements Initializable {

    @FXML
    private Canvas cnvs;
    @FXML
    private Button startBtn;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private TableView<Table1> t1;
    @FXML
    private TableColumn<Table1, Integer> t1c1;
    @FXML
    private TableColumn<Table1, Integer> t1c2;
    @FXML
    private Label resultLabel;

    private GraphicsContext gc;
    private Double x_center = 40.0;
    private Double y_center = 350.0;
    private Double x_move;
    private Double y_move;
    private Double scale = 30.0;
    private Integer n = 6;
    private Double a = 1.1;
    private Double b = 4.5;
    private Double h = (4.5 - 1.1) / n;
    private Integer oldN = n;

    private final ObservableList<Table1> data1 = FXCollections.observableArrayList();

    @FXML
    void startWork(ActionEvent e) {

        data1.clear();
        n = 7;
        Double[][] tempXY = new Double[n][2];
        for (int i = 0; i < n; i++) {
            tempXY[i][0] = a + i * h;
            tempXY[i][1] = f(a + i * h);
            data1.add(new Table1(tempXY[i][0], tempXY[i][1]));
        }

        Double integralValue = 0.0;

        Double[] A = {
                -0.94910791,
                0.94910791,
                -0.7415119,
                0.7415119,
                -0.40584515,
                0d
        };

        Double[] t = {
                0.12948496,
                0.12948496,
                0.27970540,
                0.27970540,
                0.38183006,
                0.38183006,
                0.41795918,
                0.41795918
        };

        switch (choiceBox.getValue().toString()) {

            case "Left":
                for (int i = 1; i < n; i++)
                    integralValue += (tempXY[i][0] - tempXY[i - 1][0]) * tempXY[i - 1][1];
                break;

            case "Right":
                for (int i = 1; i < n; i++)
                    integralValue += (tempXY[i][0] - tempXY[i - 1][0]) * tempXY[i][1];
                break;

            case "Center":
                for (int i = 1; i < n; i++)
                    integralValue += (tempXY[i][0] - tempXY[i - 1][0]) * f((tempXY[i][0] + tempXY[i - 1][0]) / 2);
                break;

            case "Trapeze":
                for (int i = 1; i < n; i++)
                    integralValue += (tempXY[i][0] - tempXY[i - 1][0]) * (tempXY[i][1] + tempXY[i - 1][1]);
                integralValue /= 2d;
                break;

            case "Simpson":
                for (int i = 2; i < n; i += 2) {
                    double h = tempXY[i][0] - tempXY[i - 2][0];
                    integralValue += h * (tempXY[i - 2][1] + 4 * tempXY[i - 1][1] + tempXY[i][1]);
                }
                integralValue /= 3d;
                break;

            case "Gausse": {
                for (int i = 1; i < n; i++) {
                    Double tmp = tempXY[i][0] + tempXY[i - 1][0];
                    System.out.println(i+": "+tmp);
                    integralValue += tmp / 2 * A[i] * f(tmp / 2 + tmp / 2 * t[i]);
                    System.out.println(i+"I: "+integralValue);
                }
                break;
            }
        }
        n = oldN;

        resultLabel.setText(" J = " + integralValue + " Погрешность = " + abs(-3.22658 - (integralValue)));
        draw();
    }

    private void draw() {
        gc.clearRect(0, 0, cnvs.getWidth(), cnvs.getHeight());
        draw_axis(gc);
        draw_grid(gc);
        gc.beginPath();

        for (double i = -x_center; i < 500 - x_center; i++) {
            double x = i / scale;
            gc.moveTo(i + x_center, -f(x) * scale + y_center);
            i++;
            x = i / scale;
            gc.lineTo(i + x_center, -f(x) * scale + y_center);
            i--;
        }

        gc.stroke();
        gc.closePath();
        gc.setFill(Color.LIGHTBLUE);

        for (double i = a * scale; i < b * scale; i++) {
            double x = i / scale;
            double p1 = i + x_center;
            double p2 = -f(x) * scale + y_center;
            if (f(x) * scale > 0) gc.fillRect(p1, p2, 1, f(x) * scale);
            else gc.fillRect(p1, y_center, 1, -f(x) * scale);
        }
        gc.stroke();
        gc.closePath();
    }

    private Double f(Double x) {
        //return 3.25 * (cos(1 / x) / sin(1 / x));
        return -((cos(x) + 3.25 * tan(1 / x)));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = cnvs.getGraphicsContext2D();
        choiceBox.getItems().addAll("Left", "Right", "Center", "Trapeze", "Simpson", "Gausse");
        choiceBox.getSelectionModel().selectFirst();
        t1c1.setCellValueFactory(new PropertyValueFactory<>("x"));
        t1c2.setCellValueFactory(new PropertyValueFactory<>("f_x"));
        t1.setItems(data1);
    }

    @FXML
    private void scaleChange(ScrollEvent event) {
        if (event.getDeltaY() > 0 && scale < 100) scale++;
        else if (scale > 2.5) scale--;
        draw();
    }

    @FXML
    private void onPress(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            x_center -= (x_move - e.getX());
            y_center -= (y_move - e.getY());
            draw();
        }
        x_move = e.getX();
        y_move = e.getY();
    }

    @FXML
    private void onDown(MouseEvent e) {
        x_move = e.getX();
        y_move = e.getY();
    }

    private void draw_axis(GraphicsContext gc) {
        gc.beginPath();
        gc.setLineWidth(3);
        gc.strokeLine(x_center, cnvs.getHeight(), x_center, 0);
        gc.strokeLine(0, y_center, cnvs.getWidth(), y_center);
        gc.stroke();
        gc.closePath();
    }

    private void draw_grid(GraphicsContext gc) {
        gc.beginPath();
        gc.setLineWidth(0.5);
        Integer j = 0;
        Double coef = 1.0;

        if (scale < 10) coef = 5.0;
        if (scale < 5) coef = 10.0;

        for (Double i = x_center; i < cnvs.getWidth(); i += scale * coef) {
            Integer tmp = (int) ((j++) * coef);
            if (i > x_center) gc.strokeText((tmp).toString(), i - 5, y_center + 10);
            gc.moveTo(i, 0);
            gc.lineTo(i, cnvs.getHeight());
        }

        for (Double i = x_center; i > 0; i -= scale * coef) {
            gc.moveTo(i, 0);
            gc.lineTo(i, cnvs.getHeight());
        }

        for (Double y = y_center; y < cnvs.getHeight(); y += scale * coef) {
            gc.moveTo(0, y);
            gc.lineTo(cnvs.getWidth(), y);
        }

        Integer i = 0;
        for (Double y = y_center; y > 0.0; y -= scale * coef) {
            Integer tmp = (int) ((i++) * coef);
            gc.strokeText((tmp).toString(), x_center - 15, y + 10);
            gc.moveTo(0, y);
            gc.lineTo(cnvs.getWidth(), y);
        }

        gc.stroke();
        gc.closePath();
    }
}
