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
 * 预算填报订制【场站】版本数据对象 t_reporting_table_station_version
 * 
 * @author cmkj
 * @date 2024-09-26
 */
@Data
@TableName("t_reporting_table_station_version")
public class TableStationVersion implements Serializable {
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

    @Excel(name = "项目类型", readConverterExp = "1=CNG加气站,2=LNG加气站,3=L-CNG合建站,4=LPG加气站,5=门站,6=高中压调压站,7=气源厂|储配站,8=其他一,9=其他二,10=其他三,11=分布式光伏,12=分布式能源,13=交通能源")
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

    /** 投资总额 */
    @Excel(name = "投资总额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal investment;

    /** 截止去年已结算金额 */
    @Excel(name = "截止去年已结算金额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal completedSettlement;

    /** 今年实际结算金额 */
    @Excel(name = "今年实际结算金额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal actualSettlement;

    /** 结算资金明细 */
    @Excel(name = "结算资金明细")
    private String details;

    /** 明年预计结算金额 */
    @Excel(name = "明年预计结算金额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal expectedSettlement;

    /** 职能部门审核结算金额 */
    @Excel(name = "职能部门审核结算金额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal auditSettlement;

    /** 审核调整原因 */
    @Excel(name = "审核调整原因")
    private String auditDescription;

    /** 年1 */
    @Excel(name = "年1")
    private BigDecimal year1Settlement;

    /** 年2 */
    @Excel(name = "年2")
    private BigDecimal year2Settlement;

    /** 年3 */
    @Excel(name = "年3")
    private BigDecimal year3Settlement;

    /** 年4 */
    @Excel(name = "年4")
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
