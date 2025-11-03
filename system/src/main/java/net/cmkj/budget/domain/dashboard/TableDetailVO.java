package net.cmkj.budget.domain.dashboard;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @FileName TableDetailVO
 * @Description 子大屏table页实际费用返回对象
 * @Author @hanjianghui
 * @Date 2025/9/9 16:31
 **/
@Data
public class TableDetailVO {

    /**
     * 总账日期
     */
    private String zzrq;

    /**
     * 会计科目说明
     */
    private String kjkmsm;

    /**
     * 日记账头说明
     */
    private String rjztsm;

    /**
     * 摘要
     */
    private String zy;

    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 总金额
     */
    private BigDecimal total;

    /**
     * 贷方金额
     */
    private BigDecimal bwddf;
}
