package net.cmkj.budget.domain.dashboard;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @FileName YstjResultType
 * @Description 预算统计数据库结果类型
 * @Author @hanjianghui
 * @Date 2025/6/17 9:26
 **/
@Data
public class YstjResultType {
    private Long Id;
    private String areaName;
    private BigDecimal total;
}
