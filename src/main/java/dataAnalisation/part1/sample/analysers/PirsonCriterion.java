package dataAnalisation.part1.sample.analysers;

import dataAnalisation.part1.sample.kvantil.Kvantil;
import dataAnalisation.part1.sample.models.AgreementCriterionRow;
import dataAnalisation.part1.sample.models.ClassRow;
import dataAnalisation.part1.sample.models.VarRow;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

class PirsonCriterion {

    private Double criticalValue, criterion;
    private int n;
    private List<Double> list;

    PirsonCriterion(int v, List<ClassRow> classList,ArrayList<VarRow> varList, String distrName, List<Double> list) {
        this.n = list.size();
        this.list = list;
        criticalValue = getCriticalValue(v);
        criterion = getCriterion(classList, varList, v+1, distrName);
    }

    /**
     * @param v is par2 number of splits minus one (m-1)
     * @return kvantil
     * */
    private Double getCriticalValue(int v) {
        criticalValue = Kvantil.pirsonKvantil(0.95,v);
        System.out.println("\ncritic " + criticalValue);
        return criticalValue;
    }

    private Double getCriterion(List<ClassRow> classList, ArrayList<VarRow> varList, int m, String distrName) {
        Double result = 0d;
        VariableNetAnalyser analyser = VariableNetFactory.getAnalyser(distrName, varList);
        for (int i = 0; i < m; i++) {
            Double tmp = (analyser.getDistribution(classList.get(i).getBorders().get(1)) -
                    analyser.getDistribution(classList.get(i).getBorders().get(0)))*n;
            result += pow(classList.get(i).getC_Frequency() - tmp, 2) / tmp;
        }
        System.out.println("criterion " + result+"\n");
        criterion = result;
        return criterion;
    }

    private String isPossible() {
        return (criterion <= criticalValue) ? "вирогідно" : "невирогідно";
    }

    AgreementCriterionRow getAgrementCriterionRow(){
         return new AgreementCriterionRow(criterion, criticalValue, isPossible());
    }

}
