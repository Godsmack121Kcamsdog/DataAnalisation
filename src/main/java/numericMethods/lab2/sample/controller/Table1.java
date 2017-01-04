package numericMethods.lab2.sample.controller;

import javafx.beans.property.SimpleDoubleProperty;

public class Table1 {
	private final SimpleDoubleProperty x;
	private final SimpleDoubleProperty f_x;
	
	public Table1(Double x, Double fx) {
		this.x = new SimpleDoubleProperty(x);
		this.f_x = new SimpleDoubleProperty(fx);
	}
	
	public Double getX() { return x.get(); }
	public void setX(Double x1) { x.set(x1); }
	public Double getF_x() { return f_x.get(); }
	public void setF_x(Double fx) { f_x.set(fx); }

}
