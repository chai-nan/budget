package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.cmkj.budget.domain.dashboard.QueryTableDetailDTO;
import net.cmkj.budget.domain.ReportingTask;
import net.cmkj.budget.domain.dashboard.*;
import net.cmkj.budget.mapper.BudgetDashboardMapper;
import net.cmkj.budget.mapper.ReportingTaskMapper;
import net.cmkj.budget.service.IBudgetDashboardDataService;
import net.cmkj.budget.service.IReportingTaskService;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysDept;
import net.cmkj.common.core.domain.entity.SysUser;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @FileName BudgetDashboardDataServiceImpl
 * @Description 大屏数据服务层实现类
 * @Author @hanjianghui
 * @Date 2025/6/13 14:28
 **/
@Service
@Slf4j
public class BudgetDashboardDataServiceImpl extends ServiceImpl<ReportingTaskMapper, ReportingTask> implements IBudgetDashboardDataService {

    @Autowired
    BudgetDashboardMapper budgetDashboardMapper;

    @Autowired
    private IReportingTaskService taskService;

    @Autowired
    private ISysDeptService deptService;

    @Override
    public Data getData(Long taskId, Long deptId) {

        BigDecimal divisor = new BigDecimal("10000");

        // 通过taskId 获取当前任务信息
        ReportingTask task = taskService.selectReportingTaskById(taskId);
        // 获取当前任务的年份
        Integer currentYear = task.getBudgetYear();

        // 准备参数
        Map<String, Long> params = new HashMap<>();
        params.put("taskId", taskId);
        params.put("deptId", deptId);


        // 预算统计
        Ystj ystj = new Ystj();

        // 预算统计-区域名称
        List<String> areaNames = new ArrayList<>();
        // 预算统计-预算费用
        List<BigDecimal> ystj_budget_List = new ArrayList<>();
        // 预算统计-实际费用
        List<BigDecimal> ystj_actual_List = new ArrayList<>();

        // 获取预算统计-实际费用
        List<YstjResultType> ystj_actual_cost = budgetDashboardMapper.actual_cost_by_dept(params);
//        if (ystj_actual_cost != null && !ystj_actual_cost.isEmpty()) {
//            ystj_actual_cost.forEach(total -> ystj_actual_List.add(total.getTotal().divide(divisor, 2, RoundingMode.HALF_UP)));
//        }

        List<YstjResultType> Ystjgz = budgetDashboardMapper.getYstjgz(params);
        List<YstjResultType> Ystjldbh = budgetDashboardMapper.getYstjldbh(params);
        List<YstjResultType> Ystjzctz = budgetDashboardMapper.getYstjzctz(params);
        List<YstjResultType> Ystjaqsc = budgetDashboardMapper.getYstjaqsc(params);
        List<YstjResultType> Ystjywtz = budgetDashboardMapper.getYstjywtz(params);
        List<YstjResultType> Ystjrcsc = budgetDashboardMapper.getYstjrcsc(params);
        List<YstjResultType> Ystjqt = budgetDashboardMapper.getYstjqt(params);

        // 合并所有列表
        List<YstjResultType> mergedList = new ArrayList<>();
        mergedList.addAll(Ystjgz);
        mergedList.addAll(Ystjldbh);
        mergedList.addAll(Ystjzctz);
        mergedList.addAll(Ystjaqsc);
        mergedList.addAll(Ystjywtz);
        mergedList.addAll(Ystjrcsc);
        mergedList.addAll(Ystjqt);
        Map<String, BigDecimal> result = mergedList.stream()
                .filter(item -> item.getAreaName() != null && item.getTotal() != null) // 过滤空值
                .sorted(Comparator.comparingLong(YstjResultType::getId)) // 按id排序
                .collect(Collectors.groupingBy(
                        YstjResultType::getAreaName, // 按id分组
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                YstjResultType::getTotal,
                                BigDecimal::add
                        )
                ));
        // 生成一个与result同结构的列表，默认值为0，再用ystj_actual_cost中匹配的areaName对应的数值覆盖掉对应位置的0
        // 创建一个以result结构为模板的Map，初始值为0
        Map<String, BigDecimal> actualCostMap = new HashMap<>();
        result.keySet().forEach(areaName -> actualCostMap.put(areaName, BigDecimal.ZERO));

        // 用ystj_actual_cost中的实际值替换对应位置的0
        if (ystj_actual_cost != null && !ystj_actual_cost.isEmpty()) {
            ystj_actual_cost.forEach(item -> {
                if (actualCostMap.containsKey(item.getAreaName())) {
                    actualCostMap.put(item.getAreaName(), item.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
            });
        }
    
        result.forEach((areaName, total) -> {
            areaNames.add(areaName);
            ystj_budget_List.add(total.divide(divisor, 2, RoundingMode.HALF_UP));
            ystj_actual_List.add(actualCostMap.get(areaName));
        });

        // 预算统计-区域
        ystj.setArea(areaNames);
        // 预算统计-预算费用
        ystj.setYsfy(ystj_budget_List);
        // 预算统计-实际费用
        ystj.setSjfy(ystj_actual_List);


        // 获取与员工成本相关费用 员工成本就是工资
        // BigDecimal ygcb = BigDecimal.valueOf(436084700.00 + 1154855.00);
        BigDecimal ygcb = budgetDashboardMapper.getYgcb(params).divide(divisor, 2,
                RoundingMode.HALF_UP).add(budgetDashboardMapper.getLdbhfy(params).divide(divisor, 2,
                RoundingMode.HALF_UP));
        // 员工成本-去除劳动保护 劳动保护在动态预算里面算过了 算全部预算的时候去除
        // BigDecimal ygcb1 = budgetDashboardMapper.getYgcb(params).divide(divisor, 2, RoundingMode.HALF_UP);
        // 获取与资产投资相关费用
        // BigDecimal zctz = BigDecimal.valueOf(141814763.60);
        BigDecimal zctz = budgetDashboardMapper.getZctz(params).divide(divisor, 2, RoundingMode.HALF_UP);
        // 获取与安全生产相关费用
        // BigDecimal aqsc = BigDecimal.valueOf(81198838.32);
        BigDecimal aqsc = budgetDashboardMapper.getAqsc(params).divide(divisor, 2, RoundingMode.HALF_UP);
        // 获取与业务拓展相关费用
        // BigDecimal ywtz = BigDecimal.valueOf(9124587.00);
        BigDecimal ywtz = budgetDashboardMapper.getYwtz(params).divide(divisor, 2, RoundingMode.HALF_UP);
        // 获取与日常生产经营相关费用
        // BigDecimal rcsc = BigDecimal.valueOf(57676871.87);
        BigDecimal rcsc = budgetDashboardMapper.getRcscjy(params).divide(divisor, 2, RoundingMode.HALF_UP);
        // 获取其他可控费用
        // BigDecimal qtkk = BigDecimal.valueOf(160063745.57);
        // 其他可控
        BigDecimal qtkk = budgetDashboardMapper.getQtkk(params).divide(divisor, 2, RoundingMode.HALF_UP);
        // 其他可控-车费
        BigDecimal qtkkcf = budgetDashboardMapper.getQtkkcf(params).divide(divisor, 2, RoundingMode.HALF_UP);
        // 其他可控-资本性支出
        // BigDecimal qtkkzbxzc = budgetDashboardMapper.getQt(params).divide(divisor, 2, RoundingMode.HALF_UP);

        // 其他
        BigDecimal qt = qtkk.add(qtkkcf);
        // 今年 预算费用
        BigDecimal ze = ygcb.add(zctz).add(aqsc).add(ywtz).add(rcsc).add(qt);


        // 整体概况
        Ztgk ztgk = new Ztgk();
        // 预算金额
        // 动态表预算金额
        // BigDecimal dynamicBudgetMoney =
        //        budgetDashboardMapper.getCalculateTotalMoney1(params).divide(divisor, 2, RoundingMode.HALF_UP);
        // 固定表预算金额
        // BigDecimal fixedBudgetMoney = budgetDashboardMapper.getCalculateTotalMoney2(params).divide(divisor, 2,
        //        RoundingMode.HALF_UP);
        // BigDecimal budgetMoney = dynamicBudgetMoney.add(fixedBudgetMoney).add(ygcb1);

        ztgk.setYsje(ze);


        // 今年 实际费用
        Map<String, Long> params_currentYear_sjfy = new HashMap<>();
        params_currentYear_sjfy.put("taskId", taskId);
        params_currentYear_sjfy.put("gap", 0L);
        params_currentYear_sjfy.put("deptId", deptId);
        BigDecimal actualCostMoney = budgetDashboardMapper.getCalculateActualCost(params_currentYear_sjfy).divide(divisor, 2,
                RoundingMode.HALF_UP);
        ztgk.setSjfy(actualCostMoney);

        // 去年 实际费用
        Map<String, Long> params_lastYear_qnsjfy = new HashMap<>();
        params_lastYear_qnsjfy.put("taskId", taskId);
        params_lastYear_qnsjfy.put("gap", 1L);
        params_lastYear_qnsjfy.put("deptId", deptId);
        BigDecimal actualCostMoneyLastYear =
                budgetDashboardMapper.getCalculateActualCost(params_lastYear_qnsjfy).divide(divisor, 2,
                        RoundingMode.HALF_UP);

        // 前年 实际费用
        Map<String, Long> params_beforeYear_jnsjfy = new HashMap<>();
        params_beforeYear_jnsjfy.put("taskId", taskId);
        params_beforeYear_jnsjfy.put("gap", 2L);
        params_beforeYear_jnsjfy.put("deptId", deptId);
        BigDecimal actualCostMoneyBeforeLastYear =
                budgetDashboardMapper.getCalculateActualCost(params_beforeYear_jnsjfy).divide(divisor, 2,
                        RoundingMode.HALF_UP);


        // 去年金额
        // 获取去年计划id
        Map<String, Long> previousYearTaskParams = new HashMap<>();
        previousYearTaskParams.put("taskId", taskId);
        previousYearTaskParams.put("gap", 1L);
        Long previousYearTaskId = budgetDashboardMapper.getPreviousYearTaskId(previousYearTaskParams);

        Map<String, Long> previousYearParams = new HashMap<>();
        previousYearParams.put("taskId", previousYearTaskId);
        previousYearParams.put("deptId", deptId);

        // 去年预算金额
        BigDecimal previousYearYgcb = BigDecimal.ZERO;
        BigDecimal previousYearZctz = BigDecimal.ZERO;
        BigDecimal previousYearAqsc = BigDecimal.ZERO;
        BigDecimal previousYearYwtz = BigDecimal.ZERO;
        BigDecimal previousYearRcsc = BigDecimal.ZERO;
        BigDecimal previousYearQtkk = BigDecimal.ZERO;
        BigDecimal previousYearQtkkcf = BigDecimal.ZERO;
        BigDecimal previousYearQt = BigDecimal.ZERO;
        BigDecimal qnje = BigDecimal.ZERO;
        if (previousYearTaskId != null) {
            // 获取与员工成本相关费用 员工成本就是工资
            previousYearYgcb = budgetDashboardMapper.getYgcb(previousYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP).add(budgetDashboardMapper.getLdbhfy(previousYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP));
            // 获取与资产投资相关费用
            previousYearZctz = budgetDashboardMapper.getZctz(previousYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
            // 获取与安全生产相关费用
            previousYearAqsc = budgetDashboardMapper.getAqsc(previousYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
            // 获取与业务拓展相关费用
            previousYearYwtz = budgetDashboardMapper.getYwtz(previousYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
            // 获取与日常生产经营相关费用
            previousYearRcsc = budgetDashboardMapper.getRcscjy(previousYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
            // 获取其他可控费用
            // 其他可控
            previousYearQtkk = budgetDashboardMapper.getQtkk(previousYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
            // 其他可控-车费
            previousYearQtkkcf = budgetDashboardMapper.getQtkkcf(previousYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
            // 其他
            previousYearQt = previousYearQtkk.add(previousYearQtkkcf);
            // 去年 预算金额
            qnje = previousYearYgcb.add(previousYearZctz).add(previousYearAqsc).add(previousYearYwtz).add(previousYearRcsc).add(previousYearQt);
        }

        // 前年 预算金额
        // 获取前年计划id
        Map<String, Long> beforeLastYearTaskParams = new HashMap<>();
        beforeLastYearTaskParams.put("taskId", taskId);
        beforeLastYearTaskParams.put("gap", 2L);
        Long beforeLastYearTaskId = budgetDashboardMapper.getPreviousYearTaskId(beforeLastYearTaskParams);

        Map<String, Long> beforeLastYearParams = new HashMap<>();
        beforeLastYearParams.put("taskId", beforeLastYearTaskId);
        beforeLastYearParams.put("deptId", deptId);

        // 前年 预算金额
        BigDecimal beforeLastYearYgcb = BigDecimal.ZERO;
        BigDecimal beforeLastYearZctz = BigDecimal.ZERO;
        BigDecimal beforeLastYearAqsc = BigDecimal.ZERO;
        BigDecimal beforeLastYearYwtz = BigDecimal.ZERO;
        BigDecimal beforeLastYearRcsc = BigDecimal.ZERO;
        BigDecimal beforeLastYearQtkk = BigDecimal.ZERO;
        BigDecimal beforeLastYearQtkkcf = BigDecimal.ZERO;
        BigDecimal beforeLastYearQt = BigDecimal.ZERO;
        BigDecimal beforeLastYeaje = BigDecimal.ZERO;
        if (beforeLastYearTaskId != null) {

            // 获取与员工成本相关费用 员工成本就是工资
            beforeLastYearYgcb = budgetDashboardMapper.getYgcb(beforeLastYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP).add(budgetDashboardMapper.getLdbhfy(beforeLastYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP));
            // 获取与资产投资相关费用
            beforeLastYearZctz = budgetDashboardMapper.getZctz(beforeLastYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
            // 获取与安全生产相关费用
            beforeLastYearAqsc = budgetDashboardMapper.getAqsc(beforeLastYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
            // 获取与业务拓展相关费用
            beforeLastYearYwtz = budgetDashboardMapper.getYwtz(beforeLastYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
            // 获取与日常生产经营相关费用
            beforeLastYearRcsc = budgetDashboardMapper.getRcscjy(beforeLastYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
            // 获取其他可控费用
            // 其他可控
            beforeLastYearQtkk = budgetDashboardMapper.getQtkk(beforeLastYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
            // 其他可控-车费
            beforeLastYearQtkkcf = budgetDashboardMapper.getQtkkcf(beforeLastYearParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
            // 其他
            beforeLastYearQt = beforeLastYearQtkk.add(beforeLastYearQtkkcf);
            // 前年 预算金额
            beforeLastYeaje =
                    beforeLastYearYgcb.add(beforeLastYearZctz).add(beforeLastYearAqsc).add(beforeLastYearYwtz).add(beforeLastYearRcsc).add(beforeLastYearQt);
        }

        List<HashMap<String, Object>> yszcList = new ArrayList<>();

        // 设置年份
        List<Integer> years = new ArrayList<>();
        years.add(currentYear);
        years.add(currentYear - 1);
        years.add(currentYear - 2);
        HashMap<String, Object> mapYears = new HashMap<>();
        mapYears.put("years", years);
        yszcList.add(mapYears);

        HashMap<String, Object> map01 = new HashMap<>();
        map01.put("value", ze);
        map01.put("name", "今年预算总额");
        yszcList.add(map01);

        HashMap<String, Object> map02 = new HashMap<>();
        map02.put("value", qnje);
        map02.put("name", "去年预算总额");
        yszcList.add(map02);

        HashMap<String, Object> map03 = new HashMap<>();
        map03.put("value", beforeLastYeaje);
        map03.put("name", "前年预算总额");
        yszcList.add(map03);

        HashMap<String, Object> map04 = new HashMap<>();
        map04.put("value", actualCostMoney);
        map04.put("name", "今年实际总额");
        yszcList.add(map04);

        HashMap<String, Object> map05 = new HashMap<>();
        map05.put("value", actualCostMoneyLastYear);
        map05.put("name", "去年实际总额");
        yszcList.add(map05);

        HashMap<String, Object> map06 = new HashMap<>();
        map06.put("value", actualCostMoneyBeforeLastYear);
        map06.put("name", "前年实际总额");
        yszcList.add(map06);




        // 预算费用实际费用
        Yssjfy yssjfy = new Yssjfy();
        // List<SjfyResultType> fy = budgetDashboardMapper.getfydbsjfy(params);
        // log.info("实际费用:{}", fy);
        // type
        List<String> yssjfytType = new ArrayList<>();
        yssjfytType.add("员工成本");
        yssjfytType.add("资产投资");
        yssjfytType.add("安全生产");
        yssjfytType.add("业务拓展");
        yssjfytType.add("日常生产");
        yssjfytType.add("其他可控");
        yssjfy.setType(yssjfytType);
        // 今年 预算费用
        List<BigDecimal> ysfyList = new ArrayList<>();
        ysfyList.add(ygcb);
        ysfyList.add(zctz);
        ysfyList.add(aqsc);
        ysfyList.add(ywtz);
        ysfyList.add(rcsc);
        ysfyList.add(qt);
        yssjfy.setCurrentYearYsfy(ysfyList);

        // 去年 预算费用
        List<BigDecimal> qnysfyList = new ArrayList<>();
        qnysfyList.add(previousYearYgcb);
        qnysfyList.add(previousYearZctz);
        qnysfyList.add(previousYearAqsc);
        qnysfyList.add(previousYearYwtz);
        qnysfyList.add(previousYearRcsc);
        qnysfyList.add(previousYearQt);
        yssjfy.setLastYearYsfy(qnysfyList);



        // 实际费用
        List<BigDecimal> sjfyList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            sjfyList.add(BigDecimal.ZERO); // 初始化为 BigDecimal.ZERO
        }

        // 今年实际费用
        BigDecimal sjfy_ygcb = BigDecimal.ZERO;
        BigDecimal sjfy_zctz = BigDecimal.ZERO;
        BigDecimal sjfy_aqsc = BigDecimal.ZERO;
        BigDecimal sjfy_ywtz = BigDecimal.ZERO;
        BigDecimal sjfy_rcsc = BigDecimal.ZERO;
        BigDecimal sjfy_qt = BigDecimal.ZERO;
        Map<String, Long> params_sjfy = new HashMap<>();
        params_sjfy.put("taskId", taskId);
        params_sjfy.put("gap", 0L);
        params_sjfy.put("deptId", deptId);
        List<SjfyResultType> sjfyResultTypes = budgetDashboardMapper.getfydbsjfy(params_sjfy);
        for (SjfyResultType sjfyResultType : sjfyResultTypes) {
            switch (sjfyResultType.getItemType()) {
                case "与员工成本相关的费用":
                    sjfy_ygcb = sjfyResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP);
                    sjfyList.set(0, sjfy_ygcb);
                    break;
                case "与资产投资相关费用":
                    sjfy_zctz = sjfyResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP);
                    sjfyList.set(1, sjfy_zctz);
                    break;
                case "与安全生产相关费用":
                    sjfy_aqsc = sjfyResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP);
                    sjfyList.set(2, sjfy_aqsc);
                    break;
                case "与业务拓展相关的费用":
                    sjfy_ywtz = sjfyResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP);
                    sjfyList.set(3, sjfy_ywtz);
                    break;
                case "与日常生产经营相关的费用":
                    sjfy_rcsc = sjfyResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP);
                    sjfyList.set(4, sjfy_rcsc);
                    break;
                case "其他可控费用":
                    sjfy_qt = sjfyResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP);
                    sjfyList.set(5, sjfy_qt);
                    break;
            }
        }

        yssjfy.setCurrentYearSjfy(sjfyList);

        // 去年实际费用
        Map<String, Long> params_sjfy_qn = new HashMap<>();
        params_sjfy_qn.put("taskId", taskId);
        params_sjfy_qn.put("gap", 1L);
        params_sjfy_qn.put("deptId", deptId);
        List<SjfyResultType> sjfyResultTypes_qn = budgetDashboardMapper.getfydbsjfy(params_sjfy_qn);

        // 创建新的列表存储去年实际费用数据
        List<BigDecimal> lastYearSjfyList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            lastYearSjfyList.add(BigDecimal.ZERO); // 初始化为 BigDecimal.ZERO
        }

        BigDecimal sjfy_ygcb_qn = BigDecimal.ZERO;
        BigDecimal sjfy_zctz_qn = BigDecimal.ZERO;
        BigDecimal sjfy_aqsc_qn = BigDecimal.ZERO;
        BigDecimal sjfy_ywtz_qn = BigDecimal.ZERO;
        BigDecimal sjfy_rcsc_qn = BigDecimal.ZERO;
        BigDecimal sjfy_qt_qn = BigDecimal.ZERO;

        for (SjfyResultType sjfyResultType : sjfyResultTypes_qn) {
            switch (sjfyResultType.getItemType()) {
                case "与员工成本相关的费用":
                    sjfy_ygcb_qn = sjfyResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP);
                    lastYearSjfyList.set(0, sjfy_ygcb_qn);
                    break;
                case "与资产投资相关费用":
                    sjfy_zctz_qn = sjfyResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP);
                    lastYearSjfyList.set(1, sjfy_zctz_qn);
                    break;
                case "与安全生产相关费用":
                    sjfy_aqsc_qn = sjfyResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP);
                    lastYearSjfyList.set(2, sjfy_aqsc_qn);
                    break;
                case "与业务拓展相关的费用":
                    sjfy_ywtz_qn = sjfyResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP);
                    lastYearSjfyList.set(3, sjfy_ywtz_qn);
                    break;
                case "与日常生产经营相关的费用":
                    sjfy_rcsc_qn = sjfyResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP);
                    lastYearSjfyList.set(4, sjfy_rcsc_qn);
                    break;
                case "其他可控费用":
                    sjfy_qt_qn = sjfyResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP);
                    lastYearSjfyList.set(5, sjfy_qt_qn);
                    break;
            }
        }

        yssjfy.setLastYearSjfy(lastYearSjfyList);


        // 预算分析

        List<HashMap<String, Object>> ysfxList = new ArrayList<>();
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("value", ygcb);
        map1.put("name", "与员工成本相关费用");
        ysfxList.add(map1);


        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("value", zctz);
        map2.put("name", "与资产投资相关费用");
        ysfxList.add(map2);

        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("value", aqsc);
        map3.put("name", "与安全生产相关费用");
        ysfxList.add(map3);

        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("value", ywtz);
        map4.put("name", "与业务拓展相关费用");
        ysfxList.add(map4);

        HashMap<String, Object> map5 = new HashMap<>();
        map5.put("value", rcsc);
        map5.put("name", "日常生产经营");
        ysfxList.add(map5);

        HashMap<String, Object> map6 = new HashMap<>();
        map6.put("value", qt);
        map6.put("name", "其他");
        ysfxList.add(map6);


        // 子大屏

        List<HashMap<String, Object>> zdpList = new ArrayList<>();
        HashMap<String, Object> map11 = new HashMap<>();
        map11.put("value", sjfy_ygcb);
        map11.put("name", "员工成本");
        map11.put("id", 1);
        zdpList.add(map11);


        HashMap<String, Object> map12 = new HashMap<>();
        map12.put("value", sjfy_zctz);
        map12.put("name", "资产投资");
        map12.put("id", 2);
        zdpList.add(map12);

        HashMap<String, Object> map13 = new HashMap<>();
        map13.put("value", sjfy_aqsc);
        map13.put("name", "安全生产");
        map13.put("id", 3);
        zdpList.add(map13);

        HashMap<String, Object> map14 = new HashMap<>();
        map14.put("value", sjfy_ywtz);
        map14.put("name", "业务拓展");
        map14.put("id", 4);
        zdpList.add(map14);

        HashMap<String, Object> map15 = new HashMap<>();
        map15.put("value", sjfy_rcsc);
        map15.put("name", "日常生产");
        map15.put("id", 5);
        zdpList.add(map15);

        HashMap<String, Object> map16 = new HashMap<>();
        map16.put("value", sjfy_qt);
        map16.put("name", "其他");
        map16.put("id", 6);
        zdpList.add(map16);


        List<HashMap<String, Object>> lxzbbtList = new ArrayList<>();
        HashMap<String, Object> map21 = new HashMap<>();
        map21.put("value", ygcb.add(zctz).add(aqsc).add(ywtz).add(rcsc));
        map21.put("name", "总额");
        lxzbbtList.add(map21);


        HashMap<String, Object> map22 = new HashMap<>();
        map22.put("value", qt);
        map22.put("name", "其他");
        lxzbbtList.add(map22);


        //支出同比
        List<HashMap<String, Object>> zctbList = new ArrayList<>();
        HashMap<String, Object> map8 = new HashMap<>();
        map8.put("value", actualCostMoney);
        map8.put("name", "今年总额");
        zctbList.add(map8);

        HashMap<String, Object> map9 = new HashMap<>();
        map9.put("value", actualCostMoneyLastYear);
        map9.put("name", "去年总额");
        zctbList.add(map9);


        //

        Data data = new Data();
        data.setZtgk(ztgk);
        data.setYszc(yszcList);
        data.setYsfx(ysfxList);
        data.setLxzbbt(lxzbbtList);
        data.setZdp(zdpList);
        data.setYstj(ystj);
        data.setYssjfy(yssjfy);
        return data;
    }

    @Override
    public AjaxResult getCompanyAndTask() {
        try {
            CompanyAndTask companyAndTask = new CompanyAndTask();
            List<Company> companyList = budgetDashboardMapper.getCompany();
            if (SysUser.isAdmin(SecurityUtils.getUserId())) {
                companyAndTask.setCompanys(companyList);
            } else {
                SysDept dept = deptService.selectDeptById(SecurityUtils.getDeptId());
                // 筛选出companyList中id为当前用户部门的parentId的数据
                companyAndTask.setCompanys(companyList.stream().filter(company -> String.valueOf(company.getId()).equals(String.valueOf(dept.getParentId()))).collect(Collectors.toList()));
            }
            ReportingTask reportingTask = new ReportingTask();
            List<ReportingTask> reportingTasks = baseMapper.selectReportingTaskList(reportingTask);
            List<Task> taskList = new ArrayList<>();
            for (ReportingTask task : reportingTasks) {
                Task task1 = new Task();
                task1.setId(task.getId());
                task1.setName(task.getName());
                taskList.add(task1);
            }
            companyAndTask.setTasks(taskList);
            return AjaxResult.success("获取成功", companyAndTask);
        } catch (Exception e) {
            log.error("获取失败公司和任务失败：" + e.getMessage(), e);
            return AjaxResult.error("获取失败", new CompanyAndTask());
        }
    }

    @Override
    public Ygcbzdp getZdpData(Long taskId, Long deptId, Long type) {
        if (type == 1L) {
            type = 53L;//员工成本
        } else if (type == 2L) {
            type = 61L;//资产投资
        } else if (type == 3L) {
            type = 62L;//安全生产
        } else if (type == 4L) {
            type = 63L;//业务拓展
        } else if (type == 5L) {
            type = 60L;//日常生产
        } else if (type == 6L) {
            type = 59L;//其他
        }
        Long deptId1 = SecurityUtils.getDeptId();
        if (deptId == null) {
            deptId = deptId1;
        }
        BigDecimal divisor = new BigDecimal("10000");

        // 准备参数
        Map<String, Long> params = new HashMap<>();
        params.put("taskId", taskId);
        params.put("deptId", deptId);

        // 获取去年任务id
        Map<String, Long> previousYearTaskParams = new HashMap<>();
        previousYearTaskParams.put("taskId", taskId);
        previousYearTaskParams.put("gap", 1L);
        Long lastTaskId = budgetDashboardMapper.getPreviousYearTaskId(previousYearTaskParams);

        // 根据当前任务id获取当前任务
        ReportingTask currentTask = taskService.selectReportingTaskById(taskId);
        // 根据当前任务上一年的任务获取上一年任务
        ReportingTask previousTask = taskService.selectReportingTaskById(lastTaskId);

        // Top-2 当前实际发生金额（实际花费） byTypeCode 不用二次判断
        BigDecimal sjhf = BigDecimal.ZERO;
        Map<String, Long> currentsjhfParams = new HashMap<>();
        if (currentTask != null) {
            currentsjhfParams.put("budgetYear", currentTask.getBudgetYear() != null ?
                    currentTask.getBudgetYear().longValue() : 0L);
            currentsjhfParams.put("typeCode", type);
            currentsjhfParams.put("deptId", deptId);
            // 今年实际花费
            sjhf = budgetDashboardMapper.getActualCostByYear(currentsjhfParams).divide(divisor, 2,
                    RoundingMode.HALF_UP);
        }

        // Top-4 上一年实际发生金额（去年实际花费） byTypeCode 不用二次判断
        Map<String, Long> previoussjhfParams = new HashMap<>();

        previoussjhfParams.put("budgetYear", currentTask.getBudgetYear() != null ?
                currentTask.getBudgetYear().longValue() - 1L : 0L);
        previoussjhfParams.put("typeCode", type);
        previoussjhfParams.put("deptId", deptId);
        // 去年实际花费
        BigDecimal qnsjhf = budgetDashboardMapper.getActualCostByYear(previoussjhfParams).divide(divisor, 2,
                RoundingMode.HALF_UP);


        // Top-1 当前预算填报金额
        BigDecimal dqys = BigDecimal.ZERO;
        if (taskId != null) {
            if (type == 53L) {
                // 员工成本
                dqys = budgetDashboardMapper.getYgcb(params).divide(divisor, 2,
                        RoundingMode.HALF_UP).add(budgetDashboardMapper.getLdbhfy(params).divide(divisor, 2,
                        RoundingMode.HALF_UP));
            } else if (type == 61L) {
                //资产投资
                dqys = budgetDashboardMapper.getZctz(params).divide(divisor, 2, RoundingMode.HALF_UP);
            } else if (type == 62L) {
                //安全生产
                dqys = budgetDashboardMapper.getAqsc(params).divide(divisor, 2, RoundingMode.HALF_UP);
            } else if (type == 63L) {
                //业务拓展
                dqys = budgetDashboardMapper.getYwtz(params).divide(divisor, 2, RoundingMode.HALF_UP);
            } else if (type == 60L) {
                //日常生产
                dqys = budgetDashboardMapper.getRcscjy(params).divide(divisor, 2, RoundingMode.HALF_UP);
            } else if (type == 59L) {
                // 其他可控
                BigDecimal qtkk = budgetDashboardMapper.getQtkk(params).divide(divisor, 2, RoundingMode.HALF_UP);
                // 其他可控-车费
                BigDecimal qtkkcf = budgetDashboardMapper.getQtkkcf(params).divide(divisor, 2, RoundingMode.HALF_UP);
                dqys = qtkk.add(qtkkcf);
            }
        }

        // Top-3 上一年度预算金额（去年预算）
        Map<String, Long> previousYearParams = new HashMap<>();
        params.put("taskId", lastTaskId);
        params.put("deptId", deptId);

        BigDecimal qnys = BigDecimal.ZERO;
        if (lastTaskId != null) {
            if (type == 53L) {
                // 员工成本
                qnys = budgetDashboardMapper.getYgcb(previousYearParams).divide(divisor, 2,
                        RoundingMode.HALF_UP).add(budgetDashboardMapper.getLdbhfy(previousYearParams).divide(divisor, 2,
                        RoundingMode.HALF_UP));
            } else if (type == 61L) {
                //资产投资
                qnys = budgetDashboardMapper.getZctz(previousYearParams).divide(divisor, 2, RoundingMode.HALF_UP);
            } else if (type == 62L) {
                //安全生产
                qnys = budgetDashboardMapper.getAqsc(previousYearParams).divide(divisor, 2, RoundingMode.HALF_UP);
            } else if (type == 63L) {
                //业务拓展
                qnys = budgetDashboardMapper.getYwtz(previousYearParams).divide(divisor, 2, RoundingMode.HALF_UP);
            } else if (type == 60L) {
                //日常生产
                qnys = budgetDashboardMapper.getRcscjy(previousYearParams).divide(divisor, 2, RoundingMode.HALF_UP);
            } else if (type == 59L) {
                // 其他可控
                BigDecimal qtkk = budgetDashboardMapper.getQtkk(previousYearParams).divide(divisor, 2,
                        RoundingMode.HALF_UP);
                // 其他可控-车费
                BigDecimal qtkkcf = budgetDashboardMapper.getQtkkcf(previousYearParams).divide(divisor, 2,
                        RoundingMode.HALF_UP);
                qnys = qtkk.add(qtkkcf);
            }
        }

        // 顶部
        Top top = new Top();
        // 当前预算填报金额
        top.setDqystbje(dqys);
        // 当前实际发生金额
        top.setDqsjfsje(sjhf);
        // 上一年度预算金额
        top.setSyndysje(qnys);
        //上一年实际发生金额
        top.setSyndsjfsje(qnsjhf);


        Map<String, Long> actual_cost_quarterly_map = new HashMap<>();
        actual_cost_quarterly_map.put("taskId", taskId);
        actual_cost_quarterly_map.put("deptId", deptId);
        actual_cost_quarterly_map.put("type", type);

        List<LinkedHashMap<String, Object>> actual_cost_quarterly = budgetDashboardMapper.get_actual_cost_quarterly(actual_cost_quarterly_map);

        // 获取去年4个季度的实际金额

        List<BigDecimal> last_year_quarterly = new ArrayList<>();
        List<BigDecimal> current_year_quarterly = new ArrayList<>();

        // 循环遍历actual_cost_quarterly 将前四项的total放到last_year_quarterly中去 将后四项的total放到current_year_quarterly中去
        for (int i = 0; i < actual_cost_quarterly.size(); i++) {
            LinkedHashMap<String, Object> item = actual_cost_quarterly.get(i);
            Object total = item.get("total");
            BigDecimal totalValue;

            if (total instanceof BigDecimal) {
                totalValue = (BigDecimal) total;
            } else if (total != null) {
                totalValue = new BigDecimal(total.toString());
            } else {
                totalValue = BigDecimal.ZERO;
            }

            if (i < 4) {
                // 前四项放到last_year_quarterly中去
                last_year_quarterly.add(totalValue.divide(divisor, 2, RoundingMode.HALF_UP));
            } else if (i < 8) {
                // 后四项放到current_year_quarterly中去
                current_year_quarterly.add(totalValue.divide(divisor, 2, RoundingMode.HALF_UP));
            }
        }
        HashMap<String, Object> last_year_quarterly_map = new HashMap<>();
        last_year_quarterly_map.put("year", currentTask.getBudgetYear() - 1);
        last_year_quarterly_map.put("quarterly", last_year_quarterly);

        HashMap<String, Object> current_year_quarterly_map = new HashMap<>();
        current_year_quarterly_map.put("year", currentTask.getBudgetYear());
        current_year_quarterly_map.put("quarterly", current_year_quarterly);

        List<HashMap<String, Object>> last_current_year_quarterly = new ArrayList<>();
        last_current_year_quarterly.add(last_year_quarterly_map);
        last_current_year_quarterly.add(current_year_quarterly_map);



        List<HashMap<String, Object>> ysfxList = new ArrayList<>();
        HashMap<String, Object> map1 = new HashMap<>();

        // Top-1 当前预算填报金额
        map1.put("value", dqys);
        map1.put("name", "全年预算");
        ysfxList.add(map1);

        HashMap<String, Object> map2 = new HashMap<>();
        // Top-3 上一年度预算金额（去年预算）
        map2.put("value", qnys);
        map2.put("name", "上一年预算");
        ysfxList.add(map2);

        // 支出同比分析
/*        Zctbfx zctbfx = new Zctbfx();
        // 去年支出
        zctbfx.setSynzc(qnsjhf);
        // 本年度支出
        zctbfx.setBndzc(sjhf);*/

        List<HashMap<String, Object>> zctbfxList = new ArrayList<>();
        HashMap<String, Object> map21 = new HashMap<>();
        // Top-4 上一年实际发生金额（去年实际花费）
        map21.put("value", qnsjhf);
        map21.put("name", "上一年支出");
        zctbfxList.add(map21);

        HashMap<String, Object> map22 = new HashMap<>();
        // Top-2 当前实际发生金额（实际花费）
        map22.put("value", sjhf);
        map22.put("name", "本年度支出");
        zctbfxList.add(map22);

        // 预算支出进度占比 ysje zcje
        List<HashMap<String, Object>> yszcjdzbList = new ArrayList<>();
        HashMap<String, Object> map11 = new HashMap<>();
        // Top-1 当前预算填报金额
        map11.put("value", dqys);
        map11.put("name", "预算金额");
        yszcjdzbList.add(map11);

        HashMap<String, Object> map12 = new HashMap<>();
        // Top-2 当前实际发生金额（实际花费）
        map12.put("value", sjhf);
        map12.put("name", "支出金额");
        yszcjdzbList.add(map12);

        // 预算金额和支出金额分析 ysjezcjefx
        Ysjezcjefx ysjezcjefx = new Ysjezcjefx();

        List<String> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(String.format("%02d", i));
        }
        ysjezcjefx.setMonths(months);
        // 预算金额
        // ysjezcjefx.setYsje(new ArrayList<>());
        BigDecimal[] parts = splitAmount(dqys);
        ysjezcjefx.setYsje(parts);

        // 实际支出金额
        List<BigDecimal> zcjeList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            zcjeList.add(BigDecimal.ZERO); // 初始化为 BigDecimal.ZERO
        }
        Map<String, Long> currentsjhfParams1 = new HashMap<>();
        currentsjhfParams1.put("taskId", taskId);
        currentsjhfParams1.put("deptId", deptId);
        currentsjhfParams1.put("type", type);
        List<zdpjefxResultType> actualCostByMonth = budgetDashboardMapper.getActualCostByMonth(currentsjhfParams1);
        if (actualCostByMonth != null && !actualCostByMonth.isEmpty()) {

            for (zdpjefxResultType zdpjefxResultType : actualCostByMonth) {
                if (zdpjefxResultType.getMonth().equals("01")) {
                    zcjeList.set(0, zdpjefxResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
                if (zdpjefxResultType.getMonth().equals("02")) {
                    zcjeList.set(1, zdpjefxResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
                if (zdpjefxResultType.getMonth().equals("03")) {
                    zcjeList.set(2, zdpjefxResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
                if (zdpjefxResultType.getMonth().equals("04")) {
                    zcjeList.set(3, zdpjefxResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
                if (zdpjefxResultType.getMonth().equals("05")) {
                    zcjeList.set(4, zdpjefxResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
                if (zdpjefxResultType.getMonth().equals("06")) {
                    zcjeList.set(5, zdpjefxResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
                if (zdpjefxResultType.getMonth().equals("07")) {
                    zcjeList.set(6, zdpjefxResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
                if (zdpjefxResultType.getMonth().equals("08")) {
                    zcjeList.set(7, zdpjefxResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
                if (zdpjefxResultType.getMonth().equals("09")) {
                    zcjeList.set(8, zdpjefxResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
                if (zdpjefxResultType.getMonth().equals("10")) {
                    zcjeList.set(9, zdpjefxResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
                if (zdpjefxResultType.getMonth().equals("11")) {
                    zcjeList.set(10, zdpjefxResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
                if (zdpjefxResultType.getMonth().equals("12")) {
                    zcjeList.set(11, zdpjefxResultType.getTotal().divide(divisor, 2, RoundingMode.HALF_UP));
                }
            }

        }
        ysjezcjefx.setZcje(zcjeList);
        Map<String, Long> params2 = new HashMap<>();
        params2.put("taskId", taskId);
        params2.put("deptId", deptId);
        // Table
        Tables tables = new Tables();
        if (type == 53L) {//员工
            //子大屏table(员工成本)
            List<LinkedHashMap<String, Object>> table = new ArrayList<>();
            table = budgetDashboardMapper.get_actual_cost_ygcb_table(params2);
            // 1. 处理表头（keys）
            List<String> headers = new ArrayList<>();
            if (!table.isEmpty()) {
                headers.addAll(table.get(0).keySet()); // 获取第一个map的所有key作为表头
            }
            // 2. 处理数据（values）
            Object[][] data = new Object[table.size()][headers.size()]; // 创建二维数组
            // 填充数据
            for (int row = 0; row < table.size(); row++) {
                HashMap<String, Object> map = table.get(row);
                for (int col = 0; col < headers.size(); col++) {
                    data[row][col] = map.get(headers.get(col)); // 确保value顺序与header顺序一致
                }
            }
            tables.setNames(headers);
            tables.setValus(data);
        } else if (type == 61L) {
            //子大屏table(资产投资)
            List<LinkedHashMap<String, Object>> table2 = new ArrayList<>();
            table2 = budgetDashboardMapper.get_actual_cost_zctz_table(params2);
            // 1. 处理表头（keys）
            List<String> headers2 = new ArrayList<>();
            if (!table2.isEmpty()) {
                headers2.addAll(table2.get(0).keySet()); // 获取第一个map的所有key作为表头
            }
            // 2. 处理数据（values）
            Object[][] data2 = new Object[table2.size()][headers2.size()]; // 创建二维数组
            // 填充数据
            for (int row = 0; row < table2.size(); row++) {
                HashMap<String, Object> map = table2.get(row);
                for (int col = 0; col < headers2.size(); col++) {
                    data2[row][col] = map.get(headers2.get(col)); // 确保value顺序与header顺序一致
                }
            }

            tables.setNames(headers2);
            tables.setValus(data2);
        } else if (type == 63L) {

            //子大屏table(业务拓展)
            List<LinkedHashMap<String, Object>> table2 = new ArrayList<>();
            table2 = budgetDashboardMapper.get_actual_cost_ywtz_table(params2);
            // 1. 处理表头（keys）
            List<String> headers2 = new ArrayList<>();
            if (!table2.isEmpty()) {
                headers2.addAll(table2.get(0).keySet()); // 获取第一个map的所有key作为表头
            }
            // 2. 处理数据（values）
            Object[][] data2 = new Object[table2.size()][headers2.size()]; // 创建二维数组
            // 填充数据
            for (int row = 0; row < table2.size(); row++) {
                HashMap<String, Object> map = table2.get(row);
                for (int col = 0; col < headers2.size(); col++) {
                    data2[row][col] = map.get(headers2.get(col)); // 确保value顺序与header顺序一致
                }
            }

            tables.setNames(headers2);
            tables.setValus(data2);
        } else if (type == 62L) {

            //子大屏table(安全生产)
            List<LinkedHashMap<String, Object>> table2 = new ArrayList<>();
            table2 = budgetDashboardMapper.get_actual_cost_aqsc_table(params2);
            // 1. 处理表头（keys）
            List<String> headers2 = new ArrayList<>();
            if (!table2.isEmpty()) {
                headers2.addAll(table2.get(0).keySet()); // 获取第一个map的所有key作为表头
            }
            // 2. 处理数据（values）
            Object[][] data2 = new Object[table2.size()][headers2.size()]; // 创建二维数组
            // 填充数据
            for (int row = 0; row < table2.size(); row++) {
                HashMap<String, Object> map = table2.get(row);
                for (int col = 0; col < headers2.size(); col++) {
                    data2[row][col] = map.get(headers2.get(col)); // 确保value顺序与header顺序一致
                }
            }

            tables.setNames(headers2);
            tables.setValus(data2);
        } else if (type == 60L) {

            //子大屏table(日常生产经营)
            List<LinkedHashMap<String, Object>> table2 = new ArrayList<>();
            table2 = budgetDashboardMapper.get_actual_cost_rcsc_table(params2);
            // 1. 处理表头（keys）
            List<String> headers2 = new ArrayList<>();
            if (!table2.isEmpty()) {
                headers2.addAll(table2.get(0).keySet()); // 获取第一个map的所有key作为表头
            }
            // 2. 处理数据（values）
            Object[][] data2 = new Object[table2.size()][headers2.size()]; // 创建二维数组
            // 填充数据
            for (int row = 0; row < table2.size(); row++) {
                HashMap<String, Object> map = table2.get(row);
                for (int col = 0; col < headers2.size(); col++) {
                    data2[row][col] = map.get(headers2.get(col)); // 确保value顺序与header顺序一致
                }
            }

            tables.setNames(headers2);
            tables.setValus(data2);
        } else if (type == 59L) {

            //子大屏table(其他)
            List<LinkedHashMap<String, Object>> table2 = new ArrayList<>();
            table2 = budgetDashboardMapper.get_actual_cost_qt_table(params2);
            // 1. 处理表头（keys）
            List<String> headers2 = new ArrayList<>();
            if (!table2.isEmpty()) {
                headers2.addAll(table2.get(0).keySet()); // 获取第一个map的所有key作为表头
            }
            // 2. 处理数据（values）
            Object[][] data2 = new Object[table2.size()][headers2.size()]; // 创建二维数组
            // 填充数据
            for (int row = 0; row < table2.size(); row++) {
                HashMap<String, Object> map = table2.get(row);
                for (int col = 0; col < headers2.size(); col++) {
                    data2[row][col] = map.get(headers2.get(col)); // 确保value顺序与header顺序一致
                }
            }

            tables.setNames(headers2);
            tables.setValus(data2);
        }


        Ygcbzdp ygcbzdp = new Ygcbzdp();
        ygcbzdp.setTables(tables);
        ygcbzdp.setTop(top);

        // 去年和本年四季实际费用
        ygcbzdp.setLastAndCurrentYearQuarterly(last_current_year_quarterly);

        //ygcbzdp.setYsfx(ygcbysfx);
        ygcbzdp.setYsfx(ysfxList);
        //ygcbzdp.setZctbfx(zctbfx);
        ygcbzdp.setZctbfx(zctbfxList);
        //ygcbzdp.setYszcjdzb(yszcjdzb);
        ygcbzdp.setYszcjdzb(yszcjdzbList);
        ygcbzdp.setYsjezcjefx(ysjezcjefx);

        return ygcbzdp;
    }

    @Override
    public List<TableDetailVO> getTableDetailInfo(QueryTableDetailDTO queryTableDetailDTO) {

        Map<String, Object> getTableDetailParams = new HashMap<>();
        getTableDetailParams.put("taskId", queryTableDetailDTO.getTaskId());
        getTableDetailParams.put("parentId", queryTableDetailDTO.getCompanyId());
        getTableDetailParams.put("deptName", queryTableDetailDTO.getDeptName());
        getTableDetailParams.put("subjectName", queryTableDetailDTO.getSubjectName());

        List<TableDetailVO> tableDetailVOList =
                budgetDashboardMapper.get_actual_cost_table_detail(getTableDetailParams);

        return tableDetailVOList;
    }


    public static BigDecimal[] splitAmount(BigDecimal total) {
        BigDecimal[] parts = new BigDecimal[12];

        // 计算每份基数（整数部分）
        BigDecimal base = total.divide(new BigDecimal("12"), 0, RoundingMode.DOWN);

        // 计算余数
        BigDecimal remainder = total.subtract(base.multiply(new BigDecimal("12")));

        // 填充前11份
        for (int i = 0; i < 11; i++) {
            parts[i] = base;
        }

        // 第12份 = 基数 + 余数
        parts[11] = base.add(remainder);

        return parts;
    }
}
