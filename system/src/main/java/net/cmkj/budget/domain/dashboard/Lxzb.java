package net.cmkj.budget.domain.dashboard;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;

/**
 * @FileName Lxzb
 * @Description 类型占比
 * @Author @hanjianghui
 * @Date 2025/6/13 14:15
 **/
@lombok.Data
public class Lxzb {
    /**
     * 安全生产
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal aqsc;
    /**
     * 其他
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal qt;
    /**
     * 其他可控
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal qtkk;
    /**
     * 日产生产
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal rcsc;
    /**
     * 员工成本
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal ygcb;
    /**
     * 业务拓展
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal ywtz;
    /**
     * 资产投资
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal zctz;
    /**
     * 总额
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal ze;
}

