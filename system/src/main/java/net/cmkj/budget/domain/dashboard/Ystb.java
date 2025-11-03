package net.cmkj.budget.domain.dashboard;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @FileName Ystb
 * @Description 预算同比变化
 * @Author @hanjianghui
 * @Date 2025/6/13 14:16
 **/
@lombok.Data
public class Ystb {
    /**
     * 今年总额
     */
    //private BigDecimal jnze;
    /**
     * 去年总额
     */
    //private BigDecimal qnje;


    private List<HashMap<String,Object>> ystb ;
}