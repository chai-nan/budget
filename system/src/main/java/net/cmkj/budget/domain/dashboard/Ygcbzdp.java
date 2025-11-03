package net.cmkj.budget.domain.dashboard;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @FileName Ygcbzdp
 * @Description 员工成本子大屏
 * @Author @hanjianghui
 * @Date 2025/6/15 21:17
 **/
@Data
public class Ygcbzdp {
    /**
     * 表格
     */
    private Tables tables;

    /**
     * 顶部
     */
    private Top top;

    /**
     * 预算分析
     */
    private List<HashMap<String,Object>> ysfx ;

    /**
     * 预算金额支出分析
     */
    private Ysjezcjefx ysjezcjefx;

    /**
     * 预算支出进度对比
     */
    private List<HashMap<String,Object>> yszcjdzb ;

    /**
     * 预算同比分析
     */
    private List<HashMap<String,Object>> zctbfx ;

    /**
     * 去年和本年四季实际费用
     */
    private List<HashMap<String,Object>> lastAndCurrentYearQuarterly;
}
