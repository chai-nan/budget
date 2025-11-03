package net.cmkj.budget.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 填报科目部门关联对象 t_budget_item_dept
 *
 * @author cmkj
 * @date 2024-07-31
 */
@Data
@TableName("t_budget_item_dept")
public class BudgetItemDept implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 填报项目 */
    private Long itemId;

    /** 填报部门 */
    private Long deptId;
}
