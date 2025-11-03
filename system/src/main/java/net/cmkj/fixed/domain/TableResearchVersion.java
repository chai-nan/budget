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
 * 预算填报订制【科研计划申报】版本数据对象 t_reporting_table_research_version
 * 
 * @author cmkj
 * @date 2024-09-26
 */
@Data
@TableName("t_reporting_table_research_version")
public class TableResearchVersion implements Serializable {
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

    /** 立项单位 */
    @Excel(name = "立项单位")
    private Long deptId;

    /** 项目状态：1:续建项目 2:新增项目 */
    @Excel(name = "项目状态：1:续建项目 2:新增项目")
    private Integer projectStatus;

    /** 1:技术创新 2：技术引进 3：软课题研究 */
    @Excel(name = "项目类别",readConverterExp = "1=基础研究,2=应用研究,3=试验发展")
    private Integer projectType;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String number;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quantity;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String name;

    /** 协作单位 */
    @Excel(name = "协作单位")
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
    @Excel(name = "项目总预算金额", readConverterExp = "万=元", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal investment;

    /** 已支出金额（元） */
    @Excel(name = "已支出金额", readConverterExp = "万=元", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal completedSettlement;

    /** 预计支出总金额（元） */
    @Excel(name = "预计支出总金额", readConverterExp = "万=元", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal expectedSettlement;

    /** 无形资产 */
    @Excel(name = "无形资产", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal intangibleAssets;

    /** 固定资产 */
    @Excel(name = "固定资产", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal fixedAssets;

    /** 资产预算说明 */
    @Excel(name = "资产预算说明")
    private String fixedAssetsDescription;

    /** 费用小计 */
    @Excel(name = "费用小计", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal subtotalCosts;

    /** 费用预算说明 */
    @Excel(name = "费用预算说明")
    private String subtotalCostsDescription;

    /** 职能部室审核后预算（元） */
    @Excel(name = "职能部室审核后预算", readConverterExp = "元=")
    private BigDecimal auditSettlement;

    /** 审核调整原因 */
    @Excel(name = "审核调整原因")
    private String auditDescription;

    /** 年1预计结算金额 */
    @Excel(name = "结算金额",parentName = "number+,2,{}年预计结算情况")
    private BigDecimal year1Settlement;

    /** 年2预计结算金额 */
    @Excel(name = "结算金额",parentName = "number+,3,{}年预计结算情况")
    private BigDecimal year2Settlement;

    /** 年3预计结算金额 */
    @Excel(name = "结算金额",parentName = "number+,4,{}年预计结算情况")
    private BigDecimal year3Settlement;

    /** 年4预计结算金额 */
    @Excel(name = "结算金额",parentName = "number+,5,{}年预计结算情况")
    private BigDecimal year4Settlement;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

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
