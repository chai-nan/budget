package net.cmkj.budget.domain;

import lombok.Data;

/**
* @FileName UnauditedWarningVO
* @Description 未及时审核预警信息
* @Author @hanjianghui
* @Date 2025/7/30 15:33
**/
@Data
public class UnauditedWarningVO {
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 表格显示名称
     */
    private String tableDisplayName;
}