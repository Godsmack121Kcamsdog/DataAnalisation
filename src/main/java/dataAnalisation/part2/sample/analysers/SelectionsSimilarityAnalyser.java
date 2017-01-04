package dataAnalisation.part2.sample.analysers;

import dataAnalisation.part1.sample.kvantil.Kvantil;
import dataAnalisation.part2.sample.models.SelectionModel;
import dataAnalisation.part2.sample.models.SelectionRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class SelectionsSimilarityAnalyser {

    private SelectionModel selection;
    private List<Double> l1, l2;
    private final static char X = 'X';
    private final static char Y = 'Y';
    private List<Double> z;
    private final Double T;
    private final Double U;

    public SelectionsSimilarityAnalyser(List<Double> l1, List<Double> l2) {
        selection = new SelectionModel();
        this.l1 = l1;
        this.l2 = l2;
        z = new ArrayList<>();
        for (int i = 0; i < l1.size(); i++) {
            z.add(l1.get(i) - l2.get(i));
            selection.add(X, l1.get(i));
            selection.add(Y, l2.get(i));
        }
        T = Kvantil.studentKvantil(1 - (0.05 / 2d), z.size() - 1);
        U = Kvantil.normalKvantil(1 - 0.05 / 2);
    }

    private SelectionRow DependentMediumCheck() {
        Double z_z = getAverage(z);
        Double S = sqrt(getS2(z, z_z));
        Double t = (z_z * sqrt(z.size())) / S;
        return new SelectionRow(abs(t), T, abs(t) <= T, false);
    }

    private SelectionRow UndependentMediumCheck() {
        Double x_x = getAverage(l1);
        Double y_y = getAverage(l2);
        Double S2 = getS2(l1, x_x) / l1.size() + getS2(l2, y_y) / l2.size();
        Double t = getAverage(z) / sqrt(S2);
        return new SelectionRow(abs(t), T, abs(t) <= T, false);
    }

    private SelectionRow DispersionCheck() {
        Double S2_x = getS2(l1, getAverage(l1));
        Double S2_y = getS2(l2, getAverage(l2));
        Double f = S2_x / S2_y;
        int v1, v2;
        if (S2_x >= S2_y) {
            v1 = l1.size() - 1;
            v2 = l2.size() - 1;
        } else {
            v1 = l2.size() - 1;
            v2 = l1.size() - 1;
            f = pow(f, -1);
        }
        Double F = Kvantil.fisherKvantil(1 - 0.05, v1, v2);
        return new SelectionRow(f, F, f <= F, false);
    }

    private SelectionRow signedVilcolsonRangeCriterion() {
        Double[][] rang = new Double[z.size()][3];
        for (int i = 0; i < z.size(); i++) {
            rang[i][0] = z.get(i);
            rang[i][1] = (z.get(i) > 0) ? 1d : 0d;
        }
        Double[][] range = selection.getRangesArrayForInnerSelection(rang, z.size());
        Double u = (getCriterionStatistic(range, z.size()) - getE_T(z.size())) / sqrt(getD_T(z.size()));
        return new SelectionRow(abs(u), U, abs(u) <= U, true);
    }

    private SelectionRow rangedVilcolsonRangeCriterion() {
        List<Double> list = selection.getRangesListByKey(X);
        Double w_x = list.stream().mapToDouble(d -> d).sum();
        Double Ew = l1.size() * (z.size() + 1) / 2d;
        Double Dw = (l1.size() * l2.size() * (z.size() + 1d)) / 12d;
        Double W = abs((w_x - Ew) / sqrt(Dw));
        return new SelectionRow(W, U, W <= U, true);
    }

    private Double getCriterionStatistic(Double[][] arr, int n) {
        double t = 0d;
        for (int i = 0; i < n; i++) t += arr[i][1] * arr[i][2];
        return t;
    }

    private Double getE_T(int n) {
        return 0.25 * n * (n + 1);
    }

    private Double getD_T(int n) {
        return (1 / 24d) * n * (n + 1) * (2 * n + 1);
    }

    private Double getAverage(List<Double> z) {
        return z.stream().collect(Collectors.averagingDouble(d -> d));
    }

    private Double getS2(List<Double> z, Double z_z) {
        return z.stream().mapToDouble(d -> pow(d - z_z, 2)).sum() / (z.size() - 1);
    }

    public List<SelectionRow> getSelectionRow(boolean isDependent) {
        return (isDependent) ?
                Arrays.asList(DispersionCheck(), DependentMediumCheck(), signedVilcolsonRangeCriterion())
                :
                Arrays.asList(DispersionCheck(), UndependentMediumCheck(), rangedVilcolsonRangeCriterion());
    }

}



















//    private SelectionRow sharp() {
//        Double[][] rang = new Double[z.size() * 2][3];
//        for (int i = 0; i < z.size(); i++) {
//            rang[i][0] = l1.get(i);
//            System.out.println(rang[i][0]);
//            rang[i][1] = 1d;
//        }
//        for (int i = z.size(), j = 0; i < z.size() * 2; i++, j++) {
//            rang[i][0] = l2.get(j);
//            rang[i][1] = 2d;
//        }
//        Double[][] range = selection.getRangesArrayForInnerSelection(rang, z.size() * 2);
//        Double w_x = getW(rang,1);
//        int n1 = l1.size();
//        int n2 = l2.size();
//        Double Ew = n1 * (z.size() + 1) / 2d;
//        Double Dw = (n1 * n2 * (z.size() + 1d)) / 12d;
//        System.out.println("ranged " + (abs((w_x - Ew) / sqrt(Dw))) + " " + Kvantil.normalKvantil(1 - 0.05 / 2));
//        Double W = abs((w_x - Ew) / sqrt(Dw));
//        return new SelectionRow(W, Kvantil.normalKvantil(1 - 0.05 / 2), (W <= Kvantil.normalKvantil(1 - 0.05 / 2)));
//    }

//    private Double getW(Double[][] arr, int kryt) {
//        double sum = 0;
//        for (int i = 0; i < z.size() * 2; i++) {
//            if (arr[i][1] == kryt)
//                sum += arr[i][2];
//        }
//        return sum;
//    }
