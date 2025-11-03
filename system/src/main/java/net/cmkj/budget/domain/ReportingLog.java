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
 * 填报记录对象 t_reporting_log
 *
 * @author cmkj
 * @date 2024-07-22
 */
@Data
@TableName("t_reporting_log")
public class ReportingLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**  */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 填报项目ID */
    @Excel(name = "填报项目ID")
    private Long itemId;

    /** 预算ID */
    @Excel(name = "预算ID")
    private String budgetId;

    /** 操作人 */
    private Long userId;
    @Excel(name = "操作人")
    private String userName;

    /** 操作部门 */
    private Long deptId;
    @Excel(name = "操作部门")
    private String deptName;


    /** 操作状态（1：提交申请  2：审批通过 3：审批驳回） */
    @Excel(name = "操作状态", readConverterExp = "1=：提交申请,2=：审批通过,3=：审批驳回")
    private Integer status;

    /** 备注描述 */
    @Excel(name = "备注描述")
    private String remark;

    /** 操作时间 */
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /** 请求参数 */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}
