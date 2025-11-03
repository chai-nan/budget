package net.cmkj.budget.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import net.cmkj.budget.domain.*;
import net.cmkj.budget.mapper.BudgetDashboardMapper;
import net.cmkj.budget.mapper.BudgetItemDeptMapper;
import net.cmkj.budget.mapper.BudgetSubjectMapper;
import net.cmkj.budget.mapper.BudgetWarningMapper;
import net.cmkj.budget.service.*;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysDept;
import net.cmkj.common.utils.DateUtils;
import net.cmkj.common.utils.SecurityUtils;
import net.cmkj.common.utils.StringUtils;
import net.cmkj.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 预警配置Service业务层处理
 *
 * @author cmkj
 * @date 2024-11-27
 */
@Service
public class BudgetWarningServiceImpl extends ServiceImpl<BudgetWarningMapper, BudgetWarning> implements IBudgetWarningService {

    @Autowired
    private IReportingTaskService reportingTaskService;

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private BudgetItemDeptMapper budgetItemDeptMapper;

    @Autowired
    private IDynamicSqlService dynamicSqlService;

    @Autowired
    private IReportingTaskDeptService reportingTaskDeptService;

    @Autowired
    private IBudgetWarningInfoService budgetWarningInfoService;

    @Autowired
    private IActualCostsService actualCostsService;

    @Autowired
    private IBudgetItemService itemService;

    @Autowired
    private IBudgetStatisticsService budgetSummary;

    @Autowired
    private IReportingVersionService reportingVersionService;

    @Autowired
    private BudgetSubjectMapper budgetSubjectMapper;

    @Autowired
    private IBudgetReportingService reportingService;

    @Autowired
    private BudgetDashboardMapper budgetDashboardMapper;

    @Autowired
    private IBudgetWarningInfoService warningInfoService;


    /**
     * 查询预警配置
     *
     * @param id 预警配置主键
     * @return 预警配置
     */
    @Override
    public BudgetWarning selectBudgetWarningById(Long id) {
        return baseMapper.selectBudgetWarningById(id);
    }

    /**
     * 查询预警配置列表
     *
     * @param budgetWarning 预警配置
     * @return 预警配置
     */
    @Override
    public List<BudgetWarning> selectBudgetWarningList(BudgetWarning budgetWarning) {
        return baseMapper.selectBudgetWarningList(budgetWarning);
    }

    /**
     * 新增预警配置
     *
     * @param budgetWarning 预警配置
     * @return 结果
     */
    @Override
    public int insertBudgetWarning(BudgetWarning budgetWarning) {
        budgetWarning.setCreateBy(SecurityUtils.getUsername());
        budgetWarning.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertBudgetWarning(budgetWarning);
    }

    /**
     * 修改预警配置
     *
     * @param budgetWarning 预警配置
     * @return 结果
     */
    @Override
    public int updateBudgetWarning(BudgetWarning budgetWarning) {
        budgetWarning.setUpdateBy(SecurityUtils.getUsername());
        budgetWarning.setUpdateTime(DateUtils.getNowDate());
        return baseMapper.updateBudgetWarning(budgetWarning);
    }

    /**
     * 批量删除预警配置
     *
     * @param ids 需要删除的预警配置主键
     * @return 结果
     */
    @Override
    public int deleteBudgetWarningByIds(Long[] ids) {
        return baseMapper.deleteBudgetWarningByIds(ids, SecurityUtils.getUsername());
    }

    /**
     * 删除预警配置信息
     *
     * @param id 预警配置主键
     * @return 结果
     */
    @Override
    public int deleteBudgetWarningById(Long id) {
        return baseMapper.deleteBudgetWarningById(id, SecurityUtils.getUsername());
    }

    /**
     * 定时任务执行方法
     */
    @Override
    public void warningTask() {
        BudgetWarning budgetWarning = new BudgetWarning();
        budgetWarning.setStatus(1);
        List<BudgetWarning> budgetWarnings = baseMapper.selectBudgetWarningList(budgetWarning);
        SysDept queryDept = new SysDept();
        queryDept.setLevel(2);
        List<SysDept> sysDepts = deptMapper.selectDeptList(queryDept);
        for (BudgetWarning warningTask : budgetWarnings) {
            ReportingTask task = reportingTaskService.selectReportingTaskById(warningTask.getTaskId());
            Long taskId = task.getId();
            if (task != null && task.getDelFlag().equals("0")) {
                if (warningTask.getType() == 1) { //每月提醒(未填报)
                    if (task.getCompletionStatus().equals("1")) {
                        //Integer day = warningTask.getDay();
                        int day = 1;
                        Calendar cal = Calendar.getInstance();
                        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
                        if (day == currentDay) { //同一天
                            for (SysDept sysDept : sysDepts) {
                                boolean flg = false;
                                Long deptId = sysDept.getDeptId();
                                ReportingTaskDept reportingTaskDept = new ReportingTaskDept();
                                reportingTaskDept.setTaskId(taskId);
                                reportingTaskDept.setDeptId(deptId);
                                List<ReportingTaskDept> reportingTaskDepts =
                                        reportingTaskDeptService.selectReportingTaskDeptList(reportingTaskDept);
                                if (reportingTaskDepts.isEmpty()) {
                                    List<BudgetItem> items =
                                            budgetItemDeptMapper.selectBudgetItemDeptListByDeptId(sysDept.getDeptId());
                                    for (BudgetItem item : items) {
                                        String sql = "SELECT * FROM `t_reporting_table_" + item.getTableName() + "` " +
                                                "where dept_id = " + deptId + " and del_flag = 0 and task_id = " + taskId;
                                        if (dynamicSqlService.dynamicSelectListSql(sql).isEmpty()) {
                                            flg = true;
                                        }
                                    }
                                    if (flg) {
                                        BudgetWarningInfo info = new BudgetWarningInfo();
                                        info.setDeptId(deptId);
                                        info.setDeptName(sysDept.getDeptName());
                                        info.setParentId(sysDept.getParentId());
                                        info.setTaskId(taskId);
                                        info.setTaskName(task.getName());
                                        info.setType(1);
                                        info.setMessage("有待填报任务，请及时填报！");
                                        info.setCreatedTime(new Date());
                                        budgetWarningInfoService.insertBudgetWarningInfo(info);
                                    }
                                }
                            }
                        }
                    }
                } else if (warningTask.getType() == 2) { //每天一次（超出提醒）
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    if (task.getBudgetYear() == year) {
                        ActualCosts queryActualCosts = new ActualCosts();
                        queryActualCosts.setYear(year);
                        List<ActualCosts> actualCosts = actualCostsService.selectActualCostsCountList(queryActualCosts);
                        for (ActualCosts actualCost : actualCosts) {
                            Long subjectId = actualCost.getSubjectId();
                            Long deptId = actualCost.getDeptId();
                            //BigDecimal actualIncurred = actualCost.getActualIncurred();
                            BigDecimal actualIncurred = actualCost.getTotal();
                            BigDecimal budget = BigDecimal.ZERO;
                            SysDept dept = deptMapper.selectDeptById(deptId);
                            if (subjectId != null && deptId != null) {
                                BudgetItem queryItem = new BudgetItem();
                                queryItem.setSubjectId(subjectId);
                                queryItem.setDelFlag("0");
                                List<BudgetItem> items = itemService.selectBudgetItemList(queryItem);
                                for (BudgetItem item : items) {
                                    if (item.getTableName().equals("pipeline")) {
                                        String sql = "SELECT IFNULL(SUM(expected_settlement),0) AS budgetTotal FROM " +
                                                "t_reporting_table_" + item.getTableName() + " t  where t.task_id = " + taskId + " and and del_flag = 0  and status = 5 t.dept_id  = " + deptId;
                                        Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                                        BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                                        budget = budget.add(budgetTotal); // 更新 ysMoney 的值
                                    } else if (item.getTableName().equals("station")) {
                                        String sql = "SELECT IFNULL(SUM(expected_settlement),0) AS budgetTotal FROM " +
                                                "t_reporting_table_" + item.getTableName() + " t  where t.task_id = " + taskId + " and and del_flag = 0  and status = 5 t.dept_id  = " + deptId;
                                        Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                                        BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                                        budget = budget.add(budgetTotal); // 更新 ysMoney 的값
                                    } else if (item.getTableName().equals("research")) {
                                        String sql = "SELECT IFNULL( SUM(COALESCE(t.intangible_assets,0)+COALESCE(t" +
                                                ".fixed_assets,0)), 0 ) AS budgetTotal FROM t_reporting_table_" + item.getTableName() + " t  where t.task_id = " + taskId + " and and del_flag = 0  and status = 5 t.dept_id  = " + deptId;
                                        Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                                        BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                                        budget = budget.add(budgetTotal); // 更新 ysMoney 的값
                                    } else if (item.getTableName().equals("meter")) {
                                        String sql = "SELECT IFNULL(SUM(budget),0) AS budgetTotal FROM " +
                                                "t_reporting_table_" + item.getTableName() + " t  where t.task_id = " + taskId + " and del_flag = 0  and status = 5 and t.dept_id  = " + deptId;
                                        Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                                        BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                                        budget = budget.add(budgetTotal); // 更新 ysMoney 的값
                                    } else if (item.getTableName().equals("charity")) {
                                        String sql = "SELECT IFNULL(SUM(budget),0) AS budgetTotal FROM " +
                                                "t_reporting_table_" + item.getTableName() + " t  where t.task_id = " + taskId + "  and del_flag = 0  and status = 5 and t.dept_id = " + deptId;
                                        Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                                        BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                                        budget = budget.add(budgetTotal); // 更新 ysMoney 的값
                                    } else if (item.getTableName().equals("lowvalue")) {
                                        String sql = "SELECT IFNULL(ROUND(SUM(budget),0) AS budgetTotal FROM " +
                                                "t_reporting_table_" + item.getTableName() + " t  where t.task_id = " + taskId + " and del_flag = 0  and status = 5  and t.dept_id  = " + deptId;
                                        Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                                        BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                                        budget = budget.add(budgetTotal); // 更新 ysMoney 的값
                                    } else if (item.getTableName().equals("information_system")) {
                                        String sql = "SELECT IFNULL( SUM( COALESCE(t.intangible_assets,0)+COALESCE(t" +
                                                ".fixed_assets,0) ), 0 ) AS budgetTotal FROM t_reporting_table_" + item.getTableName() + " t  where t.task_id = " + taskId + " and del_flag = 0  and status = 5  and t.dept_id  = " + deptId;
                                        Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                                        BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                                        budget = budget.add(budgetTotal); // 更新 ysMoney 的값
                                    } else if (item.getTableName().equals("housing")) {
                                        String sql = "SELECT IFNULL(SUM(expected_settlement),0) AS budgetTotal FROM " +
                                                "t_reporting_table_" + item.getTableName() + " t  where t.task_id = " + taskId + " and del_flag = 0  and status = 5  and t.dept_id  = " + deptId;
                                        Map<String, Object> info = dynamicSqlService.dynamicSelectInfoSql(sql);
                                        BigDecimal budgetTotal = new BigDecimal(info.get("budgetTotal").toString());
                                        budget = budget.add(budgetTotal); // 更新 ysMoney 的값
                                    } else {
                                        String sql = "SELECT IFNULL(SUM(budget),0) as budget  FROM " +
                                                "`t_reporting_table_" + item.getTableName() + "` where dept_id = " + deptId + " and del_flag = 0  and status = 5 and task_id = " + taskId;
                                        Map<String, Object> map = dynamicSqlService.dynamicSelectInfoSql(sql);
                                        if (map != null && map.get("budget") != null) {
                                            budget = budget.add(new BigDecimal(map.get("budget").toString()));
                                        }
                                    }
                                }
                            }
                            BigDecimal twentyPercent =
                                    budget.multiply(new BigDecimal(warningTask.getProportion() / 100));
                            BigDecimal budgetLimit = budget.add(twentyPercent);
                            int comparisonResult = actualIncurred.compareTo(budgetLimit);
                            if (comparisonResult > 0) {
                                BudgetWarningInfo info = new BudgetWarningInfo();
                                info.setDeptId(deptId);
                                info.setDeptName(dept.getDeptName());
                                info.setParentId(dept.getParentId());
                                info.setTaskId(task.getId());
                                info.setTaskName(task.getName());
                                info.setType(1);
                                info.setMessage("预算已超出" + warningTask.getProportion() + "%");
                                info.setBudgetMoney(budget);
                                info.setActualMoney(actualIncurred);
                                info.setCreatedTime(new Date());
                                budgetWarningInfoService.insertBudgetWarningInfo(info);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 定时任务：
     * 报表填报预警
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reportWarning() {
        BudgetWarning queryWarning = new BudgetWarning();
        queryWarning.setType(1);
        queryWarning.setStatus(1);

        // 查询所有启用的填报预警配置
        List<BudgetWarning> warningList = baseMapper.selectBudgetWarningList(queryWarning);

        BudgetItem queryItem = new BudgetItem();
        queryItem.setDelFlag("0");

        // 查询所有有效的预算项目
        List<BudgetItem> itemList = itemService.selectBudgetItemList(queryItem);
        for (BudgetWarning warning : warningList) {
            //判断是否大于开始时间
            Date startTime = warning.getStartTime();
            Instant currentTime = Instant.now();
            if (startTime == null || startTime.toInstant().isAfter(currentTime)) {
                continue;
            }
            Integer remind = warning.getRemind();
            Date lastTime = warning.getLastTime();
            if (lastTime != null) {
                if (remind == 1) { //一天一次
                    if (!DateUtils.isDaysAgo(lastTime, 1)) {
                        continue;
                    }
                } else if (remind == 2) { //一周一次
                    if (!DateUtils.isDaysAgo(lastTime, 7)) {
                        continue;
                    }
                } else if (remind == 3) { //单次任务
                    continue;
                }
            }
            // 获取当前任务
            Long taskId = warning.getTaskId();
            ReportingTask task = reportingTaskService.selectReportingTaskById(taskId);
            if (task == null) {
                continue;
            }

            // 获取去年任务的任务id
            Map<String, Long> previousYearTaskParams = new HashMap<>();
            previousYearTaskParams.put("taskId", taskId);
            previousYearTaskParams.put("gap", 1L);
            Long lastTaskId = budgetDashboardMapper.getPreviousYearTaskId(previousYearTaskParams);

            //手动修改填报状态
            ReportingTaskDept reportingTaskDept = new ReportingTaskDept();
            reportingTaskDept.setTaskId(taskId);
            List<ReportingTaskDept> reportingTaskDeptList =
                    reportingTaskDeptService.selectReportingTaskDeptList(reportingTaskDept);

            String itemIds = warning.getItemIds();
            if (itemIds == null) {
                continue;
            }
            String[] split = itemIds.split(",");
            for (String itemId : split) {
                for (BudgetItem item : itemList) {
                    if (item.getId().toString().equals(itemId)) {
                        // 查询今年已填报的部门
                        String sql =
                                "SELECT COUNT(id) total,dept_id as deptId FROM `t_reporting_table_" + item.getTableName() + "` where del_flag = 0 and `status`!= 1 and `status` != 4  and task_id = " + warning.getTaskId() + " GROUP BY dept_id";
                        List<Map<String, Object>> mapList = dynamicSqlService.dynamicSelectListSql(sql);
                        // 查询去年已填报的部门
                        List<Map<String, Object>> lastYearMapList = new ArrayList<>();
                        if (lastTaskId != null){
                            String lastYearSql =
                                    "SELECT COUNT(id) total,dept_id as deptId FROM `t_reporting_table_" + item.getTableName() + "` where del_flag = 0 and `status`!= 1 and `status` != 4  and task_id = " + lastTaskId + " GROUP BY dept_id";
                            lastYearMapList = dynamicSqlService.dynamicSelectListSql(lastYearSql);
                        }

                        // 查询所有的部门
                        List<SysDept> deptList = budgetItemDeptMapper.selectDeptListByItemId(item.getId());
                        for (SysDept sysDept : deptList) {
                            boolean isReporting = false;
                            for (ReportingTaskDept taskDept : reportingTaskDeptList) {
                                //部门已手动调整填报状态则忽略
                                if (taskDept.getDeptId().equals(sysDept.getDeptId())) {
                                    isReporting = true;
                                }
                            }
                            if (!isReporting) {
                                int total = 0;
                                for (Map<String, Object> map : mapList) {
                                    if (map.get("deptId") != null && map.get("deptId").toString().equals(sysDept.getDeptId().toString())) {
                                        total = Integer.parseInt(map.get("total").toString());
                                    }
                                }
                                //无填报数据
                                if (total == 0) {
                                    // 今年未填报 并且去年已填报 生成报表填报预警
                                    if (lastYearMapList != null && lastYearMapList.size() > 0) {
                                        for (Map<String, Object> map : lastYearMapList) {
                                            if (map.get("deptId") != null && map.get("deptId").toString().equals(sysDept.getDeptId().toString())) {
                                                BudgetWarningInfo info = getReportWarning(item, sysDept, task);
                                                budgetWarningInfoService.insertBudgetWarningInfo(info);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            warning.setLastTime(new Date());
            if (remind == 3) {
                warning.setStatus(2);
            }
            baseMapper.updateBudgetWarning(warning);
        }
    }

    /**
     * 定时任务：
     * 超出预警
     */
    @Override
    public void exceedWarning() {
        BudgetWarning query = new BudgetWarning();
        query.setType(2);
        query.setStatus(1);
        List<BudgetWarning> warningList = baseMapper.selectBudgetWarningList(query);
        for (BudgetWarning warning : warningList) {
            //判断是否大于开始时间
//            Date startTime = warning.getStartTime();
//            Instant currentTime = Instant.now();
//            if (startTime == null || startTime.toInstant().isAfter(currentTime)) {
//                continue;
//            }

            Double proportion = warning.getProportion();
            if (proportion == null || proportion <= 0) {
                continue;
            }
            Integer remind = warning.getRemind();
            Date lastTime = warning.getLastTime();
            if (lastTime != null) {
                if (remind == 1) { //一天一次
                    if (!DateUtils.isDaysAgo(lastTime, 1)) {
                        continue;
                    }
                } else if (remind == 2) { //一周一次
                    if (!DateUtils.isDaysAgo(lastTime, 7)) {
                        continue;
                    }
                } else if (remind == 3) { //单次任务
                    continue;
                }
            }
            Long taskId = warning.getTaskId();
            ReportingTask task = reportingTaskService.selectReportingTaskById(taskId);
            if (task == null || task.getBudgetYear() == null) {
                continue;
            }
            ReportingVersion version = reportingVersionService.selectReportingVersionById(warning.getVersionId());
            if (version == null || !version.getStatus().equals("2") || !version.getTaskId().equals(task.getId())) {
                continue;
            }
            //所有科目各部门预算数据
            List<Map<String, Object>> dataList = budgetSummary.budgetVersionWarningSummary(task.getId(),
                    version.getId());
            //实际发生费用
            ActualCosts queryActualCosts = new ActualCosts();
            queryActualCosts.setYear(task.getBudgetYear());
            List<ActualCosts> actualCosts = actualCostsService.selectActualCostsCountList(queryActualCosts);

            SysDept queryDept = new SysDept();
            queryDept.setLevel(2);
            List<SysDept> deptList = deptMapper.selectDeptList(queryDept);

            String[] subjectIds;
            String subjects = warning.getSubjects();
            if (StringUtils.isNotEmpty(subjects)) {
                subjectIds = subjects.split(",");
            } else {
                subjectIds = null;
            }
            //所有填报科目
            BudgetSubject querySubject = new BudgetSubject();
            querySubject.setDelFlag("0");
            List<BudgetSubject> subjectList = budgetSubjectMapper.selectBudgetSubjectList(querySubject);

            if (subjectIds != null) {
                List<Long> subjectIdLongs = Arrays.stream(subjectIds)
                        .map(Long::valueOf)
                        .toList();

                subjectList = subjectList.stream()
                        .filter(subject -> subjectIdLongs.contains(subject.getId()))
                        .toList();
            }

            for (BudgetSubject subject : subjectList) {
                for (ActualCosts actualCost : actualCosts) {
                    for (SysDept sysDept : deptList) {
                        if (actualCost.getDeptId().equals(sysDept.getDeptId())) {
                            if (actualCost.getSubjectId().equals(subject.getId())) {
                                for (Map<String, Object> map : dataList) {
                                    if (map.get("subjectId").toString().equals(subject.getId().toString())) {
                                        BigDecimal budget = (BigDecimal) map.get("dept" + actualCost.getDeptId());
                                        //BigDecimal actualIncurred = actualCost.getActualIncurred();
                                        BigDecimal actualIncurred = actualCost.getTotal();
                                        BigDecimal threshold = budget.multiply(BigDecimal.valueOf(proportion / 100));
                                        if (actualIncurred.compareTo(threshold) > 0) {
                                            BudgetWarningInfo info = getExceedWarning(sysDept, task, subject, budget,
                                                    actualIncurred, proportion);
                                            budgetWarningInfoService.insertBudgetWarningInfo(info);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            warning.setLastTime(new Date());
            if (remind == 3) {
                warning.setStatus(2);
            }
            baseMapper.updateBudgetWarning(warning);
        }
    }

    /**
     * 定时任务：
     * 费用预警
     */
    @Override
    public void costWarning() {
        BudgetWarning query = new BudgetWarning();
        query.setType(3);
        query.setStatus(1);
        List<BudgetWarning> warningList = baseMapper.selectBudgetWarningList(query);
        for (BudgetWarning warning : warningList) {
            //判断是否大于开始时间
            Date startTime = warning.getStartTime();
            Instant currentTime = Instant.now();
            if (startTime == null || startTime.toInstant().isAfter(currentTime)) {
                continue;
            }

            Integer remind = warning.getRemind();
            Date lastTime = warning.getLastTime();
            if (lastTime != null) {
                if (remind == 1) { //一天一次
                    if (!DateUtils.isDaysAgo(lastTime, 1)) {
                        continue;
                    }
                } else if (remind == 2) { //一周一次
                    if (!DateUtils.isDaysAgo(lastTime, 7)) {
                        continue;
                    }
                } else if (remind == 3) { //单次任务
                    continue;
                }
            }

            Long taskId = warning.getTaskId();
            ReportingTask task = reportingTaskService.selectReportingTaskById(taskId);
            if (task == null) {
                continue;
            }

            //科目
            String subjects = warning.getSubjects();
            if (StringUtils.isEmpty(subjects)) {
                continue;
            }
            String[] subjectIds = subjects.split(",");
            //所有填报科目
            BudgetSubject querySubject = new BudgetSubject();
            querySubject.setDelFlag("0");
            List<BudgetSubject> subjectList = budgetSubjectMapper.selectBudgetSubjectList(querySubject);

            List<Long> subjectIdLongs = Arrays.stream(subjectIds)
                    .map(Long::valueOf)
                    .toList();

            subjectList = subjectList.stream()
                    .filter(subject -> subjectIdLongs.contains(subject.getId()))
                    .toList();

            String subjectNames = subjectList.stream()
                    .map(BudgetSubject::getName)
                    .collect(Collectors.joining("，"));
            //部门
            SysDept queryDept = new SysDept();
            queryDept.setLevel(2);
            List<SysDept> deptList = deptMapper.selectDeptList(queryDept);
            for (SysDept sysDept : deptList) {
                BudgetWarningInfo info = new BudgetWarningInfo();
                info.setDeptId(sysDept.getDeptId());
                info.setDeptName(sysDept.getDeptName());
                info.setParentId(sysDept.getParentId());
                info.setParentName(sysDept.getParentName());
                info.setTaskId(task.getId());
                info.setTaskName(task.getName());
                info.setType(3);
                info.setMessage("请及时对【" + subjectNames + "】科目提单付款！");
                info.setCreatedTime(new Date());
                info.setStatus(1);
                budgetWarningInfoService.insertBudgetWarningInfo(info);
            }
            warning.setLastTime(new Date());
            if (remind == 3) {
                warning.setStatus(2);
            }
            baseMapper.updateBudgetWarning(warning);
        }
    }

    /**
     * 经济事项预警
     */
    @Override
    public void economicMattersWarning() {
        BudgetWarning query = new BudgetWarning();
        query.setType(4);
        query.setStatus(1);
        List<BudgetWarning> warningList = baseMapper.selectBudgetWarningList(query);
        for (BudgetWarning warning : warningList) {
            //判断是否大于开始时间
            Date startTime = warning.getStartTime();
            Instant currentTime = Instant.now();
            if (startTime == null || startTime.toInstant().isAfter(currentTime)) {
                continue;
            }

            Integer remind = warning.getRemind();
            Date lastTime = warning.getLastTime();
            if (lastTime != null) {
                if (remind == 1) { //一天一次
                    if (!DateUtils.isDaysAgo(lastTime, 1)) {
                        continue;
                    }
                } else if (remind == 2) { //一周一次
                    if (!DateUtils.isDaysAgo(lastTime, 7)) {
                        continue;
                    }
                } else if (remind == 3) { //单次任务
                    continue;
                }
            }

            Long taskId = warning.getTaskId();
            ReportingTask task = reportingTaskService.selectReportingTaskById(taskId);
            if (task == null) {
                continue;
            }

            // 修改为处理多个部门ID的情况
            String[] deptIds = warning.getDeptId().split(",");
            for (String deptId : deptIds) {
                SysDept sysDept = deptMapper.selectDeptById(Long.parseLong(deptId.trim()));
                if (sysDept != null) {
                    BudgetWarningInfo info = new BudgetWarningInfo();
                    info.setDeptId(sysDept.getDeptId());
                    info.setDeptName(sysDept.getDeptName());
                    info.setParentId(sysDept.getParentId());
                    info.setParentName(sysDept.getParentName());
                    info.setTaskId(task.getId());
                    info.setTaskName(task.getName());
                    info.setType(4);
                    info.setMessage("当前任务经济事项预警：" + warning.getItemName());
                    info.setCreatedTime(new Date());
                    info.setStatus(1);
                    budgetWarningInfoService.insertBudgetWarningInfo(info);
                }
            }

            warning.setLastTime(new Date());
            if (remind == 3) {
                warning.setStatus(2);
            }
            baseMapper.updateBudgetWarning(warning);
        }
    }

    /**
     * 未审核预警
     */
    @Override
    public Map<String, Object> getNotReportedList(Budget budgetQuery) {

        Map<String, Object> map = new HashMap<>();

        BudgetItem budgetItemQuery = new BudgetItem();
        budgetItemQuery.setTaskId(budgetQuery.getTaskId());
        budgetItemQuery.setSelectType(budgetQuery.getSelectType());
        List<BudgetItem> budgetItems = reportingService.itemList(budgetItemQuery, SecurityUtils.getUserId());

        // 从预警记录里面获取去年填报但今年未填报的数据

        // 去年填报 今年未填报预警列表
        List<BudgetWarningInfo> reportWarningList = new ArrayList<>();

        BudgetWarningInfo queryWarning = new BudgetWarningInfo();
        queryWarning.setType(1);
        if(SecurityUtils.isAdmin(SecurityUtils.getLoginUser().getUserId())){
            reportWarningList = new ArrayList<>();
        }else{
//            SysDept dept = SecurityUtils.getLoginUser().getUser().getDept();
//            if(dept.getLevel() == 1){
//                queryWarning.setParentId(dept.getDeptId());
//            }else{
//                queryWarning.setDeptId(dept.getDeptId());
//            }
            queryWarning.setTaskId(budgetQuery.getTaskId());
            reportWarningList = warningInfoService.selectBudgetWarningInfoList(queryWarning);
        }

        // 未填报部门
        List<UnauditedWarningVO> notReportedList = new ArrayList<>();

        if (budgetItems != null && budgetItems.size() > 0) {
            for (BudgetItem budget : budgetItems){
                budget.setSelectType(budgetItemQuery.getSelectType());
                budget.setTaskId(budgetQuery.getTaskId());
                BudgetItem budgetItem = itemService.selectBudgetItemById(budget.getId());
                if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
                    return AjaxResult.success(map);
                }
                List<SysDept> itemDepts = budgetItemDeptMapper.selectDeptListByItemId(budgetItem.getId());

                // 查询今年未填报的部门
                String sql = "SELECT dept_id as deptId FROM t_reporting_table_" + budgetItem.getTableName() + " where task_id" +
                        " = " + budget.getTaskId() + " and del_flag = '0' and ( status = 1 or status = 4 ) GROUP BY " +
                        "dept_id";
                List<Map<String, Object>> deptList = dynamicSqlService.dynamicSelectListSql(sql);

                // 找出今年未填报的部门deptList 和  去年填报今年未填报的预警记录 reportWarningList 重合的部门
                List<Map<String, Object>> deptList2 = new ArrayList<>();
                for (Map<String, Object> deptMap : deptList) {
                    for (BudgetWarningInfo warning : reportWarningList) {
                        if (deptMap.get("deptId").equals(warning.getDeptId())) {
                            deptList2.add(deptMap);
                        }
                    }
                }
                for (SysDept dept : itemDepts) {
                    for (Map<String, Object> deptMap : deptList2) {
                        if (dept.getDeptId().equals(deptMap.get("deptId"))) {
                            ReportingTaskDept reportingTaskDept = new ReportingTaskDept();
                            reportingTaskDept.setTaskId(budget.getTaskId());
                            reportingTaskDept.setDeptId(dept.getDeptId());
                            if (reportingTaskDeptService.selectReportingTaskDeptList(reportingTaskDept).isEmpty()) {
                                UnauditedWarningVO warningVO = new UnauditedWarningVO();
                                warningVO.setCompanyName(dept.getParentName());
                                warningVO.setDeptName(dept.getDeptName());
                                warningVO.setTableDisplayName(budgetItem.getTableDisplayName());

                                notReportedList.add(warningVO);
                            }
                        }
                    }
                }
            }
        }

        // 测试数据
//        UnauditedWarningVO warningVO = new UnauditedWarningVO();
//        warningVO.setCompanyName("郑州华润燃气股份有限公司");
//        warningVO.setDeptName("人力资源部");
//        warningVO.setTableDisplayName("表10工资福利及员工教育");
//        notReportedList.add(warningVO);

        map.put("notReportedList", notReportedList);
        return map;
    }


    private static BudgetWarningInfo getReportWarning(BudgetItem item, SysDept sysDept, ReportingTask task) {
        BudgetWarningInfo info = new BudgetWarningInfo();
        info.setDeptId(sysDept.getDeptId());
        info.setDeptName(sysDept.getDeptName());
        info.setParentId(sysDept.getParentId());
        info.setParentName(sysDept.getParentName());
        info.setTaskId(task.getId());
        info.setTaskName(task.getName());
        info.setType(1);
        info.setMessage("【" + task.getName() + "】中的【" + item.getTableDisplayName() + "】未填报的数据，请按时填报");
        info.setCreatedTime(new Date());
        info.setStatus(1);
        info.setItemId(item.getId());
        info.setItemName(item.getTableDisplayName());
        return info;
    }

    private static BudgetWarningInfo getExceedWarning(SysDept sysDept, ReportingTask task, BudgetSubject subject,
                                                      BigDecimal budget, BigDecimal actualIncurred, Double proportion) {
        BudgetWarningInfo info = new BudgetWarningInfo();
        info.setDeptId(sysDept.getDeptId());
        info.setDeptName(sysDept.getDeptName());
        info.setParentId(sysDept.getParentId());
        info.setParentName(sysDept.getParentName());
        info.setTaskId(task.getId());
        info.setTaskName(task.getName());
        info.setType(2);
        info.setMessage("【" + task.getName() + "】中的【" + subject.getName() + "】实际费用已经超出了【" + proportion +
                "%】，请及时关注实际费用");
        info.setCreatedTime(new Date());
        info.setStatus(1);
        info.setBudgetMoney(budget);
        info.setActualMoney(actualIncurred);
        info.setProportion(proportion);
        info.setSubjectId(subject.getId());
        info.setSubjectName(subject.getName());
        return info;
    }
}
