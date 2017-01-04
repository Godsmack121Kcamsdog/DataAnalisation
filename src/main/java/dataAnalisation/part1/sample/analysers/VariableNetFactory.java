package dataAnalisation.part1.sample.analysers;

import dataAnalisation.part1.sample.models.VarRow;

import java.util.ArrayList;
import java.util.NoSuchElementException;

class VariableNetFactory {

    private final static String PARETO = "Pareto";
    private final static String NORMAL = "Normal";

    private VariableNetFactory() {
    }

    static VariableNetAnalyser getAnalyser(String type, ArrayList<VarRow> list) {
        switch (type) {
            case (PARETO):
                return new ParetoNetAnalyser(list);
            case (NORMAL):
                return new NormalNetAnalyser(list);
            default:
                throw new NoSuchElementException("Distribution name is not recognized");
        }
    }
}
