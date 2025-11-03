package net.cmkj.budget.domain.dashboard;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @FileName SjfyResultType
 * @Description 实际费用数据库结果类型
 * @Author @hanjianghui
 * @Date 2025/6/17 9:42
 **/
@Data
public class SjfyResultType {
    private BigDecimal total;
    private String itemType;
}
