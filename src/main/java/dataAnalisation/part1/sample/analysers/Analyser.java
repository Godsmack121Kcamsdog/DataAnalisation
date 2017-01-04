package dataAnalisation.part1.sample.analysers;

import com.google.common.collect.SortedMultiset;

import java.util.List;

class Analyser {

    private List<Double> list;
    private SortedMultiset<Double> multiset;
    private boolean isEven;

    Analyser(List<Double> list, SortedMultiset<Double> multiset) {
        this.list = list;
        this.multiset = multiset;
    }

    Analyser(List<Double> list, boolean isEven){
        this.list = list;
        this.isEven = isEven;
    }

    Analyser(List<Double> list) {
        this.list = list;
    }

    List<Double> getList() {
        return list;
    }

    boolean isEven() {
        return isEven;
    }

    SortedMultiset<Double> getMultiset() {
        return multiset;
    }
}
