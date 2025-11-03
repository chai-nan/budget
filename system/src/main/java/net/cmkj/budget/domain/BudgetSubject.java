package net.cmkj.budget.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;
import java.util.Map;
import net.cmkj.common.annotation.Excel;
import lombok.Data;

import java.io.Serial;

/**
 * 预算科目对象 t_budget_subject
 *
 * @author cmkj
 * @date 2024-07-15
 */
@Data
@TableName("t_budget_subject")
public class BudgetSubject implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**  */
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(exist = false)
    @Excel(name = "费用类型")
    private String itemType;

    /** 预算科目 */
    @Excel(name = "汇总科目")
    private String name;

    /** 财务科目 */
    @Excel(name = "总账明细账科目", dictType = "financial_subjects")
    private String financelSubject;

    @Excel(name = "预算模板科目")
    private String reportName;

    /** 特殊类型 */
    private String specialType;

    /** 预算类型 */
    private String budgetType;

    @Excel(name = "科目类型", dictType = "dept_type")
    private String type;

    /** 增长率 */
    @Excel(name = "预算比例", suffix = "%")
    private BigDecimal budgetRatio;

    /** 增长率 */
    @Excel(name = "增长率", suffix = "%")
    private BigDecimal growthRate;

    private Integer hzExport;
    private Integer orderNum;

    private Integer xsExport;
    private Integer xsNumber;

    private Integer glExport;
    private Integer glNumber;

    private Integer yfExport;
    private Integer yfNumber;

    private Integer zzExport;
    private Integer zzNumber;

    /** 父ID */
    private Long parentId;
    /** 科目等级 */
    private Integer level;
    /** 祖级列表 */
    private String ancestors;

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


    @TableField(exist = false)
    private String financelSubjectName;

    @TableField(exist = false)
    private List<BudgetSubject> children;

    /** 请求参数 */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}
