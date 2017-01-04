package numericMethods.lab1.sample.logic.interpol;


import numericMethods.lab1.sample.logic.Function;
import numericMethods.lab1.sample.model.Coordinate;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Form {

    private Function f;
    private ArrayList<Coordinate> result;
    private ArrayList<Coordinate> result1;
    private ArrayList<Coordinate> result2;
    ArrayList<Double[]> f_nodes;


    public ArrayList<Coordinate> getResult() {
        return result;
    }

    public ArrayList<Coordinate> getResult1() {
        return result1;
    }

    public ArrayList<Coordinate> getResult2() {
        return result2;
    }

    Form(Function f) {
        this.f = f;
        f_nodes = f.getNodes();
        result = new ArrayList<>();
        result1 = new ArrayList<>();
        result2 = new ArrayList<>();
    }

    abstract Double L(Double arg);

    void fillResults(){
        result.addAll(f.getFullnodes().stream().
                map(el -> new Coordinate(el[0], el[1], L(el[0]))).collect(Collectors.toList()));
        result1.addAll(f_nodes.stream().
                map(el -> new Coordinate(el[0], el[1], L(el[0]))).collect(Collectors.toList()));
        result2.addAll(f.getNodes2().stream().
                map(el -> new Coordinate(el[0], el[1], L(el[0]))).collect(Collectors.toList()));
    }

    public String toString(ArrayList<double[]> nodes) {
        String rez = "";
        for (int i = 0; i < nodes.size(); i++)
            rez += "x_" + i + "=" + Double.toString(nodes.get(i)[0]) + "  ;  f(" + Double.toString(nodes.get(i)[0]) + ")=" + Double.toString(nodes.get(i)[1]) + "\n";
        return rez;
    }

}
