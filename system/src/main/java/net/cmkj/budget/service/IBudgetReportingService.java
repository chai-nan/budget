package net.cmkj.budget.service;

import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.Budget;
import net.cmkj.budget.domain.BudgetItem;
import net.cmkj.budget.domain.ReportingVersion;
import net.cmkj.budget.domain.TableModel;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.domain.entity.SysDept;
import net.cmkj.common.core.page.TableDataInfo;

import java.util.List;
import java.util.Map;

/**
 * 预算填报Service接口
 * 
 * @author cmkj
 * @date 2024-07-15
 */
public interface IBudgetReportingService {

    List<BudgetItem> itemList(BudgetItem budgetItem, Long userId);

    List<TableModel> itemFieldList(Long itemId,Long taskId);

    List<Map<String, Object>> budgetList(Budget query,Long userId);

    List<TableModel> itemEditField(Budget query);

    List<TableModel> itemAuditField(Budget query);

    AjaxResult add(Map<String, String> map);

    AjaxResult update(Map<String, String> map);

    AjaxResult delete(Budget budget);

    AjaxResult updateStatus(Budget budget);

    AjaxResult reportingLog(Budget budget);

    Map<String,Object> itemInfo(Budget budget);

    Map<String, Object> itemSubmissionDept(Budget budget);

    AjaxResult saveVersion(ReportingVersion reportingVersion);

    List<Map<String, Object>> budgetAddList(Budget query,Long userId);

    List<SysDept> itemFieldDept(Budget query,Long userId);

    void export(HttpServletResponse response, Budget query, Long userId);

    TableDataInfo budgetSummaryList(Budget query);

    AjaxResult backfill(Budget budget,Long userId);

    Map<String, Object> itemCostByDept(Budget query);

    AjaxResult checkStatus();

    AjaxResult updateTaskStatus(Budget budget);

    AjaxResult clickAudit(Budget budget);

    AjaxResult rejectByItem(Budget budget,Long userId);
}
