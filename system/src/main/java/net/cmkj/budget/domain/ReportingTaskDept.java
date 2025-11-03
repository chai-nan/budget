package net.cmkj.budget.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import net.cmkj.common.annotation.Excel;
import lombok.Data;

import java.io.Serial;

/**
 * 部门任务填报情况对象 t_reporting_task_dept
 * 
 * @author cmkj
 * @date 2024-11-05
 */
@Data
@TableName("t_reporting_task_dept")
public class ReportingTaskDept implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 任务 */
    @Excel(name = "任务")
    private Long taskId;

    /** 部门 */
    @Excel(name = "部门")
    private Long deptId;

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
