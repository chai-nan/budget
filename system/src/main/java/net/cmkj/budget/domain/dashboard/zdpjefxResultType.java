package net.cmkj.budget.domain.dashboard;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @FileName zdpjefxResultType
 * @Description 预算金额和支出金额分析返回结果
 * @Author @hanjianghui
 * @Date 2025/6/18 4:41
 **/
@Data
public class zdpjefxResultType {
    private String month;
    private BigDecimal total;
}
