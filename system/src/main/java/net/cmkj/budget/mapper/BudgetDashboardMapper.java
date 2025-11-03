package net.cmkj.budget.mapper;

import net.cmkj.budget.domain.dashboard.*;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName BudgetDashboardMapper
 * @Description T
 * @Author @hanjianghui
 * @Date 2025/6/13 16:04
 **/
@Mapper
public interface BudgetDashboardMapper {
    BigDecimal getYgcb(Map<String, Long> params);

    BigDecimal getLdbhfy(Map<String, Long> params);

    BigDecimal getAqsc(Map<String, Long> params);

    BigDecimal getRcscjy(Map<String, Long> params);

    BigDecimal getYwtz(Map<String, Long> params);

    BigDecimal getZctz(Map<String, Long> params);

    BigDecimal getQtkk(Map<String, Long> params);

    BigDecimal getQtkkcf(Map<String, Long> params);

    // 没有用到
    BigDecimal getQt(Map<String, Long> params);

    List<Company> getCompany();

    // 没有用到
    BigDecimal getCalculateTotalMoney1(Map<String, Long> params);

    // 没有用到
    BigDecimal getCalculateTotalMoney2(Map<String, Long> params);

    BigDecimal getCalculateActualCost(Map<String, Long> params);

    //获取员工成本实际花销 没有用到
    BigDecimal getYgcbsjfy(Map<String, Long> params);

    // 预算统计动态预算 没有用到
    List<YstjResultType> getYstjdtys(Map<String, Long> params);

    // 没有用到
    List<YstjResultType> getYstjgdys(Map<String, Long> params);

    /** 预算统计-工资*/
    List<YstjResultType> getYstjgz(Map<String, Long> params);
    /** 预算统计-劳动保护*/
    List<YstjResultType> getYstjldbh(Map<String, Long> params);
    /** 预算统计-资产投资*/
    List<YstjResultType> getYstjzctz(Map<String, Long> params);
    /** 预算统计-安全生产*/
    List<YstjResultType> getYstjaqsc(Map<String, Long> params);
    /** 预算统计-业务拓展*/
    List<YstjResultType> getYstjywtz(Map<String, Long> params);
    /** 预算统计-日常生产*/
    List<YstjResultType> getYstjrcsc(Map<String, Long> params);
    /** 预算统计-其他*/
    List<YstjResultType> getYstjqt(Map<String, Long> params);
    /** 预算统计-实际费用 按照部门划分*/
    List<YstjResultType> actual_cost_by_dept(Map<String, Long> params);

    List<SjfyResultType> getfydbsjfy(Map<String, Long> params);

    // SjfyResultType fydb_sjfy_by_type(Map<String, Long> params);



    // 获取去年任务id
    Long getPreviousYearTaskId(Map<String, Long> params);

    BigDecimal getActualCostByYear(Map<String, Long> params);

    List<zdpjefxResultType> getActualCostByMonth(Map<String, Long> params);

    BigDecimal getCalculateActualCostLastYear(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_wage_statistics_by_dept(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_Zctz_by_dept(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_Ywtz_by_dept(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_Aqsc_by_dept(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_Rcsc_by_dept(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_Qtkk_by_dept(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_wage_statistics_by_company(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_Zctz_by_company(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_Aqsc_by_company(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_Ywtz_by_company(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_Rcsc_by_company(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_Qtkk_by_company(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_actual_cost_ygcb_table(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_actual_cost_zctz_table(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_actual_cost_aqsc_table(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_actual_cost_ywtz_table(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_actual_cost_rcsc_table(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_actual_cost_qt_table(Map<String, Long> params);

    List<LinkedHashMap<String, Object>> get_actual_cost_quarterly(Map<String, Long> params);

    List<TableDetailVO> get_actual_cost_table_detail(Map<String, Object> params);
}
