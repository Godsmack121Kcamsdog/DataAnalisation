package numericMethods.lab1.sample.logic.interpol;

import numericMethods.lab1.sample.logic.Function;

public class LagrangeManager extends Form {

    public LagrangeManager(Function f) {
        super(f);
        fillResults();
    }

    @Override
    public Double L(Double arg) {
        double result = 0;
        for (int i = 0; i < f_nodes.size(); i++) {
            double k = 1;
            for (int j = 0; j < f_nodes.size(); j++) {
                if (j != i) {
                    k *= (arg - f_nodes.get(j)[0]) / (f_nodes.get(i)[0] - f_nodes.get(j)[0]);
                }
            }
            result += k * f_nodes.get(i)[1];
        }
        return result;
    }

}


