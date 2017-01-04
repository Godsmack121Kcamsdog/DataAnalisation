package dataAnalisation.part1.sample.analysers;

import dataAnalisation.part1.sample.Main;
import dataAnalisation.part1.sample.kvantil.Kvantil;
import dataAnalisation.part1.sample.models.UnchangedСharacteristicRow;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.*;

class UnchangedСharacteristicAnalyser extends Analyser {

    private final int N;
    private final static char
            ARITHMETIC_AVERAGE = '0',
            ASSYMETRIC_INDEX   = '1',
            ECSCESS_INDEX      = '2',
            CONTRECSCESS_INDEX = '3',
            VARIATION_INDEX    = '4',
            SQUARED_CONCESSION = '5',
            SQARED_AVERAGE     = '6',
            MEDIANA            = '7';

    private Map<Character, Double[]> table_dataMap;

    UnchangedСharacteristicAnalyser(List<Double> list, boolean isEven) {
        super(list, isEven);
        N = list.size();
        table_dataMap = new HashMap<>();
        table_dataMap.put(SQUARED_CONCESSION, new Double[2]);
        table_dataMap.put(ECSCESS_INDEX, new Double[2]);
        table_dataMap.put(ASSYMETRIC_INDEX, new Double[2]);
        getSquared_concessionByID(' ');
    }


    private Double getArithmetic_average() {
        return Main.round(getList().stream().collect(Collectors.averagingDouble(v -> v)), 4);
    }//среднее арифметическое

    private Double getSquared_average() {
        return sqrt(Main.round(getList().stream().mapToDouble(v->pow(v-getArithmetic_average(),2)).sum()/(double)(N-1),4));
    }//среднее квадратичное

    private Double getMediana() {
        return (isEven()) ? (getList().get((N-1) / 2) + getList().get((N) / 2)) / 2d : getList().get((N-1) / 2);
    }//медиана

    List<Double> anomalousValues() {

        ArrayList<Double> anomalies = new ArrayList<>();

        double a, b;

        getElementValueByID(ASSYMETRIC_INDEX);
        getElementValueByID(ECSCESS_INDEX);

        double sq_conc = table_dataMap.get(SQUARED_CONCESSION)[1];
        double assymetric_index = hipotesa() ? 0 : table_dataMap.get(ASSYMETRIC_INDEX)[1];
        double ecscess_index = table_dataMap.get(ECSCESS_INDEX)[0];
        double ar_aver = getArithmetic_average();

        double t1 = 2 + 0.2 * log10(0.04 * N);
        double t2 = sqrt(19d * sqrt(abs(ecscess_index - 1)) + 1);

        t1 *= sq_conc;
        t2 *= sq_conc;

        if (assymetric_index != 0){
            a = (assymetric_index > 0) ? ar_aver - t2 : ar_aver - t1;
            b = (assymetric_index > 0) ? ar_aver + t1 : ar_aver + t2;
        }
        else {
            a = ar_aver - t1;
            b = ar_aver + t2;
        }

        //System.out.println("m "+ par2+" "+b);
        anomalies.addAll(getList().stream().filter(d -> d >= a && d >= b).collect(Collectors.toList()));
        return anomalies;

    }

    private Double getAssymetric_index() {
        Double sum = getList().stream().mapToDouble(v -> Main.round(pow(v - getArithmetic_average(), 3),4)).sum();
        Double index = 1.0/(N*pow(table_dataMap.get(SQUARED_CONCESSION)[0],3))*sum;
        table_dataMap.get(ASSYMETRIC_INDEX)[0] = Main.round(index,4);
        table_dataMap.get(ASSYMETRIC_INDEX)[1] = Main.round((sqrt(N*(N-1))/(N-2d))*index, 4);
        return Main.round((sqrt(N*(N-1))/(N-2d))*index, 4);
    }//коэфициент ассиметрии

    private Double getEcscess_index() {
        Double sum = getList().stream().mapToDouble(v->pow(v - getArithmetic_average(), 4)).sum();
        Double index = 1/(N*pow(table_dataMap.get(SQUARED_CONCESSION)[0],4))*sum;
        table_dataMap.get(ECSCESS_INDEX)[0] = index;
        table_dataMap.get(ECSCESS_INDEX)[1] = Main.round((pow(N,2)-1d)/(double)((N-2)*(N-3))*((index-3d)+6d/(N+1)),4);

        return table_dataMap.get(ECSCESS_INDEX)[1];
    }//коэфициент эксцесса

    private Double getContrecscess_index() {
        return Main.round(1d/sqrt(abs(table_dataMap.get(ECSCESS_INDEX)[0])),4);
    }//коэфициент контрэксцесса

    private Double getVariation_index() {
        return Main.round(table_dataMap.get(SQUARED_CONCESSION)[1]/ getArithmetic_average(),4);
    }//коэфициент вариации


    private Double getElementValueByID(char id){

        switch (id) {
            case ARITHMETIC_AVERAGE: {
                return getArithmetic_average();
            }

            case SQARED_AVERAGE: {
                return getSquared_average();
            }

            case MEDIANA:{
                return getMediana();
            }

            case ASSYMETRIC_INDEX: {
                return getAssymetric_index();
            }

            case ECSCESS_INDEX: {
                return getEcscess_index();
            }

            case CONTRECSCESS_INDEX: {
                return getContrecscess_index();
            }

            case VARIATION_INDEX: {
                return getVariation_index();
            }

            default: {
                throw new NoSuchElementException();
            }
        }
    }//значение по имени

    private Double getSquared_concessionByID(char id){
        Double aver = getArithmetic_average();
        switch (id) {

            case ARITHMETIC_AVERAGE: {
                return table_dataMap.get(SQUARED_CONCESSION)[1] / sqrt((double) N);
            }

            case MEDIANA:{
                return 0.0;
            }

            case SQARED_AVERAGE: {
                return table_dataMap.get(SQUARED_CONCESSION)[1] / sqrt(2d * N);
            }
            case ASSYMETRIC_INDEX: {
                return sqrt(6d * (N - 2d) / ((N + 1d) * (N + 3d)));
            }

            case ECSCESS_INDEX: {
                return sqrt((24d * N * (N - 2) * (N - 3)) / ((pow(N + 1, 2)) * (N + 3) * (N + 5)));
            }

            case CONTRECSCESS_INDEX: {
                Double ecsc = table_dataMap.get(ECSCESS_INDEX)[0];
                return sqrt(abs(ecsc) / (29d * N)) * pow(abs(pow(ecsc, 2) - 1), 3 / 4d);
            }

            case VARIATION_INDEX: {
                Double varI = getVariation_index();
                return varI * sqrt((1 + 2 * pow(varI, 2)) / (double) (2 * N));
            }

            default: {
                Double sum = getList().stream().mapToDouble(v -> Main.round(pow(v, 2) - pow(aver, 2),4)).sum();
                Double sum1 = getList().stream().mapToDouble(v -> Main.round(pow(v - aver, 2),4)).sum();
                table_dataMap.get(SQUARED_CONCESSION)[0] = Main.round(sqrt(1 / (double)N * sum),4);
                table_dataMap.get(SQUARED_CONCESSION)[1] = Main.round(sqrt(1 / (double)(N - 1) * sum1),4);
                return table_dataMap.get(SQUARED_CONCESSION)[1]/sqrt((double) 2*N);
            }
        }
    }//среднеквадратическое отклонение

    private Double[] getTruth_intervalByID(char id){

        Double t = Kvantil.kvantil(N);
        if (id!=MEDIANA){
            double dif = t*getSquared_concessionByID(id);
            return new Double[]{getElementValueByID(id)-dif, getElementValueByID(id)+dif};
        }
        else
            return null;
    }

    List<UnchangedСharacteristicRow> getUnchangedCharesteristicsRowList() {
        Character[] valuesID = {
                ARITHMETIC_AVERAGE,
                MEDIANA,
                SQARED_AVERAGE,
                ASSYMETRIC_INDEX,
                ECSCESS_INDEX,
                CONTRECSCESS_INDEX,
                VARIATION_INDEX
        };

        List<UnchangedСharacteristicRow> list = new ArrayList<>();
        for (Character id : valuesID) {
            Double value = getElementValueByID(id);
            Double sq_conces = getSquared_concessionByID(id);
            Double[] truth_interval = getTruth_intervalByID(id);
            list.add(new UnchangedСharacteristicRow(value, sq_conces, truth_interval));
        }
        return list;
    }

    private boolean hipotesa(){

        double kvantilStNorm = 3.715d;

        double At=0;

        double s_a = getSquared_concessionByID(ASSYMETRIC_INDEX);

        double A_n = table_dataMap.get(ASSYMETRIC_INDEX)[1];

        System.out.println("Результат проверки гипотезы:");
        boolean result = ( Math.abs((A_n-At)/s_a)<=kvantilStNorm);
        System.out.println((result) ? "Симметричное" : "Несимметричное");
        return ( Math.abs((A_n-At)/s_a)<=kvantilStNorm);
    }


}
