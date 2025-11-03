package net.cmkj.budget.domain.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @FileName Ysjezcjefx
 * @Description 子大屏-预算金额和支出金额分析
 * @Author @hanjianghui
 * @Date 2025/6/15 21:22
 **/
@Data
public class Ysjezcjefx {
    private List<String> months;
    //private List<BigDecimal> ysje;
    private BigDecimal[] ysje;
    private List<BigDecimal> zcje;
}
