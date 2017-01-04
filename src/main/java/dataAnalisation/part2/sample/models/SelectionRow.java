package dataAnalisation.part2.sample.models;

import dataAnalisation.part1.sample.Main;

public class SelectionRow {

    private Double dispersion;
    private Double medium;
    private String canceled;

    public SelectionRow(Double dispersion, Double medium, boolean isCanceled, boolean isSelection) {
        this.dispersion = Main.round(dispersion, 4);
        this.medium = Main.round(medium, 4);
        canceled = (!isSelection) ? ((isCanceled) ? "Збігаються" : "Не збігаються") : ((isCanceled) ? "Однорідні" : "Не однорідні");
    }

    public Double getDispersion() {
        return dispersion;
    }

    public Double getMedium() {
        return medium;
    }

    public String getCanceled() {
        return canceled;
    }
}
