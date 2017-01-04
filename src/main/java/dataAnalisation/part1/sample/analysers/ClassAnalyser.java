package dataAnalisation.part1.sample.analysers;

import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.util.concurrent.AtomicDouble;
import dataAnalisation.part1.sample.models.ClassRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.BoundType.CLOSED;
import static com.google.common.collect.BoundType.OPEN;

class ClassAnalyser extends Analyser{

    private ArrayList<Double[]> lims;
    private static int m;
    private double h;

    ClassAnalyser(List<Double> list, SortedMultiset<Double> multiset, int m) {
        super(list, multiset);
        lims = new ArrayList<>();
        ClassAnalyser.m = m;
    }


    private List<Double[]> getBorders() {
        Double[][] arr = new Double[m][2];

        double last = getMultiset().elementSet().last();
        double first = getMultiset().elementSet().first();
        h = (last - first) / m;
        System.out.println("h"+h);

        for (int i = 0; i < m; i++) {
            arr[i][0] = first + i * h;
            arr[i][1] = first + (i + 1) * h;
            lims.add(arr[i]);
        }
        return lims;
    }


    private int[] getClassFrequency() {
        int[] freq = new int[lims.size()];
        for (int j = 0; j < lims.size(); j++) {
            Double[] arr = lims.get(j);

            for (Multiset.Entry<Double> entry : getMultiset().entrySet()) {
                if ((entry.getElement() >= arr[0]) && (entry.getElement() < arr[1]))
                    freq[j] += entry.getCount();
            }
        }
        return freq;
    }

    private double[] getClassRFrequency() {
        return Arrays.stream(getClassFrequency()).
                mapToDouble(value -> (double) value /getList().size()).toArray();
    }


    private double[] getEmpFunction() {
        AtomicDouble prevEmpValue = new AtomicDouble();
        double[] rez = new double[lims.size()];
        double varStart = getMultiset().firstEntry().getElement();
        double varFinish = getMultiset().lastEntry().getElement();
        for (int j = 0; j < rez.length; j++) {
            double start = j * h + varStart;
            double finish = start + h;

            SortedMultiset subMultiset = getMultiset().subMultiset(start, CLOSED,
                    finish, finish != varFinish ? OPEN : CLOSED);
            int frequency = subMultiset.size();
            double rlFreq = frequency / (double) getMultiset().size();
            rez[j] = prevEmpValue.addAndGet(rlFreq);
        }
        return rez;
    }

    ArrayList<ClassRow> getClassRowList() {
        ArrayList<ClassRow> classList = new ArrayList<>();
        long start = System.currentTimeMillis();

        List<Double[]> l = getBorders();
        int[] fr = getClassFrequency();
        double[] rFr = getClassRFrequency();
        double[] empF = getEmpFunction();

        long end = System.currentTimeMillis();
        System.out.println("classList time: " + (end - start) + "ms");

        for (int i = 0; i < l.size(); i++)
            classList.add(new ClassRow(i + 1, l.get(i), fr[i], rFr[i], empF[i]));

        return classList;
    }

}
