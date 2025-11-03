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
import java.util.List;

/**
 * 预算填报项目【天然气管线】对象 t_reporting_table_pipeline
 * 
 * @author cmkj
 * @date 2024-09-03
 */
@Data
@TableName("t_reporting_table_pipeline")
public class TablePipeline implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 填报任务ID */
    private Long taskId;

    @Excel(name = "企业名称")
    @TableField(exist = false)
    private String parentName;

    //@Excel(name = "填报任务")
    @TableField(exist = false)
    private String taskName;

    /** 部门ID */
    private Long deptId;

    @Excel(name = "填报部门")
    @TableField(exist = false)
    private String deptName;

    /** 状态1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝） */
    @Excel(name = "填报状态", dictType = "budget_status" )
    private Integer status;

    /** 项目状态：1:续建项目 2:新增项目 */
    @Excel(name = "项目状态", readConverterExp = "1=续建项目,2=新增项目")
    private Integer projectStatus;

    @Excel(name = "项目类型", readConverterExp = "1=燃气管道-高压管网,2=燃气管道-中压管网,3=燃气管道-低压管网,4=燃气管道-其他")
    private Integer projectType;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String name;

    /** 项目现状 */
    @Excel(name = "项目现状")
    private String situation;

    /** 建设内容、进度 */
    @Excel(name = "建设内容、进度")
    private String contentProgress;

    /** 投资分析 */
    @Excel(name = "投资分析")
    private String analysis;

    /** 管网总长度 */
    @Excel(name = "管网长度(KM)",parentName = "投资概况")
    private BigDecimal length;

    /** 投资总额 */
    @Excel(name = "投资总额",parentName = "投资概况", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal investment;

    /** 已完结长度 */
    @Excel(name = "已结算长度(KM)",parentName = "number-,1,截至{}年底已结算情况")
    private BigDecimal completedLength;

    /** 已完结资金 */
    @Excel(name = "已结算金额",parentName = "number-,1,截至{}年底已结算情况", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal completedSettlement;

    /** 今年实际结算长度 */
    @Excel(name = "结算长度(KM)",parentName = "number+,0,{}年实际结算情况（含截至年底尚未发生预计数）")
    private BigDecimal actualLength;

    /** 今年实际结算金额 */
    @Excel(name = "结算金额",parentName = "number+,0,{}年实际结算情况（含截至年底尚未发生预计数）", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal actualSettlement;

    /** 结算资金明细 */
    @Excel(name = "结算资金明细",parentName = "number+,0,{}年实际结算情况（含截至年底尚未发生预计数）")
    private String details;

    /** 预计结算长度 */
    @Excel(name = "结算长度",parentName = "number+,1,{}年预计结算情况（元/KM)")
    private BigDecimal expectedLength;

    /** 预计结算金额 */
    @Excel(name = "结算金额",parentName = "number+,1,{}年预计结算情况（元/KM)", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal expectedSettlement;

    /** 职能部门审核结算长度 */
    //@Excel(name = "结算长度",parentName = "职能部门审核业务部门预算后填报（元/KM)")
    private BigDecimal auditLength;

    /** 职能部门审核结算金额 */
    //@Excel(name = "结算金额",parentName = "职能部门审核业务部门预算后填报（元/KM)")
    private BigDecimal auditSettlement;

    /** 审核调整原因 */
    //@Excel(name = "审核调整原因",parentName = "职能部门审核业务部门预算后填报（元/KM)")
    private String auditDescription;

    /** 年1预计结算长度 */
    @Excel(name = "结算长度(KM)",parentName = "number+,2,{}年预计结算情况（元/KM)")
    private BigDecimal year1Length;

    /** 年1预计结算金额 */
    @Excel(name = "结算金额",parentName = "number+,2,{}年预计结算情况（元/KM)")
    private BigDecimal year1Settlement;

    /** 年2预计结算长度 */
    @Excel(name = "结算长度(KM)",parentName = "number+,3,{}年预计结算情况（元/KM)")
    private BigDecimal year2Length;

    /** 年2预计结算金额 */
    @Excel(name = "结算金额",parentName = "number+,3,{}年预计结算情况（元/KM)")
    private BigDecimal year2Settlement;

    /** 年3预计结算长度 */
    @Excel(name = "结算长度(KM)",parentName = "number+,4,{}年预计结算情况（元/KM)")
    private BigDecimal year3Length;

    /** 年3预计结算金额 */
    @Excel(name = "结算金额",parentName = "number+,4,{}年预计结算情况（元/KM)")
    private BigDecimal year3Settlement;

    /** 年4预计结算长度 */
    @Excel(name = "结算长度(KM)",parentName = "number+,5,{}年预计结算情况（元/KM)")
    private BigDecimal year4Length;

    /** 年4预计结算金额 */
    @Excel(name = "结算金额",parentName = "number+,5,{}年预计结算情况（元/KM)")
    private BigDecimal year4Settlement;

    /** 年5预计结算长度 */
    //@Excel(name = "结算长度",parentName = "number+,6,{}年预计结算情况")
    private BigDecimal year5Length;

    /** 年5预计结算金额 */
    //@Excel(name = "结算金额",parentName = "number+,6,{}年预计结算情况")
    private BigDecimal year5Settlement;

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
    private Long parentId;

    @TableField(exist = false)
    private List<Long> deptIds;

    @TableField(exist = false)
    private Integer selectType;

    @TableField(exist = false)
    private Integer reportNumber;

    @TableField(exist = false)
    private String userName;
}
