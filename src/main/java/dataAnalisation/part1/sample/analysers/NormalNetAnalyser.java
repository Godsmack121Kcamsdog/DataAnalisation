package dataAnalisation.part1.sample.analysers;

import dataAnalisation.part1.sample.Main;
import dataAnalisation.part1.sample.models.UnchangedСharacteristicRow;
import dataAnalisation.part1.sample.models.VarRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.averagingDouble;

class NormalNetAnalyser extends VariableNetAnalyser {

    private List<Double> doubleList;

    NormalNetAnalyser(ArrayList<VarRow> varList) {
        super(varList);
    }

    @Override
    Double getT(Double x) {
        return null;
    }

    @Override
    Double getZ(Double fx) {
        return null;
    }

    @Override
    Double getD_par1() {
        return pow(par2[0],2)/ (double) doubleList.size();
    }

    @Override
    Double getD_par2() {
        return pow(par2[0],2)/(2d* doubleList.size());
    }

    @Override
    void count_Marks() {
        doubleList = this.list.stream().map(VarRow::getValue).collect(Collectors.toList());
        Double x2 = Main.round(doubleList.stream().collect(averagingDouble(d->pow(d,2))),4);
        par1[0] = Main.round(doubleList.stream().collect(averagingDouble(v -> v)), 4);
        par2[0] = doubleList.size()*1d / (doubleList.size() - 1d) * sqrt(x2 - pow(par1[0],2));
    }

    @Override
    List<UnchangedСharacteristicRow> getMarksRows() {
        Double a_sq = sqrt(getD_par1());
        Double k_sq = sqrt(getD_par2());
        return Arrays.asList(
                new UnchangedСharacteristicRow(par2[0], a_sq, getTruthInterval(par2[0], a_sq)),
                new UnchangedСharacteristicRow(par1[0], k_sq, getTruthInterval(par1[0], k_sq)));
    }

    @Override
    ArrayList<Double[]> getLinedCoordsList() {
        return null;
    }


    @Override
    Double getDensity(Double x, int m) {
        return null;
    }

    @Override
    public Double getDistribution(Double x) {
        return null;
    }
}
