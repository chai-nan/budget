package net.cmkj.budget.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.cmkj.budget.domain.*;
import net.cmkj.budget.mapper.BudgetItemDeptMapper;
import net.cmkj.budget.mapper.BudgetReportingMapper;
import net.cmkj.budget.service.*;
import net.cmkj.common.constant.HttpStatus;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysDept;
import net.cmkj.common.core.domain.entity.SysRole;
import net.cmkj.common.core.domain.entity.SysUser;
import net.cmkj.common.core.domain.model.LoginUser;
import net.cmkj.common.core.page.TableDataInfo;
import net.cmkj.common.utils.*;
import net.cmkj.common.utils.bean.BeanUtils;
import net.cmkj.common.utils.uuid.IdUtils;
import net.cmkj.fixed.domain.*;
import net.cmkj.fixed.service.*;
import net.cmkj.system.mapper.SysDeptMapper;
import net.cmkj.system.mapper.SysDictDataMapper;
import net.cmkj.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 预算填报Service业务层处理
 *
 * @author cmkj
 * @date 2024-07-15
 */
@Service
@Slf4j
public class BudgetReportingServiceImpl implements IBudgetReportingService {

    @Autowired
    private BudgetReportingMapper reportingMapper;

    @Autowired
    private IReportingTaskService taskService;

    @Autowired
    private IBudgetItemService itemService;

    @Autowired
    private ITableModelService tableModelService;

    @Autowired
    private IReportingLogService reportingLogService;

    @Autowired
    private IReportingVersionService reportingVersionService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private IDynamicSqlService dynamicSqlService;

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private BudgetItemDeptMapper budgetItemDeptMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private ITablePipelineService pipelineService;

    @Autowired
    private ITablePipelineVersionService pipelineVersionService;

    @Autowired
    private ITableStationService stationService;

    @Autowired
    private ITableStationVersionService stationVersionService;

    @Autowired
    private ITableHousingService housingService;

    @Autowired
    private ITableHousingVersionService housingVersionService;

    @Autowired
    private ITableInformationSystemService informationSystemService;

    @Autowired
    private ITableInformationSystemVersionService informationSystemVersionService;

    @Autowired
    private ITableResearchService researchService;

    @Autowired
    private ITableResearchVersionService researchVersionService;

    @Autowired
    private ITableMeterService meterService;

    @Autowired
    private ITableMeterVersionService meterVersionService;

    @Autowired
    private ITableCharityService charityService;

    @Autowired
    private ITableCharityVersionService charityVersionService;

    @Autowired
    private ITableLowvalueService lowvalueService;

    @Autowired
    private ITableLowvalueVersionService lowvalueVersionService;

    @Autowired
    private ITableWagesService tableWagesService;

    @Autowired
    private ITableWagesVersionService tableWagesVersionService;

    @Autowired
    private IActualCostsService actualCostsService;

    @Autowired
    private IReportingTaskDeptService reportingTaskDeptService;

    @Override
    public List<BudgetItem> itemList(BudgetItem budgetItem, Long userId) {
        List<BudgetItem> list = new ArrayList<>();
        Integer selectType = budgetItem.getSelectType();
        ReportingTask task = taskService.selectReportingTaskById(budgetItem.getTaskId());
        if (selectType == null || task == null) {
            return list;
        }
        if (selectType == 1) {
            if (!SecurityUtils.isAdmin(userId)) {
                Long[] itemIds = null;
                Long[] deptIds = null;
                SysUser sysUser = userMapper.selectUserById(userId);
                if (sysUser == null || sysUser.getDept() == null) {
                    return list;
                }
                SysDept dept = sysUser.getDept();
                try {
                    if (dept.getLevel() == 1) {
                        deptIds = deptMapper.selectChildIds(dept.getDeptId());
                        if (deptIds.length == 0) {
                            return list;
                        }
                        itemIds = budgetItemDeptMapper.selectItemByDepts(deptIds);
                    } else if (dept.getLevel() == 2) {
                        deptIds = new Long[]{dept.getDeptId()};
                        itemIds = budgetItemDeptMapper.selectItemByDepts(deptIds);
                    } else {
                        return list;
                    }
                    budgetItem.setIds(itemIds);
                } catch (Exception e) {
                    return list;
                }
            }
        } else if (selectType == 2) {
            if (!SecurityUtils.isAdmin(userId)) {
                budgetItem.setUserId(userId);
            }
        }
        budgetItem.setDelFlag("0");
        budgetItem.setQueryType(1);
        list = itemService.selectBudgetItemList(budgetItem);
        if (selectType == 1) {
            String sql;
            for (BudgetItem item : list) {
                if (StringUtil.isNotEmpty(item.getTableName())) {
                    Budget query = new Budget();
                    query.setItemId(item.getId());
                    List<SysDept> depts = itemFieldDept(query, userId);
                    List<Long> deptIds = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
                    StringBuilder deptSql = new StringBuilder();
                    if (deptIds.size() > 0) {
                        deptSql.append(" AND dept_id IN (");
                        for (int i = 0; i < deptIds.size(); i++) {
                            deptSql.append(deptIds.get(i));
                            if (i < deptIds.size() - 1) {
                                deptSql.append(", ");
                            }
                        }
                        deptSql.append(") ");
                    } else {
                        deptSql.append(" AND dept_id = 0 ");
                    }
                    sql = "select count(1) as count  from t_reporting_table_" + item.getTableName() + " where task_id" +
                            " = " + task.getId() + " and  del_flag = '0' and status > 1 " + deptSql;
                    Map<String, Object> datas = dynamicSqlService.dynamicSelectInfoSql(sql);
                    item.setIsReporting(Optional.ofNullable(datas.get("count"))
                            .map(countStr -> Integer.parseInt(countStr.toString()))
                            .orElse(0));
                    sql = "select count(1) as count  from t_reporting_table_" + item.getTableName() + " where task_id" +
                     " = " + task.getId() + " and  del_flag = '0' and status = 4 " + deptSql;
                    datas = dynamicSqlService.dynamicSelectInfoSql(sql);
                    item.setIsReject(Optional.ofNullable(datas.get("count"))
                            .map(countStr -> Integer.parseInt(countStr.toString()))
                            .orElse(0));
                }
            }
        }
        return list;
    }

    @Override
    public Map<String, Object> itemInfo(Budget budget) {
        Map<String, Object> map = new HashMap<>();
        BudgetItem budgetItem = itemService.selectBudgetItemById(budget.getItemId());
        if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
            return map;
        }
        String vettedSql = "";
        String allSql = "";
        if (budget.getSelectType() != null && budget.getSelectType() == 2) {
            if (budgetItem.getReportingType().equals("1")) { // 动态表
                vettedSql = "select count(1) as count , SUM(budget) as budget,SUM(budget_year) as budgetYear, SUM" +
                 "(estimated_incurred) as estimatedIncurred, SUM(actual_incurred) as actualIncurred from " +
                  "t_reporting_table_" + budgetItem.getTableName() + " where task_id = " + budget.getTaskId() + " and" +
                   " del_flag = '0' and (status = 3 or status = 5)";
                allSql = "select count(1) as count , SUM(budget) as budget,SUM(budget_year) as budgetYear, SUM" +
                 "(estimated_incurred) as estimatedIncurred, SUM(actual_incurred) as actualIncurred from " +
                  "t_reporting_table_" + budgetItem.getTableName() + " where task_id = " + budget.getTaskId() + " and" +
                   " del_flag = '0' and status != 1 and status != 4 ";
            } else if (budgetItem.getReportingType().equals("2")) { //定制表
                if (budgetItem.getTableName().equals("pipeline")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(expected_settlement), 2) AS budget FROM " +
                     "t_reporting_table_pipeline where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "(status = 3 or status = 5) ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(expected_settlement), 2) AS budget FROM " +
                     "t_reporting_table_pipeline where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "status != 1 and status != 4 ";
                } else if (budgetItem.getTableName().equals("station")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(expected_settlement), 2) AS budget FROM " +
                     "t_reporting_table_station where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "(status = 3 or status = 5) ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(expected_settlement), 2) AS budget FROM " +
                     "t_reporting_table_station where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "status != 1 and status != 4 ";
                } else if (budgetItem.getTableName().equals("research")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS " +
                     "budget FROM t_reporting_table_research where task_id = " + budget.getTaskId() + " and del_flag " +
                      "= '0' and (status = 3 or status = 5) ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS " +
                     "budget FROM t_reporting_table_research where task_id = " + budget.getTaskId() + " and del_flag " +
                      "= '0' and status != 1 and status != 4 ";
                } else if (budgetItem.getTableName().equals("meter")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(budget), 2) AS budget FROM " +
                     "t_reporting_table_meter where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "(status = 3 or status = 5) ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(budget), 2) AS budget FROM " +
                     "t_reporting_table_meter where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "status != 1 and status != 4 ";
                } else if (budgetItem.getTableName().equals("charity")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(budget), 2) AS budget FROM " +
                     "t_reporting_table_charity where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "(status = 3 or status = 5) ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(budget), 2) AS budget FROM " +
                     "t_reporting_table_charity where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "status != 1 and status != 4 ";
                } else if (budgetItem.getTableName().equals("lowvalue")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(budget), 2) AS budget FROM " +
                     "t_reporting_table_lowvalue where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "(status = 3 or status = 5) ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(budget), 2) AS budget FROM " +
                     "t_reporting_table_lowvalue where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "status != 1 and status != 4 ";
                } else if (budgetItem.getTableName().equals("information_system")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS " +
                     "budget FROM t_reporting_table_information_system where task_id = " + budget.getTaskId() + " and" +
                      " del_flag = '0' and (status = 3 or status = 5) ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS " +
                     "budget FROM t_reporting_table_information_system where task_id = " + budget.getTaskId() + " and" +
                      " del_flag = '0' and status != 1 and status != 4 ";
                } else if (budgetItem.getTableName().equals("housing")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(expected_settlement), 2) AS budget FROM " +
                     "t_reporting_table_housing where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "(status = 3 or status = 5) ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(expected_settlement), 2) AS budget FROM " +
                     "t_reporting_table_housing where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "status != 1 and status != 4 ";
                }
            } else {
                return map;
            }
        } else if (budget.getSelectType() != null && budget.getSelectType() == 3) {
            if (budgetItem.getReportingType().equals("1")) { // 动态表
                vettedSql = "select count(1) as count , SUM(budget) as budget,SUM(budget_year) as budgetYear, SUM" +
                 "(estimated_incurred) as estimatedIncurred, SUM(actual_incurred) as actualIncurred from " +
                  "t_reporting_table_" + budgetItem.getTableName() + " where task_id = " + budget.getTaskId() + " and" +
                   " del_flag = '0' and status = 5 ";
                allSql = "select count(1) as count , SUM(budget) as budget,SUM(budget_year) as budgetYear, SUM" +
                 "(estimated_incurred) as estimatedIncurred, SUM(actual_incurred) as actualIncurred from " +
                  "t_reporting_table_" + budgetItem.getTableName() + " where task_id = " + budget.getTaskId() + " and" +
                   " del_flag = '0' and (status = 3 or status = 5)";
            } else if (budgetItem.getReportingType().equals("2")) { //定制表
                if (budgetItem.getTableName().equals("pipeline")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(expected_settlement), 2) AS budget FROM " +
                     "t_reporting_table_pipeline where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "status = 5 ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(expected_settlement), 2) AS budget FROM " +
                     "t_reporting_table_pipeline where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "(status = 3 or status = 5) ";
                } else if (budgetItem.getTableName().equals("station")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(expected_settlement), 2) AS budget FROM " +
                     "t_reporting_table_station where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "status = 5 ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(expected_settlement), 2) AS budget FROM " +
                     "t_reporting_table_station where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "(status = 3 or status = 5) ";
                } else if (budgetItem.getTableName().equals("research")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS " +
                     "budget FROM t_reporting_table_research where task_id = " + budget.getTaskId() + " and del_flag " +
                      "= '0' and status = 5 ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS " +
                     "budget FROM t_reporting_table_research where task_id = " + budget.getTaskId() + " and del_flag " +
                      "= '0' and (status = 3 or status = 5) ";
                } else if (budgetItem.getTableName().equals("meter")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(budget), 2) AS budget FROM " +
                     "t_reporting_table_meter where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "status = 5 ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(budget), 2) AS budget FROM " +
                     "t_reporting_table_meter where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "(status = 3 or status = 5) ";
                } else if (budgetItem.getTableName().equals("charity")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(budget), 2) AS budget FROM " +
                     "t_reporting_table_charity where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "status = 5 ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(budget), 2) AS budget FROM " +
                     "t_reporting_table_charity where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "(status = 3 or status = 5) ";
                } else if (budgetItem.getTableName().equals("lowvalue")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(budget), 2) AS budget FROM " +
                     "t_reporting_table_lowvalue where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "status = 5 ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(budget), 2) AS budget FROM " +
                     "t_reporting_table_lowvalue where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "(status = 3 or status = 5) ";
                } else if (budgetItem.getTableName().equals("information_system")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS " +
                     "budget FROM t_reporting_table_information_system where task_id = " + budget.getTaskId() + " and" +
                      " del_flag = '0' and status = 5 ";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(intangible_assets)+SUM(fixed_assets), 2) AS " +
                     "budget FROM t_reporting_table_information_system where task_id = " + budget.getTaskId() + " and" +
                      " del_flag = '0' and (status = 3 or status = 5) ";
                } else if (budgetItem.getTableName().equals("housing")) {
                    vettedSql = "SELECT count(1) as count ,  ROUND(SUM(expected_settlement), 2) AS budget FROM " +
                     "t_reporting_table_housing where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "status = 5";
                    allSql = "SELECT count(1) as count ,  ROUND(SUM(expected_settlement), 2) AS budget FROM " +
                     "t_reporting_table_housing where task_id = " + budget.getTaskId() + " and del_flag = '0' and " +
                      "(status = 3 or status = 5) ";
                }
            } else {
                return map;
            }
        } else {
            return map;
        }

        Map<String, Object> vettedDatas = dynamicSqlService.dynamicSelectInfoSql(vettedSql);
        Map<String, Object> allDatas = dynamicSqlService.dynamicSelectInfoSql(allSql);
        map.put("count", vettedDatas.get("count") == null ? 0 : Integer.parseInt(vettedDatas.get("count").toString()));
        map.put("allCount", allDatas.get("count") == null ? 0 : Integer.parseInt(allDatas.get("count").toString()));
        map.put("budget", vettedDatas.get("budget") == null ? 0 :
 Double.parseDouble(vettedDatas.get("budget").toString()));
        map.put("allBudget", allDatas.get("budget") == null ? 0 :
                Double.parseDouble(allDatas.get("budget").toString()));
        map.put("budgetYear", vettedDatas.get("budgetYear") == null ? 0 : Double.parseDouble(vettedDatas.get(
                "budgetYear").toString()));
        map.put("allBudgetYear", allDatas.get("budgetYear") == null ? 0 : Double.parseDouble(allDatas.get("budgetYear"
        ).toString()));
        map.put("estimatedIncurred", vettedDatas.get("estimatedIncurred") == null ? 0 :
                Double.parseDouble(vettedDatas.get("estimatedIncurred").toString()));
        map.put("allEstimatedIncurred", allDatas.get("estimatedIncurred") == null ? 0 :
 Double.parseDouble(allDatas.get("estimatedIncurred").toString()));
        map.put("actualIncurred", vettedDatas.get("actualIncurred") == null ? 0 : Double.parseDouble(vettedDatas.get(
                "actualIncurred").toString()));
        map.put("allActualIncurred", allDatas.get("actualIncurred") == null ? 0 : Double.parseDouble(allDatas.get(
                "actualIncurred").toString()));
        return map;
    }

    @Override
    public Map<String, Object> itemSubmissionDept(Budget budget) {
        Map<String, Object> map = new HashMap<>();
        BudgetItem budgetItem = itemService.selectBudgetItemById(budget.getItemId());
        if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
            return map;
        }
        List<SysDept> itemDepts = budgetItemDeptMapper.selectDeptListByItemId(budgetItem.getId());
        // 已填报部门
        List<SysDept> submitDepts = new ArrayList<>();
        // 未填报部门
        List<SysDept> notDepts = new ArrayList<>();
        // 待审核部门
        List<SysDept> notCheckDepts = new ArrayList<>();
        String sql = "SELECT dept_id as deptId FROM t_reporting_table_" + budgetItem.getTableName() + " where task_id" +
         " = " + budget.getTaskId() + " and del_flag = '0' and status != 1 and status != 4 GROUP BY dept_id";
        List<Map<String, Object>> deptList = dynamicSqlService.dynamicSelectListSql(sql);
        for (SysDept dept : itemDepts) {
            int flag = 0;
            for (Map<String, Object> deptMap : deptList) {
                if (dept.getDeptId().equals(deptMap.get("deptId"))) {
                    flag = 1;
                    submitDepts.add(dept);
                }
            }
            if (flag == 0) {
                ReportingTaskDept reportingTaskDept = new ReportingTaskDept();
                reportingTaskDept.setTaskId(budget.getTaskId());
                reportingTaskDept.setDeptId(dept.getDeptId());
                if (reportingTaskDeptService.selectReportingTaskDeptList(reportingTaskDept).isEmpty()) {
                    notDepts.add(dept);
                }
            }
        }
        List<Map<String, Object>> checkList = new ArrayList<>();
        if (budget.getSelectType() != null && budget.getSelectType() == 2) {
            String checkSql = "SELECT dept_id as deptId FROM t_reporting_table_" + budgetItem.getTableName() + " " +
                    "where task_id = " + budget.getTaskId() + " and del_flag = '0' and (status = 2 or status = 6) GROUP BY " +
              "dept_id";
            checkList = dynamicSqlService.dynamicSelectListSql(checkSql);
        } else if (budget.getSelectType() != null && budget.getSelectType() == 3) {
            String checkSql = "SELECT dept_id as deptId FROM t_reporting_table_" + budgetItem.getTableName() + " " +
 "where task_id = " + budget.getTaskId() + " and del_flag = '0' and status = 3 GROUP BY dept_id";
            checkList = dynamicSqlService.dynamicSelectListSql(checkSql);
        }
        for (SysDept dept : itemDepts) {
            for (Map<String, Object> deptMap : checkList) {
                if (dept.getDeptId().equals(deptMap.get("deptId"))) {
                    notCheckDepts.add(dept);
                }
            }
        }
        map.put("allCount", itemDepts.size());
        map.put("allDepts", itemDepts);
        map.put("submitCount", submitDepts.size());
        map.put("submitDepts", submitDepts);
        map.put("notCount", notDepts.size());
        map.put("notDepts", notDepts);
        map.put("notCheckCount", notCheckDepts.size());
        map.put("notCheckDepts", notCheckDepts);
        return map;
    }

    @Override
    public List<TableModel> itemFieldList(Long itemId, Long taskId) {
        TableModel model = new TableModel();
        model.setItemId(itemId);
        List<TableModel> fieldList = tableModelService.selectTableModelList(model);
        addFieldDict(fieldList);
        ReportingTask task = taskService.selectReportingTaskById(taskId);
        if (task != null && task.getYear() != null) {
            changeYearName(fieldList, task.getYear());
        }
        return fieldList;
    }

    @Override
    public List<Map<String, Object>> budgetList(Budget query, Long userId) {
        List<Map<String, Object>> list = new ArrayList<>();
        BudgetItem budgetItem = itemService.selectBudgetItemById(query.getItemId());
        if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
            return list;
        }
        String tableName = budgetItem.getTableName();
        StringBuilder sql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        StringBuilder where = new StringBuilder();
        sql.append(" ( select t.id, t.task_id, t.`year`, t.dept_id, t.user_id, t.budget, t.budget_year, t" +
         ".actual_incurred, t.estimated_incurred, t.cost_description, t.`status`, t.review_budget, t.review_reason, t" +
                ".create_by, DATE_FORMAT(t.create_time, '%Y-%m-%d %H:%i:%s') AS create_time, t.update_by, DATE_FORMAT(t" +
           ".update_time, '%Y-%m-%d %H:%i:%s') AS update_time, t.remark, t.del_flag, u.nick_name as user_name, d" +
            ".dept_name");
        countSql.append(" ( SELECT NULL AS id, NULL AS task_id, NULL AS `year`, t.dept_id, NULL AS user_id, SUM(t" +
         ".budget) AS budget, SUM(t.budget_year) AS budget_year, SUM(t.actual_incurred) AS actual_incurred, SUM(t" +
          ".estimated_incurred) AS estimated_incurred, NULL AS cost_description, '' AS `status`, NULL AS " +
                "review_budget, NULL AS review_reason, NULL AS create_by, NOW() AS create_time, NULL AS update_by, NULL AS" +
            " update_time, NULL AS remark, NULL AS del_flag, NULL AS user_name, CONCAT(d.dept_name, '合计') AS " +
                "dept_name");
        List<TableModel> fieldList = tableModelService.selectTableModelListByItem(query.getItemId());
        for (TableModel field : fieldList) {
            sql.append(", ").append(" e.").append(field.getFieldName());
            countSql.append(", NULL AS ").append(field.getFieldName());
        }
        where.append(" from t_reporting_table_").append(tableName).append(" t  LEFT JOIN t_reporting_table_").append(tableName).append("_extension e ON t.ID = e.ID LEFT JOIN sys_user u on u.user_id = t.user_id LEFT JOIN sys_dept d on d.dept_id = t.dept_id ").append(" where t.task_id = ").append(query.getTaskId()).append(" and t.del_flag = '0' ");
        if (query.getDeptId() != null) {
            where.append(" and t.dept_id = ").append(query.getDeptId());
        }
        if (query.getParentId() != null) {
            where.append(" and d.parent_id = ").append(query.getParentId());
        }
        String orderby = "";
        if (query.getSelectType() != null && query.getSelectType() == 1) {
            List<SysDept> depts = itemFieldDept(query, userId);
            List<Long> deptIds = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
            if (deptIds.size() > 0) {
                where.append(" AND t.dept_id IN (");
                for (int i = 0; i < deptIds.size(); i++) {
                    where.append(deptIds.get(i));
                    if (i < deptIds.size() - 1) {
                        where.append(", ");
                    }
                }
                where.append(") ");
            } else {
                where.append(" AND t.dept_id = 0");
            }
            orderby = " ORDER BY dept_id,  CASE  WHEN t.status = 1 THEN 1  WHEN t.status = 4 THEN 2  ELSE 3  END,  " +
                    "create_time ";

            //只查询当前填报部门数据
            where.append(" AND t.dept_id in (SELECT dept_id from t_budget_item_dept WHERE item_id = " + budgetItem.getId() + " )");
            PageUtils.startPage();
            return dynamicSqlService.dynamicSelectListSql(sql.append(where).append("  )  ").append(orderby).toString());
        } else if (query.getSelectType() != null && query.getSelectType() == 2) {
            if (query.getStatus() == null) {
                where.append(" and (t.`status` = 2 or t.`status` = 3 or t.`status` = 4 or t.`status` = 5 or t" +
                        ".`status` = 6  ) ");
            } else if (query.getStatus() == 2 || query.getStatus() == 3 || query.getStatus() == 4 || query.getStatus() == 5 || query.getStatus() == 6) {
                where.append(" and t.`status` = ").append(query.getStatus());
            } else {
                where.append(" and t.`status` = -1 ");
            }
            orderby = " ORDER BY dept_id ,  CASE  WHEN status = 2 THEN 1  WHEN status = 6 THEN 2  ELSE 3  END, " +
                    "create_time ";
        } else if (query.getSelectType() != null && query.getSelectType() == 3) {
            if (query.getStatus() == null) {
                where.append(" and (t.`status` = 3 or t.`status` = 5  or t.`status` = 6 ) ");
            } else if (query.getStatus() == 3 || query.getStatus() == 5 || query.getStatus() == 6) {
                where.append(" and t.`status` = ").append(query.getStatus());
            } else {
                where.append(" and t.`status` = -1 ");
            }
            orderby = " ORDER BY dept_id ,   CASE  WHEN status = 3 THEN 1  ELSE 2  END, create_time ";
        }
        //只查询当前填报部门数据
        where.append(" AND t.dept_id in (SELECT dept_id from t_budget_item_dept WHERE item_id = " + budgetItem.getId() + " )");
        PageUtils.startPage();
        return dynamicSqlService.dynamicSelectListSql(sql.append(where).append("  ) UNION ALL ").append(countSql).append(where).append(" GROUP BY t.dept_id  ) ").append(orderby).toString());
    }

    @Override
    public List<TableModel> itemEditField(Budget query) {
        List<TableModel> fieldList = new ArrayList<>();
        addFixedField(fieldList, false);
        List<TableModel> tableModels = tableModelService.selectTableModelListByItem(query.getItemId());
        fieldList.addAll(tableModels);
        Map<String, Object> info = new HashMap<>();
        if (StringUtil.isNotEmpty(query.getId())) {
            info = budgetInfo(query);
        }
        ReportingTask task = taskService.getById(query.getTaskId());
        for (TableModel field : fieldList) {
            if (field.getFieldName().equals("task_id")) {
                if (task != null) {
                    field.setValue(task.getId().toString());
                }
            } else if (field.getFieldName().equals("year")) {
                if (task != null) {
                    field.setValue(task.getYear().toString());
                }
            } else {
                field.setValue(info.get(field.getFieldName()) == null ? "" : info.get(field.getFieldName()).toString());
            }
        }
        return fieldList;
    }

    @Override
    public List<TableModel> itemAuditField(Budget query) {
        List<TableModel> fieldList = new ArrayList<>();
        addAuditField(fieldList);
        Map<String, Object> info = new HashMap<>();
        if (StringUtil.isNotEmpty(query.getId())) {
            info = budgetInfo(query);
        }
        for (TableModel field : fieldList) {
            if (field.getFieldName().equals("status")) {
                field.setValue(info.get(field.getFieldName()) == null ? "" : info.get(field.getFieldName()).toString());
            }
        }
        return fieldList;
    }

    @Override
    public AjaxResult add(Map<String, String> map) {
        if (map == null || map.get("itemId") == null || map.get("taskId") == null) {
            return AjaxResult.warn("参数不能为空！");
        }
        ReportingTask task = taskService.getById(map.get("taskId"));
        if (task == null) {
            return AjaxResult.warn("任务对象为空！");
        }
        if (task.getStartTime() == null || task.getStartTime().after(DateUtils.getNowDate())) {
            return AjaxResult.warn("任务未开始！");
        }
        if (task.getCompletionStatus().equals("2")) {
            return AjaxResult.warn("任务已结束！");
        }
        BudgetItem budgetItem = itemService.selectBudgetItemById(Long.parseLong(map.get("itemId")));
        if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
            return AjaxResult.warn("填报项目表数据有误！");
        }
        if (StringUtils.isEmpty(map.get("dept_id"))) {
            return AjaxResult.warn("填报部门有误！");
        }
        return transactionTemplate.execute(new TransactionCallback<AjaxResult>() {
            @Override
            public AjaxResult doInTransaction(TransactionStatus status) {
                try {
                    LoginUser user = SecurityUtils.getLoginUser();
                    String tableName = budgetItem.getTableName();
                    String uuid = IdUtils.simpleUUID();
                    StringBuilder tableSql = new StringBuilder();
                    tableSql.append("insert into t_reporting_table_").append(tableName).append(" ( id, task_id, " +
"`year`, dept_id, user_id, budget, budget_year, actual_incurred, estimated_incurred, " +
                      "cost_description, `status`, review_budget, review_reason, create_by, create_time, remark, " +
                       "del_flag )  values ( '").append(uuid).append("', ")
                            .append(map.get("taskId")).append(", ").append(task.getBudgetYear()).append(", ").append(StringUtils.isEmpty(map.get("dept_id")) ? null : map.get("dept_id")).append(", ").append(user.getUserId()).append(", ").append(StringUtils.isEmpty(map.get("budget")) ? null : map.get("budget")).append(", ")
                            .append(StringUtils.isEmpty(map.get("budget_year")) ? null : map.get("budget_year")).append(", ").append(StringUtils.isEmpty(map.get("actual_incurred")) ? null : map.get("actual_incurred")).append(", ").append(StringUtils.isEmpty(map.get("estimated_incurred")) ? null : map.get("estimated_incurred")).append(", ")
                            .append(StringUtils.isEmpty(map.get("cost_description")) ? null : "'" + map.get(
                                    "cost_description") + "'").append(", ").append(1).append(", ").append(StringUtils.isEmpty(map.get("review_budget")) ? null : map.get("review_budget")).append(", ").append(StringUtils.isEmpty(map.get("review_reason")) ? null : "'" + map.get("review_reason") + "'").append(", '").
                            append(user.getUsername()).append("', sysdate() , ").append(StringUtils.isEmpty(map.get(
                                    "remark")) ? null : "'" + map.get("remark") + "'").append(", '0' )");
                    if (dynamicSqlService.dynamicInsertSql(tableSql.toString()) < 0) {
                        return AjaxResult.error();
                    }
                    StringBuilder extensionSql = new StringBuilder();
                    extensionSql.append("insert into t_reporting_table_").append(tableName).append("_extension ( id ");
                    List<TableModel> fieldList = tableModelService.selectTableModelListByItem(budgetItem.getId());
                    for (TableModel field : fieldList) {
                        extensionSql.append(", ").append(field.getFieldName());
                    }
                    extensionSql.append(" ) values ( '").append(uuid).append("'");
                    for (TableModel field : fieldList) {
                        String date = map.get(field.getFieldName());
                        extensionSql.append(", ").append(StringUtils.isEmpty(date) ? null : "'" + date + "'");
                    }
                    extensionSql.append(" )");
                    if (dynamicSqlService.dynamicInsertSql(extensionSql.toString()) > 0) {
                        return AjaxResult.success();
                    } else {
                        throw new RuntimeException();
                    }
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("预算填报异常: {}", e.getMessage(), e);
                    return AjaxResult.error("预算填报异常:【" + e.getCause().getMessage() + "】");
                }
            }
        });
    }

    @Override
    @Transactional
    public AjaxResult update(Map<String, String> map) {
        BudgetItem budgetItem = itemService.getById(map.get("itemId"));
        if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
            return AjaxResult.error("填报项目数据有误！");
        }
        String tableName = budgetItem.getTableName();
        Map<String, Object> queryMap =
                dynamicSqlService.dynamicSelectInfoSql("select * from t_reporting_table_" + tableName + " where id = '" + map.get("id") + "'");
        if (queryMap != null && queryMap.get("status") != null && !queryMap.get("status").equals(1) && !queryMap.get(
                "status").equals(4)) {
            return AjaxResult.error("数据已提交，禁止修改");
        }
        List<TableModel> tableModels = tableModelService.selectTableModelListByItem(budgetItem.getId());
        addFixedField(tableModels, true);
        Map<String, Object> main = new HashMap<>();
        Map<String, Object> extension = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String values = StringUtils.isEmpty(entry.getValue()) ? null : entry.getValue();
            if (key.equals("id")) {
                if ((StringUtils.isEmpty(values))) {
                    return AjaxResult.error("非法数据");
                }
                main.put(key, values);
                extension.put(key, values);
            } else if (key.equals("dept_id")) {
                if ((StringUtils.isEmpty(values))) {
                    return AjaxResult.error("填报部门有误！");
                }
                main.put(key, values);
            } else {
                for (TableModel model : tableModels) {
                    if (model.getFieldName().equals(key)) {
                        if (model.getSourceTable().equals("main")) {
                            main.put(key, values);
                        } else if (model.getSourceTable().equals("extension")) {
                            extension.put(key, values);
                        }
                    }
                }
            }
        }
        main.put("update_by", SecurityUtils.getUsername());
        main.put("update_time", DateUtils.getNowDate());
        reportingMapper.updateModuleData("t_reporting_table_" + tableName, main);
        if (extension.size() > 1) {
            reportingMapper.updateModuleData("t_reporting_table_" + tableName + "_extension", extension);
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult delete(Budget budget) {
        try {
            BudgetItem budgetItem = itemService.getById(budget.getItemId());
            if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
                return AjaxResult.error("填报项目数据有误！");
            }
            String sql = "update t_reporting_table_" + budgetItem.getTableName() + " set del_flag='2' , update_by = " +
             "'" + SecurityUtils.getUsername() + "' , update_time = sysdate() where id = '" + budget.getId() + "'";
            if (dynamicSqlService.dynamicUpdateSql(sql) > 0) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error();
            }
        } catch (Exception e) {
            return AjaxResult.error("删除异常");
        }
    }

    @Override
    @Transactional
    public AjaxResult updateStatus(Budget budget) {
        BudgetItem budgetItem = itemService.getById(budget.getItemId());
        if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
            return AjaxResult.error("填报项目数据有误！");
        }
        if (budget.getStatus() == null) {
            return AjaxResult.error("操作状态有误！");
        }
        if (budget.getId() == null || budget.getId().isEmpty()) {
            return AjaxResult.error("选中数据有误！");
        }
        return transactionTemplate.execute(new TransactionCallback<AjaxResult>() {
            @Override
            public AjaxResult doInTransaction(TransactionStatus status) {
                try {
                    String[] ids = budget.getId().split(",");
                    for (String id : ids) {
                        if (StringUtils.isEmpty(id)) {
                            continue;
                        }
                        Map<String, Object> queryMap = dynamicSqlService.dynamicSelectInfoSql("select * from " +
                         "t_reporting_table_" + budgetItem.getTableName() + " where id = '" + id + "'");
                        if (budget.getStatus() == 2) {
                            if (queryMap != null && queryMap.get("status") != null && !queryMap.get("status").equals(1) && !queryMap.get("status").equals(4) && !queryMap.get("status").equals(3)) {
                                continue;
                            }
                        }
                        if (budget.getStatus() == 3 || budget.getStatus() == 4) {
                            if (queryMap != null && queryMap.get("status") != null && !queryMap.get("status").equals(2) && !queryMap.get("status").equals(6)) {
                                continue;
                            }
                        }
                        if (budget.getStatus() == 5 || budget.getStatus() == 6) {
                            if (queryMap != null && queryMap.get("status") != null && !queryMap.get("status").equals(3)) {
                                continue;
                            }
                        }
                        if (budget.getStatus() == 1) {
                            if (queryMap != null && queryMap.get("status") != null && !queryMap.get("status").equals(2)) {
                                continue;
                            }
                        }
                        String reviewReason = "";
                        if (StringUtils.isNotEmpty(budget.getReviewReason())) {
                            reviewReason = " review_reason = '" + budget.getReviewReason() + "' ,";
                        }
                        String remark = "";
                        if (StringUtils.isNotEmpty(budget.getRemark())) {
                            remark = " remark = '" + budget.getRemark() + "' ,";
                        }
                        String reviewBudget = "";
                        if (budget.getReviewBudget() != null) {
                            reviewBudget = " review_budget=" + budget.getReviewBudget() + " , ";
                        }

                        String sql =
                        "update t_reporting_table_" + budgetItem.getTableName() + " set status=" + budget.getStatus() + " , " + reviewBudget + reviewReason + remark + " update_by = '" + SecurityUtils.getUsername() + "' , update_time = sysdate() where id = '" + id + "'";
                        if (dynamicSqlService.dynamicUpdateSql(sql) > 0) {
                            ReportingLog reportingLog = new ReportingLog();
                            reportingLog.setItemId(budgetItem.getId());
                            reportingLog.setBudgetId(id);
                            reportingLog.setUserId(SecurityUtils.getUserId());
                            reportingLog.setDeptId(SecurityUtils.getDeptId());
                            reportingLog.setRemark(budget.getRemark());
                            reportingLog.setTime(DateUtils.getNowDate());
                            if (budget.getStatus() == 2) {
                                if (queryMap.get("status").equals(3)) {
                                    reportingLog.setStatus(5);//提交撤回
                                    reportingLog.setRemark("撤回审批通过操作");
                                } else {
                                    ReportingLog query = new ReportingLog();
                                    query.setBudgetId(id);
                                    query.setItemId(reportingLog.getItemId());
                                    int size = reportingLogService.selectReportingLogList(query).size();
                                    if (size > 0) {
                                        reportingLog.setStatus(4);
                                        reportingLog.setRemark("重新提交预算申请");
                                    } else {
                                        reportingLog.setStatus(1);
                                        reportingLog.setRemark("提交预算申请");
                                    }
                                }
                            } else if (budget.getStatus() == 3 || budget.getStatus() == 5) {
                                reportingLog.setStatus(2);
                            } else if (budget.getStatus() == 4 || budget.getStatus() == 6) {
                                reportingLog.setStatus(3);
                            } else if (budget.getStatus() == 1) {
                                reportingLog.setStatus(5);//提交撤回
                                reportingLog.setRemark("撤回提交审批申请");
                            }
                            if (reportingLogService.insertReportingLog(reportingLog) <= 0) {
                                throw new RuntimeException();
                            }
                        } else {
                            throw new RuntimeException();
                        }
                    }
                    return AjaxResult.success();
                } catch (Exception e) {
                    log.error("预算填报异常: {}", e.getMessage(), e);
                    status.setRollbackOnly();
                    return AjaxResult.error("预算填报异常！");
                }
            }
        });
    }

    @Override
    public AjaxResult reportingLog(Budget budget) {
        ReportingLog reportingLog = new ReportingLog();
        reportingLog.setItemId(budget.getItemId());
        reportingLog.setBudgetId(budget.getId());
        return AjaxResult.success(reportingLogService.selectReportingLogList(reportingLog));
    }

    @Override
    public AjaxResult saveVersion(ReportingVersion reportingVersion) {
        try {
            if (reportingVersion.getName() == null) {
                return AjaxResult.error("版本名称为空！");
            }
            ReportingTask task = taskService.selectReportingTaskById(reportingVersion.getTaskId());
            if (task == null) {
                return AjaxResult.error("任务信息有误！");
            }
            int count = 0;
            int wages = 0;
            BudgetItem queryItem = new BudgetItem();
            queryItem.setDelFlag("0");
            List<BudgetItem> items = itemService.selectBudgetItemList(queryItem);
            for (BudgetItem item : items) {
                if (StringUtils.isNotEmpty(item.getTableName())) {
                    String sql = "";
                    if (item.getTableName().equals("wages")) {
                        sql = "select count(1) as count  from t_reporting_table_" + item.getTableName() + " where " +
                         "task_id = " + task.getId() + " and del_flag = '0' and  status = 2 ";
                        Map<String, Object> data = dynamicSqlService.dynamicSelectInfoSql(sql);
                        if (data == null || data.get("count") == null) {
                            return AjaxResult.error("生成失败，请检查【" + item.getTableDisplayName() + "】预算表配置数据！");
                        }
                        wages = data.get("count") == null ? 0 : Integer.parseInt(data.get("count").toString());
                    } else {
                        sql = "select count(1) as count  from t_reporting_table_" + item.getTableName() + " where " +
                         "task_id = " + task.getId() + " and del_flag = '0' and  status = 3 ";
                        Map<String, Object> data = dynamicSqlService.dynamicSelectInfoSql(sql);
                        if (data == null || data.get("count") == null) {
                            return AjaxResult.error("生成失败，请检查【" + item.getTableDisplayName() + "】预算表配置数据！");
                        }
                        count += data.get("count") == null ? 0 : Integer.parseInt(data.get("count").toString());
                    }
                }
            }
            if (count > 0) {
                return AjaxResult.error("生成失败，存在待审查预算数据！");
            }
            if (wages > 0) {
                return AjaxResult.error("生成失败，存在待审查工资数据！");
            }
            reportingVersion.setYear(task.getBudgetYear());
            reportingVersion.setStatus("1");
            reportingVersion.setTaskId(task.getId());
            if (reportingVersionService.insertReportingVersion(reportingVersion) > 0) {
                Thread thread = new Thread(() -> {
                    executeSaveVersion(reportingVersion, task, items);
                });
                thread.start();
                return AjaxResult.success("版本生成中，请稍后从版本控制中查看");
            } else {
                return AjaxResult.error("生成失败！");
            }
        } catch (Exception e) {
            log.error("版本生成异常{}", e.getMessage(), e);
            reportingVersion.setStatus("3");
            reportingVersionService.updateReportingVersion(reportingVersion);
            return AjaxResult.error("版本生成异常！");
        }
    }

    @Override
    public List<Map<String, Object>> budgetAddList(Budget query, Long userId) {
        List<Map<String, Object>> list = new ArrayList<>();
        BudgetItem budgetItem = itemService.selectBudgetItemById(query.getItemId());
        if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
            return list;
        }
        ReportingTask task = taskService.selectReportingTaskById(query.getTaskId());
        if (task == null) {
            return list;
        }
        List<SysDept> depts = itemFieldDept(query, userId);
        List<Long> deptIds = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
        String tableName = budgetItem.getTableName();
        StringBuilder sql = new StringBuilder();
        sql.append("select t.id, t.task_id, t.`year`, t.dept_id, t.user_id, t.budget, t.budget_year, t" +
         ".actual_incurred, t.estimated_incurred, t.cost_description, t.`status`, t.review_budget, t.review_reason, t" +
          ".create_by, DATE_FORMAT(t.create_time, '%Y-%m-%d %H:%i:%s') AS create_time, t.update_by, DATE_FORMAT(t" +
           ".update_time, '%Y-%m-%d %H:%i:%s') AS update_time, t.remark, t.del_flag, u.user_name, d.dept_name, dd" +
            ".dict_label as status_name");
        List<TableModel> fieldList = tableModelService.selectTableModelListByItem(query.getItemId());
        for (TableModel field : fieldList) {
            sql.append(", ").append(" e.").append(field.getFieldName());
        }
        sql.append(" FROM t_reporting_table_").append(tableName)
                .append(" t LEFT JOIN t_reporting_table_").append(tableName).append("_extension e ON t.ID = e.ID")
                .append(" LEFT JOIN sys_user u ON u.user_id = t.user_id")
                .append(" LEFT JOIN sys_dept d ON d.dept_id = t.dept_id")
                .append(" LEFT JOIN sys_dict_data dd ON t.`status` = dd.dict_value AND dd.dict_type = 'budget_status'")
                .append(" WHERE t.task_id = ").append(query.getTaskId())
                .append(" AND t.del_flag = '0'");
        if (deptIds.size() > 0) {
            sql.append(" AND t.dept_id IN (");
            for (int i = 0; i < deptIds.size(); i++) {
                sql.append(deptIds.get(i));
                if (i < deptIds.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(") ");
        } else {
            sql.append(" AND t.dept_id = 0 ");
        }
        sql.append(" ORDER BY  CASE  WHEN t.status = 1 THEN 1  WHEN t.status = 4 THEN 2  ELSE 3  END, t.dept_id , t" +
                ".create_time");
        List<Map<String, Object>> dates = dynamicSqlService.dynamicSelectListSql(sql.toString());
        if (!dates.isEmpty()) {
            list.addAll(dates);
        }
        return list;
    }

    @Override
    public List<SysDept> itemFieldDept(Budget query, Long userId) {
        List<SysDept> depts = new ArrayList<>();
        BudgetItem budgetItem = itemService.selectBudgetItemById(query.getItemId());
        if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
            return depts;
        }
        SysUser user = userMapper.selectUserById(userId);
        if (user == null || user.getDept() == null) {
            return depts;
        }
        SysDept dept = user.getDept();
        List<SysDept> itemDepts = budgetItemDeptMapper.selectDeptListByItemId(budgetItem.getId());
        if (!SecurityUtils.isAdmin(userId)) {
            if (dept.getLevel() == 1) {
                SysDept queryDept = new SysDept();
                queryDept.setParentId(dept.getDeptId());
                List<SysDept> userDepts = deptMapper.selectDeptList(queryDept);
                List<SysDept> commonDepts = userDepts.stream()
                        .filter(userDept -> itemDepts.stream()
                                .anyMatch(itemDept -> itemDept.getDeptId().longValue() == userDept.getDeptId().longValue()))
                        .toList();
                depts.addAll(commonDepts);
            } else if (dept.getLevel() == 2) {
                if (itemDepts.stream().anyMatch(item -> item.getDeptId().longValue() == dept.getDeptId().longValue())) {
                    depts.add(deptMapper.selectDeptById(dept.getDeptId()));
                }
            }
        } else {
            depts = itemDepts;
        }
        return depts;
    }

    @Override
    public void export(HttpServletResponse response, Budget query, Long userId) {
        List<Map<String, Object>> titles = new ArrayList<>();
        List<TableModel> tableModels = new ArrayList<>();
        addFixedField(tableModels, false);
        TableModel queryModel = new TableModel();
        queryModel.setItemId(query.getItemId());
        List<TableModel> queryModels = tableModelService.selectTableModelList(queryModel);
        queryModels.removeIf(model -> model.getFieldName().equals("status") && model.getType() == 1);
        tableModels.addAll(queryModels);
        ReportingTask task = taskService.selectReportingTaskById(query.getTaskId());
        if (task != null && task.getYear() != null) {
            changeYearName(tableModels, task.getYear());
        }
        List<Map<String, Object>> datas = budgetExportList(query, userId);
        //if(query.getExportType()!=null && query.getExportType()==2){
        Map<String, Object> parentMap = new HashMap<>();
        parentMap.put("name", "企业名称");
        parentMap.put("key", "parentName");
        titles.add(parentMap);
        //}
        for (TableModel tableModel : tableModels) {
            Map<String, Object> map = new HashMap<>();
            String fieldName = tableModel.getFieldName();
            String fieldDisplayName = tableModel.getFieldDisplayName();
            if (task != null && task.getYear() != null) {
                if (fieldName.equals("budget")) {
                    fieldDisplayName = (task.getYear() + 1) + "年预算";
                } else if (fieldName.equals("budget_year")) {
                    fieldDisplayName = task.getYear() + "年预算";
                } else if (fieldName.equals("actual_incurred")) {
                    fieldDisplayName = task.getYear() + "年实际发生额";
                } else if (fieldName.equals("estimated_incurred")) {
                    fieldDisplayName = task.getYear() + "年预计发生费用";
                }
            }
            if (tableModel.getFieldType().equals("4") && tableModel.getSourceTable().equals("extension")) {
                map.put("name", fieldDisplayName);
                map.put("key", tableModel.getFieldName() + "Name");
            } else {
                map.put("name", fieldDisplayName);
                map.put("key", tableModel.getFieldName());
            }
            titles.add(map);
        }
        ExcelUtils.exportSingleHeaderExcel(response, titles, datas);
    }


    @Override
    public TableDataInfo budgetSummaryList(Budget query) {
        List<BudgetSummary> list = new ArrayList<>();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        BudgetItem budgetItem = itemService.selectBudgetItemById(query.getItemId());
        if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
            return rspData;
        }
        ReportingTask task = taskService.selectReportingTaskById(query.getTaskId());
        if (task == null) {
            return rspData;
        }
        String querySql = "";
        if (StringUtils.isNotEmpty(query.getDeptName())) {
            querySql = " and pd.dept_name like concat('%', '" + query.getDeptName() + "', '%') ";
        }
        String deptSql = "";
        List<SysDept> itemDepts = budgetItemDeptMapper.selectDeptListByItemId(budgetItem.getId());
        // 通过id获取当前用户
        List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            for (SysRole role : roles) {
                if (role.getRoleKey().equals("financialManagement")) {
                    SysUser queryUser = userMapper.selectUserById(SecurityUtils.getUserId());
                    String companyIds = queryUser.getCompanyIds();
                    List<String> companyIdsList = Arrays.asList(companyIds.split(","));
                    itemDepts = itemDepts.stream()
                            .filter(dept -> companyIdsList.contains(String.valueOf(dept.getParentId()))).toList();
                }
             }
        }
        if (itemDepts.size() > 0) {
            deptSql += " AND t.dept_id IN (";
            for (int i = 0; i < itemDepts.size(); i++) {
                deptSql += itemDepts.get(i).getDeptId();
                if (i < itemDepts.size() - 1) {
                    deptSql += ", ";
                }
            }
            deptSql += " ) ";
        } else {
            deptSql += " AND t.dept_id = 0 ";
        }
        String sql = "";
        if (query.getSelectType() != null && query.getSelectType() == 2) {
            sql = "SELECT pd.dept_name as deptName,pd.dept_id as deptId,SUM(budget) as budget,SUM(budget_year) as " +
             "budgetYear,SUM(actual_incurred) as actualIncurred,SUM(estimated_incurred) as estimatedIncurred , SUM" +
              "(CASE WHEN t.status = 2 THEN 1 ELSE 0 END) AS reportNumber FROM t_reporting_table_" + budgetItem.getTableName() + " t LEFT JOIN sys_dept d on t.dept_id = d.dept_id LEFT JOIN sys_dept pd on d.parent_id = pd.dept_id where t.del_flag = '0' and t.task_id = " + task.getId() + " and t.status != 1 and  t.status != 4 " + deptSql + querySql + " GROUP BY d.parent_id";
        } else if (query.getSelectType() != null && query.getSelectType() == 3) {
            sql = "SELECT pd.dept_name as deptName,pd.dept_id as deptId,SUM(budget) as budget,SUM(budget_year) as " +
             "budgetYear,SUM(actual_incurred) as actualIncurred,SUM(estimated_incurred) as estimatedIncurred , SUM" +
              "(CASE WHEN t.status = 3 THEN 1 ELSE 0 END) AS reportNumber FROM t_reporting_table_" + budgetItem.getTableName() + " t LEFT JOIN sys_dept d on t.dept_id = d.dept_id LEFT JOIN sys_dept pd on d.parent_id = pd.dept_id where t.del_flag = '0' and t.task_id = " + task.getId() + " and (t.status = 3 or  t.status = 5) " + deptSql + querySql + " GROUP BY d.parent_id";
        } else {
            return rspData;
        }
        PageUtils.startPage();
        List<Map<String, Object>> dates = dynamicSqlService.dynamicSelectListSql(sql);
        dates.forEach(date -> {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            BudgetSummary budgetSummary = new BudgetSummary();
            budgetSummary.setDeptId(Long.parseLong(date.get("deptId") == null ? "0" : date.get("deptId").toString()));
            budgetSummary.setDeptName(date.get("deptName") == null ? "" : date.get("deptName").toString());
            budgetSummary.setBudget(Double.parseDouble(date.get("budget") == null ? "0" :
date.get("budget").toString()));
            budgetSummary.setBudgetYear(Double.parseDouble(date.get("budgetYear") == null ? "0" : date.get(
                    "budgetYear").toString()));
            budgetSummary.setActualIncurred(Double.parseDouble(date.get("actualIncurred") == null ? "0" : date.get(
                    "actualIncurred").toString()));
            budgetSummary.setEstimatedIncurred(Double.parseDouble(date.get("estimatedIncurred") == null ? "0" :
                    date.get("estimatedIncurred").toString()));
            budgetSummary.setReportNumber(Integer.parseInt(date.get("reportNumber") == null ? "0" : date.get(
                    "reportNumber").toString()));
            list.add(budgetSummary);
        });
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(dates).getTotal());
        return rspData;
    }

    @Override
    public AjaxResult backfill(Budget query, Long userId) {
        ReportingTask task = taskService.selectReportingTaskById(query.getTaskId());
        if (task == null || task.getLastTask() == null) {
            return AjaxResult.error("任务数据有误！");
        }
        ReportingTask lastTask = taskService.selectReportingTaskById(task.getLastTask());
        if (lastTask == null) {
            return AjaxResult.error("关联任务失败！");
        }
        Long[] itemIds = null;
        Long[] deptIds = null;
        SysUser sysUser = userMapper.selectUserById(userId);
        if (sysUser == null || sysUser.getDept() == null) {
            return AjaxResult.error("用户数据有误！");
        }
        SysDept dept = sysUser.getDept();
        if (dept.getLevel() == 1) {
            deptIds = deptMapper.selectChildIds(dept.getDeptId());
            if (deptIds.length == 0) {
                return AjaxResult.warn("暂无相关数据");
            }
            itemIds = budgetItemDeptMapper.selectItemByDepts(deptIds);
        } else if (dept.getLevel() == 2) {
            deptIds = new Long[]{dept.getDeptId()};
            itemIds = budgetItemDeptMapper.selectItemByDepts(deptIds);
        } else {
            return AjaxResult.warn("暂无相关数据");
        }
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setDelFlag("0");
        budgetItem.setIds(itemIds);
        List<BudgetItem> items = itemService.selectBudgetItemList(budgetItem);
        Set<Long> delDeptIds = new HashSet<>();
        for (BudgetItem item : items) {
            String tableName = item.getTableName();
            if (StringUtil.isNotEmpty(tableName)) {
                for (Long deptId : deptIds) {
                    if (item.getReportingType().equals("1")) {
                        String sql = "select t.id, t.task_id, t.`year`, t.dept_id, t.user_id, t.budget, t" +
                         ".budget_year, t.actual_incurred, t.estimated_incurred, t.cost_description, t.`status`, t" +
                          ".review_budget, t.review_reason, t.create_by, DATE_FORMAT(t.create_time, '%Y-%m-%d " +
                           "%H:%i:%s') AS create_time, t.update_by, DATE_FORMAT(t.update_time, '%Y-%m-%d %H:%i:%s') " +
                            "AS update_time, t.remark, t.del_flag " +
                                "FROM  `t_reporting_table_" + tableName + "` t where t.task_id = " + task.getId() +
                                " and t.del_flag = 0 and t.dept_id = " + deptId;
                        List<Map<String, Object>> dates = dynamicSqlService.dynamicSelectListSql(sql);
                        if (dates != null && dates.size() > 0) {
                            delDeptIds.add(deptId);
                        }
                    } else if (item.getReportingType().equals("2")) {
                        if ("pipeline".equals(tableName)) {
                            TablePipeline pipeline = new TablePipeline();
                            pipeline.setTaskId(task.getId());
                            pipeline.setDeptId(deptId);
                            List<TablePipeline> dates = pipelineService.selectTablePipelineList(pipeline);
                            if (dates != null && dates.size() > 0) {
                                delDeptIds.add(deptId);
                            }
                        } else if ("station".equals(tableName)) { //场站工程
                            TableStation station = new TableStation();
                            station.setTaskId(task.getId());
                            station.setDeptId(deptId);
                            List<TableStation> dates = stationService.selectTableStationList(station);
                            if (dates != null && dates.size() > 0) {
                                delDeptIds.add(deptId);
                            }
                        } else if ("housing".equals(tableName)) { //房屋建设、装修
                            TableHousing housing = new TableHousing();
                            housing.setTaskId(task.getId());
                            housing.setDeptId(deptId);
                            List<TableHousing> dates = housingService.selectTableHousingList(housing);
                            if (dates != null && dates.size() > 0) {
                                delDeptIds.add(deptId);
                            }
                        } else if ("information_system".equals(tableName)) { //信息系统建设
                            TableInformationSystem informationSystem = new TableInformationSystem();
                            informationSystem.setTaskId(task.getId());
                            informationSystem.setDeptId(deptId);
                            List<TableInformationSystem> dates =
                            informationSystemService.selectTableInformationSystemList(informationSystem);
                            if (dates != null && dates.size() > 0) {
                                delDeptIds.add(deptId);
                            }
                        } else if ("research".equals(tableName)) { //科研计划
                            TableResearch research = new TableResearch();
                            research.setTaskId(task.getId());
                            research.setDeptId(deptId);
                            List<TableResearch> dates = researchService.selectTableResearchList(research);
                            if (dates != null && dates.size() > 0) {
                                delDeptIds.add(deptId);
                            }
                        } else if ("meter".equals(tableName)) { //计量器
                            TableMeter meter = new TableMeter();
                            meter.setTaskId(task.getId());
                            meter.setDeptId(deptId);
                            List<TableMeter> dates = meterService.selectTableMeterList(meter);
                            if (dates != null && dates.size() > 0) {
                                delDeptIds.add(deptId);
                            }
                        } else if ("charity".equals(tableName)) { //慈善公益
                            TableCharity charity = new TableCharity();
                            charity.setTaskId(task.getId());
                            charity.setDeptId(deptId);
                            List<TableCharity> dates = charityService.selectTableCharityList(charity);
                            if (dates != null && dates.size() > 0) {
                                delDeptIds.add(deptId);
                            }
                        } else if ("lowvalue".equals(tableName)) { //低值易耗
                            TableLowvalue lowvalue = new TableLowvalue();
                            lowvalue.setTaskId(task.getId());
                            lowvalue.setDeptId(deptId);
                            List<TableLowvalue> dates = lowvalueService.selectTableLowvalueList(lowvalue);
                            if (dates != null && dates.size() > 0) {
                                delDeptIds.add(deptId);
                            }
                        }
                    }
                }
            }
        }
        Set<Long> addDeptIds = new HashSet<>();
        for (Long deptId : deptIds) {
            if (!delDeptIds.contains(deptId)) {
                addDeptIds.add(deptId);
            }
        }
        if (addDeptIds.size() == 0) {
            return AjaxResult.warn("操作取消，已有填报数据！");
        }

        return transactionTemplate.execute(new TransactionCallback<AjaxResult>() {
            @Override
            public AjaxResult doInTransaction(TransactionStatus status) {
                try {
                    for (BudgetItem item : items) {
                        String tableName = item.getTableName();
                        if (StringUtil.isNotEmpty(tableName)) {
                            for (Long deptId : addDeptIds) {
                                if (item.getReportingType().equals("1")) {
                                    StringBuilder sql = new StringBuilder();
                                    sql.append("select t.id, t.task_id, t.`year`, t.dept_id, t.user_id, t.budget, t" +
                                     ".budget_year, t.actual_incurred, t.estimated_incurred, t.cost_description, t" +
                                      ".`status`, t.review_budget, t.review_reason, t.create_by, DATE_FORMAT(t" +
                                       ".create_time, '%Y-%m-%d %H:%i:%s') AS create_time, t.update_by, DATE_FORMAT(t" +
                                        ".update_time, '%Y-%m-%d %H:%i:%s') AS update_time, t.remark, t.del_flag ");
                                    List<TableModel> fieldList =
                                     tableModelService.selectTableModelListByItem(item.getId());
                                    for (TableModel field : fieldList) {
                                        sql.append(", ").append(" e.").append(field.getFieldName());
                                    }
                                    sql.append(" from t_reporting_table_").append(tableName).append(" t  LEFT JOIN " +
                                     "t_reporting_table_").append(tableName).append("_extension e ON t.ID = e.ID ").append(" where t.task_id = ").append(lastTask.getId()).append(" and t.del_flag = '0'    and t.`status` = 5  AND t.dept_id = " + deptId);
                                    List<Map<String, Object>> dates =
                                     dynamicSqlService.dynamicSelectListSql(sql.toString());
                                    if (dates != null && dates.size() > 0) {
                                        for (Map<String, Object> map : dates) {
                                            String uuid = IdUtils.simpleUUID();
                                            StringBuilder tableSql = new StringBuilder();
                                            tableSql.append("insert into t_reporting_table_").append(tableName).append(" ( id, task_id, `year`, dept_id, user_id, budget, budget_year, actual_incurred, estimated_incurred, cost_description, `status`, review_budget, review_reason, create_by, create_time, remark, del_flag )  values ( '").append(uuid).append("', ")
                                                    .append(task.getId()).append(", ").append(task.getBudgetYear()).append(", ").append(deptId).append(", ").append(sysUser.getUserId()).append(", ").append(map.get("budget") == null ? null : map.get("budget")).append(", ")
                                                    .append(map.get("budget_year") == null ? null : map.get(
                                                            "budget_year")).append(", ").append(map.get(
                                                                    "actual_incurred") == null ? null : map.get(
                                                                            "actual_incurred")).append(", ").append(map.get("estimated_incurred") == null ? null : map.get("estimated_incurred").toString()).append(", ")
                                                    .append(map.get("cost_description") == null ? null :
                                                     "'" + map.get("cost_description") + "'").append(", ").append(1).append(", ").append(map.get("review_budget") == null ? null : map.get("review_budget")).append(", ").append(map.get("review_reason") == null ? null : "'" + map.get("review_reason") + "'").append(", '").
                                                    append(sysUser.getUserName()).append("', sysdate() , ").append(map.get("remark") == null ? null : "'" + map.get("remark") + "'").append(", '0' )");
                                            if (dynamicSqlService.dynamicInsertSql(tableSql.toString()) < 0) {
                                                throw new RuntimeException();
                                            }
                                            StringBuilder extensionSql = new StringBuilder();
                                            extensionSql.append("insert into t_reporting_table_").append(tableName).append("_extension ( id ");
                                            for (TableModel field : fieldList) {
                                                extensionSql.append(", ").append(field.getFieldName());
                                            }
                                            extensionSql.append(" ) values ( '").append(uuid).append("'");
                                            for (TableModel field : fieldList) {
                                                Object date = map.get(field.getFieldName());
                                                extensionSql.append(", ").append(date == null ? null : "'" + date +
 "'");
                                            }
                                            extensionSql.append(" ) ");
                                            if (dynamicSqlService.dynamicInsertSql(extensionSql.toString()) <= 0) {
                                                throw new RuntimeException();
                                            }
                                        }
                                    }
                                } else if (item.getReportingType().equals("2")) {
                                    if ("pipeline".equals(tableName)) {
                                        TablePipeline pipeline = new TablePipeline();
                                        pipeline.setTaskId(lastTask.getId());
                                        pipeline.setDeptId(deptId);
                                        List<TablePipeline> dates = pipelineService.selectTablePipelineList(pipeline);
                                        for (TablePipeline date : dates) {
                                            date.setId(null);
                                            date.setTaskId(task.getId());
                                            date.setStatus(1);
                                            date.setCreateTime(new Date());
                                            date.setCreateBy(sysUser.getUserName());
                                            date.setUpdateTime(null);
                                            date.setUpdateBy(null);
                                        }
                                        pipelineService.saveBatch(dates);
                                    } else if ("station".equals(tableName)) { //场站工程
                                        TableStation station = new TableStation();
                                        station.setTaskId(lastTask.getId());
                                        station.setDeptId(deptId);
                                        List<TableStation> dates = stationService.selectTableStationList(station);
                                        for (TableStation date : dates) {
                                            date.setId(null);
                                            date.setTaskId(task.getId());
                                            date.setStatus(1);
                                            date.setCreateTime(new Date());
                                            date.setCreateBy(sysUser.getUserName());
                                            date.setUpdateTime(null);
                                            date.setUpdateBy(null);
                                        }
                                        stationService.saveBatch(dates);
                                    } else if ("housing".equals(tableName)) { //房屋建设、装修
                                        TableHousing housing = new TableHousing();
                                        housing.setTaskId(lastTask.getId());
                                        housing.setDeptId(deptId);
                                        List<TableHousing> dates = housingService.selectTableHousingList(housing);
                                        for (TableHousing date : dates) {
                                            date.setId(null);
                                            date.setTaskId(task.getId());
                                            date.setStatus(1);
                                            date.setCreateTime(new Date());
                                            date.setCreateBy(sysUser.getUserName());
                                            date.setUpdateTime(null);
                                            date.setUpdateBy(null);
                                        }
                                        housingService.saveBatch(dates);
                                    } else if ("information_system".equals(tableName)) { //信息系统建设
                                        TableInformationSystem informationSystem = new TableInformationSystem();
                                        informationSystem.setTaskId(lastTask.getId());
                                        informationSystem.setDeptId(deptId);
                                        List<TableInformationSystem> dates =
                                                informationSystemService.selectTableInformationSystemList(informationSystem);
                                        for (TableInformationSystem date : dates) {
                                            date.setId(null);
                                            date.setTaskId(task.getId());
                                            date.setStatus(1);
                                            date.setCreateTime(new Date());
                                            date.setCreateBy(sysUser.getUserName());
                                            date.setUpdateTime(null);
                                            date.setUpdateBy(null);
                                        }
                                        informationSystemService.saveBatch(dates);
                                    } else if ("research".equals(tableName)) { //科研计划
                                        TableResearch research = new TableResearch();
                                        research.setTaskId(lastTask.getId());
                                        research.setDeptId(deptId);
                                        List<TableResearch> dates = researchService.selectTableResearchList(research);
                                        for (TableResearch date : dates) {
                                            date.setId(null);
                                            date.setTaskId(task.getId());
                                            date.setStatus(1);
                                            date.setCreateTime(new Date());
                                            date.setCreateBy(sysUser.getUserName());
                                            date.setUpdateTime(null);
                                            date.setUpdateBy(null);
                                        }
                                        researchService.saveBatch(dates);
                                    } else if ("meter".equals(tableName)) { //计量器
                                        TableMeter meter = new TableMeter();
                                        meter.setTaskId(lastTask.getId());
                                        meter.setDeptId(deptId);
                                        List<TableMeter> dates = meterService.selectTableMeterList(meter);
                                        for (TableMeter date : dates) {
                                            date.setId(null);
                                            date.setTaskId(task.getId());
                                            date.setStatus(1);
                                            date.setCreateTime(new Date());
                                            date.setCreateBy(sysUser.getUserName());
                                            date.setUpdateTime(null);
                                            date.setUpdateBy(null);
                                        }
                                        meterService.saveBatch(dates);
                                    } else if ("charity".equals(tableName)) { //慈善公益
                                        TableCharity charity = new TableCharity();
                                        charity.setTaskId(lastTask.getId());
                                        charity.setDeptId(deptId);
                                        List<TableCharity> dates = charityService.selectTableCharityList(charity);
                                        for (TableCharity date : dates) {
                                            date.setId(null);
                                            date.setTaskId(task.getId());
                                            date.setStatus(1);
                                            date.setCreateTime(new Date());
                                            date.setCreateBy(sysUser.getUserName());
                                            date.setUpdateTime(null);
                                            date.setUpdateBy(null);
                                        }
                                        charityService.saveBatch(dates);
                                    } else if ("lowvalue".equals(tableName)) { //低值易耗
                                        TableLowvalue lowvalue = new TableLowvalue();
                                        lowvalue.setTaskId(lastTask.getId());
                                        lowvalue.setDeptId(deptId);
                                        List<TableLowvalue> dates = lowvalueService.selectTableLowvalueList(lowvalue);
                                        for (TableLowvalue date : dates) {
                                            date.setId(null);
                                            date.setTaskId(task.getId());
                                            date.setStatus(1);
                                            date.setCreateTime(new Date());
                                            date.setCreateBy(sysUser.getUserName());
                                            date.setUpdateTime(null);
                                            date.setUpdateBy(null);
                                        }
                                        lowvalueService.saveBatch(dates);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("预算填报异常: {}", e.getMessage(), e);
                    return AjaxResult.error("预算填报异常:【" + e.getCause().getMessage() + "】");
                }
                return AjaxResult.success();
            }
        });
    }

    @Override
    public Map<String, Object> itemCostByDept(Budget query) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal actualIncurred = BigDecimal.ZERO;
        BigDecimal estimatedIncurred = BigDecimal.ZERO;
        ReportingTask reportingTask = taskService.selectReportingTaskById(query.getTaskId());
        BudgetItem budgetItem = itemService.selectBudgetItemById(query.getItemId());
        if (reportingTask != null && budgetItem != null) {
            ActualCosts queryActualCosts = new ActualCosts();
            queryActualCosts.setYear(reportingTask.getYear() == null ? 0 : reportingTask.getYear());
            queryActualCosts.setSubjectId(budgetItem.getSubjectId() == null ? 0L : budgetItem.getSubjectId());
            queryActualCosts.setDeptId(query.getDeptId() == null ? 0L : query.getDeptId());
            ActualCosts actualCost = actualCostsService.selectLast(queryActualCosts);
            if (actualCost != null) {
                actualIncurred = actualCost.getActualIncurred();
                estimatedIncurred = actualCost.getEstimatedIncurred();
            }
        }
        map.put("actual_incurred", actualIncurred);
        map.put("estimated_incurred", estimatedIncurred);
        return map;
    }

    @Override
    @Transactional
    public AjaxResult clickAudit(Budget budget) {
        if (budget.getStatus() == null || (budget.getStatus() != 5 && budget.getStatus() != 6)) {
            return AjaxResult.error("审核状态有误！");
        }
        ReportingTask task = taskService.getById(budget.getTaskId());
        if (task == null) {
            return AjaxResult.error("任务数据有误！");
        }
        BudgetItem item = itemService.getById(budget.getItemId());
        List<BudgetItem> items = new ArrayList<>();
        if (item != null) {
            items.add(item);
        } else {
            BudgetItem queryItem = new BudgetItem();
            queryItem.setDelFlag("0");
            items.addAll(itemService.selectBudgetItemList(queryItem));
        }
        return transactionTemplate.execute(new TransactionCallback<AjaxResult>() {
            @Override
            public AjaxResult doInTransaction(TransactionStatus status) {
                try {
                    Integer type = budget.getType();
                    for (BudgetItem item : items) {
                        if (type != null && type == 2) {
                            if (item.getId().longValue() != 1L) {
                                continue;
                            }
                        } else {
                            if (item.getId().longValue() == 1L) {
                                continue;
                            }
                        }
                        List<Map<String, Object>> datas = new ArrayList<>();
                        if (item.getReportingType().equals("3")) {
                            datas =
                                    dynamicSqlService.dynamicSelectListSql("select * from t_reporting_table_" + item.getTableName() + " WHERE task_id = " + task.getId() + " and status = 2 and del_flag = '0' ");
                        } else {
                            datas =
                                    dynamicSqlService.dynamicSelectListSql("select * from t_reporting_table_" + item.getTableName() + " WHERE task_id = " + task.getId() + " and status = 3 and del_flag = '0' ");
                        }
                        if (datas.size() > 0) {
                            for (Map<String, Object> data : datas) {
                                if (data.get("id") == null) {
                                    continue;
                                }
                                String id = data.get("id").toString();
                                String sql =
                                        "update t_reporting_table_" + item.getTableName() + " set status=" + budget.getStatus() + " , update_by = '" + SecurityUtils.getUsername() + "' , update_time = sysdate() where id = '" + id + "'";
                                if (dynamicSqlService.dynamicUpdateSql(sql) > 0) {
                                    ReportingLog reportingLog = new ReportingLog();
                                    reportingLog.setItemId(item.getId());
                                    reportingLog.setBudgetId(id);
                                    reportingLog.setUserId(SecurityUtils.getUserId());
                                    reportingLog.setDeptId(SecurityUtils.getDeptId());
                                    reportingLog.setRemark(budget.getRemark());
                                    reportingLog.setTime(DateUtils.getNowDate());
                                    if (budget.getStatus() == 5) {
                                        reportingLog.setStatus(2);
                                    } else if (budget.getStatus() == 6) {
                                        reportingLog.setStatus(3);
                                    }
                                    if (reportingLogService.insertReportingLog(reportingLog) <= 0) {
                                        throw new RuntimeException();
                                    }
                                } else {
                                    throw new RuntimeException();
                                }
                            }
                        }
                    }
                    return AjaxResult.success();
                } catch (Exception e) {
                    log.error("预算填报异常: {}", e.getMessage(), e);
                    status.setRollbackOnly();
                    return AjaxResult.error("预算填报异常！");
                }
            }
        });
    }

    @Override
    public AjaxResult rejectByItem(Budget budget, Long userId) {
        ReportingTask task = taskService.getById(budget.getTaskId());
        if (task == null) {
            return AjaxResult.error("所属任务数据有误！");
        }
        Integer type = budget.getSelectType();
        if (type != 2 && type != 3) {
            return AjaxResult.error("非法数据！");
        }
        BudgetItem queryItem = new BudgetItem();
        queryItem.setIds(budget.getItemIds());
        queryItem.setDelFlag("0");
        List<BudgetItem> items = itemService.selectBudgetItemList(queryItem);
        if (items == null || items.isEmpty()) {
            return AjaxResult.error("选中预算表数据有误！");
        }
        Long taskId = task.getId();
        return transactionTemplate.execute(new TransactionCallback<AjaxResult>() {
            @Override
            public AjaxResult doInTransaction(TransactionStatus status) {
                try {
                    String sql = "";
                    for (BudgetItem item : items) {
                        String tableName = item.getTableName();
                        if (StringUtil.isNotEmpty(tableName)) {
                            try {
                                if (type == 2) {
                                    sql = "UPDATE t_reporting_table_" + tableName + " SET status = 4 WHERE task_id = "
                                            + taskId + " and del_flag = '0' and (status = 2 or status = 6) ";
                                } else if (type == 3) {
                                    sql = "UPDATE t_reporting_table_" + tableName + " SET status = 6 WHERE task_id = "
                                     + taskId + " and del_flag = '0' and status = 5 ";
                                }
                                dynamicSqlService.dynamicUpdateSql(sql);
                            } catch (Exception e) {
                                throw new RuntimeException();
                            }
                        }
                    }
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("一键驳回异常: {}", e.getMessage(), e);
                    return AjaxResult.error("一键驳回异常:【" + e.getCause().getMessage() + "】");
                }
                return AjaxResult.success();
            }
        });
    }

    @Override
    public AjaxResult checkStatus() {
        ReportingTask task = taskService.getLast();
        if (task != null && task.getCompletionStatus().equals("1")) {
            SysUser sysUser = userMapper.selectUserById(SecurityUtils.getUserId());
            Long deptId = sysUser.getDeptId();
            List<SysRole> roles = sysUser.getRoles();
            for (SysRole role : roles) {
                if (role.getRoleKey().equals("businessFilling")) {
                    ReportingTaskDept reportingTaskDept = new ReportingTaskDept();
                    reportingTaskDept.setTaskId(task.getId());
                    reportingTaskDept.setDeptId(deptId);
                    List<ReportingTaskDept> reportingTaskDepts =
                            reportingTaskDeptService.selectReportingTaskDeptList(reportingTaskDept);
                    if (reportingTaskDepts.size() == 0) {
                        List<BudgetItem> items = budgetItemDeptMapper.selectBudgetItemDeptListByDeptId(deptId);
                        for (BudgetItem item : items) {
                            String sql = "SELECT * FROM `t_reporting_table_" + item.getTableName() + "` where dept_id" +
                             " = " + deptId + " and del_flag = 0 and task_id = " + task.getId();
                            if (dynamicSqlService.dynamicSelectListSql(sql).size() == 0) {
                                return AjaxResult.success(task).put("status", 1);
                            }
                        }
                    }
                }
            }
        }
        return AjaxResult.success(task).put("status", 2);
    }

    @Override
    public AjaxResult updateTaskStatus(Budget budget) {
        ReportingTaskDept reportingTaskDept = new ReportingTaskDept();
        reportingTaskDept.setTaskId(budget.getTaskId());
        reportingTaskDept.setDeptId(SecurityUtils.getDeptId());
        return reportingTaskDeptService.insertReportingTaskDept(reportingTaskDept) >= 0 ? AjaxResult.success() :
 AjaxResult.error();
    }

    public void executeSaveVersion(ReportingVersion reportingVersion, ReportingTask task, List<BudgetItem> items) {
        AtomicBoolean transactionFailed = new AtomicBoolean(false);
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    Long versionId = reportingVersion.getId();
                    for (BudgetItem item : items) {
                        String tableName = item.getTableName();
                        if (StringUtils.isNotEmpty(tableName)) {
                            if (item.getReportingType().equals("1")) {
                                String sql = "INSERT INTO t_reporting_table_" + tableName + "_version (budget_id," +
                                        "task_id,YEAR,dept_id,user_id,budget,budget_year,actual_incurred,estimated_incurred," +
                                        "cost_description,review_budget,review_reason,version_id,create_by,create_time) " +
                                        "SELECT id AS budget_id,task_id,YEAR,dept_id,user_id,budget,budget_year," +
                                        "actual_incurred,estimated_incurred,cost_description,review_budget," +
                                        "review_reason," + versionId + " AS version_id,create_by,create_time " +
                                        "FROM t_reporting_table_" + tableName + " WHERE `status` = 5 and del_flag = 0" +
                                         "  and  task_id = " + task.getId();
                                dynamicSqlService.dynamicInsertSql(sql);
                                StringBuilder insertSql = new StringBuilder();
                                StringBuilder selectSql = new StringBuilder();
                                insertSql.append("INSERT INTO t_reporting_table_").append(tableName).append(
                                        "_extension_version (budget_id, version_id ");
                                selectSql.append(" SELECT  e.id, ").append(versionId).append(" as version_id");
                                List<TableModel> tableModels =
                                 tableModelService.selectTableModelListByItem(item.getId());
                                for (TableModel tableField : tableModels) {
                                    insertSql.append(", ").append(tableField.getFieldName());
                                    selectSql.append(", e.").append(tableField.getFieldName());
                                }
                                insertSql.append(") ");
                                selectSql.append(" FROM t_reporting_table_").append(tableName).append("_extension e " +
                                        "LEFT JOIN t_reporting_table_").append(tableName).append(" t on t.id = e.id  WHERE  " +
                                        "t.status = 5 and t.del_flag = 0 and t.task_id = ").append(task.getId());
                                insertSql.append(selectSql);
                                dynamicSqlService.dynamicInsertSql(insertSql.toString());
                            } else if (item.getReportingType().equals("2")) {
                                if ("pipeline".equals(tableName)) {
                                    TablePipeline pipeline = new TablePipeline();
                                    pipeline.setTaskId(task.getId());
                                    pipeline.setStatus(5);
                                    List<TablePipeline> dates = pipelineService.selectTablePipelineList(pipeline);
                                    List<TablePipelineVersion> versions = new ArrayList<>();
                                    for (TablePipeline date : dates) {
                                        TablePipelineVersion version = new TablePipelineVersion();
                                        BeanUtils.copyBeanProp(version, date);
                                        version.setId(null);
                                        version.setVersionId(versionId);
                                        versions.add(version);
                                    }
                                    pipelineVersionService.saveBatch(versions);
                                } else if ("station".equals(tableName)) { //场站工程
                                    TableStation station = new TableStation();
                                    station.setTaskId(task.getId());
                                    station.setStatus(5);
                                    List<TableStation> dates = stationService.selectTableStationList(station);
                                    List<TableStationVersion> versions = new ArrayList<>();
                                    for (TableStation date : dates) {
                                        TableStationVersion version = new TableStationVersion();
                                        BeanUtils.copyBeanProp(version, date);
                                        version.setId(null);
                                        version.setVersionId(versionId);
                                        versions.add(version);
                                    }
                                    stationVersionService.saveBatch(versions);
                                } else if ("housing".equals(tableName)) { //房屋建设、装修
                                    TableHousing housing = new TableHousing();
                                    housing.setTaskId(task.getId());
                                    housing.setStatus(5);
                                    List<TableHousing> dates = housingService.selectTableHousingList(housing);
                                    List<TableHousingVersion> versions = new ArrayList<>();
                                    for (TableHousing date : dates) {
                                        TableHousingVersion version = new TableHousingVersion();
                                        BeanUtils.copyBeanProp(version, date);
                                        version.setId(null);
                                        version.setVersionId(versionId);
                                        versions.add(version);
                                    }
                                    housingVersionService.saveBatch(versions);
                                } else if ("information_system".equals(tableName)) { //信息系统建设
                                    TableInformationSystem informationSystem = new TableInformationSystem();
                                    informationSystem.setTaskId(task.getId());
                                    informationSystem.setStatus(5);
                                    List<TableInformationSystem> dates =
                                     informationSystemService.selectTableInformationSystemList(informationSystem);
                                    List<TableInformationSystemVersion> versions = new ArrayList<>();
                                    for (TableInformationSystem date : dates) {
                                        TableInformationSystemVersion version = new TableInformationSystemVersion();
                                        BeanUtils.copyBeanProp(version, date);
                                        version.setId(null);
                                        version.setVersionId(versionId);
                                        versions.add(version);
                                    }
                                    informationSystemVersionService.saveBatch(versions);
                                } else if ("research".equals(tableName)) { //科研计划
                                    TableResearch research = new TableResearch();
                                    research.setTaskId(task.getId());
                                    research.setStatus(5);
                                    List<TableResearch> dates = researchService.selectTableResearchList(research);
                                    List<TableResearchVersion> versions = new ArrayList<>();
                                    for (TableResearch date : dates) {
                                        TableResearchVersion version = new TableResearchVersion();
                                        BeanUtils.copyBeanProp(version, date);
                                        version.setId(null);
                                        version.setVersionId(versionId);
                                        versions.add(version);
                                    }
                                    researchVersionService.saveBatch(versions);
                                } else if ("meter".equals(tableName)) { //计量器
                                    TableMeter meter = new TableMeter();
                                    meter.setTaskId(task.getId());
                                    meter.setStatus(5);
                                    List<TableMeter> dates = meterService.selectTableMeterList(meter);
                                    List<TableMeterVersion> versions = new ArrayList<>();
                                    for (TableMeter date : dates) {
                                        TableMeterVersion version = new TableMeterVersion();
                                        BeanUtils.copyBeanProp(version, date);
                                        version.setId(null);
                                        version.setVersionId(versionId);
                                        versions.add(version);
                                    }
                                    meterVersionService.saveBatch(versions);
                                } else if ("charity".equals(tableName)) { //慈善公益
                                    TableCharity charity = new TableCharity();
                                    charity.setTaskId(task.getId());
                                    charity.setStatus(5);
                                    List<TableCharity> dates = charityService.selectTableCharityList(charity);
                                    List<TableCharityVersion> versions = new ArrayList<>();
                                    for (TableCharity date : dates) {
                                        TableCharityVersion version = new TableCharityVersion();
                                        BeanUtils.copyBeanProp(version, date);
                                        version.setId(null);
                                        version.setVersionId(versionId);
                                        versions.add(version);
                                    }
                                    charityVersionService.saveBatch(versions);
                                } else if ("lowvalue".equals(tableName)) { //低值易耗
                                    TableLowvalue lowvalue = new TableLowvalue();
                                    lowvalue.setTaskId(task.getId());
                                    lowvalue.setStatus(5);
                                    List<TableLowvalue> dates = lowvalueService.selectTableLowvalueList(lowvalue);
                                    List<TableLowvalueVersion> versions = new ArrayList<>();
                                    for (TableLowvalue date : dates) {
                                        TableLowvalueVersion version = new TableLowvalueVersion();
                                        BeanUtils.copyBeanProp(version, date);
                                        version.setId(null);
                                        version.setVersionId(versionId);
                                        versions.add(version);
                                    }
                                    lowvalueVersionService.saveBatch(versions);
                                }
                            } else if (item.getReportingType().equals("3") && item.getTableName().equals("wages")) {
                                TableWages wages = new TableWages();
                                wages.setTaskId(task.getId());
                                wages.setStatus(5);
                                List<TableWages> dates = tableWagesService.selectTableWagesList(wages);
                                List<TableWagesVersion> versions = new ArrayList<>();
                                for (TableWages date : dates) {
                                    TableWagesVersion version = new TableWagesVersion();
                                    BeanUtils.copyBeanProp(version, date);
                                    version.setId(null);
                                    version.setVersionId(versionId);
                                    versions.add(version);
                                }
                                tableWagesVersionService.saveBatch(versions);
                            }
                        }
                    }
                } catch (Exception e) {
                    transactionFailed.set(true);
                    status.setRollbackOnly();
                    log.error("版本数据保存异常{}", e.getMessage(), e);
                }
                return null;
            }
        });
        if (transactionFailed.get()) {
            reportingVersion.setStatus("3");
            reportingVersionService.updateReportingVersion(reportingVersion);
        } else {
            reportingVersion.setStatus("2");
            reportingVersionService.updateReportingVersion(reportingVersion);
        }
    }

    /**
     * 添加固定字段
     */
    public void addFixedField(List<TableModel> fieldList, boolean flag) {
        TableModel field = new TableModel();
        field.setFieldName("dept_name");
        field.setFieldDisplayName("填报部门");
        field.setFieldType("2");
        field.setFieldDisplay("1");
        field.setFieldRequired("0");
        field.setFieldQuery("1");
        field.setSourceTable("other");
        fieldList.add(field);
        if (flag) {
            field = new TableModel();
            field.setFieldName("budget");
            field.setFieldDisplayName("费用预算（元）");
            field.setFieldType("3");
            field.setFieldLength(10);
            field.setFieldDisplay("0");
            field.setFieldRequired("0");
            field.setFieldQuery("1");
            field.setSourceTable("main");
            fieldList.add(field);

            field = new TableModel();
            field.setFieldName("budget_year");
            field.setFieldDisplayName("本年预算（元）");
            field.setFieldType("3");
            field.setFieldLength(10);
            field.setFieldDisplay("0");
            field.setFieldRequired("1");
            field.setFieldQuery("1");
            field.setSourceTable("main");
            fieldList.add(field);

            field = new TableModel();
            field.setFieldName("actual_incurred");
            field.setFieldDisplayName("本年实际发生额（元）");
            field.setFieldType("3");
            field.setFieldLength(10);
            field.setFieldDisplay("1");
            field.setFieldRequired("1");
            field.setFieldQuery("1");
            field.setSourceTable("main");
            fieldList.add(field);

            field = new TableModel();
            field.setFieldName("estimated_incurred");
            field.setFieldDisplayName("全年预计发生费用（元）");
            field.setFieldType("3");
            field.setFieldLength(10);
            field.setFieldDisplay("1");
            field.setFieldRequired("0");
            field.setFieldQuery("1");
            field.setSourceTable("main");
            fieldList.add(field);
        }

        field = new TableModel();
        field.setFieldName("status_name");
        field.setFieldDisplayName("填报状态");
        field.setFieldType("2");
        field.setFieldDisplay("1");
        field.setFieldRequired("0");
        field.setFieldQuery("1");
        field.setSourceTable("other");
        fieldList.add(field);

    }

    public void addAuditField(List<TableModel> fieldList) {
        TableModel field = new TableModel();
        field.setFieldName("status");
        field.setFieldDisplayName("状态");
        field.setFieldType("1");
        field.setFieldLength(1);
        field.setFieldDisplay("0");
        field.setFieldRequired("0");
        field.setSourceTable("main");
        fieldList.add(field);

        field = new TableModel();
        field.setFieldName("remark");
        field.setFieldDisplayName("审核意见");
        field.setFieldType("1");
        field.setFieldLength(255);
        field.setFieldDisplay("1");
        field.setFieldRequired("0");
        field.setSourceTable("main");
        fieldList.add(field);
    }

    public void addFieldDict(List<TableModel> fieldList) {
        fieldList.forEach(field -> {
            if (field.getFieldType().equals("4")) {
                field.setDictDatas(dictDataMapper.selectDictDataByTypeId(field.getDictId()));
            }
        });
    }

    public Map<String, Object> budgetInfo(Budget query) {
        Map<String, Object> map = new HashMap<>();
        BudgetItem budgetItem = itemService.selectBudgetItemById(query.getItemId());
        if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
            return map;
        }
        String tableName = budgetItem.getTableName();
        StringBuilder sql = new StringBuilder();
        sql.append("select t.id, t.task_id, t.`year`, t.dept_id, t.user_id, t.budget, t.budget_year, t" +
                ".actual_incurred, t.estimated_incurred, t.cost_description, t.`status`, t.review_budget, t.review_reason, t" +
          ".create_by, DATE_FORMAT(t.create_time, '%Y-%m-%d %H:%i:%s') AS create_time, t.update_by, DATE_FORMAT(t" +
           ".update_time, '%Y-%m-%d %H:%i:%s') AS update_time, t.remark, t.del_flag ");
        List<TableModel> fieldList = tableModelService.selectTableModelListByItem(query.getItemId());
        for (TableModel field : fieldList) {
            sql.append(" , ").append(" e.").append(field.getFieldName());
        }
        sql.append(" from t_reporting_table_").append(tableName).append(" t  LEFT JOIN t_reporting_table_").append(tableName).append("_extension e ON t.ID = e.ID").append(" where t.id = '").append(query.getId()).append("'");
        return dynamicSqlService.dynamicSelectInfoSql(sql.toString());
    }

    private List<Map<String, Object>> budgetExportList(Budget query, Long userId) {
        List<Map<String, Object>> list = new ArrayList<>();
        BudgetItem budgetItem = itemService.selectBudgetItemById(query.getItemId());
        if (budgetItem == null || StringUtil.isEmpty(budgetItem.getTableName())) {
            return list;
        }
        ReportingTask task = taskService.selectReportingTaskById(query.getTaskId());
        if (task == null) {
            return list;
        }
        List<TableModel> fieldList = tableModelService.selectTableModelListByItem(query.getItemId());
        String tableName = budgetItem.getTableName();
        if (query.getExportType() != null && query.getExportType() == 2) {
            //只查询当前填报部门数据
            String deptSql =
                    " AND t.dept_id in (SELECT dept_id from t_budget_item_dept WHERE item_id = " + budgetItem.getId() + " ) ";
            String parentSql = "";
            if (query.getSelectType() != null && query.getSelectType() == 2) {
                parentSql = "SELECT pd.dept_name as parentName,pd.dept_id as parentId,SUM(budget) as budget,SUM" +
                 "(budget_year) as budget_year,SUM(actual_incurred) as actual_incurred,SUM(estimated_incurred) as " +
                        "estimated_incurred FROM t_reporting_table_" + budgetItem.getTableName() + " t LEFT JOIN sys_dept d" +
                   " on t.dept_id = d.dept_id LEFT JOIN sys_dept pd on d.parent_id = pd.dept_id where t.del_flag = " +
                    "'0' and t.task_id = " + task.getId() + " and t.status != 1 and  t.status != 4 " + deptSql + " " +
                     "GROUP BY d.parent_id";
            } else if (query.getSelectType() != null && query.getSelectType() == 3) {
                parentSql = "SELECT pd.dept_name as parentName,pd.dept_id as parentId,SUM(budget) as budget,SUM" +
                 "(budget_year) as budget_year,SUM(actual_incurred) as actual_incurred,SUM(estimated_incurred) as " +
                  "estimated_incurred FROM t_reporting_table_" + budgetItem.getTableName() + " t LEFT JOIN sys_dept d" +
                   " on t.dept_id = d.dept_id LEFT JOIN sys_dept pd on d.parent_id = pd.dept_id where t.del_flag = " +
                        "'0' and t.task_id = " + task.getId() + " and (t.status = 3 or  t.status = 5) " + deptSql + " " +
                     "GROUP BY d.parent_id";
            } else {
                return list;
            }
            List<Map<String, Object>> parentList = dynamicSqlService.dynamicSelectListSql(parentSql);
            if (parentList != null && !parentList.isEmpty()) {
                for (Map<String, Object> parent : parentList) {
                    list.add(parent);
                    StringBuilder sql = new StringBuilder();
                    sql.append("select t.id, t.task_id, t.`year`, t.dept_id, t.user_id, t.budget, t.budget_year, t" +
                     ".actual_incurred, t.estimated_incurred, t.cost_description, t.`status`, t.review_budget, t" +
                      ".review_reason, t.create_by, DATE_FORMAT(t.create_time, '%Y-%m-%d %H:%i:%s') AS create_time, t" +
                            ".update_by, DATE_FORMAT(t.update_time, '%Y-%m-%d %H:%i:%s') AS update_time, t.remark, t" +
                        ".del_flag, u.user_name, d.dept_name, dd.dict_label as status_name");
                    for (TableModel field : fieldList) {
                        if (field.getFieldType().equals("4")) {
                            sql.append(", ").append(" e.").append(field.getFieldName()).append(", ").append(field.getFieldName() + ".dict_label as ").append(field.getFieldName() + "Name");
                        } else {
                            sql.append(", ").append(" e.").append(field.getFieldName());
                        }
                    }
                    sql.append(" FROM t_reporting_table_").append(tableName)
                            .append(" t LEFT JOIN t_reporting_table_").append(tableName).append("_extension e ON t.ID" +
                             " = e.ID")
                            .append(" LEFT JOIN sys_user u ON u.user_id = t.user_id")
                            .append(" LEFT JOIN sys_dept d ON d.dept_id = t.dept_id")
                            .append(" LEFT JOIN sys_dict_data dd ON t.`status` = dd.dict_value AND dd.dict_type = " +
                             "'budget_status'");
                    for (TableModel field : fieldList) {
                        if (field.getFieldType().equals("4")) {
                            String dictType = StringUtils.isEmpty(field.getDictType()) ? " abc " : field.getDictType();
                            sql.append(" LEFT JOIN sys_dict_data " + field.getFieldName() + " ON e." + field.getFieldName() + " = " + field.getFieldName() + ".dict_value AND " + field.getFieldName() + ".dict_type = '" + dictType + "'");
                        }
                    }
                    sql.append(" WHERE t.task_id = ").append(query.getTaskId()).append(" AND t.del_flag = '0'");
                    if (query.getSelectType() != null && query.getSelectType() == 2) {//职能岗导出
                        sql.append(" and (t.`status` = 2 or t.`status` = 3 or t.`status` = 5 or t.`status` = 6  ) ");
                    } else if (query.getSelectType() != null && query.getSelectType() == 3) {//业财岗导出
                        sql.append(" and (t.`status` = 3 or t.`status` = 5  ) ");
                    }
                    sql.append(" AND d.parent_id = ").append(parent.get("parentId"));
                    //只查询当前填报部门数据
                    sql.append(" AND t.dept_id in (SELECT dept_id from t_budget_item_dept WHERE item_id = " + budgetItem.getId() + " ) ");
                    sql.append("order by t.dept_id");
                    List<Map<String, Object>> dates = dynamicSqlService.dynamicSelectListSql(sql.toString());
                    if (!dates.isEmpty()) {
                        list.addAll(dates);
                    }
                }
            } else {
                return list;
            }
        } else {
            List<SysDept> depts = itemFieldDept(query, userId);
            List<Long> deptIds = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
            StringBuilder sql = new StringBuilder();
            sql.append("select pd.dept_name as parentName,t.id, t.task_id, t.`year`, t.dept_id, t.user_id, t.budget, " +
             "t.budget_year, t.actual_incurred, t.estimated_incurred, t.cost_description, t.`status`, t" +
              ".review_budget, t.review_reason, t.create_by, DATE_FORMAT(t.create_time, '%Y-%m-%d %H:%i:%s') AS " +
 "create_time, t.update_by, DATE_FORMAT(t.update_time, '%Y-%m-%d %H:%i:%s') AS update_time, t.remark, t" +
                ".del_flag, u.user_name, d.dept_name, dd.dict_label as status_name");
            for (TableModel field : fieldList) {
                if (field.getFieldType().equals("4")) {
                    sql.append(", ").append(" e.").append(field.getFieldName()).append(", ").append(field.getFieldName() + ".dict_label as ").append(field.getFieldName() + "Name");
                } else {
                    sql.append(", ").append(" e.").append(field.getFieldName());
                }
            }
            sql.append(" FROM t_reporting_table_").append(tableName)
                    .append(" t LEFT JOIN t_reporting_table_").append(tableName).append("_extension e ON t.ID = e.ID")
                    .append(" LEFT JOIN sys_user u ON u.user_id = t.user_id")
                    .append(" LEFT JOIN sys_dept d ON d.dept_id = t.dept_id")
                    .append(" LEFT JOIN sys_dept pd on d.parent_id = pd.dept_id")
                    .append(" LEFT JOIN sys_dict_data dd ON t.`status` = dd.dict_value AND dd.dict_type = " +
                     "'budget_status'");
            for (TableModel field : fieldList) {
                if (field.getFieldType().equals("4")) {
                    String dictType = StringUtils.isEmpty(field.getDictType()) ? " abc " : field.getDictType();
                    sql.append(" LEFT JOIN sys_dict_data " + field.getFieldName() + " ON e." + field.getFieldName() + " = " + field.getFieldName() + ".dict_value AND " + field.getFieldName() + ".dict_type = '" + dictType + "'");
                }
            }
            sql.append(" WHERE t.task_id = ").append(query.getTaskId()).append(" AND t.del_flag = '0'").append(" AND t.dept_id IN (");
            for (int i = 0; i < deptIds.size(); i++) {
                sql.append(deptIds.get(i));
                if (i < deptIds.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(") order by t.dept_id");
            List<Map<String, Object>> dates = dynamicSqlService.dynamicSelectListSql(sql.toString());
            if (!dates.isEmpty()) {
                list.addAll(dates);
            }
        }
        return list;
    }

    private void changeYearName(List<TableModel> fieldList, Integer year) {
        for (TableModel tableModel : fieldList) {
            if (tableModel.getFieldDisplayName().equals("后年")) {
                tableModel.setFieldDisplayName((year + 2) + "年");
            } else if (tableModel.getFieldDisplayName().equals("大后年")) {
                tableModel.setFieldDisplayName((year + 3) + "年");
            } else if (tableModel.getFieldDisplayName().equals("大大后年")) {
                tableModel.setFieldDisplayName((year + 4) + "年");
            } else if (tableModel.getFieldDisplayName().equals("大大大后年")) {
                tableModel.setFieldDisplayName((year + 5) + "年");
            }
        }
    }

}
