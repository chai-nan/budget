package net.cmkj.fixed.domain;

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
 * 预算填报订制【工资福利】版本数据对象 t_reporting_table_wages_version
 * 
 * @author cmkj
 * @date 2024-09-26
 */
@Data
@TableName("t_reporting_table_wages_version")
public class TableWagesVersion implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 版本ID */
    @Excel(name = "版本ID")
    private Long versionId;

    /** 填报任务ID */
    @Excel(name = "填报任务ID")
    private Long taskId;

    /** 部门 */
    @Excel(name = "部门")
    private Long deptId;

    /** 员工 */
    @Excel(name = "员工", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal staff;

    /** 劳务费 */
    @Excel(name = "劳务费", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal labor;

    /** 福利费 */
    @Excel(name = "福利费", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal welfare;

    /** 教育经费 */
    @Excel(name = "教育经费", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal education;

    /** 工会经费 */
    @Excel(name = "工会经费", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal unionFunds;

    /** 养老保险 */
    @Excel(name = "养老保险", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal endowment;

    /** 工伤保险 */
    @Excel(name = "工伤保险", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal workInjury;

    /** 失业保险 */
    @Excel(name = "失业保险", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal unemployment;

    /** 医疗保险 */
    @Excel(name = "医疗保险", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal medical;

    /** 生育保险 */
    @Excel(name = "生育保险", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal maternity;

    /** 社会保险费-单位部分 */
    @Excel(name = "社会保险费-单位部分", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal social;

    /** 住房公积金-单位部分 */
    @Excel(name = "住房公积金-单位部分", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal provident;

    /** 企业年金-单位部分 */
    @Excel(name = "企业年金-单位部分", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal annuity;

    /** 研发人工成本 */
    @Excel(name = "研发人工成本", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal research;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}
