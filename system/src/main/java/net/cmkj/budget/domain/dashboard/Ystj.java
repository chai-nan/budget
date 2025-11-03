package net.cmkj.budget.domain.dashboard;

import java.math.BigDecimal;
import java.util.List;

/**
 * @FileName Ystj
 * @Description 预算统计
 * @Author @hanjianghui
 * @Date 2025/6/13 14:17
 **/

@lombok.Data
public class Ystj {
    /**
     * 区域
     */
    private List<String> area;
    /**
     * 预算费用
     */
    private List<BigDecimal> ysfy;
    /**
     * 实际费用
     */
    private List<BigDecimal> sjfy;
}
