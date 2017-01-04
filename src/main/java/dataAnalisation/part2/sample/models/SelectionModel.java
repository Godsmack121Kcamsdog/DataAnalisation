package dataAnalisation.part2.sample.models;

import java.util.*;
import java.util.stream.Collectors;

public class SelectionModel {

    private final static Comparator<MyNode> COMP =
            (d1, d2) -> Double.compare(d1.getValue(), d2.getValue());
    private final Set<Character> keys;
    private final List<MyNode> nodesList;

    public SelectionModel() {
        nodesList = new ArrayList<>();
        keys = new HashSet<>();
    }

    public void add(Character key, Double item) {
        nodesList.add(new MyNode(key, item));
        keys.add(key);
        nodesList.sort(COMP);
    }

    public void addAll(Map<Character, Double> map) {
        nodesList.addAll(map.entrySet().stream()
                .map(entry -> new MyNode(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList()));
    }

    public void clear() {
        nodesList.clear();
    }

    public List<Double> getDataList() {
        return nodesList.stream().map(MyNode::getValue).collect(Collectors.toList());
    }

    public List<Double> getRangesList() {
        setRanges(nodesList);
        return nodesList.stream().map(MyNode::getRange).collect(Collectors.toList());
    }

    private void setRanges(List<MyNode> row) {
        System.out.println(nodesList.size());
        int i = 0;
        int indexLeft, indexRight = 0, count, sum;
        while (i < row.size()) {
            indexLeft = i;
            count = 1;
            sum = 0;
            for (int j = i + 1; j < row.size(); j++) {
                if (row.get(i).getValue().equals(row.get(j).getValue())) count++;
                else {
                    indexRight = j - 1;
                    break;
                }
            }
            if (count != 1) {
                for (int k = indexLeft; k <= indexRight; k++) {
                    sum += k;
                }
                sum /= count;
                for (int k = indexLeft; k <= indexRight; k++) {
                    row.get(k).setRange((double) sum);
                }
                i = indexRight + 1;
            } else {
                row.get(i).setRange((double) i);
                i++;
            }

        }
    }

    public Double[][] getRangesArrayForInnerSelection(Double[][] arrDat, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (arrDat[j][0] > arrDat[j + 1][0]) {
                    double tmp = arrDat[j][0];
                    arrDat[j][0] = arrDat[j + 1][0];
                    arrDat[j + 1][0] = tmp;
                }
            }
        }
        int h = 0;
        while (h < n - 1) {
            if (arrDat[h][0].equals(arrDat[h + 1][0])) {
                int rang = 1;
                int sum = h + 1;
                int j = h;
                while (arrDat[h][0].equals(arrDat[h + 1][0]) && (h < n * 2 - 2)) {
                    sum += ++h;
                    rang++;
                }
                h = j;
                int k = 0;
                while (k < rang) {
                    arrDat[h][2] = sum / (double) rang;
                    h++;
                    k++;
                }
            } else arrDat[h][2] = (++h) * 1d;
        }
        arrDat[n - 1][2] = h + 1d;
        return arrDat;
    }

    public List<Double> getRangesListByKey(Character key) {
        setRanges(nodesList);
        if (!keys.contains(key)) throw new NullPointerException("Model doesn`t contain Object by key:" + key);
        return nodesList.stream().filter(myNode -> myNode.getKey().equals(key)).map(MyNode::getRange).collect(Collectors.toList());
    }

    public List<Double> getDataListByKey(Character key) {
        if (!keys.contains(key)) throw new NullPointerException("Model doesn`t contain Object by key:" + key);
        return nodesList.stream().filter(myNode -> myNode.getKey().equals(key)).map(MyNode::getValue).collect(Collectors.toList());
    }

    private class MyNode {
        private Character key;
        private Double item;
        private Double range;

        MyNode(Character key, Double item) {
            this.key = key;
            this.item = item;
            range = 1d;
        }

        Character getKey() {
            return key;
        }

        Double getValue() {
            return item;
        }

        void setRange(Double range) {
            this.range = range;
        }

        Double getRange() {
            return range;
        }
    }

}
