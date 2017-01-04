package numericMethods.lab1.sample.logic.interpol;

import numericMethods.lab1.sample.logic.Function;
import numericMethods.lab1.sample.logic.gauss.Gaus;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class LeastSquareManager extends Form {

    private ArrayList<Double> c;
    private ArrayList<Double> d;
    private Double[] a;
    private int m;
    private Double[][] gram;
    private Double[] d_gram;
    private int size;

    public LeastSquareManager(Function f, int m) {
        super(f);
        c = new ArrayList<>();
        d = new ArrayList<>();
        this.size = m + 1;
        gram = new Double[size][size];
        d_gram = new Double[size];
        this.m = m;
        gramMatrix();
        a = Gaus.gaus(gram, d_gram);
        fillResults();
    }

    @Override
    public Double L(Double arg) {
        double res = a[0];
        for (int i = 1; i < size; i++)
            res += (a[i] * pow(arg, i));
        return res;
    }

    private void gramMatrix() {

        //Формирование матрицы Грама A по векторам данных X,F
        for (double k = 0; k <= m; k++) {
            double sum_d = 0d;
            for (Double[] f_node : f_nodes)
                sum_d += (f_node[1] * pow(f_node[0], k));
            d.add(sum_d);
            d_gram[(int) k] = sum_d;
            System.out.println("d" + Double.toString(k) + " " + Double.toString(d.get((int) k)));
        }

        for (double j = 0d; j <= 2 * m; j++) {
            double c_j = 0d;
            for (Double[] node : f_nodes) c_j += pow(node[0], j);
            c.add(c_j);
            System.out.println("c" + Integer.toString((int) j) + " " + Double.toString(c.get((int) j)));
        }

        gram = new Double[size][size];
        int k = 0;
        for (int i = 0; i < size; i++) {//по строкам
            int l = k;
            for (int j = 0; j < size; j++) {//по столбцам
                gram[i][j] = c.get(k);
                k++;
            }
            k = l + 1;
        }
    }

}
