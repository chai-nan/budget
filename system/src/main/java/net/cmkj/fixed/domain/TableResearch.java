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
 * 预算填报项目【科研计划申报】对象 t_reporting_table_research
 * 
 * @author cmkj
 * @date 2024-08-27
 */
@Data
@TableName("t_reporting_table_research")
public class TableResearch implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    @Excel(name = "企业名称")
    @TableField(exist = false)
    private String parentName;

    /** 填报任务ID */
    private Long taskId;

    //@Excel(name = "填报任务")
    @TableField(exist = false)
    private String taskName;

    /** 立项单位 */
    private Long deptId;

    @Excel(name = "填报部门")
    @TableField(exist = false)
    private String deptName;

    /** 状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝） */
    @Excel(name = "填报状态", dictType = "budget_status")
    private Integer status;

    /** 项目状态：1:续建项目 2:新增项目 */
    @Excel(name = "项目状态", readConverterExp = "1=续建项目,2=新增项目")
    private Integer projectStatus;

    /** 1:技术创新 2：技术引进 3：软课题研究 */
    @Excel(name = "项目类别",readConverterExp = "1=基础研究,2=应用研究,3=试验发展")
    private Integer projectType;

    /** 项目编号 */
    //@Excel(name = "项目编号")
    private String number;

    /** 数量 */
    //@Excel(name = "数量")
    private Integer quantity;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String name;

    /** 协作单位 */
    @Excel(name = "项目协作单位")
    private String cooperativeUnit;

    /** 计划开题时间 */
    @Excel(name = "计划开题时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 计划结题时间 */
    @Excel(name = "计划结题时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 主要研究内容 */
    @Excel(name = "主要研究内容")
    private String researchContents;

    /** 项目预期主要成果形式 */
    @Excel(name = "项目预期主要成果形式")
    private String deliverables;

    /** 项目总预算金额（元） */
    @Excel(name = "项目总预算金额（元）", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal investment;

    /** 已支出金额（元） */
    @Excel(name = "number-,1,截至{}年底已支出金额（元）", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal completedSettlement;

    /** 预计支出总金额（元） */
    @Excel(name = "number+,0,{}年预计支出总金额（元）", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal expectedSettlement;

    /** 无形资产 */
    @Excel(name = "无形资产",parentName = "number+,1,{}年预算总金额（元）", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal intangibleAssets;

    /** 固定资产 */
    @Excel(name = "固定资产",parentName = "number+,1,{}年预算总金额（元）", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal fixedAssets;

    /** 资产预算说明 */
    @Excel(name = "资产预算说明",parentName = "number+,1,{}年预算总金额（元）")
    private String fixedAssetsDescription;

    /** 费用小计 */
    @Excel(name = "费用小计",parentName = "number+,1,{}年预算总金额（元）", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal subtotalCosts;

    /** 费用预算说明 */
    @Excel(name = "费用预算说明",parentName = "number+,1,{}年预算总金额（元）")
    private String subtotalCostsDescription;

    /** 职能部室审核后预算（元） */
    //@Excel(name = "职能部室审核后预算",parentName = "职能部门审核业务部门预算后填报（元）")
    private BigDecimal auditSettlement;

    /** 审核调整原因 */
    //@Excel(name = "审核调整原因",parentName = "职能部门审核业务部门预算后填报（元）")
    private String auditDescription;

    /** 年1预计结算金额 */
    @Excel(name = "number+,2,{}年预计结算金额（元）")
    private BigDecimal year1Settlement;

    /** 年2预计结算金额 */
    @Excel(name = "number+,3,{}年预计结算金额（元）")
    private BigDecimal year2Settlement;

    /** 年3预计结算金额 */
    @Excel(name = "number+,4,{}年预计结算金额（元）")
    private BigDecimal year3Settlement;

    /** 年4预计结算金额 */
    @Excel(name = "number+,5,{}年预计结算金额（元）")
    private BigDecimal year4Settlement;

    /** 备注 */
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
