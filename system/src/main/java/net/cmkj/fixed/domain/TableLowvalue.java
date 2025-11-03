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
 * 预算填报项目【低值易耗品】对象 t_reporting_table_lowvalue
 * 
 * @author cmkj
 * @date 2024-08-28
 */
@Data
@TableName("t_reporting_table_lowvalue")
public class TableLowvalue implements Serializable {
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

    /** 1:工具购置类 2：办公用品类 3：计量器具类 4：信息类 */
    @Excel(name = "填报类型", readConverterExp = "1=工具购置类,2=办公用品类,3=计量器具类,4=信息类")
    private Integer reportingType;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quantity;

    /** 价格（元） */
    @Excel(name = "价格", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal price;

    /** 费用预算（元） */
    @Excel(name = "number+,1,{}年费用预算（元）", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal budget;

    /** 费用详情说明 */
    @Excel(name = "费用详情说明")
    private String description;

    /** 职能部室审核后预算（元） */
    //@Excel(name = "职能部室审核后预算",parentName = "职能部门审核业务部门预算后填报（元）")
    private BigDecimal auditBudget;

    /** 审核调整原因 */
    //@Excel(name = "审核调整原因",parentName = "职能部门审核业务部门预算后填报（元）")
    private String auditDescription;

    /** 上年预算（元） */
    @Excel(name = "预算",parentName = "number+,0,{}年（元）", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal lastBudget;

    /** 上年累计发生费用（元） */
    @Excel(name = "累计发生费用",parentName = "number+,0,{}年（元）", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal lastAccumulatedExpenses;

    /** 调剂后预算额度 */
    //@Excel(name = "调剂后预算额度",parentName = "预算调整审批结束后登记调整明细")
    private BigDecimal adjustmentBudget;

    /** 调剂明细 */
    //@Excel(name = "调剂明细",parentName = "预算调整审批结束后登记调整明细")
    private String adjustmentDescription;

    /** 全年预计发生费用（元） */
    @Excel(name = "年全年预计发生费用",parentName = "number+,0,{}年（元）", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal projectedCosts;


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
