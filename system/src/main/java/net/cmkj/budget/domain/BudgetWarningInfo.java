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
 * 预警详情对象 t_budget_warning_info
 * 
 * @author cmkj
 * @date 2024-11-27
 */
@Data
@TableName("t_budget_warning_info")
public class BudgetWarningInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String deptName;

    /** 父部门id */
    @Excel(name = "父部门id")
    private Long parentId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String parentName;

    /** 任务id */
    @Excel(name = "任务id")
    private Long taskId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 实际发生费用 */
    @Excel(name = "实际发生费用")
    private BigDecimal actualMoney;

    /** 预算费用 */
    @Excel(name = "预算费用")
    private BigDecimal budgetMoney;

    /** 比例 */
    private Double proportion;

    /** 类型 1：填报提醒  2：超出提醒 */
    @Excel(name = "类型 1：填报提醒  2：超出提醒")
    private Integer type;

    /** 提示内容 */
    @Excel(name = "提示内容")
    private String message;

    /** 提示时间 */
    @Excel(name = "提示时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    /** 填报表id */
    private Long itemId;

    private String itemName;

    /** 预算科目id */
    @Excel(name = "预算科目id")
    private Long subjectId;

    /** 预算科目名称 */
    @Excel(name = "预算科目名称")
    private String subjectName;

    /** 状态（备用） */
    private Integer status;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 状态标志（0代表存在  2代表删除） */
    private String delFlag;

    @TableField(exist = false)
    private Integer queryDays;

}
