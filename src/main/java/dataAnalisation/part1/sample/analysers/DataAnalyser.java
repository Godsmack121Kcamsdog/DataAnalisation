package dataAnalisation.part1.sample.analysers;

import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;
import dataAnalisation.part1.sample.models.AgreementCriterionRow;
import dataAnalisation.part1.sample.models.ClassRow;
import dataAnalisation.part1.sample.models.UnchangedСharacteristicRow;
import dataAnalisation.part1.sample.models.VarRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class DataAnalyser {

    private List<Double> list;
    private SortedMultiset<Double> multiset;

    private boolean isEven;//четный
    private int m;
    private String distributionName;

    public DataAnalyser(List list, String distributionName) {
        this.list = new ArrayList<>();
        this.list.addAll(list);
        this.distributionName = distributionName;
        Collections.sort(list);
        multiset = TreeMultiset.create();
        multiset.addAll(list);
        isEven = (list.size() % 2 == 0);
        defaultSplitting();
    }

    public void setSplitting(int s) {
        if (s > multiset.elementSet().size() - 1 || s < 1)
            throw new IllegalArgumentException("Illegal Splitting`s setting");
        m = s;
    }

    public void defaultSplitting() {
        int m1 = list.size();
        m1 = (m1 >= 100) ? (int) pow(m1, (double) 1 / 3) : (int) sqrt(m1);
        m = (m1 % 2 == 0) ? (m1 - 1) : m1;
        System.out.println("M" + m);
    }

    public List<Double> getList() {
        return list;
    }

    public ArrayList<ClassRow> getClassRowList() {
        return new ClassAnalyser(list, multiset, m).getClassRowList();
    }

    public ArrayList<VarRow> getVarRowList() {
        //System.out.println(list);
        return new VarAnalyser(list, multiset).getVarClassList();
    }

    public AgreementCriterionRow getAgreementCriterionRow(List<ClassRow> list, ArrayList<VarRow> varList) {
        return new PirsonCriterion(m - 1, list, varList, distributionName, this.list).getAgrementCriterionRow();
    }

    public ArrayList<Double[]> getVarNetList(ArrayList<VarRow> list) {
        return VariableNetFactory.getAnalyser(distributionName, list).getLinedCoordsList();
    }

    public List<UnchangedСharacteristicRow> getCharesteristicsList() {
        return new UnchangedСharacteristicAnalyser(list, isEven).getUnchangedCharesteristicsRowList();
    }

    public List<UnchangedСharacteristicRow> getMarks(ArrayList<VarRow> list) {
        return VariableNetFactory.getAnalyser(distributionName, list).getMarksRows();
    }

    public List<Double> reload() {
        List<Double> list = getAnom();
        int n = this.list.size();
        for (int i = 0; i < n; i++) {//по всем вариантам
            for (Double aList : list) {
                if (Objects.equals(this.list.get(i), aList)) {
                    this.list.remove(i);
                    i--;
                    n--;
                }
            }
        }
        return this.list;
    }

    List<Double> getSelection() {
        return this.list;
    }

    private List<Double> getAnom() {
        return new UnchangedСharacteristicAnalyser(list, isEven).anomalousValues();
    }

    public List<Double[]> getDensityList(ArrayList<VarRow> list) {
        VariableNetAnalyser analyser = VariableNetFactory.getAnalyser(distributionName, list);
        ArrayList<Double[]> list1 = new ArrayList<>();
        multiset.elementSet().stream().forEach(x -> list1.add(new Double[]{x, analyser.getDensity(x, m)}));
        return list1;
    }

    public List<Double[]> getDistributionList(ArrayList<VarRow> list) {
        VariableNetAnalyser analyser = VariableNetFactory.getAnalyser(distributionName, list);
        ArrayList<Double[]> list1 = new ArrayList<>();
        multiset.elementSet().stream().forEach(x -> list1.add(new Double[]{x, analyser.getDistribution(x)}));
        return list1;
    }

    public String getAnomString() {
        return new UnchangedСharacteristicAnalyser(list, isEven).anomalousValues().toString();
    }
}
