package numericMethods.lab1.sample.logic;

import java.util.ArrayList;

import static java.lang.Math.*;


public class Function {

    private double ALPHA;
    private double BETA;

    private ArrayList<Double[]> nodes;
    private ArrayList<Double[]> nodes2;

    private ArrayList<Double[]> fullnodes;

    public Function(double ALPHA, double BETA, int n) {
        nodes = new ArrayList<>();
        nodes2 = new ArrayList<>();
        fullnodes = new ArrayList<>();
        this.ALPHA = ALPHA;
        this.BETA = BETA;
        double a = abs(ALPHA) / 10d + 1;
        double b = 2 * (abs(BETA) - 1);
        double h = (b - a) / n;
        this.nodes = nodes(a, h, n, false);
        this.fullnodes = fullnodes(a, h, n);
        this.nodes2 = nodes(a, h, n, true);
    }

    private double function(Double x) {
        return ALPHA * cos(x) + BETA * tan(1/x);
    }

    private ArrayList<Double[]> nodes(Double a, Double h, int n, boolean isTmp) {
        ArrayList<Double[]> nods = new ArrayList<>();
        for (int k = 0; k <= n; k++) {
            double x = (isTmp) ? (a + ((2d * k - 1d) / 2d) * h) : (a + k * h);
            nods.add(new Double[]{x, function(x)});
        }
        return nods;
    }

    private ArrayList<Double[]> fullnodes(Double a, Double h, int n) {
        ArrayList<Double[]> full = new ArrayList<>();
        double x;
        for (int k = 0; k <= n + 1; k++) {
            x = a + ((2d * k - 1d) / 2d) * h ;
            full.add(new Double[]{x, function(x)});
            if (!(k == n + 1)) {
                x = a + k * h;
                full.add(new Double[]{x, function(x)});
            }
        }
        return full;
    }

    public String toString(ArrayList<Double[]> nodes) {
        String rez = "";
        for (int i = 0; i < nodes.size(); i++) {
            rez += "x_" + i + "=" + Double.toString(nodes.get(i)[0]) + ";  f(" + Double.toString(nodes.get(i)[0]) + ")=" + Double.toString(nodes.get(i)[1]) + "\n";
        }
        return rez;
    }

    public ArrayList<Double[]> getNodes() {
        return nodes;
    }

    public ArrayList<Double[]> getNodes2() {
        return nodes2;
    }

    public ArrayList<Double[]> getFullnodes() {
        return fullnodes;
    }

//    public void setFullnodes(LinkedList<Double[]> fullnodes) {
//        ArrayList<Double[]> list = new ArrayList<>();
//        list.addAll(fullnodes);
//        this.fullnodes = list;
//    }
}
