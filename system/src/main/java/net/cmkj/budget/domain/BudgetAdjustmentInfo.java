package net.cmkj.budget.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import net.cmkj.common.annotation.Excel;
import lombok.Data;

import java.io.Serial;

/**
 * 预算调整明细（OA填报）对象 t_budget_adjustment_info
 * 
 * @author cmkj
 * @date 2024-10-28
 */
@Data
@TableName("t_budget_adjustment_info")
public class BudgetAdjustmentInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long adjustmentId;

    /** 调整科目 */
    @Excel(name = "调整科目")
    private Long subjectId;

    /** 1：年度 2：季度（不做统计） */
    @Excel(name = "1：年度 2：季度", readConverterExp = "不=做统计")
    private String type;

    /** 调出部门 */
    @Excel(name = "调出部门")
    private Long outDept;

    /** 调入部门 */
    @Excel(name = "调入部门")
    private Long inDept;

    /** 调整金额 */
    @Excel(name = "调整金额")
    private BigDecimal amount;

    /** 请求参数 */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}
