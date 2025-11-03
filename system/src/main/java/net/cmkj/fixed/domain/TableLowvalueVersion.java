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
 * 预算填报订制【低值易耗品】版本数据对象 t_reporting_table_lowvalue_version
 * 
 * @author cmkj
 * @date 2024-09-26
 */
@Data
@TableName("t_reporting_table_lowvalue_version")
public class TableLowvalueVersion implements Serializable {
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

    /** 填报部门 */
    @Excel(name = "填报部门")
    private Long deptId;

    /** 1:工具购置类 2：办公用品类 3：计量器具类 4：信息类 */
    @Excel(name = "1:工具购置类 2：办公用品类 3：计量器具类 4：信息类")
    private Integer reportingType;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quantity;

    /** 价格（元） */
    @Excel(name = "价格", readConverterExp = "元=", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal price;

    /** 费用预算（元） */
    @Excel(name = "费用预算", readConverterExp = "元=", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal budget;

    /** 费用详情说明 */
    @Excel(name = "费用详情说明")
    private String description;

    /** 职能部室审核后预算（元） */
    @Excel(name = "职能部室审核后预算", readConverterExp = "元=", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal auditBudget;

    /** 审核调整原因 */
    @Excel(name = "审核调整原因")
    private String auditDescription;

    /** 上年预算（元） */
    @Excel(name = "上年预算", readConverterExp = "元=", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal lastBudget;

    /** 上年累计发生费用（元） */
    @Excel(name = "上年累计发生费用", readConverterExp = "元=", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal lastAccumulatedExpenses;

    /** 调剂后预算额度 */
    @Excel(name = "调剂后预算额度", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal adjustmentBudget;

    /** 调剂明细 */
    @Excel(name = "调剂明细")
    private String adjustmentDescription;

    /** 全年预计发生费用（元） */
    @Excel(name = "全年预计发生费用", readConverterExp = "元=", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal projectedCosts;

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
