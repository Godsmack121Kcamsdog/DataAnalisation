package dataAnalisation.part1.sample.analysers;

import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import dataAnalisation.part1.sample.models.VarRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class VarAnalyser extends Analyser {

    VarAnalyser(List<Double> list, SortedMultiset<Double> multiset) {
        super(list, multiset);
    }

    private int[] getVarFrequency() {
        return getMultiset().entrySet().stream().mapToInt(Multiset.Entry::getCount).toArray();
    }

    private double[] getrFrequency() {
        return Arrays.stream(getVarFrequency()).
                mapToDouble(value -> (double) value / getList().size()).toArray();
    }

    private double[] getEmpFunction() {
        double[] rez = new double[getMultiset().elementSet().size()];
        for (int j = 0; j < rez.length; j++)
            rez[j] = (j + 1) / (double) getMultiset().elementSet().size();
        return rez;
    }

    ArrayList<VarRow> getVarClassList() {

        ArrayList<VarRow> list = new ArrayList<>();

        long start = System.currentTimeMillis();

        int[] fr = getVarFrequency();
        double[] rFr = getrFrequency();
        double[] empF = getEmpFunction();
        double[] var = getMultiset().elementSet().stream().mapToDouble(value -> value).toArray();

        long end = System.currentTimeMillis();
        System.out.println("varList time: " + (end - start) + "ms");
        for (int i = 0; i < var.length; i++)
            list.add(new VarRow(i + 1, var[i], fr[i], rFr[i], empF[i]));

        return list;
    }

}
