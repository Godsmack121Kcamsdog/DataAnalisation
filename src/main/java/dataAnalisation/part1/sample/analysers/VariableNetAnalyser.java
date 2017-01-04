package dataAnalisation.part1.sample.analysers;

import dataAnalisation.part1.sample.models.UnchangedСharacteristicRow;
import dataAnalisation.part1.sample.models.VarRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.sqrt;

abstract class VariableNetAnalyser {

    protected ArrayList<VarRow> list;
    ArrayList<Double[]> linedCoordsList;
    Double[] par1 = new Double[2];
    Double[] par2 = new Double[2];
    protected int n;

    VariableNetAnalyser(ArrayList<VarRow> list) {
        this.list = list;
        n = list.size();
        linedCoordsList = new ArrayList<>();
        count_Marks();
    }

   abstract Double getT(Double x);

   abstract Double getZ(Double fx);

   abstract Double getD_par1();

   abstract Double getD_par2();

   Double[] getTruthInterval(Double value, Double sq_conc){return new Double[]{value-sq_conc, value+sq_conc};}

   abstract void count_Marks();

   abstract ArrayList<Double[]> getLinedCoordsList();

   List<UnchangedСharacteristicRow> getMarksRows(){
       Double a_sq = sqrt(getD_par1());
       System.out.println(a_sq);
       System.out.println(getD_par1());
       System.out.println(par2[1] - a_sq);
       System.out.println(par2[1] + a_sq);
       Double k_sq = sqrt(getD_par2());
       return Arrays.asList(
               new UnchangedСharacteristicRow(par2[1], a_sq, getTruthInterval(par2[1], a_sq)),
               new UnchangedСharacteristicRow(par1[1], k_sq, getTruthInterval(par1[1], k_sq)));
   }

   abstract Double getDensity(Double x, int m);

   public abstract Double getDistribution(Double x);

}
