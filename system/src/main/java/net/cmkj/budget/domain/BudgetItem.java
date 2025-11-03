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
 * 预算配置对象 t_budget_item
 * 
 * @author cmkj
 * @date 2024-07-15
 */
@Data
@TableName("t_budget_item")
public class BudgetItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 数据表名 */
    @Excel(name = "数据表名")
    private String tableName;

    /** 显示表名 */
    @Excel(name = "显示表名")
    private String tableDisplayName;

    @Excel(name = "预算类型")
    private String type;

    /** 管控部门 */
    private Long deptId;
    @Excel(name = "管控部门")
    @TableField(exist = false)
    private String deptName;

    /** 审核人 */
    private Long userId;
    @Excel(name = "审核人")
    @TableField(exist = false)
    private String userName;

    /** 预算科目 */
    private Long subjectId;
    @Excel(name = "预算科目")
    @TableField(exist = false)
    private String subjectName;

    /** 报表类型 1:动态报表 2：定制报表*/
    private String reportingType;

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

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 排序 */
    private Integer orderNum;

    /** 删除标志（0代表正常 1代表停用 2代表删除） */
    private String delFlag;

    /** 填报部门 */
    @TableField(exist = false)
    private Long[] deptIds;

    /** 是否填报 */
    @TableField(exist = false)
    private Integer isReporting;

    /** 是否驳回 */
    @TableField(exist = false)
    private Integer isReject;

    /** 是否审核 */
    @TableField(exist = false)
    private Integer isAudit;

    @TableField(exist = false)
    private Double growthRate;

    /** 是否确认 */
    @TableField(exist = false)
    private Integer isConfirm;

    /** 查询类型 1：填报页面 2:审核页面 3：确认页面 */
    @TableField(exist = false)
    private Integer selectType;

    @TableField(exist = false)
    private Long taskId;

    @TableField(exist = false)
    private String specialType;

    @TableField(exist = false)
    private String itemType;

    /** 1：除去工资  */
    @TableField(exist = false)
    private Integer queryType;

    @TableField(exist = false)
    private Long[] ids;

    @TableField(exist = false)
    private Integer subjectType;

    /** 请求参数 */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}
