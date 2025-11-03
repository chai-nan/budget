package net.cmkj.fixed.domain;

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
 * 预算预算项目【工资福利】对象 t_reporting_table_wages
 * 
 * @author cmkj
 * @date 2024-08-26
 */
@Data
@TableName("t_reporting_table_wages")
public class TableWages implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(exist = false)
    private String ids;

    /** 填报任务ID */
    private Long taskId;

    private Long deptId;

    @Excel(name = "部门")
    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private Long deptParentId;

    /** 员工 */
    @Excel(name = "工资总额（员工）", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal staff;

    /** 劳务费 */
    @Excel(name = "劳务派遣", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal labor;

    /** 合计 */
    @Excel(name = "合计", cellType = Excel.ColumnType.NUMERIC)
    @TableField(exist = false)
    private BigDecimal total;

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
    @Excel(name = "研发经费", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal research;

    /** 总计 */
    @Excel(name = "总计", cellType = Excel.ColumnType.NUMERIC)
    @TableField(exist = false)
    private BigDecimal allTotal;

    /** 状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝） */
    @Excel(name = "状态", dictType = "budget_status")
    private Integer status;

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
    private Integer selectType;

    @TableField(exist = false)
    private String remark;

    @TableField(exist = false)
    private String parentName;

}
