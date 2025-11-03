package net.cmkj.budget.domain;

import lombok.Data;

@Data
public class BudgetSummary {

    private String deptName;
    private Long deptId;
    private Double budget;
    private Double budgetYear;
    private Double actualIncurred;
    private Double estimatedIncurred;
    private Integer reportNumber;
}
