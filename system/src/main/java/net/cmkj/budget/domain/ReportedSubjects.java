package net.cmkj.budget.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import net.cmkj.common.annotation.Excel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 上报科目对象 t_reported_subjects
 * 
 * @author cmkj
 * @date 2025-01-09
 */
@Data
@TableName("t_reported_subjects")
public class ReportedSubjects implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 上报科目 */
    @Excel(name = "上报科目")
    private String name;

    /** 科目类型 */
    @Excel(name = "科目类型")
    private Integer type;

    /** 费用类型 */
    @Excel(name = "费用类型")
    private String budgetType;

    private String budgetTypeName;

    /** 填报科目 */
    @Excel(name = "填报科目")
    private String subjects;

    private String subjectNames;

    /** 排序 */
    @Excel(name = "排序")
    private Integer orderNum;

    /** 一季度比例 */
    @Excel(name = "一季度比例")
    private BigDecimal oneQuarterlyRatio;

    /** 二季度比例 */
    @Excel(name = "二季度比例")
    private BigDecimal twoQuarterlyRatio;

    /** 三季度比例 */
    @Excel(name = "三季度比例")
    private BigDecimal threeQuarterlyRatio;

    /** 四季度比例 */
    @Excel(name = "四季度比例")
    private BigDecimal fourQuarterlyRatio;

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
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}
