package net.cmkj.budget.domain.dashboard;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;

/**
 * @FileName Ysfx
 * @Description 预算分析
 * @Author @hanjianghui
 * @Date 2025/6/13 14:16
 **/
@lombok.Data
public class Ysfx {
    /**
     * 与安全生产相关费用
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal aqsc;
    /**
     * 日常生产经营
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal qcscjy;
    /**
     * 其他
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal qt;
    /**
     * 与员工成本相关费用
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal ygcb;
    /**
     * 与业务拓展相关费用
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal ywtz;
    /**
     * 与资产投资相关费用
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal zctz;
}

