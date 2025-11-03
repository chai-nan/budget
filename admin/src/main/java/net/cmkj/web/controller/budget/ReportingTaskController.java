package net.cmkj.web.controller.budget;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.budget.domain.ReportingTask;
import net.cmkj.budget.service.IReportingTaskService;
import net.cmkj.common.annotation.Log;
import net.cmkj.common.core.controller.BaseController;
import net.cmkj.common.core.domain.AjaxResult;
import net.cmkj.common.core.page.TableDataInfo;
import net.cmkj.common.enums.BusinessType;
import net.cmkj.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预算任务Controller
 * 
 * @author cmkj
 * @date 2024-07-16
 */
@RestController
@RequestMapping("/budget/task")
public class ReportingTaskController extends BaseController
{
    @Autowired
    private IReportingTaskService reportingTaskService;

    /**
     * 查询预算任务列表
     */
    @SaCheckPermission("budget:task:list")
    @GetMapping("/list")
    public TableDataInfo list(ReportingTask reportingTask) {
        startPage();
        List<ReportingTask> list = reportingTaskService.selectReportingTaskList(reportingTask);
        return getDataTable(list);
    }

    /**
     * 导出预算任务列表
     */
    @SaCheckPermission("budget:task:export")
    @Log(title = "预算任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReportingTask reportingTask) {
        List<ReportingTask> list = reportingTaskService.selectReportingTaskList(reportingTask);
        ExcelUtil<ReportingTask> util = new ExcelUtil<ReportingTask>(ReportingTask.class);
        util.exportExcel(response, list, "预算任务数据");
    }

    /**
     * 获取预算任务详细信息
     */
    @SaCheckPermission("budget:task:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(reportingTaskService.selectReportingTaskById(id));
    }

    /**
     * 新增预算任务
     */
    @SaCheckPermission("budget:task:add")
    @Log(title = "预算任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ReportingTask reportingTask) {
        return toAjax(reportingTaskService.insertReportingTask(reportingTask));
    }

    @Log(title = "预算任务", businessType = BusinessType.INSERT)
    @PostMapping("/relaunch")
    public AjaxResult relaunch(@RequestBody ReportingTask reportingTask) {
        return reportingTaskService.relaunch(reportingTask, getLoginUser());
    }

    /**
     * 修改预算任务
     */
    @SaCheckPermission("budget:task:edit")
    @Log(title = "预算任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ReportingTask reportingTask) {
        return toAjax(reportingTaskService.updateReportingTask(reportingTask));
    }

    /**
     * 删除预算任务
     */
    @SaCheckPermission("budget:task:remove")
    @Log(title = "预算任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(reportingTaskService.deleteReportingTaskByIds(ids));
    }

    @Log(title = "预算任务须知", businessType = BusinessType.UPDATE)
    @PostMapping("/updateTips/{id}")
    public AjaxResult updateTips(@PathVariable("id") Long id) {
        return toAjax(reportingTaskService.updateTips(id));
    }

    @GetMapping("/selectTips/{id}")
    public AjaxResult selectTips(@PathVariable("id") Long id) {
        return AjaxResult.success(reportingTaskService.selectTips(id));
    }

    @GetMapping("/listAll")
    public List<ReportingTask> listAll(ReportingTask reportingTask) {
        return reportingTaskService.listAll(reportingTask);
    }
}
