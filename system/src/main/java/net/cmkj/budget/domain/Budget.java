package net.cmkj.budget.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 预算查询
 * 
 * @author cmkj
 */
@Data
public class Budget implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private Long taskId;

    private Long itemId;

    private Long deptId;

    private String deptName;

    private Long parentId;

    private Integer status;

    private String remark;

    private Double reviewBudget;

    private String reviewReason;

    private Integer selectType;

    private Integer exportType;

    private Integer type;

    @TableField(exist = false)
    private Long[] itemIds;

}
