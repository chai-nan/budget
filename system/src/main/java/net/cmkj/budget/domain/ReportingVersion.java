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
 * 版本控制对象 t_reporting_version
 * 
 * @author cmkj
 * @date 2024-07-17
 */
@Data
@TableName("t_reporting_version")
public class ReportingVersion implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 版本名称 */
    @Excel(name = "版本名称")
    private String name;

    /** 审核状态（1：新生成 2：已通过） */
    @Excel(name = "审核状态", readConverterExp = "1=：新生成,2=：已通过")
    private String status;

    /** 年份 */
    @Excel(name = "年份")
    private Integer year;

    /** 任务ID */
    private Long taskId;
    @Excel(name = "任务名称")
    private String taskName;

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
