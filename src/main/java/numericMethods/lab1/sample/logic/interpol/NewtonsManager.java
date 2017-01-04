package numericMethods.lab1.sample.logic.interpol;


import numericMethods.lab1.sample.logic.Function;

public class NewtonsManager extends Form {

    public NewtonsManager(Function f) {
        super(f);
        fillResults();
    }


    /**Разделённые разности*/
    private double divided_differences(int i, int k) {
        double f = 0d;
        for (int j = i; j <= i + k; j++) {
            double mult = 1d;
            for (int m = i; m <= i + k; m++) {
                if (m != j) mult *= (f_nodes.get(j)[0] - f_nodes.get(m)[0]);
            }
            f += f_nodes.get(j)[1] / mult;
        }
        return f;
    }

    /**Многочлен Ньютона*/
    @Override
    Double L(Double arg) {
        double P = 0d;
        double mult = 1d;
        for (int i = 0; i < f_nodes.size(); i++) {
            if (i == 0) {
                P += f_nodes.get(0)[1];
                continue;
            }
            mult *= (arg - f_nodes.get(i - 1)[0]);
            P += divided_differences(0, i) * mult;
        }
        return P;
    }

}

