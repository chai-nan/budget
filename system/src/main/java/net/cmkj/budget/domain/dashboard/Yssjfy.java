package net.cmkj.budget.domain.dashboard;

import java.math.BigDecimal;
import java.util.List;

/**
 * @FileName Yssjfy
 * @Description 预算费用实际费用
 * @Author @hanjianghui
 * @Date 2025/6/13 14:16
 **/
@lombok.Data
public class Yssjfy {

    private List<String> type;

    /**
     * 当前年 实际费用
     */
    private List<BigDecimal> currentYearSjfy;

    /**
     * 当前年 预算费用
     */
    private List<BigDecimal> currentYearYsfy;

    /**
     * 上一年 预算费用
     */
    private List<BigDecimal> lastYearYsfy;

    /**
     * 上一年 实际费用
     */
    private List<BigDecimal> lastYearSjfy;
}

