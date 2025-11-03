package net.cmkj.budget.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 预算科目财务科目对象 t_budget_subject_finance
 * 
 * @author cmkj
 * @date 2024-10-14
 */
@Data
@TableName("t_budget_subject_finance")
public class BudgetSubjectFinance implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 科目ID */
    private Long subjectId;

    /** 财务科目 */
    private String financelSubject;

    @TableField(exist = false)
    private String financelSubjectName;
}
