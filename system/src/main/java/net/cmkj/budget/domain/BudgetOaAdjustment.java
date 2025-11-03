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

/**
 * OA预算填报调整对象 t_budget_oa_adjustment
 * 
 * @author cmkj
 * @date 2025-03-28
 */
@Data
@TableName("t_budget_oa_adjustment")
public class BudgetOaAdjustment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 任务ID */
    private Long taskId;

    @Excel(name = "任务名称")
    @TableField(exist = false)
    private String taskName;

    @Excel(name = "调出公司")
    @TableField(exist = false)
    private String outParentDeptName;

    /** 调出部门 */
    private Long outDept;

    @Excel(name = "调出部门")
    @TableField(exist = false)
    private String outDeptName;

    /** 调出科目 */
    private Long outSubject;

    @Excel(name = "调出科目")
    @TableField(exist = false)
    private String outSubjectName;


    @Excel(name = "调入公司")
    @TableField(exist = false)
    private String inParentDeptName;

    /** 调入部门 */
    private Long inDept;

    @Excel(name = "调入部门")
    @TableField(exist = false)
    private String inDeptName;

    /** 调入科目 */
    private Long inSubject;
    @Excel(name = "调入科目")
    @TableField(exist = false)
    private String inSubjectName;

    /** 调整金额 */
    @Excel(name = "调整金额")
    private BigDecimal amount;

    /** 调整说明 */
    @Excel(name = "调整说明")
    private String remark;

    /** 创建者 */
    @Excel(name = "创建人")
    private String createBy;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    @TableField(exist = false)
    private Long inParentDept;

    @TableField(exist = false)
    private Long outParentDept;
}
