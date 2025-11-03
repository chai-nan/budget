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
 * 预算填报项目【房屋建设、装修】对象 t_reporting_table_housing
 * 
 * @author cmkj
 * @date 2024-08-27
 */
@Data
@TableName("t_reporting_table_housing")
public class TableHousing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

    /** 部门ID */
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

    /** 项目类型：1：房屋建设 2：房屋装修 */
    @Excel(name = "项目类型",readConverterExp = "1=房屋建筑物-办公楼,2=房屋建筑物-房屋装修,3=房屋建筑物-仓库等,4=房屋建筑物-土地")
    private Integer projectType;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String name;

    /** 项目现状 */
    @Excel(name = "项目现状")
    private String situation;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quantity;

    /** 建设内容、进度 */
    @Excel(name = "建设内容、进度")
    private String contentProgress;

    /** 投资分析 */
    @Excel(name = "投资分析")
    private String analysis;

    /** 投资总额 */
    @Excel(name = "投资总额", cellType = Excel.ColumnType.NUMERIC,parentName = "资金安排（元）")
    private BigDecimal investment;

    /** 已结算资金 */
    @Excel(name = "number-,1,截至{}年底已结算金额", cellType = Excel.ColumnType.NUMERIC,parentName = "资金安排（元）")
    private BigDecimal completedSettlement;

    /** 今年实际结算金额 */
    @Excel(name = "number+,0,{}年实际结算金额（含截至年底尚未发生预计数）", cellType = Excel.ColumnType.NUMERIC,parentName = "资金安排（元）")
    private BigDecimal actualSettlement;

    /** 结算资金明细 */
    @Excel(name = "number+,0,{}年结算资金明细")
    private String details;

    /** 明年预计结算金额 */
    @Excel(name = "number+,1,{}年预计结算金额（元）")
    private BigDecimal expectedSettlement;

    /** 职能部门审核结算金额 */
    //@Excel(name = "number+,1,{}年预计结算金额",parentName = "职能部门审核业务部门预算后填报（元）")
    private BigDecimal auditSettlement;

    /** 审核调整原因 */
    //@Excel(name = "审核调整原因",parentName = "职能部门审核业务部门预算后填报（元）")
    private String auditDescription;

    /** 年1 */
    @Excel(name = "number+,2,{}年预计结算金额（元）")
    private BigDecimal year1Settlement;

    /** 年2 */
    @Excel(name = "number+,3,{}年预计结算金额（元）")
    private BigDecimal year2Settlement;

    /** 年3 */
    @Excel(name = "number+,4,{}年预计结算金额（元）")
    private BigDecimal year3Settlement;

    /** 年4 */
    @Excel(name = "number+,5,{}年预计结算金额（元）")
    private BigDecimal year4Settlement;


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
