package net.cmkj.budget.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.cmkj.common.annotation.Excel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 预算调整（OA填报）对象 t_budget_adjustment
 * 
 * @author cmkj
 * @date 2024-10-16
 */
@Data
@TableName("t_budget_adjustment")
public class BudgetAdjustment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 预算年份 */
    @Excel(name = "预算年份")
    private Integer budgetYear;

    /** 调整科目 */
    private Long subjectId;

    /** OA编号 */
    @Excel(name = "OA编号")
    private String number;

    @TableField(exist = false)
    @Excel(name = "调整科目")
    private String subjectName;

    private Long itemId;

    /** 调出部门 */
    private Long outDept;

    @TableField(exist = false)
    @Excel(name = "调出部门")
    private String outDeptName;

    /** 调入部门 */
    private Long inDept;

    @TableField(exist = false)
    @Excel(name = "调入部门")
    private String inDeptName;

    /** 调整金额 */
    @Excel(name = "调整金额")
    private BigDecimal amount;

    /** 调整说明 */
    @Excel(name = "调整说明")
    private String remark;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 请求参数 */
    @TableField(exist = false)
    private List<BudgetAdjustmentInfo> items;
}
