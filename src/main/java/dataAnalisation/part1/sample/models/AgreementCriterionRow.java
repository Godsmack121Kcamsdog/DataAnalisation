package dataAnalisation.part1.sample.models;

import dataAnalisation.part1.sample.Main;

public class AgreementCriterionRow {

    private String conclusion;
    private Double criterion;
    private Double criticalValue;

    public AgreementCriterionRow(Double criterion, Double criticalValue, String conclusion) {
        this.criterion = Main.round(criterion, 4);
        this.criticalValue = Main.round(criticalValue,4);
        this.conclusion = conclusion;
    }

    public String getConclusion() {
        return conclusion;
    }

    public Double getCriterion() {
        return criterion;
    }

    public Double getCriticalValue() {
        return criticalValue;
    }


}
