package net.cmkj.fixed.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class FixedVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /********************** 公共字段 ********************************/

    private Long id;

    private String ids;

    /** 填报项ID */
    private Long itemId;

    /** 填报项表名 */
    private String tableName;

    /** 填报任务ID */
    private Long taskId;

    /** 部门ID */
    private Long deptId;

    private List<Long> deptIds;

    private Long parentId;

    /** 项目类型*/
    private Integer projectType;

    /** 项目状态：1:续建项目 2:新增项目 */
    private Integer projectStatus;

    /** 项目名称 */
    private String name;

    /** 状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝） */
    private Integer status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 备注 */
    private String remark;

    /** 查询类型 */
    private Integer selectType;

    /** 导出类型 */
    private Integer exportType;

    /********************** 天然气管线 ********************************/

    /** 项目现状 */
    private String situation;

    /** 建设内容、进度 */
    private String contentProgress;

    /** 投资分析 */
    private String analysis;

    /** 管网总长度 */
    private BigDecimal length;

    /** 投资总额 */
    private BigDecimal investment;

    /** 已完结长度 */
    private BigDecimal completedLength;

    /** 已完结资金 */
    private BigDecimal completedSettlement;

    /** 今年实际结算长度 */
    private BigDecimal actualLength;

    /** 今年实际结算金额 */
    private BigDecimal actualSettlement;

    /** 结算资金明细 */
    private String details;

    /** 预计结算长度 */
    private BigDecimal expectedLength;

    /** 预计结算金额 */
    private BigDecimal expectedSettlement;

    /** 职能部门审核结算长度 */
    private BigDecimal auditLength;

    /** 职能部门审核结算金额 */
    private BigDecimal auditSettlement;

    /** 审核调整原因 */
    private String auditDescription;

    /** 年1预计结算长度 */
    private BigDecimal year1Length;

    /** 年1预计结算金额 */
    private BigDecimal year1Settlement;

    /** 年2预计结算长度 */
    private BigDecimal year2Length;

    /** 年2预计结算金额 */
    private BigDecimal year2Settlement;

    /** 年3预计结算长度 */
    private BigDecimal year3Length;

    /** 年3预计结算金额 */
    private BigDecimal year3Settlement;

    /** 年4预计结算长度 */
    private BigDecimal year4Length;

    /** 年4预计结算金额 */
    private BigDecimal year4Settlement;

    /** 年4预计结算长度 */
    private BigDecimal year5Length;

    /** 年4预计结算金额 */
    private BigDecimal year5Settlement;
    /********************** 信息系统建设 ********************************/
    /** 拟投运时间 */
    private Date operationalTime;

    /** 职能部门审核无形资产 */
    private BigDecimal auditIntangibleAssets;

    /** 职能部门审核固定资产 */
    private String auditFixedAssets;

    /********************** 科研计划 ********************************/

    /** 项目编号 */
    private String number;

    /** 计划开题时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 计划结题时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 主要研究内容 */
    private String researchContents;

    /** 项目预期主要成果形式 */
    private String deliverables;

    /** 项目总预算金额（元） */
    private BigDecimal totalBudget;

    /** 项目经费概算（元） */
    private BigDecimal budgetEstimate;

    /** 承担单位 */
    private String undertakingUnit;

    /** 协作单位 */
    private String cooperativeUnit;

    /** 无形资产 */
    private BigDecimal intangibleAssets;

    /** 固定资产 */
    private BigDecimal fixedAssets;

    /** 费用小计 */
    private BigDecimal subtotalCosts;

    /** 年费用预算（元） */
    private BigDecimal budget;

    /** 职能部室审核后预算（元） */
    private BigDecimal auditBudget;

    /** 上年预算（元） */
    private BigDecimal lastBudget;

    /** 上年累计发生费用（元） */
    private BigDecimal lastAccumulatedExpenses;

    /** 调剂后预算额度 */
    private BigDecimal adjustmentBudget;

    /** 调剂明细 */
    private String adjustmentDescription;

    /** 全年预计发生费用（元） */
    private BigDecimal projectedCosts;

    /** 资产预算说明 */
    private String fixedAssetsDescription;

    /** 费用预算说明 */
    private String subtotalCostsDescription;

    /******************************  计量器具校准、维修、改造 ********************************/

    /** 填报类型：1:安全类  2：非安全类 */
    private Integer reportingType;

    /** 数量 */
    private Integer quantity;

    /** 价格（元） */
    private BigDecimal price;

    /** 费用详情说明 */
    private String description;

}
