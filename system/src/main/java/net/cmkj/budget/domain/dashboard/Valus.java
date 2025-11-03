package net.cmkj.budget.domain.dashboard;

import java.math.BigDecimal;

/**
 * @FileName Valus
 * @Description 子大屏-表格数据
 * @Author @hanjianghui
 * @Date 2025/6/15 21:28
 **/
@lombok.Data
public class Valus {
    private String dept;
    private BigDecimal gz;
    private BigDecimal lwf;
    private BigDecimal shbxf;
    private BigDecimal zgjyjf;
    private BigDecimal zgzfgjj;
}
