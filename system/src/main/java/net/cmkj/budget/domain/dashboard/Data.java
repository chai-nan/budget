package net.cmkj.budget.domain.dashboard;

import java.util.HashMap;
import java.util.List;

/**
 * @FileName Data
 * @Description Data
 * @Author @hanjianghui
 * @Date 2025/6/13 14:14
 **/
@lombok.Data
public class Data {
    /**
     * 类型占比
     */
    private Lxzb lxzb;
    private List<HashMap<String,Object>> lxzbbt ;
    /**
     * 预算分析
     */
    private List<HashMap<String,Object>> ysfx ;
    /**
     * 预算费用实际费用
     */
    private Yssjfy yssjfy;
    /**
     * 近三年预算支出
     */
    private List<HashMap<String,Object>> yszc ;
    /**
     * 预算统计
     */
    private Ystj ystj;
    /**
     * 6个子大屏
     */
    private List<HashMap<String,Object>> zdp ;
    /**
     * 整体概况
     */
    private Ztgk ztgk;


}

