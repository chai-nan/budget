package net.cmkj.budget.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import net.cmkj.common.annotation.Excel;
import lombok.Data;

import java.io.Serial;

/**
 * 预警对象 t_warning
 * 
 * @author cmkj
 * @date 2024-11-07
 */
@Data
@TableName("t_warning")
public class Warning implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String deptName;

    /** 实际发生费用 */
    @Excel(name = "实际发生费用")
    private BigDecimal actualMoney;

    /** 预算费用 */
    @Excel(name = "预算费用")
    private BigDecimal budgetMoney;
    /** 预警状态（1：未超出，2：已超出）实际费用和预算费用对比 */
    @Excel(name = "预警状态")
    private Integer moneyType;

    /** 任务id */
    @Excel(name = "任务id")
    private Long taskId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 提示消息类型（1：费用预警） */
    @Excel(name = "提示消息类型", readConverterExp = "1=：费用预警")
    private Integer messageType;

    /** 提示内容 */
    @Excel(name = "提示内容")
    private String message;

    /** 提示时间 */
    @Excel(name = "提示时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    /** 备注 */
    @Excel(name = "备注")
    private String reamrk;

    /** 年度 */
    @Excel(name = "年度")
    private Integer year;

    /** 预算科目id */
    @Excel(name = "预算科目id")
    private Long subjectId;

    /** 预算科目名称 */
    @Excel(name = "预算科目名称")
    private String subjectName;

    /** 删除标志（0代表正常 1代表停用 2代表删除） */
    private String delFlag;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 请求参数 */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}
