package net.cmkj.budget.domain.dashboard;

import lombok.Data;

/**
 * @FileName QueryTableDetailDTO
 * @Description 查询子大屏table页实际费用参数
 * @Author @hanjianghui
 * @Date 2025/9/9 15:56
 **/
@Data
public class QueryTableDetailDTO {
    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 公司id
     */
    private Long companyId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 科目名称
     */
    private String subjectName;
}
