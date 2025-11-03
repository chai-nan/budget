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
 * 预算填报订制【信息系统】版本数据对象 t_reporting_table_information_system_version
 * 
 * @author cmkj
 * @date 2024-09-26
 */
@Data
@TableName("t_reporting_table_information_system_version")
public class TableInformationSystemVersion implements Serializable {
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

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 项目状态：1:续建项目 2:新增项目 */
    @Excel(name = "项目状态：1:续建项目 2:新增项目")
    private Integer projectStatus;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String name;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quantity;

    /** 项目现状 */
    @Excel(name = "项目现状")
    private String situation;

    /** 建设内容、进度 */
    @Excel(name = "建设内容、进度")
    private String contentProgress;

    /** 拟投运时间 */
    @Excel(name = "拟投运时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date operationalTime;

    /** 投资总额 */
    @Excel(name = "投资总额")
    private BigDecimal investment;

    /** 截止去年已结算资金 */
    @Excel(name = "截止去年已结算资金", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal completedSettlement;

    /** 今年实际结算金额 */
    @Excel(name = "今年实际结算金额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal actualSettlement;

    /** 无形资产 */
    @Excel(name = "无形资产", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal intangibleAssets;

    /** 固定资产 */
    @Excel(name = "固定资产", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal fixedAssets;

    /** 职能部门审核无形资产 */
    @Excel(name = "职能部门审核无形资产", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal auditIntangibleAssets;

    /** 职能部门审核固定资产 */
    @Excel(name = "职能部门审核固定资产")
    private String auditFixedAssets;

    /** 审核调整原因 */
    @Excel(name = "审核调整原因")
    private String auditDescription;

    /** 年1 */
    @Excel(name = "number+,2,{}年（元）")
    private BigDecimal year1Settlement;

    /** 年2 */
    @Excel(name = "number+,3,{}年（元）")
    private BigDecimal year2Settlement;

    /** 年3 */
    @Excel(name = "number+,4,{}年（元）")
    private BigDecimal year3Settlement;

    /** 年4 */
    @Excel(name = "number+,5,{}年（元）")
    private BigDecimal year4Settlement;

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
