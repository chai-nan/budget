package net.cmkj.budget.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.cmkj.common.annotation.Excel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 预警配置对象 t_budget_warning
 * 
 * @author cmkj
 * @date 2024-11-27
 */
@Data
@TableName("t_budget_warning")
public class BudgetWarning implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 任务ID */
    private Long taskId;

    @Excel(name = "任务名称")
    @TableField(exist = false)
    private String taskName;

    /** 预警类型  1：填报预警  2：超出预警 3：费用报销预警 4：经济事项 5: 未及时审核 */
    @Excel(name = "预警类型 1：填报预警  2：超出预警 3：费用报销预警 4：经济事项 5：未及时审核")
    private Integer type;

    /** 填报表ID */
    private String itemIds;

    /** 预警科目 */
    private String subjects;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 比例 */
    @Excel(name = "比例")
    private Double proportion;

    private Long versionId;

    /** 事项名称 */
    private String itemName;

    /** 公司id */
    private String companyId;

    /** 部门id */
    private String deptId;

    /** 未审核天数 */
    private Integer pendingReviewDays;

    /** 状态：1：开启  2：关闭 */
    @Excel(name = "状态：1：开启  2：关闭")
    private Integer status;

    /** 提醒：1：一日一次  2：一周一次 */
    @Excel(name = "提醒：1：一日一次  2：一周一次")
    private Integer remind;

    /** 上次日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastTime;

    /** 删除标志（0代表正常 1代表停用 2代表删除） */
    private String delFlag;

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
}
