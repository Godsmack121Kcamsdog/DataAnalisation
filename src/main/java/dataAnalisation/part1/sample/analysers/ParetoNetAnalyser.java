package dataAnalisation.part1.sample.analysers;

import dataAnalisation.part1.sample.models.VarRow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.log;
import static java.lang.Math.pow;


class ParetoNetAnalyser extends VariableNetAnalyser {

    ParetoNetAnalyser(ArrayList<VarRow> list) {
        super(list);
    }

    @Override
    Double getT(Double x) {
        return -log(x);
    }

    @Override
    Double getZ(Double fx){
        System.out.println(log(1-fx));
        return log(1-fx);
    }

    @Override
    Double getD_par1(){
        return pow(n-2d/(n*(n-1d)),2)*(pow(par1[0]* par2[0],2)/(n*pow(par2[1],4)));
    }

    @Override
    Double getD_par2(){
        return pow((n-2d)/n,2)*(pow(par2[0],2)/n);
    }


    @Override
    void count_Marks(){
        final Comparator<VarRow> comp = (d1, d2) -> Double.compare(d1.getValue(),d2.getValue());
        par1[0] = list.stream().min(comp).get().getValue();
        count_a();
        par1[1] = (1d - 1d/((n-1d)* par2[1]))* par1[0];
    }

    @Override
    ArrayList<Double[]> getLinedCoordsList() {
        for (VarRow r : list) linedCoordsList.add(new Double[2]);
        for (int i = 0; i < n-1; i++) {
            linedCoordsList.get(i)[0] = getT(list.get(i).getValue());
            linedCoordsList.get(i)[1] = getZ(list.get(i).getDistrib());
        }
        ArrayList<Double[]> list = new ArrayList<>();
        list.addAll(linedCoordsList);
        return list;
    }

    @Override
    Double getDensity(Double x, int m){
        return ((par2[0]/ par1[0])*pow(par1[0]/x, par2[0]+1))*n*(m+0.8);
    }

    @Override
    public Double getDistribution(Double x) {
        return 1 - pow(par1[0]/x, par2[0]);
    }

    private void count_a(){
        List<Double> list = this.list.stream().map(VarRow::getValue).collect(Collectors.toList());
        Double denom = list.stream().mapToDouble(x -> log(x/ par1[0])).sum();
        par2[0] = n/denom;
        par2[1] = (n-2d)/(double)n*par2[0];
    }
}
