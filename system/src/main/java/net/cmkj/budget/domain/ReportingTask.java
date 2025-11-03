package net.cmkj.budget.domain;

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
import java.util.Date;
import java.util.Map;

/**
 * 预算任务对象 t_reporting_task
 * 
 * @author cmkj
 * @date 2024-07-16
 */
@Data
@TableName("t_reporting_task")
public class ReportingTask implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 填报任务ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 年份 */
    @Excel(name = "填报年份")
    private Integer year;

    @Excel(name = "预算年度")
    private Integer budgetYear;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String name;

    /** 起始时间 */
    @Excel(name = "起始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;



    /** 完成情况 （1：进行中 2：已完成） */
    @Excel(name = "完成情况 ", readConverterExp = "1=进行中,2=已完成")
    private String completionStatus;

    /** 审核状态 （1：待审核 2：已通过 3：未通过） */
    @Excel(name = "审核状态 ", readConverterExp = "1=待审核,2=已通过,3=未通过")
    private String auditStatus;

    /** 填报说明 */
    @Excel(name = "填报说明")
    private String reportingExplain;

    /** 审核说明 */
    private String auditExplain;

    /** 上次任务 */
    private Long lastTask;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 已重新生成任务 */
    @TableField(exist = false)
    private Long newTask;

    /** 请求参数 */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;

    /** 首页待办事项状态名称 */
    @TableField(exist = false)
    private String statusName;
}
