package net.cmkj.budget.domain.dashboard;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;

/**
 * @FileName Ztgk
 * @Description 整体概况
 * @Author @hanjianghui
 * @Date 2025/6/13 14:18
 **/
@lombok.Data
public class Ztgk {
    /**
     * 实际费用
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal sjfy;
    /**
     * 预算金额
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal ysje;
}
