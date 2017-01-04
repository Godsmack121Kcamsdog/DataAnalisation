package numericMethods.lab1.sample.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Coordinate {

    private DoubleProperty x;
    private DoubleProperty fx;
    private DoubleProperty Lx;
    private StringProperty Rx;

    public Coordinate(Double value, Double funcValue, Double LValue) {
        this.fx = new SimpleDoubleProperty(funcValue);
        this.x = new SimpleDoubleProperty(value);
        this.Lx = new SimpleDoubleProperty(LValue);
        Double remTerm = funcValue - LValue;
        String format = Double.toString(remTerm);
        this.Rx = new SimpleStringProperty(format);
    }

    public Double getValue() {
        return x.getValue();
    }

    public Double getLValue() {
        return Lx.getValue();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public DoubleProperty fxProperty() {
        return fx;
    }

    public DoubleProperty LxProperty() {
        return Lx;
    }

    public StringProperty RxProperty() {
        return Rx;
    }


}
